/*******************************************************************************
 * Copyright (c) 2013, Daniel Murphy
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 * 	* Redistributions of source code must retain the above copyright notice,
 * 	  this list of conditions and the following disclaimer.
 * 	* Redistributions in binary form must reproduce the above copyright notice,
 * 	  this list of conditions and the following disclaimer in the documentation
 * 	  and/or other materials provided with the distribution.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 * NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 ******************************************************************************/
/**
 * Created at 12:12:02 PM Jan 23, 2011
 */
package org.jbox2d.d.dynamics.joints;

import org.jbox2d.d.common.MathUtils;
import org.jbox2d.d.common.Rot;
import org.jbox2d.d.common.Settings;
import org.jbox2d.d.common.Vector2;
import org.jbox2d.d.dynamics.SolverData;
import org.jbox2d.d.pooling.IWorldPool;

/**
 * The pulley joint is connected to two bodies and two fixed ground points. The
 * pulley supports a ratio such that: length1 + ratio * length2 <= constant Yes,
 * the force transmitted is scaled by the ratio. Warning: the pulley joint can
 * get a bit squirrelly by itself. They often work better when combined with
 * prismatic joints. You should also cover the the anchor points with static
 * shapes to prevent one side from going to zero length.
 * 
 * @author Daniel Murphy
 */
public class PulleyJoint extends Joint {

	public static final double MIN_PULLEY_LENGTH = 2.0f;

	private final Vector2 m_groundAnchorA = new Vector2();
	private final Vector2 m_groundAnchorB = new Vector2();
	private double m_lengthA;
	private double m_lengthB;

	// Solver shared
	private final Vector2 m_localAnchorA = new Vector2();
	private final Vector2 m_localAnchorB = new Vector2();
	private double m_constant;
	private double m_ratio;
	private double m_impulse;

	// Solver temp
	private int m_indexA;
	private int m_indexB;
	private final Vector2 m_uA = new Vector2();
	private final Vector2 m_uB = new Vector2();
	private final Vector2 m_rA = new Vector2();
	private final Vector2 m_rB = new Vector2();
	private final Vector2 m_localCenterA = new Vector2();
	private final Vector2 m_localCenterB = new Vector2();
	private double m_invMassA;
	private double m_invMassB;
	private double m_invIA;
	private double m_invIB;
	private double m_mass;

	protected PulleyJoint(IWorldPool argWorldPool, PulleyJointDef def) {
		super(argWorldPool, def);
		m_groundAnchorA.set(def.groundAnchorA);
		m_groundAnchorB.set(def.groundAnchorB);
		m_localAnchorA.set(def.localAnchorA);
		m_localAnchorB.set(def.localAnchorB);

		assert (def.ratio != 0.0f);
		m_ratio = def.ratio;

		m_lengthA = def.lengthA;
		m_lengthB = def.lengthB;

		m_constant = def.lengthA + m_ratio * def.lengthB;
		m_impulse = 0.0f;
	}

	public double getLengthA() {
		return m_lengthA;
	}

	public double getLengthB() {
		return m_lengthB;
	}

	public double getCurrentLengthA() {
		final Vector2 p = pool.popVec2();
		m_bodyA.getWorldPointToOut(m_localAnchorA, p);
		p.subLocal(m_groundAnchorA);
		double length = p.length();
		pool.pushVec2(1);
		return length;
	}

	public double getCurrentLengthB() {
		final Vector2 p = pool.popVec2();
		m_bodyB.getWorldPointToOut(m_localAnchorB, p);
		p.subLocal(m_groundAnchorB);
		double length = p.length();
		pool.pushVec2(1);
		return length;
	}

	public Vector2 getLocalAnchorA() {
		return m_localAnchorA;
	}

	public Vector2 getLocalAnchorB() {
		return m_localAnchorB;
	}

	@Override
	public void getAnchorA(Vector2 argOut) {
		m_bodyA.getWorldPointToOut(m_localAnchorA, argOut);
	}

	@Override
	public void getAnchorB(Vector2 argOut) {
		m_bodyB.getWorldPointToOut(m_localAnchorB, argOut);
	}

	@Override
	public void getReactionForce(double inv_dt, Vector2 argOut) {
		argOut.set(m_uB).mulLocal(m_impulse).mulLocal(inv_dt);
	}

	@Override
	public double getReactionTorque(double inv_dt) {
		return 0f;
	}

	public Vector2 getGroundAnchorA() {
		return m_groundAnchorA;
	}

	public Vector2 getGroundAnchorB() {
		return m_groundAnchorB;
	}

	public double getLength1() {
		final Vector2 p = pool.popVec2();
		m_bodyA.getWorldPointToOut(m_localAnchorA, p);
		p.subLocal(m_groundAnchorA);

		double len = p.length();
		pool.pushVec2(1);
		return len;
	}

	public double getLength2() {
		final Vector2 p = pool.popVec2();
		m_bodyB.getWorldPointToOut(m_localAnchorB, p);
		p.subLocal(m_groundAnchorB);

		double len = p.length();
		pool.pushVec2(1);
		return len;
	}

	public double getRatio() {
		return m_ratio;
	}

	@Override
	public void initVelocityConstraints(final SolverData data) {
		m_indexA = m_bodyA.m_islandIndex;
		m_indexB = m_bodyB.m_islandIndex;
		m_localCenterA.set(m_bodyA.m_sweep.localCenter);
		m_localCenterB.set(m_bodyB.m_sweep.localCenter);
		m_invMassA = m_bodyA.m_invMass;
		m_invMassB = m_bodyB.m_invMass;
		m_invIA = m_bodyA.m_invI;
		m_invIB = m_bodyB.m_invI;

		Vector2 cA = data.positions[m_indexA].c;
		double aA = data.positions[m_indexA].a;
		Vector2 vA = data.velocities[m_indexA].v;
		double wA = data.velocities[m_indexA].getOmega();

		Vector2 cB = data.positions[m_indexB].c;
		double aB = data.positions[m_indexB].a;
		Vector2 vB = data.velocities[m_indexB].v;
		double wB = data.velocities[m_indexB].getOmega();

		final Rot qA = pool.popRot();
		final Rot qB = pool.popRot();
		final Vector2 temp = pool.popVec2();

		qA.set(aA);
		qB.set(aB);

		// Compute the effective masses.
		Rot.mulToOutUnsafe(qA, temp.set(m_localAnchorA)
				.subLocal(m_localCenterA), m_rA);
		Rot.mulToOutUnsafe(qB, temp.set(m_localAnchorB)
				.subLocal(m_localCenterB), m_rB);

		m_uA.set(cA).addLocal(m_rA).subLocal(m_groundAnchorA);
		m_uB.set(cB).addLocal(m_rB).subLocal(m_groundAnchorB);

		double lengthA = m_uA.length();
		double lengthB = m_uB.length();

		if (lengthA > 10f * Settings.linearSlop_constraint) {
			m_uA.mulLocal(1.0f / lengthA);
		} else {
			m_uA.setZero();
		}

		if (lengthB > 10f * Settings.linearSlop_constraint) {
			m_uB.mulLocal(1.0f / lengthB);
		} else {
			m_uB.setZero();
		}

		// Compute effective mass.
		double ruA = Vector2.cross(m_rA, m_uA);
		double ruB = Vector2.cross(m_rB, m_uB);

		double mA = m_invMassA + m_invIA * ruA * ruA;
		double mB = m_invMassB + m_invIB * ruB * ruB;

		m_mass = mA + m_ratio * m_ratio * mB;

		if (m_mass > 0.0f) {
			m_mass = 1.0f / m_mass;
		}

		if (data.step.warmStarting) {

			// Scale impulses to support variable time steps.
			m_impulse *= data.step.dtRatio;

			// Warm starting.
			final Vector2 PA = pool.popVec2();
			final Vector2 PB = pool.popVec2();

			PA.set(m_uA).mulLocal(-m_impulse);
			PB.set(m_uB).mulLocal(-m_ratio * m_impulse);

			vA.x += m_invMassA * PA.x;
			vA.y += m_invMassA * PA.y;
			wA += m_invIA * Vector2.cross(m_rA, PA);
			vB.x += m_invMassB * PB.x;
			vB.y += m_invMassB * PB.y;
			wB += m_invIB * Vector2.cross(m_rB, PB);

			pool.pushVec2(2);
		} else {
			m_impulse = 0.0f;
		}
		// data.velocities[m_indexA].v.set(vA);
		data.velocities[m_indexA].setOmega(wA);
		// data.velocities[m_indexB].v.set(vB);
		data.velocities[m_indexB].setOmega(wB);

		pool.pushVec2(1);
		pool.pushRot(2);
	}

	@Override
	public void solveVelocityConstraints(final SolverData data) {
		Vector2 vA = data.velocities[m_indexA].v;
		double wA = data.velocities[m_indexA].getOmega();
		Vector2 vB = data.velocities[m_indexB].v;
		double wB = data.velocities[m_indexB].getOmega();

		final Vector2 vpA = pool.popVec2();
		final Vector2 vpB = pool.popVec2();
		final Vector2 PA = pool.popVec2();
		final Vector2 PB = pool.popVec2();

		Vector2.crossToOutUnsafe(wA, m_rA, vpA);
		vpA.addLocal(vA);
		Vector2.crossToOutUnsafe(wB, m_rB, vpB);
		vpB.addLocal(vB);

		double Cdot = -Vector2.dot(m_uA, vpA) - m_ratio
				* Vector2.dot(m_uB, vpB);
		double impulse = -m_mass * Cdot;
		m_impulse += impulse;

		PA.set(m_uA).mulLocal(-impulse);
		PB.set(m_uB).mulLocal(-m_ratio * impulse);
		vA.x += m_invMassA * PA.x;
		vA.y += m_invMassA * PA.y;
		wA += m_invIA * Vector2.cross(m_rA, PA);
		vB.x += m_invMassB * PB.x;
		vB.y += m_invMassB * PB.y;
		wB += m_invIB * Vector2.cross(m_rB, PB);

		// data.velocities[m_indexA].v.set(vA);
		data.velocities[m_indexA].setOmega(wA);
		// data.velocities[m_indexB].v.set(vB);
		data.velocities[m_indexB].setOmega(wB);

		pool.pushVec2(4);
	}

	@Override
	public boolean solvePositionConstraints(final SolverData data) {
		final Rot qA = pool.popRot();
		final Rot qB = pool.popRot();
		final Vector2 rA = pool.popVec2();
		final Vector2 rB = pool.popVec2();
		final Vector2 uA = pool.popVec2();
		final Vector2 uB = pool.popVec2();
		final Vector2 temp = pool.popVec2();
		final Vector2 PA = pool.popVec2();
		final Vector2 PB = pool.popVec2();

		Vector2 cA = data.positions[m_indexA].c;
		double aA = data.positions[m_indexA].a;
		Vector2 cB = data.positions[m_indexB].c;
		double aB = data.positions[m_indexB].a;

		qA.set(aA);
		qB.set(aB);

		Rot.mulToOutUnsafe(qA, temp.set(m_localAnchorA)
				.subLocal(m_localCenterA), rA);
		Rot.mulToOutUnsafe(qB, temp.set(m_localAnchorB)
				.subLocal(m_localCenterB), rB);

		uA.set(cA).addLocal(rA).subLocal(m_groundAnchorA);
		uB.set(cB).addLocal(rB).subLocal(m_groundAnchorB);

		double lengthA = uA.length();
		double lengthB = uB.length();

		if (lengthA > 10.0f * Settings.linearSlop_constraint) {
			uA.mulLocal(1.0f / lengthA);
		} else {
			uA.setZero();
		}

		if (lengthB > 10.0f * Settings.linearSlop_constraint) {
			uB.mulLocal(1.0f / lengthB);
		} else {
			uB.setZero();
		}

		// Compute effective mass.
		double ruA = Vector2.cross(rA, uA);
		double ruB = Vector2.cross(rB, uB);

		double mA = m_invMassA + m_invIA * ruA * ruA;
		double mB = m_invMassB + m_invIB * ruB * ruB;

		double mass = mA + m_ratio * m_ratio * mB;

		if (mass > 0.0f) {
			mass = 1.0f / mass;
		}

		double C = m_constant - lengthA - m_ratio * lengthB;
		double linearError = MathUtils.abs(C);

		double impulse = -mass * C;

		PA.set(uA).mulLocal(-impulse);
		PB.set(uB).mulLocal(-m_ratio * impulse);

		cA.x += m_invMassA * PA.x;
		cA.y += m_invMassA * PA.y;
		aA += m_invIA * Vector2.cross(rA, PA);
		cB.x += m_invMassB * PB.x;
		cB.y += m_invMassB * PB.y;
		aB += m_invIB * Vector2.cross(rB, PB);

		// data.positions[m_indexA].c.set(cA);
		data.positions[m_indexA].a = aA;
		// data.positions[m_indexB].c.set(cB);
		data.positions[m_indexB].a = aB;

		pool.pushRot(2);
		pool.pushVec2(7);

		return linearError < Settings.linearSlop_constraint;
	}
}
