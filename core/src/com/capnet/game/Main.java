package com.capnet.game;

import com.badlogic.gdx.Game;

public class Main extends Game {

	@Override
	public void create () {
		this.setScreen(new MainMenuScreen(this));
		//this.setScreen(new GeneratorScreen(this));
	}


	@Override
	public void render () {
		super.render();
	}

}
