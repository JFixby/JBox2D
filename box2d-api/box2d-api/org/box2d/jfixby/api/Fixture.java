package org.box2d.jfixby.api;

public interface Fixture {

	Box2DBody getBody();

	ShapeType getType();

	Shape getShape();
}
