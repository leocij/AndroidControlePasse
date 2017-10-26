package com.lemelo.controlepasse;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Toast;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by leoci on 19/10/2017.
 */

public class RecargaFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.activity_recarga, container, false);
        getActivity().setTitle("Recarga de passe");

        ScrollView scrollView = (ScrollView) view.findViewById(R.id.scrollViewRecarga);
        scrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(MotionEvent.ACTION_DOWN == event.getAction()) {
                    //Fecha teclado ao clicar
                    new MyKeyboard().hideKeyboard(getActivity(), v);
                } else if(MotionEvent.ACTION_UP == event.getAction()) {
                    v.performClick();
                }

                return true;
            }
        });

        //Saldo atual
        final EditText txtSaldoAtual = (EditText) view.findViewById(R.id.txtSaldoAtual);
        txtSaldoAtual.setText(saldoAtual(txtSaldoAtual));
        txtSaldoAtual.setEnabled(false);

        //preenche data e hora
        final EditText txtDataHora = (EditText) view.findViewById(R.id.txtDataHora);
        SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
        txtDataHora.setText(sdf1.format(Calendar.getInstance().getTime()));

        final EditText txtValorPasse = (EditText) view.findViewById(R.id.txtValorPasse);
        NumberFormat nfValorPasse = NumberFormat.getCurrencyInstance();
        txtValorPasse.setText(nfValorPasse.format(0));
        txtValorPasse.addTextChangedListener(new MoneyTextWatcher(txtValorPasse));
        txtValorPasse.requestFocus();
        //Abre teclado automático
        new MyKeyboard().showKeyboard(getActivity());

        final EditText txtQuantidade = (EditText) view.findViewById(R.id.txtQuantidade);

        final EditText txtValorRecarga = (EditText) view.findViewById(R.id.txtValorRecarga);
        txtValorRecarga.setEnabled(false);

        final Button btnSalvar = (Button) view.findViewById(R.id.btnSalvar);

        txtQuantidade.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    String valorPasseStr = txtValorPasse.getText().toString();

                    String valorPasseNF = null;
                    try {
                        valorPasseNF = NumberFormat.getCurrencyInstance().parse(valorPasseStr).toString();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    BigDecimal valorPasse = new BigDecimal(valorPasseNF);

                    String quantidadeStr = txtQuantidade.getText().toString();
                    if(quantidadeStr == null || quantidadeStr.equals("")){
                        quantidadeStr = "0";
                    }
                    Integer quantidade = Integer.parseInt(quantidadeStr);

                    BigDecimal valorRecarga = valorPasse.multiply(BigDecimal.valueOf(quantidade));

                    NumberFormat nfValorRecarga = NumberFormat.getCurrencyInstance();
                    txtValorRecarga.setText(nfValorRecarga.format(valorRecarga));
                    txtValorRecarga.addTextChangedListener(new MoneyTextWatcher(txtValorRecarga));
                }
            }
        });

        txtValorRecarga.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    //Fecha teclado ao clicar
                    new MyKeyboard().hideKeyboard(getActivity(), view);
                    btnSalvar.requestFocus();
                }
            }
        });

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            SQLiteDatabase db;
            @Override
            public void onClick(View v) {
                try {
                    String saldoAtualStr = txtSaldoAtual.getText().toString();

                    String dataHoraStr = txtDataHora.getText().toString();

                    String valorPasseStr = txtValorPasse.getText().toString();
                    if(valorPasseStr.equals("")){
                        NumberFormat nf = NumberFormat.getCurrencyInstance();
                        valorPasseStr = nf.format(0);
                    }

                    String quantidadeStr = txtQuantidade.getText().toString();

                    FabricaConexao fabrica = new FabricaConexao(getContext());
                    db = fabrica.getWritableDatabase();
                    RecargaDao recargaDao = new RecargaDao(db);

                    Recarga recarga = new Recarga();
                    recarga.setSaldoAtual(saldoAtualStr);
                    recarga.setDataHora(dataHoraStr);
                    recarga.setValorPasse(valorPasseStr);
                    recarga.setQuantidade(quantidadeStr);

                    recargaDao.insert(recarga);

                    Toast.makeText(getContext(), "Registro Salvo!", Toast.LENGTH_LONG).show();

                    new MyRefresh().recargaFragment(getActivity());
//
//                    //Limpa formulário
//                    SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
//                    txtDataHora.setText(sdf1.format(Calendar.getInstance().getTime()));
//
//                    //fecha teclado e abre teclado
//                    new MyKeyboard().hideKeyboard(getActivity(),v);
//                    new MyKeyboard().showKeyboard(getActivity());
//
//                    NumberFormat nf = NumberFormat.getCurrencyInstance();
//                    txtValorPasse.setText(nf.format(0));
//                    txtValorPasse.addTextChangedListener(new MoneyTextWatcher(txtValorPasse));
//                    txtValorPasse.requestFocus();
//
//                    txtQuantidade.setText("");

                } catch (ParseException e) {
                    e.printStackTrace();
                } finally {
                    if (db != null && db.isOpen()) {
                        db.close();
                    }
                }
            }
        });

        return view;
    }

    private String saldoAtual(EditText txtSaldoAtual) {
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
}
