package com.pk.et.infra.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class StringUtil {
	private static final String CONTINUES = " ...";

	public static final String BLANK = "";

	public static final String SPACE = " ";

	private static final String SEPARATOR = "/";

	public static final String HASH_SEPARATOR = "#";

	public static final String HYPHEN = " - ";

	public static final String COMMA_SEPARATOR = ",";

	public static boolean isNullOrEmptyString(final String str) {
		if (null == str || str.trim().isEmpty()) {
			return true;
		} else {
			return false;
		}

	}

	/**
	 * This method will concatenate given 2 string provided both or not null and
	 * are not empty.otherwise will return empty.
	 * 
	 * @param str1
	 * @param str2
	 * @return
	 */
	public static String concatenateStrings(final String str1, final String str2) {
		if (nullCheck(str1) || nullCheck(str2)) {
			return BLANK;
		} else {
			return str1 + SEPARATOR + str2;
		}

	}

	/**
	 * This method will return true in case given string is null or empty,false
	 * otherwise.
	 * 
	 * @param str
	 * @return
	 */
	protected static boolean nullCheck(final String str) {
		if (null == str || str.trim().equals(BLANK)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * <p>
	 * Checks if a String is not empty ("") and not null.
	 * </p>
	 * 
	 * <pre>
	 * StringUtils.isNotEmpty(null)      = false
	 * StringUtils.isNotEmpty("")        = false
	 * StringUtils.isNotEmpty(" ")       = true
	 * StringUtils.isNotEmpty("bob")     = true
	 * StringUtils.isNotEmpty("  bob  ") = true
	 * </pre>
	 * 
	 * @param str
	 *            the String to check, may be null
	 * @return <code>true</code> if the String is not empty and not null
	 */
	public static boolean isNotEmpty(final String str) {
		return !isEmpty(str);
	}

	/**
	 * <p>
	 * Checks if a String is empty ("") or null.
	 * </p>
	 * 
	 * <pre>
	 * StringUtils.isEmpty(null)      = true
	 * StringUtils.isEmpty("")        = true
	 * StringUtils.isEmpty(" ")       = false
	 * StringUtils.isEmpty("bob")     = false
	 * StringUtils.isEmpty("  bob  ") = false
	 * </pre>
	 * 
	 * <p>
	 * NOTE: This method changed in Lang version 2.0. It no longer trims the
	 * String. That functionality is available in isBlank().
	 * </p>
	 * 
	 * @param str
	 *            the String to check, may be null
	 * @return <code>true</code> if the String is empty or null
	 */
	public static boolean isEmpty(final String str) {
		return str == null || str.isEmpty();
	}

	/**
	 * <p>
	 * Compares two Strings, returning <code>true</code> if they are equal.
	 * </p>
	 * 
	 * <p>
	 * <code>null</code>s are handled without exceptions. Two <code>null</code>
	 * references are considered to be equal. The comparison is case sensitive.
	 * </p>
	 * 
	 * <pre>
	 * StringUtils.equals(null, null)   = true
	 * StringUtils.equals(null, "abc")  = false
	 * StringUtils.equals("abc", null)  = false
	 * StringUtils.equals("abc", "abc") = true
	 * StringUtils.equals("abc", "ABC") = false
	 * </pre>
	 * 
	 * @see java.lang.String#equals(Object)
	 * @param str1
	 *            the first String, may be null
	 * @param str2
	 *            the second String, may be null
	 * @return <code>true</code> if the Strings are equal, case sensitive, or
	 *         both <code>null</code>
	 */
	public static boolean equalsString(final String str1, final String str2) {
		return str1 == null ? str2 == null : str1.equals(str2);
	}

	/**
	 * This method returns an empty String in case given String is null
	 * 
	 * @param stringToBeCheckedForNull
	 * @return
	 */
	public static String convertToEmptyIfNull(
			final String stringToBeCheckedForNull) {
		if (null != stringToBeCheckedForNull) {
			return stringToBeCheckedForNull;
		}
		return BLANK;
	}

	/**
	 * Truncates a list of separated elements in order to show only the
	 * maxLength first elements followed by three dots.
	 * 
	 * @param stringValue
	 *            the string to truncate.
	 * @param separator
	 *            separator to use
	 * @param maxLength
	 *            Max number of elements to display before the three dots.
	 * @return the truncated list.
	 */
	public static String reduce(final String stringValue,
			final String separator, final int maxLength) {
		StringBuilder reducedValue = new StringBuilder();
		if (nullCheck(stringValue)) {
			reducedValue = new StringBuilder("");
		} else if (lessThan(stringValue, separator, maxLength)) {
			reducedValue = new StringBuilder(stringValue);
		} else {
			final String[] elements = stringValue.split(separator);
			reducedValue = new StringBuilder("");
			for (int i = 0; i < maxLength; i++) {
				if (0 < i) {
					reducedValue = reducedValue.append(separator + " ");
				}
				reducedValue = reducedValue.append(elements[i].trim());
			}

			if (maxLength < elements.length) {
				reducedValue = reducedValue.append(CONTINUES);
			}
		}
		return reducedValue.toString();
	}

	/**
	 * Truncates a list of separated elements in order to show only the
	 * maxLength first elements followed by three dots.
	 * 
	 * @param stringValue
	 *            the string to truncate.
	 * @param separator
	 *            separator to use
	 * @param maxLength
	 *            Max number of elements to display before the three dots.
	 * @return the truncated list.
	 */
	public static String reduceToGivenLength(final String stringValue,
			final int maxLength) {
		if (nullCheck(stringValue)) {
			return StringUtil.BLANK;
		} else if (stringValue.length() > maxLength) {
			return stringValue.substring(0, maxLength).concat(CONTINUES);
		} else {
			return stringValue;
		}
	}

	private static boolean lessThan(final String stringValue,
			final String separator, final int maxLength) {
		final String[] elements = stringValue.split(separator);
		return elements.length < maxLength;
	}

	public static String hyphanate(final String str1, final String str2,
			final int maxLength) {
		if (false == nullCheck(str1)) {
			if (false == nullCheck(str2)) {
				return reduceToGivenLength(str1.concat(HYPHEN).concat(str2),
						maxLength);
			}
			return str1;
		} else {
			return BLANK;
		}
	}

	/**
	 * Remove all html tags and return the string without this tags.
	 * 
	 * @param htmlString
	 *            the html string to parse.
	 * @return the string without html tags.
	 */
	public static String removeHtmlTags(final String htmlString) {
		String noHtml = null;
		final Map<String, String> tagsReplacementValues = new HashMap<String, String>();
		tagsReplacementValues.put("<div>", "\n");
		tagsReplacementValues.put("</div>", "\n\n");
		tagsReplacementValues.put("\\<.*?\\>", "");
		tagsReplacementValues.put("''", "'");
		tagsReplacementValues.put("&#38;", "\u0026"); // &
		tagsReplacementValues.put("&#128;", "\u20AC"); // 
		tagsReplacementValues.put("&#139;", "<");
		tagsReplacementValues.put("&#156;", "\u0153"); // 
		tagsReplacementValues.put("&#171;", "\"");
		tagsReplacementValues.put("&#187;", "\"");
		tagsReplacementValues.put("&#224;", "\u00E0"); // à
		tagsReplacementValues.put("&#225;", "\u00E1"); // á
		tagsReplacementValues.put("&#226;", "\u00E2"); // â
		tagsReplacementValues.put("&#231;", "\u00E7"); // ç
		tagsReplacementValues.put("&#232;", "\u00E8"); // è
		tagsReplacementValues.put("&#233;", "\u00E9"); // é
		tagsReplacementValues.put("&#234;", "\u00EA"); // ê
		tagsReplacementValues.put("&#235;", "\u00EB"); // ë
		tagsReplacementValues.put("&#236;", "\u00EC"); // ì
		tagsReplacementValues.put("&#237;", "\u00ED"); // í
		tagsReplacementValues.put("&#238;", "\u00EE"); // î
		tagsReplacementValues.put("&#239;", "\u00EF"); // ï
		tagsReplacementValues.put("&#244;", "\u00F4"); // ô
		tagsReplacementValues.put("&#249;", "\u00F9"); // ù
		tagsReplacementValues.put("&#251;", "\u00FB"); // û
		tagsReplacementValues.put("&nbsp;", "\u00A0"); // NO-BREAK SPACE

		if (htmlString != null) {
			noHtml = htmlString;
			for (final Entry<String, String> tagReplacement : tagsReplacementValues
					.entrySet()) {
				noHtml = noHtml.replaceAll(tagReplacement.getKey(),
						tagReplacement.getValue());
			}
		}
		return noHtml;
	}

	/**
	 * Remove extra \n \t \r\n from the given string to inline the content.
	 * 
	 * @param stringToClean
	 * @return
	 */
	public static String removeCarriageReturnAndTabs(final String stringToClean) {
		return stringToClean.replaceAll("\r\n", "").replaceAll("\\r\\n", "")
				.replaceAll("\\t", "").replaceAll("\\n", "")
				.replaceAll("\n", "");
	}

	public static List<String> formatStringWithBreakLine(final String content,
			final int size) {
		final List<String> result = new ArrayList<String>();
		if (content != null) {
			final String[] contentSplittedByBreakLine = content.split("\n");
			for (final String splittedValue : contentSplittedByBreakLine) {
				result.addAll(StringUtil.formatString(splittedValue, size));
			}
		}
		return result;
	}

	public static List<String> formatString(final String content, final int size) {
		final List<String> result = new ArrayList<String>();
		if (content != null) {
			int fromIndex = 0;
			boolean hasMoreData = !content.isEmpty();
			while (hasMoreData) {
				int toIndex;
				if (fromIndex + size <= content.length()) {
					toIndex = fromIndex + size;
					while (toIndex < content.length() - 1
							&& content.charAt(toIndex) != ' ') {
						toIndex++;
					}
				} else {
					toIndex = content.length();
					hasMoreData = false;
				}
				final String subValue = content.substring(fromIndex, toIndex);
				result.add(subValue.trim());
				fromIndex = toIndex;
			}
		}
		return result;
	}

	public static String getSubStringWithInToken(final String string,
			final String startToken, final String endToken) {
		String subString = "";

		final int tokenStartIndex = string.indexOf(startToken);
		final int tokenEndIndex = string.indexOf(endToken);

		if (tokenEndIndex > 0 && tokenStartIndex >= 0
				&& tokenEndIndex > tokenStartIndex) {
			subString = string.substring(tokenStartIndex + 1, tokenEndIndex);
		}

		return subString;
	}
}
