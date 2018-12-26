import java.io.*;
import java.util.*;

public class FoodVariety {
	public static void main(String[] args) throws Exception{
		//BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedReader br = new BufferedReader(new FileReader("FoodVarietyTest.txt"));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int n = Integer.parseInt(st.nextToken());
		int m = Integer.parseInt(st.nextToken());
		int k = Integer.parseInt(st.nextToken());
	
		int MAX_ROAD_LENGTH = 1000000;
		int[] road = new int[MAX_ROAD_LENGTH];
		List<Integer> customers = new ArrayList<Integer>();
		int p,t;
		for (int i = 0; i < n; i++) {
			st = new StringTokenizer(br.readLine());
			p = Integer.parseInt(st.nextToken());
			t = Integer.parseInt(st.nextToken());
			road[p] = t;
		}
		st = new StringTokenizer(br.readLine());
		for (int i = 0; i < m; i++) {
			p = Integer.parseInt(st.nextToken());
			customers.add(p);
			road[p] = -1;
		}
		int d = 0;
		HashSet<Integer> cuisines = new HashSet<Integer>();
		for (Integer c: customers) {
			int i = 0;
			while (cuisines.size() < k) {
				i += 1;
				if ((c-i) >= 0) {
					if (road[c-i] > 0)
						cuisines.add(road[c-i]);
				}
				if ((c+i) < MAX_ROAD_LENGTH) {
					if (road[c+i] > 0)
						cuisines.add(road[c+i]);
				}
				
			}
			d = Math.max(d,i);
			cuisines.clear();
		}
		
		
		
		System.out.println(d);
		






	}
}