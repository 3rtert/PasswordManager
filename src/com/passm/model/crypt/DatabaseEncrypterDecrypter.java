package com.passm.model.crypt;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.logging.Logger;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import com.passm.model.database.DatabaseCreator;

public class DatabaseEncrypterDecrypter {
	
	private final static Logger LOGGER = Logger.getLogger(DatabaseEncrypterDecrypter.class.getName());
	
	private final static int KEY_SIZE = 256;
	private final static int SALT_SIZE = 256;
	private final static int ITERATION_COUNT = 2000;
	private final static int BUFFER_SIZE = 16;
	private final static String CIPHER = "AES";
	private final static String ALGORITHM = "PBKDF2WithHmacSHA256";
	private final static String CIPHER_TRANSFORMATION = "AES/CBC/PKCS5Padding";
	
	private final static String DATABASE_FILE = DatabaseCreator.getDatabaseName();
	private final static String ENCRYPTED_DATABASE_FILE = DATABASE_FILE + ".enc";
	
	private SecretKey secretKey;
	private Cipher cipher;

	public DatabaseEncrypterDecrypter(char[] password) {
		byte[] salt = new byte[SALT_SIZE];
	    SecureRandom random = new SecureRandom();
	    random.nextBytes(salt);
	    
	    //TODO: handle random salt
	    salt = "nmb aq3406j0B)J(^$)VW$()k9046".getBytes();
	    
	    PBEKeySpec pbeKeySpec = new PBEKeySpec(password, salt, ITERATION_COUNT, KEY_SIZE);
	    SecretKey pbeKey;
		try {
			pbeKey = SecretKeyFactory.getInstance(ALGORITHM).generateSecret(pbeKeySpec);
			this.secretKey =  new SecretKeySpec(pbeKey.getEncoded(), CIPHER);
		    this.cipher = Cipher.getInstance(CIPHER_TRANSFORMATION);
		} catch (InvalidKeySpecException | NoSuchAlgorithmException | NoSuchPaddingException e) {
			LOGGER.warning(e.getMessage());
		}
	}
	
	public static boolean isDatabaseEncrypted() {
		return new File(ENCRYPTED_DATABASE_FILE).exists();
	}
	
	public boolean encrypt() {
		return encrypt(DATABASE_FILE, ENCRYPTED_DATABASE_FILE);
	}
	
	public boolean decrypt() {
		return decrypt(ENCRYPTED_DATABASE_FILE, DATABASE_FILE);
	}

	private boolean encrypt(String inputFileName, String outputFileName) {
		LOGGER.info("Start encrypting file " + inputFileName + " to file " + outputFileName);
		try {
			cipher.init(Cipher.ENCRYPT_MODE, secretKey);
		} catch (InvalidKeyException e) {
			LOGGER.warning(e.getMessage());
			return false;
		}
		new File(outputFileName).delete();
	    byte[] iv = cipher.getIV();

	    try (FileOutputStream fileOut = new FileOutputStream(outputFileName);
	      CipherOutputStream cipherOut = new CipherOutputStream(fileOut, cipher)) {
	        fileOut.write(iv);
	        byte[] data = getData(inputFileName);
	        if(data.length == 0) {
	        	return false;
	        }
	        cipherOut.write(data);
		} catch (IOException e) {
			LOGGER.warning(e.getMessage());
			return false;
		}
	    new File(inputFileName).delete();
	    LOGGER.info("Encryption completed successfully");
	    return true;
	}
	
	private byte[] getData(String inputFileName) {
		byte[] data = new byte[0];
		try (FileInputStream fileIn = new FileInputStream(inputFileName)) {
			data = fileIn.readAllBytes();
		} catch (IOException e) {
			LOGGER.warning(e.getMessage());
		}
		return data;
	}

	private boolean decrypt(String inputFileName, String outputFileName) {
		LOGGER.info("Start decrypting file " + inputFileName + " to file " + outputFileName);
		new File(outputFileName).delete();
	    try (FileInputStream fileIn = new FileInputStream(inputFileName)) {
	    	byte[] fileIv = new byte[BUFFER_SIZE];
	        fileIn.read(fileIv);
	        cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(fileIv));
	        try (CipherInputStream cipherIn = new CipherInputStream(fileIn, cipher)) {
	        	try (FileOutputStream fileOut = new FileOutputStream(outputFileName)) {
	        		boolean decryptionCompleted = false;
		        	while(!decryptionCompleted) {
		        		int nextByte = cipherIn.read();
		        		if(nextByte != -1) {
		        			fileOut.write(nextByte);
		        		} else {
			        		decryptionCompleted = true;
			        	}
		        	}
	        	}
	        }
	    } catch (IOException|InvalidKeyException|InvalidAlgorithmParameterException e) {
	    	LOGGER.warning(e.getMessage());
	    	new File(outputFileName).delete();
	    	return false;
		}
	    LOGGER.info("Decryption completed successfully");
	    return true;
	}
}
