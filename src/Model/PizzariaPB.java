package Model;

public class PizzariaPB extends PizzariaPorRegiao {

	@Override
	public PizzaDaRegiao impostoDaRegiao(String pizzaR) {
		
		PizzaDaRegiao pizzaDaRegiao = null;
		switch (pizzaR.toUpperCase()) {
		case "CALABRESA":
			pizzaDaRegiao = new PizzaCalabresaPB();
			break;
		case "MUSSARELA":
			pizzaDaRegiao = new PizzaMussarelaPB();
			break;
		case "QUATRO QUEIJOS":
			pizzaDaRegiao = new PizzaQuatroQueijosPB();
			break;
		case "FRANGO":
			pizzaDaRegiao = new PizzaFrangoPB();
			break;
		}
		
		return pizzaDaRegiao;
	}
	
}
