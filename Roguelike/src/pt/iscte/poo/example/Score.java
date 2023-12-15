package pt.iscte.poo.example;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Score { // USAR COMPARABLE
	
	private final static String LOCATION = "./scores/scores.txt";
	
	private boolean won = false;
	private int score = 1000;
	private String nome;
	private long startTime = System.currentTimeMillis();
	
	public Score(String nome) {
		this.nome = nome;
	}
	
	public Score(String nome, int score) {
		this.score = score;
		this.nome = nome;
	}
	
	//------------ GETTERS
	
	public String getNome() {
		return this.nome;
	}
	
	public int getScore() {
		return this.score;
	}
	
	//------- SETTERS & ADDERS
	public void changeName(String nome) {
		this.nome = nome;
	}
	
	public void addScore(int points) {
		this.score += points;
	}
	
	public void setWon() {
		this.won = true;
	}
	
	//--------- Exporters & Importers
	
	public void exporter(List<Score> scores, boolean b) {
		try {
			if (b) {
				PrintWriter writer = new PrintWriter(new File(LOCATION));
				for(Score s : scores) {
					writer.println(s);
				}
				writer.close();
			}
			else {
				PrintWriter writer = new PrintWriter(new File(LOCATION));
				int i = 0;
				for(Score s : scores) {
					if (i == 5) {
						writer.close();
						return;
					}
					writer.println(s);
					i++;
				}
				writer.close();
			}
		}
		catch(Exception e) {
			System.out.println("ERRO NO EXPORTER");
		}
	}
	
	public List<Score> importer() {
		List<Score> scores = new ArrayList<Score>();
		try {
			Scanner scanner = new Scanner(new File(LOCATION));
			while(scanner.hasNextLine()) {
				String line = scanner.nextLine();
				String [] components = line.split(":");
				String nome = components[0];
				int score = Integer.parseInt(components[1]);
				Score s = new Score(nome, score);
				scores.add(s);
			}
			scanner.close();
		}
		catch (Exception e) {
			System.err.println("ERRO NO IMPORTER");
		}
		return scores;
	}
	
	public void comparador(List<Score> scores) {
		ComparadorDeScores comp = new ComparadorDeScores();
		scores.sort(comp);
	}
	
	//------------ CALCULATE
	 
	public int calculateTimeUsed(int time) {
		if (won) {
			if (time > 180) {
				return 100;
			}
			if (time > 120) {
				return 300;
			}
			if (time > 60) {
				return 500;
			}
			return 700;
		}
		return 0;
	}
	
	
	public int calculateScore() { // USAR CONSUMER
		final long endTime = System.currentTimeMillis();
		int time = (int)(endTime - startTime) * 1/1000;
		int scoreTime = calculateTimeUsed(time);
		addScore(scoreTime - Engine.getInstance().getTurns());
		List<Score> scores = importer();
		scores.add(this);
		comparador(scores);
		if(scores.size() > 5) {
			exporter(scores, false);
		}
		else {
			exporter(scores, true);
		}
		return score;
	}
	
	//----------- TOSTRING
	
	@Override
	public String toString() {
		return this.nome + ":" + this.score; 
	}
}
