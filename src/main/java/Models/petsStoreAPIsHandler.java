package Models;

import io.restassured.RestAssured;
import io.restassured.config.EncoderConfig;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.json.simple.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class petsStoreAPIsHandler {


    // This method should be enhanced by accepting headers as parameters
    // but no need to do it for now
    public static Response GETOperation(String URL) {
        return RestAssured.given().config(RestAssured.config()
                        .encoderConfig(EncoderConfig.encoderConfig()
                                .appendDefaultContentCharsetToContentTypeIfUndefined(false)))
                .relaxedHTTPSValidation().headers("accept", "application/json")
                .when()
                .get(URL);

    }


    public static Response GET_FindPetByStatus(String status) {
        String url = "https://petstore.swagger.io/v2/pet/findByStatus?status=" + status + "";
        return GETOperation(url);
    }

    public static String GET_ResponseAsString(Response res, String JSONPath) {
        JsonPath path = new JsonPath(res.asString());
        String str1 = path.getString(JSONPath).replace("[", " ");
        String str2 = str1.replace("]", "");
        String str3 = str2.replace(", ", " ");
        System.out.println(str3);
        //return path.getString("name");
        return str3;
    }

    public static String getAllPets(String status, String JSONPath) {
        return GET_ResponseAsString(GET_FindPetByStatus(status), JSONPath);
    }

    public static Response addNewPet(String petID, String name, String petStatus) {
        JSONObject categoryParams = new JSONObject();
        categoryParams.put("id", 0);
        categoryParams.put("name", "string");

        JSONObject reqParams = new JSONObject();
        reqParams.put("id",(long)Double.parseDouble(petID));

        reqParams.put("category", categoryParams);

        reqParams.put("name", name);

        reqParams.put("tags", Arrays.asList(new LinkedHashMap<String, Object>() {
            {
                put("id", 0);
                put("name", "string");
            }
        }));

        reqParams.put("photoUrls", Arrays.asList("string"));

        reqParams.put("status", petStatus);

        System.out.println(reqParams.toJSONString());

        return RestAssured.given().config(RestAssured.config()
                        .encoderConfig(EncoderConfig.encoderConfig()
                                .appendDefaultContentCharsetToContentTypeIfUndefined(false)))
                .relaxedHTTPSValidation().header("accept", "application/json")
                .header("content-Type", "application/json")
                .when().body(reqParams.toJSONString())
                .post("https://petstore.swagger.io/v2/pet");


    }

    public static Response updatePetDetails(String petID, String newName, String newStatus) {
        Response res = null;

        JSONObject categoryParams = new JSONObject();
        categoryParams.put("id", "0");
        categoryParams.put("name", "string");

        JSONObject reqParams = new JSONObject();
        reqParams.put("id", petID);

        reqParams.put("category", categoryParams);

        reqParams.put("name", newName);

        // reqParams.put("")
        reqParams.put("tags", Arrays.asList(new LinkedHashMap<String, Object>() {
            {
                put("id", "0");
                put("name", "string");
            }
        }));


        reqParams.put("status", newStatus);


        res = RestAssured.given().config(RestAssured.config()
                        .encoderConfig(EncoderConfig.encoderConfig()
                                .appendDefaultContentCharsetToContentTypeIfUndefined(false)))
                .relaxedHTTPSValidation().header("accept", "application/json")
                .header("Content-Type", "application/json")
                .body(reqParams.toString()).when()
                .put("https://petstore.swagger.io/v2/pet");

        //System.out.println(reqParams.toJSONString());
        return res;

    }

    public static Response deleteAPetDetails(String petID) {

        String url = "https://petstore.swagger.io/v2/pet/" + petID + "";
        return RestAssured.given().config(RestAssured.config()
                        .encoderConfig(EncoderConfig.encoderConfig()
                                .appendDefaultContentCharsetToContentTypeIfUndefined(false)))
                .relaxedHTTPSValidation().headers("accept", "application/json")
                .when()
                .delete(url);

    }


    public static Response findPetById(String id) {

        String url = "https://petstore.swagger.io/v2/pet/" + id + "";

        return GETOperation(url);
    }


    public static HashMap<String, String> findSummary() {

        Response res = GETOperation("https://petstore.swagger.io/v2/store/inventory");
        HashMap<String, String> hashMap = new HashMap<String,String>();

        String responseAsString = res.asPrettyString();

        //set default values
        hashMap.getOrDefault("Available","0");
        hashMap.getOrDefault("Sold","0");
        hashMap.getOrDefault("Pending","0");


        JsonPath jsonPath = new JsonPath(responseAsString);
        if (responseAsString.contains("available"))
            hashMap.put("Available", jsonPath.getString("available"));
        if (responseAsString.contains("sold"))
            hashMap.put("Sold", jsonPath.getString("sold"));
        if (responseAsString.contains("pending"))
            hashMap.put("Pending", jsonPath.getString("pending"));


        return hashMap;

    }

    public static void main(String[] args) {
        // addNewPet("");
        System.out.println(findSummary());
    }

}
