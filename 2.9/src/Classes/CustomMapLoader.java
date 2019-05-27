package Classes;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import Enumerators.Windows;
public class CustomMapLoader {

	public static Path ResourcePath;
	public static void LoadMap(boolean auto) {
		if (!auto) {
		JOptionPane.showMessageDialog(SplashX4.canvas, "Please locate a project, using the '.XLocator' File.", "Open Project", JOptionPane.OK_OPTION);
		try {			    
			        String folder = System.getProperty("user.dir");
			        JFileChooser chooser = new JFileChooser(folder);
			  
			        chooser.setDialogTitle("Locate project");
				    chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				    chooser.setAcceptAllFileFilterUsed(false);
				    FileNameExtensionFilter filter = new FileNameExtensionFilter(".XLocator", "XLocator");
				    chooser.setFileFilter(filter);
				    chooser.showOpenDialog(null);
				    ResourcePath = chooser.getCurrentDirectory().toPath();
				  if (!Files.exists(Paths.get(chooser.getCurrentDirectory() + "/Project.XMem"))) {
					  JOptionPane.showMessageDialog(SplashX4.canvas, "Project file is missing!", "Open Project", JOptionPane.OK_OPTION); return;}   
				  if (!Files.exists(Paths.get(chooser.getCurrentDirectory() + "/ItemSet.XMem"))) {
					  JOptionPane.showMessageDialog(SplashX4.canvas, "ItemSet file is Missing!", "Open Project", JOptionPane.OK_OPTION);return;}   
				  if (!Files.exists(Paths.get(chooser.getCurrentDirectory() + "/CharSet.XMem"))) {
					  JOptionPane.showMessageDialog(SplashX4.canvas, "CharSet is missing!", "Open Project", JOptionPane.OK_OPTION);return;}   
				
				
				
				
			
		} catch (Exception e) {}
		}
		try {	
		SplashX4.mapHeight = Integer.parseInt(FileHandler.ReadFile(ResourcePath + "/Project.XMem", true, false, 0, 0));
		SplashX4.mapWidth = Integer.parseInt(FileHandler.ReadFile(ResourcePath + "/Project.XMem", false, false, 0, 1));
		JOptionPane.showMessageDialog(SplashX4.canvas, "Press ok to attempt to load from project, this may take some time - be patient.", "Open Project", JOptionPane.OK_OPTION);
		FileHandler.ReadFile(ResourcePath + "/CharSet.XMem", true, false, 0, 0);
		RenderHandler.DefaultCharSet = new String[1000][1000];
		RenderHandler.ActiveCharSet = new String[1000][1000];
		RenderHandler.ItemSet = new String[1000][1000];
		for (int x = 0; x <= SplashX4.mapHeight; x++) {
			for (int y = 0; y <= SplashX4.mapWidth; y++) {
				RenderHandler.DefaultCharSet[x][y] = FileHandler.ReadFile(ResourcePath + "/CharSet.XMem", false, false, x, y);
			}}

		for (int x = 0; x <= SplashX4.mapHeight; x++) {
			for (int y = 0; y <= SplashX4.mapWidth; y++) {
				RenderHandler.ItemSet[x][y] = FileHandler.ReadFile(ResourcePath + "/CharSet.XMem", false, false, x, y);
			}}
		
		try {
		CharacterHandler.CharX = Integer.parseInt(JOptionPane.showInputDialog(SplashX4.canvas, "CharacterX Position?"));  
		CharacterHandler.CharY = Integer.parseInt(JOptionPane.showInputDialog(SplashX4.canvas, "CharacterY Position?")); 
		if (CharacterHandler.CharX > SplashX4.mapHeight) {throw new IndexOutOfBoundsException();}
		if (CharacterHandler.CharY > SplashX4.mapWidth) {throw new IndexOutOfBoundsException();}
		RenderHandler.offsetx = CharacterHandler.CharX -1;
		RenderHandler.offsety = CharacterHandler.CharY -1;
		RenderHandler.ItemSet[CharacterHandler.CharX][CharacterHandler.CharY] = "BuilderRight";
		} catch (Exception e) {
			JOptionPane.showMessageDialog(SplashX4.canvas, "Invalid character position entered", "Open Project", JOptionPane.OK_OPTION);
			SplashX4.restart = true; return;
		}
		
		
		SplashX4.CurrentWindow = Windows.Game;
		SoundHandler.StartMusic();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(SplashX4.canvas, "Encountered an error whilst loading the map, we'll try to restart Splash X4 after the error:", "Failed to open Project", JOptionPane.OK_OPTION);
			JOptionPane.showMessageDialog(SplashX4.canvas, e.getMessage(), "ERROR (Stack in terminal)",JOptionPane.OK_OPTION);
			e.printStackTrace(); SplashX4.restart = true; return;}}}
