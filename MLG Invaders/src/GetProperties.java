import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.StringReader;
import java.util.Properties;

public final class GetProperties {

	private static Properties properties ;
	
	/*
	 * Podanie zrodla pliku konfiguracyjnego
	 */
	static {
		try {
			setProperties(get("src/mlg.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * Pobieranie danych z pliku 
	 */
	private static Properties get(String file) throws IOException {
		Properties properties = new Properties();
		File f = new File(file);

		RandomAccessFile raf = new RandomAccessFile(f, "r");
		byte[] b = new byte[(int) raf.length()];
		raf.read(b);
		String prop = new String(b);

		properties.load(new StringReader(prop));
		raf.close();
		return properties;
	}

	public static Properties getProperties() {
		return properties;
	}

	private static void setProperties(Properties properties) {
		GetProperties.properties = properties;
	}
}
