package pt.iscte.poo.example;

import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Point2D;
import pt.iscte.poo.utils.Vector2D;

public abstract class GameElement implements ImageTile {
	
	private Point2D position;
	private int layer;
	
	public GameElement(Point2D position, int layer) {
		this.layer = layer;
		this.position = position;
	}
	
	//---------- GETTERS

	
	@Override // ImageTile
	public Point2D getPosition() {
		return this.position;
	}
	
	@Override // ImageTile
	public int getLayer() {
		return this.layer;
	}
	
	//---------- SETTERS & ADDERS
	
		public void changePosition(Point2D p) {
			this.position = p;
		}
		
		// GameElement
		public void addPosition(Vector2D vec) {
			this.position = position.plus(vec);
		}
		
		// GameElement
		public void changeLayer(int x) {
			this.layer = x;
		}
	
	//----------- Create GameElement
	
	public static GameElement createObject(String line, int layer) {
		GameElement e = null;
		String [] parts = line.split(",");
		Point2D position = new Point2D(Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));
		switch (parts[0]) {
		case "#":
			Wall wall = new Wall(position, layer);
			return wall;
		case " ":
			Floor floor = new Floor(position, layer);
			return floor;
		case "Skeleton":
			Skeleton skelly = new Skeleton(position, layer);
			return skelly;
		case "Bat":
			Bat b = new Bat(position, layer);
			return b;
		case "Key":
			return new Key(position, parts[3], layer);
		case "Door":
			if (parts.length == 7) {
				Door door = new Door(position, parts[3], new Point2D(Integer.parseInt(parts[4]), Integer.parseInt(parts[5])), parts[6], layer);
				return door;
			}
        	else {
        		Door door = new Door(position, parts[3], new Point2D(Integer.parseInt(parts[4]), Integer.parseInt(parts[5])), layer);
        		return door;
        	}	
		case "Sword":
			return new Sword(position, layer);
		case "HealingPotion":
			HealingPotion pot = new HealingPotion(position, layer);
			return pot;
		case "Thug":
			Thug bully = new Thug(position, layer);
			return bully;
		case "Armor":
			Armor armor = new Armor(position, layer);
			return armor;
		case "Treasure":
			Treasure t = new Treasure(position, layer);
			return t;
		case "Scorpio":
			Scorpio scorpio = new Scorpio(position, layer);
			return scorpio;
		case "Thief":
			Thief thief = new Thief(position, layer);
			return thief;
		}
		return e;
	}
	
	//------------- DoSomething & Utils
	
	public static void doSomething(GameElement e) { 
		Engine en = Engine.getInstance();
		if (e instanceof Mobs) {
			Mobs.losesHealth(e);
			return;
		}
		if (e instanceof Door) {
			Door.doSomethingWithDoor(e);
			return;
		}
		if (e instanceof Obtainable) {
			en.getHero().addHotbar(e);
			return;
		}
	}
	
	public static GameElement giveGE(Point2D position, Vector2D vec) {
		Engine en = Engine.getInstance();
		for(GameElement g : en.getGES()) {
			if (samePosition(g.getPosition(), position, vec) && g.getLayer() == en.getRoomNumber()) return g;
		}
		return null;
	}
	
	public static boolean samePosition(Point2D destination ,Point2D from, Vector2D vec) {
		return (int)from.plus(vec).distanceTo(destination) == 0;
	}
	
	//---------------Abstract Functions
	
	public abstract boolean isPassable();	
	
}
