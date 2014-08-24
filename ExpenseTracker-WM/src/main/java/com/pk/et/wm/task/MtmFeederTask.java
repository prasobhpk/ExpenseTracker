package com.pk.et.wm.task;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionException;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;

import com.pk.et.infra.jms.ScheduledJobProcessor;
import com.pk.et.infra.util.FileHelper;
import com.pk.et.infra.util.Lock;

/**
 * Task to feed mtms.
 */
@ManagedResource(description = "Bean to launch an mtms feed")
public class MtmFeederTask extends ScheduledJobProcessor {

	private static final Logger LOG = LoggerFactory
			.getLogger(MtmFeederTask.class);

	@Autowired
	@Qualifier("mtmFeederJob")
	private Job job;

	@Value("#{feederConf['mtm.file.repo.location']}")
	String fileRepo;

	public static final String MTM_CSV_EXTENSION = "*.csv";
	public static final String FILE_NAME_PATH = "input.file.name";

	@Override
	protected void execute(final com.pk.et.infra.jms.Job job) {
		try {
			executeTask();
		} catch (final JobExecutionException e) {
			LOG.error(
					"JobExecutionException thrown while running mtm feeding, exception : {}",
					e.getMessage());
		} catch (final IOException e) {
			LOG.error(
					"IOException thrown while running mtm feeding, exception : {}",
					e.getMessage());
		}
	}

	/**
	 * Launch a feed for mtms.
	 * 
	 * @throws JobExecutionAlreadyRunningException
	 *             thrown if job is already running.
	 * @throws JobRestartException
	 *             thrown if job restart.
	 * @throws JobInstanceAlreadyCompleteException
	 *             thrown if job is already completed.
	 * @throws IOException
	 *             thrown on IO errors.
	 * @throws JobParametersInvalidException
	 *             thrown if job parameters are invalid.
	 */
	@ManagedOperation(description = "Launch a manual events feed")
	public void executeTask() throws JobExecutionAlreadyRunningException,
			JobRestartException, JobInstanceAlreadyCompleteException,
			IOException, JobParametersInvalidException {
		LOG.info("run mtm feeding");

		try {
			// this.lockService.waitForTheLock(Lock.MTM_IMPORT, "MTM");
			final File rootDir = FileHelper.createDirectory(this.fileRepo);
			launchJob(rootDir);
		} catch (final JobExecutionAlreadyRunningException je) {
			LOG.error(je.getMessage());
			throw je;
		} finally {
			this.lockService.releaseLock(Lock.MTM_IMPORT);
		}
		LOG.info("run mtm feeding done.");
	}

	void launchJob(final File directory)
			throws JobExecutionAlreadyRunningException, JobRestartException,
			JobInstanceAlreadyCompleteException, JobParametersInvalidException {

		String multiResourcesPath = directory.getAbsolutePath()
				+ File.separator + MTM_CSV_EXTENSION;
		multiResourcesPath = multiResourcesPath.replace('\\', '/');

		final JobParametersBuilder jobParametersBuilder = this.jobParametersFactory
				.createDefaultParametersBuilder()//
				.addString(FILE_NAME_PATH, multiResourcesPath);
		final JobParameters jobParameters = jobParametersBuilder
				.toJobParameters();

		this.jobLauncher.run(this.job, jobParameters);
	}

}
