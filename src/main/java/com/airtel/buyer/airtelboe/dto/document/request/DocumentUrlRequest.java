package com.airtel.buyer.airtelboe.dto.document.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DocumentUrlRequest {

    private String docName;
    private String contentId;
}
