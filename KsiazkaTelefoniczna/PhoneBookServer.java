import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;


/* 
 *  Serwer
 *
 *  Autor: Mateusz Krawczak
 *   Data: 26.12 2018 r.
 */

class PhoneBookServer extends JFrame implements  Runnable {
	
	private static final long serialVersionUID = 1L;

	static final int SERVER_PORT = 25001;
	ServerSocket serwer;
	String host;
	
	public static void main(String [] args){
		new PhoneBookServer();
	}
	
	private JLabel information=new JLabel("Serwer włączony");
	
	PhoneBookServer(){ 
		super("SERWER");
	  	setSize(150,100);
	  	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	  	JPanel panel = new JPanel();
	  	panel.add(information);
	  	setContentPane(panel);
	  	setVisible(true);
	  	
	  	new Thread(this).start(); 
	}
	
	public void run() {
		Socket s;
	  	
	  	
	  	try
	  	{
	  		host = InetAddress.getLocalHost().getHostName();
	  	   	serwer = new ServerSocket(SERVER_PORT);
		} catch(IOException e)
		{ 	
		 	
		 	System.out.println(e);
		   	JOptionPane.showMessageDialog(null, "Gniazdko dla serwera nie moze byc utworzone"); 
		   	System.exit(0);
		}
		System.out.println("Serwer zostal uruchomiony na hoscie " + host);
	  	
		while(true)
	  	{	
			try 
			{ 
				s = serwer.accept();
				if (s!=null)
		  			new WatekKlienta(s);
		  		
			} catch(IOException e)
			{ 
				
				System.out.println("BLAD SERWERA: Nie mozna polaczyc sie z klientem ");
			}
		}
	}
	
} 

class WatekKlienta implements Runnable {
	/* 
	 * Klasa pomocnicza, która jest pośrednikiem pomiedzy serwerem a klasą PhoneBook
	 *
	 *  Autor: Mateusz Krawczak
	 *   Data: 26.12 2018 r.
	 */
	int loop=0;
	private Socket socket;
	private ObjectOutputStream output;
	private ObjectInputStream input;

	PhoneBook pb=new PhoneBook();
	
	WatekKlienta(Socket s) throws IOException
	{ 
		socket = s;
	  	Thread t = new Thread(this);
	  	t.start();
	}
	
	public ObjectOutputStream getOutput(){ return output; }
	
	Scanner sc;
	public void run()
	{  
		
		String m ;
		String name ;
		String[] parts = new String[2];
		String part1 ; 
		String part2 ;
		String part3 ;
		String plik;
		
	   	try
	   	{
	   		
	   		output = new ObjectOutputStream(socket.getOutputStream());
	  		input = new ObjectInputStream(socket.getInputStream());
	   		
			while(true)
			{
				
				m = (String)input.readObject();
				int counter = 0;
			    for (char c : m.toCharArray()) {
			        if (c == ' ' ) {
			            counter++;
			        }
			    }
				if(counter==1 ) {
				parts = m.split(" ",2);
				
				part1 = parts[0]; 
				part2 = parts[1];
			
				 if(part1.equals("GET")) {
						
						name=part2;
						if(pb.get(name)==null) {
							getOutput().writeObject("ERROR brak takiego imienia w książce");
						}else {
							getOutput().writeObject("OK "+pb.get(name));
						}
							
						}
				 else if(part1.equals("DELETE")) {
						
						name=part2;
						if(pb.get(name)==null) {
							getOutput().writeObject("ERROR brak takiego imienia w książce");
						}else {
							getOutput().writeObject(pb.delete(name));
						}
							
						}
				 else if(part1.equals("SAVE")) {
						
						plik=part2;
						if(part2.equals("") ){
							getOutput().writeObject("ERROR nie podano nazwy pliku");
					}else {
							getOutput().writeObject(pb.save(plik));
						}
				 }
			
				 else if(part1.equals("LOAD")) {
						
						plik=part2;
					 
						try {
							File toRead=new File(plik);
					        FileInputStream fis=new FileInputStream(toRead);
							getOutput().writeObject(pb.load(fis));
						  } catch (FileNotFoundException e) {
							  getOutput().writeObject("ERROR "+String.valueOf(e));
						  }
							
						}	 else {
							
								getOutput().writeObject("ERROR nie zrozumiano polecenia");
						}
			
			}else if(counter==2 ) {
					parts = m.split(" ",3);
					
					part1 = parts[0]; 
					part2 = parts[1];
					part3= parts[2];
					
					 if(part1.equals("PUT")) {
						
							  if(isNumeric(part3)) {
						getOutput().writeObject(pb.put(part2,part3));
								}else {
									getOutput().writeObject("ERROR nie podano numeru");
								}
								
							}
					 else if(part1.equals("REPLACE")) {
							if(pb.get(part2)!=null) {
						  if(isNumeric(part3)) {
							  
					getOutput().writeObject(pb.replace(part2,part3));
							}else {
								getOutput().writeObject("ERROR nie podano numeru");
							}
							}else {
								getOutput().writeObject("ERROR nie ma takiego imienia w ksiazce");
							}
						} else {
							
								getOutput().writeObject("ERROR nie zrozumiano polecenia");
						}
				}
				
				
				else if(m.equals("CLOSE")) {
					
					socket.close();
					input.close();
					output.close();
					socket = null;
					break;	
				}
			
						
				
				 else if(m.equals("LIST")){ 		
					
					
					 
					 getOutput().writeObject(pb.list());
							
					}
				 else {
					if(loop>=1)
					getOutput().writeObject("ERROR nie zrozumiano polecenia");
				 }
				
				loop++;
				}
	   	} catch(Exception e) {}
	}
	 public static boolean isNumeric(String str)  {
		 
		   try  
		   {  
		      Integer.parseInt(str);  
		   }  
		   catch(NumberFormatException nfe)  
		   {  
		     return false;  
		   }  
		   return true;  
		 }
	}

