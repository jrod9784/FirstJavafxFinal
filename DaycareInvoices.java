package application;

import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class DaycareInvoices extends Application{
	private TextField tfinvoicePayment = new TextField();
	private TextField daysAtDaycare = new TextField();
	private TextField childAge = new TextField();
	private TextField shoppingList = new TextField();
	ArrayList<String> shoppingListArray = new ArrayList<>();
	private Button addToShoppingList = new Button("Add to List");
	private TextArea shoppingListText = new TextArea();
	private Button saveAs = new Button("Save As:");
	
	@Override
	public void start(Stage primaryStage) {
		
	
		GridPane invoice = new GridPane();
		
		shoppingListText.setPrefSize(100, 10);
		invoice.add(shoppingList, 5, 1);
		invoice.add(new Label("Add Item:"), 4, 1);
		invoice.add(addToShoppingList, 6, 2);
		invoice.add(shoppingListText, 6, 1);
		invoice.add(saveAs, 6, 3);
		saveAs.setOnAction(e -> {
			FileChooser FC = new FileChooser();
			FC.getExtensionFilters().addAll(new ExtensionFilter("All Files", "*.*"));
			File file = FC.showSaveDialog(primaryStage);
			if(file != null) {
				try {
					PrintStream PS = new PrintStream(file);
					PS.println(shoppingListText.getText());
					PS.flush();
				}catch(FileNotFoundException e1){
					System.out.println("Cannot Save");
				}
			}
			shoppingListText.clear();
		});
		
		shoppingList.setOnKeyPressed(e->{
			if(e.getCode() == KeyCode.ENTER) {
				createShoppingList();
			}
		});
		
		addToShoppingList.setOnAction(e -> {
			createShoppingList();
		});
		invoice.setPrefSize(800, 400);
		invoice.setAlignment(Pos.TOP_LEFT);
		invoice.setPadding(new Insets(10, 10, 10, 10));
		invoice.setHgap(10);
		invoice.setVgap(10);
		invoice.add(new Label("Number of days: "), 1, 1);
		invoice.add(new Label("Enter child's age: "), 1, 2);
		invoice.add(childAge, 2, 2);
		Button calculateInvoice = new Button("Calculate Weekly Payment");
		invoice.add(calculateInvoice, 2, 4);
		invoice.add(tfinvoicePayment, 2, 3);
		tfinvoicePayment.setEditable(false);
		invoice.add(new Label("Total Owed: "), 1, 3);
		
		calculateInvoice.setOnAction(e -> getWeeklyPayment());
	
		invoice.add(daysAtDaycare, 2, 1);	
		calculateInvoice.setStyle("-fx-background-color: gold");
		
	
		Scene invoiceScene = new Scene(invoice);
		primaryStage.setTitle("Invoices");
		primaryStage.setScene(invoiceScene);
		primaryStage.show();

		
	}
	public static void main(String[] args) {
		Application.launch(args);
	}
	
	private void getWeeklyPayment() {
		int days = Integer.parseInt(daysAtDaycare.getText());
		int months = Integer.parseInt(childAge.getText());
		NewInvoice cameron = new NewInvoice(days, months);
		
		tfinvoicePayment.setText(String.valueOf(cameron.getWeeklyPayment()));
		
	}
	private void createShoppingList() {
		shoppingListArray.add(shoppingList.getText());
		System.out.println(shoppingListArray);
		shoppingListText.appendText(shoppingList.getText() + ", ");
		shoppingList.clear();

	}
	
}


