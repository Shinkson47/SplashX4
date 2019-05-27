package Classes;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

import Enumerators.MapList;
import Maps.Map1;
import Maps.Map2;
import Maps.Map3;

public class FileHandler {
	public static String[][] FieldMemory = new String[11][11];
	FileHandler() {
		FieldMemory = new String[11][11];

	}


	
	public Boolean ValueExists(){
		//TODO Load file to array
		//TODO Compare value
		//TODO Return comparison
		return false;
		
	}
	

	public static void Boot() {
		LoadFile("/AppData/Preferences.XPref", true);
		SplashX4.vehicleCount = -1;
		SplashX4.Vehicles = new String[8];
		SplashX4.FirstTime = ReadFile(null, false, true, 0,3) == "false";
		SplashX4.FrameLimit = Integer.parseInt(FileHandler.ReadFile(null, false, true, 0, 2));
		Integer.parseInt(ReadFile(null, false, true, 0,0));
		Integer.parseInt(ReadFile(null, false, true, 0,1));
		if (GameHandler.getMapAsMap(ReadFile(null, false, true, 1, 0)) != SplashX4.CurrentMap){
			LoadMap(SplashX4.CurrentMap);
		} else {
			LoadMap(GameHandler.getMapAsMap(ReadFile(null, false, true, 1, 0)));
		}
		String temp = ReadFile(null, false, true, 1,5);
		SplashX4.DeveloperMode = temp.matches("true");
		SoundHandler.Boot();
	    }
	
	
	public static String ReadFile(String File, boolean Reload, boolean Internal, Integer FieldX, Integer FieldY){
		if (Reload) {LoadFile(File, Internal);}
		return FieldMemory[FieldX][FieldY];}
	
	
	private static void LoadFile(String File, boolean Internal) {
		String file = null;
		if (Internal) {file = SplashX4.ResourcePath + File; FieldMemory = new String[11][11];} else {file = File; FieldMemory = new String[1000][1000];}
		if (!Files.exists(Paths.get(file))) {return;}
        String line = "";
        String Delimiter = ",";
        Integer x = 0;
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
            while ((line = br.readLine()) != null) { //if line isn't blank
                String[] CurrentField = line.split(Delimiter); //split field by delimiter, save to variable
                
                FieldMemory[x] = CurrentField; //Save to array  
                
                x++; //Count line
                }

        } catch (FileNotFoundException e) {
        	e.printStackTrace();
        } catch (IOException e) {
        	e.printStackTrace();
        } finally {if (br != null) {try {br.close();} catch (IOException e) {e.printStackTrace();}}}}
	
	
	
	
	
	

	public static void WriteFile(String File, Integer FieldX, Integer FieldY, String Value){
		LoadFile(File, true);
		FieldMemory[FieldX][FieldY] = Value;
		SaveFile(File);
	}
	
	
	private static void SaveFile(String File) {
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(SplashX4.ResourcePath + File)));
			writer.write("");
		for (Integer x = 0; x <= 10; x++) {
			
			for (Integer y = 0; y <= 10; y++) {
				try {
					
					writer.append(FieldMemory[x][y] + ",");
				} catch (Exception e) {
					
				} }
			writer.newLine();
			}
			
		try {writer.close();} catch (IOException e) {
		}	
		}catch (Exception e2) {}}
	
public static void SaveGame() {
	LoadFile("/AppData/Preferences.XPref", true);
	for (int i = 0; i <= 3; i++) {FileHandler.WriteFile("/AppData/Preferences.XPref", 2, i, SplashX4.Vehicles[i]);}
}
		
@SuppressWarnings({ "static-access", "unused" })
public static void LoadMap(MapList MapName) {
	switch (MapName){
	case Map1:
		Map1 map1 = new Map1();
		
		RenderHandler.ActiveCharSet = map1.ActiveCharSet;
		RenderHandler.DefaultCharSet = map1.DefaultCharSet;
		SplashX4.mapHeight = map1.MapX;
		SplashX4.mapWidth = map1.MapY;
		RenderHandler.ItemSet = map1.ItemSet;
		LoadFile("/AppData/Preferences.XPref", true);
		CharacterHandler.CharSet(map1.CharStartX, map1.CharStartY, "Right");
	break;
	case Map2:
		Map2 map2 = new Map2();
		RenderHandler.ActiveCharSet = map2.ActiveCharSet;
		RenderHandler.DefaultCharSet = map2.DefaultCharSet;
		SplashX4.mapHeight = map2.MapX;
		SplashX4.mapWidth = map2.MapY;
		RenderHandler.ItemSet = map2.ItemSet;
		LoadFile("/AppData/Preferences.XPref", true);
		CharacterHandler.CharSet(map2.CharStartX, map2.CharStartY, "Right");
	break;
	case Map3:
		Map3 map3 = new Map3();
		RenderHandler.ActiveCharSet = Map3.ActiveCharSet;
		RenderHandler.DefaultCharSet = Map3.DefaultCharSet;
		SplashX4.mapHeight = Map3.MapX;
		SplashX4.mapWidth = Map3.MapY;
		RenderHandler.ItemSet = Map3.ItemSet;
		LoadFile("/AppData/Preferences.XPref", true);
		CharacterHandler.CharSet(Map3.CharStartX, Map3.CharStartY, "Right");
	break;
	
	default:
		LoadMap(MapList.Map1);
		return;
	}
		CharacterHandler.AddVechicle(FieldMemory[2][0], false, true);
		CharacterHandler.AddVechicle(FieldMemory[2][1], true, false);
		CharacterHandler.AddVechicle(FieldMemory[2][2], true, false);
		CharacterHandler.AddVechicle(FieldMemory[2][3], true, false);
		
		
	
	
	
	}}
