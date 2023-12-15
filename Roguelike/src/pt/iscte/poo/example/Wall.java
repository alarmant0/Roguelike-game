package pt.iscte.poo.example;

import pt.iscte.poo.utils.Point2D;

public class Wall extends GameElement {

	public Wall(Point2D position, int layer) {
		super(position, layer);
	}

	@Override
	public boolean isPassable() {
		return false;
	}
	
	@Override
	public String getName() {
		return "Wall";
	}
	
}
