package org.box2d.jfixby.api;

import com.jfixby.scarabei.api.floatn.Float2;

public interface RayCastCallback {

	public double reportRayFixture(Fixture fixture, Float2 point, Float2 normal,
			double fraction);
}
