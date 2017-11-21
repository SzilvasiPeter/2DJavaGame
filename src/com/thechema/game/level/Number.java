package com.thechema.game.level;

import com.thechema.game.graphics.Shader;
import com.thechema.game.graphics.Texture;
import com.thechema.game.graphics.VertexArray;
import com.thechema.math.Matrix4f;
import com.thechema.math.Vector3f;

public class Number {
	private float SIZE = 1.6f;
	private Matrix4f ml_matrix;
	
	private static Texture texture;
	public static VertexArray mesh;
	
	private int indexOfNumber = 0;
	
	private Vector3f position = new Vector3f();
	
	public Number(){
	float[] vertices = new float[]{
			-SIZE / 3.0f,-SIZE / 3.0f, 0.2f,
			-SIZE / 3.0f,SIZE / 3.0f, 0.2f,
			SIZE / 3.0f,SIZE / 3.0f, 0.2f,
			SIZE / 3.0f,-SIZE / 3.0f, 0.2f
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
		texture = new Texture("res/Score/number"+Level.getNumber()+".png");
	}
	public void setPosition(float x, float y){
		position.x = x;
		position.y = y;
		ml_matrix = Matrix4f.translate(position);
	}
	
	public void render(){
		Shader.SCORE.enable();
		Shader.SCORE.setUniformMat4f("ml_matrix", Matrix4f.translate(position).multiply(Matrix4f.rotate(0)));
		texture.bind();
		mesh.render();
		Shader.SCORE.disable();
	}
	
	public float getY() {
		return position.y;
	}
	
	public float getX() {
		return position.y;
	}
	
	public float getSize() {
		return SIZE;
	}
	
	public Matrix4f getMl_matrix() {
		return ml_matrix;
	}
	
	public void setIndexOfNumber(int n){
		this.indexOfNumber = n;
	}
	public int getIndexOfNumber(){
		return indexOfNumber;
	}
}
