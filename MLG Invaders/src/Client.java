import java.awt.Color;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;



public class Client {
	/**
	 * Variable storing ip address
	 */
	private static String ip;
	/**
	 * Variable storing port number
	 */
	private static String port;
	/**
	 * Variable storing socket
	 */
	private static Socket socket;

	/**
	 * 
	 * @param count
	 * @param br
	 * @param socket
	 * @return
	 * @throws IOException
	 */
	public static String[] getLines(int count, BufferedReader br, Socket socket)
			throws IOException {
		String[] answer = new String[count];
		for (int i = 0; i < count; i++) {
			answer[i] = br.readLine();
			if (answer[i].equals(Protocol.ERROR))
				throw new IOException("server error");
		}
		socket.close();
		return answer;
	}
	
	public static String[] getLine(BufferedReader br, Socket socket)
			throws IOException {
		String[] answer = new String[30];
		answer[0] = br.readLine();
			// br to input stream socketa
		
		socket.close();
		return answer;
	}

	/**
	 * 
	 * @param count
	 * @param br
	 * @param socket
	 * @return
	 * @throws IOException
	 */
	public static String getLines2(int count, BufferedReader br, Socket socket)
			throws IOException {
		String answer = null;// = new String[count];
		for (int i = 0; i < count; i++){
			answer = br.readLine();
		}
			// br to input stream socketa
		/*
			if (answer.equals(Protocol.ERROR)){
				throw new IOException("server error");
		
			}*/
		socket.close();
		return answer;
	}
	

	/**
	 * Za argument bierze komende "command", adres ip oraz port. Tworzy socket o danym ip oraz porcie. 
	 * Soket ten ma input(buforowany) oraz output stream. Wysyla na output komende i w zaleznosi
	 * od odebranej informacji zwrotnej od serwera pobiera odpowiednie informacje (switch)
	 * @param command
	 * @param ip
	 * @param port
	 * @return a line from server answer
	 * @throws NumberFormatException
	 * @throws IOException
	 */
	public static String[] connection(String command, String ip, String port)

			throws NumberFormatException, IOException {

		
		
		/**
		 * Creating client socket
		 */
		try{
		socket = new Socket(ip, Integer.parseInt(port));
		socket.setSoTimeout(3000);
		}
		catch (NumberFormatException e)
		{	return null;
		}
		catch (IOException e){
			return null;
		}

		/**
		 * Creating output stream
		 */
		OutputStream output_stream		= socket.getOutputStream();
		PrintWriter byte_output_stream 	= new PrintWriter(output_stream, true);
		
		/**
		 * Creating output stream with buffer
		 */
		InputStream input_stream = socket.getInputStream();
		BufferedReader byte_input_stream = new BufferedReader(new InputStreamReader(input_stream));
		
		
		/**
		 * Sending command to the server
		 */
		byte_output_stream.println(command);
		
		

		/**
		 * Method doing what the server sent
		 */
		switch (command) {
	
		case "get_properties": {
			
			System.out.println(" \t Przed pobraniem: czy konfiguracja pochodzi z serwera? = " + Config.getProperties().getProperty("server"));
			
			try{
				Config.getProperties().load(input_stream);	
			}
			catch (NumberFormatException e)
			{	return null;
			}
			catch (IOException e){
				return null;
			}
			
			
			System.out.println(" \t Po pobraniu: czy konfiguracja pochodzi z serwera? = " + Config.getProperties().getProperty("server"));
			
			return getLine(byte_input_stream, socket); 
			
		}
		case "Get high scores":{
			String[] temp = getLines(20, byte_input_stream, socket);

			
			return temp;
		}

		default: {
			socket.close();
			throw new IOException("unknown command");
		}
		}
	}

	/**
	 * Method establishing the connection
	 * @param command
	 * @param ip_
	 * @param port_
	 * @throws NumberFormatException
	 * @throws IOException
	 */
	public static void setConnection (String command, String ip_, String port_) throws NumberFormatException, IOException{

		ip = ip_;
		port = port_;
	}
}
