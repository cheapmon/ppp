package tools;

import java.security.SecureRandom;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public abstract class SecurityService {

	/*
	 * Jedes Token wird 60 Minuten lang gespeichert.
	 */
	private static final long TOKENLIFETIME = 1000 * 60 * 60;

	private static final String symbols = "abcdefghijklmnopqrstuvwxyzABCDEFGJKLMNPRSTUVWXYZ0123456789.-$";
	private static final Random random = new SecureRandom();
	private static List<Token> tokenList = new ArrayList<>();

	private static String createTokenId(int length) {

		char[] buf = new char[length];
		for (int idx = 0; idx < buf.length; ++idx)
			buf[idx] = symbols.charAt(random.nextInt(symbols.length()));
		return new String(buf);
	}

	public static void removeAccess(String tokenId){
		for(Token token: tokenList){
			if(token.getId().equals(tokenId)){
				tokenList.remove(token);
				return;
			}
		}
	}

	public static Token grantAccess(){

		long currentTime = new Date().getTime();
		Token token = new Token(createTokenId(64), new Timestamp(currentTime
				+ TOKENLIFETIME));
		tokenList.add(token);
		return token;
	}

	public static boolean checkAccess(String tokenId){
		
		Token token = null;
		
		for(Token t: tokenList){
			if(t.getId().equals(tokenId)){
				token = t;
			}
		}

		// Falls KEIN Token in der DB liegt.
		if (token == null) {
			return false;
		}

		// Falls ein Token in der DB liegt.
		long currentTime = new Date().getTime();
		if (token.getExpirationTime().getTime() >= currentTime) {
			token.setExpirationTime(new Timestamp(currentTime + TOKENLIFETIME));
			return true;
		} else {
			removeAccess(tokenId);
			return false;
		}
	}
}