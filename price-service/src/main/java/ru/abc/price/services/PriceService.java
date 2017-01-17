package ru.abc.price.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.abc.common.dto.Response;
import ru.abc.common.dto.price.AvgPriceRequest;
import ru.abc.common.dto.price.AvgPriceResponse;
import ru.abc.common.dto.price.WritePriceRequest;
import ru.abc.common.services.BaseService;
import ru.abc.price.domain.Price;
import ru.abc.price.repositories.PriceRepository;

import java.math.BigDecimal;
import java.util.List;

@Service
public class PriceService extends BaseService {

    private static final Logger logger = LoggerFactory.getLogger(PriceService.class);

    @Autowired
    private PriceRepository priceRepository;

    public AvgPriceResponse avg(AvgPriceRequest avgPriceRequest) {
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
        result = new AvgPriceResponse(sum.divide(new BigDecimal(i), 2, BigDecimal.ROUND_HALF_UP));
        logEndMethod("avg", result);
        return result;
    }

    @RequestMapping(value = "/write", produces = "application/json")
    public Response write(WritePriceRequest writePriceRequest) {
        logStartMethod("write", writePriceRequest);

        Price price = new Price(writePriceRequest.getProductId(), writePriceRequest.getPrice());
        priceRepository.insert(price);
        Response result = new Response();
        logEndMethod("write", result);
        return result;
    }


}
