package ru.abc.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import ru.abc.api.ApiApplication;
import ru.abc.api.clients.PriceServiceClient;
import ru.abc.common.dto.Response;
import ru.abc.common.dto.price.AvgPriceRequest;
import ru.abc.common.dto.price.AvgPriceResponse;
import ru.abc.common.dto.price.WritePriceRequest;

import java.math.BigDecimal;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApiApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
public class ApiIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @MockBean
    private PriceServiceClient priceServiceClient;

    @Before
    public void init() {
        jdbcTemplate.execute("insert into product(name) values ('Чай')");

        when(priceServiceClient.avg(any(AvgPriceRequest.class))).thenReturn(new AvgPriceResponse(new BigDecimal(100)));
        when(priceServiceClient.write(any(WritePriceRequest.class))).thenReturn(new Response());
    }

    @Test
    public void test() throws Exception {
        MvcResult mvcResult = mvc.perform(put("/api")
                .content("{\"name\": \"Чай\", \"price\": 100}")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn();

        ObjectMapper objectMapper = new ObjectMapper();
        Response response = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Response.class);

        assertThat(response.getErrorCode(), is("0"));
        assertThat(response.getErrorMessage(), is(""));
    }

}
