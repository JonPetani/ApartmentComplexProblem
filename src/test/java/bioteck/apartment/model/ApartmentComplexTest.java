	package bioteck.apartment.model;

	import org.junit.jupiter.api.Test;
    import static org.junit.jupiter.api.Assertions.assertEquals;
    import static org.junit.jupiter.api.Assertions.assertNotNull;

	public class ApartmentComplexTest {
		@Test public void testCreate() {
	PropertyOwner po = new PropertyOwner("i52316121", "Tooth Pickers Dentistry Inc");
	PropertyPortfolio pp = new PropertyPortfolio(po, "Main Portfolio");
	ApartmentComplex complex = ApartmentComplex.create("A Complex complex", 4500, pp, "123 Number Street", "A Big Complex");
	assertEquals(complex.getName(), "A Complex complex");
	assertEquals(complex.getCost(), 4500);
	assertEquals(complex.getPortfolio(), pp);
	assertEquals(complex.getAddress(), "123 Number Street");
	assertEquals(complex.getComplexDescription(), "A Big Complex");
	}
	}
