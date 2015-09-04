package org.box2d.r3.jbox2d.f;

import org.box2d.jfixby.api.Box2DTransform;
import org.jbox2d.f.common.Vector2;

import com.jfixby.cmns.api.geometry.Float2;

public class JTransform implements Box2DTransform {

	// public static final int POS_X = org.jbox2d.common.Transform.POS_X;
	// public static final int POS_Y = org.jbox2d.common.Transform.POS_Y;
	// public static final int COS = org.jbox2d.common.Transform.COS;
	// public static final int SIN = org.jbox2d.common.Transform.SIN;

	org.jbox2d.f.common.Transform gdx_transform;

	public JTransform(org.jbox2d.f.common.Transform gdx_transform) {
		this.gdx_transform = gdx_transform;
	}

	@Override
	public String toString() {
		return "GdxTransform [gdx_transform=" + gdx_transform + "]";
	}

	final Vector2 tmpV = new Vector2();
	final Vector2 tmpO = new Vector2();

	@Override
	public void transform(Float2 temp) {
		tmpV.set((float) temp.getX(), (float) temp.getY());
		org.jbox2d.f.common.Transform.mulToOut(gdx_transform, tmpV, tmpO);
		// L.d("gdx_transform", gdx_transform);
		// L.d("tmpV", tmpV);
		// L.d("tmpO", tmpO);

		temp.set(tmpO.x, tmpO.y);

		//
		// double x = gdx_transform.vals[POS_X] + gdx_transform.vals[COS]
		// * temp.getX() + -gdx_transform.vals[SIN] * temp.getY();
		// double y = gdx_transform.vals[POS_Y] + gdx_transform.vals[SIN]
		// * temp.getX() + gdx_transform.vals[COS] * temp.getY();
		// temp.set(x, y);

	}

	@Override
	public void reverse(Float2 temp_point) {
		throw new Error("Not supported!");
	}

}
