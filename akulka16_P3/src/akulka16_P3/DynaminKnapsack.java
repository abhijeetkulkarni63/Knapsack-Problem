package akulka16_P3;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

public class DynaminKnapsack {

	public static int[][] B;
	public static int n;
	public static int W;
	public static ArrayList<Integer> profits;
	public static ArrayList<Integer> weights;

	public static void startDynamicKnapsack(String pathName) {
		File file = new File("output.txt");
		try {
			BufferedReader bufferedReader = new BufferedReader(new FileReader(pathName));
			BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file.getAbsolutePath()));
			String problemSize = "";
			while ((problemSize = bufferedReader.readLine()) != null) {
				String[] size = problemSize.split("\\s+");
				n = Integer.parseInt(size[0]);
				W = Integer.parseInt(size[1]);
				B = new int[n + 1][W + 1];
				profits = new ArrayList<Integer>();
				weights = new ArrayList<Integer>();
				profits.add(0);
				weights.add(0);
				for (int i = 0; i < Integer.parseInt(size[0]); i++) {
					String[] itemInfo = bufferedReader.readLine().split("\\s+");
					weights.add(Integer.parseInt(itemInfo[0]));
					profits.add(Integer.parseInt(itemInfo[1]));
				}
				long startTime = System.currentTimeMillis();
				solveKnapsack();
				long endTime = System.currentTimeMillis();
				int i=n-1;
				int w_temp = W;
				ArrayList<Integer> knapsackItems = new ArrayList<Integer>();
				while(i > 0 && w_temp > 0){
					if(B[i][w_temp] != B[i-1][w_temp]){
						knapsackItems.add(i);
						w_temp = w_temp - weights.get(i);
						i = i - 1;
					} else {
						i = i - 1;
					}
				}
				if (!file.exists()) {
					file.createNewFile();
				}
				bufferedWriter.write(n + " " + B[n][W] + " " + (endTime - startTime)/1000);
				bufferedWriter.newLine();
				for (Integer item : knapsackItems) {
					bufferedWriter.write(weights.get(item) + " " + profits.get(item));
					bufferedWriter.newLine();
				}
			}
			bufferedReader.close();
			bufferedWriter.close();

		} catch (Exception expception) {
			expception.printStackTrace();
		}
	}

	private static void solveKnapsack() {
		for (int i = 0; i <= W; i++) {
			B[0][i] = 0;
		}
		for (int k = 1; k <= n; k++) {
			B[k][0] = 0;
			for (int w = 1; w <= W; w++) {
				int weightDiff = w - weights.get(k);
				if ((weights.get(k) <= w) && ((B[k - 1][weightDiff] + profits.get(k)) > B[k - 1][w])) {
					B[k][w] = B[k - 1][weightDiff] + profits.get(k);
				} else {
					B[k][w] = B[k - 1][w];
				}
			}
		}
	}

	public static void main(String[] args) {
		String pathName = "input.txt";
		startDynamicKnapsack(pathName);
	}
}