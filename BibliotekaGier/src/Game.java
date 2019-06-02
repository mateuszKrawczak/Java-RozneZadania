import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.JOptionPane;


/*
 * Program: Klasa posiadaj¹ca obiekty i operacje na nich
 *          
 *    Plik: Game.java
 *          
 *   Autor: Mateusz Krawczak
 *    Data: 05.10.2018 r.
 *
 */


enum TypeOfTheGame {
	NONE("---"),
	OTHER("Inny"),
	RAILLY("Wyœcigowa"), 
	SPORT("Sportowa"), 
	ADVENTURE("Przygodowa"), 
	AGILITY("Zrêcznoœciowa"), 
	STRATEGIC("Strategia");
	
	String type;

	private  TypeOfTheGame(String Type ) {
		type=Type;
		
	}
	public String toString() {
		return type;
	}
}
enum PegiOfTheGame {
	
	THREE("3"),
	SEVEN("7"),
	TWELVE("12"), 
	SIXTEEN("16"), 
	EIGHTEEN("18");
	
	String pegi;

	private  PegiOfTheGame(String pegi ) {
		this.pegi=pegi;
		
	}
	public String toString() {
		return pegi;
	}
}
class GameException extends Exception  {

	private static final long serialVersionUID = 1L;

	public GameException(String message) {
		super(message);
	}
}



public class Game implements Comparable<Game>{
	
	private  String name;
	private  PegiOfTheGame pegi;
	private  float price;
	private   TypeOfTheGame type;
 
	
	public Game(String name) throws GameException {
		setName(name);
		pegi=PegiOfTheGame.THREE;
		type=TypeOfTheGame.NONE;
	}

	
	public  float getPrice() {
		return price;
		
	}

	public String getName() {
		
		return name;
	}
	
	
	public PegiOfTheGame getPegi() {
		
		return pegi;
	
	}
	public  TypeOfTheGame getType() throws NullPointerException{
		
		return type;

	
	}
	
	

	
	@Override
	public String toString() {  
		return name+","+price+","+pegi+","+type;
	}

	public static void saving(Game g,PrintWriter save1,GroupOfGames games) throws FileNotFoundException {
		
		
	
		save1.println(g.name);
		
		save1.println(g.price);
		
		save1.println(g.pegi);
		
		save1.println(g.type);
		
		//JOptionPane.showMessageDialog(null, "Zapisano pomyslnie!");
		

	}
	
	public static void saving(Game g,File file) throws FileNotFoundException {
		
		PrintWriter save=new PrintWriter(file);
		
		save.println(g.getName());
		
		save.println(g.getPrice());
		
		save.println(g.getPegi());
		
		save.println(g.getType());
		
		JOptionPane.showMessageDialog(null, "Zapisano pomyslnie!");
		
		save.close();
	}
	public void setName(String name1) throws GameException{
		
		if ((name1 == null) || name1.equals(""))
			throw new GameException("Pole [Nazwa] musi byæ wype³nione.");
		this.name=name1;
	}
	public  void setPrice(float f) throws GameException{
		
		
		while(true) {
		if(f < 0 || f > 500) {
			
			throw new GameException("Cena musi byæ w przedziale 0-500");
			
		}else {break;}
		}
		this.price= f;
	}
	public  void setPrice(String price) throws GameException {
		
		try { 
			setPrice(Float.parseFloat(price));
		} catch (NumberFormatException e) {
			throw new GameException("Cena musi byæ liczb¹ ca³kowit¹.");
		}
	}
	
	public  void setPegi(PegiOfTheGame pegiOfTheGame) {
		
			
	this.pegi=pegiOfTheGame;
				
	}
	public  void setPegi(String pegiS) {
		while(true) {
			
			
			
			
			for(PegiOfTheGame pegi : PegiOfTheGame.values()){
				if (pegi.pegi.equals(pegiS)) {
					this.pegi = pegi;
					return;
				}
			}
		
		}
	}	
	public  void setType(String type1) throws NullPointerException
	{
		
		
		while(true) {
			
		
		
		for(TypeOfTheGame type : TypeOfTheGame.values()){
			if (type.type.equals(type1)) {
				this.type = type;
				return;
			}
			
			
		}
		}	
		
	}
	public void setType(TypeOfTheGame type) {
		this.type=type;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((pegi == null) ? 0 : pegi.hashCode());
		result = prime * result + Float.floatToIntBits(price);
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}


	public boolean equals(Game obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Game other = (Game) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (pegi != other.pegi)
			return false;
		if (Float.floatToIntBits(price) != Float.floatToIntBits(other.price))
			return false;
		if (type != other.type)
			return false;
		return true;
	}


	@Override
	public int compareTo(Game g) {
		int result = name.compareTo(g.name);
		       
		        return result;
	}
	
	static Game  loadingGroup(BufferedReader reader) throws IOException, GameException {
		Game game;
		String[] txt=new String[4];
		
		for(int i=0;i<4;i++) {
			String line=reader.readLine();
			txt[i]=line;
			System.out.println(line);
		}	if(txt[0]==null) {
			return null;
		}else {
			game=new Game(txt[0]);
			game.setName(txt[0]);
			
			game.setPrice(Float.parseFloat(txt[1]));
			
			game.setPegi(txt[2]);
			game.setType(txt[3]);
		return game;
		}
	
	}
}
