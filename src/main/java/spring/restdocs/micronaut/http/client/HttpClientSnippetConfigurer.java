package spring.restdocs.micronaut.http.client;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.MutableHttpRequest;
import io.micronaut.http.filter.ClientFilterChain;
import io.micronaut.http.filter.HttpClientFilter;
import org.reactivestreams.Publisher;
import org.springframework.restdocs.config.SnippetConfigurer;

public class HttpClientSnippetConfigurer extends
        SnippetConfigurer<HttpClientDocumentationConfigurer, HttpClientSnippetConfigurer>
    implements HttpClientFilter {

    /**
     * Creates a new {@code SnippetConfigurer} with the given {@code parent}.
     *
     * @param httpClientDocumentationConfigurer the parent
     */
    protected HttpClientSnippetConfigurer(HttpClientDocumentationConfigurer httpClientDocumentationConfigurer) {
        super(httpClientDocumentationConfigurer);
    }

    @Override
    public Publisher<? extends HttpResponse<?>> doFilter(MutableHttpRequest<?> request, ClientFilterChain chain) {
        return chain.proceed(request);
    }
}
