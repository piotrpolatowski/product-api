package com.api;

import com.PaginatedProductCatalog;
import com.api.model.Product;
import com.codahale.metrics.MetricRegistry;
import com.metrics.MetricTypes;
import com.google.common.util.concurrent.AbstractService;
import com.google.inject.Inject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

@Slf4j
@Path("products")
@Singleton
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class ProductCatalogService extends AbstractService {

    private final MetricRegistry metricRegistry;

    private final PaginatedProductCatalog catalog;

    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)
    public Response list(@QueryParam("page") int page) {
        metricRegistry.meter(MetricTypes.API_PRODUCT_LIST_REQUESTS.getMetricName()).mark();

        try {
            return Response.ok(catalog.list(page)).build();
        } catch (Exception e) {
            metricRegistry.meter(MetricTypes.API_PRODUCT_ERRORS.getMetricName()).mark();
            return errorResponse(e);
        }
    }

    @POST
    @Path("add")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response add(Product product) {
        metricRegistry.meter(MetricTypes.API_PRODUCT_ADD_REQUESTS.getMetricName()).mark();

        try {
            catalog.add(product);
            return Response.ok().build();
        } catch (Exception e) {
            metricRegistry.meter(MetricTypes.API_PRODUCT_ERRORS.getMetricName()).mark();
            return errorResponse(e);
        }
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