package application;
	
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;


public class Main extends Application 
{
	public static Stage primaryStage;
	
	@Override
	public void start(Stage primaryStage) 
	{
		Main.primaryStage = primaryStage;
		Main.primaryStage.getIcons().add(new Image(this.getClass().getResourceAsStream("icon.png")));
		Main.primaryStage.show();
		PromptController pc = new PromptController();
		pc.show();
	}
	
	public static String format(double d)
	{
		return (d == (int) d ? String.format("%d", (int) d) : String.format("%s", Math.round(d * 100000) / 100000.0));
	}
	
	public static void main(String[] args) 
	{
		launch(args);
//		Formula x = new Formula(97, 72);
//		System.out.println(x.getHeightAtVelocity(-82));
	}
}
