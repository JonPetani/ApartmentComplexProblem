package bioteck.apartment.model;


import javax.persistence.ManyToOne;
import javax.persistence.ForeignKey;
import javax.persistence.Table;
import javax.persistence.JoinColumn;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;

/**
* A Key from a Renter to a Apartment to be used for the  Renter2Apartment Tree Set in the Occupancy Book class
*
* @author Jonathan Petani
* @version 2
*/

@Entity
@Table(name="Candidate2Apartment")
public class Candidate2Apartment {
    
    @Id
    @GeneratedValue
    Long Id = Long.valueOf(0);

    @ManyToOne
    @JoinColumn(name="ApartmentId", nullable=false, foreignKey=@ForeignKey(name="ApartmentId_FK"))
    private Apartment apartment = Apartment.EMPTY;

    @ManyToOne
    @JoinColumn(name="CandidateId", nullable=false, foreignKey=@ForeignKey(name="CandidateId_FK"))
    private Candidate renter = Candidate.EMPTY;
    
    /**
    *Constructor
    *@param A Candidate
    *@param A Apartment
    *@throws IllegalArgumentException if the candidate is null
    *@throws IllegalArgumentException if the apartment owner is null
    */

    private Candidate2Apartment() {}

    public Candidate2Apartment(Candidate renter, Apartment apt) throws IllegalArgumentException {
        if (apt == null)
            throw new IllegalArgumentException("there cannot be a key here if there is no apartment to reference from");
        if (renter == null)
            throw new IllegalArgumentException("there cannot be a key if there is no renter of an apartment to reference from");
        this.apartment = apt;
        this.renter = renter;
    }
    
    /**
    *@return Id of table
    */
    public Long getId() {
    return this.Id;
    }
    
    
    /**
    *@return The Renter
    */
    public Candidate getRenter() {
        return this.renter;
    }
    
    /**
    *@return The Apartment
    */
    public Apartment getApartment() {
        return this.apartment;
    }
    
    public String toString() {
    return ("Apartment " + apartment.getNumber() + " Candidate " + renter.getFName());
    }
}
