package br.com.leonardobenedeti.oramaaccounts;

import java.lang.ref.SoftReference;

/**
 * Created by leonardobenedeti on 12/08/16.
 */
public class AccountsModel {

    private int id;
    private int profile;
    private String account;
    private String reference_date;
    private String balance;
    private String retrieval;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProfile() {
        return profile;
    }

    public void setProfile(int profile) {
        this.profile = profile;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getReference_date() {
        return reference_date;
    }

    public void setReference_date(String reference_date) {
        this.reference_date = reference_date;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getRetrieval() {
        return retrieval;
    }

    public void setRetrieval(String retrieval) {
        this.retrieval = retrieval;
    }
}
