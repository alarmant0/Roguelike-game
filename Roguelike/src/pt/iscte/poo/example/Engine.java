package pt.iscte.poo.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import pt.iscte.poo.gui.ImageMatrixGUI;
import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.observer.Observed;
import pt.iscte.poo.observer.Observer;
import pt.iscte.poo.utils.Point2D;
import java.io.File;
import java.io.FileNotFoundException;

public class Engine implements Observer {            // GRUPO 101 

	//----------------------- ATRIBUTOS
	
	public static final int GRID_HEIGHT = 11;
	public static final int GRID_WIDTH = 10;
	public static final String ROOM_DIR = "rooms/";
	
	private static Engine INSTANCE = null;
	private ImageMatrixGUI gui = ImageMatrixGUI.getInstance();
	
	private Map<Integer, String> roomDB = new HashMap<Integer, String>();
	private Map<Integer, List<GameElement>> geOfRooms = new HashMap<Integer, List<GameElement>>(); // GAMEELEMENTS DE VARIOS ROOMS
	
	private Score score;
	private int roomNumber = 0;
	private Hero hero = new Hero(new Point2D(4,4), this.roomNumber);
	private int turns;

	//---------------------------- CONSTRUTOR
	
	private Engine() {		
		gui.registerObserver(this);
		gui.setSize(GRID_WIDTH, GRID_HEIGHT);
		gui.go();
	}
	
	//----------------- START
	
	public void start() {
		for(int i = 0;i<5;i++) {
			this.geOfRooms.put(i, new ArrayList<GameElement>());
		}
		String s = gui.askUser("Qual é o teu user: ");
		this.score = new Score(s);
		initImages();
		createLevel();
		gui.setStatusMessage("ROGUE Starter Package - Turns:" + turns + " - LEVEL: " + this.roomNumber + " - " + "HERO HP : " + hero.getHealthPoints());
		gui.update();
	}
	
	//------------------------------- GETTERS

	public Hero getHero() {
		return this.hero;
	}
	
	public Score getScore() {
		return this.score;
	}
	
	public static Engine getInstance() {
		if (INSTANCE == null) INSTANCE = new Engine();
		return INSTANCE;
	}
	
	public ImageMatrixGUI getGUI() {
		return this.gui;
	}
	
	public List<GameElement> getGES() {
		return this.geOfRooms.get(this.roomNumber);
	}
	
	public int getRoomNumber() {
		return this.roomNumber;
	}
	
	public int getTurns() {
		return this.turns;
	}

	
	//-------------- SETTERS AND ADDERS
	
	public void setRoomNumber(int x) {
		this.roomNumber = x;
	}
	
	public void addGES(List<GameElement> ls) { 
		List<ImageTile> tile = new ArrayList<ImageTile>();
		for(GameElement e : ls) {
			tile.add(e);
			geOfRooms.get(roomNumber).add(e);
		}
		gui.addImages(tile);
	}
	
	public void removeGES(GameElement e) { 
		List<GameElement> toRemove = new ArrayList<GameElement>();
		toRemove.add(e);
		this.geOfRooms.get(roomNumber).removeAll(toRemove);
	}
	
	public void addTurn() {
		this.turns++;
	}
	
	//----------------- ASSOCIATE ROOMS TO A NUMBER
	
		private void initImages() {
			int i = 0;
	        File dir = new File(ROOM_DIR);
	        for (File f : dir.listFiles()) {
	            assert (f.getName().lastIndexOf('.') != -1);
	            roomDB.put(i, ROOM_DIR + f.getName());
	            i++;          
	        }
	    }
	
	//---------------------------------------------    CREATE LEVEL
	
	public void createLevel() {
		if (this.geOfRooms.get(roomNumber).size() == 0) {
			addRoom(new File(roomDB.get(this.roomNumber)));
			addObjects(new File(roomDB.get(this.roomNumber)));
		}
		else {
			addRoom(new File(roomDB.get(this.roomNumber)));
			showLevel();
		}
		hero.addHeroHealth();
		Mobs.checkHealth();
	}
	
	public void showLevel() {
		if(this.geOfRooms.get(this.roomNumber).size() == 0) return;
		List<GameElement> ls = this.geOfRooms.get(this.roomNumber);
		List<ImageTile> tileList = new ArrayList<ImageTile>();
		for(GameElement e : ls) {
			tileList.add(e);
		}
		gui.addImages(tileList);
	}
	
	private void addRoom(File file) {
		List<GameElement> tileList = new ArrayList<GameElement>();
		List<ImageTile> floors = new ArrayList<ImageTile>();
		try {
        	Scanner scanner = new Scanner(file);
            for(int x = 0;x!=10;x++) {
            	String s = scanner.nextLine();
            	for(int y = 0;y!=10;y++) {
            		if(s.charAt(y) == '#') {
            			Wall wall = new Wall(new Point2D(y,x), this.roomNumber);
            			tileList.add(wall);
            		}
            		else {
            			Floor floor = new Floor(new Point2D(y,x), this.roomNumber);
            			floors.add(floor); // MUDAR
            		}
            	}
            }
            scanner.close();
		}	
		catch (FileNotFoundException e) {
			System.err.println("Erro na leitura do ficheiro!");
		}
		gui.addImages(floors);
		addGES(tileList);
	}
	
	private void addObjects(File file) {
		List<GameElement> tileList = new ArrayList<GameElement>();
		try {   
            Scanner scanner = new Scanner(new File(roomDB.get(this.roomNumber))); 
            while(scanner.hasNextLine()) {
                String s = scanner.nextLine();
                if (s.length() == 0 || s.charAt(0) == '#' || s.charAt(0) == ' ') {
                    continue;
                }
                else {
                    GameElement e = GameElement.createObject(s,this.roomNumber);
                    tileList.add(e);
                }
            }
            scanner.close();
        }
		catch (FileNotFoundException e) {
            System.err.println("Erro no ficheiro!");
        }
		tileList.add(hero);
		addGES(tileList);
	}

	//-------------------------- LOOP DO JOGO
	
	@Override
	public void update(Observed source) {
		if (ImageMatrixGUI.getInstance().wasWindowClosed())
			System.out.println("Ending Instance!");	
			
		int key = ((ImageMatrixGUI) source).keyPressed();
		hero.doSomethingWithKey(key);
		
		gui.setStatusMessage("ROGUE Starter Package - Turns:" + turns + " - LEVEL: " + this.roomNumber + " - " + "HERO HP : " + hero.getHealthPoints());
		gui.update();
	}
}