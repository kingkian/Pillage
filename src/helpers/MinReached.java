package helpers;

import gameWorldObjects.NPC;
import sage.ai.behaviortrees.BTCondition;
import server.NPCcontroller;

public class MinReached extends BTCondition
{
	
private NPCcontroller npcController;	
private NPC npc;
private long lastUpdateTime;
	
 public MinReached(NPCcontroller c, NPC n, boolean toNegate)
 {
	super(toNegate);
 npcController = c;
 npc = n;
 lastUpdateTime = System.nanoTime();
 }
 
 
 protected boolean check()
 { 
	if(npcController.getDirection())
	{ 
		return false;
	}
	else
	{
		if(npc.getGridJ()>0)
		{

			return true;
		}
		else
		{
			npcController.toggleDirection();
			return false;
		}
			
	}
 }

}