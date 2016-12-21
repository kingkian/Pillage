//Kian Faroughi
//Csc165 - Assignment 1
//Doctor Gordon
//CSUS Fall 2015
//Event class used by controller to move along x axis with left analog

package movementActions;

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

public class MoveRightLeftAction3P extends AbstractInputAction {
	
	private ICamera camera;
	private Camera3P cc1;
	private int scaleSpeed;
	private int width;
	private int offset;
	private Tile cursorObject;
	private SceneNode cameraPointer;
	private int max;
	int test = 0;
	private Client client;
	private MyGame game;
	private double lastMove = 0;
	
	public MoveRightLeftAction3P(MyGame game)
	{
			this.game = game;
			camera = game.getCamera();
			this.cc1 = game.getCamera3P();
			this.scaleSpeed = game.getScaleLeftRightGamepad();
			this.width = game.returnWidth();
			this.offset = game.returnOffest();
			this.cursorObject = game.getCursorObject();
			this.cameraPointer = game.getCameraPointer();
			max = width - (offset/2);
			this.client = game.getClient();
	}
	
	public void performAction(float time, Event event)
	{
		

		
		if(event.getValue()>0.35 || event.getValue()<-0.35)
		{
	
			if(event.getValue()>0)
			{
				double currentTime = System.currentTimeMillis();
				double timeDifference = currentTime - lastMove;
			
				
				if(timeDifference>200)
				{
					
					
					int gridI = cursorObject.getGridI();
					int gridJ = cursorObject.getGridJ();
					
					
					if(gridJ<63)
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
				
				
			
			
			}
			else 
			{
				double currentTime = System.currentTimeMillis();
				double timeDifference = currentTime - lastMove;
			
				
				if(timeDifference>200)
				{
				
					
					int gridI = cursorObject.getGridI();
					int gridJ = cursorObject.getGridJ();
					
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
	

}
