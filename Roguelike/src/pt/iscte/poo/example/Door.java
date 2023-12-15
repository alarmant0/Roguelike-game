package pt.iscte.poo.example;

import java.util.ArrayList;
import java.util.List;

import pt.iscte.poo.utils.Point2D;

public class Door extends GameElement {

	private static final List<Door> opened = new ArrayList<Door>();
	private Point2D init;
	private String key;
	private boolean isStair;
	private String room;
	private boolean isOpen;
	
	public Door(Point2D position, String room, Point2D init, String key, int layer) {
		super(position, layer);
		this.init = init;
		this.key = key;
		this.room = room;
		this.isOpen = false;
	}
	
	public Door(Point2D position, String room, Point2D init, int layer) {
		super(position, layer);
		this.room = room;
		this.init = init;
		this.isStair = true;
	}

	//----------- GETTERS
	
	public boolean getisStair() {
		return this.isStair;
	}
	
	public boolean getIsOpen() {
		return this.isOpen;
	}
	
	public Character getRoom() {
		return this.room.charAt(this.room.length()-1);
	}
	
	public Point2D getInit() {
		return this.init;
	}
	
	public String getKey() {
		if (this.key == null) return "IS NULL";
		return this.key;
	}
	
	public boolean canBeOpen(Key key) {
		return key.getName().equals(this.key);
	}
	
	public boolean isSameDoor(Door d) { 
		return ((d.getKey().equals(this.getKey())) && (Character.getNumericValue(d.getRoom()) == this.getLayer())); 
	}
	
	@Override
	public boolean isPassable() {
		return false;
	}
	
	@Override // ImageTile
	public String getName() {
		if(isStair) {
			if (Character.getNumericValue(this.getRoom()) < Engine.getInstance().getRoomNumber()) return "StairsUp";
			return "StairsDown";
		}
		else {
			for(Door d : opened) {
				if (isSameDoor(d) || this.getIsOpen()) {
					return "DoorOpen";
				}
			}
			return "DoorClosed";
		}
	}
	
	//------------ SETTERS & ADDERS

	
	public void addDoorOpened(Door d) {
		opened.add(d);
	}
	
	public void setOpenDoor() {
		this.isOpen = true;
	}
	
	public void OpenDoor() {
		this.isStair = true;
	}
	
	
	//-------- CHANGE ROOM
	
	public static void switchRoom(GameElement e) {
		Engine en = Engine.getInstance();
		Door a = null; 
		for(GameElement d : en.getGES()) {
			if (e == d) {
				a = (Door) d;
			}
		}
		en.getGUI().clearImages();
		for(GameElement k : en.getHero().getHotBar()) {
			en.removeGES(k);
		}
		en.setRoomNumber(Character.getNumericValue(a.getRoom()));
		en.getHero().changeLayer(Character.getNumericValue(a.getRoom()));
		en.getHero().changePosition(a.getInit());
		en.createLevel();
		en.addGES(en.getHero().getHotBar());
	}
	
	public static void tryOpenDoor(GameElement e) { // Door
		Door door = null; 
		Engine en = Engine.getInstance();
		for(GameElement d : en.getGES()) {
			if (e == d) { 
				door = (Door) d;
			}
		}
		if (en.getHero().getHotBarSize() == 0) return;
		for(int i = 0;i < en.getHero().getHotBar().size();i++) {
			if (en.getHero().getHotBar().get(i).getName().equals("Key")) {
				Key key = (Key) en.getHero().getHotBar().get(i);
				if (key.getKeyId().equals(door.getKey())) {
					en.getGUI().clearImages();
					for(GameElement k : en.getHero().getHotBar()) {
						en.removeGES(k);
					}
					door.addDoorOpened(door);
					door.setOpenDoor();
					key.use();
					en.getHero().organizeHotBar();
					en.setRoomNumber(Character.getNumericValue(door.getRoom()));
					en.getHero().changeLayer(Character.getNumericValue(door.getRoom()));
					en.createLevel();
					en.getHero().changePosition(door.getInit());
					en.addGES(en.getHero().getHotBar());
				}
			}
		}
	}
	
	public static void doSomethingWithDoor(GameElement e) {
		if (e.getName().equals("DoorClosed")) {
			tryOpenDoor(e);
		}
		else {
			switchRoom(e);
		}
	}
	
}
