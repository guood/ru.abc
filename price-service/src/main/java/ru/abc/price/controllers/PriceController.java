package ru.abc.price.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.abc.common.controllers.BaseController;
import ru.abc.common.dto.Response;
import ru.abc.common.dto.price.AvgPriceRequest;
import ru.abc.common.dto.price.AvgPriceResponse;
import ru.abc.common.dto.price.WritePriceRequest;
import ru.abc.price.domain.Price;
import ru.abc.price.repositories.PriceRepository;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/price")
public class PriceController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(PriceController.class);

    @Autowired
    private PriceRepository priceRepository;

    @RequestMapping(value = "/avg", produces = "application/json")
    public AvgPriceResponse avg(@RequestBody AvgPriceRequest avgPriceRequest) {
        logStartMethod("avg", avgPriceRequest);
        AvgPriceResponse result;
        List<Price> priceList = priceRepository.findByProductId(avgPriceRequest.getProductId());
        logger.debug(messages.getMessage("price.find.by.productId.message", avgPriceRequest.getProductId(), priceList));
        if (priceList == null || priceList.isEmpty()) {
            return new AvgPriceResponse(Response.NOT_FOUND_CODE, "Price not found");
        }
        BigDecimal sum = BigDecimal.ZERO;
        int i = 0;
        for (Price p : priceList) {
            sum = sum.add(p.getPrice());
            i++;
        }
        result = new AvgPriceResponse(sum.divide(BigDecimal.valueOf(i)));
        logEndMethod("avg", result);
        return result;
    }

    @RequestMapping(value = "/write", produces = "application/json")
    public Response write(@RequestBody WritePriceRequest writePriceRequest) {
        logStartMethod("write", writePriceRequest);

        Price price = new Price(writePriceRequest.getProductId(), writePriceRequest.getPrice());
        priceRepository.insert(price);
        Response result = new Response();
        logEndMethod("write", result);
        return result;
    }
}
