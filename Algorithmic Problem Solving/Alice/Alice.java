import java.io.*;
import java.util.*;

public class Alice {
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		StringBuilder out = new StringBuilder();

		int t = in.nextInt();
		int num_cities;
		HashSet<String> hs;

		for (int i = 0; i < t; i++) {
			num_cities = in.nextInt();
			hs = new HashSet<String>();
			for (int j = 0; j < num_cities; j++) {
				hs.add(in.next());
			}
			out.append(hs.size());
			if (i != (t-1))
				out.append("\n");
			
		}
		System.out.println(out.toString());

	}
}