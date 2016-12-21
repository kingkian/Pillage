//Kian Faroughi
//Csc165 - Assignment 1
//Doctor Gordon
//CSUS Fall 2015
//Event class used by controller to look up and down with right analog stick

package keyboardMovement;


import m2.Camera3P;
import m2.MyGame;
import client.Client;
import graphicslib3D.Matrix3D;
import net.java.games.input.Event;
import sage.input.action.AbstractInputAction;
import sage.scene.SceneNode;

public class OrbitUpAction extends AbstractInputAction {
	 
	private Camera3P cc;
	private int scaleSpeed;
	private float minElevation;
	private float maxElevation;
	private SceneNode cameraPointer;
	private float elevation, distanceFromTarget;
	private MyGame game;
	private Client client;
	
	int x = 0;
	
	public OrbitUpAction(MyGame game)
	{
			this.game = game;
			cc = game.getCamera3P();
			this.scaleSpeed = game.getScaleOrbitKeyboard();
			this.minElevation = (float)game.getMinElevation();
			this.maxElevation = (float)game.getMaxElevation();
			this.cameraPointer = game.returnCameraPointer();
			this.client = game.getClient();
			
	}
	
	public void performAction(float time, Event evt)
	 {
		scaleSpeed = game.getScaleOrbitKeyboard();
		minElevation = (float)game.getMinElevation();
		
		 float normalizeTime = time/(scaleSpeed);
	
 
	 elevation = cc.getElevation();
	 distanceFromTarget = cc.getDistanceFromTarget();
	 
	
	 if(elevation>minElevation)
	 {
	 cc.setElevation(elevation-normalizeTime);
	
	 
	
	 
	 cc.update(time);
	 
	 game.updateLifeBars();
	
	 
	 }
	 
	 }
	
	
	 
} 
	