import java.io.*;
import java.util.*;

public class Friends4 {
	public static int[] ages;
	public static int[] down;
	public static int[] up;
	public static Map<Integer,String> opa;
	public static Map<String,Integer> sosa;
	public static int[] f;
	public static void main(String[] args) throws Exception{
		// BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedReader br = new BufferedReader(new FileReader("Friends4Test.txt"));
		
		int n = Integer.parseInt(br.readLine());

		f = new int[n+1];
		opa = new HashMap<Integer,String>();
		sosa = new HashMap<String,Integer>();
		ages = new int[n+1];
		down = new int[n+1];
		up = new int[n+1];
		StringTokenizer st;
		String oblah;
		int clout;
		for (int i = 1; i <= n; i++) {
			st = new StringTokenizer(br.readLine());
			oblah = st.nextToken();
			clout = Integer.parseInt(st.nextToken());
			f[i] = i;
			ages[i] = clout;
			opa.put(i,oblah);
			sosa.put(oblah,i);
			down[i] = i;
			up[i] = i;
			
		}

		int num_queries = Integer.parseInt(br.readLine());
		String p1;
		String p2;
		int parent;
		StringBuilder out = new StringBuilder();

		for (int i = 0; i < num_queries; i++) {
			st = new StringTokenizer(br.readLine());
			p1 = st.nextToken();
			p2 = st.nextToken();
			parent = union(sosa.get(p1),sosa.get(p2));
			out.append(opa.get(down[parent]) + " " + opa.get(up[parent]) + "\n");
		}

		out.setLength(out.length()-1);
		System.out.println(out.toString());
	}

	public static int find(int x) {
		if (f[x] == x)
			return x;
		return f[x] = find(f[x]);
	}

	public static int union(int x, int y) {
		int fx = find(x);
		int fy = find(y);

		if (ages[up[fx]] >= ages[up[fy]]) {
			if (ages[up[fx]] == ages[up[fy]]) {
				if (opa.get(up[fx]).compareTo(opa.get(up[fy])) < 0)
					up[fy] = up[fx];
			} else {
				up[fy] = up[fx];
			}
		}
		if (ages[down[fx]] <= ages[down[fy]]) {
			if (ages[down[fx]] == ages[down[fy]]) {
				if (opa.get(down[fx]).compareTo(opa.get(down[fy])) < 0)
					down[fy] = down[fx];
			} else {
				down[fy] = down[fx];
			}
		}

		if (fx != fy) 
			f[fx] = fy;
		return fy;










	}
}