package org.box2d.r3.jbox2d.f;

import org.box2d.jfixby.api.BodyDef;
import org.box2d.jfixby.api.Box2DComponent;
import org.box2d.jfixby.api.Box2DWorld;
import org.box2d.jfixby.api.Box2DWorldSpecs;
import org.box2d.jfixby.api.CircleShape;
import org.box2d.jfixby.api.FixtureDef;
import org.box2d.jfixby.api.PolygonShape;

public class JBox2DFloat implements Box2DComponent {

	public JBox2DFloat() {

	}

	@Override
	public Box2DWorld newBox2DWorld(Box2DWorldSpecs specs) {
		return new JWorld(specs);

	}

	@Override
	public Box2DWorldSpecs newBox2DWorldSpecs() {
		return new JWorldSpecs();
	}

	@Override
	public FixtureDef newFixtureDef() {
		return new JFixtureDef();
	}

	@Override
	public BodyDef newBodyDef() {
		return new JBodyDef();
	}

	@Override
	public PolygonShape newPolygonShape() {
		return new JPolygonShape();
	}

	@Override
	public CircleShape newCircleShape() {
		return new JCircleShape();
	}

}
