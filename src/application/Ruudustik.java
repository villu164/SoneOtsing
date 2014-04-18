package application;


import java.awt.Point;

import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.TitledPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class Ruudustik extends Group {
	private double laius = 500;
	private Paiguta paiguta;
	private TextBox valitud;
	private Scene scene;
	private EventType viimane_event;
	private int mouse_released_rida;
	private int mouse_released_veerg;
	private int mouse_pressed_rida;
	private int mouse_pressed_veerg;
	private int mouse_dragged_rida;
	private int mouse_dragged_veerg;
	private double algus_x;
	private double algus_y;
	private double lopp_x;
	private double lopp_y;
	private TextBox algus_ruut;
	private TextBox ruudu_kohal;
	private TextBox[][] tb;
	private boolean mouse_up = false;
	private boolean mouse_down = false;
	Ruudustik(Paiguta p, Scene scene){
		System.out.println(p.leiaSoned());
		System.out.println(Kompass.kompass);
		paiguta = p;
		this.scene = scene;
		System.out.println(p);
	}
	
	public TextBox get_ruudu_kohal(double x, double y){
		for(int i = 0;i<tb.length;i++){
			for(int j = 0;j<tb[i].length;j++){
				if (tb[i][j].getBoundsInParent().contains(x, y) && (ruudu_kohal == null || !(ruudu_kohal.rida == tb[i][j].rida && ruudu_kohal.veerg == tb[i][j].veerg))) {
					ruudu_kohal = tb[i][j];
					//System.out.println(i + "," + j + " " + ruudu_kohal.getText());
					return ruudu_kohal;
				}
			}
		}
		return null;
	}
	
	public Group anna_ruudustik(double laius){
		System.out.println("Genereerin ruudustiku");
		Group ruudustik = new Group();
		GridPane grid = new GridPane();
		char[][] tabel = paiguta.getMaatriks().getTabel();
		
		int ridu = paiguta.getMaatriks().getRidu();
		int veerge = paiguta.getMaatriks().getVeerge();
		tb = new TextBox[ridu][veerge];
		
		for(int i = 0;i<tabel.length;i++){
			for(int j = 0;j<tabel[i].length;j++){
				final String karakter = Character.toString(tabel[i][j]);
				final int rida = i;
				final int veerg = j;
				final TextBox ruut = new TextBox(karakter,rida,veerg,laius/veerge,laius/ridu);
				tb[i][j] = ruut;
				ruut.addEventHandler(MouseEvent.ANY, new EventHandler<MouseEvent>() {
				    public void handle(MouseEvent me) {
				    	//System.out.println(me.getEventType());
				    	//System.out.println(me);
				    	if ((valitud != ruut || me.getEventType() != viimane_event)) {
				    		valitud = ruut;
				    		viimane_event = me.getEventType();
				    		
				    		//System.out.println("Valitud:" + karakter + " i=" + rida + ",j=" + veerg + " " +  me.toString());
				    	}
				    	
				    	if (me.getEventType().toString().equals("MOUSE_PRESSED")){
			    			mouse_down = true;
			    			mouse_up = false;
			    			algus_ruut = ruudu_kohal;
			    		}
			    		if (me.getEventType().toString().equals("MOUSE_DRAGGED")){
			    			
			    		}
			    		if (me.getEventType().toString().equals("MOUSE_RELEASED")){
			    			mouse_down = false;
			    			mouse_up = true;
			    			System.out.println(algus_ruut + " " + ruudu_kohal);
			    		}
				    	
				    }
				    
				}
				);
				
				scene.addEventHandler(MouseEvent.ANY, new EventHandler<MouseEvent>() {
				    public void handle(MouseEvent me) {
				    	//System.out.println(me.getEventType());
				    	//System.out.println(me);
				    	ruudu_kohal = get_ruudu_kohal(me.getX(),me.getY());
				    }
				});
				
				
				
				grid.add(ruut, j,i);
			}
		}
			
		//tekst.setTextAlignment(TextAlignment.JUSTIFY);
		grid.setLayoutX(0);
		grid.setLayoutY(0);
		
		ruudustik.getChildren().add(grid);
		return ruudustik;
	}
}
