import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;


/* 
 *  Klient
 *
 *  Autor: Mateusz Krawczak
 *   Data: 26.12 2018 r.
 */

class PhoneBookClient extends JFrame implements ActionListener, Runnable{
	
	private static final long serialVersionUID = 1L;
	
	public static void main(String[] args) {
		String name;
		name = JOptionPane.showInputDialog("Podaj nazwe uzytkownika");
		if (name != null && !name.equals("")) {
			new PhoneBookClient(name, "127.0.0.1");
		}
	}
	
	
	static final int SERVER_PORT = 25001;
	private String name;
	private Socket socket;
	private ObjectOutputStream outputStream;
	private ObjectInputStream inputStream;
	JPanel panel = new JPanel();
	JLabel messageLabel = new JLabel("Napisz:");
	JLabel textAreaLabel = new JLabel("Dialog:");
	private JTextField messageField = new JTextField(20);
	private JTextArea  textArea     = new JTextArea(15,18);
	PhoneBookClient(String name, String host) {
		super(name);
		this.name = name;
		setSize(300, 310);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent event) {
				try {
					outputStream.close();
					inputStream.close();
					socket.close();
				} catch (IOException e) {
					System.out.println(e);
				}
			}
			
		});
		
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		panel.add(messageLabel);
		panel.add(messageField);
		messageField.addActionListener(this);
		panel.add(textAreaLabel);
		JScrollPane scrolls = new JScrollPane(textArea, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		panel.add(scrolls);
		setContentPane(panel);
		setVisible(true);
		new Thread(this).start();  
	}
	
	synchronized public void printReceivedMessage(String message){
		String text = textArea.getText();
 		textArea.setText(text + ">>> " + message + "\n");
	}
	
	synchronized public void printSentMessage(String message){
		String text = textArea.getText();
	 	textArea.setText(text + "<<< " + message + "\n");
	}

	public void actionPerformed(ActionEvent e)
	{ String message;
	  Object s = e.getSource();
	  if (s==messageField)
	  {
	  	try{ message = messageField.getText();
	  		if(message.equals("BYE")) {
					socket.close();
					inputStream.close();
					outputStream.close();
					dispose();
				}
	  		 outputStream.writeObject(message);
	  		 printSentMessage(message);
	  		}catch(IOException er)
	  	{ System.out.println("Wyjatek klienta "+er);
	  		}
	  }
	  repaint();
	  messageField.setText("");
	}
	
	public void run(){
		try{
	  		socket = new Socket("127.0.0.1", SERVER_PORT);
	  		inputStream = new ObjectInputStream(socket.getInputStream());
	  		outputStream = new ObjectOutputStream(socket.getOutputStream());
	  		outputStream.writeObject(name);
	  	} catch(IOException e){ 
		   	JOptionPane.showMessageDialog(null, "Polaczenie sieciowe dla klienta nie moze byc utworzone");
		   	setVisible(false);
		   	dispose();  
		    return;
		 }
		 try{
		 	while(true){
		 		String message = (String)inputStream.readObject();
		 		printReceivedMessage(message);
		 	}
		 } catch(Exception e){
		   	JOptionPane.showMessageDialog(null, "Polaczenie sieciowe dla klienta zostalo przerwane");
		   	setVisible(false);
		   	dispose();
		 }	
	}
	
} 