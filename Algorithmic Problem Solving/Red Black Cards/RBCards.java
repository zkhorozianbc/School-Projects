import java.io.*;
import java.util.*;

public class RBCards {
	public static int[] cards;
	public static int[] colors;
	public static int red;
	public static int black;
	public static int[] rank;
	public static int n;
	public static int q;
	public static int m;
	public static int count = 0;
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new FileReader("RBCardsTest.txt"));
		//BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		StringBuilder out = new StringBuilder();
		n = Integer.parseInt(st.nextToken());
		q = Integer.parseInt(st.nextToken());
		m = n+1;
		red = 2*m+1;
		black = 2*m+2;
		colors = new int[m];
		cards = new int[2*m];
		rank = new int[2*m];

		for (int i = 0; i < 2*m; i++) {
			if (i != m && i != 0) {
				cards[i] = i;
				rank[i] = 1;
			} else {
				cards[i] = 0;
			}
		}

		String op;
		int x;
		int y;
		boolean result = true;
		for (int i = 1; i <= q; i++) {
			st = new StringTokenizer(br.readLine());
			op = st.nextToken();
			x = Integer.parseInt(st.nextToken());
			if (st.hasMoreTokens()) {
				y = Integer.parseInt(st.nextToken());
				result = relate(x,y,op);
			} else {
				result = fill(x,op);
			}
			for (int k: cards)
				System.out.print(k + " ");
			System.out.println();
			for (int k: rank)
				System.out.print(k + " ");
			System.out.println();
			for (int k: colors)
				System.out.print(k + " ");
			System.out.println();
			if (!result)
				out.append("Q" + i + ": ?\n");
			if (count == n) {
				out.append("Q" + i + ": I know\n");
				
				for (int j = 1; j < colors.length; j++) {
					if (colors[j] == red) {
						out.append("r");
					} else if (colors[j] == black) {
						out.append("b");
					} else {
						out.append("0");
					}
				}
				System.out.println(out.toString());
				return;
			}
			
			


		}
		out.append("I am not sure");
		System.out.println(out.toString());
	}
	public static boolean relate(int x, int y, String c) {
		int fx = find(x);
		int fy = find(y);
		int fxp = find(x+m);
		int fyp = find(y+m);
		if ((c.equals("d") && fx == fy) || (c.equals("s") && fx == fyp))
			return false;
		if (rank[fx] > rank[fy]) {
			cards[fx] = c.equals("d") ? fyp: fy;
			cards[fxp] = c.equals("d") ? fy: fyp;
		} else if (rank[fy] > rank[fx]){
			cards[fy] = c.equals("d") ? fxp: fx;
			cards[fyp] = c.equals("d") ? fx: fxp;

		} else {
			cards[fy] = c.equals("d") ? fxp: fx;
			cards[fyp] = c.equals("d") ? fx: fxp;
			rank[cards[fy]] += 1;
			rank[cards[fyp]] += 1;

		}

		return true;
	}

	public static boolean fill(int x, String c) {
		int shade = c.equals("r") ? red : black;
		int shadeComp = c.equals("r") ? black : red;
		int fx = find(x);
		int fxp = find(x+m);
		if (((colors[fx%m] != 0 && colors[fx%m] == shadeComp)) || 
				((colors[fxp%m] != 0 && colors[fxp%m] == shade)))
			return false;
		colors[fx%m] = shade;
		colors[fxp%m] = shadeComp;
		
		return true;
	}


	public static int find(int x) {
		if (cards[x] == x)
			return x;

		return cards[x] = find(cards[x]);
	}




}