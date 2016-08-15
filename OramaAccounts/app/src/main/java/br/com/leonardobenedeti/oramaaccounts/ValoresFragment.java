package br.com.leonardobenedeti.oramaaccounts;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.TimePickerDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ValoresFragment extends Fragment{

    RecyclerView recList;
    List<AccountsModel> accountsModelList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_valores, container, false);


        recList = (RecyclerView) rootView.findViewById(R.id.cardList);
        recList.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);

        LinearLayout chamaData = (LinearLayout) rootView.findViewById(R.id.show_date_picker);
        final TextView date = (TextView) rootView.findViewById(R.id.textView2);
        final TextView all = (TextView) rootView.findViewById(R.id.todos);

        all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onResume();
                date.setText("DD MM YYYY");
            }
        });

        chamaData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DialogFragment newFragment = new DatePickerFragment() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {

                        Calendar calendar = Calendar.getInstance();
                        calendar.set(year, month, day);
                        Date dateValue = calendar.getTime();


                        SimpleDateFormat simpleDate =  new SimpleDateFormat("dd MMM yyyy");
                        String dateText = simpleDate.format(dateValue);

                        SimpleDateFormat simpleDateForJson =  new SimpleDateFormat("yyyy-MM-dd");
                        String dateTextJson = simpleDateForJson.format(dateValue);

                        date.setText(dateText);
                        degenerateJson(loadJSONFromAsset(), dateTextJson );
                    }
                };
                newFragment.show(getActivity().getFragmentManager(), "datePicker");

            }
        });

        return rootView;




    }

    @Override
    public void onResume() {
        super.onResume();
        degenerateJson(loadJSONFromAsset(), null );
    }

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getActivity().getAssets().open("contas.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }


    public void degenerateJson(String data, String date){

        try {
            JSONObject json = new JSONObject(data);

            if(!json.isNull("contas")){
                JSONArray ja = json.getJSONArray("contas");
                accountsModelList = new ArrayList<AccountsModel>();

                for(int i = 0, tam = ja.length(); i < tam; i++){
                    JSONObject jAccs = ja.getJSONObject(i);

                    AccountsModel accountsModel = new AccountsModel();

                    accountsModel.setAccount(jAccs.getString("account"));
                    accountsModel.setBalance(jAccs.getString("balance"));
                    accountsModel.setId(i);
                    accountsModel.setProfile(jAccs.getInt("profile"));
                    accountsModel.setReference_date(jAccs.getString("reference_date"));
                    accountsModel.setRetrieval(jAccs.getString("retrieval"));

                    if (date!= null && accountsModel.getReference_date().equals(date)) {
                        accountsModelList.add(accountsModel);
                    }else if(date==null){
                        accountsModelList.add(accountsModel);
                    }
                }

                AccAdapter accAdapter = new AccAdapter(accountsModelList);
                recList.setAdapter(accAdapter);
            }
        }
        catch(Exception e){
            Toast.makeText(getActivity(), "Error"+e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

}
