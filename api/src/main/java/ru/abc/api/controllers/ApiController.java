package ru.abc.api.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import ru.abc.api.clients.PriceServiceClient;
import ru.abc.api.domain.Product;
import ru.abc.api.repositories.ProductRepository;
import ru.abc.common.controllers.BaseController;
import ru.abc.common.dto.Response;
import ru.abc.common.dto.api.ProductPriceRequest;
import ru.abc.common.dto.price.AvgPriceRequest;
import ru.abc.common.dto.price.AvgPriceResponse;
import ru.abc.common.dto.price.WritePriceRequest;
import ru.abc.common.exceptions.ApplicationException;

import javax.validation.Valid;
import java.math.BigDecimal;

@RestController
public class ApiController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(ApiController.class);

    @Value("${api.price.delta:0.7}")
    private BigDecimal priceDelta;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private PriceServiceClient priceServiceClient;

    @RequestMapping(value = "/api", produces = "application/json")
    public Response api(@Valid @RequestBody ProductPriceRequest productPrice) throws ApplicationException {
        logger.info("{}.{}({})", ApiController.class, "avg", productPrice);

        Product product = getProduct(productPrice);
        AvgPriceResponse avgPriceResponse = getAvgPrice(productPrice, product);
        validatePrice(productPrice, avgPriceResponse);
        writePrice(productPrice, product);

        return new Response();
    }

    private void writePrice(ProductPriceRequest productPrice, Product product) throws ApplicationException {
        WritePriceRequest writePriceRequest = new WritePriceRequest(product.getId(), productPrice.getPrice());
        Response writePriceResponse = priceServiceClient.write(writePriceRequest);
        logger.debug(messages.getMessage("api.writePrice.message", productPrice.getName(), writePriceResponse));
        if (!writePriceResponse.isOk()) {
            throw new ApplicationException(writePriceResponse.getErrorCode(), "api.extSystem.price.write.error", writePriceResponse);
        }
    }

    private void validatePrice(ProductPriceRequest productPrice, AvgPriceResponse avgPriceResponse) throws ApplicationException {
        if (avgPriceResponse.isOk()) {
            // Проверяем цену, когда нашли историю цен товара
            BigDecimal avgPrice = avgPriceResponse.getAvgPrice();
            BigDecimal minPrice = avgPrice.multiply(BigDecimal.ONE.subtract(priceDelta));
            BigDecimal maxPrice = avgPrice.multiply(BigDecimal.ONE.add(priceDelta));
            if (minPrice.compareTo(productPrice.getPrice()) > 0) {
                throw new ApplicationException(Response.VALIDATION_ERROR_CODE, "api.price.error.less.then.min", productPrice.getName(), productPrice.getPrice(), minPrice);
            }
            else if (maxPrice.compareTo(productPrice.getPrice()) < 0) {
                throw new ApplicationException(Response.VALIDATION_ERROR_CODE, "api.price.error.more.then.max", productPrice.getName(), productPrice.getPrice(), minPrice);
            }
        }
    }

    private AvgPriceResponse getAvgPrice(ProductPriceRequest productPrice, Product product) throws ApplicationException {
        AvgPriceResponse avgPriceResponse = priceServiceClient.avg(new AvgPriceRequest(product.getId()));
        logger.debug(messages.getMessage("api.extSystem.price.avg.response", productPrice.getName(), avgPriceResponse));
        if (!avgPriceResponse.isOk() && !avgPriceResponse.isNotFound()) {
            throw new ApplicationException(avgPriceResponse.getErrorCode(), "api.extSystem.price.avg.error", productPrice.getName(), avgPriceResponse);
        }
        return avgPriceResponse;
    }

    private Product getProduct(ProductPriceRequest productPrice) throws ApplicationException {
        Product product = productRepository.findByNameIgnoreCase(productPrice.getName());
        if (product == null) {
            throw new ApplicationException(Response.VALIDATION_ERROR_CODE, "api.error.product.notFound", productPrice.getName());
        }
        logger.debug(messages.getMessage("api.product.found", productPrice.getName(), product));
        return product;
    }
}
