package algorithm;

import java.awt.Image;
import java.util.List;

import util.Coefficient;

public class Matching {

	private static final float W_R = 3.0f;
	private static final float W_G = 3.0f;
	private static final float W_B = 3.0f;

	public static Image match(Image source, List<Image> database) {
		int count = database.size();

		DecompositionResult src = Preprocessing.decomposeImage(source);

		double[] scores = new double[count];

		DecompositionResult[] results = new DecompositionResult[count];
		for (int i = 0; i < count; i++) {
			results[i] = Preprocessing.decomposeImage(database.get(i));
		}

		for (int i = 0; i < count; i++) {
			DecompositionResult result = results[i];
			// Red
			scores[i] += W_R * Math.abs(src.avgColorA - result.avgColorA);
			for (int j = 0; j < Preprocessing.M; j++) {
				Coefficient c = result.getA(j);
				if (c.value > 0) {
					scores[i] -= W_R
									* Math.min(Math.max(c.x, c.y),
												Preprocessing.M);
				}
			}

			// Green
			scores[i] += W_G * Math.abs(src.avgColorB - result.avgColorB);

			// Blue
			scores[i] += W_B * Math.abs(src.avgColorC - result.avgColorC);
		}

		int index = 0;
		double score = scores[0];

		for (int i = 1; i < count; i++) {
			if (scores[i] < score) {
				index = i;
			}
		}

		return database.get(index);
	}
}
