import java.io.*;
import java.util.*;

public class CollectingCoins {
	static int[][] grid;
	static int n;
	static int MAX_X = 100;
	static int MAX_Y = 100;
	public static void main(String[] args) throws Exception {
		// BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedReader br = new BufferedReader(new FileReader("CollectingCoinsTest.txt"));

		n = Integer.parseInt(br.readLine());
		grid = new int[100][100];

		StringTokenizer st;

		for (int i = 0; i < n; i++) {
			st = new StringTokenizer(br.readLine());
			int x = Integer.parseInt(st.nextToken());
			int y = Integer.parseInt(st.nextToken());
			grid[x][y] = 1;
		}

		

		int found = 0;
		int trips = 0;
		int[] d;
		int i,j;
		while (found < n) {
			trips += 1;
			i = 0;
			j = 0;
			while (found < n && i < MAX_Y) {
				d = lastRightIndex(i);
				j = d[0];
				found += d[1];
				i += 1;
			}
		}
		System.out.println(found);
	}

	static int[] lastRightIndex(int y) {
		int lastRightI = MAX_X-1;
		int count = 0;
		for (int i = 0; i < MAX_X; i++)
			if (grid[y][i] == 1) {
				count += 1;
				grid[y][i] = 0;
				lastRightI = i;
			}
		int[] d = new int[2];
		d[0] = lastRightI;
		d[1] = count;
		return d;

	}


}