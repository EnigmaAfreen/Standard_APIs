package com.airtel.buyer.airtelboe.dto.fetchboemismatch.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class BoeMismatchData {

//    @JsonProperty("partnerName")
//    private String partnerName;

    @JsonProperty("partnerCode")
    private String partnerCode;

//    @JsonProperty("boeNo")
//    private String boeNo;

//    @JsonProperty("airtelBoeRefNo")
//    private String shipmentId;

//    @JsonProperty("boeDate")
//    private String boeDate;

//    @JsonProperty("chaName")
//    private String chaName;

//    @JsonProperty("dutyAmount")
//    private String dutyAmount;

    @JsonProperty("rejectionReason")
    private String rejectionReason;

    @JsonProperty("actualAmount")
    private String actualAmount;

    @JsonProperty("rejectionDesc")
    private String rejectionDesc;

    @JsonProperty("mismatchDocs")
    private List<MismatchDoc> mismatchDocList;

}
