package mobi.stimulus.accesoprivado.network;

import android.text.TextUtils;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIclient {

    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    private static Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl("https://stimulus.mobi:3001/api/user_access/v1/auth/")
            .addConverterFactory(GsonConverterFactory.create());

    //////////////
    // Usar este método para coger el token
    public static APIinterface apIinterface(){
        return APIclient.createService(APIinterface.class);
    }
    // Usar este método para recoger datos que necesiten token
    public static APIinterface apIinterface(String token){
        return APIclient.createService(APIinterface.class,token);
    }

    public static <S> S createService(Class<S> serviceClass) {
        return createService(serviceClass, null);
    }

    public static <S> S createService(Class<S> serviceClass, final String authToken) {
        Retrofit retrofit = null;
        if(TextUtils.isEmpty(authToken)){
            httpClient.interceptors().clear();
            builder.client(httpClient.build());
            retrofit = builder.build();

        }else{
            AuthenticationInterceptor interceptor = new AuthenticationInterceptor(authToken);

            if(interceptor != null && !httpClient.interceptors().isEmpty()){
                httpClient.interceptors().clear();
            }

            httpClient.addInterceptor(interceptor);

            builder.client(httpClient.build());
            retrofit = builder.build();

        }
        if(retrofit != null) return retrofit.create(serviceClass);

        return null;
    }
}
