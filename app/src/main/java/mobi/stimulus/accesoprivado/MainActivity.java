package mobi.stimulus.accesoprivado;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.webkit.CookieManager;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.google.gson.Gson;

import mobi.stimulus.accesoprivado.R;
import mobi.stimulus.accesoprivado.network.APIclient;
import mobi.stimulus.accesoprivado.network.models.LoginRequest;
import mobi.stimulus.accesoprivado.network.models.LoginResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private String url = "https://stimulus.mobi";
    private WebView webView;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        webView = findViewById(R.id.webView);
        configuraWebView();

        String usuario = obtenerUsuario();
        String pass = obtenerPass();

        autenticarUsuario(usuario, pass);
    }

    private void configuraWebView(){
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setLoadsImagesAutomatically(true);
        webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        webSettings.setMediaPlaybackRequiresUserGesture(false);
    }

    private void autenticarUsuario(String usuario, String pass){
        // retrofit, hace el login, recoge lo que recibe, y rellena las coockies del webview
        LoginRequest loginRequest = new LoginRequest(usuario,pass);
        Call<LoginResponse> call = APIclient.apIinterface().login(loginRequest);
        try {
            call.enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    if(response.body() != null) {
                        LoginResponse to = response.body();
                        // primero al token y luego al idstimulus
                        String cookieStringToken = "token=" + to.getToken() + "; path=/";
                        CookieManager.getInstance().setCookie(url, cookieStringToken, tokenInsertado -> {
                            if(tokenInsertado){
                                //recoger el usuario y meterlo en el cookie manager
                                Gson gson = new Gson();
                                String json = gson.toJson(to.getUser());
                                String cookieStringUser = "idStimulus=" + json + "; path=/";
                                CookieManager.getInstance().setCookie(url, cookieStringUser, usuarioIsertado -> {
                                    if(usuarioIsertado) lanzaWebView();
                                });
                            }
                        });

                    }else{
                        Toast.makeText(getApplicationContext(),"Datos incorrectos. Vuelve a intentarlo",Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    t.printStackTrace();
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /***
     * Metodo que obtiene el nombre de usuario con el que loguear
     * @return usuario
     */
    private String obtenerUsuario(){
        //TODO cambiar esto por la forma en que se decida obtener el nombre de usuario
        return "test.stimulus";
    }

    /***
     * Metodo que obtiene la pass con que loguear
     * @return password de usuario
     */
    private String obtenerPass(){
        //TODO cambiar esto por la forma en que se decida obtener la pass de usuario
        return "TestUser";
    }

    private void lanzaWebView(){
        webView.loadUrl("https://stimulus.mobi/user_access/app/");
        webView.setWebViewClient(new WebViewClient());
        webView.setWebChromeClient(new WebChromeClient());

        NoSalirDelDominio webClient = new NoSalirDelDominio();
        webClient.setContext(this);

        webView.setWebViewClient(webClient);
    }

}
class NoSalirDelDominio extends WebViewClient {
    private Context context;

    public void setContext(Context context){
        this.context = context;
    }

    @Override
    public void doUpdateVisitedHistory(WebView view, String url, boolean isReload) {
        if(url.contains("stimulus.mobi/admin")){
            Toast.makeText(context,"No se puede salir de la página",Toast.LENGTH_SHORT).show();
            view.loadUrl("https://stimulus.mobi/user_access/app/");
        }
        super.doUpdateVisitedHistory(view, url, isReload);
    }
}