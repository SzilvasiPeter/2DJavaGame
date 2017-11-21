package com.thechema.game.level;

//import static org.lwjgl.glfw.GLFW.GLFW_KEY_SPACE;
//import static org.lwjgl.glfw.GLFW.GLFW_KEY_E;

import com.thechema.game.graphics.Shader;
import com.thechema.game.graphics.Texture;
import com.thechema.game.graphics.VertexArray;
//import com.thechema.game.input.Input;
import com.thechema.math.Matrix4f;
import com.thechema.math.Vector3f;

public class Ammo {

private float SIZE = 1.6f;
	
	private boolean initialize = true;
	private float rot = 0;
	
	private Matrix4f ml_matrix;
	
	private static Texture texture;
	public static VertexArray mesh;
	
	private Vector3f position = new Vector3f();
	private float delta = 0.0f;
	
	public Ammo(){
		float[] vertices = new float[]{
				-SIZE / 3.0f,-SIZE / 3.0f, 0.25f,
				-SIZE / 3.0f,SIZE / 3.0f, 0.25f,
				SIZE / 3.0f,SIZE / 3.0f, 0.25f,
				SIZE / 3.0f,-SIZE / 3.0f, 0.25f
			};
			
			byte[] indices = new byte[]{
				0, 1, 2,
				2, 3, 0
			};
			
			float[] tcs = new float[]{
					0, 1,
					0, 0,
					1, 0,
					1, 1
			};
			mesh = new VertexArray(vertices, indices, tcs);
			texture = new Texture("res/rocket.png");
	}
	public Ammo(float x, float y){
		position.x = x;
		position.y = y;
		ml_matrix = Matrix4f.translate(position);
	}
	
	public void update(float ay){
		if(initialize){
			position.x = 0.0f;
			//position.y = position.y;
			initialize = !initialize;
		}
		position.y = ay;
		/*
		position.y -= delta;
		if(Input.isKeyDown(GLFW_KEY_SPACE))
			delta = -0.1f;
		else
			delta += 0.01f;*/
		/*if(Input.isKeyDown(GLFW_KEY_E)){
			shot();
		}*/
		
		rot = -delta * 90.0f; 
	}
	
	public void render(){
		Shader.AMMO.enable();
		Shader.AMMO.setUniformMat4f("ml_matrix", Matrix4f.translate(position).multiply(Matrix4f.rotate(rot)));
		texture.bind();
		mesh.render();
		Shader.AMMO.disable();
	}
	
	public void shot(){
		for (float j = 0; j <= 0.4; j+=0.03) {
			position.x += j;	
		}
	}
	
	public void fall(){
		delta = 0.2f;
	}

	public float getY() {
		return position.y;
	}
	
	public float getX() {
		return position.x;
	}

	public float getSize() {
		return SIZE;
	}

	public Matrix4f getMl_matrix() {
		return ml_matrix;
	}
}
