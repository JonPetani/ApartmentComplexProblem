package bioteck.apartment.api;
import bioteck.apartment.model.Candidate;
import bioteck.apartment.db.DB;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

@Path("/candidates")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CandidateAPI{

   @GET
   public List<Candidate> CList() {
      return DB.query(s -> s.createQuery("from Candidate").setMaxResults(50).getResultList());
   }

   @GET
   @Path("/query")
   public List<Candidate> query(@QueryParam("searchany") String str ) {
      if (str == null) return new ArrayList<Candidate>();
      String tstr = str.trim();
      return DB.query( s -> s.createQuery
                       ("select c from Candidate c " +
                        "where c.socialSecurity like :str or c.lname like :str or c.fname like :str " +
                        "or c.address like :str or c.phoneNum like :str",
                        Candidate.class)
                       .setParameter("str", "%" + tstr + "%")
                       .setMaxResults(50)
                       .getResultList()
                       );
   }                                

   @GET
   @Path("/{id}")
   public Candidate get(@PathParam("id") String id) {
      String tid = id.trim();

      if (Utility.isNum(tid)) {
         return (Candidate) DB.query
            (s -> Arrays.asList(s.get(Candidate.class, Long.valueOf(tid))) ).get(0);
      } else {
         return Candidate.EMPTY;
      }
   }

   @POST
   public void save(Candidate c) {
      DB.transact( s -> s.save(c) );
   }

   @PUT
   public void update(Candidate c) {
      if (c != null && c.getId() != null) {
         DB.transact( s -> s.update(c) );
      }
   }

   @DELETE
   @Path("/{id}")
   public void remove(@PathParam("id") Long id) {
      DB.transact( s ->  s.delete(id) );
   }
}
