package org.box2d.r3.jbox2d.d;

import org.jbox2d.d.common.Vector2;

import com.jfixby.scarabei.api.floatn.ReadOnlyFloat2;
import com.jfixby.scarabei.api.floatn.Float2;
import com.jfixby.scarabei.red.geometry.RedPoint;

public class JPoint2D extends RedPoint implements Float2 {
	final private Vector2 gdx_vector;

	public JPoint2D(Vector2 position) {
		gdx_vector = position;
	}

	public Vector2 getGdxPoint() {
		return gdx_vector;
	}

	public JPoint2D update() {
		this.gdx_vector.x =  this.getX();
		this.gdx_vector.y =  this.getY();
		return this;
	}

	@Override
	public JPoint2D setXY(double x, double y) {
		super.setXY(x, y);
		return update();
	}

	@Override
	public JPoint2D setX(double x) {
		super.setX(x);
		return update();
	}

	@Override
	public JPoint2D setY(double y) {
		super.setY(y);
		return update();
	}

	@Override
	public JPoint2D set(ReadOnlyFloat2 other) {
		super.set(other);
		return update();
	}

	@Override
	public JPoint2D setXY() {
		super.setXY();
		return update();
	}

	@Override
	public JPoint2D add(ReadOnlyFloat2 offset) {
		super.add(offset);
		return update();
	}

	@Override
	public JPoint2D addX(double delta) {
		super.addX(delta);
		return update();
	}

	@Override
	public JPoint2D addY(double delta) {
		super.addY(delta);
		return update();
	}

	@Override
	public JPoint2D add(double deltaX, double deltaY) {
		super.add(deltaX, deltaY);
		return update();
	}

}
