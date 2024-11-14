package com.airtel.buyer.airtelboe.service.chamaster;

import com.airtel.buyer.airtelboe.dto.common.BoeResponse;
import com.airtel.buyer.airtelboe.dto.fetchchamaster.response.FetchChaMasterData;
import com.airtel.buyer.airtelboe.dto.fetchiecmaster.response.FetchIecMasterData;
import com.airtel.buyer.airtelboe.exception.BoeException;
import com.airtel.buyer.airtelboe.model.supplier.BTVL_MST_BOE_CHA_TBL;
import com.airtel.buyer.airtelboe.repository.supplier.BtvlMstBoeChaTblRepository;
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
public class ChaMasterServiceImpl implements ChaMasterService {

    @Autowired
    private BtvlMstBoeChaTblRepository btvlMstBoeChaTblRepository;

    @Autowired
    private HttpServletResponse httpServletResponse;

    @Value("UPLOAD_TMP_DIRECTORY")
    private String uploadTmpDir;

    @Override
    public BoeResponse<List<FetchChaMasterData>> chaMasterInformation() {

        BoeResponse<List<FetchChaMasterData>> boeResponse = new BoeResponse<>();
        boeResponse.setData(this.getFetchChaMasterDataListObject());

        return boeResponse;
    }

    private List<FetchChaMasterData> getFetchChaMasterDataListObject() {

        List<FetchChaMasterData> fetchChaMasterDataList = null;

        List<BTVL_MST_BOE_CHA_TBL> chaList =
                this.btvlMstBoeChaTblRepository.findByPurgeFlag(0);

        if (chaList != null && !chaList.isEmpty()) {
            fetchChaMasterDataList = chaList.stream().map(this::getFetchChaMasterDataObject).collect(Collectors.toList());
        }

        return fetchChaMasterDataList;
    }

    private FetchChaMasterData getFetchChaMasterDataObject(BTVL_MST_BOE_CHA_TBL bTVL_MST_BOE_CHA_TBL) {

        FetchChaMasterData fetchChaMasterData = new FetchChaMasterData();
        fetchChaMasterData.setLegalEntity(bTVL_MST_BOE_CHA_TBL.getLeName());
        fetchChaMasterData.setLob(bTVL_MST_BOE_CHA_TBL.getLob());
        fetchChaMasterData.setOuName(bTVL_MST_BOE_CHA_TBL.getOu());
        fetchChaMasterData.setPortCode(bTVL_MST_BOE_CHA_TBL.getPortCode());
        fetchChaMasterData.setPortDescription(bTVL_MST_BOE_CHA_TBL.getPortDescription());
        fetchChaMasterData.setChaName(bTVL_MST_BOE_CHA_TBL.getChaName());
        fetchChaMasterData.setChaCode(bTVL_MST_BOE_CHA_TBL.getChaCode());
        fetchChaMasterData.setChaEmail(bTVL_MST_BOE_CHA_TBL.getChaEmail());
        fetchChaMasterData.setChaPhoneNo(bTVL_MST_BOE_CHA_TBL.getChaPhoneNo());

        return fetchChaMasterData;
    }

    @Override
    public void chaMasterExcel() {

        List<FetchChaMasterData> fetchChaMasterDataList = this.getFetchChaMasterDataListObject();

        String excelName = "CHAMasterDetails";

        List<String> headerTextList = this.getExcelHeaders();
        List<Map<String, String>> rowMapList = this.getExcelRowsData(fetchChaMasterDataList);

        String excelCheck = ExcelUtil.downloadExcel(headerTextList, rowMapList, excelName, httpServletResponse, this.uploadTmpDir);

        if (excelCheck.equalsIgnoreCase("y")) {
            log.info("CHA Master Excel Generated successfully");
        } else {
            throw new BoeException("Something went wrong", null, HttpStatus.OK);
        }

    }

    private List<String> getExcelHeaders() {

        List<String> headerTextList = new ArrayList<>();
        headerTextList.add("LEGAL ENTITY NAME");
        headerTextList.add("LOB");
        headerTextList.add("CIRCLE NAME/OU NAME");
        headerTextList.add("PORT CODE");
        headerTextList.add("PORT DESCRIPTION");
        headerTextList.add("CHA NAME");
        headerTextList.add("CHA CODE");
        headerTextList.add("CHA EMAIL");
        headerTextList.add("CHA PHONE NO.");

        return headerTextList;
    }

    private List<Map<String, String>> getExcelRowsData(List<FetchChaMasterData> excelData) {

        List<Map<String, String>> rowMapList = new ArrayList<>();

        for (FetchChaMasterData row : excelData) {
            Map<String, String> map = new LinkedHashMap<>();

            map.put("LEGAL ENTITY NAME", row.getLegalEntity());
            map.put("LOB", row.getLob());
            map.put("CIRCLE NAME/OU NAME", row.getOuName());
            map.put("PORT CODE", row.getPortCode());
            map.put("PORT DESCRIPTION", row.getPortDescription());
            map.put("CHA NAME", row.getChaName());
            map.put("CHA CODE", row.getChaCode());
            map.put("CHA EMAIL", row.getChaEmail());
            map.put("CHA PHONE NO.", row.getChaPhoneNo());

            rowMapList.add(map);
        }

        return rowMapList;
    }

}
