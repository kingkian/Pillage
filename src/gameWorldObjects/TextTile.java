package gameWorldObjects;

import graphicslib3D.Point3D;

public class TextTile {
	
	private boolean isOccupied;
	private boolean isCovered;
	private boolean isSelected;
	private String tennant;
	private double x, y, z;
	private int gridI, gridJ;
	
	public TextTile(double x, double y, double z, int i, int j)
	{
		isSelected = false;
		isOccupied = false;
		isCovered = false;
		tennant = "0";
		this.x = x;
		this.y = y;
		this.z = z;
		gridI = i;
		gridJ = j;
	}
	
	public String toString()
	{
		return tennant;
	}
	
	public void unOccupy()
	{
		if(isOccupied)
		{
			isOccupied = false;
			tennant = "0";
		}
		else
		{
			System.out.println("Nobody Here to kick out.");
		}
	}
	
	public void occupy(String s)
	{
		if(isOccupied==false)
		{
			isOccupied = true;
			tennant = s;
		}
		else
		{
			System.out.println("Already somebody here!");
		}
	}
	
	public void cover(String s)
	{
		if(isCovered==false)
		{
			isCovered = true;
		
			tennant = s;
		}
		else
		{
			System.out.println("Already covered!");
		}
		
	}
	
	public void setLocation(double x, double y, double z)
	{
		this. x = x;
		this.y = y;
		this.z = z;
	}
	
	public double getX()
	{
		return x;
	}
	
	public double getY()
	{
		return y;
	}
	
	public double getZ()
	{
		return z;
	}
	
	public int getGridI()
	{
		return gridI;
	}
	
	public int getGridJ()
	{
		return gridJ;
	}
	
	public boolean isSelected()
	{
		return isSelected;
	}
	
	public void setIsSelected(boolean b)
	{
		isSelected = b;
	}
	public boolean isOccupied()
	{
		return isOccupied;
	}
	
	public boolean isCovered()
	{
		return isCovered;
	}
	

}
