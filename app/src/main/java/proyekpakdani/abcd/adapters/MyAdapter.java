package proyekpakdani.abcd.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;

import proyekpakdani.abcd.R;
import proyekpakdani.abcd.activities.Questionnaire;
import proyekpakdani.abcd.models.Isi;

/**
 * Created by SLD on 5/21/2017.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> implements Filterable {

    private ArrayList<Isi> projects;
    private ArrayList<Isi> filteredList;
    private Context context;

    public MyAdapter(ArrayList<Isi> arrayList, Context context) {
        this.projects = arrayList;
        this.filteredList = arrayList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_dashboard, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Isi isi = filteredList.get(position);
        int endIndex = isi.getUpdated().length() - 14;

        holder.txtjudul.setText(isi.getName());
        holder.txtdesc.setText(isi.getDescription());
        holder.txttanggal.setText("Last updated: " + isi.getUpdated().substring(0, endIndex));

        holder.cardView.setOnClickListener(new View.OnClickListener(){


            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Questionnaire.class);
                intent.putExtra("surveyID", isi.getId().toString());
//                intent.putExtra("surveyLink", isi.getSurveys().get(0).toString());
                context.startActivity(intent);
            }

        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView txtjudul;
        public TextView txtdesc;
        public TextView txttanggal;
        private CardView cardView;

        public ViewHolder (View itemView){
            super(itemView);
            txtjudul = (TextView) itemView.findViewById(R.id.JudulProyek);
            txtdesc = (TextView) itemView.findViewById(R.id.PembuatProyek);
            txttanggal = (TextView) itemView.findViewById(R.id.TanggalProyek);
            cardView = (CardView) itemView.findViewById(R.id.CardProyek);
        }
    }

    @Override
    public int getItemCount() {
        return filteredList.size();
    }

    @Override
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String charString = charSequence.toString();

                if (charString.isEmpty()) {

                    filteredList = projects;
                } else {

                    ArrayList<Isi> mFilteredList = new ArrayList<>();

                    for (Isi isi : projects) {

                        if (isi.getName().toLowerCase().contains(charString.toLowerCase())) {

                            mFilteredList.add(isi);
                        }
                    }

                    filteredList = mFilteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filteredList = (ArrayList<Isi>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
}

