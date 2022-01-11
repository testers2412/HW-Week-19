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
public class StoreCRUDTest extends TestBase {



   /* "id": 4,
            "name": "Harrow123",
            "type": "BigBox",
            "address": "12 1st Street A",
            "address2": "abc",
            "city": "London",
            "state": "MN",
            "zip": "25687",
            "lat": 44.879314,
            "lng": 93.077156,
            "hours": "Mon: 10-9; Tue: 10-9; Wed: 10-9; Thurs: 10-9; Fri: 10-9; Sat: 10-9; Sun: 10-8",
            "createdAt": "2016-11-17T17:57:05.708Z",
            "updatedAt": "2022-01-04T21:34:10.040Z"*/

    public static String name = "Harrow" + TestUtils.getRandomValue();
    public static String type = "BigBox";
    public static String address = "12 1st Street A";
    public static String address2 = "abc";
    public static String city = "London";
    public static String state = "MN";
    public static String zip = "25687";
    public static double lat = 44.879314;
    public static double lng = 93.077156;
    public static String hours = "Mon: 10-9; Tue: 10-9; Wed: 10-9; Thurs: 10-9; Fri: 10-9; Sat: 10-9; Sun: 10-8";
    public static int storeID;

@Steps
StoresSteps storesSteps;

    @Title("Create new store record")
    @Test
    public void test001() {

        storesSteps.createStore(name,type,address,address2,city,state,zip,lat,lng,hours).log().all().statusCode(201);

    }

    @Title("Verify new store has been created in the application and get store ID")
    @Test
    public void test002() {

        HashMap<String,Object> storeRecord = storesSteps.getStoreInformationWithName(name);
        Assert.assertThat(storeRecord,hasValue(name));
        storeID = (int) storeRecord.get("id");

    }

    @Title("Change store information and verify change has been reflected in the record")
    @Test
    public void test003() {
        name = name+TestUtils.getRandomValue();

        storesSteps.changeStoreInfo(storeID,name,type,address,address2,city,state,zip,lat,lng,hours).log().all().statusCode(200);
        HashMap<String,Object> storeRecord = storesSteps.getStoreInformationWithName(name);
        Assert.assertThat(storeRecord,hasValue(name));


    }

    @Title("Delete store record and verify store has been deleted")
    @Test
    public void test004() {

        storesSteps.deleteStoreRecord(storeID).log().all().statusCode(200);
        storesSteps.verifyStoreRecord(storeID).log().all().statusCode(404);


    }


   /* @Title ("Get all store details")
    @Test
    public void getAllStores(){
        SerenityRest.given()
                .log().all()
                .when()
                .get()
                .then().log().all().statusCode(200);
    }*/

}
