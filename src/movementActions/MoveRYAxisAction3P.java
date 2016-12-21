//Kian Faroughi
//Csc165 - Assignment 1
//Doctor Gordon
//CSUS Fall 2015
//Event class used by controller to look up and down with right analog stick

package movementActions;

import m2.Camera3P;
import m2.MyGame;
import client.Client;
import net.java.games.input.Event;
import sage.camera.ICamera;
import sage.input.action.AbstractInputAction;
import sage.scene.SceneNode;

public class MoveRYAxisAction3P extends AbstractInputAction {
	
	private Camera3P cc;

	private Client client;
	private int scaleSpeed;
	private float minElevation;
	private float maxElevation;
	private SceneNode cursorObject;
	private SceneNode cameraPointer;
	private MyGame game;
	private double lastMove = 0;
	
	
	private float elevation, distanceFromTarget;
	
	public MoveRYAxisAction3P(MyGame game)
	{	
			this.game = game;
			cc = game.getCamera3P();
			this.scaleSpeed = game.getScaleOrbitGamepad();
			this.minElevation = (float)game.getMinElevation();
			this.maxElevation = (float)game.getMaxElevation();
			this.cursorObject = game.getCursorObject();
			this.cameraPointer = game.getCameraPointer();
			this.client = game.getClient();
		
	}
	
	public void performAction(float time, Event event)
	{
			
			
			
		 if(event.getValue()>0.5 || event.getValue()<-0.5)
			{
			 
			 
			 double currentTime = System.currentTimeMillis();
			 double timeDifference = currentTime - lastMove;
			
				
				if(timeDifference>40)
				{
				 
			 
					lastMove = System.currentTimeMillis();
			 
			 
			 	scaleSpeed = game.getScaleOrbitGamepad();
			 	minElevation = (float)game.getMinElevation();
			 	maxElevation = (float)game.getMaxElevation();
			 	
			 	float normalizeTime = (((time/25)*event.getValue())*.8f);
			 	
			 	
			// 	System.out.println(normalizeTime);
			 	
			 	
				 elevation = cc.getElevation();
				 distanceFromTarget = cc.getDistanceFromTarget();
				 float y = distanceFromTarget/elevation;
			
				 if(event.getValue()>0)
				{
				 if(elevation<maxElevation)
				 {
				 cc.setElevation(elevation+normalizeTime);
				 
//				cc.update(time);
				

				
				 }
				}
				else
				{
				if(elevation>minElevation)
				{
				cc.setElevation(elevation+normalizeTime);
				
//				cc.update(time);
			
				
				}
				}
			 
				 
				 
				}
			}
		 

		
		
	}
	
}
		
			


