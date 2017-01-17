package ru.abc.price.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.abc.common.controllers.BaseController;
import ru.abc.common.dto.Response;
import ru.abc.common.dto.price.AvgPriceRequest;
import ru.abc.common.dto.price.AvgPriceResponse;
import ru.abc.common.dto.price.WritePriceRequest;
import ru.abc.price.services.PriceService;

import javax.validation.Valid;

@RestController
@RequestMapping("/price")
public class PriceController extends BaseController {

    @Autowired
    private PriceService priceService;

    @RequestMapping(value = "/avg", produces = "application/json")
    public AvgPriceResponse avg(@Valid @RequestBody AvgPriceRequest avgPriceRequest) {
        return priceService.avg(avgPriceRequest);
    }

    @RequestMapping(value = "/write", produces = "application/json")
    public Response write(@Valid @RequestBody WritePriceRequest writePriceRequest) {
        return priceService.write(writePriceRequest);
    }
}
