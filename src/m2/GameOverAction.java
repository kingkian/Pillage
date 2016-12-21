//Kian Faroughi
//Csc165 - Assignment 2
//Doctor Gordon
//CSUS Fall 2015
//Class is an Action Object to Quit the Game

package m2;


import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import client.Client;
import net.java.games.input.Event;
import sage.camera.ICamera;
import sage.input.action.AbstractInputAction;

public class GameOverAction extends AbstractInputAction {
	
	private ICamera camera;
	private MyGame myGame;
	private Client client;
	
	int x = 0;
	
	public GameOverAction(MyGame game)
	{
			camera = game.getCamera();
			myGame = game;
			this.client = game.getClient();
	}
	
	public void performAction(float time, Event event)
	{
		if(event.getComponent().toString().matches("Escape"))
		{
			
		myGame.getMyDisplaySystem().getMyFrame().dispatchEvent(new WindowEvent(myGame.getMyDisplaySystem().getMyFrame(), WindowEvent.WINDOW_CLOSING));
		}
		else
		{
			myGame.byeMessage();
			myGame.quitGame();
		}
		
		

	}
	
}
		