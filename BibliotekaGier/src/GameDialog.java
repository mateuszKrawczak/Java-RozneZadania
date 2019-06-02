


import java.awt.Dialog;
import java.awt.FlowLayout;

import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;


import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;

import javax.swing.JOptionPane;

import javax.swing.JTextField;


/*
 * Program: Klasa odpowiedzialna za komponenty i dzia³anie dialogowego okna 
 *          
 *    Plik: GameDialog.java
 *          
 *   Autor: Mateusz Krawczak
 *    Data: 15.10.2018 r.
 *
 */
public class GameDialog extends JDialog implements ActionListener, Serializable {
	
	private static final long serialVersionUID = 1L;

	
	private Game game;
	
	JTextField tname=new JTextField(10);
	JTextField tprice=new JTextField("0",3);
	
	JButton ok= new JButton("OK");
	JButton cancel=new JButton("Anuluj");
	
	JComboBox<TypeOfTheGame> typeBox = new JComboBox<TypeOfTheGame>(TypeOfTheGame.values());
	JComboBox<PegiOfTheGame> pegiBox = new JComboBox<PegiOfTheGame>(PegiOfTheGame.values());
	
	private GameDialog(Window parent, Game g) {
		super(parent, Dialog.ModalityType.DOCUMENT_MODAL);
		setTitle("Dane gry");
		
			
			
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(200, 200);
		setLocationRelativeTo(parent);
		
		
		this.game = g;
		
		
		if (game!=null){
			
			tname.setText(game.getName());
			tprice.setText(String.valueOf(game.getPrice()));
			pegiBox.setSelectedItem(game.getPegi());
			typeBox.setSelectedItem(game.getType());
		}
		
		
		ok.addActionListener( this );
		cancel.addActionListener( this );
		
		
		add(GameWindow.lname);
		add(tname);
		add(GameWindow.lprice);
		add(tprice);
		add(GameWindow.lpegi);
		add(pegiBox);
		add(GameWindow.ltype);
		add(typeBox);
		add(ok);
		add(cancel);
		
		setLayout(new FlowLayout());
		
		
		setVisible(true);
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object evt=e.getSource();
		
		if (evt == ok) {
			try {
				if (game == null) { 
					game  = new Game (tname.getText());
					
				} else { 
					game.setName(tname.getText());
				
				}
				
				game.setPrice(tprice.getText());
				game.setPegi( (PegiOfTheGame)pegiBox.getSelectedItem());
				game.setType( (TypeOfTheGame) typeBox.getSelectedItem());
				
			
				dispose();
			} catch (GameException err) {
				
				JOptionPane.showMessageDialog(this, err.getMessage(), "B³¹d", JOptionPane.ERROR_MESSAGE);
			}
			
		
		}

		if (evt == cancel) {
			
			dispose();
		}
		System.out.println(game);
	}
	


	public static Game createGame(Window parent) {
		GameDialog dialog = new GameDialog(parent, null);
		return dialog.game;
	}

	
	
	public static void editGame(Window parent, Game g) {
		new GameDialog(parent, g);
	}

}

