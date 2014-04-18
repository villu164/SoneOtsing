package application;

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

class TextBox extends Group {
	private Text text;
	private Rectangle rectangle;
	private Rectangle clip;
	private String string;
	int rida;
	int veerg;
	private boolean ruudu_kohal = false;
	private boolean hiir_alla = false;
	private boolean hiir_yles = false;
	Color valitud = Color.RED;
	Color mitte_valitud = Color.BLACK;
	Color vajutatud = Color.YELLOW;
	//Color vabastatud = Color.BLUE;
	public StringProperty textProperty() { return text.textProperty(); }

	TextBox(String string, int rida, int veerg, double width, double height) {
		this.rida = rida;
		this.veerg = veerg;
		this.string = string;
		this.text = new Text(string);
		text.setTextAlignment(TextAlignment.CENTER);
		text.setFill(Color.FORESTGREEN);
		text.setTextOrigin(VPos.CENTER);
		text.setFont(Font.font("Comic Sans MS", height/1.2));
		text.setFontSmoothingType(FontSmoothingType.LCD);

		this.rectangle = new Rectangle(width, height);
		rectangle.setFill(Color.BLACK);

		this.clip = new Rectangle(width, height);
		text.setClip(clip);

		this.getChildren().addAll(rectangle, text);
	}
	
	String getText(){
		return string;
	}
	
	void setFill(Color c){
		rectangle.setFill(c);
	}
	

	public boolean isRuudu_kohal() {
		return ruudu_kohal;
	}

	public void setRuudu_kohal(boolean ruudu_kohal) {
		if (!this.hiir_alla) {
			this.ruudu_kohal = ruudu_kohal;
			if (ruudu_kohal) {
				setFill(valitud);
			}
			else {
				setFill(mitte_valitud);
			}
			
		}
	}

	public boolean isHiir_alla() {
		return hiir_alla;
	}

	public void setHiir_alla(boolean hiir_alla) {
		this.hiir_alla = hiir_alla;
		if (hiir_alla) {
			setFill(vajutatud);
		}
		else {
			if (!this.ruudu_kohal) setFill(mitte_valitud);
		}
		
	}

	public boolean isHiir_yles() {
		return hiir_yles;
	}

	public void setHiir_yles(boolean hiir_yles) {
		this.hiir_yles = hiir_yles;
	}

	@Override protected void layoutChildren() {
		final double w = rectangle.getWidth();
		final double h = rectangle.getHeight();
		clip.setWidth(w);
		clip.setHeight(h);
		clip.setLayoutX(0);
		clip.setLayoutY(-h/2);

		text.setWrappingWidth(w * 0.9);
		text.setLayoutX(w / 2 - text.getLayoutBounds().getWidth() / 2);
		text.setLayoutY(h / 2);
	}
	public String toString(){
		return string + "(" + rida + "," + veerg + ")";
	}
}