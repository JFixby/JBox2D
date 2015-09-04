package org.box2d.jfixby.api;

import com.jfixby.cmns.api.collections.Collection;
import com.jfixby.cmns.api.geometry.ClosedPolygonalChain;
import com.jfixby.cmns.api.geometry.Float2;

public interface PolygonShape extends Shape {

	void setAsBox(double half_width, double half_height);

	void setVertices(Collection<Float2> vertices);

	ClosedPolygonalChain getClosedPolygonalChain();

	// ClosedPolygonalChain getClosedPolygonalChain();

	// Collection<Vertex> listVertices();

	// ClosedPolygonalChain getPoly();
}
