package helpers;

import gameWorldObjects.GhostCharacter;
import gameWorldObjects.NPC;
import gameWorldObjects.Tile;
import gameWorldObjects.Character;
import graphicslib3D.Matrix3D;
import graphicslib3D.Vector3D;
import m2.MyGame;
import sage.scene.Controller;
import sage.scene.SceneNode;

public class MyHoveringCursorController extends Controller {
	
	private boolean makeMove;
	private boolean leftFirst  = true;
	private double directionX;
	private double directionZ;
	
	
	
	private float start = -1.0f;
	private float finish =  1.0f;
	
	private float currentTranslate = 0;
	private boolean goingUp = false;
	private boolean startHover = true;
	private double initialY, finalY, finalBottomY;
	private MyGame game;
	
	long startTime, lastUpdateTime;
	
	private Vector3D location;
	
	public MyHoveringCursorController(SceneNode avatar, MyGame game)
	{
		super();
		this.addControlledNode(avatar);
		makeMove = false;
		directionX = 0;
		directionZ = 0;
		
		this.game = game;
		
		startTime = System.nanoTime();
		
		initialY = avatar.getLocalTranslation().getCol(3).getY();
		finalY = initialY + finish;
		finalBottomY = initialY + start;
		
		
		
	location = controlledNodes.get(0).getLocalTranslation().getCol(3);
	
	}


	public void update(double time)
	{
		
	
		
	
		
		if(startHover)
		{
				
		double normalizeTime2 = time/300;
		
	
		for(SceneNode node: controlledNodes)
		{
			if(node instanceof Tile)
					{
				
						node.translate(0, currentTranslate, 0);

					}
		}
	
		
		double myY = controlledNodes.get(0).getLocalTranslation().getCol(3).getY();
		

		if(myY < finalY)
		{
		
		currentTranslate+= normalizeTime2;
		}
		else if(myY > finalBottomY)
		{
			currentTranslate -= normalizeTime2;
		}
		
		
		if(currentTranslate>finish)
		{
			goingUp = false;
			currentTranslate = finish;
			controlledNodes.get(0).getLocalTranslation().getCol(3).setY(finalY);
	
		}
		if(currentTranslate<start)
		{
			goingUp = true;
			currentTranslate = start;
			controlledNodes.get(0).getLocalTranslation().getCol(3).setY(finalBottomY);
	
		}
		
/*
		if(goingUp)
		{
		currentTranslate+= normalizeTime2;
		}
		else
		{
			currentTranslate -= normalizeTime2;
		}
		
		if(currentTranslate>finish)
		{
			goingUp = false;
			currentTranslate = finish;
			controlledNodes.get(0).getLocalTranslation().getCol(3).setY(finalY);
	
		}
		if(currentTranslate<start)
		{
			goingUp = true;
			currentTranslate = start;
			controlledNodes.get(0).getLocalTranslation().getCol(3).setY(initialY);
	
		}
*/		
	
		
		}
		
	
if(makeMove)
{
/*
	if(controlledNodes.get(0) instanceof Tile)
	{
		startHover();
	}

*/
			double normalizeTime = time/18;
			
			int x = 0;
			int z = 0;
			

		if(directionX>0 || directionX <0)
		{
			if(directionX>0)
			{
				
				if((directionX-normalizeTime)<0)
				{
					x = 1;
					normalizeTime = directionX;
					directionX = 0;
					
				}
				else
				{
				x = 1;
				directionX -= normalizeTime;
				}
			}
			else if(directionX<0)
			{
				
				if((directionX+normalizeTime)>0)
				{
					x = 1;
					normalizeTime = directionX;
					directionX = 0;
				}
				else
				{
				x = -1;
				directionX += normalizeTime;
				}
			}
		}
		if(directionZ>0 || directionZ<0)
		{
			if(directionZ>0)
			{
			
				if((directionZ-normalizeTime)<0)
				{
					z = 1;
					normalizeTime = directionZ;
					directionZ = 0;
				}
				else
				{
				
					z = 1;
					directionZ -= normalizeTime;
				}
			}
			
			else if(directionZ<0)
			{
				
				if((directionZ+normalizeTime)>0)
				{
					z = 1;
					normalizeTime = directionZ;
					directionZ = 0;
				}
				else
				{
					z = -1;
					directionZ += normalizeTime;
				}
			}
		}
		
	
		
		
			for(SceneNode node: controlledNodes)
			{
			
				
				node.translate((float)(normalizeTime*x), 0, (float)(normalizeTime*z));
				
				if(node instanceof GhostCharacter)
				{
					GhostCharacter thisGhost = (GhostCharacter)node;
					thisGhost.updateLifeBar();
				}
				else if(node instanceof Character)
				{
					Character thisCharacter = (Character)node;
					thisCharacter.updateLifeBar();
				}
				else if(node instanceof NPC)
				{
					NPC thisNPC= (NPC)node;
					thisNPC.updateLifeBar();
				}
			}
			
		
			if(directionX==0 && directionZ==0)
			{
				makeMove = false;
				
				
					Matrix3D newTranslation = new Matrix3D();
					newTranslation.translate(game.getCursorObject().getX()+0.2, controlledNodes.get(0).getLocalTranslation().getCol(3).getY(), game.getCursorObject().getZ()+2.5);
					controlledNodes.get(0).setLocalTranslation(newTranslation);
					
				
				
			}
			
			
		
		}
		
	}
	
	public void makeMove(int x, int z , boolean leftFirst)
	{
		makeMove = true;
		
		int movementX = x*10;
		int movementZ = z*10;
		
		directionX += movementX;
		directionZ += movementZ;
		
		this.leftFirst = leftFirst;

	}
	
	public void startHover()
	{
		startHover = true;
	}

}
