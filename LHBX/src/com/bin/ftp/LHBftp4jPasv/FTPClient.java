package com.bin.ftp.LHBftp4jPasv;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.InflaterInputStream;

import javax.net.ssl.SSLSocketFactory;

/**
 * @date 2011-8-7
 * @author zhanghaitao
 * @version 1.0
 * 
 */
public class FTPClient {
	private SSLSocketFactory sslSocketFactory = (SSLSocketFactory) SSLSocketFactory
			.getDefault();
	private ArrayList communicationListeners = new ArrayList();
	private FTPConnector connector = new DirectConnector();
	private Object lock = new Object();
	private String host = null;

	private FTPCommunicationChannel communication = null;
	/**
	 * The RegExp Pattern object used to parse the reply to a PASV command.
	 */
	private static final Pattern PASV_PATTERN = Pattern
			.compile("\\d{1,3},\\d{1,3},\\d{1,3},\\d{1,3},\\d{1,3},\\d{1,3}");

	private int port = 0;
	private int security = 0;
	private String username;
	private String password;
	private boolean connected = false;
	private boolean authenticated = false;

	private boolean utf8Supported = false;

	private boolean mlsdSupported = false;

	private boolean modezSupported = false;

	private boolean modezEnabled = false;
	private boolean restSupported = false;

	private long autoNoopTimeout = 0L;
	private AutoNoopTimer autoNoopTimer;
	private long nextAutoNoopTime;

	private InputStream dataTransferInputStream = null;

	private OutputStream dataTransferOutputStream = null;

	/**
	 * The FTPListParser objects registered on the client.
	 */
	private ArrayList listParsers = new ArrayList();

	public FTPClient() {
	}

	public FTPConnector getConnector() {
		synchronized (this.lock) {
			return this.connector;
		}
	}

	public void setConnector(FTPConnector connector) {
		synchronized (this.lock) {
			this.connector = connector;
		}
	}

	public void setSSLSocketFactory(SSLSocketFactory sslSocketFactory) {
		synchronized (this.lock) {
			this.sslSocketFactory = sslSocketFactory;
		}
	}

	public SSLSocketFactory getSSLSocketFactory() {
		synchronized (this.lock) {
			return this.sslSocketFactory;
		}
	}

	private Socket ssl(Socket socket, String host, int port) throws IOException {
		return this.sslSocketFactory.createSocket(socket, host, port, true);
	}

	public String[] connect(String host, int port)
			throws IllegalStateException, IOException,
			FTPIllegalReplyException, FTPException {

		synchronized (this.lock) {
			if (this.connected) {
				throw new IllegalStateException("Client already connected to "
						+ host + " on port " + port);
			}
			Socket connection = null;

			try {
				connection = this.connector.connectForCommunicationChannel(
						host, port);
				if (this.security == 1) {
					connection = ssl(connection, host, port);
				}

				this.communication = new FTPCommunicationChannel(connection,
						"UTF-8");
				for (Iterator i = this.communicationListeners.iterator(); i
						.hasNext();) {
					this.communication
							.addCommunicationListener((FTPCommunicationListener) i
									.next());
				}

				FTPReply wm = this.communication.readFTPReply();
				if (!wm.isSuccessCode()) {
					throw new FTPException(wm);
				}

				this.connected = true;
				// this.authenticated = false;
				// this.parser = null;
				this.host = host;
				this.port = port;
				this.username = null;
				this.password = null;
				this.utf8Supported = false;
				this.restSupported = false;
				this.mlsdSupported = false;
				this.modezSupported = false;
				// this.dataChannelEncrypted = false;

				// Returns the welcome message.
				return wm.getMessages();
			} catch (IOException e) {
				// D'oh!
				throw e;
			} finally {
				// If connection has failed...
				if (!connected) {
					if (connection != null) {
						// Close the connection, 'cause it should be open.
						try {
							connection.close();
						} catch (Throwable t) {
							;
						}
					}
				}
			}
		}
	}

	public FTPFile[] list() throws IllegalStateException, IOException,
			FTPIllegalReplyException, FTPException {
		return list(null);
	}

	/**
	 * This method adds a FTPCommunicationListener to the object.
	 * 
	 * @param listener
	 *            The listener.
	 */
	public void addCommunicationListener(FTPCommunicationListener listener) {
		synchronized (lock) {
			communicationListeners.add(listener);
			if (communication != null) {
				communication.addCommunicationListener(listener);
			}
		}
	}

	/**
	 * This method removes a FTPCommunicationListener previously added to the
	 * object.
	 * 
	 * @param listener
	 *            The listener to be removed.
	 */
	public void removeCommunicationListener(FTPCommunicationListener listener) {
		synchronized (lock) {
			communicationListeners.remove(listener);
			if (communication != null) {
				communication.removeCommunicationListener(listener);
			}
		}
	}

	/**
	 * This method returns a list with all the {@link FTPCommunicationListener}
	 * used by the client.
	 * 
	 * @return A list with all the FTPCommunicationListener used by the client.
	 */
	public FTPCommunicationListener[] getCommunicationListeners() {
		synchronized (lock) {
			int size = communicationListeners.size();
			FTPCommunicationListener[] ret = new FTPCommunicationListener[size];
			for (int i = 0; i < size; i++) {
				ret[i] = (FTPCommunicationListener) communicationListeners
						.get(i);
			}
			return ret;
		}
	}

	/**
	 * This method lists the entries of the current working directory parsing
	 * the reply to a FTP LIST command.
	 * 
	 * The response to the LIST command is parsed through the FTPListParser
	 * objects registered on the client. The distribution of ftp4j contains some
	 * standard parsers already registered on every FTPClient object created. If
	 * they don't work in your case (a FTPListParseException is thrown), you can
	 * build your own parser implementing the FTPListParser interface and add it
	 * to the client by calling its addListParser() method.
	 * 
	 * Calling this method blocks the current thread until the operation is
	 * completed. The operation could be interrupted by another thread calling
	 * abortCurrentDataTransfer(). The list() method will break with a
	 * FTPAbortedException.
	 * 
	 * @param fileSpec
	 *            A file filter string. Depending on the server implementation,
	 *            wildcard characters could be accepted.
	 * @return The list of the files (and directories) in the current working
	 *         directory.
	 * @throws IllegalStateException
	 *             If the client is not connected or not authenticated.
	 * @throws IOException
	 *             If an I/O error occurs.
	 * @throws FTPIllegalReplyException
	 *             If the server replies in an illegal way.
	 * @throws FTPException
	 *             If the operation fails.
	 * @throws FTPDataTransferException
	 *             If a I/O occurs in the data transfer connection. If you
	 *             receive this exception the transfer failed, but the main
	 *             connection with the remote FTP server is in theory still
	 *             working.
	 * @throws FTPAbortedException
	 *             If operation is aborted by another thread.
	 * @throws FTPListParseException
	 *             If none of the registered parsers can handle the response
	 *             sent by the server.
	 * @see FTPListParser
	 * @see FTPClient#addListParser(FTPListParser)
	 * @see FTPClient#getListParsers()
	 * @see FTPClient#abortCurrentDataTransfer(boolean)
	 * @see FTPClient#listNames()
	 * @since 1.2
	 */
	public FTPFile[] list(String fileSpec) throws IllegalStateException,
			IOException, FTPIllegalReplyException, FTPException {
		synchronized (lock) {
			// Is this client connected?
			if (!connected) {
				throw new IllegalStateException("Client not connected");
			}
			// Is this client authenticated?
			if (!authenticated) {
				throw new IllegalStateException("Client not authenticated");
			}
			// ASCII, please!
			communication.sendFTPCommand("TYPE A");
			FTPReply r = communication.readFTPReply();
			touchAutoNoopTimer();
			if (!r.isSuccessCode()) {
				throw new FTPException(r);
			}
			// Prepares the connection for the data transfer.
			FTPDataTransferConnectionProvider provider = openDataTransferChannel();

			String command = "LIST";
			// Adds the file/directory selector.
			if (fileSpec != null && fileSpec.length() > 0) {
				command += " " + fileSpec;
			}
			// Sends the command.
			communication.sendFTPCommand(command);
			Socket dtConnection;
			try {
				try {
					dtConnection = provider.openDataTransferConnection();
				} finally {
					r = communication.readFTPReply();
					touchAutoNoopTimer();
					if (r.getCode() != 150 && r.getCode() != 125) {
						throw new FTPException(r);
					}
				}
			} finally {
				provider.dispose();
			}
			// Fetch the list from the data transfer connection.
			ArrayList lines = new ArrayList();
			NVTASCIIReader dataReader = null;
			try {
				// Opens the data transfer connection.
				dataTransferInputStream = dtConnection.getInputStream();
				// MODE Z enabled?
				if (modezEnabled) {
					dataTransferInputStream = new InflaterInputStream(
							dataTransferInputStream);
				}
				// Let's do it!
				dataReader = new NVTASCIIReader(dataTransferInputStream,
						"UTF-8");
				String line;
				while ((line = dataReader.readLine()) != null) {
					if (line.length() > 0) {
						lines.add(line);
					}
				}
			} catch (IOException e) {
			} finally {
				if (dataReader != null) {
					try {
						dataReader.close();
					} catch (Throwable t) {
						;
					}
				}
				try {
					dtConnection.close();
				} catch (Throwable t) {
					;
				}
				// Consume the result reply of the transfer.
				communication.readFTPReply();
				// Set to null the instance-level input stream.
				dataTransferInputStream = null;
			}
			// Build an array of lines.
			int size = lines.size();
			String[] list = new String[size];
			for (int i = 0; i < size; i++) {
				list[i] = (String) lines.get(i);
			}
			// Parse the list.
			FTPFile[] ret = null;
			// Forces the MLSDListParser.
			MLSDListParser parser = new MLSDListParser();
			ret = parser.parse(list);
			return ret;
		}
	}

	/**
	 * This method opens a data transfer channel.
	 */
	private FTPDataTransferConnectionProvider openDataTransferChannel()
			throws IOException, FTPIllegalReplyException, FTPException {
		if (modezEnabled) {
			// Sends the MODE S command.
			communication.sendFTPCommand("MODE S");
			FTPReply r = communication.readFTPReply();
			touchAutoNoopTimer();
			if (r.isSuccessCode()) {
				modezEnabled = false;
			}
		}
		// Active or passive?
		// if (passive) {
		return openPassiveDataTransferChannel();
		// } else {
		// return openActiveDataTransferChannel();
		// }
	}

	/**
	 * This method opens a data transfer channel in active mode.
	 */
	private FTPDataTransferConnectionProvider openActiveDataTransferChannel()
			throws IOException, FTPIllegalReplyException, FTPException {
		// Create a FTPDataTransferServer object.
		FTPDataTransferServer server = new FTPDataTransferServer() {
			public Socket openDataTransferConnection() {
				Socket socket = super.openDataTransferConnection();
				return socket;
			}
		};
		int port = server.getPort();
		int p1 = port >>> 8;
		int p2 = port & 0xff;
		int[] addr = pickLocalAddress();
		// Send the port command.
		communication.sendFTPCommand("PORT " + addr[0] + "," + addr[1] + ","
				+ addr[2] + "," + addr[3] + "," + p1 + "," + p2);
		FTPReply r = communication.readFTPReply();
		touchAutoNoopTimer();
		if (!r.isSuccessCode()) {
			// Disposes.
			server.dispose();
			// Closes the already open connection (if any).
			try {
				Socket aux = server.openDataTransferConnection();
				aux.close();
			} catch (Throwable t) {
				;
			}
			// Throws the exception.
			throw new FTPException(r);
		}
		return server;
	}

	/**
	 * This method opens a data transfer channel in passive mode.
	 */
	private FTPDataTransferConnectionProvider openPassiveDataTransferChannel()
			throws IOException, FTPIllegalReplyException, FTPException {
		// Send the PASV command.
		communication.sendFTPCommand("PASV");
		// Read the reply.
		FTPReply r = communication.readFTPReply();
		touchAutoNoopTimer();
		if (!r.isSuccessCode()) {
			throw new FTPException(r);
		}
		// Use a regexp to extract the remote address and port.
		String addressAndPort = null;
		String[] messages = r.getMessages();
		for (int i = 0; i < messages.length; i++) {
			Matcher m = PASV_PATTERN.matcher(messages[i]);
			if (m.find()) {
				int start = m.start();
				int end = m.end();
				addressAndPort = messages[i].substring(start, end);
				break;
			}
		}
		if (addressAndPort == null) {
			// The remote server has not sent the coordinates for the
			// data transfer connection.
			throw new FTPIllegalReplyException();
		}
		// Parse the string extracted from the reply.
		StringTokenizer st = new StringTokenizer(addressAndPort, ",");
		int b1 = Integer.parseInt(st.nextToken());
		int b2 = Integer.parseInt(st.nextToken());
		int b3 = Integer.parseInt(st.nextToken());
		int b4 = Integer.parseInt(st.nextToken());
		int p1 = Integer.parseInt(st.nextToken());
		int p2 = Integer.parseInt(st.nextToken());
		final InetAddress remoteAddress;
		// Ignore address?
		// String useSuggestedAddress = System
		// .getProperty(FTPKeys.PASSIVE_DT_USE_SUGGESTED_ADDRESS);
		String useSuggestedAddress = "IP";
		if ("true".equalsIgnoreCase(useSuggestedAddress)
				|| "yes".equalsIgnoreCase(useSuggestedAddress)
				|| "1".equals(useSuggestedAddress)) {
			remoteAddress = InetAddress.getByAddress(new byte[] { (byte) b1,
					(byte) b2, (byte) b3, (byte) b4 });
		} else {
			remoteAddress = InetAddress.getByName(host);
		}
		final int remotePort = (p1 << 8) | p2;
		FTPDataTransferConnectionProvider provider = new FTPDataTransferConnectionProvider() {

			public Socket openDataTransferConnection() {
				// Establish the connection.
				Socket dtConnection = null;
				String remoteHost = remoteAddress.getHostAddress();
				try {
					dtConnection = connector.connectForDataTransferChannel(
							remoteHost, remotePort);
				} catch (IOException e) {
				}
				return dtConnection;
			}

			public void dispose() {
				// nothing to do
			}

		};
		return provider;
	}

	public void login(String username, String password, String account)
			throws IllegalStateException, IOException,
			FTPIllegalReplyException, FTPException {

		synchronized (this.lock) {
			this.authenticated = false;

			this.communication.sendFTPCommand("USER " + username);
			FTPReply r = this.communication.readFTPReply();
			boolean passwordRequired;
			boolean accountRequired;
			switch (r.getCode()) {
			case 230:
				passwordRequired = false;
				accountRequired = false;
				break;
			case 331:
				passwordRequired = true;

				accountRequired = false;
				break;
			case 332:
				passwordRequired = false;
				accountRequired = true;
			default:
				throw new FTPException(r);
			}

			if (passwordRequired) {
				if (password == null) {
					throw new FTPException(331);
				}
				this.communication.sendFTPCommand("PASS " + password);
				r = this.communication.readFTPReply();
				switch (r.getCode()) {
				case 230:
					accountRequired = false;
					break;
				case 332:
					accountRequired = true;
					break;
				default:
					throw new FTPException(r);
				}
			}

			if (accountRequired) {
				if (account == null) {
					throw new FTPException(332);
				}

				this.communication.sendFTPCommand("ACCT " + account);
				r = this.communication.readFTPReply();
				switch (r.getCode()) {
				case 230:
					break;
				default:
					throw new FTPException(r);
				}

			}
			this.authenticated = true;
			this.username = username;
			this.password = password;

		}

		postLoginOperations();

		startAutoNoopTimer();

	}

	public void disconnect(boolean sendQuitCommand)
			throws IllegalStateException, IOException,
			FTPIllegalReplyException, FTPException {
		synchronized (this.lock) {
			if (!this.connected) {
				throw new IllegalStateException("Client not connected");
			}

			if (this.authenticated) {
				stopAutoNoopTimer();
			}

			if (sendQuitCommand) {
				this.communication.sendFTPCommand("QUIT");
				FTPReply r = this.communication.readFTPReply();
				if (!r.isSuccessCode()) {
					throw new FTPException(r);
				}
			}

			this.communication.close();
			this.communication = null;

			this.connected = false;
		}
	}

	/**
	 * Picks the local address for an active data transfer operation.
	 * 
	 * @return The local address as a 4 integer values array.
	 * @throws IOException
	 *             If an unexpected I/O error occurs while trying to resolve the
	 *             local address.
	 */
	private int[] pickLocalAddress() throws IOException {
		// Forced address?
		int[] ret = pickForcedLocalAddress();
		// Auto-detect?
		if (ret == null) {
			ret = pickAutoDetectedLocalAddress();
		}
		// Returns.
		return ret;
	}

	/**
	 * If a local address for active data transfers has been supplied through
	 * the {@link FTPKeys#ACTIVE_DT_HOST_ADDRESS}, it returns it as a 4 elements
	 * integer array; otherwise it returns null.
	 * 
	 * @return The forced local address, or null.
	 */
	private int[] pickForcedLocalAddress() {
		int[] ret = null;
		String aux = FTPKeys.ACTIVE_DT_HOST_ADDRESS;
		if (aux != null) {
			boolean valid = false;
			StringTokenizer st = new StringTokenizer(aux, ".");
			if (st.countTokens() == 4) {
				valid = true;
				int[] arr = new int[4];
				for (int i = 0; i < 4; i++) {
					String tk = st.nextToken();
					try {
						arr[i] = Integer.parseInt(tk);
					} catch (NumberFormatException e) {
						arr[i] = -1;
					}
					if (arr[i] < 0 || arr[i] > 255) {
						valid = false;
						break;
					}
				}
				if (valid) {
					ret = arr;
				}
			}
			if (!valid) {
				// warning to the developer
				System.err.println("WARNING: invalid value \"" + aux
						+ "\" for the " + FTPKeys.ACTIVE_DT_HOST_ADDRESS
						+ " system property. The value should "
						+ "be in the x.x.x.x form.");
			}
		}
		return ret;
	}

	/**
	 * Auto-detects the local network address, and returns it in the form of a 4
	 * elements integer array.
	 * 
	 * @return The detected local address.
	 * @throws IOException
	 *             If an unexpected I/O error occurs while trying to resolve the
	 *             local address.
	 */
	private int[] pickAutoDetectedLocalAddress() throws IOException {
		InetAddress addressObj = InetAddress.getLocalHost();
		byte[] addr = addressObj.getAddress();
		int b1 = addr[0] & 0xff;
		int b2 = addr[1] & 0xff;
		int b3 = addr[2] & 0xff;
		int b4 = addr[3] & 0xff;
		int[] ret = { b1, b2, b3, b4 };
		return ret;
	}

	/**
	 * Enable and disable the auto-noop feature.
	 * 
	 * If the supplied value is greater than 0, the auto-noop feature is
	 * enabled, otherwise it is disabled. If positive, the field is used as a
	 * timeout value (expressed in milliseconds). If autoNoopDelay milliseconds
	 * has passed without any communication between the client and the server, a
	 * NOOP command is automaticaly sent to the server by the client.
	 * 
	 * The default value for the auto noop delay is 0 (disabled).
	 * 
	 * @param autoNoopTimeout
	 *            The duration of the auto-noop timeout, in milliseconds. If 0
	 *            or less, the auto-noop feature is disabled.
	 * 
	 * @since 1.5
	 */
	public void setAutoNoopTimeout(long autoNoopTimeout) {
		synchronized (lock) {
			if (connected && authenticated) {
				stopAutoNoopTimer();
			}
			long oldValue = this.autoNoopTimeout;
			long newValue = autoNoopTimeout;
			this.autoNoopTimeout = autoNoopTimeout;
			if (oldValue != 0 && newValue != 0 && nextAutoNoopTime > 0) {
				nextAutoNoopTime = nextAutoNoopTime - (oldValue - newValue);
			}
			if (connected && authenticated) {
				startAutoNoopTimer();
			}
		}
	}

	/**
	 * Starts the auto-noop timer thread.
	 */
	private void startAutoNoopTimer() {
		if (autoNoopTimeout > 0) {
			autoNoopTimer = new AutoNoopTimer();
			autoNoopTimer.start();
		}
	}

	/**
	 * Stops the auto-noop timer thread.
	 * 
	 * @since 1.5
	 */
	private void stopAutoNoopTimer() {
		if (autoNoopTimer != null) {
			autoNoopTimer.interrupt();
			autoNoopTimer = null;
		}
	}

	/**
	 * Resets the auto noop timer.
	 */
	private void touchAutoNoopTimer() {
		if (autoNoopTimer != null) {
			nextAutoNoopTime = System.currentTimeMillis() + autoNoopTimeout;
		}
	}

	/**
	 * Performs some post-login operations, such trying to detect server support
	 * for utf8.
	 * 
	 * @throws IllegalStateException
	 *             If the client is not connected. Call the connect() method
	 *             before!
	 * @throws IOException
	 *             If an I/O error occurs.
	 * @throws FTPIllegalReplyException
	 *             If the server replies in an illegal way.
	 * @throws FTPException
	 *             If login fails.
	 */
	private void postLoginOperations() throws IllegalStateException,
			IOException, FTPIllegalReplyException, FTPException {
		synchronized (lock) {
			utf8Supported = false;
			restSupported = false;
			mlsdSupported = false;
			modezSupported = false;
			communication.sendFTPCommand("FEAT");
			FTPReply r = communication.readFTPReply();
			if (r.getCode() == 211) {
				String[] lines = r.getMessages();
				for (int i = 1; i < lines.length - 1; i++) {
					String feat = lines[i].trim().toUpperCase();
					// REST STREAM supported?
					if ("REST STREAM".equalsIgnoreCase(feat)) {
						restSupported = true;
						continue;
					}
					// UTF8 supported?
					if ("UTF8".equalsIgnoreCase(feat)) {
						utf8Supported = true;
						communication.changeCharset("UTF-8");
						continue;
					}
					// MLSD supported?
					if ("MLSD".equalsIgnoreCase(feat)) {
						mlsdSupported = true;
						continue;
					}
					// MODE Z supported?
					if ("MODE Z".equalsIgnoreCase(feat)
							|| feat.startsWith("MODE Z ")) {
						modezSupported = true;
						continue;
					}
				}
			}
			// Turn UTF 8 on (if supported).
			if (utf8Supported) {
				communication.sendFTPCommand("OPTS UTF8 ON");
				communication.readFTPReply();
			}
		}
	}

	public void noop() throws IllegalStateException, IOException,
			FTPIllegalReplyException, FTPException {
		synchronized (this.lock) {
			if (!this.connected) {
				throw new IllegalStateException("Client not connected");
			}

			if (!this.authenticated) {
				throw new IllegalStateException("Client not authenticated");
			}

			this.communication.sendFTPCommand("NOOP");
			FTPReply r = this.communication.readFTPReply();
			if (!r.isSuccessCode()) {
				throw new FTPException(r);
			}
			touchAutoNoopTimer();
		}
	}

	private class AutoNoopTimer extends Thread {

		public void run() {
			synchronized (lock) {
				if (nextAutoNoopTime <= 0 && autoNoopTimeout > 0) {
					nextAutoNoopTime = System.currentTimeMillis()
							+ autoNoopTimeout;
				}
				while (!Thread.interrupted() && autoNoopTimeout > 0) {
					// Sleep till the next NOOP.
					long delay = nextAutoNoopTime - System.currentTimeMillis();
					if (delay > 0) {
						try {
							lock.wait(delay);
						} catch (InterruptedException e) {
							break;
						}
					}
					// Is it really time to NOOP?
					if (System.currentTimeMillis() >= nextAutoNoopTime) {
						// Yes!
						try {
							noop();
						} catch (Throwable t) {
							; // ignore...
						}
					}
				}
			}
		}

	}

}
