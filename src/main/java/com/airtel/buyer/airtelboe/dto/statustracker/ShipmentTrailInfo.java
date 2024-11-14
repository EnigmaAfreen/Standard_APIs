package com.airtel.buyer.airtelboe.dto.statustracker;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Data
public class ShipmentTrailInfo {

    private BigDecimal TrailId;
    private BigDecimal ShipmentId;
    private BigDecimal Stage;
    private BigDecimal BucketNo;
    private BigDecimal Status;
    private String CommentHeader;
    private String CommentLine;
    private String Action;
    private String ActionBy;
    private BigDecimal PurgeFlag;
    private String Attribute1;
    private String Attribute2;
    private String Attribute3;
    private String Attribute4;
    private String Attribute5;
    private String Attribute6;
    private String Attribute7;
    private String Attribute8;
    private String Attribute9;
    private String Attribute10;
    private String Attribute11;
    private String Attribute12;
    private String Attribute13;
    private String Attribute14;
    private String Attribute15;
    private LocalDateTime CreationDate;
    private String Createdby;
    private LocalDateTime ModifiedDate;
    private String Modifiedby;

}
