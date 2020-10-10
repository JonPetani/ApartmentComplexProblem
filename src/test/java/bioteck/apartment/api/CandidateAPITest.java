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

import bioteck.apartment.model.Candidate;

public class CandidateAPITest {
   private Header acceptJson = new Header("Accept", "application/json");
   private Candidate c1 =  Candidate.create("394-321-3454", "Francis", "John",
                                         "55, Granite Street, Hooksett, NH", "584-322-9965");

   static void setup() {
      RestAssured.baseURI = "http://localhost:8080/BluePM/api";
   }
      
   private void addTestCandidate(Candidate candidate) {
      setup();

      given()
         .contentType( ContentType.JSON )
         .header( acceptJson )
         .body( candidate )
      .expect()
         .statusCode(204)
         .log().ifError()
         .when()
     .post("/candidates");
   }

   private void removeTestCandidate( String ssn) {
      setup();
      Response response =
         given()
            .queryParam("searchany", ssn )
        .expect()
            .statusCode(200)
            .log().ifError()
        .when()
            .get("/candidates/query" );

      List<Map<String,String>> candidatesList = response.jsonPath().getList("");

      given()
         .pathParam("id", candidatesList.get(0).get("id") )
      .expect()
         .statusCode(204)
         .log().ifError()
      .when()
         .delete("/candidates/{id}");
   }


   @Test public void addCandidateTest() {
      setup();
      addTestCandidate( c1 );
   }

   @Test public void removeCandidateTest() {
      setup();

      removeTestCandidate( c1.getSocialSecurity() );
   }

   @Test public void queryTest() {
      setup();

      Response response =
         given()
            .queryParam("searchany", c1.getPhoneNumber() )
        .expect()
            .statusCode(200)
            .log().ifError()
        .when()
            .get("/candidates/query" );
   }

}
