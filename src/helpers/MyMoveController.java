package helpers;

import gameWorldObjects.GhostCharacter;
import gameWorldObjects.NPC;
import gameWorldObjects.Tile;
import gameWorldObjects.Character;
import graphicslib3D.Matrix3D;
import graphicslib3D.Vector3D;
import sage.scene.Controller;
import sage.scene.SceneNode;

public class MyMoveController extends Controller {
	
	private boolean makeMove;
	private boolean leftFirst  = true;
	private double directionX;
	private double directionZ;
	
	
	
	private float start = -1.0f;
	private float finish =  1.0f;
	
	private float currentTranslate = 0;
	private boolean goingUp = false;
	private boolean startHover = false;
	private double initialY, finalY;
	
	public MyMoveController(SceneNode avatar)
	{
		super();
		this.addControlledNode(avatar);
		makeMove = false;
		directionX = 0;
		directionZ = 0;
		
		initialY = avatar.getLocalTranslation().getCol(3).getY();
		finalY = initialY + finish;
		
	
	}


	public void update(double time)
	{
		
		
	
		
	
if(makeMove)
{


	
	
			double normalizeTime = time/18;
			
			int x = 0;
			int z = 0;
			
	
	if(leftFirst)
	{
			
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
		else if(directionZ>0 || directionZ<0)
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
		
	}
		
	else
	{
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
		else if(directionX>0 || directionX <0)
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
				
				for(SceneNode node: controlledNodes)
				{
				if(node instanceof GhostCharacter)
				{
					GhostCharacter thisGhost = (GhostCharacter)node;
					thisGhost.finishMove();
				}
				else if(node instanceof Character)
				{
					Character thisCharacter = (Character)node;
					thisCharacter.finishMove();
				}
				else if(node instanceof NPC)
				{
					NPC thisCharacter = (NPC)node;
					thisCharacter.finishMove();
				}
				}
				
	
				
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
