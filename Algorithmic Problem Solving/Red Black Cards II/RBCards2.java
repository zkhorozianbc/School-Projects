import java.io.*;
import java.util.*;

public class RBCards2 {
	public static int[] cards;
	public static int[] colors;
	public static int red;
	public static int black;
	public static Map<Integer,Integer> size = new HashMap<Integer,Integer>();
	public static int n;
	public static int m;
	public static int count = 0;
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new FileReader("RBCardsTest.txt"));
		//BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		StringBuilder out = new StringBuilder();
		n = Integer.parseInt(st.nextToken());
		int q = Integer.parseInt(st.nextToken());
		m = n+1;
		red = 2*m+1;
		black = 2*m+2;
		colors = new int[m];
		cards = new int[2*m];
		for (int i = 0; i < 2*m; i++) {
			if (i != m) {
				cards[i] = i;
				size.put(i,1);
			}

		}
		cards[n+1] = 0;

		String op;
		int x;
		int y;
		boolean result = true;
		for (int i = 1; i <= q; i++) {
			st = new StringTokenizer(br.readLine());
			op = st.nextToken();
			x = Integer.parseInt(st.nextToken());
			switch(op) {
				case "d":
					y = Integer.parseInt(st.nextToken());
					result = d(x,y);
					break;
				case "s":
					y = Integer.parseInt(st.nextToken());
					result = s(x,y);
					break;
				default:
					result = fill(x,op);
					break;

			}
			for (int r = 1; r < colors.length; r++)
				System.out.print(colors[r] + " ");
			System.out.println();
			boolean flag = true;
			for (int r = 1; r < colors.length; r++) {
				if (colors[r] == 0) {
					flag = false;
					break;
				}
			}
		
			if (!result)
				out.append("Q" + i + ": ?\n");
			// System.out.println(count);
			if (flag) {
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
	public static boolean d(int x, int y) {
		int fx = find(x);
		int fy = find(y);
		int fxp = find(x+m);
		int fyp = find(y+m);
		if (fx == fy) {
			return false;
		}
		for (int i = 1; i < cards.length; i++) {
			if (cards[i] == fx) {
				if (colors[fyp%m] != 0)
					colors[i%m] = colors[fyp%m];
				count += 1;
				cards[i] = fyp;
			}
			else if (cards[i] == fxp) {
				if (colors[fy%m] != 0)
					colors[i%m] = colors[fy%m];
				count += 1;
				cards[i] = fy;
			}
		}
		return true;
	}
	public static boolean s(int x, int y) {
		int fx = find(x);
		int fy = find(y);
		int fxp = find(x+m);
		int fyp = find(y+m);
		if (fx == fyp) {
			return false;
		}

		for (int i = 1; i < cards.length; i++) {
			if (cards[i] == fx) {
				if (colors[fy%m] != 0)
					colors[i%m] = colors[fy%m];
				count += 1;
				cards[i] = fy;
			}
			if (cards[i] == fxp) {
				if (colors[fyp%m] != 0)
					colors[i%m] = colors[fyp%m];
				count += 1;
				cards[i] = fyp;
			}
		}
		return true;
	}

	public static boolean fill(int x, String c) {
		int shade;
		int shadeComp;
		if (c.equals("r")) {
			shade = red;
			shadeComp = black;
		} else {
			shade = black;
			shadeComp = red;
		}
		int fx = find(x);
		int fxp = find(x+m);
		// for (int i = 1; i < cards.length; i++) {
		// 	if ((cards[i] == fx && (colors[i%m] != 0 && colors[i%m] != shade)) || 
		// 		(cards[i] == fxp && (colors[i%m] != 0 && colors[i%m] == shade)))
		// 		return false;
		// }
		if ((colors[fx%m] != 0 && colors[fx%m] != shade))
			return false;

		for (int i = 1; i < colors.length; i++) {
			if (cards[i] == fx) {
				colors[i%m] = shade;
				count += 1;
			}
			if (cards[i] == fxp) {
				colors[i%m] = shadeComp;
				count += 1;
			}
		}
		
		return true;


	}


	public static int find(int x) {
		if (cards[x] == x)
			return x;
		return cards[x] = find(cards[x]);
	}




}