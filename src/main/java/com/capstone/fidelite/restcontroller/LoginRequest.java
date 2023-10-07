package com.capstone.fidelite.restcontroller;

import java.util.Objects;

public class LoginRequest {
    private String email;
    private String pswd;


	public LoginRequest() {
    }

    public LoginRequest(String email, String pswd) {
        this.email = email;
        this.pswd = pswd;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPswd() {
        return pswd;
    }

    public void setPswd(String pswd) {
        this.pswd = pswd;
    }
    
    @Override
	public int hashCode() {
		return Objects.hash(email, pswd);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LoginRequest other = (LoginRequest) obj;
		return Objects.equals(email, other.email) && Objects.equals(pswd, other.pswd);
	}
}
