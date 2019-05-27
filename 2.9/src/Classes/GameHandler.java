package Classes;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import com.github.javaparser.ParseException;
import com.studiohartman.jamepad.ControllerState;

import Enumerators.MapList;
import Enumerators.TurretOnLevel;
import Maps.TileData;

public class GameHandler {
	public static int TickRule = 0, nextx = 0, nexty = 0;
	public static long Ticks = 0, Frames = 0, KeyPresses = 0;
	public static Random rnd = new Random();
	public static Boolean MadEnemies = false;
	public static long PlayTime, StartTime;
	
	public static void Tick() {
		Ticks++;
		switch (TickRule){
		case 0:
			//EnemyTurrets
			if (SplashX4.TurretOnLevel == TurretOnLevel.Yes) {
				//See if turrets are due to shoot
				
				
				//Handle shot bullets
			}
			
			// Search for turrets
			if (SplashX4.TurretOnLevel.equals(Enumerators.TurretOnLevel.Unknown)) {
				for (int x = 0; x <= SplashX4.mapHeight; x++) {
					for (int y = 0; y <= SplashX4.mapWidth; y++) {
					try {if (RenderHandler.ActiveCharSet[x][y].toLowerCase().contains("turret")) {
						SplashX4.TurretOnLevel = TurretOnLevel.Yes;
						//Turret X Position
						SplashX4.Turrets[SplashX4.TurretCount][0] = String.valueOf(x);
						//Turret Y Position
						SplashX4.Turrets[SplashX4.TurretCount][1] = String.valueOf(y);
						//Turret Level
						SplashX4.Turrets[SplashX4.TurretCount][2] = "1";
						//Turret bullet in use?
						SplashX4.Turrets[SplashX4.TurretCount][3] = "false";
						//Turret bullet x
						SplashX4.Turrets[SplashX4.TurretCount][4] = String.valueOf(x);
						//Turret bullet y
						SplashX4.Turrets[SplashX4.TurretCount][5] = String.valueOf(y);
						//Turret Bullet Direction
						SplashX4.Turrets[SplashX4.TurretCount][6] = "0";
						SplashX4.TurretCount++;
												
					}} catch (Exception e) {}
					
					}}
				//If no turrets were found, update state to false
				if (SplashX4.TurretOnLevel == TurretOnLevel.Unknown) {SplashX4.TurretOnLevel = TurretOnLevel.No;}			
			}
			break;
		case 1:
			SoundHandler.Tick();
			BulletTick();
			break;
		case 2:
			if (SplashX4.CurrentWindow == Enumerators.Windows.Bridging) {

				switch (SelectorHandler.SelectorDirection.toLowerCase()) {
				case "up":
					nextx--;
				break;
				case "down":
					nextx++;
				break;
				case "left":
					nexty--;
				break;
				case "right":
					nexty++;
				}
				if (SplashX4.Material < 8) {SplashX4.CurrentWindow = Enumerators.Windows.Game; break;} 
				
				try { if (!(RenderHandler.DefaultCharSet[nextx][nexty].equals("Water"))) {SplashX4.CurrentWindow = Enumerators.Windows.Game; break;} } catch (Exception e) {SplashX4.CurrentWindow = Enumerators.Windows.Game; break;} 
				
				if (SplashX4.Material < 8) {SplashX4.CurrentWindow = Enumerators.Windows.Game;} 
				
				
					CharacterHandler.CharMove(SelectorHandler.SelectorDirection, null);
					RenderHandler.ItemSet[nextx][nexty] = null;
					RenderHandler.DefaultCharSet[nextx][nexty] = "PathBlack";
					SplashX4.Material -= 8;
			}			
			break;
		case 3:
			XInputHandler.ControllerConnected();
			break;
		case 4:	
			EnemyTick();
			break;
		case 5:
			if (SplashX4.DeveloperMode) {
				if (SplashX4.WindowY > 950) {
					SplashX4.WindowY = 950;
					SplashX4.updateWindow = true;
				}
			} else {
				if (SplashX4.WindowY > 800) {
					SplashX4.WindowY = 800;
					SplashX4.updateWindow = true;
				}
			}
			if (CharacterHandler.Health < 0) {
				SplashX4.EndGame("You Died!");
			}
			break;
		case 6:
			break;
		case 7:
			break;
		case 8:
			break;
		case 9:
			break;
		case 10:
			break;
		case 11:
			break;
		case 12:
			break;
		case 13:
			break;
		case 14:
			break;
		case 15:
			break;
		case 16:
			break;
		case 17:
			break;
		case 18:
			break;
		case 19:
			break;
		case 20:
			break;
		case 21:
			break;
		case 22:
			break;
		case 23:
			break;
		case 24:
			break;
		case 25:
			break;
		case 26:
			break;
		case 27:
			break;
		case 28:
			break;
		case 29:
			break;			
		}if (TickRule == 29) {TickRule = 0;} else {TickRule++;}}
	
	public static void resetMenu() {
		MenuState = false;
		MenuTick = 0;
	}
	
	public static String getTimeFromMillis(long millis) throws ParseException {
	    String hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis),
	            TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
	            TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
	    return hms;
	}
	
	public static void updateController() {
	if (XInputHandler.ControllerIsConnected) {
		ControllerState currState1 = XInputHandler.controllers.getState(0);
		XInputHandler.NewInput(currState1);}
	}
	
	
	public static boolean MenuState = false;
	public static int MenuTick = 0;	
	public static void MenuTick() {
		if (MenuTick == 200) {
			MenuTick = 0;
			MenuState = !MenuState;			
		} 
		else {MenuTick++;}
	}

	
	
	
	static String[][] ActiveBullets = new String[200][10];
	//ShootHandler
	public static boolean NewShoot(int XTarget, int YTarget, int XOrigin, int YOrigin) {
		 int activeCount = Integer.parseInt(ActiveBullets[0][0]);
		 ActiveBullets[0][0] = String.valueOf(activeCount + 1);
		 activeCount ++;
		 //TargetX
		 ActiveBullets[activeCount][0] = String.valueOf(XTarget);
		 //TargetY
		 ActiveBullets[activeCount][1] = String.valueOf(YTarget);
		 //CurrentX
		 ActiveBullets[activeCount][2] = String.valueOf(XOrigin);
		 //CurrentY
		 ActiveBullets[activeCount][3] = String.valueOf(YOrigin);
		 //NextX 4
		 //NextY 5
		ActiveBullets[activeCount][6] = "false";
		return true;
	}
	
	private static void removeBullet(int id) {
		 int activeCount = Integer.parseInt(ActiveBullets[0][0]);
		 ActiveBullets[0][0] = String.valueOf(activeCount - 1);
		for (int i = id; i <= activeCount + 1; i++) {
		ActiveBullets[i][0] = ActiveBullets[i + 1][0];
		 ActiveBullets[i][1] = ActiveBullets[i + 1][1];
		 ActiveBullets[i][2] = ActiveBullets[i + 1][2];
		 ActiveBullets[i][3] = ActiveBullets[i + 1][3];
		 ActiveBullets[i][4] = ActiveBullets[i + 1][4];
		 ActiveBullets[i][5] = ActiveBullets[i + 1][5];
		 ActiveBullets[i][6] = ActiveBullets[i + 1][6];
		}
		
		for (int i = activeCount + 1; i <= activeCount + 10; i++) {
			ActiveBullets[i][0] = null;
			 ActiveBullets[i][1] = null;
			 ActiveBullets[i][2] = null;
			 ActiveBullets[i][3] = null;
			 ActiveBullets[i][4] = null;
			 ActiveBullets[i][5] = null;
			 ActiveBullets[i][6] = null;
			}
		
		
		
	}
	
	private static int BulletGetVector(int From, int To){
		int vector = -1;
		if (From > To) {vector = From - 1;}
		if (From < To) {vector = From + 1;}
		if (From == To) {vector = From;}
		return vector;
	}
	
	public static void BulletTick() {
		try {
			try {if (Integer.parseInt(ActiveBullets[0][0]) == 0){return;}} catch (Exception e){}
		
		for (int i = 1; i <= Integer.parseInt(ActiveBullets[0][0]); i++) {
			 //NextX
			 //NextY
			ActiveBullets[i][4] = String.valueOf(BulletGetVector(Integer.parseInt(ActiveBullets[i][2]),Integer.parseInt(ActiveBullets[i][0])));
			ActiveBullets[i][5] = String.valueOf(BulletGetVector(Integer.parseInt(ActiveBullets[i][3]),Integer.parseInt(ActiveBullets[i][1])));
			

			
			if (CharacterHandler.BoundCheck(Integer.parseInt(ActiveBullets[i][4]), Integer.parseInt(ActiveBullets[i][5]))){
			 RenderHandler.ItemSet[Integer.parseInt(ActiveBullets[i][2])][Integer.parseInt(ActiveBullets[i][3])] = null;
			 RenderHandler.ItemSet[Integer.parseInt(ActiveBullets[i][4])][Integer.parseInt(ActiveBullets[i][5])] = "Bullet";
			ActiveBullets[i][2] = String.valueOf(ActiveBullets[i][4]);
			ActiveBullets[i][3] = String.valueOf(ActiveBullets[i][5]);
			
			}else {
				 RenderHandler.ItemSet[Integer.parseInt(ActiveBullets[i][2])][Integer.parseInt(ActiveBullets[i][3])] = null;
				 SoundHandler.SFX("Hit");
				 bulletDamage(Integer.parseInt(ActiveBullets[i][4]),Integer.parseInt(ActiveBullets[i][5]));
				 removeBullet(i);
			}}
		}catch(Exception e) {}}

	private static void bulletDamage(int x, int y) {
		//Item layer
		try {
		if (!(RenderHandler.ItemSet[x][y].equals("") 
				|| RenderHandler.ItemSet[x][y].equals("null") 
				|| RenderHandler.ItemSet[x][y].equals("Null"))) {
			RenderHandler.ItemSet[x][y] = TileData.GetDestroyTexture(RenderHandler.ItemSet[x][y]);
			return;
		}
		} catch(Exception e) {}
		
		//If no item, background layer
			RenderHandler.DefaultCharSet[x][y] = TileData.GetDestroyTexture(RenderHandler.DefaultCharSet[x][y]);
	}
	
	static String[][] Enemies = new String[200][10];
	public static void EnemyTick() {
		try {
			try {if (Integer.parseInt(Enemies[0][0]) == 0){return;}} catch (Exception e){}
		
		for (int i = 1; i <= Integer.parseInt(Enemies[0][0]); i++) {
			 //NextX
			 //NextY
			Enemies[i][4] = String.valueOf(BulletGetVector(Integer.parseInt(Enemies[i][2]),CharacterHandler.CharX));
			Enemies[i][5] = String.valueOf(BulletGetVector(Integer.parseInt(Enemies[i][3]),CharacterHandler.CharY));
			
			
			if (CharacterHandler.BoundCheck(Integer.parseInt(Enemies[i][4]), Integer.parseInt(Enemies[i][5]))){
			RenderHandler.ItemSet[Integer.parseInt(Enemies[i][2])][Integer.parseInt(Enemies[i][3])] = null;
			RenderHandler.ItemSet[Integer.parseInt(Enemies[i][4])][Integer.parseInt(Enemies[i][5])] = "mapnull";
			Enemies[i][2] = String.valueOf(Enemies[i][4]);
			Enemies[i][3] = String.valueOf(Enemies[i][5]);
			
			}else {
			if (Enemies[i][4].equals(String.valueOf(CharacterHandler.CharX)) && Enemies[i][5].equals(String.valueOf(CharacterHandler.CharY))){
				StatisticStore.ShotsTaken++;
				CharacterHandler.Health -= 10;
				StatisticStore.DamageTaken+= 10;
			}
				 RemoveEnemy(i);
		
				 
				}}
		}catch(Exception e) {
		}}
	
	
	public static void RemoveEnemy(int id) {
		//Remove enemy from display, and play sound effect.
		 RenderHandler.ItemSet[Integer.parseInt(Enemies[id][2])][Integer.parseInt(Enemies[id][3])] = null;
		 SoundHandler.SFX("Hit");
		
		
		int lengthtowork = Integer.parseInt(Enemies[0][0]); //get length of enemies list
		Enemies[0][0] = String.valueOf(lengthtowork - 1);
		
		//nullify current enemy
		Enemies[id][1] = null;
		Enemies[id][2] = null;
		Enemies[id][3] = null;
		Enemies[id][4] = null;
		Enemies[id][5] = null;
		Enemies[id][6] = null;
		Enemies[id][7] = null;
		
		//Shift other enemy entries upward, starting from item above removed entity.
		
		for (int i = id + 1; i <= lengthtowork + 1; i++) {
			for (int x = 0; x <= 10; x++) {
				Enemies[id + i][x] = Enemies[id + i + 1][x];
			}
		}		
	}
	
	
	public static void NewEnemy(int XOrigin, int YOrigin) {
		if (!(CharacterHandler.BoundCheck(XOrigin, YOrigin))) {
			return;
		}
		int activeCount;
		 try {activeCount = Integer.parseInt(Enemies[0][0]);} catch (Exception e) {activeCount = 0;}
		 Enemies[0][0] = String.valueOf(activeCount + 1);
		 activeCount ++;
		 //CurrentX
		 Enemies[activeCount][2] = String.valueOf(XOrigin);
		 //CurrentY
		 Enemies[activeCount][3] = String.valueOf(YOrigin);

		Enemies[activeCount][6] = "false";
	}

	public static String getVehicleAsString(Enumerators.Vehicles vehicle) {
		try {
		switch (vehicle) {
		case Builder:
			return "Builder";
		case Excavator:
			return "Excavator";
		case Tank:
			return "Tank";
		case Nothing:
			return "Nothing";
		case Invalid:
			return "Invalid";
		}
		return "Invalid Vehicle Identifyer";
		}catch (Exception e) {
			return "Invalid Vehicle Identifyer";
		}
	}
	
	public static Enumerators.Vehicles getVehicleAsVehicle(String vehicle) {
		switch (vehicle) {
		case "Builder":
			return Enumerators.Vehicles.Builder;
		case "Excavator":
			return Enumerators.Vehicles.Excavator;
		case "Tank":
			return Enumerators.Vehicles.Tank;
		case "Nothing":
			return Enumerators.Vehicles.Tank;
		}
		return Enumerators.Vehicles.Invalid;
	}
	
	public static MapList getMapAsMap(String Map) {
		switch (Map) {
		case "Map1":
			return MapList.Map1;
		case "Map2":
			return MapList.Map2;
		case "Map3":
			return MapList.Map3;
		case "Map4":
			return MapList.Map4;
		case "Map5":
			return MapList.Map5;
		case "Map6":
			return MapList.Map6;
		case "Map7":
			return MapList.Map7;
		case "Map8":
			return MapList.Map8;
		case "Map9":
			return MapList.Map9;
			

		}
		return Enumerators.MapList.Map1;
	}
	
}
