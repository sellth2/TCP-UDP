import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.net.ServerSocketFactory;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocketFactory;


class Conf {
	boolean client;
	int listenPort;
	String DestionationIP;
	int DestinationPort;
	boolean Proto;
	
	Conf(boolean client,int listenPort,String DestionationIP,int DestinationPort,boolean Proto){
		this.client=client;this.listenPort=listenPort;this.DestionationIP=DestionationIP;this.DestinationPort=DestinationPort;this.Proto=Proto;}
}

public class Main {
	
	

	public static void main(String[] args) throws IOException {
		
		List<Conf> progs = new ArrayList<Conf>();
		progs.add(new Conf(true,5000,"localhost",443,true));
		progs.add(new Conf(true,6269,"localhost",5050,true));	//Decoy!!!
		progs.add(new Conf(false,443,"localhost",5000,true));
		
		File conf = new File("C:\\Users\\DellPc\\eclipse-workspace\\NetworkProje\\conf.txt");
		/*
		if(conf.exists()) {
			Scanner in = new Scanner(conf);
			while(in.hasNextLine()) {
				String line = in.nextLine();
				//	Start parsing lines in conf.txt
				String[] options = line.split(" = ");
				if(options[0].equals("client")) {	//	being a client or not being a client, its all that matters.
					// options[1] => yes or no
				} else if(options[0].equals("ListenPort")) {	//	Get portnumber to listen
					ports.add(Integer.parseInt(options[1]));
				} else if(options[0].equals("DestionationIP")) {
					
				} else if(options[0].equals("DestinationPort")) {
					
				} else if(options[0].equals("Proto")) {	//	Protochol to use TCP or UDP
					
				}
			}
		} else { System.out.print("\n------\nERR: Configuration file could not found! [conf.txt]\n-----\n"); }
		
		ServerSocketFactory ssf = Main.getServerSocketFactory("TLS");
		*/
		
		for (int i=0;i < progs.size();i++) {
			//dont touch			
			//dont touch
			//System.out.print("Port("+ ss.getLocalPort() +") is waiting to connect...\n"); 
			
			new PortListener(progs.get(i));
		}
		
		Scanner sc = new Scanner(System.in);
		sc.nextLine();
		
		

	}
	

}
