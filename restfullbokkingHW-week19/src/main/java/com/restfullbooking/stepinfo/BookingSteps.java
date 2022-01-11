package com.restfullbooking.stepinfo;

import com.restfullbooking.constants.EndPoints;
import com.restfullbooking.model.AuthPojo;
import com.restfullbooking.model.BookingPojo;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;

import java.util.HashMap;

public class BookingSteps {
    static String token;

    @Step("Create new booking with firstName: {0}, lastName: {1}, totalPrice: {2}, depositPaid: {3}, bookingDates: {4}, additionalNeeds: {5}")
    public ValidatableResponse createBooking(String firstName, String lastName, int totalPrice, boolean depositPaid, HashMap<String, Object> bookingDates, String additionalNeeds) {
        BookingPojo bookingPojo = BookingPojo.getBookingPojo(firstName, lastName, totalPrice, depositPaid, bookingDates, additionalNeeds);
        return SerenityRest.given().log().all()
                .contentType(ContentType.JSON)
                .header("Accept", "application/json")
                .body(bookingPojo)
                .when()
                .post(EndPoints.ALL_BOOKING)
                .then();
    }

    @Step("Verify booking is created with firstName: {0}")
    public ValidatableResponse getBookingByFirstName(String firstname) {


        return SerenityRest.given().log().all()
                .header("Accept", "application/json")
                .queryParam("firstname", firstname)
                .when()
                .get(EndPoints.ALL_BOOKING)
                .then().statusCode(200);
    }

    @Step("Update and verify firstname: {0}, lastname: {1}, totalprice: {2},depositpaid: {3}," +
            "bookingdates: {4}, additionalneeds: {5}, booking: {6}")
    public ValidatableResponse updateTheBooking(String firstname, String lastname, int totalprice,
                                                boolean depositpaid, HashMap<String, Object> bookingdates,
                                                String additionalneeds, int bookingId) {
        token = "token="+ AuthPojo.getAuthToken();
        BookingPojo bookingPojo = BookingPojo.getBookingPojo(firstname, lastname, totalprice, depositpaid, bookingdates, additionalneeds);


        return SerenityRest.given().log().all()
                .contentType(ContentType.JSON)
                .header("Accept", "application/json")
                .header("cookie",token)
                .pathParam("bookingid",bookingId)
                .body(bookingPojo)
                .when()
                .post(EndPoints.BOOKING_ID)
                .then().statusCode(200);

    }
    @Step("Deleting the booking with bookingid: {0}")
    public ValidatableResponse deleteBooking(int bookingid) {
        //token generated at the time of update will be used here using static variable
        return SerenityRest.given().log().all()
                .contentType(ContentType.JSON)
                .header("cookie", token)
                .pathParam("bookingId", bookingid)
                .when()
                .delete(EndPoints.BOOKING_ID)
                .then();
    }
    @Step("Getting booking information with firstname: {0}")
    public ValidatableResponse findNewRecordById(int bookingid) {
        //find the new record by id
        return SerenityRest.given().log().all()
                .header("Accept", "application/json")
                .pathParam("bookingid", bookingid)
                .when()
                .get(EndPoints.BOOKING_ID)
                .then();
    }
}

