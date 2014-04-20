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
	Font font;
	Color valitud = Color.RED;
	Color mitte_valitud = Color.BLACK;
	Color mitte_valitud_invert = Color.BEIGE;
	Color mitte_valitud_not_invert = mitte_valitud;
	Color teksti_varv = Color.FORESTGREEN;
	Color teksti_varv_invert = Color.BLACK;
	Color teksti_varv_not_invert = teksti_varv;
	
	boolean inverted = false;
	Color leitud_taust = Color.GOLD;
	Color vajutatud = Color.YELLOW;
	private boolean leitud = false;
	//Color vabastatud = Color.BLUE;
	public StringProperty textProperty() { return text.textProperty(); }

	TextBox(String string, int rida, int veerg, double width, double height) {
		this.rida = rida;
		this.veerg = veerg;
		this.string = string;
		Random r = new Random();
		String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	    if (this.string.equals(".")) {
	    	String random_karakter = Character.toString(alphabet.charAt(r.nextInt(alphabet.length())));
	    	this.string = " ";
	    	//this.string = random_karakter;
	    }
		this.text = new Text(this.string);
		text.setTextAlignment(TextAlignment.CENTER);
		text.setFill(teksti_varv);
		text.setTextOrigin(VPos.CENTER);
		font = get_font(height);
		text.setFont(Font.font("Comic Sans MS", height/1.2));
		text.setFontSmoothingType(FontSmoothingType.LCD);

		this.rectangle = new Rectangle(width, height);
		rectangle.setFill(Color.BLACK);

		this.clip = new Rectangle(width, height);
		text.setClip(clip);

		this.getChildren().addAll(rectangle, text);
	}
	
	Font get_font(double size){
		return Font.font("Comic Sans MS", size);
	}
	
	void invert(){
		inverted = !inverted;
		if (inverted) {
			mitte_valitud = mitte_valitud_invert;
			teksti_varv = teksti_varv_invert;
		}
		else {
			mitte_valitud = mitte_valitud_not_invert;
			teksti_varv = teksti_varv_not_invert;
		}
		if (!ruudu_kohal) setFill(mitte_valitud);
		text.setFill(teksti_varv);
	}
	
	String getText(){
		return string;
	}
	
	void setFill(Color c){
		rectangle.setFill(c);
	}
	
	void setLeitudFill(Color c){
		leitud_taust = c;
	}

	public boolean isLeitud() {
		return leitud;
	}

	public void setLeitud(boolean leitud) {
		this.leitud = leitud;
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
				if (leitud) setFill(leitud_taust);
				else setFill(mitte_valitud);
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
			if (!this.ruudu_kohal) {
				if (leitud) setFill(leitud_taust);
				else setFill(mitte_valitud);
			}
		}
		
	}
	
	public void setSize(double d){
		rectangle.setWidth(d);
		rectangle.setHeight(d);
		clip.setWidth(d);
		clip.setHeight(d);
		text.setFont(get_font(d/2));
		layoutChildren();
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