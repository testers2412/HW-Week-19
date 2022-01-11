package com.bestbuyapi.bestbuyinfo;

import com.bestbuyapi.constants.EndPoints;
import com.bestbuyapi.constants.Path;
import com.bestbuyapi.model.CategoryPojo;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;

import java.util.HashMap;

public class CategoriesSteps {


    @Step ("Create category record with id: {0}, name : {1}")
    public ValidatableResponse createCategory(String id, String name){

        CategoryPojo categoryPojo = CategoryPojo.getCategoryPojo(id, name);

        return SerenityRest.given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(categoryPojo)
                .when()
                .post(Path.CATEGORY)
                .then();


    }

    @Step ("Verify category record with name : {0}")
    public HashMap<String, Object> verifyCategoryRecord(String id){

        return SerenityRest.given()
                .log().all()
                .queryParam("id",id)
                .when()
                .get(Path.CATEGORY)
                .then()
                .extract()
                .path("data.findAll{it.id='"+id+"'}.get(0)");


    }


    @Step ("Change category record with id: {0}, name: {1}")
    public ValidatableResponse changeCategoryRecord(String id, String name){


        CategoryPojo categoryPojo = CategoryPojo.getCategoryPojo(id,name);

        return SerenityRest.given()
                .log().all()
                .contentType(ContentType.JSON)
                .pathParam("id",id)
                .body(categoryPojo)
                .when()
                .put(Path.CATEGORY+EndPoints.CHANGE_CATEGORY_INFO)
                .then();


    }



    @Step ("Delete category record with categoryID: {0}")
    public ValidatableResponse deleteCategory(String id){

        return SerenityRest.given()
                .log().all()
                .pathParam("id", id)
                .when()
                .delete(Path.CATEGORY+EndPoints.DELETE_CATEGORY)
                .then();
    }


    @Step ("Verify category record has deleted for categoryID: {0}")
    public ValidatableResponse verifyCategoryDeleted(String id){

        return SerenityRest.given()
                .log().all()
                .pathParam("id", id)
                .when()
                .get(Path.CATEGORY+EndPoints.GET_CATEGORY_BY_ID)
                .then();

    }


}
