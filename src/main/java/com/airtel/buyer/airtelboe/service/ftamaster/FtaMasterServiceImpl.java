package com.airtel.buyer.airtelboe.service.ftamaster;

import com.airtel.buyer.airtelboe.dto.common.BoeResponse;
import com.airtel.buyer.airtelboe.dto.fetchftamaster.response.FetchFtaMasterData;
import com.airtel.buyer.airtelboe.exception.BoeException;
import com.airtel.buyer.airtelboe.model.supplier.BTVL_MST_BOE_FTA_TBL;
import com.airtel.buyer.airtelboe.repository.supplier.BtvlMstBoeFtaTblRepository;
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
public class FtaMasterServiceImpl implements FtaMasterService {

    @Autowired
    private BtvlMstBoeFtaTblRepository btvlMstBoeFtaTblRepository;

    @Autowired
    private HttpServletResponse httpServletResponse;

    @Value("UPLOAD_TMP_DIRECTORY")
    private String uploadTmpDir;

    @Override
    public BoeResponse<List<FetchFtaMasterData>> ftaMasterInformation() {

        BoeResponse<List<FetchFtaMasterData>> boeResponse = new BoeResponse<>();
        boeResponse.setData(this.getFetchFtaMasterDataListObject());

        return boeResponse;
    }

    private List<FetchFtaMasterData> getFetchFtaMasterDataListObject() {

        List<FetchFtaMasterData> fetchFtaMasterDataList = null;

        List<BTVL_MST_BOE_FTA_TBL> ftaList =
                this.btvlMstBoeFtaTblRepository.findByPurgeFlag(0);

        if (ftaList != null && !ftaList.isEmpty()) {
            fetchFtaMasterDataList = ftaList.stream().map(this::getFetchFtaMasterDataObject).collect(Collectors.toList());
        }

        return fetchFtaMasterDataList;
    }

    private FetchFtaMasterData getFetchFtaMasterDataObject(BTVL_MST_BOE_FTA_TBL bTVL_MST_BOE_FTA_TBL) {

        FetchFtaMasterData fetchFtaMasterData = new FetchFtaMasterData();
        fetchFtaMasterData.setCountry(bTVL_MST_BOE_FTA_TBL.getCountry());
        fetchFtaMasterData.setCountryCode(bTVL_MST_BOE_FTA_TBL.getCountryCode());

        return fetchFtaMasterData;
    }

    @Override
    public void ftaMasterExcel() {

        List<FetchFtaMasterData> fetchFtaMasterDataList = this.getFetchFtaMasterDataListObject();

        String excelName = "FTAMasterDetails";

        List<String> headerTextList = this.getExcelHeaders();
        List<Map<String, String>> rowMapList = this.getExcelRowsData(fetchFtaMasterDataList);

        String excelCheck = ExcelUtil.downloadExcel(headerTextList, rowMapList, excelName, httpServletResponse, this.uploadTmpDir);

        if (excelCheck.equalsIgnoreCase("y")) {
            log.info("FTA Master Excel Generated successfully");
        } else {
            throw new BoeException("Something went wrong", null, HttpStatus.OK);
        }

    }

    private List<String> getExcelHeaders() {

        List<String> headerTextList = new ArrayList<>();
        headerTextList.add("COUNTRY");
        headerTextList.add("COUNTRY CODE");

        return headerTextList;
    }

    private List<Map<String, String>> getExcelRowsData(List<FetchFtaMasterData> excelData) {

        List<Map<String, String>> rowMapList = new ArrayList<>();

        for (FetchFtaMasterData row : excelData) {
            Map<String, String> map = new LinkedHashMap<>();

            map.put("COUNTRY", row.getCountry());
            map.put("COUNTRY CODE", row.getCountryCode());

            rowMapList.add(map);
        }

        return rowMapList;
    }

}
