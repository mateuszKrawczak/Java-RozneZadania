import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collection;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

/* 
 *  Klasa phone book odpowiedzialna za wszystkie polecenia wpisywane przez uzytkownika
 *
 *  Autor: Mateusz Krawczak
 *   Data: 26.12 2018 r.
 */

public class PhoneBook {
	ConcurrentHashMap<String, String> phonebook=new ConcurrentHashMap<>();
	String name;
	String phone_number;
	
	Collection<String> values = phonebook.values();
	
	

ConcurrentHashMap<String, String> listNames() {
	return phonebook;
}



String list() {
	
	return "OK "+phonebook.keySet();
	
}
@Override
public int hashCode() {
	final int prime = 31;
	int result = 1;
	
	result = prime * result + ((name == null) ? 0 : name.hashCode());
	result = prime * result + ((phone_number == null) ? 0 : phone_number.hashCode());
	result = prime * result + ((phonebook == null) ? 0 : phonebook.hashCode());
	result = prime * result + ((values == null) ? 0 : values.hashCode());
	return result;
}



String load(FileInputStream fis) throws IOException, ClassNotFoundException{
	 try{
	        
	        ObjectInputStream ois=new ObjectInputStream(fis);

	        ConcurrentHashMap<String,String> mapInFile=(ConcurrentHashMap<String,String>)ois.readObject();

	        ois.close();
	        fis.close();
	       for(Entry<String, String> m :mapInFile.entrySet()){
	           phonebook.put(m.getKey(),m.getValue());
	        }
	    }catch(FileNotFoundException e)
	 {
	    	  e.printStackTrace();
	    	}
	return "OK";
}
String save(String file) throws IOException{
	
	 try{
	 File fileOne=new File(file);
	    FileOutputStream fos=new FileOutputStream(fileOne);
	       ObjectOutputStream oos=new ObjectOutputStream(fos);

	        oos.writeObject(phonebook);
	        oos.flush();
	        oos.close();
	        fos.close();
	 }catch(Exception e){}

	   
	return "OK";
}
String get(String name) {
	for (String n: phonebook.keySet()){

     if(n.equals(name)) {
    	 phone_number=phonebook.get(n);
    	 break;
    	
     }else {
    	 phone_number=null;
     }
     
     

}
	 return phone_number;
}
String put(String name,String number) {
	phonebook.put(name, number);
	return "OK";
}
String replace(String name,String number) {
	

	    	phonebook.replace(name,number);
	 return "OK";
}
String delete(String name) {
	phonebook.remove(name);
	
	return "OK";
}

}
