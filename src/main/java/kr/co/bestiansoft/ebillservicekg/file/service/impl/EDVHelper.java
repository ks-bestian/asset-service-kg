package kr.co.bestiansoft.ebillservicekg.file.service.impl;

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
