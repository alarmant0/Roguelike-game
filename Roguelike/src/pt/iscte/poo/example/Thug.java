package pt.iscte.poo.example;

import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class Thug extends Mobs {

	private final static int HEALTH = 5;
	private final static int ATACK = 3;
	private final static int POINTS = 200;
	
	public Thug(Point2D position, int layer) {
		super(position, layer, HEALTH, ATACK, POINTS);
	}

	@Override // Mobs
	public void atack(Mobs m) {
		if (!Engine.getInstance().getHero().hasArmor()) {
			int y = (int)(Math.random() * 10); // 0 <= x < 10
			if (y < 3) {
				m.lossHealthPoints(ATACK);
			}
		}
		else {
			if ((int)(Math.random() * 2) == 1) {
				int a = (int)(Math.random() * 10); // 0 <= x < 10
				if (a < 3) {
					m.lossHealthPoints(ATACK);
				}
			}
		}
	}
	
	@Override
	public String getName() {
		return "Thug";
	}

	@Override
	public void move(Direction dic) {
		super.addPosition(dic.asVector());
	}

	@Override
	public boolean canMove() {
		Engine en = Engine.getInstance();
		return this.getLayer() == en.getRoomNumber();
	}

	@Override
	public boolean isPassable() {
		return false;
	}

	@Override
	public boolean canAtack() {
		return canMove();
	}

}
