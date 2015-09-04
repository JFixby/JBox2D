package org.box2d.r3.jbox2d.f;

public class JContactListener implements org.jbox2d.f.callbacks.ContactListener {

	private org.box2d.jfixby.api.ContactListener contactListener;

	public JContactListener(org.box2d.jfixby.api.ContactListener contactListener) {
		this.contactListener = contactListener;
	}

	@Override
	public void beginContact(org.jbox2d.f.dynamics.contacts.Contact contact) {
		// throw new Error();
	}

	@Override
	public void endContact(org.jbox2d.f.dynamics.contacts.Contact contact) {
		// throw new Error();
	}

	@Override
	public void preSolve(org.jbox2d.f.dynamics.contacts.Contact contact,
			org.jbox2d.f.collision.Manifold oldManifold) {
		// throw new Error();
	}

	@Override
	public void postSolve(org.jbox2d.f.dynamics.contacts.Contact contact,
			org.jbox2d.f.callbacks.ContactImpulse impulse) {
		// throw new Error();
	}

}
