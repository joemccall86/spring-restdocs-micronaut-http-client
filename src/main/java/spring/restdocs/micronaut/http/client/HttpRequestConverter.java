package spring.restdocs.micronaut.http.client;

import io.micronaut.http.HttpRequest;
import org.springframework.restdocs.operation.OperationRequest;
import org.springframework.restdocs.operation.OperationRequestFactory;
import org.springframework.restdocs.operation.RequestConverter;

import java.util.Collections;

public class HttpRequestConverter<B> implements RequestConverter<HttpRequest<B>> {
    @Override
    public OperationRequest convert(HttpRequest<B> request) {
        return new OperationRequestFactory().create(
                request.getUri(),
                SpringMicronautConverters.convertMethod(request.getMethod()),
                extractContent(request),
                SpringMicronautConverters.convertHeaders(request.getHeaders()),
                SpringMicronautConverters.convertParameters(request.getParameters()),
                Collections.emptyList() // TODO - parts
        );
    }

    private byte[] extractContent(HttpRequest request) {

        final byte[] content;

        if (request.getBody().isPresent()) {
            Object body = request.getBody().get();

            if (body instanceof String) {
                content = ((String) body).getBytes(request.getCharacterEncoding());
            } else if (body instanceof byte[]) {
                content = (byte[]) body;
            } else {
                // TODO: add the following types
                // - inputStream
                // - file
                // IllegalStateException otherwise
                content = new byte[0];
            }
        } else {
            content = new byte[0];
        }

        return content;
    }
}
