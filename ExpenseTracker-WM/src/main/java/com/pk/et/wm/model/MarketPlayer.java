package com.pk.et.wm.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class MarketPlayer {
	private String gainer;
	private String loser;

	public String getGainer() {
		return this.gainer;
	}

	public void setGainer(final String gainer) {
		this.gainer = gainer;
	}

	public String getLoser() {
		return this.loser;
	}

	public void setLoser(final String loser) {
		this.loser = loser;
	}

}
