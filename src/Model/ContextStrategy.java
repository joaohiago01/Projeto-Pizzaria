package Model;

import java.util.ArrayList;

public class ContextStrategy<E> {
	
	private StrategyIterator strategyIterator;
	
	@SuppressWarnings("unchecked")
	public ContextStrategy (Object collection) {
		
		if (collection instanceof String[]) {
			this.strategyIterator = new VetorIteratorPizzaria((Object[]) collection);
		} else if (collection instanceof ArrayList) {
			this.strategyIterator = new ArrayListIteratorPizzaria<E>((ArrayList<E>) collection);
		}
	}
	
	public IteratorPizzaria kindIterator() {
		return this.strategyIterator.criarIterator();
	}
}
