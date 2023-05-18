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
                URL url = this.getClass().getClassLoader().getResource(soundName);
                AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
                Clip clip = AudioSystem.getClip(); 
                clip.open(audioIn);
                clip.start();
                clip.addLineListener(new LineListener(){
                    public void update(LineEvent e) {
                        if (e.getType() == LineEvent.Type.STOP) {
                            clip.drain();
                            clip.close();
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