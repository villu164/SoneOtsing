package application;

import java.util.Random;

import javafx.beans.property.StringProperty;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontSmoothingType;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

class LahendBox extends Group {
	private Lahend lahend;
	private Text text;

	LahendBox(Lahend lahend, Font font) {
		text =  new Text(lahend.toString());
        text.setStrikethrough(lahend.isLeitud());
        text.setTextAlignment(TextAlignment.CENTER);
		text.setFill(Color.BLACK);
		text.setTextOrigin(VPos.CENTER);
		text.setFont(font);
		text.setFontSmoothingType(FontSmoothingType.LCD);
		this.lahend = lahend;
        this.getChildren().add(text);
	}
	
	public void checkLeitud(){
		if (this.lahend.isLeitud()) text.setStrikethrough(true);
	}
}