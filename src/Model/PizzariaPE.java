package Model;

public class PizzariaPE extends PizzariaPorRegiao {

	@Override
	public PizzaDaRegiao impostoDaRegiao(String pizzaR) {
		
		PizzaDaRegiao pizzaDaRegiao = null;
		switch (pizzaR.toUpperCase()) {
		case "CALABRESA":
			pizzaDaRegiao = new PizzaCalabresaPE();
			break;
		case "MUSSARELA":
			pizzaDaRegiao = new PizzaMussarelaPE();
			break;
		case "QUATRO QUEIJOS":
			pizzaDaRegiao = new PizzaQuatroQueijosPE();
			break;
		case "FRANGO":
			pizzaDaRegiao = new PizzaFrangoPE();
			break;
		}
		
		return pizzaDaRegiao;
	}
	
	
	
}
