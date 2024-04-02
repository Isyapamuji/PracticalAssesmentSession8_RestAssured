import io.qameta.allure.Feature;
import io.restassured.http.ContentType;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;


@Feature("Test Meal Plan")
public class MealPlanTest extends BaseTest{
    String apiKey = "a402ba2d8fd6478f843de69a2ffa0dad";
    String hash = "ba75fa106643bb4925d0597d98a690494df9b7f7";
    String username ="isyapamuji2";

    @Test(description = "Generate MealPlan")
    public void MealPlanGenerateTest(){
        given()
                .queryParam("apiKey",apiKey)
                .log().ifValidationFails()
                .when()
                .get("generate")
                .then()
                .log().ifValidationFails()
                .statusCode(200)
                //Assertion isi 1 hari
                .body("week.monday.meals.size()",equalTo(3));

    }

    @Test(description = "Add Item to meal plan")
    public void AddItemtoMealPlanTest(){
        String requestBody = "[\n" +
                "    {\n" +
                "        \"date\": 1589500800,\n" +
                "        \"slot\": 1,\n" +
                "        \"position\": 0,\n" +
                "        \"type\": \"INGREDIENTS\",\n" +
                "        \"value\": {\n" +
                "            \"ingredients\": [\n" +
                "                {\n" +
                "                    \"name\": \"1 banana\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"name\": \"coffee\",\n" +
                "                    \"unit\": \"cup\",\n" +
                "                    \"amount\": \"1\",\n" +
                "                    \"image\": \"https://img.spoonacular.com/ingredients_100x100/brewed-coffee.jpg\"\n" +
                "                }\n" +
                "            ]\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"date\": 1589500800,\n" +
                "        \"slot\": 2,\n" +
                "        \"position\": 0,\n" +
                "        \"type\": \"CUSTOM_FOOD\",\n" +
                "        \"value\": {\n" +
                "            \"id\": 348,\n" +
                "            \"servings\": 1,\n" +
                "            \"title\": \"Aldi Spicy Cashews - 30g\",\n" +
                "            \"image\": \"https://img.spoonacular.com/ingredients_100x100/cashews.jpg\"\n" +
                "        }\n" +
                "    }\n" +
                "]";
        given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .queryParam("apiKey",apiKey)
                .queryParam("hash",hash)
                .body(requestBody)
                .log().ifValidationFails()
                .when()
                .post("/{username}/items",username)
                .then()
                .log().ifValidationFails()
                .statusCode(200)
                .body("status",equalTo("success"));

//                .extract()
//                .path("id");
    }

    @Test(description = "Delete item to mealplan")
    public void DeleteItemtoMealPlanTest(){
        given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .queryParam("apiKey",apiKey)
                .queryParam("hash",hash)
                .when()
                .delete("/{username}/items/25640944",username)
                .then()
                .log().ifValidationFails()
                .statusCode(200)
                .body("status",equalTo("success"));

    }

}
