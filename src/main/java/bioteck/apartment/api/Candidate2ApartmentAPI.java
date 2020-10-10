package bioteck.apartment.api;
import bioteck.apartment.model.Candidate2Apartment;
import bioteck.apartment.db.DB;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

@Path("/candidate2apartments")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class Candidate2ApartmentAPI{

@GET
public List<Candidate2Apartment> C2AList() {
return DB.query(s -> s.createQuery("from Candidate2Apartment").setMaxResults(50).getResultList());
}

@GET
@Path("/query")
public List<Candidate2Apartment> query(@QueryParam("searchany") String str ) {
      if (str == null) return new ArrayList<Candidate2Apartment>();
      String tstr = str.trim();
      return DB.query( s -> s.createQuery
               ("select c2a from Apartment c2a " +
                 "where c2a.apartment.complex.name like :str or c2a.apartment.concat(number) like :str or c2a.renter.socialSecurity like :str or c2a.renter.fname like :str or c2a.renter.lname like :str",
                Candidate2Apartment.class)
               .setParameter("str", "%" + tstr + "%")
              .setMaxResults(50)
              .getResultList()
         );
   }                                

   @GET
   @Path("/{id}")
   public Candidate2Apartment get(@PathParam("id") String id) {
      String tid = id.trim();

      if (Utility.isNum(tid)) {
          return (Candidate2Apartment) DB.query
             (s -> Arrays.asList(s.get(Candidate2Apartment.class, Long.valueOf(tid))) ).get(0);
      } else {
         return null;
      }
   }

   @POST
   public void save(Candidate2Apartment c2a) {
      DB.transact( s -> s.save(c2a) );
   }

   @PUT
   public void update(Candidate2Apartment c2a) {
      if (c2a != null && c2a.getId() != null) {
         DB.transact( s -> s.update(c2a) );
      }
   }

   @DELETE
   @Path("/{id}")
   public void remove(@PathParam("id") Long id) {
      DB.transact( s ->  s.delete(id) );
   }

}