package pt.iscte.poo.example;

import pt.iscte.poo.utils.Point2D;

public class Red extends GameElement {

	public Red(Point2D position, int layer) {
		super(position, layer);
	}

	@Override
	public String getName() {
		return "Red";
	}

	@Override
	public boolean isPassable() {
		return false;
	}

}
