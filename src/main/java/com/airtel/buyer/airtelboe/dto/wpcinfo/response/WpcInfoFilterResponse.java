package com.airtel.buyer.airtelboe.dto.wpcinfo.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class WpcInfoFilterResponse {

    @JsonProperty("airtelBoeRefNo")
    private BigDecimal airtelBoeRefNo;

    @JsonProperty("fromDate")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MMM-yyyy")
    private LocalDate fromDate;

    @JsonProperty("toDate")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MMM-yyyy")
    private LocalDate toDate;

    @JsonProperty("invoiceNumber")
    private String invoiceNumber;

    @JsonProperty("partnerName")
    private String partnerName;

    @JsonProperty("boeNumber")
    private String boeNumber;

    @JsonProperty("chaName")
    private String chaName;

    @JsonProperty("destinationPort")
    private String destinationPort;

    @JsonProperty("toDateErrMsg")
    private String toDateErrMsg;

    @JsonProperty("fromDateErrMsg")
    private String fromDateErrMsg;

//    @JsonProperty("fromDateErrMsg")
//    private String fromDateErrMsg;
}
