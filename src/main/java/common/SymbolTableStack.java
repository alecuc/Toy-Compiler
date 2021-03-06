package common;

import java.util.ArrayDeque;
import java.util.Deque;

public class SymbolTableStack {
	
	private Deque<SymbolTable> stack;

	public SymbolTableStack() {
		stack = new ArrayDeque<>();
	}

	public void enterScope(){
		stack.push(new SymbolTable());
	}

	public Symbol lookup(String item){

		Symbol found = null;

		for (SymbolTable current : stack){
			found = current.get(item);
			if(found != null)
				break;
		}

		return found;
	}

	public Symbol lookup(String item,int type){

		Symbol found = null;

		for (SymbolTable current : stack){
			found = current.get(item);
			if(found != null && found.entryType == type)
				break;
		}

		return found != null && found.entryType == type ? found : null;
	}

	public boolean probe(Symbol item){
		return stack.peek().containsKey(item.identifier);
	}

	public boolean probe(String item){
		return stack.peek().containsKey(item);
	}

	public boolean addId(Symbol item){
		if(probe(item.identifier))
			return false;
		
		stack.peek().put(item.identifier,item);
		return true;
	}

	public void exitScope(){
		stack.pop();
	}


}
