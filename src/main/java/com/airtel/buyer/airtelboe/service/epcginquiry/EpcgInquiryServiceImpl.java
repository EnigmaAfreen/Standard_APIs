package com.airtel.buyer.airtelboe.service.epcginquiry;

import com.airtel.buyer.airtelboe.dto.common.BoeResponse;
import com.airtel.buyer.airtelboe.dto.common.Error;
import com.airtel.buyer.airtelboe.dto.epcginquiry.request.EpcgInquiryRequest;
import com.airtel.buyer.airtelboe.dto.epcginquiry.response.EpcgFilter;
import com.airtel.buyer.airtelboe.dto.epcginquiry.response.EpcgInquiryRecords;
import com.airtel.buyer.airtelboe.dto.epcginquiry.response.EpcgInquiryResponseData;
import com.airtel.buyer.airtelboe.dto.wpcinquiry.request.WpcInquiryRequest;
import com.airtel.buyer.airtelboe.dto.wpcinquiry.response.WpcInquiryRecords;
import com.airtel.buyer.airtelboe.dto.wpcinquiry.response.WpcInquiryResponseData;
import com.airtel.buyer.airtelboe.exception.BoeException;
import com.airtel.buyer.airtelboe.model.supplier.BTVL_MST_BOE_EPCG_TBL;
import com.airtel.buyer.airtelboe.repository.supplier.BtvlMstBoeEpcgTblRepository;
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

@Service
@Slf4j
public class EpcgInquiryServiceImpl implements EpcgInquiryService {

    @Value("${app.node}")
    public String appNode;

    @Value("${UPLOAD_TMP_DIRECTORY}")
    public String uploadTmpDir;

    @Autowired
    BtvlMstBoeEpcgTblRepository btvlMstBoeEpcgTblRepository;

    @Override
    public BoeResponse<EpcgInquiryResponseData> getEpcgInquiryData(EpcgInquiryRequest epcgInquiryRequest) {
        log.info("Inside EpcgInquiryServiceImpl :: method :: getEpcgInquiryData");

        BoeResponse<EpcgInquiryResponseData> boeResponse = new BoeResponse<>();
        EpcgInquiryResponseData epcgInquiryResponseData = new EpcgInquiryResponseData();

        epcgInquiryResponseData.setEpcgFilter(this.getFilterResponseData(epcgInquiryRequest));
        epcgInquiryResponseData.setEpcgInquiryRecordsList(this.fetchData(epcgInquiryRequest, boeResponse));

        boeResponse.setData(epcgInquiryResponseData);
        log.info("Exit method :: getEpcgInquiryData");
        return boeResponse;
    }


    private List<EpcgInquiryRecords> fetchData(EpcgInquiryRequest epcgInquiryRequest, BoeResponse<EpcgInquiryResponseData> boeResponse) {

        log.info("Inside EpcgInquiryServiceImpl :: method :: fetchData");

        try {
            List<BTVL_MST_BOE_EPCG_TBL> boe_epcg_tbl = this.btvlMstBoeEpcgTblRepository.getData(
                    epcgInquiryRequest.getLob(), epcgInquiryRequest.getFromDate(), epcgInquiryRequest.getToDate(),
                    epcgInquiryRequest.getEndDate(), epcgInquiryRequest.getLe(), epcgInquiryRequest.getPort());

            List<EpcgInquiryRecords> recordsList = null;

            if (boe_epcg_tbl != null && !boe_epcg_tbl.isEmpty()) {
                recordsList = boe_epcg_tbl.stream().map(this::setData).collect(Collectors.toList());
            }

            log.info("Exit :: method :: fetchData");
            return recordsList;

        } catch (Exception e) {
            log.info("Exception raised while fetching epcg data :: " + e.getMessage());
            e.printStackTrace();
            this.setErrorObject(boeResponse);
        }
        return null;
    }

    private EpcgInquiryRecords setData(BTVL_MST_BOE_EPCG_TBL btvlMstBoeEpcgTbl) {

        EpcgInquiryRecords epcgInquiryRecords = new EpcgInquiryRecords();

        epcgInquiryRecords.setLicenceNo(btvlMstBoeEpcgTbl.getLicenceNo());
        epcgInquiryRecords.setLeName(btvlMstBoeEpcgTbl.getLeName());
        epcgInquiryRecords.setItemCode(btvlMstBoeEpcgTbl.getItemCode());
        epcgInquiryRecords.setItemDesc(btvlMstBoeEpcgTbl.getItemCode());
        epcgInquiryRecords.setPortCode(btvlMstBoeEpcgTbl.getPortCode());
        epcgInquiryRecords.setCreationDate(btvlMstBoeEpcgTbl.getCreationDate());
        epcgInquiryRecords.setDebitQty(btvlMstBoeEpcgTbl.getTotItemQtyEpcgLc() != null && btvlMstBoeEpcgTbl.getBalanceQty() != null ?
                btvlMstBoeEpcgTbl.getTotItemQtyEpcgLc().subtract(btvlMstBoeEpcgTbl.getBalanceQty()) :
                null);
        epcgInquiryRecords.setEndDate(btvlMstBoeEpcgTbl.getEndDate());
        epcgInquiryRecords.setLicenceDate(btvlMstBoeEpcgTbl.getLicenceDate());
        epcgInquiryRecords.setDutySaved(btvlMstBoeEpcgTbl.getDutySaved());
        epcgInquiryRecords.setInvoiceNo(btvlMstBoeEpcgTbl.getInvoiceNumber());
        epcgInquiryRecords.setLob(btvlMstBoeEpcgTbl.getLob());
        epcgInquiryRecords.setInvoiceDate(epcgInquiryRecords.getInvoiceDate());
        epcgInquiryRecords.setBoeNo(epcgInquiryRecords.getBoeNo());
        epcgInquiryRecords.setBoeDate(btvlMstBoeEpcgTbl.getBoeDate());
        epcgInquiryRecords.setItemSerialNo(btvlMstBoeEpcgTbl.getAttribute1());

        return epcgInquiryRecords;
    }

    private EpcgFilter getFilterResponseData(EpcgInquiryRequest epcgInquiryRequest) {

        EpcgFilter filterResponse = new EpcgFilter();

        filterResponse.setLob(epcgInquiryRequest.getLob());
        filterResponse.setEndDate(epcgInquiryRequest.getEndDate());
        filterResponse.setFromDate(epcgInquiryRequest.getFromDate());
        filterResponse.setToDate(epcgInquiryRequest.getToDate());
        filterResponse.setLe(epcgInquiryRequest.getLe());
        filterResponse.setPort(epcgInquiryRequest.getPort());

        return filterResponse;
    }

    @Override
    public BoeResponse<EpcgInquiryResponseData> getEpcgExcelData(EpcgInquiryRequest epcgInquiryRequest, HttpServletResponse httpServletResponse) {
        log.info("Inside EpcgInquiryServiceImpl :: method :: getEpcgExcelData");

        BoeResponse<EpcgInquiryResponseData> boeResponse = new BoeResponse<>();
        EpcgInquiryResponseData epcgInquiryResponseData = new EpcgInquiryResponseData();

        epcgInquiryResponseData.setEpcgInquiryRecordsList(this.fetchData(epcgInquiryRequest, boeResponse));

        String excelName = "EpcgDataReport";
        List excelData = epcgInquiryResponseData.getEpcgInquiryRecordsList();

        List<String> headerTextList = this.getEpcgExcelHeaders();
        List<Map<String, String>> rowMapList = this.getEpcgExcelRowsData(excelData);


        String excelCheck = ExcelUtil.downloadExcel(headerTextList, rowMapList, excelName, httpServletResponse, this.uploadTmpDir);

        if (excelCheck.equalsIgnoreCase("y")) {
            log.info("EPCG Inquiry Excel Generated successfully");
        } else {
            throw new BoeException("Something went wrong", boeResponse, HttpStatus.OK);
        }
        log.info("Exit method :: getEpcgExcelData");
        return boeResponse;
    }

    private List<Map<String, String>> getEpcgExcelRowsData(List<EpcgInquiryRecords> excelData) {

        List<Map<String, String>> rowMapList = new ArrayList<>();

        for (EpcgInquiryRecords row : excelData) {
            Map<String, String> map = new LinkedHashMap<>();

            map.put("LICENCE NO", row.getLicenceNo() != null ? row.getLicenceNo() : "");
            map.put("LICENCE DATE", row.getLicenceDate() != null ? String.valueOf(row.getLicenceDate()) : "");
            map.put("LEGAL ENTITY", row.getLeName() != null ? row.getLeName() : "");
            map.put("LOB", row.getLob());
            map.put("INVOICE NO.", row.getInvoiceNo());
            map.put("INVOICE DATE", row.getInvoiceDate() != null ? String.valueOf(row.getInvoiceDate()) : "");
            map.put("BOE NO.", row.getBoeNo());
            map.put("BOE DATE", row.getBoeDate() != null ? String.valueOf(row.getBoeDate()) : "");
            map.put("PORT", row.getPortCode());
            map.put("ITEM SERIAL NO", row.getItemSerialNo());
            map.put("ITEM CODE", row.getItemCode());
            map.put("ITEM DESCRIPTION", row.getItemDesc());
            map.put("DEBIT QTY", String.valueOf(row.getDebitQty()));
            map.put("DUTY SAVED", row.getDutySaved());
            map.put("END DATE", row.getEndDate() != null ? String.valueOf(row.getEndDate()) : "");

            rowMapList.add(map);
        }
        return rowMapList;
    }

    private List<String> getEpcgExcelHeaders() {

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
        headerTextList.add("ITEM SERIAL NO");
        headerTextList.add("ITEM CODE");
        headerTextList.add("ITEM DESCRIPTION");
        headerTextList.add("DEBIT QTY");
        headerTextList.add("DUTY SAVED");
        headerTextList.add("END DATE");

        return headerTextList;
    }

    private void setErrorObject(BoeResponse<EpcgInquiryResponseData> boeResponse) {
        Error error = new Error();
        error.setCode(CommonConstants.EPCG_INQUIRY_ERROR_CODE);
        error.setTraceId(BoeUtils.generateTraceId(appNode));
        boeResponse.setError(error);
    }

}
