package bioteck.apartment.db;

import org.hibernate.Session;
import java.util.List;

public interface IQuery<T> {
public List<T> process(Session s);
}