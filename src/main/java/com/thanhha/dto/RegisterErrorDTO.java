package com.thanhha.dto;

import java.io.Serializable;

public class RegisterErrorDTO implements Serializable {
    private String usernameLengthErr;
    private String passwordLengthErr;
    private String confirmPasswordNotMatched;
    private String firstnameLengthErr;
    private String lastnameLengthErr;

    public RegisterErrorDTO(String usernameLengthErr, String passwordLengthErr, String confirmPasswordNotMatched,
                            String firstnameLengthErr, String lastnameLengthErr) {
        this.usernameLengthErr = usernameLengthErr;
        this.passwordLengthErr = passwordLengthErr;
        this.confirmPasswordNotMatched = confirmPasswordNotMatched;
        this.firstnameLengthErr = firstnameLengthErr;
        this.lastnameLengthErr = lastnameLengthErr;
    }

    public RegisterErrorDTO() {
    }

    public String getUsernameLengthErr() {
        return usernameLengthErr;
    }

    public void setUsernameLengthErr(String usernameLengthErr) {
        this.usernameLengthErr = usernameLengthErr;
    }

    public String getPasswordLengthErr() {
        return passwordLengthErr;
    }

    public void setPasswordLengthErr(String passwordLengthErr) {
        this.passwordLengthErr = passwordLengthErr;
    }

    public String getConfirmPasswordNotMatched() {
        return confirmPasswordNotMatched;
    }

    public void setConfirmPasswordNotMatched(String confirmPasswordNotMatched) {
        this.confirmPasswordNotMatched = confirmPasswordNotMatched;
    }

    public String getFirstnameLengthErr() {
        return firstnameLengthErr;
    }

    public void setFirstnameLengthErr(String firstnameLengthErr) {
        this.firstnameLengthErr = firstnameLengthErr;
    }

    public String getLastnameLengthErr() {
        return lastnameLengthErr;
    }

    public void setLastnameLengthErr(String lastnameLengthErr) {
        this.lastnameLengthErr = lastnameLengthErr;
    }
}
