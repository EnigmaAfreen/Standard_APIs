package com.airtel.buyer.airtelboe.dto.fetchchecklist.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class FetchCheckListData {

    @JsonProperty("checkListHeader")
    private CheckListHeaderRes  checkListHeaderRes;

    @JsonProperty("checkListInvoices")
    private List<CheckListInvoiceRes> checkListInvoiceResList;

}
