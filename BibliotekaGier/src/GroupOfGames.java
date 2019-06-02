import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;



import javax.swing.JOptionPane;



/*
 * Program: Aplikacja okienkowa z GUI, która umo¿liwia zarz¹dzanie
 *          grupami obiektów klasy Game.
 *    Plik: GroupOfGames.java
 *
 *   Autor: Mateusz Krawczak
 *    Data: 28.10.2018 r.
 */

enum GroupType {
	
	ARRAYLIST("Lista (klasa ArrayList)"),
	HASHSET("Zbiór(klasa HashSet)");
	

	String typeName;

	private GroupType(String tn) {
		typeName = tn;
	}

	@Override
	public String toString() {
		return typeName;
	}

	public static GroupType find(String tn){
		for(GroupType type : values()){
			if (type.typeName.equals(tn)){
				return type;
			}
		}
		return null;
	}

	public Collection<Game> createCollection() throws GameException {
		switch (this) {
		
		case ARRAYLIST:  return new ArrayList<Game>();
		case HASHSET:    return new HashSet<Game>();
		default:          throw new GameException("Nie ma takiego typu kolekcji.");
		}
	}
}  

public class GroupOfGames implements Serializable, Iterable<Game>{

	private static final long serialVersionUID = 1L;

	private String nameCollection;
	private GroupType typeCollection;
	private Collection<Game> collection;
	
	
	public GroupOfGames(GroupType type, String name) throws GameException {
		setName(name);
		if (type==null){
			throw new GameException("Nieprawid³owy typ kolekcji.");
		}
		typeCollection = type;
		collection = typeCollection.createCollection();
	}


	public GroupOfGames(String type_name, String name) throws GameException {
		setName(name);
		GroupType type = GroupType.find(type_name);
		if (type==null){
			throw new GameException("Nieprawid³owy typ kolekcji.");
		}
		typeCollection = type;
		collection = typeCollection.createCollection();
	}
	@Override
	public Iterator<Game> iterator() {
		// TODO Auto-generated method stub
		return collection.iterator();
	}
	
	public String getName() {
		return nameCollection;
	}

	public void setName(String name) throws GameException {
		if((name=="")||(name==null)) {
			throw new GameException("Nazwa grupy musi byc okreslona.");
		}
		nameCollection = name;
	}

	public GroupType getType() {
		return typeCollection;
	}

	public  void setType(GroupType type) throws GameException {
		if (type == null) {
			throw new GameException("Typ kolekcji musi byc okreslony.");
		}
		if (typeCollection == type) {
			return;
		}
		
		Collection<Game> oldCollection = collection;
		collection = type.createCollection();
		typeCollection = type;
		
		collection.addAll(oldCollection);
		
	}

	public Collection<Game> getCollection() {
		return collection;
	}

	public void setCollection(Collection<Game> collection) {
		this.collection = collection;
	}
	public void setType(String type_name) throws GameException {
		for(GroupType type : GroupType.values()){
			if (type.toString().equals(type_name)) {
				setType(type);
				return;
			}
		}
		throw new GameException("Nie ma takiego typu kolekcji.");
	}

	public String toString() {
		return nameCollection;
		
	}
	public void sortName() throws GameException {
		       if (typeCollection == GroupType.HASHSET ) {
		           
		    	   JOptionPane.showMessageDialog(null, "Nie mo¿na sortowac kolekcji SET", "Uwaga", 0);
		                } else {
		            Collections.sort((ArrayList<Game>)collection);
		            return;
		                }
		            }

	
	public void add(Game e) {
		 collection.add(e);
	}
	
	

	public int size() {
		return collection.size();
	}
	static void savingToFile(GroupOfGames g, File file) throws FileNotFoundException {
		PrintWriter save=new PrintWriter(file);
		save.println(g.getName());
		save.println(g.getType());
		for(Game game:g.collection) {
			Game.saving(game, save,g);
		}
		
		JOptionPane.showMessageDialog(null, "Zapisano pomyœlnie");
		save.close();
	}
	static GroupOfGames loadingFromFile(File file) throws IOException, GameException {
		try{
		BufferedReader fileReader = null;
		fileReader = new BufferedReader(new FileReader(file));
		String[] txt=new String[2];
		
		
		String line;
		
		for(int i=0;i<2;i++) {
			line=fileReader.readLine();
			
			
			
			txt[i]=line;
			System.out.println(line);
		}
		GroupOfGames games=new GroupOfGames(txt[1],txt[0]);
		games.setName(txt[0]);
		games.setType(txt[1]);
		
		Game game;
		
		while((game =Game.loadingGroup(fileReader)) != null) {
			games.collection.add(game);
		}
		System.out.println(games.getCollection());
		return games;
		//System.out.println(games.getCollection());
		}catch(FileNotFoundException e){
			JOptionPane.showMessageDialog(null, "Nie odnaleziono pliku " + file);
			return null;
		}
	
		}
	
		


}
