package org.box2d.jfixby.api;

import com.jfixby.cmns.api.geometry.Float2;

public interface Box2DWorldSpecs {

	public static final double STEPS_PER_BOX2D_SECOND = 60;

	void setGravity(Float2 gravity);

	void setDoSleep(boolean doSleep);

	public Float2 getGravity();

	boolean getDoSleep();

}
