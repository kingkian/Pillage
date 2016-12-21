package client;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.UUID;
import java.util.Vector;

import m2.MyGame;
import graphicslib3D.Vector3D;
import sage.networking.client.GameConnectionClient;

public class Client extends GameConnectionClient{
	
	private MyGame game;
	private UUID id;
	private Vector ghostAvatars;
	
	private ArrayList<UUID> otherPlayers;
	private ArrayList<UUID> npcServers;

	public Client(InetAddress remoteAddr, int remotePort, ProtocolType protocolType, MyGame game) throws IOException {
		super(remoteAddr, remotePort, protocolType);
		this.game = game;
		id = UUID.randomUUID();
		ghostAvatars = new Vector();
		
		otherPlayers = new ArrayList<UUID>();
		npcServers = new ArrayList<UUID>();
	}
	
	protected void processPacket(Object msg){
		
		
		String message = (String) msg;
		String[] msgTokens = message.split(",");
	
	/*	UUID ghostID = UUID.fromString(msgTokens[1]);
		
		String[] pos = {msgTokens[2], msgTokens[3], msgTokens[4]};
		String[] cameraPos = {msgTokens[5], msgTokens[6], msgTokens[7]};
		String[] cameraRot = {msgTokens[8], msgTokens[9], msgTokens[10]};
	*/	
		
		
		if(msgTokens[0].compareTo("join")==0)
		{
			if(msgTokens[1].compareTo("success")==0)
			{
				
				if(game.isConnected()==false)
				{
				game.setIsConnected(true);
				
				for(int i = 0; i<game.getMyTeam().size(); i++)
				{
			    sendCreateMessage(game.getMyTeam().get(i).getCharacterID(), game.getMyTeam().get(i).getCharacterTypeName(), game.getMyTeam().get(i).getGridI(), game.getMyTeam().get(i).getGridJ());
			    sendUpdateAttackMessage(game.getMyTeam().get(i).getCharacterID(), game.getMyTeam().get(i).getAttack());
				}
			    System.out.println("\nJoined the Game Successfully.\n");
	
				}
			}
			if(msgTokens[1].compareTo("failure")==0){
				
				game.setIsConnected(false);
				System.out.println("Could Not Join the Game.\n");
			}	
		}
		
		
		else if(msgTokens[0].compareTo("bye")==0)
		{
			
			UUID ghostID = UUID.fromString(msgTokens[1]);
			removeGhostAvatar(ghostID);
		}
		
		if(msgTokens[0].compareTo("newcomer")==0)
		{
			for(int i = 0; i<game.getMyTeam().size(); i++)
			{
		    sendCreateMessage(game.getMyTeam().get(i).getCharacterID(), game.getMyTeam().get(i).getCharacterTypeName(), game.getMyTeam().get(i).getGridI(), game.getMyTeam().get(i).getGridJ());
			sendUpdateAttackMessage(game.getMyTeam().get(i).getCharacterID(), game.getMyTeam().get(i).getAttack());
			}
		}
		
		
		
		else if(msgTokens[0].compareTo("create")==0){

			
			UUID playerID = UUID.fromString(msgTokens[1]);
			int characterID = Integer.parseInt(msgTokens[2]);
			String characterType = msgTokens[3];
			int gridI = Integer.parseInt(msgTokens[4]);
			int gridJ = Integer.parseInt(msgTokens[5]);
	

			if(playerID!=id)
			{
				boolean addMe = true;
				
				for(int i = 0; i<otherPlayers.size(); i++)
				{
					boolean thisGhost = otherPlayers.get(i).equals(playerID);
					
					if(thisGhost)
					{
						addMe = false;
					}
					
				}
				
				if(addMe)
				{
				
					otherPlayers.add(playerID);
				}
				createGhostCharacter(playerID, characterID, characterType, gridI, gridJ);
			}
			
		}
		
				
		else if(msgTokens[0].compareTo("move")==0){


			UUID playerID = UUID.fromString(msgTokens[1]);
			int characterID = Integer.parseInt(msgTokens[2]);
			int gridI = Integer.parseInt(msgTokens[3]);
			int gridJ = Integer.parseInt(msgTokens[4]);
			
			if(playerID!=id)
			{
			game.updateGhostCharacter(playerID, characterID, gridI, gridJ);
			}
		}
		
		else if(msgTokens[0].compareTo("attack")==0){


			UUID playerID = UUID.fromString(msgTokens[1]);
			int characterID = Integer.parseInt(msgTokens[2]);
			int attack = Integer.parseInt(msgTokens[3]);
			
			
			if(playerID!=id)
			{
			game.ghostAttack(characterID, attack);
			}
		}
		else if(msgTokens[0].compareTo("updateAttack")==0){


			UUID playerID = UUID.fromString(msgTokens[1]);
			int characterID = Integer.parseInt(msgTokens[2]);
			int attack = Integer.parseInt(msgTokens[3]);
			
			
			
			if(playerID!=id)
			{
			game.updateGhostAttack(characterID, attack);
			}
		}
		else if(msgTokens[0].compareTo("npcAttack")==0){


			UUID playerID = UUID.fromString(msgTokens[1]);
			int characterID = Integer.parseInt(msgTokens[2]);
			int attack = Integer.parseInt(msgTokens[3]);
			
			
			if(playerID!=id)
			{
			game.npcAttack(characterID,  attack);
			}
		}
		
		else if(msgTokens[0].compareTo("houses")==0)
		{
			game.sendHouses();
		}
		
		else if(msgTokens[0].compareTo("createNPC")==0){

			
			UUID playerID = UUID.fromString(msgTokens[1]);
			int characterID = Integer.parseInt(msgTokens[2]);
			String characterType = msgTokens[3];
			int gridI = Integer.parseInt(msgTokens[4]);
			int gridJ = Integer.parseInt(msgTokens[5]);
	

			if(playerID!=id)
			{
				boolean addMe = true;
				
				for(int i = 0; i<npcServers.size(); i++)
				{
					boolean thisGhost = npcServers.get(i).equals(playerID);
					
					if(thisGhost)
					{
						addMe = false;
					}
					
				}
				
				if(addMe)
				{
				
					npcServers.add(playerID);
				}
				createNPC(playerID, characterID, characterType, gridI, gridJ);
			}
			
		}
		else if(msgTokens[0].compareTo("yourTurn")==0){
			

			UUID playerID = UUID.fromString(msgTokens[1]);

			
			
			
			if(playerID!=id)
			{
			game.makeMyTurn();
			}
		}
				
		else if(msgTokens[0].compareTo("moveNPC")==0){


			UUID playerID = UUID.fromString(msgTokens[1]);
			int characterID = Integer.parseInt(msgTokens[2]);
			int gridI = Integer.parseInt(msgTokens[3]);
			int gridJ = Integer.parseInt(msgTokens[4]);
			
			if(playerID!=id)
			{
			game.updateNPC(playerID, characterID, gridI, gridJ);
			}
		}
		
		else if(msgTokens[0].compareTo("byeN")==0)
		{
	
			UUID ghostID = UUID.fromString(msgTokens[1]);
			int characterID = Integer.parseInt(msgTokens[2]);

			removeNPC(ghostID, characterID);
		}
	}
	

	
	
	
	private void createGhostCharacter(UUID ghostID, int cId, String cType, int gI, int gJ)
	{
		game.createGhostCharacter(ghostID, cId, cType, gI, gJ);
		
	}

	private void removeGhostAvatar(UUID ghostID) {
		game.removeGhost(ghostID);
		
	}

	private void createNPC(UUID ghostID, int cId, String cType, int gI, int gJ)
	{
		game.createNPC(ghostID, cId, cType, gI, gJ);
		
	}

	private void removeNPC(UUID ghostID, int cID) {
		game.removeNPC(ghostID, cID);
		
	}
	
	public void sendCreateMessage(int cID, String cType, int gI, int gJ){
		try{
			String message = new String("create,"+ id.toString());
		 
			message += "," + cID;
			
			message += ","+ cType;
			
			message += ","+ gI;
			
			message += ","+ gJ;
			
			sendPacket(message);

		}
		catch(IOException e){e.printStackTrace();}
	}
	
	public void sendHouseMessage(int gI, int gJ)
	{
		try{
			String message = new String("house,"+ id.toString());
			
			message += ","+ gI;
			
			message += ","+ gJ;
			
			sendPacket(message);

		}
		catch(IOException e){e.printStackTrace();}
	}
	
	public void sendMoveMessage(int cID, int gI, int gJ){
			try{
			
				String message = new String("move,"+id.toString());
				
				message += ","+ cID;
				
				message += ","+ gI;
				
				message += ","+ gJ;
						
				sendPacket(message);
			}
			catch(IOException e){e.printStackTrace();}
		}
	
	
	public void sendAttackMessage(int cID, int attack){
		try{
		
			String message = new String("attack,"+id.toString());
			
			message += ","+ cID;
			
			message += ","+ attack;
					
			sendPacket(message);
		}
		catch(IOException e){e.printStackTrace();}
	}
	
	public void sendUpdateAttackMessage(int cID, int attack){
		try{
		
			String message = new String("updateAttack,"+id.toString());
			
			message += ","+ cID;
			
			message += ","+ attack;
					
			sendPacket(message);
			
			
		}
		catch(IOException e){e.printStackTrace();}
	}
	
	
	
	
	public void sendAttackNPCMessage(int cID, int attack){
		try{
		
			String message = new String("npcAttack,"+id.toString());
			
			message += ","+ cID;
			
			message += ","+ attack;
					
			sendPacket(message);
		}
		catch(IOException e){e.printStackTrace();}
	}
	
	public void sendYourTurn()
	{
		try{
		String message = new String("yourTurn,"+id.toString());
		sendPacket(message);
		
		
		}
		catch(IOException e){e.printStackTrace();}
		
		
		
	}
	
	
	
	public void sendJoinMessage(){
		try{
			sendPacket(new String("join,"+id.toString()));
		}catch(IOException e){e.printStackTrace();}
	}

	public void sendByeMessage(){
			
		try{
			sendPacket(new String("bye,"+id.toString()));
			this.shutdown();
		}catch(IOException e){e.printStackTrace();}
	}
		
	
	

	
	
}
