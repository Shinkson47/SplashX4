package Classes;
import java.io.BufferedInputStream;
import java.io.FileInputStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JOptionPane;

public class SoundHandler {
	public static Clip sfx = null, Music = null;
	public static boolean MusicEnabled = true, EngineEnabled = false, Booted = false;
	static int STIndex = 0;
	static int sfxQCount = 0;
	static final int TrackCount = 22;
	private static AudioInputStream audioInputStream;   
	private static String[] sfxQ = new String[4];
	private static long playpos = 0;
	    //Channel true = sfx, else music
	    private static void PlayFile(String filename, boolean Chanel){
	    	try {
	    		   audioInputStream = AudioSystem.getAudioInputStream(new BufferedInputStream(new FileInputStream(filename)));
	            if (Chanel) {          	
	            	try {if (sfx.isActive()) {return;}} catch (Exception e) {}
	            	try {	
	            		sfx.stop();
	            		sfx.drain();
	            		sfx.close();
	            	}catch (Exception e) {}
	            	sfx = AudioSystem.getClip();
	            	sfx.open(audioInputStream);
	            	sfx.start();
	            } else {
	            	if (!MusicEnabled) {return;}
	            	try {	
	            		Music.stop();
	            		Music.drain();
	            		Music.close();
	            		}catch (Exception e) {}

	            	Music = AudioSystem.getClip();
	            	Music.open(audioInputStream);
	            	Music.start();         	
	            }
	        } catch (Exception ex) {
	            ex.printStackTrace();
	            Error();
	        }
	    }
	    
	    
	    //Music Handler for external
	    private static void PlayTrack() {
	    	String Location = String.valueOf(SplashX4.ResourcePath + "/Audio/Runtime/ST" + STIndex + ".wav");	 
	    	PlayFile(Location, false);
	    }
	    
	    public static void Boot() {
	    	if (Booted) {return;}
	    	EngineEnabled = true;
	    	String Location = String.valueOf(SplashX4.ResourcePath + "/Audio/Runtime/ST" + STIndex + ".wav");
	    	PlayFile(Location, false);
	    	if (!EngineEnabled) {JOptionPane.showMessageDialog(SplashX4.canvas, "The sound engine was unable to start up correctly, so it has been disabled. Please check your resources and try reloading SplashX4. ","Audio Boot Error", JOptionPane.OK_OPTION);
	    	} else {
	    		FileHandler.WriteFile("/AppData/Preferences.XPref", 0, 4, "true");
	    	}
	    	try {Music.stop(); Music.drain(); Music.flush();} catch (Exception e) {}
	    MusicEnabled = EngineEnabled;	
	    Booted = true;
	    }
	    	
	    
	    public static void SFX(String Effect) {
	    	if (!EngineEnabled) {return;}
	    	String Location = String.valueOf(SplashX4.ResourcePath + "/Audio/Runtime/" + Effect + ".wav");
	    	PlayFile(Location, true);
	    }
	    
	    public static void PlayMisc(String name, boolean voice) {
	    	if (!EngineEnabled) {return;}
	    	String Location = String.valueOf(SplashX4.ResourcePath + "/Audio/Runtime/" + name + ".wav");
	    	PlayFile(Location, voice);
	    }
	    
	    public static void Pause() {
	    	try {
	    		playpos = Music.getMicrosecondPosition();
	    		Music.stop();
	    		MusicEnabled = false;
	    	} catch (Exception e) {}
	    }
	    
	    public static void Unpause() {
	    	try {
	    		Music.setMicrosecondPosition(playpos);
	    		Music.start();
	    		MusicEnabled = true;
	    	} catch (Exception e) {}
	    }

	    public static void NextTrack() {
	    	if (!EngineEnabled) {return;}
	    	if (STIndex >= TrackCount) {STIndex = 0;} else {STIndex++;}
	    	PlayTrack();
	    }
	    
	    public static void PrevTrack() {
	    	if (!EngineEnabled) {return;}
	    	if (STIndex <= 0) {STIndex = TrackCount;} else {STIndex--;}
	    	PlayTrack();
	    }
	    
	    public static void StartMusic () {
	    	if (!EngineEnabled) {return;}
	    	MusicEnabled = true;
	    	STIndex = 0;
	    	String Location = String.valueOf(SplashX4.ResourcePath + "/Audio/Runtime/ST" + STIndex + ".wav");
	    	PlayFile(Location, false);
	    }
	    
	    public static void PlayIndex(int index) {
	    	if (!EngineEnabled) {return;}
	    	MusicEnabled = true;
	    	STIndex = 0;
	    	String Location = String.valueOf(SplashX4.ResourcePath + "/Audio/Runtime/ST" + index + ".wav");
	    	PlayFile(Location, false);
	    }
	    
	    public static void PlayStop() {
	    	if (!EngineEnabled) {return;}
	    	MusicEnabled = !MusicEnabled;
	    	if (MusicEnabled) {
	    		StartMusic();
	    	} else {
	    		try {Music.stop(); Music.drain(); Music.flush();}catch (Exception e) {}
	    	}}
	    
	    public static void Tick() {
	    	if (!EngineEnabled) {return;}
	    	try {if ((!Music.isActive()) && MusicEnabled){NextTrack();}} catch (Exception e) {}
	    	if (!MusicEnabled) {
	    		try {if (!Music.isActive()){Music.stop(); Music.drain();}} catch (Exception e) {}
	    	}
	    	try {if ((sfxQCount > 0) & !(sfx.isActive())) {
	    	 PlayFile(sfxQ[sfxQCount], true);
	    	 sfxQCount--;}} catch (Exception e) {
	    	}}
	    
	    public static int getST() {
	    	return STIndex;
	    }
	    
	    public static void DisableSoundEngine() {
	    	EngineEnabled = false;
	    	MusicEnabled = false;
	    	Booted = false;
	    	try {Music.stop(); Music.drain(); Music.flush();}catch (Exception e) {}
	    	try {sfx.stop(); sfx.drain(); sfx.flush();}catch (Exception e) {}
	    	FileHandler.WriteFile(SplashX4.ResourcePath.toString() + "/AppData/Preferences.XPref", 0, 4, String.valueOf(EngineEnabled));
	    }
	    
	    public static void EnableSoundEngine() {
	    	EngineEnabled = true;
	    	MusicEnabled = true;
	    	FileHandler.WriteFile(SplashX4.ResourcePath.toString() + "/AppData/Preferences.XPref", 0, 4, String.valueOf(EngineEnabled));
	    }
	    
	    private static void Error() {
	    	EngineEnabled = false;
	    	JOptionPane.showMessageDialog(SplashX4.canvas, "The sound engine has just disabled itself due to an error, if this persists try redownloading the resources folder.","Error monitor", JOptionPane.OK_OPTION);
	    	FileHandler.WriteFile("/AppData/Preferences.XPref", 0, 4, "false");
	    }
	    
	    public static void Shutdown() {
	    	try {Music.stop(); Music.drain(); Music.flush();}catch (Exception e) {}
	    	try {sfx.stop(); sfx.drain(); sfx.flush();}catch (Exception e) {}
	    	EngineEnabled = false;
	    	Booted = false;
	    }

}

	

