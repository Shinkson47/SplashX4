package Classes;
import Maps.TileData;

public class SelectorHandler {
	private static boolean SelectorMode = false, Moved = false, Used = false;
	public static int SelectorX = 0, SelectorY = 0, SelectorRange = 3, offsetx, offsety, StartX, StartY, PrevTargetX, PrevTargetY; 
	public static int SelectorScreenX, SelectorScreenY, TileIndex = 0;
	public static String ActionToPerform = "Build";
	public static String TileToBuild = "Dirt", SelectorDirection = "UP";
	
	
	public static void EnableSelector(String Action, int StartX, int StartY, int Range) {
		Moved = false;
		SoundHandler.SFX("ConfirmAlert");
		ActionToPerform = Action;
		SelectorMode = true;
		offsety = RenderHandler.offsety;
		offsetx = RenderHandler.offsetx;
		SelectorX = StartX;
		SelectorY = StartY;
		SelectorHandler.StartX = StartX;
		SelectorHandler.StartY = StartY;
		SelectorScreenX = StartX - offsetx;
		SelectorScreenY = StartY - offsety;
		SelectorRange = Range;
	}
	
	public static void DisableSelector(boolean ActionCompleted, boolean resetoffset) {
		if (ActionCompleted) {SoundHandler.SFX("Confirm");} else {SoundHandler.SFX("Damage");}
		SelectorMode = false;
		if (resetoffset) {
		RenderHandler.offsety = offsety;
		RenderHandler.offsetx = offsetx;
		}
		SelectorRange = 3;
	}
	
	public static void ConfirmSelection() {
		boolean Completed = false;
		if ((SelectorX == CharacterHandler.CharX) && (SelectorY == CharacterHandler.CharY)){DisableSelector(Completed ,true); return;}
		
		switch (ActionToPerform) {
		case "Shoot":
			if ((Used) && !(Moved)) {
				GameHandler.NewShoot(PrevTargetX, PrevTargetY, CharacterHandler.CharX, CharacterHandler.CharY);
			} else {
				GameHandler.NewShoot(SelectorX, SelectorY, CharacterHandler.CharX, CharacterHandler.CharY);
				PrevTargetX = SelectorX;
				PrevTargetY = SelectorY;
			}
			Completed = true;
			StatisticStore.ShotsMade++;
			break;
		case "Excavate":
			
			String tile = RenderHandler.ItemSet[SelectorX][SelectorY];
			if (TileData.isExcavatable(tile)) {
				RenderHandler.ItemSet[SelectorX][SelectorY] = TileData.GetDestroyTexture(tile);
				Completed = true;
				StatisticStore.Excavates++;
			}
			
			break;
		case "Teleport":
			RenderHandler.ItemSet[CharacterHandler.CharX][CharacterHandler.CharY] = null;
			CharacterHandler.CharX = SelectorX;
			CharacterHandler.CharY = SelectorY;
			RenderHandler.offsetx = CharacterHandler.CharX -1;
			RenderHandler.offsety = CharacterHandler.CharY -1;
			RenderHandler.ItemSet[CharacterHandler.CharX][CharacterHandler.CharY] = "BuilderRight";
			Completed = true;
			return;
		case "Bridge":
			if (SplashX4.Material >= 25) {
			GameHandler.nextx = SelectorHandler.SelectorX; GameHandler.nexty = SelectorHandler.SelectorY;
			RenderHandler.ItemSet[GameHandler.nextx][GameHandler.nexty] = null;
			RenderHandler.DefaultCharSet[GameHandler.nextx][GameHandler.nexty] = "PathBlack";
			SplashX4.Material -= 15;
			ActionToPerform = "Teleport";
			ConfirmSelection();
			StatisticStore.Bridges++;
			ActionToPerform = "Bridge";
			SplashX4.CurrentWindow = Enumerators.Windows.Bridging;
			Completed = true;
			}
			break;
		case "Carry":
			//
			break;
		case "Place":
			//
			break;
		case "Build":
			if (SplashX4.Material >= 5) {
			if (CharacterHandler.BoundCheck(SelectorX, SelectorY)) {
			SplashX4.CurrentWindow = Enumerators.Windows.BuildSelector;
			ActionToPerform = "BuildComplete";
			PrevTargetX = SelectorX;
			PrevTargetY = SelectorY;
			Completed = true;
			SplashX4.Material -= 5;
			StatisticStore.Builds++;
			} else {
			Completed = false;
			}} else {
			Completed = false;
			}
			break;
		case "BuildComplete":
			if (TileData.getBuildLayer(TileToBuild)) {
				RenderHandler.ItemSet[PrevTargetX][PrevTargetY] = "null";
				RenderHandler.DefaultCharSet[PrevTargetX][PrevTargetY] = TileToBuild;
			} else {
				RenderHandler.ItemSet[PrevTargetX][PrevTargetY] = TileToBuild;
				RenderHandler.DefaultCharSet[PrevTargetX][PrevTargetY] = "PathBlack";
			}
			
			if (!(SplashX4.CurrentWindow == Enumerators.Windows.Bridging)) {SplashX4.CurrentWindow = Enumerators.Windows.Game;}
			Completed = true;
			break;
		}
		DisableSelector(Completed, true);
		Used = true;
	}
	
	public static boolean isEnabled() {
		return (SelectorMode);
	}
	
	
	public static void MoveSelector(String dir) {
		Moved = true;
		SoundHandler.SFX("Walk");
			if (dir == "UP") {
				SelectorHandler.SelectorDirection = "UP";
				if (SelectorX == (StartX - SelectorRange)) {return;}
				if (SelectorX == 0) {return;}
				if (SelectorScreenX == 0 && RenderHandler.offsetx >= 0) {RenderHandler.offsetx--; SelectorScreenX++;}
				
			
				SelectorScreenX--;
				SelectorX--;
			}
			
			
			if (dir == "LEFT") {
				SelectorHandler.SelectorDirection = "LEFT";
				if (SelectorY == (StartY - SelectorRange)) {return;}
				if (SelectorY == 0) {return;}
				if (SelectorScreenY == 0 && RenderHandler.offsety >= 0) {RenderHandler.offsety--; SelectorScreenY++;}
				
				
				SelectorScreenY--;
				SelectorY--;
			}
			
			
			
			
			
			
			if (dir == "DOWN") {	
				SelectorHandler.SelectorDirection = "DOWN";
				if (SelectorX == SplashX4.mapHeight) {return;}
				if (SelectorX == (StartX + SelectorRange)) {return;}
				if (SelectorScreenX == 5 && !((RenderHandler.offsetx + 5) >= SplashX4.mapHeight)) {RenderHandler.offsetx++; SelectorScreenX--;}
			
				
				SelectorScreenX++;
				SelectorX++;
				}
			
			if (dir == "RIGHT") {
				SelectorHandler.SelectorDirection = "RIGHT";
				if (SelectorY == (StartY + SelectorRange)) {return;}
				if (SelectorY == SplashX4.mapWidth) {return;}
				if (SelectorScreenY == 13 && !((RenderHandler.offsety + 5) >= SplashX4.mapWidth)) {RenderHandler.offsety++; SelectorScreenY--;}
				
			
				SelectorScreenY++;
				SelectorY++;
				}
			
			
			
	
	if (SelectorScreenX >= 6) {DisableSelector(false, true);}
	if (SelectorScreenY >= 14) {DisableSelector(false, true);}
	
	}
	}
	
	
	

