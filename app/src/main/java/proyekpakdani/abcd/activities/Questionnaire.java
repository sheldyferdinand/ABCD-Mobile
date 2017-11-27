package proyekpakdani.abcd.activities;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import proyekpakdani.abcd.Interface;
import proyekpakdani.abcd.adapters.QuestionnaireAdapter;
import proyekpakdani.abcd.models.QuestionnaireContent;
import proyekpakdani.abcd.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class Questionnaire extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    public static final String BASE_URL = "http://admin.odc-abcd.com/";
    private RecyclerView recyclerView;
    private Toolbar toolbar;
    private QuestionnaireAdapter adapter;
    private ArrayList<QuestionnaireContent> isiList;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Context context;

    private void findViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbarQuestionnaire);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewQuestionnaire);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout_questionnaire);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionnaire);
        findViews();
        setSupportActionBar(toolbar);

        initViews();
        setSwipeRefreshLayout();
    }

    private void initViews() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        isiList = new ArrayList<>();
        adapter = new QuestionnaireAdapter(isiList,Questionnaire.this);
        recyclerView.setAdapter(adapter);
        swipeRefreshLayout.setOnRefreshListener(this);
    }

    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    private void loadJSON(){
        if (isOnline()) {
            swipeRefreshLayout.setRefreshing(true);
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            Interface request = retrofit.create(Interface.class);
//            int removedFront = BASE_URL.length();
//            String link = getIntent().getStringExtra("surveyLink").substring(removedFront);
//
//
//            if(link != null && !link.isEmpty()) {
                Call<List<QuestionnaireContent>> call = request.getQuestionnaire("api/survey/project/144");
                call.enqueue(new Callback<List<QuestionnaireContent>>() {
                                 @Override
                                 public void onResponse(Call<List<QuestionnaireContent>> call, Response<List<QuestionnaireContent>> response) {

                                     List<QuestionnaireContent> list = response.body();
                                     QuestionnaireContent isi;
                                     if (list.size() != 0) {
                                         for (int i = 0; i < list.size(); i++) {
                                             isi = new QuestionnaireContent();
                                             isi.setName(list.get(i).getName());
                                             isi.setDescription(list.get(i).getDescription());
                                             isiList.add(isi);

                                         }
                                     }
                                     adapter.notifyDataSetChanged();

                                     swipeRefreshLayout.setRefreshing(false);
                                 }

                                 @Override
                                 public void onFailure(Call<List<QuestionnaireContent>> call, Throwable t) {
                                     swipeRefreshLayout.setRefreshing(false);
                                     Log.d("Error", t.getMessage());
                                 }
                             }
                );
//            }
        }
        else
        {
            try {
                AlertDialog alertDialog = new AlertDialog.Builder(this).create();

                alertDialog.setTitle("Info");
                alertDialog.setMessage("Internet not available, Cross check your internet connectivity and try again");
                alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
                alertDialog.setButton(Dialog.BUTTON_POSITIVE,"OK",new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        setSwipeRefreshLayout();
                    }
                });
                alertDialog.show();
            }
            catch(Exception e)
            {
                //Log.d(Constants.TAG, "Show Dialog: "+e.getMessage());
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_dashboard, menu);

        MenuItem searchMenuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchMenuItem);
        searchMenuItem(searchView);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_account) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void searchMenuItem(SearchView searchView) {

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                adapter.getFilter().filter(newText);
                return true;
            }
        });
    }

    private void setSwipeRefreshLayout(){
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        swipeRefreshLayout.setRefreshing(true);
                                        loadJSON();
                                    }
                                }
        );
    }

    public void onRefresh() {
        isiList.clear();
        loadJSON();
    }


}
