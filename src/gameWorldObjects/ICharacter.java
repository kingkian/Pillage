package gameWorldObjects;

import helpers.MyMoveController;

public interface ICharacter {
	
	
	public double getMaxAttack();
	public int getAttack();
	public void setAttack(int newAttack);
	public double getLife();
	public void setLife(double newLife);
	public double getMaxMove();
	public MyMoveController getMyMoveController();
	public void toggleShowLifeBar();
	public void setShowLifeBar(boolean b);
	public String getCharacterTypeName();
	public void setLocation(double x, double y, double z);
	public void setGridIJ(int i, int j);
	public void setGridI(int i);
	public void setGridJ(int j);
	public double getX();
	public double getY();
	public double getZ();
	public int getCharacterID();
	public void finishMove();
	public boolean attackUsed();
	public boolean moveUsed();
	public boolean takeHit(int attackDamage);
	public void finishTakingHit();
	public boolean isAlive();
	public void setAlive(boolean b);
	public void die();
	public boolean isKing();
	public void updateLifeBar();
//	public void initAudio();
//	public void playMoveSound();
//	public void playAttackSound();
//	public void playSelectSound();

	

}
