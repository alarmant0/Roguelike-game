package pt.iscte.poo.example;

import pt.iscte.poo.utils.Point2D;

public class RedGreen extends GameElement {

	public RedGreen(Point2D position, int layer) {
		super(position, layer);
	}

	@Override
	public String getName() {
		return "RedGreen";
	}

	@Override
	public boolean isPassable() {
		return false;
	}
		

}
