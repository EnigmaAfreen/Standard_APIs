package com.airtel.buyer.airtelboe.model.supplier;


import com.airtel.buyer.airtelboe.model.BTVL_GST_PIN_STATE_CITY_PK;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.math.BigDecimal;

@Entity
@Data
@IdClass(BTVL_GST_PIN_STATE_CITY_PK.class)
public class BTVL_GST_PIN_STATE_CITY_MV {

    @Id
    @Column(name = "PINCODE")
    private BigDecimal pincode;

    @Id
    @Column(name = "STATE")
    private String state;

    @Id
    @Column(name = "CITY")
    private String city;

}
