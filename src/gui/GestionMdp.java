package gui;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class GestionMdp {
	private static final String salt = "roucoulerBourseFantaisie";
	
	public static String hash(String mdp) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			String mdpSalt = mdp + salt;
			
			byte[] hashing = digest.digest(mdpSalt.getBytes(StandardCharsets.UTF_8));
			

            StringBuilder hexString = new StringBuilder();
            for (byte b : hashing) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
	}
	
	public static String getMdpResultHash(char[] pwdTab) {
		String pwd = "";
		for (int i = 0; i<pwdTab.length; i++) {
			pwd += pwdTab[i];
		}
		return GestionMdp.hash(pwd);
	}
}
