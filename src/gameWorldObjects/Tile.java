//Kian Faroughi
//Csc165 - Assignment 2
//Doctor Gordon
//CSUS Fall 2015
//Extention of TriMesh, Code from Dr. Gordon


package gameWorldObjects;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.UUID;

import sage.scene.TriMesh;
import sage.scene.shape.Teapot;
import sage.texture.Texture;
import sage.texture.TextureManager;

public class Tile extends TriMesh
{
	


private static float transparency =0.5f;	
private static float transparency2 = 0.15f;

private static float transparency3 = 0.2f;

private static float[] vrts = new float[] {1,-1,1,-1,-1,1,-1,-1,-1,1,-1,-1,1,1,1,-1,1,1,-1,1,-1,1,1,-1};

private static float[] tileVertices = new float[] {1,0,1,1,0,-1,-1,0,-1,-1,0,1};
private static float[] blue = new float[] {0.0f,0.0f,1.0f,transparency,0.0f,0.0f,1.0f,transparency, 0.0f,0.0f,1.0f,transparency, 0.0f,0.0f,1.0f,transparency}; 
private static int[] tileTriangles = new int[]{0,1,2,3,0,2};
private static float[] orange = new float[] {1.0f,0.55f, 0.0f, transparency, 1.0f,0.55f, 0.0f, transparency, 1.0f,0.55f, 0.0f, transparency, 1.0f,0.55f, 0.0f, transparency};
private static float[] orange2 = new float[] {1.0f,0.55f, 0.0f, transparency2, 1.0f,0.55f, 0.0f, transparency2, 1.0f,0.55f, 0.0f, transparency2, 1.0f,0.55f, 0.0f, transparency2};


private static float[] blue2 = new float[] {0.0f,0.0f,1.0f,transparency2,0.0f,0.0f,1.0f,transparency2, 0.0f,0.0f,1.0f,transparency2, 0.0f,0.0f,1.0f,transparency2}; 
private static float[] red2 = new float[] {1.0f, 0.6f, 0.6f, transparency2, 1.0f, 0.6f, 0.6f, transparency2,1.0f, 0.6f, 0.6f, transparency2,1.0f, 0.6f, 0.6f, transparency2};

private static float[] white = new float[] {1.0f, 1.0f, 1.0f, transparency3, 1.0f, 1.0f, 1.0f,transparency3, 1.0f, 1.0f, 1.0f, transparency3, 1.0f, 1.0f, 1.0f, transparency3};
private static float[] white2 = new float[] {1.0f, 1.0f, 1.0f, transparency, 1.0f, 1.0f, 1.0f,transparency, 1.0f, 1.0f, 1.0f, transparency, 1.0f, 1.0f, 1.0f, transparency};


private static float[] textureCoordinates = new float[] {1,0,1,1,0,1,0,0};
private static float[] cl = new float[] {.8f,.8f,.8f,1,.8f,.8f,.8f,1,.8f,.8f,.8f,1,.8f,.8f,.8f,1,.8f,.8f,.8f,1,.8f,.8f,.8f,1,.8f,.8f,.8f,1,.8f,.8f,.8f,1};

private static float[] yellow = new float[] {1.0f,1.0f, 0.0f, transparency, 1.0f,1.0f, 0.0f, transparency, 1.0f,1.0f, 0.0f, transparency, 1.0f,1.0f, 0.0f, transparency};
private static float[] purple = new float[] {0.8f,0.7f, 1.0f, transparency, 0.8f,0.7f, 1.0f, transparency, 0.8f,0.7f, 1.0f, transparency, 0.8f,0.7f, 1.0f, transparency};
private static float[] red = new float[] {1.0f, 0.6f, 0.6f, transparency, 1.0f, 0.6f, 0.6f, transparency,1.0f, 0.6f, 0.6f, transparency,1.0f, 0.6f, 0.6f, transparency};
private static float[] green = new float[] {0.6f, 1.0f, 0.6f, transparency, 0.6f, 1.0f, 0.6f, transparency, 0.6f, 1.0f, 0.6f, transparency, 0.6f, 1.0f, 0.6f, transparency};

private static float[] lifeGreen = new float[] {0.0f, 1.0f, 0.0f, 0.7f, 0.0f, 1.0f, 0.0f, 0.7f, 0.0f, 1.0f, 0.0f, 0.7f, 0.0f, 1.0f, 0.0f, 0.7f};
private static float[] lifeOrange = new float[] {1.0f, 0.65f, 0.0f, 0.7f, 1.0f, 0.65f, 0.0f, 0.7f,1.0f, 0.65f, 0.0f, 0.7f,1.0f, 0.65f, 0.0f, 0.7f};
private static float[] lifeRed = new float[] {1.0f, 0.0f, 0.0f, 0.7f, 1.0f, 0.0f, 0.0f, 0.7f,1.0f, 0.0f, 0.0f, 0.7f,1.0f, 0.0f, 0.0f, 0.7f};



private double x, y, z;
private int gridI, gridJ;

private Texture cursorTexture;
private Texture blankTexture;

public Tile()
{
	 FloatBuffer vertBuf = com.jogamp.common.nio.Buffers.newDirectFloatBuffer(tileVertices);
			 
	 IntBuffer triangleBuf = com.jogamp.common.nio.Buffers.newDirectIntBuffer(tileTriangles);
	 
	 FloatBuffer textures = com.jogamp.common.nio.Buffers.newDirectFloatBuffer(textureCoordinates);

			 
	 FloatBuffer colorBuf = com.jogamp.common.nio.Buffers.newDirectFloatBuffer(blue);		 
	 
	 this.setVertexBuffer(vertBuf);
	 this.setTextureBuffer(textures);
			 
	 this.setIndexBuffer(triangleBuf); 
			 
//	 this.setColorBuffer(colorBuf);
	
	 this.scale(5.0f, 0, 5.0f);
	 

	 


	 
	 
}


public void setColorBuffer(String s)
{ 
	FloatBuffer colorBuf;
	
	
	if(s=="blue")
	 {
		 colorBuf =
		 com.jogamp.common.nio.Buffers.newDirectFloatBuffer(blue);
		 this.setColorBuffer(colorBuf);
		
	//	this.setTexture(cursorTexture);
		
	 }
	else if(s=="white")
	{
		
		colorBuf =
				 com.jogamp.common.nio.Buffers.newDirectFloatBuffer(white);
				 this.setColorBuffer(colorBuf);
		

	}
	else if(s=="white2")
	{
		
		colorBuf =
				 com.jogamp.common.nio.Buffers.newDirectFloatBuffer(white2);
				 this.setColorBuffer(colorBuf);
		

	}
	
	else if(s=="blue2")
	 {
		 colorBuf =
		 com.jogamp.common.nio.Buffers.newDirectFloatBuffer(blue2);
		 this.setColorBuffer(colorBuf);
		
	//	this.setTexture(cursorTexture);
		
	 }
	
	
	
	 else if(s=="green")
	 {
		colorBuf =
		com.jogamp.common.nio.Buffers.newDirectFloatBuffer(green);
		this.setColorBuffer(colorBuf);
		
	//	this.setTexture(cursorTexture);

	 }
	 else if(s=="red")
	 {
		  colorBuf =
		 com.jogamp.common.nio.Buffers.newDirectFloatBuffer(red);
		  this.setColorBuffer(colorBuf);
		  
	//	this.setTexture(cursorTexture);

	 }
	 else if(s=="red2")
	 {
		  colorBuf =
		 com.jogamp.common.nio.Buffers.newDirectFloatBuffer(red2);
		  this.setColorBuffer(colorBuf);
		  
	//	this.setTexture(cursorTexture);

	 }
	 else if(s=="purple")
	 {	
		
		 colorBuf = 
		 com.jogamp.common.nio.Buffers.newDirectFloatBuffer(purple);
		  this.setColorBuffer(colorBuf);
		  
	//	this.setTexture(cursorTexture);

	 }
	 else if(s=="yellow")
	 {	
		 colorBuf = 
		 com.jogamp.common.nio.Buffers.newDirectFloatBuffer(yellow);
		  this.setColorBuffer(colorBuf);
		  
	//	this.setTexture(cursorTexture);

				
	 }
	 else if(s=="lifeGreen")
	 {
		colorBuf =
		com.jogamp.common.nio.Buffers.newDirectFloatBuffer(lifeGreen);
		this.setColorBuffer(colorBuf);
		
//		this.setTexture(blankTexture);

	 }
	 else if(s=="lifeOrange")
	 {
		colorBuf =
		com.jogamp.common.nio.Buffers.newDirectFloatBuffer(lifeOrange);
		this.setColorBuffer(colorBuf);
//		this.setTexture(blankTexture);

	 }
	 else if(s=="lifeRed")
	 {
		colorBuf =
		com.jogamp.common.nio.Buffers.newDirectFloatBuffer(lifeRed);
		this.setColorBuffer(colorBuf);
//		this.setTexture(blankTexture);

	 }
	 else if(s=="orange")
	 {
		 colorBuf =
					com.jogamp.common.nio.Buffers.newDirectFloatBuffer(orange);
					this.setColorBuffer(colorBuf);
	 }
	 else if(s=="orange2")
	 {
		 colorBuf =
					com.jogamp.common.nio.Buffers.newDirectFloatBuffer(orange2);
					this.setColorBuffer(colorBuf);
	 }
	 else
	 {
	 colorBuf =
	 com.jogamp.common.nio.Buffers.newDirectFloatBuffer(cl);
	 this.setColorBuffer(colorBuf);
	 }
}




public void setLocation(double x, double y, double z)
{
	this. x = x;
	this.y = y;
	this.z = z;
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

public double getX()
{
	return x;
}

public double getY()
{
	return y;
}

public double getZ()
{
	return z;
}

public void setX(double x)
{
	this.x = x;
}

public void setY(double y)
{
	this.y = y;
}

public void setZ(double z)
{
	this.z = z;
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


