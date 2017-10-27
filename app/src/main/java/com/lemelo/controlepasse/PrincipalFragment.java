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

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

        //Saldo atual
        final EditText txtSaldoAtual = (EditText) view.findViewById(R.id.txtSaldoAtual);
        txtSaldoAtual.setText(saldoAtual());
        txtSaldoAtual.setEnabled(false);

        //preenche data e hora
        final EditText txtDataHora = (EditText) view.findViewById(R.id.txtDataHora);
        SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
        txtDataHora.setText(sdf1.format(Calendar.getInstance().getTime()));

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

        preencheLinhaOnibus(view);

        //Valor do passe
        final EditText txtValorPasse = (EditText) view.findViewById(R.id.txtValorPasse);
        txtValorPasse.setText(valorPasse());

        String strSaltoAtual = txtSaldoAtual.getText().toString();
        String nfSaldoAtual = null;
        try {
            nfSaldoAtual = NumberFormat.getCurrencyInstance().parse(strSaltoAtual).toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        BigDecimal bdSaldoAtual = new BigDecimal(nfSaldoAtual);

        String strValorPasse = txtValorPasse.getText().toString();
        String nfValorPasse = null;
        try {
            nfValorPasse = NumberFormat.getCurrencyInstance().parse(strValorPasse).toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        BigDecimal bdValorPasse = new BigDecimal(nfValorPasse);

        BigDecimal bdSaldoFuturo = bdSaldoAtual.subtract(bdValorPasse);

        final EditText txtSaldoFuturo = (EditText) view.findViewById(R.id.txtSaldoFuturo);
        NumberFormat nfSaldoFuturo = NumberFormat.getCurrencyInstance();
        txtSaldoFuturo.setText(nfSaldoFuturo.format(bdSaldoFuturo));
        txtSaldoFuturo.setEnabled(false);

        final Button btnSalvar = (Button) view.findViewById(R.id.btnSalvar);
        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db;
                FabricaConexao fabrica = new FabricaConexao(getContext());
                db = fabrica.getWritableDatabase();

                PrincipaDao
            }
        });

        return view;
    }

    private String saldoAtual() {
        SQLiteDatabase db;
        FabricaConexao fabrica = new FabricaConexao(getContext());
        db = fabrica.getWritableDatabase();
        SaldoDao saldoDao = new SaldoDao(db);
        String saldoAtualStr = saldoDao.getSaldoAtual();
        if(saldoAtualStr == null) {
            saldoAtualStr = "0";
        }

        BigDecimal bdSaldoAtual = new BigDecimal(saldoAtualStr);
        NumberFormat nfSaldoAtual = NumberFormat.getCurrencyInstance();

        return nfSaldoAtual.format(bdSaldoAtual);
    }

    private String valorPasse() {
        SQLiteDatabase db;
        FabricaConexao fabrica = new FabricaConexao(getContext());
        db = fabrica.getWritableDatabase();
        RecargaDao recargaDao = new RecargaDao(db);
        String valorPasse = recargaDao.getValorPasse();
        if(valorPasse == null) {
            valorPasse = "0";
        }

        NumberFormat nfValorPasse = NumberFormat.getCurrencyInstance();
        BigDecimal bdValorPasse = new BigDecimal(valorPasse);

        return nfValorPasse.format(bdValorPasse);
    }

    private void preencheLinhaOnibus(View view) {
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
                //Toast.makeText(getContext(),"You have selected item: " + list.get(index), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
