package com.lemelo.controlepasse;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * Created by leoci on 26/10/2017.
 */

class MyRefresh {
    public void recargaFragment(FragmentActivity activity) {
        RecargaFragment fr = new RecargaFragment();
        FragmentManager fm = activity.getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragment_content, fr);
        ft.commit();
    }
}
