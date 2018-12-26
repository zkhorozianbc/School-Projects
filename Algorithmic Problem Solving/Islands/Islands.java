import java.io.*;
import java.util.*;
import java.lang.Math.*;
public class Islands {
	public static int l = 4;
	public static int n;
	public static int m;
	public static int count = 0;
	public static int rowNbr[] = new int[] {-1, -1, -1,  0, 0,  1, 1, 1}; 
    public static int colNbr[] = new int[] {-1,  0,  1, -1, 1, -1, 0, 1}; 
	public static void main(String[] args) throws Exception{
		// BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedReader br = new BufferedReader(new FileReader("IslandsTest.txt"));
		StringTokenizer st = new StringTokenizer(br.readLine());

		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());
		
		int q = Integer.parseInt(st.nextToken());
		int[][] australia = new int[n][m];
		for (int i = 0; i < n; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < m; j++) {
				australia[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		printMatrix(australia);
		int[][] armenia;
		for (int epoch = 0; epoch < q; epoch++) {
			int height = Integer.parseInt(br.readLine());
			armenia = flood(australia,height);
			dfsUtil(armenia);
			System.out.println(count);
			count = 0;
		}
	}


	public static void dfsUtil(int[][] a) {
		boolean[][] visited = new boolean[n][m];
		System.out.println("haaaa");
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				if (a[i][j] == 1 && !visited[i][j])
					System.out.println(dfs(a,i,j,visited, 1));
				System.out.println("ha");
			}

		}
		
	}

	public static int dfs(int[][] a, int i, int j, boolean[][] visited, int num_step) {
		System.out.println(num_step);
		visited[i][j] = true;
		num_step += 1;
		for (int k = 0; k < 8; ++k) 
            if (isSafe(a, i + rowNbr[k], j + colNbr[k], visited)) 
                dfs(a, i + rowNbr[k], j + colNbr[k], visited, num_step);
        return num_step;
        


	}


	public static boolean isSafe(int[][] a, int i, int j, boolean[][] visited) {
		return (i >= 0) && (i < n) && 
               (j >= 0) && (j < m) && 
               (a[i][j]==1 && !visited[i][j]); 
	}




	public static int[][] flood(int[][] a, int height) {

		int[][] b = new int[n][m];

		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				if (a[i][j] > height){
					b[i][j] = 1;
				} else {
					b[i][j] = 0;
				}
			}
		}
		return b;
	}

	public static void printMatrix(int[][] a) {

		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				System.out.print(a[i][j] + " ");
			}
			System.out.println();
		}
	}
}