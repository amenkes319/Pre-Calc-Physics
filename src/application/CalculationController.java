package application;

import java.io.IOException;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class CalculationController
{
	@FXML private Label heightLbl, velocityLbl, workLbl, answerLbl;
	@FXML private ChoiceBox<String> typeDropdown;
	@FXML private TextField inputTxtFld;
	@FXML private Button calculateBtn, backBtn;
	
	private Formula formula;
	private String currentSelection;

	public CalculationController(Formula formula)
	{
		this.formula = formula;
		this.currentSelection = "";
	}

	public void show()
	{
		try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Calculations.fxml"));

            loader.setController(this);
            Main.primaryStage.setResizable(false);
            Main.primaryStage.setTitle("Calculations");
            Main.primaryStage.setScene(new Scene(loader.load()));
            Main.primaryStage.centerOnScreen();
            Main.primaryStage.show();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
		
		init();
	}
	
	public void init()
	{
		this.heightLbl.setText("h(t) = " + this.formula.getHeightFormula());
		this.velocityLbl.setText("v(t) = " + this.formula.getVelocityFormula());
		
		this.typeDropdown.getItems().add("Height at Time");
		this.typeDropdown.getItems().add("Height at Velocity");
		this.typeDropdown.getItems().add("Velocity at Time");
		this.typeDropdown.getItems().add("Velocity at Height");
		this.typeDropdown.getItems().add("Time at Height");
		this.typeDropdown.getItems().add("Time at Velocity");
		this.typeDropdown.getItems().add("Max Height");
		this.typeDropdown.getItems().add("Time to Max Height");
		this.typeDropdown.getItems().add("Time to Start Position");
		this.typeDropdown.getItems().add("Time to Ground");
		
		
		this.typeDropdown.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() 
		{
			@Override
			public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number2) 
			{
				currentSelection = typeDropdown.getItems().get((Integer) number2);
			
				if (currentSelection.equals("Height at Time") || currentSelection.equals("Height at Velocity"))
					inputTxtFld.setPromptText("Enter a height");
				else if (currentSelection.equals("Velocity at Time") || currentSelection.equals("Velocity at Height"))
					inputTxtFld.setPromptText("Enter a velocity");
				else if (currentSelection.equals("Time at Height") || currentSelection.equals("Time at Velocity"))
					inputTxtFld.setPromptText("Enter a time");
				else
					inputTxtFld.setPromptText("");
			
				if (currentSelection.equals("Max Height") || currentSelection.equals("Time to Max Height") ||
					currentSelection.equals("Time to Start Position") || currentSelection.equals("Time to Ground"))
					inputTxtFld.setDisable(true);
				else
					inputTxtFld.setDisable(false);
			}
		});
		
		this.calculateBtn.setOnAction(e -> 
		{
			double input = this.inputTxtFld.getText().equals("") ? 0 : Double.valueOf(this.inputTxtFld.getText());
			if (input == 0)
				this.inputTxtFld.setText("0");
			
			this.workLbl.setText(work(input));
			this.answerLbl.setText(answer(input));
		});
		
		this.backBtn.setOnAction(e ->
        {
        	Main.primaryStage.close();
            PromptController pc = new PromptController();
            pc.show();
        });
	}
	
	private String answer(double input)
	{
		double[] answers;
		switch (this.currentSelection)
		{
			case "Height at Time" :
				return "h(" + input + ") = " + format(this.formula.getHeightAtTime(input)) + " ft";
			
			case "Height at Velocity" :
				return "h(" + format(this.formula.getTimeAtVelocity(input)) + ") = " + format(this.formula.getHeightAtVelocity(input)) + " ft";
			
			case "Velocity at Time" :
				return "v(" + input + ") = " + format(this.formula.getVelocityAtTime(input)) + " ft/s";
			
			case "Velocity at Height" :
				answers = this.formula.getVelocityAtHeight(input);
				return "v(" + format(this.formula.getTimeAtHeight(input)[0]) + ") = " + (answers.length == 1 ? format(answers[0]) + " ft/s" : 
						format(answers[0]) + " ft/s, v(" + format(this.formula.getTimeAtHeight(input)[1]) + ") = " + format(answers[1]) + " ft/s");
			
			case "Time at Height" :
				answers = this.formula.getTimeAtHeight(input);
				return "t = " + (answers.length == 1 ? answers[0] + " ft/s" : "t = " + answers[0] + " sec, t = " + answers[1] + " sec");
			
			case "Time at Velocity" :
				return "t = " + this.formula.getTimeAtVelocity(input) + " sec";
			
			case "Max Height" :
				return "h(" + this.formula.getTimeAtVelocity(0) + ") = " + this.formula.getMaxHeight() + " ft";
			
			case "Time to Max Height" :
				return "t = " + this.formula.getTimeToMaxHeight() + " sec";
			
			case "Time to Start Position" :
				return "t = " + this.formula.getTimeToStartPosition() + " sec";
			
			case "Time to Ground" :
				return "t = " + this.formula.getTimeToGround() + " sec";
			
			default :
				return "";
		}
	}
	
	private String work(double input)
	{
		double[] answers;
		switch (this.currentSelection)
		{
			case "Height at Time" :
				return "h(" + format(input) + ") = " + this.formula.getHeightFormula(input);
			
			case "Height at Velocity" :
				double time = this.formula.getTimeAtVelocity(input);
				return "v(t) = " + format(input) + "\n" +
					   format(input) + " = " + this.formula.getVelocityFormula() + "\n" +
					   "0 = " + format(this.formula.getInitVelocity() - input) + " - 32t" + "\n" +
					   "t = " + format(time) + "\n" +
					   "h(" + format(time) + ") = " + this.formula.getHeightFormula(time);
			
			case "Velocity at Time" :
				return this.formula.getVelocityAtTime(input) + " ft/s";
			
			case "Velocity at Height" :
				answers = this.formula.getVelocityAtHeight(input);
				return answers.length == 1 ? answers[0] + " ft/s" : answers[0] + " ft/s and " + answers[1] + " ft/s";
			
			case "Time at Height" :
				answers = this.formula.getTimeAtHeight(input);
				return answers.length == 1 ? answers[0] + " ft/s" : answers[0] + " sec and " + answers[1] + " sec";
			
			case "Time at Velocity" :
				return this.formula.getTimeAtVelocity(input) + " sec";
			
			case "Max Height" :
				return this.formula.getMaxHeight() + " ft";
			
			case "Time to Max Height" :
				return this.formula.getTimeToMaxHeight() + " sec";
			
			case "Time to Start Position" :
				return this.formula.getTimeToStartPosition() + " sec";
			
			case "Time to Ground" :
				return this.formula.getTimeToGround() + " sec";
			
			default :
				return "";
		}
	}
	
	private String format(double d)
	{
		return (d == (int) d ? String.format("%d", (int) d) : String.format("%s", d));
	}
}
