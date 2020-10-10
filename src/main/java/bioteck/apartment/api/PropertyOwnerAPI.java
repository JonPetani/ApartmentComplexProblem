package bioteck.apartment.api;
import bioteck.apartment.model.PropertyOwner;
import bioteck.apartment.db.DB;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

@Path("/property-owners")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PropertyOwnerAPI{

@GET
public List<PropertyOwner> POList() {
return DB.query(s -> s.createQuery("from PropertyOwner").setMaxResults(50).getResultList());
}

@GET
@Path("/query")
public List<PropertyOwner> query(@QueryParam("searchany") String str ) {
      if (str == null) return new ArrayList<PropertyOwner>();
      String tstr = str.trim();
      return DB.query( s -> s.createQuery
               ("select po from PropertyOwner po " +
					  "where po.taxID like :str or po.name like :str or po.address like :str ",
                PropertyOwner.class)
               .setParameter("str", "%" + tstr + "%")
				  .setMaxResults(50)
				  .getResultList()
         );
	}                                

	@GET
	@Path("/{id}")
	public PropertyOwner get(@PathParam("id") String id) {
      String tid = id.trim();

		if (Utility.isNum(tid)) {
          return (PropertyOwner) DB.query
             (s -> Arrays.asList(s.get(PropertyOwner.class, Long.valueOf(tid))) ).get(0);
		} else {
         return PropertyOwner.EMPTY;
		}
	}

	@POST
   public void save(PropertyOwner po) {
      DB.transact( s -> s.save(po) );
	}

	@PUT
	public void update(PropertyOwner po) {
		if (po != null && po.getId() != null) {
         DB.transact( s -> s.update(po) );
      }
	}

	@DELETE
	@Path("/{id}")
	public void remove(@PathParam("id") Long id) {
		DB.transact( s ->  s.delete(id) );
	}

}