package keyboardMovement;


import m2.Camera3P;
import m2.MyGame;
import client.Client;
import net.java.games.input.Event;
import sage.input.action.AbstractInputAction;
import sage.scene.SceneNode;

public class OrbitZoomOutAction extends AbstractInputAction {
	 
	private Camera3P cc;
	private int scaleSpeed;
	private float minZoom;
	private float maxZoom;
	private SceneNode cameraPointer;
	private float f;
	private MyGame game;
	private Client client;

	
	int x = 0;
	
	public OrbitZoomOutAction(MyGame game)
	{
			this.game = game;
			cc = game.getCamera3P();
			this.scaleSpeed = game.getScaleZoom();
			this.minZoom = (float)game.getMinZoom();
			this.maxZoom = (float)game.getMaxZoom();
			this.cameraPointer = game.returnCameraPointer();
			this.client = game.getClient();
			
	}
	
	public void performAction(float time, Event evt)
	 {
		scaleSpeed = game.getScaleZoom();
		maxZoom = (float)game.getMaxZoom();
		
		float normalizeTime = time/(scaleSpeed);
	
	
		

	 f = cc.getDistanceFromTarget();
	 if(f<maxZoom)
	 {
	 cc.setDistanceFromTarget(f+normalizeTime);
	cc.update(time);
	
	 }
	
	 
} 
	
} 