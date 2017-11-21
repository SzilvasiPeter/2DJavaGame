package com.thechema.game.level;

import com.thechema.game.graphics.Shader;
import com.thechema.game.graphics.Texture;
import com.thechema.game.graphics.VertexArray;
import com.thechema.math.Matrix4f;
import com.thechema.math.Vector3f;

public class Coin {

	private float SIZE = 0.5f;
	
	private Matrix4f ml_matrix;
	
	private static Texture texture;
	public static VertexArray mesh;
	
	private Vector3f position = new Vector3f();
	private float rot;
	private float delta = 0.0f;
	
	public Coin(){
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
			texture = new Texture("res/coin.jpg");
	}
	public Coin(float x, float y){
		position.x = x;
		position.y = y;
		ml_matrix = Matrix4f.translate(position);
	}
	public void update(){
		delta=0.25f;
		
		position.x -= delta/5;
		
	}
	public void coinTranslate(){
		position.x = 6.0f;
		position.y = 4.0f;
	}
	
	public void render(){
		Shader.COIN.enable();
		Shader.COIN.setUniformMat4f("ml_matrix", Matrix4f.translate(position).multiply(Matrix4f.rotate(rot)));
		texture.bind();
		mesh.render();
		Shader.COIN.disable();
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
