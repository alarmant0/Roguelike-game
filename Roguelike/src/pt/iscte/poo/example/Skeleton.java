package pt.iscte.poo.example;

import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class Skeleton extends Mobs {

	public final static int HEALTH = 5;
	public final static int ATACK = 1;
	public final static int POINTS = 100;

	private boolean canMove;
	
	public Skeleton(Point2D position, int layer) {
		super(position, layer, HEALTH, ATACK, POINTS);
		this.canMove = true;
	}
	
	// Skeleton
	public boolean canMoveTurn() {
		return this.canMove;
	}
	
	@Override // ImageTile
	public String getName() {
		return "Skeleton";
	}

	@Override // Mobs
	public void move(Direction dic) {
		if (canMove) {
			super.addPosition(dic.asVector());
		}
		this.canMove = !this.canMove;
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
		if (!Engine.getInstance().getHero().hasArmor()) {
			if (canAtack()) m.lossHealthPoints(ATACK);
		}
		else {
			if (((int) (Math.random() * 2)) == 1) {
				if (canAtack()) m.lossHealthPoints(ATACK);
			}
		}
		this.canMove = !this.canMove;
	}

	@Override
	public boolean canAtack() {
		return this.canMove;
	}

}
