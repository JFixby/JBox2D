package org.box2d.r3.jbox2d.f;

import org.box2d.jfixby.api.PolygonShape;
import org.jbox2d.f.common.Vector2;

import com.jfixby.cmns.api.collections.Collection;
import com.jfixby.cmns.api.geometry.ClosedPolygonalChain;
import com.jfixby.cmns.api.geometry.Float2;
import com.jfixby.cmns.api.geometry.Geometry;

public class JPolygonShape extends JShape implements PolygonShape {

	private org.jbox2d.f.collision.shapes.PolygonShape gdx_shape;
	final ClosedPolygonalChain chain;

	public JPolygonShape() {
		this.gdx_shape = new org.jbox2d.f.collision.shapes.PolygonShape();
		chain = Geometry.newClosedPolygonalChain();
	}

	public JPolygonShape(org.jbox2d.f.collision.shapes.PolygonShape gdx_shape2) {
		this.gdx_shape = gdx_shape2;
		chain = Geometry.newClosedPolygonalChain();
	}

	@Override
	public void setAsBox(double half_width, double half_height) {
		gdx_shape.setAsBox((float) half_width, (float) half_height);
	}

	Vector2[] tmp = null;

	private Vector2[] wrap(Collection<Float2> vertices) {
		int N = vertices.size();

		if (tmp == null) {
			tmp = new Vector2[N];
		}
		if (tmp.length != N) {
			tmp = new Vector2[N];
		}
		for (int i = 0; i < N; i++) {
			tmp[i] = new Vector2();
			tmp[i].x = (float) vertices.getElementAt(i).getX();
			tmp[i].y = (float) vertices.getElementAt(i).getY();
			// = ((GdxPoint2D) vertices.getElementAt(i)).getGdxPoint();
		}
		return tmp;
	}

	@Override
	public org.jbox2d.f.collision.shapes.PolygonShape getGdxShape() {
		return gdx_shape;
	}

	@Override
	public void update(org.jbox2d.f.collision.shapes.Shape gdx_shape) {
		this.gdx_shape = (org.jbox2d.f.collision.shapes.PolygonShape) gdx_shape;
	}

	final Vector2 tmpV = new Vector2();

	@Override
	public ClosedPolygonalChain getClosedPolygonalChain() {
		final int N = this.gdx_shape.getVertexCount();
		chain.setSize(N);
		for (int i = 0; i < N; i++) {
			final Vector2 vx = this.gdx_shape.getVertex(i);
			chain.getVertex(i).relative().set(vx.x, vx.y);
		}
		return chain;
	}

	@Override
	public void setVertices(Collection<Float2> vertices) {
		gdx_shape.set(this.wrap(vertices), vertices.size());
	}
}
