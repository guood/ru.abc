package ru.abc.price.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Document(collection = "price")
public class Price {

    @Id
    private String id;
    private Integer productId;
    private BigDecimal price;

    public Price() {
    }

    public Price(Integer productId, BigDecimal price) {
        this.productId = productId;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append(Price.class.getName())
                .append("{")
                .append("id=").append(id).append(", ")
                .append("productId=").append(productId).append(", ")
                .append("price=").append(price)
                .append("}")
                .toString();
    }
}
