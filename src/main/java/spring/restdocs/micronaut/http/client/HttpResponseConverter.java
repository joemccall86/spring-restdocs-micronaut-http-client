package spring.restdocs.micronaut.http.client;

import io.micronaut.http.HttpResponse;
import org.springframework.restdocs.operation.OperationResponse;
import org.springframework.restdocs.operation.OperationResponseFactory;
import org.springframework.restdocs.operation.ResponseConverter;

public class HttpResponseConverter implements ResponseConverter<HttpResponse<?>> {
    @Override
    public OperationResponse convert(HttpResponse<?> response) {
        return new OperationResponseFactory().create(
                SpringMicronautConverters.convertStatus(response.getStatus()),
                SpringMicronautConverters.convertHeaders(response.getHeaders()),
                null
//                response.body()// TODO
        );
    }
}
