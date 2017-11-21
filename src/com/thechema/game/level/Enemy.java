package com.thechema.game.level;

import com.thechema.game.graphics.Shader;
import com.thechema.game.graphics.Texture;
import com.thechema.game.graphics.VertexArray;
import com.thechema.math.Matrix4f;
import com.thechema.math.Vector3f;

public class Enemy {

	private float SIZE = 1.0f;
	
	private boolean direction = true;
	private boolean initialize = true;
	
	private Matrix4f ml_matrix;
	
	private static Texture texture;
	public static VertexArray mesh;
	
	private Vector3f position = new Vector3f();
	private float rot;
	private float delta = 0.0f;
	
	public Enemy(){
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
			texture = new Texture("res/ufo.png");
	}
	public void update(){
		if(initialize){
			position.y = 4.0f;
			position.x = 8.5f;
			initialize = !initialize;
		}
		//position.y = 2.5f;
		//position.x = 3.0f;
		
		position.y -= delta/5;
		if(direction)
			delta = -0.05f;
		else
			delta = +0.05f;
		
		//rot = -delta * 90.0f;
	}
	
	public void changeDirection(){
		delta *= -1;
		direction = !direction;
	}
	
	public void render(){
		Shader.ENEMY.enable();
		Shader.ENEMY.setUniformMat4f("ml_matrix", Matrix4f.translate(position).multiply(Matrix4f.rotate(rot)));
		texture.bind();
		mesh.render();
		Shader.ENEMY.disable();
	}
	
	public float getX(){
		return position.x;
	}
	
	public void setX(float x){
		position.x = position.x + x;
	}

	public float getY() {
		return position.y;
	}

	public float getSize() {
		return SIZE;
	}

	public Matrix4f getMl_matrix() {
		return ml_matrix;
	}
	public Vector3f getPosition() {
		return position;
	}
	
	public void setPosition(float x, float y) {
		this.position.y = y;
		this.position.x = x;
	}
	
	
}
