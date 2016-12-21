 package server;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;

import gameWorldObjects.Grid;
import graphicslib3D.Vector3D;
import sage.networking.server.GameConnectionServer;
import sage.networking.server.IClientInfo;



public class Server extends GameConnectionServer<UUID>
{


private ArrayList<UUID> currentClients = new ArrayList<UUID>();

	private NPCcontroller npcController;
	private long startTime, lastUpdateTime;
	private UUID id;
	
	int myTurn = 0;
	

	public Server(int localPort) throws IOException{
		super(localPort, ProtocolType.TCP);
		System.out.println("\nServer Created.\nServer IP Address: " + Inet4Address.getLocalHost().getHostAddress() + "\nServer Port: " + localPort);
	
		startTime = System.nanoTime();
		lastUpdateTime = startTime;
		id = UUID.randomUUID();
		
		npcController = new NPCcontroller(id);

		npcLoop();

	}
	
	
	public void npcLoop() // NPC control loop
	 { 
		while (true)
		{ 
			long frameStartTime = System.nanoTime();
			float elapMilSecs = (frameStartTime-lastUpdateTime)/(1000000.0f);
			if (elapMilSecs >= 1800.0f)
			{ 
				lastUpdateTime = frameStartTime;
		//		npcController.update();
		//		updateNPCInfo();
				
				
	
	 }
	 Thread.yield();
	 } }

	
	
	public void acceptClient(IClientInfo ci, Object o){
		String message = (String) o;
		String[] messageTokens = message.split(",");
		
		if(messageTokens.length>0){
			if(messageTokens[0].compareTo("join")==0){
				
				if(currentClients.size()<2)
				{
			
				UUID clientID = UUID.fromString(messageTokens[1]);
				boolean addClient = true;
				
				
				
				for(int i =0; i<currentClients.size();i++)
				{
					if(clientID==currentClients.get(i))
							{
						addClient = false;
							}
					else
					{
						
					}
				}
				if(addClient)
				{
				currentClients.add(clientID);
				addClient(ci, clientID);
				sendJoinedMessage(clientID,true);
				createClientNPC(clientID);
				
				if(currentClients.size()==1)
				{
				//	requestHouses(clientID);
				}
				
				
				
				
				}
				
			}
				else
				{
					System.out.println("Somebody is trying to join a full game.");
				}
				
				
			}
		}
	}
	
	public void processPacket(Object o, InetAddress senderIP, int sndPort){
	
		super.processPacket(o, senderIP,  sndPort);
		
		if(o!=null)
		{
		String message = (String) o;
		
		if(message!=null)
		{
		String[] msgTokens = message.split(",");
	
		
			if(msgTokens.length>0){
				
				UUID clientID = UUID.fromString(msgTokens[1]);
			
				
				
				if(msgTokens[0].compareTo("bye")==0){
					
				//	npcController.removeTextGhosts(clientID);
					
					sendByeMessages(clientID);
					removeClient(clientID);
					
					while(true)
					{
						for(int i = 0; i<currentClients.size(); i++)
						{
							boolean thisClient = currentClients.get(i).equals(clientID);
							
							if(thisClient)
							{
								currentClients.remove(i);
								System.out.println("\nServer: A Player Has Disconnected From the Session.");								
								break;
							}
						}
						break;
						
					}
					
					if(currentClients.size()==0)
					{
						/*
						System.out.println("All of the clients have disconnected from the session. Would you like to shutdown the server? (y/n)");
						Scanner scanner = new Scanner(System.in);
						String s = scanner.nextLine();
						scanner.close();
						if(s.charAt(0)=='y' || s.charAt(0)=='Y')
						{
							try {
								this.shutdown();
								System.exit(0);
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						else if(s.charAt(0)=='n' || s.charAt(0)=='N')
						{
							System.out.println("\nThe Server Will Remain Running.");
						}
						else
						{
							System.out.println("\nDid not understand the input. Will continue running server.");
						}
						*/
						
						System.out.println("\nAll clients have closed their connections. The server is going to shutdown...");
						try {
							this.shutdown();
							System.out.println("...Server Shutdown Succesffully.");
							System.exit(0);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					else
					{
						System.out.println("There are still " + currentClients.size() + " clients connected to the session.");

					}
					
					

				}
				
				else if(msgTokens[0].compareTo("create")==0){
					
				
					int characterID = Integer.parseInt(msgTokens[2]);
					String characterType = msgTokens[3];
					int gridI = Integer.parseInt(msgTokens[4]);
					int gridJ = Integer.parseInt(msgTokens[5]);
					
					npcController.createTextGhost(clientID, characterID, characterType, gridI, gridJ);
					
					sendCreateMessages(clientID, characterID, characterType, gridI, gridJ);
			
				}
				
				else if(msgTokens[0].compareTo("attack")==0){
					
					
					int characterID = Integer.parseInt(msgTokens[2]);
					int attack = Integer.parseInt(msgTokens[3]);
					
					
					
			
					
					sendAttackMessages(clientID, characterID, attack);
			
				}
				

				else if(msgTokens[0].compareTo("npcAttack")==0){
					
					
					int characterID = Integer.parseInt(msgTokens[2]);
					int attack = Integer.parseInt(msgTokens[3]);
					
					
					
			
					
					sendNPCAttackMessages(clientID, characterID, attack);
			
				}
				
				
				else if(msgTokens[0].compareTo("updateAttack")==0){
					
					
					int characterID = Integer.parseInt(msgTokens[2]);
					int attack = Integer.parseInt(msgTokens[3]);
					
				
					
			
					
					sendUpdateAttackMessages(clientID, characterID, attack);
			
				}

				else if(msgTokens[0].compareTo("yourTurn")==0){
					
					myTurn++;
					
					if(myTurn == 2)
					{
					
						npcController.update();
						
						try {
						    Thread.sleep(5000);                 //1000 milliseconds is one second.
						} catch(InterruptedException ex) {
						    Thread.currentThread().interrupt();
						}	
						
						
						updateNPCInfo();
						myTurn = 0;
						System.out.println("NPC Turn");
						
						
						try {
						    Thread.sleep(3000);                 //1000 milliseconds is one second.
						} catch(InterruptedException ex) {
						    Thread.currentThread().interrupt();
						}
						
					}
					
				
					
				
				
					
			
					
					sendTurnMessages(clientID);
			
				}
				
				else if(msgTokens[0].compareTo("move")==0){

					
					int characterID = Integer.parseInt(msgTokens[2]);
					int gridI = Integer.parseInt(msgTokens[3]);
					int gridJ = Integer.parseInt(msgTokens[4]);
					
					npcController.updateTextGhost(clientID, characterID, gridI, gridJ);
					
					sendMoveMessages(clientID, characterID, gridI, gridJ);
				}
				
				
				else if(msgTokens[0].compareTo("house")==0){
					
					
					
					int gridI = Integer.parseInt(msgTokens[2]);
					int gridJ = Integer.parseInt(msgTokens[3]);
					
					npcController.coverTile(gridI, gridJ);
					
				
					
					
					
					
					
				}
				
			
				
			}
		}
	}
}



	private void sendCreateMessages(UUID clientID, int cID, String cType, int gI, int gJ) {
		try{
			String message = new String("create," + clientID);
			message += "," + cID;
			message += "," + cType;
			message += "," + gI;
			message += "," + gJ;

			
			forwardPacketToAll(message, clientID);
		}catch(IOException e){e.printStackTrace();}
	}

	private void sendMoveMessages(UUID clientID, int cID, int gI, int gJ){
		try{
			String message = new String("move," + clientID);
			message += "," + cID;
			message += "," + gI;
			message += "," + gJ;

		//	System.out.println("Message Forwarded to All Clients: " + message);
			
			forwardPacketToAll(message, clientID);
		}catch(IOException e){e.printStackTrace();}
	}
	
	public void sendAttackMessages(UUID clientID, int cID, int attack){
		try{
		
			String message = new String("attack,"+id.toString());
			
			message += ","+ cID;
			
			message += ","+ attack;
					
			forwardPacketToAll(message, clientID );
		}
		catch(IOException e){e.printStackTrace();}
		
	
	}
	
	public void sendTurnMessages(UUID clientID){
		try{
		
			String message = new String("yourTurn,"+id.toString());
			
					
			forwardPacketToAll(message, clientID );
		}
		catch(IOException e){e.printStackTrace();}
		
		
	
	}
	
	public void sendUpdateAttackMessages(UUID clientID, int cID, int attack){
		try{
		
			String message = new String("updateAttack,"+id.toString());
			
			message += ","+ cID;
			
			message += ","+ attack;
					
			forwardPacketToAll(message, clientID );
		}
		catch(IOException e){e.printStackTrace();}
		

	}
	
	public void sendNPCAttackMessages(UUID clientID, int cID, int attack){
		try{
		
			String message = new String("npcAttack,"+id.toString());
			
			message += ","+ cID;
			
			message += ","+ attack;
					
			forwardPacketToAll(message, clientID );
		}
		catch(IOException e){e.printStackTrace();}
	}
	
	private void updateNPCInfo()
	{
		for(int i = 0; i<npcController.getNPCs().size(); i++)
		{
			
		
		try{
			String message = new String("moveNPC," + id);
			message += "," + npcController.getNPCs().get(i).getCharacterID();
			message += "," + npcController.getNPCs().get(i).getGridI();
			message += "," + npcController.getNPCs().get(i).getGridJ();

		//	System.out.println("Message Forwarded to All Clients: " + message);
			
			forwardPacketToAll(message, id);
		}catch(IOException e){e.printStackTrace();}
		
		}
	}
	
	
	private void requestHouses(UUID clientID)
	{
		try{
			String message = new String("houses," + id);			
			sendPacket(message, clientID);
		}catch(IOException e){e.printStackTrace();}
	}
	
	private void createClientNPC(UUID clientID)
	{
		for(int i = 0; i<npcController.getNPCs().size(); i++)
		{
			
		
		try{
			String message = new String("createNPC," + id);
			message += "," + npcController.getNPCs().get(i).getCharacterID();
			message += "," + npcController.getNPCs().get(i).getCharacterTypeName();
			message += "," + npcController.getNPCs().get(i).getGridI();
			message += "," + npcController.getNPCs().get(i).getGridJ();

		//	System.out.println("Message Forwarded to All Clients: " + message);
			
			sendPacket(message, clientID);
		}catch(IOException e){e.printStackTrace();}
		
		}
	}
	
	
	private void sendJoinedMessage(UUID clientID, boolean success) {
		try{
			String message = new String("join,");
			if(success) 
				{
				message += "success";
				sendPacket(message,clientID);
				
				System.out.println("\nServer: A New Client Has Joined the Session as Player " + currentClients.size() + ".\nClientID: " + clientID);
				
				
				String notifyJoin = new String("newcomer");
				forwardPacketToAll(notifyJoin, clientID);
				
				
				}
			else
				message += "failure";
			sendPacket(message,clientID);
		}catch(IOException e) {e.printStackTrace();}
		
	}
	
	private void sendByeMessages(UUID clientID){
		
				String message = new String("bye," + clientID);
				
				
				try{
				
				forwardPacketToAll(message, clientID);
				
				}catch(IOException e){e.printStackTrace();}
				
	}
	
	private void sendByeNPCMessages(UUID clientID){
		
		for(int i = 0; i<npcController.getNPCs().size(); i++)
		{
		String message = new String("byeN," + clientID);
		
		message += "," + npcController.getNPCs().get(i).getCharacterID();

		
		try{
		
		forwardPacketToAll(message, clientID);
		
		}catch(IOException e){e.printStackTrace();}
		
		}
		
}
	
	private void sendWantsDetailsMessages(UUID clientID) {
		// TODO Auto-generated method stub
		
	}
	
	private void sndDetailsMsg(UUID clientID, UUID remoteID, String[] position){
		
	}

	


	public static void main(String[] args) throws IOException
	{
		
		
		
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("\nWelcome to War (Working Title). This is the server application.\n\nPlease enter the port number you wish to use for the TCP connection:");
		
		int port = scanner.nextInt();
		scanner.close();
		
		Server server = new Server(port);
		
		
		
		
	}

}
