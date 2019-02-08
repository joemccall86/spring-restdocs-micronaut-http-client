package spring.restdocs.micronaut.http.client;

import io.micronaut.http.HttpParameters;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.restdocs.operation.Parameters;

class SpringMicronautConverters {

    static HttpMethod convertMethod(io.micronaut.http.HttpMethod method) {
        return HttpMethod.valueOf(method.name());
    }

    static HttpHeaders convertHeaders(io.micronaut.http.HttpHeaders headers) {
        HttpHeaders springHttpHeaders = new HttpHeaders();
        headers.forEachValue(springHttpHeaders::add);
        return springHttpHeaders;
    }

    static Parameters convertParameters(HttpParameters httpParameters) {
        Parameters parameters = new Parameters();
        httpParameters.forEachValue(parameters::add);
        return parameters;
    }

    static HttpStatus convertStatus(io.micronaut.http.HttpStatus status) {
        return HttpStatus.valueOf(status.getCode());
    }
}
