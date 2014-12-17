package network;

import java.io.IOException;
import java.util.ArrayList;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.kryonet.examples.chat.Network;
import com.esotericsoftware.kryonet.examples.chat.Network.CameraUpdateMessage;
import com.esotericsoftware.kryonet.examples.chat.Network.ChatMessage;
import com.esotericsoftware.kryonet.examples.chat.Network.JoinLobbyRequest;
import com.esotericsoftware.kryonet.examples.chat.Network.StartGameMessage;
import com.esotericsoftware.kryonet.examples.chat.Network.UpdateNames;

public class HantoServer {
	Server server;

	public HantoServer () throws IOException {
		server = new Server() {
			protected Connection newConnection () {
				// By providing our own connection implementation, we can store per
				// connection state without a connection ID to state look up.
				return new HantoConnection();
			}
		};

		// For consistency, the classes to be sent over the network are
		// registered by the same method for both the client and server.
		Network.register(server);

		server.addListener(new Listener() {
			public void received (Connection c, Object object) {
				// We know all connections for this server are actually ChatConnections.
				HantoConnection connection = (HantoConnection)c;
				if (object instanceof CameraUpdateMessage) {
					CameraUpdateMessage msg = (CameraUpdateMessage)object;
					server.sendToAllExceptTCP(c.getID(), msg);
					return;
				}
				
				if (object instanceof JoinLobbyRequest) {
					// Ignore the object if the name is invalid.
					String name = ((JoinLobbyRequest)object).name;
					if (name == null) return;
					name = name.trim();
					if (name.length() == 0) return;
					// Store the name on the connection.
					connection.name = name;
					// Send everyone a new list of connection names.
					updateNames();
					return;
				}

				if (object instanceof ChatMessage) {
					// Ignore the object if a client tries to chat before registering a name.
					if (connection.name == null) return;
					ChatMessage chatMessage = (ChatMessage)object;
					// Ignore the object if the chat message is invalid.
					String message = chatMessage.text;
					if (message == null) return;
					message = message.trim();
					if (message.length() == 0) return;
					// Prepend the connection's name and send to everyone.
					chatMessage.text = connection.name + ": " + message;
					server.sendToAllTCP(chatMessage);
					return;
				}
			}

			public void disconnected (Connection c) {
				HantoConnection connection = (HantoConnection)c;
				if (connection.name != null) {
					// Announce to everyone that someone (with a registered name) has left.
					ChatMessage chatMessage = new ChatMessage();
					chatMessage.text = connection.name + " disconnected.";
					server.sendToAllTCP(chatMessage);
					updateNames();
				}
			}
		});
		server.bind(Network.port);
		server.start();

		// Open a window to provide an easy way to stop the server.
//		JFrame frame = new JFrame("Chat Server");
//		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//		frame.addWindowListener(new WindowAdapter() {
//			public void windowClosed (WindowEvent evt) {
//				server.stop();
//			}
//		});
//		frame.getContentPane().add(new JLabel("Close to stop the chat server."));
//		frame.setSize(320, 200);
//		frame.setLocationRelativeTo(null);
//		frame.setVisible(true);
	}

	void updateNames () {
		// Collect the names for each connection.
		Connection[] connections = server.getConnections();
		ArrayList names = new ArrayList(connections.length);
		for (int i = connections.length - 1; i >= 0; i--) {
			HantoConnection connection = (HantoConnection)connections[i];
			names.add(connection.name);
		}
		// Send the names to everyone.
		
		if (connections.length > 1)
		{
			StartGameMessage startgamemsg = new StartGameMessage();
			server.sendToAllTCP(startgamemsg);
		}
		//UpdateNames updateNames = new UpdateNames();
		//updateNames.names = (String[])names.toArray(new String[names.size()]);
		//server.sendToAllTCP(updateNames);
	}

	// This holds per connection state.
	static class HantoConnection extends Connection {
		public String name;
	}

}
