package helpers;

import gameWorldObjects.GhostCharacter;
import gameWorldObjects.ICharacter;
import gameWorldObjects.NPC;
import gameWorldObjects.Tile;
import gameWorldObjects.Character;
import graphicslib3D.Matrix3D;
import graphicslib3D.Vector3D;
import sage.scene.Controller;
import sage.scene.SceneNode;

public class MyLifeBarController extends Controller {
	

private boolean takeHit;
private double damageAmount;
private double lifeBarX;
private double beforeLife, afterLife, duringHitLife;
private Tile lifeBar;
private ICharacter character;

Matrix3D initialScale;
	
	public MyLifeBarController(Tile lifeBar, ICharacter character)
	{
		super();
		this.addControlledNode(lifeBar);
		this.lifeBar = lifeBar;
		takeHit = false;
		lifeBarX = lifeBar.getLocalScale().getCol(0).getX();

		
		initialScale = lifeBar.getLocalScale();
		
		
		beforeLife = character.getLife();
		afterLife = character.getLife();
		this.character = character;
	}


	public void update(double time)
	{
		
		
		
if(takeHit)
{


	
	double normalizeTime = time/70;
			
	
	damageAmount = damageAmount - normalizeTime;
	duringHitLife = duringHitLife - normalizeTime;
	
	if(damageAmount<0)
	{
		damageAmount = 0;
	}
		
			Matrix3D testScale = new Matrix3D();
			double newScale = ((duringHitLife/100)*lifeBarX);
			
			newScale = newScale/lifeBar.getLocalScale().getCol(0).getX();
			
			
			
			if(newScale>0)
			{
			lifeBar.scale((float)newScale, 1f,1f);
			}
			else if(afterLife<=0)
			{	
					
					
					if(character instanceof GhostCharacter)
					{
						((GhostCharacter) character).die();
					}
					else if(character instanceof Character)
					{
						((Character) character).die();
					}
					else if(character instanceof NPC)
					{
						((NPC) character).die();
					}
					
					
			}
			
	
			
			character.updateLifeBar();
			
			
			/*
			Matrix3D newScaleMate = initialScale;
			
			newScaleMate.concatenate(testScale);

			
			lifeBar.setLocalScale(newScaleMate);
			*/
			
			
		
			if(damageAmount == 0)
			{
				
				
		
				takeHit = false;
				duringHitLife = afterLife;
				
				newScale = ((duringHitLife/100)*lifeBarX);
				newScale = newScale/lifeBar.getLocalScale().getCol(0).getX();
				lifeBar.scale((float)newScale, 1f,1f);
								
				
				character.setLife(afterLife);
		
				
				
			
			
				
				
				
				
			}
			
			
		
		}
		
	}
	
	public boolean takeHit(int damageAmount)
	{
		
		if(takeHit)
		{
			return false;
		}
		else
		{
		takeHit = true;
		
		this.damageAmount = damageAmount;
		beforeLife = character.getLife();
		duringHitLife = beforeLife;
		afterLife = beforeLife - damageAmount;

		
		
		if(afterLife<=0)
		{
			return true;
		}
		else
		{
			return false;
		}
		
		
		
		
		}
		
	}
	


}
