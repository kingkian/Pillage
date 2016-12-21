//Kian Faroughi
//Csc165 - Game Project
//Doctor Gordon
//CSUS Fall 2015

//Controller Class for third person controller

package m2;

import graphicslib3D.Matrix3D;
import graphicslib3D.Point3D;
import graphicslib3D.Vector3D;
import sage.camera.ICamera;
import sage.scene.SceneNode;
import sage.scene.TriMesh;
import sage.util.MathUtils;

public class Camera3P
{
 private ICamera cam; //the camera being controlled

 private SceneNode cameraPointer; //the target the camera looks at
 
 private float cameraAzimuth; //rotation of camera around target Y axis
 private float cameraElevation; //elevation of camera above target
 private float cameraDistanceFromTarget;
 private Point3D cameraPointerPos; // avatar’s position in the world
 private Vector3D worldUpVec;
 private Point3D desiredLocation;
 private Point3D idealLocation;
 private MyGame game;
 
 private double offsetX = 0;
 private double offsetZ = 0;
 
 public Camera3P(MyGame game)
 { 
	 this.cam = game.getCamera();
	 this.game = game;
	 this.cameraPointer = game.returnCameraPointer();
	 
	 worldUpVec = new Vector3D(0,1,0);
	 
	 cameraDistanceFromTarget = (float)game.getCamera1DistanceFromTarget();
	 cameraAzimuth = (float)game.getCamera1Azimuth(); // start from BEHIND and ABOVE the target
	 cameraElevation = (float)game.getCamera1Elevation(); // elevation is in degrees

	 updateCameraPointerPosition();
	 updateCameraPosition();
	 cam.lookAt(cameraPointerPos,  worldUpVec);
	 
	 
 }
 
 
 public void update(float time)
 {
 game.updateSkyboxes();
 updateCameraPointerPosition();
 updateCameraPosition();
 game.updateSkyboxes();
 game.render();
 //Built in Sage Function
 cam.lookAt(cameraPointerPos, worldUpVec); 
 }
 
 
 private void updateCameraPointerPosition()
 { 
	 cameraPointerPos = new Point3D(cameraPointer.getWorldTranslation().getCol(3));
 }
 
 
 private void updateCameraPosition()
 {
 double theta = cameraAzimuth;
 double phi = cameraElevation ;
 double r = cameraDistanceFromTarget;

 // calculate new camera position in Cartesian Coordinates
 Point3D relativePosition = MathUtils.sphericalToCartesian(theta, phi, (r));
 Point3D desiredCameraLoc = relativePosition.add(cameraPointerPos);
 

 
// Point3D desiredCameraLoc = relativePosition.add();
 idealLocation = relativePosition;
 desiredLocation = desiredCameraLoc;
 //desiredLocation = finalDesiredCameraLoc;
  
 cam.setLocation(desiredCameraLoc);

 }
 
 
 public Point3D getDesiredLocation()
 {
	 return desiredLocation;
 }
 
 public float getAzimuth()
 {

		 
	 return cameraAzimuth;
 }
 

 
 public float getElevation()
 {
	 return cameraElevation;
 }
 
 public float getDistanceFromTarget()
 {
	 return cameraDistanceFromTarget;
 }
 
 
 public void setAzimuth(float az)
 {
	 cameraAzimuth = az;
 }
 
 public void setElevation(float el)
 {
	 cameraElevation = el;
 }
 
 public void setDistanceFromTarget(float dst)
 {
	 cameraDistanceFromTarget = dst;
 }
 
 public void resetCamera()
 {
	 cam.setLocation(idealLocation);
 }
 
 public double getOffsetX()
 {
	 return offsetX;
 }
 
 public double getOffsetZ()
 {
	 return offsetZ;
 }
 
 public void setOffsetX(double x)
 {
	 offsetX = x;
 }
 
 public void setOffsetZ(double z)
 {
	 offsetZ = z;
 }
 
}