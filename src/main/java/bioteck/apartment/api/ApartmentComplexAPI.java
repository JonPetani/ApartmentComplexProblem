package bioteck.apartment.api;
import bioteck.apartment.model.ApartmentComplex;
import bioteck.apartment.db.DB;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

@Path("/apartment-complexes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ApartmentComplexAPI{

@GET
public List<ApartmentComplex> ACList() {
return DB.query(s -> s.createQuery("from ApartmentComplex").setMaxResults(50).getResultList());
}

@GET
@Path("/query")
public List<ApartmentComplex> query(@QueryParam("searchany") String str ) {
      if (str == null) return new ArrayList<ApartmentComplex>();
      String tstr = str.trim();
      return DB.query( s -> s.createQuery
               ("select ac from ApartmentComplex ac " +
                 "where ac.name like :str or ac.address like :str or ac.desc like :str",
                ApartmentComplex.class)
               .setParameter("str", "%" + tstr + "%")
              .setMaxResults(50)
              .getResultList()
         );
   }                                

   @GET
   @Path("/{id}")
   public ApartmentComplex get(@PathParam("id") String id) {
      String tid = id.trim();

      if (Utility.isNum(tid)) {
          return (ApartmentComplex) DB.query
             (s -> Arrays.asList(s.get(ApartmentComplex.class, Long.valueOf(tid))) ).get(0);
      } else {
         return null;
      }
   }

   @POST
   public void save(ApartmentComplex ac) {
      DB.transact( s -> s.save(ac) );
   }

   @PUT
   public void update(ApartmentComplex ac) {
      if (ac != null && ac.getId() != null) {
         DB.transact( s -> s.update(ac) );
      }
   }

   @DELETE
   @Path("/{id}")
   public void remove(@PathParam("id") Long id) {
      DB.transact( s ->  s.delete(id) );
   }

}