package helpers;

import gameWorldObjects.NPC;
import sage.ai.behaviortrees.BTCondition;
import server.NPCcontroller;

public class MaxReached extends BTCondition
{
	
private NPCcontroller npcController;	
private NPC npc;
private long lastUpdateTime;
	
 public MaxReached(NPCcontroller c, NPC n, boolean toNegate)
 {
	super(toNegate);
 npcController = c;
 npc = n;

 }
 
 
 protected boolean check()
 { 
	if(npcController.getDirection())
	{ 
		if(npc.getGridJ()<63)
		{
			
		System.out.println("came");
		return true;
		
		}
		else
		{
			
			npcController.toggleDirection();
			return false;
			
		}
	}
	else
	{
		return false;
			
	}
 }

}