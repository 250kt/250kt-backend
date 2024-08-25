package fr.gofly.response;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class TafResponse {

    @Setter
    @Getter
    @JsonProperty("results")
    private int results;

    @Setter
    @Getter
    @JsonProperty("data")
    private List<String> data;

}