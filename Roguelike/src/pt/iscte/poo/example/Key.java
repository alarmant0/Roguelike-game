package pt.iscte.poo.example;

import pt.iscte.poo.utils.Point2D;

public class Key extends Obtainable implements Usable {

	private String id;
	
	public Key(Point2D position, String id, int layer) {
		super(position, layer);
		this.id = id;
	}
	
	// Key
	public String getKeyId() {
		return this.id;
	}	
	
	@Override // ImageTile
	public String getName() {
		return "Key";
	}

	@Override // Obtainable
	public void use() {
		Engine en = Engine.getInstance();
		en.getHero().removeFromObs(this);
		en.removeGES(this);
		
	}

	@Override
	public boolean isPassable() {
		return true;
	}
	
}
