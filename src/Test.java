public class Test {
	public static void main(String[] args){
		System.out.println("Tere, saan muuta");
		System.out.println(new Maatriks(3,5));
		for(int i = 0;i<8;i++){
			Suund s = new Suund(i);
			System.out.println(s.toString(true));
		}
	}
}
