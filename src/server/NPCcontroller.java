package server;

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
	
	private boolean first = true;
	
	private ServerGrid tileGrid;
	private boolean makeMove;
	private double directionX;
	private double directionZ;
	private ArrayList<TextNPC> npcs;
	private ArrayList<TextGhost> ghosts;
	private int maxMove = 6;
	private int maxAttack = 1;
	
	
	private TextNPC npc;
	
	BehaviorTree behaviorTree  = new BehaviorTree(BTCompositeType.SELECTOR);

	
	private boolean direction = false;
	
	private long lastMove;
	
	public NPCcontroller(UUID pID)
	{
		super();
		
		tileGrid = new ServerGrid();
		
		npcs = new ArrayList<TextNPC>();
		ghosts = new ArrayList<TextGhost>();
		
		TextNPC npc = new TextNPC(pID, 1, "N", 24,63);
		this.npc = npc;
		tileGrid.getTileGrid()[24][63].occupy("N");
	
		
		npcs.add(npc);
		
		//setUpBehaviorTree();
		
		
		makeMove = false;
		directionX = 0;
		directionZ = 0;
		lastMove = System.nanoTime();
		
		
	}


	public void update()
	{

		
		int closestGhost = 10000;
		int ghostI = 0;
		int ghostJ = 0;
		
		//behaviorTree.update((elapsedTime/1000));

		
		for(int i = 0; i<npcs.size(); i++)
		{
			
			int gI = npcs.get(i).getGridI();
			int gJ = npcs.get(i).getGridJ();
			
			tileGrid.getTileGrid()[gI][gJ].unOccupy();
			
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
			
			
			tileGrid.getTileGrid()[gI][gJ].occupy("N");
			
		}
		
		
		
		for(int npc = 0; npc<npcs.size(); npc++)
		{
			
			int gI = npcs.get(npc).getGridI();
			int gJ = npcs.get(npc).getGridJ();	
		
		
			for(int i =-6; i<7; i++)
			{
				for(int j = -6; j<7;j++)
				{
					if((gI+i)<63 && (gJ+j)<63 && (gI+i)>0 && (gJ+j)>0)
					{
					if(tileGrid.getTileGrid()[gI+i][gJ+j].isOccupied())
					{
						int amount = i+j;
						if(amount<0)
						{
							amount = amount*-1;
						}
						if((amount)<closestGhost)
						{
							closestGhost = amount;
							ghostI = gI+i;
							ghostJ = gJ+j;
						}
					}
					}
				}
			}
		
		
		}
//		System.out.println(closestGhost);
//		System.out.println(ghostI);
//		System.out.println(ghostJ);
			
			
	}
	
	public ArrayList<TextNPC> getNPCs()
	{
		return npcs;
	}

	/*
	public void setUpBehaviorTree()
	{
		behaviorTree.insertAtRoot(new BTSequence(10));
		behaviorTree.insertAtRoot(new BTSequence(20));
		behaviorTree.insert(10, new MaxReached(this,npc,false));
		behaviorTree.insert(10, new MoveLeft(npc));
		behaviorTree.insert(20, new MinReached(this,npc,false));
		behaviorTree.insert(20, new MoveRight(npc));
	
	
	}
	*/
	
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
	
	public ServerGrid getTileGrid()
	{
		return tileGrid;
	}
	
	
	
	public void updateTextGhost(UUID pID, int cID, int gI, int gJ)
	{
		while(true)
		{
			
		for(int i = 0; i<ghosts.size(); i++)
		{
			boolean thisPlayer = ghosts.get(i).getPlayerID().equals(pID);
			int thisCharacter = ghosts.get(i).getCharacterID();
			
			if(thisPlayer)
			{
				if(thisCharacter==cID)
				{
					int gridI = ghosts.get(i).getGridI();
					int gridJ = ghosts.get(i).getGridJ();
					
					tileGrid.getTileGrid()[gridI][gridJ].unOccupy();
					
					int z = gridI - gI;
					int x = gridJ - gJ;
					
				//	tileGrid.getTileGrid()[gI][gJ].occupy(ghostTeam.get(i).getCharacterTypeName());
					tileGrid.getTileGrid()[gI][gJ].occupy("G");

					ghosts.get(i).setGridIJ(gI,  gJ);
					break;
				}
					
			}
		}
		break;
		}
		
	
		
	}
	
	
	
	
	
	
	

	public void createTextGhost(UUID pID, int cID, String cType, int gI, int gJ)
	{
		
		TextGhost ghost = new TextGhost(pID, cID, "G", gI,gJ);
		ghosts.add(ghost);
		tileGrid.getTileGrid()[gI][gJ].occupy("G");
	
	}
	public void removeTextGhosts(UUID pID)
	{
		System.out.println(ghosts.size());
		boolean notFinished = true;
		
		while(notFinished)
		{
			
		
		for(int i = 0; i<ghosts.size(); i++)
		{
			
			
			boolean thisGhost = ghosts.get(i).getPlayerID().equals(pID);
			
			if(thisGhost)
			{
				tileGrid.getTileGrid()[ghosts.get(i).getGridI()][ghosts.get(i).getGridJ()].unOccupy();
				ghosts.remove(i);
			
			}

			boolean getOut = false;
			for(int j = 0; j<ghosts.size(); j++)
			{
				boolean checkGetOut = ghosts.get(j).getPlayerID().equals(pID);
				
				if(getOut)
				{
					getOut = true;
				}
			}
			if(getOut == false)
			{
				notFinished = false;
			}

		}
		}
	}
	
	public void coverTile(int gI, int gJ)
	{
		tileGrid.getTileGrid()[gI][gJ].cover("X");
		
	}

}
