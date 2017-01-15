package ru.abc.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import ru.abc.common.dto.Response;
import ru.abc.common.dto.price.AvgPriceResponse;
import ru.abc.price.PriceServiceApplication;
import ru.abc.price.domain.Price;

import java.math.BigDecimal;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = PriceServiceApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
public class PriceControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Before
    public void init() {
        mongoTemplate.dropCollection(Price.class);
        mongoTemplate.insert(new Price(1, new BigDecimal(100)));
    }

    @Test
    public void testAvg()throws Exception {
        MvcResult mvcResult = mvc.perform(put("/price/avg")
                .content("{\"productId\": 1}")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn();
        ObjectMapper objectMapper = new ObjectMapper();
        AvgPriceResponse avgPriceResponse = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), AvgPriceResponse.class);
        assertThat(avgPriceResponse.getErrorCode(), is("0"));
        assertThat(avgPriceResponse.getErrorMessage(), is(""));
        assertThat(avgPriceResponse.getAvgPrice(), is(new BigDecimal(100)));
    }

    @Test
    public void testWrite()throws Exception {
        MvcResult mvcResult = mvc.perform(put("/price/write")
                .content("{\"productId\": 2, \"price\": 150}")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn();
        ObjectMapper objectMapper = new ObjectMapper();
        Response avgPriceResponse = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Response.class);
        assertThat(avgPriceResponse.getErrorCode(), is("0"));
        assertThat(avgPriceResponse.getErrorMessage(), is(""));

        List<Price> priceList = mongoTemplate.find(query(where("productId").is(2)), Price.class);
        assertThat(priceList.size(), is(1));
        assertThat(priceList.get(0).getPrice(), is(new BigDecimal(150)));
    }


}
