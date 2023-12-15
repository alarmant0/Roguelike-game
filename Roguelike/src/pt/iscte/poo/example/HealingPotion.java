package pt.iscte.poo.example;

import java.util.ArrayList;
import java.util.List;

import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Point2D;

public class HealingPotion extends Obtainable implements Usable {

	public HealingPotion(Point2D position, int layer) {
		super(position, layer);
	}

	@Override
	public String getName() {
		return "HealingPotion";
	}

	@Override
	public void use() {
		Engine en = Engine.getInstance();
		en.getHero().setPoison(false);
		if (en.getHero().getHealthPoints() > 5) {
			en.getHero().lossHealthPoints(-(en.getHero().getAtack() - en.getHero().getHealthPoints()));
		}
		else {
			en.getHero().lossHealthPoints(-5);
		}
		en.getHero().addHeroHealth();
		en.getHero().showHealth();
		en.getHero().removeFromObs(this);
		en.removeGES(this);
		List<ImageTile> toRemove = new ArrayList<>();
		toRemove.add(this);
		en.getGUI().removeImages(toRemove);
	}

	@Override
	public boolean isPassable() {
		return true;
	}

}
