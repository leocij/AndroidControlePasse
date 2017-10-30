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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Toast;

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

        final String linhaOnibus = preencheLinhaOnibus(view);

        //Valor do passe
        final EditText txtValorPasse = (EditText) view.findViewById(R.id.txtValorPasse);
        txtValorPasse.setText(valorPasse());
        txtValorPasse.setEnabled(false);

        final String strSaldoAtual = txtSaldoAtual.getText().toString();
        String nfSaldoAtual = null;
        try {
            nfSaldoAtual = NumberFormat.getCurrencyInstance().parse(strSaldoAtual).toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        final BigDecimal bdSaldoAtual = new BigDecimal(nfSaldoAtual);

        String strValorPasse = txtValorPasse.getText().toString();
        String nfValorPasse = null;
        try {
            nfValorPasse = NumberFormat.getCurrencyInstance().parse(strValorPasse).toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        final BigDecimal bdValorPasse = new BigDecimal(nfValorPasse);

        BigDecimal bdSaldoFuturo = bdSaldoAtual.subtract(bdValorPasse);

        final EditText txtSaldoFuturo = (EditText) view.findViewById(R.id.txtSaldoFuturo);
        NumberFormat nfSaldoFuturo = NumberFormat.getCurrencyInstance();
        txtSaldoFuturo.setText(nfSaldoFuturo.format(bdSaldoFuturo));
        txtSaldoFuturo.setEnabled(false);

        final Button btnSalvar = (Button) view.findViewById(R.id.btnSalvar);
        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String strValorPasse2 = txtValorPasse.getText().toString();
                BigDecimal bdValorPasse2 = null;
                try {
                    String nfValorPasse2 = NumberFormat.getCurrencyInstance().parse(strValorPasse2).toString();
                    bdValorPasse2 = new BigDecimal(nfValorPasse2);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                //Toast.makeText(getContext(), "Comparação: " + bdSaldoAtual.compareTo(bdValorPasse2) + " ValorPasse: " + bdValorPasse2, Toast.LENGTH_LONG).show();

                if (bdSaldoAtual.compareTo(bdValorPasse2) == -1) {
                    Toast.makeText(getContext(), "Saldo Atual não é suficiente!", Toast.LENGTH_LONG).show();
                } else {
                    SQLiteDatabase db;
                    FabricaConexao fabrica = new FabricaConexao(getContext());
                    db = fabrica.getWritableDatabase();

                    PrincipalDao principalDao = new PrincipalDao(db);

                    Principal principal = new Principal();
                    principal.setSaldoAtual(txtSaldoAtual.getText().toString());
                    principal.setDataHora(txtDataHora.getText().toString());
                    principal.setLinhaOnibus(linhaOnibus);
                    principal.setValorPasse(txtValorPasse.getText().toString());
                    principal.setSaldoFuturo(txtSaldoFuturo.getText().toString());

                    try {
                        principalDao.insert(principal);

                        Toast.makeText(getContext(), "Registro Salvo!", Toast.LENGTH_LONG).show();

                        new MyRefresh().principalFragment(getActivity());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
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

    private String preencheLinhaOnibus(View view) {
        final Spinner spiLinhaOnibus = (Spinner) view.findViewById(R.id.linhaOnibus);


        SQLiteDatabase db;

        FabricaConexao fabrica = new FabricaConexao(getContext());
        db = fabrica.getWritableDatabase();
        LinhaOnibusDao linhaOnibusDao = new LinhaOnibusDao(db);
        final List<LinhaOnibus> list = linhaOnibusDao.listAll();

        ArrayAdapter<LinhaOnibus> linhaOnibusArrayAdapter = new ArrayAdapter<LinhaOnibus>(getActivity(), android.R.layout.simple_spinner_item, list);
        linhaOnibusArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spiLinhaOnibus.setAdapter(linhaOnibusArrayAdapter);

        //System.out.println("----------> Erro: " + spiLinhaOnibus.getSelectedItemPosition());

        if(spiLinhaOnibus.getSelectedItemPosition() >= 0) {
            return spiLinhaOnibus.getSelectedItem().toString();
        } else {
            return "Lista de ônibus está vazia!";
        }

//        spiLinhaOnibus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                int index = spiLinhaOnibus.getSelectedItemPosition();
//                linhaOnibus =  list.get(index);
//                //Toast.makeText(getContext(),"You have selected item: " + list.get(index), Toast.LENGTH_LONG).show();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
    }
}
