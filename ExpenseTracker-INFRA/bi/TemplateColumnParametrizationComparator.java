package com.pk.et.infra.bi;

import java.util.Comparator;

import com.pk.et.infra.model.bi.TemplateColumnParametrization;
import com.pk.et.infra.model.bi.TemplateColumnParametrizationId;

public class TemplateColumnParametrizationComparator implements
		Comparator<TemplateColumnParametrization> {

	public int compare(final TemplateColumnParametrization o1,
			final TemplateColumnParametrization o2) {
		final int index1 = o1.getIndexPosition();
		final int index2 = o2.getIndexPosition();
		if (index1 == index2) {

			final TemplateColumnParametrizationId id1 = o1.getId();
			final TemplateColumnParametrizationId id2 = o2.getId();
			if (id1 != null && id2 != null) {
				return id1.toString().compareTo(id2.toString());
			} else if (id1 != null && id2 == null) {
				return 1;
			} else if (id1 == null && id2 != null) {
				return -1;
			} else {
				return 0;
			}
		} else if (index1 < index2) {
			return -1;
		} else {
			return 1;
		}
	}
}
