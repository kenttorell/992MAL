package lispInterpreter.Tokens;

import java.util.ArrayList;

import lispInterpreter.LispInterpreter;
import lispInterpreter.lexicalEnvironment.LexEnv;

public class List implements Expression{
	public ArrayList<Expression> list;
	boolean isLiteral;
	
	public List() {
		this.list = new ArrayList<Expression>();
	}
	public List(boolean isLiteral) {
		this.isLiteral = isLiteral;
		this.list = new ArrayList<Expression>();
	}
	public List(java.util.List<Expression> list, boolean isLiteral) {
		this.isLiteral = isLiteral;
		this.list = new ArrayList<Expression>();
		for(Expression item : list) {
			this.list.add(item);
		}
	}
	
	public void add(Expression e) {
		this.list.add(e);
	}
	
	
	@Override
	public boolean isLiteral() {
		return isLiteral;
	}
	@Override
	public void setLiteral() {
		isLiteral = true;
	}
	
	@Override
	public Expression eval(LexEnv environment, boolean inEval) {
		if(this.isLiteral() && !inEval) {
			for(Expression expr: list) {
				expr.setLiteral();
			}
			return this;
		}
		if(list.get(0) instanceof List) {
			list.set(0, list.get(0).eval(environment, false));
			return this.eval(environment, false);
		} else if(list.get(0) instanceof Atom) {
//			Atom a = ((Atom)(list.get(0)));
			Atom a = (Atom)(list.get(0));
			if(a.val instanceof String) {
				String errorMessage = "";
				
				
				switch((String)a.val) {
				case "+":
//					if(list.size() == 1) {
//						return new Atom("0");
//					}
					if(list.size() != 3) {
						errorMessage = "Incorrect number of arguments for add";
						break;
					}
					Number n1 = (Number)((Atom)(list.get(1).eval(environment, false))).val;
					Number n2 = (Number)((Atom)(list.get(2).eval(environment, false))).val;
					
					if(n1 instanceof Integer && n2 instanceof Integer) {
						return new Atom(Integer.toString((Integer)n1+(Integer)n2));
					}else if(n1 instanceof Integer) {
						return new Atom(Float.toString((Integer)n1 + (Float)n2));
					}else if(n2 instanceof Integer) {
						return new Atom(Float.toString((Float)n1 + (Integer)n2));
					}
					return new Atom(Float.toString((Float)n1 + (Float)n2));
					
				
				case "-":
					if(list.size() != 3) {
						errorMessage = "Incorrect number of arguments for subtract";
						break;
					}
					n1 = (Number)((Atom)(list.get(1).eval(environment, false))).val;
					n2 = (Number)((Atom)(list.get(2).eval(environment, false))).val;
					
					if(n1 instanceof Integer && n2 instanceof Integer) {
						return new Atom(Integer.toString((Integer)n1-(Integer)n2));
					}else if(n1 instanceof Integer) {
						return new Atom(Float.toString((Integer)n1 - (Float)n2));
					}else if(n2 instanceof Integer) {
						return new Atom(Float.toString((Float)n1 - (Integer)n2));
					}
					return new Atom(Float.toString((Float)n1 - (Float)n2));
				
				case "*":
					if(list.size() != 3) {
						errorMessage = "Incorrect number of arguments for multiplication";
						break;
					}
					n1 = (Number)((Atom)(list.get(1).eval(environment, false))).val;
					n2 = (Number)((Atom)(list.get(2).eval(environment, false))).val;
					
					if(n1 instanceof Integer && n2 instanceof Integer) {
						return new Atom(Integer.toString((Integer)n1*(Integer)n2));
					}else if(n1 instanceof Integer) {
						return new Atom(Float.toString((Integer)n1 * (Float)n2));
					}else if(n2 instanceof Integer) {
						return new Atom(Float.toString((Float)n1 * (Integer)n2));
					}
					return new Atom(Float.toString((Float)n1 * (Float)n2));
					
				case "/":
					if(list.size() != 3) {
						errorMessage = "Incorrect number of arguments for divide";
						break;
					}
					n1 = (Number)((Atom)(list.get(1).eval(environment, false))).val;
					n2 = (Number)((Atom)(list.get(2).eval(environment, false))).val;
					
					if(n1 instanceof Integer && n2 instanceof Integer) {
						return new Atom(Integer.toString((Integer)n1/(Integer)n2));
					}else if(n1 instanceof Integer) {
						return new Atom(Float.toString((Integer)n1 / (Float)n2));
					}else if(n2 instanceof Integer) {
						return new Atom(Float.toString((Float)n1 / (Integer)n2));
					}
					return new Atom(Float.toString((Float)n1 / (Float)n2));
					
				case "=":
				case "/=":
				case ">": 
				case "<":
				case ">=":
				case "<=":
				case "max":
				case "min":
					if(list.size() != 3) {
						errorMessage = "Incorrent number of argument, requires 2, found " + (list.size()-1);
					}
					return evalComparison(environment, inEval, (String)a.val);
					
				case "defvar":
					String valueName = (String)((Atom)(list.get(1).eval(environment, false))).val;
					Expression value = ((list.get(2).eval(environment, false)));
					list.get(1).setLiteral();
					environment.putValue(valueName, value);
					return list.get(1);
				
				case "defun":
					//(defun varName (argList) Expression)
					String funName = (String)((Atom)(list.get(1))).val;
					ArrayList<String> args = new ArrayList<String>();
					ArrayList<Expression> argList = ((List)(list.get(2))).list;
					for(Expression e : argList) {
						args.add((String)((Atom)e).val);
					}
					Function f = new Function(funName, args, list.get(3));
					environment.putSymbol(funName, f);
					return f;
					
					
				case "lambda":
					//TODO (lambda (argumentList) (expression))
					args = new ArrayList<String>();
					argList = ((List)(list.get(1))).list;
					for(Expression e : argList) {
						args.add((String)((Atom)e).val);
					}
					f = new Function("LAMBDA", args, list.get(2));
					return f;
					
					
				case "car":
					return ((List)(list.get(1).eval(environment, false))).list.get(0);
					
				case "cdr":
//					return new List(((List)(list.get(1).eval())).list.subList(1, list.size()+1), list.get(1).isLiteral());
					List evaluated = ((List)(list.get(1).eval(environment, false)));
					java.util.List<Expression> subList = evaluated.list.subList(1, evaluated.list.size());
					return new List(subList, evaluated.isLiteral);
				
				case "funcall": //(funcall FUNCTION argList)
					f = (Function)(list.get(1).eval(environment, false));
					LexEnv newEnvironment = new LexEnv(environment);
					ArrayList<String> functionArgList = f.args;
					for(int i = 0; i < functionArgList.size(); i++) {
						newEnvironment.putValue(functionArgList.get(i), list.get(i+2).eval(environment, false));
					}
					
					return f.eval(newEnvironment, false);
				
				case "setq":
					String name = null;
					Expression val = null;
					for(int i = 1; i < list.size(); i = i+2) {
						name = (String)((Atom)(list.get(i))).val;
						val = list.get(i+1);
						environment.setq(name, val);
					}
					return val;
					
				case "eval":
					//NOT WORKING YET!
					return list.get(1).eval(environment, list.get(1).isLiteral());
					
				case "if":
					list.get(1).eval(environment, false);
					
				default:// (fName args)
					f = (Function)environment.getSymbol((String)((Atom)(list.get(0))).val);
					if(f == null){
						errorMessage = "Unrecognized function call";
						break;
					}
					newEnvironment = new LexEnv(environment);
					functionArgList = f.args;
					for(int i = 0; i < functionArgList.size(); i++) {
						newEnvironment.putValue(functionArgList.get(i), list.get(i+1).eval(environment, false));
					}
					
					return f.eval(newEnvironment, false);
					
							
				}
				throw new IllegalArgumentException(errorMessage);
			
				
			}	
		}else if(list.get(0) instanceof Function) {
			Function f = (Function)list.get(0);
			LexEnv newEnvironment = new LexEnv(environment);
			ArrayList<String> functionArgList = f.args;
			for(int i = 0; i < functionArgList.size(); i++) {
				newEnvironment.putValue(functionArgList.get(i), list.get(i+1).eval(environment, false));
			}
			
			return f.eval(newEnvironment, false);
		}
		return null;
	}
	
	private Expression evalComparison(LexEnv environment, boolean inEval, String operator) {
		try {
		Atom a = (Atom)(this.list.get(1).eval(environment, inEval));
		Atom b = (Atom)(this.list.get(2).eval(environment, inEval));
		
		Float aVal = (Float)a.val;
		Float bVal = (Float)b.val;
		
		switch(operator) {
		case "=":
			if ((a.val).equals(b.val)) return new Atom("t"); return new Atom("nil");
		case "/=":
			if ((a.val).equals(b.val)) return new Atom("nil"); return new Atom("t");
		case ">": 
			if ((aVal)>(bVal)) return new Atom("t"); return new Atom("nil");
		case "<":
			if ((aVal)<(bVal)) return new Atom("t"); return new Atom("nil");
		case ">=":
			if ((aVal)>=(bVal)) return new Atom("t"); return new Atom("nil");
		case "<=":
			if ((aVal)<=(bVal)) return new Atom("t"); return new Atom("nil");
		case "max":
			if ((aVal)>(bVal)) return a; return b;
		case "min":
			if ((aVal)<(bVal)) return a; return b;
		}
		
		return null;
		
		}catch(Exception e) {return new Atom("nil");}
	}

}
