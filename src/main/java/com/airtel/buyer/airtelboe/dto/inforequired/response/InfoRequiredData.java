package com.airtel.buyer.airtelboe.dto.inforequired.response;

import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Data
public class InfoRequiredData {

    private List<BoeInfoTrail> boeInfoTrail;

}
