package bioteck.apartment.model;
import java.util.Map;
import java.util.TreeSet;
import java.util.List;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.HashMap;
import java.util.stream.Stream;
import java.util.SortedSet;
import java.util.stream.Collectors;
import java.util.ArrayList;

import javax.persistence.ManyToOne;
import javax.persistence.ForeignKey;
import javax.persistence.Table;
import javax.persistence.JoinColumn;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;

import org.hibernate.Transaction;
import org.hibernate.Session;
import java.io.Serializable;
import bioteck.apartment.db.DB;

/**
 * A Occupancy Book to connect Apartments to Candidates and vice versa
 *
 * @author Jonathan Petani
 * @version 3
 */

public class OccupancyBook {

   private static final OccupancyBook onlyBook = new OccupancyBook();  

   private OccupancyBook() {
   }

   /**
    * Singleton Method
    *@return A occupancy book
    */
   public static OccupancyBook getSingleton() {
      return onlyBook;
   }
   
   /**
    * Clears out all data stored in the Tree Sets
    */
   public void clear() {
      DB.transact ( s -> {
            String bDelete = "delete Candidate2Apartment ";
            int DeletedEntities = s.createQuery( bDelete ).executeUpdate();
         });
   }
   
   /**
    * Adds a Candidate to a Apartment when they can become a renter
    *@param A Candidate to add to the Apartment
    *@param A Apartment for the Candidate to rent
    */
   public void addRenterToApartment(Candidate renter, Apartment apt) {
      if (renter == (Candidate) null || renter == Candidate.EMPTY)
         return;
      if (apt == (Apartment) null || apt == Apartment.EMPTY)
         return;
         
      DB.transact ( s -> {
            List<Candidate> rr = s.createQuery(
                                               "select c " +
                                               "from Candidate c " +
                                               "where c.socialSecurity = :cssn",
                                               Candidate.class)
               .setParameter( "cssn", renter.getSocialSecurity())
               .getResultList();

            List<Apartment> aa = s.createQuery(
                                               "select a " +
                                               "from Apartment a " +
                                               "where a.complex.name = :acn " +
                                               "  and a.number = :an ",
                                               Apartment.class)
               .setParameter( "acn", apt.getComplex().getName())
               .setParameter( "an", apt.getNumber())
               .getResultList();
            Candidate cr = rr.isEmpty() ? cr = s.get(Candidate.class, s.save(renter)) : rr.get(0);
            Apartment a1 = aa.isEmpty() ? a1 = s.get(Apartment.class, s.save(apt)) : aa.get(0);
            s.save( new Candidate2Apartment(cr,a1) );
         });
   }
   
   /**
    * Removes a Candidate from the Apartment when they are no longer renting it
    *@param The Candidate no longer renting the Apartment
    *@param The Apartment he Candidate is no longer renting
    */
   public void removeRenterFromApartment( Candidate renter, Apartment apt) {

      if (renter == null || renter == Candidate.EMPTY)
         return;
      if (apt == null || apt == Apartment.EMPTY)
         return;
          
      DB.transact(s -> {s.createQuery( "delete Candidate2Apartment c2a where " +
                                       "c2a.apartment = :acn and " +
                                       "c2a.renter = :cssn ")
               .setParameter( "acn", apt)
               .setParameter( "cssn", renter)
               .executeUpdate();
         });
   }
   
   /**
    * Creates a Set for all Tenants of a given Apartmennt
    *@param A Apartment to base the Set off of
    *@return A Set of Candidates for the given Apartment
    */
   public Set<Candidate> rentersForApartment( Apartment a ) {
      
      if (a == null) return new HashSet<Candidate>();

      List<Candidate2Apartment> ra = DB.query( s -> s.createQuery(
                                                                  "select c2a " +
                                                                  "from Candidate2Apartment c2a " +
                                                                  "where c2a.apartment.complex.name = :acn " +
                                                                  "  and c2a.apartment.number = :an ",
                                                                  Candidate2Apartment.class)
                                               .setParameter( "acn", a.getComplex().getName())
                                               .setParameter( "an", a.getNumber())
                                               .getResultList()
                                               );

      return ra.stream().map( g -> g.getRenter() ).collect( Collectors.toSet());
   }
   
   /**
    *@return A HashSet for all occupied apartments using the Apartment2RenterKey
    */
   public Set<Candidate2Apartment> occupancySet() {
    
      List<Candidate2Apartment> ca = DB.query( s -> s.createQuery(
                                                                  "select c2a " +
                                                                  "from Candidate2Apartment c2a ",
                                                                  Candidate2Apartment.class).getResultList()
                                               );
      return new HashSet<Candidate2Apartment>( ca );
   }
   
   /**
    * Calculates rent for a given renter
    *@param A renter
    *@return A Mapping of the Apartment's Rent to the Renter
    */
   public Map<Apartment,Double>  calculateRentStatement( Candidate renter ) {
 
      
      List<Candidate2Apartment> cc = DB.query(s -> s.createQuery(
                                                                 "select c2a " +
                                                                 "from Candidate2Apartment c2a " +
                                                                 "where c2a.renter.socialSecurity = :cssn",
                                                                 Candidate2Apartment.class)
                                              .setParameter( "cssn", renter.getSocialSecurity())
                                              .getResultList()
                                              );


      // Do a simple calculation for rent:  Evenly divide rent by the number of  renters
      // in the apartment !z  
      return cc.stream()
         .map( g -> g.getApartment() ).collect
         ( Collectors.toMap( a -> a, a -> a.getRent() / this.rentersForApartment(a).size() ));
   }
   
   /**
    * Calculate monthly revenue of a given Complex
    *@param A Apartment Complex
    *@return The monthly revenue of the provided complex
    */
   public double monthlyRevenue(ApartmentComplex property) {
      if (property == null)
         return 0.0;
      
      List<Candidate2Apartment> ac = DB.query( s -> s.createQuery(
                                                                  "select c2a " +
                                                                  "from Candidate2Apartment c2a " +
                                                                  "where c2a.apartment.complex.name= :acname",
                                                                  Candidate2Apartment.class).setParameter( "acname", property.getName()).getResultList());
      return ac.stream()
         .map( g -> g.getApartment() )
         .collect( Collectors.toSet())
         .stream()
         .mapToDouble( g -> g.getRent())
         .sum();
   }
    
}
