package bioteck.apartment.api;
import bioteck.apartment.model.Apartment;
import bioteck.apartment.db.DB;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

@Path("/apartments")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ApartmentAPI{

@GET
public List<Apartment> AList() {
return DB.query(s -> s.createQuery("from Apartment").setMaxResults(50).getResultList());
}

@GET
@Path("/query")
public List<Apartment> query(@QueryParam("searchany") String str ) {
      if (str == null) return new ArrayList<Apartment>();
      String tstr = str.trim();
      return DB.query( s -> s.createQuery
               ("select a from Apartment a " +
                 "where a.complex.name like :str or a.complex.address like :str or a.desc like :str or concat(a.number) like :str",
                Apartment.class)
               .setParameter("str", "%" + tstr + "%")
              .setMaxResults(50)
              .getResultList()
         );
   }                                

   @GET
   @Path("/{id}")
   public Apartment get(@PathParam("id") String id) {
      String tid = id.trim();

      if (Utility.isNum(tid)) {
          return (Apartment) DB.query
             (s -> Arrays.asList(s.get(Apartment.class, Long.valueOf(tid))) ).get(0);
      } else {
         return null;
      }
   }

   @POST
   public void save(Apartment a) {
      DB.transact( s -> s.save(a) );
   }

   @PUT
   public void update(Apartment a) {
      if (a != null && a.getId() != null) {
         DB.transact( s -> s.update(a) );
      }
   }

   @DELETE
   @Path("/{id}")
   public void remove(@PathParam("id") Long id) {
      DB.transact( s ->  s.delete(id) );
   }

}