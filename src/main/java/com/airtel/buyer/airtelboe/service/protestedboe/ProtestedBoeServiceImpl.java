package com.airtel.buyer.airtelboe.service.protestedboe;

import com.airtel.buyer.airtelboe.dto.common.BoeResponse;
import com.airtel.buyer.airtelboe.dto.common.Error;
import com.airtel.buyer.airtelboe.dto.fetchprotestedboe.request.FetchProtestedRequest;
import com.airtel.buyer.airtelboe.dto.fetchprotestedboe.response.FetchProtestedData;
import com.airtel.buyer.airtelboe.dto.fetchprotestedboe.response.ProtestedBoeFilter;
import com.airtel.buyer.airtelboe.dto.fetchprotestedboe.response.ProtestedBoeRecord;
import com.airtel.buyer.airtelboe.dto.protestedboeaction.request.ProtestedBoeActionRequest;
import com.airtel.buyer.airtelboe.dto.protestedboeaction.response.ProtestedBoeActionData;
import com.airtel.buyer.airtelboe.exception.BoeException;
import com.airtel.buyer.airtelboe.model.supplier.BTVL_WKF_BOE_PROTESTED_TBL;
import com.airtel.buyer.airtelboe.repository.ProtestedBoe;
import com.airtel.buyer.airtelboe.repository.supplier.BtvlWkfBoeProtestedTblRepository;
import com.airtel.buyer.airtelboe.user.UserDetailsImpl;
import com.airtel.buyer.airtelboe.util.BoeUtils;
import com.airtel.buyer.airtelboe.util.CommonConstants;
import com.airtel.buyer.airtelboe.util.excel.ExcelUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProtestedBoeServiceImpl implements ProtestedBoeService {

    @Autowired
    private BtvlWkfBoeProtestedTblRepository btvlWkfBoeProtestedTblRepository;

    @Autowired
    private ProtestedBoeTransactionService protestedBoeTransactionService;

    @Autowired
    private HttpServletResponse httpServletResponse;

    @Value("UPLOAD_TMP_DIRECTORY")
    private String uploadTmpDir;

    @Value("${app.node}")
    public String appNode;

    @Override
    public BoeResponse<FetchProtestedData> protestedBoeInformation(FetchProtestedRequest fetchProtestedRequest, int page, int size) {

        BoeResponse<FetchProtestedData> boeResponse = new BoeResponse<>();
        boeResponse.setData(this.getFetchProtestedDataObject(fetchProtestedRequest, page, size));

        return boeResponse;
    }

    private FetchProtestedData getFetchProtestedDataObject(FetchProtestedRequest fetchProtestedRequest, int page, int size) {

        FetchProtestedData fetchProtestedData = new FetchProtestedData();
        fetchProtestedData.setProtestedBoeFilter(this.getProtestedBoeFilterObject(fetchProtestedRequest));
        fetchProtestedData.setProtestedBoeRecordList(this.getProtestedBoeRecordListObject(fetchProtestedRequest, page, size));

        return fetchProtestedData;
    }

    private ProtestedBoeFilter getProtestedBoeFilterObject(FetchProtestedRequest fetchProtestedRequest) {

        ProtestedBoeFilter protestedBoeFilter = new ProtestedBoeFilter();
        protestedBoeFilter.setBoeNo(fetchProtestedRequest.getBoeNo());
        protestedBoeFilter.setShipmentId(fetchProtestedRequest.getShipmentId());
        protestedBoeFilter.setStatus(fetchProtestedRequest.getStatus());
        protestedBoeFilter.setValidFrom(fetchProtestedRequest.getValidFrom());
        protestedBoeFilter.setValidTo(fetchProtestedRequest.getValidTo());

        return protestedBoeFilter;
    }

    private List<ProtestedBoeRecord> getProtestedBoeRecordListObject(FetchProtestedRequest fetchProtestedRequest, int page, int size) {

        List<ProtestedBoeRecord> protestedBoeRecordList = null;

        Page<ProtestedBoe> resultPage = this.btvlWkfBoeProtestedTblRepository.fetchProtestedBoeRecords(
                fetchProtestedRequest.getBoeNo(),
                fetchProtestedRequest.getShipmentId(),
                fetchProtestedRequest.getStatus(),
                fetchProtestedRequest.getValidFrom(),
                fetchProtestedRequest.getValidTo(),
                PageRequest.of(page - 1, size)
        );

        List<ProtestedBoe> protestedBoeList = resultPage.getContent();

        if (protestedBoeList != null && !protestedBoeList.isEmpty()) {
            protestedBoeRecordList = protestedBoeList.stream().map(this::getProtestedBoeRecordObject).collect(Collectors.toList());
        }

        return protestedBoeRecordList;
    }

    private ProtestedBoeRecord getProtestedBoeRecordObject(ProtestedBoe protestedBoe) {

        ProtestedBoeRecord protestedBoeRecord = new ProtestedBoeRecord();
        protestedBoeRecord.setOuNo(protestedBoe.getOuName());
        protestedBoeRecord.setSupplierName(protestedBoe.getVendorName());
        protestedBoeRecord.setPoNo(protestedBoe.getPoNo());
        protestedBoeRecord.setInvoiceNo(protestedBoe.getInvoiceNumbers());
        protestedBoeRecord.setShipmentId(protestedBoe.getShipmentId());
        protestedBoeRecord.setBoeNo(protestedBoe.getBoeNo());
        protestedBoeRecord.setBoeDate(protestedBoe.getBoeDate());
        protestedBoeRecord.setBoeProtestedDate(protestedBoe.getboeProtestDate());
        protestedBoeRecord.setChaName(protestedBoe.getChaName());
        protestedBoeRecord.setPortCode(protestedBoe.getPortCode());
        protestedBoeRecord.setReasonForProtest(protestedBoe.getReasonForProtest());
        protestedBoeRecord.setDaysRemaining((int) this.calculateDaysRemaining(protestedBoe.getboeProtestDate()));
        protestedBoeRecord.setActionStatus(protestedBoe.getActionStatus());
        protestedBoeRecord.setActionDate(protestedBoe.getActionDate());

        return protestedBoeRecord;
    }

    private long calculateDaysRemaining(LocalDate boeProtestDate) {

        LocalDateTime pDate = boeProtestDate.atStartOfDay();
        log.info("Protested Date : " + pDate.toString());
        pDate = pDate.plusDays(90);
        log.info("Protested Date Plus 90 Days : " + pDate.toString());
        LocalDateTime today = LocalDateTime.now();
        long diff = ChronoUnit.DAYS.between(today, pDate);
        log.info("Days Remaining : " + diff);

        return diff;
    }

    @Override
    public BoeResponse<ProtestedBoeActionData> protestedBoeAction(ProtestedBoeActionRequest protestedBoeActionRequest, UserDetailsImpl userDetails) {

        BoeResponse<ProtestedBoeActionData> boeResponse = new BoeResponse<>();
        boeResponse.setData(this.getProtestedBoeActionDataObject(protestedBoeActionRequest));

        if (this.checkMandatoryFields(protestedBoeActionRequest, boeResponse)) {
            this.action(protestedBoeActionRequest, userDetails);
        }

        return boeResponse;
    }

    private ProtestedBoeActionData getProtestedBoeActionDataObject(ProtestedBoeActionRequest protestedBoeActionRequest) {

        ProtestedBoeActionData protestedBoeActionData = new ProtestedBoeActionData();
        protestedBoeActionData.setShipmentIdList(protestedBoeActionRequest.getShipmentIdList());

        return protestedBoeActionData;
    }

    private Boolean checkMandatoryFields(ProtestedBoeActionRequest protestedBoeActionRequest,
                                         BoeResponse<ProtestedBoeActionData> boeResponse) {

        Boolean isValidationPassed = Boolean.TRUE;

        ProtestedBoeActionData protestedBoeActionData = boeResponse.getData();

        if (protestedBoeActionRequest.getShipmentIdList() == null ||
                protestedBoeActionRequest.getShipmentIdList().isEmpty()) {
            isValidationPassed = Boolean.FALSE;
            protestedBoeActionData.setShipmentIdErrMsg(CommonConstants.FIELD_IS_MANDATORY);
        }

        log.info("ProtestedBoeServiceImpl :: method :: checkMandatoryFields :: " + isValidationPassed);

        if (!isValidationPassed) {
            this.setErrorObject(boeResponse);
        }

        return isValidationPassed;
    }

    private void setErrorObject(BoeResponse<ProtestedBoeActionData> boeResponse) {
        Error error = new Error();
        error.setCode(CommonConstants.PROTESTED_BOE_ACTION_ERROR_CODE);
        error.setTraceId(BoeUtils.generateTraceId(appNode));
        boeResponse.setError(error);
    }

    private void action(ProtestedBoeActionRequest protestedBoeActionRequest, UserDetailsImpl userDetails) {

        for (BigDecimal shipmentId : protestedBoeActionRequest.getShipmentIdList()) {

            BTVL_WKF_BOE_PROTESTED_TBL bTVL_WKF_BOE_PROTESTED_TBL =
                    this.btvlWkfBoeProtestedTblRepository.findByShipmentIdAndPurgeFlag(shipmentId, 0);

            if (bTVL_WKF_BOE_PROTESTED_TBL != null && this.isValidAction(bTVL_WKF_BOE_PROTESTED_TBL)) {

                try {
                    this.protestedBoeTransactionService.protestedAction(bTVL_WKF_BOE_PROTESTED_TBL, userDetails);
                } catch (Exception e) {
                    log.info("Exception raised :: " + e.getMessage());
                }
            }

        }

    }

    private Boolean isValidAction(BTVL_WKF_BOE_PROTESTED_TBL bTVL_WKF_BOE_PROTESTED_TBL) {

        Boolean isValid = Boolean.FALSE;

        if (!"CLOSE".equalsIgnoreCase(bTVL_WKF_BOE_PROTESTED_TBL.getActionStatus())) {
            isValid = Boolean.TRUE;
        }

        log.info("ProtestedBoeServiceImpl :: method :: isValidAction :: " + bTVL_WKF_BOE_PROTESTED_TBL.getShipmentId() + " :: " + isValid);
        return isValid;
    }

    @Override
    public void protestedBoeExcel(FetchProtestedRequest fetchProtestedRequest) {

        List<ProtestedBoeRecord> protestedBoeRecordList = this.getProtestedBoeRecordListObject(fetchProtestedRequest, 1, 1000);

        String excelName = "ProtestedBoeOverallReport";

        List<String> headerTextList = this.getExcelHeaders();
        List<Map<String, String>> rowMapList = this.getExcelRowsData(protestedBoeRecordList);

        String excelCheck = ExcelUtil.downloadExcel(headerTextList, rowMapList, excelName, httpServletResponse, this.uploadTmpDir);

        if (excelCheck.equalsIgnoreCase("y")) {
            log.info("Protested BOE Excel Generated successfully");
        } else {
            throw new BoeException("Something went wrong", null, HttpStatus.OK);
        }

    }

    private List<String> getExcelHeaders() {

        List<String> headerTextList = new ArrayList<>();
        headerTextList.add("OU NO.");
        headerTextList.add("SUPPLIER NAME");
        headerTextList.add("PO NO.");
        headerTextList.add("INVOICE NO.");
        headerTextList.add("BOE NO.");
        headerTextList.add("AIRTEL REF NO.");
        headerTextList.add("BOE DATE");
        headerTextList.add("BOE PROTEST DATE");
        headerTextList.add("CHA NAME");
        headerTextList.add("PORT CODE");
        headerTextList.add("REASON FOR PROTEST");
        //              headerTextList.add("DAYS REMAINING");
        headerTextList.add("ACTION STATUS OPEN / CLOSE");
        headerTextList.add("ACTION DATE");

        return headerTextList;
    }

    private List<Map<String, String>> getExcelRowsData(List<ProtestedBoeRecord> excelData) {

        List<Map<String, String>> rowMapList = new ArrayList<>();

        for (ProtestedBoeRecord row : excelData) {
            Map<String, String> map = new LinkedHashMap<>();

            map.put("OU NO.", String.valueOf(row.getOuNo()));
            map.put("SUPPLIER NAME", (row.getSupplierName()));
            map.put("PO NO.", row.getPoNo());
            map.put("INVOICE NO.", row.getInvoiceNo());
            map.put("BOE NO.", row.getBoeNo());
            map.put("AIRTEL REF NO.", String.valueOf(row.getShipmentId()));
            map.put("BOE DATE", String.valueOf(row.getBoeDate()));
            map.put("BOE PROTEST DATE", String.valueOf(row.getBoeProtestedDate()));
            map.put("CHA NAME", row.getChaName());
            map.put("PORT CODE", row.getPortCode());
            map.put("REASON FOR PROTEST", row.getReasonForProtest());
            map.put("ACTION STATUS OPEN / CLOSE", row.getActionStatus());
            map.put("ACTION DATE", String.valueOf(row.getActionDate()));

            rowMapList.add(map);
        }

        return rowMapList;
    }

}
