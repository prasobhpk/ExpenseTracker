package com.pk.et.infra.bi;

import java.util.ArrayList;
import java.util.List;

/**
 * All the file format.
 */
public enum FileFormat {
	PDF("pdf", "application/pdf"), //

	CSV("csv", "text/csv"), //
	XLS("xls", "application/excel"), //
	TXT("txt", "text/plain"), //

	DOC("doc", "application/msword");

	public static final String FILE_BASENAME_EXTENSION_SEPARATOR = ".";

	/**
	 * Value of the file format. Example, "pdf" for the pdf format, "xls" for
	 * the excel format and so on.
	 */
	private final String value;

	private final String mimeType;

	/**
	 * The constructor.
	 * 
	 * @param value
	 *            , the value to set.
	 * @param reasonIfNotSupported
	 *            Reason if the file format is not supported, null if the format
	 *            is supported by the generator.
	 */
	private FileFormat(final String value, final String mimeType) {
		this.value = value;
		this.mimeType = mimeType;
	}

	/**
	 * Value of the file format. Example, "pdf" for the pdf format, "xls" for
	 * the excel format and so on.
	 * 
	 * @return the value.
	 */
	public String getValue() {
		return this.value;
	}

	/**
	 * @return the mimeType.
	 */
	public String getMimeType() {
		return this.mimeType;
	}

	public List<String> getAllMimeTypes() {
		List<String> allMimeTypes = new ArrayList<String>();
		if (this == FileFormat.XLS) {
			allMimeTypes = getAllXlsMimeTypes();
			return allMimeTypes;
		} else if (this == FileFormat.DOC) {
			allMimeTypes = getAllDocMimeTypes();
		} else {
			allMimeTypes.add(this.getMimeType());
		}
		return allMimeTypes;
	}

	private List<String> getAllDocMimeTypes() {
		final List<String> allMimeTypes = new ArrayList<String>();
		allMimeTypes
				.add("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
		allMimeTypes.add("application/vnd.ms-word.document.macroEnabled.12");
		allMimeTypes.add(FileFormat.DOC.getMimeType());
		return allMimeTypes;
	}

	private List<String> getAllXlsMimeTypes() {
		final List<String> allMimeTypes = new ArrayList<String>();
		allMimeTypes.add(FileFormat.XLS.getMimeType());
		allMimeTypes.add("application/vnd.ms-excel.sheet.macroEnabled.12");// xlsm
		allMimeTypes
				.add("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		allMimeTypes.add("application/vnd.ms-excel");
		allMimeTypes
				.add("application/vnd.ms-excel.sheet.binary.macroEnabled.12");// xlsb
		return allMimeTypes;
	}

	/**
	 * get the DPS supporting file formats
	 */
	public static List<FileFormat> getDPSFileFormats() {
		final List<FileFormat> dpsFileFormats = new ArrayList<FileFormat>();
		dpsFileFormats.add(FileFormat.PDF);
		dpsFileFormats.add(FileFormat.CSV);
		dpsFileFormats.add(FileFormat.XLS);
		return dpsFileFormats;
	}

	public static FileFormat getFileFormatByMimeType(final String mimeType) {
		FileFormat matchingFormat = null;
		final FileFormat[] formats = FileFormat.values();
		int idx = 0;
		while (matchingFormat == null && idx < formats.length) {
			if (formats[idx].mimeType.equals(mimeType)) {
				matchingFormat = formats[idx];
			}
			idx++;
		}
		return matchingFormat;
	}

	public FileFormat getFileFormatsByAllMimeType(final String mimeType) {
		FileFormat matchingFormat = getFileFormatByMimeType(mimeType);
		if (matchingFormat == null) {
			if (getAllXlsMimeTypes().contains(mimeType)) {
				matchingFormat = FileFormat.XLS;
			} else if (getAllDocMimeTypes().contains(mimeType)) {
				matchingFormat = FileFormat.DOC;
			}
		}
		return matchingFormat;
	}

	public static String getDefaultExcelFormat() {
		return "application/vnd.ms-excel";
	}
}
