import  java.io.*; 
import java.net.URL; 
import javax.sound.sampled.*;

/**
    * This class plays sound effects.
*/
public class SoundPlayer {
    private boolean muted = false;

    /**
	 * Creates a new SoundPlayer object.
	 */
    public SoundPlayer() {
    };

    /**
    * Muteds & unmutes sound effects.
    * @param pitch The pitch the game is being played on.
    */
    public void toggleMute(Pitch pitch) {
        muted = !muted;
        pitch.updateMuteStatus(muted);
    }

    /**
	* Plays a given sound effect.
	* @param soundName The decimal to be rounded to 1 decimal place.
	*/
    public void playSound(String soundName) {
        
        try {
            if (muted == false) {
                URL url = this.getClass().getClassLoader().getResource(soundName); // Get url to sound file
                AudioInputStream audioIn = AudioSystem.getAudioInputStream(url); // Get audio input stream for url
                Clip clip = AudioSystem.getClip(); // Get audiosystem clip
                clip.open(audioIn); // Open the clip
                clip.start(); // Start
                clip.addLineListener(new LineListener(){
                    public void update(LineEvent e) {
                        if (e.getType() == LineEvent.Type.STOP) { // Wait for clip to finish
                            clip.drain(); // Clean up memory
                            clip.close(); // Close the clip
                        }
                    }
                });

            };
            
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();        
        }
    }
}