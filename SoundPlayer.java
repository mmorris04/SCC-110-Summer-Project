import  java.io.*; 
import java.net.URL; 
import javax.sound.sampled.*;
import javax.swing.*;


public class SoundPlayer {
    public SoundPlayer() {
        PlaySound("fanfare.wav");
    };

    public void PlaySound(String soundName) {
        try {
            URL url = this.getClass().getClassLoader().getResource(soundName);
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
            Clip clip = AudioSystem.getClip(); 
            clip.open(audioIn);
            clip.start();
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