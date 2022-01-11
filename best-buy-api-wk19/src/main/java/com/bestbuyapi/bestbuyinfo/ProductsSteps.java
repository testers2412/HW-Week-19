package com.bestbuyapi.bestbuyinfo;

import com.bestbuyapi.constants.EndPoints;
import com.bestbuyapi.constants.Path;
import com.bestbuyapi.model.ProductPojo;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;

import java.util.HashMap;

public class ProductsSteps {


    @Step("Create new product with name: {0}, type: {1}, price: {2}, upc: {3}, shipping: {4}, description: {5}, manufacturer: {6}, model: {7}, url: {8}, image: {9}")
    public ValidatableResponse createProduct(String name,
                                             String type,
                                             double price,
                                             String upc,
                                             int shipping,
                                             String description,
                                             String manufacturer,
                                             String model,
                                             String url,
                                             String image) {

        ProductPojo productPojo = ProductPojo.getProductPojo(name, type, price, upc, shipping, description, manufacturer, model, url, image);

        return SerenityRest.given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(productPojo)
                .when()
                .post(Path.PRODUCT)
                .then();

    }

    @Step ("Verify product with name: {0}")
    public HashMap<String,Object> verifyProductInformation(String name){

        return SerenityRest.given()
                .log().all()
                .queryParam("name",name)
                .when()
                .get(Path.PRODUCT)
                .then()
                .extract()
                .path("data.findAll{it.name='"+name+"'}.get(0)");


    }


    @Step ("Change product with productID: {0}, name: {1}, type: {2}, price: {3}, upc: {4}, shipping: {5}, description: {6}, manufacturer: {7}, model: {8}, url: {9}, image: {10}")
    public ValidatableResponse changeProduct(int productID,String name,
                                             String type,
                                             double price,
                                             String upc,
                                             int shipping,
                                             String description,
                                             String manufacturer,
                                             String model,
                                             String url,
                                             String image){
        ProductPojo productPojo = ProductPojo.getProductPojo(name, type, price, upc, shipping, description, manufacturer, model, url, image);

        return SerenityRest.given()
                .log().all()
                .contentType(ContentType.JSON)
                .pathParam("productID",productID)
                .body(productPojo)
                .when()
                .put(Path.PRODUCT+ EndPoints.CHANGE_PRODUCT_INFO)
                .then();


    }

    @Step("delete product with productID: {0} ")
    public ValidatableResponse deleteProduct(int productID){

        return SerenityRest.given()
                .log().all()
                .pathParam("productID",productID)
                .when()
                .delete(Path.PRODUCT+EndPoints.DELETE_PRODUCT)
                .then();

    }

    @Step("Verify product has been deleted for productID: {0}")
    public ValidatableResponse verifyProductDeleted(int productID){
        return SerenityRest.given()
                .log().all()
                .pathParam("productID",productID)
                .when()
                .get(Path.PRODUCT+EndPoints.GET_PRODUCT_BY_ID)
                .then();
    }


}
