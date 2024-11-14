package com.airtel.buyer.airtelboe.service.fetchchecklist;

import com.airtel.buyer.airtelboe.dto.common.BoeResponse;
import com.airtel.buyer.airtelboe.dto.fetchchecklist.response.*;
import com.airtel.buyer.airtelboe.exception.BoeException;
import com.airtel.buyer.airtelboe.model.supplier.BTVL_WKF_BOE_CHK_H_TBL;
import com.airtel.buyer.airtelboe.model.supplier.BTVL_WKF_BOE_CHK_INV_TBL;
import com.airtel.buyer.airtelboe.model.supplier.BTVL_WKF_BOE_CHK_L_TBL;
import com.airtel.buyer.airtelboe.repository.supplier.BtvlWkfBoeChkHTblRepository;
import com.airtel.buyer.airtelboe.repository.supplier.BtvlWkfBoeChkInvTblRepository;
import com.airtel.buyer.airtelboe.repository.supplier.BtvlWkfBoeChkLTblRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class FetchCheckListServiceImpl implements FetchCheckListService {

    @Autowired
    private BtvlWkfBoeChkHTblRepository btvlWkfBoeChkHTblRepository;

    @Autowired
    private BtvlWkfBoeChkInvTblRepository btvlWkfBoeChkInvTblRepository;

    @Autowired
    private BtvlWkfBoeChkLTblRepository btvlWkfBoeChkLTblRepository;


    @Override
    public BoeResponse<FetchCheckListData> checkListInformation(BigDecimal shipmentId) {

        BoeResponse<FetchCheckListData> boeResponse = new BoeResponse<>();

        BTVL_WKF_BOE_CHK_H_TBL bTVL_WKF_BOE_CHK_H_TBL =
                this.btvlWkfBoeChkHTblRepository.findByShipmentIdAndPurgeFlag(shipmentId, 0);

        if (bTVL_WKF_BOE_CHK_H_TBL != null) {

            boeResponse.setData(this.getFetchCheckListDataObject(bTVL_WKF_BOE_CHK_H_TBL));

        } else {
            log.info("No record found in BTVL_WKF_BOE_CHK_H_TBL for Shipment Id :: " + shipmentId);
            throw new BoeException("No record found", null, HttpStatus.BAD_REQUEST);
        }

        return boeResponse;
    }

    private FetchCheckListData getFetchCheckListDataObject(BTVL_WKF_BOE_CHK_H_TBL bTVL_WKF_BOE_CHK_H_TBL) {

        FetchCheckListData fetchCheckListData = new FetchCheckListData();
        fetchCheckListData.setCheckListHeaderRes(this.getCheckListHeaderResObject(bTVL_WKF_BOE_CHK_H_TBL));
        fetchCheckListData.setCheckListInvoiceResList(this.getCheckListInvoiceResListObject(bTVL_WKF_BOE_CHK_H_TBL.getBoeHeaderId()));

        return fetchCheckListData;
    }

    private CheckListHeaderRes getCheckListHeaderResObject(BTVL_WKF_BOE_CHK_H_TBL bTVL_WKF_BOE_CHK_H_TBL) {

        CheckListHeaderRes checkListHeaderRes = new CheckListHeaderRes();
        checkListHeaderRes.setCustomerInfoRes(this.getCustomerInfoResObject(bTVL_WKF_BOE_CHK_H_TBL));
        checkListHeaderRes.setSupplierInfoRes(this.getSupplierInfoResObject(bTVL_WKF_BOE_CHK_H_TBL));
        checkListHeaderRes.setShipmentInfoRes(this.getShipmentInfoResObject(bTVL_WKF_BOE_CHK_H_TBL));
        checkListHeaderRes.setCustomInfoRes(this.getCustomInfoResObject(bTVL_WKF_BOE_CHK_H_TBL));
        checkListHeaderRes.setTotalDutyAmt(bTVL_WKF_BOE_CHK_H_TBL.getAttribute2());
        checkListHeaderRes.setMiscCharges(bTVL_WKF_BOE_CHK_H_TBL.getAttribute3());

        return checkListHeaderRes;
    }

    private CustomerInfoRes getCustomerInfoResObject(BTVL_WKF_BOE_CHK_H_TBL bTVL_WKF_BOE_CHK_H_TBL) {

        CustomerInfoRes customerInfoRes = new CustomerInfoRes();
        customerInfoRes.setIecCode(bTVL_WKF_BOE_CHK_H_TBL.getCiIecCode());
        customerInfoRes.setBranchCode(bTVL_WKF_BOE_CHK_H_TBL.getCiBranchCode());
        customerInfoRes.setGstn(bTVL_WKF_BOE_CHK_H_TBL.getCiGstnNo());
        customerInfoRes.setPanNo(bTVL_WKF_BOE_CHK_H_TBL.getCiPanNo());
        customerInfoRes.setAuthorizedDealerCode(bTVL_WKF_BOE_CHK_H_TBL.getCiAdCode());
        customerInfoRes.setLegalEntity(bTVL_WKF_BOE_CHK_H_TBL.getCiLegalEntity());
        customerInfoRes.setImporterName(bTVL_WKF_BOE_CHK_H_TBL.getCiImporterName());
        customerInfoRes.setImporterAddress(bTVL_WKF_BOE_CHK_H_TBL.getCiImporterAddress());
        customerInfoRes.setShipTo(bTVL_WKF_BOE_CHK_H_TBL.getCiShipTo());

        return customerInfoRes;
    }

    private SupplierInfoRes getSupplierInfoResObject(BTVL_WKF_BOE_CHK_H_TBL bTVL_WKF_BOE_CHK_H_TBL) {

        SupplierInfoRes supplierInfoRes = new SupplierInfoRes();
        supplierInfoRes.setSupplierExporterName(bTVL_WKF_BOE_CHK_H_TBL.getSiSuppExpName());
        supplierInfoRes.setSupplierExporterAddress(bTVL_WKF_BOE_CHK_H_TBL.getSiSuppExpAddress());
        supplierInfoRes.setSupplierExporterCountry(bTVL_WKF_BOE_CHK_H_TBL.getSiSuppExpCountry());

        return supplierInfoRes;
    }

    private ShipmentInfoRes getShipmentInfoResObject(BTVL_WKF_BOE_CHK_H_TBL bTVL_WKF_BOE_CHK_H_TBL) {

        ShipmentInfoRes shipmentInfoRes = new ShipmentInfoRes();
        shipmentInfoRes.setCountryOrigin(bTVL_WKF_BOE_CHK_H_TBL.getShiCountryOrigin());
        shipmentInfoRes.setCountryOriginCode(bTVL_WKF_BOE_CHK_H_TBL.getShiCountryOriginCode());
        shipmentInfoRes.setCountryConsignment(bTVL_WKF_BOE_CHK_H_TBL.getShiCountryConsignment());
        shipmentInfoRes.setCountryConsignmentCode(bTVL_WKF_BOE_CHK_H_TBL.getShiCountryConsignmentCode());
        shipmentInfoRes.setPortLoading(bTVL_WKF_BOE_CHK_H_TBL.getShiPortLoading());
        shipmentInfoRes.setGrossShipmentWeigth(bTVL_WKF_BOE_CHK_H_TBL.getShiShipmentWeight());
        shipmentInfoRes.setGrossShipmentVolume(bTVL_WKF_BOE_CHK_H_TBL.getShiShipmentWeightUom());
        shipmentInfoRes.setTotalNoOfPackages(bTVL_WKF_BOE_CHK_H_TBL.getShiTotalNoOfPackages());
        shipmentInfoRes.setVesselAgentName(bTVL_WKF_BOE_CHK_H_TBL.getShiVesselAgentName());
        shipmentInfoRes.setRotationNo(bTVL_WKF_BOE_CHK_H_TBL.getShiRotationNo());
        shipmentInfoRes.setRotationDate(bTVL_WKF_BOE_CHK_H_TBL.getShiRotationDate());
        shipmentInfoRes.setAwbBol(bTVL_WKF_BOE_CHK_H_TBL.getShiAwbBol());

        return shipmentInfoRes;
    }

    private CustomInfoRes getCustomInfoResObject(BTVL_WKF_BOE_CHK_H_TBL bTVL_WKF_BOE_CHK_H_TBL) {

        CustomInfoRes customInfoRes = new CustomInfoRes();
        customInfoRes.setChaCode(bTVL_WKF_BOE_CHK_H_TBL.getCiChaCode());
        customInfoRes.setPdBondNo(bTVL_WKF_BOE_CHK_H_TBL.getCiPdBondNo());
        customInfoRes.setBoeType(bTVL_WKF_BOE_CHK_H_TBL.getCiBoeType());
        customInfoRes.setStampColFreeNoDate(bTVL_WKF_BOE_CHK_H_TBL.getCiStampColFreeNoDt());

        return customInfoRes;
    }

    private List<CheckListInvoiceRes> getCheckListInvoiceResListObject(BigDecimal boeHeaderId) {

        List<CheckListInvoiceRes> checkListInvoiceResList = null;

        List<BTVL_WKF_BOE_CHK_INV_TBL> invoiceList =
                this.btvlWkfBoeChkInvTblRepository.findByBoeHeaderIdAndPurgeFlag(boeHeaderId, 0);

        if (invoiceList != null && !invoiceList.isEmpty()) {
            checkListInvoiceResList = invoiceList.stream().map(this::getCheckListInvoiceResObject).collect(Collectors.toList());
        }

        return checkListInvoiceResList;
    }

    private CheckListInvoiceRes getCheckListInvoiceResObject(BTVL_WKF_BOE_CHK_INV_TBL bTVL_WKF_BOE_CHK_INV_TBL) {

        CheckListInvoiceRes checkListInvoiceRes = new CheckListInvoiceRes();
        checkListInvoiceRes.setIncoTerm(bTVL_WKF_BOE_CHK_INV_TBL.getIncoTerm());
        checkListInvoiceRes.setFreightAmt(bTVL_WKF_BOE_CHK_INV_TBL.getFreight());
        checkListInvoiceRes.setInsuranceAmt(bTVL_WKF_BOE_CHK_INV_TBL.getInsurance());
        checkListInvoiceRes.setExchangeRate(bTVL_WKF_BOE_CHK_INV_TBL.getExchangeRate());
        checkListInvoiceRes.setInvoiceNumber(bTVL_WKF_BOE_CHK_INV_TBL.getInvoiceNumber());
        checkListInvoiceRes.setInvoiceDate(bTVL_WKF_BOE_CHK_INV_TBL.getInvoiceDate());
        checkListInvoiceRes.setInvoiceValue(bTVL_WKF_BOE_CHK_INV_TBL.getInvoiceValue());
        checkListInvoiceRes.setCurrencyCode(bTVL_WKF_BOE_CHK_INV_TBL.getCurrencyCode());
        checkListInvoiceRes.setCheckListLineResList(this.getCheckListLineResListObject(bTVL_WKF_BOE_CHK_INV_TBL.getBoeInvHeaderId()));

        return checkListInvoiceRes;
    }

    private List<CheckListLineRes> getCheckListLineResListObject(BigDecimal boeInvHeaderId) {

        List<CheckListLineRes> checkListLineResList = null;

        List<BTVL_WKF_BOE_CHK_L_TBL> lineList =
                this.btvlWkfBoeChkLTblRepository.findByBoeInvHeaderIdAndPurgeFlag(boeInvHeaderId, 0);

        if (lineList != null && !lineList.isEmpty()) {
            checkListLineResList = lineList.stream().map(this::getCheckListLineResObject).collect(Collectors.toList());
        }

        return checkListLineResList;
    }

    private CheckListLineRes getCheckListLineResObject(BTVL_WKF_BOE_CHK_L_TBL bTVL_WKF_BOE_CHK_L_TBL) {

        CheckListLineRes checkListLineRes = new CheckListLineRes();
        checkListLineRes.setItemCodeAndDesc(bTVL_WKF_BOE_CHK_L_TBL.getItemCodeAndDesc());
        checkListLineRes.setItemQty(bTVL_WKF_BOE_CHK_L_TBL.getItemQty());
        checkListLineRes.setUnitPrice(bTVL_WKF_BOE_CHK_L_TBL.getUnitPrice());
        checkListLineRes.setHsn(bTVL_WKF_BOE_CHK_L_TBL.getHsn());
        checkListLineRes.setAssessableValue(bTVL_WKF_BOE_CHK_L_TBL.getAssessableValue());
        checkListLineRes.setBasicCustomDutyPer(bTVL_WKF_BOE_CHK_L_TBL.getBasicCustomDutyPer());
        checkListLineRes.setSwsPer(bTVL_WKF_BOE_CHK_L_TBL.getSwsPer());
        checkListLineRes.setIgstPer(bTVL_WKF_BOE_CHK_L_TBL.getIgstPer());
        checkListLineRes.setBasicCustomDuty(bTVL_WKF_BOE_CHK_L_TBL.getBasicCustomDuty());
        checkListLineRes.setSws(bTVL_WKF_BOE_CHK_L_TBL.getSws());
        checkListLineRes.setIgst(bTVL_WKF_BOE_CHK_L_TBL.getIgst());
        checkListLineRes.setCompensationCessOnIgst(bTVL_WKF_BOE_CHK_L_TBL.getCompensationCessOnIgst());
        checkListLineRes.setDutyOtherThanGst(bTVL_WKF_BOE_CHK_L_TBL.getDutyOtherThanGst());
        checkListLineRes.setTotalDuty(bTVL_WKF_BOE_CHK_L_TBL.getTotalDuty());
        checkListLineRes.setAntiDumpingPer(bTVL_WKF_BOE_CHK_L_TBL.getAntiDumpingPer());
        checkListLineRes.setAntiDumpingAmt(bTVL_WKF_BOE_CHK_L_TBL.getAntiDumpingAmt());
        checkListLineRes.setExemptionNotn(bTVL_WKF_BOE_CHK_L_TBL.getExemptionNotn());
        checkListLineRes.setWpcLicenseNo(bTVL_WKF_BOE_CHK_L_TBL.getWpcLicenseNo());
        checkListLineRes.setEpcgLicenseNo(bTVL_WKF_BOE_CHK_L_TBL.getEpcgLicenceNo());

        return checkListLineRes;
    }


}
