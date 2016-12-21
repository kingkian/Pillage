//Kian Faroughi
//Csc165 - Assignment 1
//Doctor Gordon
//CSUS Fall 2015

package m2;



import java.io.IOException;

import java.util.Scanner;
public class Starter {
	


	public static void main(String[] args) throws IOException
	{
		
		
		
		Scanner scanner = new Scanner(System.in);
		
		
		
		
		System.out.println("\n\nWelcome to War (Working Title). This is the Client Application.");
		
		
		
		
		System.out.println("\nPlease Enter the IP Address of the Server Application:");
		String serverAddress = scanner.nextLine();
		
	
		
		System.out.println("\nPlease enter the Server TCP Port Number:");
		int serverPort = scanner.nextInt();
	
		System.out.println("\nInfiltrate the Village or Defend the King? (Enter 1 for King's Warriors/Enter 2 for Invaders)");
		int playerOne  = scanner.nextInt();
	
		System.out.println("\nKeyboard or Gamepad? (Enter 1 for Keyboard/Enter 2 for Gamepad)");
		int keyBoard  = scanner.nextInt();
		
		
		
		if(playerOne==1)
		{
			if(keyBoard==1)
			{
				//MyGame game = new MyGame("192.168.0.210", serverPort, true, true);
						MyGame game = new MyGame(serverAddress,serverPort, true,true);
				game.start();
			}
			else
			{
				//MyGame game = new MyGame("192.168.0.210", serverPort, true, false);
						MyGame game = new MyGame(serverAddress,serverPort, true,false);
				game.start();
			}
		
			
		}
		else if(playerOne==2)
		{
			if(keyBoard==1)
			{
				MyGame game = new MyGame("192.168.0.210", serverPort, false, true);
				//		MyGame game = new MyGame(serverAddress,serverPort, true,true);
				game.start();
			}
			else
			{
				MyGame game = new MyGame("192.168.0.210", serverPort, false, false);
				//		MyGame game = new MyGame(serverAddress,serverPort, true,false);
				game.start();
			}
		}

		scanner.close();
		
		
		
		System.out.println();
		
		
		
		
		
		
		

	
	
}

	

}
