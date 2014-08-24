package com.pk.et.wm.model;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class MarketStatus {
	private MarketPlayer marketPlayer;
	private List<MarkToMarket> mtms;

	public MarketPlayer getMarketPlayer() {
		return this.marketPlayer;
	}

	public void setMarketPlayer(final MarketPlayer marketPlayer) {
		this.marketPlayer = marketPlayer;
	}

	public List<MarkToMarket> getMtms() {
		return this.mtms;
	}

	public void setMtms(final List<MarkToMarket> mtms) {
		this.mtms = mtms;
	}

}
