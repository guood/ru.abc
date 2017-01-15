package ru.abc.common.dto.price;

import ru.abc.common.dto.Request;

public class AvgPriceRequest extends Request {
    private Integer productId;

    public AvgPriceRequest() {
    }

    public AvgPriceRequest(Integer productId) {
        this.productId = productId;
    }

    public Integer getProductId() {
        return productId;
    }

    public String toString() {
        return new StringBuilder()
                .append(AvgPriceRequest.class.getName())
                .append("{")
                .append("productId=").append(productId)
                .append("}")
                .toString();
    }
}
