package com.airtel.buyer.airtelboe.repository.supplier;

import com.airtel.buyer.airtelboe.model.supplier.BTVL_WKF_INVOICE_HEADER_TBL;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;

public interface BtvlWkfInvoiceHeaderTblRepository extends JpaRepository<BTVL_WKF_INVOICE_HEADER_TBL, BigDecimal> {

    List<BTVL_WKF_INVOICE_HEADER_TBL> findByInvoiceHeaderIdIn(List<BigDecimal> invoiceHeaderIds);
}
