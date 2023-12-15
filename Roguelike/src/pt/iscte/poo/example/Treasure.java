package pt.iscte.poo.example;

import pt.iscte.poo.utils.Point2D;

public class Treasure extends GameElement {

	public Treasure(Point2D position, int layer) {
		super(position, layer);
	}

	@Override
	public String getName() {
		return "Treasure";
	}

	@Override
	public boolean isPassable() {
		return false;
	}
	
	public static void win() {
		Engine en = Engine.getInstance();
		en.getGUI().setMessage("Good Job! You won with a score of : " + en.getScore().calculateScore());
	}
	
}
