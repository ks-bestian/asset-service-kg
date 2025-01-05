package kr.co.bestiansoft.ebillservicekg.login.vo;

import java.security.MessageDigest;

//import javax.xml.bind.DatatypeConverter;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

@ApiModel(description = "로그인 조회")
@Data
public class LoginRequest {

	@ApiModelProperty(value = "사용자 아이디")
    private String userId;

	@ApiModelProperty(value = "사용자 패스워드")
    private String pswd;

    @Builder
	public LoginRequest(String userId, String pswd) {
		this.userId = userId;
		this.pswd = pswd;
	}

	public static String getSha256(String input){
		/*
		String sha256 ="";
		try{
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
	        digest.update(input.getBytes());
	        byte[] hash = digest.digest();
	        sha256 =  DatatypeConverter.printHexBinary(hash);
	    }catch(Exception e){
	        e.printStackTrace();
	        sha256 = null;
	    }
	    return sha256;
	    */

        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = String.format("%02X", b);
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
	}

}
