import  java.io.*; 
import java.net.URL; 
import javax.sound.sampled.*;
import javax.swing.*;


public class SoundPlayer {
    private boolean muted = false;

    public SoundPlayer() {
    };

    public void MuteSound() {
        muted = true;
    }

    public void UnmuteSound() {
        muted = false;
    }
    
    public void PlaySound(String soundName) {
        
        try {
            if (muted == false) {
                URL url = this.getClass().getClassLoader().getResource(soundName);
                AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
                Clip clip = AudioSystem.getClip(); 
                clip.open(audioIn);
                clip.start();
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