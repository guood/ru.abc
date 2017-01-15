package ru.abc.common.dto.price;

import ru.abc.common.dto.Response;

import java.math.BigDecimal;

public class AvgPriceResponse extends Response {

    private BigDecimal avgPrice;

    public AvgPriceResponse() {
    }

    public AvgPriceResponse(BigDecimal avgPrice) {
        this.avgPrice = avgPrice;
    }

    public AvgPriceResponse(String errorCode, String errorMessage) {
        super(errorCode, errorMessage);
    }

    public BigDecimal getAvgPrice() {
        return avgPrice;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append(AvgPriceResponse.class.getName())
                .append("{")
                .append(super.toString()).append(", ")
                .append("avgPrice=").append(avgPrice)
                .append("}")
                .toString();
    }
}
