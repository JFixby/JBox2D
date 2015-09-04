package org.box2d.r3.jbox2d.f;

import org.box2d.jfixby.api.BodyDef;
import org.box2d.jfixby.api.BodyType;

import com.jfixby.cmns.api.geometry.Float2;

public class JBodyDef implements BodyDef {

	private JPoint2D position;

	public JBodyDef() {
		gdx_bodydef = new org.jbox2d.f.dynamics.BodyDef();
		position = new JPoint2D(gdx_bodydef.position);
	}

	private final org.jbox2d.f.dynamics.BodyDef gdx_bodydef;

	@Override
	public Float2 position() {
		return position;
	}

	@Override
	public void setType(BodyType type) {
		gdx_bodydef.type = resolve(type);
	}

	private org.jbox2d.f.dynamics.BodyType resolve(BodyType type) {
		if (type == BodyType.DynamicBody) {
			return org.jbox2d.f.dynamics.BodyType.DYNAMIC;
		}
		if (type == BodyType.KinematicBody) {
			return org.jbox2d.f.dynamics.BodyType.KINEMATIC;
		}
		if (type == BodyType.StaticBody) {
			return org.jbox2d.f.dynamics.BodyType.STATIC;
		}
		throw new Error();

	}

	public org.jbox2d.f.dynamics.BodyDef getGdxBodyDef() {
		return gdx_bodydef;
	}
}
