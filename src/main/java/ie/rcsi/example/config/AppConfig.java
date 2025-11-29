package ie.rcsi.example.config;

import com.fasterxml.jackson.core.util.DefaultIndenter;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        // Enable pretty print globally
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

        // Configure custom indentation (4 spaces, one field per line)
        DefaultPrettyPrinter prettyPrinter = new DefaultPrettyPrinter();
        DefaultIndenter indenter = new DefaultIndenter("    ", "\n"); // 4 spaces + newline
        prettyPrinter.indentObjectsWith(indenter);
        prettyPrinter.indentArraysWith(indenter);

        // Set the custom pretty printer as default
        objectMapper.setDefaultPrettyPrinter(prettyPrinter);
        return objectMapper;
    }
}
