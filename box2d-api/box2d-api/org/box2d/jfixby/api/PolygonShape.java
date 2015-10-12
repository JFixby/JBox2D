package org.box2d.jfixby.api;

import com.jfixby.cmns.api.collections.Collection;
import com.jfixby.cmns.api.floatn.Float2;
import com.jfixby.cmns.api.geometry.ClosedPolygonalChain;

public interface PolygonShape extends Shape {

	void setAsBox(double half_width, double half_height);

	void setVertices(Collection<Float2> vertices);

	ClosedPolygonalChain getClosedPolygonalChain();

	// ClosedPolygonalChain getClosedPolygonalChain();

	// Collection<Vertex> listVertices();

	// ClosedPolygonalChain getPoly();
}
