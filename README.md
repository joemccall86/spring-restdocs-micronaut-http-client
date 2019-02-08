# Spring REST Docs for Micronaut HTTP Client
A spring-restdocs implementation that can be used with Micronaut HTTP Client

## Note
This is a work-in-progress. PRs are welcome.

## (potential) Example Usage

To generate snippets, use the following examples as a guide:

## Spock/Groovy

```groovy
@Rule
JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation()
    
def httpClient = DocumentingHttpClient.create(new URL("http://localhost:$serverPort"))

def 'document widget api - index'() {
  given:
  httpClient.documentingFilters << document('widget-index-example', 
    preProcessRequest(
      removeHeaders('Accept-Charset')
    ),
    preProcessResponse(
      prettyPrint(),
      removeHeaders('X-Application-Context')
    ),
    requestParams(
      parameterWithName('broken')
        .optional()
        .description('whether or not to include broken widgets in the response')
    ),
    responseFields(
      fieldWithPath('[].name')
        .type(JsonFieldType.STRING)
        .description('The name of the widget'),
        
      fieldWithPath('[].shelfLifeYears')
        .type(JsonFieldType.NUMBER)
        .description('The shelf life of the widget in years'),
        
      fieldWithPath('[].isBroken')
        .type(JsonFieldType.BOOLEAN)
        .optional()
        .description('whether or not the widget is broken')
    )
  )
  
  when:
  def response = httpClient.toBlocking().exchange(
    HttpRequest.GET("/api/widgets?broken=true", List<Widget>))
    
  then:
  response.status == HttpStatus.OK
}
```

The above code will generate snippets in build/generated-snippets/widget-index-example. See Spring REST Docs for further details on how to use this.

## JUnit
TODO

## Installation
TODO

## License
Spring REST Docs for Micronaut HTTP Client is open source software released under the [Apache 2.0 license](http://www.apache.org/licenses/LICENSE-2.0.html).
