package com.itbenevides.qualbanco;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.itbenevides.qualbanco.dao.Banco;
import com.itbenevides.qualbanco.dao.Conta;
import com.itbenevides.qualbanco.dao.DAO;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.UUID;

public class ActivityContas extends AppCompatActivity {

    RecyclerView rvContas;
    public String strfiltro="";
    int modo=1;
    private AdView avPropaganda;
    public Conta contaselecionada=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_contas);

        avPropaganda = (AdView) findViewById(R.id.banner_app);
        inicializarAdListener(avPropaganda);
        requisitarPropaganda(getApplicationContext(), avPropaganda);

        FloatingActionButton actionButtonadd = (FloatingActionButton)findViewById(R.id.floatbtadd);
        actionButtonadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                geraAlertaGravar();
            }
        });
        new carregaBancoLV().execute();

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


                strfiltro = s;
                ((contaAdapterList) rvContas.getAdapter()).getFilter().filter(s);
                return false;

            }

            @Override
            public boolean onQueryTextChange(String s) {


                strfiltro = s;
                ((contaAdapterList) rvContas.getAdapter()).getFilter().filter(s);

                return false;
            }
        });




        return true;
    }
    public class carregaBancoLV extends AsyncTask<Integer, Integer, Void> {


        private ProgressDialog progressdialog;
        List<Conta> contas;





        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressdialog = new ProgressDialog(ActivityContas.this);
            progressdialog.setMessage("Carregando dados...");
            progressdialog.setIndeterminate(true);
            progressdialog.setCancelable(false);
            progressdialog.setCanceledOnTouchOutside(false);
            progressdialog.show();

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            rvContas = (RecyclerView)findViewById(R.id.rv);
            rvContas.setHasFixedSize(true);


            if(modo==1){
                LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
                rvContas.setLayoutManager(llm);
            }else if(modo==2){
                GridLayoutManager llm =  new GridLayoutManager(getApplicationContext(),2);
                rvContas.setLayoutManager(llm);
            }



            contaAdapterList adapter = new contaAdapterList(contas,getApplicationContext());
            rvContas.setAdapter(adapter);



            ((contaAdapterList) rvContas.getAdapter()).getFilter().filter(strfiltro);


            ItemClickSupport.addTo(rvContas).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                @Override
                public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                  geraAlertaClickItem(contas.get(position));
                }
            });

            progressdialog.dismiss();
        }

        @Override
        protected Void doInBackground(Integer... integers) {


            contas = Conta.consultar(DAO.getHelper(getApplicationContext()));
            return null;
        }
    }


    public void geraAlertaClickItem(final Conta conta) {
        final Dialog dialog = new Dialog(this,android.support.v7.appcompat.R.style.Theme_AppCompat_DayNight_Dialog_Alert);
        Window window = dialog.getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND,
                WindowManager.LayoutParams.FLAG_BLUR_BEHIND);




        dialog.setTitle("Item selecionado");




        dialog.setContentView(R.layout.dialog_click_itens);


        Button bteditar = (Button)dialog.findViewById(R.id.dialog_bteditar);
        final Button btexcluir = (Button)dialog.findViewById(R.id.dialog_btexcluir);
        final Button btcompartilhar = (Button)dialog.findViewById(R.id.dialog_btcompartilhar);
         final int[] posi=new int[1];
        btcompartilhar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final List<Banco> bancos = Banco.consultar(DAO.getHelper(getApplicationContext()));
                for(int i=0;i<bancos.size();i++) {
                    if (conta.getCodigobanco().equals(bancos.get(i).codigobanco)) {
                        posi[0] = i;

                        break;
                    }
                }

                String text="";

                text="Banco: "+bancos.get(posi[0]).getNome()+"\n"
                        +"Código: "+bancos.get(posi[0]).getCodigobanco()+"\n"
                        +"Agência: "+conta.getCodigoagencia()+"\n"
                        +"Conta corrente: "+conta.getCodigoconta()+"\n"
                        +"Nome: "+conta.getNome()+"\n"
                        +"CPF: "+conta.getCpf()+"\n\n"
                        +"Use o 'Qual Banco' você também:  https://goo.gl/CcVEWZ";



                Intent textShareIntent = new Intent(Intent.ACTION_SEND);
                textShareIntent.putExtra(Intent.EXTRA_TEXT, text);
                textShareIntent.setType("text/plain");
                startActivity(Intent.createChooser(textShareIntent, "share"));

                dialog.dismiss();




            }
        });


        btexcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DAO.getHelper(getApplicationContext()).excluir(conta);
                new carregaBancoLV().execute();
                dialog.dismiss();




            }
        });



        bteditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contaselecionada =conta;
                dialog.dismiss();
                geraAlertaGravar();



            }
        });











        dialog.show();

    }



    public void geraAlertaGravar() {
        final Dialog dialog = new Dialog(this,android.support.v7.appcompat.R.style.Theme_AppCompat_DayNight_DialogWhenLarge);
        Window window = dialog.getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND,
                WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
        dialog.setCancelable(false);

 //       int divierId = dialog.getContext().getResources()
    //            .getIdentifier("android:id/titleDivider", null, null);
      //  View divider = dialog.findViewById(divierId);
//        divider.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

            dialog.setTitle("Conta corrente");




            dialog.setContentView(R.layout.dialog_cadastro_contas);
        final AutoCompleteTextView acbanco = (AutoCompleteTextView) dialog.findViewById(R.id.dialogac_banco);
        final List<Banco> bancos = Banco.consultar(DAO.getHelper(getApplicationContext()));
        ArrayAdapter<Banco> adapter = new ArrayAdapter<Banco>(dialog.getContext(),R.layout.simple_dropdown_item_1line, bancos);
        acbanco.setAdapter(adapter);

        final TextView tvagencia = (TextView)dialog.findViewById(R.id.dialog_agencia);
        final TextView tvcc = (TextView)dialog.findViewById(R.id.dialog_conta_corrente);
        final TextView tvcpf = (TextView)dialog.findViewById(R.id.dialog_cpf);
        final TextView tvnome = (TextView)dialog.findViewById(R.id.dialog_none);

        Button btsalvar = (Button)dialog.findViewById(R.id.dialog_btsalvar);
        Button btcancelar = (Button)dialog.findViewById(R.id.dialog_cancelar);
        final int[] posi = new int[1];
        posi[0] =-1;

        if(contaselecionada!=null){
            tvagencia.setText(contaselecionada.getCodigoagencia());
            tvcc.setText(contaselecionada.getCodigoconta());
            tvnome.setText(contaselecionada.getNome());
            tvcpf.setText(contaselecionada.getCpf());
            for(int i=0;i<bancos.size();i++){
                if(contaselecionada.getCodigobanco().equals(bancos.get(i).codigobanco)){
                    posi[0]=i;
                    acbanco.setText(bancos.get(i).nome);
                    break;
                }
            }


        }



        btcancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                contaselecionada=null;
            }
        });




        acbanco.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View arg1, int pos,
                                    long id) {

                Banco selection = (Banco) parent.getItemAtPosition(pos);
                int pos2 = -1;

                for (int i = 0; i < bancos.size(); i++) {
                    if (bancos.get(i).getCodigobanco().equals(selection.getCodigobanco())) {
                        posi[0]  = i;
                        break;
                    }
                }



            }
        });
        btsalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (posi[0] < 0) {
                    Snackbar.make(view, "Selecione um banco.", Snackbar.LENGTH_LONG).show();
                    acbanco.requestFocus();
                    return;
                }
                if (tvcc.getText().length() <= 0) {
                    Snackbar.make(view, "Digite a conta corrente.", Snackbar.LENGTH_LONG).show();
                    tvcc.requestFocus();
                    return;
                }
                if (tvnome.getText().length() <= 0) {
                    Snackbar.make(view, "Digite o nome completo.", Snackbar.LENGTH_LONG).show();
                    tvcc.requestFocus();
                    return;
                }
                if (tvcpf.getText().length() <= 0||(!isCNPJ(tvcpf.getText().toString())&&!isCPF(tvcpf.getText().toString()))) {
                    Snackbar.make(view, "Digite um cpf/cnpj válido.", Snackbar.LENGTH_LONG).show();
                    tvcc.requestFocus();
                    return;
                }
                if (tvagencia.getText().length() <= 0) {
                    Snackbar.make(view, "Digite a agencia.", Snackbar.LENGTH_LONG).show();
                    tvagencia.requestFocus();
                    return;
                }

                Conta conta = null;

                if (contaselecionada != null) {
                    conta = contaselecionada;
                } else {
                    conta = new Conta();
                }

                conta.setNome(tvnome.getText().toString().toUpperCase());
                conta.setCpf(tvcpf.getText().toString());
                conta.setCodigoconta(tvcc.getText().toString().toUpperCase());
                conta.setCodigoagencia(tvagencia.getText().toString().toUpperCase());
                Banco banco = bancos.get(posi[0]);

                conta.setCodigobanco(banco.getCodigobanco());

                DAO.getHelper(getApplicationContext()).salvar2(conta);
                contaselecionada=null;

                Snackbar.make(view, "Salvo com sucesso.", Snackbar.LENGTH_LONG).show();
                new carregaBancoLV().execute();

                dialog.dismiss();


            }
        });


        dialog.show();

    }
    public boolean isCPF(String strCpf) {


        if (strCpf.equals("00000000000") || strCpf.equals("11111111111") ||
                strCpf.equals("22222222222") || strCpf.equals("33333333333") ||
                strCpf.equals("44444444444") || strCpf.equals("55555555555") ||
                strCpf.equals("66666666666") || strCpf.equals("77777777777") ||
                strCpf.equals("88888888888") || strCpf.equals("99999999999") ||
                (strCpf.length() != 11))
            return (false);


        int d1, d2;
        int digito1, digito2, resto;
        int digitoCPF;
        String nDigResult;

        d1 = d2 = 0;
        digito1 = digito2 = resto = 0;

        for (int nCount = 1; nCount < strCpf.length() - 1; nCount++) {
            digitoCPF = Integer.valueOf(strCpf.substring(nCount - 1, nCount)).intValue();

            d1 = d1 + (11 - nCount) * digitoCPF;

            d2 = d2 + (12 - nCount) * digitoCPF;
        }
        ;

        resto = (d1 % 11);

        if (resto < 2)
            digito1 = 0;
        else
            digito1 = 11 - resto;

        d2 += 2 * digito1;

        resto = (d2 % 11);

        if (resto < 2)
            digito2 = 0;
        else
            digito2 = 11 - resto;

        String nDigVerific = strCpf.substring(strCpf.length() - 2, strCpf.length());

        nDigResult = String.valueOf(digito1) + String.valueOf(digito2);

        return nDigVerific.equals(nDigResult);
    }


    public boolean isCNPJ(String CNPJ) {
        // considera-se erro CNPJ's formados por uma sequencia de numeros iguais
        if (CNPJ.equals("00000000000000") || CNPJ.equals("11111111111111") ||
                CNPJ.equals("22222222222222") || CNPJ.equals("33333333333333") ||
                CNPJ.equals("44444444444444") || CNPJ.equals("55555555555555") ||
                CNPJ.equals("66666666666666") || CNPJ.equals("77777777777777") ||
                CNPJ.equals("88888888888888") || CNPJ.equals("99999999999999") ||
                (CNPJ.length() != 14))
            return (false);

        char dig13, dig14;
        int sm, i, r, num, peso;

        // "try" - protege o c�digo para eventuais erros de conversao de tipo (int)
        try {
            // Calculo do 1o. Digito Verificador
            sm = 0;
            peso = 2;
            for (i = 11; i >= 0; i--) {
                // converte o i-�simo caractere do CNPJ em um n�mero:
                // por exemplo, transforma o caractere '0' no inteiro 0
                // (48 eh a posi��o de '0' na tabela ASCII)
                num = (int) (CNPJ.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso + 1;
                if (peso == 10)
                    peso = 2;
            }

            r = sm % 11;
            if ((r == 0) || (r == 1))
                dig13 = '0';
            else dig13 = (char) ((11 - r) + 48);

            // Calculo do 2o. Digito Verificador
            sm = 0;
            peso = 2;
            for (i = 12; i >= 0; i--) {
                num = (int) (CNPJ.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso + 1;
                if (peso == 10)
                    peso = 2;
            }

            r = sm % 11;
            if ((r == 0) || (r == 1))
                dig14 = '0';
            else dig14 = (char) ((11 - r) + 48);

            // Verifica se os d�gitos calculados conferem com os d�gitos informados.
            if ((dig13 == CNPJ.charAt(12)) && (dig14 == CNPJ.charAt(13)))
                return (true);
            else return (false);
        } catch (InputMismatchException erro) {
            return (false);
        }
    }
}
