package gameWorldObjects.models;

import java.io.File;

import graphicslib3D.Matrix3D;
import sage.model.loader.OBJLoader;
import sage.scene.TriMesh;
import sage.texture.Texture;
import sage.texture.TextureManager;

public class Arrow extends TriMesh {
	
	private TriMesh obj;
	
	public Arrow(String s){
		OBJLoader loader = new OBJLoader();
		loader.setShowWarnings(false);
		
		if(s.equals("chair"))
		{
		obj = loader.loadModel("gameWorldObjects"+File.separator+"models"+File.separator+"table.obj");
		Texture texture = TextureManager.loadTexture2D("textures/chairUVLayout2.jpg");
		obj.setTexture(texture);
		}
		else if(s.equals("adventurer"))
		{
			obj = loader.loadModel("gameWorldObjects"+File.separator+"models"+File.separator+"Adventurer.obj");
			Texture texture = TextureManager.loadTexture2D("textures/AdventurerTexture.jpg");
			obj.setTexture(texture);
		}
		else if(s.equals("castle"))
		{
			obj = loader.loadModel("gameWorldObjects"+File.separator+"models"+File.separator+"Castle.obj");
		//	Texture texture = TextureManager.loadTexture2D("textures/brick2.jpg");
		//	obj.setTexture(texture);
		}
		
		else if(s.equals("house"))
		{
			obj = loader.loadModel("gameWorldObjects"+File.separator+"models"+File.separator+"house.obj");
			Texture texture = TextureManager.loadTexture2D("textures/house.png");
			obj.setTexture(texture);
		}
		else if(s.equals("mhouse"))
		{
			obj = loader.loadModel("gameWorldObjects"+File.separator+"models"+File.separator+"House2_1Upload.obj");
		
		}
		else
		{
			obj = loader.loadModel("gameWorldObjects"+File.separator+"models"+File.separator+"Adventurer.obj");
			Texture texture = TextureManager.loadTexture2D("textures/AdventurerTexture.jpg");
			obj.setTexture(texture);
		}
		Matrix3D translate = obj.getLocalTranslation();
		translate.translate(15,15,-5);
		obj.setLocalTranslation(translate);
		obj.scale(5,5,5);
		obj.updateLocalBound();
		obj.updateGeometricState(0, true);
		
		
		

	}
	
	public TriMesh getObj(){
		return obj;
	}
	
}
