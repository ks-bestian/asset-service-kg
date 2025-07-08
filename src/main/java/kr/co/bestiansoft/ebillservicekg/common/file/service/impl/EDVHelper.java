package kr.co.bestiansoft.ebillservicekg.common.file.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Properties;

import javax.annotation.Resource;
import javax.jcr.Binary;
import javax.jcr.LoginException;
import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.Repository;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.SimpleCredentials;
import javax.jcr.query.Query;
import javax.jcr.query.QueryManager;
import javax.jcr.query.QueryResult;

import org.apache.jackrabbit.JcrConstants;
//import org.apache.jackrabbit.api.JackrabbitValue;
import org.apache.jackrabbit.commons.JcrUtils;
import org.apache.jackrabbit.value.BinaryValue;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;


@Component
public class EDVHelper {

	private static final Logger LOGGER = LoggerFactory.getLogger(EDVHelper.class);
	private static Repository repository = null;

    @Value("${edv.rmi}")
    private String edvUrl;

    @Value("${edv.root-folder}")
    private String edvRoot;

    @Value("${edv.property-path}")
    private String edvPath;

    @Value("${edv.property-name}")
    private String edvName;

    @Value("${edv.datastore-path}")
    private String datastorePath;


	public Repository init() {
        if (null == repository) {
            try {
            	repository = JcrUtils.getRepository(edvUrl);
            	setEdvRootFolder();
            } catch (RepositoryException e) {
            	LOGGER.error(e.getMessage());
			}
        }
        return repository;
    }

	/**
	 * Updates the root folder path for EDV (Electronic Document Vault) in a properties file.
	 * If the properties file already exists and contains the specified property name,
	 * the method will update its value with the new root folder path. If the property does not exist
	 * or the file does not exist, no update will be performed.
	 *
	 * The method works as follows:
	 * 1. Creates a `File` reference to the properties file path defined by `edvPath`.
	 * 2. Verifies if the properties file exists.
	 * 3. Reads the properties file, checks for the presence of the specified property name,
	 *    and updates its value with the new root folder path if the current value is different.
	 * 4. Writes the updated properties back to the file.
	 *
	 */
	public void setEdvRootFolder() {

		String edvRootFolder 	= edvRoot;
		String edvPropertyPath	= edvPath;
		String edvPropertyName	= edvName;

		File directoryProp = new File(edvPropertyPath);

		if(directoryProp.exists()) {
			InputStream is;
			OutputStream os = null;
        	try {
        		is = new FileInputStream(directoryProp);
        		try {
	                Properties props = new Properties();
	                props.load(is);
	                if(props.containsKey(edvPropertyName) && !edvRootFolder.equals(props.getProperty(edvPropertyName).toString())) {
	                	props.setProperty(edvPropertyName, edvRootFolder);
	                	os = new FileOutputStream(directoryProp);
	                	props.store(os, null);
	                }

	            } catch (IOException e) {
	            	LOGGER.error(e.getMessage());
	            } finally {
	                IOUtils.closeQuietly(is);
	                IOUtils.closeQuietly(os);
	            }
			} catch (FileNotFoundException e) {
				LOGGER.error(e.getMessage());
			}
        }
	}

	/**
	 * Retrieves a session by initializing a repository and logging in with provided credentials.
	 * The method attempts to initialize the repository using the `init` method,
	 * then logs in using default credentials ("admin", "admin") to obtain a session.
	 * If login or repository initialization fails, it logs the error and throws an exception.
	 *
	 * @return A valid Session object if login is successful.
	 * @throws Exception If there is a login or repository initialization error.
	 */
	public Session getSession() throws Exception {
		Session session = null;
        try {
            session = init().login(new SimpleCredentials("admin", "admin".toCharArray()));
        } catch (LoginException e) {
        	LOGGER.error(e.getMessage());
        	throw new Exception(e.getMessage());

        } catch (RepositoryException e) {
        	LOGGER.error(e.getMessage());
        	throw new Exception(e.getMessage());
        }
        return session;
    }

	/**
	 * Saves a file to the repository with the given file ID and input stream.
	 * The method stores the input stream as a binary resource under the specified file ID
	 * and sets its metadata, such as MIME type, encoding, and last modified timestamp.
	 *
	 * @param fileId The unique identifier for the file to be saved in the repository.
	 * @param is The input stream containing the file data.
	 * @return The file ID of the saved file.
	 * @throws Exception If there is an error during the repository session handling or file saving process.
	 */
	public String save(String fileId, InputStream is) throws Exception {

        Session session = getSession();

        try {
            Node root = session.getRootNode();
            Node filenode = root.addNode(fileId, JcrConstants.NT_FILE);

            Binary binary = session.getValueFactory().createBinary(is);
            is.close();

            Calendar created = Calendar.getInstance();
            Node resourcenode = filenode.addNode(JcrConstants.JCR_CONTENT, JcrConstants.NT_RESOURCE);
            resourcenode.setProperty(JcrConstants.JCR_MIMETYPE, "application/octest-stream");
            resourcenode.setProperty(JcrConstants.JCR_DATA, binary);
            resourcenode.setProperty(JcrConstants.JCR_ENCODING, "UTF-8");
            resourcenode.setProperty(JcrConstants.JCR_LASTMODIFIED, created);

            session.save();
            session.logout();
        } catch (RepositoryException e) {
        	LOGGER.error(e.getMessage());
        } catch (IOException e) {
        	LOGGER.error(e.getMessage());
        }
        return fileId;
    }

	/**
	 * Downloads a file from the repository based on the provided file ID.
	 * The method retrieves the binary data of the specified file and returns it as an InputStream.
	 * If an error occurs during the retrieval, an exception is logged and thrown to the caller.
	 *
	 * @param fileId The unique identifier for the file to be downloaded from the repository.
	 * @return An InputStream containing the binary data of the requested file, or null if an error occurs.
	 * @throws Exception If there is an error during the repository session handling or file retrieval process.
	 */
	public InputStream download(String fileId) throws Exception {

        InputStream is = null;
        Session session = getSession();
        try {

        	Node root = session.getRootNode();
            Node fileNode = root.getNode(fileId);
            Node resourcenode = fileNode.getNode(JcrConstants.JCR_CONTENT);

            Binary binary = JcrUtils.getBinaryProperty(resourcenode, JcrConstants.JCR_DATA, null);

            is = binary.getStream();
            session.logout();
        } catch (RepositoryException e) {
        	LOGGER.error(e.getMessage());
        }
        return is;
    }

	/**
	 * Deletes a file or node from the repository based on the provided file ID.
	 * The method retrieves the root node of the repository, searches for nodes
	 * matching the given file ID, and removes them. After successful deletion,
	 * the session is saved and logged out. If any repository issues occur during
	 * this process, they are logged.
	 *
	 * @param fileId The unique identifier of the file or node to be deleted from the repository.
	 * @throws Exception If there is an error during the repository session handling or node deletion process.
	 */
	public void delete(String fileId) throws Exception {
        Session session = getSession();
        try {
            Node root = session.getRootNode();
            NodeIterator filenodeite = root.getNodes(fileId);

            if (filenodeite.hasNext()) {
                while (filenodeite.hasNext()) {
                    Node filenode = filenodeite.nextNode();
                    filenode.remove();
                }
            }

            session.save();
            session.logout();
        } catch (RepositoryException e) {
        	LOGGER.error(e.getMessage());
        }
    }

	public void query(){

		try {

	         // Get a session and query manager
	         Session session = getSession();
	         QueryManager queryManager = session.getWorkspace().getQueryManager();

	         // Define the JCR-SQL2 query
	         //String queryStatement = "SELECT * FROM [nt:base]";
	         //String queryStatement = "SELECT * FROM [nt:file]";
	         String queryStatement = "SELECT * FROM [nt:base] AS s WHERE name(s)='0609baaa-14e8-4462-8539-d2363cccbabf'";

	         // Create the query object
	         Query query = queryManager.createQuery(queryStatement, Query.JCR_SQL2);

	         // Execute the query and obtain the results
	         QueryResult result = query.execute();

	         // Iterate through the results and print out the node names
	         NodeIterator nodeIterator = result.getNodes();
	         while (nodeIterator.hasNext()) {
	            Node node = nodeIterator.nextNode();
	            System.out.println("Node name: " + node.getName() + ", path: " + node.getPath());

	            if(JcrConstants.JCR_CONTENT.equals(node.getName())) {
	            	System.out.println("Node size: " + JcrUtils.getBinaryProperty(node, JcrConstants.JCR_DATA, null).getSize());
	            }

	         }

	         // Logout of the session
	         session.logout();

	      } catch (Exception e) {
	    	  LOGGER.error(e.getMessage());
	      }
	   }



	private final int STREAM_BUFFER_LENGTH = 1024;
	private final char[] HEX = "0123456789abcdef".toCharArray();
	private final String DIGEST = "SHA-256";

	private byte[] digestStream(MessageDigest digest, InputStream is) throws IOException {
        byte[] buffer = new byte[STREAM_BUFFER_LENGTH];
        int read = is.read(buffer, 0, STREAM_BUFFER_LENGTH);

        while (read > -1) {
            digest.update(buffer, 0, read);
            read = is.read(buffer, 0, STREAM_BUFFER_LENGTH);
        }
        is.close();

        return digest.digest();
    }

	private String encodeHexString(byte[] value) {
        char[] buffer = new char[value.length * 2];
        for (int i = 0; i < value.length; i++) {
            buffer[2 * i] = HEX[(value[i] >> 4) & 0x0f];
            buffer[2 * i + 1] = HEX[value[i] & 0x0f];
        }
        return new String(buffer);
    }

	public String getHash(InputStream is) throws Exception {
		MessageDigest digest = MessageDigest.getInstance(DIGEST);
		byte[] b = digestStream(digest, is);

		return encodeHexString(b);
	}

	public String getFilePath(String hash) throws Exception {
		String ret = datastorePath;
		ret = ret + "/" + hash.substring(0, 2);
		ret = ret + "/" + hash.substring(2, 4);
		ret = ret + "/" + hash.substring(4, 6);
		ret = ret + "/" + edvRoot + "_" + hash;
		return ret;
	}


}
