package com.studentapp.studenttest;

import com.studentapp.studentinfo.StudentSteps;
import com.studentapp.utils.TestUtils;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.hamcrest.Matchers.hasValue;

@RunWith(SerenityRunner.class)
public class StudentCRUDTest {
    static String firstName = "PrimUser" + TestUtils.getRandomValue();
    static String lastName = "PrimeUser" + TestUtils.getRandomValue();
    static String programme = "Api Testing";
    static String email = TestUtils.getRandomValue() + "xyz@gmail.com";
    static int studentId;
    static List<String> coursesList;
    public StudentCRUDTest(){
        coursesList= new ArrayList<>();
        coursesList.add("Selenium");
        coursesList.add("Java");
    }
    @Steps
    StudentSteps studentSteps;
    @Title("This will create new student")
    @Test
    public void test001(){
        /*List<String> coursesList= new ArrayList<>();
        coursesList.add("Selenium");
        coursesList.add("Java");*/
        studentSteps.createStudent(firstName,lastName,email,programme,coursesList)
                .log().all().statusCode(201);
    }
    @Title("Verify new created student was added")
    @Test
    public void test002(){
        HashMap<String,Object> value= studentSteps.getStudentInfoByFirstname(firstName);
        Assert.assertThat(value,hasValue(firstName));
        studentId= (int) value.get("id");
    }
    @Title("Update the user information")
    @Test
    public void test003(){
        firstName= firstName+"_updated";
        ValidatableResponse response= studentSteps.updateStudent(studentId,firstName,lastName,email,programme,coursesList);
        response.log().all().statusCode(200);
        HashMap<String ,Object> value= studentSteps.getStudentInfoByFirstname(firstName);
        Assert.assertThat(value,hasValue(firstName));
    }
    @Title("Delete the student and verify deletetion")
    @Test
    public void test004(){
        studentSteps.deleteStudent(studentId).statusCode(204);
        studentSteps.getStudentId(studentId).statusCode(404);
    }
}
