package View;

public abstract class SaborDecorate extends SaborPizza{

	private SaborPizza saborPizza;
	
	public SaborDecorate (SaborPizza saborPizza) {
		this.saborPizza = saborPizza;
	}

	@Override
	public String getNome() {
		return saborPizza.getNome() +"#"+ this.nome;
	}

	@Override
	public double getPreco() {
		return saborPizza.getPreco() + this.preco;
	}
	
}
