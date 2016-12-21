//Kian Faroughi
//Csc165 - Assignment 2
//Doctor Gordon
//CSUS Fall 2015
//Gem defined my programmer
//Gem has multiple color buffers. When its points have been used, the gem sets color to specific collor buffer


package gameWorldObjects;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Random;

import sage.scene.TriMesh;

public class Gem extends TriMesh
{
private static float[] vrts = new float[] {0,7,0, -1,3,1,  1,3,1,  1,3,-1,  -1,3,-1,  0,-7,0, -1,-3,1,  1,-3,1,  1,-3,-1,  -1,-3,-1,  0,0,4, 4,0,0, 0,0,-4, -4,0,0};
private static float[] cl = new float[] {0,1,0,1,  0,0,1,1,  0,0,1,1,   0,0,1,1,  0,0,1,1,   0,1,0,1,   0,0,1,1,  0,0,1,1,  0,0,1,1,  0,0,1,1,  0,1,0,1,  0,1,0,1,  0,1,0,1, 0,1,0,1};
private static float[] cl2 = new float[] {0,0,1,1,  0,1,0,1,  0,1,0,1,   0,1,0,1,  0,1,0,1,   0,0,1,1,   0,1,0,1,  0,1,0,1,  0,1,0,1,  0,1,0,1,  0,0,1,1,  0,0,1,1,  0,0,1,1, 0,0,1,1};
private static float[] cl3 = new float[] {1,1,(float).2,1,  1,0,0,1,  1,0,0,1,   1,0,0,1,  1,0,0,1,   1,1,(float).2,1,   1,0,0,1,  1,0,0,1,  1,0,0,1,  1,0,0,1,  1,1,(float).2,1,  1,1,(float).2,1,  1,1,(float).2,1, 1,1,(float).2,1};
private static float[] cl4 = new float[] {1,0,0,1,  1,1,1,1,  1,1,1,1,   1,1,1,1,  1,1,1,1,   1,0,0,1,   1,1,1,1,  1,1,1,1,  1,1,1,1,  1,1,1,1,  1,0,0,1,  1,0,0,1,  1,0,0,1, 1,0,0,1};

private static int[] triangles = new int[] {0,1,2,  0,2,3,  0,3,4,  0,4,1,  1,4,13,  4,9,13,  6,9,13,  1,6,13, 1,2,10,  1,6,10,  6,7,10, 2,7,10, 2,3,11, 2,7,11, 3,8,11, 7,8,11,  3,4,12,  3,8,12,  4,9,12, 8,9,12, 6,7,5, 7,8,5, 8,9,5, 9,6,5};
FloatBuffer colorBuf, colorBuf2, colorBuf3, colorBuf4;
ArrayList<FloatBuffer> floatBuffer;
private Boolean pointsUsed = false;
private int points = 0;
private int rotateOnY;
	
	
 public Gem()
 { 
 
	 
	 points = randomInt(10,30);
	 
	 FloatBuffer vertBuf =
	 com.jogamp.common.nio.Buffers.newDirectFloatBuffer(vrts);
     this.setVertexBuffer(vertBuf);
 
 
 int chooseColor = randomInt(1,3);
 Random r = new Random();
 floatBuffer = new ArrayList();

 
colorBuf =com.jogamp.common.nio.Buffers.newDirectFloatBuffer(cl);
  	//		this.setColorBuffer(colorBuf);
 
 
colorBuf2 = com.jogamp.common.nio.Buffers.newDirectFloatBuffer(cl2);
 	//		this.setColorBuffer(colorBuf2);
 
 
colorBuf3 = com.jogamp.common.nio.Buffers.newDirectFloatBuffer(cl3);
 	//		this.setColorBuffer(colorBuf3);

colorBuf4 = com.jogamp.common.nio.Buffers.newDirectFloatBuffer(cl4);
 			
 floatBuffer.add(colorBuf);
 floatBuffer.add(colorBuf2);
 floatBuffer.add(colorBuf3);
 
 this.setColorBuffer(floatBuffer.get(r.nextInt(3)));

 
  
  
 IntBuffer triangleBuf =
 com.jogamp.common.nio.Buffers.newDirectIntBuffer(triangles);
 this.setIndexBuffer(triangleBuf); 
 
 
float one = (float)randomInt(15,20);
float two = (float)randomInt(25, 35);
float three = (float) randomInt(40, 80);
float[] scales = new float[] {one, two, three};
Random r2 = new Random();
float theScale = (float) scales[r2.nextInt(3)];
 
// this.scale((theScale/100), (theScale/100), (theScale/100));
 this.scale(.3f, .3f, .3f);
 
rotateOnY = randomInt(0,2);


 
 
 } 
 
 	public int getRotateOnY()
 	{
 		return rotateOnY;
 	}
 


 	public Boolean getPointsUsed()
	{
		return pointsUsed;
	}
	
	public void usePoints()
	{
		pointsUsed = true;
		changeToUsed();
	}
	
	private void changeToUsed()
	{
		this.setColorBuffer(colorBuf4);
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
	
}