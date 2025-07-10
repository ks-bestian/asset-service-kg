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
    static {
        if (Security.getProvider(BouncyCastleProvider.PROVIDER_NAME) == null) {
            Security.addProvider(new BouncyCastleProvider());
        }
    }

    public String hashWithGost(byte[] fileContent) {
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("GOST3411", BouncyCastleProvider.PROVIDER_NAME);
        } catch (NoSuchAlgorithmException | NoSuchProviderException e) {
            throw new ServiceException("Ошибка при инициализации алгоритма GOST3411 или провайдера Bouncy Castle: " + e.getMessage(), e);
        }
        byte[] hashedBytes = digest.digest(fileContent);
        return HexFormat.of().formatHex(hashedBytes);
    }

    public String calculateGost3411Hash(Path filePath) {
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("GOST3411", BouncyCastleProvider.PROVIDER_NAME);

        } catch (NoSuchAlgorithmException | NoSuchProviderException e) {
            throw new ServiceException("Ошибка при инициализации алгоритма GOST3411 или провайдера Bouncy Castle: " + e.getMessage(), e);
        }

        try (InputStream is = Files.newInputStream(filePath)) {
            byte[] buffer = new byte[8192];
            int bytesRead;
            while ((bytesRead = is.read(buffer)) != -1) {
                digest.update(buffer, 0, bytesRead);
            }
        } catch (IOException e) {
            // Handle file reading errors
            throw new ServiceException("Ошибка чтения файла для хеширования GOST3411: " + filePath.toString(), e);
        }

        byte[] hashedBytes = digest.digest();

        return HexFormat.of().formatHex(hashedBytes);
    }
}
