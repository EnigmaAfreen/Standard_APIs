package com.airtel.buyer.airtelboe.service.scmdashboard;

import com.airtel.buyer.airtelboe.dto.common.BoeResponse;
import com.airtel.buyer.airtelboe.dto.common.Error;
import com.airtel.buyer.airtelboe.dto.scmdashboard.request.ScmDashRequest;
import com.airtel.buyer.airtelboe.dto.scmdashboard.response.ScmBucketData;
import com.airtel.buyer.airtelboe.dto.scmdashboard.response.ScmDashRecords;
import com.airtel.buyer.airtelboe.dto.scmdashboard.response.ScmDashResponse;
import com.airtel.buyer.airtelboe.dto.scmdashboard.response.ScmFilterResponse;
import com.airtel.buyer.airtelboe.exception.BoeException;
import com.airtel.buyer.airtelboe.model.supplier.BTVL_EMP_ROLE_MAPPING_TBL;
import com.airtel.buyer.airtelboe.repository.ScmDashData;
import com.airtel.buyer.airtelboe.repository.supplier.BtvlEmpRoleMappingTblRepository;
import com.airtel.buyer.airtelboe.repository.supplier.BtvlWkfBoeShipmentTblRepository;
import com.airtel.buyer.airtelboe.util.BoeUtils;
import com.airtel.buyer.airtelboe.util.CommonConstants;
import com.airtel.buyer.airtelboe.util.excel.ExcelUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.*;

@Service
@Slf4j
public class ScmDashboardServiceImpl implements ScmDashboardService {

    @Autowired
    private BtvlWkfBoeShipmentTblRepository btvlWkfBoeShipmentTblRepository;

    @Autowired
    private BtvlEmpRoleMappingTblRepository btvlEmpRoleMappingTblRepository;

    @Value("${app.node}")
    public String appNode;

    @Value("${UPLOAD_TMP_DIRECTORY}")
    public String uploadTmpDir;


    @Override
    public BoeResponse<ScmDashResponse> getScmDashData(int page, int size, String olm, String role, ScmDashRequest scmDashRequest) {

        log.info("Inside class ScmDashboardServiceImpl :: method :: getScmDashData");
        BoeResponse<ScmDashResponse> boeResponse = new BoeResponse<>();
        ScmDashResponse scmDashResponse = new ScmDashResponse();

        scmDashResponse.setBucket(scmDashRequest.getBucket());
        scmDashResponse.setScmFilterResponse(this.getFilterResponseData(scmDashRequest));

        String lobVal = null;
        switch (role.toUpperCase()) {
            case "SCM_ADMIN":
                scmDashResponse.setScmBucketData(this.getBucketData(page, size, lobVal, scmDashRequest, boeResponse));
                break;
            case "SCM_LOB":
                lobVal = this.getLoggedInAirtelUserSCMLob(olm, 13, boeResponse);
                scmDashResponse.setScmBucketData(this.getBucketData(page, size, lobVal, scmDashRequest, boeResponse));
                break;
            case "SCM_GVIEW":
                lobVal = this.getLoggedInAirtelUserSCMLob(olm, 14, boeResponse);
                scmDashResponse.setScmBucketData(this.getBucketData(page, size, lobVal, scmDashRequest, boeResponse));
                break;
        }
        boeResponse.setData(scmDashResponse);
        log.info("Exit method :: getScmDashData");
        return boeResponse;
    }


    private String getLoggedInAirtelUserSCMLob(String olm, Integer empRoleId, BoeResponse<ScmDashResponse> boeResponse) {
        log.info("Inside method :: getLoggedInAirtelUserSCMLob");
        try {
            List<BTVL_EMP_ROLE_MAPPING_TBL> btvlEmpRoleMappingTblList = this.btvlEmpRoleMappingTblRepository.findByEmpOlmIdAndEmpRoleIdAllIgnoreCase(olm, empRoleId);

            log.info("Olm id :: " + btvlEmpRoleMappingTblList.get(0).getEmpOlmId() + " Lob's :: " + btvlEmpRoleMappingTblList.get(0).getLob());
            return btvlEmpRoleMappingTblList.get(0).getLob().toUpperCase();

        } catch (Exception e) {
            e.getMessage();
            log.info("Exception raised :: method :: getLoggedInAirtelUserSCMLob");
            this.setErrorObject(boeResponse);
        }
        return null;
    }

    private ScmBucketData getBucketData(int page, int size, String lob, ScmDashRequest scmDashRequest, BoeResponse<ScmDashResponse> boeResponse) {

        ScmBucketData scmBucketData = new ScmBucketData();

        try {
            Pageable pageable = PageRequest.of(page - 1, size);

            Page<ScmDashData> scmDashData = this.btvlWkfBoeShipmentTblRepository.getScmDashData(scmDashRequest.getBucket(), scmDashRequest.getScmDashFilterRequest().getAirtelBoeRefNo(),
                    scmDashRequest.getScmDashFilterRequest().getFromDate(), scmDashRequest.getScmDashFilterRequest().getToDate(),
                    scmDashRequest.getScmDashFilterRequest().getInvoiceNumber(), scmDashRequest.getScmDashFilterRequest().getPartnerName(),
                    scmDashRequest.getScmDashFilterRequest().getBoeNumber(), scmDashRequest.getScmDashFilterRequest().getChaName(),
                    scmDashRequest.getScmDashFilterRequest().getDestinationPort(), lob, scmDashRequest.getScmDashFilterRequest().getOuName(), pageable);


            log.info("Records per page :: " + scmDashData.getNumberOfElements());

            //Passing Lob to count method to get buckets count in case of role is SCM_LOB or SCMGVIEW
            List<ScmDashData> allBucketsCount = this.btvlWkfBoeShipmentTblRepository.getBucketCount(lob);
            Map<String, Integer> statusCount = countBucketData(allBucketsCount);

            //get sum of values of map-> statusCount to display all shipment count.
            scmBucketData.setAllShipmentCount(statusCount.values().stream().mapToInt(Integer::intValue).sum());
            scmBucketData.setDdpShipmentCount(statusCount.getOrDefault("10", 0));
            scmBucketData.setFfAssignmentCount(statusCount.getOrDefault("11", 0));
            scmBucketData.setProtestedBoeApprovalCount(statusCount.getOrDefault("12", 0));
            scmBucketData.setRfiCount(statusCount.getOrDefault("13", 0));
            scmBucketData.setBoeMisMatchCount(statusCount.getOrDefault("14", 0));
            scmBucketData.setBoeRejectedByScmCount(statusCount.getOrDefault("6", 0));

            scmBucketData.setRecordsList(this.getRecords(scmDashData, boeResponse));
        } catch (Exception e) {
            log.info("Exception raised while fetching scm dashboard data :: " + e.getMessage());
            e.printStackTrace();
            this.setErrorObject(boeResponse);
        }
        return scmBucketData;
    }

    private List<ScmDashRecords> getRecords(Page<ScmDashData> scmDashData, BoeResponse<ScmDashResponse> boeResponse) {

        List<ScmDashRecords> recordsList = new ArrayList<>();

        scmDashData.stream().forEach(f -> {

            ScmDashRecords records = new ScmDashRecords();

            records.setOuName(f.getOuName() != null ? f.getOuName() : "");
            records.setAirtelBoeRefNo(f.getAirtelBoeRefNo() != null ? f.getAirtelBoeRefNo() : null);
            records.setBoeNo(f.getBoeNo() != null ? f.getBoeNo() : "");
            records.setBoeDate(f.getBoeDate() != null ? f.getBoeDate() : null);
            records.setAwbBlNo(f.getAwbBlNo() != null ? f.getAwbBlNo() : "");
            records.setPoNo(f.getPoNo() != null ? f.getPoNo() : "");
            records.setInvoiceNo(f.getInvoiceNo() != null ? f.getInvoiceNo() : "");
            records.setPartnerName(f.getPartnerName() != null ? f.getPartnerName() : "");
            records.setIncoTerm(f.getIncoTerm() != null ? f.getIncoTerm() : "");
            records.setModeOfShipment(f.getModeOfShipment() != null ? f.getModeOfShipment() : "");
            records.setShipmentArrivalDate(f.getShipmentArrivalDate() != null ? f.getShipmentArrivalDate() : null);
            records.setDestinationPort(f.getDestinationPort() != null ? f.getDestinationPort() : "");
            records.setChaAgent(f.getChaAgent() != null ? f.getChaAgent() : "");
            records.setStatus(f.getStatus() != null ? f.getStatus() : null);
            records.setStatusDesc(f.getStatusDesc() != null ? f.getStatusDesc() : "");
            records.setPaymentDate(f.getPaymentDate() != null ? f.getPaymentDate() : null);
            records.setBucketNo(f.getBucketNo() != null ? f.getBucketNo() : null);
            records.setCreationDate(f.getCreationDate() != null ? f.getCreationDate() : null);
            records.setOrgId(f.getOrgId() != null ? f.getOrgId() : null);
            records.setLob(f.getLob() != null ? f.getLob() : "");
            records.setAssignedChaId(f.getAssignedChaId() != null ? f.getAssignedChaId() : "");
            records.setDutyAmount(f.getDutyAmount() != null ? f.getDutyAmount() : "");

            recordsList.add(records);

        });
        return recordsList;
    }

    private ScmFilterResponse getFilterResponseData(ScmDashRequest scmDashRequest) {

        ScmFilterResponse scmFilterResponse = new ScmFilterResponse();

        scmFilterResponse.setAirtelBoeRefNo(scmDashRequest.getScmDashFilterRequest().getAirtelBoeRefNo());
        scmFilterResponse.setBoeNumber(scmDashRequest.getScmDashFilterRequest().getBoeNumber());
        scmFilterResponse.setChaName(scmDashRequest.getScmDashFilterRequest().getChaName());
        scmFilterResponse.setDestinationPort(scmDashRequest.getScmDashFilterRequest().getDestinationPort());
        scmFilterResponse.setInvoiceNumber(scmDashRequest.getScmDashFilterRequest().getInvoiceNumber());
        scmFilterResponse.setPartnerName(scmDashRequest.getScmDashFilterRequest().getPartnerName());
        scmFilterResponse.setFromDate(scmDashRequest.getScmDashFilterRequest().getFromDate());
        scmFilterResponse.setToDate(scmDashRequest.getScmDashFilterRequest().getToDate());

        return scmFilterResponse;
    }

    //method to count all the buckets data
    public Map<String, Integer> countBucketData(List<ScmDashData> records) {
        log.info("Inside countBucketData method");
        Map<String, Integer> statusCount = new HashMap<>();
        for (ScmDashData record : records) {
            String status = String.valueOf(record.getBucketNo());
            statusCount.put(status, statusCount.getOrDefault(status, 0) + 1);
        }
        log.info("Exiting method countBucketData :: total status count in table :: " + statusCount);
        return statusCount;
    }

    @Override
    public BoeResponse<ScmDashResponse> getScmDashExcelData(int page, int size, String olm, String role, ScmDashRequest scmDashRequest, HttpServletResponse httpServletResponse) {

        log.info("Inside ScmDashboardServiceImpl :: method :: getScmDashExcelData");
        BoeResponse<ScmDashResponse> boeResponse = new BoeResponse<>();
        ScmDashResponse scmDashResponse = new ScmDashResponse();

        String lobVal = null;
        if (role.equalsIgnoreCase("SCM_ADMIN") && !StringUtils.isBlank(role)) {

            scmDashResponse.setScmBucketData(this.getBucketData(page, size, lobVal, scmDashRequest, boeResponse));

        } else if (role.equalsIgnoreCase("SCM_LOB") && !StringUtils.isBlank(role)) {

            lobVal = this.getLoggedInAirtelUserSCMLob(olm, 13, boeResponse);
            scmDashResponse.setScmBucketData(this.getBucketData(page, size, lobVal, scmDashRequest, boeResponse));

        } else if (role.equalsIgnoreCase("SCM_GVIEW") && !StringUtils.isBlank(role)) {

            lobVal = this.getLoggedInAirtelUserSCMLob(olm, 14, boeResponse);
            scmDashResponse.setScmBucketData(this.getBucketData(page, size, lobVal, scmDashRequest, boeResponse));
        }

        String excelName = getExcelName(scmDashRequest);
        List excelData = scmDashResponse.getScmBucketData().getRecordsList();

        List<String> headerTextList = this.getScmDashExcelHeaders();
        List<Map<String, String>> rowMapList = this.getScmDashExcelRowsData(excelData);


        String excelCheck = ExcelUtil.downloadExcel(headerTextList, rowMapList, excelName, httpServletResponse, this.uploadTmpDir);

        if (excelCheck.equalsIgnoreCase("y")) {
            log.info("ScmDashboard Excel Generated successfully");
        } else {
            throw new BoeException("Something went wrong", boeResponse, HttpStatus.OK);
        }
        log.info("Exit method :: getScmDashExcelData");
        return boeResponse;
    }


    @Override
    public BoeResponse<ScmDashResponse> getScmDashRawExcelData(int page, int size, String olm, String role, ScmDashRequest scmDashRequest, HttpServletResponse httpServletResponse) {

        log.info("Inside ScmDashboardServiceImpl :: method :: getScmDashRawExcelData");
        BoeResponse<ScmDashResponse> boeResponse = new BoeResponse<>();
        ScmDashResponse scmDashResponse = new ScmDashResponse();

        String lobVal = null;
        if (role.equalsIgnoreCase("SCM_ADMIN") && !StringUtils.isBlank(role)) {

            scmDashResponse.setScmBucketData(this.getBucketData(page, size, lobVal, scmDashRequest, boeResponse));

        } else if (role.equalsIgnoreCase("SCM_LOB") && !StringUtils.isBlank(role)) {

            lobVal = this.getLoggedInAirtelUserSCMLob(olm, 13, boeResponse);
            scmDashResponse.setScmBucketData(this.getBucketData(page, size, lobVal, scmDashRequest, boeResponse));

        } else if (role.equalsIgnoreCase("SCM_GVIEW") && !StringUtils.isBlank(role)) {

            lobVal = this.getLoggedInAirtelUserSCMLob(olm, 14, boeResponse);
            scmDashResponse.setScmBucketData(this.getBucketData(page, size, lobVal, scmDashRequest, boeResponse));
        }

        String excelName = getExcelName(scmDashRequest);
        List excelData = scmDashResponse.getScmBucketData().getRecordsList();

        List<String> headerTextList = this.getScmDashExcelHeaders();
        List<Map<String, String>> rowMapList = this.getScmDashExcelRowsData(excelData);


        String excelCheck = ExcelUtil.downloadExcel(headerTextList, rowMapList, excelName, httpServletResponse, this.uploadTmpDir);

        if (excelCheck.equalsIgnoreCase("y")) {
            log.info("Raw data Excel Generated successfully");
        } else {
            throw new BoeException("Something went wrong", boeResponse, HttpStatus.OK);
        }
        log.info("Exit method :: getScmDashExcelData");
        return boeResponse;
    }


    private String getExcelName(ScmDashRequest scmDashRequest) {

        if (scmDashRequest.getBucket() == null) {
            return "AllShipmentDetails";
        } else if (scmDashRequest.getBucket().equals(new BigDecimal(10))) {
            return "DDpShipmentDetails";
        } else if (scmDashRequest.getBucket().equals(new BigDecimal(11))) {
            return "FFShipmentDetails";
        } else if (scmDashRequest.getBucket().equals(new BigDecimal(12))) {
            return "ProtestedBoeDetails";
        } else if (scmDashRequest.getBucket().equals(new BigDecimal(13))) {
            return "RfiDetails";
        } else if (scmDashRequest.getBucket().equals(new BigDecimal(14))) {
            return "BoeMismatchDetails";
        } else if (scmDashRequest.getBucket().equals(new BigDecimal(6))) {
            return "ScmRejectedBoe";
        }
        return null;
    }

    private List<String> getScmDashExcelHeaders() {

        List<String> headerTextList = new ArrayList<>();

        headerTextList.add("OU NAME");
        headerTextList.add("AIRTEL REFERENCE NO.");
        headerTextList.add("BOE NO.");
        headerTextList.add("BOE DATE");
        headerTextList.add("AWB / BL NO.");
        headerTextList.add("PO NO.");
        headerTextList.add("INVOICE NO.");
        headerTextList.add("PARTNER NAME");
        headerTextList.add("INCO TERM");
        headerTextList.add("MODE OF SHIPMENT");
        headerTextList.add("SHIPMENT ARRIVAL DATE");
        headerTextList.add("DESTINATION PORT");
        headerTextList.add("CHA AGENT");
        headerTextList.add("STATUS");
        headerTextList.add("PAYMENT DATE");

        return headerTextList;
    }

    private List<Map<String, String>> getScmDashExcelRowsData(List<ScmDashRecords> excelData) {

        List<Map<String, String>> rowMapList = new ArrayList<>();

        for (ScmDashRecords row : excelData) {
            Map<String, String> map = new LinkedHashMap<>();

            map.put("OU NAME", row.getOuName());
            map.put("AIRTEL REFERENCE NO.", row.getAirtelBoeRefNo() != null ? row.getAirtelBoeRefNo().toString() : null);
            map.put("BOE NO.", row.getBoeNo() != null ? row.getBoeNo() : "");
            map.put("BOE DATE", row.getBoeDate() != null ? row.getBoeDate().toString() : null);
            map.put("AWB / BL NO.", row.getAwbBlNo() != null ? row.getAwbBlNo() : "");
            map.put("PO NO.", row.getPoNo() != null ? row.getPoNo() : "");
            map.put("INVOICE NO.", row.getInvoiceNo() != null ? row.getInvoiceNo() : "");
            map.put("PARTNER NAME", row.getPartnerName() != null ? row.getPartnerName() : "");
            map.put("INCO TERM", row.getIncoTerm() != null ? row.getIncoTerm() : "");
            map.put("MODE OF SHIPMENT", row.getModeOfShipment() != null ? row.getModeOfShipment() : "");
            map.put("SHIPMENT ARRIVAL DATE",
                    row.getShipmentArrivalDate() != null ? row.getShipmentArrivalDate().toString() : null);
            map.put("DESTINATION PORT", row.getDestinationPort() != null ? row.getDestinationPort() : "");
            map.put("CHA AGENT", row.getChaAgent() != null ? row.getChaAgent() : "");
            map.put("STATUS", row.getStatusDesc() != null ? row.getStatusDesc() : "");
            map.put("PAYMENT DATE", row.getPaymentDate() != null ? row.getPaymentDate().toString() : null);

            rowMapList.add(map);
        }
        return rowMapList;
    }

    private void setErrorObject(BoeResponse<ScmDashResponse> boeResponse) {
        Error error = new Error();
        error.setCode(CommonConstants.SCM_DASHBOARD_ERROR_CODE);
        error.setTraceId(BoeUtils.generateTraceId(appNode));
        boeResponse.setError(error);
    }
}
