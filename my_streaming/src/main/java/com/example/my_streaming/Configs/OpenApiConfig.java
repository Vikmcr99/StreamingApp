package com.example.my_streaming.Configs;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(

        info = @Info(

                contact = @Contact (name = "Victor Robinson", email = "victor.robinson@al.infnet.edu.br"),
                description = "Streaming app for my final term project at the INFNET college",
                title = "My_Streaming - Assessment"),

        servers = {@Server (description = "Local ENV", url = "http://localhost:8080")}

)


public class OpenApiConfig {

}