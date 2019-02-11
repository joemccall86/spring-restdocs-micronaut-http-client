package spring.restdocs.micronaut.http.client;

import org.springframework.restdocs.generate.RestDocumentationGenerator;
import org.springframework.restdocs.operation.preprocess.OperationRequestPreprocessor;
import org.springframework.restdocs.operation.preprocess.OperationResponsePreprocessor;
import org.springframework.restdocs.snippet.Snippet;

public abstract class HttpClientDocumentation {

    private static final HttpRequestConverter REQUEST_CONVERTER = new HttpRequestConverter<>();
    private static final HttpResponseConverter RESPONSE_CONVERTER = new HttpResponseConverter<>();


    private HttpClientDocumentation() {
    }

    public static HttpClientDocumentationFilter document(String identifier, Snippet... snippets) {
        return new HttpClientDocumentationFilter(
                new RestDocumentationGenerator(
                        identifier,
                        REQUEST_CONVERTER, RESPONSE_CONVERTER,
                        snippets)
        );
    }

    public static HttpClientDocumentationFilter document(String identifier,
                                                        OperationRequestPreprocessor requestPreprocessor,
                                                        Snippet... snippets) {
        return new HttpClientDocumentationFilter(
                new RestDocumentationGenerator(
                        identifier,
                        REQUEST_CONVERTER, RESPONSE_CONVERTER,
                        requestPreprocessor,
                        snippets)
        );
    }

    public static HttpClientDocumentationFilter document(String identifier,
                                                        OperationResponsePreprocessor responsePreprocessor, Snippet... snippets) {
        return new HttpClientDocumentationFilter(new RestDocumentationGenerator<>(identifier,
                REQUEST_CONVERTER, RESPONSE_CONVERTER, responsePreprocessor, snippets));
    }

    public static HttpClientDocumentationFilter document(String identifier,
                                                        OperationRequestPreprocessor requestPreprocessor,
                                                        OperationResponsePreprocessor responsePreprocessor, Snippet... snippets) {
        return new HttpClientDocumentationFilter(new RestDocumentationGenerator(identifier,
                REQUEST_CONVERTER, RESPONSE_CONVERTER, requestPreprocessor,
                responsePreprocessor, snippets));
    }
}
