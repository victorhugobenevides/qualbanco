package com.itbenevides.qualbanco;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
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
import android.widget.ImageView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.List;

public class ActivityCodigos extends AppCompatActivity {
    RecyclerView rvBancos;
    public String strfiltro="";
    int modo=1;
    private AdView avPropaganda;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        //Carrega o arquivo de menu.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.busca, menu);

        SearchView mSearchView = (SearchView) menu.findItem(R.id.search).getActionView();
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {


            @Override
            public boolean onQueryTextSubmit(String s) {



                strfiltro=s;
                ((bancoAdapterList)rvBancos.getAdapter()).getFilter().filter(s);
                return false;

            }

            @Override
            public boolean onQueryTextChange(String s) {


                strfiltro=s;
                ((bancoAdapterList)rvBancos.getAdapter()).getFilter().filter(s);

                return false;
            }
        });




        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_codigos);

        final ImageView imvlist = (ImageView) findViewById(R.id.imv_list);
        final ImageView imvgrid = (ImageView) findViewById(R.id.imv_grid);

        imvlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                modo=1;
                carregaBancoLV carregaBanco=new carregaBancoLV();
                carregaBanco.execute();
            }
        });

        imvgrid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(modo==1){
                    modo=2;
                    imvgrid.setImageResource(R.drawable.btlist);
                }else{
                    modo=1;
                    imvgrid.setImageResource(R.drawable.btgrid);
                }

                carregaBancoLV carregaBanco=new carregaBancoLV();
                carregaBanco.execute();
            }
        });
        carregaBancoLV carregaBanco=new carregaBancoLV();
        carregaBanco.execute();

        avPropaganda = (AdView) findViewById(R.id.banner_app);
        inicializarAdListener(avPropaganda);
        requisitarPropaganda(getApplicationContext(), avPropaganda);
    }
    public class carregaBancoLV extends AsyncTask<Integer, Integer, Void> {


        private ProgressDialog progressdialog;
        List<Banco> bancos;





        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressdialog = new ProgressDialog(ActivityCodigos.this);
            progressdialog.setMessage("Carregando dados...");
            progressdialog.setIndeterminate(true);
            progressdialog.setCancelable(false);
            progressdialog.setCanceledOnTouchOutside(false);
            progressdialog.show();

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            rvBancos = (RecyclerView)findViewById(R.id.rv);
            rvBancos.setHasFixedSize(true);

      /*  JazzyRecyclerViewScrollListener jazzyScrollListener = new JazzyRecyclerViewScrollListener();
        rvBancos.setOnScrollListener(jazzyScrollListener);*/

            // LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());


            if(modo==1){
                LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
                rvBancos.setLayoutManager(llm);
            }else if(modo==2){
                GridLayoutManager llm =  new GridLayoutManager(getApplicationContext(),2);
                rvBancos.setLayoutManager(llm);
            }



            bancoAdapterList adapter = new bancoAdapterList(bancos,getApplicationContext());
            rvBancos.setAdapter(adapter);



            ((bancoAdapterList) rvBancos.getAdapter()).getFilter().filter(strfiltro);



            progressdialog.dismiss();
        }

        @Override
        protected Void doInBackground(Integer... integers) {


            bancos = Banco.consultar(DAO.getHelper(getApplicationContext()));
            return null;
        }
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
