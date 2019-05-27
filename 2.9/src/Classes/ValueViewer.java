package Classes;
import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Label;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;

import javax.swing.JFrame;

public class ValueViewer extends JFrame {
	private static final long serialVersionUID = 1L;
	private static Canvas DebugCanvas = new Canvas();
	private static boolean start;
	ValueViewer(){
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(0, (int) (SplashX4.LEFT_ALIGNMENT),300, 800);
		setFocusable(true);
		setResizable(false);
		add(DebugCanvas);
		setVisible(true);
		setTitle("Variables");
		DebugCanvas.createBufferStrategy(3);}
		int [] oldpixels;
	public void Tick() {
		try {setBounds(SplashX4.game.getX() + 1000,SplashX4.game.getY(),300, 800);} catch (Exception e) {setBounds(0,0,300, 800);}
		
		BufferStrategy bufferStrategy = DebugCanvas.getBufferStrategy();
		Graphics graphics = bufferStrategy.getDrawGraphics();
		paint(graphics);		
		
		graphics.setFont(RenderHandler.title.deriveFont(10.0f));
	    graphics.setColor(java.awt.Color.BLACK);
		graphics.drawString("SplashX4.class:", 0, 10);
		graphics.drawString("SerialID: " + SplashX4.serialVersionUID, 0, 30);
		graphics.drawString("inFocus: " + SplashX4.inFocus, 0, 40);
		graphics.drawString("ResourcePath: " + SplashX4.ResourcePath, 0, 50);
		graphics.drawString("FrameLimit: " + SplashX4.FrameLimit, 0, 60);
		graphics.drawString("WindowX: " + SplashX4.WindowX, 0, 70);
		graphics.drawString("WindowY: " + SplashX4.WindowY, 0, 80);
		graphics.drawString("CurrentWindow: " + SplashX4.CurrentWindow, 0, 90);
		graphics.drawString("vehicle: " + GameHandler.getVehicleAsString(SplashX4.CurrentVehicle), 0, 100);
		graphics.drawString("CurrentMap: " + SplashX4.CurrentMap, 0, 110);
		graphics.drawString("KeyPressedInFrame: " + SplashX4.KeyPressedInFrame, 0, 120);
		graphics.drawString("DeveloperMode: " + SplashX4.DeveloperMode, 0, 130);
		graphics.drawString("DebugPrepared: " + SplashX4.DebugPrepared, 0, 140);
		graphics.drawString("restart: " + SplashX4.restart, 0, 150);
		//
		graphics.drawString("mapHeight: " + SplashX4.mapHeight, 0, 170);
		graphics.drawString("mapWidth: " + SplashX4.mapWidth, 0, 180);
		graphics.drawString("vehicleCount: " + SplashX4.vehicleCount, 0, 190);
		graphics.drawString("TurretCount: " + SplashX4.TurretCount, 0, 200);
		graphics.drawString("Energy: " + SplashX4.Energy, 0, 210);
		graphics.drawString("Gas: " + SplashX4.Gas, 0, 220);
		graphics.drawString("Material: " + SplashX4.Material, 0, 230);
		graphics.drawString("DebugX: " + SplashX4.DebugX, 0, 240);
		graphics.drawString("DebugY: " + SplashX4.DebugY, 0, 250);
		graphics.drawString("ControllerRule: " + SplashX4.ControllerRule, 0, 260);
		graphics.drawString("FreeCam: " + SplashX4.FreeCam, 0, 270);
		graphics.drawString("TurretOnLevel: " + SplashX4.TurretOnLevel, 0, 280);
		graphics.drawString("FirstTime: " + SplashX4.FirstTime, 0, 290);

		
		
		
		
		graphics.drawString("SoundHandler.class:", 0, 310);
		graphics.drawString("MusicEnabled: " + SoundHandler.MusicEnabled, 0, 330);
		graphics.drawString("EngineEnabled: " + SoundHandler.EngineEnabled, 0, 340);
		graphics.drawString("Booted: " + SoundHandler.Booted, 0, 350);
		graphics.drawString("STIndex: " + SoundHandler.STIndex, 0, 360);
		graphics.drawString("sfxQCount: " + SoundHandler.sfxQCount, 0, 370);
		graphics.drawString("TrackCount: " + SoundHandler.TrackCount, 0, 380);
		boolean isactive = false;
		try {isactive = SoundHandler.Music.isActive();} catch (Exception e) {}
		graphics.drawString("Music.isactive: " + isactive, 0, 390);
		isactive = false;
		try {isactive = SoundHandler.sfx.isActive();} catch (Exception e) {}
		graphics.drawString("sfx.isactive " + isactive, 0, 400);
		
		
		
		graphics.drawString("CharacterHandler.class:", 0, 420);
		graphics.drawString("CharX: "+ CharacterHandler.CharX, 0, 440);
		graphics.drawString("CharY: "+ CharacterHandler.CharY, 0, 450);
		
		
		graphics.drawString("RenderHandler.class:", 0, 470);
		graphics.drawString("OffsetX: " + RenderHandler.offsetx, 0, 490);
		graphics.drawString("OffsetY: " + RenderHandler.offsety, 0, 500);
		graphics.drawString("PrevOffX: " + RenderHandler.prevoffx, 0, 510);
		graphics.drawString("PrevOffY: " + RenderHandler.prevoffy, 0, 520);
		graphics.drawString("MMOffsetX: " + RenderHandler.MMOffsetX, 0, 530);
		graphics.drawString("MMOffsetY: "  + RenderHandler.MMOffsetY, 0, 540);
		
		graphics.drawString("GameHandler.class:", 0, 560);
		graphics.drawString("TickRule: "  + GameHandler.TickRule, 0, 580);
		graphics.drawString("Ticks: "  + GameHandler.Ticks, 0, 590);
		graphics.drawString("Frames: "  + GameHandler.Frames, 0, 600);
		graphics.drawString("KeyPresses: "  + GameHandler.KeyPresses, 0, 610);
		graphics.drawString("UpTime: " + Duration.ofNanos(SplashX4.UpTime), 0, 620);
		graphics.drawString("debug: " + FileHandler.FieldMemory.length, 0, 640);
		graphics.drawString("MenuState: " + GameHandler.MenuState, 0, 650);
		graphics.drawString("MenuTick: " + GameHandler.MenuTick, 0, 660);
		
		
		
		graphics.drawString("SelectorHandler.class:", 0, 800);
		graphics.drawString("XInputHandler.class:", 0, 800);
		graphics.drawString("SelectorHandler.class:", 0, 800);
		graphics.drawString("CustomMapLoader.class:", 0, 800);
		
		
		
		graphics.dispose();
		bufferStrategy.show();
	}}
	

	
