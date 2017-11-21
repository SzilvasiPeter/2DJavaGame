package com.thechema.game.level;

import static org.lwjgl.glfw.GLFW.*;

import com.thechema.game.graphics.Shader;
import com.thechema.game.graphics.Texture;
import com.thechema.game.graphics.VertexArray;
import com.thechema.game.input.Input;
import com.thechema.math.Matrix4f;
import com.thechema.math.Vector3f;

public class Airplane {

	private float SIZE = 1.0f;
	private VertexArray mesh;
	private Texture texture;
	
	private boolean initialize = true;
	
	private Vector3f position = new Vector3f();
	private float rot;
	private float delta = 0.0f;
	
	public Airplane(){
		float[] vertices = new float[]{
				-SIZE / 2.0f,-SIZE / 2.0f, 0.2f,
				-SIZE / 2.0f,SIZE / 2.0f, 0.2f,
				SIZE / 2.0f,SIZE / 2.0f, 0.2f,
				SIZE / 2.0f,-SIZE / 2.0f, 0.2f
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
			texture = new Texture("res/airplane2.png");
	}
	public void update(){
		if(initialize){
			position.x = -0.0f;
			initialize = !initialize;
		}
		
		position.y -= delta;
		if(Input.isKeyDown(GLFW_KEY_SPACE))
			delta = -0.1f;
		else
			delta += 0.01f;
		
		if(Input.isKeyDown(GLFW_KEY_E)){
			//ammo = new Ammo();
		}
		
		rot = -delta * 90.0f;
	}
	
	public void stay(){
		position.x = getX();
		position.y = getY();
	}
	
	public void fall(){
		delta = 0.2f;
	}
	
	public void render(){
		Shader.AIRPLANE.enable();
		Shader.AIRPLANE.setUniformMat4f("ml_matrix", Matrix4f.translate(position).multiply(Matrix4f.rotate(rot)));
		texture.bind();
		mesh.render();
		Shader.AIRPLANE.disable();
	}

	public float getY() {
		return position.y;
	}
	
	public float getX(){
		return position.x;
	}

	public float getSize() {
		return SIZE;
	}
	
	public void setDelta(float x){
		this.delta = x;
	}
	
	public void setPositionY(float y){
		this.position.y = y;
	}
}