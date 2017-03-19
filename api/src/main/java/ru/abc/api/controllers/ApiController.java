package ru.abc.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.abc.api.services.ApiService;
import ru.abc.common.controllers.BaseController;
import ru.abc.common.dto.Response;
import ru.abc.common.dto.api.ProductPriceRequest;
import ru.abc.common.exceptions.ApplicationException;

import javax.validation.Valid;

@RestController
public class ApiController extends BaseController {
    @Autowired
    private ApiService apiService;

    @RequestMapping(value = "/br1/api", produces = "application/json")
    public Response api(@Valid @RequestBody ProductPriceRequest productPrice) throws ApplicationException {
        return apiService.api(productPrice);
    }
}
