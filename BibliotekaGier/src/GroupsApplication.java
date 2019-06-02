import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

/*
 * Program: Aplikacja, która pozwala na zarz¹dzanie kolekcjami obiektów Game
 *    Plik: GroupManagerApp.java
 *
 *   Autor: Mateusz Krawczak
 *    Data: 03.listopad.2018 r.
 */

public class GroupsApplication extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;

	private  String welcome =
			"Program do zarz¹dzania kolekcjami gier\n" +
	        "Autor: Mateusz Krawczak\n" +
					"Indeks: 241318\n"+
			"Data:   03.listopad.2018 r.\n";

	
	public static void main(String[] args) throws GameException {
	//	games=new GroupOfPeople();
		new GroupsApplication();
	}

	
	private GroupOfGames games;
	private List<GroupOfGames> currentList = new ArrayList<GroupOfGames>();

	JMenuBar bar  = new JMenuBar();
	
	

	JMenu file1= new JMenu("Program"); 
	JMenu save= new JMenu("Zapisz grupe");
	JMenu load= new JMenu("Wczytaj grupe"); 
	
	
	
	JMenuItem zn=new JMenuItem("Do pliku");
	JMenuItem wn=new JMenuItem("Z pliku");
	JMenuItem exit=new JMenuItem("Wyjœcie");
	JMenuItem info=new JMenuItem("O programie");
	


	
	JButton newB = new JButton("Stwórz now¹ grupê");
	JButton editB = new JButton("Edytuj wybran¹ grupe");
	JButton deleteB = new JButton(" Usuñ wybran¹ grupe ");
	

	ListOfGroups groupList;


	public GroupsApplication() throws GameException {
	
		setTitle("GroupApplication");
		setSize(500, 500);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setJMenuBar(bar); 
		
		JPanel panel=new JPanel();
		
		bar.add(file1);
		bar.add(save);
		bar.add(load);
		save.add(zn);
		load.add(wn);
		file1.add(info);
		file1.add(exit);
		
		panel.setBorder(BorderFactory.createTitledBorder("Lista grup:"));
		
		info.addActionListener(this);
		exit.addActionListener(this);
		wn.addActionListener(this);
		zn.addActionListener(this);
		newB.addActionListener(this);
		editB.addActionListener(this);
		deleteB.addActionListener(this);
		file1.addActionListener(this);

		groupList = new ListOfGroups(currentList, 400, 300);
		groupList.refreshView();

		
		panel.add(groupList);
		panel.add(newB);
		panel.add(editB);
		panel.add(deleteB);
	
		setContentPane(panel);

		setVisible(true);
	}


	public void actionPerformed(ActionEvent evt) {

		Object s = evt.getSource();

		if (s == newB ) {

				try {
					games=GameWindow.createNewGroup(this);
				} catch (GameException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				currentList.add(games);
		
		}

		else if (s == editB) {
			
			int index=groupList.getSelectedIndex();
			
			GroupOfGames g = null;
				 if (index >= 0) {
						Iterator<GroupOfGames> ite1 = currentList.iterator();
				while (index-- >= 0)  {
				 g=(GroupOfGames) ite1.next();
					 }
				 
				try {
					GameWindow.editGroup(this, g);
				} catch (GameException e) {
					
					e.printStackTrace();
				}
			
			
			}
		}

		else if (s ==deleteB) {
			int index = groupList.getSelectedIndex();
			if (index >= 0) {
				Iterator<GroupOfGames> iterator = currentList.iterator();
				while (index-- >= 0)
					iterator.next();
				iterator.remove();
			}
		}

		else if (s == wn ) {
			JFileChooser chooser = new JFileChooser();
			chooser.showOpenDialog(this);
			chooser.setCurrentDirectory(new File("."));
			File file=chooser.getSelectedFile();
			try {
				
					try {
						currentList.add(GroupOfGames.loadingFromFile(file));
					} catch (IOException | GameException e) {
						JOptionPane.showMessageDialog(null, "Nie wybrano folderu","B³¹d",JOptionPane.ERROR_MESSAGE);
						e.printStackTrace();
					}
				} catch(NullPointerException e2) {
					JOptionPane.showMessageDialog(null, "Nie wybrano folderu","B³¹d",JOptionPane.ERROR_MESSAGE);
				}
		}
		else if (s == zn ) {
			GroupOfGames g = null;
			int index = groupList.getSelectedIndex();
			
			if (index >= 0) {
				Iterator<GroupOfGames> iterator = currentList.iterator();
				while (index-- >= 0) {
					 g=iterator.next();
				}
				GroupOfGames groupToSave = g;
				
					JFileChooser chooser=new JFileChooser();
					chooser.showOpenDialog(this);
					chooser.setCurrentDirectory(new File("."));
					File file=chooser.getSelectedFile();
					try {
						GroupOfGames.savingToFile(groupToSave,file);
					}
						catch (FileNotFoundException e1) {
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

		else if (s == info) {
			JOptionPane.showMessageDialog(this, getWelcome());
		}
		else if (s == exit) {
			System.exit(0);
		}

		
		groupList.refreshView();
		}



	public String getWelcome() {
		return welcome;
	}



	public void setWelcome(String welcome) {
		this.welcome = welcome;
	}

	
}



class ListOfGroups extends JScrollPane {
private static final long serialVersionUID = 1L;

	private List<GroupOfGames> list;
	private JTable table;
	private DefaultTableModel tableModel;

	public ListOfGroups(List<GroupOfGames> list, int width, int height){
		this.list = list;
		setPreferredSize(new Dimension(width, height));
		setBorder(BorderFactory.createTitledBorder("Lista grup:"));

		String[] tableHeader = { "Nazwa grupy", "Typ kolekcji", "Liczba gier" };
		tableModel = new DefaultTableModel(tableHeader, 0);
		table = new JTable(tableModel) {

			private static final long serialVersionUID = 1L;

		
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
		for (GroupOfGames group : list) {
			if (group != null) {
				String[] row = { group.getName(), group.getType().toString(), "" + group.size() };
				tableModel.addRow(row);
			}
		}
	}

	int getSelectedIndex(){
		int index = table.getSelectedRow();
		if (index<0) {
			JOptionPane.showMessageDialog(this, "¯adana grupa nie jest zaznaczona.", "B³¹d", JOptionPane.ERROR_MESSAGE);
		}
		return index;
	}
}
