package com.pk.et.infra.bi;

/**
 * Allows defining an alignment on an object.
 * 
 * @param <T>
 *            The object type on which we define an alignment
 */
public interface AlignmentDefinition<T> {

	/**
	 * Aligns left.
	 * 
	 * @param target
	 *            The target to align
	 */
	void applyLeftTo(T target);

	/**
	 * Aligns centered.
	 * 
	 * @param target
	 *            The target to align
	 */
	void applyCenterTo(T target);

	/**
	 * Aligns right.
	 * 
	 * @param target
	 *            The target to align
	 */
	void applyRightTo(T target);
}
