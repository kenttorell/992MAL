package lispInterpreter.Tokens;

import lispInterpreter.lexicalEnvironment.LexEnv;

public interface Expression{
//	Expression element;
	
//	public Expression() {
//		
//	}
//	public Expression(Expression element) {
//		if(!(element instanceof List || element instanceof Atom)){
//			throw new IllegalArgumentException("Expressions must be either an element or an Atom");
//		}
//		this.element = element;
//	}
	
	
//	@Override
	public Expression eval(LexEnv environment, boolean inEval); //{
//		Object rtrn = null;
//		if(element instanceof List) {
//			rtrn = ((List)element).eval();
//		}
//		if(element instanceof Atom) {
//			rtrn = ((Atom)element).eval();
//		}
//		return null;
//	}
	
	public boolean isLiteral();

	public void setLiteral();
}