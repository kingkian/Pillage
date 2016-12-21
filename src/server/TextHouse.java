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

public class TextHouse
{

private Boolean pointsUsed = false;
private int points = 0;

private double maxMove = 42;
private String characterType;

private int gridI, gridJ;




 public TextHouse( int gI, int gJ)
 { 


	 
	 gridI = gI;
	 gridJ = gJ;
	 


	 

	 



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
	


	
	
	
}