import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.KeyStore;

import javax.net.ServerSocketFactory;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocketFactory;

public class PortListener extends Thread {
	
	//	Atrebutes
	//ServerSocket ss;
	Conf conf;	//	True if act as a server
	
	public PortListener(Conf conf) throws IOException  {
		
		this.conf=conf;
		
		
		if(conf.client) {	// Recive it and encript and send to other PortListener
			
		} else {			// Recive it and decript and send to server
			
		}
		
		
		
		
		
		
		
		
		
		this.start();	// call --> run()
		
	}
	
	// All the work here!!
	@Override
	public void run(){
		// all the work here
		if(conf.client) {						// Ating as client
			try {
			ServerSocketFactory ssf = this.getServerSocketFactory("TLS");
			ServerSocket ss = ssf.createServerSocket(conf.listenPort);
			System.out.print("Port("+ ss.getLocalPort() +") is listening [ClientONE doing its job]...\n"); 
			BufferedReader bR;
			Socket s = ss.accept();
			System.out.print("Connection coming... [to ClientONE "+s.getLocalPort()+"]\n"); 
			bR = new BufferedReader(new InputStreamReader(s.getInputStream()));
			//	-----
			Socket g = new Socket(conf.DestionationIP,conf.DestinationPort);	//	Outside Server's ip address and port number [secure channel values]
			//	-----
			DataOutputStream dOS = new DataOutputStream(g.getOutputStream());
			//byte[] message = new byte[9999];
			String message = bR.readLine();
			System.out.print("Connection accapted... [to ServerONE "+s.getLocalPort()+"]\n");
			dOS.writeBytes(message);
			dOS.writeBytes(" Bu bilgiler serverden geliyor!\n");
			g.close();
			s.close();
			
			/*
			File myFile = new File("C:\\Users\\DellPc\\eclipse-workspace\\NetworkProje\\"+line);
			if(myFile.exists()) {
				//Found the file
				//dOS.writeBytes("Success!!!");
				DataInputStream dIS = new DataInputStream(new FileInputStream(myFile));
				int size = (int) myFile.length();
				byte[] arr = new byte[size];
				dIS.readFully(arr);
				dOS.write(arr);
				
				s.close();
			} else {
				//no such file
				dOS.writeBytes("Hata: File doesnt exist!");
				s.close();} */
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {	// server actions hehe
			try {
				ServerSocketFactory ssf = this.getServerSocketFactory("TLS");
				ServerSocket ss = ssf.createServerSocket(conf.listenPort);
				System.out.print("Port("+ ss.getLocalPort() +") is listening [ServerONE doing its job]...\n"); 
				BufferedReader bR;
				Socket s = ss.accept();
				System.out.print("Connection coming... [to ServerONE "+s.getLocalPort()+"]\n"); 
				bR = new BufferedReader(new InputStreamReader(s.getInputStream()));
				//	-----
				Socket g = new Socket(conf.DestionationIP,conf.DestinationPort);	//	Outside Server's ip address and port number [secure channel values]
				//	-----
				 
				DataOutputStream dOS = new DataOutputStream(g.getOutputStream());
				//byte[] message = new byte[9999];
				int message = bR.read();
				System.out.print("Connection accapted... [to ServerONE "+s.getLocalPort()+"]\n");
				dOS.writeBytes(Integer.toString(message));
				g.close();
				s.close();
				
			} catch (IOException e) {
				e.printStackTrace();
			}	
		}
	}
	/////!!!!! Do not touch ----!!!!!
	private ServerSocketFactory getServerSocketFactory(String type) {
		if (type.equals("TLS")) {
		    SSLServerSocketFactory ssf = null;
		    try {
			SSLContext ctx;
		        KeyManagerFactory kmf;
		        KeyStore ks;
		        char[] passphrase = "importkey".toCharArray();

		        ctx = SSLContext.getInstance("TLS");
		        kmf = KeyManagerFactory.getInstance("SunX509");
		        ks = KeyStore.getInstance("JKS");

		        ks.load(new FileInputStream("keystore.ImportKey"), passphrase);
		        kmf.init(ks, passphrase);
		        ctx.init(kmf.getKeyManagers(), null, null);

		        ssf = ctx.getServerSocketFactory();
		        return ssf;
		    } catch (Exception e) {
		        e.printStackTrace();
		    }
		} else {
		    return ServerSocketFactory.getDefault();
		}
		return null;
        }


}
