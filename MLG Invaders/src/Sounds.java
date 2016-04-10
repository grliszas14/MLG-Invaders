import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
/*
*Klasa do odtwarzania dŸwiêków
*/
public class Sounds {

	public static synchronized void playSound(final String url) {
		new Thread(new Runnable() {
			public void run() {
				try {
					Clip clip = AudioSystem.getClip();
					AudioInputStream inputStream = AudioSystem.getAudioInputStream(Sounds.class.getResource(url));
					clip.open(inputStream);
					clip.start();
				} catch (Exception e) {
					System.out.println(e);
				}
			}
		}).start();
	}
}
