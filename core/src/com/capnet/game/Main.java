package com.capnet.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.capnet.server.HostService;
import com.capnet.share.networking.ISocketConnect;
import com.capnet.share.networking.PacketManager;

import java.io.IOException;
import java.net.Socket;

public class Main extends Game {

	@Override
	public void create () {
		this.setScreen(new MainMenuScreen(this));

	}


	@Override
	public void render () {
		super.render();
	}

}
