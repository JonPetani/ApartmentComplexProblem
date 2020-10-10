package bioteck.apartment.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import java.util.HashSet;
import java.util.Set;
import java.util.List;
import java.util.stream.Stream;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.ArrayList;

public class PropertyPortfolioTest {
	@Test public void testCreate() {
	PropertyOwner po = new PropertyOwner("98765434", "Turkey Toppers Inc");
	PropertyPortfolio pp = new PropertyPortfolio(po, "Turkey Portfolio");
	assertEquals(pp.getOwner(), po);
	assertEquals(pp.getName(), "Turkey Portfolio");
	}

	@Test public void testEquals() {
    PropertyOwner po = new PropertyOwner("98765434", "Turkey Toppers Inc");
    PropertyPortfolio pp1 = new PropertyPortfolio(po, "Basic Portfolio");
    PropertyPortfolio pp2 = new PropertyPortfolio(po, "Medium Portfolio");
    PropertyPortfolio pp3 = new PropertyPortfolio(po, "Basic Portfolio");

    assertTrue(pp1.equals(pp3));
    assertFalse(pp1.equals(pp2));
    assertEquals( pp1.hashCode(), pp3.hashCode() );
	}

	@Test public void testComparison() {
	PropertyOwner po = new PropertyOwner("98765434", "Turkey Toppers Inc");
    PropertyPortfolio pp1 = new PropertyPortfolio(po, "Basic Portfolio");
    PropertyPortfolio pp2 = new PropertyPortfolio(po, "Medium Portfolio");
    PropertyPortfolio pp3 = new PropertyPortfolio(po, "Basic Portfolio");
    
    assertTrue( pp1.compareTo(pp3) == 0);
    assertTrue( pp1.compareTo(pp2) < 0);
	}
    
    @Test public void testAddProperty() {
    PropertyOwner po = new PropertyOwner("98765434", "Turkey Toppers Inc");
	PropertyPortfolio pp = new PropertyPortfolio(po, "Turkey Portfolio");
	ApartmentComplex ac1 = ApartmentComplex.create("Crater in the Wall Apartments", 500, pp, "43 Test Drive", "Big Complex");
    ApartmentComplex ac2 = ApartmentComplex.create("LongMeadows", 750, pp, "12 State Street", "Little Complex");
    List<ApartmentComplex> p = new ArrayList<>();
    p.add(ac1);
    p.add(ac2);

    List np = pp.getAllProperties();
    assertEquals(np.size(), p.size());
    assertTrue(p.containsAll(np));
    }

	@Test public void testCost() {
	PropertyOwner po = new PropertyOwner("98765434", "Turkey Toppers Inc");
	PropertyPortfolio pp = new PropertyPortfolio(po, "Turkey Portfolio");
	ApartmentComplex ac1 = ApartmentComplex.create("Crater in the Wall Apartments", 2000, pp, "University Ave", "Something");
    ApartmentComplex ac2 = ApartmentComplex.create("LongMeadows", 1000, pp, "Last Lane", "Medium");

    assertEquals(3000.0, pp.Cost());
	}
}
