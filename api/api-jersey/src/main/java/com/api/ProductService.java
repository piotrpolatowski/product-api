package com.api;

import com.codahale.metrics.MetricRegistry;
import com.metrics.MetricTypes;
import com.api.model.Product;
import com.storage.Storage;
import com.google.common.util.concurrent.AbstractService;
import com.google.inject.Inject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.util.List;

@Slf4j
@Path("products")
@Singleton
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class ProductService extends AbstractService {

    @Inject
    private final MetricRegistry metricRegistry;

    @Inject
    private final Storage<Product> storage;


    private final int pageSize = 3;

    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)
    public Response list(@QueryParam("page") int page) {
        metricRegistry.meter(MetricTypes.API_PRODUCT_LIST_REQUESTS.getMetricName()).mark();

        try {
            return okResponse(page);
        } catch (Exception e) {
            metricRegistry.meter(MetricTypes.API_PRODUCT_ERRORS.getMetricName()).mark();
            return errorResponse(e);
        }
    }

    private Response okResponse(int page) {
        List<Product> products = storage.get(pageSize * page, pageSize);
        return Response.ok(products).build();
    }

    private Response errorResponse(Exception e) {
        String message = "Product service error.";
        log.error(message, e);
        throw new WebApplicationException(
                Response.status(Status.INTERNAL_SERVER_ERROR)
                        .entity(message)
                        .build());
    }


    @Override
    protected void doStart() {
        notifyStarted();
    }

    @Override
    protected void doStop() {
        notifyStopped();
    }
}