package com.example.federicoisaia;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.SeekBar;

import com.example.federicoisaia.databinding.FragmentFragmentoSimularPlazoFijoBinding;

import java.text.DecimalFormat;


public class FragmentoSimularPlazoFijo extends Fragment {
    private DecimalFormat formato = new DecimalFormat();
    private FragmentFragmentoSimularPlazoFijoBinding binding;
    private Integer dias_plazo;
    private Float capital;
    private Double tna;
    private Double intereses_ganados;
    private Double monto_total;
    private Double monto_total_anual;
    private NavController navController;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tna=0.0;
        intereses_ganados=0.0;
        monto_total=0.0;
        monto_total_anual=0.0;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFragmentoSimularPlazoFijoBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController= NavHostFragment.findNavController(FragmentoSimularPlazoFijo.this);
        binding.campoTna.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
            calcular();
            }
        });

        binding.campoTea.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                calcular();
            }
        });
        binding.campoCapital.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                calcular();
            }
        });
        binding.barraPlazo.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                calcular();
                binding.etiquetaPlazo.setText(binding.barraPlazo.getProgress()*30+" días");
                binding.indicadorDiasPlazo.setText(binding.barraPlazo.getProgress()*30+" días");
                calcular();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        binding.botonConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putFloat("capital", capital);
                bundle.putInt("dias", dias_plazo);
                bundle.putString("nombre", getArguments().getString("nombre"));
                bundle.putString("apellido", getArguments().getString("apellido"));

                navController.navigate(R.id.action_fragmentoSimularPlazoFijo_to_fragmentoConstruirPlazoFijo,bundle);
            }
        });


    }
    private void calcular(){
        if(camposvacíos() || camposNegativos())binding.botonConfirmar.setEnabled(false);
        else {
            binding.botonConfirmar.setEnabled(true);
            capital = Float.parseFloat(String.valueOf(binding.campoCapital.getText().toString()));
            tna = Double.parseDouble(String.valueOf(binding.campoTna.getText().toString()));
            dias_plazo = 30 * (binding.barraPlazo.getProgress());
            intereses_ganados = capital * ((tna/100) * dias_plazo / 365);
            monto_total = intereses_ganados + capital;
            monto_total_anual = (intereses_ganados * 12) + capital;

            formato.setMaximumFractionDigits(2);

            binding.etiquetaPlazo.setText("Plazo: " + dias_plazo + " días");
            binding.etiquetaCapital.setText("Capital: " + formato.format(capital));
            binding.etiquetaInteresGanado.setText("Intereses ganados: $ " + formato.format(intereses_ganados));
            binding.etiquetaMontoTotal.setText("Monto total: $ " + formato.format(monto_total));
            binding.etiquetaMontoAnual.setText("Monto total anual: $ " + formato.format(monto_total_anual));
        }
        return;


    }
    private Boolean camposvacíos(){
        return (binding.campoTna.getText().toString().isEmpty() || binding.campoTea.getText().toString().isEmpty() || binding.campoCapital.getText().toString().isEmpty());
    }
    private Boolean camposNegativos(){

        return (Double.parseDouble(binding.campoTna.getText().toString()) < 0.0 || Double.parseDouble(binding.campoCapital.getText().toString()) < 0.0 || Double.parseDouble(binding.campoTea.getText().toString()) < 0.0 ) ;
    }


}