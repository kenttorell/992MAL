package lispInterpreter.Scanner;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

public class MyScanner {
	File myFile;
	InputStream inStream;

	public MyScanner(String fileName) throws FileNotFoundException {
		myFile = new File(fileName);
		inStream = new FileInputStream(myFile);

	}

	public Stack<String> getStringArray() {
		String s = "";
		Scanner scan = new Scanner(inStream);
		while (scan.hasNextLine()) {
			s = s + scan.nextLine();
		}
		return scanLine(s);
	}

	public static Stack<String> scanLine(String line) {
		String[] badArr = (line.replace("(", " ( ").replace(")", " ) ").replace("\n", " ").replace("\r", " ").replace("'", " ' "))
				.split(" ");
		ArrayList<String> arrList = new ArrayList<String>();
		for (String s : badArr) {
			if (" ".equals(s) || "".equals(s)) {
				continue;
			}
			arrList.add(s);
		}
		return toStack(arrList.toArray(new String[arrList.size()]));
	}

	private static Stack<String> toStack(String[] array) {
		Stack<String> rtrn = new Stack<String>();
		for (int i = array.length - 1; i >= 0; i--) {
			rtrn.push(array[i]);
		}
		return rtrn;
	}
}
