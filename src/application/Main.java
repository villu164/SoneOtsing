package application;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Map;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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

public class Main extends Application {
	private Paiguta paiguta;
	private TextBox[][] tb;
	private StringBuilder konsool = new StringBuilder(10);
	private ArrayList<LahendBox> lbal = new ArrayList<LahendBox>();
	private boolean voit = false;

	public Color get_juhuslik_fill(){
		int R = (int)(Math.random()*256);
		int G = (int)(Math.random()*256);
		int B= (int)(Math.random()*256);
		return Color.rgb(R, G, B); //random color, but can be bright or dull
	}

	
	
	//otsitakse üles, millise ruudu kohal on kursor ( värvitakse punaseks )
	public void get_ruudu_kohal(double x, double y, MouseEvent me){
		boolean leitud = false;
		boolean kinnita = false;
		TextBox viimane_ruut = null;
		TextBox esimene_ruut = null;
		TextBox ruudu_kohal = null;
		ArrayList<TextBox> pakkumine = new ArrayList<TextBox>();
		for(int i = 0;i<tb.length;i++){
			for(int j = 0;j<tb[i].length;j++){
				TextBox ruut = tb[i][j];
				if (ruut.isHiir_alla()) esimene_ruut = ruut; // kui ruut on  märgitud alla vajutatuks, siis esimene ruut see on
				if (leitud) { //kui üks ruut on leitud, mille kohal olla, siis rohkem neid ei ole ega tule
					ruut.setRuudu_kohal(false);
					ruudu_kohal = ruut;
					continue;
				}
				leitud = ruut.getBoundsInParent().contains(x, y); //siin leiame üles, millise ruudu kohal me oleme
				if (leitud) viimane_ruut = ruut; //kui tegemist on ruud kohale olemisega, siis on tegemist samaaegselt ka viimase ruuduga
				switch (me.getEventType().toString()) {
				case "MOUSE_PRESSED":
					ruut.setHiir_alla(leitud); //kui hiirega tehakse klikk, siis märgitakse ruut alla vajutatuks
					ruut.setRuudu_kohal(leitud); //oleme ruudu kohal, kui leitud==true
					break;

				case "MOUSE_DRAGGED":
					ruut.setRuudu_kohal(leitud); //oleme ruudu kohal, kui leitud==true
					break;

				case "MOUSE_MOVED":
					ruut.setRuudu_kohal(leitud); //oleme ruudu kohal, kui leitud==true
					break;

				case "MOUSE_EXITED_TARGET":
					ruut.setRuudu_kohal(false); //kui läheme kastist välja, siis me kohe kindlasti pole ruudu kohal
					break;

				case "MOUSE_RELEASED":
					if (leitud) { //kui hiir lahti lastakse, siis märgitakse alla kinnita ja siit hakkab kõik pihta
						kinnita = true;
						if (esimene_ruut == viimane_ruut) {
							kinnita = false;
							ruut.setRuudu_kohal(false);
							esimene_ruut.setRuudu_kohal(false);
							esimene_ruut.setHiir_alla(false);
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
			return;
		}
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
			if (kinnita){
				boolean oige = paiguta.leidub(lahend);
				if (oige) {
					Color leitud_fill = get_juhuslik_fill();
					System.out.println("YES:" + lahend);
					Kirjuta.faili("Pakkusid sone: '" + lahend + "' ja sa ei eksi");
					
					for (TextBox ruut : pakkumine){
						ruut.setLeitud(true);
						ruut.setLeitudFill(leitud_fill);
					}
					if (paiguta.leiaSoned().isEmpty() && !voit){
						Kirjuta.teade("V6itsid m2ngu!!! :D");
						paiguta.kustuta_salvestus();
						voitsid();
					}
					for (LahendBox lb : lbal) {
						lb.checkLeitud();
					}
				}
				else{
					System.out.println("NO:" + lahend);
					Kirjuta.faili("Pakkusid sone: '" + lahend + "' aga kahjuks selliste ei leidu :(");
				}
				if (Debug.isDebug()) System.out.println(paiguta.leiaSoned());
			}
		}
		return;
	}

	
	//esimene ja viimane ruut määravad ära, milline on sirge ning kontrolli vaadatakse, kas asub sama joone peal
	private boolean kas_ruut_on_sirgel(TextBox esimene_ruut, TextBox viimane_ruut, TextBox kontrolli){
		if (esimene_ruut == null || viimane_ruut == null || esimene_ruut == viimane_ruut) return false;
		boolean tulemus = kas_ruudud_on_joonel(esimene_ruut,viimane_ruut) != 0 && kas_ruudud_on_joonel(esimene_ruut,viimane_ruut) == kas_ruudud_on_joonel(esimene_ruut,kontrolli) && kas_ruudud_on_joonel(kontrolli,viimane_ruut) == kas_ruudud_on_joonel(esimene_ruut,viimane_ruut);
		if (tulemus) {
			if (esimene_ruut != kontrolli && viimane_ruut != kontrolli) kontrolli.setFill(Color.GREEN);
		}
		return tulemus;
	}

	//millisesse ilmakaarde kuulub kahe ruudu suund
	private int kas_ruudud_on_joonel(TextBox esimene_ruut, TextBox viimane_ruut) {
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

	//näitame võidu teadet ning väljume programmist;
	public void voitsid(){
		teade("Voitsid",true);
		voit = true;
	}

	//copy-paste ühest praksi osast ja võimaldab näidata kastikest küsimusega kas väljun
	public void kas_valjun(final Stage peaLava){
		// luuakse teine lava
		final Stage kusimus = new Stage();
		// kysimuse ja kahe nupu loomine
		Label label = new Label("Kas tahad kinni panna?");
		Button okButton = new Button("Jah");
		Button cancelButton = new Button("Ei");

		// syndmuse lisamine nupule Jah
		okButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				kusimus.hide();
			}
		});

		// syndmuse lisamine nupule Ei
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

		// kysimuse ja nuppude gruppi paigutamine
		VBox vBox = new VBox(10);
		vBox.setAlignment(Pos.CENTER);
		vBox.getChildren().addAll(label, pane);

		// stseeni loomine ja n2itamine
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

	//saab n2idata teadet ja otsustada, kas peale seda teadet, see teade peidetakse voi programm valjub
	public void teade(String sonum, final boolean valjun){
		// luuakse teine lava
		final Stage teade = new Stage();
		// kysimuse ja kahe nupu loomine
		Label label = new Label(sonum);
		Button okButton = new Button("Jah");

		// syndmuse lisamine nupule Jah
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
			
			// aknasyndmuse lisamine
			peaLava.setOnHiding(new EventHandler<WindowEvent>() {
				public void handle(WindowEvent event) {
					kas_valjun(peaLava);
				}
			});

			peaLava.setMinHeight(pikkus*1.3);
			peaLava.setMinWidth(laius);
			peaLava.setWidth(600);
			peaLava.setHeight((pikkus*1.3/laius)*600);

			scene.widthProperty().addListener(new ChangeListener<Number>() {

				@Override
				public void changed(ObservableValue<? extends Number> arg0,
						Number arg1, Number arg2) {
					double max_pikkus = Screen.getPrimary().getBounds().getHeight();
					double uus_pikkus = scene.getWidth()/veerge*ridu;
					double safe_pikkus = Math.min(max_pikkus/1.3,uus_pikkus);
					for(int i = 0;i<tb.length;i++){
						for(int j = 0;j<tb[i].length;j++){
							TextBox ruut = tb[i][j];
							ruut.setSize(safe_pikkus/ridu);
						}
					}
					for (LahendBox lb : lbal) {
						lb.setSize(safe_pikkus*0.3);
					}
					flow.setPrefWrapLength(scene.getWidth());
					flow.setTranslateY(safe_pikkus);
					flow.setVgap(safe_pikkus/ridu/6);
					flow.setHgap(safe_pikkus/ridu/6);

				}

			});

			System.out.println("Genereerin ruudustiku. Peaekraani lahutus on " + Screen.getPrimary().getBounds().getWidth() + ";" + Screen.getPrimary().getBounds().getHeight()); //näite
			Group ruudustik = new Group();
			GridPane grid = new GridPane();

			for(int i = 0;i<tabel.length;i++){
				for(int j = 0;j<tabel[i].length;j++){
					final String karakter = Character.toString(tabel[i][j]);
					final int rida = i;
					final int veerg = j;
					final TextBox ruut = new TextBox(karakter,rida,veerg,laius/veerge,pikkus/ridu); //vastava suurusega tähekasti loomine
					tb[i][j] = ruut; //iga tähekast läheb nii textbox(tb) array-list-i
					grid.add(ruut, j,i); //kui ka grid(ruudustikule)-i
				}
			}

			scene.addEventHandler(MouseEvent.ANY, new EventHandler<MouseEvent>() {
				public void handle(MouseEvent me) {
					get_ruudu_kohal(me.getX(),me.getY(),me); //käivitab graafilise liidese südame -> siin tehakse kogu loogika, mis juhib sõna valikut
				}
			});

			scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
				public void handle(KeyEvent event) {
					if (event.isMetaDown()) { //vältimaks command+q bugi käivitumist OS X süsteemil, siis eemaldame selle ohu siin
						event.consume();
						return;
					}
					if (event.isShiftDown()) {
						if (event.getCode() == KeyCode.ENTER) {
							secret_code(konsool.toString()); //kui on tegemist shift+enter, siis käivita salakood
						}
						else {
							konsool.append(event.getText()); //kui on tegemist shift+sümbol, siis lisa see sümbol nimekirja
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
						peaLava.hide(); //Q tähte vajutades näita väljumisküsimust
						event.consume();
						return;
					case F1:
					case H:
					case A:
						kas_abistan(); //F1, H ja A tähti vajutades näita abistavat teadet
						event.consume();
						return;
					default:
						break;
					}
					//kui siiani pole keegi mujale suunanud, siis inverdime tausta, et kasutaja näeks, et midagi juhtus
					for(int i = 0;i<tb.length;i++){
						for(int j = 0;j<tb[i].length;j++){
							TextBox ruut = tb[i][j];
							ruut.invert(); 
						}
					}
				}

				//salajane meetod, mis kasutab shift-i all hoitud ning seejärel shift+enter vajutatud sõnu, et käivitada salajasi tausta protsesse.
				private void secret_code(String string) {
					switch (string) {
					case "DEBUG": //Debug sisestamisel käivitatakse debug mode, mille üks väljunditest on see, et konsooli väljastatakse kõikide lahendite koordinaadid
						Debug.setDebug(!Debug.isDebug());
						break;
					case "ERROR": //Debug sisestamisel käivitatakse debug mode, mille üks väljunditest on see, et konsooli väljastatakse kõikide lahendite koordinaadid
						System.out.println(1/0);
						break;

					default:
						break;
					}
					System.out.println("konsool: " + konsool.toString());
					konsool.setLength(0); //sea konsool string-builder pikkus 0-iks ehk lähtesta
				}
			});
			
			Map<String, Lahend> lahendid = paiguta.getLahendid(); //võtame hetkel kasutusel olevad lahendid
			for(Map.Entry<String, Lahend> lahend : lahendid.entrySet()){
				LahendBox lb = new LahendBox(lahend.getValue(),pikkus); //igale lahendile loome lahend_kasti
				lbal.add(lb); //need kastid lisame nii array_list-i
				flow.getChildren().add(lb); // kui ka flow_pane-ile
			}		    
			grid.setLayoutX(0);
			grid.setLayoutY(0);
			ruudustik.getChildren().add(grid); //lisa täherägastik ruudustik_gruppi
			ruudustik.getChildren().add(flow); //lisa lahend_sõnad ruudustik_gruppi
			ruudustik.setLayoutX(0);
			ruudustik.setLayoutY(0);
			root.getChildren().add(ruudustik); //lisa ruudustik_grupp juurele
			peaLava.show(); //tee pealava nähtavaks
			kas_abistan(); //näita lisaks abistavat ekraani esimese asjana
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
