
public class Debug {
	private static boolean debug = true;
	public static boolean isDebug() {
		return debug;
	}
	static void out(Object o){
		if (debug) System.out.println(o);
	}
}
