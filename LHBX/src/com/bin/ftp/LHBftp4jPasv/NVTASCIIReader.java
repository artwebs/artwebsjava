package com.bin.ftp.LHBftp4jPasv;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

/**
 * @date 2011-8-7
 * @author zhanghaitao
 * @version 1.0
 * 
 */
public class NVTASCIIReader extends Reader {

	private static final String SYSTEM_LINE_SEPARATOR = System
			.getProperty("line.separator");
	private InputStream stream;
	private Reader reader;

	public NVTASCIIReader(InputStream stream, String charsetName)
			throws IOException {
		this.stream = stream;
		this.reader = new InputStreamReader(stream, charsetName);
	}

	@Override
	public void close() throws IOException {
		// TODO Auto-generated method stub
		synchronized (this) {
			this.reader.close();
		}
	}

	@Override
	public int read(char[] cbuf, int off, int len) throws IOException {
		synchronized (this) {
			return this.reader.read(cbuf, off, len);
		}
	}

	public void changeCharset(String charsetName) throws IOException {
		synchronized (this) {
			this.reader = new InputStreamReader(this.stream, charsetName);
		}
	}

	public String readLine() throws IOException {
		StringBuffer buffer = new StringBuffer();
		int previous = -1;
		int current = -1;
		while (true) {
			int i = this.reader.read();
			if (i == -1) {
				if (buffer.length() == 0) {
					return null;
				}
				return buffer.toString();
			}
			previous = current;
			current = i;
			if (/* previous == '\r' && */current == '\n') {
				// End of line.
				return buffer.toString();
			} else if (previous == '\r' && current == 0) {
				// Literal new line.
				buffer.append(SYSTEM_LINE_SEPARATOR);
			} else if (current != 0 && current != '\r') {
				buffer.append((char) current);
			}
		}
	}

}
