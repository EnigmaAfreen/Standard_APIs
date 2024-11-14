package com.airtel.buyer.airtelboe.service.iecmaster;

import com.airtel.buyer.airtelboe.dto.common.BoeResponse;
import com.airtel.buyer.airtelboe.dto.fetchiecmaster.response.FetchIecMasterData;
import com.airtel.buyer.airtelboe.exception.BoeException;
import com.airtel.buyer.airtelboe.model.supplier.BTVL_MST_BOE_IEC_TBL;
import com.airtel.buyer.airtelboe.repository.supplier.BtvlMstBoeIecTblRepository;
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
public class IecMasterServiceImpl implements IecMasterService {

    @Autowired
    private BtvlMstBoeIecTblRepository btvlMstBoeIecTblRepository;

    @Autowired
    private HttpServletResponse httpServletResponse;

    @Value("UPLOAD_TMP_DIRECTORY")
    private String uploadTmpDir;

    @Override
    public BoeResponse<List<FetchIecMasterData>> iecMasterInformation() {

        BoeResponse<List<FetchIecMasterData>> boeResponse = new BoeResponse<>();
        boeResponse.setData(this.getFetchIecMasterDataListObject());

        return boeResponse;
    }

    private List<FetchIecMasterData> getFetchIecMasterDataListObject() {

        List<FetchIecMasterData> fetchIecMasterDataList = null;

        List<BTVL_MST_BOE_IEC_TBL> iecList =
                this.btvlMstBoeIecTblRepository.findByPurgeFlag(0);

        if (iecList != null && !iecList.isEmpty()) {
            fetchIecMasterDataList = iecList.stream().map(this::getFetchIecMasterDataObject).collect(Collectors.toList());
        }

        return fetchIecMasterDataList;
    }

    private FetchIecMasterData getFetchIecMasterDataObject(BTVL_MST_BOE_IEC_TBL bTVL_MST_BOE_IEC_TBL) {

        FetchIecMasterData fetchIecMasterData = new FetchIecMasterData();
        fetchIecMasterData.setLegalEntity(bTVL_MST_BOE_IEC_TBL.getLeName());
        fetchIecMasterData.setAdCode(bTVL_MST_BOE_IEC_TBL.getAdCode());
        fetchIecMasterData.setIecCode(bTVL_MST_BOE_IEC_TBL.getIecCode());
        fetchIecMasterData.setAddress(bTVL_MST_BOE_IEC_TBL.getAddress());
        fetchIecMasterData.setZip(bTVL_MST_BOE_IEC_TBL.getZip());
        fetchIecMasterData.setCityName(bTVL_MST_BOE_IEC_TBL.getCity());
        fetchIecMasterData.setPan(bTVL_MST_BOE_IEC_TBL.getPan());
        fetchIecMasterData.setGstn(bTVL_MST_BOE_IEC_TBL.getGstinNumber());
        fetchIecMasterData.setBranchCode(bTVL_MST_BOE_IEC_TBL.getBranchCode());
        fetchIecMasterData.setState(bTVL_MST_BOE_IEC_TBL.getState());

        return fetchIecMasterData;
    }

    @Override
    public void iecMasterExcel() {

        List<FetchIecMasterData> fetchIecMasterDataList = this.getFetchIecMasterDataListObject();

        String excelName = "IECMasterDetails";

        List<String> headerTextList = this.getExcelHeaders();
        List<Map<String, String>> rowMapList = this.getExcelRowsData(fetchIecMasterDataList);

        String excelCheck = ExcelUtil.downloadExcel(headerTextList, rowMapList, excelName, httpServletResponse, this.uploadTmpDir);

        if (excelCheck.equalsIgnoreCase("y")) {
            log.info("IEC Master Excel Generated successfully");
        } else {
            throw new BoeException("Something went wrong", null, HttpStatus.OK);
        }

    }

    private List<String> getExcelHeaders() {

        List<String> headerTextList = new ArrayList<>();
        headerTextList.add("LEGAL ENTITY NAME");
        headerTextList.add("AD (AUTHORISED DEALER) CODE");
        headerTextList.add("IEC (IMPORT EXPORT) CODE");
        headerTextList.add("ADDRESS");
        headerTextList.add("ZIP");
        headerTextList.add("CITY NAME");
        headerTextList.add("PAN");
        headerTextList.add("GSTN");
        headerTextList.add("BRANCH CODE");
        headerTextList.add("STATE");

        return headerTextList;
    }

    private List<Map<String, String>> getExcelRowsData(List<FetchIecMasterData> excelData) {

        List<Map<String, String>> rowMapList = new ArrayList<>();

        for (FetchIecMasterData row : excelData) {
            Map<String, String> map = new LinkedHashMap<>();

            map.put("LEGAL ENTITY NAME", row.getLegalEntity());
            map.put("AD (AUTHORISED DEALER) CODE", row.getAdCode());
            map.put("IEC (IMPORT EXPORT) CODE", row.getIecCode());
            map.put("ADDRESS", row.getAddress());
            map.put("ZIP", row.getZip());
            map.put("CITY NAME", row.getCityName());
            map.put("PAN", row.getPan());
            map.put("GSTN", row.getGstn());
            map.put("BRANCH CODE", row.getBranchCode() != null ? row.getBranchCode().toString() : null);
            map.put("STATE", row.getState());

            rowMapList.add(map);
        }

        return rowMapList;
    }

}
