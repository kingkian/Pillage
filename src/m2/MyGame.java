//Kian Faroughi
//Csc165 - Assignment 2
//Doctor Gordon
//CSUS Fall 2015

package m2;

import sage.audio.*;
import com.jogamp.openal.ALFactory;
import sage.animation.AnimationSequence;
import sage.app.BaseGame;
import sage.audio.AudioManagerFactory;
import sage.audio.AudioResource;
import sage.audio.AudioResourceType;
import sage.audio.IAudioManager;
import sage.audio.Sound;
import sage.audio.SoundType;
import sage.camera.ICamera;
import sage.camera.JOGLCamera;
import sage.display.*;
import sage.event.EventManager;
import sage.event.IEventListener;
import sage.event.IEventManager;
import sage.event.IGameEvent;
import sage.input.IInputManager;
import sage.input.InputManager;
import sage.model.loader.ogreXML.OgreXMLParser;
import sage.networking.IGameConnection.ProtocolType;
import sage.physics.IPhysicsEngine;
import sage.physics.IPhysicsObject;
import sage.physics.PhysicsEngineFactory;
import sage.renderer.IRenderer;
import sage.scene.SceneNode;
import sage.scene.SkyBox;
import sage.scene.SkyBox.Face;
import sage.scene.TriMesh;
import sage.scene.bounding.BoundingBox;
import sage.scene.bounding.BoundingVolume;
import sage.scene.shape.Cube;
import sage.scene.shape.Line;
import sage.scene.shape.Pyramid;
import sage.scene.shape.Rectangle;
import sage.scene.shape.Sphere;
import sage.scene.shape.Teapot;
import sage.scene.state.RenderState;
import sage.scene.state.TextureState;
import sage.terrain.AbstractHeightMap;
import sage.terrain.HillHeightMap;
import sage.terrain.ImageBasedHeightMap;
import sage.terrain.TerrainBlock;
import sage.texture.Texture;
import sage.texture.TextureManager;
import sage.scene.Group;
import sage.scene.HUDImage;
import sage.scene.HUDString;
import sage.scene.Model3DTriMesh;
import graphicslib3D.Matrix3D;
import graphicslib3D.MatrixStack;
import graphicslib3D.Point3D;
import graphicslib3D.Vector3D;
import helpers.MoveCameraEvent;
import helpers.MyCameraMoveController;
import helpers.MyHoveringCursorController;
import helpers.MyMoveController;
import net.java.games.input.Controller;
import net.java.games.input.Keyboard;
import physics.PhysicsBase;
import physics.StartPhysics;

import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.TreeMap;
import java.util.UUID;
import sage.scene.state.BlendState;
import sage.renderer.IRenderer;
import sage.scene.state.RenderState.RenderStateType;
import sage.scene.SceneNode.RENDER_MODE;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.swing.JOptionPane;

import actions.DeselectCharacter;
import actions.SelectCharacter;
import m2.Camera3P;

import gameWorldObjects.*;
import gameWorldObjects.Character;
import gameWorldObjects.models.CustomObject;
import gameWorldObjects.models.GroundPlaneTile;
import gameWorldObjects.models.House;
import keyboardMovement.*;
import client.Client;
import movementActions.*;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.text.DecimalFormat;


public class MyGame extends BaseGame implements IEventListener{
	

private HUDImage myTurnHUD, enemyTurnHUD, youWonHUD, youLostHUD;
private HUDImage infoHUD;
private HUDString teamMembersAliveHUD, moveLeftHUD, attackLeftHUD, kingLifeHUD;
private String teamMembersAliveString, moveLeftString, attackLeftString;


private int moveTurnLeft;
private int attackTurnLeft;
private boolean gameOver = false;
private boolean winner = false;
private boolean loser = false;

private float time = 0;
private HUDString timeString;

private MyDisplaySystem myDisplay;
private IDisplaySystem display;
private IRenderer renderer;

private boolean keyBoard;
private boolean playerOne;
private boolean myTurn;

IEventManager eventManager;
IInputManager im;
private ArrayList<Controller> controllers;

private int frameWidth = 1024;
private int frameHeight = 768;
private boolean isFullScreenMode = false;

private Client client;
private boolean isConnected = false;
private String serverAddress;
private int serverPort;
private ProtocolType serverProtocol;


private ScriptEngine jsEngine;
private MatrixStack matrixStack;
private FileReader fileReader;
private String scriptFileName = "scriptFile.js";
private String scriptFileName2 = "scriptFileDynamic.js";
private File dynamicScript = new File("scriptFileDynamic.js");
private long fileLastModifiedTime = dynamicScript.lastModified();
private boolean dynamicScriptOn;

private ICamera camera1;
private Camera3P cc1;
private SkyBox skybox1, skybox;
private MyCube cameraPointer;
private double cameraPointerSpawnX = 15.0f;
private double cameraPointerSpawnY = 0.6f;
private double cameraPointerSpawnZ = -5.0f;
private double cameraPointerScaleX = 4f;
private double cameraPointerScaleY = 0.2f;
private double cameraPointerScaleZ = 4f;
private String cameraPointerColor = "red";
private boolean goingUpOrDown = false;
private boolean goingLeftOrRight = false;
private boolean cameraMovedYet = false;
private boolean moveCameraUpDown = false;
private boolean moveCameraLeftRight =  false;
private boolean moveLeftAndUp = false;
private boolean moveRightAndUp = false;


private MyCameraMoveController moveCameraController;


private MyHoveringCursorController hoveringCursorController;


private Tile cursorObject;
private double cursorObjectX = 0;
private double cursorObjectY = 0;
private double cursorObjectZ = 0;
private double cursorObjectScaleX = 5.0f;
private double cursorObjectScaleY = 0;
private double cursorObjectScaleZ = 5.0f;
private Texture cursorTextureYellow, cursorTextureGreen, cursorTextureKing, cursorTextureAttack, cursorTextureMove, cursorTextureX, cursorTextureClear, cursorTextureTeam, cursorTextureEnemy;
private Texture hoveringCursorTeam, hoveringCursorAttack, hoveringCursorPointer, hoveringCursorKing, hoveringCursorMove, hoveringCursorEnemy;


private Tile hoveringCursorObject;
private double hoverCursorObjectX = 0;
private double hoveringCursorObjectY = 0;
private double hoveringCursorObjectZ = 0;
private double hoveringCursorObjectScaleX = 5.0f;
private double hoveringCursorObjectScaleY = 0;
private double hoveringCursorObjectScaleZ = 5.0f;


private double houseScaleX, houseScaleY,houseScaleZ, castleX, castleY, castleZ;
private SceneNode house1, house2, house3, house4, house5, house6, house7, house8, house9, house10, house11, house12;




private double mapTileY = 0;
private double mapTileScaleX = 7;
private double mapTileScaleY = 0;
private double mapTileScaleZ = 7;
private ArrayList<SceneNode> groundPlaneTiles;

private ArrayList<Tile> groundPlaneBlue;
private double transparentGroundBlueShow = 0.2;
private double transparentGroundBlueNoShow = -1.0;
private boolean showGroundPlane = false;

private Matrix3D defaultScaleGroundPlaneBlue;

private Grid tileGrid;


private ArrayList<Line> grid;
private int width = 400;
private int gridWidth = 380;
private int offset = 10;

private double gridYSelect = 0.7;
private double gridYNoSelect = -.7;
private double gridY = -0.7;
private String gridLineColor = "blue";

private double transparentPlaneY = 0;
private String transparentGridColor = "blue";

private double ghostSpawnY = 0.4f;
private double ghostScaleX = 4f;
private double ghostScaleY = 0.2f;
private double ghostScaleZ = 4f;
private String ghostColor = "green";


private double camera1ViewAngle = 90;
private double camera1AspectRatio = (16/9);
private double camera1NearClip = 0.01;
private double camera1FarClip = 1000;
private double camera1Left = 0;
private double camera1Right = 1;
private double camera1Bottom = 0;
private double camera1Top = 1;

private double skyboxScaleX = 40;
private double skyboxScaleY = 40;
private double skyboxScaleZ = 40;
private String skyboxNorthPicture = "skybox/north.jpg";
private String skyboxEastPicture = "skybox/east.jpg";
private String skyboxSouthPicture = "skybox/south.jpg";
private String skyboxWestPicture = "skybox/west.jpg";
private String skyboxUpPicture = "skybox/up.jpg";
private String skyboxDownPicture = "skybox/down.jpg";

private double camera1DistanceFromTarget = 45.0f;
private double camera1Azimuth = 180;
private double camera1Elevation = 65.0f;

private double maxElevation = 80;
private double minElevation = 25;

private double minZoom = 30;
private double maxZoom = 160;

private int scaleForwardBackwardKeyboard = 16;
private int scaleLeftRightKeyboard = 24;
private int scaleOrbitKeyboard = 25;
private int scaleZoom = 50;

private int scaleForwardBackwardGamepad = 16;
private int scaleLeftRightGamepad = 24;
private int scaleOrbitGamepad = 25;

private ArrayList<gameWorldObjects.Character> myTeam;
private ArrayList<gameWorldObjects.NPC> npcs;
private ArrayList<GhostCharacter> ghostTeam;
private ArrayList<SceneNode> buildings;

private boolean characterSelected = false;
private boolean movePossible = false;
private boolean attackPossible = false;
private double moveDistanceX;
private double moveDistanceZ;


private SceneNode gridStarterSphere;
private boolean firstLoop = true;

private Sphere sphere;
private PhysicsBase physicsBase;
private IPhysicsEngine physicsEngine;
private TriMesh arrowTest, arrow3;


private IAudioManager audioMgr;
private AudioResource resource1, resource2;
private Sound npcSound, themeSong;


private Group model;
private Model3DTriMesh myHorse;

public MyGame(String serverAddr, int sPort, boolean playerOne, boolean keyBoard)
{
	super();
	this.serverAddress = serverAddr;
	this.serverPort = sPort;
	this.serverProtocol = ProtocolType.TCP;
	
	this.playerOne = playerOne;
	this.keyBoard = keyBoard;
	
	if(playerOne)
	{
		myTurn = true;
		
		
	}
	else
	{
		myTurn = false;
		

	}
}



public void checkTurn()
{
	attackTurnLeft = myTeam.size();
	moveTurnLeft = myTeam.size();
	
	if(myTurn)
	{
		for(int i = 0; i<myTeam.size(); i++)
		{
			if(myTeam.get(i).attackUsed())
			{
				attackTurnLeft--;
				
			}
			if(myTeam.get(i).moveUsed())
			{
				moveTurnLeft--;
			}
		}
		
		if(attackTurnLeft==0 && moveTurnLeft==0)
		{
			myTurn = false;
			client.sendYourTurn();
		}
		else
		{

		}
		
		
		
	}
	else
	{
		
	}
	
	
/*	
	for(int i = 0; i<ghostTeam.size(); i++)
	{
		System.out.println("Ghost Team Member: " + ghostTeam.get(i).getCharacterID() + "  Class: " + ghostTeam.get(i).getCharacterTypeName() + " Is Alive: " + ghostTeam.get(i).isAlive() + " Move Used: " + ghostTeam.get(i).moveUsed() + " Attack Used: " + ghostTeam.get(i).attackUsed());
	}
	for(int i = 0; i<myTeam.size(); i++)
	{
		System.out.println("Team Member: " + myTeam.get(i).getCharacterID() + "  Class: " + myTeam.get(i).getCharacterTypeName() + " Is Alive: " + myTeam.get(i).isAlive() + " Move Used: " + myTeam.get(i).moveUsed() + " Attack Used: " + myTeam.get(i).attackUsed());
	}
*/	
	
}


public void makeMyTurn()
{
	myTurn = true;
	for(int i =0; i<myTeam.size();i++)
	{
		if(myTeam.get(i).isAlive())
		{
		myTeam.get(i).setMoveUsed(false);
		myTeam.get(i).setAttackUsed(false);
		}
	}
}

protected void initSystem()
{	
	
	this.setUpScript();
	
	this.runScript(scriptFileName);
	this.evalScript1();
	
	this.runScript(scriptFileName2);
	this.evalScript2();
	
	System.out.println("\nScripts Read Successfully.\n");
	
	display = createDisplaySystem();
	renderer = display.getRenderer();
	setDisplaySystem(display);
	
	InputManager inputManager = new InputManager();
	setInputManager(inputManager);
	im = getInputManager();

	ArrayList<SceneNode> gameWorld = new ArrayList<SceneNode>();
	setGameWorld(gameWorld);
	
	eventManager = EventManager.getInstance();
	eventManager.addListener(this, MoveCameraEvent.class);

}

protected void initGame()
{	

	
	initGameObjects();
	
	setUpClient();

	setUpController();
	
	
	cc1.update(0);
	
}


//Send the Bounds of the Map to the Server
public void sendHouses()
{
	for(int i = 0; i<64; i++)
	{
		for(int j = 0; j<64; j++)
		{
		
			if(tileGrid.getTileGrid()[i][j].isCovered())
			{
				client.sendHouseMessage(i,  j);
			}
		}
	}
}


//Initialize Audio
public void initAudio()
{
	 

	
	 
	// AudioResource resource1, resource2;
//	 audioMgr = AudioManagerFactory.createAudioManager(
//	 "sage.audio.joal.JOALAudioManager");
//	 if(!audioMgr.initialize())
//	 { System.out.println("Audio Manager failed to initialize!");
//	 return;
//	 }
//	 resource1 = audioMgr.createAudioResource("horse.wav",
//	 AudioResourceType.AUDIO_SAMPLE);
//	 npcSound = new Sound(resource1, SoundType.SOUND_EFFECT, 100, true);
//
//	 npcSound.initialize(audioMgr);
//	 npcSound.setMaxDistance(50.0f);
//	 npcSound.setMinDistance(3.0f);
//	 npcSound.setRollOff(5.0f);
//	 npcSound.setLocation(this.getCamera().getLocation());
//	 setEarParameters();
//	 npcSound.play();
	 
	 
	 
/*
		 resource2 = audioMgr.createAudioResource("themeSong.wav",
		 AudioResourceType.AUDIO_SAMPLE);Fset
		themeSong = new Sound(resource1, SoundType.SOUND_EFFECT, 100, true);

		 themeSong.initialize(audioMgr);
		 themeSong.setMaxDistance(50.0f);
		 themeSong.setMinDistance(3.0f);
		 themeSong.setRollOff(5.0f);
		 themeSong.setLocation(this.getCamera().getLocation());
		 setEarParameters();
		 themeSong.play();
	*/ 
	 

	
}



public void triggerEvent(String s, int x, int z)
{
	if(s=="MoveCameraEvent")
	{
		MoveCameraEvent moveCameraEvent = new MoveCameraEvent(x,z);
		eventManager.triggerEvent(moveCameraEvent);
	}
}


public void update(float elapsedTimeMS)
{

	//myHorse.updateAnimation(elapsedTimeMS);
	
	
	updateScripts();
	
	checkCameraTileDistance();
	
	if(isCharacterSelected())
	{
	updatePossibleMove();
	}
	else
	{
		updatePossibleSelectCharacter();
	}

		
	if(firstLoop)
	{
	
		makeGridBlocked(elapsedTimeMS);
		


	}
	else
	{
		super.update(elapsedTimeMS);
	}
	
	
	client.processPackets();
	
	updateDisplay(elapsedTimeMS);
	
	cc1.update(elapsedTimeMS);
	
//	 npcSound.setLocation(new Point3D(npcs.get(0).getWorldTranslation().getCol(3)));
//	 setEarParameters();
//	 updateSoundDistances(elapsedTimeMS);
	
//	physicsBase.update(elapsedTimeMS);
	

	
}


//Determine which areas of the map are allowed to be used.
public void makeGridBlocked(float elapsedTimeMS)
{

	 for(int i = 0; i<64; i++)
		{
	
			for(int j = 0; j<64; j++)
			{
				
				
				Matrix3D move = new Matrix3D();
				move.translate(tileGrid.getTileGrid()[i][j].getX(), 3, tileGrid.getTileGrid()[i][j].getZ());
				gridStarterSphere.setLocalTranslation(move);
				super.update(elapsedTimeMS);
				
				for(int k =0; k<buildings.size(); k++)
				{
				
	
					
					if((gridStarterSphere.getWorldBound().intersects(buildings.get(k).getWorldBound())))
					{
				
						
					
						tileGrid.getTileGrid()[i][j].cover("X");
					}
					
				}
			}
		}
	 
	 /*
	 	tileGrid.getTileGrid()[51][12].cover("X");
	 	tileGrid.getTileGrid()[51][13].cover("X");
	 	tileGrid.getTileGrid()[52][12].cover("X");
	 	tileGrid.getTileGrid()[52][13].cover("X");
	 	
	 	tileGrid.getTileGrid()[53][10].cover("X");
	 	tileGrid.getTileGrid()[53][11].cover("X");
	 	tileGrid.getTileGrid()[53][12].cover("X");
	 	tileGrid.getTileGrid()[53][13].cover("X");
	 	
		tileGrid.getTileGrid()[54][10].cover("X");
	 	tileGrid.getTileGrid()[54][11].cover("X");
	 	tileGrid.getTileGrid()[54][12].cover("X");
	 	tileGrid.getTileGrid()[54][13].cover("X");
	 	
		tileGrid.getTileGrid()[53][0].cover("X");
	 	tileGrid.getTileGrid()[53][1].cover("X");
	 	tileGrid.getTileGrid()[54][0].cover("X");
	 	tileGrid.getTileGrid()[54][1].cover("X");
	 	
	 	
		tileGrid.getTileGrid()[28][12].cover("X");
	 	tileGrid.getTileGrid()[28][13].cover("X");
	 	tileGrid.getTileGrid()[27][12].cover("X");
	 	tileGrid.getTileGrid()[27][13].cover("X");
	 	
	 	tileGrid.getTileGrid()[26][10].cover("X");
	 	tileGrid.getTileGrid()[26][11].cover("X");
	 	tileGrid.getTileGrid()[26][12].cover("X");
	 	tileGrid.getTileGrid()[26][13].cover("X");
	 	
		tileGrid.getTileGrid()[25][10].cover("X");
	 	tileGrid.getTileGrid()[25][11].cover("X");
	 	tileGrid.getTileGrid()[25][12].cover("X");
	 	tileGrid.getTileGrid()[25][13].cover("X");
	 	
	 	tileGrid.getTileGrid()[25][0].cover("X");
	 	tileGrid.getTileGrid()[25][1].cover("X");
	 	tileGrid.getTileGrid()[26][0].cover("X");
	 	tileGrid.getTileGrid()[26][1].cover("X");
	 */	
	 	tileGrid.getTileGrid()[0][20].cover("X");
	 	tileGrid.getTileGrid()[1][20].cover("X");
	 	tileGrid.getTileGrid()[2][20].cover("X");
	 	tileGrid.getTileGrid()[0][21].cover("X");
	 	tileGrid.getTileGrid()[1][21].cover("X");
	 	tileGrid.getTileGrid()[2][21].cover("X");
	 	
	 	tileGrid.getTileGrid()[13][20].cover("X");
	 	tileGrid.getTileGrid()[14][20].cover("X");
	 	tileGrid.getTileGrid()[15][20].cover("X");
	 	tileGrid.getTileGrid()[16][20].cover("X");
	 	tileGrid.getTileGrid()[13][21].cover("X");
	 	tileGrid.getTileGrid()[14][21].cover("X");
	 	tileGrid.getTileGrid()[15][21].cover("X");
	 	tileGrid.getTileGrid()[16][21].cover("X");
	 	tileGrid.getTileGrid()[15][22].cover("X");
	 	tileGrid.getTileGrid()[16][22].cover("X");
	 	
	 	
		tileGrid.getTileGrid()[0][42].cover("X");
	 	tileGrid.getTileGrid()[1][42].cover("X");
	 	tileGrid.getTileGrid()[2][42].cover("X");
	 	tileGrid.getTileGrid()[0][43].cover("X");
	 	tileGrid.getTileGrid()[1][43].cover("X");
	 	tileGrid.getTileGrid()[2][43].cover("X");
	 	
	 	tileGrid.getTileGrid()[13][42].cover("X");
	 	tileGrid.getTileGrid()[14][42].cover("X");
	 	tileGrid.getTileGrid()[15][42].cover("X");
	 	tileGrid.getTileGrid()[16][42].cover("X");
	 	tileGrid.getTileGrid()[13][43].cover("X");
	 	tileGrid.getTileGrid()[14][43].cover("X");
	 	tileGrid.getTileGrid()[15][43].cover("X");
	 	tileGrid.getTileGrid()[16][43].cover("X");
	 	tileGrid.getTileGrid()[15][41].cover("X");
	 	tileGrid.getTileGrid()[16][41].cover("X");
	 	
	 
	 
	 
		firstLoop = false;
		
		
		removeGameWorldObject(gridStarterSphere);
		
		
		if(playerOne)
		{
			
		
		sendHouses();
		}
		else
		{
			
		}
}




public void updateSoundDistances(float elapsedTimeMS)
{
	//themeSong.setLocation(getCamera().getLocation());
	
	npcSound.setLocation(new Point3D(npcs.get(0).getWorldTranslation().getCol(3)));
//	System.out.println("NPC Sound: " + npcSound.getLocation());
	
//	setEarParameters();

	//npcSound.update(elapsedTimeMS);
	
	Point3D ear =  audioMgr.getEar().getLocation();
	Point3D npc = npcSound.getLocation();
	
	double distanceX = ear.getX() - npc.getX();
	double distanceZ = ear.getZ() - npc.getZ();
	
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
	
	npcSound.setVolume((int)(100-(totalDistance*4)));

}





public void updatePossibleMove()
{
	Vector3D avatarPosition = cursorObject.getLocalTranslation().getCol(3);
	double avatarX = avatarPosition.getX();
	double avatarZ = avatarPosition.getZ();

	gameWorldObjects.Character selectedCharacter = myTeam.get(0);
	double selectedCharacterX = 0;
	double selectedCharacterZ = 0;
	double selectedCharacterMaxDistance = 0;
	double selectedCharacterMaxAttack = 0;
		
	while(true)
	{
			for(int i = 0; i<myTeam.size(); i++)
			{
				if(((Character) myTeam.get(i)).isSelected())
				{
					selectedCharacter = (gameWorldObjects.Character)myTeam.get(i);
					selectedCharacterX = selectedCharacter.getLocalTranslation().getCol(3).getX();
					selectedCharacterZ = selectedCharacter.getLocalTranslation().getCol(3).getZ();
					selectedCharacterMaxDistance = selectedCharacter.getMaxMove();
					selectedCharacterMaxAttack = selectedCharacter.getMaxAttack();

					break;
				}
			}
			break;
	}
		
	double currentDistanceX = avatarX - selectedCharacterX;
	double currentDistanceZ = avatarZ - selectedCharacterZ;
	if(currentDistanceX < 0)
	{
		currentDistanceX = currentDistanceX*-1;
	}
	if(currentDistanceZ < 0)
	{
		currentDistanceZ = currentDistanceZ*-1;
	}
	
	moveDistanceX = currentDistanceX;
	moveDistanceZ = currentDistanceZ;
	
	double currentTotalDistance = currentDistanceX + currentDistanceZ;
	
	int selectedCharacterGridI = 0;
	int selectedCharacterGridJ = 0;
	
	while(true)
	{
		for(int i = 0; i<myTeam.size(); i++)
		{
			
		if(myTeam.get(i).isSelected())
		{
			selectedCharacterGridI = myTeam.get(i).getGridI();
			selectedCharacterGridJ = myTeam.get(i).getGridJ();
			break;
		}
		
		}
		break;
	}
	
	
	boolean ghostSpot = false;
	while(true)
	{
	for(int i = 0; i<ghostTeam.size(); i++)
	{
		if(cursorObject.getGridI()==ghostTeam.get(i).getGridI() && cursorObject.getGridJ()==ghostTeam.get(i).getGridJ())
		{
			ghostSpot = true;
			break;
		}
	}
	break;
	}
	
	boolean npcSpot = false;
	while(true)
	{
	for(int i = 0; i<npcs.size(); i++)
	{
		if(cursorObject.getGridI()==npcs.get(i).getGridI() && cursorObject.getGridJ()==npcs.get(i).getGridJ())
		{
			npcSpot = true;
			break;
		}
	}
	break;
	}
	
	
	if(cursorObject.getGridI()==selectedCharacterGridI && cursorObject.getGridJ()==selectedCharacterGridJ)
	{
		cursorObject.setColorBuffer("white2");
		cursorObject.setTexture(cursorTextureGreen);
		hoveringCursorObject.setTexture(hoveringCursorPointer);
		
		
		setMovePossible(true);
		setAttackPossible(false);
	}
	else if(ghostSpot)
	{
		
			if(currentTotalDistance<= selectedCharacterMaxAttack)
			{
		
				cursorObject.setColorBuffer("red");
				cursorObject.setTexture(cursorTextureAttack);
				hoveringCursorObject.setTexture(hoveringCursorAttack);
				setAttackPossible(true);
				
				
			}
		
			else
			{
			cursorObject.setColorBuffer("red");
			cursorObject.setTexture(cursorTextureX);
			hoveringCursorObject.setTexture(hoveringCursorEnemy);
			setAttackPossible(false);
			}
			
			
		setMovePossible(false);
	}
	else if(npcSpot)
	{
		if(currentTotalDistance<= selectedCharacterMaxAttack)
		{
			
			cursorObject.setColorBuffer("red");
			cursorObject.setTexture(cursorTextureAttack);
			hoveringCursorObject.setTexture(hoveringCursorAttack);
			setAttackPossible(true);
			
			
		}
		else
		{
			cursorObject.setColorBuffer("red");
			cursorObject.setTexture(cursorTextureX);
			hoveringCursorObject.setTexture(hoveringCursorEnemy);
			setAttackPossible(false);
		}
		
		
		setMovePossible(false);
	}
	
	else if(tileGrid.getTileGrid()[cursorObject.getGridI()][cursorObject.getGridJ()].isOccupied())
	{
		
		if(currentTotalDistance <= selectedCharacterMaxDistance)
		{
			cursorObject.setColorBuffer("white2");
			cursorObject.setTexture(cursorTextureX);
			hoveringCursorObject.setTexture(hoveringCursorTeam);
		}
		else
		{
			cursorObject.setColorBuffer("white");
			cursorObject.setTexture(cursorTextureX);
			hoveringCursorObject.setTexture(hoveringCursorTeam);
		}
		
		
		setMovePossible(false);
		setAttackPossible(false);
	}
	else if(currentTotalDistance <= selectedCharacterMaxDistance)
	{
		if(selectedCharacter.moveUsed()==false)
		{
		cursorObject.setColorBuffer("white2");
		cursorObject.setTexture(cursorTextureMove);
		hoveringCursorObject.setTexture(hoveringCursorMove);
		
		setMovePossible(true);
		setAttackPossible(false);
		}
		else
		{
			cursorObject.setColorBuffer("white2");
			cursorObject.setTexture(cursorTextureX);
			hoveringCursorObject.setTexture(hoveringCursorPointer);
			
			setMovePossible(false);
			setAttackPossible(false);
		}
	}
	
	else
	{
		cursorObject.setColorBuffer("white");
		cursorObject.setTexture(cursorTextureX);
		hoveringCursorObject.setTexture(hoveringCursorPointer);
		
		setMovePossible(false);
		setAttackPossible(false);
	}
	
}


public void updatePossibleSelectCharacter()
{
	Vector3D avatarPosition = cursorObject.getLocalTranslation().getCol(3);
	double avatarX = avatarPosition.getX();
	double avatarZ = avatarPosition.getZ();
	boolean possibleSelected = false;
	


	while(true)
	{
		
		for(int i = 0; i<myTeam.size(); i++)
		{
		
			if(avatarX > (myTeam.get(i).getLocalTranslation().getCol(3).getX() - 5) && avatarX < (myTeam.get(i).getLocalTranslation().getCol(3).getX() + 5))
			{
				if(avatarZ > (myTeam.get(i).getLocalTranslation().getCol(3).getZ() - 5) && avatarZ < (myTeam.get(i).getLocalTranslation().getCol(3).getZ() + 5))
				{
					
					if(myTurn)
					{
						if(myTeam.get(i).moveUsed()==false)
						{
							if(myTeam.get(i).isKing())
							{
								cursorObject.setColorBuffer("white");
								cursorObject.setTexture(cursorTextureKing);
								hoveringCursorObject.setTexture(hoveringCursorKing);
							}
							else
							{
							cursorObject.setColorBuffer("white");
							cursorObject.setTexture(cursorTextureYellow);
							hoveringCursorObject.setTexture(hoveringCursorPointer);
							}
						}
						else if(myTeam.get(i).attackUsed()==false)
						{
							if(myTeam.get(i).isKing())
							{
								cursorObject.setColorBuffer("white");
								cursorObject.setTexture(cursorTextureKing);
								hoveringCursorObject.setTexture(hoveringCursorKing);
							}
							else
							{
							cursorObject.setColorBuffer("white");
							cursorObject.setTexture(cursorTextureYellow);
							hoveringCursorObject.setTexture(hoveringCursorPointer);
							}
						}
						else
						{
							if(myTeam.get(i).isKing())
							{
								cursorObject.setColorBuffer("white");
								cursorObject.setTexture(cursorTextureClear);
								hoveringCursorObject.setTexture(hoveringCursorKing);
							}
							else
							{
							cursorObject.setColorBuffer("white");
							cursorObject.setTexture(cursorTextureClear);
							hoveringCursorObject.setTexture(hoveringCursorTeam);
							}
						}
					
					}
					else
					{
						if(myTeam.get(i).isKing())
						{
							cursorObject.setColorBuffer("white");
							cursorObject.setTexture(cursorTextureClear);
							hoveringCursorObject.setTexture(hoveringCursorKing);
						}
						else
						{
						cursorObject.setColorBuffer("white");
						cursorObject.setTexture(cursorTextureClear);
						hoveringCursorObject.setTexture(hoveringCursorTeam);
						}
					}
					
					showGroundPlane = true;
					

					
					for(int r = 0; r<myTeam.size(); r++)
					{
				
						myTeam.get(r).setShowLifeBar(true);
						myTeam.get(r).updateLifeBar();
					}
					for(int s = 0; s<ghostTeam.size(); s++)
					{
				
					ghostTeam.get(s).setShowLifeBar(true);
					ghostTeam.get(s).updateLifeBar();
					}
					for(int t = 0; t<npcs.size(); t++)
					{
			
					npcs.get(t).setShowLifeBar(true);
					npcs.get(t).updateLifeBar();
					}
					
					
					
					
					updateTransparentGroundPlane(myTeam.get(i).getLocalTranslation().getCol(3).getX(), myTeam.get(i).getLocalTranslation().getCol(3).getZ(), 1, myTeam.get(i).getMaxMove());

			
					
				
					
					possibleSelected = true;
					
					
					
				
					
					break;
				}
			}
		}
		break;
	}
	
	

	if(possibleSelected)
	{
	}
	else
	{
		boolean foundOne = false;
		
		
		
		while(true)
		{
				
			
			for(int g = 0; g<ghostTeam.size(); g++)
				{
					if(avatarX > (ghostTeam.get(g).getLocalTranslation().getCol(3).getX() - 5) && avatarX < (ghostTeam.get(g).getLocalTranslation().getCol(3).getX() + 5))
					{
						if(avatarZ > (ghostTeam.get(g).getLocalTranslation().getCol(3).getZ() - 5) && avatarZ < (ghostTeam.get(g).getLocalTranslation().getCol(3).getZ() + 5))
						{
							
							
							String s = ghostTeam.get(g).getCharacterTypeName();
							
							if(s.matches("K"))
							{
								
								cursorObject.setColorBuffer("red");
								cursorObject.setTexture(cursorTextureKing);
								hoveringCursorObject.setTexture(hoveringCursorKing);
								showGroundPlane = true;
							}
							else
							{
								
								
							cursorObject.setColorBuffer("red");
							cursorObject.setTexture(cursorTextureEnemy);
							hoveringCursorObject.setTexture(hoveringCursorEnemy);
							showGroundPlane = true;
							}
	
							
							for(int bro = 0; bro<myTeam.size(); bro++)
							{
						
								myTeam.get(bro).setShowLifeBar(true);
								myTeam.get(bro).updateLifeBar();
							}
							for(int bro2 = 0; bro2<ghostTeam.size(); bro2++)
							{
						
							ghostTeam.get(bro2).setShowLifeBar(true);
							ghostTeam.get(bro2).updateLifeBar();
							}
							for(int bro3 = 0; bro3<npcs.size(); bro3++)
							{
						
							npcs.get(bro3).setShowLifeBar(true);
							npcs.get(bro3).updateLifeBar();
							}
							
							updateTransparentGroundPlane(ghostTeam.get(g).getLocalTranslation().getCol(3).getX(), ghostTeam.get(g).getLocalTranslation().getCol(3).getZ(), 2, ghostTeam.get(g).getMaxMove());
							foundOne = true;
							
							
							
							break;
						}
					}	
				}
				break;
			}
		
		
		
	if(foundOne)
	{
		
	}
	else
		{
		
			while(true)
			{
					
				for(int n = 0; n<npcs.size(); n++)
					{
						if(avatarX > (npcs.get(n).getLocalTranslation().getCol(3).getX() - 5) && avatarX < (npcs.get(n).getLocalTranslation().getCol(3).getX() + 5))
						{
							if(avatarZ > (npcs.get(n).getLocalTranslation().getCol(3).getZ() - 5) && avatarZ < (npcs.get(n).getLocalTranslation().getCol(3).getZ() + 5))
							{
								
								cursorObject.setColorBuffer("red");
								cursorObject.setTexture(cursorTextureEnemy);
								hoveringCursorObject.setTexture(hoveringCursorEnemy);
			
								showGroundPlane = true;
		
								for(int sis = 0; sis<myTeam.size(); sis++)
								{
				
									myTeam.get(sis).setShowLifeBar(true);
									myTeam.get(sis).updateLifeBar();
								}
								for(int sis2 = 0; sis2<ghostTeam.size(); sis2++)
								{
	
								ghostTeam.get(sis2).setShowLifeBar(true);
								ghostTeam.get(sis2).updateLifeBar();
								}
								for(int sis3 = 0; sis3<npcs.size(); sis3++)
								{
				
								npcs.get(sis3).setShowLifeBar(true);
								npcs.get(sis3).updateLifeBar();
								}
								
								
								
								
								updateTransparentGroundPlane(npcs.get(n).getLocalTranslation().getCol(3).getX(), npcs.get(n).getLocalTranslation().getCol(3).getZ(), 3,npcs.get(n).getMaxMove());
								foundOne = true;
								
							
								
								
								break;
							}
						}	
					}
					break;
				}
		}
	
	if(foundOne)
		{
		}
	else
	{
		
			
		
			cursorObject.setColorBuffer("white");
			cursorObject.setTexture(cursorTextureClear);
			hoveringCursorObject.setTexture(hoveringCursorPointer);
			
			showGroundPlane = false;
			updateTransparentGroundPlane(0,0, 1, 56);
			

			for(int r = 0; r<myTeam.size(); r++)
			{
				
				myTeam.get(r).setShowLifeBar(false);
				myTeam.get(r).updateLifeBar();
			}
			for(int s = 0; s<ghostTeam.size(); s++)
			{
		
			ghostTeam.get(s).setShowLifeBar(false);
			ghostTeam.get(s).updateLifeBar();
			}
			for(int t = 0; t<npcs.size(); t++)
			{
		
			npcs.get(t).setShowLifeBar(false);
			npcs.get(t).updateLifeBar();
			}
			
			
		}
			
	}	


	
}

public void updateScripts()
{
	
	if(dynamicScriptOn)
	{
		long modTime = dynamicScript.lastModified();
		
		if(modTime>fileLastModifiedTime)
		{
			fileLastModifiedTime = modTime;
			this.runScript(scriptFileName2);
			this.evalScript2();
			
			display.setHeight(frameHeight);
			display.setWidth(frameWidth);
			
			camera1.setPerspectiveFrustum(camera1ViewAngle,camera1AspectRatio,camera1NearClip,camera1FarClip);
			camera1.setViewport(camera1Left, camera1Right, camera1Bottom, camera1Top);
			
		/*
			updateTileY();
							
			if(isCharacterSelected())
			{
				gridY = gridYSelect;
				
				
			}
			else
			{
				gridY = gridYNoSelect;
				
			}
			
			updateGridY();			

			
			
			
			Matrix3D cursorObj = new Matrix3D();
			cursorObj.translate(cursorObjectX, cursorObjectY, cursorObjectZ);
			Matrix3D cursorObjScale = new Matrix3D();
			cursorObjScale.scale(cursorObjectScaleX,  cursorObjectScaleY,  cursorObjectScaleZ);
			cursorObject.setLocalTranslation(cursorObj);
			cursorObject.setLocalScale(cursorObjScale);
			
			
			
			
			Matrix3D houseScale = new Matrix3D();
			houseScale.translate(houseScaleX, houseScaleY,  houseScaleZ);
			arrowTest.setLocalTranslation(houseScale);
			*/
		}
	}
	
}

public void updateGridY()
{
	
	for(int i = 0; i<grid.size();i++)
	{
		Matrix3D gridMat = new Matrix3D();
		gridMat.translate(grid.get(i).getLocalTranslation().getCol(3).getX(), gridY, grid.get(i).getLocalTranslation().getCol(3).getZ());
		grid.get(i).setLocalTranslation(gridMat);
	}
}


public void updateTransparentGroundPlane(double x, double z, int teamType, double maxDistance)
{	
	int normalizeDistance = (int)maxDistance/10;
	
	if(teamType == 1)
	{
		for(int size = 0; size<groundPlaneBlue.size(); size++)
		{
			groundPlaneBlue.get(size).setColorBuffer("blue2");
		}
	}
	else if(teamType == 2)
	{
		for(int size = 0; size<groundPlaneBlue.size(); size++)
		{
			groundPlaneBlue.get(size).setColorBuffer("red2");
		}
	}
	else
	{
		for(int size = 0; size<groundPlaneBlue.size(); size++)
		{
			groundPlaneBlue.get(size).setColorBuffer("orange2");
		}
	}
		
	if(showGroundPlane)
	{
		
		 	Matrix3D gridMat1 = new Matrix3D();	
						
			for(int ii = 0; ii<7; ii++)
			{
				gridMat1 = new Matrix3D();
				gridMat1.translate(x, transparentGroundBlueNoShow, z+(ii*10));
				
				
				groundPlaneBlue.get(ii).setLocalTranslation(gridMat1);
			}
			for(int kk = 1; kk<(7); kk++)
			{
				gridMat1 = new Matrix3D();
				gridMat1.translate(x, transparentGroundBlueNoShow, z-(kk*10));
				
				
				groundPlaneBlue.get(kk+6).setLocalTranslation(gridMat1);
			}
		
		int offset = 6 - normalizeDistance;
		
	
		Matrix3D gridMat = new Matrix3D();	
		
		
		for(int i = 0; i<(7-offset); i++)
		{
			gridMat = new Matrix3D();
			gridMat.translate(x, transparentGroundBlueShow, z+(i*10));
			
			
			groundPlaneBlue.get(offset+i).setLocalTranslation(gridMat);
		}
		for(int k = 1; k<(7-offset); k++)
		{
			gridMat = new Matrix3D();
			gridMat.translate(x, transparentGroundBlueShow, z-(k*10));
			
			
			groundPlaneBlue.get(offset+k+6).setLocalTranslation(gridMat);
		}
		
	
	}
	
	else
	{
		
			Matrix3D gridMat = new Matrix3D();		
		
			
			for(int i = 0; i<7; i++)
			{
				gridMat = new Matrix3D();
				gridMat.translate(x, transparentGroundBlueNoShow, z+(i*10));
				
				
				groundPlaneBlue.get(i).setLocalTranslation(gridMat);
			}
			for(int k = 1; k<(7); k++)
			{
				gridMat = new Matrix3D();
				gridMat.translate(x, transparentGroundBlueNoShow, z-(k*10));
				
				
				groundPlaneBlue.get(k+6).setLocalTranslation(gridMat);
			}
			
		
		}

	}
	
	

public void updateTileY()
{
	for(int i = 0; i<groundPlaneTiles.size();i++)
	{
		Matrix3D gridMat = new Matrix3D();
		gridMat.translate(groundPlaneTiles.get(i).getLocalTranslation().getCol(3).getX(), mapTileY, groundPlaneTiles.get(i).getLocalTranslation().getCol(3).getZ());
		groundPlaneTiles.get(i).setLocalTranslation(gridMat);
	}
}

public void updateDisplay(float elapsedTimeMS)
{	
	time += elapsedTimeMS;
	DecimalFormat df = new DecimalFormat("0.0");
	timeString.setText("Time = " + df.format(time/1000));



	if(myTurn)
	{
		int alive = 0;
		int move = 0;
		int attack = 0;
		
		
		for(int i = 0; i<myTeam.size(); i++)
		{
			
		
			if(myTeam.get(i).moveUsed())
			{
			}
			else
			{
				move++;
			}
			
			if(myTeam.get(i).attackUsed())
			{
			}
			else
			{
				attack++;
			}
			
			
			
		}

		
		attackLeftHUD.setText("Attacks Left: " + attack);
		
		moveLeftHUD.setText("Moves Left: " + move);

		
	
		camera1.removeFromHUD(enemyTurnHUD);
		camera1.addToHUD(myTurnHUD);
		camera1.addToHUD(attackLeftHUD);
		camera1.addToHUD(moveLeftHUD);
	}
	else
	{
		
		
		attackLeftHUD.setText("");
		
		moveLeftHUD.setText("");
		
		camera1.removeFromHUD(myTurnHUD);
		camera1.addToHUD(enemyTurnHUD);
	}
	
	if(playerOne)
	{
		int alive = 0;

		for(int i = 0; i<myTeam.size(); i++)
		{
			if(myTeam.get(i).isAlive())
			{
				alive++;
			}		
		}
		teamMembersAliveHUD.setText("Team Members Alive: " + alive);
	}
	else
	{
		int kingLife = 0;
		
		while(true)
		{
			
			for(int i = 0; i<myTeam.size(); i++)
			{
				if(myTeam.get(i).getCharacterTypeName().matches("K"))
				{
					kingLife = (int)myTeam.get(i).getLife();
					break;
				}
				
			}
			break;
		}
		
		kingLifeHUD.setText("King's Life Remaining: " + kingLife);
	}
	
	
	
	
	if(gameOver)
	{
		camera1.removeFromHUD(myTurnHUD);
		camera1.removeFromHUD(enemyTurnHUD);
		teamMembersAliveHUD.setText("");
		attackLeftHUD.setText("");
		moveLeftHUD.setText("");
		
		
		if(winner)
		{
			camera1.addToHUD(youWonHUD);
		}
		else if(loser)
		{
			camera1.addToHUD(youLostHUD);
		}
	}
	
	
}

public void updateSkyboxes()
{
	Matrix3D cameraLocation = new Matrix3D();
	cameraLocation.translate(cc1.getDesiredLocation().getX(), cc1.getDesiredLocation().getY(), cc1.getDesiredLocation().getZ());
	skybox1.setLocalTranslation(cameraLocation);
}

public void updateGhostCharacter(UUID pID, int cID, int gI, int gJ)
{
	while(true)
	{
		
	for(int i = 0; i<ghostTeam.size(); i++)
	{
		boolean thisPlayer = ghostTeam.get(i).getPlayerID().equals(pID);
		int thisCharacter = ghostTeam.get(i).getCharacterID();
		
		if(thisPlayer)
		{
			if(thisCharacter==cID)
			{
				int gridI = ghostTeam.get(i).getGridI();
				int gridJ = ghostTeam.get(i).getGridJ();
				
				tileGrid.getTileGrid()[gridI][gridJ].unOccupy();
				
				int z = gridI - gI;
				int x = gridJ - gJ;
				
				tileGrid.getTileGrid()[gI][gJ].occupy(ghostTeam.get(i).getCharacterTypeName());
			//	tileGrid.getTileGrid()[gI][gJ].occupy("G");

				ghostTeam.get(i).setGridIJ(gI,  gJ);
				ghostTeam.get(i).setLocation(getGrid().getTileGrid()[gI][gJ].getX(),ghostTeam.get(i).getY(), getGrid().getTileGrid()[gI][gJ].getZ());

				ghostTeam.get(i).moveGhost(x, z);
				break;
			}
				
		}
		
	
	}
	break;
	}
		
	
}

public void updateNPC(UUID pID, int cID, int gI, int gJ)
{
	while(true)
	{
		
	for(int i = 0; i<npcs.size(); i++)
	{
		boolean thisPlayer = npcs.get(i).getPlayerID().equals(pID);
		int thisCharacter = npcs.get(i).getCharacterID();
		
		if(thisPlayer)
		{
			if(thisCharacter==cID)
			{
				if(npcs.get(i).isAlive())
				{
				int gridI = npcs.get(i).getGridI();
				int gridJ = npcs.get(i).getGridJ();
				
				tileGrid.getTileGrid()[gridI][gridJ].unOccupy();
				
				int z = gridI - gI;
				int x = gridJ - gJ;
				
			//	tileGrid.getTileGrid()[gI][gJ].occupy(ghostTeam.get(i).getCharacterTypeName());
				tileGrid.getTileGrid()[gI][gJ].occupy(npcs.get(i).getCharacterTypeName());

				npcs.get(i).setGridIJ(gI,  gJ);
				npcs.get(i).setLocation(getGrid().getTileGrid()[gI][gJ].getX(),npcs.get(i).getY(), getGrid().getTileGrid()[gI][gJ].getZ());

				npcs.get(i).moveGhost(x, z);
				}
			
				break;
			}
				
		}
	}
	break;
	}
	
}

public void removeGameWorldObject(Object o)
{
	this.removeGameWorldObject(o);
}

public void ghostAttack(int characterID, int attack)
{
	

	
	while(true)
	{
		for(int i = 0; i<myTeam.size(); i++)
		{
			
			
			int thisCharacter = myTeam.get(i).getCharacterID();
			
			if(characterID==thisCharacter)
			{
			
				myTeam.get(i).takeHit(attack);
		
				break;
			}
			
		}
		break;
	}
	

}

public void updateGhostAttack(int characterID, int attack)
{
	while(true)
	{
		for(int i = 0; i<ghostTeam.size(); i++)
		{
			int thisCharacter = ghostTeam.get(i).getCharacterID();
			
			
			if(characterID==thisCharacter)
			{
				
				
				ghostTeam.get(i).setAttack(attack);
				
			

							
				break;
			}
		}
		break;
	}
	
	
}





public void npcAttack(int characterID, int attack)
{
	while(true)
	{
		for(int i = 0; i<npcs.size(); i++)
		{
			if(npcs.get(i).getCharacterID()==characterID)
			{
				npcs.get(i).takeHit(attack);
				break;
			}
		}
		break;
	}
}





public boolean handleEvent(IGameEvent event)
{
	if(event.toString()=="MoveCameraEvent")
	{
		MoveCameraEvent moveCamera = (MoveCameraEvent)event;
		int x = moveCamera.getDirectionX();
		int z = moveCamera.getDirectionZ();
		moveCameraController.makeMove(x, z);
	}
	return true;
}

protected void render()
{
	renderer.setCamera(camera1);
	super.render();
}

private MyDisplaySystem createDisplaySystem()
{
	myDisplay = new MyDisplaySystem(frameWidth, frameHeight, 32, 50, isFullScreenMode, "sage.renderer.jogl.JOGLRenderer", this, client);
	int count = 0;
	
	while(!myDisplay.isCreated())
	{
		try
		{
			Thread.sleep(10);
		}
		catch (InterruptedException e)
		{
			throw new RuntimeException("Display creation interrupted");
		}
		count++;
	
		if(count%80==0)
		{
	
		}if(count>2000)
		{
			throw new RuntimeException("Unable to create display");
		}
	}

	
	return myDisplay;
	
}

public void setUpScript()
{
	ScriptEngineManager factory = new ScriptEngineManager();	
	jsEngine = factory.getEngineByName("js");
	jsEngine.put("game",this);
}

private void runScript(String name)	
{		
	try {
		fileReader = new FileReader(name);
		jsEngine.eval(fileReader);
		fileReader.close();
		
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	catch(IOException e2)
	{
		System.out.println("IO problem with " + scriptFileName + e2);
	}
	catch(ScriptException e3)
	{
		System.out.println("ScriptException in " + scriptFileName + e3);
	}
	catch(NullPointerException e4)
	{
		System.out.println("Null pointer exception in " + scriptFileName + e4);
	}

	
}

public void evalScript1()
{
	
	dynamicScriptOn = (boolean)jsEngine.get("dynamicScriptOn");
	
	isFullScreenMode = (boolean)jsEngine.get("isFullScreenMode");
	
	camera1Left = (double)jsEngine.get("camera1Left");
	camera1Right = (double)jsEngine.get("camera1Right");
	camera1Bottom = (double)jsEngine.get("camera1Bottom");
	camera1Top = (double)jsEngine.get("camera1Top");
		
	skyboxScaleX = (double)jsEngine.get("skyboxScaleX");
	skyboxScaleY = (double)jsEngine.get("skyboxScaleY");
	skyboxScaleZ = (double)jsEngine.get("skyboxScaleZ");
	
	skyboxNorthPicture = (String)jsEngine.get("skyboxNorthPicture");
	skyboxEastPicture = (String)jsEngine.get("skyboxEastPicture");
	skyboxSouthPicture = (String)jsEngine.get("skyboxSouthPicture");
	skyboxWestPicture = (String)jsEngine.get("skyboxWestPicture");
	skyboxUpPicture = (String)jsEngine.get("skyboxUpPicture");
	skyboxDownPicture = (String)jsEngine.get("skyboxDownPicture");
	
	camera1DistanceFromTarget = (double)jsEngine.get("camera1DistanceFromTarget");
	camera1Azimuth = (double)jsEngine.get("camera1Azimuth");
	camera1Elevation = (double)jsEngine.get("camera1Elevation");
	
	width = (int)jsEngine.get("width");
	gridWidth = (int)jsEngine.get("gridWidth");
	offset = (int)jsEngine.get("offset");
	
	cameraPointerSpawnX = (double)jsEngine.get("cameraPointerSpawnX");
	cameraPointerSpawnY = (double)jsEngine.get("cameraPointerSpawnY");
	cameraPointerSpawnZ = (double)jsEngine.get("cameraPointerSpawnZ");

	ghostSpawnY = (double)jsEngine.get("ghostSpawnY");
	
	mapTileY = (double)jsEngine.get("mapTileY");

	mapTileScaleX = (double)jsEngine.get("mapTileScaleX");
	mapTileScaleY = (double)jsEngine.get("mapTileScaleY");
	mapTileScaleZ = (double)jsEngine.get("mapTileScaleZ");
	
	cursorObjectX = (double)jsEngine.get("cursorObjectX");
	cursorObjectY = (double)jsEngine.get("cursorObjectY");
	cursorObjectZ = (double)jsEngine.get("cursorObjectZ");
	cursorObjectScaleX = (double)jsEngine.get("cursorObjectScaleX");
	cursorObjectScaleY = (double)jsEngine.get("cursorObjectScaleY");
	cursorObjectScaleZ = (double)jsEngine.get("cursorObjectScaleZ");
}

public void evalScript2()
{

	camera1ViewAngle = (double)jsEngine.get("camera1ViewAngle");
	camera1NearClip = (double)jsEngine.get("camera1NearClip");
	camera1FarClip = (double)jsEngine.get("camera1FarClip");
	
	gridLineColor = (String)jsEngine.get("gridLineColor");
	transparentGridColor = (String)jsEngine.get("transparentGridColor");
	gridYSelect = (double)jsEngine.get("gridYSelect");
	gridYNoSelect = (double)jsEngine.get("gridYNoSelect");

	cameraPointerColor = (String)jsEngine.get("cameraPointerColor");
	cameraPointerScaleX = (double)jsEngine.get("cameraPointerScaleX");
	cameraPointerScaleY = (double)jsEngine.get("cameraPointerScaleY");
	cameraPointerScaleZ = (double)jsEngine.get("cameraPointerScaleZ");
	
	ghostColor = (String)jsEngine.get("ghostColor");
	ghostScaleX = (double)jsEngine.get("ghostScaleX");
	ghostScaleY = (double)jsEngine.get("ghostScaleY");
	ghostScaleZ = (double)jsEngine.get("ghostScaleZ");
	
	scaleForwardBackwardKeyboard = (int)jsEngine.get("scaleForwardBackwardKeyboard");
	scaleLeftRightKeyboard = (int)jsEngine.get("scaleForwardBackwardKeyboard");
	scaleOrbitKeyboard = (int)jsEngine.get("scaleOrbitKeyboard");
	scaleZoom = (int)jsEngine.get("scaleZoom");
	scaleForwardBackwardGamepad = (int)jsEngine.get("scaleForwardBackwardGamepad");
	scaleLeftRightGamepad = (int)jsEngine.get("scaleLeftRightGamepad");
	scaleOrbitGamepad  = (int)jsEngine.get("scaleOrbitGamepad");
	
	maxElevation = (double)jsEngine.get("maxElevation");
	minElevation = (double)jsEngine.get("minElevation");
	minZoom = (double)jsEngine.get("minZoom");
	maxZoom = (double)jsEngine.get("maxZoom");

	

	mapTileY = (double)jsEngine.get("mapTileY");

		
	mapTileScaleX = (double)jsEngine.get("mapTileScaleX");
	mapTileScaleY = (double)jsEngine.get("mapTileScaleY");
	mapTileScaleZ = (double)jsEngine.get("mapTileScaleZ");
	
	cursorObjectX = (double)jsEngine.get("cursorObjectX");
	cursorObjectY = (double)jsEngine.get("cursorObjectY");
	cursorObjectZ = (double)jsEngine.get("cursorObjectZ");
	cursorObjectScaleX = (double)jsEngine.get("cursorObjectScaleX");
	cursorObjectScaleY = (double)jsEngine.get("cursorObjectScaleY");
	cursorObjectScaleZ = (double)jsEngine.get("cursorObjectScaleZ");
	

	transparentGroundBlueShow = (double)jsEngine.get("transparentGroundBlueShow");
	transparentGroundBlueNoShow = (double)jsEngine.get("transparentGroundBlueNoShow");


	houseScaleX = (double)jsEngine.get("houseScaleX");
	houseScaleY = (double)jsEngine.get("houseScaleY");
	houseScaleZ = (double)jsEngine.get("houseScaleZ");
	
	castleX = (double)jsEngine.get("castleX");
	castleY  = (double)jsEngine.get("castleY");
	castleZ = (double)jsEngine.get("castleZ");

}

public void setUpClient()
{
	try
	 { 
	
		client = new Client(InetAddress.getByName(serverAddress),
		serverPort, serverProtocol, this); 
		client.sendJoinMessage();

	 }
	catch (UnknownHostException e) 
	{ 
		e.printStackTrace(); 
	}
	catch (IOException e) 
	{ 
		e.printStackTrace(); 
	}
	
	
	
}

public void setUpController()
{
	
	 //Print connected controllers
	  /*
		for(int i = 0; i<controllers.size();i++)
		{
			if(controllers.get(i) instanceof Controller)
			{
				Controller currentController = controllers.get(i);
				controller = controllers.get(i).toString();
				System.out.println("Controller name: " + currentController.getName() + "  Controller Type: " + currentController.getType());
		
			}
		}
		*/
	
		
		controllers = im.getControllers();
		//Initialize controller that will be used in the game
		cc1 = new Camera3P(this);

		
		//Movement
		MoveForwardAction3P moveForward3P = new MoveForwardAction3P(this);
		MoveLeftAction3P moveLeft3P = new MoveLeftAction3P(this);
		MoveBackwardAction3P moveBackward3P = new MoveBackwardAction3P(this);
		MoveRightAction3P moveRight3P = new MoveRightAction3P(this);
		
		
		//orbit camera
		OrbitUpAction orbitUp = new OrbitUpAction(this);
		OrbitDownAction orbitDown = new OrbitDownAction(this);
		OrbitZoomInAction orbitZoomIn = new OrbitZoomInAction(this);
		OrbitZoomOutAction orbitZoomOut = new OrbitZoomOutAction(this);
		
		//Game Exit
		GameOverAction endGame = new GameOverAction(this);
		
		//Controller Movement and Camera Look
		MoveForwardBackwardAction3P moveForwardBackward3P = new MoveForwardBackwardAction3P(this);
		MoveRightLeftAction3P moveRightLeft3P = new MoveRightLeftAction3P(this);
		MoveRYAxisAction3P moveRYAxis3P = new MoveRYAxisAction3P(this);
		
		MoveAction3P moveCharacter = new MoveAction3P(this);


		SelectCharacter selectCharacter = new SelectCharacter(this);
		DeselectCharacter deselectCharacter = new DeselectCharacter(this);
		
		//physics
		physicsBase = new PhysicsBase(cameraPointer, sphere);
		StartPhysics startPhysics = new StartPhysics(physicsBase);
		
		
		if(keyBoard)
		{
		
		for(int i=0;i<controllers.size(); i++)
		{
			String s = ("" + controllers.get(i).getType());
			if(s.equals("Keyboard"))
			{
				//Map Actions to Keyboard Keys
				im.associateAction(controllers.get(i), net.java.games.input.Component.Identifier.Key.W, moveForward3P, IInputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
				im.associateAction(controllers.get(i), net.java.games.input.Component.Identifier.Key.A, moveLeft3P, IInputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
				im.associateAction(controllers.get(i), net.java.games.input.Component.Identifier.Key.S, moveBackward3P, IInputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
				im.associateAction(controllers.get(i), net.java.games.input.Component.Identifier.Key.D, moveRight3P, IInputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
				im.associateAction(controllers.get(i), net.java.games.input.Component.Identifier.Key.UP, orbitUp, IInputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
				im.associateAction(controllers.get(i), net.java.games.input.Component.Identifier.Key.DOWN, orbitDown, IInputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
				im.associateAction(controllers.get(i), net.java.games.input.Component.Identifier.Key.ESCAPE, endGame, IInputManager.INPUT_ACTION_TYPE.ON_PRESS_ONLY);
				im.associateAction(controllers.get(i), net.java.games.input.Component.Identifier.Key.R, orbitZoomIn, IInputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
				im.associateAction(controllers.get(i), net.java.games.input.Component.Identifier.Key.E, orbitZoomOut, IInputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
				
				im.associateAction(controllers.get(i), net.java.games.input.Component.Identifier.Key.J, selectCharacter, IInputManager.INPUT_ACTION_TYPE.ON_PRESS_ONLY);
				im.associateAction(controllers.get(i), net.java.games.input.Component.Identifier.Key.F, deselectCharacter, IInputManager.INPUT_ACTION_TYPE.ON_PRESS_ONLY);

			//	im.associateAction(controllers.get(i), net.java.games.input.Component.Identifier.Key.SPACE,startPhysics,IInputManager.INPUT_ACTION_TYPE.ON_PRESS_ONLY);

			}
			
		}
		}
		else if(keyBoard==false)
		{
			for(int i=0;i<controllers.size(); i++)
			{
				String s = ("" + controllers.get(i).getType());
			if(s.equals("Gamepad"))
			{
				
				//Map Movement and Camera Look to Analog Sticks of Controller
				im.associateAction(controllers.get(i), net.java.games.input.Component.Identifier.Axis.Y, moveForwardBackward3P, IInputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
				im.associateAction(controllers.get(i), net.java.games.input.Component.Identifier.Axis.X, moveRightLeft3P, IInputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
				im.associateAction(controllers.get(i), net.java.games.input.Component.Identifier.Axis.RY, moveRYAxis3P, IInputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);	
				im.associateAction(controllers.get(i), net.java.games.input.Component.Identifier.Button._4, orbitZoomOut, IInputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);	
				im.associateAction(controllers.get(i), net.java.games.input.Component.Identifier.Button._5, orbitZoomIn, IInputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);	
				im.associateAction(controllers.get(i), net.java.games.input.Component.Identifier.Button._7, endGame, IInputManager.INPUT_ACTION_TYPE.ON_PRESS_ONLY);	
				im.associateAction(controllers.get(i), net.java.games.input.Component.Identifier.Button._0, selectCharacter, IInputManager.INPUT_ACTION_TYPE.ON_PRESS_ONLY);
				im.associateAction(controllers.get(i), net.java.games.input.Component.Identifier.Button._1, deselectCharacter, IInputManager.INPUT_ACTION_TYPE.ON_PRESS_ONLY);
				im.associateAction(controllers.get(i), net.java.games.input.Component.Identifier.Axis.POV, moveCharacter, IInputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);

			}
			if(s.equals("Stick"))
			{
				//Map Movement and Camera Look to Analog Sticks of Controller
				im.associateAction(controllers.get(i), net.java.games.input.Component.Identifier.Axis.Y, moveForwardBackward3P, IInputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
				im.associateAction(controllers.get(i), net.java.games.input.Component.Identifier.Axis.X, moveRightLeft3P, IInputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
				im.associateAction(controllers.get(i), net.java.games.input.Component.Identifier.Axis.RY, moveRYAxis3P, IInputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);	
				im.associateAction(controllers.get(i), net.java.games.input.Component.Identifier.Button._4, orbitZoomOut, IInputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);	
				im.associateAction(controllers.get(i), net.java.games.input.Component.Identifier.Button._5, orbitZoomIn, IInputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);	
				im.associateAction(controllers.get(i), net.java.games.input.Component.Identifier.Button._7, endGame, IInputManager.INPUT_ACTION_TYPE.ON_PRESS_ONLY);	
				im.associateAction(controllers.get(i), net.java.games.input.Component.Identifier.Button._0, selectCharacter, IInputManager.INPUT_ACTION_TYPE.ON_PRESS_ONLY);
				im.associateAction(controllers.get(i), net.java.games.input.Component.Identifier.Button._1, deselectCharacter, IInputManager.INPUT_ACTION_TYPE.ON_PRESS_ONLY);
				im.associateAction(controllers.get(i), net.java.games.input.Component.Identifier.Axis.POV, moveCharacter, IInputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);

			}
		}
		}

	
}

public void initGameObjects()
{

	eventManager = EventManager.getInstance();

	tileGrid = new Grid();

	createSkyboxes();
	createPlayers();
	
	//Create HUDs
	createHUD();
	
	//Create all game objects 
	createGameObjects();
	
	createTerrain();

	super.update((float) 0.0);
	
}

public void createGemMatrix()
{
	Gem gem1 = new Gem();
	Gem gem2 = new Gem();
	Group group1 = new Group(); // solar system
	Group group2 = new Group(); // planet system rotation
	Group group3 = new Group(); // planet system position
	Group group4 = new Group(); // moon system rotation
	group1.addChild(gem1);
	group1.addChild(group2);
	group2.addChild(gem2);

	group1.setIsTransformSpaceParent(true);
	group2.setIsTransformSpaceParent(true);

	gem1.setIsTransformSpaceParent(true);
	gem2.setIsTransformSpaceParent(true);

	
	group1.translate(10, 45, 30);
	group2.translate(-20,0,0);
	addGameWorldObject(group1);
	
}



public void createHUD()
{
	//Update each of the strings on the HUD
	timeString = new HUDString("Time: " + time);
	timeString.setLocation(0.01,.01);
	timeString.setRenderMode(sage.scene.SceneNode.RENDER_MODE.ORTHO);
	timeString.setCullMode(sage.scene.SceneNode.CULL_MODE.NEVER);
	timeString.setColor(Color.white);
	camera1.addToHUD(timeString);
	
	teamMembersAliveString = "4";
	teamMembersAliveHUD = new HUDString("Team Members Still Alive: " + teamMembersAliveString);
	teamMembersAliveHUD.setLocation(0.01,.84);
	teamMembersAliveHUD.setRenderMode(sage.scene.SceneNode.RENDER_MODE.ORTHO);
	teamMembersAliveHUD.setCullMode(sage.scene.SceneNode.CULL_MODE.NEVER);
	teamMembersAliveHUD.scale(.8f, .8f, .8f);
	teamMembersAliveHUD.setColor(Color.white);
	//camera1.addToHUD(teamMembersAliveHUD);
	
	kingLifeHUD = new HUDString("King's Life Remaining: 100");
	kingLifeHUD.setLocation(0.01, .84);
	kingLifeHUD.setRenderMode(sage.scene.SceneNode.RENDER_MODE.ORTHO);
	kingLifeHUD.setCullMode(sage.scene.SceneNode.CULL_MODE.NEVER);
	kingLifeHUD.scale(0.8f, 0.8f, 0.8f);
	kingLifeHUD.setColor(Color.white);
	
	if(playerOne)
	{
		camera1.addToHUD(teamMembersAliveHUD);
	}
	else
	{
		camera1.addToHUD(kingLifeHUD);
	}
	
	
	moveLeftString = "5";
	moveLeftHUD = new HUDString("Moves Left: " + moveLeftString);
	moveLeftHUD.setLocation(0.01,.80);
	moveLeftHUD.setRenderMode(sage.scene.SceneNode.RENDER_MODE.ORTHO);
	moveLeftHUD.setCullMode(sage.scene.SceneNode.CULL_MODE.NEVER);
	moveLeftHUD.scale(.8f, .8f, .8f);
	moveLeftHUD.setColor(Color.white);

	attackLeftString = "5";
	attackLeftHUD = new HUDString("Attacks Left: " + attackLeftString);
	attackLeftHUD.setLocation(0.01,.76);
	attackLeftHUD.setRenderMode(sage.scene.SceneNode.RENDER_MODE.ORTHO);
	attackLeftHUD.setCullMode(sage.scene.SceneNode.CULL_MODE.NEVER);
	attackLeftHUD.scale(.8f, .8f, .8f);
	attackLeftHUD.setColor(Color.white);


	myTurnHUD = new HUDImage("textures/playerTurn.png");
	myTurnHUD.setLocation(-.8, .85);
	myTurnHUD.scale(.3f, .16f, .3f);
	myTurnHUD.setRenderMode(sage.scene.SceneNode.RENDER_MODE.ORTHO);
	myTurnHUD.setCullMode(sage.scene.SceneNode.CULL_MODE.NEVER);
	
	
	enemyTurnHUD = new HUDImage("textures/enemyTurn.png");
	enemyTurnHUD.setLocation(-.8, 0.85);
	enemyTurnHUD.scale(.3f,  .16f,  .3f);
	enemyTurnHUD.setRenderMode(sage.scene.SceneNode.RENDER_MODE.ORTHO);
	enemyTurnHUD.setCullMode(sage.scene.SceneNode.CULL_MODE.NEVER);
	
	
	
	
	
	if(myTurn)
	{

		camera1.addToHUD(myTurnHUD);
		camera1.addToHUD(moveLeftHUD);
		camera1.addToHUD(attackLeftHUD);
	}
	else
	{
		camera1.addToHUD(enemyTurnHUD);
	}

	youWonHUD = new HUDImage("textures/youWon.png");
	youWonHUD.scale(1.92f, 1.92f, 1f);
	youWonHUD.setRenderMode(sage.scene.SceneNode.RENDER_MODE.ORTHO);
	youWonHUD.setCullMode(sage.scene.SceneNode.CULL_MODE.NEVER);
	
	youLostHUD = new HUDImage("textures/youLost.png");
	youLostHUD.scale(1.92f, 1.92f, 1f);
	youLostHUD.setRenderMode(sage.scene.SceneNode.RENDER_MODE.ORTHO);
	youLostHUD.setCullMode(sage.scene.SceneNode.CULL_MODE.NEVER);


}


public void createSkyboxes()
{
	
	Texture northTexture = TextureManager.loadTexture2D(skyboxNorthPicture);
	Texture eastTexture = TextureManager.loadTexture2D(skyboxEastPicture);
	Texture southTexture = TextureManager.loadTexture2D(skyboxSouthPicture);
	Texture westTexture = TextureManager.loadTexture2D(skyboxWestPicture);
	Texture upTexture = TextureManager.loadTexture2D(skyboxUpPicture);
	Texture downTexture = TextureManager.loadTexture2D(skyboxDownPicture);
	
	skybox1 = new SkyBox("SkyBox",20.0f, 20.0f, 20.0f);
	skybox1.scale((float)skyboxScaleX, (float)skyboxScaleY, (float)skyboxScaleZ);
	skybox1.setTexture(SkyBox.Face.North, northTexture);
	skybox1.setTexture(SkyBox.Face.East, eastTexture);
	skybox1.setTexture(SkyBox.Face.South, southTexture);
	skybox1.setTexture(SkyBox.Face.West,  westTexture);
	skybox1.setTexture(SkyBox.Face.Up, upTexture);
	skybox1.setTexture(SkyBox.Face.Down, downTexture);

	addGameWorldObject(skybox1);	


	
}

public void createGameObjects()
{

	
	
	ghostTeam = new ArrayList<GhostCharacter>();
	npcs = new ArrayList<NPC>();
	
	
	
	defaultScaleGroundPlaneBlue = new Matrix3D();
	defaultScaleGroundPlaneBlue.scale(5.0f, 0, 5.0f);


	createGroundPlane();
	createMap();
	createGridStarterSphere();
	createTeam();

	 
	 
//	initPhysicsSystem();
//	createSagePhysicsWorld();
	
	
	
//	 OgreXMLParser loader = new OgreXMLParser();
//	 try
//	 { model = loader.loadModel("Cube.mesh.xml",
//	 "Material.material",
//	 "Cube.skeleton.xml");
//	 model.updateGeometricState(0, true);
//	 java.util.Iterator<SceneNode> modelIterator = model.iterator();
//	 myHorse = (Model3DTriMesh) modelIterator.next();
//	 
//	 FloatBuffer bf = myHorse.getVertexBuffer();
//	 int[] s = myHorse.getVertexBoneIDs();
//	// System.out.println(s);
//	 }
//	 catch (Exception e)
//	 { e.printStackTrace();
//	 }
//	 
//
//	 myHorse.scale(2,2,2);
//	 myHorse.translate(0, 55,20);
//	 Vector3D upY = new Vector3D(0,1,0);
//	 myHorse.rotate(90, upY);
//	 myHorse.updateAnimation(0);
//	 myHorse.startAnimation("Walk");
//	 myHorse.updateAnimation(0);
//	 addGameWorldObject(myHorse);
	
	 
	 createGemMatrix();
	

	

}

public void createGridStarterSphere()
{
	
	matrixStack = new MatrixStack(5);
	
	gridStarterSphere = new Gem();
	BoundingBox c = new BoundingBox();
	c.setYExtent(3);
	c.setXExtent(.2f);
	c.setZExtent(.4f);
	gridStarterSphere.setLocalBound(c);	
	addGameWorldObject(gridStarterSphere);
}


public void createMap()
{

	
	Vector3D up = new Vector3D(0,1,0);
/*	
	arrowTest = new CustomObject("castle").getObj();
	arrowTest.translate(0, -10, -77);
	arrowTest.scale(26.0f, 45.0f, 23.05f);
	Vector3D y = new Vector3D(0,1,0);
	arrowTest.rotate(90, y);
	addGameWorldObject(arrowTest);
*/
	
	arrowTest = new CustomObject("castle").getObj();
	
	arrowTest.translate(0, -10, -77);
	arrowTest.scale(20.8f, 45.0f, 28.5f);
	Vector3D y = new Vector3D(0,1,0);
	//arrowTest.rotate(90, y);
	addGameWorldObject(arrowTest);
	
	
	House house1Obj = new House();
	house1 = house1Obj.getObj();
	house1.translate(290f,0,290f);
	house1.rotate(180, up);
	BoundingBox b = new BoundingBox();
	b.setYExtent(5);
	b.setXExtent(2.5f);
	b.setZExtent(2f);
	house1.setLocalBound(b);

	
	House house2Obj = new House();
	house2 = house2Obj.getObj();
	house2.translate(-290f,0,290f);
	house2.rotate(90, up);
	BoundingBox b2 = new BoundingBox();
	b2.setYExtent(5);
	b2.setXExtent(2.5f);
	b2.setZExtent(2f);
	house2.setLocalBound(b2);
	
	House house3Obj = new House();
	house3 = house3Obj.getObj();
	house3.translate(-160f,0,120f);
	house3.rotate(-90, up);
	BoundingBox b3 = new BoundingBox();
	b3.setYExtent(5);
	b3.setXExtent(2.5f);
	b3.setZExtent(2f);
	house3.setLocalBound(b3);
	
	
	House house4obj = new House();
	house4 = house4obj.getObj();
	house4.translate(-290f,0,-290f);
	BoundingBox b4 = new BoundingBox();
	b4.setYExtent(5);
	b4.setXExtent(2.5f);
	b4.setZExtent(2f);
	house4.setLocalBound(b4);
	
	House house5obj = new House();
	house5 = house5obj.getObj();
	house5.translate(-290f,0,-50f);
	house5.rotate(90, up);
	BoundingBox b5 = new BoundingBox();
	b5.setYExtent(5);
	b5.setXExtent(2.5f);
	b5.setZExtent(2f);
	house5.setLocalBound(b5);
		
	
	House house6Obj = new House();
	house6 = house6Obj.getObj();
	house6.translate(-160f,0,-160f);
	house6.rotate(180, up);
	BoundingBox b6 = new BoundingBox();
	b6.setYExtent(5);
	b6.setXExtent(2.5f);
	b6.setZExtent(2f);
	house6.setLocalBound(b6);
	
/*	House house7Obj = new House();
	house7 = house7Obj.getObj();
	house7.translate(0f,0,210f);
	house7.rotate(180, up);
	BoundingBox b7 = new BoundingBox();
	b7.setYExtent(5);
	b7.setXExtent(2.5f);
	b7.setZExtent(2f);
	house7.setLocalBound(b7); */
	
	
	House house7Obj = new House();
	house7 = house7Obj.getObj();
	house7.translate(290f,0,-20f);
	house7.rotate(180, up);
	BoundingBox b7 = new BoundingBox();
	b7.setYExtent(5);
	b7.setXExtent(2.5f);
	b7.setZExtent(2f);
	house7.setLocalBound(b7);
	
	
	
	
	House house8Obj = new House();
	house8 = house8Obj.getObj();
	house8.translate(0f,0,10f);
	house8.rotate(90, up);
	BoundingBox b8 = new BoundingBox();
	b8.setYExtent(5);
	b8.setXExtent(2.5f);
	b8.setZExtent(2f);
	house8.setLocalBound(b8);

	House house9Obj = new House();
	house9 = house9Obj.getObj();
	house9.translate(0f,0,-250f);
	BoundingBox b9 = new BoundingBox();
	b9.setYExtent(5);
	b9.setXExtent(2.5f);
	b9.setZExtent(2f);
	house9.setLocalBound(b9);
	
	House house10Obj = new House();
	house10 = house10Obj.getObj();
	house10.translate(130f,0,-120f);
	house10.rotate(180, up);
	BoundingBox b10 = new BoundingBox();
	b10.setYExtent(5);
	b10.setXExtent(2.5f);
	b10.setZExtent(2f);
	house10.setLocalBound(b10);
	
	House house11Obj = new House();
	house11 = house11Obj.getObj();
	house11.translate(250f,0,-290f);
	house11.rotate(-90, up);
	BoundingBox b11 = new BoundingBox();
	b11.setYExtent(5);
	b11.setXExtent(2.5f);
	b11.setZExtent(2f);
	house11.setLocalBound(b11);
	
/*	House house12Obj = new House();
	house12 = house12Obj.getObj();
	house12.translate(150f,0,140);
	house12.rotate(-90, up);
	BoundingBox b12 = new BoundingBox();
	b12.setYExtent(5);
	b12.setXExtent(2.5f);
	b12.setZExtent(2f);
	house12.setLocalBound(b12); */
	
	House house12Obj = new House();
	house12 = house12Obj.getObj();
	house12.translate(150f,0,100);
	house12.rotate(-90, up);
	BoundingBox b12 = new BoundingBox();
	b12.setYExtent(5);
	b12.setXExtent(2.5f);
	b12.setZExtent(2f);
	house12.setLocalBound(b12);
	
	
	addGameWorldObject(house1);
	addGameWorldObject(house2);
	addGameWorldObject(house3);
	addGameWorldObject(house4);
	addGameWorldObject(house5);
	addGameWorldObject(house6);
	addGameWorldObject(house7);
	addGameWorldObject(house8);
	addGameWorldObject(house9);
	addGameWorldObject(house10);
	addGameWorldObject(house11);
	addGameWorldObject(house12);

	
	
	buildings = new ArrayList<SceneNode>();
	buildings.add(house1);
	buildings.add(house2);
	buildings.add(house3);
	buildings.add(house4);
	buildings.add(house5);
	buildings.add(house6);
	buildings.add(house7);
	buildings.add(house8);
	buildings.add(house9);
	buildings.add(house10);
	buildings.add(house11);
	buildings.add(house12);
	

}




public void createTeam()
{
	
	
	myTeam = new ArrayList();
	
	if(playerOne)
	{
		
		Character firstCharacter = new Character("B", 1, this);
		tileGrid.getTileGrid()[58][18].occupy(firstCharacter.getCharacterTypeName());
		firstCharacter.setGridIJ(58, 18);
		firstCharacter.setLocation(tileGrid.getTileGrid()[58][18].getX(), firstCharacter.getY(), tileGrid.getTileGrid()[58][18].getZ());
		firstCharacter.translate((float)firstCharacter.getX(), (float)firstCharacter.getY(), (float)firstCharacter.getZ());
		myTeam.add(firstCharacter);
		addGameWorldObject(firstCharacter);
		
		IRenderer renderer = DisplaySystem.getCurrentDisplaySystem().getRenderer();
		BlendState btransp = (BlendState) renderer.createRenderState(RenderStateType.Blend);
		btransp.setBlendEnabled(true);
		btransp.setEnabled(true);
		firstCharacter.getLifeBar().setRenderState(btransp);
		firstCharacter.getLifeBar().updateRenderStates();
		firstCharacter.getLifeBar().setRenderMode(RENDER_MODE.TRANSPARENT);
		addGameWorldObject(firstCharacter.getLifeBar());
		
		
		
		Character secondCharacter = new Character("W", 2, this);
		tileGrid.getTileGrid()[59][23].occupy(secondCharacter.getCharacterTypeName());
		secondCharacter.setGridIJ(59, 23);
		secondCharacter.setLocation(tileGrid.getTileGrid()[59][23].getX(), secondCharacter.getY(), tileGrid.getTileGrid()[59][23].getZ());
		secondCharacter.translate((float)secondCharacter.getX(), (float)secondCharacter.getY(), (float)secondCharacter.getZ());
		myTeam.add(secondCharacter);
		addGameWorldObject(secondCharacter);
		secondCharacter.getLifeBar().setRenderState(btransp);
		secondCharacter.getLifeBar().updateRenderStates();
		secondCharacter.getLifeBar().setRenderMode(RENDER_MODE.TRANSPARENT);
		addGameWorldObject(secondCharacter.getLifeBar());
		
		
		Character thirdCharacter = new Character("A", 3, this);
		tileGrid.getTileGrid()[61][22].occupy(thirdCharacter.getCharacterTypeName());
		thirdCharacter.setGridIJ(61, 22);
		thirdCharacter.setLocation(tileGrid.getTileGrid()[61][22].getX(), thirdCharacter.getY(), tileGrid.getTileGrid()[61][22].getZ());
		thirdCharacter.translate((float)thirdCharacter.getX(), (float)thirdCharacter.getY(), (float)thirdCharacter.getZ());
		myTeam.add(thirdCharacter);
		addGameWorldObject(thirdCharacter);
		thirdCharacter.getLifeBar().setRenderState(btransp);
		thirdCharacter.getLifeBar().updateRenderStates();
		thirdCharacter.getLifeBar().setRenderMode(RENDER_MODE.TRANSPARENT);
		addGameWorldObject(thirdCharacter.getLifeBar());
		
		Character fourthCharacter = new Character("A", 4, this);
		tileGrid.getTileGrid()[60][19].occupy(fourthCharacter.getCharacterTypeName());
		fourthCharacter.setGridIJ(60, 19);
		fourthCharacter.setLocation(tileGrid.getTileGrid()[60][19].getX(), fourthCharacter.getY(), tileGrid.getTileGrid()[60][19].getZ());
		fourthCharacter.translate((float)fourthCharacter.getX(), (float)fourthCharacter.getY(), (float)fourthCharacter.getZ());
		myTeam.add(fourthCharacter);
		addGameWorldObject(fourthCharacter);
		fourthCharacter.getLifeBar().setRenderState(btransp);
		fourthCharacter.getLifeBar().updateRenderStates();
		fourthCharacter.getLifeBar().setRenderMode(RENDER_MODE.TRANSPARENT);
		addGameWorldObject(fourthCharacter.getLifeBar());
		
		Character fifthCharacter = new Character("B", 5, this);
		tileGrid.getTileGrid()[61][24].occupy(fifthCharacter.getCharacterTypeName());
		fifthCharacter.setGridIJ(61, 24);
		fifthCharacter.setLocation(tileGrid.getTileGrid()[61][24].getX(), fifthCharacter.getY(), tileGrid.getTileGrid()[61][24].getZ());
		fifthCharacter.translate((float)fifthCharacter.getX(), (float)fifthCharacter.getY(), (float)fifthCharacter.getZ());
		myTeam.add(fifthCharacter);
		addGameWorldObject(fifthCharacter);
		fifthCharacter.getLifeBar().setRenderState(btransp);
		fifthCharacter.getLifeBar().updateRenderStates();
		fifthCharacter.getLifeBar().setRenderMode(RENDER_MODE.TRANSPARENT);
		addGameWorldObject(fifthCharacter.getLifeBar());
		
		Character sixthCharacter = new Character("W", 6, this);
		tileGrid.getTileGrid()[59][21].occupy(sixthCharacter.getCharacterTypeName());
		sixthCharacter.setGridIJ(59, 21);
		sixthCharacter.setLocation(tileGrid.getTileGrid()[59][21].getX(), sixthCharacter.getY(), tileGrid.getTileGrid()[59][21].getZ());
		sixthCharacter.translate((float)sixthCharacter.getX(), (float)sixthCharacter.getY(), (float)sixthCharacter.getZ());
		myTeam.add(sixthCharacter);
		addGameWorldObject(sixthCharacter);
		sixthCharacter.getLifeBar().setRenderState(btransp);
		sixthCharacter.getLifeBar().updateRenderStates();
		sixthCharacter.getLifeBar().setRenderMode(RENDER_MODE.TRANSPARENT);
		addGameWorldObject(sixthCharacter.getLifeBar());
		
		
		
	/*
	Character firstCharacter = new Character("B", 1, this);
	tileGrid.getTileGrid()[30][63].occupy(firstCharacter.getCharacterTypeName());
	firstCharacter.setGridIJ(30, 63);
	firstCharacter.setLocation(tileGrid.getTileGrid()[30][63].getX(), firstCharacter.getY(), tileGrid.getTileGrid()[30][63].getZ());
	firstCharacter.translate((float)firstCharacter.getX(), (float)firstCharacter.getY(), (float)firstCharacter.getZ());
	myTeam.add(firstCharacter);
	addGameWorldObject(firstCharacter);
	
	IRenderer renderer = DisplaySystem.getCurrentDisplaySystem().getRenderer();
	BlendState btransp = (BlendState) renderer.createRenderState(RenderStateType.Blend);
	btransp.setBlendEnabled(true);
	btransp.setEnabled(true);
	firstCharacter.getLifeBar().setRenderState(btransp);
	firstCharacter.getLifeBar().updateRenderStates();
	firstCharacter.getLifeBar().setRenderMode(RENDER_MODE.TRANSPARENT);
	addGameWorldObject(firstCharacter.getLifeBar());
	
	
	
	Character secondCharacter = new Character("W", 2, this);
	tileGrid.getTileGrid()[29][63].occupy(secondCharacter.getCharacterTypeName());
	secondCharacter.setGridIJ(29, 63);
	secondCharacter.setLocation(tileGrid.getTileGrid()[29][63].getX(), secondCharacter.getY(), tileGrid.getTileGrid()[29][63].getZ());
	secondCharacter.translate((float)secondCharacter.getX(), (float)secondCharacter.getY(), (float)secondCharacter.getZ());
	myTeam.add(secondCharacter);
	addGameWorldObject(secondCharacter);
	secondCharacter.getLifeBar().setRenderState(btransp);
	secondCharacter.getLifeBar().updateRenderStates();
	secondCharacter.getLifeBar().setRenderMode(RENDER_MODE.TRANSPARENT);
	addGameWorldObject(secondCharacter.getLifeBar());
	
	
	Character thirdCharacter = new Character("W", 3, this);
	tileGrid.getTileGrid()[28][63].occupy(thirdCharacter.getCharacterTypeName());
	thirdCharacter.setGridIJ(28, 63);
	thirdCharacter.setLocation(tileGrid.getTileGrid()[28][63].getX(), thirdCharacter.getY(), tileGrid.getTileGrid()[28][63].getZ());
	thirdCharacter.translate((float)thirdCharacter.getX(), (float)thirdCharacter.getY(), (float)thirdCharacter.getZ());
	myTeam.add(thirdCharacter);
	addGameWorldObject(thirdCharacter);
	thirdCharacter.getLifeBar().setRenderState(btransp);
	thirdCharacter.getLifeBar().updateRenderStates();
	thirdCharacter.getLifeBar().setRenderMode(RENDER_MODE.TRANSPARENT);
	addGameWorldObject(thirdCharacter.getLifeBar());
	
	Character fourthCharacter = new Character("A", 4, this);
	tileGrid.getTileGrid()[28][62].occupy(fourthCharacter.getCharacterTypeName());
	fourthCharacter.setGridIJ(28, 62);
	fourthCharacter.setLocation(tileGrid.getTileGrid()[28][62].getX(), fourthCharacter.getY(), tileGrid.getTileGrid()[28][62].getZ());
	fourthCharacter.translate((float)fourthCharacter.getX(), (float)fourthCharacter.getY(), (float)fourthCharacter.getZ());
	myTeam.add(fourthCharacter);
	addGameWorldObject(fourthCharacter);
	fourthCharacter.getLifeBar().setRenderState(btransp);
	fourthCharacter.getLifeBar().updateRenderStates();
	fourthCharacter.getLifeBar().setRenderMode(RENDER_MODE.TRANSPARENT);
	addGameWorldObject(fourthCharacter.getLifeBar());
	*/

	for(int i = 0; i<myTeam.size(); i++)
	{
		myTeam.get(i).updateLifeBar();
	}
	
	}
	
	else
	{
	/*
		Character firstCharacter = new Character("K", 1, this);
		tileGrid.getTileGrid()[25][60].occupy(firstCharacter.getCharacterTypeName());
		firstCharacter.setGridIJ(25, 60);
		firstCharacter.setLocation(tileGrid.getTileGrid()[25][60].getX(), firstCharacter.getY(), tileGrid.getTileGrid()[25][60].getZ());
		firstCharacter.translate((float)firstCharacter.getX(), (float)firstCharacter.getY(), (float)firstCharacter.getZ());
		myTeam.add(firstCharacter);
		addGameWorldObject(firstCharacter);
		
		IRenderer renderer = DisplaySystem.getCurrentDisplaySystem().getRenderer();
		BlendState btransp = (BlendState) renderer.createRenderState(RenderStateType.Blend);
		btransp.setBlendEnabled(true);
		btransp.setEnabled(true);
		firstCharacter.getLifeBar().setRenderState(btransp);
		firstCharacter.getLifeBar().updateRenderStates();
		firstCharacter.getLifeBar().setRenderMode(RENDER_MODE.TRANSPARENT);
		addGameWorldObject(firstCharacter.getLifeBar());
		
		
		
		Character secondCharacter = new Character("W", 2, this);
		tileGrid.getTileGrid()[24][61].occupy(secondCharacter.getCharacterTypeName());
		secondCharacter.setGridIJ(24, 61);
		secondCharacter.setLocation(tileGrid.getTileGrid()[24][61].getX(), secondCharacter.getY(), tileGrid.getTileGrid()[24][61].getZ());
		secondCharacter.translate((float)secondCharacter.getX(), (float)secondCharacter.getY(), (float)secondCharacter.getZ());
		myTeam.add(secondCharacter);
		addGameWorldObject(secondCharacter);
		secondCharacter.getLifeBar().setRenderState(btransp);
		secondCharacter.getLifeBar().updateRenderStates();
		secondCharacter.getLifeBar().setRenderMode(RENDER_MODE.TRANSPARENT);
		addGameWorldObject(secondCharacter.getLifeBar());
		
		
		Character thirdCharacter = new Character("W", 3, this);
		tileGrid.getTileGrid()[24][60].occupy(thirdCharacter.getCharacterTypeName());
		thirdCharacter.setGridIJ(24, 60);
		thirdCharacter.setLocation(tileGrid.getTileGrid()[24][60].getX(), thirdCharacter.getY(), tileGrid.getTileGrid()[24][60].getZ());
		thirdCharacter.translate((float)thirdCharacter.getX(), (float)thirdCharacter.getY(), (float)thirdCharacter.getZ());
		myTeam.add(thirdCharacter);
		addGameWorldObject(thirdCharacter);
		thirdCharacter.getLifeBar().setRenderState(btransp);
		thirdCharacter.getLifeBar().updateRenderStates();
		thirdCharacter.getLifeBar().setRenderMode(RENDER_MODE.TRANSPARENT);
		addGameWorldObject(thirdCharacter.getLifeBar());
		
		Character fourthCharacter = new Character("A", 4, this);
		tileGrid.getTileGrid()[23][60].occupy(fourthCharacter.getCharacterTypeName());
		fourthCharacter.setGridIJ(23, 60);
		fourthCharacter.setLocation(tileGrid.getTileGrid()[23][60].getX(), fourthCharacter.getY(), tileGrid.getTileGrid()[23][60].getZ());
		fourthCharacter.translate((float)fourthCharacter.getX(), (float)fourthCharacter.getY(), (float)fourthCharacter.getZ());
		myTeam.add(fourthCharacter);
		addGameWorldObject(fourthCharacter);
		fourthCharacter.getLifeBar().setRenderState(btransp);
		fourthCharacter.getLifeBar().updateRenderStates();
		fourthCharacter.getLifeBar().setRenderMode(RENDER_MODE.TRANSPARENT);
		addGameWorldObject(fourthCharacter.getLifeBar());	
	*/	

		Character firstCharacter = new Character("K", 1, this);
		tileGrid.getTileGrid()[2][30].occupy(firstCharacter.getCharacterTypeName());
		firstCharacter.setGridIJ(2, 30);
		firstCharacter.setLocation(tileGrid.getTileGrid()[2][30].getX(), firstCharacter.getY(), tileGrid.getTileGrid()[2][30].getZ());
		firstCharacter.translate((float)firstCharacter.getX(), (float)firstCharacter.getY(), (float)firstCharacter.getZ());
		myTeam.add(firstCharacter);
		addGameWorldObject(firstCharacter);
		
		IRenderer renderer = DisplaySystem.getCurrentDisplaySystem().getRenderer();
		BlendState btransp = (BlendState) renderer.createRenderState(RenderStateType.Blend);
		btransp.setBlendEnabled(true);
		btransp.setEnabled(true);
		firstCharacter.getLifeBar().setRenderState(btransp);
		firstCharacter.getLifeBar().updateRenderStates();
		firstCharacter.getLifeBar().setRenderMode(RENDER_MODE.TRANSPARENT);
		addGameWorldObject(firstCharacter.getLifeBar());
		
		
		
		Character secondCharacter = new Character("W", 2, this);
		tileGrid.getTileGrid()[3][31].occupy(secondCharacter.getCharacterTypeName());
		secondCharacter.setGridIJ(3, 31);
		secondCharacter.setLocation(tileGrid.getTileGrid()[3][31].getX(), secondCharacter.getY(), tileGrid.getTileGrid()[3][31].getZ());
		secondCharacter.translate((float)secondCharacter.getX(), (float)secondCharacter.getY(), (float)secondCharacter.getZ());
		myTeam.add(secondCharacter);
		addGameWorldObject(secondCharacter);
		secondCharacter.getLifeBar().setRenderState(btransp);
		secondCharacter.getLifeBar().updateRenderStates();
		secondCharacter.getLifeBar().setRenderMode(RENDER_MODE.TRANSPARENT);
		addGameWorldObject(secondCharacter.getLifeBar());
		
		
		Character thirdCharacter = new Character("A", 3, this);
		tileGrid.getTileGrid()[0][28].occupy(thirdCharacter.getCharacterTypeName());
		thirdCharacter.setGridIJ(0, 28);
		thirdCharacter.setLocation(tileGrid.getTileGrid()[0][28].getX(), thirdCharacter.getY(), tileGrid.getTileGrid()[0][28].getZ());
		thirdCharacter.translate((float)thirdCharacter.getX(), (float)thirdCharacter.getY(), (float)thirdCharacter.getZ());
		myTeam.add(thirdCharacter);
		addGameWorldObject(thirdCharacter);
		thirdCharacter.getLifeBar().setRenderState(btransp);
		thirdCharacter.getLifeBar().updateRenderStates();
		thirdCharacter.getLifeBar().setRenderMode(RENDER_MODE.TRANSPARENT);
		addGameWorldObject(thirdCharacter.getLifeBar());
		
		Character fourthCharacter = new Character("A", 4, this);
		tileGrid.getTileGrid()[4][30].occupy(fourthCharacter.getCharacterTypeName());
		fourthCharacter.setGridIJ(4, 30);
		fourthCharacter.setLocation(tileGrid.getTileGrid()[4][30].getX(), fourthCharacter.getY(), tileGrid.getTileGrid()[4][30].getZ());
		fourthCharacter.translate((float)fourthCharacter.getX(), (float)fourthCharacter.getY(), (float)fourthCharacter.getZ());
		myTeam.add(fourthCharacter);
		addGameWorldObject(fourthCharacter);
		fourthCharacter.getLifeBar().setRenderState(btransp);
		fourthCharacter.getLifeBar().updateRenderStates();
		fourthCharacter.getLifeBar().setRenderMode(RENDER_MODE.TRANSPARENT);
		addGameWorldObject(fourthCharacter.getLifeBar());
		
		Character fifthCharacter = new Character("B", 5, this);
		tileGrid.getTileGrid()[3][34].occupy(fifthCharacter.getCharacterTypeName());
		fifthCharacter.setGridIJ(3, 34);
		fifthCharacter.setLocation(tileGrid.getTileGrid()[3][34].getX(), fifthCharacter.getY(), tileGrid.getTileGrid()[3][34].getZ());
		fifthCharacter.translate((float)fifthCharacter.getX(), (float)fifthCharacter.getY(), (float)fifthCharacter.getZ());
		myTeam.add(fifthCharacter);
		addGameWorldObject(fifthCharacter);
		fifthCharacter.getLifeBar().setRenderState(btransp);
		fifthCharacter.getLifeBar().updateRenderStates();
		fifthCharacter.getLifeBar().setRenderMode(RENDER_MODE.TRANSPARENT);
		addGameWorldObject(fifthCharacter.getLifeBar());
		
		Character sixthCharacter = new Character("W", 6, this);
		tileGrid.getTileGrid()[1][32].occupy(sixthCharacter.getCharacterTypeName());
		sixthCharacter.setGridIJ(1, 32);
		sixthCharacter.setLocation(tileGrid.getTileGrid()[1][32].getX(), sixthCharacter.getY(), tileGrid.getTileGrid()[1][32].getZ());
		sixthCharacter.translate((float)sixthCharacter.getX(), (float)sixthCharacter.getY(), (float)sixthCharacter.getZ());
		myTeam.add(sixthCharacter);
		addGameWorldObject(sixthCharacter);
		sixthCharacter.getLifeBar().setRenderState(btransp);
		sixthCharacter.getLifeBar().updateRenderStates();
		sixthCharacter.getLifeBar().setRenderMode(RENDER_MODE.TRANSPARENT);
		addGameWorldObject(sixthCharacter.getLifeBar());
		
		Character seventhCharacter = new Character("B", 7, this);
		tileGrid.getTileGrid()[0][33].occupy(seventhCharacter.getCharacterTypeName());
		seventhCharacter.setGridIJ(0, 33);
		seventhCharacter.setLocation(tileGrid.getTileGrid()[0][33].getX(), seventhCharacter.getY(), tileGrid.getTileGrid()[0][33].getZ());
		seventhCharacter.translate((float)seventhCharacter.getX(), (float)seventhCharacter.getY(), (float)seventhCharacter.getZ());
		myTeam.add(seventhCharacter);
		addGameWorldObject(seventhCharacter);
		seventhCharacter.getLifeBar().setRenderState(btransp);
		seventhCharacter.getLifeBar().updateRenderStates();
		seventhCharacter.getLifeBar().setRenderMode(RENDER_MODE.TRANSPARENT);
		addGameWorldObject(seventhCharacter.getLifeBar());
			
		
		
		
	for(int i = 0; i<myTeam.size(); i++)
	{
		myTeam.get(i).updateLifeBar();
	}
	
	}
	
}

public void createPlayers()
{
	createPlayerOne();
}


public void updateLifeBars()
{
	for(int i = 0; i<myTeam.size(); i++)
	{
		myTeam.get(0).updateLifeBar();
	}
	for(int j = 0; j<npcs.size(); j++)
	{
		npcs.get(j).updateLifeBar();
	}
	for(int k = 0; k<ghostTeam.size(); k++)
	{
		ghostTeam.get(k).updateLifeBar();
	}
	
}


public void createCamera()
{	 
	
	 cameraPointer = new MyCube(cameraPointerColor);
	 IRenderer renderer = DisplaySystem.getCurrentDisplaySystem().getRenderer();
	 BlendState btransp = (BlendState) renderer.createRenderState(RenderStateType.Blend);
	 btransp.setBlendEnabled(true);
	 btransp.setEnabled(true);
	 cameraPointer.setRenderState(btransp);
	 cameraPointer.updateRenderStates();
	 cameraPointer.setRenderMode(RENDER_MODE.TRANSPARENT);
	 cameraPointer.translate((float)cameraPointerSpawnX, (float)cameraPointerSpawnY, (float)cameraPointerSpawnZ);
	 cameraPointer.scale((float)cameraPointerScaleX, (float)cameraPointerScaleY, (float)cameraPointerScaleZ);
	 addGameWorldObject(cameraPointer);
	 
	 
	 skybox1.setLocalTranslation(cameraPointer.getLocalTranslation());
	 
	 camera1 = new JOGLCamera(renderer);
	 camera1.setPerspectiveFrustum(camera1ViewAngle,camera1AspectRatio,camera1NearClip,camera1FarClip);
	 camera1.setViewport(camera1Left, camera1Right, camera1Bottom, camera1Top);
	 
	 moveCameraController = new MyCameraMoveController(cameraPointer);
	 cameraPointer.addController(moveCameraController);

}

public void createPlayerOne()
{
	createCursorObject();
	createCamera();

}

public void createCursorObject()
{
	
	cursorTextureYellow = TextureManager.loadTexture2D("cursorTextures/cursorTextureYellow.png");
	cursorTextureGreen = TextureManager.loadTexture2D("cursorTextures/cursorTextureGreen.png");
	cursorTextureTeam = TextureManager.loadTexture2D("cursorTextures/cursorTextureTeam.png");
	cursorTextureEnemy = TextureManager.loadTexture2D("cursorTextures/cursorTextureEnemy.png");
	cursorTextureKing = TextureManager.loadTexture2D("cursorTextures/cursorTextureKing.png");
	cursorTextureAttack = TextureManager.loadTexture2D("cursorTextures/cursorTextureAttack.png");
	cursorTextureMove = TextureManager.loadTexture2D("cursorTextures/cursorTextureMove.png");
	cursorTextureX = TextureManager.loadTexture2D("cursorTextures/cursorTextureX.png");
	cursorTextureClear = TextureManager.loadTexture2D("cursorTextures/cursorTextureClear.png");

	hoveringCursorTeam = TextureManager.loadTexture2D("hoveringCursorTextures/hoveringCursorTeam.png");
	hoveringCursorAttack = TextureManager.loadTexture2D("hoveringCursorTextures/hoveringCursorAttack.png");
	hoveringCursorPointer = TextureManager.loadTexture2D("hoveringCursorTextures/hoveringCursorPointer.png");
	hoveringCursorKing = TextureManager.loadTexture2D("hoveringCursorTextures/hoveringCursorKing.png");
	hoveringCursorMove = TextureManager.loadTexture2D("hoveringCursorTextures/hoveringCursorMove.png");
	hoveringCursorEnemy = TextureManager.loadTexture2D("hoveringCursorTextures/hoveringCursorEnemy.png");

	
	if(playerOne)
	{
		cursorObject = new Tile();
		cursorObject.setColorBuffer("white");
		cursorObject.setTexture(cursorTextureClear);
		IRenderer renderer = DisplaySystem.getCurrentDisplaySystem().getRenderer();
		BlendState btransp = (BlendState) renderer.createRenderState(RenderStateType.Blend);
		btransp.setBlendEnabled(true);
		btransp.setEnabled(true);
		cursorObject.setRenderState(btransp);
		cursorObject.updateRenderStates();
		cursorObject.setRenderMode(RENDER_MODE.TRANSPARENT);

		
		tileGrid.getTileGrid()[63][19].setIsSelected(true);
		cursorObject.setGridIJ(63, 19);
		cursorObject.setLocation(tileGrid.getTileGrid()[63][19].getX(), cursorObjectY , tileGrid.getTileGrid()[63][19].getZ());
		cursorObject.translate((float)cursorObject.getX(), (float)cursorObject.getY(), (float)cursorObject.getZ());
		addGameWorldObject(cursorObject);
				
		cameraPointerSpawnX = tileGrid.getTileGrid()[63][19].getX();
		cameraPointerSpawnZ = tileGrid.getTileGrid()[63][19].getZ();
		
		hoveringCursorObject = new Tile();
		hoveringCursorObject.setColorBuffer("white2");
		hoveringCursorObject.setTexture(hoveringCursorPointer);
		
		hoveringCursorObject.setRenderState(btransp);
		hoveringCursorObject.updateRenderStates();
		hoveringCursorObject.setRenderMode(RENDER_MODE.TRANSPARENT);
		
		
		
		hoveringCursorObject.setLocation(tileGrid.getTileGrid()[63][19].getX(), cursorObjectY , tileGrid.getTileGrid()[63][19].getZ());
		hoveringCursorObject.translate((float)(cursorObject.getX()+0.2), 18, (float)(cursorObject.getZ()+2.5));
		Vector3D axis = new Vector3D(1,0,0);
		hoveringCursorObject.rotate(-45,  axis);
		hoveringCursorObject.scale(.5f,.5f,.5f);
		
		hoveringCursorController = new MyHoveringCursorController(hoveringCursorObject, this);
		hoveringCursorObject.addController(hoveringCursorController);
	
		addGameWorldObject(hoveringCursorObject);
		
		/*
		cursorObject = new Tile();
		cursorObject.setColorBuffer("white");
		cursorObject.setTexture(cursorTextureClear);
		IRenderer renderer = DisplaySystem.getCurrentDisplaySystem().getRenderer();
		BlendState btransp = (BlendState) renderer.createRenderState(RenderStateType.Blend);
		btransp.setBlendEnabled(true);
		btransp.setEnabled(true);
		cursorObject.setRenderState(btransp);
		cursorObject.updateRenderStates();
		cursorObject.setRenderMode(RENDER_MODE.TRANSPARENT);

		
		tileGrid.getTileGrid()[30][60].setIsSelected(true);
		cursorObject.setGridIJ(30, 60);
		cursorObject.setLocation(tileGrid.getTileGrid()[30][60].getX(), cursorObjectY , tileGrid.getTileGrid()[30][60].getZ());
		cursorObject.translate((float)cursorObject.getX(), (float)cursorObject.getY(), (float)cursorObject.getZ());
		addGameWorldObject(cursorObject);
		
		cameraPointerSpawnX = tileGrid.getTileGrid()[30][60].getX();
		cameraPointerSpawnZ = tileGrid.getTileGrid()[30][60].getZ();
		
		hoveringCursorObject = new Tile();
		hoveringCursorObject.setColorBuffer("white2");
		hoveringCursorObject.setTexture(hoveringCursorPointer);
		
		hoveringCursorObject.setRenderState(btransp);
		hoveringCursorObject.updateRenderStates();
		hoveringCursorObject.setRenderMode(RENDER_MODE.TRANSPARENT);
		
		
		
		hoveringCursorObject.setLocation(tileGrid.getTileGrid()[30][60].getX(), cursorObjectY , tileGrid.getTileGrid()[30][60].getZ());
		hoveringCursorObject.translate((float)(cursorObject.getX()+0.2), 18, (float)(cursorObject.getZ()+2.5));
		Vector3D axis = new Vector3D(1,0,0);
		hoveringCursorObject.rotate(-45,  axis);
		hoveringCursorObject.scale(.5f,.5f,.5f);
		
		hoveringCursorController = new MyHoveringCursorController(hoveringCursorObject, this);
		hoveringCursorObject.addController(hoveringCursorController);
	
		addGameWorldObject(hoveringCursorObject);
		*/
	}
	
	
	else
	{
		/*
		cursorObject = new Tile();
		cursorObject.setColorBuffer("white");
		cursorObject.setTexture(cursorTextureClear);
		IRenderer renderer = DisplaySystem.getCurrentDisplaySystem().getRenderer();
		BlendState btransp = (BlendState) renderer.createRenderState(RenderStateType.Blend);
		btransp.setBlendEnabled(true);
		btransp.setEnabled(true);
		cursorObject.setRenderState(btransp);
		cursorObject.updateRenderStates();
		cursorObject.setRenderMode(RENDER_MODE.TRANSPARENT);

		
		tileGrid.getTileGrid()[25][60].setIsSelected(true);
		cursorObject.setGridIJ(25, 60);
		cursorObject.setLocation(tileGrid.getTileGrid()[25][60].getX(), cursorObjectY , tileGrid.getTileGrid()[25][60].getZ());
		cursorObject.translate((float)cursorObject.getX(), (float)cursorObject.getY(), (float)cursorObject.getZ());
		addGameWorldObject(cursorObject);
		
		cameraPointerSpawnX = tileGrid.getTileGrid()[25][60].getX();
		cameraPointerSpawnZ = tileGrid.getTileGrid()[25][60].getZ();
		
		hoveringCursorObject = new Tile();
		hoveringCursorObject.setColorBuffer("white2");
		hoveringCursorObject.setTexture(hoveringCursorPointer);
		
		hoveringCursorObject.setRenderState(btransp);
		hoveringCursorObject.updateRenderStates();
		hoveringCursorObject.setRenderMode(RENDER_MODE.TRANSPARENT);
		
		
		
		hoveringCursorObject.setLocation(tileGrid.getTileGrid()[25][60].getX(), cursorObjectY , tileGrid.getTileGrid()[25][60].getZ());
		hoveringCursorObject.translate((float)(cursorObject.getX()+0.2), 20, (float)(cursorObject.getZ()+2.5));
		Vector3D axis = new Vector3D(1,0,0);
		hoveringCursorObject.rotate(-45,  axis);
		hoveringCursorObject.scale(.5f,.5f,.5f);
		

		hoveringCursorController = new MyHoveringCursorController(hoveringCursorObject, this);
		hoveringCursorObject.addController(hoveringCursorController);
		
		
		addGameWorldObject(hoveringCursorObject);
		*/
	
		cursorObject = new Tile();
		cursorObject.setColorBuffer("white");
		cursorObject.setTexture(cursorTextureClear);
		IRenderer renderer = DisplaySystem.getCurrentDisplaySystem().getRenderer();
		BlendState btransp = (BlendState) renderer.createRenderState(RenderStateType.Blend);
		btransp.setBlendEnabled(true);
		btransp.setEnabled(true);
		cursorObject.setRenderState(btransp);
		cursorObject.updateRenderStates();
		cursorObject.setRenderMode(RENDER_MODE.TRANSPARENT);

		
		tileGrid.getTileGrid()[5][29].setIsSelected(true);
		cursorObject.setGridIJ(5,29 );
		cursorObject.setLocation(tileGrid.getTileGrid()[5][29].getX(), cursorObjectY , tileGrid.getTileGrid()[5][29].getZ());
		cursorObject.translate((float)cursorObject.getX(), (float)cursorObject.getY(), (float)cursorObject.getZ());
		addGameWorldObject(cursorObject);
		
		cameraPointerSpawnX = tileGrid.getTileGrid()[5][29].getX();
		cameraPointerSpawnZ = tileGrid.getTileGrid()[5][29].getZ();
		
		hoveringCursorObject = new Tile();
		hoveringCursorObject.setColorBuffer("white2");
		hoveringCursorObject.setTexture(hoveringCursorPointer);
		
		hoveringCursorObject.setRenderState(btransp);
		hoveringCursorObject.updateRenderStates();
		hoveringCursorObject.setRenderMode(RENDER_MODE.TRANSPARENT);
		
		
		
		hoveringCursorObject.setLocation(tileGrid.getTileGrid()[5][29].getX(), cursorObjectY , tileGrid.getTileGrid()[5][29].getZ());
		hoveringCursorObject.translate((float)(cursorObject.getX()+0.2), 20, (float)(cursorObject.getZ()+2.5));
		Vector3D axis = new Vector3D(1,0,0);
		hoveringCursorObject.rotate(-45,  axis);
		hoveringCursorObject.scale(.5f,.5f,.5f);
		

		hoveringCursorController = new MyHoveringCursorController(hoveringCursorObject, this);
		hoveringCursorObject.addController(hoveringCursorController);
		
		
		addGameWorldObject(hoveringCursorObject);
		
	}	
}

public void createGhostCharacter(UUID pID, int cID, String cType, int gI, int gJ)
{
	
	
	
	GhostCharacter ghostCharacter = new GhostCharacter(pID, cID, cType, gI, gJ, this);
	

	tileGrid.getTileGrid()[gI][gJ].occupy(cType);
//	tileGrid.getTileGrid()[gI][gJ].occupy("G");

	ghostCharacter.setLocation(tileGrid.getTileGrid()[gI][gJ].getX(), ghostCharacter.getY(), tileGrid.getTileGrid()[gI][gJ].getZ());
	ghostCharacter.translate((float)ghostCharacter.getX(), (float)ghostCharacter.getY(), (float)ghostCharacter.getZ());
	
	addGameWorldObject(ghostCharacter);
	ghostTeam.add(ghostCharacter);

	
	
	IRenderer renderer = DisplaySystem.getCurrentDisplaySystem().getRenderer();
	BlendState btransp = (BlendState) renderer.createRenderState(RenderStateType.Blend);
	btransp.setBlendEnabled(true);
	btransp.setEnabled(true);
	ghostCharacter.getLifeBar().setRenderState(btransp);
	ghostCharacter.getLifeBar().updateRenderStates();
	ghostCharacter.getLifeBar().setRenderMode(RENDER_MODE.TRANSPARENT);
	addGameWorldObject(ghostCharacter.getLifeBar());
	

}

public void createNPC(UUID pID, int cID, String cType, int gI, int gJ)
{
	NPC npc = new NPC(pID, cID, cType, gI, gJ, this);
	

	tileGrid.getTileGrid()[gI][gJ].occupy(cType);
//	tileGrid.getTileGrid()[gI][gJ].occupy("N");

	npc.setLocation(tileGrid.getTileGrid()[gI][gJ].getX(), npc.getY(), tileGrid.getTileGrid()[gI][gJ].getZ());
	npc.translate((float)npc.getX(), (float)npc.getY(), (float)npc.getZ());
	
	addGameWorldObject(npc);
	npcs.add(npc);

	
	
	IRenderer renderer = DisplaySystem.getCurrentDisplaySystem().getRenderer();
	BlendState btransp = (BlendState) renderer.createRenderState(RenderStateType.Blend);
	btransp.setBlendEnabled(true);
	btransp.setEnabled(true);
	npc.getLifeBar().setRenderState(btransp);
	npc.getLifeBar().updateRenderStates();
	npc.getLifeBar().setRenderMode(RENDER_MODE.TRANSPARENT);
	addGameWorldObject(npc.getLifeBar());
	
	

	initAudio();

}


public void createGroundPlane()
{
	groundPlaneTiles = new ArrayList();
	
	Texture texture = TextureManager.loadTexture2D("textures/groundPlaneTexture.png");
	
	for(int i = 0; i<8; i++)
	{
		for(int j= 0; j<8; j++)
		{
			GroundPlaneTile gt = new GroundPlaneTile();
			gt.setTexture(texture);
			SceneNode tile = (SceneNode)gt.getObj();
			tile.translate((280 - (j*80)), (float)mapTileY, (280 - (i*80)));
			addGameWorldObject(tile);
			groundPlaneTiles.add(tile);
		}
	}
	
	
	groundPlaneBlue = new ArrayList<Tile>();
	
	boolean goingUp = true;
	int offset = 13;
	
	for(int j = 0; j<7; j++)
	{
	Tile tile = new Tile();
	tile.setColorBuffer("blue2");
	IRenderer renderer = DisplaySystem.getCurrentDisplaySystem().getRenderer();
	BlendState btransp = (BlendState) renderer.createRenderState(RenderStateType.Blend);
	btransp.setBlendEnabled(true);
	btransp.setEnabled(true);
	tile.setRenderState(btransp);
	tile.updateRenderStates();
	tile.setRenderMode(RENDER_MODE.TRANSPARENT);
	//cursorObject.translate((float)cursorObjectX, (float)cursorObjectY, (float)cursorObjectZ);
	

	tile.setLocation(5, transparentGroundBlueNoShow ,  5+(j*10));
	tile.scale((float)offset, 1, 1);
	tile.translate(5, (float)transparentGroundBlueNoShow, 5+(j*10));

	if(goingUp)
	{
		offset-=2;
	}
	
	addGameWorldObject(tile);
	groundPlaneBlue.add(tile);
	}
	
	int offset2 = 11;
	for(int k = 1; k<7; k++)
	{
	Tile tile = new Tile();
	tile.setColorBuffer("blue2");
	IRenderer renderer = DisplaySystem.getCurrentDisplaySystem().getRenderer();
	BlendState btransp = (BlendState) renderer.createRenderState(RenderStateType.Blend);
	btransp.setBlendEnabled(true);
	btransp.setEnabled(true);
	tile.setRenderState(btransp);
	tile.updateRenderStates();
	tile.setRenderMode(RENDER_MODE.TRANSPARENT);
	//cursorObject.translate((float)cursorObjectX, (float)cursorObjectY, (float)cursorObjectZ);
	

	tile.setLocation(5, transparentGroundBlueNoShow , 5-(k*10));
	tile.scale((float)offset2, 1, 1);
	tile.translate(5, (float)transparentGroundBlueNoShow, 5-(k*10));

	if(goingUp)
	{
		offset2-=2;
	}
	
	addGameWorldObject(tile);
	groundPlaneBlue.add(tile);
	}



}

public void createGrid()
{
	grid = new ArrayList<Line>();
	gridY = gridYNoSelect;
	
	int currentOffset = width;

	createOneGrid(currentOffset);
	
	int iterations = width/offset;
	
	for(int i = 0; i<iterations*2; i++)
	{
		currentOffset -=offset;
		createOneGrid(currentOffset);
	}
	
}


public void createYAxis()
{
	Point3D originY = new Point3D(0,2,0);
	Point3D positiveY = new Point3D(0,102,0);
	Point3D negativeY = new Point3D(0,-100,0);
	Line positiveYAxis = new Line(originY, positiveY, Color.green, 2);
	addGameWorldObject(positiveYAxis);
	Color lightGreen = new Color((float).6,(float)1,(float).6);
	Line negativeYAxis = new Line(originY, negativeY, lightGreen, 2);
	addGameWorldObject(negativeYAxis);
	
	Point3D originX = new Point3D(0,10,0);
	Point3D positiveX = new Point3D(102,10,0);
	Point3D negativeX = new Point3D(0,2,-102);
	Line positiveXAxis = new Line(originX, positiveX, Color.yellow, 2);
	addGameWorldObject(positiveXAxis);
	
	Point3D originZ = new Point3D(0,10,0);
	Point3D positiveZ = new Point3D(0,10,102);
	Point3D negativeZ = new Point3D(0,2,-102);
	Line positiveZAxis = new Line(originX, positiveZ, Color.red, 2);
	addGameWorldObject(positiveZAxis);
	
}



private void createTerrain()
{ 
	 Vector3D     terrainScale  = new Vector3D(1, 4.5f, 12);
	    Point3D      terrainOrigin = new Point3D(-150, 0, -100);
	    String       name          = "Terrain";
	    TextureState mtnTexState;
	    Texture      mtnTex;

	    // Create the height map from an image.
	/*
		HillHeightMap myHillHeightMap = new HillHeightMap(100, 2000, 1.0f, 20.0f,(byte)2, 12345);
		myHillHeightMap.setHeightScale(0.2f);

	    // Create the terrain block using the height map's size, and the origin
	    // specified above.
	    TerrainBlock terBlock = new TerrainBlock(name, myHillHeightMap.getSize(), terrainScale,
	      myHillHeightMap.getHeightData(), terrainOrigin);

	    // Create the texture for the terain.
	    mtnTex = TextureManager.loadTexture2D("textures/terrain.png");
	    mtnTex.setApplyMode(Texture.ApplyMode.Replace);

	    // Create the render state.
	    mtnTexState = (TextureState)DisplaySystem.getCurrentDisplaySystem()
	      .getRenderer().createRenderState(RenderState.RenderStateType.Texture);
	    mtnTexState.setTexture(mtnTex);
	    mtnTexState.setEnabled(true);
	    terBlock.setRenderState(mtnTexState);
	    
	    Vector3D up = new Vector3D(0,1,0);
	    
	//   terBlock.scale(5, 0, 5); 
	    terBlock.scale(50,1,30);
	   terBlock.translate(-875,-455,0);
	   terBlock.rotate(90, up); 
	   addGameWorldObject(terBlock);
	   */
	    
		HillHeightMap myHillHeightMap = new HillHeightMap(50, 500, 2.f, 10.0f,(byte)2, 12345);
		myHillHeightMap.setHeightScale(0.1f);

	    // Create the terrain block using the height map's size, and the origin
	    // specified above.
	    TerrainBlock terBlock = new TerrainBlock(name, myHillHeightMap.getSize(), terrainScale,
	      myHillHeightMap.getHeightData(), terrainOrigin);

	    // Create the texture for the terain.
	    mtnTex = TextureManager.loadTexture2D("textures/rocks.png");
	    mtnTex.setApplyMode(Texture.ApplyMode.Replace);

	    // Create the render state.
	    mtnTexState = (TextureState)DisplaySystem.getCurrentDisplaySystem()
	      .getRenderer().createRenderState(RenderState.RenderStateType.Texture);
	    mtnTexState.setTexture(mtnTex);
	    mtnTexState.setEnabled(true);
	    terBlock.setRenderState(mtnTexState);
	    
	    Vector3D up = new Vector3D(0,1,0);
	    
	//   terBlock.scale(5, 0, 5); 
	//    terBlock.scale(3,.1f,3);
	  
	    terBlock.scale(4f, 0.05f, 1.4f);
	    terBlock.translate(-230,0,-219);
	   terBlock.rotate(90, up); 
	   addGameWorldObject(terBlock);
		
}





private void createSagePhysicsWorld()
{
	
	// add the ball physics
	float mass = 1.0f;
	IPhysicsObject ballP = physicsEngine.addSphereObject(physicsEngine.nextUID(),
			mass, sphere.getWorldTransform().getValues(),1.0f);
	ballP.setBounciness(1.0f);
	
	sphere.setPhysicsObject(ballP);
	// add the ground plane physics
	float up[] = {-0.05f, 0.95f, 0}; // {0,1,0} is flat
	
//	IPhysicsObject groundPlaneP = physicsEngine.addSphereObject(physicsEngine.nextUID(),
//			mass, groundPlane.getWorldTransform().getValues(),1.0f);
//	groundPlaneP.setBounciness(1.0f);
//	groundPlane.setPhysicsObject(groundPlaneP);
// should also set damping, friction, etc.


}

protected void initPhysicsSystem()
{ 
	String engine = "sage.physics.JBullet.JBulletPhysicsEngine";
	physicsEngine = PhysicsEngineFactory.createPhysicsEngine(engine);
	physicsEngine.initSystem();
	float[] gravity = {0, -1f, 0};
	physicsEngine.setGravity(gravity);
}

public void removeGhost(UUID ghostID)
{
	
	boolean notFinished = true;
	
	while(notFinished)
	{
		
	for(int i = 0; i<ghostTeam.size(); i++)
	{
		
		
		boolean thisGhost = ghostTeam.get(i).getPlayerID().equals(ghostID);
		
		if(thisGhost)
		{
			tileGrid.getTileGrid()[ghostTeam.get(i).getGridI()][ghostTeam.get(i).getGridJ()].unOccupy();
			removeGameWorldObject(ghostTeam.get(i));
			removeGameWorldObject(ghostTeam.get(i).getLifeBar());
			ghostTeam.remove(i);
		
		}
		if(ghostTeam.size()==0)
		{
			notFinished = false;
		}

	}
	}
	
}

public void removeNPC(UUID ghostID, int cID)
{
	System.out.println(npcs.size());

	for(int i = 0; i<npcs.size(); i++)
	{
		
		
		boolean thisServer = npcs.get(i).getPlayerID().equals(ghostID);
		
		
		if(thisServer && (npcs.get(i).getCharacterID() == cID))
		{
			tileGrid.getTileGrid()[npcs.get(i).getGridI()][npcs.get(i).getGridJ()].unOccupy();
			removeGameWorldObject(npcs.get(i));
			removeGameWorldObject(npcs.get(i).getLifeBar());
			npcs.remove(i);
			break;
		}
		

	}
	
	
}


public void shutdown()
{
	
	super.shutdown();
	
	if(client!=null)
	{
		setIsConnected(false);
		client.sendByeMessage();
		try {
			wait(15);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	try
	{
		client.shutdown();
	}
	catch(IOException e)
	{
		e.printStackTrace();
	}


}

public void quitGame()
{	
	System.out.println("War (Working Title) Shutdown.");
	
	// First release sounds
	//npcSound.release(audioMgr);
	//resource1.unload();
	//audioMgr.shutdown();
	
	
	this.setGameOver(true);
	System.exit(0); 
}

public void byeMessage()
{
	
	client.sendByeMessage();

}

public void checkCameraTileDistance()
{
	Vector3D cursorObj = cursorObject.getLocalTranslation().getCol(3);
	Vector3D camera = cameraPointer.getLocalTranslation().getCol(3);
	
	double differenceX = cursorObj.getX() - camera.getX();
	double differenceZ = cursorObj.getZ() - camera.getZ();
	double zoom = cc1.getDistanceFromTarget();
	zoom = zoom/10;
	
/*	
	System.out.println("difference X: " + differenceX);
	System.out.println("Is Greater Than? : " + (16+(5*zoom)));
	System.out.println("Is less than? : " + (-(16+(5*zoom))));
	
	System.out.println("\ndifference Z: " + differenceZ);
	System.out.println("Is Great Than? : " + ((15+10*zoom)));
	System.out.println("Is less than? : " + ((0-(5*zoom))));
	
	
	if(differenceX >(16+(5*zoom)) || differenceX < -(16+(5*zoom)))
	{
		System.out.println("True!");
		return true;

	}
	else if(differenceZ >(15+(10*zoom)) || differenceZ <(0-(5*zoom)))
	{
		System.out.println("True!");
		return true;
	}
	else
	{
		return false;
	}
	*/
	
	if(cameraMovedYet = false)
	{
		cameraMovedYet = true;
	}
	
	if(differenceX > 15 || differenceX < -15)
	{
		if(differenceX>0)
		{
			goingLeftOrRight = true;
		}
		else
		{
			goingLeftOrRight = false;
		}
		
		moveCameraLeftRight = true;

	}
	else
	{
		moveCameraLeftRight = false;
	}
	
	if(differenceZ > 15 || differenceZ < -15)
	{
		if(differenceZ>0)
		{
			goingUpOrDown = true;
		}
		else
		{
			goingUpOrDown = false;
		}
		moveCameraUpDown = true;
	}
	else
	{
		moveCameraUpDown = false;
	}
	

	
	if(differenceZ >25 && differenceX > 25)
	{
		moveLeftAndUp = true;
	}
	else
	{
		moveLeftAndUp = false;
	}
	
	if(differenceZ > 25 && differenceX < -25)
	{
		moveRightAndUp = true;
	}
	else
	{
		moveRightAndUp = false;
	}

}


public boolean moveCameraUpDown()
{
	return moveCameraUpDown;
}

public boolean moveCameraLeftRight()
{
	return moveCameraLeftRight;
}

public void setMoveCameraUpDown(boolean b)
{
	moveCameraUpDown = b;
}

public void setMoveCameraLeftRight(boolean b)
{
	moveCameraLeftRight = b;
}


public boolean goingLeftOrRight()
{
	return goingLeftOrRight;
}

public void setGoingLeftOrRight(boolean b)
{
	 goingLeftOrRight = b;
}

public boolean goingUpOrDown()
{
	return goingUpOrDown;
}

public void setGoingUpOrDown(boolean b)
{
	goingUpOrDown = b;
}

public boolean getCameraMovedYet()
{
	return cameraMovedYet;
}



public boolean moveLeftAndUp()
{
	return moveLeftAndUp;
}

public boolean moveRightAndUp()
{
	return moveRightAndUp;
}
public void setMoveLeftAndUp(boolean b)
{
	moveLeftAndUp = b;
}
public void setMoveRightAndUp(boolean b)
{
	moveRightAndUp = b; 
}


public void createOneGrid(double offset)
{
	
	IRenderer renderer = DisplaySystem.getCurrentDisplaySystem().getRenderer();
	BlendState btransp = (BlendState) renderer.createRenderState(RenderStateType.Blend);
	btransp.setBlendEnabled(true);
	btransp.setEnabled(true);

	Color thisColor = Color.black;
	thisColor = getLineColor();
	
	//Create the game axis. Positive direction are dark colors, while light versions are negative axis
	Point3D originX = new Point3D(offset,gridY,0);
	
	Point3D originZ = new Point3D(0,gridY,offset);


	Point3D positiveX = new Point3D(width,gridY,offset);
	Point3D negativeX = new Point3D(-width,gridY,offset);
		
	Point3D positiveZ = new Point3D(offset,gridY,width);
	Point3D negativeZ = new Point3D(offset,gridY,-width);

	Line positiveXAxis = new Line(originZ, positiveX, thisColor, 1);
	positiveXAxis.setRenderState(btransp);
	positiveXAxis.updateRenderStates();
	positiveXAxis.setRenderMode(RENDER_MODE.TRANSPARENT);

	addGameWorldObject(positiveXAxis);
	grid.add(positiveXAxis);
	
	Line negativeXAxis = new Line(originZ, negativeX, thisColor, 1);
	negativeXAxis.setRenderState(btransp);
	negativeXAxis.updateRenderStates();
	negativeXAxis.setRenderMode(RENDER_MODE.TRANSPARENT);
	addGameWorldObject(negativeXAxis);
	grid.add(negativeXAxis);
	
	Line positiveZAxis = new Line(originX, positiveZ, thisColor, 1);
	positiveZAxis.setRenderState(btransp);
	positiveZAxis.updateRenderStates();
	positiveZAxis.setRenderMode(RENDER_MODE.TRANSPARENT);
	addGameWorldObject(positiveZAxis);
	grid.add(positiveZAxis);
	
	Line negativeZAxis = new Line(originX, negativeZ, thisColor, 1);
	negativeZAxis.setRenderState(btransp);
	negativeZAxis.updateRenderStates();
	negativeZAxis.setRenderMode(RENDER_MODE.TRANSPARENT);
	addGameWorldObject(negativeZAxis);
	grid.add(negativeZAxis);
}

public Color getLineColor()
{
	if(gridLineColor=="blue")
	{
		return Color.blue;
	}
	else if(gridLineColor=="green")
	{
		return Color.green;
	}
	else if(gridLineColor=="white")
	{
		//return Color.white;
		Color white = new Color(1f,1f,1f,0.3f);
		return white;
	}
	else if(gridLineColor=="red")
	{
		return Color.red;
	}
	else if(gridLineColor=="black")
	{
		return Color.black;
	}
	else if(gridLineColor=="purple")
	{
		Color purple = new Color(127,0,255);
		return purple;
	}
	else
	{
		return Color.blue;
	}
}



public void printCameraLocation(Point3D cameraLocation)
{
	//Print out camera information
	System.out.println("Current Camera Location- X: " + cameraLocation.getX() + ", Y: " + cameraLocation.getY() + ", Z: " + cameraLocation.getZ());

}

public Vector3D getCameraPointerPosition()
{

	return cameraPointer.getLocalTranslation().getCol(3);
}




public boolean isConnected()
{
	return isConnected;
}


public void setIsConnected(Boolean b)
{
	isConnected = b;
}


public ICamera getCamera()
{
	return camera1;
}

public Camera3P getCamera3P()
{
	return cc1;
}

public SkyBox getSkybox()
{
	return skybox1;
}

public MyCube returnCameraPointer()
{
	return cameraPointer;
}


public ArrayList<Line> getGridLines()
{
	return grid;
}

public ArrayList<SceneNode> getGroundPlaneTiles()
{
	return groundPlaneTiles;
}

public int returnWidth()
{
	return width;
}

public int returnGridWidth()
{
	return gridWidth;
}

public int returnOffest()
{
	return offset;
}

public double getCamera1DistanceFromTarget()
{
	return camera1DistanceFromTarget;
}

public double getCamera1Azimuth()
{
	return camera1Azimuth;
}

public double getCamera1Elevation()
{
	return camera1Elevation;
}

public double getMaxElevation()
{
	return maxElevation;
}

public double getMinElevation()
{
	return minElevation;
}

public double getMinZoom()
{
	return minZoom;
}

public double getMaxZoom()
{
	return maxZoom;
}

public int getScaleForwardBackwardKeyboard()
{
	return scaleForwardBackwardKeyboard;
}

public int getScaleLeftRightKeyboard()
{
	return scaleLeftRightKeyboard;
}

public int getScaleOrbitKeyboard()
{
	return scaleOrbitKeyboard;
	
}

public int getScaleZoom()
{
	return scaleZoom;
}

public int getScaleForwardBackwardGamepad()
{
	return scaleForwardBackwardGamepad;
}

public int getScaleLeftRightGamepad()
{
	return scaleLeftRightGamepad;
}

public int getScaleOrbitGamepad()
{
	return scaleOrbitGamepad;
}

public Client getClient()
{
	return client;
}

public String getServerAddress()
{
	return serverAddress;
}

public int serverPort()
{
	return serverPort();
}


public ArrayList<NPC> getNPCList()
{
	return npcs;
}

public ArrayList<GhostCharacter> getGhostAvatarList()
{
	return ghostTeam;
}

//used to generate a random integer
private static int randomInt(int min, int max) {
	Random random = new Random();
    int randomNum = random.nextInt((max - min) + 1) + min;
    return randomNum;
}

public float getTime() {
	return time;
}

public MyDisplaySystem getMyDisplaySystem()
{
	return myDisplay;
}

public IDisplaySystem getDisplay() {
	return display;
}

public boolean isFullScreenMode() {
	return isFullScreenMode;
}

public void setFullScreenMode(boolean isFullScreenMode) {
	this.isFullScreenMode = isFullScreenMode;
}

public int getServerPort() {
	return serverPort;
}

public ArrayList<GhostCharacter> getGhostTeam() {
	return ghostTeam;
}

public void setGhostAvatars(ArrayList<GhostCharacter> ghostAvatars) {
	this.ghostTeam = ghostAvatars;
}

public boolean isDynamicScriptOn() {
	return dynamicScriptOn;
}

public MyCube getCameraPointer() {
	return cameraPointer;
}


public Grid getGrid()
{
	return tileGrid;
}

public int getOffset() {
	return offset;
}

public ArrayList<gameWorldObjects.Character> getMyTeam()
{
	return myTeam;
}

public boolean isCharacterSelected()
{
	return characterSelected;
}


public void selectCharacter()
{
	
		characterSelected = true;
	//	gridY = gridYSelect;
	//	updateGridY();
		
		
		for(int q = 0; q<groundPlaneBlue.size(); q++)
		{
			groundPlaneBlue.get(q).setColorBuffer("blue");
		}
		

		for(int r = 0; r<myTeam.size(); r++)
		{

			myTeam.get(r).setShowLifeBar(true);
			myTeam.get(r).updateLifeBar();
		}
		for(int s = 0; s<ghostTeam.size(); s++)
		{
	
		ghostTeam.get(s).setShowLifeBar(true);
		ghostTeam.get(s).updateLifeBar();
		}
		for(int t = 0; t<npcs.size(); t++)
		{
		
		npcs.get(t).setShowLifeBar(true);
		npcs.get(t).updateLifeBar();
		}
		
		
		
		double x = 0;
		double z = 0;
		
		while(true)
		{
			for(int i = 0; i<myTeam.size(); i++)
			{
				if(((Character) myTeam.get(i)).isSelected())
				{
					x = myTeam.get(i).getLocalTranslation().getCol(3).getX();
					z = myTeam.get(i).getLocalTranslation().getCol(3).getZ();

					break;
				}
			}
			break;
		}
		
		
		
		
		cursorObject.setColorBuffer("white2");
		cursorObject.setTexture(cursorTextureTeam);
		hoveringCursorObject.setTexture(hoveringCursorTeam);
		
		
	}


public void deselectCharacter()
{

		characterSelected = false;
	//	gridY = gridYNoSelect;
	//	updateGridY();
		
		
		showGroundPlane = false;
		updateTransparentGroundPlane(0,0, 1, 57);
		
		for(int q = 0; q<groundPlaneBlue.size(); q++)
		{
			groundPlaneBlue.get(q).setColorBuffer("blue2");
		}
	

		for(int r = 0; r<myTeam.size(); r++)
		{
		
			myTeam.get(r).setShowLifeBar(false);
			myTeam.get(r).updateLifeBar();
		}
		for(int s = 0; s<ghostTeam.size(); s++)
		{
		
		ghostTeam.get(s).setShowLifeBar(false);
		ghostTeam.get(s).updateLifeBar();
		}
		for(int t = 0; t<npcs.size(); t++)
		{

		npcs.get(t).setShowLifeBar(false);
		npcs.get(t).updateLifeBar();
		}
		

		cursorObject.setColorBuffer("white");
		cursorObject.setTexture(cursorTextureClear);
		hoveringCursorObject.setTexture(hoveringCursorPointer);
		

		

}














/*

 
public void toggleCharacterSelected()
{
	if(characterSelected)
	{
		characterSelected = false;
	//	gridY = gridYNoSelect;
	//	updateGridY();
		
		
		showGroundPlane = false;
		updateTransparentGroundPlane(0,0, 1, 57);
		
		for(int q = 0; q<groundPlaneBlue.size(); q++)
		{
			groundPlaneBlue.get(q).setColorBuffer("blue2");
		}
	

		for(int r = 0; r<myTeam.size(); r++)
		{
		
			myTeam.get(r).setShowLifeBar(false);
			myTeam.get(r).updateLifeBar();
		}
		for(int s = 0; s<ghostTeam.size(); s++)
		{
		
		ghostTeam.get(s).setShowLifeBar(false);
		ghostTeam.get(s).updateLifeBar();
		}
		for(int t = 0; t<npcs.size(); t++)
		{

		npcs.get(t).setShowLifeBar(false);
		npcs.get(t).updateLifeBar();
		}
		

		cursorObject.setColorBuffer("white");
		cursorObject.setTexture(cursorTextureClear);
		hoveringCursorObject.setTexture(hoveringCursorPointer);
		

		
}
	else
	{
		characterSelected = true;
	//	gridY = gridYSelect;
	//	updateGridY();
		
		
		for(int q = 0; q<groundPlaneBlue.size(); q++)
		{
			groundPlaneBlue.get(q).setColorBuffer("blue");
		}
		

		for(int r = 0; r<myTeam.size(); r++)
		{
			myTeam.get(r).getLifeBar().setColorBuffer("lifeGreen");
			myTeam.get(r).setShowLifeBar(true);
			myTeam.get(r).updateLifeBar();
		}
		for(int s = 0; s<ghostTeam.size(); s++)
		{
		ghostTeam.get(s).getLifeBar().setColorBuffer("lifeGreen");	
		ghostTeam.get(s).setShowLifeBar(true);
		ghostTeam.get(s).updateLifeBar();
		}
		for(int t = 0; t<npcs.size(); t++)
		{
		npcs.get(t).getLifeBar().setColorBuffer("lifeGreen");	
		npcs.get(t).setShowLifeBar(true);
		npcs.get(t).updateLifeBar();
		}
		
		
		
		double x = 0;
		double z = 0;
		
		while(true)
		{
			for(int i = 0; i<myTeam.size(); i++)
			{
				if(((Character) myTeam.get(i)).isSelected())
				{
					x = myTeam.get(i).getLocalTranslation().getCol(3).getX();
					z = myTeam.get(i).getLocalTranslation().getCol(3).getZ();

					break;
				}
			}
			break;
		}
		
		
		
		
		cursorObject.setColorBuffer("white2");
		cursorObject.setTexture(cursorTextureTeam);
		hoveringCursorObject.setTexture(hoveringCursorTeam);
		
		
	}
}

*/

public void setAttackPossible(boolean b)
{
	attackPossible = b;
}

public MyHoveringCursorController getHoveringCursorController()
{
	return hoveringCursorController;
}

public boolean getAttackPossible()
{
	return attackPossible;
}

public void setMovePossible(boolean b)
{
	movePossible = b;
}

public IAudioManager getAudioManager()
{
	return audioMgr;
}

public boolean getMovePossible()
{
	return movePossible;
}

public double getMoveDistanceX()
{
	return moveDistanceX;
}

public double getMoveDistanceZ()
{
	return moveDistanceZ;
}

public Tile getCursorObject()
{
	return cursorObject;
}

public void printCursor()
{
	tileGrid.whereIsTheCursor();
}

public void setEarParameters()
{

/*
	Vector3D camDir = new Vector3D(0,0,1);
	
	audioMgr.getEar().setLocation(camera1.getLocation());
	audioMgr.getEar().setOrientation(camDir, new Vector3D(0,0,1));
	
//	System.out.println("Ear: " + audioMgr.getEar().getLocation());
	
	Point3D ear = audioMgr.getEar().getLocation();
	Point3D npc = npcSound.getLocation();
*/
	
	 Matrix3D avDir = (Matrix3D) (cursorObject.getLocalRotation());
	 float camAz = cc1.getAzimuth();
	 avDir.rotateY(180.0f-camAz);
	 Vector3D camDir = new Vector3D(0,0,1);
	 camDir = camDir.mult(avDir);
	 audioMgr.getEar().setLocation(camera1.getLocation());
	 audioMgr.getEar().setOrientation(camDir, new Vector3D(0,1,0));

	

}

public Model3DTriMesh returnHorse()
{
	return myHorse;
}

public boolean myTurn()
{
	return myTurn;
}


public void checkGameOver()
{

if(gameOver==false)
{
	if(playerOne)
	{
		while(true)
		{
			for(int i = 0; i<ghostTeam.size(); i++)
			{
				if(ghostTeam.get(i).getCharacterTypeName().matches("K"))
				{
					if(ghostTeam.get(i).isAlive())
					{
						
					}
					else
					{
						winner = true;
						gameOver = true;
					}
					break;
				}
			}
			break;
		}
		
		int alive = myTeam.size();
		
		for(int i = 0; i<myTeam.size(); i++)
		{
				if(myTeam.get(i).isAlive())
				{
				}
				else
				{
					alive--;
				}
		}
		
		if(alive==0)
		{
			gameOver = true;
			loser = true;
		}
		
		if(winner)
		{
			System.out.println("The King has been assassinated.");
		}
		else if(loser)
		{
			System.out.println("We lost the battle. The King lives.");
		}
		else
		{
			System.out.println("The King has not been terminated. We fight another day.");
		}
	}
	else
	{
		int alive = ghostTeam.size();
		
		for(int i = 0; i<ghostTeam.size(); i++)
		{
			if(ghostTeam.get(i).isAlive())
			{
				
			}
			else
			{
				alive--;
			}
		}
		
		if(alive==0)
		{
			gameOver = true;
			winner = true;
		}
		
		
		while(true)
		{
			for(int i = 0; i<myTeam.size(); i++)
			{
				if(myTeam.get(i).getCharacterTypeName().matches("K"))
				{
					if(myTeam.get(i).isAlive())
					{
						
					}
					else
					{
						gameOver = true;
						loser = true;
					}
					break;
				}
			}
			break;
		}
		
		
		if(winner)
		{
			System.out.println("We terminated the invaders your honor. Long live the King.");
		}
		else if(loser)
		{
			System.out.println("The darkest of days are ahead. The King lives in spirit.");
		}
		else
		{
			System.out.println("There are still " + alive + " invaders to murder! Defend the King's honor!");
		}
	}
}

	
}


}
	

