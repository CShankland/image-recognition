package algorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import util.Coefficient;
import util.Vector3;

public class DecompositionResult {
	public final float avgColorA;
	public final float avgColorB;
	public final float avgColorC;

	private Coefficient[] topColorA;
	private Coefficient[] topColorB;
	private Coefficient[] topColorC;

	public DecompositionResult(Vector3[][] computedData, int m) {
		avgColorA = computedData[0][0].x;
		avgColorB = computedData[0][0].y;
		avgColorC = computedData[0][0].z;

		topColorA = new Coefficient[m];
		topColorB = new Coefficient[m];
		topColorC = new Coefficient[m];

		ArrayList<Coefficient> dataA = new ArrayList<Coefficient>(
																	computedData.length
																			* computedData.length);
		ArrayList<Coefficient> dataB = new ArrayList<Coefficient>(
																	computedData.length
																			* computedData.length);
		ArrayList<Coefficient> dataC = new ArrayList<Coefficient>(
																	computedData.length
																			* computedData.length);

		for (int i = 0; i < computedData.length; i++) {
			for (int j = 0; j < computedData[i].length; j++) {
				if (i == 0 && j == 0) {
					continue;
				}
				dataA.add(new Coefficient(computedData[i][j].x, i, j));
				dataB.add(new Coefficient(computedData[i][j].y, i, j));
				dataC.add(new Coefficient(computedData[i][j].z, i, j));
			}
		}

		Comparator<Coefficient> c = new Comparator<Coefficient>() {

			@Override
			public int compare(Coefficient o1, Coefficient o2) {
				return Float.compare(Math.abs(o2.value), Math.abs(o1.value));
			}

		};

		Collections.sort(dataA, c);
		Collections.sort(dataB, c);
		Collections.sort(dataC, c);

		for (int i = 0; i < m; i++) {
			topColorA[i] = dataA.get(i);
			topColorB[i] = dataB.get(i);
			topColorC[i] = dataC.get(i);
		}
	}

	public Coefficient getA(int index) {
		if (index >= topColorA.length || index < 0) {
			return null;
		}

		return topColorA[index];
	}

	public Coefficient getB(int index) {
		if (index >= topColorB.length || index < 0) {
			return null;
		}

		return topColorB[index];
	}

	public Coefficient getC(int index) {
		if (index >= topColorC.length || index < 0) {
			return null;
		}

		return topColorC[index];
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("***** DecompositionResult *****\n");
		sb.append("Average Color: " + avgColorA + ", " + avgColorB + ", "
					+ avgColorC + "\n");
		sb.append("Top Color A: \n");
		for (Coefficient c : topColorA) {
			sb.append(c.toString() + "\n");
		}
		sb.append("\nTop Color B: \n");
		for (Coefficient c : topColorB) {
			sb.append(c.toString() + "\n");
		}
		sb.append("\nTop Color C: \n");
		for (Coefficient c : topColorC) {
			sb.append(c.toString() + "\n");
		}

		return sb.toString();
	}
}
