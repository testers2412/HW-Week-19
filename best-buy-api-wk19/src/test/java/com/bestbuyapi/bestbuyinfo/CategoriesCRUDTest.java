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
public class CategoriesCRUDTest extends TestBase {

    /*{
        "id": "abcat0020001",
            "name": "Learning Toys",
            "createdAt": "2016-11-17T17:57:04.285Z",
            "updatedAt": "2016-11-17T17:57:04.285Z",
            "subCategories": [

      ]*/

    public static String name = "Learning Toys"+ TestUtils.getRandomValue();
    public static String id = "abcat001"+TestUtils.getRandomValue();


    @Steps
    CategoriesSteps categoriesSteps;

    @Title("Create new category")
    @Test
    public void test013(){

        categoriesSteps.createCategory(id,name).log().all().statusCode(201);

    }

    @Title("Verify new category has been created")
    @Test
    public void test014(){

        HashMap<String,Object> category = categoriesSteps.verifyCategoryRecord(id);
        Assert.assertThat(category,hasValue(id));
    }


    @Title("Change category information")
    @Test
    public void test015(){

        name = name+TestUtils.getRandomValue();
        categoriesSteps.changeCategoryRecord(id,name).log().all().statusCode(200);
    }

    @Title("Delete category and verify record has been deleted from application")
    @Test
    public void test016(){

        categoriesSteps.deleteCategory(id).log().all().statusCode(200);
        categoriesSteps.verifyCategoryDeleted(id).log().all().statusCode(404);
    }

}
