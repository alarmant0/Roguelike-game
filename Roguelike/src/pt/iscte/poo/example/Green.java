package pt.iscte.poo.example;

import pt.iscte.poo.utils.Point2D;

public class Green extends GameElement {

	public Green(Point2D position, int layer) {
		super(position, layer);
	}

	@Override // ImageTile
	public String getName() {
		return "Green";
	}

	@Override
	public boolean isPassable() {
		return false;
	}
	
}
