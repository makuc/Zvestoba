package si.fri.prpo.zvestoba.api.v1;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

import javax.ws.rs.ApplicationPath;

@OpenAPIDefinition(
        info = @Info(
                title = "Zvestoba API",
                version = "v0.6",
                description = "REST API za upravljanje zvestobe uporabnikov do ponujenih storitev.",
                contact = @Contact(
                        name = "Armin Makovec",
                        email= "armin.makovec@hotmail.com"
                )
        ),
        servers = @Server(url="")
)
@ApplicationPath("v1")
public class APIv1 extends javax.ws.rs.core.Application {
}
