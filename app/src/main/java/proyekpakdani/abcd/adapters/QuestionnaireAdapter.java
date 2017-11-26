package proyekpakdani.abcd.adapters;

/**
 * Created by SLD on 14-Sep-17.
 */

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import proyekpakdani.abcd.activities.Questionnaire;
import proyekpakdani.abcd.models.Isi;
import proyekpakdani.abcd.models.QuestionnaireContent;
import proyekpakdani.abcd.R;
import proyekpakdani.abcd.questionsUtils.SurveyActivity;

/**
 * Created by SLD on 5/21/2017.
 */

public class QuestionnaireAdapter extends RecyclerView.Adapter<QuestionnaireAdapter.ViewHolder> implements Filterable {

    private static final int SURVEY_REQUEST = 1337;
    private ArrayList<QuestionnaireContent> questionnaires;
    private ArrayList<QuestionnaireContent> filteredList;
    private Context context;

    public QuestionnaireAdapter(ArrayList<QuestionnaireContent> arrayList, Context context) {
        this.questionnaires = arrayList;
        this.filteredList = arrayList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_questionnaire, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        QuestionnaireContent content = filteredList.get(position);
        holder.txtjudul.setText(content.getName());
        holder.txttanggal.setText("Last updated: " + content.getUpdated());

        holder.shareMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //creating a popup menu
                PopupMenu popup = new PopupMenu(context, holder.shareMenu, Gravity.RIGHT);
                //inflating menu from xml resource
                popup.inflate(R.menu.menu_card);
                //adding click listener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.menu_share:
                                shareTextUrl();
                                break;
//                            case R.id.menu2:
//                                //handle menu2 click
//                                break;
//                            case R.id.menu3:
//                                //handle menu3 click
//                                break;
                        }
                        return false;
                    }
                });
                //displaying the popup
                popup.show();
            }
        });

        holder.cardView.setOnClickListener(new View.OnClickListener(){


            @Override
            public void onClick(View v) {
                Intent i_survey = new Intent(context, SurveyActivity.class);
                i_survey.putExtra("json_survey", loadSurveyJson("example_survey_1.json"));
                ((Questionnaire)context).startActivityForResult(i_survey, SURVEY_REQUEST);
            }

        });
    }

    private String loadSurveyJson(String filename) {
        try {
            InputStream is = ((Questionnaire)context).getAssets().open(filename);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            return new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }




    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView txtjudul;
        public TextView txttanggal;
        public TextView shareMenu;
        private CardView cardView;

        public ViewHolder (View itemView){
            super(itemView);
            txtjudul = (TextView) itemView.findViewById(R.id.JudulQuestionnaire);
            txttanggal = (TextView) itemView.findViewById(R.id.TanggalQuestionnaire);
            shareMenu = (TextView) itemView.findViewById(R.id.textViewOptions);
            cardView = (CardView) itemView.findViewById(R.id.CardQuestionnaire);
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

                    filteredList = questionnaires;
                } else {

                    ArrayList<QuestionnaireContent> mFilteredList = new ArrayList<>();

                    for (QuestionnaireContent content : questionnaires) {

                        if (content.getName().toLowerCase().contains(charString.toLowerCase())) {

                            mFilteredList.add(content);
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
                filteredList = (ArrayList<QuestionnaireContent>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    private void shareTextUrl() {
        try {
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");
            i.putExtra(Intent.EXTRA_SUBJECT, "My application name");
            String sAux = "Bingung? Buka nih \n\n";
            sAux = sAux + "https://google.com";
            i.putExtra(Intent.EXTRA_TEXT, sAux);
            context.startActivity(Intent.createChooser(i, "Share using"));
        } catch(Exception e) {
            //e.toString();
        }
    }


}
