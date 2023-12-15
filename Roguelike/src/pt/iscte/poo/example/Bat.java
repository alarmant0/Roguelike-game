package pt.iscte.poo.example;

import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class Bat extends Mobs {

	public final static int HEALTH = 3;
	public final static int ATACK = 1;
	public final static int POINTS = 50;
	
	public Bat(Point2D position, int layer) {
		super(position, layer, HEALTH, ATACK, POINTS);
	}
	
	@Override // ImageTile
	public String getName() {
		return "Bat";
	}

	@Override // Mobs
	public void move(Direction dic) {
		if (((int) (Math.random() * 2)) == 1) {
			super.addPosition(dic.asVector());
		}
		else {
			dic = Direction.random();
			if (GameElement.giveGE(getPosition(), dic.asVector()) == null) 
				super.addPosition(dic.asVector());
		}
	}

	@Override
	public boolean isPassable() {
		return false;
	}

	@Override
	public boolean canMove() {
		Engine en = Engine.getInstance();
		return this.getLayer() == en.getRoomNumber();
	}

	@Override
	public void atack(Mobs m) {
		if ((int) (Math.random() * 2) == 1) {
			if (!Engine.getInstance().getHero().hasArmor()) {
				m.lossHealthPoints(ATACK);
				if (this.getHealthPoints() != 3) super.lossHealthPoints(-1);
			}
			else {
				m.lossHealthPoints(ATACK);
				if (this.getHealthPoints() != 3) super.lossHealthPoints(-1);
			}
		}
	}

	@Override
	public boolean canAtack() {
		return canMove();
	}

}
