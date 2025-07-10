package kr.co.bestiansoft.ebillservicekg.sed_jk.services.common;

import kr.co.bestiansoft.ebillservicekg.sed_jk.exception.ServiceException;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.stereotype.Service;

//import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.util.HexFormat;

@Service
public class HashingService {
    private static final String GOST_ALGORITHM = "GOST3411";
    private static final int BUFFER_SIZE = 8192;
    private static final String INIT_ERROR_MSG = "Ошибка при инициализации алгоритма GOST3411 или провайдера Bouncy Castle: ";
    private static final String READ_ERROR_MSG = "Ошибка чтения файла для хеширования GOST3411: ";

    static {
        if (Security.getProvider(BouncyCastleProvider.PROVIDER_NAME) == null) {
            Security.addProvider(new BouncyCastleProvider());
        }
    }

    private MessageDigest initializeDigest() {
        try {
            return MessageDigest.getInstance(GOST_ALGORITHM, BouncyCastleProvider.PROVIDER_NAME);
        } catch (NoSuchAlgorithmException | NoSuchProviderException e) {
            throw new ServiceException(INIT_ERROR_MSG + e.getMessage(), e);
        }
    }

    private String formatHash(byte[] hashedBytes) {
        return HexFormat.of().formatHex(hashedBytes);
    }

    public String calculateHash(byte[] content) {
        MessageDigest digest = initializeDigest();
        return formatHash(digest.digest(content));
    }

    public String calculateHash(String content) {
        return calculateHash(content.getBytes(StandardCharsets.UTF_8));
    }

    public String calculateHash(Path filePath) {
        try (InputStream is = Files.newInputStream(filePath)) {
            return calculateHash(is);
        } catch (IOException e) {
            throw new ServiceException(READ_ERROR_MSG + ": " + filePath, e);
        }
    }

    public String calculateHash(InputStream is) {
        MessageDigest digest = initializeDigest();
        try {
            byte[] buffer = new byte[BUFFER_SIZE];
            int bytesRead;
            while ((bytesRead = is.read(buffer)) != -1) {
                digest.update(buffer, 0, bytesRead);
            }
        } catch (IOException e) {
            throw new ServiceException(READ_ERROR_MSG, e);
        }
        return formatHash(digest.digest());
    }







//    2025-07-10 beomsu Refactoring
//    public String hashWithGost(byte[] fileContent) {
//        MessageDigest digest;
//        try {
//            digest = MessageDigest.getInstance("GOST3411", BouncyCastleProvider.PROVIDER_NAME);
//        } catch (NoSuchAlgorithmException | NoSuchProviderException e) {
//            throw new ServiceException("Ошибка при инициализации алгоритма GOST3411 или провайдера Bouncy Castle: " + e.getMessage(), e);
//        }
//        byte[] hashedBytes = digest.digest(fileContent);
//        return HexFormat.of().formatHex(hashedBytes);
//    }
//
//    public String calculateGost3411Hash(Path filePath) {
//        MessageDigest digest;
//        try {
//            digest = MessageDigest.getInstance("GOST3411", BouncyCastleProvider.PROVIDER_NAME);
//
//        } catch (NoSuchAlgorithmException | NoSuchProviderException e) {
//            throw new ServiceException("Ошибка при инициализации алгоритма GOST3411 или провайдера Bouncy Castle: " + e.getMessage(), e);
//        }
//
//        try (InputStream is = Files.newInputStream(filePath)) {
//            byte[] buffer = new byte[8192];
//            int bytesRead;
//            while ((bytesRead = is.read(buffer)) != -1) {
//                digest.update(buffer, 0, bytesRead);
//            }
//        } catch (IOException e) {
//            // Handle file reading errors
//            throw new ServiceException("Ошибка чтения файла для хеширования GOST3411: " + filePath.toString(), e);
//        }
//
//        byte[] hashedBytes = digest.digest();
//
//        return HexFormat.of().formatHex(hashedBytes);
//    }


}
