package bioteck.apartment.db;

import java.util.Map.Entry;
import org.hibernate.NaturalIdLoadAccess;

public interface DBNaturalIdLoadAccess extends NaturalIdLoadAccess{

DBNaturalIdLoadAccess getWrapped();

public default DBNaturalIdLoadAccess dbUsing(Entry<String,Object> nid) {
	return (DBNaturalIdLoadAccess) getWrapped().using(nid.getKey(), nid.getValue());
}
}