package ru.abc.common.dto.api;

import org.hibernate.validator.constraints.NotBlank;
import ru.abc.common.dto.Request;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class ProductPriceRequest extends Request {

    @NotBlank(message = "api.validation.name.notDefined")
    private String name;
    @NotNull(message = "api.validation.price.notDefined")
    private BigDecimal price;

    public ProductPriceRequest() {
    }

    public ProductPriceRequest(String name, BigDecimal price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append(ProductPriceRequest.class.getName())
                .append("{")
                .append("name=").append(name).append(", ")
                .append("price=").append(price)
                .append("}")
                .toString();
    }
}
