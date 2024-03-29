package lispInterpreter.Tokens;

import lispInterpreter.LispInterpreter;
import lispInterpreter.lexicalEnvironment.LexEnv;

public class Atom implements Expression {
	Object val;
	private boolean isLiteral;

	public Atom(String val) {
		try {
			this.val = Integer.parseInt(val);
		} catch (NumberFormatException e1) {
			try {
				this.val = Float.parseFloat(val);
			} catch (NumberFormatException e2) {
				this.val = val;
			}
		}
	}
	
	public Atom(String val, boolean isLiteral) {
		this.isLiteral = isLiteral;
		try {
			this.val = Integer.parseInt(val);
		} catch (NumberFormatException e1) {
			try {
				this.val = Float.parseFloat(val);
			} catch (NumberFormatException e2) {
				this.val = val;
			}
		}
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
	public Expression eval(LexEnv environment, boolean inEval) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		Expression rtrn = this;
		if(this.isLiteral() && !inEval) {
			return this;
		}
		if (val instanceof String) {
//			rtrn = environment.getSymbol((String) val); //rtrn becomes null if getSymbol fails to find symbol
//			if (rtrn == null) {
				rtrn = environment.getValue((String) val);
//			}
			if (rtrn == null) {
				rtrn = this;
			}
			
		}
		

		return rtrn;
	}

	public Object getVal() {
		return val;
	}
}
