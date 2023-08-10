package com.practice.demo.dto.json_obtaining;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Map;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class CurrencyRatesJsonDto {

    @JsonProperty(value = "Timestamp")
    private Timestamp timestamp;

    @JsonProperty(value = "Valute")
    private Map<String, Valute> valuteMap;

    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Valute {

        @JsonProperty(value = "Nominal")
        private int nominal;

        @JsonProperty(value = "Value")
        private BigDecimal value;
    }
}
