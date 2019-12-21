package Model;

public class VetorIteratorPizzaria implements IteratorPizzaria, StrategyIterator {

	protected Object[] vetor;
	protected int cont;

	public VetorIteratorPizzaria (Object[] vetor) {
		this.vetor = vetor;
	}

	@Override
	public Object next() {
		String obj = vetor[cont].toString();
		cont++;
		return obj;
	}

	@Override
	public boolean hasNext() {
		if (cont >= vetor.length || vetor[cont] == null)
			return false;
		else 
			return true;
	}

	@Override
	public IteratorPizzaria criarIterator() {
		return this;
	}

}
