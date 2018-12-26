import java.io.*;
import java.util.*;

public class StudentsInARowLL {
	public static LinkedList<String> opa;
	public static int num_names;
	public static int num_actions;
	public static StringBuilder out;
	public static void main(String[] args) throws Exception {
		init();
		
		

	}

	public static void init() throws Exception {
		setOutputChannel();
		readInput();
		printFinalOutput();
	}

	public static void setOutputChannel() {
		out = new StringBuilder();
	}

	public static void addToOutputChannel(String s) {
		out.append(s + "\n");
	}

	public static void printFinalOutput() {
		out.setLength(out.length() - 1);
		System.out.println(out.toString());
	}
	public static void takeAction(String op, String s1) {
		takeAction(op,s1,null);
	}

	public static void takeAction(String op, String s1, String s2) {
		switch(op) {
			case "e":
				e(s1);
				break;
			case "x":
				x(s1,s2);
				break;
			case "l":
				addToOutputChannel(l(s1));
				break;
			default:
				addToOutputChannel(r(s1));
		}
	
	}


	public static void readInput() throws Exception {
		//read names
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		num_names = Integer.parseInt(st.nextToken());
		
		st = new StringTokenizer(br.readLine());
		opa = new LinkedList<String>();
	
		for (int i = 0; i < num_names; i++) {
			opa.add(st.nextToken());
		}

		//read action line by line and execute each action when read
		st = new StringTokenizer(br.readLine());
		num_actions = Integer.parseInt(st.nextToken());

		String op;
		String s1;
		String s2;
		for (int i = 0; i < num_actions; i++) {
			st = new StringTokenizer(br.readLine());
			op = st.nextToken();
			if (op.equals("x")) {
				s1 = st.nextToken();
				s2 = st.nextToken();
				takeAction(op,s1,s2);
			} else {
				s1 = st.nextToken();
				takeAction(op,s1);
			}
			
		}
		
	}

	public static void e(String s1) {
		opa.remove(s1);
	}

	public static void x(String s1,String s2) {
		int i = opa.indexOf(s1);
		int j = opa.indexOf(s2);
		opa.set(i,s2);
		opa.set(j,s1);
		
	}
	public static String r(String s1) {
		int i = opa.indexOf(s1);
		if (i  == opa.size() - 1)
			return "-1";
		return opa.get(i+1);
	}
	public static String l(String s1) {
		int i = opa.indexOf(s1);
		if (i == 0)
			return "-1";
		return opa.get(i-1);
	}


























}   