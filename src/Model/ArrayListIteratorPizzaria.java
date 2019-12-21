package Model;

import java.util.ArrayList;

public class ArrayListIteratorPizzaria<E> implements IteratorPizzaria, StrategyIterator {

	protected ArrayList<E> arrayList;
	protected int cont;
	
	public ArrayListIteratorPizzaria (ArrayList<E> arrayList) {
		this.arrayList = arrayList;
	}
	
	@Override
	public Object next() {
		Object next = arrayList.get(cont);
		cont++;
		return next;
	}

	@Override
	public boolean hasNext() {
		return cont < arrayList.size();
	}

	@Override
	public IteratorPizzaria criarIterator() {
		return this;
	}

}
