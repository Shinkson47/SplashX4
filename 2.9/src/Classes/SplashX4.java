package Classes;
import java.awt.Canvas;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.KeyEventDispatcher;
import java.awt.Label;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.studiohartman.jamepad.ControllerManager;

import Enumerators.Windows;



public class SplashX4 extends JFrame implements Runnable, KeyListener, KeyEventDispatcher {
	public static final long serialVersionUID = 4167944352484193531L;
	//Base blocks
	public static Canvas canvas = new Canvas();
	private RenderHandler renderer;
	static JFileChooser chooser;
	public static Path ResourcePath;
	public static Thread gameThread;
	static SplashX4 game;
	public static ValueViewer viewer = null;
	
	
	//Numerics
	public static int FrameLimit = 30, WindowX = 1000, WindowY = 800, mapHeight = 0, mapWidth = 0, vehicleCount = -1, TurretCount = 0, Energy = 150, Gas = 10, Material = 200, DebugX = 0, DebugY = 0, ControllerRule = 0, restartcount = 0;
	public static long StartTime = 0, UpTime = 0;
	
	//Booleans
	public static boolean inFocus = false, KeyPressedInFrame = false, DeveloperMode = false, DebugPrepared = false, restart = false, FreeCam = false, FirstTime = true;
    public static boolean updateWindow = false;
	
	
	//Strings
	public static String[] Vehicles = new String[8];
	public static String[][]Turrets = new String[200][6];
	public static Enumerators.TurretOnLevel TurretOnLevel = Enumerators.TurretOnLevel.No;
	
	//Custom enumerators
	public static Windows CurrentWindow = Windows.Menu;
	public static Enumerators.Vehicles CurrentVehicle = Enumerators.Vehicles.Nothing;
	public static Enumerators.MapList CurrentMap = Enumerators.MapList.Map1;
	
	//Objects
	Label l;
	JLabel imageLabel = new JLabel();
	
	
	//Instantiate
	public SplashX4(){
		boolean found = false;
		//Locate resource location
		 if (Files.exists(Paths.get("./Resources/AppData/Preferences.XPref"))) {
			 found = true; 
			 ResourcePath = Paths.get("./Resources");
			 } else {
			 }
		 
		if (found == false) {
			JOptionPane.showMessageDialog(canvas, "The resources folder can't be found, please locate 'Resources.XLocator' within the resources.", "Cannot locate resources", JOptionPane.OK_OPTION);
			try {
				EventQueue.invokeAndWait(new Runnable() {
				    @Override
				    public void run() {
				        String folder = System.getProperty("user.dir");
				        JFileChooser chooser = new JFileChooser(folder);
				  
				        chooser.setDialogTitle("Locate Resources.");;
					    chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
					    chooser.setAcceptAllFileFilterUsed(false);
					    FileNameExtensionFilter filter = new FileNameExtensionFilter(".XLocator", "XLocator");
					    chooser.setFileFilter(filter);
					    @SuppressWarnings("unused")
						int result = chooser.showOpenDialog(null);
					  if (!Files.exists(Paths.get(chooser.getCurrentDirectory() + "/AppData/Preferences.XPref"))) {
					  JOptionPane.showMessageDialog(canvas, "Invalid selection", "Error", JOptionPane.OK_OPTION);
					  System.out.println("CRASH HANDLER: Thread Closed: " + String.valueOf(UpTime) + " " + restartcount + " " + 3);	
					  Runtime.getRuntime().halt(3);		
					  }   
					  ResourcePath = chooser.getCurrentDirectory().toPath();
				    }
				});
			} catch (InvocationTargetException e) {
					e.printStackTrace();
			} catch (InterruptedException e) {
					e.printStackTrace();
			}
		}
		FileHandler.Boot();
		if (FirstTime) {JOptionPane.showMessageDialog(canvas, "Please note: due to key regestering in Java, key presses will become unresponsive when focus changes on the window. When this happens, press TAB.", "Notice", JOptionPane.OK_OPTION); 
			JOptionPane.showMessageDialog(canvas, "Registered key presses are marked by a flicker in backbround colour. This can be toggled off by pressing '#'.", "Notice", JOptionPane.OK_OPTION);		
			FileHandler.WriteFile("/AppData/Preferences.XPref", 0, 3, "false");}
		if (DeveloperMode) {WindowY += 150;}

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0,0, WindowX,WindowY);
		setLocationRelativeTo(null);
		setFocusable(true);
		addKeyListener(this);
		setResizable(false);
		
		l=new Label();  
		l.setBounds(0,0,0,0);  
		l.setVisible(true);
		add(l);
        add(canvas);
		setVisible(true);
		setTitle("SplashX4");
		CurrentWindow = Windows.Menu;	
		SoundHandler.PlayMisc("MenuTheme", false);
		try {XInputHandler.controllers.quitSDLGamepad();} catch (Exception e) {}
		XInputHandler.controllers = new ControllerManager();
		XInputHandler.controllers.initSDLGamepad();
		XInputHandler.ControllerConnected();
		canvas.createBufferStrategy(3);
		renderer = new RenderHandler(getWidth(), getHeight());
		RenderHandler.title = RenderHandler.GetFont("Xirod");
		if (FileHandler.ReadFile("/Appdata/Preferences.XPref", true, true, 0, 5).equals("true")) {
			int i = JOptionPane.showConfirmDialog(canvas, "Triggered to auto load a Map Project. Continue?");
			if (i == 0) {
				FileHandler.WriteFile("/AppData/Preferences.XPref", 0, 5, "false");
				CustomMapLoader.ResourcePath = Paths.get(FileHandler.ReadFile(null, false, true, 0, 6));
				CustomMapLoader.LoadMap(true);
			}//yes
			if (i == 2) {System.out.println("CRASH HANDLER: Thread Closed: " + String.valueOf(UpTime) + " " + restartcount + " " + 4);	Runtime.getRuntime().halt(4);}//cancel
			if (i == -1) {System.out.println("CRASH HANDLER: Thread Closed: " + String.valueOf(UpTime) + " " + restartcount + " " + 4);	Runtime.getRuntime().halt(4);}//close
		
		}

		if (FileHandler.ReadFile("/Appdata/Preferences.XPref", true, true, 0, 7).equals("true")) {viewer = new ValueViewer();}
			}
	

	static void StartGame() {	
		FileHandler.Boot();
		RenderHandler.offsetx = 0 + CharacterHandler.CharX - 1;
		RenderHandler.offsety = 0 + CharacterHandler.CharY - 1;
		CharacterHandler.Health = 87;
		DebugX = 0;
		DebugY = 0;
		GameHandler.StartTime = System.nanoTime();
		StatisticStore.Initialise();
		GameHandler.ActiveBullets[0][0] = String.valueOf(0);
		CurrentWindow = Windows.Game;
		SoundHandler.NextTrack();
	}
	
	public void WindowBoundUpdate() {
		setBounds(0,0,WindowX, WindowY);
		setLocationRelativeTo(null);
	}
	

	@Override
	 public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == 40) {DebugX++;
		RenderHandler.MMOffsetX++;
		return;
		} //down 
		if (e.getKeyCode() == 38) {DebugX--;
		RenderHandler.MMOffsetX--;
		return;
		} // up
		if (e.getKeyCode() == 37) {DebugY--;
		RenderHandler.MMOffsetY--;
		return;
		} // left
		if (e.getKeyCode() == 39) {DebugY++;
		RenderHandler.MMOffsetY++;
		return;
		} //right

		RenderHandler.MMOffsetX = 0;
		RenderHandler.MMOffsetY = 0;
		char key = e.getKeyChar();
		KeyPressedInFrame = true;	
		if (key == '¬') {RestartThread();}
		switch (CurrentWindow) {
		

		
		case Game:
		
		
		//FOR DeveloperMode
		if (key == '-') {
		DeveloperMode = !DeveloperMode;
		FileHandler.WriteFile("/AppData/Preferences.XPref", 1, 5, String.valueOf(DeveloperMode));
		if (DeveloperMode) {
			WindowY += 150;			
		} else {WindowY -= 150;}
			WindowBoundUpdate();
		return;
		}
		//Escape for end game
		if (e.getKeyCode() == 27) {
			if (SelectorHandler.isEnabled()) {SelectorHandler.DisableSelector(false, true);}
			else {SoundHandler.Pause(); CurrentWindow = Windows.Pause;}};
		//Selector
		if (SelectorHandler.isEnabled()) {
			if (key == 'w' || key == 'W') {SelectorHandler.MoveSelector("UP");}
			if (key == 'a' || key == 'A') {SelectorHandler.MoveSelector("LEFT");}
			if (key == 's' || key == 'S') {SelectorHandler.MoveSelector("DOWN");}
			if (key == 'd' || key == 'D') {SelectorHandler.MoveSelector("RIGHT");}
			if (key == ' ') {SelectorHandler.ConfirmSelection();}
			return;
		}
		

		//FOR Freecam
		if (FreeCam) {
			if (key == 'w' || key == 'W') {
				if (RenderHandler.offsetx > 0) {RenderHandler.offsetx--;}}
			if (key == 'a' || key == 'A') {
				if (RenderHandler.offsety > 0) { RenderHandler.offsety--;}}
			if (key == 's' || key == 'S') {
				RenderHandler.offsetx++;}
			if (key == 'd' || key == 'D') {
				RenderHandler.offsety++;}
			if (!DeveloperMode) {return;}
		}
		
		
		if (DeveloperMode) {
			if (key == '1') {FreeCam = true; RenderHandler.prevoffx = RenderHandler.offsetx; RenderHandler.prevoffy = RenderHandler.offsety;}
			if (key == '2') {FreeCam = false; RenderHandler.offsetx = RenderHandler.prevoffx; RenderHandler.offsety = RenderHandler.prevoffy;}
			if (key == '3') {SelectorHandler.EnableSelector("Teleport", CharacterHandler.CharX, CharacterHandler.CharY, 10000);}
			if (key == '4') {GameHandler.NewEnemy(CharacterHandler.CharX - 2, CharacterHandler.CharY - 2);}
			if (key == '0') {System.out.println("Splash: Thread forcefully closed by user. [Overiding error code] 101"); gameThread.stop();}
			DeveloperMode = false;
			return;
		}
		
		//Vehicle
		if (key == '1') {CurrentVehicle = GameHandler.getVehicleAsVehicle(Vehicles[0]); CharacterHandler.Update();}
		if (key == '2') {CurrentVehicle = GameHandler.getVehicleAsVehicle(Vehicles[1]); CharacterHandler.Update();}
		if (key == '3') {CurrentVehicle = GameHandler.getVehicleAsVehicle(Vehicles[2]); CharacterHandler.Update();}
		if (key == '4') {CurrentVehicle = GameHandler.getVehicleAsVehicle(Vehicles[3]); CharacterHandler.Update();}
		
		//Selector
		if (key == ' ') {
			if (CurrentVehicle.equals(Enumerators.Vehicles.Tank)) {
				SelectorHandler.EnableSelector("Shoot", CharacterHandler.CharX, CharacterHandler.CharY, 5);}
			if (CurrentVehicle.equals(Enumerators.Vehicles.Builder)) {
				SelectorHandler.EnableSelector("Build", CharacterHandler.CharX, CharacterHandler.CharY, 1);}
			if (CurrentVehicle.equals(Enumerators.Vehicles.Excavator)) {
				SelectorHandler.EnableSelector("Excavate", CharacterHandler.CharX, CharacterHandler.CharY, 1);}
		}
		
		//FOR SoundHandler
		if (key == 'i' || key == 'I') {SoundHandler.PrevTrack();}
		if (key == 'o' || key == 'O') {SoundHandler.PlayStop();}
		if (key == 'p' || key == 'P') {SoundHandler.NextTrack();}
		
		//FOR CharacterHandler
		if (key == 'w' || key == 'W') {CharacterHandler.CharMove("UP", null);}
		if (key == 'a' || key == 'A') {CharacterHandler.CharMove("LEFT", null);}
		if (key == 's' || key == 'S') {CharacterHandler.CharMove("DOWN", null);}
		if (key == 'd' || key == 'D') {CharacterHandler.CharMove("RIGHT", null);}
		break;
		case BuildSelector:
			if (e.getKeyCode() == 37) {} // left
			if (e.getKeyCode() == 39) {} //right
			if (key == 'w' || key == 'W') {SelectorHandler.SelectorDirection = "UP";}
			if (key == 'a' || key == 'A') {SelectorHandler.SelectorDirection = "LEFT";}
			if (key == 's' || key == 'S') {SelectorHandler.SelectorDirection = "DOWN";}
			if (key == 'd' || key == 'D') {SelectorHandler.SelectorDirection = "RIGHT";}
			if (key == ' ') {SelectorHandler.ConfirmSelection();}	
			if (key == '3') {SelectorHandler.ActionToPerform = "Bridge"; SelectorHandler.ConfirmSelection();}
		break;
		case Menu:
		GameHandler.resetMenu();
		if (key == 'o' || key == 'O') {SoundHandler.PlayStop();}
		if (key == 'l' || key == 'L') {CustomMapLoader.LoadMap(false);}
		if (e.getKeyCode() == 10) {StartGame();}
		if (key == 'm' || key == 'M') {CurrentWindow = Enumerators.Windows.MapSelector;}
		if (e.getKeyCode() == 27) {Runtime.getRuntime().halt(0);}
		break;
		
		case MapSelector:
		if (e.getKeyCode() == 27) {CurrentWindow = Enumerators.Windows.Menu;};
		if (key == '1') {CurrentMap =  Enumerators.MapList.Map1;CurrentWindow = Enumerators.Windows.Menu;}
		if (key == '2') {CurrentMap =  Enumerators.MapList.Map2;CurrentWindow = Enumerators.Windows.Menu;}
		if (key == '3') {CurrentMap =  Enumerators.MapList.Map3;CurrentWindow = Enumerators.Windows.Menu;}
		if (key == '4') {CurrentMap =  Enumerators.MapList.Map4;CurrentWindow = Enumerators.Windows.Menu;}
		if (key == '5') {CurrentMap =  Enumerators.MapList.Map5;CurrentWindow = Enumerators.Windows.Menu;}
		if (key == '6') {CurrentMap =  Enumerators.MapList.Map6;CurrentWindow = Enumerators.Windows.Menu;}
		if (key == '7') {CurrentMap =  Enumerators.MapList.Map7;CurrentWindow = Enumerators.Windows.Menu;}
		if (key == '8') {CurrentMap =  Enumerators.MapList.Map8;CurrentWindow = Enumerators.Windows.Menu;}
		if (key == '9') {CurrentMap =  Enumerators.MapList.Map9;CurrentWindow = Enumerators.Windows.Menu;}
				
			
		
		break;
		
		case Bridging:
			if (e.getKeyCode() == 27) {CurrentWindow = Enumerators.Windows.Game;}
		break;
		case Pause:
			if (e.getKeyCode() == 27) {CurrentWindow = Enumerators.Windows.Game; SoundHandler.Unpause();}
			if (key == '3') {EndGame("Quit!");}
			
		break;
		
		case GameOver:
			if (e.getKeyCode() == 27) {RestartThread();}
		break;
		}	
		
		
		   	}
	 
	@Override public void keyTyped(KeyEvent e) {}	@Override public void keyReleased(KeyEvent e) {}
	
	public void render() {
		GameHandler.Frames++;
			BufferStrategy bufferStrategy = canvas.getBufferStrategy();
			Graphics graphics = bufferStrategy.getDrawGraphics();
			super.paint(graphics);		
			renderer.render(graphics);
			inFocus = this.isFocused();
			graphics.dispose();
			bufferStrategy.show();}
	
	
	public void run() {
		long lastTime = System.nanoTime(); //long 2^63
		double nanoSecondConversion = 1000000000.0 / FrameLimit; 
		double changeInSeconds = 0;
		
		while(true) {
			if (!(viewer == null)) {viewer.Tick();}
			UpTime = System.nanoTime() - StartTime;
			long now = System.nanoTime();
			changeInSeconds += (now - lastTime) / nanoSecondConversion;
			if (inFocus == true) {
				
			while(changeInSeconds >= 1) {
				if (updateWindow) {WindowBoundUpdate(); updateWindow = false;}
				
				if (CurrentWindow.equals(Enumerators.Windows.Game) || CurrentWindow.equals(Enumerators.Windows.Bridging)) {GameHandler.Tick(); GameHandler.PlayTime = (System.nanoTime() - GameHandler.StartTime); StatisticStore.PlayTime = GameHandler.PlayTime;} else {
					GameHandler.updateController();}
				if (CurrentWindow == Enumerators.Windows.Menu) {GameHandler.MenuTick();}
				
				if (SoundHandler.EngineEnabled) {
					setTitle(String.valueOf("Splash X4, DEV2.8 - Track: " + SoundHandler.getST()));
				} else {
					setTitle(String.valueOf("Splash X4, DEV2.8"));
				}
				
				changeInSeconds--;
				ControllerRule++;
				if (ControllerRule == 5) {
					GameHandler.updateController();
					ControllerRule = 0;
				}
			}} else {
				if (CurrentWindow == Enumerators.Windows.Game) {SoundHandler.Pause(); CurrentWindow = Enumerators.Windows.Pause;}
			}
			
			if (restart) {RestartThread();}
			render();	

			lastTime = now;}}

	

	public static void main(String[] args){
		StartTime = System.nanoTime();
		while (true){
			try {
		gameThread = null;
		game = null;
		game = new SplashX4();
		gameThread = new Thread(game);
		gameThread.setName("fail");
		gameThread.start();
		while (gameThread.isAlive()) {}
		restartcount++;
		if (gameThread.getName().equals("restart")) {
					System.out.println("CRASH HANDLER: Thread closed, but actively identified itself to not be a crash.");
				} else {
					System.out.println("CRASH HANDLER: Thread Closed: " + String.valueOf(UpTime) + " " + restartcount + " " + 1);		
					JOptionPane.showMessageDialog(canvas, "Splash X4 has encountered a fatal runtime problem and has been forced to close. Press OK to terminate.");
					Runtime.getRuntime().halt(1);
				}
			

		

		
		}catch (OutOfMemoryError e) {
			try {
				gameThread = null;
				game = null;
			} catch (Exception e1) {}
			System.out.println("CRASH HANDLER: Thread Closed: " + String.valueOf(UpTime) + " " + restartcount + " " + 2);	
			JOptionPane.showMessageDialog(canvas, "SplashX4 has been closed because it has ran out of memory!", "Notice", JOptionPane.OK_OPTION);		
			JOptionPane.showMessageDialog(canvas, "If you have more RAM to allocate, reffer to the manual on how to allocate more.", "Notice", JOptionPane.OK_OPTION);
			Runtime.getRuntime().halt(2);
		}}}

	public static void EndGame(String cause) {
		StatisticStore.Cause = cause;
		SoundHandler.PlayMisc("Game Over", false);
		SelectorHandler.DisableSelector(false, true);
		CurrentVehicle = null;
		SoundHandler.STIndex = 0;
		DeveloperMode = false;
		DebugPrepared = false;
		Energy = 150;
		Gas = 10;
		Material = 200;
		mapHeight = 0;
		mapWidth = 0; vehicleCount = -1; 
		TurretCount = 0;
		Vehicles = new String[4];
		Turrets = new String[200][6];
		FreeCam = false;
		TurretOnLevel = Enumerators.TurretOnLevel.Unknown;
		RenderHandler.ActiveCharSet = new String [1000] [1000];
		RenderHandler.DefaultCharSet = new String [1000] [1000];
		RenderHandler.ItemSet = new String [1000] [1000];
    	CurrentWindow = Enumerators.Windows.GameOver;
    	restart = false;
	}


	@SuppressWarnings("deprecation")
	public void RestartThread() {
		try {viewer.setVisible(false);}catch(Exception e) {}
		SoundHandler.Shutdown();
		setVisible(false); 
		gameThread.setName("restart");
		gameThread.stop();
	}
	
	@Override
	public boolean dispatchKeyEvent(KeyEvent e) {
	return false;
	}}