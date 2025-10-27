package hust.hoangson.clients;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ClientResponse<T> {

    @JsonProperty("data") // <- map với field data của BaseResponse
    private T body;

    @JsonProperty("code")
    private int code;

    @JsonProperty("message")
    private String message;


    public ClientResponse() {}

    @JsonCreator
    public ClientResponse(
            @JsonProperty("data") T body,
            @JsonProperty("code") int statusCode,
            @JsonProperty("message") String errorMessage) {
        this.body = body;
        this.code = statusCode;
        this.message = errorMessage;
    }

}
