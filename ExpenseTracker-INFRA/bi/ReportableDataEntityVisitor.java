package com.pk.et.infra.bi;


/**
 * Visitor for the ReportableDataEntity enumeration
 * 
 */
public interface ReportableDataEntityVisitor<RETURN_TYPE, PARAM_TYPE> {

	RETURN_TYPE visitProduct(ReportableDataEntity reportableDataEntity,
			PARAM_TYPE parameter);

	RETURN_TYPE visitPosition(ReportableDataEntity reportableDataEntity,
			PARAM_TYPE parameter);

	RETURN_TYPE visitMarkToMarker(ReportableDataEntity reportableDataEntity,
			PARAM_TYPE parameter);

	RETURN_TYPE visitProductPrice(ReportableDataEntity reportableDataEntity,
			PARAM_TYPE parameter);

	RETURN_TYPE visitTrades(ReportableDataEntity reportableDataEntity,
			PARAM_TYPE parameter);

	RETURN_TYPE visitProductUnderlyingPricingParameter(
			ReportableDataEntity reportableDataEntity, PARAM_TYPE parameter);

	RETURN_TYPE visitUnderlyings(ReportableDataEntity reportableDataEntity,
			PARAM_TYPE parameter);
}
