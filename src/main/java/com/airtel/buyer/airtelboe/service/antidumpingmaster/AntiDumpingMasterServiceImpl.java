package com.airtel.buyer.airtelboe.service.antidumpingmaster;

import com.airtel.buyer.airtelboe.dto.common.BoeResponse;
import com.airtel.buyer.airtelboe.dto.fetchantidumpingmaster.response.FetchAntiDumpingMasterData;
import com.airtel.buyer.airtelboe.exception.BoeException;
import com.airtel.buyer.airtelboe.model.supplier.BTVL_MST_BOE_ANTI_DUMPING_TBL;
import com.airtel.buyer.airtelboe.repository.supplier.BtvlMstBoeAntiDumpingTblRepository;
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
public class AntiDumpingMasterServiceImpl implements AntiDumpingMasterService {

    @Autowired
    private BtvlMstBoeAntiDumpingTblRepository btvlMstBoeAntiDumpingTblRepository;

    @Autowired
    private HttpServletResponse httpServletResponse;

    @Value("UPLOAD_TMP_DIRECTORY")
    private String uploadTmpDir;

    @Override
    public BoeResponse<List<FetchAntiDumpingMasterData>> antiDumpingMasterInformation() {

        BoeResponse<List<FetchAntiDumpingMasterData>> boeResponse = new BoeResponse<>();
        boeResponse.setData(this.getFetchAntiDumpingMasterDataListObject());

        return boeResponse;
    }

    private List<FetchAntiDumpingMasterData> getFetchAntiDumpingMasterDataListObject() {

        List<FetchAntiDumpingMasterData> fetchAntiDumpingMasterDataList = null;

        List<BTVL_MST_BOE_ANTI_DUMPING_TBL> antiDumpingList =
                this.btvlMstBoeAntiDumpingTblRepository.findByPurgeFlag(0);

        if (antiDumpingList != null && !antiDumpingList.isEmpty()) {
            fetchAntiDumpingMasterDataList = antiDumpingList.stream().map(this::getFetchAntiDumpingMasterDataObject).collect(Collectors.toList());
        }

        return fetchAntiDumpingMasterDataList;
    }

    private FetchAntiDumpingMasterData getFetchAntiDumpingMasterDataObject(BTVL_MST_BOE_ANTI_DUMPING_TBL bTVL_MST_BOE_ANTI_DUMPING_TBL) {

        FetchAntiDumpingMasterData fetchAntiDumpingMasterData = new FetchAntiDumpingMasterData();
        fetchAntiDumpingMasterData.setCountry(bTVL_MST_BOE_ANTI_DUMPING_TBL.getCountry());
        fetchAntiDumpingMasterData.setCountryCode(bTVL_MST_BOE_ANTI_DUMPING_TBL.getCountryCode());
        fetchAntiDumpingMasterData.setSupplierName(bTVL_MST_BOE_ANTI_DUMPING_TBL.getSupplierName());
        fetchAntiDumpingMasterData.setSupplierCode(bTVL_MST_BOE_ANTI_DUMPING_TBL.getSupplierCode());
        fetchAntiDumpingMasterData.setItemCode(bTVL_MST_BOE_ANTI_DUMPING_TBL.getItemCode());
        fetchAntiDumpingMasterData.setItemDescription(bTVL_MST_BOE_ANTI_DUMPING_TBL.getItemDescription());
        fetchAntiDumpingMasterData.setDutyPer(bTVL_MST_BOE_ANTI_DUMPING_TBL.getDutyPercent());

        return fetchAntiDumpingMasterData;
    }

    @Override
    public void antiDumpingMasterExcel() {

        List<FetchAntiDumpingMasterData> fetchAntiDumpingMasterDataList = this.getFetchAntiDumpingMasterDataListObject();

        String excelName = "AntiDumpingMasterDetails";

        List<String> headerTextList = this.getExcelHeaders();
        List<Map<String, String>> rowMapList = this.getExcelRowsData(fetchAntiDumpingMasterDataList);

        String excelCheck = ExcelUtil.downloadExcel(headerTextList, rowMapList, excelName, httpServletResponse, this.uploadTmpDir);

        if (excelCheck.equalsIgnoreCase("y")) {
            log.info("FTA Master Excel Generated successfully");
        } else {
            throw new BoeException("Something went wrong", null, HttpStatus.OK);
        }

    }

    private List<String> getExcelHeaders() {

        List<String> headerTextList = new ArrayList<>();
        headerTextList.add("NAME OF COUNTRY");
        headerTextList.add("COUNTRY CODE");
        headerTextList.add("SUPPLIER NAME");
        headerTextList.add("SUPPLIER CODE");
        headerTextList.add("ITEM CODE");
        headerTextList.add("ITEM DESCRIPTION");
        headerTextList.add("DUTY %");

        return headerTextList;
    }

    private List<Map<String, String>> getExcelRowsData(List<FetchAntiDumpingMasterData> excelData) {

        List<Map<String, String>> rowMapList = new ArrayList<>();

        for (FetchAntiDumpingMasterData row : excelData) {
            Map<String, String> map = new LinkedHashMap<>();

            map.put("NAME OF COUNTRY", row.getCountry());
            map.put("COUNTRY CODE", row.getCountryCode());
            map.put("SUPPLIER NAME", row.getSupplierName());
            map.put("SUPPLIER CODE", row.getSupplierCode());
            map.put("ITEM CODE", row.getItemCode());
            map.put("ITEM DESCRIPTION", row.getItemDescription());
            map.put("DUTY %", row.getDutyPer());

            rowMapList.add(map);
        }

        return rowMapList;
    }

}
