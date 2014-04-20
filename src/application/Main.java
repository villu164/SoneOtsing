package application;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontSmoothingType;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;


public class Main extends Application {
	private Group ruut;
	private Stage global_peaLava;
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
	private StringBuilder konsool = new StringBuilder(10);
	private ArrayList<LahendBox> lbal = new ArrayList<LahendBox>();
	private boolean mouse_up = false;
	private boolean mouse_down = false;
	private boolean voit = false;

	public Color get_juhuslik_fill(){
		int R = (int)(Math.random()*256);
		int G = (int)(Math.random()*256);
		int B= (int)(Math.random()*256);
		return Color.rgb(R, G, B); //random color, but can be bright or dull
	}
	public Color get_juhuslik_ilus_fill(){
		//to get rainbow, pastel colors
		Random random = new Random();
		final float hue = random.nextFloat();
		final float saturation = 0.9f;//1.0 for brilliant, 0.0 for dull
		final float luminance = 1.0f; //1.0 for brighter, 0.0 for black
		return Color.hsb(hue, saturation, luminance);
	}

	public TextBox get_ruudu_kohal(double x, double y, MouseEvent me){
		boolean leitud = false;
		boolean kinnita = false;
		boolean lohista = false;
		TextBox viimane_ruut = null;
		TextBox esimene_ruut = null;
		TextBox ruudu_kohal = null;
		ArrayList<TextBox> pakkumine = new ArrayList<TextBox>();
		//System.out.println(me.getEventType());
		for(int i = 0;i<tb.length;i++){
			for(int j = 0;j<tb[i].length;j++){
				TextBox ruut = tb[i][j];

				if (ruut.isHiir_alla()) esimene_ruut = ruut;
				if (leitud) { 
					//kui esimene ruut on leitud, siis k��ik j��rgmised on mitte, sest ilma selle tingimuseta on v��imalik, et korraga on valitud 4 ruutu
					//Seega tavaliselt siia ei tulda
					ruut.setRuudu_kohal(false);
					ruudu_kohal = ruut;
					continue;
				}
				leitud = ruut.getBoundsInParent().contains(x, y);
				if (leitud) viimane_ruut = ruut;
				switch (me.getEventType().toString()) {
				case "MOUSE_PRESSED":
					ruut.setHiir_alla(leitud);
					ruut.setRuudu_kohal(leitud);
					break;

				case "MOUSE_DRAGGED":
					lohista = true;
					ruut.setRuudu_kohal(leitud);
					break;

				case "MOUSE_MOVED":
					ruut.setRuudu_kohal(leitud);
					break;

				case "MOUSE_EXITED_TARGET":
					ruut.setRuudu_kohal(false);
					break;

				case "MOUSE_RELEASED":
					if (leitud) {
						kinnita = true;
						if (esimene_ruut == viimane_ruut) {
							kinnita = false;
							ruut.setRuudu_kohal(false);
							esimene_ruut.setRuudu_kohal(false);
							esimene_ruut.setHiir_alla(false);
							//							ruut.setRuudu_kohal(true);
						}
						else {

							ruut.setRuudu_kohal(leitud);
						}
					}
					else {
						ruut.setRuudu_kohal(false);
					}

					break;

				default:
					break;
				}
			}
		}
		if (esimene_ruut != null && viimane_ruut == null && me.getEventType().toString() == "MOUSE_RELEASED" && ruudu_kohal == null) {
			esimene_ruut.setRuudu_kohal(false);
			esimene_ruut.setHiir_alla(false);
			return null;
		}
		//kas_ruudud_on_joonel(esimene_ruut,viimane_ruut);
		if (!(esimene_ruut == null || viimane_ruut == null || esimene_ruut == viimane_ruut)) {
			for(int i = 0;i<tb.length;i++){
				for(int j = 0;j<tb[i].length;j++){
					TextBox ruut = tb[i][j];
					if (kinnita) {						
						ruut.setHiir_alla(false);
					}
				}
			}
			int pikkus = 2;
			pakkumine.add(esimene_ruut);
			pakkumine.add(viimane_ruut);
			for(int i = Math.min(esimene_ruut.rida, viimane_ruut.rida);i<=Math.max(esimene_ruut.rida, viimane_ruut.rida);i++){
				for(int j = Math.min(esimene_ruut.veerg, viimane_ruut.veerg);j<=Math.max(esimene_ruut.veerg, viimane_ruut.veerg);j++){
					TextBox ruut = tb[i][j];
					boolean tulemus = kas_ruut_on_sirgel(esimene_ruut,viimane_ruut,ruut);
					if (tulemus) {
						pikkus++;
						pakkumine.add(ruut);
					}
				}
			}
			int lahend_rida = (int)(Math.signum(viimane_ruut.rida - esimene_ruut.rida));
			int lahend_veerg = (int)(Math.signum(viimane_ruut.veerg - esimene_ruut.veerg));
			Point punkt = new Point(esimene_ruut.veerg,esimene_ruut.rida);
			Kompass suund = Kompass.toKompass(lahend_rida,lahend_veerg);
			Lahend lahend = new Lahend(pikkus,punkt,suund);
			paiguta.vota(lahend);
			//System.out.println(lahend.toString(true) + " " + kinnita);
			if (kinnita){
				boolean oige = paiguta.leidub(lahend);
				if (oige) {
					Color leitud_fill = get_juhuslik_fill();
					System.out.println("YES:" + lahend);
					for (TextBox ruut : pakkumine){
						ruut.setLeitud(true);
						ruut.setLeitudFill(leitud_fill);
					}
					if (paiguta.leiaSoned().isEmpty() && !voit){
						voitsid();
					}
					for (LahendBox lb : lbal) {
						lb.checkLeitud();
					}
				}
				else{
					System.out.println("NO:" + lahend);
				}
				if (Debug.isDebug()) System.out.println(paiguta.leiaSoned());
			}

			//Lahend lahend = Lahend.parseLahend("");
			//bug 1, kui lohistada aknast v��lja, siis j����b kollane alles. Kui teha kl��ps ainult ��he ruudu peale, siis j����b kollane alles. Kui kollane on alles, siis j����b drag peale
			//but 2, lahendi s��na n��itab aeg,ajalt teibaid 

		}
		//register_selection
		return null;
	}

	private boolean kas_ruut_on_sirgel(TextBox esimene_ruut, TextBox viimane_ruut, TextBox kontrolli){
		if (esimene_ruut == null || viimane_ruut == null || esimene_ruut == viimane_ruut) return false;
		boolean tulemus = kas_ruudud_on_joonel(esimene_ruut,viimane_ruut) != 0 && kas_ruudud_on_joonel(esimene_ruut,viimane_ruut) == kas_ruudud_on_joonel(esimene_ruut,kontrolli) && kas_ruudud_on_joonel(kontrolli,viimane_ruut) == kas_ruudud_on_joonel(esimene_ruut,viimane_ruut);
		boolean vahel = kontrolli.rida < Math.max(esimene_ruut.rida, viimane_ruut.rida) && kontrolli.rida > Math.min(esimene_ruut.rida, viimane_ruut.rida);
		if (tulemus) {
			if (esimene_ruut != kontrolli && viimane_ruut != kontrolli) kontrolli.setFill(Color.GREEN);
		}
		return tulemus;
	}

	private int kas_ruudud_on_joonel(TextBox esimene_ruut, TextBox viimane_ruut) {
		// TODO Auto-generated method stub
		int tulemus = 0;
		if (esimene_ruut != null && viimane_ruut != null && esimene_ruut != viimane_ruut) {
			if (esimene_ruut.rida == viimane_ruut.rida) {
				//System.out.println("rida klapib");
				tulemus = 1;
			}
			if (esimene_ruut.veerg == viimane_ruut.veerg) {
				//System.out.println("veerg klapib");
				tulemus = 2;
			}
			if ((esimene_ruut.veerg - esimene_ruut.rida) == (viimane_ruut.veerg - viimane_ruut.rida)) {
				//System.out.println("diagonaal 1 klapib");
				tulemus = 3;
			}
			if ((esimene_ruut.veerg + esimene_ruut.rida) == (viimane_ruut.veerg + viimane_ruut.rida)) {
				//System.out.println("diagonaal 2 klapib");
				tulemus = 4;
			}
		}
		return tulemus;
	}

	public void voitsid(){
		teade("Voitsid",true);
		voit = true;
		
	}
	
	public void kas_valjun(final Stage peaLava){
		//if (true) System.exit(1);
		// luuakse teine lava
		final Stage kusimus = new Stage();
		// k��simuse ja kahe nupu loomine
		Label label = new Label("Kas tahad kinni panna?");
		Button okButton = new Button("Jah");
		Button cancelButton = new Button("Ei");

		// s��ndmuse lisamine nupule Jah
		okButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				kusimus.hide();
			}
		});

		// s��ndmuse lisamine nupule Ei
		cancelButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				peaLava.show();
				kusimus.hide();
			}
		});
		

		// nuppude grupeerimine
		FlowPane pane = new FlowPane(10, 10);
		pane.setAlignment(Pos.CENTER);
		pane.getChildren().addAll(okButton, cancelButton);

		// k��simuse ja nuppude gruppi paigutamine
		VBox vBox = new VBox(10);
		vBox.setAlignment(Pos.CENTER);
		vBox.getChildren().addAll(label, pane);

		//stseeni loomine ja n��itamine
		Scene stseen2 = new Scene(vBox);
		
		stseen2.setOnKeyPressed(new EventHandler<KeyEvent>() {
		    public void handle(KeyEvent event) {
		        switch (event.getCode()) {
				case Q:
				case Y:
				case J:
				case ENTER:
					System.exit(0);
					return;
					
				case E:
				case N:
					peaLava.show();
					kusimus.hide();
					break;
					
					

				default:
					break;
				}
		    }
		});
		
		kusimus.setScene(stseen2);
		kusimus.show();
	}

	public void kas_abistan(){
		teade(Abi.fx_abitekst());
	}

	public void teade(String sonum){
		teade(sonum,false);
	}
	
	public void teade(String sonum, final boolean valjun){
		final Stage peaLava = global_peaLava;
		//if (true) System.exit(1);
		// luuakse teine lava
		final Stage teade = new Stage();
		// k��simuse ja kahe nupu loomine
		Label label = new Label(sonum);
		Button okButton = new Button("Jah");

		// s��ndmuse lisamine nupule Jah
		okButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				if (!valjun) teade.hide();
		        else System.exit(2);
			}
		});

		// nuppude grupeerimine
		FlowPane pane = new FlowPane(10, 10);
		pane.setAlignment(Pos.CENTER);
		pane.getChildren().addAll(okButton);

		// k��simuse ja nuppude gruppi paigutamine
		VBox vBox = new VBox(10);
		vBox.setAlignment(Pos.CENTER);
		vBox.getChildren().addAll(label, pane);

		//stseeni loomine ja n��itamine
		Scene stseen2 = new Scene(vBox);
		
		stseen2.setOnKeyPressed(new EventHandler<KeyEvent>() {
		    public void handle(KeyEvent event) {
		        if (!valjun) teade.hide();
		        else System.exit(2);
		    }
		});
		
		teade.setScene(stseen2);
		teade.show();
	}

	
	@Override
	public void start(final Stage peaLava) {
		try {
			global_peaLava = peaLava;
			final BorderPane root = new BorderPane();
			final double pikkus = 100;
			paiguta = Paiguta.riigid();
			char[][] tabel = paiguta.getMaatriks().getTabel();

			final int ridu = paiguta.getMaatriks().getRidu();
			final int veerge = paiguta.getMaatriks().getVeerge();
			tb = new TextBox[ridu][veerge];

			final double laius = pikkus*1.0*veerge/ridu;
			final Scene scene = new Scene(root,laius,pikkus*1.3);

			final FlowPane flow = new FlowPane();
			flow.setPadding(new Insets(5, 0, 5, 0));
			flow.setVgap(4);
			flow.setHgap(10);
			flow.setPrefWrapLength(laius); // preferred width allows for two columns
			flow.setTranslateY(pikkus);
			flow.setStyle("-fx-background-color: FFFFFF;");

			
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			peaLava.setScene(scene);
			//root.getChildren().add(karakter.anna_karakter('A'));
			// aknas��ndmuse lisamine
			peaLava.setOnHiding(new EventHandler<WindowEvent>() {
				public void handle(WindowEvent event) {
					kas_valjun(peaLava);
				}
			}); //siin l��peb aknas��ndmuse kirjeldus

			//Loe.tyhjenda();

			peaLava.setMinHeight(pikkus*1.3);
			peaLava.setMinWidth(laius);
			peaLava.setWidth(600);
			peaLava.setHeight((pikkus*1.3/laius)*600);
			
			//peaLava.minWidthProperty().bind(scene.heightProperty().multiply((veerge/ridu)/1.6));
			//peaLava.minHeightProperty().bind(scene.widthProperty().divide((veerge/ridu)/1.6));
			
			scene.widthProperty().addListener(new ChangeListener<Number>() {

				@Override
				public void changed(ObservableValue<? extends Number> arg0,
						Number arg1, Number arg2) {
					double max_pikkus = Screen.getPrimary().getBounds().getHeight();
					double uus_pikkus = scene.getWidth()/veerge*ridu;
					double safe_pikkus = Math.min(max_pikkus/1.3,uus_pikkus);
					//peaLava.setHeight(peaLava.getWidth()/veerge*ridu*1.6);
					for(int i = 0;i<tb.length;i++){
						for(int j = 0;j<tb[i].length;j++){
							TextBox ruut = tb[i][j];
							ruut.setSize(safe_pikkus/ridu);
						}
					}
					for (LahendBox lb : lbal) {
						lb.setSize(safe_pikkus*0.3);
					}
					//System.out.println(uus_pikkus + ";" + max_pikkus + ";" + safe_pikkus);
					//double laius = scene.getWidth()*veerge/ridu;
					flow.setPrefWrapLength(scene.getWidth()); // preferred width allows for two columns
					flow.setTranslateY(safe_pikkus);
					flow.setVgap(safe_pikkus/ridu/6);
					flow.setHgap(safe_pikkus/ridu/6);

				}

			});

			

			
			System.out.println("Genereerin ruudustiku. Peaekraani lahutus on " + Screen.getPrimary().getBounds().getWidth() + ";" + Screen.getPrimary().getBounds().getHeight());
			Group ruudustik = new Group();
			GridPane grid = new GridPane();
			
			for(int i = 0;i<tabel.length;i++){
				for(int j = 0;j<tabel[i].length;j++){
					final String karakter = Character.toString(tabel[i][j]);
					final int rida = i;
					final int veerg = j;
					final TextBox ruut = new TextBox(karakter,rida,veerg,laius/veerge,pikkus/ridu);
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
							}

						}

					}
							);

					scene.addEventHandler(MouseEvent.ANY, new EventHandler<MouseEvent>() {
						public void handle(MouseEvent me) {
							//System.out.println(me.getEventType());
							//System.out.println(me);
							get_ruudu_kohal(me.getX(),me.getY(),me);
						}
					});


					scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
					    public void handle(KeyEvent event) {
					    	if (event.isMetaDown()) {
					    		event.consume();
					    		return;
					    	}
					        if (event.isShiftDown()) {
					        	if (event.getCode() == KeyCode.ENTER) {
					        		secret_code(konsool.toString());
					        	}
					        	else {
					        		konsool.append(event.getText());
					        	}
					            event.consume();
					            return;
					        }
					        switch (event.getCode()) {
							case ENTER:
								break;
							case F2:
								break;
							case Q:
								peaLava.hide();
								event.consume();
								return;
							case F1:
							case H:
							case A:
								kas_abistan();
								event.consume();
								return;
								
							default:
								break;
							}
					        //System.out.println(event.toString() + " " + event.getEventType().toString().equals("KEY_RELEASED"));
							for(int i = 0;i<tb.length;i++){
								for(int j = 0;j<tb[i].length;j++){
									TextBox ruut = tb[i][j];
									ruut.invert();
								}
							}
					    }

						private void secret_code(String string) {
							switch (string) {
							case "DEBUG":
								Debug.setDebug(!Debug.isDebug());
								break;

							default:
								break;
							}
							System.out.println("konsool: " + konsool.toString());
							konsool.setLength(0);
						}
					});
					grid.add(ruut, j,i);
				}
			}
			
			
			Map<String, Lahend> lahendid = paiguta.getLahendid();
			StringBuilder sb = new StringBuilder();
			boolean on_veel = false;
			for(Map.Entry<String, Lahend> lahend : lahendid.entrySet()){
				LahendBox lb = new LahendBox(lahend.getValue(),pikkus);
				lbal.add(lb);
				flow.getChildren().add(lb);
			}		    

			//tekst.setTextAlignment(TextAlignment.JUSTIFY);
			grid.setLayoutX(0);
			grid.setLayoutY(0);

			ruudustik.getChildren().add(grid);
			ruudustik.getChildren().add(flow);






			ruudustik.setLayoutX(0);
			ruudustik.setLayoutY(0);
			root.getChildren().add(ruudustik);

			peaLava.show();
			kas_abistan();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
