package si.fri.prpo.skupina19.sledilnik.api;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.info.License;
import org.eclipse.microprofile.openapi.annotations.servers.Server;


@OpenAPIDefinition(info = @Info(title = "Sledilnik stevila obiskovalcev API", version = "v1",
                    contact = @Contact(email = "nb9762@student.uni-lj.si"),
                    license = @License(name= "dev"), description = "API za storitev Sledilnik stevila obiskovalcev."),
                    servers = @Server(url = "http://localhost:8080/"))
@ApplicationPath("/v1")
public class SledilnikApplication extends Application{
}
