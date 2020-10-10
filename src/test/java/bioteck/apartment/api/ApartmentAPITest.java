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

import bioteck.apartment.model.Apartment;
import bioteck.apartment.model.ApartmentComplex;

public class ApartmentAPITest {
   private Header acceptJson = new Header("Accept", "application/json");
   private ApartmentComplex ac1 = new ApartmentComplex("A Complex complex", "123 Number Street");
   private Apartment a1 =  new Apartment(ac1, 1);

   static void setup() {
      RestAssured.baseURI = "http://localhost:8080/BluePM/api";
   }
   
   private void addTestApartment(Apartment apartment) {
      setup();

      given()
         .contentType( ContentType.JSON )
         .header( acceptJson )
         .body( apartment )
      .expect()
         .statusCode(204)
         .log().ifError()
         .when()
     .post("/apartments");
   }

   private void removeTestApartment( int number) {
      setup();
      Response response =
         given()
            .queryParam("searchany", number )
        .expect()
            .statusCode(200)
            .log().ifError()
        .when()
            .get("/apartments/query" );

      List<Map<String,String>> apartmentsList = response.jsonPath().getList("");

      given()
         .pathParam("id", apartmentsList.get(0).get("id") )
      .expect()
         .statusCode(204)
         .log().ifError()
      .when()
         .delete("/apartments/{id}");
   }


   @Test public void addApartmentTest() {
      setup();
      addTestApartment( a1 );
   }

   @Test public void removeApartmentTest() {
      setup();
      removeTestApartment( a1.getNumber() );
   }

   @Test public void queryTest() {
      setup();

      Response response =
         given()
            .queryParam("searchany", a1.getNumber() )
        .expect()
            .statusCode(200)
            .log().ifError()
        .when()
            .get("/apartments/query" );
   }

}
