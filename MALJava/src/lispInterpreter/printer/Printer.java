package lispInterpreter.printer;

import java.util.ArrayList;

import lispInterpreter.Tokens.Atom;
import lispInterpreter.Tokens.Expression;
import lispInterpreter.Tokens.Function;
import lispInterpreter.Tokens.List;
import lispInterpreter.lexicalEnvironment.LexEnv;

public class Printer {
	public static void print(Expression e, LexEnv environment) {
		if(e.isLiteral()) {
			if(e instanceof Atom) {
				System.out.print(((Atom)e).getVal());
			}else if(e instanceof List) {
				System.out.print("(");
				ArrayList<Expression> list = ((List)e).list;
				for(Expression expr : list) {
					if(!(expr == list.get(0))) {
						System.out.print(" ");
					}
					expr.setLiteral();
					print(expr, environment);
//					System.out.print(" ");
				}
				System.out.print(")");
			}
		}
		else if(e instanceof Atom) {
			Atom atomToPrint = (Atom)e;
			if(atomToPrint.getVal() instanceof Number) {
				System.out.print(atomToPrint.getVal());
			}
			else {
				if(atomToPrint == atomToPrint.eval(environment, false)) {
					System.out.print("No variable found with name " + atomToPrint.getVal());
				}
			}
		}
		else if(e instanceof Function) {
			Function f = (Function)e;
			System.out.print("(" + f.name + " (");
			for(String argName : f.args) {
				System.out.print(argName + " ");
			}
			System.out.print(") ");
			printLiteral(f.body);
			System.out.print(")");
		}
	}
	
	private static void printLiteral(Expression e) {
		if(e instanceof Atom) {
			System.out.print(((Atom)e).getVal());
		}else if(e instanceof List) {
			System.out.print("( ");
			for(Expression item : ((List)e).list) {
				printLiteral(item);
				System.out.print(" ");
			}
			System.out.print(")");
		}
	}
}
