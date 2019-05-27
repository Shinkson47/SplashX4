package Classes;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.File;
import java.util.Random;

import javax.swing.ImageIcon;
public class RenderHandler 
{
	private BufferedImage view;
	private int[] pixels;
	public static int offsetx = 0, prevoffx = 0, MMOffsetX = 0;
	public static int offsety = 0, prevoffy = 0, MMOffsetY = 0;
	int KeyFrame = 0;
	public static String[][] DefaultCharSet = new String[1000][1000], ActiveCharSet = new String[50][50], ItemSet = new String[50][50];
	public static Font title;
	
	
	public RenderHandler(int width, int height) 
	{
		//Create a BufferedImage that will represent our view.
		view = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

		//Create an array for pixels
		pixels = ((DataBufferInt) view.getRaster().getDataBuffer()).getData();
	}
	
	public void render(Graphics graphics)
	{
		graphics.setFont(title.deriveFont(10.0f));
	    graphics.setColor(java.awt.Color.WHITE);
		//Drawer background
		Random rnd = new Random();
		int colour;
		
		if (SplashX4.inFocus == true) {
			if (SplashX4.KeyPressedInFrame) {
				
				colour = 0x404080; KeyFrame++; if (KeyFrame > 5) {
					GameHandler.KeyPresses++;
					SplashX4.KeyPressedInFrame = false; KeyFrame = 0;}
				} else {
					if (XInputHandler.ControllerIsConnected && SplashX4.CurrentWindow == Enumerators.Windows.Menu) {colour = 0x15db36;} else {
					colour = 0x303080;}
					
				}
			for(int index = 0; index < pixels.length; index++) {pixels[index] = colour;} }
		else {for(int index = 0; index < pixels.length; index++) {pixels[index] = (int)(rnd.nextInt() * 0xFFFFFF);}		}
		graphics.drawImage(view, 0, 0, view.getWidth(), view.getHeight(), null);
		if(!SplashX4.inFocus) {return;}
		
		
		
		switch (SplashX4.CurrentWindow) {
		
		case Menu:
			graphics.drawImage(getImage("/UI/Menu Items/Overlay.png"), 0, 0, null);
			if (GameHandler.MenuState) {
				graphics.drawImage(getImage("/UI/Menu Items/CO.png"), 0, 0, null);
			} else {
				graphics.drawImage(getImage("/UI/Menu Items/MO.png"), 0, 0, null);
			}
			
			break;
		case Game:
		case Bridging:
		case Pause:
		case BuildSelector:
				graphics.drawImage(getImage("/UI/Game Items/GameBacking.png"), 0, 0, null);
				for (int x = 0; x <= 5; x++) {//Rows
				for (int y = 0; y <= 13; y++) {//Columns
					String texture;
					//MapLayer 1
					if (DefaultCharSet[x + offsetx][y + offsety] == null) {texture = "mapnull";} else {texture = DefaultCharSet[x + offsetx][y + offsety];}
					graphics.drawImage(getImage("/Textures/" + texture + ".png"), 64 * y + 52, 64 * x + 95, null);
					//MapLayer Item
					
					if (ItemSet[x + offsetx][y + offsety] == null) {texture = null;} else { texture = ItemSet[x + offsetx][y + offsety];}
					
					if (!(texture == null)) {
					graphics.drawImage(getImage("/Textures/" + texture + ".png"), 64 * y + 52, 64 * x + 95, null);					
				}}}
				
				//MiniMap
				if (SplashX4.CurrentWindow.equals(Enumerators.Windows.Game) || SplashX4.CurrentWindow.equals(Enumerators.Windows.Bridging)) {
				for (int x = 0; x <= 14; x++) {//Rows
					for (int y = 0; y <= 17; y++) {//Columns
						String texture;
						try {
						//MapLayer 1
						if (DefaultCharSet[x + offsetx - 5 + MMOffsetX][y + offsety - 5 + MMOffsetY] == null) {texture = "mapnull";} else { texture = DefaultCharSet[x + offsetx - 5 + MMOffsetX][y + offsety - 5 + MMOffsetY];}
						graphics.drawImage(getImage("/Textures/M" + texture + ".png"), 16 * y + 665, 16 * x + 510, null);
						
						//MapLayer Item
						if (ItemSet[x + offsetx - 5 + MMOffsetX][y + offsety - 5 + MMOffsetY] == null) {texture = null;} else { texture = ItemSet[x + offsetx - 5 + MMOffsetX][y + offsety - 5 + MMOffsetY];}
						if (!(texture == null)) {
						graphics.drawImage(getImage("/Textures/M" + texture + ".png"), 16 * y + 665, 16 * x + 510, null);}
						}catch (Exception e) {}
					}}}
				
				//Vehicles
				for (int i = 0; i < 4; i++) {
				graphics.drawImage(getImage("/Textures/" + SplashX4.Vehicles[i] + "Right.png"), 54 + (70 * i), 600, null);
				}
				
				//Selector
				if (SelectorHandler.isEnabled() || SplashX4.CurrentWindow == Enumerators.Windows.BuildSelector) {
				graphics.drawImage(getImage("/Textures/Selector" + SelectorHandler.SelectorDirection + ".png"), 64 * SelectorHandler.SelectorScreenY + 52, 64 * SelectorHandler.SelectorScreenX + 95, null);
				}
				
				//Dev
				if (SplashX4.DeveloperMode) { 
				graphics.drawImage(getImage("/UI/Game Items/Dev.png"), 936, 0, null);
				}
				
				//Text
				graphics.drawString(String.valueOf(SplashX4.Material), 75, 740);
			    graphics.drawString(String.valueOf(SplashX4.Gas), 175, 740);
			    graphics.drawString(String.valueOf(SplashX4.Energy), 275, 740);
			   
			    //Selected menu
			    String selectorImage = "mapnull";
			    if (SelectorHandler.isEnabled()) {			    	
			    	try {
			    	if (ItemSet[SelectorHandler.SelectorX][SelectorHandler.SelectorY].equals(null) || ItemSet[SelectorHandler.SelectorX][SelectorHandler.SelectorY].equals("null")) {
			    		selectorImage = DefaultCharSet[SelectorHandler.SelectorX][SelectorHandler.SelectorY];
			    	} else {
			    		selectorImage = ItemSet[SelectorHandler.SelectorX][SelectorHandler.SelectorY];
			    	}
			    	} catch (Exception e) {
			    		selectorImage = DefaultCharSet[SelectorHandler.SelectorX][SelectorHandler.SelectorY];
			    	}
			    	
			    	if ((SelectorHandler.SelectorX == CharacterHandler.CharX) && (SelectorHandler.SelectorY == CharacterHandler.CharY)) {
			    		selectorImage = DefaultCharSet[SelectorHandler.SelectorX][SelectorHandler.SelectorY];
			    	}			    	
			    } else {selectorImage = DefaultCharSet[CharacterHandler.CharX][CharacterHandler.CharY];}
			    graphics.drawImage(getImage("/Textures/" + selectorImage + ".png"), 362, 585, null);
			    
			    	
			    	
			    //health
			    for (int i = 0; i <= CharacterHandler.Health + SplashX4.DebugX; i++) {
			    	graphics.drawImage(getImage("/UI/Game Items/Health/HEALTH" + i + ".png"), 457 + (2 * i), 507, null);
			    }
			    
 
			    //BuildMode
			    if (SplashX4.CurrentWindow == Enumerators.Windows.BuildSelector) {
				graphics.drawImage(getImage("/Textures/" + SelectorHandler.TileToBuild + ".png"), 780, 530, null);}
				
				//Pause Menu
				if (SplashX4.CurrentWindow == Enumerators.Windows.Pause) {
					graphics.drawImage(getImage("/UI/Game Items/PauseMenu.png"), 0, 0, null);
				}
				
				break;
		case GameOver:
			graphics.drawImage(getImage("/UI/Game Items/GameOver.png"), 0, 0, null);
			
			int gameoveroffset = 286;
			graphics.drawString("Cause: " + StatisticStore.Cause, 275, gameoveroffset + 10);
			graphics.drawString("Moves Made: " + StatisticStore.MovesMade, 275, gameoveroffset + 20);
			graphics.drawString("Items built: " + StatisticStore.Builds, 275, gameoveroffset + 30);
			graphics.drawString("Items excavated: " + StatisticStore.Excavates, 275, gameoveroffset + 40);
			graphics.drawString("Bridges built: " + StatisticStore.Bridges, 275, gameoveroffset + 50);
			graphics.drawString("Buildings Built: " + StatisticStore.Buildings, 275, gameoveroffset + 60);
			graphics.drawString("Shots Fired: " + StatisticStore.ShotsMade, 275, gameoveroffset + 70);
			graphics.drawString("Shots Taken: " + StatisticStore.ShotsTaken, 275, gameoveroffset + 80);
			graphics.drawString("Damage Dealt: " + StatisticStore.DamageDealt, 275, gameoveroffset + 90);
			graphics.drawString("Damage Taken: " + StatisticStore.DamageTaken, 275, gameoveroffset + 100);
			graphics.drawString("Items Upgraded: " + StatisticStore.UpgradesMade, 275, gameoveroffset + 110);
			graphics.drawString("Enemies Destroyed: " + StatisticStore.EnemiesDestroyed, 275, gameoveroffset + 120);
			graphics.drawString("Total enemies spawned: " + StatisticStore.EnemyCount, 275, gameoveroffset + 130);
			try {
			graphics.drawString("Total Play Time: " + GameHandler.getTimeFromMillis(StatisticStore.PlayTime), 275, gameoveroffset + 140);
			} catch (Exception e) {}
			
			
		break;
		case MapSelector:
			graphics.drawImage(getImage("/UI/Menu Items/Overlay.png"), 0, 0, null);
			graphics.drawImage(getImage("/UI/Menu Items/MapOverlay.png"), 0, 0, null);
		break;
		}
		
		if (SplashX4.DebugX > 0) {
			 graphics.drawString(String.valueOf(SplashX4.DebugX), 10, 10);
		}
		if (SplashX4.DebugY > 0) {
			 graphics.drawString(String.valueOf(SplashX4.DebugY), 10, 20);
		}

		
	
	}

	
	private static Image getImage(String resources) {
		ImageIcon drawer = new ImageIcon(SplashX4.ResourcePath + resources);
		return drawer.getImage();
	}
	
	static Font GetFont(String name) {
		Font font = new Font("sans", Font.PLAIN, 24);
		 try {
	            String fname = SplashX4.ResourcePath + "/UI/" + name + ".ttf";
	            File fontFile = new File(fname);
	            font = Font.createFont(Font.TRUETYPE_FONT, fontFile);
	            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
	            ge.registerFont(font);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
		return font;
	}
	

}


