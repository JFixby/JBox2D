package org.box2d.jfixby.api;

import com.jfixby.scarabei.api.floatn.FixedFloat2;
import com.jfixby.scarabei.api.floatn.Float2;

public interface CircleShape extends Shape {

	void setRadius(double radius);

	void setPosition(Float2 centerTmp);

	FixedFloat2 getPosition();

	double getRadius();
}
