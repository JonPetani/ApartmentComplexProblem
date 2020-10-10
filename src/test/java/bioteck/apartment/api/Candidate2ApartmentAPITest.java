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
import bioteck.apartment.model.Candidate;
import bioteck.apartment.model.Candidate2Apartment;
public class Candidate2ApartmentAPITest {
   private Header acceptJson = new Header("Accept", "application/json");
   private ApartmentComplex ac1 = new ApartmentComplex("A Complex complex", "123 Number Street");
   private Apartment a1 =  new Apartment(ac1, 1);
   private Candidate c1 = new Candidate("394-321-3454", "Francis", "John"
                                         );
   private Candidate2Apartment c12a1 = new Candidate2Apartment(c1, a1);
   static void setup() {
      RestAssured.baseURI = "http://localhost:8080/BluePM/api";
   }
   
   private void addTestCandidate2Apartment(Candidate2Apartment candidate2apartment) {
      setup();

      given()
         .contentType( ContentType.JSON )
         .header( acceptJson )
         .body( candidate2apartment )
      .expect()
         .statusCode(204)
         .log().ifError()
         .when()
     .post("/candidate2apartments");
   }

   private void removeTestCandidate2Apartment(String socialSecurity, int number) {
      setup();
      Response response =
         given()
            .queryParam("searchany", socialSecurity, number)
        .expect()
            .statusCode(200)
            .log().ifError()
        .when()
            .get("/candidate2apartments/query" );

      List<Map<String,String>> candidate2apartmentsList = response.jsonPath().getList("");

      given()
         .pathParam("id", candidate2apartmentsList.get(0).get("id") )
      .expect()
         .statusCode(204)
         .log().ifError()
      .when()
         .delete("/candidate2apartments/{id}");
   }


   @Test public void addCandidate2ApartmentTest() {
      setup();
      addTestCandidate2Apartment( c12a1 );
   }

   @Test public void removeCandidate2ApartmentTest() {
      setup();
      removeTestCandidate2Apartment( c1.getSocialSecurity(), a1.getNumber() );
   }

   @Test public void queryTest() {
      setup();

      Response response =
         given()
            .queryParam("searchany", c1.getSocialSecurity(), a1.getNumber() )
        .expect()
            .statusCode(200)
            .log().ifError()
        .when()
            .get("/candidate2apartments/query" );
   }

}
