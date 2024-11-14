package com.airtel.buyer.airtelboe.repository.supplier;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Repository
public class SupplierDaoRepository {

    @Autowired
    @Qualifier("supplierDataSource")
    private DataSource dataSource;

    @Value("${SQL_QUERY_TIMEOUT}")
    private String sqlQueryTimeOut;

    public  Map<String, String> callShipmentStatusReportPrc(String lob, BigDecimal orgId, Timestamp fromDate,
                                                                  Timestamp toDate, String chaCode, String boeNo,
                                                                  BigDecimal airtelRefNumber, String portCode, String loggedInUserEmail) {
        log.info("Entering method :: callShipmentStatusReportPrc");

        Map<String, String> map = new HashMap<>();

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

        Connection dbConnection = null;
        CallableStatement callableStatement = null;

        String getDBUSERByUserIdSql =
                "{call BTVL_BOE_SHPMNT_STATUS_PKG.BTVL_MAIN_BOE_PRC (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";

        try {
            LocalDateTime now = LocalDateTime.now();
            log.info("Time before fetching connection from datasource:::" + dtf.format(now));

            dbConnection = this.dataSource.getConnection();

            now = LocalDateTime.now();
            log.info("Time after fetching connection from datasource:::" + dtf.format(now));

            callableStatement = dbConnection.prepareCall(getDBUSERByUserIdSql);

            callableStatement.setObject(1, "SCM");
            callableStatement.setObject(2, lob);
            callableStatement.setObject(3, null); //leName
            callableStatement.setObject(4, orgId);
            callableStatement.setObject(5, chaCode);
            callableStatement.setObject(6, fromDate);
            callableStatement.setObject(7, toDate);
            callableStatement.setObject(8, boeNo);
            callableStatement.setObject(9, airtelRefNumber); //shipmentId
            callableStatement.setObject(10, portCode); //destinationPort
            callableStatement.setObject(11, loggedInUserEmail); //email
            callableStatement.registerOutParameter(12, Types.VARCHAR); //status
            callableStatement.registerOutParameter(13, Types.VARCHAR); //errorMsg

            now = LocalDateTime.now();
            log.info("Time before executing procedure:::" + dtf.format(now));

            callableStatement.execute();

            now = LocalDateTime.now();

            log.info("Time after executing procedure:::" + dtf.format(now));

            log.info("Status :: " + callableStatement.getObject(12));
            log.info("Error Msg :: " + callableStatement.getObject(13));

            map.put("status", (String) callableStatement.getObject(12));
            map.put("errorMsg", (String) callableStatement.getObject(13));

        } catch (Exception e) {
            log.info("Exception raised :: " + e.getMessage());
            e.printStackTrace();
        }

        log.info("Exiting method :: callShipmentStatusReportPrc");
        return map;
    }
}
