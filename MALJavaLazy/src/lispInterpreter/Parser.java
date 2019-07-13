package lispInterpreter;

import java.util.Stack;

import lispInterpreter.Tokens.Atom;
import lispInterpreter.Tokens.Expression;
import lispInterpreter.Tokens.List;

class Parser {
	static Stack<String> stack;
	
	public static Expression parse(Stack<String> stack) {
		Parser.stack = stack;
		return parseExpression();
	}
	private static Expression parseExpression() {
		String currentToken = stack.pop();
		boolean isStringLit = false;
		if("'".equals(currentToken)) {
			isStringLit = true;
			currentToken = stack.pop();
		}
		if("(".equals(currentToken)) {
			return parseList(isStringLit);
		}
		return new Atom(currentToken, isStringLit);
	}
	
	private static List parseList(boolean isStringLit) {
		String currentToken = stack.pop();
		List list = new List(isStringLit);
		while(!(")".equals(currentToken))) {
			boolean newIsStringLit = false;
			if("'".equals(currentToken)) {
				newIsStringLit = true;
				currentToken = stack.pop();
			}
			if("(".equals(currentToken)) {
				list.add(parseList(newIsStringLit));
			}else {
				list.add(new Atom(currentToken, newIsStringLit));
			}
			
			currentToken = stack.pop();
		}
		return list;
	}
	
	
}
