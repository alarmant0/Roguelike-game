package pt.iscte.poo.example;

import pt.iscte.poo.utils.Point2D;

public class Sword extends Obtainable {

	public Sword(Point2D position, int layer) {
		super(position, layer);
	}

	@Override
	public String getName() {
		return "Sword";
	}
	
	@Override
	public boolean isPassable() {
		return true;
	}
	
}
