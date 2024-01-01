package flab.project.config;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityScheme.In;
import io.swagger.v3.oas.models.security.SecurityScheme.Type;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

        @Bean
        public OpenAPI openAPI() {
                return new OpenAPI()
                        .info(new Info()
                        .title("Prostargram API 문서")
                        .description("개발자의 SNS를 컨셉으로한 프로젝트입니다.(은비님 성진님 여기다가 뭐 적을지 멘트 추천점여)")
                        .version("1.0.0"))
                .components(new Components()
//                        .addSecuritySchemes("bearer-key",
//                                new io.swagger.v3.oas.models.security.SecurityScheme()
//                                        .name("Authorization")
//                                        .type(Type.APIKEY)
//                                        .in(In.HEADER)
//                                        .scheme("bearer")
//                                        .bearerFormat("JWT")));
                        .addSecuritySchemes("bearer-key", new SecurityScheme()
                                .name("bearer-key")
                                .type(SecurityScheme.Type.HTTP) // HTTP 방식
                                .scheme("bearer")
                                .bearerFormat("JWT")));
        }

}
