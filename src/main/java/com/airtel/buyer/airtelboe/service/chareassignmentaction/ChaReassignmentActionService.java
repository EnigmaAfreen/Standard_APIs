package com.airtel.buyer.airtelboe.service.chareassignmentaction;

import com.airtel.buyer.airtelboe.dto.chareassignmentaction.request.ChaReassignmentRequest;
import com.airtel.buyer.airtelboe.dto.chareassignmentaction.response.ChaReassignmentResponse;
import com.airtel.buyer.airtelboe.dto.common.BoeResponse;
import com.airtel.buyer.airtelboe.user.UserDetailsImpl;

public interface ChaReassignmentActionService {
    BoeResponse<ChaReassignmentResponse> submitChaReassignment(ChaReassignmentRequest chaReassignmentRequest, UserDetailsImpl userDetails);
}
