package gameWorldObjects.models;

import java.io.File;

import graphicslib3D.Matrix3D;
import sage.model.loader.OBJLoader;
import sage.scene.TriMesh;
import sage.texture.Texture;
import sage.texture.TextureManager;

public class House extends TriMesh {
	
	private TriMesh obj;
	
	public House() {
		OBJLoader loader = new OBJLoader();
		loader.setShowWarnings(false);
		
		
		obj = loader.loadModel("gameWorldObjects"+File.separator+"models"+File.separator+"house.obj");
		Texture texture = TextureManager.loadTexture2D("textures/house.png");
		obj.setTexture(texture);
		
	
		obj.scale(5,5,10);
		obj.updateLocalBound();
		obj.updateGeometricState(0, true);

		Matrix3D translate = obj.getLocalTranslation();
		translate.translate(15,15,-5);
		obj.setLocalTranslation(translate);
		
		

	}
	
	public TriMesh getObj(){
		return obj;
	}
	
}
