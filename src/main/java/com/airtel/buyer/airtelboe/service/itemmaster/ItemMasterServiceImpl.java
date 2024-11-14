package com.airtel.buyer.airtelboe.service.itemmaster;

import com.airtel.buyer.airtelboe.dto.additemmaster.request.AddItemMasterRequest;
import com.airtel.buyer.airtelboe.dto.additemmaster.response.AddItemMasterData;
import com.airtel.buyer.airtelboe.dto.common.BoeResponse;
import com.airtel.buyer.airtelboe.dto.common.Error;
import com.airtel.buyer.airtelboe.dto.edititemmaster.request.EditItemMasterRequest;
import com.airtel.buyer.airtelboe.dto.edititemmaster.response.EditItemMasterData;
import com.airtel.buyer.airtelboe.dto.enddateitemmaster.request.EndDateItemMasterRequest;
import com.airtel.buyer.airtelboe.dto.enddateitemmaster.response.EndDateItemMasterData;
import com.airtel.buyer.airtelboe.dto.fetchitemcode.response.FetchItemCodeData;
import com.airtel.buyer.airtelboe.dto.fetchitemmaster.response.FetchItemMasterData;
import com.airtel.buyer.airtelboe.exception.BoeException;
import com.airtel.buyer.airtelboe.model.supplier.BTVL_MST_BOE_ITEM_TBL;
import com.airtel.buyer.airtelboe.repository.supplier.BtvlMstBoeItemTblRepository;
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
public class ItemMasterServiceImpl implements ItemMasterService {

    @Autowired
    private BtvlMstBoeItemTblRepository btvlMstBoeItemTblRepository;

    @Autowired
    private ItemMasterTransactionService itemMasterTransactionService;

    @Autowired
    private HttpServletResponse httpServletResponse;

    @Value("UPLOAD_TMP_DIRECTORY")
    private String uploadTmpDir;

    @Value("${app.node}")
    public String appNode;

    @Override
    public BoeResponse<List<FetchItemMasterData>> itemMasterInformation() {

        BoeResponse<List<FetchItemMasterData>> boeResponse = new BoeResponse<>();
        boeResponse.setData(this.getFetchItemMasterDataListObject());

        return boeResponse;
    }

    private List<FetchItemMasterData> getFetchItemMasterDataListObject() {

        List<FetchItemMasterData> fetchItemMasterDataList = null;

        List<BTVL_MST_BOE_ITEM_TBL> itemList =
                this.btvlMstBoeItemTblRepository.findByPurgeFlag(0);

        if (itemList != null && !itemList.isEmpty()) {
            fetchItemMasterDataList = itemList.stream().map(this::getFetchItemMasterDataObject).collect(Collectors.toList());
        }

        return fetchItemMasterDataList;
    }

    private FetchItemMasterData getFetchItemMasterDataObject(BTVL_MST_BOE_ITEM_TBL bTVL_MST_BOE_ITEM_TBL) {

        FetchItemMasterData fetchItemMasterData = new FetchItemMasterData();
        fetchItemMasterData.setItemId(bTVL_MST_BOE_ITEM_TBL.getItemId());
        fetchItemMasterData.setItemCode(bTVL_MST_BOE_ITEM_TBL.getItemCode());
        fetchItemMasterData.setHsn(bTVL_MST_BOE_ITEM_TBL.getHsn());
        fetchItemMasterData.setDutyCategory(bTVL_MST_BOE_ITEM_TBL.getDutyCategory());
        fetchItemMasterData.setExemptionNotificationNo(bTVL_MST_BOE_ITEM_TBL.getExemptionNotifNo());
        fetchItemMasterData.setEndDate(bTVL_MST_BOE_ITEM_TBL.getEndDate());

        return fetchItemMasterData;
    }

    @Override
    public BoeResponse<AddItemMasterData> addItemMaster(AddItemMasterRequest addItemMasterRequest, UserDetailsImpl userDetails) {

        BoeResponse<AddItemMasterData> boeResponse = new BoeResponse<>();
        boeResponse.setData(this.getAddItemMasterDataObject(addItemMasterRequest));

        if (this.checkMandatoryFieldsForAdd(addItemMasterRequest, boeResponse)) {
            this.itemMasterTransactionService.add(addItemMasterRequest, userDetails);
        }

        return boeResponse;
    }

    private AddItemMasterData getAddItemMasterDataObject(AddItemMasterRequest addItemMasterRequest) {

        AddItemMasterData addItemMasterData = new AddItemMasterData();
        addItemMasterData.setItemCode(addItemMasterRequest.getItemCode());
        addItemMasterData.setHsn(addItemMasterRequest.getHsn());
        addItemMasterData.setDutyCategory(addItemMasterRequest.getDutyCategory());
        addItemMasterData.setExemptionNotificationNo(addItemMasterRequest.getExemptionNotificationNo());

        return addItemMasterData;
    }

    private Boolean checkMandatoryFieldsForAdd(AddItemMasterRequest addItemMasterRequest,
                                               BoeResponse<AddItemMasterData> boeResponse) {

        Boolean isValidationPassed = Boolean.TRUE;

        AddItemMasterData addItemMasterData = boeResponse.getData();

        if (StringUtils.isBlank(addItemMasterRequest.getItemCode())) {
            isValidationPassed = Boolean.FALSE;
            addItemMasterData.setItemCodeErrMsg(CommonConstants.FIELD_IS_MANDATORY);
        }

        if (StringUtils.isBlank(addItemMasterRequest.getHsn())) {
            isValidationPassed = Boolean.FALSE;
            addItemMasterData.setHsnErrMsg(CommonConstants.FIELD_IS_MANDATORY);
        }

        if (StringUtils.isBlank(addItemMasterRequest.getDutyCategory())) {
            isValidationPassed = Boolean.FALSE;
            addItemMasterData.setDutyCategoryErrMsg(CommonConstants.FIELD_IS_MANDATORY);
        }

        if (StringUtils.isBlank(addItemMasterRequest.getExemptionNotificationNo())) {
            isValidationPassed = Boolean.FALSE;
            addItemMasterData.setExemptionNotificationNoErrMsg(CommonConstants.FIELD_IS_MANDATORY);
        }

        log.info("ItemMasterServiceImpl :: method :: checkMandatoryFieldsForAdd :: " + isValidationPassed);

        if (!isValidationPassed) {
            this.setErrorObject(boeResponse, CommonConstants.ITEM_MASTER_ADD_ERROR_CODE);
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
    public BoeResponse<EditItemMasterData> editItemMaster(EditItemMasterRequest editItemMasterRequest, UserDetailsImpl userDetails) {

        BoeResponse<EditItemMasterData> boeResponse = new BoeResponse<>();
        boeResponse.setData(this.getEditItemMasterDataObject(editItemMasterRequest));

        if (this.checkMandatoryFieldsForEdit(editItemMasterRequest, boeResponse)) {
            this.itemMasterTransactionService.edit(editItemMasterRequest, userDetails);
        }

        return boeResponse;
    }

    private EditItemMasterData getEditItemMasterDataObject(EditItemMasterRequest editItemMasterRequest) {

        EditItemMasterData editItemMasterData = new EditItemMasterData();
        editItemMasterData.setItemId(editItemMasterRequest.getItemId());
        editItemMasterData.setHsn(editItemMasterRequest.getHsn());
        editItemMasterData.setExemptionNotificationNo(editItemMasterRequest.getExemptionNotificationNo());
        editItemMasterData.setDutyCategory(editItemMasterRequest.getDutyCategory());

        return editItemMasterData;
    }

    private Boolean checkMandatoryFieldsForEdit(EditItemMasterRequest editItemMasterRequest,
                                                BoeResponse<EditItemMasterData> boeResponse) {

        Boolean isValidationPassed = Boolean.TRUE;

        EditItemMasterData editItemMasterData = boeResponse.getData();

        if (editItemMasterRequest.getItemId() == null ||
                StringUtils.isBlank(editItemMasterRequest.getItemId().toString())) {
            isValidationPassed = Boolean.FALSE;
            editItemMasterData.setItemIdErrMsg(CommonConstants.FIELD_IS_MANDATORY);
        }

        if (StringUtils.isBlank(editItemMasterRequest.getHsn())) {
            isValidationPassed = Boolean.FALSE;
            editItemMasterData.setHsnErrMsg(CommonConstants.FIELD_IS_MANDATORY);
        }

        if (StringUtils.isBlank(editItemMasterRequest.getDutyCategory())) {
            isValidationPassed = Boolean.FALSE;
            editItemMasterData.setDutyCategoryErrMsg(CommonConstants.FIELD_IS_MANDATORY);
        }

        if (StringUtils.isBlank(editItemMasterRequest.getExemptionNotificationNo())) {
            isValidationPassed = Boolean.FALSE;
            editItemMasterData.setExemptionNotificationNoErrMsg(CommonConstants.FIELD_IS_MANDATORY);
        }

        log.info("ItemMasterServiceImpl :: method :: checkMandatoryFieldsForEdit :: " + isValidationPassed);

        if (!isValidationPassed) {
            this.setErrorObject(boeResponse, CommonConstants.ITEM_MASTER_EDIT_ERROR_CODE);
        }

        return isValidationPassed;
    }

    @Override
    public BoeResponse<EndDateItemMasterData> endDateItemMaster(EndDateItemMasterRequest endDateItemMasterRequest, UserDetailsImpl userDetails) {

        BoeResponse<EndDateItemMasterData> boeResponse = new BoeResponse<>();
        boeResponse.setData(this.getEndDateItemMasterDataObject(endDateItemMasterRequest));

        if (this.checkMandatoryFieldsForEndDate(endDateItemMasterRequest, boeResponse)) {
            this.itemMasterTransactionService.endDate(endDateItemMasterRequest, userDetails);
        }

        return boeResponse;
    }

    private EndDateItemMasterData getEndDateItemMasterDataObject(EndDateItemMasterRequest endDateItemMasterRequest) {

        EndDateItemMasterData endDateItemMasterData = new EndDateItemMasterData();
        endDateItemMasterData.setItemId(endDateItemMasterRequest.getItemId());
        endDateItemMasterData.setEndDate(endDateItemMasterRequest.getEndDate());

        return endDateItemMasterData;
    }

    private Boolean checkMandatoryFieldsForEndDate(EndDateItemMasterRequest endDateItemMasterRequest,
                                                   BoeResponse<EndDateItemMasterData> boeResponse) {

        Boolean isValidationPassed = Boolean.TRUE;

        EndDateItemMasterData endDateItemMasterData = boeResponse.getData();

        if (endDateItemMasterRequest.getItemId() == null ||
                StringUtils.isBlank(endDateItemMasterRequest.getItemId().toString())) {
            isValidationPassed = Boolean.FALSE;
            endDateItemMasterData.setItemIdErrMsg(CommonConstants.FIELD_IS_MANDATORY);
        }

        if (endDateItemMasterRequest.getEndDate() == null ||
                StringUtils.isBlank(endDateItemMasterRequest.getEndDate().toString())) {
            isValidationPassed = Boolean.FALSE;
            endDateItemMasterData.setEndDateErrMsg(CommonConstants.FIELD_IS_MANDATORY);
        }

        log.info("ItemMasterServiceImpl :: method :: checkMandatoryFieldsForEndDate :: " + isValidationPassed);

        if (!isValidationPassed) {
            this.setErrorObject(boeResponse, CommonConstants.ITEM_MASTER_END_DATE_ERROR_CODE);
        }

        return isValidationPassed;
    }

    @Override
    public BoeResponse<List<FetchItemCodeData>> itemCodeList() {

        BoeResponse<List<FetchItemCodeData>> boeResponse = new BoeResponse<>();
        boeResponse.setData(this.getFetchItemCodeDataListObject());

        return boeResponse;
    }

    private List<FetchItemCodeData> getFetchItemCodeDataListObject() {

        List<FetchItemCodeData> fetchItemCodeDataList = null;

        List<BTVL_MST_BOE_ITEM_TBL> itemList =
                this.btvlMstBoeItemTblRepository.findByPurgeFlag(0);

        if (itemList != null && !itemList.isEmpty()) {
            fetchItemCodeDataList = itemList.stream().map(this::getFetchItemCodeDataObject).collect(Collectors.toList());
        }

        return fetchItemCodeDataList;
    }

    private FetchItemCodeData getFetchItemCodeDataObject(BTVL_MST_BOE_ITEM_TBL bTVL_MST_BOE_ITEM_TBL) {

        FetchItemCodeData fetchItemCodeData = new FetchItemCodeData();
        fetchItemCodeData.setItemCodeName(bTVL_MST_BOE_ITEM_TBL.getItemCode());
        fetchItemCodeData.setItemCodeValue(bTVL_MST_BOE_ITEM_TBL.getItemCode());

        return fetchItemCodeData;
    }

    @Override
    public void itemMasterExcel() {

        List<FetchItemMasterData> fetchItemMasterDataList = this.getFetchItemMasterDataListObject();

        String excelName = "ItemCodeMasterDetails";

        List<String> headerTextList = this.getExcelHeaders();
        List<Map<String, String>> rowMapList = this.getExcelRowsData(fetchItemMasterDataList);

        String excelCheck = ExcelUtil.downloadExcel(headerTextList, rowMapList, excelName, httpServletResponse, this.uploadTmpDir);

        if (excelCheck.equalsIgnoreCase("y")) {
            log.info("ITEM Master Excel Generated successfully");
        } else {
            throw new BoeException("Something went wrong", null, HttpStatus.OK);
        }

    }

    private List<String> getExcelHeaders() {

        List<String> headerTextList = new ArrayList<>();
        headerTextList.add("ITEM CODE");
        headerTextList.add("HSN");
        headerTextList.add("DUTY CATEGORY");
        headerTextList.add("EXEMPTION NOTIFICATION NO.");
        headerTextList.add("END DATE");

        return headerTextList;
    }

    private List<Map<String, String>> getExcelRowsData(List<FetchItemMasterData> excelData) {

        List<Map<String, String>> rowMapList = new ArrayList<>();

        for (FetchItemMasterData row : excelData) {
            Map<String, String> map = new LinkedHashMap<>();

            map.put("ITEM CODE", row.getItemCode());
            map.put("HSN", row.getHsn());
            map.put("DUTY CATEGORY", row.getDutyCategory());
            map.put("EXEMPTION NOTIFICATION NO.", row.getExemptionNotificationNo());
            map.put("END DATE", row.getEndDate() != null ? row.getEndDate().toString() : null);

            rowMapList.add(map);
        }

        return rowMapList;
    }

}
