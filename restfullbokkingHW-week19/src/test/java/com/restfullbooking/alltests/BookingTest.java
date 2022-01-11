package com.restfullbooking.alltests;

import com.restfullbooking.constants.EndPoints;
import com.restfullbooking.model.BookingPojo;
import com.restfullbooking.stepinfo.BookingSteps;
import com.restfullbooking.testbase.TestBase;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasValue;

@RunWith(SerenityRunner.class)
public class BookingTest extends TestBase {
    static String firstName= "Ram";
    static String lastName="Kumar";
    static int totalPrice= 400;
    static boolean depositPaid=true;

   static int bookingId;
    static String additionalNeeds="bed and breakfast";


@Steps
    BookingSteps bookingSteps;


    @Title("This will create the new booking")
    @Test
    public void test001(){
       HashMap<String ,Object> bookingDates= new HashMap<String,Object>();
        bookingDates.put("checkin","2022-01-10");
        bookingDates.put("checkout","2022-01-20");
        ValidatableResponse response= bookingSteps.createBooking(firstName,lastName,totalPrice,depositPaid,bookingDates,additionalNeeds);
    response.log().all().statusCode(200);
    }
@Title("Verify Booking is created")
    @Test
    public void test002(){

    ValidatableResponse response= bookingSteps.getBookingByFirstName(firstName);
    List<Object> value= response.extract().path("bookingid");
   bookingId= (int) value.get(0);
    System.out.println(bookingId);
    }
@Title("Update the booking and verify")
    @Test
    public void test003(){
        firstName= firstName+"updated";
    HashMap<String ,Object> bookingDates= new HashMap<String,Object>();
    bookingDates.put("checkin","2022-01-10");
    bookingDates.put("checkout","2022-01-20");
        ValidatableResponse response= bookingSteps.updateTheBooking(firstName,lastName,totalPrice,depositPaid,bookingDates,additionalNeeds,bookingId);
         response.statusCode(200).log().all();
     List<Integer> id=     bookingSteps.getBookingByFirstName(firstName).extract().path("bookingid");

     Assert.assertThat(id.get(0),equalTo(bookingId));

}
    @Title("Delete the newly created record with ID")
    @Test
    public void test004(){
        //deleting the booking
        bookingSteps.deleteBooking(bookingId);
        //verifying the deletion by finding the record with id
        ValidatableResponse response = bookingSteps.findNewRecordById(bookingId);
        response.statusCode(404)
                .log().all();
}}
