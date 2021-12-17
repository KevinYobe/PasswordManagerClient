package passwordmanager.client.rest;

import java.net.URI;
import java.util.List;

import org.springframework.stereotype.Component;

import com.passwordmanager.client.model.Roles;

/**
 * @author KYobe
 *
 */
@Component
public class RolesRestClient extends AbstractRestClient {
	
	public RolesRestClient() {
	
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Roles> getAll(URI url) {
		return (List<Roles>) super.getAll(url);
	}

	@Override
	public Roles get(URI url) {
		return (Roles) super.get(url);
	}

	@Override
	public Roles post(URI url, Object obj) {
		return (Roles) super.post(url, obj);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Roles> postAll(URI url, List<?> items) {
		return (List<Roles>) super.postAll(url, items);
	}
	
	
	
	

}
