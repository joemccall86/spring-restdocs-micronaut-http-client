package spring.restdocs.micronaut.http.client;

import io.micronaut.core.async.publisher.Publishers;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MutableHttpRequest;
import io.micronaut.http.filter.ClientFilterChain;
import io.micronaut.http.filter.HttpClientFilter;
import org.reactivestreams.Publisher;
import org.springframework.restdocs.RestDocumentationContext;
import org.springframework.restdocs.generate.RestDocumentationGenerator;

import java.util.Collections;
import java.util.Map;

public class HttpClientDocumentationFilter implements HttpClientFilter {

    private final RestDocumentationGenerator<HttpRequest, HttpResponse> delegate;

    public HttpClientDocumentationFilter(RestDocumentationGenerator<HttpRequest, HttpResponse> delegate) {
        this.delegate = delegate;
    }

    @Override
    public Publisher<? extends HttpResponse<?>> doFilter(MutableHttpRequest<?> request, ClientFilterChain chain) {

        // WARNING: previous implementation had to make a copy of the response for documenting purposes because the stream was closed.
        // I may have to do this here as well.


        // grab the configuration map from wherever it is stored by the HttpClientDocumentationConfigurer.
        Map<String, Object> configuration = (Map<String, Object>) request.getAttribute("srdConfig").get();
        RestDocumentationContext context = (RestDocumentationContext) request.getAttribute("srdContext").get();

        configuration.put(RestDocumentationContext.class.getName(), context);

        return Publishers.then(
                chain.proceed(request),
                (response) -> delegate.handle(request, response, configuration)
        );
    }
}
