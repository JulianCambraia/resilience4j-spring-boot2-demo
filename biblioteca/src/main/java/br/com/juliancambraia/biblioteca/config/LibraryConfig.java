package br.com.juliancambraia.biblioteca.config;

import br.com.juliancambraia.biblioteca.service.BibliotecaServiceImpl;
import io.micrometer.core.instrument.Clock;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.newrelic.NewRelicConfig;
import io.micrometer.newrelic.NewRelicMeterRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.client.RootUriTemplateHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriTemplateHandler;

@Configuration
public class LibraryConfig {
    Logger logger = LoggerFactory.getLogger(BibliotecaServiceImpl.class);
    //private static final String baseUrl = "https://biblioteca-service.apps.np.sdpcf.com";
    private static final String baseUrl = "http://localhost:8083";

    @Bean
    RestTemplate restTemplate(RestTemplateBuilder builder) {
        UriTemplateHandler uriTemplateHandler = new RootUriTemplateHandler(baseUrl);
        return builder
                .uriTemplateHandler(uriTemplateHandler)
                .build();
    }

    NewRelicConfig newRelicConfig = new NewRelicConfig() {

        @Override
        public String apiKey() {
            return "wQsPsZ8e9TUHvXE15_wcTJkoo08nBco5";
        }

        @Override
        public String accountId() {
            return "1807775";
        }

        @Override
        public String get(String key) {
            return null;
        }
    };

    MeterRegistry registry = new NewRelicMeterRegistry(newRelicConfig, Clock.SYSTEM);
}
