package pt.iscte.poo.example;

import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class Scorpio extends Mobs {

	public final static int HEALTH = 2;
	public final static int ATACK = 1;
	public final static int POINTS = 150;
	
	public Scorpio(Point2D position, int layer) {
		super(position, layer, HEALTH, ATACK, POINTS);
	}

	@Override
	public String getName() {
		return "Scorpio";
	}

	@Override
	public void move(Direction dic) {
		super.addPosition(dic.asVector());
	}

	@Override
	public void atack(Mobs m) {
		Engine.getInstance().getHero().setPoison(true);
	}

	@Override
	public boolean canAtack() {
		return canMove();
	}

	@Override
	public boolean canMove() {
		return super.getLayer() == Engine.getInstance().getRoomNumber();
	}

	@Override
	public boolean isPassable() {
		return false;
	}

}
