package mobi.stimulus.accesoprivado.network;

import mobi.stimulus.accesoprivado.network.models.LoginRequest;
import mobi.stimulus.accesoprivado.network.models.LoginResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface APIinterface {

    @POST("login")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);
}
