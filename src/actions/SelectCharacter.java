//Kian Faroughi
//Csc165 - Assignment 1
//Doctor Gordon
//CSUS Fall 2015
//Event class used by keyboard key to move backward


package actions;

import m2.Camera3P;
import m2.MyGame;

import java.util.ArrayList;

import client.Client;
import gameWorldObjects.Character;
import gameWorldObjects.Tile;
import graphicslib3D.Matrix3D;
import graphicslib3D.Point3D;
import graphicslib3D.Vector3D;
import net.java.games.input.Event;
import sage.camera.ICamera;
import sage.input.action.AbstractInputAction;
import sage.scene.Model3DTriMesh;
import sage.scene.SceneNode;
import sage.scene.TriMesh;

public class SelectCharacter extends AbstractInputAction {
	
	private ICamera camera;
	private Camera3P cc1;
	private SceneNode cameraBox;
	
	private int width;
	private int offset;
	private int max;
	
	private Tile cursorObject;
	
	private Client client;
	private MyGame game;
	private ArrayList<gameWorldObjects.Character> myTeam;
	private ArrayList<gameWorldObjects.GhostCharacter> ghostTeam;
	private ArrayList<gameWorldObjects.NPC> npcs;


	private Model3DTriMesh myHorse;
	
	public SelectCharacter(MyGame game)
	{
			this.game = game;
			camera = game.getCamera();
			cursorObject = game.getCursorObject();
			this.width = game.returnWidth();
			this.offset = game.returnOffest();
			max = width - (offset/2);
			this.client = game.getClient();
			this.cc1 = game.getCamera3P();
	
			myTeam = game.getMyTeam();
			ghostTeam = game.getGhostTeam();
			npcs = game.getNPCList();
			
			myHorse = game.returnHorse();
	}
	
	public void performAction(float time, Event event)
	{	
		
		Vector3D cursorObjPosition = cursorObject.getLocalTranslation().getCol(3);
		double cursorObjX = cursorObjPosition.getX();
		double cursorObjZ = cursorObjPosition.getZ();

		
if(game.myTurn())
{
		
	
		if(game.isCharacterSelected())
		{	
			if(game.getMovePossible())
			{
				
				gameWorldObjects.Character selectedCharacter = (Character) myTeam.get(0);
				
				double selectedCharacterX = 0;
				double selectedCharacterZ = 0;
					
				while(true)
				{
						for(int i = 0; i<myTeam.size(); i++)
						{
							if(((Character) myTeam.get(i)).isSelected())
							{
								selectedCharacter = (Character) myTeam.get(i);
								selectedCharacterX = selectedCharacter.getLocalTranslation().getCol(3).getX();
								selectedCharacterZ = selectedCharacter.getLocalTranslation().getCol(3).getZ();
								break;
							}
						}
						break;
				}
					
				double currentDistanceX = cursorObjX - selectedCharacterX;
				double currentDistanceZ = cursorObjZ - selectedCharacterZ;
				if(currentDistanceX < 0)
				{
					currentDistanceX = currentDistanceX*-1;
				}
				if(currentDistanceZ < 0)
				{
					currentDistanceZ = currentDistanceZ*-1;
				}
				
		
				
				if(currentDistanceX>5 || currentDistanceZ>5)
				{
						
								float newX = 0;
								float newZ = 0;
								double currentX = selectedCharacter.getLocalTranslation().getCol(3).getX();
								double currentZ = selectedCharacter.getLocalTranslation().getCol(3).getZ();
								
								if(cursorObjX>selectedCharacter.getLocalTranslation().getCol(3).getX())
								{
									newX = (float)game.getMoveDistanceX() + 5;
								}
								else
								{
									newX =  (float)-(game.getMoveDistanceX()+5);
								}
								if(cursorObjZ>selectedCharacter.getLocalTranslation().getCol(3).getZ())
								{
									newZ = (float)game.getMoveDistanceZ()+5;
								}
								else
								{
									newZ = (float)-(game.getMoveDistanceZ()+5);
								}
								
								int x = (int)newX/10;
								int z = (int)newZ/10;
	
								
								
								int gridI = selectedCharacter.getGridI();
								int gridJ = selectedCharacter.getGridJ();
								boolean houseOnX = false;
								boolean houseOnZ = false;
								
								
								boolean personOnX = false;
								boolean personOnZ = false;
								
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
											if((gridJ-j)>=0)
											{
											if(game.getGrid().getTileGrid()[gridI][gridJ-j].isCovered())
											{
												houseOnX = true;
											}
											else if(game.getGrid().getTileGrid()[(gridI)][gridJ-j].isOccupied())
											{
												houseOnX = true;
											}
											}
											else if(game.getGrid().getTileGrid()[(gridI-offset)][(gridJ-xx)].isOccupied())
											{
												houseOnX = true;
											}
										}
										else
										{
											if(game.getGrid().getTileGrid()[(gridI)][gridJ+j].isCovered())
											{
												houseOnX = true;
											}
											else if(game.getGrid().getTileGrid()[(gridI)][gridJ+j].isOccupied())
											{
												houseOnX = true;
											}
											else if(game.getGrid().getTileGrid()[(gridI-offset)][(gridJ+xx)].isOccupied())
											{
												houseOnX = true;
											}
										}
									}
								}
								
								
								if(houseOnX)
								{
									
								
									
									selectedCharacter.getMyMoveController().makeMove(x, z, false);
									selectedCharacter.setMoveUsed(true);
								
									
									

									
									
									
									
								}
								else
								{
										
									
								selectedCharacter.getMyMoveController().makeMove(x, z, true);
								selectedCharacter.setMoveUsed(true);
								
								
								
								}
								

							
								
								game.getGrid().getTileGrid()[gridI][gridJ].unOccupy();
							
								
								
								
								gridI -= z;
								gridJ -= x;
								
								
								
								
								
								
								
								game.getGrid().getTileGrid()[gridI][gridJ].occupy(selectedCharacter.getCharacterTypeName());
								selectedCharacter.setGridIJ(gridI, gridJ);
								selectedCharacter.setLocation(game.getGrid().getTileGrid()[gridI][gridJ].getX(), selectedCharacter.getY(), game.getGrid().getTileGrid()[gridI][gridJ].getZ());
								
								
								
								
						
								int selectedCharacterMaxAttack = (int)selectedCharacter.getMaxAttack()/10;
								int selectedCharacterGridI = selectedCharacter.getGridI();
								int selectedCharacterGridJ = selectedCharacter.getGridJ();
								
								
								boolean possibleAttack = false;
								
							
								
								for(int checkAttack = 1; checkAttack<selectedCharacterMaxAttack+1; checkAttack++)
								{
								
									
								
									
									while(true)
									{
									for(int i = 0; i<ghostTeam.size(); i++)
									{
										
	
										
										if((selectedCharacterGridI+checkAttack)==ghostTeam.get(i).getGridI() && selectedCharacterGridJ==ghostTeam.get(i).getGridJ())
										{
											possibleAttack = true;
											break;
										}
										else if((selectedCharacterGridI-checkAttack)==ghostTeam.get(i).getGridI() && selectedCharacterGridJ==ghostTeam.get(i).getGridJ())
										{
											possibleAttack = true;
											break;
										}
										else if((selectedCharacterGridI)==ghostTeam.get(i).getGridI() && (selectedCharacterGridJ+checkAttack)==ghostTeam.get(i).getGridJ())
										{
											possibleAttack = true;
											break;
										}
										else if((selectedCharacterGridI)==ghostTeam.get(i).getGridI() && (selectedCharacterGridJ-checkAttack)==ghostTeam.get(i).getGridJ())
										{
											possibleAttack = true;
											break;
										}
									}
									break;
									}
									
									
									if(!possibleAttack)
									{
										
										while(true)
										{
												for(int i = 0; i<npcs.size(); i++)
												{
											
													if((selectedCharacterGridI+1)==npcs.get(i).getGridI() && selectedCharacterGridJ==npcs.get(i).getGridJ())
													{
														possibleAttack = true;
														break;
													}
													else if((selectedCharacterGridI-1)==npcs.get(i).getGridI() && selectedCharacterGridJ==npcs.get(i).getGridJ())
													{
														possibleAttack = true;
														break;
													}
													else if((selectedCharacterGridI)==npcs.get(i).getGridI() && (selectedCharacterGridJ+1)==npcs.get(i).getGridJ())
													{
														possibleAttack = true;
														break;
													}
													else if((selectedCharacterGridI)==npcs.get(i).getGridI() && (selectedCharacterGridJ-1)==npcs.get(i).getGridJ())
													{
														possibleAttack = true;
														break;
													}
												}
													break;
										}
									}
							}
								
								
								
								if(possibleAttack)
								{
								//	System.out.println("An attack is available.");
				
								}
								else
								{
									selectedCharacter.setAttackUsed(true);
								//	System.out.println("Nobody to attack in the region");
									
								}
								
								
								
								
								client.sendMoveMessage(selectedCharacter.getCharacterID(), gridI, gridJ);
			
								
								selectedCharacter.toggleSelected();
							
								game.deselectCharacter();
								
								
								game.checkTurn();
								

				}
				else if(currentDistanceX<=5 && currentDistanceZ<=5)
				{
					
								selectedCharacter.toggleSelected();
								selectedCharacter.setMoveUsed(true);
								game.deselectCharacter();
								
							
								

								Vector3D avatarPosition = cursorObject.getLocalTranslation().getCol(3);
								double avatarX = avatarPosition.getX();
								double avatarZ = avatarPosition.getZ();
								int selectedCharacterMaxAttack = (int)selectedCharacter.getMaxAttack()/10;
								int selectedCharacterGridI = selectedCharacter.getGridI();
								int selectedCharacterGridJ = selectedCharacter.getGridJ();
								
								
								boolean possibleAttack = false;
								for(int checkAttack = 1; checkAttack<selectedCharacterMaxAttack+1; checkAttack++)
								{
								
									while(true)
									{
									for(int i = 0; i<ghostTeam.size(); i++)
									{
										if(selectedCharacterGridI+checkAttack<=63)
										{
										
										if((selectedCharacterGridI+checkAttack)==ghostTeam.get(i).getGridI() && selectedCharacterGridJ==ghostTeam.get(i).getGridJ())
										{
											possibleAttack = true;
											break;
										}
										}
										if((selectedCharacterGridI-checkAttack)==ghostTeam.get(i).getGridI() && selectedCharacterGridJ==ghostTeam.get(i).getGridJ())
										{
											possibleAttack = true;
											break;
										}
										
										if(selectedCharacterGridJ+checkAttack<=63)
										{
										if((selectedCharacterGridI)==ghostTeam.get(i).getGridI() && (selectedCharacterGridJ+checkAttack)==ghostTeam.get(i).getGridJ())
										{
											possibleAttack = true;
											break;
										}
										}
										if((selectedCharacterGridI)==ghostTeam.get(i).getGridI() && (selectedCharacterGridJ-checkAttack)==ghostTeam.get(i).getGridJ())
										{
											possibleAttack = true;
											break;
										}
									}
									break;
									}
									
									if(!possibleAttack)
									{
									while(true)
									{
									for(int i = 0; i<npcs.size(); i++)
									{
										
										if(selectedCharacterGridI+checkAttack<=63)
										{
										if((selectedCharacterGridI+checkAttack)==npcs.get(i).getGridI() && selectedCharacterGridJ==npcs.get(i).getGridJ())
										{
											possibleAttack = true;
											break;
										}
										}
										if((selectedCharacterGridI-checkAttack)==npcs.get(i).getGridI() && selectedCharacterGridJ==npcs.get(i).getGridJ())
										{
											possibleAttack = true;
											break;
										}
										
										if(selectedCharacterGridJ+checkAttack<=63)
										{
										if((selectedCharacterGridI)==npcs.get(i).getGridI() && (selectedCharacterGridJ+checkAttack)==npcs.get(i).getGridJ())
										{
											possibleAttack = true;
											break;
										}
										}
										if((selectedCharacterGridI)==npcs.get(i).getGridI() && (selectedCharacterGridJ-checkAttack)==npcs.get(i).getGridJ())
										{
											possibleAttack = true;
											break;
										}
									}
									break;
									}
									}
								}
								
								
								if(possibleAttack)
								{
								//	System.out.println("An attack is available.");
				
								}
								else
								{
									selectedCharacter.setAttackUsed(true);
								//	System.out.println("Nobody to attack in the region");
								}
								
								
								
								
								game.checkTurn();
							
				}
					
			}
			
			
			else if(game.getAttackPossible())
			{		
				
				gameWorldObjects.Character selectedCharacter = myTeam.get(0);
				
					while(true)
					{
							for(int i = 0; i<myTeam.size(); i++)
							{
								if(myTeam.get(i).isSelected())
								{
									selectedCharacter = myTeam.get(i);
									break;
								}
							}
							break;
					}
				
				
				
					gameWorldObjects.GhostCharacter ghostCharacter;
					double ghostCharacterX = 0;
					double ghostCharacterZ = 0;
						
					double currentDistanceX;
					double currentDistanceZ;
					
					boolean found = false;
					
					while(true)
					{
							for(int i = 0; i<ghostTeam.size(); i++)
							{
								ghostCharacterX = ghostTeam.get(i).getX();
								ghostCharacterZ = ghostTeam.get(i).getZ();
								
								currentDistanceX = cursorObjX - ghostCharacterX;
								currentDistanceZ = cursorObjZ - ghostCharacterZ;
								
								if(currentDistanceX < 0)
								{
									currentDistanceX = currentDistanceX*-1;
								}
								if(currentDistanceZ < 0)
								{
									currentDistanceZ = currentDistanceZ*-1;
								}
								
						
								if(currentDistanceX<=5 && currentDistanceZ<=5)
								{
									ghostCharacter = ghostTeam.get(i);
									boolean killed = ghostCharacter.takeHit(selectedCharacter.getAttack());
									
									if(killed)
									{
										
										int attack = selectedCharacter.getAttack();
										attack+=5;
										selectedCharacter.setAttack(attack);
								

										
										client.sendUpdateAttackMessage(selectedCharacter.getCharacterID(), selectedCharacter.getAttack());
										
									}
									
									found = true;
									
									
									
									client.sendAttackMessage(ghostCharacter.getCharacterID(), selectedCharacter.getAttack());
									
									selectedCharacter.setAttackUsed(true);
									
									
									selectedCharacter.setMoveUsed(true);
									
									
									selectedCharacter.toggleSelected();
									game.deselectCharacter();
									
									game.checkTurn();
									
									break;
								}
							}
							break;
					}
					
					
					if(!found)
					{
						
						
						gameWorldObjects.NPC thenpc;
						double thenpcX = 0;
						double thenpcZ = 0;
						
						
						while(true)
						{
								for(int i = 0; i<npcs.size(); i++)
								{
									thenpcX = npcs.get(i).getX();
									thenpcZ = npcs.get(i).getZ();
									
									currentDistanceX = cursorObjX - thenpcX;
									currentDistanceZ = cursorObjZ - thenpcZ;
									
									if(currentDistanceX < 0)
									{
										currentDistanceX = currentDistanceX*-1;
									}
									if(currentDistanceZ < 0)
									{
										currentDistanceZ = currentDistanceZ*-1;
									}
									
							
									if(currentDistanceX<=5 && currentDistanceZ<=5)
									{
										thenpc = npcs.get(i);
										boolean killed = thenpc.takeHit(selectedCharacter.getAttack());
										
										if(killed)
										{
										
											int attack = selectedCharacter.getAttack();
											attack+=10;
											selectedCharacter.setAttack(attack);
									
											
											client.sendUpdateAttackMessage(selectedCharacter.getCharacterID(), selectedCharacter.getAttack());
											
										}
										
										selectedCharacter.setAttackUsed(true);
										
										selectedCharacter.setMoveUsed(true);
										
										client.sendAttackNPCMessage(thenpc.getCharacterID(), selectedCharacter.getAttack());
										
										
										
										selectedCharacter.toggleSelected();
										game.deselectCharacter();
										
										game.checkTurn();
										
										break;
									}
								}
								break;
						}
					}
			}
			
		}
		
		


		
				
			else
			{
				boolean found = false;
			while(true)
			{
				for(int i = 0; i<myTeam.size(); i++)
				{
					
					
					if(cursorObjX > (myTeam.get(i).getLocalTranslation().getCol(3).getX() - 5) && cursorObjX < (myTeam.get(i).getLocalTranslation().getCol(3).getX() + 5))
					{
						if(cursorObjZ > (myTeam.get(i).getLocalTranslation().getCol(3).getZ() - 5) && cursorObjZ < (myTeam.get(i).getLocalTranslation().getCol(3).getZ() + 5))
						{
							
							if(myTeam.get(i).moveUsed()==false || myTeam.get(i).attackUsed()==false)
							{
							
								((Character) myTeam.get(i)).toggleSelected();
								game.selectCharacter();
								found = true;
							}
						
						break;
						}
					}
				
				}
				break;
			}

			
			}
		

	}

	}

}

