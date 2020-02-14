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
	
	private Stage stage;
	
	public void show()
	{
		this.stage = new Stage();
		
		try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Prompt.fxml"));

            loader.setController(this);
            this.stage.setResizable(false);
            this.stage.setTitle("Prompt");
            this.stage.setScene(new Scene(loader.load()));
            this.stage.centerOnScreen();
            this.stage.show();
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
				this.stage.close();
				CalculationController cc = new CalculationController(new Formula(Double.valueOf(heightTxtFld.getText()), Double.valueOf(velocityTxtFld.getText())));
				cc.show();
			}
			catch (NumberFormatException e1)
			{
				
			}
		});
	}
}
