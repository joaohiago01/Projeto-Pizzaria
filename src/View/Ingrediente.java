package View;

public class Ingrediente extends SaborDecorate {

	public Ingrediente(SaborPizza saborPizza, String nome, double preco) {
		super(saborPizza);
		this.nome = nome;
		this.preco = preco;
	}
	
}

