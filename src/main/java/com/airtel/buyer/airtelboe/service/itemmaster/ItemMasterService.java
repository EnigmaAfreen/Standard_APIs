package com.airtel.buyer.airtelboe.service.itemmaster;

import com.airtel.buyer.airtelboe.dto.additemmaster.request.AddItemMasterRequest;
import com.airtel.buyer.airtelboe.dto.additemmaster.response.AddItemMasterData;
import com.airtel.buyer.airtelboe.dto.common.BoeResponse;
import com.airtel.buyer.airtelboe.dto.edititemmaster.request.EditItemMasterRequest;
import com.airtel.buyer.airtelboe.dto.edititemmaster.response.EditItemMasterData;
import com.airtel.buyer.airtelboe.dto.enddateitemmaster.request.EndDateItemMasterRequest;
import com.airtel.buyer.airtelboe.dto.enddateitemmaster.response.EndDateItemMasterData;
import com.airtel.buyer.airtelboe.dto.fetchitemcode.response.FetchItemCodeData;
import com.airtel.buyer.airtelboe.dto.fetchitemmaster.response.FetchItemMasterData;
import com.airtel.buyer.airtelboe.user.UserDetailsImpl;

import java.util.List;

public interface ItemMasterService {

    BoeResponse<List<FetchItemMasterData>> itemMasterInformation();

    BoeResponse<AddItemMasterData> addItemMaster(AddItemMasterRequest addItemMasterRequest, UserDetailsImpl userDetails);

    BoeResponse<EditItemMasterData> editItemMaster(EditItemMasterRequest editItemMasterRequest, UserDetailsImpl userDetails);

    BoeResponse<EndDateItemMasterData> endDateItemMaster(EndDateItemMasterRequest endDateItemMasterRequest, UserDetailsImpl userDetails);

    BoeResponse<List<FetchItemCodeData>> itemCodeList();

    void itemMasterExcel();

}
