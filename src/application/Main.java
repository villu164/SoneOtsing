package application;

import java.awt.Point;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;


public class Main extends Application {
	private Group ruut;
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
				if (leitud) { //kui esimene ruut on leitud, siis k��ik j��rgmised on mitte, sest ilma selle tingimuseta on v��imalik, et korraga on valitud 4 ruutu
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

				case "MOUSE_RELEASED":
					if (leitud) kinnita = true;
					ruut.setHiir_yles(leitud);
					ruut.setRuudu_kohal(leitud);
					break;

				default:
					break;
				}
			}
		}
		if (esimene_ruut != null && me.getEventType().toString() == "MOUSE_RELEASED" && ruudu_kohal == null) {
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
						ruut.setHiir_yles(false);
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
			if (kinnita){
				boolean oige = paiguta.leidub(lahend);
				if (oige) {
					System.out.println("YES:" + lahend);
					for (TextBox ruut : pakkumine){
						ruut.setLeitud(true);
					}
				}
				else{
					System.out.println("NO:" + lahend);
				}
				System.out.println(paiguta.leiaSoned());
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


	@Override
	public void start(final Stage peaLava) {
		try {

			final BorderPane root = new BorderPane();
			double laius = 600;
			final Scene scene = new Scene(root,laius,laius);

			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			peaLava.setScene(scene);
			//root.getChildren().add(karakter.anna_karakter('A'));
			// aknas��ndmuse lisamine
			peaLava.setOnHiding(new EventHandler<WindowEvent>() {
				public void handle(WindowEvent event) {
					if (true) System.exit(1);
					// luuakse teine lava
					final Stage kusimus = new Stage();
					// k��simuse ja kahe nupu loomine
					Label label = new Label("Kas t��esti tahad kinni panna?");
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
					kusimus.setScene(stseen2);
					kusimus.show();
				}
			}); //siin l��peb aknas��ndmuse kirjeldus

			//Loe.tyhjenda();
			paiguta = Paiguta.riigid();

			scene.widthProperty().addListener(new ChangeListener<Number>() {

				@Override
				public void changed(ObservableValue<? extends Number> arg0,
						Number arg1, Number arg2) {
					// TODO Auto-generated method stub
					if (ruut != null && root.getChildren().contains(ruut)) {
						//root.getChildren().remove(ruut);
						//Ruudustik ruudustik = new Ruudustik(paiguta);
						//ruut = ruudustik.anna_ruudustik(scene.getWidth());
						//root.getChildren().add(ruut);
						peaLava.setHeight(scene.getWidth());
						peaLava.setWidth(scene.getWidth());
					}
				}

			});



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



					grid.add(ruut, j,i);
				}
			}

			//tekst.setTextAlignment(TextAlignment.JUSTIFY);
			grid.setLayoutX(0);
			grid.setLayoutY(0);

			ruudustik.getChildren().add(grid);







			ruudustik.setLayoutX(0);
			ruudustik.setLayoutY(0);
			root.getChildren().add(ruudustik);

			peaLava.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
