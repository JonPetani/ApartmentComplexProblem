package bioteck.apartment.api;

import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

public class RestApplication extends Application {
	private Set<Object> singletons = new HashSet<>();
	private Set<Class<?>> classes = new HashSet<>();
	public RestApplication() {
    classes.add(CandidateAPI.class);
    classes.add(PropertyOwnerAPI.class);
    classes.add(PropertyPortfolioAPI.class);
    classes.add(ApartmentComplexAPI.class);
    classes.add(ApartmentAPI.class);
    classes.add(Candidate2ApartmentAPI.class);
	}

	@Override
	public Set<Class<?>> getClasses() {
	return classes;	
	} 

	@Override
	public Set<Object> getSingletons() {
	return singletons;
	}
}