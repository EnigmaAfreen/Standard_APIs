package com.airtel.buyer.airtelboe.dto.statustracker;

import com.airtel.buyer.airtelboe.model.supplier.BTVL_WKF_BOE_SHIP_LINE_TBL;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ShipmentLines {

    private String invoiceNumber;
    private BigDecimal invoiceHeaderId;
    private List<BTVL_WKF_BOE_SHIP_LINE_TBL> lines;
    private String lineCountry;

}
