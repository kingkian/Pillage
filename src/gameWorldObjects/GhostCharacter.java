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
import java.util.UUID;

import graphicslib3D.Matrix3D;
import graphicslib3D.Vector3D;
import helpers.MyLifeBarController;
import helpers.MyMoveController;
import m2.MyGame;
import sage.scene.TriMesh;

public class GhostCharacter extends TriMesh implements ICharacter
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
	
private boolean isSelected = false;
private double maxMove = 42;
private double maxAttack = 12;
	
private MyMoveController myMoveController;
private MyLifeBarController myLifeBarController;

private Tile lifeBar;
private boolean showLifeBar;
private double life = 100.0;

private boolean moveUsed = false;
private boolean attackUsed = false;
private boolean isKing = false;


private int attack = 20;
private boolean isAlive = true;


private String characterType;
private double x, y, z;
private int gridI, gridJ;
private int characterID;
private UUID playerID;
private MyGame game;

 public GhostCharacter(UUID pID, int cID, String cType, int gI, int gJ, MyGame game)
 { 
	 x = 0;
	 y = 0;
	 z = 0;
	 life = 100;
	 
	 if(cType=="W")
	 {
		 maxMove = 42;
		 attack = 20;
		 maxAttack = 12;
		 
	 }
	 else if(cType=="A")
	 {
		 maxMove = 52;
		 attack = 15;
		 maxAttack = 22;
	 }
	 else if(cType=="B")
	 {
		 maxMove = 32;
		 attack = 25;
		 maxAttack = 12;
	 }
	 else if(cType=="K")
	 {
		
		 maxMove = 22;
		 attack = 15;
		 maxAttack = 12;
		 isKing = true;
	 }
	 
	 
	 characterType = cType;
	 characterID = cID;
	 playerID = pID;
	 
	 gridI = gI;
	 gridJ = gJ;
	 

	 this.game = game;
	 
 
	 this.myMoveController = new MyMoveController(this);
	 this.addController(myMoveController);
	 
	 lifeBar = new Tile();
	 Vector3D axis = new Vector3D(1,0,0);
	 lifeBar.rotate(-45,  axis);
	 lifeBar.setColorBuffer("lifeGreen");
	 Matrix3D lifeBarScale = new Matrix3D();
	 lifeBarScale.scale(4f,0,1.2f);
	 lifeBar.setLocalScale(lifeBarScale);
	 showLifeBar = false;
	 myLifeBarController = new MyLifeBarController(lifeBar, this);
	 lifeBar.addController(myLifeBarController);
	 
	 
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
 floatBuffer.add(colorBuf4);
 
// this.setColorBuffer(floatBuffer.get(r.nextInt(3)));

 this.setColorBuffer(floatBuffer.get(3));
 
  
  
 IntBuffer triangleBuf =
 com.jogamp.common.nio.Buffers.newDirectIntBuffer(triangles);
 this.setIndexBuffer(triangleBuf); 
 
  
// this.scale((theScale/100), (theScale/100), (theScale/100));
 this.scale(.3f, .3f, .3f);
 this.scale(3f,3f,3f);
 
rotateOnY = randomInt(0,2);

 } 
 
 
 public GhostCharacter(int cID, String cType, int gI, int gJ, MyGame game)
 { 
	 x = 0;
	 y = 0;
	 z = 0;
	 life = 100;
	 
	 if(cType=="W")
	 {
		 maxMove = 42;
		 attack = 20;
		 maxAttack = 12;
		 
	 }
	 else if(cType=="A")
	 {
		 maxMove = 52;
		 attack = 15;
		 maxAttack = 22;
	 }
	 else if(cType=="B")
	 {
		 maxMove = 32;
		 attack = 25;
		 maxAttack = 12;
	 }
	 else if(cType=="K")
	 {
		 maxMove = 22;
		 attack = 15;
		 maxAttack = 12;
		 isKing = true;
	 }
	 
	 
	 characterType = cType;
	 characterID = cID;
	 
	 gridI = gI;
	 gridJ = gJ;
	 

	 this.game = game;
	 
 
	 this.myMoveController = new MyMoveController(this);
	 this.addController(myMoveController);
	 
	 lifeBar = new Tile();
	 Vector3D axis = new Vector3D(1,0,0);
	 lifeBar.rotate(-45,  axis);
	 lifeBar.setColorBuffer("lifeGreen");
	 Matrix3D lifeBarScale = new Matrix3D();
	 lifeBarScale.scale(4f,0,1.2f);
	 lifeBar.setLocalScale(lifeBarScale);
	 myLifeBarController = new MyLifeBarController(lifeBar, this);
	 lifeBar.addController(myLifeBarController);
	 showLifeBar = false;
	 
	 
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
 floatBuffer.add(colorBuf4);
 
// this.setColorBuffer(floatBuffer.get(r.nextInt(3)));

 this.setColorBuffer(floatBuffer.get(3));
 
  
  
 IntBuffer triangleBuf =
 com.jogamp.common.nio.Buffers.newDirectIntBuffer(triangles);
 this.setIndexBuffer(triangleBuf); 
 
  
// this.scale((theScale/100), (theScale/100), (theScale/100));
 this.scale(.3f, .3f, .3f);
 this.scale(3f,3f,3f);
 
rotateOnY = randomInt(0,2);

 } 
 
 
 	public boolean isKing()
 	{
 		return isKing;
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
	
	public boolean isSelected()
	{
		return isSelected;
	}
	
	public void toggleSelected()
	{
		if(isSelected)
		{
			isSelected = false;
		
			this.setColorBuffer(floatBuffer.get(0));
			
		}
		else
		{
			isSelected = true;
		
			this.setColorBuffer(floatBuffer.get(2));
		}
	}
	
	public double getMaxMove()
	{
		return maxMove;
	}
	
	
	
	public MyMoveController getMyMoveController()
	{
		return myMoveController;
	}
	
	
	
	public int getAttack()
 	{
 		return (int)attack;
 	}
 	
 	public void setAttack(int newAttack)
 	{
 		attack = newAttack;
 	}
 	
 	public double getLife()
 	{
 		return life;
 	}
 	
 	public void setLife(double newLife)
 	{
 		life = newLife;
 	}
 	
	
	
	public void toggleShowLifeBar()
	{
		if(showLifeBar)
		{
			showLifeBar = false;
		}
		else
		{
			showLifeBar = true;
		}
	}
	
	public void updateLifeBar()
	{
		updateLifeBarRotation();
		
		if(showLifeBar)
		{
			double characterX = this.getLocalTranslation().getCol(3).getX();
			double characterZ = this.getLocalTranslation().getCol(3).getZ();
			Matrix3D lifeBar = new Matrix3D();
			lifeBar.translate(characterX-3,  15,  characterZ);
			this.lifeBar.setLocalTranslation(lifeBar);
			
			if(game.isCharacterSelected())
			{
				
				if(getLife() > 70)
				{
					this.lifeBar.setColorBuffer("lifeGreen");
				}
				else if(getLife() > 30)
				{
					this.lifeBar.setColorBuffer("lifeOrange");
				}
				else
				{
					this.lifeBar.setColorBuffer("lifeRed");
				}
				
			}
			else
			{
			
				
				if(getLife() > 70)
				{
					this.lifeBar.setColorBuffer("green");
		
				}
				else if(getLife() > 30)
				{
					this.lifeBar.setColorBuffer("orange");
				}
				else
				{
					this.lifeBar.setColorBuffer("red");
				}
				
			}
			
			
		}
		else
		{
			double characterX = this.getLocalTranslation().getCol(3).getX();
			double characterZ = this.getLocalTranslation().getCol(3).getZ();
			Matrix3D lifeBar = new Matrix3D();
			lifeBar.translate(characterX-3,  -30,  characterZ);
			this.lifeBar.setLocalTranslation(lifeBar);
		}

		
	}
	
	public void updateLifeBarRotation()
	{
		if(showLifeBar)
		{
			Vector3D rightAxis = game.getCamera().getRightAxis();
			
			Matrix3D rotation = lifeBar.getLocalRotation();
			rotation.setRow(0, rightAxis);
			
		}
	}
	

	public String getCharacterTypeName()
	{
	
		return characterType;

	}
	
	public Tile getLifeBar()
	{
		return lifeBar;
	}
	
	public void setShowLifeBar(boolean b)
	{
		showLifeBar = b;
		
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
	
	public int getGridI()
	{
		return gridI;
	}
	
	public int getGridJ()
	{
		return gridJ;
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
	
	public int getCharacterID()
	{
		return characterID;
	}
	
	public UUID getPlayerID()
	{
		return playerID;
	}
	
	public void moveGhost(int x, int z)
	{
		
		int gridI = getGridI();
		int gridJ = getGridJ();
		boolean houseOnX = false;
		boolean houseOnZ = false;
		
		
		int zz = 0;
		if(z<0)
		{
			zz = z*-1;
		}
		else
		{
			zz = z;
		}
		
		int xx = 0;
		if(x<0)
		{
			xx = x*-1;
		}
		else
		{
			xx  = x;
		}
		
		for(int i = 0; i<zz; i++)
		{
			int offset = i;
			if(z<0)
			{
				offset = offset*-1;
			}
			
			for(int j = 1; j<xx+1; j++)
			{
				if(x>0)
				{
					if((gridI-offset)>=0 && (gridJ-j)>=0)
					{
					if(game.getGrid().getTileGrid()[(gridI-offset)][gridJ-j].isCovered())
					{
						houseOnX = true;
					}
					}
				}
				else
				{
					if((gridI-offset)>=0 && (gridJ+j)<=63)
					{
					if(game.getGrid().getTileGrid()[(gridI-offset)][gridJ+j].isCovered())
					{
						houseOnX = true;
					}
					}
				}
			}
		}
		
		
		if(houseOnX)
		{
			this.myMoveController.makeMove(x, z, false);
		}
		else
		{
			this.myMoveController.makeMove(x, z, true);
		}
		
		
		
	//	this.myMoveController.makeMove(x, z);
	}
	public void finishMove()
	{
		Matrix3D newTranslation = new Matrix3D();
		newTranslation.translate(getX(), getY(), getZ());
		this.setLocalTranslation(newTranslation);
		
	}


	@Override
	public double getMaxAttack() {
		return maxAttack;
	}
	

	@Override
	public boolean attackUsed() {
		
		return attackUsed;
	}


	@Override
	public boolean moveUsed() {
		// TODO Auto-generated method stub
		return moveUsed;
	}

	@Override
	public boolean takeHit(int attackDamage) {
		
		boolean killed = myLifeBarController.takeHit(attackDamage);
	
		return killed;
	
	}


	@Override
	public void finishTakingHit() {
	
		gameWorldObjects.Character selectedCharacter = game.getMyTeam().get(0);
		
		while(true)
		{
				for(int i = 0; i<game.getMyTeam().size(); i++)
				{
					if(game.getMyTeam().get(i).isSelected())
					{
						selectedCharacter = game.getMyTeam().get(i);
						break;
					}
				}
				break;
		}
	
		
		
	
		
		
	
	}
	
	public void setAlive(boolean b)
	{
		isAlive = b;
	}
	
	public boolean isAlive()
	{
		return isAlive;
	}
	
	
	public void die()
	{
		if(isAlive())
		{
		game.getGrid().getTileGrid()[getGridI()][getGridJ()].unOccupy();
		this.setGridIJ(63, 63);
		this.translate(200,200,2000);
		this.setLocation(1000,1000,1000);
		this.lifeBar.translate(200, 200, 2000);		
		this.setAlive(false);
		game.checkGameOver();
		
		
			
		}
		else
		{
			
		}
	
	}

/*
	@Override
	public void initAudio() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void playMoveSound() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void playAttackSound() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void playSelectSound() {
		// TODO Auto-generated method stub
		
	}
	*/
}