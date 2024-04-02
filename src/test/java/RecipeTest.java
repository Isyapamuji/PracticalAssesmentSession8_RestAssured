import io.qameta.allure.Feature;
import io.restassured.http.ContentType;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;


@Feature("Test Recipes")
public class RecipeTest extends BaseTest2 {
    String apiKey = "a402ba2d8fd6478f843de69a2ffa0dad";
    String hash = "ba75fa106643bb4925d0597d98a690494df9b7f7";
    String username ="isyapamuji2";

    @Test(description = "Search Recipes")
    public void SearchRecipes(){
        given()
                .queryParam("apiKey",apiKey)
                .log().ifValidationFails()
                .when()
                .get("complexSearch")
                .then()
                .log().ifValidationFails()
                .statusCode(200)
                //Assertion isi 1 hari
                .body("results.size()",equalTo(10))
                .body("totalResults", equalTo(5216));
    }

@Test(description = "Search Recipes by Nutrients")
    public void findByNutrients(){
        given()
                .queryParam("apiKey",apiKey)
                .queryParam("minCarbs",10)
                .queryParam("maxCarbs","50")
                .queryParam("number","3")
                .log().ifValidationFails()
                .when()
                .get("findByNutrients")
                .then()
                .log().ifValidationFails()
                .statusCode(200)
                //Assertion isi 1 hari
                .body("results.size()",equalTo(3));
    }

    @Test(description = "Analyze Recipe")
    public void AnalyzeRecipe() {
        String requestBody = "{\n" +
                "    \"title\": \"Spaghetti Carbonara\",\n" +
                "    \"servings\": 2,\n" +
                "    \"ingredients\": [\n" +
                "        \"1 lb spaghetti\",\n" +
                "        \"3.5 oz pancetta\",\n" +
                "        \"2 Tbsps olive oil\",\n" +
                "        \"1  egg\",\n" +
                "        \"0.5 cup parmesan cheese\"\n" +
                "    ],\n" +
                "    \"instructions\": \"Bring a large pot of water to a boil and season generously with salt. Add the pasta to the water once boiling and cook until al dente. Reserve 2 cups of cooking water and drain the pasta. \"\n" +
                "}";
        given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .queryParam("apiKey",apiKey)
                .body(requestBody)
                .log().ifValidationFails()
                .when()
                .post("analyze")
                .then()
                .log().ifValidationFails()
                .statusCode(200)
                .body("title",equalTo("Spaghetti Carbonara"));
    }
}