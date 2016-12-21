//Kian Faroughi
//Csc165 - Assignment 1
//Doctor Gordon
//CSUS Fall 2015
//Event class used by keyboard key to move forward

package keyboardMovement;


import m2.Camera3P;
import m2.MyGame;
import client.Client;
import gameWorldObjects.Tile;
import graphicslib3D.Matrix3D;
import graphicslib3D.Vector3D;
import net.java.games.input.Event;
import sage.camera.ICamera;
import sage.input.action.AbstractInputAction;
import sage.scene.SceneNode;

public class MoveForwardAction3P extends AbstractInputAction {
	
	private ICamera camera;
	private int scaleSpeed;
	private int width;
	private int offset;
	private int max;
	private Tile cursorObject;
	private SceneNode cameraPointer;
	private MyGame myGame;
	private Client client;
	private Camera3P cc1;
	private MyGame game;
	private double lastMove = 0;
	
	public MoveForwardAction3P(MyGame game)
	{
			this.game = game;
			camera = game.getCamera();
			this.cc1 = game.getCamera3P();
			this.cameraPointer = game.getCameraPointer();
			this.scaleSpeed = game.getScaleForwardBackwardKeyboard();
			this.cursorObject = game.getCursorObject();
			this.width = game.returnWidth();
			this.offset = game.returnOffest();
			this.max = width/offset;
			this.client = game.getClient();
	}
	
	public void performAction(float time, Event event)
	{
		
	
		
		double currentTime = System.currentTimeMillis();
		double timeDifference = currentTime - lastMove;
	
		
		if(timeDifference>140)
		{
		 
			
			int gridI = cursorObject.getGridI();
			int gridJ = cursorObject.getGridJ();
			
			if(gridI>0)
			{
				
				if(game.getGrid().getTileGrid()[gridI-1][gridJ].isCovered())
				{
					System.out.println("Sorry, the tile is covered.");
				}
				else
				{
				
				
				game.getGrid().getTileGrid()[gridI][gridJ].setIsSelected(false);
		
				gridI -=1;
				game.getGrid().getTileGrid()[gridI][gridJ].setIsSelected(true);

				
				
				Matrix3D newTranslation = new Matrix3D();
				double x = game.getGrid().getTileGrid()[gridI][gridJ].getX();
				double z = game.getGrid().getTileGrid()[gridI][gridJ].getZ();
				double y = cursorObject.getY();
				newTranslation.translate(x, y, z);
				
				cursorObject.setLocation(x, y, z);
				cursorObject.setGridIJ(gridI,  gridJ);
				cursorObject.setLocalTranslation(newTranslation);
				
				game.getHoveringCursorController().makeMove(0, 1, false);
				
				
				lastMove = System.currentTimeMillis();
		//		game.getGrid().whereIsTheCursor();

				
				if(game.moveCameraUpDown())
				 {
					if(game.goingUpOrDown())
					{
						game.triggerEvent("MoveCameraEvent", 0,1);
						 cc1.update(time);	
					}
					else
					{
								
					}				
				 }
				}
				
			}
			
		
		}
		 
	}

}