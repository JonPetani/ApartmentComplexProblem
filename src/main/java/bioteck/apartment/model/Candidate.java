package bioteck.apartment.model;

import java.text.DecimalFormat;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;

import java.util.List;
import java.util.ArrayList;
import javax.persistence.OneToMany;
import javax.persistence.CascadeType;
import org.hibernate.annotations.GenericGenerator;
//import org.hibernate.annotations.NaturalId;
/**
 * Represents Portfolios of Properties
 *
 * @author Jonathan Petani
 * @version 3
 */
@Entity
@Table(name="Candidate")
public class Candidate implements Comparable<Candidate> {

   @Id
   @GeneratedValue(generator = "increment")
   @GenericGenerator(name = "increment", strategy = "increment")
   @Column(name = "id")
   private Long id = Long.valueOf(0);

   //@NaturalId
   @Column(name="FirstName", nullable=false)
   private String fname = " ";

   //@NaturalId
   @Column(name="LastName", nullable=false)
   private String lname = " ";

   @Column(name="PhoneNumber", nullable=true)
   private String phoneNum = " ";

   @Column(name="Address", nullable=true)
   private String address = " ";
   
   //@NaturalId
   @Column(name="SSID", nullable=false)
   private String socialSecurity = " ";

   @Column(name="Income", nullable=true)
   private double salary = 0.0;
    
   @OneToMany(mappedBy="renter", targetEntity=Candidate2Apartment.class, cascade = {CascadeType.ALL})
   private List<Candidate2Apartment> c2a = new ArrayList<Candidate2Apartment>();
    
   private Candidate() {}
   /**
    * Alternative to Null
    */
   public final static Candidate EMPTY = Candidate.create("", "", "", "", "");

   /**
    * MIN and MAX parameters for a given Candidate's SSID
    */
   public final static Candidate MIN = Candidate.create("000-00-0000", "", "", "", "");
   public final static Candidate MAX = Candidate.create("899-99-9999", "", "", "", ""); 

   /**
    * Constructor
    *@param SSID
    *@param Last Name of Candidate
    *@param First Name of Candidate
    *@param Address
    *@param Phone Number
    *@throws IllegalArgumentException if SSID is null
    *@throws IllegalArgumentException if Last Name is null
    *@throws IllegalArgumentException if First Name is null
    *@throws IllegalArgumentException if Address is null
    *@throws IllegalArgumentException if Phone Number is null
    */
   public Candidate(String SocialSecurity, String LName, String FName )
      throws IllegalArgumentException {
      if (FName == null)
         throw new IllegalArgumentException("Everybody has a first name presumibly. No name was provided.");
      if (LName == null)
         throw new IllegalArgumentException("Everybody has a last name presumibly. No name was provided.");
      if (SocialSecurity == null)
         throw new IllegalArgumentException("Every candidate must have a social security number to be included, none was provided.");
      this.fname = FName;
      this.lname = LName;
      this.socialSecurity = SocialSecurity;
   }
    
   public static Candidate create( String socialSecurity, String lname, String fname,
                                   String address, String phoneNumber ) {
      Candidate c = new Candidate( socialSecurity, lname, fname);
      c.setAddress( address);
      c.setPhoneNumber( phoneNumber );
      return c;
   }

   /**
    *@return Id of Table
    */
   public Long getId() {
      return this.id;
   }
   /**
    * Sets Phone Number of a Candidate if it has to be changed
    *@param A Phone Number String
    */
   public void setPhoneNumber(String PhoneNum) {
      this.phoneNum = PhoneNum;
   }

   /**
    *@return Phone Number String
    */
   public String getPhoneNumber() {
      return this.phoneNum;
   }

   /**
    *@return Address String
    */
   public void setAddress(String address) {
      this.address = address.trim();
   }

   /**
    *@return Address String
    */
   public String getAddress() {
      return this.address;
   }

   /**
    * Sets salary of a Candidate
    * For the exception here, it makes sense that some people's income is negative if they are in debt and unemployed(worst candidate of all)
    *@param A Salary amount
    */
   public void setSalary(double Salary) {
      this.salary = Salary;
   }

   /**
    *@return The salary of a Candidate
    */
   public double getSalary() {
      return this.salary;
   }

   /**
    *@return A Candidate's First Name
    */
   public String getFName() {
      return this.fname;
   }

   /**
    *@return A Candidate's Last Name
    */
   public String getLName() {
      return this.lname;
   }

   /**
    *@return A SSID String
    */
   public String getSocialSecurity() {
      return this.socialSecurity;   
   }
   
   /**
    * Equals Method
    *@param Takes a object
    *@return True if not null and a instance of Candidates
    */
   public boolean equals(Object o) {
      if (o == null || ! (o instanceof Candidate) )  return false;
      Candidate C = (Candidate) o;
      return this.socialSecurity == C.socialSecurity;
   }
   
   /**
    * Hash Code for various data structures
    *@return integer value from hashcode
    */
   public int hashCode() {
      return this.socialSecurity.hashCode();
   }

   /**
    * compareTo method
    *@param A Candidate
    *@return Integer to be used by a TreeSet in locating data
    */
   public int compareTo(Candidate candidate) {
      return this.socialSecurity.compareTo(candidate.socialSecurity);
   }

   /**
    *@return ToString for Candidate Class
    *@return A string will all related information
    */
   public String toString() {
      DecimalFormat formatter = new DecimalFormat("$#.00");
      StringBuilder sb = new StringBuilder("Candidate[");
      sb.append(" Name = ").append(this.fname).append(" ").append(this.lname)
         .append(", Phone Number ").append(this.phoneNum).append(", Salary = ").append(formatter.format(this.salary)).append(", SSID = ").append(this.socialSecurity).append("]\n");
      return sb.toString();
   }
}
