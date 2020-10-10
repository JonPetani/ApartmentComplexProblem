package bioteck.apartment.model;

import java.text.DecimalFormat;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.persistence.ForeignKey;

import java.util.List;
import java.util.ArrayList;
import javax.persistence.OneToMany;
import javax.persistence.CascadeType;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NaturalId;

/**
* Represents an Apartment Object with it's own unique attributes like a rent, a number, and a status of being rented or not.
*@Author Jonathan Petani
*@Version 2
*/

@Entity
@Table(name="Apartment")
public class Apartment implements Comparable<Apartment>{
   @Id
   @GeneratedValue(generator = "increment")
   @GenericGenerator(name = "increment", strategy = "increment")
   @Column(name = "id")
   private Long id = Long.valueOf(0);
   
   @NaturalId
   @ManyToOne
   @JoinColumn(name="ApartmentComplexId", nullable=false, foreignKey=@ForeignKey(name="ApartmentComplexId_FK") )
   private ApartmentComplex complex;
   
   @NaturalId
   @Column(name="Number", nullable=false)
   private int number = 0;

   @Column(name="MonthlyRent", nullable=true)
   private double rent = 0.0;   //monthly
   
   @Column(name="Description", nullable=true)
   private String desc = "";

   @OneToMany(mappedBy="apartment", targetEntity=Candidate2Apartment.class, cascade = {CascadeType.ALL})
   private List<Candidate2Apartment> c2a = new ArrayList<Candidate2Apartment>();

   private Apartment() {}
   /**
   * A proper replacement to Null
   */
   public final static Apartment EMPTY = new Apartment(ApartmentComplex.EMPTY, 1);
   
   /**
   * A private Constructor for the Apartment Class. Tests if the inputs are valid.
   *@param complex Takes a complex provided through the IProperty interface iff is not null.
   *@param number Takes a Apartment number iff is positive.
   *@throws IllegalArgumentException if a complex's name is null.
   *@throws IllegalArgumentException if a complex's number is non positive.
   */
   public Apartment(ApartmentComplex complex, int number) throws IllegalArgumentException {
      if (complex == null)
         throw new IllegalArgumentException ("Every complex has a name. This complex was input as null.");
      if (number <= 0)
         throw new IllegalArgumentException ("Apartment numbers are always positive. This number is not positive.");
      this.complex = complex;
      this.number = number;
   }
   
   public Long getId() {
      return this.id;   
   }
   /**
   * Provides the Number of a Apartment to identify it.
   *@return A Apartment number.
   */
   public int getNumber() {
     return this.number;
  }
   /**
   * Provides the Complex the Apartment belongs to.
   *@return A Complex of type IProperty
   */
   public IProperty getComplex() {
     return this.complex;
  }
  
   /**
   * Provides Rent of the Apartment.
   *@return Rent of the Apartment Object
   */
   public double getRent() {
     return this.rent;
  }
  
  public int compareTo(Apartment a) {
   int x = this.complex.compareTo(a.complex);
   if (x != 0) return x;
   return Integer.valueOf(this.number).compareTo(Integer.valueOf(a.number));  
} 
   /**
   * Sets Rent of the Apartment, checking for any negative rent values.
   *@param rent A rent value determined in the Complex class
   *@throws IllegalArgumentException if rent is less than 0, as this would be giving money to tenants
   */
   public void setRent( double rent) throws IllegalArgumentException{
      if (rent < 0.0)
         throw new IllegalArgumentException("You cannot have a negative rent unless paying tenants for living in the apartment is some sort of charity.");
      this.rent = rent;
   }
   
   /**
   *@param String of information about a apartment (how many rooms, size of rooms, floor on complex, etc)
   */
   public void setApartmentDescription(String desc) {
      if (desc == null)
         return;
      this.desc = desc;
   }

   /**
   *@return Description of the Apartment
   */
   public String getApartmentDescription() {
      return desc;
   }
   /**
   * Equals method for a Apartment Object. Makes sure said object is not null and is a instance of the Apartment class
   *@param o Requires a Object as input
   *@return comes false if the object is null or not a instance of Apartment, or calls itself recursively and sets the relevant number to access to the next.
   */
   public boolean equals( Object o ) {
     if (o == null || ! (o instanceof Apartment) )  return false;
     Apartment a = (Apartment) o;
     return this.complex.equals(a.complex) && this.number == a.number;
  }

   /**
   * Hash Code for the Hash Map used in the Complex Class
   */
   public int hashCode() {
     return this.complex.hashCode() + 17 * number;
  }
  
   /**
   * Builds the parts of the Strings that are in the ApartmentComplex[] square brackets (rent, apt num, rent status, Complex Name)
   *@return The String created with the Stringbuilder class.
   */
   public String toString() {
      DecimalFormat formatter = new DecimalFormat("$#.00");
      StringBuilder sb = new StringBuilder("Apartment[");
      sb.append(this.complex).append(", Rent Number = ").append(formatter.format(this.rent))
      .append(", Apartment Number").append(this.number).append(", Descrption of the Apartment: ").append(this.desc).append("]");
      return sb.toString();
   }
   
   /**
   * Create a Apartment Object
   *@param complex Apartment Complex
   *@param number Apartment Number
   *@param rent Apartment Rent
   *@param IsRented Apartment is rented (true) or not (false)
   *@return The Apartment Object.
   */
   static Apartment create( ApartmentComplex complex, int number, double rent, String desc) {
     Apartment a = new Apartment( complex ,number);
     a.setRent(rent);
     a.setApartmentDescription(desc);
     return a;
  }

}


