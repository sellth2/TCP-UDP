import java.io.*;
import java.net.*;
import java.security.KeyStore;
import javax.net.*;
import javax.net.ssl.*;
import java.security.cert.X509Certificate;
import java.security.cert.CertificateException;
import java.util.Properties;
import java.util.Scanner;

/*
 * This example demostrates how to use a SSLSocket as client to
 * send a HTTP request and get response from an HTTPS server.
 * It assumes that the client is not behind a firewall
 */

public class SSLSocketClient {

    public static void main(String[] args) throws Exception {
    	
    String fileName="";
    Scanner scanin = new Scanner(System.in);
    
    //System.out.print("Please enter port to connect: ");
    //int portNum = scanin.nextInt();
    

    Properties systemProps = System.getProperties();
    systemProps.put("javax.net.ssl.trustStore", "keystore.ImportKey");
        try {
        	//System.out.print("TRYA GIRDI");
            SSLSocketFactory factory = getSSLSocketFactory("TLS");
            SSLSocket socket =
                (SSLSocket)factory.createSocket("localhost", 5000);	// KeyPoint
            //System.out.print(" Handshake olucak ");
            socket.startHandshake();
            //System.out.print("Handshakeledi GIRDI");
            PrintWriter out = new PrintWriter(
                                  new BufferedWriter(
                                  new OutputStreamWriter(
                                  socket.getOutputStream())));	//	Will.i.am Shanksepere

            System.out.print("Please send a message: ");
            fileName = scanin.nextLine();
            
            
            
            //Sending part
            out.print(fileName + "\n");
            out.flush();

            /*
             * Make sure there were no surprises
             */
            if (out.checkError())
                System.out.println(
                    "SSLSocketClient:  java.io.PrintWriter error");

            /* read response */
            DataInputStream in = new DataInputStream(socket.getInputStream());
            // use datainputstream for upper side
            

            	
            int inputLine;
            while ((inputLine = in.read()) != -1) {
	            System.out.print("\nMessage Received: \n" + in.read());
	        }
        	

            in.close();
            out.close();
            socket.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	private static SSLSocketFactory getSSLSocketFactory(String type) {
		if (type.equals("TLS")) {
		    SocketFactory ssf = null;
		    
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

		        ssf = ctx.getSocketFactory();
		        return (SSLSocketFactory) ssf;
		    } catch (Exception e) {
		        e.printStackTrace();
		    }
		} else {
		    return (SSLSocketFactory) SSLSocketFactory.getDefault();
		}
		return null;
        }
}
