package com.airtel.buyer.airtelboe.service.shipmentstatusreport;

import com.airtel.buyer.airtelboe.dto.common.BoeResponse;
import com.airtel.buyer.airtelboe.dto.common.Error;
import com.airtel.buyer.airtelboe.dto.shipmentstatusreport.request.ShipmentStatusReportRequest;
import com.airtel.buyer.airtelboe.dto.shipmentstatusreport.response.ShipmentStatusReportResponse;
import com.airtel.buyer.airtelboe.repository.supplier.SupplierDaoRepository;
import com.airtel.buyer.airtelboe.util.BoeUtils;
import com.airtel.buyer.airtelboe.util.CommonConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class ShipmentStatusReportServiceImpl implements ShipmentStatusReportService {
    @Value("${app.node}")
    public String appNode;

    @Autowired
    public SupplierDaoRepository supplierDaoRepository;

    @Override
    public BoeResponse<ShipmentStatusReportResponse> shipmentStausReport(ShipmentStatusReportRequest shipmentStatusReportRequest, String email) {
        log.info("Inside ShipmentStatusReportServiceImpl :: method :: shipmentStausReport");
        BoeResponse<ShipmentStatusReportResponse> boeResponse = new BoeResponse<>();
        ShipmentStatusReportResponse shipmentStatusReportResponse = new ShipmentStatusReportResponse();

        boeResponse.setData(shipmentStatusReportResponse);
        if (checkMandatoryFields(shipmentStatusReportRequest, boeResponse)) {
            if (Boolean.TRUE.equals(sendReport(shipmentStatusReportRequest, boeResponse, email))) {
                shipmentStatusReportResponse.setStatus("Success");
            } else {
                shipmentStatusReportResponse.setStatus("Failed");
            }
        }
        return boeResponse;
    }

    private boolean checkMandatoryFields(ShipmentStatusReportRequest shipmentStatusReportRequest, BoeResponse<ShipmentStatusReportResponse> boeResponse) {

        log.info("Inside class :: ShipmentStatusReportServiceImpl :: method :: checkMandatoryFields");
        Boolean status = Boolean.TRUE;
        log.info("Checking Date");//Only date is mandatory in shipment status report
        log.info("From Date :: " + shipmentStatusReportRequest.getFromDate());
        log.info("To Date :: " + shipmentStatusReportRequest.getToDate());

        ShipmentStatusReportResponse shipmentStatusReportResponse = boeResponse.getData();

        if (shipmentStatusReportRequest.getFromDate() == null || shipmentStatusReportRequest.getToDate() == null) {
            status = Boolean.FALSE;
            this.setErrorObject(boeResponse);
            shipmentStatusReportResponse.setErrMsg("From date or To date is missing");

        } else if (shipmentStatusReportRequest.getFromDate().isAfter(shipmentStatusReportRequest.getToDate())) {
            status = Boolean.FALSE;
            this.setErrorObject(boeResponse);
            shipmentStatusReportResponse.setErrMsg("From date should be less than to date");

        } else if (shipmentStatusReportRequest.getToDate().isAfter(LocalDate.now())) {
            status = Boolean.FALSE;
            this.setErrorObject(boeResponse);
            shipmentStatusReportResponse.setErrMsg("To Date can not be Future Date.");

        } else {
            long diffDays = ChronoUnit.DAYS.between(shipmentStatusReportRequest.getFromDate(), shipmentStatusReportRequest.getToDate());
            log.info("Diff of From Date and To Date is :: " + diffDays);
            if (diffDays > 180) {
                status = Boolean.FALSE;
                this.setErrorObject(boeResponse);
                shipmentStatusReportResponse.setErrMsg("Date range should be less than 6 months");
            }
        }
        return status;
    }

    private Boolean sendReport(ShipmentStatusReportRequest shipmentStatusReportRequest, BoeResponse<ShipmentStatusReportResponse> boeResponse, String email) {
        log.info("Inside ShipmentStatusReportServiceImpl :: method :: sendReport");
        Map<String, String> map = new HashMap<>();

        try {
            //Converting fromDate and toDate to Timestamp from LocalDate

            LocalDateTime fromDateTime = shipmentStatusReportRequest.getFromDate().atStartOfDay();
            LocalDateTime toDateTime = shipmentStatusReportRequest.getToDate().atStartOfDay();

            Timestamp fromtimestamp = Timestamp.valueOf(fromDateTime);
            Timestamp totimestamp = Timestamp.valueOf(toDateTime);

            map = supplierDaoRepository.callShipmentStatusReportPrc(shipmentStatusReportRequest.getLob(),
                    shipmentStatusReportRequest.getOu(), fromtimestamp, totimestamp,
                    shipmentStatusReportRequest.getChaCode(), shipmentStatusReportRequest.getBoeNo(),
                    shipmentStatusReportRequest.getAirtelRefNo(), shipmentStatusReportRequest.getPortCode(), email);

            if (!StringUtils.isBlank(map.get("errorMsg")) && "error".equalsIgnoreCase(map.get("status"))) {
                ShipmentStatusReportResponse shipmentStatusReportResponse = boeResponse.getData();
                shipmentStatusReportResponse.setErrMsg(map.get("errorMsg"));
                return Boolean.FALSE;
            } else {
                return Boolean.TRUE;
            }
        } catch (Exception e) {
            this.setErrorObject(boeResponse);
            log.info("Exception raised inside method :: sendReport :: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    private void setErrorObject(BoeResponse<ShipmentStatusReportResponse> reportResponse) {
        Error error = new Error();
        error.setCode(CommonConstants.SHIPMENT_STATUS_REPORT_ERROR_CODE);
        error.setTraceId(BoeUtils.generateTraceId(appNode));
        reportResponse.setError(error);
    }
}
