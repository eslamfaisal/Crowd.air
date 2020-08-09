package com.crowd.air.tower_info.server;

import com.crowd.air.BasselResponse;
import com.crowd.air.tower_info.model.apis.CellLocationRequest;
import com.crowd.air.tower_info.model.apis.CellLocationResponse;
import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Apis {

    @POST("v2/process.php")
    Call<CellLocationResponse> getCellLocation(@Body CellLocationRequest jsonObject);

    @GET("api/customers")
    Call<List<BasselResponse>> getBasselResponse();

}
