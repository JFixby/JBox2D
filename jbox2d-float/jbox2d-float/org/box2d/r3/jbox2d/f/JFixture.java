package org.box2d.r3.jbox2d.f;

import org.box2d.jfixby.api.Box2DBody;
import org.box2d.jfixby.api.Fixture;
import org.box2d.jfixby.api.Shape;
import org.box2d.jfixby.api.ShapeType;

import com.jfixby.cmns.api.log.L;

public class JFixture implements Fixture {

	@Override
	public String toString() {
		return "GdxFixture [gdx_fixture="
				+ gdx_fixture.getFilterData().categoryBits + "]";
	}

	private org.jbox2d.f.dynamics.Fixture gdx_fixture;

	public JFixture(org.jbox2d.f.dynamics.Fixture f) {
		this.gdx_fixture = f;
	}

	@Override
	public Box2DBody getBody() {
		throw new Error();
	}

	@Override
	public ShapeType getType() {
		final org.jbox2d.f.collision.shapes.ShapeType T = this.gdx_fixture
				.getType();
		if (T == org.jbox2d.f.collision.shapes.ShapeType.POLYGON) {
			return ShapeType.Polygon;
		}
		if (T == org.jbox2d.f.collision.shapes.ShapeType.CIRCLE) {
			return ShapeType.Circle;
		}
		if (T == org.jbox2d.f.collision.shapes.ShapeType.EDGE) {
			return ShapeType.Edge;
		}
		if (T == org.jbox2d.f.collision.shapes.ShapeType.CHAIN) {
			return ShapeType.Chain;
		}

		throw new Error();
	}

	@Override
	public Shape getShape() {
		org.jbox2d.f.collision.shapes.Shape gdx_shape = this.gdx_fixture
				.getShape();
		if (shape == null) {
			newShape(gdx_shape);
		}

		shape.update(gdx_shape);

		return shape;
	}

	private void newShape(org.jbox2d.f.collision.shapes.Shape gdx_shape) {
		if (gdx_shape.getType() == org.jbox2d.f.collision.shapes.ShapeType.CIRCLE) {
			shape = new JCircleShape(
					(org.jbox2d.f.collision.shapes.CircleShape) gdx_shape);
			return;
		}
		if (gdx_shape.getType() == org.jbox2d.f.collision.shapes.ShapeType.POLYGON) {
			shape = new JPolygonShape(
					(org.jbox2d.f.collision.shapes.PolygonShape) gdx_shape);
			return;
		}

		L.d("", gdx_shape.getType());
		throw new Error();

	}

	JShape shape;

}
