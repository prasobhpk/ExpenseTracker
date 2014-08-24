package com.pk.et.infra.bi;

/**
 * Overrides the alignment of a template column.
 * 
 * <p>
 * This enum is intended to be stored in DB and used as a way to define
 * alignments without relying on switch/case constructs, by using a Visitor
 * pattern.
 * </p>
 * <p>
 * Using alignments:
 * <ol>
 * <li>Implement an {@link AlignmentDefinition}</li>
 * <li>Retrieve your Alignment from wherever it is defined</li>
 * <li>Apply the alignment on your target with your definition using
 * {@link #applyTo(AlignmentDefinition, Object)}</li>
 * </ol>
 * </p>
 * 
 */
public enum TemplateColumnAlignment {

	LEFT {
		@Override
		public <T> void applyTo(final AlignmentDefinition<T> definition,
				final T target) {
			definition.applyLeftTo(target);
		}
	},
	CENTER {

		@Override
		public <T> void applyTo(final AlignmentDefinition<T> definition,
				final T target) {
			definition.applyCenterTo(target);
		}
	},
	RIGHT {

		@Override
		public <T> void applyTo(final AlignmentDefinition<T> definition,
				final T target) {
			definition.applyRightTo(target);
		}
	};

	/**
	 * Applies the alignment to a target, using a definition to choose the
	 * alignment to apply.
	 * 
	 * @param <T>
	 *            The target type
	 * @param definition
	 *            The definition telling how to apply each alignment
	 * @param target
	 *            The target object to align
	 */
	public abstract <T> void applyTo(AlignmentDefinition<T> definition, T target);
}
