package com.pk.et.infra.jms;

/**
 * The job enum represents each clips job that could be triggered by a jms
 * message. A job should only be send by the {@link JobSender}.
 */
public enum Job {
	FEEDER_MTM("mtmFeederJob", "feederMtmJobChannel") //
	;
	/**
	 * Name of the job. Each job name should be unique.
	 */
	private final String name;

	/**
	 * Channel where the job is sent. Each channel is bind to a jms queue.
	 */
	private final String channel;

	Job(final String name, final String channel) {
		this.name = name;
		this.channel = channel;
	}

	public String getName() {
		return this.name;
	}

	public String getChannel() {
		return this.channel;
	}
}
