package algorithm;

import java.awt.Image;
import java.awt.image.BufferedImage;

import util.Vector3;

public class Preprocessing {

	public static final int DIMENSION = 128;

	public static final int M = 6;

	public static DecompositionResult decomposeImage(Image img) {
		Image i;
		synchronized (img) {
			i = img
					.getScaledInstance(DIMENSION, DIMENSION,
										Image.SCALE_DEFAULT);
		}
		BufferedImage copy = new BufferedImage(DIMENSION, DIMENSION,
												BufferedImage.TYPE_INT_RGB);
		copy.getGraphics().drawImage(i, 0, 0, null);

		Vector3[][] imgData = new Vector3[DIMENSION][DIMENSION];
		for (int x = 0; x < DIMENSION; x++) {
			for (int y = 0; y < DIMENSION; y++) {
				int color = copy.getRGB(x, y);
				float b = (color & 0xFF) / 255.0f;
				float g = ((color >> 8) & 0xFF) / 255.0f;
				float r = ((color >> 16) & 0xFF) / 255.0f;
				// System.out.println("RGB: " + r + ", " + g + ", " + b);
				imgData[x][y] = new Vector3(r, g, b);
			}
		}

		decomposeImage(imgData);

		return new DecompositionResult(imgData, M);
	}

	public static void decomposeArray(Vector3[] a) {

		int h = a.length;

		float sqrtH = (float) Math.sqrt(h);

		// A <- A / sqrt(h)
		for (int i = 0; i < h; i++) {
			a[i] = a[i].div(sqrtH);
		}

		float sqrtTwo = (float) Math.sqrt(2.0);

		while (h > 1) {
			h = h / 2;

			Vector3[] aPrime = new Vector3[a.length];
			System.arraycopy(a, 0, aPrime, 0, a.length);

			for (int i = 0; i < h; i++) {
				aPrime[i] = a[2 * i].add(a[2 * i + 1]).div(sqrtTwo);
				aPrime[i + 1] = a[2 * i].sub(a[2 * i + 1]).div(sqrtTwo);
			}

			System.arraycopy(aPrime, 0, a, 0, a.length);
		}
	}

	public static void decomposeImage(Vector3[][] t) {
		for (int row = 0; row < t.length; row++) {
			decomposeArray(t[row]);
		}

		Vector3[] colT = new Vector3[t.length];
		for (int col = 0; col < t.length; col++) {
			for (int row = 0; row < t.length; row++) {
				colT[row] = t[row][col];
			}

			decomposeArray(colT);

			for (int row = 0; row < t.length; row++) {
				t[row][col] = colT[row];
			}
		}
	}
}
