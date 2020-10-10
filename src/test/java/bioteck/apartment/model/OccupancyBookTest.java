package bioteck.apartment.model;
 
import java.util.Arrays;
import java.util.Map;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import java.util.Set;
import bioteck.apartment.db.DB;

public class OccupancyBookTest {
 
   private Candidate renter1 = Candidate.create("124-55-0000", "Kasich", "John", "100 Street Drive, Langston, North America", "456-543-4543");
   private Candidate renter2 = Candidate.create("124-55-0010", "Brown", "Larry", "454 Corn Path, Omaha, Nebraska", "454-344-3234");
   private Candidate renter3 = Candidate.create("124-55-0020", "Paredes", "Juan", "123 Taco Road, Mexico City, Mexico", "323-454-3222");
   private Candidate renter4 = Candidate.create("124-55-0030", "Ostrovsky", "Lenimere", "Red Square, Moscow, Russia", "392-494-3232");            
 
   private ApartmentComplex cmplx = new ApartmentComplex( "Murky Waters", "117 Texas Way, Iowa");
   private Apartment apt1 = Apartment.create( cmplx, 1, 300.0, "Big Apt");
   private Apartment apt2 = Apartment.create( cmplx, 2, 300.0, "Big Apt 2");    
   private ApartmentComplex cmplx2 = new ApartmentComplex( "Paradise on Earth", "45 HotDog Avenue, New York");
   private Apartment apt3 = Apartment.create( cmplx2, 3, 600.0, "Big Apt 3");    
 
   private OccupancyBook booked = OccupancyBook.getSingleton();
 
   @BeforeEach void init() {

      this.renter1 = Candidate.create("124-55-0000", "Kasich", "John", "100 Street Drive, Langston, North America", "456-543-4543");
      this.renter2 = Candidate.create("124-55-0010", "Brown", "Larry", "454 Corn Path, Omaha, Nebraska", "454-344-3234");
      this.renter3 = Candidate.create("124-55-0020", "Paredes", "Juan", "123 Taco Road, Mexico City, Mexico", "323-454-3222");
      this.renter4 = Candidate.create("124-55-0030", "Ostrovsky", "Lenimere", "Red Square, Moscow, Russia", "392-494-3232");            
      this.cmplx = new ApartmentComplex( "Murky Waters", "117 Texas Way, Iowa");
      this.apt1 = Apartment.create( cmplx, 1, 300.0, "Big Apt");
      this.apt2 = Apartment.create( cmplx, 2, 300.0, "Big Apt 2");    
      this.cmplx2 = new ApartmentComplex( "Paradise on Earth", "45 HotDog Avenue, New York");
      this.apt3 = Apartment.create( cmplx2, 3, 600.0, "Big Apt 3");    

      DB.transact ( s -> {
            s.save(this.renter1);
            s.save(this.renter2);
            s.save(this.renter3);
            s.save(this.renter4);
            this.cmplx = s.get(ApartmentComplex.class, s.save(this.cmplx));
            s.save(this.apt1);
            s.save(this.apt2);
            this.cmplx2 = s.get(ApartmentComplex.class, s.save(this.cmplx2));
            s.save(this.apt3);
         });
      this.booked = OccupancyBook.getSingleton();
      booked.addRenterToApartment( renter1, apt1);
      booked.addRenterToApartment( renter2, apt2);
      booked.addRenterToApartment( renter3, apt3);
      booked.addRenterToApartment( renter4, apt3);
      booked.addRenterToApartment( renter4, apt1);

   }
   
   @AfterEach void tearDown() {
      this.booked.clear();
      DB.transact( s -> {
            s.createQuery("delete from Candidate").executeUpdate();
            s.createQuery("delete from Apartment").executeUpdate();      
            s.createQuery("delete from ApartmentComplex").executeUpdate();     
         });  
      
   }
   
   @Test void addRenterTest() {
 
      Set<Candidate> rfapt3 = booked.rentersForApartment( apt1 );
      System.out.println(rfapt3.size());
      assertEquals( rfapt3.size(), 2 );
      
      Set<Candidate> rfapt1 = booked.rentersForApartment( apt3 );
      System.out.println(rfapt1.size());
      assertEquals( rfapt1.size(), 2 );        
   }
 
   @Test void removeRenterTest() {
      booked.removeRenterFromApartment( renter3, apt3);
      Set<Candidate> rfapt3 = booked.rentersForApartment( apt3 );
      assertEquals( rfapt3.size(), 1 );
   }
   
   @Test void calculateRentStatementTest() {    
      Map<Apartment,Double> aptsForRenter3 = booked.calculateRentStatement( renter3 );
      double totalRent4Renter3 = aptsForRenter3.values().stream().mapToDouble( d -> d.doubleValue()).sum();
      assertEquals( totalRent4Renter3, 300.0);
 
      Map<Apartment,Double> aptsForRenter4 = booked.calculateRentStatement( renter4 );
      double totalRent4Renter4 = aptsForRenter4.values().stream().mapToDouble( d -> d.doubleValue()).sum();
      assertEquals( totalRent4Renter4, 450.0);    
   }

   @Test void calculateMonthlyRevenueTest() {
      double revenue = booked.monthlyRevenue( cmplx );
      assertEquals( revenue, 600.0);
 
      revenue = booked.monthlyRevenue( cmplx2 );
      assertEquals( revenue, 600.0);    
   }
}

