package online.kehan.connect.config;

import graphql.scalars.ExtendedScalars;
import graphql.schema.GraphQLScalarType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DateScalar {
    @Bean
    public GraphQLScalarType jsonType() {
        return ExtendedScalars.Date;
    }
}
