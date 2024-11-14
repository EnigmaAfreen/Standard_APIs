package com.airtel.buyer.airtelboe.service.wpcmaster;

import com.airtel.buyer.airtelboe.dto.addwpcmaster.request.AddWpcMasterRequest;
import com.airtel.buyer.airtelboe.dto.addwpcmaster.response.AddWpcMasterData;
import com.airtel.buyer.airtelboe.dto.common.BoeResponse;
import com.airtel.buyer.airtelboe.dto.common.Error;
import com.airtel.buyer.airtelboe.dto.editwpcmaster.request.EditWpcMasterRequest;
import com.airtel.buyer.airtelboe.dto.editwpcmaster.response.EditWpcMasterData;
import com.airtel.buyer.airtelboe.dto.enddatewpcmaster.request.EndDateWpcMasterRequest;
import com.airtel.buyer.airtelboe.dto.enddatewpcmaster.response.EndDateWpcMasterData;
import com.airtel.buyer.airtelboe.dto.fetchwpcmaster.response.FetchWpcMasterData;
import com.airtel.buyer.airtelboe.exception.BoeException;
import com.airtel.buyer.airtelboe.repository.WpcMaster;
import com.airtel.buyer.airtelboe.repository.supplier.BtvlMstBoeWpcTblRepository;
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
public class WpcMasterServiceImpl implements WpcMasterService {

    @Autowired
    private BtvlMstBoeWpcTblRepository btvlMstBoeWpcTblRepository;

    @Autowired
    private WpcMasterTransactionService wpcMasterTransactionService;

    @Autowired
    private HttpServletResponse httpServletResponse;

    @Value("UPLOAD_TMP_DIRECTORY")
    private String uploadTmpDir;

    @Value("${app.node}")
    public String appNode;

    @Override
    public BoeResponse<List<FetchWpcMasterData>> wpcMasterInformation() {

        BoeResponse<List<FetchWpcMasterData>> boeResponse = new BoeResponse<>();
        boeResponse.setData(this.getFetchWpcMasterDataListObject());

        return boeResponse;
    }

    private List<FetchWpcMasterData> getFetchWpcMasterDataListObject() {

        List<FetchWpcMasterData> fetchWpcMasterDataList = null;

        List<WpcMaster> wpcList =
                this.btvlMstBoeWpcTblRepository.fetchWpcRecord();

        if (wpcList != null && !wpcList.isEmpty()) {
            fetchWpcMasterDataList = wpcList.stream().map(this::getFetchWpcMasterDataObject).collect(Collectors.toList());
        }

        return fetchWpcMasterDataList;
    }

    private FetchWpcMasterData getFetchWpcMasterDataObject(WpcMaster wpcMaster) {

        FetchWpcMasterData fetchWpcMasterData = new FetchWpcMasterData();
        fetchWpcMasterData.setWpcId(wpcMaster.getWpcId());
        fetchWpcMasterData.setLegalEntity(wpcMaster.getLegalEntity());
        fetchWpcMasterData.setPortCode(wpcMaster.getPortCode());
        fetchWpcMasterData.setItemCode(wpcMaster.getItemCode());
        fetchWpcMasterData.setLicenseNo(wpcMaster.getLicenseNo());
        fetchWpcMasterData.setWpcQuantity(wpcMaster.getWpcQty());
        fetchWpcMasterData.setBalanceQuantity(wpcMaster.getBalanceQty());
        fetchWpcMasterData.setLicenseDate(wpcMaster.getLicenseDate());
        fetchWpcMasterData.setEndDate(wpcMaster.getEndDate());

        return fetchWpcMasterData;
    }

    @Override
    public BoeResponse<AddWpcMasterData> addWpcMaster(AddWpcMasterRequest addWpcMasterRequest, UserDetailsImpl userDetails) {

        BoeResponse<AddWpcMasterData> boeResponse = new BoeResponse<>();
        boeResponse.setData(this.getAddWpcMasterDataObject(addWpcMasterRequest));

        if (this.checkMandatoryFieldsForAdd(addWpcMasterRequest, boeResponse)) {
            this.wpcMasterTransactionService.add(addWpcMasterRequest, userDetails);
        }

        return boeResponse;
    }

    private AddWpcMasterData getAddWpcMasterDataObject(AddWpcMasterRequest addWpcMasterRequest) {

        AddWpcMasterData addWpcMasterData = new AddWpcMasterData();
        addWpcMasterData.setLegalEntity(addWpcMasterRequest.getLegalEntity());
        addWpcMasterData.setPortCode(addWpcMasterRequest.getPortCode());
        addWpcMasterData.setItemCode(addWpcMasterRequest.getItemCode());
        addWpcMasterData.setLicenseNo(addWpcMasterRequest.getLicenseNo());
        addWpcMasterData.setWpcQuantity(addWpcMasterRequest.getWpcQuantity());
        addWpcMasterData.setEndDate(addWpcMasterRequest.getEndDate());
        addWpcMasterData.setLicenseDate(addWpcMasterRequest.getLicenseDate());

        return addWpcMasterData;
    }

    private Boolean checkMandatoryFieldsForAdd(AddWpcMasterRequest addWpcMasterRequest,
                                               BoeResponse<AddWpcMasterData> boeResponse) {

        Boolean isValidationPassed = Boolean.TRUE;

        AddWpcMasterData addWpcMasterData = boeResponse.getData();

        if (StringUtils.isBlank(addWpcMasterRequest.getLegalEntity())) {
            isValidationPassed = Boolean.FALSE;
            addWpcMasterData.setLegalEntityErrMsg(CommonConstants.FIELD_IS_MANDATORY);
        }

        if (StringUtils.isBlank(addWpcMasterRequest.getPortCode())) {
            isValidationPassed = Boolean.FALSE;
            addWpcMasterData.setPortCodeErrMsg(CommonConstants.FIELD_IS_MANDATORY);
        }

        if (StringUtils.isBlank(addWpcMasterRequest.getItemCode())) {
            isValidationPassed = Boolean.FALSE;
            addWpcMasterData.setItemCodeErrMsg(CommonConstants.FIELD_IS_MANDATORY);
        }

        if (StringUtils.isBlank(addWpcMasterRequest.getLicenseNo())) {
            isValidationPassed = Boolean.FALSE;
            addWpcMasterData.setLicenseNoErrMsg(CommonConstants.FIELD_IS_MANDATORY);
        }

        if (addWpcMasterRequest.getWpcQuantity() == null ||
                StringUtils.isBlank(addWpcMasterRequest.getWpcQuantity().toString())) {
            isValidationPassed = Boolean.FALSE;
            addWpcMasterData.setWpcQuantityErrMsg(CommonConstants.FIELD_IS_MANDATORY);
        }

        if (addWpcMasterRequest.getLicenseDate() == null ||
                StringUtils.isBlank(addWpcMasterRequest.getLicenseDate().toString())) {
            isValidationPassed = Boolean.FALSE;
            addWpcMasterData.setLicenseDateErrMsg(CommonConstants.FIELD_IS_MANDATORY);
        }

        if (addWpcMasterRequest.getEndDate() == null ||
                StringUtils.isBlank(addWpcMasterRequest.getEndDate().toString())) {
            isValidationPassed = Boolean.FALSE;
            addWpcMasterData.setEndDateErrMsg(CommonConstants.FIELD_IS_MANDATORY);
        }

        log.info("WpcMasterServiceImpl :: method :: checkMandatoryFieldsForAdd :: " + isValidationPassed);

        if (!isValidationPassed) {
            this.setErrorObject(boeResponse, CommonConstants.WPC_MASTER_ADD_ERROR_CODE);
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
    public BoeResponse<EditWpcMasterData> editWpcMaster(EditWpcMasterRequest editWpcMasterRequest, UserDetailsImpl userDetails) {

        BoeResponse<EditWpcMasterData> boeResponse = new BoeResponse<>();
        boeResponse.setData(this.getEditWpcMasterDataObject(editWpcMasterRequest));

        if (this.checkMandatoryFieldsForEdit(editWpcMasterRequest, boeResponse)) {
            this.wpcMasterTransactionService.edit(editWpcMasterRequest, userDetails);
        }

        return boeResponse;
    }

    private EditWpcMasterData getEditWpcMasterDataObject(EditWpcMasterRequest editWpcMasterRequest) {

        EditWpcMasterData editWpcMasterData = new EditWpcMasterData();
        editWpcMasterData.setWpcId(editWpcMasterRequest.getWpcId());
        editWpcMasterData.setLegalEntity(editWpcMasterRequest.getLegalEntity());
        editWpcMasterData.setPortCode(editWpcMasterRequest.getPortCode());
        editWpcMasterData.setItemCode(editWpcMasterRequest.getItemCode());
        editWpcMasterData.setLicenseNo(editWpcMasterRequest.getLicenseNo());
        editWpcMasterData.setWpcQuantity(editWpcMasterRequest.getWpcQuantity());
        editWpcMasterData.setConsumeQuantity(editWpcMasterRequest.getConsumeQuantity());
        editWpcMasterData.setEndDate(editWpcMasterRequest.getEndDate());

        return editWpcMasterData;
    }

    private Boolean checkMandatoryFieldsForEdit(EditWpcMasterRequest editWpcMasterRequest,
                                                BoeResponse<EditWpcMasterData> boeResponse) {

        Boolean isValidationPassed = Boolean.TRUE;

        EditWpcMasterData editWpcMasterData = boeResponse.getData();

        if (editWpcMasterRequest.getWpcId() == null ||
                StringUtils.isBlank(editWpcMasterRequest.getWpcId().toString())) {
            isValidationPassed = Boolean.FALSE;
            editWpcMasterData.setWpcIdErrMsg(CommonConstants.FIELD_IS_MANDATORY);
        }

        if (StringUtils.isBlank(editWpcMasterRequest.getLegalEntity())) {
            isValidationPassed = Boolean.FALSE;
            editWpcMasterData.setLegalEntityErrMsg(CommonConstants.FIELD_IS_MANDATORY);
        }

        if (StringUtils.isBlank(editWpcMasterRequest.getPortCode())) {
            isValidationPassed = Boolean.FALSE;
            editWpcMasterData.setPortCodeErrMsg(CommonConstants.FIELD_IS_MANDATORY);
        }

        if (StringUtils.isBlank(editWpcMasterRequest.getItemCode())) {
            isValidationPassed = Boolean.FALSE;
            editWpcMasterData.setItemCodeErrMsg(CommonConstants.FIELD_IS_MANDATORY);
        }

        if (StringUtils.isBlank(editWpcMasterRequest.getLicenseNo())) {
            isValidationPassed = Boolean.FALSE;
            editWpcMasterData.setLicenseNoErrMsg(CommonConstants.FIELD_IS_MANDATORY);
        }

        if (editWpcMasterRequest.getWpcQuantity() == null ||
                StringUtils.isBlank(editWpcMasterRequest.getWpcQuantity().toString())) {
            isValidationPassed = Boolean.FALSE;
            editWpcMasterData.setWpcQuantityErrMsg(CommonConstants.FIELD_IS_MANDATORY);
        }

        if (editWpcMasterRequest.getConsumeQuantity() == null ||
                StringUtils.isBlank(editWpcMasterRequest.getConsumeQuantity().toString())) {
            isValidationPassed = Boolean.FALSE;
            editWpcMasterData.setConsumeQuantityErrMsg(CommonConstants.FIELD_IS_MANDATORY);
        }

        if (editWpcMasterRequest.getEndDate() == null ||
                StringUtils.isBlank(editWpcMasterRequest.getEndDate().toString())) {
            isValidationPassed = Boolean.FALSE;
            editWpcMasterData.setEndDateErrMsg(CommonConstants.FIELD_IS_MANDATORY);
        }

        log.info("WpcMasterServiceImpl :: method :: checkMandatoryFieldsForEdit :: " + isValidationPassed);

        if (!isValidationPassed) {
            this.setErrorObject(boeResponse, CommonConstants.WPC_MASTER_EDIT_ERROR_CODE);
        }

        return isValidationPassed;
    }

    @Override
    public BoeResponse<EndDateWpcMasterData> endDateWpcMaster(EndDateWpcMasterRequest endDateWpcMasterRequest, UserDetailsImpl userDetails) {

        BoeResponse<EndDateWpcMasterData> boeResponse = new BoeResponse<>();
        boeResponse.setData(this.getEndDateWpcMasterDataObject(endDateWpcMasterRequest));

        if (this.checkMandatoryFieldsForEndDate(endDateWpcMasterRequest, boeResponse)) {
            this.wpcMasterTransactionService.endDate(endDateWpcMasterRequest, userDetails);
        }

        return boeResponse;
    }

    private EndDateWpcMasterData getEndDateWpcMasterDataObject(EndDateWpcMasterRequest endDateWpcMasterRequest) {

        EndDateWpcMasterData endDateWpcMasterData = new EndDateWpcMasterData();
        endDateWpcMasterData.setWpcId(endDateWpcMasterRequest.getWpcId());
        endDateWpcMasterData.setEndDate(endDateWpcMasterRequest.getEndDate());

        return endDateWpcMasterData;
    }

    private Boolean checkMandatoryFieldsForEndDate(EndDateWpcMasterRequest endDateWpcMasterRequest,
                                                   BoeResponse<EndDateWpcMasterData> boeResponse) {

        Boolean isValidationPassed = Boolean.TRUE;

        EndDateWpcMasterData endDateWpcMasterData = boeResponse.getData();

        if (endDateWpcMasterRequest.getWpcId() == null ||
                StringUtils.isBlank(endDateWpcMasterRequest.getWpcId().toString())) {
            isValidationPassed = Boolean.FALSE;
            endDateWpcMasterData.setWpcIdErrMsg(CommonConstants.FIELD_IS_MANDATORY);
        }

        if (endDateWpcMasterRequest.getEndDate() == null ||
                StringUtils.isBlank(endDateWpcMasterRequest.getEndDate().toString())) {
            isValidationPassed = Boolean.FALSE;
            endDateWpcMasterData.setEndDateErrMsg(CommonConstants.FIELD_IS_MANDATORY);
        }

        log.info("WpcMasterServiceImpl :: method :: checkMandatoryFieldsForEndDate :: " + isValidationPassed);

        if (!isValidationPassed) {
            this.setErrorObject(boeResponse, CommonConstants.WPC_MASTER_END_DATE_ERROR_CODE);
        }

        return isValidationPassed;
    }

    @Override
    public void wpcMasterExcel() {

        List<FetchWpcMasterData> fetchWpcMasterDataList = this.getFetchWpcMasterDataListObject();

        String excelName = "WPCMasterDetails";

        List<String> headerTextList = this.getExcelHeaders();
        List<Map<String, String>> rowMapList = this.getExcelRowsData(fetchWpcMasterDataList);

        String excelCheck = ExcelUtil.downloadExcel(headerTextList, rowMapList, excelName, httpServletResponse, this.uploadTmpDir);

        if (excelCheck.equalsIgnoreCase("y")) {
            log.info("WPC Master Excel Generated successfully");
        } else {
            throw new BoeException("Something went wrong", null, HttpStatus.OK);
        }

    }

    private List<String> getExcelHeaders() {

        List<String> headerTextList = new ArrayList<>();
        headerTextList.add("LEGAL ENTITY");
        headerTextList.add("PORT CODE");
        headerTextList.add("ITEM CODE");
        headerTextList.add("LICENSE NO.");
        headerTextList.add("WPC QUANTITY");
        headerTextList.add("BALANCE QUANTITY");
        headerTextList.add("LICENSE DATE");
        headerTextList.add("END DATE");

        return headerTextList;
    }

    private List<Map<String, String>> getExcelRowsData(List<FetchWpcMasterData> excelData) {

        List<Map<String, String>> rowMapList = new ArrayList<>();

        for (FetchWpcMasterData row : excelData) {
            Map<String, String> map = new LinkedHashMap<>();

            map.put("LEGAL ENTITY", row.getLegalEntity());
            map.put("PORT CODE", row.getPortCode());
            map.put("ITEM CODE", row.getItemCode());
            map.put("LICENSE NO.", row.getLicenseNo());
            map.put("WPC QUANTITY", row.getWpcQuantity() != null ? row.getWpcQuantity().toString() : null);
            map.put("BALANCE QUANTITY", row.getBalanceQuantity() != null ? row.getBalanceQuantity().toString() : null);
            map.put("LICENSE DATE", row.getLicenseDate() != null ? row.getLicenseDate().toString() : null);
            map.put("END DATE", row.getEndDate() != null ? row.getEndDate().toString() : null);

            rowMapList.add(map);
        }

        return rowMapList;
    }

}
