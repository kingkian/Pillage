package physics;

import net.java.games.input.Event;
import sage.input.action.AbstractInputAction;

public class StartPhysics extends AbstractInputAction{

	private PhysicsBase physics;
	
	public StartPhysics(PhysicsBase physics){
		this.physics = physics;
	}
	
	
	public void performAction(float arg0, Event arg1) {
		physics.setRunning(true);
		physics.setForces();
	}

}
