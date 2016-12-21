//Kian Faroughi
//Csc165 - Assignment 1
//Doctor Gordon
//CSUS Fall 2015
//Event class used by keyboard key to move backward


package movementActions;

import m2.Camera3P;
import m2.MyGame;
import client.Client;
import gameWorldObjects.Tile;
import graphicslib3D.Matrix3D;
import graphicslib3D.Point3D;
import graphicslib3D.Vector3D;
import net.java.games.input.Event;
import sage.camera.ICamera;
import sage.input.action.AbstractInputAction;
import sage.scene.SceneNode;

public class MoveAction3P extends AbstractInputAction {
	
	private ICamera camera;
	private Camera3P cc1;

	private int scaleSpeed;
	private int width;
	private int offset;
	private int max;
	private Tile cursorObject;
	private SceneNode cameraPointer;
	private Client client;
	private MyGame game;
	private double lastMove = 0;


	
	public MoveAction3P(MyGame game)
	{
			this.game = game;
			camera = game.getCamera();
			cursorObject = game.getCursorObject();
			cameraPointer = game.getCameraPointer();
			this.width = game.returnWidth();
			this.scaleSpeed = game.getScaleForwardBackwardKeyboard();
			this.offset = game.returnOffest();
			
			max = width/offset;
			this.client = game.getClient();
			this.cc1 = game.getCamera3P();
	
			
	}
	
	public void performAction(float time, Event event)
	{	
		
		
		
		double currentTime = System.currentTimeMillis();
		double timeDifference = currentTime - lastMove;
	
		
		if(timeDifference>140)
		{
		
		lastMove = System.currentTimeMillis();
		
		int gridI = cursorObject.getGridI();
		int gridJ = cursorObject.getGridJ();
		
		
		if(event.getValue()==0.25)
		{
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
		else if(event.getValue()==0.5)
		{
			if(gridJ<(((max*2))-1))
			{
				
				if(game.getGrid().getTileGrid()[gridI][gridJ+1].isCovered())
				{
					System.out.println("Sorry, the tile is covered.");
				}
				else
				{
				
				game.getGrid().getTileGrid()[gridI][gridJ].setIsSelected(false);
		
				gridJ +=1;
				game.getGrid().getTileGrid()[gridI][gridJ].setIsSelected(true);

				
				
				Matrix3D newTranslation = new Matrix3D();
				double x = game.getGrid().getTileGrid()[gridI][gridJ].getX();
				double z = game.getGrid().getTileGrid()[gridI][gridJ].getZ();
				double y = cursorObject.getY();
				newTranslation.translate(x, y, z);
				
				cursorObject.setLocation(x, y, z);
				cursorObject.setGridIJ(gridI,  gridJ);
				cursorObject.setLocalTranslation(newTranslation);
				
				game.getHoveringCursorController().makeMove(-1, 0, false);
				
				
				lastMove = System.currentTimeMillis();
	//			game.getGrid().whereIsTheCursor();

				
			
				if (game.moveCameraLeftRight())
				 {
				
					if(game.goingLeftOrRight())
					{
						
					}
					else
					{
					 game.triggerEvent("MoveCameraEvent", -1,0);
					 cc1.update(time);					
					}
				}
				
				}	
			}
		}
		else if(event.getValue()==0.75)
		{
			if(gridI<((max*2))-1)
			{
				if(game.getGrid().getTileGrid()[gridI+1][gridJ].isCovered())
				{
					System.out.println("Sorry, the tile is covered.");
				}
				else
				{
				
				game.getGrid().getTileGrid()[gridI][gridJ].setIsSelected(false);
		
				gridI +=1;
				game.getGrid().getTileGrid()[gridI][gridJ].setIsSelected(true);

				
				
				Matrix3D newTranslation = new Matrix3D();
				double x = game.getGrid().getTileGrid()[gridI][gridJ].getX();
				double z = game.getGrid().getTileGrid()[gridI][gridJ].getZ();
				double y = cursorObject.getY();
				newTranslation.translate(x, y, z);
				
				cursorObject.setLocation(x, y, z);
				cursorObject.setGridIJ(gridI,  gridJ);
				cursorObject.setLocalTranslation(newTranslation);
				
				
				game.getHoveringCursorController().makeMove(0, -1, false);
				
				lastMove = System.currentTimeMillis();
		//		game.getGrid().whereIsTheCursor();

				
				if(game.moveCameraUpDown())
				 {
					if(game.goingUpOrDown())
					{
							
					}
					else
					{
						 game.triggerEvent("MoveCameraEvent", 0 ,-1);
						 cc1.update(time);
					}
					
				 }
				}
				
			}
		}
		else if(event.getValue()==1.0)
		{
			if(gridJ>0)
			{
				
				if(game.getGrid().getTileGrid()[gridI][gridJ-1].isCovered())
				{
					System.out.println("Sorry, the tile is covered.");
				}
				else
				{
				
				
				game.getGrid().getTileGrid()[gridI][gridJ].setIsSelected(false);
		
				gridJ -=1;
				game.getGrid().getTileGrid()[gridI][gridJ].setIsSelected(true);

				
				
				Matrix3D newTranslation = new Matrix3D();
				double x = game.getGrid().getTileGrid()[gridI][gridJ].getX();
				double z = game.getGrid().getTileGrid()[gridI][gridJ].getZ();
				double y = cursorObject.getY();
				newTranslation.translate(x, y, z);
				
				cursorObject.setLocation(x, y, z);
				cursorObject.setGridIJ(gridI,  gridJ);
				cursorObject.setLocalTranslation(newTranslation);
				
				game.getHoveringCursorController().makeMove(1, 0, false);
				
				
				lastMove = System.currentTimeMillis();
		//		game.getGrid().whereIsTheCursor();

				if(game.moveCameraLeftRight())
				 {
					if(game.goingLeftOrRight())
					{
						 game.triggerEvent("MoveCameraEvent", 1,0);
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

}
