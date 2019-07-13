package lispInterpreter.lexicalEnvironment;

import java.util.HashMap;
import java.util.IllegalFormatException;

import lispInterpreter.Tokens.Atom;
import lispInterpreter.Tokens.Expression;
import lispInterpreter.Tokens.List;

public class LexEnv {
	HashMap<String, Expression> map;

	public LexEnv() {
		this.map = new HashMap<String, Expression>();
	}
	
	//UNTESTED, hopefully creates a deep copy
	public LexEnv(LexEnv old) {
		this.map = new HashMap<String, Expression>();
		HashMap<String, Expression> oldMap = old.getMap();
		for(String name : oldMap.keySet()) {
			this.map.put(name, oldMap.get(name));
		}
	}
	
	/**
	 * Adds the given function to the environment under the given name
	 * @param s The name of the function to be added
	 * @param e The expression to be saved as the function
	 * @throws IllegalFormatException
	 */
	public void putSymbol(String s, Expression e) {
		 if(map.containsKey(s)) {
		 return;
		 }
		// if(e instanceof Atom) {
		// map.put(s, (Atom)e);
		// }else
//		if (e instanceof List) {
			map.put(s, e);
//		} else {
//			throw new IllegalArgumentException("Cannot addSymbol of non-list type");
//			// Might change this to be else{return addValue(s,e);} if Lisp demands
//		}
	}
	
	public void setq(String s, Expression e) {
		map.put(s+"-value", e);
	}

	/**
	 * Looks in the current environment for the function with the given name
	 * @param s The name of the function being called
	 * @return The expression associated with the given function name.  If no function is found, null is returned.
	 */
	public Expression getSymbol(String s) {
		if (map.containsKey(s)) {
			return map.get(s);
		}
		return null;
	}

	/**
	 * Puts a value into the current lexical environment
	 * @param s The name of the variable to be added
	 * @param e The expression associated with that variable name
	 */
	public void putValue(String s, Expression e) {
		 if(map.containsKey(s + "-value")) {
		 return;
		 }
		// if(e instanceof Atom) {
		// map.put(s + "-value", (Atom)e);
		// }else {
		// throw new IllegalArgumentException("Cannot add " + s + " as a value");
		// //Might change if addSymbol changes
		// }
		map.put(s + "-value", e);
	}

	/**
	 * Gets the value of the given variable
	 * @param s The name of the variable
	 * @return The value of the given variable or null if no variable is found.
	 */
	public Expression getValue(String s) {
		if (map.containsKey(s + "-value")) {
			return map.get(s + "-value");
		}
		return null;
	}
	
	public HashMap<String, Expression> getMap(){
		return this.map;
	}
	
	/**
	 * Creates a deep copy of the current environment and returns it
	 * @return A deep copy of the current environment
	 */
	public LexEnv copy(){
		return new LexEnv(this);
	}
}
