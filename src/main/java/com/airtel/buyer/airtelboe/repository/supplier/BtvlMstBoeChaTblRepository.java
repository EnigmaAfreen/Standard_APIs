package com.airtel.buyer.airtelboe.repository.supplier;

import com.airtel.buyer.airtelboe.model.supplier.BTVL_MST_BOE_CHA_TBL;
import com.airtel.buyer.airtelboe.repository.ChaAgents;
import com.airtel.buyer.airtelboe.repository.ChaAssignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;

public interface BtvlMstBoeChaTblRepository extends JpaRepository<BTVL_MST_BOE_CHA_TBL, BigDecimal> {


    @Query(value = "select distinct cha_code as chaCode , cha_name as chaName from btvl_mst_boe_cha_tbl", nativeQuery = true)
    List<ChaAgents> fetchChaAgentList();


    List<BTVL_MST_BOE_CHA_TBL> findByChaCode(String chaCode);

    @Query(value = "SELECT         \n" +
            "    t1.cha_id as chaId,         \n" +
            "    t1.le_name as leName,         \n" +
            "    t1.ou as ou,         \n" +
            "    t1.port_code as portCode,         \n" +
            "    t1.cha_code as chaCode,        \n" +
            "    t1.CHA_EMAIL as chaEmail,        \n" +
            "    NVL(COUNT(t2.assigned_cha_id), 0) AS assignedTasks      \n" +
            "FROM         \n" +
            "    btvl_mst_boe_cha_tbl t1         \n" +
            "    LEFT OUTER JOIN btvl_wkf_boe_shipment_tbl t2 \n" +
            "        ON t1.cha_code = t2.assigned_cha_id \n" +
            "        AND t1.le_name = t2.le_name \n" +
            "        AND t1.ou = t2.ou_name \n" +
            "        AND t1.port_code = t2.destination_port    \n" +
            "        AND t2.assigned_cha_id IS NOT NULL \n" +
            "        AND t2.assigned_cha_id <> 'Assignment Failed'    \n" +
            "        AND t2.status < 11    \n" +
            "        where t1.le_name =:leName\n" +
            "        and t1.ou =:ou\n" +
            "        and t1.port_code =:portCode\n" +
            "GROUP BY         \n" +
            "    t1.cha_id,         \n" +
            "    t1.le_name,         \n" +
            "    t1.ou,         \n" +
            "    t1.port_code,         \n" +
            "    t1.cha_code,        \n" +
            "    t1.CHA_EMAIL\n" +
            "ORDER BY \n" +
            "    assignedTasks ASC, \n" +
            "    t1.cha_id ASC", nativeQuery = true)
    ChaAssignment findChaAgentsByLeOuPortCode(String leName,String ou,String portCode);

    List<BTVL_MST_BOE_CHA_TBL> findByPurgeFlag(Integer purgeFlag);

}
