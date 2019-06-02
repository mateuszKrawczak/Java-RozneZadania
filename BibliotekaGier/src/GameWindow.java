import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableModel;


/*
 * Program: Klasa posiada metode main,a takze wszystkie komponenty g³ownego okna aplikacji
 *          Zapisywanie i odczyt binarny nie dzia³a.
 *    Plik: GameWindow.java
 *          
 *   Autor: Mateusz Krawczak
 *    Data: 03.listopad.2018 r.
 *
 */
public class GameWindow extends JDialog implements ActionListener {
	


	ArrayList<Game> list= new ArrayList<Game>();
	ArrayList<Integer> list1= new ArrayList<Integer>();
	private tableOfGames currentGames;
	public static void main(String[] args) throws GameException, ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
		GroupOfGames games1=new GroupOfGames(GroupType.ARRAYLIST, "Test");
	new GameWindow(null,games1);
	
	}
	JScrollPane jScrollPane1 = new JScrollPane();
    JTable jTable1 = new JTable();
    
     

    

	private static final long serialVersionUID = 1L;
	JPanel panel=new JPanel();
	JMenuBar bar=new JMenuBar();

	
	private Game cgame;
	
	static JComboBox<GroupType> typeGroupBox = new JComboBox<GroupType>(GroupType.values());
	
	JMenu file1= new JMenu("Program"); 
	JMenu save= new JMenu("Zapisz");
	JMenu load= new JMenu("Wczytaj"); 
	JMenu options= new JMenu("Opcje");
	
	//JMenuItem zb=new JMenuItem("Binarnie");
	JMenuItem zn=new JMenuItem("Do pliku");
	//JMenuItem wb=new JMenuItem("Binarnie");
	JMenuItem wn=new JMenuItem("Z pliku");
	JMenuItem exit=new JMenuItem("Wyjœcie");
	JMenuItem info=new JMenuItem("O programie");
	JMenuItem typeCollection=new JMenuItem("Zmien typ kolekcji");
	JMenuItem nameGroup=new JMenuItem("Zmien nazwe grupy");
	
	
	JButton b1=new JButton("Nowa gra");
	JButton b2=new JButton("Edytuj gre");
	JButton b3=new JButton("Usun gre");
	JButton b4=new JButton("Usun wybran¹ grê z kolekcji");
	JButton b5=new JButton("Edytuj wybran¹ grê z kolekcji");
	JButton b6=new JButton("Sortuj gry wed³ug nazwy");
	
	JTextField tname=new JTextField(10);
	JTextField tpegi=new JTextField(10);
	JTextField tprice=new JTextField(10);
	JTextField ttype=new JTextField(10);
	JTextField ctype=new JTextField(15);
	JTextField gname=new JTextField(15);
	
	 static JLabel lname=new JLabel("Nazwa: ");
	 static JLabel lprice=new JLabel("    Cena: ");
	 static JLabel lpegi=new JLabel("    Pegi: ");
	 static JLabel ltype=new JLabel("      Typ: ");
	 JLabel space=new JLabel("\n                                                                                       ");
	 JLabel collectiontype=new JLabel("              Typ kolekcji: ");
	 JLabel nameOfTheGroup=new JLabel("                     Nazwa grupy: ");
	private GroupOfGames games;
	
	
	public GameWindow(Window parent, GroupOfGames games1) throws GameException {
		super(parent, Dialog.ModalityType.DOCUMENT_MODAL);
		setTitle("Game");  
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(400, 600);
		setResizable(false);
		setJMenuBar(bar);
		
		
		
		
		tname.setEditable(false);
		tprice.setEditable(false);
		tpegi.setEditable(false);
		ttype.setEditable(false);
		ctype.setEditable(false);
		gname.setEditable(false);
		
		gname.setText(games1.getName());
		
		ctype.setText(String.valueOf(games1.getType()));
		
		b1.addActionListener(this);
		b2.addActionListener(this);
		b3.addActionListener(this);
		b4.addActionListener(this);
		b5.addActionListener(this);
		b6.addActionListener(this);
		info.addActionListener(this);
		exit.addActionListener(this);
		wn.addActionListener(this);
		zn.addActionListener(this);
		typeCollection.addActionListener(this);
		nameGroup.addActionListener(this);
		
		


		add(panel);
		options.add(nameGroup);
		options.add(typeCollection);
		
		file1.add(info);
		file1.add(exit);
		
		setLayout(new FlowLayout());
		
		
		
		bar.add(file1);
		bar.add(save);
		bar.add(load);
		bar.add(options);
		save.add(zn);
		load.add(wn);
	 
		panel.setBorder(BorderFactory.createTitledBorder("Bie¿¹ca gra i kolekcja:"));
		
	
		panel.add(b1); 	
		panel.add(space); 	
		//panel.add(b2);
		//panel.add(b3);
		panel.add(lname);
		panel.add(tname);
		panel.add(lpegi);
		panel.add(tpegi);
		panel.add(lprice);
		panel.add(tprice);
		panel.add(ltype);
		panel.add(ttype);
		panel.add(collectiontype);
		panel.add(ctype);
		panel.add(nameOfTheGroup);
		panel.add(gname);
		games=games1;
		currentGames = new tableOfGames(games, 400, 250);
		currentGames.refreshView();
		panel.add(currentGames);	
		setContentPane(panel);
		panel.add(b4);
		panel.add(b5);
		panel.add(b6);
		
		showCurrentGame();
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	//	pack();
		setVisible(true);
	}

	
	void showCurrentGame() {
		if (cgame == null) {
			tname.setText("");
			tprice.setText("");
			tpegi.setText("");
			ttype.setText("");
		} else {
			tname.setText(cgame.getName());
			tprice.setText(String.valueOf(cgame.getPrice()));
			tpegi.setText("" + cgame.getPegi());
			ttype.setText("" + cgame.getType());
		}
	}

	public static GroupType collectionChoice(Window parent) throws GameException {
		
		
		GroupType ctypee = null;
		
	       ctypee= (GroupType) JOptionPane.showInputDialog(null,
	       "Wybierz rodzaj kolekcji: ",
	        "Wybór kolekcji",
	       JOptionPane.QUESTION_MESSAGE, 
	       
	      null, GroupType.values(), typeGroupBox);
	     
	     if (ctypee == null ) {
			    return null;
			  }else {
		
			return ctypee;
			                 }
	    
	 //     System.out.println(games.getType());
	      
	}
	static String renameGroup(Window parent) throws GameException{
		String nameg = null;
		
		
		 nameg= JOptionPane.showInputDialog(null,
			        "Podaj now¹ nazwê grupy: ",
			        "Wybór kolekcji",
			        JOptionPane.QUESTION_MESSAGE);
			
		
		 if (nameg == null || nameg.equals("")) {
			      return null;
		    }else {
		
			return nameg;
			                 }
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object evt = e.getSource();

	
			if (evt == exit) {
			System.exit(0);
			}
			else if(evt==zn) {
			//	if(cgame==null) {
			//		JOptionPane.showMessageDialog(this, "Aktualnie nie ma gry w bazie");
			//	}else {
			//	try {
			//		savingFile();
			//	} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
			//		e1.printStackTrace();
			//	}
			//	}
				int index=currentGames.getSelectedIndex();
				Game g=null;
				if (index >= 0) {
						Iterator<Game> ite1 = games.iterator();
				while (index-- >= 0)  {
				 g=ite1.next();
					 }
				
				
				if(g==null) {
							JOptionPane.showMessageDialog(this, "Aktualnie nie ma gry w bazie");
						}else {
							try {
										savingFile(g);
									} catch (FileNotFoundException e1) {
										try {
											throw new GameException("b³¹d");
										} catch (GameException e2) {
											// TODO Auto-generated catch block
											e2.printStackTrace();
										}
										e1.printStackTrace();
									}catch(NullPointerException e2) {
										JOptionPane.showMessageDialog(null, "Nie wybrano folderu","B³¹d",JOptionPane.ERROR_MESSAGE);
									}
				}
			}
		}
			else if(evt==typeCollection) {
				GroupType ctypee;
				try {
					ctypee=collectionChoice(this);
					if (ctypee == null) {
						JOptionPane.showMessageDialog(null, "Nie wybrano nowego typu kolekcji");
						   return;
					}
					games.setType((GroupType) ctypee);
				} catch (GameException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			
				ctype.setText("" +games.getType());
				        
				       
			}
			
			else if (evt == nameGroup) {
				String nameg ;
				
					try {
						nameg=renameGroup(this);
						if (nameg == null) {
							JOptionPane.showMessageDialog(null, "Nie podano nowej nazwy grupy");
							   return;
						}
						games.setName(nameg);
					} catch (GameException e2) {
						// TODO Auto-generated catch block
						JOptionPane.showMessageDialog(null, "B³¹d");
						e2.printStackTrace();
					}
				
					
			
				   
				 gname.setText(games.getName());
				
				
			}
			else if (evt == wn) {
				try {
					readingFile();
				} catch(NullPointerException e2) {
					JOptionPane.showMessageDialog(null, "Nie wybrano folderu","B³¹d",JOptionPane.ERROR_MESSAGE);
				
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (GameException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				addingGame(cgame);
			}
			else if (evt == info) {
				JOptionPane.showMessageDialog(this, getWelcome());
			}

			else if (evt == b1) { 
				
					cgame = GameDialog.createGame(this);
					addingGame(cgame);
				
		} 
			else if (evt== b2) {
				if (cgame == null) {
					JOptionPane.showMessageDialog(this, "Aktualnie nie ma gry w bazie");
					} else {
				GameDialog.editGame(this, cgame);
				
					}
			}
			else if (evt == b3) {
				if (cgame == null) {
					JOptionPane.showMessageDialog(this, "Aktualnie nie ma gry w bazie");
					} else {
				cgame = null;
					}
			}else if (evt == b5) {
				
				int index=currentGames.getSelectedIndex();
				
				 Game g = null;
					 if (index >= 0) {
							Iterator<Game> ite1 = games.iterator();
					while (index-- >= 0)  {
					 g=(Game) ite1.next();
						 }
					 
					GameDialog.editGame(this, g);
				
				
				}
			}
			else if (evt == b6) {
				
				try {
					games.sortName();
				} catch (GameException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				
				
			}
				else if (evt == b4) {
					int index=currentGames.getSelectedIndex();
					
				if (index >= 0) {
						Iterator<Game> iter = games.iterator();
				while (index-- >= 0)  {
				 iter.next();
					 }
					iter.remove();
				 }
					
					
		}
			
		
		showCurrentGame();
		
		System.out.println(games.getCollection());
	currentGames.refreshView();
	}
	
	boolean hashCheck() {
		int actualHash =cgame.hashCode();
		for(int searchHash:list1) {
			if(actualHash==searchHash) {
				JOptionPane.showMessageDialog(this, "Gra ju¿ jest w bazie", "B³¹d", JOptionPane.ERROR_MESSAGE);
				return false;
			}
			
		}
		return true;
		
		
	}
	void addingGame(Game g) {
		if(g!=null) {
		if(hashCheck()==true ){
			
		
				
				games.add(g);
				list1.add(g.hashCode());
				
			System.out.println(games.getCollection());
			System.out.println(cgame.hashCode());
			}
		}
	}
	
	public void savingFile(Game g) throws FileNotFoundException {

		JFileChooser chooser=new JFileChooser();
		chooser.showOpenDialog(this);
		chooser.setCurrentDirectory(new File("."));
		File file=chooser.getSelectedFile();
		Game.saving(g,file);
		}
		public void readingFile() throws IOException, GameException {
			
		JFileChooser chooser=new JFileChooser();
		chooser.showOpenDialog(this);
		chooser.setCurrentDirectory(new File("."));
		File file=chooser.getSelectedFile();
		loading(file);
		}
	String welcome="Program- Game\n"
			+"Autor: Mateusz Krawczak\n"
			+ "Indeks: 241318\n"
			+ "------------------\n\n\n\n\n";
	public  String getWelcome() {
		return welcome;
	}
	public  void setWelcome(String welcome) {
		this.welcome = welcome;
	}
	class tableOfGames extends JScrollPane {
		private static final long serialVersionUID = 1L;

		private GroupOfGames group;
			
			private JTable table;
			private DefaultTableModel tableModel;

			public tableOfGames(GroupOfGames games, int width, int height){
				group = games;
				
				setPreferredSize(new Dimension(width, height));
				setBorder(BorderFactory.createTitledBorder("Lista gier:"));

				String[] tableHeader = { "Nazwa", "Cena", "Pegi","Rodzaj gry" };
				tableModel = new DefaultTableModel(tableHeader,0);
				table = new JTable(tableModel) {

					private static final long serialVersionUID = 1L;

					@Override
					public boolean isCellEditable(int rowIndex, int colIndex) {
						return false; 
					}
				};
				table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
				table.setRowSelectionAllowed(true);
				setViewportView(table);
				
			}
			
			void refreshView(){
				
				tableModel.setRowCount(0);
				for (Game game : games) {
					if (game != null) {
						String[] row = { game.getName(), String.valueOf(game.getPrice()), game.getPegi().toString(),game.getType().toString() };
						tableModel.addRow(row);
				
							}
				}
				
			}

			int getSelectedIndex(){
				int index = table.getSelectedRow();
				if (index<0) {
					JOptionPane.showMessageDialog(this, "¯adna gra nie jest zaznaczona.", "B³¹d", JOptionPane.ERROR_MESSAGE);
				}
				return index;
			}
		}
	public   void loading( File file) throws IOException, GameException{
		BufferedReader fileReader = null;
		
		try{
			
			fileReader = new BufferedReader(new FileReader(file));
			String[] txt=new String[4];
			for(int i=0;i<4;i++) {
				String line=fileReader.readLine();
				txt[i]=line;
				System.out.println(line);
			}
				cgame=new Game(txt[0]);
				cgame.setName(txt[0]);
				
				cgame.setPrice(Float.parseFloat(txt[1]));
				
				cgame.setPegi(txt[2]);
				cgame.setType(txt[3]);
			
				
			fileReader.close();		
		}catch(FileNotFoundException e){
			JOptionPane.showMessageDialog(null, "Nie odnaleziono pliku " + file);
			
		}
		
		
	}
	public static GroupOfGames createNewGroup(Window parent) throws GameException {
		String newname=renameGroup(parent);
		while(newname == null||newname == "") {
			JOptionPane.showMessageDialog(null, "Nie podano nazwy grupy, spróbuj jeszcze raz");
			newname =renameGroup(parent);
		}
		GroupType newtype=collectionChoice(parent);
		while(newtype == null) {
			JOptionPane.showMessageDialog(null, "Nie wybrano nowego typu kolekcji, spróbuj jeszcze raz");
			newtype =collectionChoice(parent);
		}
		
		 GroupOfGames new_group;
		            new_group = new GroupOfGames(newtype,newname);
		               
		GameWindow dialog = new GameWindow(parent,new_group);
		       return dialog.games;
	}


	public static void editGroup(Window parent, GroupOfGames g) throws GameException {
		new GameWindow(parent, g);
		
	}
} 
