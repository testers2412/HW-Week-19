package com.studentapp.testbase;

import com.studentapp.constants.Path;
import com.studentapp.utils.PropertyReader;
import io.restassured.RestAssured;
import org.junit.BeforeClass;

public class TestBase {
    public static PropertyReader propertyReader;

    @BeforeClass
    public static void init() {
        propertyReader =propertyReader.getInstance();

        //   RestAssured.baseURI = "http://localhost";
        RestAssured.baseURI = propertyReader.getProperty("baseUrl");
        //RestAssured.port = 8080;
        RestAssured.port = Integer.parseInt(propertyReader.getProperty("port"));
        // RestAssured.basePath = "/student";
        RestAssured.basePath = Path.STUDENT;
    }
}
