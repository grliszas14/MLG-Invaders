import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.StringReader;
import java.util.Properties;
/**
 * Class to configure parameters of the game
 *
 */
public final class Config {
	/**
	 * Variable storing properties file
	 */
	private static Properties properties ;
	
	static {
		try {
			setProperties(get("src/mlg.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Method downloading data from properties file
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

	/**
	 * properties getter
	 * @return properties
	 */
	public static Properties getProperties() {
		return properties;
	}
	/*
	 * properties setter
	 */
	private static void setProperties(Properties properties) {
		Config.properties = properties;
	}
}