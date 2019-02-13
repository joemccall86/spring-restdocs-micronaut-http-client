package grails.sample.spec

import com.fasterxml.jackson.databind.ObjectMapper
import grails.testing.mixin.integration.Integration
import groovy.transform.CompileStatic
import io.micronaut.core.io.ResourceResolver
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.micronaut.http.MediaType
import io.micronaut.http.client.DefaultHttpClient
import io.micronaut.http.client.DefaultHttpClientConfiguration
import io.micronaut.http.client.ssl.NettyClientSslBuilder
import io.micronaut.http.codec.MediaTypeCodecRegistry
import io.micronaut.http.filter.HttpClientFilter
import io.micronaut.http.ssl.ClientSslConfiguration
import io.micronaut.jackson.ObjectMapperFactory
import io.micronaut.jackson.codec.JsonMediaTypeCodec
import io.micronaut.jackson.codec.JsonStreamMediaTypeCodec
import io.micronaut.runtime.ApplicationConfiguration
import org.junit.Rule
import org.springframework.restdocs.JUnitRestDocumentation
import org.springframework.restdocs.payload.JsonFieldType
import spock.lang.Specification

import javax.annotation.Nullable

import static io.micronaut.http.HttpRequest.GET
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields
import static spring.restdocs.micronaut.http.client.HttpClientDocumentation.document
import static spring.restdocs.micronaut.http.client.HttpClientDocumentation.documentationConfiguration

@Integration
class WidgetApiSpec extends Specification {

    @Rule
    final JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation()

    AdditionalFiltersHttpClient client

    def setup() {
        client = new AdditionalFiltersHttpClient(
                "http://localhost:${serverPort}".toURL(),
                documentationConfiguration(restDocumentation)
        )
    }

    def 'index method'() {
        given: 'the fields are documented'
        client.additionalFilters << document('widget-index-example',
                responseFields(
                        fieldWithPath('[].name')
                            .type(JsonFieldType.STRING)
                            .description('name of the widget'),

                        fieldWithPath('[].id')
                            .type(JsonFieldType.NUMBER)
                            .description('id of the widget'),

                        fieldWithPath('[].isExpired')
                            .type(JsonFieldType.BOOLEAN)
                            .description('whether or not the widget has expired'),

                        fieldWithPath('[].dateCreated')
                            .type(JsonFieldType.STRING)
                            .description('when the widget was created'),

                        fieldWithPath('[].lastUpdated')
                            .type(JsonFieldType.STRING)
                            .description('when the widget was last updated'),

                        fieldWithPath('[].description')
                            .type(JsonFieldType.STRING)
                            .optional()
                            .description('the optional description of the widget')
                )
        )

        when: 'the request is made'
        def response = client.toBlocking().exchange(
                GET('/api/widgets')
                    .accept(MediaType.APPLICATION_JSON_TYPE),
                String
        )

        then: 'the status is what we expect'
        response.status() == HttpStatus.OK
    }
}


// TODO This is just a proof of concept. There's a better way to do this I'm sure.
@CompileStatic
class AdditionalFiltersHttpClient extends DefaultHttpClient {

    List<HttpClientFilter> additionalFilters = new LinkedList<>()

    AdditionalFiltersHttpClient(URL url, HttpClientFilter configurationFilter) {
        super(
                url,
                new DefaultHttpClientConfiguration(),
                new NettyClientSslBuilder(new ClientSslConfiguration(), new ResourceResolver()),
                createDefaultMediaTypeRegistry(),
                configurationFilter
        )
    }

    // HACK there should be a better way
    private static MediaTypeCodecRegistry createDefaultMediaTypeRegistry() {
        ObjectMapper objectMapper = new ObjectMapperFactory().objectMapper(null, null);
        ApplicationConfiguration applicationConfiguration = new ApplicationConfiguration();
        return MediaTypeCodecRegistry.of(
                new JsonMediaTypeCodec(objectMapper, applicationConfiguration, null), new JsonStreamMediaTypeCodec(objectMapper, applicationConfiguration, null)
        );
    }

    @Override
    protected List<HttpClientFilter> resolveFilters(@Nullable HttpRequest<?> parentRequest, HttpRequest<?> request, URI requestURI) {

        def resolvedFilters = super.resolveFilters(parentRequest, request, requestURI)

        resolvedFilters.addAll(additionalFilters)

        return resolvedFilters
    }
}
