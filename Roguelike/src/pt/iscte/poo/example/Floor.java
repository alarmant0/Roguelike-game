package pt.iscte.poo.example;

import pt.iscte.poo.utils.Point2D;

public class Floor extends GameElement {

	public Floor(Point2D position, int layer) {
		super(position, layer);
	}

	@Override
	public boolean isPassable() {
		return true;
	}
	
	@Override
	public String getName() {
		return "Floor";
	}

}