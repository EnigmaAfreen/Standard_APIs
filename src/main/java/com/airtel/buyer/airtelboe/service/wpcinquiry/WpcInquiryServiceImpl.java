package com.airtel.buyer.airtelboe.service.wpcinquiry;

import com.airtel.buyer.airtelboe.dto.common.BoeResponse;
import com.airtel.buyer.airtelboe.dto.common.Error;
import com.airtel.buyer.airtelboe.dto.wpcinquiry.request.WpcInquiryRequest;
import com.airtel.buyer.airtelboe.dto.wpcinquiry.response.FilterResponse;
import com.airtel.buyer.airtelboe.dto.wpcinquiry.response.WpcInquiryRecords;
import com.airtel.buyer.airtelboe.dto.wpcinquiry.response.WpcInquiryResponseData;
import com.airtel.buyer.airtelboe.exception.BoeException;
import com.airtel.buyer.airtelboe.model.supplier.BTVL_MST_BOE_WPC_TBL;
import com.airtel.buyer.airtelboe.repository.supplier.BtvlMstBoeWpcTblRepository;
import com.airtel.buyer.airtelboe.util.BoeUtils;
import com.airtel.buyer.airtelboe.util.CommonConstants;
import com.airtel.buyer.airtelboe.util.excel.ExcelUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class WpcInquiryServiceImpl implements WpcInquiryService {

    @Autowired
    BtvlMstBoeWpcTblRepository btvlMstBoeWpcTblRepository;

    @Value("${app.node}")
    public String appNode;

    @Value("${UPLOAD_TMP_DIRECTORY}")
    public String uploadTmpDir;


    @Override
    public BoeResponse<WpcInquiryResponseData> getWpcData(WpcInquiryRequest wpcInquiryRequest) {
        log.info("Inside WpcInquiryServiceImpl :: method :: getWpcData");

        BoeResponse<WpcInquiryResponseData> boeResponse = new BoeResponse<>();
        WpcInquiryResponseData wpcInquiryResponseData = new WpcInquiryResponseData();

        wpcInquiryResponseData.setFilterResponse(this.getFilterResponseData(wpcInquiryRequest));
        wpcInquiryResponseData.setWpcInquiryRecords(this.fetchData(wpcInquiryRequest, boeResponse));

        boeResponse.setData(wpcInquiryResponseData);
        log.info("Exit method :: getWpcData");
        return boeResponse;
    }

    private List<WpcInquiryRecords> fetchData(WpcInquiryRequest wpcInquiryRequest, BoeResponse<WpcInquiryResponseData> boeResponse) {
        log.info("Inside WpcInquiryServiceImpl :: method :: fetchData");

        try {
            List<BTVL_MST_BOE_WPC_TBL> boeWpcTblRepositoryList = this.btvlMstBoeWpcTblRepository.
                    getWpcData(wpcInquiryRequest.getLob(), wpcInquiryRequest.getFromDate(), wpcInquiryRequest.getToDate(),
                            wpcInquiryRequest.getEndDate());

            List<WpcInquiryRecords> recordsList = null;

            if (boeWpcTblRepositoryList != null && !boeWpcTblRepositoryList.isEmpty()) {
                recordsList = boeWpcTblRepositoryList.stream().map(this::setData).collect(Collectors.toList());
            }

            log.info("Exit :: method :: fetchData");
            return recordsList;

        } catch (Exception e) {
            log.info("Exception raised while fetching wpc data :: " + e.getMessage());
            e.printStackTrace();
            this.setErrorObject(boeResponse);
        }
        return null;
    }

    private WpcInquiryRecords setData(BTVL_MST_BOE_WPC_TBL btvlMstBoeWpcTbl) {

        WpcInquiryRecords wpcInquiryRecords = new WpcInquiryRecords();
        wpcInquiryRecords.setPortCode(btvlMstBoeWpcTbl.getPortCode());
        wpcInquiryRecords.setItemCode(btvlMstBoeWpcTbl.getItemCode());
        wpcInquiryRecords.setLicenceNo(btvlMstBoeWpcTbl.getLicenceNo());
        wpcInquiryRecords.setWpcQty(btvlMstBoeWpcTbl.getWpcQty());
        wpcInquiryRecords.setDebitQty(btvlMstBoeWpcTbl.getWpcQty() != null && btvlMstBoeWpcTbl.getBalanceQty() != null ?
                btvlMstBoeWpcTbl.getWpcQty().subtract(btvlMstBoeWpcTbl.getBalanceQty()) :
                null);
        wpcInquiryRecords.setInvoiceNo(btvlMstBoeWpcTbl.getInvoiceNo());
        wpcInquiryRecords.setInvoiceDate(btvlMstBoeWpcTbl.getInvoiceDate());
        wpcInquiryRecords.setBoeNo(btvlMstBoeWpcTbl.getBoeNo());
        wpcInquiryRecords.setBoeDate(btvlMstBoeWpcTbl.getBoeDate());
        wpcInquiryRecords.setLob(btvlMstBoeWpcTbl.getLob());
        wpcInquiryRecords.setLicenceDate(btvlMstBoeWpcTbl.getLicenceDate());
        wpcInquiryRecords.setLeName(btvlMstBoeWpcTbl.getLeName());
        wpcInquiryRecords.setItemDesc(btvlMstBoeWpcTbl.getItemDescription());
        wpcInquiryRecords.setEndDate(btvlMstBoeWpcTbl.getEndDate());

        return wpcInquiryRecords;

    }

    private FilterResponse getFilterResponseData(WpcInquiryRequest wpcInquiryRequest) {

        FilterResponse filterResponse = new FilterResponse();

        filterResponse.setLob(wpcInquiryRequest.getLob());
        filterResponse.setEndDate(wpcInquiryRequest.getEndDate());
        filterResponse.setFromDate(wpcInquiryRequest.getFromDate());
        filterResponse.setToDate(wpcInquiryRequest.getToDate());

        return filterResponse;
    }

    @Override
    public BoeResponse<WpcInquiryResponseData> getWpcExcelData(WpcInquiryRequest wpcInquiryRequest, HttpServletResponse httpServletResponse) {
        log.info("Inside WpcInquiryServiceImpl :: method :: getWpcExcelData");

        BoeResponse<WpcInquiryResponseData> boeResponse = new BoeResponse<>();
        WpcInquiryResponseData wpcInquiryResponseData = new WpcInquiryResponseData();

        wpcInquiryResponseData.setWpcInquiryRecords(this.fetchData(wpcInquiryRequest, boeResponse));

        String excelName = "WpcDataReport";
        List excelData = wpcInquiryResponseData.getWpcInquiryRecords();

        List<String> headerTextList = this.getWpcExcelHeaders();
        List<Map<String, String>> rowMapList = this.getWpcExcelRowsData(excelData);


        String excelCheck = ExcelUtil.downloadExcel(headerTextList, rowMapList, excelName, httpServletResponse, this.uploadTmpDir);

        if (excelCheck.equalsIgnoreCase("y")) {
            log.info("Wpc Inquiry Excel Generated successfully");
        } else {
            throw new BoeException("Something went wrong", boeResponse, HttpStatus.OK);
        }
        log.info("Exit method :: getWpcExcelData");
        return boeResponse;
    }

    private List<Map<String, String>> getWpcExcelRowsData(List<WpcInquiryRecords> excelData) {

        List<Map<String, String>> rowMapList = new ArrayList<>();

        for (WpcInquiryRecords row : excelData) {
            Map<String, String> map = new LinkedHashMap<>();

            map.put("LICENCE NO", row.getLicenceNo() != null ? row.getLicenceNo() : "");
            map.put("LICENCE DATE", row.getLicenceDate() != null ? String.valueOf(row.getLicenceDate()) : "");
            map.put("LEGAL ENTITY", row.getLeName() != null ? row.getLeName() : "");
            map.put("LOB", row.getLob());
            map.put("INVOICE DATE", String.valueOf(row.getInvoiceDate()));
            map.put("BOE NO.", row.getBoeNo());
            map.put("BOE DATE", String.valueOf(row.getBoeDate()));
            map.put("PORT", row.getPortCode());
            map.put("ITEM CODE", row.getItemCode());
            map.put("ITEM DESCRIPTION", row.getItemDesc());
            map.put("DEBIT QTY", String.valueOf(row.getDebitQty()));
            map.put("WPC QTY", String.valueOf(row.getWpcQty()));
            map.put("END DATE", String.valueOf(row.getEndDate()));

            rowMapList.add(map);
        }
        return rowMapList;
    }

    private List<String> getWpcExcelHeaders() {

        List<String> headerTextList = new ArrayList<>();

        headerTextList.add("LICENCE NO");
        headerTextList.add("LICENCE DATE");
        headerTextList.add("LEGAL ENTITY");
        headerTextList.add("LOB");
        headerTextList.add("INVOICE NO.");
        headerTextList.add("INVOICE DATE");
        headerTextList.add("BOE NO.");
        headerTextList.add("BOE DATE");
        headerTextList.add("PORT");
        headerTextList.add("ITEM CODE");
        headerTextList.add("ITEM DESCRIPTION");
        headerTextList.add("DEBIT QTY");
        headerTextList.add("WPC QTY");
        headerTextList.add("END DATE");

        return headerTextList;
    }

    private void setErrorObject(BoeResponse<WpcInquiryResponseData> boeResponse) {
        Error error = new Error();
        error.setCode(CommonConstants.WPC_INQUIRY_ERROR_CODE);
        error.setTraceId(BoeUtils.generateTraceId(appNode));
        boeResponse.setError(error);
    }
}
