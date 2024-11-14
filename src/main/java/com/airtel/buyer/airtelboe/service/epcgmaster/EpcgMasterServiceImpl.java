package com.airtel.buyer.airtelboe.service.epcgmaster;

import com.airtel.buyer.airtelboe.dto.addepcgmaster.request.AddEpcgMasterRequest;
import com.airtel.buyer.airtelboe.dto.addepcgmaster.response.AddEpcgMasterData;
import com.airtel.buyer.airtelboe.dto.common.BoeResponse;
import com.airtel.buyer.airtelboe.dto.common.Error;
import com.airtel.buyer.airtelboe.dto.editepcgmaster.request.EditEpcgMasterRequest;
import com.airtel.buyer.airtelboe.dto.editepcgmaster.response.EditEpcgMasterData;
import com.airtel.buyer.airtelboe.dto.enddateepcgmaster.request.EndDateEpcgMasterRequest;
import com.airtel.buyer.airtelboe.dto.enddateepcgmaster.response.EndDateEpcgMasterData;
import com.airtel.buyer.airtelboe.dto.fetchepcgmaster.response.FetchEpcgMasterData;
import com.airtel.buyer.airtelboe.exception.BoeException;
import com.airtel.buyer.airtelboe.repository.EpcgMaster;
import com.airtel.buyer.airtelboe.repository.supplier.BtvlMstBoeEpcgTblRepository;
import com.airtel.buyer.airtelboe.user.UserDetailsImpl;
import com.airtel.buyer.airtelboe.util.BoeUtils;
import com.airtel.buyer.airtelboe.util.CommonConstants;
import com.airtel.buyer.airtelboe.util.excel.ExcelUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
public class EpcgMasterServiceImpl implements EpcgMasterService {

    @Autowired
    private BtvlMstBoeEpcgTblRepository btvlMstBoeEpcgTblRepository;

    @Autowired
    private EpcgMasterTransactionService epcgMasterTransactionService;

    @Autowired
    private HttpServletResponse httpServletResponse;

    @Value("UPLOAD_TMP_DIRECTORY")
    private String uploadTmpDir;

    @Value("${app.node}")
    public String appNode;

    @Override
    public BoeResponse<List<FetchEpcgMasterData>> epcgMasterInformation() {

        BoeResponse<List<FetchEpcgMasterData>> boeResponse = new BoeResponse<>();
        boeResponse.setData(this.getFetchEpcgMasterDataListObject());

        return boeResponse;
    }

    private List<FetchEpcgMasterData> getFetchEpcgMasterDataListObject() {

        List<FetchEpcgMasterData> fetchEpcgMasterDataList = null;

        List<EpcgMaster> epcgList =
                this.btvlMstBoeEpcgTblRepository.fetchEpcgRecord();

        if (epcgList != null && !epcgList.isEmpty()) {
            fetchEpcgMasterDataList = epcgList.stream().map(this::getFetchEpcgMasterDataObject).collect(Collectors.toList());
        }

        return fetchEpcgMasterDataList;
    }

    private FetchEpcgMasterData getFetchEpcgMasterDataObject(EpcgMaster epcgMaster) {

        FetchEpcgMasterData fetchEpcgMasterData = new FetchEpcgMasterData();
        fetchEpcgMasterData.setEpcgId(epcgMaster.getEpcgId());
        fetchEpcgMasterData.setLicenseNo(epcgMaster.getLicenceNo());
        fetchEpcgMasterData.setLegalEntity(epcgMaster.getLeName());
        fetchEpcgMasterData.setLob(epcgMaster.getLob());
        fetchEpcgMasterData.setItemSerialNo(epcgMaster.getItemSerialNum());
        fetchEpcgMasterData.setItemCode(epcgMaster.getItemCode());
        fetchEpcgMasterData.setItemDescription(epcgMaster.getItemDescription());
        fetchEpcgMasterData.setPort(epcgMaster.getPortCode());
        fetchEpcgMasterData.setTotalItemQty(epcgMaster.getTotItemQty());
        fetchEpcgMasterData.setBalanceQty(epcgMaster.getBalanceQty());
        fetchEpcgMasterData.setLicenseDate(epcgMaster.getLicenceDate());
        fetchEpcgMasterData.setEndDate(epcgMaster.getEndDate());

        return fetchEpcgMasterData;
    }

    @Override
    public BoeResponse<AddEpcgMasterData> addEpcgMaster(AddEpcgMasterRequest addEpcgMasterRequest, UserDetailsImpl userDetails) {

        BoeResponse<AddEpcgMasterData> boeResponse = new BoeResponse<>();
        boeResponse.setData(this.getAddEpcgMasterDataObject(addEpcgMasterRequest));

        if (this.checkMandatoryFieldsForAdd(addEpcgMasterRequest, boeResponse)) {
            this.epcgMasterTransactionService.add(addEpcgMasterRequest, userDetails);
        }

        return boeResponse;
    }

    private AddEpcgMasterData getAddEpcgMasterDataObject(AddEpcgMasterRequest addEpcgMasterRequest) {

        AddEpcgMasterData addEpcgMasterData = new AddEpcgMasterData();
        addEpcgMasterData.setLicenseNo(addEpcgMasterRequest.getLicenseNo());
        addEpcgMasterData.setLegalEntity(addEpcgMasterRequest.getLegalEntity());
        addEpcgMasterData.setItemSerialNo(addEpcgMasterRequest.getItemSerialNo());
        addEpcgMasterData.setItemCode(addEpcgMasterRequest.getItemCode());
        addEpcgMasterData.setItemDescription(addEpcgMasterRequest.getItemDescription());
        addEpcgMasterData.setPort(addEpcgMasterRequest.getPort());
        addEpcgMasterData.setTotalItemQty(addEpcgMasterRequest.getTotalItemQty());
        addEpcgMasterData.setBalanceQty(addEpcgMasterRequest.getBalanceQty());
        addEpcgMasterData.setLicenseDate(addEpcgMasterRequest.getLicenseDate());
        addEpcgMasterData.setEndDate(addEpcgMasterRequest.getEndDate());

        return addEpcgMasterData;
    }

    private Boolean checkMandatoryFieldsForAdd(AddEpcgMasterRequest addEpcgMasterRequest,
                                               BoeResponse<AddEpcgMasterData> boeResponse) {

        Boolean isValidationPassed = Boolean.TRUE;

        AddEpcgMasterData addEpcgMasterData = boeResponse.getData();

        if (StringUtils.isBlank(addEpcgMasterRequest.getLicenseNo())) {
            isValidationPassed = Boolean.FALSE;
            addEpcgMasterData.setLicenseNoErrMsg(CommonConstants.FIELD_IS_MANDATORY);
        }

        if (StringUtils.isBlank(addEpcgMasterRequest.getLegalEntity())) {
            isValidationPassed = Boolean.FALSE;
            addEpcgMasterData.setLegalEntityErrMsg(CommonConstants.FIELD_IS_MANDATORY);
        }

        if (StringUtils.isBlank(addEpcgMasterRequest.getItemCode())) {
            isValidationPassed = Boolean.FALSE;
            addEpcgMasterData.setItemCodeErrMsg(CommonConstants.FIELD_IS_MANDATORY);
        }

        if (StringUtils.isBlank(addEpcgMasterRequest.getItemDescription())) {
            isValidationPassed = Boolean.FALSE;
            addEpcgMasterData.setItemDescriptionErrMsg(CommonConstants.FIELD_IS_MANDATORY);
        }

        if (StringUtils.isBlank(addEpcgMasterRequest.getPort())) {
            isValidationPassed = Boolean.FALSE;
            addEpcgMasterData.setPortErrMsg(CommonConstants.FIELD_IS_MANDATORY);
        }

        if (addEpcgMasterRequest.getTotalItemQty() == null ||
                StringUtils.isBlank(addEpcgMasterRequest.getTotalItemQty().toString())) {
            isValidationPassed = Boolean.FALSE;
            addEpcgMasterData.setTotalItemQtyErrMsg(CommonConstants.FIELD_IS_MANDATORY);
        }

        if (addEpcgMasterRequest.getBalanceQty() == null ||
                StringUtils.isBlank(addEpcgMasterRequest.getBalanceQty().toString())) {
            isValidationPassed = Boolean.FALSE;
            addEpcgMasterData.setBalanceQtyErrMsg(CommonConstants.FIELD_IS_MANDATORY);
        }

        if (addEpcgMasterRequest.getLicenseDate() == null ||
                StringUtils.isBlank(addEpcgMasterRequest.getLicenseDate().toString())) {
            isValidationPassed = Boolean.FALSE;
            addEpcgMasterData.setLicenseDateErrMsg(CommonConstants.FIELD_IS_MANDATORY);
        }

        if (addEpcgMasterRequest.getEndDate() == null ||
                StringUtils.isBlank(addEpcgMasterRequest.getEndDate().toString())) {
            isValidationPassed = Boolean.FALSE;
            addEpcgMasterData.setEndDateErrMsg(CommonConstants.FIELD_IS_MANDATORY);
        }

        log.info("EpcgMasterServiceImpl :: method :: checkMandatoryFieldsForAdd :: " + isValidationPassed);

        if (!isValidationPassed) {
            this.setErrorObject(boeResponse, CommonConstants.EPCG_MASTER_ADD_ERROR_CODE);
        }

        return isValidationPassed;
    }

    private void setErrorObject(BoeResponse boeResponse, String code) {
        Error error = new Error();
        error.setCode(code);
        error.setTraceId(BoeUtils.generateTraceId(appNode));
        boeResponse.setError(error);
    }

    @Override
    public BoeResponse<EditEpcgMasterData> editEpcgMaster(EditEpcgMasterRequest editEpcgMasterRequest, UserDetailsImpl userDetails) {

        BoeResponse<EditEpcgMasterData> boeResponse = new BoeResponse<>();
        boeResponse.setData(this.getEditEpcgMasterDataObject(editEpcgMasterRequest));

        if (this.checkMandatoryFieldsForEdit(editEpcgMasterRequest, boeResponse)) {
            this.epcgMasterTransactionService.edit(editEpcgMasterRequest, userDetails);
        }

        return boeResponse;
    }

    private EditEpcgMasterData getEditEpcgMasterDataObject(EditEpcgMasterRequest editEpcgMasterRequest) {

        EditEpcgMasterData editEpcgMasterData = new EditEpcgMasterData();
        editEpcgMasterData.setEpcgId(editEpcgMasterRequest.getEpcgId());
        editEpcgMasterData.setLegalEntity(editEpcgMasterRequest.getLegalEntity());
        editEpcgMasterData.setItemCode(editEpcgMasterRequest.getItemCode());
        editEpcgMasterData.setItemDescription(editEpcgMasterRequest.getItemDescription());
        editEpcgMasterData.setPort(editEpcgMasterRequest.getPort());
        editEpcgMasterData.setEndDate(editEpcgMasterRequest.getEndDate());

        return editEpcgMasterData;
    }

    private Boolean checkMandatoryFieldsForEdit(EditEpcgMasterRequest editEpcgMasterRequest,
                                                BoeResponse<EditEpcgMasterData> boeResponse) {

        Boolean isValidationPassed = Boolean.TRUE;

        EditEpcgMasterData editEpcgMasterData = boeResponse.getData();

        if (editEpcgMasterRequest.getEpcgId() == null ||
                StringUtils.isBlank(editEpcgMasterRequest.getEpcgId().toString())) {
            isValidationPassed = Boolean.FALSE;
            editEpcgMasterData.setEpcgIdErrMsg(CommonConstants.FIELD_IS_MANDATORY);
        }

        if (StringUtils.isBlank(editEpcgMasterRequest.getLegalEntity())) {
            isValidationPassed = Boolean.FALSE;
            editEpcgMasterData.setLegalEntityErrMsg(CommonConstants.FIELD_IS_MANDATORY);
        }

        if (StringUtils.isBlank(editEpcgMasterRequest.getItemCode())) {
            isValidationPassed = Boolean.FALSE;
            editEpcgMasterData.setItemCodeErrMsg(CommonConstants.FIELD_IS_MANDATORY);
        }

        if (StringUtils.isBlank(editEpcgMasterRequest.getItemDescription())) {
            isValidationPassed = Boolean.FALSE;
            editEpcgMasterData.setItemDescriptionErrMsg(CommonConstants.FIELD_IS_MANDATORY);
        }

        if (StringUtils.isBlank(editEpcgMasterRequest.getPort())) {
            isValidationPassed = Boolean.FALSE;
            editEpcgMasterData.setPortErrMsg(CommonConstants.FIELD_IS_MANDATORY);
        }

        if (editEpcgMasterRequest.getEndDate() == null ||
                StringUtils.isBlank(editEpcgMasterRequest.getEndDate().toString())) {
            isValidationPassed = Boolean.FALSE;
            editEpcgMasterData.setEndDateErrMsg(CommonConstants.FIELD_IS_MANDATORY);
        }

        log.info("EpcgMasterServiceImpl :: method :: checkMandatoryFieldsForEdit :: " + isValidationPassed);

        if (!isValidationPassed) {
            this.setErrorObject(boeResponse, CommonConstants.EPCG_MASTER_EDIT_ERROR_CODE);
        }

        return isValidationPassed;
    }

    @Override
    public BoeResponse<EndDateEpcgMasterData> endDateEpcgMaster(EndDateEpcgMasterRequest endDateEpcgMasterRequest, UserDetailsImpl userDetails) {

        BoeResponse<EndDateEpcgMasterData> boeResponse = new BoeResponse<>();
        boeResponse.setData(this.getEndDateEpcgMasterDataObject(endDateEpcgMasterRequest));

        if (this.checkMandatoryFieldsForEndDate(endDateEpcgMasterRequest, boeResponse)) {
            this.epcgMasterTransactionService.endDate(endDateEpcgMasterRequest, userDetails);
        }

        return boeResponse;
    }

    private EndDateEpcgMasterData getEndDateEpcgMasterDataObject(EndDateEpcgMasterRequest endDateEpcgMasterRequest) {

        EndDateEpcgMasterData endDateEpcgMasterData = new EndDateEpcgMasterData();
        endDateEpcgMasterData.setEpcgId(endDateEpcgMasterRequest.getEpcgId());
        endDateEpcgMasterData.setEndDate(endDateEpcgMasterRequest.getEndDate());

        return endDateEpcgMasterData;
    }

    private Boolean checkMandatoryFieldsForEndDate(EndDateEpcgMasterRequest endDateEpcgMasterRequest,
                                                   BoeResponse<EndDateEpcgMasterData> boeResponse) {

        Boolean isValidationPassed = Boolean.TRUE;

        EndDateEpcgMasterData endDateEpcgMasterData = boeResponse.getData();

        if (endDateEpcgMasterRequest.getEpcgId() == null ||
                StringUtils.isBlank(endDateEpcgMasterRequest.getEpcgId().toString())) {
            isValidationPassed = Boolean.FALSE;
            endDateEpcgMasterData.setEpcgIdErrMsg(CommonConstants.FIELD_IS_MANDATORY);
        }

        if (endDateEpcgMasterRequest.getEndDate() == null ||
                StringUtils.isBlank(endDateEpcgMasterRequest.getEndDate().toString())) {
            isValidationPassed = Boolean.FALSE;
            endDateEpcgMasterData.setEndDateErrMsg(CommonConstants.FIELD_IS_MANDATORY);
        }

        log.info("EpcgMasterServiceImpl :: method :: checkMandatoryFieldsForEndDate :: " + isValidationPassed);

        if (!isValidationPassed) {
            this.setErrorObject(boeResponse, CommonConstants.EPCG_MASTER_END_DATE_ERROR_CODE);
        }

        return isValidationPassed;
    }

    @Override
    public void epcgMasterExcel() {

        List<FetchEpcgMasterData> fetchEpcgMasterDataList = this.getFetchEpcgMasterDataListObject();

        String excelName = "EPCGMasterDetails";

        List<String> headerTextList = this.getExcelHeaders();
        List<Map<String, String>> rowMapList = this.getExcelRowsData(fetchEpcgMasterDataList);

        String excelCheck = ExcelUtil.downloadExcel(headerTextList, rowMapList, excelName, httpServletResponse, this.uploadTmpDir);

        if (excelCheck.equalsIgnoreCase("y")) {
            log.info("EPCG Master Excel Generated successfully");
        } else {
            throw new BoeException("Something went wrong", null, HttpStatus.OK);
        }

    }

    private List<String> getExcelHeaders() {

        List<String> headerTextList = new ArrayList<>();
        headerTextList.add("LICENSE NO.");
        headerTextList.add("LEGAL ENTITY");
        headerTextList.add("LOB");
        headerTextList.add("ITEM SERIAL NO");
        headerTextList.add("ITEM CODE");
        headerTextList.add("ITEM DESCRIPTION");
        headerTextList.add("PORT");
        headerTextList.add("TOTAL ITEM QTY IN EPCG LICENCE");
        headerTextList.add("BALANCE QTY");
        headerTextList.add("LICENSE DATE");
        headerTextList.add("END DATE");

        return headerTextList;
    }

    private List<Map<String, String>> getExcelRowsData(List<FetchEpcgMasterData> excelData) {

        List<Map<String, String>> rowMapList = new ArrayList<>();

        for (FetchEpcgMasterData row : excelData) {
            Map<String, String> map = new LinkedHashMap<>();

            map.put("LICENSE NO.", row.getLicenseNo());
            map.put("LEGAL ENTITY", row.getLegalEntity());
            map.put("LOB", row.getLob());
            map.put("ITEM SERIAL NO", row.getItemSerialNo());
            map.put("ITEM CODE", row.getItemCode());
            map.put("ITEM DESCRIPTION", row.getItemDescription());
            map.put("PORT", row.getPort());
            map.put("TOTAL ITEM QTY IN EPCG LICENCE",
                    row.getTotalItemQty() != null ? row.getTotalItemQty().toString() : null);
            map.put("BALANCE QTY", row.getBalanceQty() != null ? row.getBalanceQty().toString() : null);
            map.put("LICENSE DATE", row.getLicenseDate() != null ? row.getLicenseDate().toString() : null);
            map.put("END DATE", row.getEndDate() != null ? row.getEndDate().toString() : null);

            rowMapList.add(map);
        }

        return rowMapList;
    }

}
