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
            PromptController pc = new PromptController();
            pc.show();
        });
	}
	
	private String work(double input)
	{
		switch (this.currentSelection)
		{
			case "Height at Time" :
				return heightAtTimeWork(input);
			
			case "Height at Velocity" :
				return heightAtVelocityWork(input);
			
			case "Velocity at Time" :
				return velocityAtTimeWork(input);
			
			case "Velocity at Height" :
				return velocityAtHeightWork(input);

			case "Time at Height" :
				return timeAtHeightWork(input);
			
			case "Time at Velocity" :
				return timeAtVelocityWork(input);
			
			case "Max Height" :
				return heightAtVelocityWork(0);
			
			case "Time to Max Height" :
				return timeAtVelocityWork(0);
			
			case "Time to Start Position" :
				return timeAtHeightWork(this.formula.getInitHeight());
			
			case "Time to Ground" :
				return timeAtHeightWork(0);
			
			default :
				return "";
		}
	}
	
	private String answer(double input)
	{
		switch (this.currentSelection)
		{
			case "Height at Time" :
				return heightAtTimeAnswer(input);
			
			case "Height at Velocity" :
				return heightAtVelocityAnswer(input);
				
			case "Velocity at Time" :
				return velocityAtTimeAnswer(input);
			
			case "Velocity at Height" :
				return velocityAtHeightAnswer(input);
			
			case "Time at Height" :
				return timeAtHeightAnswer(input);
			
			case "Time at Velocity" :
				return timeAtVelocityAnswer(input);
				
			case "Max Height" :
				return heightAtVelocityAnswer(0);
			
			case "Time to Max Height" :
				return timeAtVelocityAnswer(0);
			
			case "Time to Start Position" :
				return timeAtHeightAnswer(this.formula.getInitHeight());
			
			case "Time to Ground" :
				return timeAtHeightAnswer(0);
			
			default :
				return "";
		}
	}
	
	private String heightAtTimeWork(double time)
	{
		return "h(" + Main.format(time) + ") = " + this.formula.getHeightFormula(time);
	}
	
	private String heightAtVelocityWork(double velocity)
	{
		double time = this.formula.getTimeAtVelocity(velocity);
		return "v(t) = " + Main.format(velocity) + "\n" +
			   Main.format(velocity) + " = " + this.formula.getVelocityFormula() + "\n" +
			   (velocity == 0 ? "" : "0 = " + this.formula.getVelocityZeroFormula(velocity) + "\n") +
			   "t = " + Main.format(time) + "\n" +
			   "h(" + Main.format(time) + ") = " + this.formula.getHeightFormula(time);
	}
	
	private String velocityAtTimeWork(double time)
	{
		return "v(" + Main.format(time) + ") = " + this.formula.getVelocityFormula(time);
	}
	
	private String velocityAtHeightWork(double height)
	{
		double[] times = this.formula.getTimeAtHeight(height);
		String work = "h(t) = " + Main.format(height) + "\n" +
				      Main.format(height) + " = " + this.formula.getHeightFormula() + "\n" +
				      (height == 0 ? "" : "0 = " + this.formula.getHeightZeroFormula(height)) + "\n" +
				      "t = " + this.formula.getTimeFormula(height) + "\n" + 
				      (times.length == 1 ? "t = " + Main.format(times[0]) + " sec" : 
				      "t = " + Main.format(times[0]) + " sec, t = " + Main.format(times[1]) + " sec") + "\n";
		
		for (int i = 0; i < times.length; i++)
		{
			if (times[i] > 0)
			{
				work += "v(" + Main.format(times[i]) + ") = " + this.formula.getVelocityFormula(times[i]) + "\n";
			}
		}
		return work;
	}
	
	private String timeAtHeightWork(double height)
	{
		return "h(t) = " + Main.format(height) + "\n" + 
			   Main.format(height) + " = " + this.formula.getHeightFormula() + "\n" + 
			   "0 = " + this.formula.getHeightZeroFormula(height) + "\n" + 
			   "t = " + this.formula.getTimeFormula(height);
	}
	
	private String timeAtVelocityWork(double velocity)
	{
		return "v(t) = " + Main.format(velocity) + "\n" + 
			   Main.format(velocity) + " = " + this.formula.getVelocityFormula() + "\n" +
			   (velocity == 0 ? "" : "0 = " + this.formula.getVelocityZeroFormula(velocity));
	}

	private String heightAtTimeAnswer(double time)
	{
		return "h(" + Main.format(time) + ") = " + Main.format(this.formula.getHeightAtTime(time)) + " ft";
	}	

	private String heightAtVelocityAnswer(double velocity)
	{
		return "h(" + Main.format(this.formula.getTimeAtVelocity(velocity)) + ") = " + Main.format(this.formula.getHeightAtVelocity(velocity)) + " ft";
	}

	private String velocityAtTimeAnswer(double time)
	{
		return "v(" + Main.format(time) + ") = " + Main.format(this.formula.getVelocityAtTime(time)) + " ft/s";
	}	

	private String velocityAtHeightAnswer(double height)
	{
		double[] answers = this.formula.getVelocityAtHeight(height);
		String ans = "";
		for (int i = 0; i < answers.length; i++)
		{
			double time = this.formula.getTimeAtHeight(height)[i];
			if (time >= 0)
			{
				ans += "v(" + Main.format(time) + ") = " + Main.format(answers[i]) + " ft/s";
				if (i == 0)
					ans += ", ";
			}
		}
		return ans;
	}	

	private String timeAtHeightAnswer(double height)
	{
		double[] answers = this.formula.getTimeAtHeight(height);
		return "t = " + (answers.length == 1 ? Main.format(answers[0]) + " ft/s" : Main.format(answers[0]) + " sec, t = " + Main.format(answers[1]) + " sec");
	}	

	private String timeAtVelocityAnswer(double velocity)
	{
		return "t = " + Main.format(this.formula.getTimeAtVelocity(velocity)) + " sec";
	}
}
