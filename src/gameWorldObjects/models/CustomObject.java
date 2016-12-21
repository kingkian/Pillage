package gameWorldObjects.models;

import java.io.File;

import graphicslib3D.Matrix3D;
import sage.model.loader.OBJLoader;
import sage.scene.TriMesh;
import sage.texture.Texture;
import sage.texture.TextureManager;

public class CustomObject extends TriMesh {
	
	private TriMesh obj;
	
	public CustomObject(String s){
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
			obj = loader.loadModel("gameWorldObjects"+File.separator+"models"+File.separator+"Castle_Fian.obj");
		    Texture texture = TextureManager.loadTexture2D("textures/castle_Texture.jpg");
			obj.setTexture(texture);
		}
		
		else if(s.equals("house"))
		{
			obj = loader.loadModel("gameWorldObjects"+File.separator+"models"+File.separator+"Fian_House.obj");
			Texture texture = TextureManager.loadTexture2D("textures/kian_house.jpg");
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
		

		obj.updateLocalBound();
		obj.updateGeometricState(0, true);
		
		
		

	}
	
	public TriMesh getObj(){
		return obj;
	}
	
}
