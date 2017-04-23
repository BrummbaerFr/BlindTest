/**
 * Classe Lecteur, qui lit le fichier audio (format .wav) passé en paramètre au constructeur.
 *
 * @author Richard BLONDEL
 * @version 1.0
 * @since 23-04-2017
 */
package BlindTest.Metier;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Lecteur implements LineListener {

	/**
	 * le booléen playCompleted indique si la lecture est terminée ou non.
	 */
	private boolean playCompleted;
	private File fichierAudio;
	private AudioInputStream audioStream;
	private AudioFormat format;
	private Clip audioClip;

	/**
	 * Constructeur du lecteur de musique.
	 * Lit seulement les fichiers audio .wav.
	 *
	 * @param fichierAudio le chemin absolu du fichier audio à lire
	 * @see AudioInputStream
	 */
	public Lecteur(String fichierAudio) {
		this.fichierAudio = new File(fichierAudio);

		try {
			this.audioStream = AudioSystem.getAudioInputStream(this.fichierAudio);
			this.format = audioStream.getFormat();
			DataLine.Info info = new DataLine.Info(Clip.class, format);

			this.audioClip = (Clip) AudioSystem.getLine(info);
			this.audioClip.addLineListener(this);
			this.audioClip.open(this.audioStream);
		} catch (UnsupportedAudioFileException e) {
			System.out.println("Ce fichier audio n'est pas supporté.");
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			System.out.println("La lecture de ce fichier audio n'est pas supportée.");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Erreur lors de la lecture du fichier audio.");
			e.printStackTrace();
		}
	}

	/**
	 * Lis le fichier audio.
	 */
	public void jouer() {
		try {
			this.audioClip.start();

		} catch (Exception e) {
			System.out.println("Erreur.");
		}

	}

	public void pause() {
		try {
			this.audioClip.stop();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void fermer() {
		try {
			audioClip.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Attend les évènements START et STOP.
	 *
	 * @param event l'évènement (START ou STOP) qui change l'état du booléen playCompleted
	 */
	@Override
	public void update(LineEvent event) {
		LineEvent.Type type = event.getType();

		if (type == LineEvent.Type.STOP) {
			playCompleted = true;
		}
	}
}