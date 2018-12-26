import java.io.*; 
import java.util.*; 
import java.lang.Math.*;

public class CollectingCoinsII {
	static int[][] grid;
	static int[][] dp;
	static int max;
	static int n,m,s,a,b,c,d;
	static int[][] paths;
	static int[][] taylor;

	public static void main(String[] args) throws Exception {
		//BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedReader br = new BufferedReader(new FileReader("CollectingCoinsIITest.txt"));
		StringTokenizer st = new StringTokenizer(br.readLine());
		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());
		grid = new int[n][m];
		st = new StringTokenizer(br.readLine());
		s = Integer.parseInt(st.nextToken());
		a = Integer.parseInt(st.nextToken());
		b = Integer.parseInt(st.nextToken());
		c = Integer.parseInt(st.nextToken());
		d = Integer.parseInt(st.nextToken());

		// int prev = 0;
		// for (int i = 1; i < n; i++) {
		// 	System.out.println(prev + " " + i);
		// 	prev = i;
		// }

		// generateGrid2();
		generateGrid();
		countCoins();
		// printMatrix(grid);
		
	}


	static void countCoins() {
		dp = new int[n][m];
		dp[0][0] = grid[0][0];
		paths = new int[n][m];
		paths[0][0] = 1;

		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				if (grid[i][j] == 0)
					continue;
				System.out.println(i + " " + j);
				int c1 = (i <= 0 ? 0: dp[i-1][j]);
				int c2 = (j <= 0 ? 0: dp[i][j-1]);
				
				int ans =  Math.max(c1,c2);
				if (ans == 0)
					continue;
				dp[i][j] = grid[i][j] + ans;
				int p1 = ( i > 0 && (dp[i-1][j] + grid[i][j] == dp[i][j]) ? paths[i-1][j] : 0) % 10000;
				int p2 = (j > 0 && (dp[i][j-1] + grid[i][j] == dp[i][j]) ? paths[i][j-1]: 0)% 10000;
				paths[i][j] += (p1 + p2) % 10000;
				
			}
			System.out.println();
		}
		
		System.out.println(dp[n-1][m-1] + " " + paths[n-1][m-1]);
		printMatrix(dp);
		printMatrix(paths);
		
	}

	


	static void printMatrix(int[][] matrix) {
		System.out.println();
		taylor = new int[2][m];
		int curLayer = 0;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++)
				System.out.print(matrix[i][j] + " ");
			System.out.println();
		}
		System.out.println();
	}

	static void countCoins2(int[][] parts) {
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < m; j++) {
				System.out.print(parts[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println("opa");
	}

	static void generateGrid2() {
		int[][] parts = new int[2][m];
		int[][] shrimp = new int[2][m];
		int[][] scampi = new int[2][m];
		int curLayer = 0;
		int prevLayer;
		int last = s;
		for (int j = 0; j < m; j++) {
			parts[curLayer][j] = last;
			last = (((last*a)^b)+c)%d;
		}
		shrimp[0][0] = parts[0][0];
		scampi[0][0] = 1;
		for (int i = 1; i < n; i++) {
			prevLayer = curLayer;
			curLayer ^= 1;
			for (int j = 0; j < m; j++) {
				parts[curLayer][j] = last;
				last = (((last*a)^b)+c)%d;
			}
			
			for (int j = 0; j < m; j++) {
				System.out.print(parts[prevLayer][j] + " ");
			}
			System.out.println();
			for (int j = 0; j < m; j++) {
				System.out.print(parts[curLayer][j] + " ");
			}
			System.out.println();
			
			System.out.println("opa");
		}
		

	}


	static void generateGrid() {
		int last = s;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				grid[i][j] = last;
				last = (((last*a)^b)+c)%d;
			}
		}

	}
}