package spring.restdocs.micronaut.http.client;

import org.springframework.restdocs.config.RestDocumentationConfigurer;

public class HttpClientDocumentationConfigurer extends
        RestDocumentationConfigurer<
                HttpClientSnippetConfigurer,
                HttpClientOperationPreprocessorsConfigurer,
                HttpClientDocumentationConfigurer> {

    private final HttpClientSnippetConfigurer httpClientSnippetConfigurer = new HttpClientSnippetConfigurer(this);
    private final HttpClientOperationPreprocessorsConfigurer httpClientOperationPreprocessorsConfigurer = new HttpClientOperationPreprocessorsConfigurer(this);

    @Override
    public HttpClientSnippetConfigurer snippets() {
        return httpClientSnippetConfigurer;
    }

    @Override
    public HttpClientOperationPreprocessorsConfigurer operationPreprocessors() {
        return httpClientOperationPreprocessorsConfigurer;
    }
}
