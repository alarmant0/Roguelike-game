package pt.iscte.poo.example;

import pt.iscte.poo.utils.Point2D;

public abstract class Obtainable extends GameElement {

	public Obtainable(Point2D position, int layer) {
		super(position, layer);
	}

	public void drop() {
		if (this.getName().equals("HealingPotion")) {
			((Usable)this).use();
		}
		else {
			Engine.getInstance().getHero().removeFromObs(this);
			super.changePosition(Engine.getInstance().getHero().getPosition());
			super.changeLayer(Engine.getInstance().getRoomNumber());
		}
	}
	
}
