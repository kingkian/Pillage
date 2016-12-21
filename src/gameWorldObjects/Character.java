//Kian Faroughi
//Csc165 - Assignment 2
//Doctor Gordon
//CSUS Fall 2015
//Gem defined my programmer
//Gem has multiple color buffers. When its points have been used, the gem sets color to specific collor buffer


package gameWorldObjects;

import java.io.File;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Random;

import graphicslib3D.Matrix3D;
import graphicslib3D.Point3D;
import graphicslib3D.Vector3D;
import helpers.MyLifeBarController;
import helpers.MyMoveController;
import m2.MyGame;
import sage.audio.AudioResource;
import sage.audio.AudioResourceType;
import sage.audio.Sound;
import sage.audio.SoundType;
import sage.scene.TriMesh;

public class Character extends TriMesh implements ICharacter
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
private boolean moveUsed = false;
private boolean attackUsed = false;
private boolean isKing = false;


private AudioResource resource1, resource2, resource3;
private Sound selectSound, moveSound, attackSound;

private int volume;


private double life = 100;

private int attack = 20;
private boolean isAlive = true;


private String characterType;
private double x, y, z;
private int gridI, gridJ;
private int characterID;

private MyGame game;

 public Character(String s, int i, MyGame game)
 { 
	 
	
	 if(s=="W")
	 {
		 maxMove = 42;
		 attack = 20;
		 maxAttack = 12;
		 
	 }
	 else if(s=="A")
	 {
		 maxMove = 52;
		 attack = 15;
		 maxAttack = 22;
	 }
	 else if(s=="B")
	 {
		 maxMove = 32;
		 attack = 25;
		 maxAttack = 12;
	 }
	 else if(s=="K")
	 {
		 maxMove = 22;
		 attack = 15;
		 maxAttack = 12;
		 isKing = true;
	 }
	 

	 
	 
	 life = 100;
	 
	 
	 
	 this.game = game;
	 
	 characterType = s;
	 characterID = i;
 
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
 
// this.setColorBuffer(floatBuffer.get(r.nextInt(3)));

 this.setColorBuffer(floatBuffer.get(0));
 
  
  
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
 this.scale(3f,3f,3f);


x = 0;
y = 0;
z = 0;




 } 

 
 public boolean isKing()
 {
	 return isKing;
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
	
	public void setShowLifeBar(boolean b)
	{
		showLifeBar = b;
		
	}
	
	
	
	public void updateLifeBar()
	{
	
		
		if(showLifeBar)
		{
			double characterX = this.getLocalTranslation().getCol(3).getX();
			double characterZ = this.getLocalTranslation().getCol(3).getZ();
			Matrix3D lifeBar = new Matrix3D();
			lifeBar.translate(characterX-3.5,  12,  characterZ+4);
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
			lifeBar.translate(characterX-3.5,  -30,  characterZ+4);
			this.lifeBar.setLocalTranslation(lifeBar);
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

	public void setMoveUsed(boolean b)
	{
		moveUsed = b;
	}
	
	public void setAttackUsed(boolean b)
	{
		attackUsed = b;
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
		this.translate(200,200,2000);
		this.setLocation(1000,1000,1000);
		this.lifeBar.translate(200, 200, 2000);
		this.setGridIJ(63, 63);
		this.setAlive(false);
		game.checkGameOver();
		
	
		
		}
		else {
			
		}
		
	}

/*
	@Override
	public void initAudio() {
		
		String folderLoc = "sounds" + File.separator;
		

			// TODO Auto-generated method stub
			 resource1 = game.getAudioManager().createAudioResource(folderLoc+"yougonnagivemeordersfinal.wav",
			AudioResourceType.AUDIO_SAMPLE);
			selectSound = new Sound(resource1, SoundType.SOUND_EFFECT, 100, true);
			selectSound.initialize(game.getAudioManager());
			selectSound.setMaxDistance(50.0f);
			selectSound.setMinDistance(3.0f);
			selectSound.setRollOff(5.0f);
			selectSound.setLocation(new Point3D(getX(), getY(), getZ()));
			selectSound.setVolume(100);
			
			// TODO Auto-generated method stub
			 resource2 = game.getAudioManager().createAudioResource(folderLoc+"beenwaitingonyafinal.wav",
			AudioResourceType.AUDIO_SAMPLE);
			moveSound = new Sound(resource1, SoundType.SOUND_EFFECT, 100, true);
			moveSound.initialize(game.getAudioManager());
			moveSound.setMaxDistance(50.0f);
			moveSound.setMinDistance(3.0f);
			moveSound.setRollOff(5.0f);
			moveSound.setLocation(new Point3D(getX(), getY(), getZ()));
			moveSound.setVolume(100);
			// TODO Auto-generated method stub
			 resource3 = game.getAudioManager().createAudioResource(folderLoc+"youwannapieceofmeboyfinal.wav",
			AudioResourceType.AUDIO_SAMPLE);
			attackSound = new Sound(resource1, SoundType.SOUND_EFFECT, 100, true);
			attackSound.initialize(game.getAudioManager());
			attackSound.setMaxDistance(50.0f);
			attackSound.setMinDistance(3.0f);
			attackSound.setRollOff(5.0f);
			attackSound.setLocation(new Point3D(getX(), getY(), getZ()));	
			attackSound.setVolume(100);
			

			System.out.println("Audio initialized.");
		

		
		
	}

	public void setEarParameters()
	{
		 Matrix3D avDir = (Matrix3D) (game.getCursorObject().getLocalRotation());
		 float camAz = game.getCamera3P().getAzimuth();
		 avDir.rotateY(180.0f-camAz);
		 Vector3D camDir = new Vector3D(0,0,1);
		 camDir = camDir.mult(avDir);

		 
	}
	
	
	public void updateSoundDistances(float elapsedTimeMS)
	{
		selectSound.setLocation(new Point3D(getX(), getY(), getZ()));
		moveSound.setLocation(new Point3D(getX(), getY(), getZ()));
		attackSound.setLocation(new Point3D(getX(), getY(), getZ()));

		
		setEarParameters();


		
		Point3D ear =  game.getAudioManager().getEar().getLocation();
		Point3D location = new Point3D(getX(), getY(), getZ());
		
		double distanceX = ear.getX() - getX();
		double distanceZ = ear.getZ() - getZ();
		
		if(distanceX<0)
		{
			distanceX*=-1;
		}
		if(distanceZ<0)
		{
			distanceZ*=-1;
		}
		
		double totalDistance = distanceX + distanceZ;
		
		totalDistance /= 10;
		
		volume = (int)(100-(totalDistance*4));
		
		selectSound.setVolume(volume);
		moveSound.setVolume(volume);
		attackSound.setVolume(volume);
	}

	
	

	@Override
	public void playMoveSound() {
		// TODO Auto-generated method stub
			
		
			moveSound.play();
	}


	@Override
	public void playAttackSound() {
		// TODO Auto-generated method stub
		attackSound.play();
	}


	@Override
	public void playSelectSound() {
		// TODO Auto-generated method stub
		selectSound.play();
	}
	
*/
	
	
}