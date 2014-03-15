
public class Debug {
	private static boolean debug = false;
	public static void setDebug(boolean debug) {
		Debug.debug = debug;
	}
	public static boolean isDebug() {
		return debug;
	}
	static void out(Object o){
		if (debug) System.out.println(o);
	}
}
