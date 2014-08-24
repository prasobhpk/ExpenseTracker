package com.pk.et.feed.mtm.batch;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.apache.commons.io.IOUtils;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;

import com.pk.et.infra.jms.Job;
import com.pk.et.infra.jms.JobSender;
import com.pk.et.infra.jms.ScheduledJobProcessor;
import com.pk.et.infra.model.ConfigurationItem;
import com.pk.et.infra.service.IConfigurationItemService;
import com.pk.et.infra.util.ETConstants;
import com.pk.et.infra.util.FileHelper;

@ManagedResource(description = "Bean to launch an mtms zip file from nse")
public class NseMtmDownloaderTask extends ScheduledJobProcessor {
	private static final Logger LOG = LoggerFactory
			.getLogger(NseMtmDownloaderTask.class);
	private static final String[] months = new String[] { "JAN", "FEB", "MAR",
			"APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC" };
	@Autowired(required = true)
	@Qualifier("configurationItemService")
	IConfigurationItemService configurationItemService;

	@Value("#{feederConf['mtm.file.repo.location']}")
	String fileRepo;

	@Value("#{feederConf['mtm.file.base.url']}")
	String baseUrl;
	@Autowired
	JobSender jobSender;

	@ManagedOperation(description = "Launch a manual mtm zip download")
	public void download() {
		final ConfigurationItem item = this.configurationItemService
				.findByKey(ETConstants.LAST_FEED_DATE_KEY);
		final DateTimeFormatter dtf = DateTimeFormat
				.forPattern(ETConstants.DATE_FORMAT);
		LocalDate startDate = dtf.parseLocalDate(item.getDefaultValue());

		try {
			final File rootDir = FileHelper.createDirectory(this.fileRepo);
			final LocalDate endDate = LocalDate.now().plusDays(1);
			while (startDate.isBefore(endDate)) {
				final String year = "" + startDate.getYear();
				String date = "" + startDate.getDayOfMonth();
				date = date.length() == 1 ? "0" + date : date;
				final String month = months[startDate.getMonthOfYear() - 1];
				final URL url = new URL(this.baseUrl + year + "/" + month + "/"
						+ "cm" + date + month + year + "bhav.csv.zip");
				final URLConnection conn = url.openConnection();
				InputStream in = null;
				try {
					final File tempFile = File.createTempFile("nseZip", null);
					tempFile.deleteOnExit();
					in = conn.getInputStream();
					final FileOutputStream out = new FileOutputStream(tempFile);
					IOUtils.copy(in, out);

					final ZipFile zipFile = new ZipFile(tempFile);
					final Enumeration<? extends ZipEntry> enumeration = zipFile
							.entries();
					while (enumeration.hasMoreElements()) {
						final ZipEntry zipEntry = enumeration.nextElement();
						unzipEntry(zipFile, zipEntry, rootDir);
					}
					zipFile.close();
				} catch (final Exception e) {
					LOG.error(
							"Error while downloading and processing mtm from nse url:{} ,error :{}",
							new Object[] { url, e.getMessage() });
				} finally {
					if (in != null) {
						in.close();
					}
				}
				startDate = startDate.plusDays(1);
			}
			item.setDefaultValue(startDate.getDayOfMonth() + "-"
					+ startDate.getMonthOfYear() + "-" + startDate.getYear());
			this.configurationItemService.save(item);
			this.jobSender.sendJob(Job.FEEDER_MTM);
		} catch (final IOException e) {
			LOG.error("Error while nse mtm download and process task", e);
		}
	}

	void unzipEntry(final ZipFile zipFile, final ZipEntry zipEntry,
			final File directory) throws IOException {
		FileHelper.unzipEntry(zipFile, zipEntry, directory, zipEntry.getName());
	}

	@Override
	protected void execute(final Job job) {

	}
}
