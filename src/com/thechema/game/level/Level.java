package com.thechema.game.level;

import java.util.Random;

import com.thechema.game.graphics.Shader;
import com.thechema.game.graphics.Texture;
import com.thechema.game.graphics.VertexArray;
import com.thechema.game.input.Input;
import com.thechema.math.Matrix4f;
import com.thechema.math.Vector3f;

import static org.lwjgl.glfw.GLFW.*;

public class Level {
	
	private VertexArray background;
	private Texture bgTexture;
	
	private int xScroll = 0;
	private int map = 0;
	
	private Airplane airplane;
	private Enemy enemy;
	private Ammo ammo;
	private Score score;
	private Number number;
	private Number2 number2;
	private Coin coin;
	
	private Building[] Buildings = new Building[10];
	private int index = 0;
	
	private static int numberIndex = 0;
	private static int numberIndex2 = 0;
	
	private float OFFSET = 4.0f;
	private boolean control = true, reset = false, hit = false, coinHit = false;
	
	private Random random = new Random();
	
	public Level(){
		float[] vertices = new float[]{
			-10.0f, -10.0f * 9.0f / 16.0f, 0.0f,
			-10.0f, 10.0f * 9.0f / 16.0f, 0.0f,
			0.0f, 10.0f * 9.0f / 16.0f, 0.0f,
			0.0f, -10.0f * 9.0f / 16.0f, 0.0f
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
		
		background = new VertexArray(vertices, indices, tcs);
		bgTexture = new Texture("res/background.jpg");
		airplane = new Airplane();
		ammo = new Ammo();	
		enemy = new Enemy();
		score = new Score();
		number = new Number();
		number2 = new Number2();
		
		score.setPosition(-9.0f, 5.0f);
		number.setPosition(-7.0f, 5.0f);
		number2.setPosition(-8.0f, 5.0f);
		
		coin = new Coin();
		coin.coinTranslate();
		
		createBuildings();
	}
	
	private void createBuildings(){
		Building.create();
		for(int i = 0; i < 10; i += 1){
			Buildings[i] = new Building(OFFSET + index * 3.0f, 1 + random.nextFloat() * 4.0f - 13.0f);
			index += 1;
		}
	}
	
	private void updateBuildings(){
		if(index > 15){
			Buildings[index % 10] = new Building(OFFSET + index * 3.0f, 2 + random.nextFloat() * 4.0f -12.0f);
		}
		else if(index > 30){
			Buildings[index % 10] = new Building(OFFSET + index * 3.0f, 3 + random.nextFloat() * 4.0f -11.5f);
		}
		else if(index > 45){
			Buildings[index % 10] = new Building(OFFSET + index * 3.0f, 3 + random.nextFloat() * 5.0f -11.0f);
		}
		else{
			Buildings[index % 10] = new Building(OFFSET + index * 3.0f, 1 + random.nextFloat() * 4.0f -12.0f);
		}
		index += 1;
		System.out.println(index);
	}
	
	public void update(){
		if(xScroll == -71){
			numberIndex++;
			///number.setIndexOfNumber(numberIndex);
			number = new Number();
			number.setPosition(-7.0f, 5.0f);
		}else if(xScroll < -71 && xScroll % 60 == 0 ){
			if(numberIndex<9){
			numberIndex++;
			//number.setIndexOfNumber(numberIndex);
			number = new Number();
			number.setPosition(-7.0f, 5.0f);
			}else if(numberIndex ==9){
				numberIndex = 0;
				numberIndex2++;
				number = new Number();
				number.setPosition(-7.0f, 5.0f);
				number2 = new Number2();
				number2.setPosition(-8.0f, 5.0f);
			}
		}/*else if(numberIndex > 9){
			System.out.println("\n ennyi: " + numberIndex);
			numberIndex = 0;
			numberIndex2++;
			System.out.println("\n ennyi: " + numberIndex);
			number = new Number();
			number.setPosition(-7.0f, 5.0f);
			number2 = new Number2();
			number2.setPosition(-8.0f, 5.0f);
		}*/
		if(control){
			xScroll--;
			if(-xScroll % 335 == 0) map++;
			if(-xScroll > 300 && -xScroll % 60 == 0){
				updateBuildings();
			}
		}
		
		enemy.update();
		coin.update();
		if(index >= 15 && index%15==0){
			ammo = new Ammo(airplane.getX(), airplane.getY());
			hit = false;
		}
		if(index >= 10 && index%5==0 && coinHit == true){
			float random = (float) (-1.5 + Math.random() * 3); 
			coin = new Coin(airplane.getX() + 8.0f, 4.0f - random);
			coinHit = false;
		}
		if(Input.isKeyDown(GLFW_KEY_E)){
			ammo.shot();
			//System.out.println("loves");
		}
		
		
		airplane.update();
		ammo.update(airplane.getY());
		if(coinPlaneCollision() && coinHit == false){
			//System.out.println("talált!" + coin.getY() + " " + airplane.getY());
			coinHit = true;
			if(numberIndex<=6){
				numberIndex = numberIndex + 3;
				//number.setIndexOfNumber(numberIndex);
				number = new Number();
				number.setPosition(-7.0f, 5.0f);
			}
			else{
				numberIndex = numberIndex+3-10;
				numberIndex2 = numberIndex2 + 1;
				number = new Number();
				number.setPosition(-7.0f, 5.0f);
				number2 = new Number2();
				number2.setPosition(-8.0f, 5.0f);
			}
		}
		//if(enemy.getX() == ammo.getX() && enemy.getY() == ammo.getY()){
		//System.out.println("\n" + ammo.getX() + " " + enemy.getX() + " " + ammo.getY() + " " + enemy.getY());
		if(ammo.getX() > 8.5 && ammo.getX() < 11 && Math.abs((ammo.getY() - enemy.getY())) < 1 && hit ==false){
			//System.out.println("eltûnt");
			hit = true;
			if(numberIndex<=6){
				numberIndex = numberIndex + 3;
				//number.setIndexOfNumber(numberIndex);
				number = new Number();
				number.setPosition(-7.0f, 5.0f);
			}
			else{
				numberIndex = numberIndex+3-10;
				numberIndex2 = numberIndex2 + 1;
				number = new Number();
				number.setPosition(-7.0f, 5.0f);
				number2 = new Number2();
				number2.setPosition(-8.0f, 5.0f);
			}
		}
		
		if(control && collision()){
			airplane.fall();
			ammo.fall();
			control = false;
		}
		if(!control && Input.isKeyDown(GLFW_KEY_SPACE)){
			reset = true;
		}
		if(enemyCollision()){
			enemy.changeDirection();
		}
		//System.out.println("x:" + ammo.getX() + "y:" + ammo.getY());
		/*if(ammmoEnemyCollision()){
			enemy.setX(+10.0f);
			System.out.println("Talált");
		}*/
	}
	
	private void renderBuildings(){
		Shader.BUILDING.enable();
		Shader.BUILDING.setUniformMat4f("vw_matrix", Matrix4f.translate(new Vector3f(xScroll * 0.05f, 0.0f, 0.0f)));
		Building.getTexture().bind();
		Building.getMesh().bind();
		
		for(int i = 0; i < 10 ; i++){
			Shader.BUILDING.setUniformMat4f("ml_matrix", Buildings[i].getModelMatrix());

			Building.getMesh().draw();
		}
		Building.getMesh().unbind();
		Building.getTexture().unbind();
	}
	
	public boolean coinPlaneCollision(){
		float cx = coin.getX();
		float cy = coin.getY();
		//float ax = airplane.getX();
		float ay = airplane.getY();
		
		//System.out.println("COIN: " + coin.getX() + " " + coin.getY());
		
		if(cx<1.0f && cx > -1.0f){
			if(ay > cy - 1.0f && ay < cy + 1.0f){
				return true;
			}
		}
		return false;
	}
	/*
	public boolean ammmoEnemyCollision(){
		
		float ex = enemy.getX();
		float ey = enemy.getY();
		float ax = ammo.getX();
		float ay = ammo.getY();
		
		//if(ex >= ax - 1.0f && ex <= ax + 1.0f && ey >= ay - 1.0f && ey <= ay + 1.0f)
		if(ex==ax && ey==ay)
			return true;
		return false;
	}*/
	
	public boolean collision(){
		for(int i = 0; i < 10 ; i++){
			float ax = -xScroll * 0.05f;
			float ay = airplane.getY();
			
			float bx = Buildings[i].getX();
			float by = Buildings[i].getY();
			
			float ax0 = ax - airplane.getSize() / 2.0f;
			float ax1 = ax + airplane.getSize() / 2.0f;
			float ay0 = ay - airplane.getSize() / 2.0f;
			float ay1 = ay + airplane.getSize() / 2.0f;
			
			float bx0 = bx;
			float bx1 = bx + Building.getWidth();
			float by0 = by;
			float by1 = by + Building.getHeight();
			
			if(ax1 > bx0 && ax0 < bx1){
				if((ay1 > by0 && ay0 < by1)){
					return true;
				}
			}
			if(ay1 > 5.75f || ay1 < -6.0f){
				return true;
			}
		}
		return false;
	}
	public boolean enemyCollision(){
		float ey = enemy.getY();
		
		if(ey > 5.0f || ey < 3.0f)
			return true;
		else
			return false;
	}
	
	public boolean isGameOver(){
		return reset;
	}
	
	public void render(){
		bgTexture.bind();
		Shader.BG.enable();
		background.bind();
		for(int i = map; i < map + 4; i++){
			Shader.BG.setUniformMat4f("vw_matrix", Matrix4f.translate(new Vector3f(i * 10 + xScroll * 0.03f, 0.0f, 0.0f)));
			background.draw();
		}
		background.render();
		Shader.BG.disable();
		bgTexture.unbind();
		
		renderBuildings();
		airplane.render();
		
		
		if(hit==false){
			enemy.render();
			ammo.render();
		}if(coinHit==false){
			coin.render();
		}
		
		score.render();
		number.render();
		number2.render();
		
		
	}
	public static int getNumber(){
		return numberIndex;
	}
	public static int getNumber2(){
		return numberIndex2;
	}
	public static void resetNumbers(){
		numberIndex = 0;
		numberIndex2 = 0;
	}
	/*
	public int getIndex(){
		return index;
	}*/
}
