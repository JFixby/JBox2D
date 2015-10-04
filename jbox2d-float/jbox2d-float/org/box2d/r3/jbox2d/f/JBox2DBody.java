package org.box2d.r3.jbox2d.f;

import org.box2d.jfixby.api.BodyType;
import org.box2d.jfixby.api.Box2DBody;
import org.box2d.jfixby.api.Box2DTransform;
import org.box2d.jfixby.api.Fixture;
import org.box2d.jfixby.api.FixtureDef;
import org.box2d.jfixby.api.MassData;
import org.jbox2d.f.common.Vec3;
import org.jbox2d.f.common.Vector2;

import com.jfixby.cmns.api.collections.Collection;
import com.jfixby.cmns.api.collections.JUtils;
import com.jfixby.cmns.api.collections.List;
import com.jfixby.cmns.api.geometry.Float2;
import com.jfixby.cmns.api.geometry.Geometry;

public class JBox2DBody implements Box2DBody {

	private org.jbox2d.f.dynamics.Body gdx_body;
	private JMassData mass_data;
	private Float2 position = Geometry.newFloat2();
	private Float2 velocity = Geometry.newFloat2();
	private Float2 center = Geometry.newFloat2();
	private Vector2 tmp2 = new Vector2();

	public JBox2DBody(org.jbox2d.f.dynamics.Body createBody) {
		this.gdx_body = createBody;
		this.mass_data = new JMassData(gdx_body);
	}

	@Override
	public void setLinearVelocity(double vx, double vy) {
		// gdx_body.setLinearVelocity((float) vx, (float) vy);
		tmp2.set((float) vx, (float) vy);
		gdx_body.setLinearVelocity(tmp2);
	}

	@Override
	public void setTransform(double x, double y, double radians) {
		tmp2.set((float) x, (float) y);
		gdx_body.setTransform(tmp2, (float) radians);

	}

	@Override
	public void applyForceToCenter(double forceX, double forceY, boolean wake) {
		tmp2.set((float) forceX, (float) forceY);
		gdx_body.applyForceToCenter(tmp2);

		// gdx_body.applyForceToCenter((float) forceX, (float) forceY, wake);
	}

	@Override
	public MassData getMassData() {
		return this.mass_data;
	}

	@Override
	public void setMassData(MassData mass_data) {
		this.mass_data.set(mass_data);
	}

	@Override
	public Float2 getPosition() {
		final Vector2 p = this.gdx_body.getPosition();
		position.setXY(p.x, p.y);
		return position;
	}

	@Override
	public double getAngle() {
		return this.gdx_body.getAngle();
	}

	@Override
	public Float2 getLinearVelocity() {
		final Vector2 p = this.gdx_body.getLinearVelocity();
		velocity.setXY(p.x, p.y);
		return velocity;
	}

	@Override
	public void createFixture(FixtureDef fixture) {
		final JFixtureDef def = (JFixtureDef) fixture;
		this.gdx_body.createFixture(def.getGdxFixture());
	}

	@Override
	public Float2 getWorldCenter() {
		final Vector2 p = this.gdx_body.getWorldCenter();
		center.setXY(p.x, p.y);
		return center;
	}

	@Override
	public void setTransform(Float2 worldCenter, double angle) {
		tmp2.set((float) worldCenter.getX(), (float) worldCenter.getY());
		this.gdx_body.setTransform(tmp2, (float) angle);
	}

	@Override
	public Float2 getLocalPoint(Float2 anchorA) {
		throw new Error();
	}

	@Override
	public boolean isActive() {
		return this.gdx_body.isActive();
	}

	@Override
	public Box2DTransform getTransform() {
		org.jbox2d.f.common.Transform transform = this.gdx_body.getTransform();
		JTransform gdx_transform = new JTransform(transform);
		return gdx_transform;
	}

	@Override
	public Collection<Fixture> getFixtureList() {
		org.jbox2d.f.dynamics.Fixture fixtures = this.gdx_body.getFixtureList();

		List<Fixture> fixs = JUtils.newList();
		while (fixtures != null) {
			JFixture F = new JFixture(fixtures);
			fixs.add(F);
			fixtures = fixtures.getNext();
		}

		return fixs;
	}

	@Override
	public BodyType getType() {
		final org.jbox2d.f.dynamics.BodyType T = this.gdx_body.getType();
		if (T == org.jbox2d.f.dynamics.BodyType.DYNAMIC) {
			return BodyType.DynamicBody;
		}
		if (T == org.jbox2d.f.dynamics.BodyType.KINEMATIC) {
			return BodyType.KinematicBody;
		}
		if (T == org.jbox2d.f.dynamics.BodyType.STATIC) {
			return BodyType.StaticBody;
		}
		throw new Error();

	}

	@Override
	public boolean isAwake() {
		return this.gdx_body.isAwake();
	}

	public org.jbox2d.f.dynamics.Body getGdxBody() {
		return gdx_body;
	}

}
