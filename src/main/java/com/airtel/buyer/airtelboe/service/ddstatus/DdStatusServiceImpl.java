package com.airtel.buyer.airtelboe.service.ddstatus;

import com.airtel.buyer.airtelboe.dto.common.BoeResponse;
import com.airtel.buyer.airtelboe.dto.common.Error;
import com.airtel.buyer.airtelboe.dto.ddstatus.request.DdStatusRequest;
import com.airtel.buyer.airtelboe.dto.ddstatus.response.DdStatusFilterResponse;
import com.airtel.buyer.airtelboe.dto.ddstatus.response.DdStatusRecords;
import com.airtel.buyer.airtelboe.dto.ddstatus.response.DdStatusResponse;
import com.airtel.buyer.airtelboe.exception.BoeException;
import com.airtel.buyer.airtelboe.repository.DdStatusData;
import com.airtel.buyer.airtelboe.repository.supplier.BtvlWkfBoeDdTblRepository;
import com.airtel.buyer.airtelboe.util.BoeUtils;
import com.airtel.buyer.airtelboe.util.CommonConstants;
import com.airtel.buyer.airtelboe.util.excel.ExcelUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class DdStatusServiceImpl implements DdStatusService {


    @Autowired
    BtvlWkfBoeDdTblRepository btvlWkfBoeDdTblRepository;

    @Value("${app.node}")
    public String appNode;

    @Value("${UPLOAD_TMP_DIRECTORY}")
    public String uploadTmpDir;


    @Override
    public BoeResponse<DdStatusResponse> fetchDdStatusDashData(int page, int size, DdStatusRequest ddStatusRequest) {
        BoeResponse<DdStatusResponse> boeResponse = new BoeResponse<>();
        DdStatusResponse ddStatusResponse = new DdStatusResponse();
//        boeResponse.setData(ddStatusResponse);
        ddStatusResponse.setDdStatusFilterResponse(this.getFilterResponseData(ddStatusRequest));
        if (!this.checkMandatoryFields(ddStatusRequest, ddStatusResponse, boeResponse)) {
            ddStatusResponse.setRecordsList(this.getBucketData(page, size, ddStatusRequest, boeResponse));
        }

        boeResponse.setData(ddStatusResponse);

        log.info("Exit method :: fetchDdStatusDashData");

        return boeResponse;
    }

    @Override
    public BoeResponse<DdStatusResponse> getDdStatusDashExcelData(int page, int size, DdStatusRequest ddStatusRequest, HttpServletResponse httpServletResponse) {
        BoeResponse<DdStatusResponse> boeResponse = new BoeResponse<>();
        DdStatusResponse ddStatusResponse = new DdStatusResponse();
        ddStatusResponse.setDdStatusFilterResponse(this.getFilterResponseData(ddStatusRequest));
        if (!this.checkMandatoryFields(ddStatusRequest, ddStatusResponse, boeResponse)) {

            ddStatusResponse.setRecordsList(this.getBucketData(page, size, ddStatusRequest, boeResponse));
            boeResponse.setData(ddStatusResponse);

            String excelName = "ExportExcel";
            List excelData = ddStatusResponse.getRecordsList();

            List<String> headerTextList = this.getDdStatusDashExcelHeaders();
            List<Map<String, String>> rowMapList = this.getDdStatusDashExcelRowsData(excelData);

            String excelCheck = ExcelUtil.downloadExcel(headerTextList, rowMapList, excelName, httpServletResponse, this.uploadTmpDir);

            if (excelCheck.equalsIgnoreCase("y")) {
                log.info("DdStatus Dashboard Excel Generated successfully");
            } else {
                throw new BoeException("Something went wrong", boeResponse, HttpStatus.OK);
            }
            log.info("Exit method :: getDdStatusDashExcelData");

        }
        boeResponse.setData(ddStatusResponse);
        return boeResponse;
    }

    private DdStatusFilterResponse getFilterResponseData(DdStatusRequest ddStatusRequest) {

        DdStatusFilterResponse ddStatusFilterResponse = new DdStatusFilterResponse();

        ddStatusFilterResponse.setAirtelBoeRefNo(ddStatusRequest.getDdStatusFilterRequest().getAirtelBoeRefNo());
        ddStatusFilterResponse.setBoeNo(ddStatusRequest.getDdStatusFilterRequest().getBoeNo());
        ddStatusFilterResponse.setInvoiceNumber(ddStatusRequest.getDdStatusFilterRequest().getInvoiceNumber());
        ddStatusFilterResponse.setStatus(ddStatusRequest.getDdStatusFilterRequest().getStatus());
        ddStatusFilterResponse.setAwbNo(ddStatusRequest.getDdStatusFilterRequest().getAwbNo());
        ddStatusFilterResponse.setFromDate(ddStatusRequest.getDdStatusFilterRequest().getFromDate());
        ddStatusFilterResponse.setToDate(ddStatusRequest.getDdStatusFilterRequest().getToDate());

        return ddStatusFilterResponse;
    }

    private List<DdStatusRecords> getBucketData(int page, int size, DdStatusRequest ddStatusRequest, BoeResponse<DdStatusResponse> boeResponse) {


        List<DdStatusRecords> ddStatusRecords = new ArrayList<>();

        try {
            Pageable pageable = PageRequest.of(page - 1, size);

            Page<DdStatusData> ddStatusData = this.btvlWkfBoeDdTblRepository.getDdStatusDashData(ddStatusRequest.getDdStatusFilterRequest().getAirtelBoeRefNo(), ddStatusRequest.getDdStatusFilterRequest().getFromDate(),
                    ddStatusRequest.getDdStatusFilterRequest().getToDate(), ddStatusRequest.getDdStatusFilterRequest().getInvoiceNumber(),
                    ddStatusRequest.getDdStatusFilterRequest().getBoeNo(), ddStatusRequest.getDdStatusFilterRequest().getStatus(), ddStatusRequest.getDdStatusFilterRequest().getAwbNo(), pageable);


            log.info("Records per page :: " + ddStatusData.getNumberOfElements());


            ddStatusRecords = this.getRecords(ddStatusData, ddStatusRecords, boeResponse);
        } catch (Exception e) {
            log.info("Exception raised while fetching dd status dashboard data :: " + e.getMessage());
            e.printStackTrace();
            this.setErrorObject(boeResponse);
        }
        return ddStatusRecords;
    }

    private void setErrorObject(BoeResponse<DdStatusResponse> boeResponse) {
        com.airtel.buyer.airtelboe.dto.common.Error error = new Error();
        error.setCode(CommonConstants.DD_STATUS_DASHBOARD_ERROR_CODE);
        error.setTraceId(BoeUtils.generateTraceId(appNode));
        boeResponse.setError(error);
    }

    private List<DdStatusRecords> getRecords(Page<DdStatusData> ddStatusData, List<DdStatusRecords> ddStatusRecords, BoeResponse<DdStatusResponse> boeResponse) {

//        List<DdStatusRecords> recordsList = new ArrayList<>();

        ddStatusData.stream().forEach(f -> {

            DdStatusRecords records = new DdStatusRecords();

            records.setDdId(f.getDdId() != null ? f.getDdId() : null);
            records.setBoeNo(f.getBoeNo() != null ? f.getBoeNo() : "");
            records.setAirtelReferrenceNo(f.getAirtelReferrenceNo() != null ? f.getAirtelReferrenceNo() : null);
            records.setShipmentAwbNo(f.getShipmentAwbNo() != null ? f.getShipmentAwbNo() : "");
            records.setInvoiceNumbers(f.getInvoiceNumber() != null ? f.getInvoiceNumber() : "");
            records.setDocumentNo(f.getDocumentNo() != null ? f.getDocumentNo() : "");
            records.setAmount(f.getAmount() != null ? f.getAmount() : null);
            records.setPayable(f.getPayable() != null ? f.getPayable() : "");
            records.setPort(f.getPort() != null ? f.getPort() : "");
            records.setDdDate(f.getDdDate() != null ? f.getDdDate() : null);
            records.setStatus(f.getStatus() != null ? f.getStatus() : null);
            records.setDdReferrenceN(f.getDdReferrenceNo() != null ? f.getDdReferrenceNo() : "");
            records.setChaName(f.getChaName() != null ? f.getChaName() : "");
            records.setPartnerVendorCode(f.getPartnerVendorCode() != null ? f.getPartnerVendorCode() : null);

            ddStatusRecords.add(records);

        });
        return ddStatusRecords;
    }

    private List<String> getDdStatusDashExcelHeaders() {

        List<String> headerTextList = new ArrayList<>();

        headerTextList.add("BOE NO.");
        headerTextList.add("AIRTEL REFERRENCE NO.");
        headerTextList.add("SHIPMENT / AWB NO.");
        headerTextList.add("INVOICE NO.");
        headerTextList.add("DOCUMENT NO.");
        headerTextList.add("AMOUNT");
        headerTextList.add("PAYABLE");
        headerTextList.add("PORT");
        headerTextList.add("DATE");
        headerTextList.add("STATUS");
        headerTextList.add("DD REFERRENCE NO.");

        return headerTextList;
    }

    private List<Map<String, String>> getDdStatusDashExcelRowsData(List<DdStatusRecords> excelData) {

        List<Map<String, String>> rowMapList = new ArrayList<>();

        for (DdStatusRecords row : excelData) {
            Map<String, String> map = new LinkedHashMap<>();


            map.put("BOE NO.", row.getBoeNo());
            map.put("AIRTEL REFERRENCE NO.",
                    row.getAirtelReferrenceNo() != null ? row.getAirtelReferrenceNo().toString() : null);
            map.put("SHIPMENT / AWB NO.", row.getShipmentAwbNo());
            map.put("INVOICE NO.", row.getInvoiceNumbers());
            map.put("DOCUMENT NO.", row.getDocumentNo());
            map.put("AMOUNT", row.getAmount() != null ? row.getAmount().toString() : null);
            map.put("PAYABLE", row.getPayable());
            map.put("PORT", row.getPort());
            map.put("DATE", row.getDdDate() != null ? row.getDdDate().toString() : null);
            map.put("STATUS", row.getStatus());
            map.put("DD REFERRENCE NO.", row.getDdReferrenceN());
            rowMapList.add(map);

        }
        return rowMapList;
    }

    public Boolean checkMandatoryFields(DdStatusRequest ddStatusRequest,
                                        DdStatusResponse ddStatusResponse, BoeResponse<DdStatusResponse> boeResponse) {
        Boolean status = Boolean.FALSE;
        DdStatusFilterResponse ddStatusFilterResponse = ddStatusResponse.getDdStatusFilterResponse();

        if (ddStatusRequest.getDdStatusFilterRequest().getFromDate() != null && ddStatusRequest.getDdStatusFilterRequest().getToDate() == null) {
            status = Boolean.TRUE;
            ddStatusFilterResponse.setToDateErrMsg(CommonConstants.FIELD_IS_MANDATORY);
        }
        if (ddStatusRequest.getDdStatusFilterRequest().getToDate() != null && ddStatusRequest.getDdStatusFilterRequest().getFromDate() == null) {
            status = Boolean.TRUE;
            ddStatusFilterResponse.setFromDateErrMsg(CommonConstants.FIELD_IS_MANDATORY);
        }
        if (ddStatusRequest.getDdStatusFilterRequest().getFromDate() != null && ddStatusRequest.getDdStatusFilterRequest().getToDate() != null) {
            if (ddStatusRequest.getDdStatusFilterRequest().getToDate().isBefore(ddStatusRequest.getDdStatusFilterRequest().getFromDate())) {
                status = Boolean.TRUE;
                ddStatusFilterResponse.setToDateErrMsg("To Date Should be after From Date");
            }

        }

        if (status) {
            this.setErrorObject(boeResponse);
        }
        log.info("WpcInfoServiceImpl :: inside method :: checkMandatoryFields :: status: " + status);
        return status;
    }
}
