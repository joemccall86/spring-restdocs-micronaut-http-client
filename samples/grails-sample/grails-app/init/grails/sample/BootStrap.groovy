package grails.sample

class BootStrap {

    def init = { servletContext ->

        // Create some widgets for testing
        Widget.withNewTransaction {
            new Widget(
                    name: 'foo'
            ).save()

            new Widget(
                    name: 'bar',
                    description: 'bar description'
            ).save()
        }

    }
    def destroy = {
    }
}
