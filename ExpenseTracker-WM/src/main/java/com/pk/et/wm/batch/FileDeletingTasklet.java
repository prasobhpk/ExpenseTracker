package com.pk.et.wm.batch;

import java.io.File;
import java.io.FilenameFilter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.UnexpectedJobExecutionException;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.Assert;

import com.pk.et.infra.util.FileHelper;

public class FileDeletingTasklet implements Tasklet, InitializingBean {
	private static final Logger LOG = LoggerFactory
			.getLogger(FileDeletingTasklet.class);
	@Value("#{feederConf['mtm.file.repo.location']}")
	String fileRepo;

	public void afterPropertiesSet() throws Exception {
		Assert.notNull(this.fileRepo, "file must be set");
	}

	public RepeatStatus execute(final StepContribution arg0,
			final ChunkContext arg1) throws Exception {
		LOG.trace("Checking files");
		final File rootDir = FileHelper.createDirectory(this.fileRepo);

		final File[] files = rootDir.listFiles(new FilenameFilter() {
			public boolean accept(final File repo, final String name) {
				return name.endsWith(".csv");
			}
		});

		if (files.length == 0) {
			LOG.trace("No file to parse");
		}

		for (final File file : files) {
			LOG.info("Deleting file : {}", file.getPath());
			final boolean deleted = file.renameTo(new File(file.getPath()
					+ ".DONE"));
			if (!deleted) {
				LOG.warn("Could not delete file : {}", file.getPath());
				throw new UnexpectedJobExecutionException(
						"Could not delete file " + file.getPath());
			} else {
				LOG.info("{} is deleted!", file.getPath());
			}
		}
		return RepeatStatus.FINISHED;
	}

}
