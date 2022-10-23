package com.example.federicoisaia;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.federicoisaia.databinding.FragmentFragmentoConstruirPlazoFijoBinding;


public class FragmentoConstruirPlazoFijo extends Fragment {
    private FragmentFragmentoConstruirPlazoFijoBinding binding;
    private Moneda[] monedas;
    private NavController navController;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentFragmentoConstruirPlazoFijoBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        monedas = new Moneda[]{new Moneda("Dólar", 152.2), new Moneda("Peso Argentino", 1.0)};
        navController= NavHostFragment.findNavController(FragmentoConstruirPlazoFijo.this);
        ArrayAdapter adapter= new ArrayAdapter(binding.getRoot().getContext(),android.R.layout.simple_spinner_dropdown_item,monedas);
        binding.spinnerMonedas.setAdapter(adapter);
        binding.botonSimular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle nombreyapellido = new Bundle();
                nombreyapellido.putString("nombre", binding.campoNombre.getText().toString());
                nombreyapellido.putString("apellido", binding.campoApellido.getText().toString());
                navController.navigate(R.id.fragmentoSimularPlazoFijo,nombreyapellido);
            }
        });

        Bundle bundle = getArguments();
        if (bundle != null) {
            float capital_final = bundle.getFloat("capital");
            int plazo_final = bundle.getInt("dias");
            binding.campoNombre.setText(bundle.getString("nombre"));
            binding.campoApellido.setText(bundle.getString("apellido"));

            binding.botonConstruir.setEnabled(true);
            binding.botonConstruir.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    String nombre = binding.campoNombre.getText().toString();
                    String apellido = binding.campoApellido.getText().toString();
                    if (!nombre.isEmpty() && !apellido.isEmpty()) {
                        builder.setTitle("Felicitacones " + nombre + " " + apellido + "!");
                        builder.setMessage("Tu plazo fijo de " + capital_final + " " + binding.spinnerMonedas.getSelectedItem().toString() + " por "
                                + plazo_final + " días ha sido constituido!");

                        builder.setPositiveButton("Piola!", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                getActivity().finish();
                            }
                        });
                    } else {
                        builder.setTitle("Error");
                        builder.setMessage("Falto Indicar nonbre y/o apellido del solicitante");

                        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                    }
                    AlertDialog dialog = builder.create();
                    dialog.show();

                }
            });
        }
    }
}