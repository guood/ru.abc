package ru.abc;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;
import ru.abc.api.clients.PriceServiceClient;
import ru.abc.api.domain.Product;
import ru.abc.api.repositories.ProductRepository;
import ru.abc.api.services.ApiService;
import ru.abc.common.dto.Response;
import ru.abc.common.dto.api.ProductPriceRequest;
import ru.abc.common.dto.price.AvgPriceRequest;
import ru.abc.common.dto.price.AvgPriceResponse;
import ru.abc.common.dto.price.WritePriceRequest;
import ru.abc.common.messages.Messages;

import java.math.BigDecimal;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ApiServiceTest {
    @Mock
    private PriceServiceClient priceServiceClient;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private Messages messages;
    @InjectMocks
    private ApiService apiService;

    @Before
    public void init() {
        Product product = new Product();
        product.setId(1);
        product.setName("Product");
        when(productRepository.findByNameIgnoreCase(anyString())).thenReturn(product);

        when(priceServiceClient.avg(any(AvgPriceRequest.class))).thenReturn(new AvgPriceResponse(new BigDecimal(110)));
        when(priceServiceClient.write(any(WritePriceRequest.class))).thenReturn(new Response());
        ReflectionTestUtils.setField(apiService, "priceDelta", new BigDecimal(0.7));
    }

    @Test
    public void test() throws Exception {
        ProductPriceRequest productPriceRequest = new ProductPriceRequest("Product1", new BigDecimal(100.5));
        Response response = apiService.api(productPriceRequest);

        assertThat(response.getErrorCode(), is("0"));
        assertThat(response.getErrorMessage(), is(""));
        verify(productRepository).findByNameIgnoreCase(anyString());
        verify(priceServiceClient).avg(any(AvgPriceRequest.class));
        verify(priceServiceClient).write(any(WritePriceRequest.class));
    }
}
