package org.box2d.jfixby.api;

public interface ContactFilter {
	boolean shouldCollide(Fixture fixtureA, Fixture fixtureB);

}
