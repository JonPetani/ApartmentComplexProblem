package bioteck.apartment.model;

import java.util.Set;
import java.util.HashSet;
import java.util.TreeMap;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.persistence.ForeignKey;
import javax.persistence.OneToMany;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NaturalId;

/**
 * Represents Portfolios of Properties
 *
 * @author Jonathan Petani
 * @version 3
 */
@Entity
@Table(name="PropertyPortfolio")
public class PropertyPortfolio implements Comparable<PropertyPortfolio>{

   @Id
   @GeneratedValue(generator = "increment")
   @GenericGenerator(name = "increment", strategy = "increment")
   @Column(name = "id")
   Long id = Long.valueOf(0);
   
   @NaturalId
   @Column(name="Name", nullable=false)
   private String name = "";
   
   @NaturalId
   @ManyToOne
   @JoinColumn(name="PropertyOwnerId", nullable=false,foreignKey=@ForeignKey(name= "PropertyOwnerId_FK"))
   private PropertyOwner owner = PropertyOwner.EMPTY;


   @OneToMany(mappedBy="portfolio", targetEntity=ApartmentComplex.class)
   private List<ApartmentComplex> properties = new ArrayList<>();
   

   private PropertyPortfolio() {}
   /**
   *Constructor
   *@param A PropertyOwner
   *@param A Name for the Portfolio
   *@throws IllegalArgumentException if name is null
   *@throws IllegalArgumentException if property owner is null
   */
   public PropertyPortfolio( PropertyOwner owner, String name ) throws IllegalArgumentException {
      if (name == null) 
         throw new IllegalArgumentException( "null name");
      if (owner == null)
         throw new IllegalArgumentException( "null property owner");
      this.owner = owner;
      this.name = name;
   }
   
   /**
   *@return Id of Table
   */
   public Long getId() {
      return this.id;
   }
   /**
   *@return Name of portfolio
   */
   public String getName() {
      return this.name;
   }
   
   /**
   *@return Property Owner name
   */
   public PropertyOwner getOwner() {
      return this.owner;
   }
   
   public void setOwner(PropertyOwner owner) {
   this.owner = owner;   
   } 
   /**
   * Calculates Cost of the Portfolio
   *@throw IllegalArgumentException if Cost is negative. IRS seldom gives you money
   *@return A Cost double (sum of costs for all Apartment Complex(s) included in Portfolio)
   */
   public double Cost() throws IllegalArgumentException {
      double Cost = properties.stream().mapToDouble( p -> p.getCost()).sum();
      if (Cost < 0)
      throw new IllegalArgumentException("There is no such thing as a negative cost");
      return Cost;
   }
   
   /**
   * Calculates Revenue of the Portfolio
   *@return A Revenue double (sum of all rents collected)
   */
   public double Revenue() throws IllegalArgumentException {
      OccupancyBook B = OccupancyBook.getSingleton();
      if (properties.stream().mapToDouble( p -> B.monthlyRevenue(p)).sum() < 0)
         throw new IllegalArgumentException("Revenue can't be negative.");
      return properties.stream().mapToDouble( p -> B.monthlyRevenue(p)).sum();
   }
   
   /**
   * Calculates Profit
   *@return A Profit double (Revenue - Cost)
   */
   public double Profit() {
      return Revenue() - Cost();
   }
   /**
   * How much to pay each developer of the software
   *@param number of developers
   *@return Salary of each developer as Profit / Engineers in project
   */
   public double Salary(int EngineerNum) throws IllegalArgumentException{
      if (EngineerNum <= 0)
         throw new IllegalArgumentException("Can guarantee at least one person programmed this software.");
      return Profit() / EngineerNum;
   }
   
   /**
   * Hash Code for various data structures
   *@return integer value from hashcode
   */
   public int hashCode() {
      return this.name.hashCode() + 17 * this.owner.hashCode();
   }
   
   /**
   * Equals Method
   *@param Takes a object
   *@return True if not null and a instance of Portfolios and if owner in the portfolio and the current owner are different
   */
   public boolean equals( Object o) {
      if (o == null || !(o instanceof PropertyPortfolio) ) return false;
      PropertyPortfolio Port = (PropertyPortfolio) o;
      if ( this.owner != Port.owner || this.name != Port.name) return false;
      return true;
   }
   /**
   * compareTo method
   *@param A Portfolio
   *@return Integer to be used by a TreeSet in locating data
   */
   public int compareTo(PropertyPortfolio Port) {
      int A = this.getOwner().compareTo(Port.getOwner());
      if (A != 0) return A;
      return this.getName().compareTo(Port.getName());
   }
   
   /**
   * Adds a Complex to the Portfolio
   *@param A Apartment Complex
   */
   public void addProperty( ApartmentComplex prop ) {
      if (prop == null) return;
      properties.add(prop);
   }
   
   /**
   * Removes a Complex if the user needs to from the Portfolio
   *@param A Apartment Complex
   */
   public void removeProperty( ApartmentComplex prop ) {
      if (prop == null) return;
      properties.remove(prop);
   }
   
   /**
   * Gets together all Complex(s) in the Portfolio
   *@return A Set of Apartment Complex(s)
   */
   public List<ApartmentComplex> getAllProperties() {
      //return new ArrayList<ApartmentComplex>(properties);
      return properties;
   }
   /**
   * ToString for Portfolio Class
   *@return A string will all related information
   */
   public String toString() {
      StringBuffer sb = new StringBuffer("PropertyPortfolio: ");
      sb.append("owner= ").append(this.owner).append(",");
      sb.append("name= ").append(this.name);
      return sb.toString();  
   }
}
