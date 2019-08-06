package com.liyosi.api.account.transfer.exceptions;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class TransferFailedExceptionMapper implements ExceptionMapper<TransferFailedException> {

  private static class TransferFailedResponse {
    private String message;

    private Integer errorCode;

    private TransferFailedResponse(String message, Integer errorCode) {
      this.message = message;
      this.errorCode = errorCode;
    }

    @JsonProperty("errorMessage")
    public String getMessage() {
      return message;
    }

    @JsonProperty("errorCode")
    public Integer getErrorCode() {
      return errorCode;
    }
  }

  @Override
  public Response toResponse(TransferFailedException transferFailedReason) {
    return Response.status(Response.Status.BAD_REQUEST)
        .entity(new TransferFailedResponse(transferFailedReason.getMessage(), transferFailedReason.getErrorCode()))
        .type(MediaType.APPLICATION_JSON_TYPE)
        .build();
  }
}
