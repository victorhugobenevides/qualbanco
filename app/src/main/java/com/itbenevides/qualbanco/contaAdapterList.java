package com.itbenevides.qualbanco;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.itbenevides.qualbanco.dao.Banco;
import com.itbenevides.qualbanco.dao.Conta;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Onnet on 27/10/2015.
 */
public class contaAdapterList extends RecyclerView.Adapter<contaAdapterList.PersonViewHolder>  implements Filterable {
    private List<Conta> contas;
    private List<Conta> contasorig;
    private Context context;


    public contaAdapterList(List<Conta> contas, Context context) {
        this.contas = contas;
        this.context=context;

    }

    @Override
    public int getItemCount() {
        return contas.size();
    }

    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v=null;

        if(viewGroup == null) {

        }else{
            viewGroup.getTag();
        }


            v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_conta, viewGroup, false);


        PersonViewHolder pvh = new PersonViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(PersonViewHolder personViewHolder, int i) {

        StaggeredGridLayoutManager.LayoutParams layoutParams = new StaggeredGridLayoutManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams.setFullSpan(true);
        personViewHolder.itemView.setLayoutParams(layoutParams);

        personViewHolder.banconome.setText(contas.get(i).nomeBanco.toUpperCase().replace("BANCO", ""));
        personViewHolder.bancocodigo.setText(contas.get(i).codigobanco);
        personViewHolder.contacontacorrente.setText(contas.get(i).codigoconta);
        personViewHolder.contaagencia.setText(contas.get(i).codigoagencia);
        personViewHolder.contacpf.setText(contas.get(i).cpf);
        personViewHolder.nomepessoa.setText(contas.get(i).nome);


    }

    public static class PersonViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView banconome;
        TextView nomepessoa;
        TextView bancocodigo;
        TextView contacontacorrente;
        TextView contaagencia;
        TextView contacpf;





        PersonViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            banconome = (TextView)itemView.findViewById(R.id.conta_banco_nome);
            bancocodigo = (TextView)itemView.findViewById(R.id.conta_banco_codigo);
            contacontacorrente= (TextView)itemView.findViewById(R.id.conta_contacorrente);
            contaagencia= (TextView)itemView.findViewById(R.id.conta_agencia);
            contacpf= (TextView)itemView.findViewById(R.id.conta_cpf);
            nomepessoa= (TextView)itemView.findViewById(R.id.conta_nome);
        }
    }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }


    @Override
    public Filter getFilter() {

        Filter filter = new Filter() {



            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                contas.clear();
                contas.addAll(((List<Conta>) results.values));

                    notifyDataSetChanged();

            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                FilterResults results = new FilterResults();
                ArrayList<Conta> filtroscontas = new ArrayList<Conta>();


                if(contasorig ==null){

                    contasorig = new ArrayList<Conta>(contas);


                }









                if (constraint == null || constraint.length() == 0) {
                    results.count = contasorig.size();
                    results.values = contasorig;

                    // set the Original result to return

                } else {

                    for (Conta conta: contasorig) {
                        if (conta.getNome().toLowerCase().contains(constraint.toString().toLowerCase())
                                ||conta.getCodigobanco().toLowerCase().contains(constraint.toString().toLowerCase())
                                ||conta.getCodigoagencia().toLowerCase().contains(constraint.toString().toLowerCase())
                                ||conta.getCodigoconta().toLowerCase().contains(constraint.toString().toLowerCase())
                                ||conta.getCpf().toLowerCase().contains(constraint.toString().toLowerCase())){
                            filtroscontas.add(conta);
                        }
                    }


                    results.count = filtroscontas.size();
                    results.values = filtroscontas;


                }



                return results;
            }
        };

        return filter;
    }


}
