package lispInterpreter.Tokens;

import java.util.ArrayList;

import lispInterpreter.lexicalEnvironment.LexEnv;

public class Function implements Expression{
	public ArrayList<String> args;
	public Expression body;
	public String name;
	
	public Function(String name, ArrayList<String> args, Expression body) {
		this.args = args;
		this.body = body;
		this.name = name;
	}
	
	@Override
	public Expression eval(LexEnv environment, boolean inEval) {
		//TODO Args list
		return this.body.eval(environment, inEval);
	}

	@Override
	public boolean isLiteral() {
		// TODO Auto-generated method stub
		return false;
	}

	//DOES NOTHING
	@Override
	public void setLiteral() {
		// TODO Auto-generated method stub
		
	}

}
