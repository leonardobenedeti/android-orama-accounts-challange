package br.com.leonardobenedeti.oramaaccounts;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;

/**
 * Created by leonardobenedeti on 12/08/16.
 */
public class AccAdapter extends RecyclerView.Adapter<AccAdapter.AccViewHolder> {

    private List<AccountsModel> accList;

    public AccAdapter(List<AccountsModel> accList) {
        this.accList = accList;
    }

    @Override
    public int getItemCount() {
        return accList.size();
    }

    @Override
    public void onBindViewHolder(AccViewHolder accViewHolder, int i) {
        AccountsModel acc = accList.get(i);

        if (acc.getProfile() == 500){
            accViewHolder.profile.setImageResource(R.drawable.ic_aviao);
        }else {
            accViewHolder.profile.setImageResource(R.drawable.ic_coqueiro);
        }

        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setGroupingSeparator('.');
        symbols.setDecimalSeparator(',');
        DecimalFormat decimalFormat = new DecimalFormat("R$ #,#00.00", symbols);


        accViewHolder.vAccTitle.setText(acc.getAccount());
        accViewHolder.vBalance.setText(decimalFormat.format(Float.parseFloat(acc.getBalance())));
        accViewHolder.vRetrieval.setText(decimalFormat.format(Float.parseFloat(acc.getRetrieval())));
        accViewHolder.vTotal.setText(decimalFormat.format(
                Float.parseFloat(acc.getBalance()) - Float.parseFloat(acc.getRetrieval())));
    }

    @Override
    public AccViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.card_layout, viewGroup, false);

        return new AccViewHolder(itemView);
    }

    public class AccViewHolder extends RecyclerView.ViewHolder {

        protected ImageView profile;
        protected TextView vAccTitle;
        protected TextView vBalance;
        protected TextView vRetrieval;
        protected TextView vTotal;


        public AccViewHolder(View v) {
            super(v);
            profile = (ImageView) v.findViewById(R.id.icon);
            vAccTitle = (TextView) v.findViewById(R.id.account_value);
            vBalance = (TextView) v.findViewById(R.id.saldo_value);
            vRetrieval = (TextView) v.findViewById(R.id.resgate_value);
            vTotal = (TextView) v.findViewById(R.id.total_value);
        }
    }

}
