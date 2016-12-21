package helpers;

import sage.event.AbstractGameEvent;

public class MoveCameraEvent extends AbstractGameEvent 
{
	
	private int directionX;
	private int directionZ;
	
	public MoveCameraEvent(int x, int z)
	{
		directionX = x;
		directionZ = z;
	}
	
	public String toString()
	{
		return "MoveCameraEvent";
	}
	
	public int getDirectionX()
	{
		return directionX;
	}
	
	public int getDirectionZ()
	{
		return directionZ;
	}

}
