package ru.abc;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ru.abc.common.dto.Response;
import ru.abc.common.dto.price.AvgPriceRequest;
import ru.abc.common.dto.price.AvgPriceResponse;
import ru.abc.common.dto.price.WritePriceRequest;
import ru.abc.common.messages.Messages;
import ru.abc.price.domain.Price;
import ru.abc.price.repositories.PriceRepository;
import ru.abc.price.services.PriceService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PriceServiceTest {

    @Mock
    private PriceRepository priceRepository;
    @Mock
    private Messages messages;
    @InjectMocks
    private PriceService priceService;

    @Before
    public void init() {
        List<Price> priceList = new ArrayList<>();
        priceList.add(new Price(1, new BigDecimal(100)));
        priceList.add(new Price(1, new BigDecimal(110)));
        when(priceRepository.findByProductId(1)).thenReturn(priceList);
        when(priceRepository.findByProductId(2)).thenReturn(Collections.emptyList());
    }

    @Test
    public void testAvg() {
        AvgPriceRequest avgPriceRequest = new AvgPriceRequest(1);
        AvgPriceResponse avgPriceResponse = priceService.avg(avgPriceRequest);

        assertThat(avgPriceResponse.getErrorCode(), is("0"));
        assertThat(avgPriceResponse.getErrorMessage(), is(""));
        assertThat(avgPriceResponse.getAvgPrice(), is(new BigDecimal(105)));
    }

    @Test
    public void testPricesNotFound() {
        AvgPriceRequest avgPriceRequest = new AvgPriceRequest(2);
        AvgPriceResponse avgPriceResponse = priceService.avg(avgPriceRequest);

        assertThat(avgPriceResponse.getErrorCode(), is("2"));
        assertThat(avgPriceResponse.getErrorMessage(), is("Price not found"));
    }

    @Test
    public void testWrite() {
        WritePriceRequest writePriceRequest = new WritePriceRequest(1, new BigDecimal(105));
        Response response = priceService.write(writePriceRequest);

        assertThat(response.getErrorCode(), is("0"));
        assertThat(response.getErrorMessage(), is(""));
        verify(priceRepository).insert(any(Price.class));
    }
}
