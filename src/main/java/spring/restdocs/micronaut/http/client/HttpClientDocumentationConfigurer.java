package spring.restdocs.micronaut.http.client;

import org.springframework.restdocs.config.RestDocumentationConfigurer;

// TODO figure out the new SRD way to implement a configurer
public class HttpClientDocumentationConfigurer<B> extends
        RestDocumentationConfigurer<HttpClientSnippetConfigurer, HttpClientDocumentationConfigurer<B>, B> {
    @Override
    public HttpClientSnippetConfigurer snippets() {
        return null;
    }

    @Override
    public HttpClientDocumentationConfigurer operationPreprocessors() {
        return null;
    }
}
