package Classes;
import Maps.TileData;

public class CharacterHandler {
	public static int CharX = 0, CharY = 0;
	public static String direction = "left";
	public static Integer Health = 87;
	
	public static void CharMove(String dir, String actionArg) {
		StatisticStore.MovesMade++;
		direction = dir;
		RenderHandler.ItemSet[CharX][CharY] = null;
		if (dir == "UP") {if (CharX > 0) {
			if (RenderHandler.offsetx > 0 && CharX - RenderHandler.offsetx == 0) {RenderHandler.offsetx--;}
			CharX--;
			if (!BoundCheck(CharX,CharY)) {
				CharX++;}
			}}
		
		
		if (dir == "LEFT") {if (CharY > 0) {
			if (RenderHandler.offsety > 0 && CharY - RenderHandler.offsety == 0) {RenderHandler.offsety--;}
			CharY--;
			if (!BoundCheck(CharX,CharY)) {CharY++;}
		}
		}
		
		
		if (dir == "DOWN") {
			if (CharX == SplashX4.mapHeight) {CharX--;}
			if (CharX - RenderHandler.offsetx == 5 && !((RenderHandler.offsetx + 5) == SplashX4.mapHeight)) {RenderHandler.offsetx++;} 
			else {
				if (CharX + RenderHandler.offsetx == 5 && (RenderHandler.offsetx + 5) == SplashX4.mapHeight) {
					CharX--;}}
			CharX++;
			if (!BoundCheck(CharX,CharY)) {CharX--;}
			}
		
		if (dir == "RIGHT") {
			if (CharY == SplashX4.mapWidth) {CharY--;}
			if (CharY - RenderHandler.offsety == 13 && !((RenderHandler.offsety + 13) == SplashX4.mapWidth)) {RenderHandler.offsety++;} else {if (CharY + RenderHandler.offsety == 13 && (RenderHandler.offsety + 13) == SplashX4.mapHeight) {CharY--;}}
			CharY++;
			if (!BoundCheck(CharX,CharY)) {CharY--;}
			}
		 
		RenderHandler.ItemSet[CharX][CharY] = GameHandler.getVehicleAsString(SplashX4.CurrentVehicle) + direction;
		SoundHandler.SFX("Walk");
		
	}
		
	public static void Update() {
		RenderHandler.ItemSet[CharX][CharY] = GameHandler.getVehicleAsString(SplashX4.CurrentVehicle) + "Right";
	}
	
	public static boolean BoundCheck(int x, int y) {
		String tile = RenderHandler.DefaultCharSet[x][y];
		boolean bound = false;
		bound = TileData.isWalkable(tile);
		tile = RenderHandler.ItemSet[x][y];
		try {if (!(tile.equals("null"))) {bound = TileData.isWalkable(tile);}} catch (Exception e) {}
		return bound;
	}	
	
	
		
	public static void CharSet(int x, int y, String dir) {
		CharX = x;
		CharY = y;	
		RenderHandler.ItemSet[x][y] = SplashX4.CurrentVehicle + dir;
	}

	public static void AddVechicle(String Vehicle, boolean Append, boolean Select) {
		if (!Append) {SplashX4.Vehicles = new String[8];} {
			if (SplashX4.vehicleCount >= 4) {SplashX4.Vehicles[0] = null;
			for (int i = 0; i < 3; i++) {SplashX4.Vehicles[i] = SplashX4.Vehicles[i+1];}
		}}
		if (SplashX4.vehicleCount >= 3) {
			SplashX4.vehicleCount = 3;
		}
	
		SplashX4.vehicleCount++;
		SplashX4.Vehicles[SplashX4.vehicleCount] = Vehicle;
		if (Select) {SplashX4.CurrentVehicle = GameHandler.getVehicleAsVehicle(Vehicle);}
				
}}
