package com.crowd.air.tower_info.server;

import com.crowd.air.tower_info.model.apis.CellLocationRequest;
import com.crowd.air.tower_info.model.apis.CellLocationResponse;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface Apis {

    @POST("v2/process.php")
    Call<CellLocationResponse> getCellLocation(@Body CellLocationRequest jsonObject);

}
