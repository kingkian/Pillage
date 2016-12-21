//Kian Faroughi
//Csc165 - Assignment 2
//Doctor Gordon
//CSUS Fall 2015
//Gem defined my programmer
//Gem has multiple color buffers. When its points have been used, the gem sets color to specific collor buffer


package server;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

import graphicslib3D.Matrix3D;
import graphicslib3D.Vector3D;
import helpers.MyMoveController;
import m2.MyGame;
import sage.scene.TriMesh;

public class TextGhost
{

private Boolean pointsUsed = false;
private int points = 0;

private double maxMove = 42;
private String characterType;

private int gridI, gridJ;
private int characterID;
private UUID playerID;



 public TextGhost(UUID pID, int cID, String cType, int gI, int gJ)
 { 

	 
	 characterType = cType;
	 characterID = cID;
	 playerID = pID;
	 
	 gridI = gI;
	 gridJ = gJ;
	 


	 
	 points = randomInt(10,30);
	 



 }
 
 

 	public Boolean getPointsUsed()
	{
		return pointsUsed;
	}
	
	public void usePoints()
	{
		pointsUsed = true;
		
	}

	
	public int getPoints()
	{
		return points;
	}
 
	private static int randomInt(int min, int max) {
	    Random random = new Random();
	    int randomNum = random.nextInt((max - min) + 1) + min;
	    return randomNum;
	}
	
	public double getMaxMove()
	{
		return maxMove;
	}
	

	
	public String getCharacterTypeName()
	{
	
		return "N";

	}
	
	
	public void setGridIJ(int i, int j)
	{
		gridI = i;
		gridJ = j;
	}
	
	public void setGridI(int i)
	{
		gridI = i;
	}
	
	public void setGridJ(int j)
	{
		gridJ = j;
	}
	
	
	public int getGridI()
	{
		return gridI;
	}
	
	public int getGridJ()
	{
		return gridJ;
	}
	

	public int getCharacterID()
	{
		return characterID;
	}
	
	public UUID getPlayerID()
	{
		return playerID;
	}
	
	
	
}