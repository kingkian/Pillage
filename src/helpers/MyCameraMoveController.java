package helpers;

import gameWorldObjects.GhostCharacter;
import gameWorldObjects.NPC;
import gameWorldObjects.Character;
import graphicslib3D.Matrix3D;
import sage.scene.Controller;
import sage.scene.SceneNode;

public class MyCameraMoveController extends Controller {
	
	private boolean makeMove;
	private double directionX;
	private double directionZ;
	
	public MyCameraMoveController(SceneNode avatar)
	{
		super();
		this.addControlledNode(avatar);
		makeMove = false;
		directionX = 0;
		directionZ = 0;
	}


	public void update(double time)
	{
	
	//	if(makeMove)
	//	{
			double normalizeTime = time/16;
			
			int x = 0;
			int z = 0;
			
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
			
			
		
		//}
		
	}
	
	public void makeMove(int x, int z)
	{
		makeMove = true;
		
		int movementX = x*10;
		int movementZ = z*10;
		
		directionX += movementX;
		directionZ += movementZ;
		

	}

}
