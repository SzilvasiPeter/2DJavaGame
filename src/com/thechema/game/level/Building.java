package com.thechema.game.level;

import com.thechema.game.graphics.Texture;
import com.thechema.game.graphics.VertexArray;
import com.thechema.math.Matrix4f;
import com.thechema.math.Vector3f;

public class Building {

	private Vector3f position = new Vector3f();
	private Matrix4f ml_matrix;
	
	private static float width = 2.0f, height = 9.0f; 
	private static Texture texture;
	public static VertexArray mesh;
	
	public static void create(){
		float[] vertices = new float[]{
				-0.0f, 0.0f, 0.1f,
				-0.0f, height, 0.1f,
				width, height, 0.1f,
				width, 0.0f, 0.1f,
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
			texture = new Texture("res/building3.jpg");
	}
	
	public Building(float x, float y){
		position.x = x;
		position.y = y;
		ml_matrix = Matrix4f.translate(position);
	}
	
	public float getX(){
		return position.x;
	}
	
	public float getY(){
		return position.y;
	}
	
	public Matrix4f getModelMatrix(){
		return ml_matrix;
	}
	
	public static VertexArray getMesh(){
		return mesh;
	}
	
	public static Texture getTexture(){
		return texture;
	}
	
	public static float getWidth(){
		return width;
	}
	
	public static float getHeight(){
		return height;
	}
}
