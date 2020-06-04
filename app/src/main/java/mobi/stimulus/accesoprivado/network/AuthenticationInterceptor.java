package mobi.stimulus.accesoprivado.network;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;


// Esta es la clase que maniobra el token

public class AuthenticationInterceptor implements Interceptor {
    private String authToken;

    public AuthenticationInterceptor(String token) {
        this.authToken = token;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();

        Request.Builder builder = original.newBuilder()
                .header("x-access-token", authToken);

        Request request = builder.build();
        return chain.proceed(request);
    }
}
