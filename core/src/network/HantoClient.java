package network;

import java.awt.EventQueue;
import java.io.IOException;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.examples.chat.Network;
import com.esotericsoftware.kryonet.examples.chat.Network.CameraUpdateMessage;
import com.esotericsoftware.kryonet.examples.chat.Network.ChatMessage;
import com.esotericsoftware.kryonet.examples.chat.Network.JoinLobbyRequest;
import com.esotericsoftware.kryonet.examples.chat.Network.StartGameMessage;
import com.esotericsoftware.kryonet.examples.chat.Network.UpdateNames;
import com.mygdx.game.util.GameController;
import com.mygdx.screen.GameScreen;

public class HantoClient {
	public Client client;
	String mName;
	public HantoClient(String _name) {
		client = new Client();
		client.start();
		mName = _name;
		// For consistency, the classes to be sent over the network are
		// registered by the same method for both the client and server.
		Network.register(client);
		
		client.addListener(new Listener() {
			// On connect
			public void connected (Connection connection) {
				JoinLobbyRequest joinLobbyRequest = new JoinLobbyRequest();
				joinLobbyRequest.name = mName;
				client.sendTCP(joinLobbyRequest);
			}
			// On received packet
			public void received (Connection connection, Object object) {
				if (object instanceof StartGameMessage)
				{
					if (mName == "Host")
					{
						NetworkUtils.enterGame = true;
					}
					else if (mName == "Client")
					{
						NetworkUtils.enterGame = true;
					}
				}
				if (object instanceof CameraUpdateMessage) {
					System.out.println("Client Packet Received: CameraUpdateMessage");
					CameraUpdateMessage msg = (CameraUpdateMessage)object;
					NetworkUtils.cameraController.setCameraPosition(msg.x, msg.y);
					return;
				}
				if (object instanceof UpdateNames) {
					System.out.println("Client Packet Received: UpdateNames");
					return;
				}

				if (object instanceof ChatMessage) {
					ChatMessage chatMessage = (ChatMessage)object;
					System.out.println("Client Packet Received: ChatMessage");
					return;
				}
			}
			// On disconnect
			public void disconnected (Connection connection) {
				EventQueue.invokeLater(new Runnable() {
					public void run () {
					}
				});
			}
		});

		new Thread("Connect") {
			public void run () {
				try {
					System.out.println("HantoClient: ----" + NetworkUtils.serverIP);
					client.connect(5000, NetworkUtils.serverIP, Network.port);
					// Server communication after connection can go here, or in Listener#connected().
				} catch (IOException ex) {
					ex.printStackTrace();
					System.exit(1);
				}
			}
		}.start();
	}
}
class NewThread extends Thread {
	int code;
	Robot robot;
	public NewThread(int code) {
		this.code = code;
	}
    public void run() {
       
    }



}
