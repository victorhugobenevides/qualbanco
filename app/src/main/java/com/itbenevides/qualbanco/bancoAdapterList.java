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
import android.widget.ImageView;
import android.widget.TextView;



import java.util.ArrayList;
import java.util.List;

/**
 * Created by Onnet on 27/10/2015.
 */
public class bancoAdapterList extends RecyclerView.Adapter<bancoAdapterList.PersonViewHolder>  implements Filterable {
    private List<Banco> bancos;
    private List<Banco> bancosorig;
    private Context context;


    public bancoAdapterList(List<Banco> bancos, Context context) {
        this.bancos = bancos;
        this.context=context;

    }

    @Override
    public int getItemCount() {
        return bancos.size();
    }

    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v=null;

        if(viewGroup == null) {

        }else{
            viewGroup.getTag();
        }


            v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_banco, viewGroup, false);


        PersonViewHolder pvh = new PersonViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(PersonViewHolder personViewHolder, int i) {

        StaggeredGridLayoutManager.LayoutParams layoutParams = new StaggeredGridLayoutManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams.setFullSpan(true);
        personViewHolder.itemView.setLayoutParams(layoutParams);

        personViewHolder.banconome.setText(bancos.get(i).nome);
        personViewHolder.bancocodigo.setText(bancos.get(i).codigobanco);





    }

    public static class PersonViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView banconome;
        TextView bancocodigo;





        PersonViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            banconome = (TextView)itemView.findViewById(R.id.banco_nome);
            bancocodigo = (TextView)itemView.findViewById(R.id.banco_codigo);



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
                bancos.clear();
                bancos.addAll(((List<Banco>) results.values));

                    notifyDataSetChanged();

            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                FilterResults results = new FilterResults();
                ArrayList<Banco> filtrosfrutos = new ArrayList<Banco>();


                if(bancosorig ==null){

                    bancosorig = new ArrayList<Banco>(bancos);


                }









                if (constraint == null || constraint.length() == 0) {
                    results.count = bancosorig.size();
                    results.values = bancosorig;

                    // set the Original result to return

                } else {

                    for (Banco banco: bancosorig) {

                        if (banco.getNome().toLowerCase().contains(constraint.toString())||banco.getCodigobanco().toLowerCase().contains(constraint.toString()))  {
                            filtrosfrutos.add(banco);
                        }
                    }


                    results.count = filtrosfrutos.size();
                    results.values = filtrosfrutos;


                }



                return results;
            }
        };

        return filter;
    }


}
