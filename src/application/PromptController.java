package application;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class PromptController
{
	@FXML private TextField heightTxtFld, velocityTxtFld;
	@FXML private Button calculateBtn;
	
	public void show()
	{
		try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Prompt.fxml"));

            loader.setController(this);
            Main.primaryStage.setScene(new Scene(loader.load()));
            Main.primaryStage.centerOnScreen();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }

		init();
	}
	
	public void init()
	{
		this.calculateBtn.setOnAction(e ->
		{
			try
			{
				CalculationController cc = new CalculationController(new Formula(Double.valueOf(heightTxtFld.getText()), Double.valueOf(velocityTxtFld.getText())));
				cc.show();
			}
			catch (NumberFormatException e1)
			{
				
			}
		});
	}
}
