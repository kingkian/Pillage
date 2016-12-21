package physics;

import javax.vecmath.Quat4f;
import javax.vecmath.Vector3f;

import com.bulletphysics.collision.broadphase.AxisSweep3;
import com.bulletphysics.collision.broadphase.BroadphaseInterface;
import com.bulletphysics.collision.dispatch.CollisionConfiguration;
import com.bulletphysics.collision.dispatch.CollisionDispatcher;
import com.bulletphysics.collision.dispatch.DefaultCollisionConfiguration;
import com.bulletphysics.collision.shapes.CollisionShape;
import com.bulletphysics.collision.shapes.SphereShape;
import com.bulletphysics.collision.shapes.StaticPlaneShape;
import com.bulletphysics.dynamics.DiscreteDynamicsWorld;
import com.bulletphysics.dynamics.DynamicsWorld;
import com.bulletphysics.dynamics.RigidBody;
import com.bulletphysics.dynamics.RigidBodyConstructionInfo;
import com.bulletphysics.dynamics.constraintsolver.ConstraintSolver;
import com.bulletphysics.dynamics.constraintsolver.SequentialImpulseConstraintSolver;
import com.bulletphysics.linearmath.DefaultMotionState;
import com.bulletphysics.linearmath.Transform;

import gameWorldObjects.MySphere;
import graphicslib3D.Matrix3D;
import graphicslib3D.Vector3D;
import sage.camera.ICamera;
import sage.scene.SceneNode;
import sage.scene.shape.Rectangle;
import sage.scene.shape.Sphere;

public class PhysicsBase {
	 private ICamera camera;
	 private Rectangle graphicalGroundPlane;
	 private SceneNode graphicalBall;
	 private SceneNode player;
	 private boolean running;
	 private Vector3D playerLoc;

	private BroadphaseInterface broadPhaseHandler;
	private int maxProxies = 1024;
	private Vector3f worldAabbMin = new Vector3f(-10000, -10000, -10000);
	private Vector3f worldAabbMax = new Vector3f(10000, 10000, 10000);
	private DynamicsWorld physicsWorld;
	private ConstraintSolver solver;
	private CollisionConfiguration collConfig;
	private RigidBody physicsGround;
	private CollisionDispatcher collDispatcher;
	private RigidBody physicsBall;
	

	
	
	
	public PhysicsBase(SceneNode player, SceneNode ball){
		{ 
			this.player = player;
			graphicalBall = ball;
			Transform myTransform ;
		 // define the broad-phase collision to be used (Sweep-and-Prune)
			broadPhaseHandler = new AxisSweep3(worldAabbMin, worldAabbMax, maxProxies);
		 
		// set up the narrow-phase collision handler ("dispatcher")
		 collConfig = new DefaultCollisionConfiguration();
		 collDispatcher = new CollisionDispatcher(collConfig);
		
		 // create a constraint solver
		solver = new SequentialImpulseConstraintSolver();
		 
		// create a physics world utilizing the above objects
		 physicsWorld = new DiscreteDynamicsWorld(collDispatcher,
		 broadPhaseHandler, solver, collConfig);
		
		 physicsWorld.setGravity(new Vector3f(0, -10, 0));
		 
		 // define physicsGround plane: normal vector = 'up', dist from origin = 1
		 
		 CollisionShape groundShape = new StaticPlaneShape(new Vector3f(0, 1, 0), 1);
	
		 // set position and orientation of physicsGround's transform
		 myTransform = new Transform();
		 myTransform.origin.set(new Vector3f(0, -1, 0));
		 myTransform.setRotation(new Quat4f(0, 0, 0, 1));
		 
		 // define construction info for a 'physicsGround' rigid body
		 DefaultMotionState groundMotionState =
		 new DefaultMotionState(myTransform);
		 RigidBodyConstructionInfo groundCI = new RigidBodyConstructionInfo(0,
		 groundMotionState, groundShape,new Vector3f(0, 0, 0));
		 groundCI.restitution = 0.8f;
		
		 // create the physicsGround rigid body and add it to the physics world
		 physicsGround = new RigidBody(groundCI);
		 physicsWorld.addRigidBody(physicsGround);
		 
		 // define a collision shape for a physicsBall
		 CollisionShape fallShape = new SphereShape(1);
		 
		 // define a transform for position and orientation of ball collision shape
		 myTransform = new Transform();
		 myTransform.origin.set(new Vector3f(0, 20, 0));
		 myTransform.setRotation(new Quat4f(0, 0, 0, 1));
		 
		 // define the parameters of the collision shape
		 DefaultMotionState fallMotionState =
		 new DefaultMotionState(myTransform);
		 float myFallMass = 1;
		 Vector3f myFallInertia = new Vector3f(0,0,0);
		 fallShape.calculateLocalInertia(myFallMass, myFallInertia);
		 
		 // define construction info for a 'physicsBall' rigid body
		 RigidBodyConstructionInfo fallRigidBodyCI = new
		 RigidBodyConstructionInfo(myFallMass,fallMotionState,fallShape,myFallInertia);
		 fallRigidBodyCI.restitution = 0.8f;
		 
		 // create the physicsBall rigid body and add it to the physics world
		 physicsBall = new RigidBody(fallRigidBodyCI);

		 
		 physicsWorld.addRigidBody(physicsBall);
		 
		 }
		// other methods as in SAGE example.
		// for example, to create the graphics objects and scen
	}
	
	public void update(float time){
		 if (running)
		 { physicsWorld.stepSimulation(1.0f / 60.0f, 8); // 1/60th sec, 8 steps
		 // read and display the updated physicsBall position
		 
		 
		 Transform pBallTransform = new Transform();
		 physicsBall.getMotionState().getWorldTransform(pBallTransform);
		 //update the graphics ball location from the physics ball
		 float[] vals = new float[16];
		 pBallTransform.getOpenGLMatrix(vals);
		 Matrix3D gBallXform = new Matrix3D(vals);
		 graphicalBall.setLocalTranslation(gBallXform);

		 }
	}
	
	public void setRunning(boolean isRunning){
		running = isRunning;
	}
	
	public void setForces(){
		Vector3D change = player.getLocalTranslation().getCol(3).minus(graphicalBall.getLocalTranslation().getCol(3));
	//	change.normal();
		physicsBall.activate();
		physicsBall.applyCentralImpulse(new Vector3f((float)change.getX(),5f,(float)change.getZ()));
	}
}
