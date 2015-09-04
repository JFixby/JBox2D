package org.box2d.jfixby.api;

public interface QueryCallback {
	/**
	 * Called for each fixture found in the query AABB.
	 * 
	 * @return false to terminate the query.
	 */
	public boolean reportFixture(Fixture fixture);
}
