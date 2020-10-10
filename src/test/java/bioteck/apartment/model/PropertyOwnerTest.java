package bioteck.apartment.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class PropertyOwnerTest {
	@Test public void createTest() {
	PropertyOwner po = new PropertyOwner("98765434", "Turkey Toppers Inc");
	assertEquals(po.getTaxID(), "98765434");
	assertEquals(po.getName(), "Turkey Toppers Inc");	
	po.setAddress("Gravy Train Parkway, Plymouth, Massachusetts");
	assertEquals(po.getAddress(), "Gravy Train Parkway, Plymouth, Massachusetts");
	}

	@Test public void testEquals() {
    PropertyOwner po1 = new PropertyOwner("i52316121", "Tooth Pickers Dentistry Inc");
    PropertyOwner po2 = new PropertyOwner("i52316121", "Shiny Cleaners");
    PropertyOwner po3 = new PropertyOwner("i53415121", "Tooth Pickers Dentistry Inc");
    assertTrue(po1.equals(po2));
    assertFalse(po1.equals(po3));
	}

	@Test public void testComparison() {
    PropertyOwner po1 = new PropertyOwner("i52316121", "Tooth Pickers Dentistry Inc");
    PropertyOwner po2 = new PropertyOwner("i52316121", "Shiny Cleaners");
    PropertyOwner po3 = new PropertyOwner("i53415121", "Tooth Pickers Dentistry Inc");

    assertTrue( po1.compareTo(po2) == 0);
    assertTrue( po3.compareTo(po2) != po1.compareTo(po2));
	}
}
