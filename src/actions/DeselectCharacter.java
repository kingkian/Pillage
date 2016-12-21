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

public class DeselectCharacter extends AbstractInputAction {
	

	private int width;
	private int offset;

	
	private Tile cursorObject;
	

	private MyGame game;
	private ArrayList<gameWorldObjects.Character> myTeam;



	private Model3DTriMesh myHorse;
	
	public DeselectCharacter(MyGame game)
	{
			this.game = game;
	
			cursorObject = game.getCursorObject();
			this.width = game.returnWidth();
			this.offset = game.returnOffest();
	
			myTeam = game.getMyTeam();

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
					

		
				
			
								selectedCharacter.toggleSelected();
								game.deselectCharacter();
			}
		
	}
}

}