package pt.iscte.poo.example;
// SUB TESTE
import java.util.ArrayList;
import java.util.List;

import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class Thief extends Mobs {

	private final static int ATACK = 0;
	private final static int HEALTH = 5;
	public final static int POINTS = 150;

	private GameElement item = null;
	
	public Thief(Point2D position, int layer) {
		super(position, layer, HEALTH, ATACK, POINTS);
	}

	@Override
	public String getName() {
		return "Thief";
	}
	
	public GameElement getItem() {
		return this.item;
	}
	
	public void setItem(GameElement e) {
		this.item = e;
	}

	@Override
	public void move(Direction dic) {
		if (canAtack()) {
			super.addPosition(dic.asVector());
		}
		else {
			Direction op = dic.opposite();
			if (GameElement.giveGE(super.getPosition(), dic.asVector()) == null)
				super.addPosition(op.asVector());
		}
	}

	public void robItem() {
		Engine en = Engine.getInstance();
		if (en.getHero().getHotBarSize() != 0) {
			if (en.getHero().getHotBarSize() == 1) {
				GameElement e = en.getHero().getItem(0);
				en.getHero().removeFromObs(e);
				setItem(e);
				List<ImageTile> toRemove = new ArrayList<>();
				toRemove.add(e);
				en.getGUI().removeImages(toRemove);
				en.getHero().organizeHotBar();
			}
		}
	}
	
	@Override
	public void atack(Mobs m) { 
		if (canAtack()) {
			robItem();
		}
	}

	@Override
	public boolean canAtack() {
		return this.item == null;
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
	
}
