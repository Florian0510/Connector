package io.network;

import java.net.DatagramPacket;

import io.network.client.Client;
import io.network.server.Server;

public interface Network {

	/**
	 * @author Florian 
	 */
	
	
	
	/**
	 * @param  Client, packet; Access to the Client-Class and the received packet.
	 * @method onClientReceive(); Is called when the Client receives an incoming packet.
	 */	
	void onClientReceive(Client Client, DatagramPacket packet);

	
	
	/**
	 * @param  Server, packet; Access to the Server-Class and the received packet.
	 * @method onServerReceive(); Is called when the Server receives an incoming packet.
	 */	
	void onServerReceive(Server Server, DatagramPacket packet);

	
}
