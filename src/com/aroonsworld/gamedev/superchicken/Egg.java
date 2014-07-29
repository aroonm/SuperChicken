package com.aroonsworld.gamedev.superchicken;

public class Egg {
	int x,y;
	int EGGSPEED = 6;
	public Egg(int x, int y){
		this.x=x;
		this.y=y;
	}
	public void update(){
		x-=EGGSPEED;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	
	
}
