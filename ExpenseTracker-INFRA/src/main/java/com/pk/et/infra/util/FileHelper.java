package com.pk.et.infra.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.compress.archivers.ArchiveException;
import org.apache.commons.compress.archivers.ArchiveOutputStream;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Helper for managing files.
 * 
 */
public final class FileHelper {

	private static final String BOM_CHARACTER = "\uFEFF";

	private static final Logger LOG = LoggerFactory.getLogger(FileHelper.class);

	private static final String SEPARATOR = "-";

	public static final String ZIP = ".zip";

	public static final char DOT = '.';

	/**
	 * Constructor.
	 */
	private FileHelper() {
	}

	/**
	 * Unzip a zip entry to a file.
	 * 
	 * @param zipfile
	 *            The zip file containing the zip entry to unzip
	 * @param entry
	 *            The zip entry to unzip.
	 * @param outputDir
	 *            The output directory.
	 * @param outputName
	 *            The name of the unziped file.
	 * @return The file unziped.
	 * @throws IOException
	 */
	public static File unzipEntry(final ZipFile zipfile, final ZipEntry entry,
			final File outputDir, final String outputName) throws IOException {

		final File outputFile = new File(outputDir, outputName);

		final BufferedInputStream inputStream = new BufferedInputStream(
				zipfile.getInputStream(entry));
		final BufferedOutputStream outputStream = new BufferedOutputStream(
				new FileOutputStream(outputFile));

		try {
			IOUtils.copy(inputStream, outputStream);
		} catch (final IOException ioe) {
			LOG.error(ioe.getMessage());
			throw ioe;
		} finally {
			outputStream.close();
			inputStream.close();
		}

		return outputFile;
	}

	/**
	 * This method will delete the zip file if already exists and zip the given
	 * file.
	 * 
	 * @param fileToZip
	 *            the file to zip
	 * @param outputFile
	 *            the the zip file to generate
	 * @param deleteBefore
	 *            true if the target zip file must be deleted if exists.
	 * @return the zipped file
	 */
	public static File zipFile(final File fileToZip, final File outputFile,
			final boolean deleteBefore) throws FileHelperException {
		if (deleteBefore && outputFile.exists()) {
			outputFile.delete();
		}
		OutputStream out;
		try {
			out = new FileOutputStream(outputFile);
			final ArchiveOutputStream os = new ArchiveStreamFactory()
					.createArchiveOutputStream("zip", out);
			os.putArchiveEntry(new ZipArchiveEntry(fileToZip.getName()));
			IOUtils.copy(new FileInputStream(fileToZip), os);
			os.closeArchiveEntry();
			os.finish();
			out.close();
			return outputFile;

		} catch (final FileNotFoundException e) {
			throw new FileHelperException("error while zipping file", e);
		} catch (final ArchiveException e) {
			throw new FileHelperException("error while zipping file", e);
		} catch (final IOException e) {
			throw new FileHelperException("error while zipping file", e);
		}

	}

	/**
	 * This method will delete the zip file if already exists and zip the given
	 * file
	 * 
	 * @param file
	 *            the file to zip
	 * @param zipFile
	 *            Location of the ziped file
	 */
	public static File zipFile(final File file, final File zipFileDir)
			throws IOException {
		LOG.info("zip file {} to {}", new Object[] { file, zipFileDir });
		FileOutputStream fileOutputStream = null;
		ZipOutputStream zipOutputStream = null;
		File outputFile = null;
		if (null != file) {
			try {
				createDirectory(zipFileDir.getAbsolutePath());
				outputFile = new File(zipFileDir.getAbsolutePath()
						+ File.separator + removeFileExtension(file.getName())
						+ ZIP);
				if (outputFile.exists()) {
					outputFile.delete();
				}
				fileOutputStream = new FileOutputStream(outputFile);

				zipOutputStream = new ZipOutputStream(new BufferedOutputStream(
						fileOutputStream));
				addZipEntry(file, zipOutputStream);

			} catch (final IOException ioe) {
				LOG.error(ioe.getMessage());
				throw ioe;
			} finally {
				if (null != fileOutputStream) {
					fileOutputStream.close();
				}
				if (null != zipOutputStream) {
					zipOutputStream.close();
				}
			}
		}
		return outputFile;
	}

	/**
	 * this is an utility method to create a zipEnrty
	 */
	private static void addZipEntry(final File file, final ZipOutputStream out)
			throws IOException {
		final FileInputStream fi = new FileInputStream(file);
		final ZipEntry entry = new ZipEntry(file.getName());
		out.putNextEntry(entry);
		IOUtils.copy(fi, out);
		out.closeEntry();
		out.close();
		if (null != fi) {
			fi.close();
		}

	}

	/**
	 * Copy files to directories.
	 * 
	 * @param targetDirectories
	 *            directories where to copy.
	 * @param files
	 *            the reports to copy.
	 * @throws IOException
	 *             thrown when any error occurs.
	 */
	public static void copyFilesToDirectories(
			final List<File> targetDirectories, final List<File> files)
			throws IOException {
		for (final File file : files) {
			for (final File targetDirectory : targetDirectories) {
				LOG.info("copying file {} to {}", file.getAbsolutePath(),
						targetDirectory.getAbsolutePath());
				FileUtils.copyFileToDirectory(file, targetDirectory);
			}
		}
	}

	/**
	 * Move a file to a specific directory.
	 * 
	 * @param file
	 *            the file to move
	 * @param targetDirectory
	 *            the target directory
	 * @throws IOException
	 *             thrown when any error occurs.
	 */
	public static void moveFileToDirectory(final File file,
			final File targetDirectory) throws IOException {
		if (file.exists()) {
			final File destFile = new File(targetDirectory.getAbsolutePath()
					+ File.separator + file.getName());
			if (destFile.exists()) {
				FileUtils.deleteQuietly(destFile);
			}
			FileUtils.moveFileToDirectory(file, targetDirectory, true);
		}
	}

	/**
	 * Delete a file, no error if an error occur.
	 * 
	 * @param file
	 *            file to delete
	 */
	public static void deleteQuietly(final File file) {
		LOG.info("delete file {}", file);
		FileUtils.deleteQuietly(file);
	}

	/**
	 * Delete a directory and its contains.
	 * 
	 * @param directory
	 *            directory to delete
	 * @throws IOException
	 *             thrown when any error occurs.
	 */
	public static void deleteDirectory(final File directory) throws IOException {
		LOG.info("delete directory {}", directory);
		FileUtils.deleteDirectory(directory);
	}

	/**
	 * Move a file to a specific file destination.
	 * 
	 * @param file
	 *            the file to move.
	 * @param targetFile
	 *            the target file.
	 * @throws IOException
	 *             thrown when any error occurs.
	 */
	public static void moveFileToFile(final File file, final File targetFile)
			throws IOException {
		FileUtils.moveFile(file, targetFile);
	}

	/**
	 * Copy a file to a directory.
	 * 
	 * @param file
	 *            the file to copy.
	 * @param targetDirectory
	 *            the target directory.
	 * @throws IOException
	 *             thrown when any error occurs.
	 */
	public static void copyFileToDirectory(final File file,
			final File targetDirectory) throws IOException {
		FileUtils.copyFileToDirectory(file, targetDirectory, true);
	}

	/**
	 * Copy a file to a specific file destination.
	 * 
	 * @param file
	 *            the file to copy.
	 * @param destFile
	 *            the target file.
	 * @throws IOException
	 *             thrown when any error occurs.
	 */
	public static void copyFileToFile(final File file, final File destFile)
			throws IOException {
		FileUtils.copyFile(file, destFile);
	}

	/**
	 * Clean the content of a directory.
	 * 
	 * @param directory
	 *            the directory to clean.
	 * @throws IOException
	 *             thrown when any error occurs.
	 */
	public static void cleanDirectory(final File directory) throws IOException {
		FileUtils.cleanDirectory(directory);
	}

	/**
	 * Create a directory.
	 * 
	 * @param directoryName
	 *            the name of the directory to create.
	 * @throws IOException
	 *             thrown when any error occurs.
	 * @return the created directory
	 */
	public static File createDirectory(final String directoryName)
			throws IOException {
		final File directory = new File(directoryName);
		FileUtils.forceMkdir(directory);
		return directory;
	}

	/**
	 * Write a content to a text file.
	 * 
	 * @param outputFileName
	 *            the file to write.
	 * @param content
	 *            the content to write.
	 * @throws IOException
	 *             thrown when any error occurs.
	 */
	public static void write(final String outputFileName, final String content)
			throws IOException {
		FileUtils.writeStringToFile(new File(outputFileName), content, "UTF-8");
	}

	/**
	 * Write a content to a text file, in UTF-8 without BOM encoding.
	 * 
	 * @param outputFileName
	 *            the file to write.
	 * @param content
	 *            the content to write.
	 * @throws IOException
	 *             thrown when any error occurs.
	 */
	public static void writeUtf8WithoutBom(final File outputFile,
			final String content) throws IOException {
		FileUtils.writeStringToFile(outputFile, content, "UTF-8");
	}

	/**
	 * Write a content to a text file, in UTF-8 with BOM encoding.
	 * 
	 * @param outputFileName
	 *            the file to write.
	 * @param content
	 *            the content to write.
	 * @throws IOException
	 *             thrown when any error occurs.
	 */
	public static void writeUtf8WithBom(final File outputFile,
			final String content) throws IOException {
		FileUtils.writeStringToFile(outputFile, BOM_CHARACTER + content,
				"UTF-8");
	}

	/**
	 * Write a content to a xml file, beautify the output.
	 * 
	 * @param outputFileName
	 *            the file to write.
	 * @param content
	 *            the content to write.
	 * @throws IOException
	 *             thrown when any error occurs.
	 * @throws DocumentException
	 *             thrown when any error occurs during formating string.
	 */
	public static void writeXml(final String outputFileName,
			final String content) throws IOException, DocumentException {
		final StringWriter sw = formatXML(content);
		FileHelper.write(outputFileName, sw.toString());
	}

	public static void applyXslt(final String xmlFile, final String xslFile,
			final String targetFile) throws FileHelperException {
		LOG.debug("apply XSL {} to {}. Target=", new Object[] { xslFile,
				xmlFile, targetFile });
		try {
			String targetFileToGenerate = targetFile;
			if (xmlFile.equals(targetFile)) {
				targetFileToGenerate = File.createTempFile("applyXsltToXml_",
						".xml").getAbsolutePath();
			}

			final StreamSource xmlSource = new StreamSource(xmlFile);
			final StreamSource xsltSource = new StreamSource(xslFile);

			final FileOutputStream fResult = new FileOutputStream(
					targetFileToGenerate);
			final StreamResult transResult = new StreamResult(fResult);
			final TransformerFactory transFact = TransformerFactory
					.newInstance();
			final Transformer trans = transFact.newTransformer(xsltSource);
			trans.transform(xmlSource, transResult);
			if (xmlFile.equals(targetFile)) {
				copyFileToFile(new File(targetFileToGenerate), new File(
						targetFile));
			}
		} catch (final FileNotFoundException e) {
			throw new FileHelperException("error while applying xslt", e);
		} catch (final TransformerConfigurationException e) {
			throw new FileHelperException("error while applying xslt", e);
		} catch (final TransformerException e) {
			throw new FileHelperException("error while applying xslt", e);
		} catch (final IOException e) {
			throw new FileHelperException("error while applying xslt", e);
		}

	}

	public static void writeXmlUTF8WithBom(final File outputFile,
			final String content) throws IOException, DocumentException {
		final StringWriter sw = formatXML(content);
		FileHelper.writeUtf8WithBom(outputFile, sw.toString());
	}

	static StringWriter formatXML(final String content)
			throws DocumentException, IOException {
		final OutputFormat format = OutputFormat.createPrettyPrint();
		final Document document = DocumentHelper.parseText(content);
		final StringWriter sw = new StringWriter();
		// TODO use java XML APIs instead of this specific implementation class
		// (Plugin: findbugs Key: XFB_XML_FACTORY_BYPASS)
		final XMLWriter writer = new XMLWriter(sw, format);
		writer.write(document);

		sw.close();
		writer.close();
		return sw;
	}

	/**
	 * Encode a file to Base64, then return the base64 encoded string value.
	 * 
	 * @param file
	 *            the file to encode.
	 * @return the base64 encoded string value.
	 * @throws IOException
	 *             thrown when any error occurs.
	 */
	public static String encodeBase64(final File file) throws IOException {
		final byte[] data = FileUtils.readFileToByteArray(file);

		final byte[] encodedData = Base64.encodeBase64(data);

		return new String(encodedData);
	}

	/**
	 * Decode a base64 encoded string value, then return the corresponding
	 * decoded file.
	 * 
	 * @param encodedData
	 *            the base64 encoded string value.
	 * @param destFile
	 *            the corresponding decoded file.
	 * @throws IOException
	 *             thrown when any error occurs.
	 */
	public static void decodeBase64(final String encodedData,
			final File destFile) throws IOException {
		final byte[] data = Base64.decodeBase64(encodedData.getBytes());
		FileUtils.writeByteArrayToFile(destFile, data);
	}

	/**
	 * Calculate a checksum file.
	 * 
	 * @param file
	 *            the file.
	 * @return the calculated checksum.
	 * @throws IOException
	 *             thrown when any error occurs.
	 */
	public static long checksum(final File file) throws IOException {
		return FileUtils.checksumCRC32(file);
	}

	public static String convertStreamToString(final InputStream is) {
		/*
		 * To convert the InputStream to String we use the Reader.read(char[]
		 * buffer) method. We iterate until the Reader return -1 which means
		 * there's no more data to read. We use the StringWriter class to
		 * produce the string.
		 */
		String returnedString = "";
		if (is != null) {
			try {
				final Writer writer = new StringWriter();

				final char[] buffer = new char[1024];
				try {
					final Reader reader = new BufferedReader(
							new InputStreamReader(is, "UTF-8"));
					int n;
					while ((n = reader.read(buffer)) != -1) {
						writer.write(buffer, 0, n);
					}
				} catch (final IOException ioe) {
					LOG.error(ioe.getMessage());
					throw ioe;
				} finally {
					is.close();
				}
				returnedString = writer.toString();
			} catch (final UnsupportedEncodingException e) {
				LOG.error("error while converting stream to string", e);
			} catch (final IOException e) {
				LOG.error("error while converting stream to string", e);
			}
		} else {
			returnedString = "";
		}
		return returnedString;
	}

	/**
	 * for word abc.xls will return abc-xls
	 * 
	 * @param word
	 * @param index
	 * @return
	 */
	public static String removeWordingsAfterLastIndex(final String word,
			final char index) {
		if (false == StringUtil.isNullOrEmptyString(word)
				&& word.lastIndexOf(index) > 0) {
			return word.substring(0, word.lastIndexOf(index))
					+ SEPARATOR
					+ word.subSequence(word.lastIndexOf(index) + 1,
							word.length());
		} else {
			return word;
		}
	}

	public static String removeFileExtension(final String name) {
		if (name != null && 0 < name.length()) {
			final int index = name.lastIndexOf(".");
			if (0 < index) {
				return name.substring(0, index);
			} else {
				return name;
			}
		} else {
			return name;
		}
	}

	/**
	 * Same as dos2unix command on unix system.
	 * 
	 * @param fileToConvert
	 * @throws IOException
	 */
	public static void dos2Unix(final File fileToConvert) throws IOException {
		final List<String> lines = FileUtils.readLines(fileToConvert);
		for (final String line : lines) {
			line.replaceAll("\r\n", "\n");
		}
		FileUtils.writeLines(fileToConvert, null, lines, "\n");
	}

	/**
	 * Same as unix2Dos command on unix system.
	 * 
	 * @param fileToConvert
	 * @throws IOException
	 */
	public static void unix2Dos(final File fileToConvert) throws IOException {
		final List<String> lines = FileUtils.readLines(fileToConvert);
		for (final String line : lines) {
			line.replaceAll("\n", "\r\n");
		}
		FileUtils.writeLines(fileToConvert, null, lines, "\r\n");
	}

}
