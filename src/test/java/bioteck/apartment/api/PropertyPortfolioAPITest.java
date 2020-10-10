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

import bioteck.apartment.model.PropertyPortfolio;
import bioteck.apartment.model.PropertyOwner;
import bioteck.apartment.db.DB;

public class PropertyPortfolioAPITest {
   private Header acceptJson = new Header("Accept", "application/json");
   private PropertyOwner po = new PropertyOwner("00-10101011", "Charlie Martel");
   private PropertyPortfolio pp1 =  new PropertyPortfolio(po, "Portable Portfolio");

   static void setup() {
      RestAssured.baseURI = "http://localhost:8080/BluePM/api";
   }
   
   private void addTestPropertyPortfolio(PropertyPortfolio propertyportfolio) {
      setup();

      given()
         .contentType( ContentType.JSON )
         .header( acceptJson )
         .body( propertyportfolio )
      .expect()
         .statusCode(204)
         .log().ifError()
         .when()
     .post("/property-portfolios");
   }

   private void removeTestPropertyPortfolio( String taxID) {
      setup();
      Response response =
         given()
            .queryParam("searchany", taxID )
        .expect()
            .statusCode(200)
            .log().ifError()
        .when()
            .get("/property-portfolios/query" );

      List<Map<String,String>> propertyPortfoliosList = response.jsonPath().getList("");

      given()
         .pathParam("id", propertyPortfoliosList.get(0).get("id") )
      .expect()
         .statusCode(204)
         .log().ifError()
      .when()
         .delete("/property-portfolios/{id}");
   }


   @Test public void addPropertyPortfolioTest() {
      setup();
      DB.transact(s -> {s.save(po);});
      addTestPropertyPortfolio( pp1 );
   }

   @Test public void removePropertyPortfolioTest() {
      setup();
      DB.transact(s -> {s.save(po);});
      addTestPropertyPortfolio( pp1 );
      removeTestPropertyPortfolio( pp1.getOwner().getTaxID() );
   }

   @Test public void queryTest() {
      setup();

      Response response =
         given()
            .queryParam("searchany", pp1.getOwner().getTaxID() )
        .expect()
            .statusCode(200)
            .log().ifError()
        .when()
            .get("/property-portfolios/query" );
   }

}
