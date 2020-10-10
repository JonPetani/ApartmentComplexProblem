package bioteck.apartment.model;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.OneToMany;
import javax.persistence.CascadeType;
import org.hibernate.annotations.GenericGenerator;
import java.util.List;
import java.util.ArrayList;
import org.hibernate.annotations.NaturalId;

/**
 * Represents Property Owners of a Portfolio
 *
 * @author Jonathan Petani
 * @version 3
 */

@Entity
@Table(name="PropertyOwner")
public class PropertyOwner implements IReachable, Comparable<PropertyOwner> {
   
   @Id
   @GeneratedValue(generator = "increment")
   @GenericGenerator(name = "increment", strategy = "increment")
   @Column(name = "id")
   private Long id = Long.valueOf(0);
   
   @NaturalId
   @Column(name="TaxID", nullable=false)
   private String taxID = "";
   
   @NaturalId
   @Column(name="Name", nullable=false)
   private String name = "";

   @Column(name="Address", nullable=true)
   private String address = "";
   
   @OneToMany(mappedBy="owner", targetEntity=PropertyPortfolio.class, cascade={CascadeType.ALL})
   private List<PropertyPortfolio> portfolios = new ArrayList<>();


   /**
   *Alternative to Null
   */
   public static final PropertyOwner EMPTY = new PropertyOwner("","");
   
   private PropertyOwner() {}
   /**
   * Constructor
   *@param Tax ID of Owner
   *@param Name of Owner
   *@throws IllegalArgumentException if taxID is null
   *@throws IllegalArgumentException if Name is null
   */
   public PropertyOwner(String  taxID, String name ) {
      if (taxID == null )
         throw new IllegalArgumentException( "null taxID");
      if (name == null )
         throw new IllegalArgumentException( "null name");

      this.taxID = taxID;
      this.name = name;
   }
   /**
   *@return Id of Table
   */
   public Long getId() {
   return this.id;
   }
   /**
   *@return Tax ID String
   */
   public String getTaxID() {
      return this.taxID;
   }

   /**
   *@return Name String
   */
   public String getName() {
      return this.name;
   }
   
   /**
   *@return A Address String of the Owner
   */
   public String getAddress() {
      return this.address;
   }
   
   /**
   *@param Sets the Address of the Property Owner
   */
   public void setAddress( String address ) throws IllegalArgumentException{
      if (address == null) {
         throw new IllegalArgumentException("The PropertyOwner especially should not have a address that is null.");
      }

      this.address = address;
   }
   
   /**
   * CompareTo Method
   *@param A PropertyOwner
   *@return A integer to be used by Tree Sets
   */
   public int compareTo( PropertyOwner p) {
      return this.taxID.compareTo(p.taxID);
   }
   
   /**
   * Hash Code Method
   *@return A integer to be used by various data structures in the code
   */
   public int hashCode() {
      return this.taxID.hashCode();
   }
   
   /**
   * Equals Method
   *@param Takes a object
   *@return True if not null and a instance of Property Owner
   */                                                      
	   public boolean equals( Object o) {
      if ( o == null && !( o instanceof PropertyOwner) ) return false;
      PropertyOwner p = (PropertyOwner) o;
      return this.taxID.equals( p.taxID);
   }
   
   /**
   * ToString for Property Owner Class
   *@return A string will all related information
   */
   public String toString() {
      return "PropertyOwner: " + this.taxID + ", " + this.name + "\n";
   }

}
