package com.airtel.buyer.airtelboe.service.ip;

import com.airtel.buyer.airtelboe.dto.common.BoeResponse;
import com.airtel.buyer.airtelboe.dto.fetchip.request.FetchIpRequest;
import com.airtel.buyer.airtelboe.dto.fetchip.response.FetchIpData;
import com.airtel.buyer.airtelboe.dto.ipapproval.request.IpApprovalRequest;
import com.airtel.buyer.airtelboe.dto.ipapproval.response.IpApprovalData;
import com.airtel.buyer.airtelboe.dto.ipreject.request.IpRejectRequest;
import com.airtel.buyer.airtelboe.dto.ipreject.response.IpRejectData;
import com.airtel.buyer.airtelboe.dto.iprfi.request.IpRfiRequest;
import com.airtel.buyer.airtelboe.dto.iprfi.response.IpRfiData;
import com.airtel.buyer.airtelboe.user.UserDetailsImpl;

public interface IpService {

    BoeResponse<FetchIpData> ipInformation(FetchIpRequest fetchCcoRequest, int page, int size);

    BoeResponse<IpApprovalData> ipApprove(IpApprovalRequest ipApprovalRequest, UserDetailsImpl userDetails);

    BoeResponse<IpRejectData> ipReject(IpRejectRequest ipRejectRequest, UserDetailsImpl userDetails);

    BoeResponse<IpRfiData> ipRfi(IpRfiRequest ipRfiRequest, UserDetailsImpl userDetails);

    void ipExcel(FetchIpRequest fetchCcoRequest);

}
