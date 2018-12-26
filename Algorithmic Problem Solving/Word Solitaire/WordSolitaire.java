import java.io.*;
import java.util.*;

public class WordSolitaire {
	static List<List<Integer>> g;
	static int n;
	static int[] parents;
	static List<String> words;
	static boolean found;
	public static void main(String[] args) throws Exception {
		//BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedReader br = new BufferedReader(new FileReader("WordSolitaireTest.txt"));
		StringTokenizer st = new StringTokenizer(br.readLine());
		n = Integer.parseInt(st.nextToken());
		parents = new int[n];
		found = false;
		g = new ArrayList<List<Integer>>();
		words = new ArrayList<String>();
		for (int i = 0; i < n; i++) {
			words.add(br.readLine().trim());
			g.add(new ArrayList<Integer>());
		}

		String w;
		int start,end;
		for (int i = 0; i < words.size();i++) {
			w = words.get(i);
			end = w.charAt(w.length()-1);
			for (int j = 0; j < words.size(); j++) {
				if (j == i)
					continue;
				if (words.get(j).charAt(0) == end)
					g.get(i).add(j);
			}

		}

		// for (int i = 0; i < g.size(); i++) {
		// 	System.out.println(i + " " + g.get(i).toString());
		// }
		dfs();

	}

	static void backtrack(int sink) {
		int trav = sink;
		Stack<String> path = new Stack<String>();
		for (int i = 0; i < n; i++) {
			path.push(words.get(trav));
			trav = parents[trav];
		}
		StringBuilder out = new StringBuilder();
		while (!path.isEmpty()) {
			out.append(path.pop() + "\n"); 
		}
		out.setLength(out.length()-1);
		System.out.println(out.toString());
 	}

	static void dfsUtil(int u, Set<Integer> visited, int depth) {
		if (depth == n) {
			found = true;
			System.out.println();
			// backtrack(u);
			return;
		}
		System.out.print(words.get(u) + " ");
		visited.add(u);
		depth += 1;

		for (Integer v: g.get(u)) {
			if (!visited.contains(v)) {
				parents[v] = u;
				dfsUtil(v,visited,depth);
			}
		}


	}



	static void dfs() {
		Set<Integer> visited = new HashSet<Integer>();
		for (int i = 0; i < n; i++) {
			// if (found)
			// 	return;
			if (!visited.contains(i)) {
				dfsUtil(i,visited,1);
				visited.clear();
				
			}
		}
		if (!found)
			System.out.println("impossible");
	}

}