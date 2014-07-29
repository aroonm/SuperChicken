package com.aroonsworld.gamedev.superchicken;

public class Background {

	float bkx;
	float bkdx=0.5f;

	public void update(float width) {
		if (bkx > width * -1) {
			bkx -= bkdx;
		} else {
			bkx = 0;
		}
	}

	public float getBkx() {
		return bkx;
	}

	public void setBkx(float bkx) {
		this.bkx = bkx;
	}
}
