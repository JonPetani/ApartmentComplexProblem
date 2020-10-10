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

import bioteck.apartment.db.DB;
import bioteck.apartment.model.PropertyPortfolio;
import bioteck.apartment.model.PropertyOwner;
import bioteck.apartment.model.ApartmentComplex;

public class ApartmentComplexAPITest {
   private Header acceptJson = new Header("Accept", "application/json");
   private PropertyOwner po = new PropertyOwner("00-10101011", "Charlie Martel");
   private PropertyPortfolio pp1 =  new PropertyPortfolio(po, "Portable Portfolio");
   private ApartmentComplex ac1 = ApartmentComplex.create("A Complex complex", 4500, pp1, "123 Number Street", "A Big Complex");

   static void setup() {
      RestAssured.baseURI = "http://localhost:8080/BluePM/api";
   }
   
   private void addTestApartmentComplex(ApartmentComplex apartmentcomplex) {
      setup();

      given()
         .contentType( ContentType.JSON )
         .header( acceptJson )
         .body( apartmentcomplex )
      .expect()
         .statusCode(204)
         .log().ifError()
         .when()
     .post("/apartment-complexes");
   }

   private void removeTestApartmentComplex( String name) {
      setup();
      Response response =
         given()
            .queryParam("searchany", name )
        .expect()
            .statusCode(200)
            .log().ifError()
        .when()
            .get("/apartment-complexes/query" );

      List<Map<String,String>> apartmentComplexesList = response.jsonPath().getList("");

      given()
         .pathParam("id", apartmentComplexesList.get(0).get("id") )
      .expect()
         .statusCode(204)
         .log().ifError()
      .when()
         .delete("/apartment-complexes/{id}");
   }


   @Test public void addApartmentComplexTest() {
      setup();
      DB.transact(s ->{s.save(po);});
      DB.transact(s ->{s.save(pp1);});
      addTestApartmentComplex( ac1 );
   }

   @Test public void removeApartmentComplexTest() {
      setup();
      DB.transact(s ->{s.save(po);});
      DB.transact(s ->{s.save(pp1);});
      removeTestApartmentComplex( ac1.getName() );
   }

   @Test public void queryTest() {
      setup();

      Response response =
         given()
            .queryParam("searchany", ac1.getName() )
        .expect()
            .statusCode(200)
            .log().ifError()
        .when()
            .get("/apartment-complexes/query" );
   }

}
