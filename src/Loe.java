import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;


public class Loe {
	static String[] failist(String failinimi) throws FileNotFoundException{
		Scanner sc = new Scanner(new File(failinimi));
		ArrayList<String> sõned= new ArrayList<String>();
		while (sc.hasNext()) {
			String sõne = sc.next();
			sõned.add(sõne);
		}
		sc.close();
		String[] sõna = new String[sõned.size()];
		sõna = sõned.toArray(sõna);
		for(String s : sõned)
		    System.out.println(s);
		return sõna;
	}
	static String[] test(){
		String[] tagasi = {"hari","kamm","puur","konn"};
		return tagasi;
	}
	static String[] riigid(){
		String[] tagasi = {"Afganistan", "Albaania", "Alžeeria", "AmeerikaÜhendriigid", "Andorra", "Angola", "Antigua", "AraabiaÜhendemiraadid", "Argentina", "Armeenia", "Aserbaidžaan", "Austraalia", "Austria", "Bahama", "Bahrein", "Bangladesh", "Barbados", "Belau", "Belgia", "Belize", "Benin", "Bhutan", "Birma", "Boliivia", "BosniajaHertsegoviina", "Botswana", "Brasiilia", "Brunei", "Bulgaaria", "BurkinaFaso", "Burundi", "Colombia", "Costa", "Djibouti", "Dominica", "DominikaaniVabariik", "Ecuador", "Eesti", "Egiptus", "EkvatoriaalGuinea", "ElSalvador", "Elevandiluurannik", "Eritrea", "Etioopia", "Fidži", "Filipiinid", "Gabon", "Gambia", "Ghana", "Grenada", "Gruusia", "Guatemala", "Guinea", "GuineaBissau", "Guyana", "Haiti", "Hiina", "Hispaania", "Holland", "Honduras", "Horvaatia", "IdaTimor", "Iirimaa", "Iisrael", "India", "Indoneesia", "Iraak", "Iraan", "Island", "Itaalia", "Jaapan", "Jamaica", "Jeemen", "Jordaania", "Kambodža", "Kamerun", "Kanada", "Kasahstan", "Katar", "Kenya", "KeskAafrikaVabariik", "Kiribati", "Komoorid", "KongoDV", "KongoVabariik", "Kreeka", "Kuuba", "Kuveit", "Kõrgõzstan", "Küpros", "Laos", "Leedu", "Lesotho", "Libeeria", "Liechtenstein", "Liibanon", "Liibüa", "Luksemburg", "LõunaAafrikaVabariik", "LõunaKorea", "LõunaSudaan", "Läti", "Madagaskar", "Makedoonia", "Malaisia", "Malawi", "Maldiivid", "Mali", "Malta", "Maroko", "MarshalliSaared", "Mauritaania", "Mauritius", "Mehhiko", "Mikroneesia", "Moldova", "Monaco", "Mongoolia", "Montenegro", "Mosambiik", "Namiibia", "Nauru", "Nepal", "Nicaragua", "Nigeeria", "Niger", "Norra", "Omaan", "PaapuaUusGuinea", "Pakistan", "Panama", "Paraguay", "Peruu", "Poola", "Portugal", "Prantsusmaa", "PõhjaKorea", "Roheneemesaared", "Rootsi", "Rumeenia", "Rwanda", "Saalomoni", "SaintKittsJaNevis", "SaintLucia", "SaintVincentJaGrenadiinid", "Saksamaa", "Sambia", "Samoa", "SanMarino", "SoTomjaPrncipe", "SaudiAraabia", "Seišellid", "Senegal", "Serbia", "SierraLeone", "Singapur", "Slovakkia", "Sloveenia", "Somaalia", "Soome", "SriLanka", "Sudaan", "Suriname", "Suurbritannia", "Svaasimaa", "Süüria", "Šveits", "Zimbabwe", "Taani", "Tadžikistan", "Tai", "Tansaania", "Togo", "Tonga", "TrinidadjaTobago", "Tšaad", "Tšehhi", "Tšiili", "Tuneesia", "Tuvalu", "Türgi", "Türkmenistan", "Uganda", "Ukraina", "Ungari", "Uruguay", "Usbekistan", "UusMeremaa", "Valgevene", "Vanuatu", "Vatikan", "Venemaa", "Venezuela", "Vietnam"};
		return tagasi;
	}
	
	static boolean kontrolli(String sone){
		if (sone.toLowerCase().equals("välju") || sone.toLowerCase().equals("v2lju") ||
				sone.toLowerCase().equals("v2lja") || sone.toLowerCase().equals("välja")) {
			Abi.lahkumistekst();
			System.exit(0);
			return false;
		}
		if (sone.toLowerCase().equals("abi") || sone.toLowerCase().equals("appi") || sone.toLowerCase().equals("")) {
			Abi.tekst();
			return false;
		}
		return true;
	}
	static String kysiSone(){
		BufferedReader buffer=new BufferedReader(new InputStreamReader(System.in));
		try {
			String sone = buffer.readLine().trim();
			if (kontrolli(sone)){
				return sone;
			}
			else {
				return kysiSone();
			}
		} catch (IOException e) {
			System.out.println("Ei sobi! Proovi uuesti.");
			return kysiSone();
		}
	}
	static String kysiSone(String s){
		System.out.print(s);
		return kysiSone();
	}
	static int kysiNumber(){
		String sone = kysiSone();
		try {
			return Integer.parseInt(sone);
		} catch (Exception e) {
			System.out.println("Ei sobi! Proovi uuesti.");
			return kysiNumber();
		}
	}
	static int kysiNumber(String s){
		System.out.print(s);
		return kysiNumber();
	}
	static double kysiArv(){
		String sone = kysiSone();
		try {
			return Double.parseDouble(sone);
		} catch (Exception e) {
			System.out.println("Ei sobi! Proovi uuesti.");
			return kysiArv();
		}
	}
	static double kysiArv(String s){
		System.out.print(s);
		return kysiArv();
	}
	static Lahend kysiLahend(){
		String sone = kysiSone();
		try {
			return Lahend.parseLahend(sone);
		} catch (Exception e) {
			System.out.println("Ei sobi! Proovi uuesti.");
			return kysiLahend();
		}
	}
	static Lahend kysiLahend(String s){
		System.out.print(s);
		return kysiLahend();
	}
	static boolean kysiJahEi(){
		String sone = kysiSone();
		try {
			if (sone.toLowerCase().equals("e") || sone.toLowerCase().equals("ei")) return false;
			if (sone.toLowerCase().equals("j") || sone.toLowerCase().equals("jah")) return true;
			System.out.println("Ei sobi! Proovi uuesti.");
			return kysiJahEi();
		} catch (Exception e) {
			System.out.println("Ei sobi! Proovi uuesti.");
			return kysiJahEi();
		}
	}
	static boolean kysiJahEi(String s){
		System.out.print(s);
		return kysiJahEi();
	}
	static void tyhjenda(){
		int lines = 100;
		for (int i = 0;i<lines;i++) System.out.println();
	}
}
