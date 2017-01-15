package ru.abc.common.dto.price;

import ru.abc.common.dto.Request;

import java.math.BigDecimal;

public class WritePriceRequest extends Request {
    private Integer productId;
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
