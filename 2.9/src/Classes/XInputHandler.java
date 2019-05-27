package Classes;
import com.studiohartman.jamepad.ControllerManager;
import com.studiohartman.jamepad.ControllerState;

import Enumerators.Vehicles;

public class XInputHandler {
	public static ControllerState state;
	private static double sensitivity = 0.6, negsensitivity = -0.6;
	
	@SuppressWarnings("incomplete-switch")
	public static void NewInput(ControllerState newInput) {
			
			state = newInput;
			if (newInput.rightStickY < negsensitivity) {
			RenderHandler.MMOffsetX++;
			return;
			} //down 
			if (newInput.rightStickY > sensitivity) {
			RenderHandler.MMOffsetX--;
			return;
			} // up
			if (newInput.rightStickX < negsensitivity) {
			RenderHandler.MMOffsetY--;
			return;
			} // right
			if (newInput.rightStickX > sensitivity) {
			RenderHandler.MMOffsetY++;
			return;
			} //right
			if (newInput.rightStickClick) {RenderHandler.MMOffsetX = 0;RenderHandler.MMOffsetY = 0;}
			
			switch (SplashX4.CurrentWindow) {
			case Game:
		
			//Escape for end game
			if (newInput.start) {SplashX4.EndGame("Quit!");};
			//Selector
			if (SelectorHandler.isEnabled()) {
				if (newInput.leftStickY > sensitivity) {SelectorHandler.MoveSelector("UP");}
				if (newInput.leftStickX < negsensitivity) {SelectorHandler.MoveSelector("LEFT");}
				if (newInput.leftStickY < negsensitivity) {SelectorHandler.MoveSelector("DOWN");}
				if (newInput.leftStickX > sensitivity) {SelectorHandler.MoveSelector("RIGHT");}
				if (newInput.aJustPressed) {SelectorHandler.ConfirmSelection();}
				return;
			}
			
			
			//Vehicle
			if (newInput.dpadUp) {SplashX4.CurrentVehicle = GameHandler.getVehicleAsVehicle(SplashX4.Vehicles[0]); CharacterHandler.Update();}
			if (newInput.dpadRight) {SplashX4.CurrentVehicle = GameHandler.getVehicleAsVehicle(SplashX4.Vehicles[1]); CharacterHandler.Update();}
			if (newInput.dpadDown) {SplashX4.CurrentVehicle = GameHandler.getVehicleAsVehicle(SplashX4.Vehicles[2]); CharacterHandler.Update();}
			if (newInput.dpadLeft)  {SplashX4.CurrentVehicle = GameHandler.getVehicleAsVehicle(SplashX4.Vehicles[3]); CharacterHandler.Update();}
			
			//Selector
			if (newInput.aJustPressed) {
				if (SplashX4.CurrentVehicle.equals(Vehicles.Tank)) {
					SelectorHandler.EnableSelector("Shoot", CharacterHandler.CharX, CharacterHandler.CharY, 5);}
				if (SplashX4.CurrentVehicle.equals(Vehicles.Builder)) {
					SelectorHandler.EnableSelector("Build", CharacterHandler.CharX, CharacterHandler.CharY, 1);}
				if (SplashX4.CurrentVehicle.equals(Vehicles.Excavator)) {
					SelectorHandler.EnableSelector("Excavate", CharacterHandler.CharX, CharacterHandler.CharY, 1);}
			}
			
			//FOR SoundHandler
			if (newInput.lb) {SoundHandler.PrevTrack();}
			if (newInput.rightTrigger > sensitivity) {SoundHandler.PlayStop();}
			if (newInput.rb) {SoundHandler.NextTrack();}
			
			//FOR CharacterHandler
			if (newInput.leftStickY > sensitivity) {CharacterHandler.CharMove("UP", null);}
			if (newInput.leftStickX > sensitivity) {CharacterHandler.CharMove("RIGHT", null);}
			if (newInput.leftStickY < negsensitivity) {CharacterHandler.CharMove("DOWN", null);}
			if (newInput.leftStickX < negsensitivity){CharacterHandler.CharMove("LEFT", null);}
			break;
			case BuildSelector:
				//if (key == 'w' || key == 'W') {}
				//if (key == 's' || key == 'S') {}
				if (newInput.aJustPressed) {SelectorHandler.ConfirmSelection();}			
			break;
			case Menu:
			if (newInput.rightTrigger > 5) {SoundHandler.PlayStop();}
			if (newInput.aJustPressed) {SplashX4.StartGame();}
			if (newInput.bJustPressed) {Runtime.getRuntime().halt(0);};
			break;				
			
			case GameOver:
				if (newInput.a) {SplashX4.restart = true;}
			break;
			}	
			
			
			   	}
	
	public static boolean ControllerIsConnected = false;
	public static ControllerManager controllers = new ControllerManager();
	public static void ControllerConnected() {
		//controllers = new ControllerManager();
		//controllers.initSDLGamepad();
		ControllerState currState = controllers.getState(0);
		if (currState.isConnected) {
			ControllerIsConnected = true;
		} else {
			ControllerIsConnected = false;
		}
	}
		
	
	}

