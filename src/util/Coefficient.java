package util;

public class Coefficient {
	public final float value;
	public final int x;
	public final int y;

	public Coefficient(float v, int x, int y) {
		value = v;
		this.x = x;
		this.y = y;
	}

	public String toString() {
		return x + ", " + y + "\t" + value;
	}
}
