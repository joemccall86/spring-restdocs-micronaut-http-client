package spring.restdocs.micronaut.http.client;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.MutableHttpRequest;
import io.micronaut.http.filter.ClientFilterChain;
import io.micronaut.http.filter.HttpClientFilter;
import org.reactivestreams.Publisher;
import org.springframework.restdocs.RestDocumentationContext;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.config.RestDocumentationConfigurer;

import java.util.HashMap;
import java.util.Map;

public class HttpClientDocumentationConfigurer extends
        RestDocumentationConfigurer<
                HttpClientSnippetConfigurer,
                HttpClientOperationPreprocessorsConfigurer,
                HttpClientDocumentationConfigurer>
        implements HttpClientFilter {

    private final HttpClientSnippetConfigurer httpClientSnippetConfigurer = new HttpClientSnippetConfigurer(this);
    private final HttpClientOperationPreprocessorsConfigurer httpClientOperationPreprocessorsConfigurer = new HttpClientOperationPreprocessorsConfigurer(this);

    private RestDocumentationContextProvider contextProvider;

    public HttpClientDocumentationConfigurer(RestDocumentationContextProvider contextProvider) {
        this.contextProvider = contextProvider;
    }

    @Override
    public HttpClientSnippetConfigurer snippets() {
        return httpClientSnippetConfigurer;
    }

    @Override
    public HttpClientOperationPreprocessorsConfigurer operationPreprocessors() {
        return httpClientOperationPreprocessorsConfigurer;
    }

    @Override
    public Publisher<? extends HttpResponse<?>> doFilter(MutableHttpRequest<?> request, ClientFilterChain chain) {
        RestDocumentationContext context = this.contextProvider.beforeOperation();
        Map<String, Object> configuration = new HashMap<>();
        apply(configuration, context);
        // TODO discover a way to pass the context and configuration to the execution chain so the HttpClientDocumentationFilter can make use of it
        // A naive approach would be to place it in a ContextHolder singleton, but that's not thread-safe.

        // Since it's a mutable request, let's see if we can just add an attribute for now
        request.setAttribute("srdContext", context);
        request.setAttribute("srdConfig", configuration);

        return chain.proceed(request);
    }
}
