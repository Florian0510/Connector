package io.network.server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import io.network.Network;

public class Server extends Thread {

	/**
	 * @author Florian
	 */
	
	
	
	/**
	 * @param Port; Saves the Server-Address Port.
	 */
	private int Port;
	
	/**
	 * @param Socket; Saves the DatagramSocket.
	 */
	private DatagramSocket Socket;
	
	/**
	 * @param Network; Saves the Network interface.
	 */
	private Network Network;
	
	
	
	/**
	 * @param        Port, Network; Are needed to Create the Socket with the requested Port.
	 * @throws       SocketException, UnknownHostException
	 * @constructor Initialisaze the Variables [Port, Socket, Network].
	 */
	public Server(int Port, Network Network) throws SocketException {
		this.Port   = Port;
		this.Socket = new DatagramSocket(Port);
		
		this.Network = Network;
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

			this.Network.onServerReceive(this, packet);
		}
	}
	
	
	
	/**
	 * @param  data; Converted Bytes that will send.
	 * @throws IOException
	 * @method sendData(); Send the Bytes to the Saved Socket.
	 */
	public void sendData(byte[] data, InetAddress IP, int Port) throws IOException {
		DatagramPacket packet = new DatagramPacket(data, data.length, IP, Port);
		this.Socket.send(packet);
	}

	
}
