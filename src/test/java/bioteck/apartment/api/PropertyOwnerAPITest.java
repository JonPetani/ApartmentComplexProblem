package bioteck.apartment.api;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;

import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;
import io.restassured.http.Method;
import io.restassured.http.Header;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.Map;
import java.util.List;

import bioteck.apartment.model.PropertyOwner;

public class PropertyOwnerAPITest {
   private Header acceptJson = new Header("Accept", "application/json");
   private PropertyOwner po1 =  new PropertyOwner("00-10101011", "Charlie Martel");

   static void setup() {
      RestAssured.baseURI = "http://localhost:8080/BluePM/api";
   }
      
   private void addTestPropertyOwner(PropertyOwner propertyowner) {
      setup();

      given()
         .contentType( ContentType.JSON )
         .header( acceptJson )
         .body( propertyowner )
      .expect()
         .statusCode(204)
         .log().ifError()
         .when()
     .post("/property-owners");
   }

   private void removeTestPropertyOwner( String taxID) {
      setup();
      Response response =
         given()
            .queryParam("searchany", taxID )
        .expect()
            .statusCode(200)
            .log().ifError()
        .when()
            .get("/property-owners/query" );

      List<Map<String,String>> propertyOwnersList = response.jsonPath().getList("");

      given()
         .pathParam("id", propertyOwnersList.get(0).get("id") )
      .expect()
         .statusCode(204)
         .log().ifError()
      .when()
         .delete("/property-owners/{id}");
   }


   @Test public void addPropertyOwnerTest() {
      setup();
      addTestPropertyOwner( po1 );
   }

   @Test public void removePropertyOwnerTest() {
      setup();
      addTestPropertyOwner( po1 );
      removeTestPropertyOwner( po1.getTaxID() );
   }

   @Test public void queryTest() {
      setup();

      Response response =
         given()
            .queryParam("searchany", po1.getTaxID() )
        .expect()
            .statusCode(200)
            .log().ifError()
        .when()
            .get("/property-owners/query" );
   }

}
