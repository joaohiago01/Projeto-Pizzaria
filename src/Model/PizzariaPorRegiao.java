package Model;

public abstract class PizzariaPorRegiao {

	
	
	
	
	public final PizzaDaRegiao impostoDaPizza(String pizzaR) {
		PizzaDaRegiao pizzaDaRegiao = impostoDaRegiao(pizzaR);
		return pizzaDaRegiao;
	}
	
	public abstract PizzaDaRegiao impostoDaRegiao(String pizzaR);

}
