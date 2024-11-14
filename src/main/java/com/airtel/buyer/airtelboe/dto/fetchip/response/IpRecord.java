package com.airtel.buyer.airtelboe.dto.fetchip.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class IpRecord {

    @JsonProperty("boeNo")
    private String boeNo;

    @JsonProperty("airtelBoeRefNo")
    private BigDecimal shipmentId;

    @JsonProperty("ouNo")
    private String ouNo;

    @JsonProperty("poNo")
    private String poNo;

    @JsonProperty("invoiceNo")
    private String invoiceNo;

    @JsonProperty("boeDate")
    private LocalDate boeDate;

    @JsonProperty("chaName")
    private String chaName;

    @JsonProperty("partnerName")
    private String partnerName;

    @JsonProperty("partnerCode")
    private BigDecimal partnerCode;

    @JsonProperty("dutyAmount")
    private String dutyAmount;

//    @JsonProperty("ipDocs")
//    private List<IpDoc> ipDocList;

}
