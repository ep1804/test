package radix;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class MsdCountSorted {

	private final int R = 256;

	public String[] arr;
	private String[] buf;

	public MsdCountSorted(String[] a) {
		arr = Arrays.copyOf(a, a.length);
		buf = new String[arr.length];

		sort(0, a.length, 0);
	}

	private void sort(int lo, int hi, int d) {
		if(lo >= hi) return;
		
		int[] count = new int[R + 2];

		for (int i = lo; i < hi; i++)
			count[charAt(arr[i], d) + 2]++;
		for (int i = 0; i < R + 1; i++)
			count[i + 1] += count[i];
		for (int i = lo; i < hi; i++)
			buf[lo + count[charAt(arr[i], d) + 1]++] = arr[i];
		for (int i = lo; i < hi; i++)
			arr[i] = buf[i];

		for (int i = 0; i < R; i++) {
			sort(lo + count[i], lo + count[i+1], d + 1);
		}
	}

	private int charAt(String s, int at) {
		if (at < s.length())
			return s.charAt(at);
		else
			return -1;
	}

	public static void print(String[] a) {
		for (int i = 0; i < a.length; i++)
			System.out.print(a[i] + " ");
		System.out.println();
	}

	public static void print(int[] a) {
		for (int i = 0; i < a.length; i++)
			System.out.print(a[i] + " ");
		System.out.println();
	}

	public static void main(String[] args) throws FileNotFoundException {
		List<String> buf = new LinkedList<String>();
		Scanner in = new Scanner(new File("input/radix/msd"));
		while (in.hasNextLine()) {
			buf.add(in.nextLine());
		}
		in.close();

		String[] ws = new String[buf.size()];
		buf.toArray(ws);

		print(ws);

		MsdCountSorted sorted = new MsdCountSorted(ws);

		print(sorted.arr);
	}
}