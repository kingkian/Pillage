package helpers;

import gameWorldObjects.NPC;
import sage.ai.behaviortrees.BTAction;
import sage.ai.behaviortrees.BTStatus;

public class MoveRight extends BTAction
{
 
	private NPC npc;
	
	public MoveRight(NPC n)
	{ 
		npc = n; 
	}
 
	protected BTStatus update(float elapsedTime)
	{ 
		int gI = npc.getGridI();
		int gJ = npc.getGridJ();
		gJ += 1;
		npc.setGridIJ(gI, gJ);
		
		return BTStatus.BH_SUCCESS;
	} 
	}
