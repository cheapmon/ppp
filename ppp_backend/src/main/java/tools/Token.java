package tools;

import java.sql.Timestamp;

public class Token {

    private String id;
    private Timestamp expirationTime;
    
    public Token(String id, Timestamp expirationTime){
    	this.id = id;
    	this.expirationTime = expirationTime;
    }

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Timestamp getExpirationTime() {
		return expirationTime;
	}

	public void setExpirationTime(Timestamp expirationTime) {
		this.expirationTime = expirationTime;
	}
}