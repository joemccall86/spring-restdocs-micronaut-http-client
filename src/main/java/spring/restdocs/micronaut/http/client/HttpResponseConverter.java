package spring.restdocs.micronaut.http.client;

import io.micronaut.http.HttpResponse;
import org.springframework.restdocs.operation.OperationResponse;
import org.springframework.restdocs.operation.OperationResponseFactory;
import org.springframework.restdocs.operation.ResponseConverter;

public class HttpResponseConverter implements ResponseConverter<HttpResponse<String>> {

    @Override
    public OperationResponse convert(HttpResponse<String> response) {
        return new OperationResponseFactory().create(
                SpringMicronautConverters.convertStatus(response.getStatus()),
                SpringMicronautConverters.convertHeaders(response.getHeaders()),
                getContent(response)
        );
    }

    private byte[] getContent(HttpResponse<String> response) {
        if (response.getBody().isPresent()) {
            return response.getBody().get().getBytes(response.getCharacterEncoding());

        } else {
            return null;

        }
    }
}
