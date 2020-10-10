package bioteck.apartment.model;
/**
* Represents a Complex property
*Includes a name and a Cost to upkeep as a identifying trait
*@author Jonathan Petani
*@version 2
*/
public interface IProperty extends Comparable<IProperty> {
	/** Alternative to Null	*/
	public static final IProperty EMPTY = new IProperty() {
	public String getName() {return "";}
	public double getCost()   {return 0;}
	public String getAddress() {return "";}
	public String getComplexDescription() {return "";}
	public int compareTo(IProperty i) {return -1;}
	};
    /** Unique Complex Name */
    String getName();
    
    int compareTo(IProperty i);
    /** Unique Complex Cost */
    double getCost();
    /** Address of complex */
    String getAddress();
    /** Description of complex */
    String getComplexDescription();
}
