package c2.code.authenservice.repository.https_client;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import spring.boot.webcococo.models.response.ExchangeRateResponse;

@Component
@FeignClient(name = "exchangerate-client", url = "https://v6.exchangerate-api.com")
public interface ExchangerateClient {
    @GetMapping(value = "/v6/a03f68fbbe4bbcdce1d38b78/latest/USD", produces = MediaType.APPLICATION_JSON_VALUE)
    ExchangeRateResponse  getExchangeRate();
}