package pt.iscte.poo.example;

import pt.iscte.poo.utils.Direction;

public interface Movable {
	
	public void move(Direction d);
	
	public void atack(Mobs m);
	
	public boolean canAtack();
	
	public boolean canMove();
	
}
