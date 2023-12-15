	package pt.iscte.poo.example;

import java.util.ArrayList;
import java.util.List;

import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class Hero extends Mobs {

	public final static int HEALTH = 10;
	public final static int ATACK = 1;
	private List<GameElement> hotbar = new ArrayList<>(3); // Criar classe hotbar??
	private boolean isPoisoned = false;
	
	public Hero(Point2D position, int layer) {
		super(position, layer, HEALTH, ATACK, 0);
	}
	
	//------------ GETTERS
	
	public int getAtack() {
		return ATACK;
	}
	
	public int getHealth() {
		return HEALTH;
	}
	
	public GameElement getItem(int index) {
		if (index > hotbar.size()) return null;
		GameElement e = this.hotbar.get(index);
		return e;
	}
	
	public boolean hasArmor() {
		for(GameElement o : getHotBar()) 
			if (o.getName().equals("Armor") && getHotBarSize() != 0) return true;
		return false;
	}
	
	@Override // ImageTile
	public String getName() {
		return "Hero";
	}
	
	public boolean getIsPoisoned() {
		return this.isPoisoned;
	}
	
	@Override
	public boolean canAtack() {
		return canMove();
	}
	
	public List<GameElement> getHotBar() {
		return this.hotbar;
	}
	
	public void removeObs() {
		this.hotbar = new ArrayList<>();
	}
	
	public int getHotBarSize() {
		return this.hotbar.size();
	}
	
	public int hasSwordAtack() {
		for(GameElement o : getHotBar()) 
			if (o.getName().equals("Sword") && getHotBarSize() != 0) return ATACK * 2;
		return ATACK;
	}
	
	@Override
	public boolean canMove() {
		return Engine.getInstance().getRoomNumber() == super.getLayer();
	}
	
	@Override // GameElement
	public boolean isPassable() {
		return false;
	}
	
	//---------- HOTBAR FUNCTIONS
	
	public void organizeHotBar() { 
		List<GameElement> acc = this.getHotBar();
		this.removeObs();
		for(GameElement e : acc) {
			addHotbar(e);
		}	
	}	
	
	public void showHotBar() { //
		List<ImageTile> tileList = new ArrayList<ImageTile>();
		for(GameElement e : this.getHotBar()) {
			tileList.add(e);
		}
		Engine.getInstance().getGUI().addImages(tileList);
	}
	
	public void addHotbar(GameElement o) { // 
		if (this.getHotBarSize() >= 3 || o == null) return;
		Engine.getInstance().getGUI().removeImage(o);
		o.changePosition(new Point2D(5 + ( this.getHotBarSize()* 2) , 10 ));
		this.addItem(o);
		Engine.getInstance().getGUI().addImage(o);
	}
	
	//---------- SETTERS & ADDERS
	
	public void setPoison(boolean b) {
		this.isPoisoned = b;
	}
	
	public void removeFromObs(GameElement e) {
		List<GameElement> toRemove = new ArrayList<GameElement>();
		for(GameElement a : hotbar) {
			if (e == a) {
				toRemove.add(a);
			}
		}
		hotbar.removeAll(toRemove);
	}
	
	public void addItem(GameElement o) {
		this.hotbar.add(o);
	}

	//----------- SHOW HEALTH
	
	public void showHealth() {
		Engine en = Engine.getInstance();
		int acc = 0;
		if (getIsPoisoned()) super.lossHealthPoints(1);
		int damageTaken = HEALTH - super.getHealthPoints();
		for(int i = 0;i<damageTaken;i++) {
			if (i % 2 != 0) {
				en.getGUI().addImage(new Red(new Point2D(acc, 10), 0));
				acc++;	
			}
			else {
				en.getGUI().addImage(new RedGreen(new Point2D(acc, 10), 0));
			}
		}
	}
	
	public void addHeroHealth() { // Hero
		List<ImageTile> tileList = new ArrayList<ImageTile>();
		for(int i = 0;i < 5; i++) {
			Green green = new Green(new Point2D(i, 10), 0);
			tileList.add(green);
		}
		Engine.getInstance().getGUI().addImages(tileList);
	}

	
	//--------- MOVE & ATACK
	
	@Override // Mobs
	public void atack(Mobs e) {
		e.lossHealthPoints(ATACK);
	}
	
	@Override // Mobs
	public void move(Direction dic) {
		super.addPosition(dic.asVector());
	}

	//----------- Drop
	
	public void dropItem(int index) {
		if (hotbar.size() <= index) return;
		GameElement e = this.hotbar.get(index);
		System.out.println(e.getPosition().getY());
		((Obtainable)e).drop();
//		if(e instanceof Obtainable && e.getName().equals("HealingPotion")) {
//			Obtainable o = (Obtainable) e;
//			o.use();
//			en.getGUI().clearImages();
//			en.createLevel();
//			showHealth();
//			setPoison(false);
//			Engine.getInstance().removeGES(e);
//			removeFromObs(e);
//			List<ImageTile> toRemove = new ArrayList<>();
//			toRemove.add(e);
//			en.getGUI().removeImages(toRemove);
//			organizeHotBar();
//			return;
//		}
//		else {
//			removeFromObs(e);
//			e.changePosition(this.getPosition());
//			e.changeLayer(Engine.getInstance().getRoomNumber());
//		}
		organizeHotBar();
	}
	
	//------------------ KeyController
	
	public void doSomethingWithKey(int key) {
		if (Direction.isDirection(key)) {
			Mobs.checkHealth();
			Mobs.moveHero(key);
			Mobs.moveMobs();
			this.showHealth();
			this.showHotBar();
			Mobs.checkHealth();
		}
		else {
			switch(key) {
			case 49:
				this.dropItem(0);
				break;
			case 50:
				this.dropItem(1);
				break;
			case 51:
				this.dropItem(2);
				break;
			}
		}
	}
		
}
