package com.airtel.buyer.airtelboe.controller;

import com.airtel.buyer.airtelboe.dto.addepcgmaster.request.AddEpcgMasterRequest;
import com.airtel.buyer.airtelboe.dto.addepcgmaster.response.AddEpcgMasterData;
import com.airtel.buyer.airtelboe.dto.additemmaster.request.AddItemMasterRequest;
import com.airtel.buyer.airtelboe.dto.additemmaster.response.AddItemMasterData;
import com.airtel.buyer.airtelboe.dto.addwpcmaster.request.AddWpcMasterRequest;
import com.airtel.buyer.airtelboe.dto.addwpcmaster.response.AddWpcMasterData;
import com.airtel.buyer.airtelboe.dto.ccoaction.request.CcoActionRequest;
import com.airtel.buyer.airtelboe.dto.ccoaction.response.CcoActionData;
import com.airtel.buyer.airtelboe.dto.chareassignmentaction.request.ChaReassignmentRequest;
import com.airtel.buyer.airtelboe.dto.chareassignmentaction.response.ChaReassignmentResponse;
import com.airtel.buyer.airtelboe.dto.common.BoeResponse;
import com.airtel.buyer.airtelboe.dto.country.response.CheckFtaData;
import com.airtel.buyer.airtelboe.dto.epcginquiry.request.EpcgInquiryRequest;
import com.airtel.buyer.airtelboe.dto.epcginquiry.response.EpcgInquiryResponseData;
import com.airtel.buyer.airtelboe.dto.country.response.CountryData;
import com.airtel.buyer.airtelboe.dto.countryphonecode.response.CountryPhoneCodeData;
import com.airtel.buyer.airtelboe.dto.currency.response.CurrencyData;
import com.airtel.buyer.airtelboe.dto.editepcgmaster.request.EditEpcgMasterRequest;
import com.airtel.buyer.airtelboe.dto.editepcgmaster.response.EditEpcgMasterData;
import com.airtel.buyer.airtelboe.dto.edititemmaster.request.EditItemMasterRequest;
import com.airtel.buyer.airtelboe.dto.edititemmaster.response.EditItemMasterData;
import com.airtel.buyer.airtelboe.dto.editwpcmaster.request.EditWpcMasterRequest;
import com.airtel.buyer.airtelboe.dto.editwpcmaster.response.EditWpcMasterData;
import com.airtel.buyer.airtelboe.dto.enddateepcgmaster.request.EndDateEpcgMasterRequest;
import com.airtel.buyer.airtelboe.dto.enddateepcgmaster.response.EndDateEpcgMasterData;
import com.airtel.buyer.airtelboe.dto.enddateitemmaster.request.EndDateItemMasterRequest;
import com.airtel.buyer.airtelboe.dto.enddateitemmaster.response.EndDateItemMasterData;
import com.airtel.buyer.airtelboe.dto.enddatewpcmaster.request.EndDateWpcMasterRequest;
import com.airtel.buyer.airtelboe.dto.enddatewpcmaster.response.EndDateWpcMasterData;
import com.airtel.buyer.airtelboe.dto.fetchantidumpingmaster.response.FetchAntiDumpingMasterData;
import com.airtel.buyer.airtelboe.dto.fetchboedoc.response.FetchBoeDocData;
import com.airtel.buyer.airtelboe.dto.ddstatus.request.DdStatusRequest;
import com.airtel.buyer.airtelboe.dto.ddstatus.response.DdStatusResponse;
import com.airtel.buyer.airtelboe.dto.fetchboemismatch.response.BoeMismatchData;
import com.airtel.buyer.airtelboe.dto.fetchcco.request.FetchCcoRequest;
import com.airtel.buyer.airtelboe.dto.fetchcco.response.FetchCcoData;
import com.airtel.buyer.airtelboe.dto.fetchchaagentlist.response.ChaAgentListData;
import com.airtel.buyer.airtelboe.dto.fetchchamaster.response.FetchChaMasterData;
import com.airtel.buyer.airtelboe.dto.fetchchareassignmentpagedata.request.FetchChaReassignmentPageDataRequest;
import com.airtel.buyer.airtelboe.dto.fetchchareassignmentpagedata.response.ChaReassignmentPageData;
import com.airtel.buyer.airtelboe.dto.fetchchecklist.response.FetchCheckListData;
import com.airtel.buyer.airtelboe.dto.fetchdutycategory.response.FetchDutyCategoryData;
import com.airtel.buyer.airtelboe.dto.fetchepcgmaster.response.FetchEpcgMasterData;
import com.airtel.buyer.airtelboe.dto.fetchftamaster.response.FetchFtaMasterData;
import com.airtel.buyer.airtelboe.dto.fetchiecmaster.response.FetchIecMasterData;
import com.airtel.buyer.airtelboe.dto.fetchip.request.FetchIpRequest;
import com.airtel.buyer.airtelboe.dto.fetchip.response.FetchIpData;
import com.airtel.buyer.airtelboe.dto.fetchitemcode.response.FetchItemCodeData;
import com.airtel.buyer.airtelboe.dto.fetchitemmaster.response.FetchItemMasterData;
import com.airtel.buyer.airtelboe.dto.fetchlegalentity.response.FetchLegalEntityData;
import com.airtel.buyer.airtelboe.dto.fetchofflinecco.request.FetchOfflineCcoRequest;
import com.airtel.buyer.airtelboe.dto.fetchofflinecco.response.FetchOfflineCcoData;
import com.airtel.buyer.airtelboe.dto.fetchportmaster.response.FetchPortMasterData;
import com.airtel.buyer.airtelboe.dto.fetchprotestedboe.request.FetchProtestedRequest;
import com.airtel.buyer.airtelboe.dto.fetchprotestedboe.response.FetchProtestedData;
import com.airtel.buyer.airtelboe.dto.fetchshipment.response.FetchShipmentData;
import com.airtel.buyer.airtelboe.dto.fetchwpcmaster.response.FetchWpcMasterData;
import com.airtel.buyer.airtelboe.dto.indiancity.response.IndianCityData;
import com.airtel.buyer.airtelboe.dto.inforequired.response.InfoRequiredData;
import com.airtel.buyer.airtelboe.dto.ipapproval.request.IpApprovalRequest;
import com.airtel.buyer.airtelboe.dto.ipapproval.response.IpApprovalData;
import com.airtel.buyer.airtelboe.dto.ipreject.request.IpRejectRequest;
import com.airtel.buyer.airtelboe.dto.ipreject.response.IpRejectData;
import com.airtel.buyer.airtelboe.dto.iprfi.request.IpRfiRequest;
import com.airtel.buyer.airtelboe.dto.iprfi.response.IpRfiData;
import com.airtel.buyer.airtelboe.dto.offlineccoapproval.request.OfflineCcoApprovalRequest;
import com.airtel.buyer.airtelboe.dto.offlineccoapproval.response.OfflineCcoApprovalData;
import com.airtel.buyer.airtelboe.dto.offlineccoretrigger.request.OfflineCcoRetriggerRequest;
import com.airtel.buyer.airtelboe.dto.offlineccoretrigger.response.OfflineCcoRetriggerData;
import com.airtel.buyer.airtelboe.dto.port.response.PortData;
import com.airtel.buyer.airtelboe.dto.protestedboeaction.request.ProtestedBoeActionRequest;
import com.airtel.buyer.airtelboe.dto.protestedboeaction.response.ProtestedBoeActionData;
import com.airtel.buyer.airtelboe.dto.scm.stage2.request.SubmitStage2Request;
import com.airtel.buyer.airtelboe.dto.scm.stage2.response.SubmitStage2Response;
import com.airtel.buyer.airtelboe.dto.scm.stage4.request.ScmStage4RejectRequest;
import com.airtel.buyer.airtelboe.dto.scm.stage4.request.SubmitStage4Request;
import com.airtel.buyer.airtelboe.dto.scm.stage4.response.ScmStage4RejectResponse;
import com.airtel.buyer.airtelboe.dto.scm.stage4.response.SubmitStage4Response;
import com.airtel.buyer.airtelboe.dto.scmdashboard.request.ScmDashRequest;
import com.airtel.buyer.airtelboe.dto.scmdashboard.response.ScmDashResponse;
import com.airtel.buyer.airtelboe.dto.scmloblist.response.ScmLobListResponse;
import com.airtel.buyer.airtelboe.dto.scmoulist.request.OuListRequest;
import com.airtel.buyer.airtelboe.dto.scmoulist.response.OuListResponseData;
import com.airtel.buyer.airtelboe.dto.scmrfiresponse.request.ScmRfiResponseReq;
import com.airtel.buyer.airtelboe.dto.scmrfiresponse.response.ScmRfiResponseData;
import com.airtel.buyer.airtelboe.dto.shipmentstatusreport.request.ShipmentStatusReportRequest;
import com.airtel.buyer.airtelboe.dto.shipmentstatusreport.response.ShipmentStatusReportResponse;
import com.airtel.buyer.airtelboe.dto.statustracker.response.BoeStatusTrackerData;
import com.airtel.buyer.airtelboe.dto.wpcinfo.request.WpcInfoRequest;
import com.airtel.buyer.airtelboe.dto.wpcinfo.response.WpcInfoResponse;
import com.airtel.buyer.airtelboe.dto.wpcsubmitted.request.WpcSubmitRequest;
import com.airtel.buyer.airtelboe.dto.wpcsubmitted.response.WpcSubmitResponse;
import com.airtel.buyer.airtelboe.exception.BoeException;
import com.airtel.buyer.airtelboe.model.Role;
import com.airtel.buyer.airtelboe.dto.wpcinquiry.request.WpcInquiryRequest;
import com.airtel.buyer.airtelboe.dto.wpcinquiry.response.WpcInquiryResponseData;

import com.airtel.buyer.airtelboe.service.antidumpingmaster.AntiDumpingMasterService;
import com.airtel.buyer.airtelboe.service.boedoc.BoeDocService;
import com.airtel.buyer.airtelboe.service.cco.CcoService;
import com.airtel.buyer.airtelboe.service.chamaster.ChaMasterService;
import com.airtel.buyer.airtelboe.service.chareassignmentaction.ChaReassignmentActionService;
import com.airtel.buyer.airtelboe.service.country.CountryService;
import com.airtel.buyer.airtelboe.service.countryphonecode.CountryPhoneCodeService;
import com.airtel.buyer.airtelboe.service.currency.CurrencyService;
import com.airtel.buyer.airtelboe.service.ddstatus.DdStatusService;
import com.airtel.buyer.airtelboe.service.dutycategory.DutyCategoryService;
import com.airtel.buyer.airtelboe.service.epcginquiry.EpcgInquiryService;
import com.airtel.buyer.airtelboe.service.epcgmaster.EpcgMasterService;
import com.airtel.buyer.airtelboe.service.fetchagentlist.ChaReAssignmentService;
import com.airtel.buyer.airtelboe.service.fetchboemismatch.FetchBoeMismatchService;
import com.airtel.buyer.airtelboe.service.fetchchecklist.FetchCheckListService;
import com.airtel.buyer.airtelboe.service.fetchshipment.FetchShipmentService;
import com.airtel.buyer.airtelboe.service.ftamaster.FtaMasterService;
import com.airtel.buyer.airtelboe.service.iecmaster.IecMasterService;
import com.airtel.buyer.airtelboe.service.indiancity.IndianCityService;
import com.airtel.buyer.airtelboe.service.inforequired.BoeInfoRequiredService;
import com.airtel.buyer.airtelboe.service.ip.IpService;
import com.airtel.buyer.airtelboe.service.itemmaster.ItemMasterService;
import com.airtel.buyer.airtelboe.service.legalentity.LegalEntityService;
import com.airtel.buyer.airtelboe.service.offlinecco.OfflineCcoService;
import com.airtel.buyer.airtelboe.service.portmaster.PortMasterService;
import com.airtel.buyer.airtelboe.service.protestedboe.ProtestedBoeService;
import com.airtel.buyer.airtelboe.service.scm.ScmService;
import com.airtel.buyer.airtelboe.service.scmdashboard.ScmDashboardService;
import com.airtel.buyer.airtelboe.service.scmloboulist.ScmLobOuListService;
import com.airtel.buyer.airtelboe.service.scmrfiresponse.ScmRfiResponseService;
import com.airtel.buyer.airtelboe.service.shipmentstatusreport.ShipmentStatusReportService;
import com.airtel.buyer.airtelboe.service.statustracker.StatusTrackerService;
import com.airtel.buyer.airtelboe.service.wpcmaster.WpcMasterService;
import com.airtel.buyer.airtelboe.service.wpcinquiry.WpcInquiryService;
import com.airtel.buyer.airtelboe.service.WpcInfo.WpcInfoService;
import com.airtel.buyer.airtelboe.user.UserDetailsImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/api/v1/boe")
public class AirtelBoeController {

    @Autowired
    private FetchShipmentService fetchShipmentService;

    @Autowired
    private FetchCheckListService fetchCheckListService;

    @Autowired
    private ScmLobOuListService scmLobOuListService;

    @Autowired
    private ScmDashboardService scmDashboardService;

    @Autowired
    private FetchBoeMismatchService fetchBoeMismatchService;

    @Autowired
    private BoeInfoRequiredService boeInfoRequiredService;

    @Autowired
    private StatusTrackerService statusTrackerService;

    @Autowired
    private PortMasterService portMasterService;

    @Autowired
    private IecMasterService iecMasterService;

    @Autowired
    private ChaMasterService chaMasterService;

    @Autowired
    private FtaMasterService ftaMasterService;

    @Autowired
    private AntiDumpingMasterService antiDumpingMasterService;

    @Autowired
    private EpcgMasterService epcgMasterService;

    @Autowired
    private WpcMasterService wpcMasterService;

    @Autowired
    private ItemMasterService itemMasterService;

    @Autowired
    private LegalEntityService legalEntityService;

    @Autowired
    private DutyCategoryService dutyCategoryService;

    @Autowired
    private ProtestedBoeService protestedBoeService;

    @Autowired
    private OfflineCcoService offlineCcoService;

    @Autowired
    private CountryService countryService;

    @Autowired
    private CcoService ccoService;

    @Autowired
    private IpService ipService;

    @Autowired
    private BoeDocService boeDocService;

    @Autowired
    private CurrencyService currencyService;

    @Autowired
    private IndianCityService indianCityService;

    @Autowired
    private CountryPhoneCodeService countryPhoneCodeService;

    @Autowired
    private ChaReAssignmentService chaReAssignmentService;

    @Autowired
    private ChaReassignmentActionService chaReassignmentActionService;

    @Autowired
    private ScmService scmService;

    @Autowired
    private WpcInfoService wpcInfoService;

    @Autowired
    private DdStatusService ddStatusService;

    @Autowired
    private ScmRfiResponseService scmRfiResponseService;
    
    @Autowired
    private WpcInquiryService wpcInquiryService;

    @Autowired
    private ShipmentStatusReportService shipmentStatusReportService;

    @Autowired
    private EpcgInquiryService epcgInquiryService;

    @GetMapping("/shipment-info/{shipmentId}")
    public ResponseEntity<BoeResponse<FetchShipmentData>> fetchShipmentInformation(@PathVariable BigDecimal shipmentId) {
        log.info("Entered :: AirtelBoeController :: method :: fetchShipmentInformation");
        log.info("Shipment Id :: " + shipmentId);

        BoeResponse<FetchShipmentData> boeResponse = this.fetchShipmentService.shipmentInformation(shipmentId);

        log.info("Fetch Shipment Information Response :: " + boeResponse);
        log.info("Exit :: AirtelBoeController :: method :: fetchShipmentInformation");
        return new ResponseEntity<>(boeResponse, HttpStatus.OK);
    }

    @GetMapping("/checklist-info/{shipmentId}")
    public ResponseEntity<BoeResponse<FetchCheckListData>> fetchCheckListInformation(@PathVariable BigDecimal shipmentId) {
        log.info("Entered :: AirtelBoeController :: method :: fetchCheckListInformation");
        log.info("Shipment Id :: " + shipmentId);

        BoeResponse<FetchCheckListData> boeResponse = this.fetchCheckListService.checkListInformation(shipmentId);

        log.info("Fetch CheckList Information Response :: " + boeResponse);
        log.info("Exit :: AirtelBoeController :: method :: fetchCheckListInformation");
        return new ResponseEntity<>(boeResponse, HttpStatus.OK);
    }

    @GetMapping("/mismatch-info/{shipmentId}")
    public ResponseEntity<BoeResponse<BoeMismatchData>> fetchBoeMismatchInformation(@PathVariable BigDecimal shipmentId) {
        log.info("Entered :: AirtelBoeController :: method :: fetchBoeMismatchInformation");
        log.info("Shipment Id :: " + shipmentId);

        BoeResponse<BoeMismatchData> boeResponse = this.fetchBoeMismatchService.mismatchInformation(shipmentId);

        log.info("Fetch Boe Mismatch Information Response :: " + boeResponse);
        log.info("Exit :: AirtelBoeController :: method :: fetchBoeMismatchInformation");
        return new ResponseEntity<>(boeResponse, HttpStatus.OK);
    }

    @GetMapping("/info-required/{shipmentId}")
    public ResponseEntity<BoeResponse<InfoRequiredData>> InfoRequired(@PathVariable BigDecimal shipmentId) {
        log.info("Entered :: AirtelBoeController :: method :: InfoRequired");
        log.info("Shipment Id :: " + shipmentId);
        BoeResponse<InfoRequiredData> boeResponse = this.boeInfoRequiredService.fetchInfoRequiredData(shipmentId);
        log.info("Fetch InfoRequired Information Response :: " + boeResponse);
        log.info("Exit :: AirtelBoeController :: method :: InfoRequired");
        return new ResponseEntity<>(boeResponse, HttpStatus.OK);
    }

    @GetMapping("/status-tracker/{shipmentId}")
    public ResponseEntity<BoeResponse<BoeStatusTrackerData>> fetchStatusTrackerInformation(@PathVariable BigDecimal shipmentId) {
        log.info("Entered :: BoeController :: method :: fetchStatusTrackerInformation");
        log.info("Shipment Id :: " + shipmentId);
        BoeResponse<BoeStatusTrackerData> boeResponse = this.statusTrackerService.trackStatus(shipmentId);
        log.info("Fetch Shipment Information Response :: " + boeResponse);
        log.info("Exit :: BoeController :: method :: fetchStatusTrackerInformation");
        return new ResponseEntity<>(boeResponse, HttpStatus.OK);
    }

    @GetMapping("scm-lob-list")
    ResponseEntity<BoeResponse<List<ScmLobListResponse>>> getScmLobList(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        log.info("Inside AirtelBoeController :: method :: getScmLobList");

        BoeResponse<List<ScmLobListResponse>> response = null;
        //Checking role of user to grant access accordingly
        if (checkRole(userDetails, "SCM_ADMIN")) {
            response = scmLobOuListService.getLobNames(userDetails.getUsername(), "SCM_ADMIN");

        } else if (checkRole(userDetails, "SCM_LOB")) {
            response = scmLobOuListService.getLobNames(userDetails.getUsername(), "SCM_LOB");

        } else if (checkRole(userDetails, "SCM_GVIEW")) {
            response = scmLobOuListService.getLobNames(userDetails.getUsername(), "SCM_GVIEW");

        } else {
            log.info("No Role found or exception raised while checking role");
            throw new BoeException("No Data Found", null, HttpStatus.UNAUTHORIZED);
        }

        log.info("Lob response :: " + response);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("scm-ou-list")
    ResponseEntity<BoeResponse<List<OuListResponseData>>> getScmOuList(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                                       @RequestBody OuListRequest ouListRequest) {
        log.info("Inside AirtelBoeController :: method :: getScmOuList");
        BoeResponse<List<OuListResponseData>> response = null;

        //Checking role of user to grant access accordingly
        if (checkRole(userDetails, "SCM_ADMIN") || checkRole(userDetails, "SCM_LOB") ||
                checkRole(userDetails, "SCM_GVIEW")) {
            response = scmLobOuListService.getOuList(ouListRequest.getLobName());
        } else {
            log.info("No Role found or exception raised while checking role");
            throw new BoeException("No Data Found", null, HttpStatus.UNAUTHORIZED);
        }
        log.info("Lob response :: " + response);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public Boolean checkRole(UserDetailsImpl userDetails, String role) {
        for (GrantedAuthority obj : userDetails.getAuthorities()) {
            if (Role.valueOf(obj.getAuthority()).getRoleValue().equalsIgnoreCase(role)) {
                return true;
            }
        }
        return false;
    }

    @PostMapping("/scmdashboard")
    public ResponseEntity<BoeResponse<ScmDashResponse>> getScmDashData(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                                       @RequestBody ScmDashRequest scmDashRequest,
                                                                       @RequestParam(name = "page", defaultValue = "1") int page,
                                                                       @RequestParam(name = "size", defaultValue = "25") int size) {


        log.info("Inside AirtelBoeController :: method :: getScmDashData :: request :: " + scmDashRequest);
        BoeResponse<ScmDashResponse> response = null;

        //Checking role of user to grant access accordingly
        if (checkRole(userDetails, "SCM_ADMIN")) {
            response = scmDashboardService.getScmDashData(page, size, userDetails.getUsername(), "scm_admin", scmDashRequest);

        } else if (checkRole(userDetails, "SCM_LOB")) {
            response = scmDashboardService.getScmDashData(page, size, userDetails.getUsername(), "scm_lob", scmDashRequest);

        } else if (checkRole(userDetails, "SCM_GVIEW")) {
            response = scmDashboardService.getScmDashData(page, size, userDetails.getUsername(), "scm_lob", scmDashRequest);

        } else {
            log.info("No Role found or exception raised while checking role");
            throw new BoeException("No Data Found", null, HttpStatus.UNAUTHORIZED);
        }
        log.info("Exiting AirtelBoeController :: response :: " + response);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/scmdashexcel")
    public ResponseEntity<BoeResponse<ScmDashResponse>> getScmDashExcel(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                                        @RequestBody ScmDashRequest scmDashRequest,
                                                                        @RequestParam(name = "page", defaultValue = "1") int page,
                                                                        @RequestParam(name = "size", defaultValue = "25") int size,
                                                                        HttpServletResponse httpServletResponse) {


        log.info("Inside AirtelBoeController :: method :: getScmDashExcel :: request :: " + scmDashRequest);
        BoeResponse<ScmDashResponse> response = null;

        //Checking role of user to grant access accordingly
        if (checkRole(userDetails, "SCM_ADMIN")) {
            response = scmDashboardService.getScmDashExcelData(page, size, userDetails.getUsername(), "scm_admin", scmDashRequest, httpServletResponse);

        } else if (checkRole(userDetails, "SCM_LOB")) {
            response = scmDashboardService.getScmDashExcelData(page, size, userDetails.getUsername(), "scm_lob", scmDashRequest, httpServletResponse);

        } else if (checkRole(userDetails, "SCM_GVIEW")) {
            response = scmDashboardService.getScmDashExcelData(page, size, userDetails.getUsername(), "scm_lob", scmDashRequest, httpServletResponse);

        } else {
            log.info("No Role found or exception raised while checking role");
            throw new BoeException("No Data Found", null, HttpStatus.UNAUTHORIZED);
        }
        log.info("Exiting AirtelBoeController :: method :: getScmDashExcel :: response :: " + response);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/scmdashrawexcel")
    public ResponseEntity<BoeResponse<ScmDashResponse>> getScmDashRawExcel(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                                           @RequestBody ScmDashRequest scmDashRequest,
                                                                           @RequestParam(name = "page", defaultValue = "1") int page,
                                                                           @RequestParam(name = "size", defaultValue = "1000") int size,
                                                                           HttpServletResponse httpServletResponse) {


        log.info("Inside AirtelBoeController :: method :: getScmDashExcel :: request :: " + scmDashRequest);
        BoeResponse<ScmDashResponse> response = null;

        //Checking role of user to grant access accordingly
        if (checkRole(userDetails, "SCM_ADMIN")) {
            response = scmDashboardService.getScmDashRawExcelData(page, size, userDetails.getUsername(), "scm_admin", scmDashRequest, httpServletResponse);

        } else if (checkRole(userDetails, "SCM_LOB")) {
            response = scmDashboardService.getScmDashRawExcelData(page, size, userDetails.getUsername(), "scm_lob", scmDashRequest, httpServletResponse);

        } else if (checkRole(userDetails, "SCM_GVIEW")) {
            response = scmDashboardService.getScmDashRawExcelData(page, size, userDetails.getUsername(), "scm_lob", scmDashRequest, httpServletResponse);

        } else {
            log.info("No Role found or exception raised while checking role");
            throw new BoeException("No Data Found", null, HttpStatus.UNAUTHORIZED);
        }
        log.info("Exiting AirtelBoeController :: method :: getScmDashExcel :: response :: " + response);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/port-master")
    public ResponseEntity<BoeResponse<List<FetchPortMasterData>>> fetchPortMasterInformation() {
        log.info("Entered :: AirtelBoeController :: method :: fetchPortMasterInformation");

        BoeResponse<List<FetchPortMasterData>> boeResponse = this.portMasterService.portMasterInformation();

        log.info("Fetch Port Master Response :: " + boeResponse);
        log.info("Exit :: AirtelBoeController :: method :: fetchPortMasterInformation");
        return new ResponseEntity<>(boeResponse, HttpStatus.OK);
    }

    @GetMapping("/ports")
    public ResponseEntity<BoeResponse<List<PortData>>> fetchPorts() {
        log.info("Entered :: AirtelBoeController :: method :: fetchPorts");

        BoeResponse<List<PortData>> boeResponse = this.portMasterService.portList();

        log.info("Ports Response :: " + boeResponse);
        log.info("Exit :: AirtelBoeController :: method :: fetchPorts");
        return new ResponseEntity<>(boeResponse, HttpStatus.OK);
    }

    @GetMapping("/indian-ports")
    public ResponseEntity<BoeResponse<List<PortData>>> fetchIndianPorts() {
        log.info("Entered :: AirtelBoeController :: method :: fetchIndianPorts");

        BoeResponse<List<PortData>> boeResponse = this.portMasterService.indianPortList();

        log.info("Indian Ports Response :: " + boeResponse);
        log.info("Exit :: AirtelBoeController :: method :: fetchIndianPorts");
        return new ResponseEntity<>(boeResponse, HttpStatus.OK);
    }

    @GetMapping("/port-master-excel")
    public void downloadPortMasterExcel() {
        log.info("Entered :: AirtelBoeController :: method :: downloadPortMasterExcel");

        this.portMasterService.portMasterExcel();

        log.info("Exit :: AirtelBoeController :: method :: downloadPortMasterExcel");
    }

    @GetMapping("/iec-master")
    public ResponseEntity<BoeResponse<List<FetchIecMasterData>>> fetchIecMasterInformation() {
        log.info("Entered :: AirtelBoeController :: method :: fetchIecMasterInformation");

        BoeResponse<List<FetchIecMasterData>> boeResponse = this.iecMasterService.iecMasterInformation();

        log.info("Fetch IEC Master Response :: " + boeResponse);
        log.info("Exit :: AirtelBoeController :: method :: fetchIecMasterInformation");
        return new ResponseEntity<>(boeResponse, HttpStatus.OK);
    }

    @GetMapping("/iec-master-excel")
    public void downloadIecMasterExcel() {
        log.info("Entered :: AirtelBoeController :: method :: downloadIecMasterExcel");

        this.iecMasterService.iecMasterExcel();

        log.info("Exit :: AirtelBoeController :: method :: downloadIecMasterExcel");
    }

    @GetMapping("/cha-master")
    public ResponseEntity<BoeResponse<List<FetchChaMasterData>>> fetchChaMasterInformation() {
        log.info("Entered :: AirtelBoeController :: method :: fetchChaMasterInformation");

        BoeResponse<List<FetchChaMasterData>> boeResponse = this.chaMasterService.chaMasterInformation();

        log.info("Fetch CHA Master Response :: " + boeResponse);
        log.info("Exit :: AirtelBoeController :: method :: fetchChaMasterInformation");
        return new ResponseEntity<>(boeResponse, HttpStatus.OK);
    }

    @GetMapping("/cha-master-excel")
    public void downloadChaMasterExcel() {
        log.info("Entered :: AirtelBoeController :: method :: downloadChaMasterExcel");

        this.chaMasterService.chaMasterExcel();

        log.info("Exit :: AirtelBoeController :: method :: downloadChaMasterExcel");
    }

    @GetMapping("/fta-master")
    public ResponseEntity<BoeResponse<List<FetchFtaMasterData>>> fetchFtaMasterInformation() {
        log.info("Entered :: AirtelBoeController :: method :: fetchFtaMasterInformation");

        BoeResponse<List<FetchFtaMasterData>> boeResponse = this.ftaMasterService.ftaMasterInformation();

        log.info("Fetch FTA Master Response :: " + boeResponse);
        log.info("Exit :: AirtelBoeController :: method :: fetchFtaMasterInformation");
        return new ResponseEntity<>(boeResponse, HttpStatus.OK);
    }

    @GetMapping("/fta-master-excel")
    public void downloadFtaMasterExcel() {
        log.info("Entered :: AirtelBoeController :: method :: downloadFtaMasterExcel");

        this.ftaMasterService.ftaMasterExcel();

        log.info("Exit :: AirtelBoeController :: method :: downloadFtaMasterExcel");
    }

    @GetMapping("/anti-dumping-master")
    public ResponseEntity<BoeResponse<List<FetchAntiDumpingMasterData>>> fetchAntiDumpingMasterInformation() {
        log.info("Entered :: AirtelBoeController :: method :: fetchAntiDumpingMasterInformation");

        BoeResponse<List<FetchAntiDumpingMasterData>> boeResponse = this.antiDumpingMasterService.antiDumpingMasterInformation();

        log.info("Fetch Anti Dumping Master Response :: " + boeResponse);
        log.info("Exit :: AirtelBoeController :: method :: fetchAntiDumpingMasterInformation");
        return new ResponseEntity<>(boeResponse, HttpStatus.OK);
    }

    @GetMapping("/anti-dumping-master-excel")
    public void downloadAntiDumpingMasterExcel() {
        log.info("Entered :: AirtelBoeController :: method :: downloadAntiDumpingMasterExcel");

        this.antiDumpingMasterService.antiDumpingMasterExcel();

        log.info("Exit :: AirtelBoeController :: method :: downloadAntiDumpingMasterExcel");
    }

    @GetMapping("/epcg-master")
    public ResponseEntity<BoeResponse<List<FetchEpcgMasterData>>> fetchEpcgMasterInformation() {
        log.info("Entered :: AirtelBoeController :: method :: fetchEpcgMasterInformation");

        BoeResponse<List<FetchEpcgMasterData>> boeResponse = this.epcgMasterService.epcgMasterInformation();

        log.info("Fetch EPCG Master Response :: " + boeResponse);
        log.info("Exit :: AirtelBoeController :: method :: fetchEpcgMasterInformation");
        return new ResponseEntity<>(boeResponse, HttpStatus.OK);
    }

    @PostMapping("/epcg-master-add")
    public ResponseEntity<BoeResponse<AddEpcgMasterData>> addEpcgMasterInformation(@RequestBody AddEpcgMasterRequest addEpcgMasterRequest,
                                                                                   @AuthenticationPrincipal UserDetailsImpl userDetails) {
        log.info("Entered :: AirtelBoeController :: method :: addEpcgMasterInformation");
        log.info("Add EPCG Master Request :: " + addEpcgMasterRequest);

        if (addEpcgMasterRequest == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        BoeResponse<AddEpcgMasterData> boeResponse = this.epcgMasterService.addEpcgMaster(addEpcgMasterRequest, userDetails);

        log.info("Add EPCG Master Response :: " + boeResponse);
        log.info("Exit :: AirtelBoeController :: method :: addEpcgMasterInformation");
        return new ResponseEntity<>(boeResponse, HttpStatus.OK);
    }

    @PutMapping("/epcg-master-edit")
    public ResponseEntity<BoeResponse<EditEpcgMasterData>> editEpcgMasterInformation(@RequestBody EditEpcgMasterRequest editEpcgMasterRequest,
                                                                                     @AuthenticationPrincipal UserDetailsImpl userDetails) {
        log.info("Entered :: AirtelBoeController :: method :: editEpcgMasterInformation");
        log.info("Edit EPCG Master Request :: " + editEpcgMasterRequest);

        if (editEpcgMasterRequest == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        BoeResponse<EditEpcgMasterData> boeResponse = this.epcgMasterService.editEpcgMaster(editEpcgMasterRequest, userDetails);

        log.info("Edit EPCG Master Response :: " + boeResponse);
        log.info("Exit :: AirtelBoeController :: method :: editEpcgMasterInformation");
        return new ResponseEntity<>(boeResponse, HttpStatus.OK);
    }

    @PutMapping("/epcg-master-enddate")
    public ResponseEntity<BoeResponse<EndDateEpcgMasterData>> endDateEpcgMasterInformation(@RequestBody EndDateEpcgMasterRequest endDateEpcgMasterRequest,
                                                                                           @AuthenticationPrincipal UserDetailsImpl userDetails) {
        log.info("Entered :: AirtelBoeController :: method :: endDateEpcgMasterInformation");
        log.info("End Date EPCG Master Request :: " + endDateEpcgMasterRequest);

        if (endDateEpcgMasterRequest == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        BoeResponse<EndDateEpcgMasterData> boeResponse = this.epcgMasterService.endDateEpcgMaster(endDateEpcgMasterRequest, userDetails);

        log.info("End Date EPCG Master Response :: " + boeResponse);
        log.info("Exit :: AirtelBoeController :: method :: endDateEpcgMasterInformation");
        return new ResponseEntity<>(boeResponse, HttpStatus.OK);
    }

    @GetMapping("/epcg-master-excel")
    public void downloadEpcgMasterExcel() {
        log.info("Entered :: AirtelBoeController :: method :: downloadEpcgMasterExcel");

        this.epcgMasterService.epcgMasterExcel();

        log.info("Exit :: AirtelBoeController :: method :: downloadEpcgMasterExcel");
    }

    @GetMapping("/wpc-master")
    public ResponseEntity<BoeResponse<List<FetchWpcMasterData>>> fetchWpcMasterInformation() {
        log.info("Entered :: AirtelBoeController :: method :: fetchWpcMasterInformation");

        BoeResponse<List<FetchWpcMasterData>> boeResponse = this.wpcMasterService.wpcMasterInformation();

        log.info("Fetch WPC Master Response :: " + boeResponse);
        log.info("Exit :: AirtelBoeController :: method :: fetchWpcMasterInformation");
        return new ResponseEntity<>(boeResponse, HttpStatus.OK);
    }

    @PostMapping("/wpc-master-add")
    public ResponseEntity<BoeResponse<AddWpcMasterData>> addWpcMasterInformation(@RequestBody AddWpcMasterRequest addWpcMasterRequest,
                                                                                 @AuthenticationPrincipal UserDetailsImpl userDetails) {
        log.info("Entered :: AirtelBoeController :: method :: addWpcMasterInformation");
        log.info("Add WPC Master Request :: " + addWpcMasterRequest);

        if (addWpcMasterRequest == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        BoeResponse<AddWpcMasterData> boeResponse = this.wpcMasterService.addWpcMaster(addWpcMasterRequest, userDetails);

        log.info("Add WPC Master Response :: " + boeResponse);
        log.info("Exit :: AirtelBoeController :: method :: addWpcMasterInformation");
        return new ResponseEntity<>(boeResponse, HttpStatus.OK);
    }

    @PutMapping("/wpc-master-edit")
    public ResponseEntity<BoeResponse<EditWpcMasterData>> editWpcMasterInformation(@RequestBody EditWpcMasterRequest editWpcMasterRequest,
                                                                                   @AuthenticationPrincipal UserDetailsImpl userDetails) {
        log.info("Entered :: AirtelBoeController :: method :: editWpcMasterInformation");
        log.info("Edit WPC Master Request :: " + editWpcMasterRequest);

        if (editWpcMasterRequest == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        BoeResponse<EditWpcMasterData> boeResponse = this.wpcMasterService.editWpcMaster(editWpcMasterRequest, userDetails);

        log.info("Edit WPC Master Response :: " + boeResponse);
        log.info("Exit :: AirtelBoeController :: method :: editWpcMasterInformation");
        return new ResponseEntity<>(boeResponse, HttpStatus.OK);
    }

    @PutMapping("/wpc-master-enddate")
    public ResponseEntity<BoeResponse<EndDateWpcMasterData>> endDateWpcMasterInformation(@RequestBody EndDateWpcMasterRequest endDateWpcMasterRequest,
                                                                                         @AuthenticationPrincipal UserDetailsImpl userDetails) {
        log.info("Entered :: AirtelBoeController :: method :: endDateWpcMasterInformation");
        log.info("End Date WPC Master Request :: " + endDateWpcMasterRequest);

        if (endDateWpcMasterRequest == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        BoeResponse<EndDateWpcMasterData> boeResponse = this.wpcMasterService.endDateWpcMaster(endDateWpcMasterRequest, userDetails);

        log.info("End Date WPC Master Response :: " + boeResponse);
        log.info("Exit :: AirtelBoeController :: method :: endDateWpcMasterInformation");
        return new ResponseEntity<>(boeResponse, HttpStatus.OK);
    }

    @GetMapping("/wpc-master-excel")
    public void downloadWpcMasterExcel() {
        log.info("Entered :: AirtelBoeController :: method :: downloadWpcMasterExcel");

        this.wpcMasterService.wpcMasterExcel();

        log.info("Exit :: AirtelBoeController :: method :: downloadWpcMasterExcel");
    }

    @GetMapping("/item-master")
    public ResponseEntity<BoeResponse<List<FetchItemMasterData>>> fetchItemMasterInformation() {
        log.info("Entered :: AirtelBoeController :: method :: fetchItemMasterInformation");

        BoeResponse<List<FetchItemMasterData>> boeResponse = this.itemMasterService.itemMasterInformation();

        log.info("Fetch ITEM Master Response :: " + boeResponse);
        log.info("Exit :: AirtelBoeController :: method :: fetchItemMasterInformation");
        return new ResponseEntity<>(boeResponse, HttpStatus.OK);
    }

    @PostMapping("/item-master-add")
    public ResponseEntity<BoeResponse<AddItemMasterData>> addItemMasterInformation(@RequestBody AddItemMasterRequest addItemMasterRequest,
                                                                                   @AuthenticationPrincipal UserDetailsImpl userDetails) {
        log.info("Entered :: AirtelBoeController :: method :: addItemMasterInformation");
        log.info("Add ITEM Master Request :: " + addItemMasterRequest);

        if (addItemMasterRequest == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        BoeResponse<AddItemMasterData> boeResponse = this.itemMasterService.addItemMaster(addItemMasterRequest, userDetails);

        log.info("Add ITEM Master Response :: " + boeResponse);
        log.info("Exit :: AirtelBoeController :: method :: addItemMasterInformation");
        return new ResponseEntity<>(boeResponse, HttpStatus.OK);
    }

    @PutMapping("/item-master-edit")
    public ResponseEntity<BoeResponse<EditItemMasterData>> editItemMasterInformation(@RequestBody EditItemMasterRequest editItemMasterRequest,
                                                                                     @AuthenticationPrincipal UserDetailsImpl userDetails) {
        log.info("Entered :: AirtelBoeController :: method :: editItemMasterInformation");
        log.info("Edit ITEM Master Request :: " + editItemMasterRequest);

        if (editItemMasterRequest == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        BoeResponse<EditItemMasterData> boeResponse = this.itemMasterService.editItemMaster(editItemMasterRequest, userDetails);

        log.info("Edit ITEM Master Response :: " + boeResponse);
        log.info("Exit :: AirtelBoeController :: method :: editItemMasterInformation");
        return new ResponseEntity<>(boeResponse, HttpStatus.OK);
    }

    @PutMapping("/item-master-enddate")
    public ResponseEntity<BoeResponse<EndDateItemMasterData>> endDateItemMasterInformation(@RequestBody EndDateItemMasterRequest endDateItemMasterRequest,
                                                                                           @AuthenticationPrincipal UserDetailsImpl userDetails) {
        log.info("Entered :: AirtelBoeController :: method :: endDateItemMasterInformation");
        log.info("End Date ITEM Master Request :: " + endDateItemMasterRequest);

        if (endDateItemMasterRequest == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        BoeResponse<EndDateItemMasterData> boeResponse = this.itemMasterService.endDateItemMaster(endDateItemMasterRequest, userDetails);

        log.info("End Date ITEM Master Response :: " + boeResponse);
        log.info("Exit :: AirtelBoeController :: method :: endDateItemMasterInformation");
        return new ResponseEntity<>(boeResponse, HttpStatus.OK);
    }

    @GetMapping("/itemCodes")
    public ResponseEntity<BoeResponse<List<FetchItemCodeData>>> fetchItemCodes() {
        log.info("Entered :: AirtelBoeController :: method :: fetchItemCodes");

        BoeResponse<List<FetchItemCodeData>> boeResponse = this.itemMasterService.itemCodeList();

        log.info("Item Codes Response :: " + boeResponse);
        log.info("Exit :: AirtelBoeController :: method :: fetchItemCodes");
        return new ResponseEntity<>(boeResponse, HttpStatus.OK);
    }

    @GetMapping("/item-master-excel")
    public void downloadItemMasterExcel() {
        log.info("Entered :: AirtelBoeController :: method :: downloadItemMasterExcel");

        this.itemMasterService.itemMasterExcel();

        log.info("Exit :: AirtelBoeController :: method :: downloadItemMasterExcel");
    }

    @GetMapping("/legalEntities")
    public ResponseEntity<BoeResponse<List<FetchLegalEntityData>>> fetchLegalEntities() {
        log.info("Entered :: AirtelBoeController :: method :: fetchLegalEntities");

        BoeResponse<List<FetchLegalEntityData>> boeResponse = this.legalEntityService.leList();

        log.info("Legal Entities Response :: " + boeResponse);
        log.info("Exit :: AirtelBoeController :: method :: fetchLegalEntities");
        return new ResponseEntity<>(boeResponse, HttpStatus.OK);
    }

    @GetMapping("/dutyCategories")
    public ResponseEntity<BoeResponse<List<FetchDutyCategoryData>>> fetchDutyCategories() {
        log.info("Entered :: AirtelBoeController :: method :: fetchDutyCategories");

        BoeResponse<List<FetchDutyCategoryData>> boeResponse = this.dutyCategoryService.dutyCategoryList();

        log.info("Duty Categories Response :: " + boeResponse);
        log.info("Exit :: AirtelBoeController :: method :: fetchDutyCategories");
        return new ResponseEntity<>(boeResponse, HttpStatus.OK);
    }

    @PostMapping("/protested-boe")
    public ResponseEntity<BoeResponse<FetchProtestedData>> fetchProtestedBoeInformation(@RequestBody FetchProtestedRequest fetchProtestedRequest,
                                                                                        @RequestParam(name = "page", defaultValue = "1") int page,
                                                                                        @RequestParam(name = "size", defaultValue = "25") int size) {
        log.info("Entered :: AirtelBoeController :: method :: fetchProtestedBoeInformation");

        BoeResponse<FetchProtestedData> boeResponse = this.protestedBoeService.protestedBoeInformation(fetchProtestedRequest, page, size);

        log.info("Fetch Protested BOE Response :: " + boeResponse);
        log.info("Exit :: AirtelBoeController :: method :: fetchProtestedBoeInformation");
        return new ResponseEntity<>(boeResponse, HttpStatus.OK);
    }

    @PostMapping("/protested-boe-action")
    public ResponseEntity<BoeResponse<ProtestedBoeActionData>> protestedBoeAction(@RequestBody ProtestedBoeActionRequest protestedBoeActionRequest,
                                                                                  @AuthenticationPrincipal UserDetailsImpl userDetails) {
        log.info("Entered :: AirtelBoeController :: method :: protestedBoeAction");
        log.info("Protested BOE Action Request :: " + protestedBoeActionRequest);

        if (protestedBoeActionRequest == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        BoeResponse<ProtestedBoeActionData> boeResponse = this.protestedBoeService.protestedBoeAction(protestedBoeActionRequest, userDetails);

        log.info("Protested BOE Action Response :: " + boeResponse);
        log.info("Exit :: AirtelBoeController :: method :: protestedBoeAction");
        return new ResponseEntity<>(boeResponse, HttpStatus.OK);
    }

    @PostMapping("/protested-boe-excel")
    public void downloadProtestedBoeExcel(@RequestBody FetchProtestedRequest fetchProtestedRequest) {
        log.info("Entered :: AirtelBoeController :: method :: downloadProtestedBoeExcel");

        this.protestedBoeService.protestedBoeExcel(fetchProtestedRequest);

        log.info("Exit :: AirtelBoeController :: method :: downloadProtestedBoeExcel");
    }

    @PostMapping("/offline-cco")
    public ResponseEntity<BoeResponse<FetchOfflineCcoData>> fetchOfflineCcoInformation(@RequestBody FetchOfflineCcoRequest fetchOfflineCcoRequest,
                                                                                       @RequestParam(name = "page", defaultValue = "1") int page,
                                                                                       @RequestParam(name = "size", defaultValue = "25") int size) {
        log.info("Entered :: AirtelBoeController :: method :: fetchOfflineCcoInformation");

        BoeResponse<FetchOfflineCcoData> boeResponse = this.offlineCcoService.offlineCcoInformation(fetchOfflineCcoRequest, page, size);

        log.info("Fetch Offline CCO Response :: " + boeResponse);
        log.info("Exit :: AirtelBoeController :: method :: fetchOfflineCcoInformation");
        return new ResponseEntity<>(boeResponse, HttpStatus.OK);
    }

    @PostMapping("/offline-cco-approve")
    public ResponseEntity<BoeResponse<OfflineCcoApprovalData>> offlineCcoApprove(@RequestBody OfflineCcoApprovalRequest offlineCcoApprovalRequest,
                                                                                 @AuthenticationPrincipal UserDetailsImpl userDetails) {
        log.info("Entered :: AirtelBoeController :: method :: offlineCcoApprove");
        log.info("Offline Cco Approval Request :: " + offlineCcoApprovalRequest);

        if (offlineCcoApprovalRequest == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        BoeResponse<OfflineCcoApprovalData> boeResponse = this.offlineCcoService.offlineCcoApprove(offlineCcoApprovalRequest, userDetails);

        log.info("Offline Cco Approval Response :: " + boeResponse);
        log.info("Exit :: AirtelBoeController :: method :: offlineCcoApprove");
        return new ResponseEntity<>(boeResponse, HttpStatus.OK);
    }

    @PostMapping("/offline-cco-retrigger")
    public ResponseEntity<BoeResponse<OfflineCcoRetriggerData>> offlineCcoRetrigger(@RequestBody OfflineCcoRetriggerRequest offlineCcoRetriggerRequest,
                                                                                    @AuthenticationPrincipal UserDetailsImpl userDetails) {
        log.info("Entered :: AirtelBoeController :: method :: offlineCcoRetrigger");
        log.info("Offline Cco Retrigger Request :: " + offlineCcoRetriggerRequest);

        if (offlineCcoRetriggerRequest == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        BoeResponse<OfflineCcoRetriggerData> boeResponse = this.offlineCcoService.offlineCcoRetrigger(offlineCcoRetriggerRequest, userDetails);

        log.info("Offline Cco Retrigger Response :: " + boeResponse);
        log.info("Exit :: AirtelBoeController :: method :: offlineCcoRetrigger");
        return new ResponseEntity<>(boeResponse, HttpStatus.OK);
    }

    @PostMapping("/offline-cco-excel")
    public void downloadOfflineCcoExcel(@RequestBody FetchOfflineCcoRequest fetchOfflineCcoRequest) {
        log.info("Entered :: AirtelBoeController :: method :: downloadOfflineCcoExcel");

        this.offlineCcoService.offlineCcoExcel(fetchOfflineCcoRequest);

        log.info("Exit :: AirtelBoeController :: method :: downloadOfflineCcoExcel");
    }

    @GetMapping("/countries")
    public ResponseEntity<BoeResponse<List<CountryData>>> fetchCountries() {
        log.info("Entered :: AirtelBoeController :: method :: fetchCountries");

        BoeResponse<List<CountryData>> boeResponse = this.countryService.countryList();

        log.info("FTA Countries Response :: " + boeResponse);
        log.info("Exit :: AirtelBoeController :: method :: fetchCountries");
        return new ResponseEntity<>(boeResponse, HttpStatus.OK);
    }

    @PostMapping("/cco")
    public ResponseEntity<BoeResponse<FetchCcoData>> fetchCcoInformation(@RequestBody FetchCcoRequest fetchCcoRequest,
                                                                         @RequestParam(name = "page", defaultValue = "1") int page,
                                                                         @RequestParam(name = "size", defaultValue = "25") int size,
                                                                         @AuthenticationPrincipal UserDetailsImpl userDetails) {
        log.info("Entered :: AirtelBoeController :: method :: fetchCcoInformation");

        BoeResponse<FetchCcoData> boeResponse = this.ccoService.ccoInformation(fetchCcoRequest, page, size, userDetails);

        log.info("Fetch CCO Response :: " + boeResponse);
        log.info("Exit :: AirtelBoeController :: method :: fetchCcoInformation");
        return new ResponseEntity<>(boeResponse, HttpStatus.OK);
    }

    @PutMapping("/cco-approve")
    public ResponseEntity<BoeResponse<CcoActionData>> ccoApproveAction(@RequestBody CcoActionRequest ccoActionRequest,
                                                                       @AuthenticationPrincipal UserDetailsImpl userDetails) {
        log.info("Entered :: AirtelBoeController :: method :: ccoApproveAction");
        log.info("CCO Approve Request :: " + ccoActionRequest);

        if (ccoActionRequest == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        BoeResponse<CcoActionData> boeResponse = this.ccoService.ccoApprove(ccoActionRequest, userDetails);

        log.info("CCO Approve Response :: " + boeResponse);
        log.info("Exit :: AirtelBoeController :: method :: ccoApproveAction");
        return new ResponseEntity<>(boeResponse, HttpStatus.OK);
    }

    @PutMapping("/cco-reject")
    public ResponseEntity<BoeResponse<CcoActionData>> ccoRejectAction(@RequestBody CcoActionRequest ccoActionRequest,
                                                                      @AuthenticationPrincipal UserDetailsImpl userDetails) {
        log.info("Entered :: AirtelBoeController :: method :: ccoRejectAction");
        log.info("CCO Reject Request :: " + ccoActionRequest);

        if (ccoActionRequest == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        BoeResponse<CcoActionData> boeResponse = this.ccoService.ccoReject(ccoActionRequest, userDetails);

        log.info("CCO Reject Response :: " + boeResponse);
        log.info("Exit :: AirtelBoeController :: method :: ccoRejectAction");
        return new ResponseEntity<>(boeResponse, HttpStatus.OK);
    }

    @PutMapping("/cco-hold")
    public ResponseEntity<BoeResponse<CcoActionData>> ccoHoldAction(@RequestBody CcoActionRequest ccoActionRequest,
                                                                    @AuthenticationPrincipal UserDetailsImpl userDetails) {
        log.info("Entered :: AirtelBoeController :: method :: ccoHoldAction");
        log.info("CCO Hold Request :: " + ccoActionRequest);

        if (ccoActionRequest == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        BoeResponse<CcoActionData> boeResponse = this.ccoService.ccoHold(ccoActionRequest, userDetails);

        log.info("CCO Hold Response :: " + boeResponse);
        log.info("Exit :: AirtelBoeController :: method :: ccoHoldAction");
        return new ResponseEntity<>(boeResponse, HttpStatus.OK);
    }

    @PutMapping("/cco-unhold")
    public ResponseEntity<BoeResponse<CcoActionData>> ccoUnHoldAction(@RequestBody CcoActionRequest ccoActionRequest,
                                                                      @AuthenticationPrincipal UserDetailsImpl userDetails) {
        log.info("Entered :: AirtelBoeController :: method :: ccoUnHoldAction");
        log.info("CCO UnHold Request :: " + ccoActionRequest);

        if (ccoActionRequest == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        BoeResponse<CcoActionData> boeResponse = this.ccoService.ccoUnHold(ccoActionRequest, userDetails);

        log.info("CCO UnHold Response :: " + boeResponse);
        log.info("Exit :: AirtelBoeController :: method :: ccoUnHoldAction");
        return new ResponseEntity<>(boeResponse, HttpStatus.OK);
    }

    @PostMapping("/cco-excel")
    public void downloadCcoExcel(@RequestBody FetchCcoRequest fetchCcoRequest,
                                 @AuthenticationPrincipal UserDetailsImpl userDetails) {
        log.info("Entered :: AirtelBoeController :: method :: downloadCcoExcel");

        this.ccoService.ccoExcel(fetchCcoRequest, userDetails);

        log.info("Exit :: AirtelBoeController :: method :: downloadCcoExcel");
    }

    @PostMapping("/ip")
    public ResponseEntity<BoeResponse<FetchIpData>> fetchIpInformation(@RequestBody FetchIpRequest fetchIpRequest,
                                                                       @RequestParam(name = "page", defaultValue = "1") int page,
                                                                       @RequestParam(name = "size", defaultValue = "25") int size) {
        log.info("Entered :: AirtelBoeController :: method :: fetchIpInformation");

        BoeResponse<FetchIpData> boeResponse = this.ipService.ipInformation(fetchIpRequest, page, size);

        log.info("Fetch IP Response :: " + boeResponse);
        log.info("Exit :: AirtelBoeController :: method :: fetchIpInformation");
        return new ResponseEntity<>(boeResponse, HttpStatus.OK);
    }

    @PostMapping("/ip-approve")
    public ResponseEntity<BoeResponse<IpApprovalData>> ipApproveAction(@RequestBody IpApprovalRequest ipApprovalRequest,
                                                                       @AuthenticationPrincipal UserDetailsImpl userDetails) {
        log.info("Entered :: AirtelBoeController :: method :: ipApproveAction");
        log.info("IP Approve Request :: " + ipApprovalRequest);

        if (ipApprovalRequest == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        BoeResponse<IpApprovalData> boeResponse = this.ipService.ipApprove(ipApprovalRequest, userDetails);

        log.info("IP Approve Response :: " + boeResponse);
        log.info("Exit :: AirtelBoeController :: method :: ipApproveAction");
        return new ResponseEntity<>(boeResponse, HttpStatus.OK);
    }

    @PostMapping("/ip-reject")
    public ResponseEntity<BoeResponse<IpRejectData>> ipRejectAction(@RequestBody IpRejectRequest ipRejectRequest,
                                                                    @AuthenticationPrincipal UserDetailsImpl userDetails) {
        log.info("Entered :: AirtelBoeController :: method :: ipRejectAction");
        log.info("IP Reject Request :: " + ipRejectRequest);

        if (ipRejectRequest == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        BoeResponse<IpRejectData> boeResponse = this.ipService.ipReject(ipRejectRequest, userDetails);

        log.info("IP Reject Response :: " + boeResponse);
        log.info("Exit :: AirtelBoeController :: method :: ipRejectAction");
        return new ResponseEntity<>(boeResponse, HttpStatus.OK);
    }

    @PostMapping("/ip-rfi")
    public ResponseEntity<BoeResponse<IpRfiData>> ipRfiAction(@RequestBody IpRfiRequest ipRfiRequest,
                                                              @AuthenticationPrincipal UserDetailsImpl userDetails) {
        log.info("Entered :: AirtelBoeController :: method :: ipRfiAction");
        log.info("IP RFI Request :: " + ipRfiRequest);

        if (ipRfiRequest == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        BoeResponse<IpRfiData> boeResponse = this.ipService.ipRfi(ipRfiRequest, userDetails);

        log.info("IP RFI Response :: " + boeResponse);
        log.info("Exit :: AirtelBoeController :: method :: ipRfiAction");
        return new ResponseEntity<>(boeResponse, HttpStatus.OK);
    }

    @PostMapping("/ip-excel")
    public void downloadIpExcel(@RequestBody FetchIpRequest fetchIpRequest) {
        log.info("Entered :: AirtelBoeController :: method :: downloadIpExcel");

        this.ipService.ipExcel(fetchIpRequest);

        log.info("Exit :: AirtelBoeController :: method :: downloadIpExcel");
    }

    @GetMapping("/boe-doc/{airtelBoeRefNo}")
    public ResponseEntity<BoeResponse<List<FetchBoeDocData>>> fetchBoeDocInformation(@PathVariable("airtelBoeRefNo") BigDecimal shipmentId) {
        log.info("Entered :: AirtelBoeController :: method :: fetchBoeDocInformation");

        BoeResponse<List<FetchBoeDocData>> boeResponse = this.boeDocService.boeDocInformation(shipmentId);

        log.info("BOE Doc Response :: " + boeResponse);
        log.info("Exit :: AirtelBoeController :: method :: fetchBoeDocInformation");
        return new ResponseEntity<>(boeResponse, HttpStatus.OK);
    }

    @GetMapping("/currency")
    public ResponseEntity<BoeResponse<List<CurrencyData>>> fetchCurrency() {
        log.info("Entered :: BoeController :: method :: fetchCurrency");

        BoeResponse<List<CurrencyData>> boeResponse = this.currencyService.currencyList();

        log.info("Currency Response :: " + boeResponse);
        log.info("Exit :: BoeController :: method :: fetchCurrency");
        return new ResponseEntity<>(boeResponse, HttpStatus.OK);
    }

    @GetMapping("/indian-city/{pincode}")
    public ResponseEntity<BoeResponse<List<IndianCityData>>> fetchIndianCities(@PathVariable BigDecimal pincode) {
        log.info("Entered :: BoeController :: method :: fetchIndianCities");

        if (pincode == null) {
            return ResponseEntity.badRequest().build();
        }

        BoeResponse<List<IndianCityData>> boeResponse = this.indianCityService.indianCityList(pincode);

        log.info("Indian Cities Response :: " + boeResponse);
        log.info("Exit :: BoeController :: method :: fetchIndianCities");
        return new ResponseEntity<>(boeResponse, HttpStatus.OK);
    }

    @GetMapping("/country-ph-code")
    public ResponseEntity<BoeResponse<List<CountryPhoneCodeData>>> fetchCountryPhoneCode() {
        log.info("Entered :: BoeController :: method :: fetchCountryPhoneCode");

        BoeResponse<List<CountryPhoneCodeData>> boeResponse = this.countryPhoneCodeService.countryPhoneCodeList();

        log.info("Indian Cities Response :: " + boeResponse);
        log.info("Exit :: BoeController :: method :: fetchCountryPhoneCode");
        return new ResponseEntity<>(boeResponse, HttpStatus.OK);
    }

    @PostMapping("/scm/submitStage2")
    public ResponseEntity<BoeResponse<SubmitStage2Response>> submitStage2Scm(@RequestBody SubmitStage2Request submitStage2Request) {
        log.info("Inside AirtelBoeController :: method :: submitStage2Scm :: request :: " + submitStage2Request);

        BoeResponse<SubmitStage2Response> response = scmService.scmSubmitStage2Information(submitStage2Request);

        log.info("Exit AirtelBoeController :: method :: submitStage2Scm");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/scm/stage4/approve")
    public ResponseEntity<BoeResponse<SubmitStage4Response>> stage4ApproveScm(@RequestBody SubmitStage4Request submitStage4Request) {
        log.info("Inside AirtelBoeController :: method :: stage4ApproveScm :: request :: " + submitStage4Request);

        BoeResponse<SubmitStage4Response> response = scmService.stage4ApproveByScm(submitStage4Request);

        log.info("Exit AirtelBoeController :: method :: stage4ApproveScm");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/scm/stage4/reject")
    public ResponseEntity<BoeResponse<ScmStage4RejectResponse>> stage4RejectScm(@RequestBody ScmStage4RejectRequest scmStage4RejectRequest) {
        log.info("Inside AirtelBoeController :: method :: stage4RejectScm :: request :: " + scmStage4RejectRequest);

        BoeResponse<ScmStage4RejectResponse> response = scmService.stage4RejectByScm(scmStage4RejectRequest);

        log.info("Exit AirtelBoeController :: method :: stage4RejectScm");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("cha-agents")
    ResponseEntity<BoeResponse<ChaAgentListData>> getChaAgentList(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        log.info("Inside AirtelBoeController :: method :: getScmLobList");

        if (!checkRole(userDetails, "scm_admin")) {
            log.info("No Role found or exception raised while checking role");
            throw new BoeException("No Data Found", null, HttpStatus.UNAUTHORIZED);
        }
        BoeResponse<ChaAgentListData> response = this.chaReAssignmentService.fetchChaAgentList();

        log.info("Lob response :: " + response);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @PostMapping("/fetchData-cha")
    public ResponseEntity<BoeResponse<ChaReassignmentPageData>> fetchChaReAssignmentData(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                                                         @RequestBody FetchChaReassignmentPageDataRequest fetchChaReassignmentPageDataRequest) {

        log.info("Inside AirtelBoeController :: method :: fetchChaReAssignmentData :: request :: " + fetchChaReassignmentPageDataRequest);
        BoeResponse<ChaReassignmentPageData> response = null;

        //Checking role of user to grant access accordingly
        if (checkRole(userDetails, "SCM_ADMIN")) {
            log.info("Inside AirtelBoeController :: method :: fetchChaReAssignmentData :: role :: SCM_ADMIN");
            response = chaReAssignmentService.fetchReAssignmentPageData(fetchChaReassignmentPageDataRequest, userDetails.getUsername(), "scm_admin");

        } else if (checkRole(userDetails, "SCM_LOB")) {
            log.info("Inside AirtelBoeController :: method :: fetchChaReAssignmentData :: role :: SCM_LOB");
            response = chaReAssignmentService.fetchReAssignmentPageData(fetchChaReassignmentPageDataRequest, userDetails.getUsername(), "SCM_LOB");

        } else {
            log.info("No Role found or exception raised while checking role");
            throw new BoeException("No Data Found", null, HttpStatus.UNAUTHORIZED);
        }
        log.info("Exiting AirtelBoeController :: method :: fetchChaReAssignmentData :: response :: " + response);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/cha-reassign")
    public ResponseEntity<BoeResponse<ChaReassignmentResponse>> chaReAssign(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                                            @RequestBody ChaReassignmentRequest chaReassignmentRequest) {

        log.info("Inside AirtelBoeController :: method :: chaReAssign :: request :: " + chaReassignmentRequest);
        BoeResponse<ChaReassignmentResponse> response = null;

        //Checking role of user to grant access accordingly
        if (checkRole(userDetails, "SCM_ADMIN")) {
            log.info("Inside AirtelBoeController :: method :: chaReAssign :: role :: SCM_ADMIN");
            response = chaReassignmentActionService.submitChaReassignment(chaReassignmentRequest, userDetails);

        } else {
            log.info("No Role found or exception raised while checking role");
            throw new BoeException("No Data Found", null, HttpStatus.UNAUTHORIZED);
        }
        log.info("Exiting AirtelBoeController :: method :: chaReAssign :: response :: " + response);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/wpc-info")
    public ResponseEntity<BoeResponse<WpcInfoResponse>> getWpcInfoDashData(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                                           @RequestBody WpcInfoRequest wpcInfoRequest,
                                                                           @RequestParam(name = "page", defaultValue = "1") int page,
                                                                           @RequestParam(name = "size", defaultValue = "25") int size) {


        log.info("Inside AirtelBoeController :: method :: getWpcInfoDashData :: request :: " + wpcInfoRequest);
        BoeResponse<WpcInfoResponse> response = null;

        //Checking role of user to grant access accordingly
        if (checkRole(userDetails, "SCM_ADMIN")) {
            response = wpcInfoService.getWpcInfoDashData(page, size, userDetails.getUsername(), "scm_admin", wpcInfoRequest);

        } else if (checkRole(userDetails, "SCM_LOB")) {
            response = wpcInfoService.getWpcInfoDashData(page, size, userDetails.getUsername(), "scm_lob", wpcInfoRequest);

        } else if (checkRole(userDetails, "SCM_GVIEW")) {
            response = wpcInfoService.getWpcInfoDashData(page, size, userDetails.getUsername(), "scm_lob", wpcInfoRequest);

        } else {
            log.info("No Role found or exception raised while checking role");
            throw new BoeException("No Data Found", null, HttpStatus.UNAUTHORIZED);
        }
        log.info("Exiting AirtelBoeController :: response :: " + response);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/wpcSubmit")
    public ResponseEntity<BoeResponse<WpcSubmitResponse>> wpcSubmitted(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                                       @RequestBody WpcSubmitRequest wpcSubmitRequest
    ) {


        log.info("Inside AirtelBoeController :: method :: wpcSubmitted :: request :: " + wpcSubmitRequest);
        BoeResponse<WpcSubmitResponse> response = null;

        //Checking role of user to grant access accordingly
        if (checkRole(userDetails, "SCM_ADMIN")) {
            response = wpcInfoService.submitWpc(wpcSubmitRequest, userDetails);

        } else if (checkRole(userDetails, "SCM_LOB")) {
            response = wpcInfoService.submitWpc(wpcSubmitRequest, userDetails);

        } else if (checkRole(userDetails, "SCM_GVIEW")) {
            response = wpcInfoService.submitWpc(wpcSubmitRequest, userDetails);

        } else {
            log.info("No Role found or exception raised while checking role");
            throw new BoeException("No Data Found", null, HttpStatus.UNAUTHORIZED);
        }
        log.info("Exiting AirtelBoeController :: response :: " + response);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/dd-status")
    public ResponseEntity<BoeResponse<DdStatusResponse>> getDdStatusDashData(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                                             @RequestBody DdStatusRequest ddStatusRequest,
                                                                             @RequestParam(name = "page", defaultValue = "1") int page,
                                                                             @RequestParam(name = "size", defaultValue = "25") int size) {


        log.info("Inside AirtelBoeController :: method :: getDdStatusDashData :: request :: " + ddStatusRequest);
        BoeResponse<DdStatusResponse> response = null;

        //Checking role of user to grant access accordingly
        if (checkRole(userDetails, "SCM_ADMIN")) {
            response = this.ddStatusService.fetchDdStatusDashData(page, size, ddStatusRequest);

        } else {
            log.info("No Role found or exception raised while checking role");
            throw new BoeException("No Data Found", null, HttpStatus.UNAUTHORIZED);
        }
        log.info("Exiting AirtelBoeController :: response :: " + response);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/ddStatus-excel")
    public ResponseEntity<BoeResponse<DdStatusResponse>> getDdStatusDashExcel(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                                              @RequestBody DdStatusRequest ddStatusRequest,
                                                                              @RequestParam(name = "page", defaultValue = "1") int page,
                                                                              @RequestParam(name = "size", defaultValue = "25") int size,
                                                                              HttpServletResponse httpServletResponse) {


        log.info("Inside AirtelBoeController :: method :: getDdStatusDashExcel :: request :: " + ddStatusRequest);
        BoeResponse<DdStatusResponse> response = null;

        //Checking role of user to grant access accordingly
        if (checkRole(userDetails, "SCM_ADMIN")) {
            response = this.ddStatusService.getDdStatusDashExcelData(page, size, ddStatusRequest, httpServletResponse);

        } else {
            log.info("No Role found or exception raised while checking role");
            throw new BoeException("No Data Found", null, HttpStatus.UNAUTHORIZED);
        }
        log.info("Exiting AirtelBoeController :: method :: getDdStatusDashExcel :: response :: " + response);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/fta-check/{country}")
    public ResponseEntity<BoeResponse<CheckFtaData>> checkFtaAndAntiDumpingCountry(@PathVariable("country") String country) {
        log.info("Entered :: BoeController :: method :: checkFtaAndAntiDumpingCountry");
        log.info("Country :: " + country);

        BoeResponse<CheckFtaData> boeResponse = this.countryService.isFtaAndAntiDumping(country);

        log.info("Check FTA country Response :: " + boeResponse);
        log.info("Exit :: BoeController :: method :: checkFtaAndAntiDumpingCountry");
        return new ResponseEntity<>(boeResponse, HttpStatus.OK);
    }

    @PostMapping("/scm-rfi-response")
    public ResponseEntity<BoeResponse<ScmRfiResponseData>> scmRfiResponse(@RequestBody ScmRfiResponseReq scmRfiResponseReq,
                                                                          @AuthenticationPrincipal UserDetailsImpl userDetails) {
        log.info("Inside AirtelBoeController :: method :: scmRfiResponse :: request :: ");

        BoeResponse<ScmRfiResponseData> response = null;

        //Checking role of user to grant access accordingly
        if (checkRole(userDetails, "SCM_ADMIN")) {
            response = this.scmRfiResponseService.rfiResponse(scmRfiResponseReq, userDetails);

        } else if (checkRole(userDetails, "SCM_LOB")) {
            response = this.scmRfiResponseService.rfiResponse(scmRfiResponseReq, userDetails);

        } else if (checkRole(userDetails, "SCM_GVIEW")) {
            response = this.scmRfiResponseService.rfiResponse(scmRfiResponseReq, userDetails);

        } else {
            log.info("No Role found or exception raised while checking role");
            throw new BoeException("No Data Found", null, HttpStatus.UNAUTHORIZED);
        }
        log.info("Exiting AirtelBoeController ::method :: scmRfiResponse :: response :: " + response);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @PostMapping("/wpc-inquiry")
    public ResponseEntity<BoeResponse<WpcInquiryResponseData>> getWpcInquiryData(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                                                 @RequestBody WpcInquiryRequest wpcInquiryRequest) {


        log.info("Inside AirtelBoeController :: method :: getWpcInquiryData :: request :: " + wpcInquiryRequest);
        BoeResponse<WpcInquiryResponseData> response = null;

        //Checking role of user to grant access accordingly
        if (checkRole(userDetails, "SCM_ADMIN")) {
            response = wpcInquiryService.getWpcData(wpcInquiryRequest);

        } else if (checkRole(userDetails, "SCM_LOB")) {
            response = wpcInquiryService.getWpcData(wpcInquiryRequest);

        } else if (checkRole(userDetails, "SCM_GVIEW")) {
            response = wpcInquiryService.getWpcData(wpcInquiryRequest);
        } else {
            log.info("No Role found or exception raised while checking role");
            throw new BoeException("No Data Found", null, HttpStatus.UNAUTHORIZED);
        }
        log.info("Exiting AirtelBoeController :: method :: getWpcInquiryData :: response :: " + response);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/wpc-inq-excel")
    public ResponseEntity<BoeResponse<WpcInquiryResponseData>> getWpcInquiryExcelData(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                                                      @RequestBody WpcInquiryRequest wpcInquiryRequest,
                                                                                      HttpServletResponse httpServletResponse) {


        log.info("Inside AirtelBoeController :: method :: getWpcInquiryData :: request :: " + wpcInquiryRequest);
        BoeResponse<WpcInquiryResponseData> response = null;

        //Checking role of user to grant access accordingly
        if (checkRole(userDetails, "SCM_ADMIN")) {
            response = wpcInquiryService.getWpcExcelData(wpcInquiryRequest, httpServletResponse);

        } else if (checkRole(userDetails, "SCM_LOB")) {
            response = wpcInquiryService.getWpcExcelData(wpcInquiryRequest, httpServletResponse);

        } else if (checkRole(userDetails, "SCM_GVIEW")) {
            response = wpcInquiryService.getWpcExcelData(wpcInquiryRequest, httpServletResponse);
        } else {
            log.info("No Role found or exception raised while checking role");
            throw new BoeException("No Data Found", null, HttpStatus.UNAUTHORIZED);
        }
        log.info("Exiting AirtelBoeController :: method :: getWpcInquiryData :: response :: " + response);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/shipment-status-report")
    public ResponseEntity<BoeResponse<ShipmentStatusReportResponse>> getShipmentstatusReport(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                                                             @RequestBody ShipmentStatusReportRequest statusReportRequest)  {


        log.info("Inside AirtelBoeController :: method :: getShipmentstatusReport :: request :: " + statusReportRequest);
        BoeResponse<ShipmentStatusReportResponse> response = null;

        //Checking role of user to grant access accordingly
        if (checkRole(userDetails, "SCM_ADMIN")) {
            response = shipmentStatusReportService.shipmentStausReport(statusReportRequest, userDetails.getEmail());

        } else if (checkRole(userDetails, "SCM_LOB")) {
            response = shipmentStatusReportService.shipmentStausReport(statusReportRequest, userDetails.getEmail());

        } else if (checkRole(userDetails, "SCM_GVIEW")) {
            response = shipmentStatusReportService.shipmentStausReport(statusReportRequest, userDetails.getEmail());

        } else {
            log.info("No Role found or exception raised while checking role");
            throw new BoeException("No Data Found", null, HttpStatus.UNAUTHORIZED);
        }
        log.info("Exiting AirtelBoeController :: method :: getShipmentstatusReport :: response :: " + response);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/epcg-inquiry")
    public ResponseEntity<BoeResponse<EpcgInquiryResponseData>> getEpcgInquiryData(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                                                   @RequestBody EpcgInquiryRequest epcgInquiryRequest) {


        log.info("Inside AirtelBoeController :: method :: getEpcgInquiryData :: request :: " + epcgInquiryRequest);
        BoeResponse<EpcgInquiryResponseData> response = null;

        //Checking role of user to grant access accordingly
        if (checkRole(userDetails, "SCM_ADMIN")) {
            response = epcgInquiryService.getEpcgInquiryData(epcgInquiryRequest);

        } else if (checkRole(userDetails, "SCM_LOB")) {
            response = epcgInquiryService.getEpcgInquiryData(epcgInquiryRequest);

        } else if (checkRole(userDetails, "SCM_GVIEW")) {
            response = epcgInquiryService.getEpcgInquiryData(epcgInquiryRequest);
        } else {
            log.info("No Role found or exception raised while checking role");
            throw new BoeException("No Data Found", null, HttpStatus.UNAUTHORIZED);
        }
        log.info("Exiting AirtelBoeController :: method :: getEpcgInquiryData :: response :: " + response);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @PostMapping("/epcg-inq-excel")
    public ResponseEntity<BoeResponse<EpcgInquiryResponseData>> getEpcgInquiryExcelData(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                                                      @RequestBody EpcgInquiryRequest epcgInquiryRequest,
                                                                                      HttpServletResponse httpServletResponse) {


        log.info("Inside AirtelBoeController :: method :: getEpcgInquiryExcelData :: request :: " + epcgInquiryRequest);
        BoeResponse<EpcgInquiryResponseData> response = null;

        //Checking role of user to grant access accordingly
        if (checkRole(userDetails, "SCM_ADMIN")) {
            response = epcgInquiryService.getEpcgExcelData(epcgInquiryRequest, httpServletResponse);

        } else if (checkRole(userDetails, "SCM_LOB")) {
            response = epcgInquiryService.getEpcgExcelData(epcgInquiryRequest, httpServletResponse);

        } else if (checkRole(userDetails, "SCM_GVIEW")) {
            response = epcgInquiryService.getEpcgExcelData(epcgInquiryRequest, httpServletResponse);
        } else {
            log.info("No Role found or exception raised while checking role");
            throw new BoeException("No Data Found", null, HttpStatus.UNAUTHORIZED);
        }
        log.info("Exiting AirtelBoeController :: method :: getEpcgInquiryExcelData :: response :: " + response);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }



}
