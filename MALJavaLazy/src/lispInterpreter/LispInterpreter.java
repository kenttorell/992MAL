package lispInterpreter;

import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Stack;
import java.util.concurrent.TimeUnit;

import lispInterpreter.Scanner.MyScanner;
import lispInterpreter.Tokens.Expression;
import lispInterpreter.lexicalEnvironment.LexEnv;
import lispInterpreter.printer.Printer;

public class LispInterpreter {
	static Parser parser;
	public static LexEnv environment = new LexEnv();

	public static void main(String[] args) {
		Expression expression = null;
		MyScanner scan = null;
		try {
			scan = new MyScanner(args[0]);
			expression = Parser.parse(scan.getStringArray());
		} catch (FileNotFoundException e) {
			System.out.println("File not found, please try another!");
			System.exit(1);
		} catch (IndexOutOfBoundsException e) {
			System.out.println("No file inputted (please add as argument).");
			System.out.println("Starting command line interpreter.");
			System.out.println("PLEASE PRINT EACH ENTIRE STATEMENT ON A SINGLE LINE");
			System.out.println("---------------------------------------------------");
			commandLine();
		} catch (Exception e) {
			System.out.println("Parsing error");
			e.printStackTrace();
			System.exit(1);
		}
		
		try {
			expression.eval(environment, false);
		}catch(Exception e) {
			System.out.println("Error on evaluation");
			e.printStackTrace();
		}
	}

	private static void commandLine() {
		Scanner scan = new Scanner(System.in);
		Expression expression = null;
		while (true) {
			System.out.println();
			System.out.print("lisp.java> ");
			String line = scan.nextLine();
			if ("exit".equals(line) || "exit()".equals(line)) {
				break;
			}

			Stack<String> stack = MyScanner.scanLine(line);
			try {
				expression = Parser.parse(stack);
				try {
					Expression evaluation = expression.eval(environment, false);
					Printer.print(evaluation, environment);
				} catch (Exception e) {
					System.out.println("Issue with evaluation");
					System.out.println("Stack trace: ");
					e.printStackTrace();
					//Sleep for a second for print format reasons
					try {
						TimeUnit.SECONDS.sleep(1);
					} catch (InterruptedException e1) {
					}
					
					System.out.println("\nPlease type \'exit\' or \'exit()\' to quit");
				}
			} catch (Exception e) {
				System.out.println("Parsing error");
				System.out.println("Stack trace:");
				e.printStackTrace();
				
				//Sleep for a second for print format reasons
				try {
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e1) {
				}
				
				System.out.println("\nPlease type \'exit\' or \'exit()\' to quit");
			}

		} // End of while loop
		System.out.println("Goodbye!");
		System.exit(0);
	}

}
