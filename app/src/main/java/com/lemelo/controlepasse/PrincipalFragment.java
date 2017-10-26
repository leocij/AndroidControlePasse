package com.lemelo.controlepasse;


import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Toast;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by leoci on 17/10/2017.
 */

public class PrincipalFragment extends Fragment {
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.activity_principal, container, false);
        getActivity().setTitle("Tela Principal");

        new MyKeyboard().hideKeyboard(getActivity(), view);

        //fecha teclado ao clicar
        ScrollView scrollView = (ScrollView) view.findViewById(R.id.scrollViewPrincipal);
        scrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                new MyKeyboard().hideKeyboard(getActivity(), v);
                return false;
            }
        });

        //preenche data
        final EditText txtData = (EditText) view.findViewById(R.id.txtData);
        SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        txtData.setText(sdf1.format(Calendar.getInstance().getTime()));

        //preenche hora
        final EditText txtHora = (EditText) view.findViewById(R.id.txtHora);
        SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm", Locale.getDefault());
        txtHora.setText(sdf2.format(Calendar.getInstance().getTime()));

        //addItemsOnSpinner(view);

        final Button addLinhaOnibus = (Button) view.findViewById(R.id.btnAddLinhaOnibus);
        addLinhaOnibus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinhaOnibusFragment cadastra = new LinhaOnibusFragment();
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.fragment_content, cadastra);
                ft.commit();
            }
        });

        preencheSpinner(view);

        valorPasse(view);



        return view;
    }

    private void valorPasse(View view) {
        SQLiteDatabase db;
        FabricaConexao fabrica = new FabricaConexao(getContext());
        db = fabrica.getWritableDatabase();
        RecargaDao recargaDao = new RecargaDao(db);
        String valorPasse = recargaDao.getValorPasse();
        if(valorPasse == null) {
            valorPasse = "0";
        }

        final EditText txtValorPasse = (EditText) view.findViewById(R.id.txtValorPasse);
        NumberFormat nfValorPasse = NumberFormat.getCurrencyInstance();
        BigDecimal bdValorPasse = new BigDecimal(valorPasse);
        txtValorPasse.setText(nfValorPasse.format(bdValorPasse));
    }

    private void preencheSpinner(View view) {
        final Spinner spiLinhaOnibus = (Spinner) view.findViewById(R.id.linhaOnibus);

        SQLiteDatabase db;

        FabricaConexao fabrica = new FabricaConexao(getContext());
        db = fabrica.getWritableDatabase();
        LinhaOnibusDao linhaOnibusDao = new LinhaOnibusDao(db);
        final List<LinhaOnibus> list = linhaOnibusDao.listAll();

        ArrayAdapter<LinhaOnibus> linhaOnibusArrayAdapter = new ArrayAdapter<LinhaOnibus>(getActivity(), android.R.layout.simple_spinner_item, list);
        linhaOnibusArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spiLinhaOnibus.setAdapter(linhaOnibusArrayAdapter);
        spiLinhaOnibus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int index = spiLinhaOnibus.getSelectedItemPosition();
                Toast.makeText(getContext(),"You have selected item: " + list.get(index), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
