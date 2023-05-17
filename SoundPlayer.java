import  java.io.*; 
import java.net.URL; 
import javax.sound.sampled.*;
import javax.swing.*;


public class SoundPlayer {
    private boolean muted = false;

    public SoundPlayer() {
    };

    public void ToggleMute(Pitch pitch) {
        muted = !muted;
        pitch.UpdateMuteStatus(muted);
    }

    
    public void PlaySound(String soundName) {
        
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