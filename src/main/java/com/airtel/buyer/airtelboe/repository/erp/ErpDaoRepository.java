package com.airtel.buyer.airtelboe.repository.erp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Repository
public class ErpDaoRepository {

    @Autowired
    @Qualifier("erpDataSource")
    private DataSource dataSource;

    @Value("${SQL_QUERY_TIMEOUT}")
    private String sqlQueryTimeOut;

    public Map<String, String> getCco(String ou) {
        log.info("Entering method :: getCco");

        Map<String, String> map = new HashMap<>();

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

        Connection dbConnection = null;
        CallableStatement callableStatement = null;

        String getDBUSERByUserIdSql = "{call btvl_boe_cco_prc(?,?,?,?)}";

        try {
            LocalDateTime now = LocalDateTime.now();
            log.info("Time before fetching connection from datasource:::" + dtf.format(now));

            dbConnection = this.dataSource.getConnection();

            now = LocalDateTime.now();
            log.info("Time after fetching connection from datasource:::" + dtf.format(now));

            callableStatement = dbConnection.prepareCall(getDBUSERByUserIdSql);

            callableStatement.setObject(1, ou);
            callableStatement.registerOutParameter(2, Types.VARCHAR);
            callableStatement.registerOutParameter(3, Types.VARCHAR);
            callableStatement.registerOutParameter(4, Types.VARCHAR);

            now = LocalDateTime.now();
            log.info("Time before executing procedure:::" + dtf.format(now));

            callableStatement.execute();

            now = LocalDateTime.now();

            log.info("Time after executing procedure:::" + dtf.format(now));

            log.info("olmId :: " + callableStatement.getObject(2));
            log.info("emailId :: " + callableStatement.getObject(3));
            log.info("error :: " + callableStatement.getObject(4));

            map.put("olmId", (String) callableStatement.getObject(2));
            map.put("emailId", (String) callableStatement.getObject(3));
            map.put("error", (String) callableStatement.getObject(4));

            //            map.put("olmId", "A1BO15X0");
            //            map.put("emailId", "a_gaurav.aggarwal@airtel.com");

        } catch (Exception e) {
            log.info("Exception raised :: " + e.getMessage());
            e.printStackTrace();
        }
        log.info("Exiting method :: getCco");
        return map;
    }

    public String callIpApprovalPrc(BigDecimal shipmentId) {
        log.info("Entering method :: callIpApprovalPrc with ShipmentId : " + shipmentId);

        String status = "";

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

        Connection dbConnection = null;
        CallableStatement callableStatement = null;

        String getDBUSERByUserIdSql = "{call btvl_sportal_boe_apprve_ip_pkg. btvl_boe_main_prc  (?, ?, ?)}";

        try {
            LocalDateTime now = LocalDateTime.now();
            log.info("Time before fetching connection from datasource:::" + dtf.format(now));

            dbConnection = this.dataSource.getConnection();

            now = LocalDateTime.now();
            log.info("Time after fetching connection from datasource:::" + dtf.format(now));

            callableStatement = dbConnection.prepareCall(getDBUSERByUserIdSql);

            callableStatement.setObject(1, shipmentId);
            callableStatement.setObject(2, "N");
            callableStatement.registerOutParameter(3, Types.VARCHAR);

            now = LocalDateTime.now();
            log.info("Time before executing procedure:::" + dtf.format(now));

            callableStatement.execute();

            now = LocalDateTime.now();

            log.info("Time after executing procedure:::" + dtf.format(now));

            log.info("Status :: " + callableStatement.getObject(3));

            status = (String) callableStatement.getObject(3);

        } catch (Exception e) {
            log.info("Exception raised ::: " + e.getMessage(), e);
        }

        log.info("Exiting method :: callIpApprovalPrc");
        return status;
    }

}
