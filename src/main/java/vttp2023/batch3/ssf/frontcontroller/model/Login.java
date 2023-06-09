package vttp2023.batch3.ssf.frontcontroller.model;

import java.io.Serializable;
import java.util.Random;

import jakarta.validation.constraints.Size;

public class Login implements Serializable {
    
    @Size(min=2, message="Your username cannot be less than 2 characters")
    private String username;

    @Size(min=2, message="Your password cannot be less than 2 characters")
    private String password;

    private String captchaString;
    private int captchaAnswer;
    private int userResponse;

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getCaptchaString() {
        return captchaString;
    }
    public void setCaptchaString(String captchaString) {
        this.captchaString = captchaString;
    }
    public int getCaptchaAnswer() {
        return captchaAnswer;
    }
    public void setCaptchaAnswer(int captchaAnswer) {
        this.captchaAnswer = captchaAnswer;
    }
    public int getUserResponse() {
        return userResponse;
    }
    public void setUserResponse(int userResponse) {
        this.userResponse = userResponse;
    }

    public static Login generateCaptcha() {
        Random rand = new Random();
        Login login = new Login();
        int captcha1 = rand.nextInt(50);
        int captcha2 = rand.nextInt(50);
        login.setCaptchaString("What is "+captcha1+"+"+captcha2+"?");
        login.setCaptchaAnswer(captcha1+captcha2);
        return login;
    }
    
}
