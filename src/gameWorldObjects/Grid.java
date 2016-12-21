package gameWorldObjects;

import sage.display.DisplaySystem;
import sage.renderer.IRenderer;
import sage.scene.SceneNode.RENDER_MODE;
import sage.scene.state.BlendState;
import sage.scene.state.RenderState.RenderStateType;

public class Grid {
	
	private TextTile[][] tileGrid = new TextTile[64][64];
	
	public Grid()
	{
		
	
		for(int i = 0; i<64; i++)
		{
			for(int j = 0; j<64; j++)
			{
				TextTile textTile = new TextTile(315 - (j*10), 5, (315 - (i*10)), i, j);
				tileGrid[i][j] = textTile;
			}
		}
	}
	
	public TextTile[][] getTileGrid()
	{
		return tileGrid;
	}
	
	public void currentMap()
	{
		System.out.println("=============================================================================================================================");
		
		for(int i = 0; i<64; i++)
		{
			String s = "=";
			for(int j = 0; j<64; j++)
			{
				
				s += " " + tileGrid[i][j].toString();
			}
			s += " =";
			System.out.println(s);
		}
		
		System.out.println("==============================================================================================================================");

	}
	
	public void currentMapLocations()
	{
		System.out.println("==============================================================================================================================");
		
		for(int i = 0; i<64; i++)
		{
			String s = "=";
			for(int j = 0; j<64; j++)
			{
				
				s += " (" + tileGrid[i][j].getX() + ", " + tileGrid[i][j].getY() + ", " + tileGrid[i][j].getZ() + ")";
			}
			s += " =";
			System.out.println(s);
		}
		
		System.out.println("=========================================================================================================================================================================================");

	}
	
	public void currentGrid()
	{
		System.out.println("===================================================================");
		
		for(int i = 0; i<64; i++)
		{
			String s = "=";
			for(int j = 0; j<64; j++)
			{
				
				s += " (" + tileGrid[i][j].getGridI() +  ", " + tileGrid[i][j].getGridJ() + ")";
			}
			s += " =";
			System.out.println(s);
		}
		
		System.out.println("===================================================================");

	}
	
	public void whereIsTheCursor()
	{
		System.out.println("====================================================================================================================");
		
		for(int i = 0; i<64; i++)
		{
			String s = "=";
			for(int j = 0; j<64; j++)
			{
				if(tileGrid[i][j].isSelected())
				{
					s += " Y";
				}
				else
				{
					s += " N";
				}
			}
			s += " =";
			System.out.println(s);
		}
		
		System.out.println("=======================================================================================================================");

	}
	

}
