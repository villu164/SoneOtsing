package application;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class Karakter extends Group {
	private double laius = 200;
	private double korgus = laius;
	Karakter(){
	}
	
	public Group anna_karakter(char c){
		System.out.println("Genereerin karakteri");
		Rectangle kast = new Rectangle(laius, korgus, Color.YELLOW);
		Text tekst = new Text(0,0,Character.toString('A'));
		tekst.setFont(Font.font ("Courier New", laius));
		tekst.setFill(Color.RED);
		tekst.setTextAlignment(TextAlignment.JUSTIFY);
		tekst.setLayoutX(-tekst.getBoundsInParent().getMinX());
		tekst.setLayoutY(-tekst.getBoundsInParent().getMinY());

		//System.out.println(tekst.getBoundsInParent().getMinX() + "," + tekst.getBoundsInParent().getMinY());
		//tekst.autosize();
		Group karakter = new Group();
		tekst.setTextAlignment(TextAlignment.CENTER);;
		//karakter.getChildren().add(kast);
		karakter.getChildren().add(tekst);
		return karakter;
	}
}
