package com.bestbuyapi.bestbuyinfo;

import com.bestbuyapi.utils.TestUtils;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import resources.testdata.TestBase;

import java.util.HashMap;

import static org.hamcrest.Matchers.hasValue;

@RunWith(SerenityRunner.class)
public class ServicesCRUDTest extends TestBase {

    /*{
        "id": 1,
            "name": "Geek Squad Services",
            "createdAt": "2016-11-17T17:56:35.881Z",
            "updatedAt": "2016-11-17T17:56:35.881Z"
    }*/

    public static String name = "Photo Printing"+ TestUtils.getRandomValue();
    public static int serviceID;

    @Steps
    ServicesSteps servicesSteps;

    @Title ("Create new service record")
    @Test
    public void test005(){

        servicesSteps.createService(name).log().all().statusCode(201);

    }

    @Title ("Verify new service record has been created successfully")
    @Test
    public void test006(){
        HashMap<String,Object> service = servicesSteps.verifyServiceRecord(name);
        Assert.assertThat(service,hasValue(name));
        serviceID = (int) service.get("id");

    }

    @Title ("Change service record and verify service information ")
    @Test
    public void test007(){

        name = name+TestUtils.getRandomValue();

        servicesSteps.changeServiceRecord(serviceID, name).log().all().statusCode(200);
        HashMap<String,Object> service = servicesSteps.verifyServiceRecord(name);
        Assert.assertThat(service,hasValue(name));


    }

    @Title ("Delete service record and verify service record has been deleted")
    @Test
    public void test008(){
         servicesSteps.deleteService(serviceID).log().all().statusCode(200);
         servicesSteps.verifyServiceRecordByID(serviceID).log().all().statusCode(404);

    }

}
