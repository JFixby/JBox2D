package org.box2d.jfixby.api;

import com.jfixby.cmns.api.geometry.Float2;

public interface BodyDef {

	Float2 position();

	void setType(BodyType type);

}
