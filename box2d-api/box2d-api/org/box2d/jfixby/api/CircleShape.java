package org.box2d.jfixby.api;

import com.jfixby.cmns.api.geometry.FixedFloat2;
import com.jfixby.cmns.api.geometry.Float2;

public interface CircleShape extends Shape {

	void setRadius(double radius);

	void setPosition(Float2 centerTmp);

	FixedFloat2 getPosition();

	double getRadius();
}
