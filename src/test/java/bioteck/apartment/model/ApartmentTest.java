package bioteck.apartment.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


public class ApartmentTest {
    @Test public void testCreate() {
        ApartmentComplex complex = new ApartmentComplex("Complex #1", "75 Where are We Way");
        Apartment A = Apartment.create(complex, 1, 500, "2 Beds, 1 Bathroom");
        assertNotNull(A.getNumber(), "Apartment should be created");
        assertEquals(A.getRent(), 500);
        assertEquals(A.getComplex(), complex, "apartment has correct complex");
        assertEquals(A.getNumber(), 1, "Apartment has the expected number.");
        assertEquals(A.getApartmentDescription(), "2 Beds, 1 Bathroom");
    }
}
