package com.restfullbooking.model;

import com.restfullbooking.constants.EndPoints;
import io.restassured.http.ContentType;
import net.serenitybdd.rest.SerenityRest;
import org.yecht.Data;

public class AuthPojo {
    static String token;
    private String userName;
    private String password;


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public static String getAuthToken(){
        AuthPojo authPojo= new AuthPojo();
        authPojo.setUserName("admin");
        authPojo.setPassword("password123");
        token = SerenityRest.given().log().all()
                .contentType(ContentType.JSON)
                .body(authPojo)
                .when()
                .post("https://restful-booker.herokuapp.com/auth")
                .then()
                .extract()
                .path("token");
        return token;
    }

}
