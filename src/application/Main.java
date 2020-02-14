package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;


public class Main extends Application 
{
	public static Stage primaryStage;
	
	@Override
	public void start(Stage primaryStage) 
	{
		Main.primaryStage = primaryStage;
		PromptController pc = new PromptController();
		pc.show();
	}
	
	public static void main(String[] args) 
	{
		launch(args);
//		Formula x = new Formula(97, 72);
//		System.out.println(x.getHeightAtVelocity(-82));
	}
}
