import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
public class Rooks {
	static char rook = 'r';
	static int n;
	static char[][] board;
	static int[][] hor;
	static int[][] vert;
	static boolean[][] blocked;
	static List<List<Integer>> g;
	static int[] mt;
	static boolean[] used;
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new FileReader("RooksTest.txt"));
		n = Integer.parseInt(br.readLine());
		board = new char[n][n];
		hor = new int[n][n];
		vert = new int[n][n];
		blocked = new boolean[n][n];
		g = new ArrayList<List<Integer>>();
		mt = new int[n*n];
		used = new boolean[n*n];

		String row;
		char cell;
		for (int i = 0; i < n; i++) {
			row = br.readLine();
			for (int j = 0; j < n; j++) {
				board[i][j] = row.charAt(j);
				blocked[i][j] = row.charAt(j) == '#' ? true: false;
			}
		}

		int hC = matchHor();
		int vC = matchVert();
		createGraph(hC);
		int ans = get_ans(hC,vC);
		printMatrix(g);
		System.out.println();
		System.out.println(ans);



		
	}

	static boolean dfs(int v) {
		// for (int i: mt) {
		// 	System.out.print(i + " ");
		// }
		// System.out.println();
		// for (boolean i: used) {
		// 	System.out.print(i + " ");
		// }
		// System.out.println();
		used[v] = true;
		for (Integer w: g.get(v)) {
			if (mt[w] == -1 || (!used[mt[w]] && dfs(mt[w]))) {
				mt[w] = v;
				return true;
			}
		}
		return false;
	}

	static int get_ans(int hC, int vC) {
		Arrays.fill(mt,0, vC, -1);
		Arrays.fill(used,0, hC, false);

		int ans = 0;
		for (int i = 0; i < hC; i++) {
			if (dfs(i)) {
				ans += 1;
				Arrays.fill(used,0, hC, false);
			}
		}
		return ans;
	}

	static void createGraph(int c) {
		for (int i = 0; i < c; i++)
			g.add(new ArrayList<Integer>());
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (blocked[i][j])
					continue;
				
				g.get(hor[i][j]).add(vert[i][j]);
			}
		}
	}



	static int matchHor() {
		int ptr = -1;
		for (int i = 0; i < n; i++)
		{
			for (int j = 0; j < n; j++)
			{
				if (blocked[i][j])
					continue;
				if (j == 0 || blocked[i][j - 1])
					ptr++;
				hor[i][j] = ptr;
			}
		}
		return ptr + 1;
	}

	static int matchVert() {
		int ptr = -1;
		for (int j = 0; j < n; j++)
		{
			for (int i = 0; i < n; i++)
			{
				if (blocked[i][j])
					continue;
				if (i == 0 || blocked[i - 1][j])
					ptr++;
				vert[i][j] = ptr;
			}
		}
		return ptr + 1;
	}


   static int binomialCoeff(int n, int k)  
    { 
      
        // Base Cases 
        if (k == 0 || k == n) 
            return 1; 
          
        // Recur 
        return binomialCoeff(n - 1, k - 1) +  
                    binomialCoeff(n - 1, k); 
    }

	static void printMatrix(List<List<Integer>> m) {
		for (int i = 0; i < m.size(); i++) {
			System.out.print(i + "  ");
			for (int j = 0; j < m.get(i).size(); j++) 
				System.out.print(m.get(i).get(j) + " ");
			System.out.println();
		}
		System.out.println();
	}
	
	static void printMatrix(char[][] m) {
		for (int i = 0; i < m.length; i++) {
			for (int j = 0; j < m.length; j++) 
				System.out.print(m[i][j] + " ");
			System.out.println();
		}
		System.out.println();
	}
	static void printMatrix(int[][] m) {
		for (int i = 0; i < m.length; i++) {
			for (int j = 0; j < m.length; j++) 
				System.out.print(m[i][j] + " ");
			System.out.println();
		}
		System.out.println();
	}
	static void printMatrix(boolean[][] m) {
		for (int i = 0; i < m.length; i++) {
			for (int j = 0; j < m.length; j++) 
				System.out.print(m[i][j] + " ");
			System.out.println();
		}
		System.out.println();
	}




}