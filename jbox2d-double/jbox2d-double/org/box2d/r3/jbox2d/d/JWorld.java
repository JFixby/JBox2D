package org.box2d.r3.jbox2d.d;

import org.box2d.jfixby.api.BodyDef;
import org.box2d.jfixby.api.Box2DBody;
import org.box2d.jfixby.api.Box2DWorld;
import org.box2d.jfixby.api.Box2DWorldSpecs;
import org.box2d.jfixby.api.ContactListener;
import org.jbox2d.d.common.Vector2;
import org.jbox2d.d.dynamics.Body;

import com.jfixby.scarabei.api.collections.Collections;
import com.jfixby.scarabei.api.collections.List;
import com.jfixby.scarabei.api.floatn.Float2;

public class JWorld implements Box2DWorld {

	private org.jbox2d.d.dynamics.World gdx_world;

	// private Box2DWorldRenderer renderer;

	public JWorld(Box2DWorldSpecs specs) {
		Float2 g = specs.getGravity();
		Vector2 gravity = new Vector2(g.getX(), g.getY());
		gdx_world = new org.jbox2d.d.dynamics.World(gravity);
		gdx_world.setAllowSleep(specs.getDoSleep());
		// specs.getDoSleep()
	}

	@Override
	public Box2DBody createBody(BodyDef boxBodyDef) {
		return new JBox2DBody(gdx_world.createBody(((JBodyDef) (boxBodyDef))
				.getGdxBodyDef()));
	}

	@Override
	public void destroyBody(Box2DBody body) {
		gdx_world.destroyBody(((JBox2DBody) body).getGdxBody());
	}

	@Override
	public void step(double box2d_time_step_delta, int velocityIterations,
			int positionIterations) {
		gdx_world.step(box2d_time_step_delta, velocityIterations,
				positionIterations);
	}

	@Override
	public void dispose() {
		// gdx_world.dispose();
	}

	@Override
	public void setContactListener(ContactListener contactListener) {
		gdx_world.setContactListener(new JContactListener(contactListener));
	}

	@Override
	public void setAutoClearForces(boolean autoclearForces) {
		gdx_world.setAutoClearForces(autoclearForces);
	}

	@Override
	public List<Box2DBody> listBodies() {

		Body bodies = this.gdx_world.getBodyList();
		List<Box2DBody> list = Collections.newList();
		while (bodies != null) {
			JBox2DBody body = new JBox2DBody(bodies);
			list.add(body);
			bodies = bodies.getNext();
		}
		return list;
	}

	// @Override
	// public Box2DWorldRenderer getRenderer() {
	// if (this.renderer == null) {
	// this.renderer = new GDXBox2DDebugRenderer();
	// }
	// return this.renderer;
	// }

	@Override
	public boolean isFloatPrecision() {
		return false;
	}

	@Override
	public boolean isDoublePrecision() {
		return true;
	}

}
