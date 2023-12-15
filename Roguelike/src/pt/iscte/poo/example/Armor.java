package pt.iscte.poo.example;

import pt.iscte.poo.utils.Point2D;

public class Armor extends Obtainable {

	public Armor(Point2D position, int layer) {
		super(position, layer);
	}

	@Override
	public String getName() {
		return "Armor";
	}
	
	@Override
	public boolean isPassable() {
		return false;
	}

}
