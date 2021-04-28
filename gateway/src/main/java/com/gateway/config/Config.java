package com.gateway.config;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import java.net.URI;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import lombok.extern.slf4j.Slf4j;


@Configuration
@EnableDiscoveryClient
@EnableWebFlux
@Slf4j
public class Config { 

	@Bean
	public RouterFunction<ServerResponse> imgRouter() {
	    return RouterFunctions
	      .resources("/**", new ClassPathResource("static/"));
	}

	
	@Bean
	RouterFunction<ServerResponse> routerFunction() {
	    return route(GET("/"), req ->
	    				ServerResponse.temporaryRedirect(URI.create("swagger-ui.html"))
	                    .build());
	}
	


	@Bean
	public OpenAPI conf() {	
		return new OpenAPI()
				.info(new Info().title("Open API")
	            .description("Gateway")
	            .version("v1")
	            .license(new License().name("Apache 2.0").url("http://springdoc.org")));
	  }

	
	@Bean
	public GroupedOpenApi collaborators() {		
		 return GroupedOpenApi
	    		 .builder()
	    		 .group("../Collaborators")
	    		 .pathsToMatch("")
	    		 .build();
	 }
	
	
	
	@Bean
	public GroupedOpenApi Characters() {		
		 return GroupedOpenApi
	    		 .builder()
	    		 .group("../Characters")
	    		 .pathsToMatch("")
	    		 .build();
	 }
	 

}