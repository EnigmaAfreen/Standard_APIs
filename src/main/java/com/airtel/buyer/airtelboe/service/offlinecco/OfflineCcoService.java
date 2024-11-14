package com.airtel.buyer.airtelboe.service.offlinecco;

import com.airtel.buyer.airtelboe.dto.common.BoeResponse;
import com.airtel.buyer.airtelboe.dto.fetchofflinecco.request.FetchOfflineCcoRequest;
import com.airtel.buyer.airtelboe.dto.fetchofflinecco.response.FetchOfflineCcoData;
import com.airtel.buyer.airtelboe.dto.offlineccoapproval.request.OfflineCcoApprovalRequest;
import com.airtel.buyer.airtelboe.dto.offlineccoapproval.response.OfflineCcoApprovalData;
import com.airtel.buyer.airtelboe.dto.offlineccoretrigger.request.OfflineCcoRetriggerRequest;
import com.airtel.buyer.airtelboe.dto.offlineccoretrigger.response.OfflineCcoRetriggerData;
import com.airtel.buyer.airtelboe.user.UserDetailsImpl;

public interface OfflineCcoService {

    BoeResponse<FetchOfflineCcoData> offlineCcoInformation(FetchOfflineCcoRequest fetchOfflineCcoRequest, int page, int size);

    BoeResponse<OfflineCcoApprovalData> offlineCcoApprove(OfflineCcoApprovalRequest offlineCcoApprovalRequest, UserDetailsImpl userDetails);

    BoeResponse<OfflineCcoRetriggerData> offlineCcoRetrigger(OfflineCcoRetriggerRequest offlineCcoRetriggerRequest, UserDetailsImpl userDetails);

    void offlineCcoExcel(FetchOfflineCcoRequest fetchOfflineCcoRequest);

}
