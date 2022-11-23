import java.io.*;
import java.net.*;

public class TCPClient {

	public static void main(String[] args) {
		try {
			System.out.println("Client is working");
			String host = "10.138.101.153";
			Socket socket = new Socket(host, 6789);
			BufferedReader inFromUser = new BufferedReader(
					new InputStreamReader(System.in));
			PrintWriter out = new PrintWriter(new OutputStreamWriter(
					socket.getOutputStream()));
			BufferedReader in = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			new ClientPrinter(in).start();
			String str;
			while (true) {
				str = inFromUser.readLine();
				out.println(str);
				out.flush();
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

}