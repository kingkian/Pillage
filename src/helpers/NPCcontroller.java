package helpers;

import gameWorldObjects.GhostCharacter;
import gameWorldObjects.Grid;
import gameWorldObjects.NPC;

import java.util.ArrayList;
import java.util.UUID;

import gameWorldObjects.Character;
import graphicslib3D.Matrix3D;
import sage.ai.behaviortrees.BTCompositeType;
import sage.ai.behaviortrees.BTSequence;
import sage.ai.behaviortrees.BehaviorTree;
import sage.scene.Controller;
import sage.scene.SceneNode;

public class NPCcontroller {
	
	private Grid tileGrid;
	private boolean makeMove;
	private double directionX;
	private double directionZ;
	private ArrayList<gameWorldObjects.NPC> npcs;
	
	private NPC npc;
	
	BehaviorTree behaviorTree  = new BehaviorTree(BTCompositeType.SELECTOR);

	
	private boolean direction = false;
	
	private long lastMove;
	
	public NPCcontroller(UUID pID)
	{
		super();
		
		npcs = new ArrayList<gameWorldObjects.NPC>();
		NPC npc = new NPC(pID, 1, "N", 24,60);
		this.npc = npc;
		npcs.add(npc);
		
		
		
		tileGrid = new Grid();
		makeMove = false;
		directionX = 0;
		directionZ = 0;
		lastMove = System.nanoTime();
		
		
	}


	public void update()
	{
		
		//behaviorTree.update((elapsedTime/1000));

		
		
		for(int i = 0; i<npcs.size(); i++)
		{
			
			int gI = npcs.get(i).getGridI();
			int gJ = npcs.get(i).getGridJ();
			
			if(direction == false)
			{
			//gI -= 1;
			if(gJ>1)
			{
			gJ -= 1;
			}
			else
			{
				direction = true;
			}
			}
			else
			{
				if(gJ<63)
				{
					gJ+=1;
				}
				else
				{
					direction = false;
				}
			}
			
			
			npcs.get(i).setGridIJ(gI, gJ);
			
		}
		
		
			
			
	}
	
	public ArrayList<NPC> getNPCs()
	{
		return npcs;
	}
	

	
	public boolean getDirection()
	{
		return direction;
	}
	
	public void toggleDirection()
	{
		if(direction)
		{
			direction = false;
		}
		else
		{
			direction = true;
		}
	}


}
