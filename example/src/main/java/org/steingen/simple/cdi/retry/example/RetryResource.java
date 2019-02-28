package org.steingen.simple.cdi.retry.example;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author steingenc
 */
@Path("retry")
public class RetryResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(RetryResource.class);

    @Inject
    RetryMe retryMe;

    @GET
    public Response test() {
        try {
            retryMe.doSomething();
        } catch (SuperAnnoyingException | SuperEvilException e) {
            LOGGER.error("Error from invocation: " + e.getMessage());
            return Response.serverError().build();
        }
        return Response.ok().build();
    }


}
