package gameWorldObjects.models;

import java.io.File;

import graphicslib3D.Matrix3D;
import sage.model.loader.OBJLoader;
import sage.scene.TriMesh;
import sage.texture.Texture;
import sage.texture.TextureManager;

public class GroundPlaneTile extends TriMesh {
	
	private TriMesh obj;
	
	public GroundPlaneTile()
	{
		OBJLoader loader = new OBJLoader();
		loader.setShowWarnings(false);
		
		
		obj = loader.loadModel("gameWorldObjects"+File.separator+"models"+File.separator+"groundPlane.obj");
	//	Texture texture = TextureManager.loadTexture2D("groundPlaneTexture.png");
	//	obj.setTexture(texture);
		
		
		
		obj.updateLocalBound();
		obj.updateGeometricState(0, true);
		
		obj.scale(7.0f, 0, 7.0f);
		

	}
	
	public TriMesh getObj(){
		return obj;
	}
	
	public void setTexture(Texture t)
	{
		obj.setTexture(t);
	}
	
}
