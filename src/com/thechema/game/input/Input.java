package com.thechema.game.input;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWKeyCallback;

public class Input extends GLFWKeyCallback {

	public static boolean[] keys = new boolean[65536];
	
	@Override
	public void invoke(long window, int key, int scanmode, int action, int mods) {
		keys[key] = action != GLFW.GLFW_RELEASE;
	}
	
	public static boolean isKeyDown(int keycode){
		return keys[keycode];
	}
}
