package com.airtel.buyer.airtelboe.service.portmaster;

import com.airtel.buyer.airtelboe.dto.common.BoeResponse;
import com.airtel.buyer.airtelboe.dto.fetchportmaster.response.FetchPortMasterData;
import com.airtel.buyer.airtelboe.dto.port.response.PortData;
import com.airtel.buyer.airtelboe.exception.BoeException;
import com.airtel.buyer.airtelboe.model.supplier.BTVL_MST_BOE_PORT_TBL;
import com.airtel.buyer.airtelboe.repository.supplier.BtvlMstBoePortTblRepository;
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
public class PortMasterServiceImpl implements PortMasterService {

    @Autowired
    private BtvlMstBoePortTblRepository btvlMstBoePortTblRepository;

    @Autowired
    private HttpServletResponse httpServletResponse;

    @Value("UPLOAD_TMP_DIRECTORY")
    private String uploadTmpDir;

    @Override
    public BoeResponse<List<FetchPortMasterData>> portMasterInformation() {

        BoeResponse<List<FetchPortMasterData>> boeResponse = new BoeResponse<>();
        boeResponse.setData(this.getFetchPortMasterDataListObject());

        return boeResponse;
    }

    private List<FetchPortMasterData> getFetchPortMasterDataListObject() {

        List<FetchPortMasterData> fetchPortMasterDataList = null;

        List<BTVL_MST_BOE_PORT_TBL> portList = this.btvlMstBoePortTblRepository.findByPurgeFlag(0);

        if (portList != null && !portList.isEmpty()) {
            fetchPortMasterDataList = portList.stream().map(this::getFetchPortMasterDataObject).collect(Collectors.toList());
        }

        return fetchPortMasterDataList;
    }

    private FetchPortMasterData getFetchPortMasterDataObject(BTVL_MST_BOE_PORT_TBL bTVL_MST_BOE_PORT_TBL) {

        FetchPortMasterData fetchPortMasterData = new FetchPortMasterData();
        fetchPortMasterData.setPortCode(bTVL_MST_BOE_PORT_TBL.getPortCode());
        fetchPortMasterData.setPortName(bTVL_MST_BOE_PORT_TBL.getPortName());
        fetchPortMasterData.setPortType(bTVL_MST_BOE_PORT_TBL.getPortType());
        fetchPortMasterData.setCountry(bTVL_MST_BOE_PORT_TBL.getCountry());
        fetchPortMasterData.setCountryCode(bTVL_MST_BOE_PORT_TBL.getCountryCode());

        return fetchPortMasterData;
    }

    @Override
    public BoeResponse<List<PortData>> portList() {

        BoeResponse<List<PortData>> boeResponse = new BoeResponse<>();
        boeResponse.setData(this.getPortDataListObject());

        return boeResponse;
    }

    private List<PortData> getPortDataListObject() {

        List<PortData> portDataList = null;

        List<BTVL_MST_BOE_PORT_TBL> portList = this.btvlMstBoePortTblRepository.findByPurgeFlag(0);

        if (portList != null && !portList.isEmpty()) {
            portDataList = portList.stream().map(this::getPortDataObject).collect(Collectors.toList());
        }

        return portDataList;
    }

    private PortData getPortDataObject(BTVL_MST_BOE_PORT_TBL bTVL_MST_BOE_PORT_TBL) {

        PortData portData = new PortData();
        portData.setPortCode(bTVL_MST_BOE_PORT_TBL.getPortCode());
        portData.setPortName(bTVL_MST_BOE_PORT_TBL.getPortName());
        portData.setPortType(bTVL_MST_BOE_PORT_TBL.getPortType());
        return portData;
    }

    @Override
    public void portMasterExcel() {

        List<FetchPortMasterData> fetchPortMasterDataList = this.getFetchPortMasterDataListObject();

        String excelName = "PortMasterDetails";

        List<String> headerTextList = this.getExcelHeaders();
        List<Map<String, String>> rowMapList = this.getExcelRowsData(fetchPortMasterDataList);

        String excelCheck = ExcelUtil.downloadExcel(headerTextList, rowMapList, excelName, httpServletResponse, this.uploadTmpDir);

        if (excelCheck.equalsIgnoreCase("y")) {
            log.info("Port Master Excel Generated successfully");
        } else {
            throw new BoeException("Something went wrong", null, HttpStatus.OK);
        }

    }

    private List<String> getExcelHeaders() {

        List<String> headerTextList = new ArrayList<>();
        headerTextList.add("PORT CODE");
        headerTextList.add("PORT NAME");
        headerTextList.add("PORT TYPE");
        headerTextList.add("COUNTRY");
        headerTextList.add("COUNTRY CODE");

        return headerTextList;
    }

    private List<Map<String, String>> getExcelRowsData(List<FetchPortMasterData> excelData) {

        List<Map<String, String>> rowMapList = new ArrayList<>();

        for (FetchPortMasterData row : excelData) {
            Map<String, String> map = new LinkedHashMap<>();

            map.put("PORT CODE", row.getPortCode());
            map.put("PORT NAME", row.getPortName());
            map.put("PORT TYPE", row.getPortType());
            map.put("COUNTRY", row.getCountry());
            map.put("COUNTRY CODE", row.getCountryCode());

            rowMapList.add(map);
        }

        return rowMapList;
    }

    @Override
    public BoeResponse<List<PortData>> indianPortList() {

        BoeResponse<List<PortData>> boeResponse = new BoeResponse<>();
        boeResponse.setData(this.getIndianPortDataListObject());

        return boeResponse;
    }

    private List<PortData> getIndianPortDataListObject() {

        List<PortData> portDataList = null;

        List<BTVL_MST_BOE_PORT_TBL> portList = this.btvlMstBoePortTblRepository.findByCountryCodeAndPurgeFlagAllIgnoreCase("IN", 0);

        if (portList != null && !portList.isEmpty()) {
            portDataList = portList.stream().map(this::getPortDataObject).collect(Collectors.toList());
        }

        return portDataList;
    }

}
