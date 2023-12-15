package pt.iscte.poo.example;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;
import pt.iscte.poo.utils.Vector2D;

public abstract class Mobs extends GameElement implements Movable {

	private int healthPoints;
	private int atackDamage;
	private int points;
	
	public Mobs(Point2D position, int layer, int healthPoints, int atackDamage, int points) {
		super(position, layer);
		this.atackDamage = atackDamage;
		this.healthPoints = healthPoints;
		this.points = points;
	}
	
	//----------- GETTERS
	
	public int getPoints() {
		return this.points;
	}
	
	public int getAtackDamage() {
		return this.atackDamage;
	}
	
	// Mobs
	public int getHealthPoints() {
		return this.healthPoints;
	}
	
	//--------- SETTERS & ADDERS
	
	// Mobs
	public void lossHealthPoints(int x) {
		this.healthPoints -= x;
	}
	
	public static void losesHealth(GameElement e) { 
		Engine en = Engine.getInstance();
		for(GameElement m : en.getGES()) 
			if (e == m) {
				((Mobs)m).lossHealthPoints(en.getHero().hasSwordAtack());
			}		
	}
	
	//----------- CHECK HEALTH
	
	public static void checkHealth() {
		Engine en = Engine.getInstance();
		Predicate<GameElement> p = e -> e instanceof Mobs && ((Mobs)e).getHealthPoints() <= 0 ;
		List<ImageTile> toRemove = new ArrayList<ImageTile>();
		for(GameElement e : en.getGES()) {
			if (e instanceof Mobs) {
				if(p.test(e)) {
					en.getScore().addScore(((Mobs)e).getPoints());
					toRemove.add(e);
				}
			}
		}
		en.getGES().removeIf(p);
		toRemove.forEach(x -> en.getGUI().removeImage(x));
		
		if (p.test(en.getHero())) {
			en.getScore().calculateScore();
			en.getGUI().setMessage("TOTAL SCORE: " + en.getScore().getScore());
			en.getGUI().dispose();
			return;
		}
	}
	
	//----------- Move Mobs
	
	public static void moveMobs() { 
		Engine en = Engine.getInstance();
		for(GameElement m : en.getGES()) {
			if (m instanceof Mobs && !m.getName().equals("Hero")) {
				Vector2D v = Vector2D.movementVector(m.getPosition(), en.getHero().getPosition());
				GameElement e = GameElement.giveGE(m.getPosition(), v); 
				if (e == null || e.isPassable()) {
					if (((Movable)m).canMove()) {
						 ((Movable) m).move(Direction.forVector(v));
					}
				}
				else {
					if (e.getName().equals("Hero")) {
						if (((Movable)m).canMove()) {
							((Movable) m).atack(en.getHero());
						}
					}
				}
			}
		}
	}
	
	public static void moveHero(int key) { 
		Engine en = Engine.getInstance();
		Direction dic = Direction.directionFor(key);
		GameElement e = GameElement.giveGE(en.getHero().getPosition(), dic.asVector());
		if ( e == null) {
			Point2D p = en.getHero().getPosition().plus(dic.asVector());
			if ((p.getY() >= 0 && p.getY() <= 9) && (p.getX() >= 0 && p.getX() <= 9)) {
				en.getHero().move(dic);
				en.addTurn();
			}
		}
		else {
			if (e.getLayer() != en.getRoomNumber()) {
				en.getHero().move(dic);
				en.addTurn();
			}
			else {
				GameElement.doSomething(e);
			}
		}
	}

}
