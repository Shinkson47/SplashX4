package Maps;

public class TileData {
	private static String[][] TileDataBase = new String[10][6];
	private static String[][] BuildDataBase = new String[10][9];
	public static int BuildDBLength = 1;
	
	public TileData() {
		//Resource name
		TileDataBase[0][0] = "PathBlack";
		//TileDataBase
		TileDataBase[0][1] = "true";
		//Excavatable
		TileDataBase[0][2] = "false";
		//Texture when destroyed
		TileDataBase[0][3] = "Dirt";
		//Can be built on
		TileDataBase[0][4] = "true";

		
		TileDataBase[1][0] = "Save";
		//TileDataBase
		TileDataBase[1][1] = "false";
		//Excavatable
		TileDataBase[1][2] = "false";
		//Texture when destroyed
		TileDataBase[1][3] = "null";
		//Can be built on
		TileDataBase[1][4] = "false";

		
		
		TileDataBase[2][0] = "Grass1";
		//TileDataBase
		TileDataBase[2][1] = "true";
		//Excavatable
		TileDataBase[2][2] = "false";
		//Texture when destroyed
		TileDataBase[2][3] = "Dirt";
		//Can be built on
		TileDataBase[2][4] = "true";

		
		
		TileDataBase[3][0] = "Grass2";
		//TileDataBase
		TileDataBase[3][1] = "true";
		//Excavatable
		TileDataBase[3][2] = "false";
		//Texture when destroyed
		TileDataBase[3][3] = "Dirt";
		//Can be built on
		TileDataBase[3][4] = "true";

		
		TileDataBase[4][0] = "Sand1";
		//TileDataBase
		TileDataBase[4][1] = "true";
		//Excavatble
		TileDataBase[4][2] = "false";
		//Texture when destroyed
		TileDataBase[4][3] = "Dirt";
		//Can be built on
		TileDataBase[4][4] = "true";

		
		
		TileDataBase[5][0] = "Sand2";
		//TileDataBase
		TileDataBase[5][1] = "true";
		//Excavatable
		TileDataBase[5][2] = "false";
		//Texture when destroyed
		TileDataBase[5][3] = "Dirt";
		//Can be built on
		TileDataBase[5][4] = "true";

		
		
		TileDataBase[6][0] = "EnemyBase";
		//TileDataBase
		TileDataBase[6][1] = "false";
		//Excavatable
		TileDataBase[6][2] = "false";
		//Texture when destroyed
		TileDataBase[6][3] = "null";
		//Can be built on
		TileDataBase[6][4] = "false";

		
		TileDataBase[7][0] = "Water";
		//TileDataBase
		TileDataBase[7][1] = "false";
		//Excavatable
		TileDataBase[7][2] = "false";
		//Texture when destroyed
		TileDataBase[7][3] = "Dirt";
		//Can be built on
		TileDataBase[7][4] = "false";

		
		TileDataBase[8][0] = "TreeNew";
		//TileDataBase
		TileDataBase[8][1] = "false";
		//Excavatable
		TileDataBase[8][2] = "true";
		//Texture when destroyed
		TileDataBase[8][3] = "null";
		//Can be built on
		TileDataBase[8][4] = "false";

		
		
		TileDataBase[9][0] = "Dirt";
		//TileDataBase
		TileDataBase[9][1] = "true";
		//Excavatable
		TileDataBase[9][2] = "false";
		//Texture when destroyed
		TileDataBase[9][3] = "Dirt";
		//Can be built on
		TileDataBase[9][4] = "true";

		
		BuildBBD();	
	}
	
	
	private static void BuildBBD() {
		//Name
		BuildDataBase[0][0] = "Dirt";
		//Material Cost
		BuildDataBase[0][1] = "10";
		//Gas Cost
		BuildDataBase[0][2] = "0";
		//Energy Cost
		BuildDataBase[0][3] = "5";
		//Material Value
		BuildDataBase[0][4] = "1";
		//Gas Value
		BuildDataBase[0][5] = "0";
		//Energy Value
		BuildDataBase[0][6] = "4";
		//Process Efficiency
		BuildDataBase[0][7] = "4";
		//BuildLayer
		BuildDataBase[0][8] = "0";
		
		BuildDataBase[1][0] = "Grass1";
		BuildDataBase[1][1] = "10";
		BuildDataBase[1][2] = "0";
		BuildDataBase[1][3] = "5";
		BuildDataBase[1][4] = "1";
		BuildDataBase[1][5] = "0";
		BuildDataBase[1][6] = "4";
		BuildDataBase[1][7] = "4";
	}
	
	public static boolean getBuildLayer(String tile) {
		int index = getBuildIndex(tile);
		if (index == 999) {
			return false;
		}
		if (Integer.parseInt(BuildDataBase[index][8]) == 0) {
			return true;
		}	
		return false;
	}
	
	@SuppressWarnings("unused")
	public static int getBuildIndex(String tile) {
		TileData tiledat = new TileData();
		for (int index = 0; index <= BuildDataBase.length + 1; index++) {
		if (BuildDataBase[index][0].equals(tile)) {return index;}
	}return 999;
	}
	
	//BuildDB
	public static String getBuildTile(int Index) {
		if (Index > BuildDBLength) {return null;}
		return BuildDataBase[Index][0];
	}
	public static int getBuildMaterialCost(int Index) {
		if (Index > BuildDBLength) {return 1000;}
		return Integer.parseInt(BuildDataBase[Index][1]);
	}
	public static int getBuildGasCost(int Index) {
		if (Index > BuildDBLength) {return 1000;}
		return Integer.parseInt(BuildDataBase[Index][2]);
	}
	public static int getBuildEnergyCost(int Index) {
		if (Index > BuildDBLength) {return 1000;}
		return Integer.parseInt(BuildDataBase[Index][3]);
	}
	public static int getBuildMaterialValue(int Index) {
		if (Index > BuildDBLength) {return 1000;}
		return Integer.parseInt(BuildDataBase[Index][4]);
	}
	public static int getBuildGasValue(int Index) {
		if (Index > BuildDBLength) {return 1000;}
		return Integer.parseInt(BuildDataBase[Index][5]);
	}
	public static int getBuildEnergyValue(int Index) {
		if (Index > BuildDBLength) {return 1000;}
		return Integer.parseInt(BuildDataBase[Index][6]);
	}
	public static int getBuildEfficiency(int Index) {
		if (Index > BuildDBLength) {return 1000;}
		return Integer.parseInt(BuildDataBase[Index][7]);
	}
	
	
	
	
	//Tiles
	@SuppressWarnings("unused")
	private static int GetIndex(String tile) {	
		TileData tiledat = new TileData();
		for (int index = 0; index <= TileDataBase.length - 1; index++) {
		if (TileDataBase[index][0].equals(tile)) {return index;}
	}return 999;}
	
	public static boolean isWalkable(String tile) {	
		try {if (tile.equals("null")) {return true;}}
		catch (NullPointerException e) {return true;}
		int index = GetIndex(tile);
		if (index == 999) {return false;}
		if (TileDataBase[index][1].equals("true")) {return true;}
		return false;}

 public static boolean isExcavatable(String tile) {
	 try {if (tile.equals("null")) {return false;}}
	 catch (NullPointerException e) {return false;}
	 int index = GetIndex(tile);
	 if (index == 999) {return false;}
	 if (TileDataBase[index][2].equals("true")) {return true;}
	 return false;}
 
 public static boolean isBuildable(String tile) {
	 try {if (tile.equals("null")) {return false;}}
	 catch (NullPointerException e) {return false;}
	 int index = GetIndex(tile);
	 if (TileDataBase[index][4].equals("true")) {return true;}
	 return false;}
 
 public static String GetDestroyTexture(String tile) {
	 try {if (tile.equals("null")) {return "Dirt";}}
	 catch (NullPointerException e) {return "Dirt";}
	 int index = GetIndex(tile);
	 if (index == 999) {return "Dirt";}
	 return TileDataBase[index][3];
	 }
 
}


