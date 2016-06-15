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
	
	private static String ip;
	private static String port;
	private static Socket socket;
	
/*	
	public String[] getLevel(String ip, String port) {
		String[] answer;
		try {
			answer = connection(Protocol.GETLEVEL, ip, port);
		} catch (NumberFormatException e) {
			return null;
		} catch (IOException e) {
			return null;
		}
		return answer;
	}
	
	public static String[] getHighScores(String ip, String port) {
		String[] answer;
		try {
			answer = connection(Protocol.GETHIGHSCORES, ip, port);
		} catch (NumberFormatException e) {
			return null;
		} catch (IOException e) {
			return null;
		}
		return answer;
	}
	*/
	
	//public static void 
	
	
	public static String[] getLines(int count, BufferedReader br, Socket socket)
			throws IOException {
		String[] answer = new String[count];
		for (int i = 0; i < count; i++) {
			answer[i] = br.readLine();
			// br to input stream socketa
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
	
/*	public static String getLines(BufferedReader br, Socket socket)
			throws IOException {
		String answer = new String();
		try{
		answer = br.readLine();
//		answer = br.toString();
		}
		catch (IOException e){
			e.printStackTrace();
		}
			// br to input stream socketa
			if (answer[i].equals(Protocol.ERROR))
				throw new IOException("server error");
		
		socket.close();
		return answer;
	}*/
	
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
		 * tworzenie socketa klienta
		 */
		try{
		socket = new Socket(ip, Integer.parseInt(port));
		socket.setSoTimeout(3000);	// standardowy timeout
		}
		catch (NumberFormatException e)
		{	return null;
		}
		catch (IOException e){
			return null;
		}

		/**
		 * tworzenie strumienia wyjsciowego
		 */
		OutputStream output_stream		= socket.getOutputStream();
		PrintWriter byte_output_stream 	= new PrintWriter(output_stream, true);
		
		/**
		 * tworzenie strumienia wyjsciowego z buforem
		 */
		InputStream input_stream = socket.getInputStream();
		BufferedReader byte_input_stream = new BufferedReader(new InputStreamReader(input_stream));
		
		
		/**
		 * wysylanie zapytania (komendy) do serwera
		 */
		byte_output_stream.println(command);
		
		

		/**
		 * Obsluga tego, co odeslal serwer. 
		 */
		switch (command) {
		
		/**
		 * jesli wyslalismy do serwera polecenie "get_properties" to powinnismy zareagowac w taki sposob:
		 */
		case "get_properties": {
			
				/*
				 * drukowanie tych komunikatow mozna wyrzucic, poki co jest pewnosc czy nam cos pobralo
				 */
			System.out.println(" \t Przed pobraniem: czy konfiguracja pochodzi z serwera? = " + Config.getProperties().getProperty("server"));
			
			try{
				Config.getProperties().load(input_stream);	// wczytujemy strumien wejsciowy i tworzymy z niego aktualnie uzywany obiekt properties
			}
			catch (NumberFormatException e)
			{	return null;
			}
			catch (IOException e){
				return null;
			}
			
			
			System.out.println(" \t Po pobraniu: czy konfiguracja pochodzi z serwera? = " + Config.getProperties().getProperty("server"));
			
			return getLine(byte_input_stream, socket); 	// zwracamy to co przyslal serwer czyli plik mlg.properties		
			
		}
		case "Get high scores":{
			String[] temp = getLines(20, byte_input_stream, socket);

		//	System.out.println("odpowiedzi ascasc = " + temp);
			
			return temp;
		}
		
		

/*		case "test_command": {
			//return getLines(1, byte_input_stream, socket);
			//return getLines(byte_input_stream, socket);
			return getLines(1,byte_input_stream, socket);
			 
		}*/
		
/*		case "get_all": {
			int linie = 23;
			String[] odp = new String[linie];
			//String temp = getLines2(linie, byte_input_stream, socket);
			odp = getLines(linie,byte_input_stream, socket);		
	System.out.println("Wykonano metode connection() switch geta ///");
	System.out.println("temp = " + odp);
			//String[] strArray = new String[] {temp};
			//return strArray;
			return odp;
		}*/
		

		
		
		
		

/*		case Protocol.GETINTERFACECOLORS: {
			return getLines(4, br, socket);
		}

		case Protocol.GETTEXTSIZE: {
			return getLines(3, br, socket);
		}

		case Protocol.GETBOARDDIMENSION: {
			return getLines(2, br, socket);
		}

		case Protocol.GETBOARDSIZE: {
			return getLines(1, br, socket);
		}

		case Protocol.GETHIGHSCORES: {
			return getLines(20, br, socket);
		}

		case Protocol.GETLEVEL: {
			pw.println("1");
		//	pw.println(Config.boardDimension[0]);
			//pw.println(Config.boardDimension[1]);
//			return getLines(3 + Config.boardDimension[0], br, socket);
			return getLines(3 + 2, br, socket);
		}*/

		default: {
			socket.close();
			throw new IOException("unknown command");
		}
		}

	
	}


	public static void setConnection (String command, String ip_, String port_) throws NumberFormatException, IOException{
		
		// jesli ip jest w formie *.*.*.* to ok, jak nie to wyjatek
		
		ip = ip_;
		port = port_;
		
/*		String[] temp;
		
		System.out.println("przed = " + Config.getProperties().getProperty("SidePanelWidth"));
		temp = Client.connection(command, ip, port);
		System.out.println("po = " + Config.getProperties().getProperty("SidePanelWidth"));*/
	}


}


/*
Client cl = new Client();
String[] temp, temp2, temp3;
//String[] temp = new String[5];
//String ip	=	"192.168.1.6";
String ip	=	"192.168.1.6";
String port	=	"1000";
int linie = 23;

//String[] test;
String[] test = new String[5];
test[0]= "jeden";
test[1] = "dwa";
test[2] = "trzy";
//String a,b;
//a="ok";
//b="";
//	a = Client.connection("test_command", ip, port);
//b =  Client.connection("get_all", ip, port);


//temp = Client.connection("get_all", ip, port);
	

		for(int i = 0; i < linie; i++){
System.out.println("Wydruk z geta: " + temp[i]);
}

System.out.println("\n\n -------------- \n\n");



System.out.println("przed = " + Config.getProperties().getProperty("SidePanelWidth"));
temp2 = Client.connection("get_properties", ip, port);
System.out.println("po = " + Config.getProperties().getProperty("SidePanelWidth"));

temp3 = Client.connection("Get high scores", ip, port);
//String str = new String(temp3, "UTF-8");

for(int i = 0; i < 20; i++){
System.out.println(temp3[i]);
}
//
//	String str = new String(temp, "UTF-8");
		System.out.println("odp na test: " + temp);
File file = new File("mlg.properties");
FileInputStream fis = new FileInputStream(file);
byte[] data = new byte[(int) file.length()];
fis.read(data);
fis.close();
String str = new String(data, "UTF-8");

System.out.println(str);
		
//System.out.println(str);

//temp = Client.connection("get_all", ip, port);
//System.out.println(temp);

//System.out.println("tablica test: " + test);
temp2 = Client.connection("klient pozdrawia Grzesia W", ip, port);
System.out.println("odp na pozdro: " + temp2);

//	System.out.println(Client.getLevel());


*/