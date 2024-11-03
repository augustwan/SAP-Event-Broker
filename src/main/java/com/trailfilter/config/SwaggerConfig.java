package com.trailfilter.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info =
            @Info(title = "Trail Filter API",
                  version = "1.0",
                  description = "API to filter trails by trail type and finishing"))
public class SwaggerConfig {
}
