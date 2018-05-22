package io.network.client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import io.network.Network;

public class Client extends Thread {

	/**
	 * @author Florian
	 */
	
	
	
	/**
	 * @param IP; Saves the IP-Address.
	 */
	public InetAddress IP;
	
	/**
	 * @param Port; Saves the Server-Address Port.
	 */
	public int Port;
	
	/**
	 * @param Socket; Saves the DatagramSocket.
	 */
	private DatagramSocket Socket;
	
	/**
	 * @param ID; Is a generated UUID to identificate the Client.
	 */
	public String ID = UUID.randomUUID().toString();
	
	/**
	 * @param Network; Saves the Network interface.
	 */
	private Network Network;

	/**
	 * @param ClientList; Saves all Clients.
	 */
	private static ArrayList<Client> ClientList = new ArrayList<>();
	
	
	
	/**
	 * @param        IP, Port, Network; Are needed to Connect the Socket with the requested Server.
	 * @throws       SocketException, UnknownHostException
	 * @constructor Initialisaze the Variables [IP, Port, Socket, Network].
	 */
	public Client(String IP, int Port, Network Network) throws SocketException, UnknownHostException {
		this.IP     = InetAddress.getByName(IP);
		this.Port   = Port;
		this.Socket = new DatagramSocket();
		
		this.Network = Network;
		
		if (!ClientList.contains(this))
			ClientList.add(this);
	}

	
	
	/**
	 * @method run(); Auto-Generated Method by Thread.class.
	 */
	@Override
	public void run() {
		while (true) {
			byte[] data = new byte[1024];
			DatagramPacket packet = new DatagramPacket(data, data.length);
			
			try {
				this.Socket.receive(packet);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			this.Network.onClientReceive(this, packet);
		}
	}
	
	
	
	/**
	 * @param  data; Converted Bytes that will send.
	 * @throws IOException
	 * @method sendData(); Send the Bytes to the Saved Socket.
	 */
	public void sendData(byte[] data) throws IOException {
		DatagramPacket packet = new DatagramPacket(data, data.length, this.IP, this.Port);
		this.Socket.send(packet);
	}
	
	
	
	/**
	 * @param  ID; Generated ID to get the Client.
	 * @method getByID(); Search the Client with the ID in the Client.ClientList.
	 * @return Client fromList : Client.ClientList;
	 */
	public static Client getByID(String ID) {
		for (Client fromList : ClientList) 
			if (fromList.ID.equals(ID))
				return fromList;
		return null;
	}
	
	
	
	/**
	 * @method allClients(); Select the List Client.ClientList.
	 * @return Client.ClientList;
	 */
	public static List<Client> allClients() {
		return ClientList;
	}

	
}
