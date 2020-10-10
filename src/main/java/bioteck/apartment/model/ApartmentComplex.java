package bioteck.apartment.model;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.Collections;
import java.util.Collection;
import java.util.stream.Stream;
import java.util.stream.Collectors;
import java.text.DecimalFormat;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.OneToMany;
import javax.persistence.ManyToOne;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import org.hibernate.annotations.GenericGenerator;
import java.util.ArrayList;
import org.hibernate.annotations.NaturalId;

/**
* Represents a Apartment Complex which contains apartment(s). Can be identified
* by it's name and it's address.
*@Author Jonathan Petani
*@Version 3
*/

@Entity
@Table(name="ApartmentComplex")
public class ApartmentComplex implements IProperty, Comparable<IProperty> {
   
   @Id
   @GeneratedValue(generator = "increment")
   @GenericGenerator(name = "increment", strategy = "increment")
   @Column(name = "id")
   private Long Id = Long.valueOf(0);
   
   @NaturalId
   @Column(name="Name", nullable=false)
   private String name = "";

   @Column(name="MonthlyCost", nullable=true)
   private double monthlyCosts = 0.0;
   
   @NaturalId
   @Column(name="Address", nullable=false)
   private String address = "";
   
   @Column(name="Description", nullable=true)
   private String desc = "";

   @OneToMany( mappedBy="complex", targetEntity = Apartment.class, cascade = { CascadeType.ALL })
   private List<Apartment> apartments = new ArrayList<>();
   
   @NaturalId
   @ManyToOne
   @JoinColumn(name="PropertyPortfolioId", nullable=true, foreignKey=@ForeignKey(name="PropertyPortfolioId_FK"))
   private PropertyPortfolio portfolio;

   private ApartmentComplex() {}


   /**
   * Alternative to null.
   */
   public final static ApartmentComplex EMPTY = new ApartmentComplex("", "");

   /**
   * Constructs Identifying traits of the Complex. It's Portfolio and the Cost
   *
   *@param name Requires the complex's name.
   *@param Portfolio.
   *@throws IllegalArgumentException if complex name is null 
   *@throws IllegalArgumentException if portfolio is null
   */
   public ApartmentComplex(String name, String address) throws IllegalArgumentException {
      if (name == null)
         throw new IllegalArgumentException("Complex Name is null, which is impossible.");
	   this.name = name;
      if (address == null)
         throw new IllegalArgumentException("Every Complex must have a address");
      this.address = address;
   }
   
   /**
   *@return Id of Table
   */   
   public Long getId() {
   return this.Id;
   }

   /**
   *@param String of information about a complex (how premium the complex's apartments typically are, size of property, location info, etc)
   */
   public void setComplexDescription(String desc) {
      if (desc == null)
         return;
      this.desc = desc;
   }

   /**
   *@return Description of the Apartment Complex
   */
   public String getComplexDescription() {
      return desc;
   }

   /**
   *@return Description of the Apartment Complex
   */
   public String getAddress() {
      return address;
   }

   /**
   *@param A Property Portfolio to contain the property
   */
   public void setPortfolio(PropertyPortfolio portfolio) {
   if (portfolio == null)
      return;
   this.portfolio = portfolio;
   }
   
   /**
   *@return The Property Portfolio of the property
   */
   public PropertyPortfolio getPortfolio() {
   return portfolio;
   }
   /**
   * Will provide the apartment complex' name
   *@return non null Name of Apartment Complex
   */
   public String getName() {
	   return this.name;
   }
    
   /**
   * Sets the Costs for the given Complex
   *@param cost Requires a input of cost
   * Will only change if the cost is more than 0 as the government giving the owner money is rare
   */

   public void setCost( double cost ) {
      if (cost >= 0.0)
	      this.monthlyCosts = cost;
   }
    
   /**
   * Will provie the cost of the given Complex
   */
   public double getCost() {
	   return this.monthlyCosts;
   }
    
   /**
   * A proper Equals method for the Hash Map
   *@param o Requires a object of the ApartmentComplex type
   *@return the boolean status of the object/ comes false if it is either null or isn't a instance of the ApartmentComplex class
   */
   @Override
   public boolean equals( Object o) {
	   if (o == null || ! (o instanceof ApartmentComplex)) return false;
	      ApartmentComplex AP = (ApartmentComplex) o;
	   return this.name.equals( AP.name );
   }
    
   /**
   * hashCode for the Hash Map.
   *@return the integer in relation to the name's hashcode
   */
   @Override
   public int hashCode() {
	   return this.name.hashCode();
   }
	public int compareTo(IProperty I) {
      return this.name.compareTo(I.getName());
   }

   /**
   * Builds the outer part of the printable string to describe the apartment complex and it's attributes.
   */
	@Override
   public String toString() {
	   StringBuilder sb = new StringBuilder("ApartmentComplex[");
	   sb.append(this.name).append(", ").append(this.address).append(", Description: ").append(this.desc).append("]");
	   return sb.toString();
   }
    
   /**
   * Creates a Apartment Object to add to the Complex Collection.
   *@param There must be a Apartment as input in order to create it.
   */
   public void addApartment( Apartment a) {
	   if (a == null) return;
	   apartments.add(a);
   }
   
   public void deleteApartment(Apartment a) {
   if (a != null)
     apartments.remove(a);
   }
   
   public List<Apartment> findApartments() {
   return apartments;
   }
   /**
   * Creates a Apartment Complex object with its own name and cost to upkeep.
   *@param Requires a name of the complex and the total number of costs related to the complex.
   *@return A Apartment Complex object.
   */
   public static final ApartmentComplex create( String name, double cost, PropertyPortfolio portfolio, String address, String desc ) {
	   ApartmentComplex cmplx = new ApartmentComplex(name, address);
      cmplx.setPortfolio(portfolio);
	   cmplx.setCost(cost);
      portfolio.addProperty(cmplx);
      cmplx.setComplexDescription(desc);
      return cmplx;
   }
    
}
