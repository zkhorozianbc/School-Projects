import java.io.*;
import java.util.*;

public class FoodVarietyTwo {
	static class Restaurant {
		public int p,t;
		public Restaurant(int p, int t) {
			this.p = p;
			this.t = t;
		}
	}
	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader br = new BufferedReader(new FileReader("FoodVarietyTest.txt"));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int n = Integer.parseInt(st.nextToken());
		int m = Integer.parseInt(st.nextToken());
		int k = Integer.parseInt(st.nextToken());
	
		
		List<Restaurant> restaurants = new ArrayList<Restaurant>();
		List<Integer> restaurantsPositions = new ArrayList<Integer>();
		List<Integer> customers = new ArrayList<Integer>();
		int p,t;
		for (int i = 0; i < n; i++) {
			st = new StringTokenizer(br.readLine());
			p = Integer.parseInt(st.nextToken());
			t = Integer.parseInt(st.nextToken());
	
			restaurants.add(new Restaurant(p,t));
			restaurantsPositions.add(p);
		}
		
		st = new StringTokenizer(br.readLine());
		for (int i = 0; i < m; i++) {
			p = Integer.parseInt(st.nextToken());
			customers.add(p);
		}
		Collections.sort(restaurants, new Comparator<Restaurant>() {
			public int compare(Restaurant a, Restaurant b) {
				return a.p - b.p;
			} 
		});
		Collections.sort(restaurantsPositions);
		Collections.sort(customers);
		
	
		


		int d = 0;
		HashSet<Integer> hello = new HashSet<Integer>();
		for (Integer c: customers) {
			int trav = -1*(Collections.binarySearch(restaurantsPositions, c)+1);
			int left = trav-1;
			int right = trav;
			int currD = d;
			int ld = c;
			int rd = c;
			while (left >= 0 && c - restaurants.get(left).p <= d) {
				hello.add(restaurants.get(left).t);
				ld = restaurants.get(left).p;
				left -= 1;
			}
			while (right < n && restaurants.get(right).p - c <= d) {
				hello.add(restaurants.get(right).t);
				rd = restaurants.get(right).p;
				right += 1;
			}

			while (hello.size() < k) {

				if (left >= 0 && right < n) {
					if (c - restaurants.get(left).p <= restaurants.get(right).p - c) {
						ld = restaurants.get(left).p;
						hello.add(restaurants.get(left).t);
						left -= 1;				
					} else {
						rd = restaurants.get(right).p;
						hello.add(restaurants.get(right).t);
						right += 1;					
					}
				}
				else if (left >= 0) {
					ld = restaurants.get(left).p;
					hello.add(restaurants.get(left).t);
					left -= 1;
				} else {
					rd = restaurants.get(right).p;
					hello.add(restaurants.get(right).t);
					right += 1;		
				}
				
			}
			hello.clear();
			d = Math.max(d,Math.max(Math.abs(c-ld),Math.abs(rd-c)));
		}
		System.out.println(d);
		






	}
}