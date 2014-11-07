package util;

public class Vector3 {

	public final float x, y, z;

	public Vector3(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public Vector3 div(float scale) {
		return new Vector3(x / scale, y / scale, z / scale);
	}

	public Vector3 mul(float scale) {
		return new Vector3(x * scale, y * scale, z * scale);
	}

	public Vector3 add(Vector3 b) {
		return new Vector3(x + b.x, y + b.y, z + b.z);
	}

	public Vector3 sub(Vector3 b) {
		return new Vector3(x - b.x, y - b.y, z - b.z);
	}
}
