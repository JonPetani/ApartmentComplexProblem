package bioteck.apartment.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class CandidateTest {
   @Test public void testCreate() {
      Candidate C = Candidate.create("899-99-9999", "Smith", "John", "5 Main Street, BigTown, Country", "100-200-3000");
      assertEquals(C.getPhoneNumber(), "100-200-3000");
      assertEquals(C.getAddress(), "5 Main Street, BigTown, Country");
      C.setSalary(5000);
      assertEquals(C.getSalary(), 5000);
      assertEquals(C.getFName(), "John");
      assertEquals(C.getLName(), "Smith");
      assertEquals(C.getSocialSecurity(), "899-99-9999");   
   }

   @Test public void testEquals() {
      Candidate c1 = Candidate.create("123-23-1232", "Hammond", "Alec", "5 South Street, Milford, New Hampshire", "938-322-3212");
      Candidate c2 = Candidate.create("243-09-2321", "Hammond", "Alec", "5 South Street, Milford, New Hampshire", "938-322-3212");
      Candidate c3 = Candidate.create("123-23-1232", "Boyleston", "Harold", "341 Long Drive, Santiago, Chile", "293-999-8865");

      assertFalse(c1.equals(c2));
      assertTrue(c1.equals(c3));
      assertEquals(c1.hashCode(), c3.hashCode());
   }

   @Test public void testComparison() {
      Candidate r1 = Candidate.create("111-11-1111", "Langbert", "Francis", "123 Number Way, Athens, Greece", "203-233-2321");
      Candidate r2 = Candidate.create("111-11-1112", "Langbert", "Francis", "123 Number Way, Athens, Greece", "203-233-2321");

      assertTrue(r1.compareTo(r2) < 0);
   }
}
