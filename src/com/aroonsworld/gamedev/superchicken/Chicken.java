package com.aroonsworld.gamedev.superchicken;

public class Chicken {
	final int JUMPSPEED = -14;
    private float centerX = 95;
    private float centerY = 320;
    private boolean jumped = false;
    int speedY=3;
    
    public void update() {
    	centerY += speedY;
    	if (jumped == true) {
            speedY += 1;
        }
    }

    public void jump() {
            speedY = JUMPSPEED;
            jumped = true;
    }

	public float getCenterX() {
		return centerX;
	}

	public void setCenterX(int centerX) {
		this.centerX = centerX;
	}

	public float getCenterY() {
		return centerY;
	}

	public void setCenterY(int centerY) {
		this.centerY = centerY;
	}

	public boolean isJumped() {
		return jumped;
	}

	public void setJumped(boolean jumped) {
		this.jumped = jumped;
	}

	public int getSpeedY() {
		return speedY;
	}

	public void setSpeedY(int speedY) {
		this.speedY = speedY;
	}

	public int getJUMPSPEED() {
		return JUMPSPEED;
	}
}
