package application;
	
import java.awt.Point;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = new BorderPane();
			Scene scene = new Scene(root,400,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			
			
			Loe.tyhjenda();
			Abi.tervitustekst();
			boolean uuesti = true;
			Paiguta p = null;		
			String[] soned = Loe.riigid();
			boolean kysimus_kohanda = Loe.kysiJahEi("Kas soovid täpsustada ridu ja veerge(vaikimisi 10x10)? Jah või Ei(vaikimisi): ",false);
			if (kysimus_kohanda) p = new Paiguta(Loe.kysiNumberSuuremKuiNull("Sisesta ridu:"),Loe.kysiNumberSuuremKuiNull("Sisesta veerge:"));
			else p = new Paiguta(10,10);
			Maatriks m = p.getMaatriks();
			for (int s = 0;s < soned.length;s++){
				for(int i = 0;i < m.getRidu();i++){
					for(int j=0;j< m.getVeerge();j++){
						Point punkt = new Point(j,i);
						for(int k = 0;k<Kompass.suunad.length;k++){
							Kompass suund = Kompass.valueOf(Kompass.suunad[k]);
							Lahend lahend = new Lahend(soned[s].toUpperCase(), punkt, suund);
							boolean staatus = p.pane(lahend);
							if (staatus) {
								i=m.getRidu();
								j=m.getVeerge();
								k=8;
							}
						}
					}
				}
			}
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
