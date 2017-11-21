package com.thechema.game;

import static org.lwjgl.glfw.GLFW.*;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import org.lwjgl.opengl.*;

import static org.lwjgl.system.MemoryUtil.*;

import java.applet.Applet;
import java.applet.AudioClip;
import java.io.IOException;
import java.net.URL;

import javax.swing.*;

import com.thechema.game.graphics.Shader;
import com.thechema.game.input.Input;
import com.thechema.game.input.Menu;
import com.thechema.game.level.Level;
import com.thechema.math.Matrix4f;

//New Comment 2
//Random Comment

public class Main implements Runnable {
	
	private int width = 800;
	private int height = 600;
	
	private Thread thread;
	private boolean running = false;
	
	private long window;
	static Menu nyito;

	//private TrueTypeFont font;
	
	private Level level;
	public void start(){
		running = true;
		thread = new Thread(this,"Game");
		thread.start();
	}
	
	private void init(){
		if(glfwInit() != true){
			System.err.println("Could not initialize GLFW!");
			return;
		}
		
		glfwWindowHint(GLFW_RESIZABLE, GL_TRUE);
		window = glfwCreateWindow(width, height, "Airplane", NULL, NULL);
		
		if(window == NULL){
			System.err.println("Could not create GLFW window!");
			return;
		}
		//ByteBuffer vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
		glfwSetWindowPos(window, 300, 100);
		
		glfwSetKeyCallback(window, new Input());
		
		glfwMakeContextCurrent(window);
		glfwShowWindow(window);
		
		GL.createCapabilities();
		
		glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
		glEnable(GL_DEPTH_TEST);
		glActiveTexture(GL_TEXTURE1);

		System.out.println("OpenGL: " + glGetString(GL_VERSION));
		Shader.loadAll();
		
		Matrix4f pr_matrix = Matrix4f.orthographic(-10.0f, 10.0f, -10.0f * 9.0f / 16.0f, 10.0f * 9.0f / 16.0f, -1.0f, 1.0f);
		Shader.BG.setUniformMat4f("pr_matrix", pr_matrix);
		Shader.BG.setUniform1i("tex", 1);
		
		Shader.AIRPLANE.setUniformMat4f("pr_matrix", pr_matrix);
		Shader.AIRPLANE.setUniform1i("tex", 1);
		
		Shader.BUILDING.setUniformMat4f("pr_matrix", pr_matrix);
		Shader.BUILDING.setUniform1i("tex", 1);
		
		Shader.ENEMY.setUniformMat4f("pr_matrix", pr_matrix);
		Shader.ENEMY.setUniform1i("tex", 1);
		
		Shader.AMMO.setUniformMat4f("pr_matrix", pr_matrix);
		Shader.AMMO.setUniform1i("tex", 1);
		
		Shader.SCORE.setUniformMat4f("pr_matrix", pr_matrix);
		Shader.SCORE.setUniform1i("tex", 1);
		
		Shader.COIN.setUniformMat4f("pr_matrix", pr_matrix);
		Shader.COIN.setUniform1i("tex", 1);
				
		level = new Level();
	}
	
	public void run(){
				init();
				long lastTime = System.nanoTime();
				double delta = 0.0;
				double ms = 1000000000.0 / 60.0;
				long timer = System.currentTimeMillis();
				int updates = 0;
				int frames = 0;
				while(running){
					long now = System.nanoTime();
					delta +=(now-lastTime) / ms;
					lastTime = now;
					if(delta >= 1.0){
						update();
						updates++;
						delta--;
					}
					render();
					frames++;
					if(System.currentTimeMillis() - timer > 1000){
						timer += 1000;
						System.out.println(updates + "ups, " + frames/10 + " fps");
						updates=0;
						frames = 0;
						
					}
					
					if(glfwWindowShouldClose(window) == true){
						running= false;
					}
					
				}
				glfwDestroyWindow(window);
				glfwTerminate();		
	}
	private void update(){
		glfwPollEvents();
		level.update();
		if(level.isGameOver()){
			Level.resetNumbers();
			//level = new Level();
			nyito.setIsRunning(false);
			running = false;


		}
	}
	private void render(){
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        
		level.render();
		
		int error = glGetError();
		if(error != GL_NO_ERROR)
			System.out.println(error);
		
		glfwSwapBuffers(window);
	}

	public static void main(String[] args) throws IOException {
		nyito = new Menu();
        nyito.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
        nyito.setVisible(true);
	}

}
