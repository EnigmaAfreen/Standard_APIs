package com.airtel.buyer.airtelboe.dto.scmloblist.response;

import lombok.Data;

import javax.persistence.Column;

@Data
public class ScmLobListResponse {

    @Column(name = "lobName")
    private String lobName;

    @Column(name = "lobNameValue")
    private String lobNameValue;
}
