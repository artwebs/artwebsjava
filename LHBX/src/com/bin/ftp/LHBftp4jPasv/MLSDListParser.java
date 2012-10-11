package com.bin.ftp.LHBftp4jPasv;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Properties;
import java.util.StringTokenizer;

/**
 * @date 2011-8-8
 * @author zhanghaitao
 * @version 1.0
 * 
 */
public class MLSDListParser implements FTPListParser {

	/**
	 * Date format 1 for MLSD date facts (supports millis).
	 */
	private static final DateFormat MLSD_DATE_FORMAT_1 = new SimpleDateFormat(
			"yyyyMMddhhmmss.SSS Z");

	/**
	 * Date format 2 for MLSD date facts (doesn't support millis).
	 */
	private static final DateFormat MLSD_DATE_FORMAT_2 = new SimpleDateFormat(
			"yyyyMMddhhmmss Z");

	public FTPFile[] parse(String[] lines) {
		ArrayList list = new ArrayList();
		for (int i = 0; i < lines.length; i++) {
			FTPFile file = parseLine(lines[i]);
			if (file != null) {
				list.add(file);
			}
		}
		int size = list.size();
		FTPFile[] ret = new FTPFile[size];
		for (int i = 0; i < size; i++) {
			ret[i] = (FTPFile) list.get(i);
		}
		return ret;
	}

	/**
	 * Parses a line ad a MLSD response element.
	 * 
	 * @param line
	 *            The line.
	 * @return The file, or null if the line has to be ignored.
	 * @throws FTPListParseException
	 *             If the line is not a valid MLSD entry.
	 */
	private FTPFile parseLine(String line) {
		// Divides facts and name.
		ArrayList list = new ArrayList();
		StringTokenizer st = new StringTokenizer(line, ";");
		while (st.hasMoreElements()) {
			String aux = st.nextToken().trim();
			if (aux.length() > 0) {
				list.add(aux);
			}
		}
		if (list.size() == 0) {
		}
		// Extracts the file name.
		String name = (String) list.remove(list.size() - 1);
		// Parses the facts.
		Properties facts = new Properties();
		for (Iterator i = list.iterator(); i.hasNext();) {
			String aux = (String) i.next();
			int sep = aux.indexOf('=');
			if (sep == -1) {
			}
			String key = aux.substring(0, sep).trim();
			String value = aux.substring(sep + 1, aux.length()).trim();
			if (key.length() == 0 || value.length() == 0) {
			}
			facts.setProperty(key, value);
		}
		// Type.
		int type = 0;
		String typeString = facts.getProperty("type");
		if (typeString == null) {
		} else if ("file".equalsIgnoreCase(typeString)) {
			type = FTPFile.TYPE_FILE;
		} else if ("dir".equalsIgnoreCase(typeString)) {
			type = FTPFile.TYPE_DIRECTORY;
		} else if ("cdir".equalsIgnoreCase(typeString)) {
			// Current directory. Skips...
			return null;
		} else if ("pdir".equalsIgnoreCase(typeString)) {
			// Parent directory. Skips...
			return null;
		} else {
			// Unknown... (link?)... Skips...
			return null;
		}
		// Last modification date.
		Date modifiedDate = null;
		String modifyString = facts.getProperty("modify");
		if (modifyString != null) {
			modifyString += " +0000";
			try {
				modifiedDate = MLSD_DATE_FORMAT_1.parse(modifyString);
			} catch (ParseException e1) {
				try {
					modifiedDate = MLSD_DATE_FORMAT_2.parse(modifyString);
				} catch (ParseException e2) {
					;
				}
			}
		}
		// Size.
		long size = 0;
		String sizeString = facts.getProperty("size");
		if (sizeString != null) {
			try {
				size = Long.parseLong(sizeString);
			} catch (NumberFormatException e) {
				;
			}
			if (size < 0) {
				size = 0;
			}
		}
		// Done!
		FTPFile ret = new FTPFile();
		ret.setType(type);
		ret.setModifiedDate(modifiedDate);
		ret.setSize(size);
		ret.setName(name);
		return ret;
	}

}
