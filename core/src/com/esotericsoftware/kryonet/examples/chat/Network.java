
package com.esotericsoftware.kryonet.examples.chat;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Frustum;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Plane;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;
import com.mygdx.game.util.CameraController;

// This class is a convenient place to keep things common to both the client and server.
public class Network {
	static public final int port = 54555;

	// This registers objects that are going to be sent over the network.
	static public void register (EndPoint endPoint) {
		Kryo kryo = endPoint.getKryo();
		kryo.register(JoinLobbyRequest.class);
		kryo.register(String[].class);
		kryo.register(float[].class);
		kryo.register(float.class);
		kryo.register(UpdateNames.class);
		kryo.register(ChatMessage.class);
		kryo.register(StartGameMessage.class);
		kryo.register(CameraUpdateMessage.class);
		kryo.register(OrthographicCamera.class);
		kryo.register(Matrix4.class);
		kryo.register(Matrix4[].class);
		kryo.register(Plane.class);
		kryo.register(Plane[].class);
		kryo.register(Vector2.class);
		kryo.register(Vector3.class);
		kryo.register(Vector2[].class);
		kryo.register(Vector3[].class);
		kryo.register(Frustum.class);
		
	}

	static public class JoinLobbyRequest {
		public String name;
	}

	static public class UpdateNames {
		public String[] names;
	}

	static public class ChatMessage {
		public String text;
	}
	
	static public class StartGameMessage
	{
		public String text = "hi";
	}
	
	static public class CameraUpdateMessage
	{
		public float x;
		public float y;
	}
}
