package ru.abc.common.dto.price;

import ru.abc.common.dto.Request;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class WritePriceRequest extends Request {
    @NotNull(message = "price.validation.productId.error.isNull")
    private Integer productId;
    @NotNull(message = "price.validation.price.error.isNull")
    private BigDecimal price;

    public WritePriceRequest() {
    }

    public WritePriceRequest(Integer productId, BigDecimal price) {
        this.productId = productId;
        this.price = price;
    }

    public Integer getProductId() {
        return productId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append(WritePriceRequest.class.getName())
                .append("{")
                .append("productId=").append(productId).append(", ")
                .append("price=").append(price)
                .append("}")
                .toString();
    }

}
