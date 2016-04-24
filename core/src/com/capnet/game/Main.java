package com.capnet.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.capnet.share.networking.ISocketConnect;
import com.capnet.share.networking.PacketManager;

import java.net.Socket;

public class Main extends Game {
	public PacketManager PacketManager = new PacketManager();
	public Socket ClientSocket;

	@Override
	public void create () {
		PacketManager.OnConnected(socket -> ClientSocket = socket);
		this.setScreen(new MainMenuScreen(this));

	}

	public boolean AttemptToConnect(String ip, int port)
	{
		return PacketManager.RegisterSocket(ip,port);
	}


	@Override
	public void render () {
		super.render();
	}

}
