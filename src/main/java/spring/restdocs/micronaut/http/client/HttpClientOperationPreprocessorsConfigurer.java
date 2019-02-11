package spring.restdocs.micronaut.http.client;

import org.springframework.restdocs.config.OperationPreprocessorsConfigurer;

public class HttpClientOperationPreprocessorsConfigurer extends
        OperationPreprocessorsConfigurer<HttpClientDocumentationConfigurer, HttpClientOperationPreprocessorsConfigurer> {

    /**
     * Creates a new {@code OperationPreprocessorConfigurer} with the given
     * {@code parent}.
     *
     * @param httpClientDocumentationConfigurer the parent
     */
    protected HttpClientOperationPreprocessorsConfigurer(HttpClientDocumentationConfigurer httpClientDocumentationConfigurer) {
        super(httpClientDocumentationConfigurer);
    }
}
