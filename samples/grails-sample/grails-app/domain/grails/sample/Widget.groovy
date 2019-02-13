package grails.sample

import grails.rest.Resource

@Resource(uri = "/api/widgets")
class Widget {

    String name
    Boolean isExpired = false
    Date dateCreated
    Date lastUpdated
    String description

    static constraints = {
        description nullable: true
    }
}
