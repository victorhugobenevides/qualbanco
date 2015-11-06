package com.itbenevides.qualbanco;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.twotoasters.jazzylistview.recyclerview.JazzyRecyclerViewScrollListener;

import java.util.List;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        TextView tvlistbancos = (TextView) findViewById(R.id.tv_btlistbancos);
        tvlistbancos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, ActivityCodigos.class);
                startActivity(i);
            }
        });
        TextView tvContas = (TextView) findViewById(R.id.tv_contas);
        tvContas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, ActivityContas.class);
                startActivity(i);
            }
        });

        AdView avPropaganda = (AdView) findViewById(R.id.banner_app);
        inicializarAdListener(avPropaganda);
        requisitarPropaganda(getApplicationContext(), avPropaganda);
    }

    public static boolean requisitarPropaganda(Context contexto, AdView advBanner) {
        // Create an ad request. Check logcat output for the hashed device
        // ID to get test ads on a physical device.

        AdRequest.Builder adBuilder = new AdRequest.Builder();
        if (BuildConfig.DEBUG) {
            adBuilder
                    .addTestDevice(AdRequest.DEVICE_ID_EMULATOR);
        }

        AdRequest adRequest = adBuilder.build();

        // Start loading the ad in the background.
        if (advBanner != null) {
            advBanner.loadAd(adRequest);
        }

        return true;
    }

    public static void inicializarAdListener(final AdView advBanner) {
        advBanner.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();

                // A propaganda foi carregada com sucesso
                // Logo, vamos mostrar para o usuário
                advBanner.setVisibility(AdView.VISIBLE);
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                String msgErro = "";
                switch (errorCode) {
                    case AdRequest.ERROR_CODE_INTERNAL_ERROR:
                        msgErro = "AdListener - Erro interno";
                        break;
                    case AdRequest.ERROR_CODE_INVALID_REQUEST:
                        msgErro = "AdListener - Requisição inválida";
                        break;
                    case AdRequest.ERROR_CODE_NETWORK_ERROR:
                        msgErro = "AdListener - Erro de rede";
                        break;
                    case AdRequest.ERROR_CODE_NO_FILL:
                        msgErro = "AdListener - Não coube";
                        break;
                }
                Log.e("RETORNOALMOCO", msgErro);
                advBanner.setVisibility(AdView.GONE);
            }
        });}
}
