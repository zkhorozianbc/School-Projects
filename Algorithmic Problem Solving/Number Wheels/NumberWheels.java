import java.io.*;
import java.util.*;
import java.math.*;
public class NumberWheels {
	
	public static void main(String[] args) {

		Scanner in = new Scanner(System.in);
		List<List<Integer>> cols = new ArrayList<List<Integer>>();
		int n = in.nextInt();

		for (int i = 0; i < 3; i++) {
			List<Integer> x = new ArrayList<Integer>();
			cols.add(x);
		}

		for (int i = 0; i < n; i++) {
			for (int j = 0; j < 3; j++) {
				cols.get(j).add(in.nextInt());
			}
		}

		rotate(cols);


	}

	public static void spin(List<List<Integer>>  l, int[] currRotations) {
		for (int i = 0; i < currRotations.length; i++) {
			for (int j = 0; j < currRotations[i]; j++)
				forward(l.get(i));
		}
	}

	public static void forward(List<Integer> l) {
		int i = l.remove(0);
		l.add(i);
	}
	public static void backward(List<Integer> l) {
		int i = l.remove(l.size()-1);
		l.add(0,i);
	}

	public static void printColumns(List<List<Integer>> l) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < l.size(); i++) {
			for (int j = 0; j < l.get(i).size(); j++) {
				sb.append(" ");
				sb.append(l.get(i).get(j));
			}
			sb.append("\n");
		}
		System.out.println(sb.toString());
	}

	public static int score(List<List<Integer>> l) {
		int z = 0;
		StringBuilder sb;
		for (int i = 0; i < l.get(0).size(); i++) {
			sb = new StringBuilder();
			for (int j = 0; j < l.size(); j++) {
				sb.append(l.get(j).get(i));
			}
			int row = Integer.parseInt(sb.toString());
			z += (row*row);
		}
		return z;
		
	}

	public static List<List<Integer>> matrixCopy(List<List<Integer>> l) {
		List<List<Integer>> b = new ArrayList<List<Integer>>();
		for (int i = 0; i < 3; i++) {
			List<Integer> x = new ArrayList<Integer>();
			b.add(x);
		}
		for (int i = 0; i < l.size(); i++) {
			for (int j = 0; j < l.get(0).size(); j++) {
				b.get(i).add(l.get(i).get(j));
			}
		}
		return b;
	}

	public static boolean hasSmallerRot(int[] a,int[] b) {
		for (int i = 0; i < a.length; i++) {
			if (a[i] < b[i]) {
				return true;
			}
			if (a[i] > b[i]) {
				return false;
			}
		}
		return false;
	}

	public static void setRotations(int[] a, int[] b) {
		for (int i = 0; i < a.length; i++) {
			a[i] = b[i];
		}
	}
	

	public static void rotate(List<List<Integer>> l) {
		int n = l.get(0).size();
		int max_rot = n;
		int[] minRotations = {0,0,0};
		int[] currRotations = {0,0,0};
		int max_score = -1;
		int curr_score = -1;
		
		// List<List<Integer>> opa;
		for (int i = 0; i < max_rot; i++) {
			for (int j = 0; j < max_rot; j++) {
				for (int k = 0; k < max_rot; k++) {
					// opa = matrixCopy(l);
					int[] new_rotations = {i,j,k};
					// setRotations(currRotations,new_rotations);
					curr_score = score(l);
					if (curr_score == max_score && hasSmallerRot(new_rotations,minRotations)) {
						setRotations(minRotations,new_rotations);
					}
					if (curr_score > max_score) {
						setRotations(minRotations,new_rotations);
						max_score = curr_score;
					}
					forward(l.get(2));

				}
				forward(l.get(1));
			}
			forward(l.get(0));
		}

		StringBuilder sb = new StringBuilder();
		sb.append(max_score);
		sb.append("\n");
		for (int i = 0; i < 3; i++) {
			if (i != 0) {
				sb.append(" ");
			}
			sb.append(minRotations[i]);
		}

		System.out.println(sb.toString());



	}
























}