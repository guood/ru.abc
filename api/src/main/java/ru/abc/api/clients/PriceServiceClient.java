package ru.abc.api.clients;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.abc.api.config.PriceServiceClientConfig;
import ru.abc.common.dto.Response;
import ru.abc.common.dto.price.AvgPriceRequest;
import ru.abc.common.dto.price.AvgPriceResponse;
import ru.abc.common.dto.price.WritePriceRequest;

@Service
@FeignClient(name = "priceService",
        url = "${api.clients.priceService.url}",
        configuration = PriceServiceClientConfig.class)
public interface PriceServiceClient {

    @RequestMapping("/price/avg")
    AvgPriceResponse avg(AvgPriceRequest avgPriceRequest);

    @RequestMapping("/price/write")
    Response write(WritePriceRequest writePriceRequest);
}
