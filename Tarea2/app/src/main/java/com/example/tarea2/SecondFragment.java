package com.example.tarea2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import com.example.tarea2.databinding.FragmentSecondBinding;
import com.example.tarea2.models.Product;
import com.example.tarea2.repositories.ProductRepository;
import com.example.tarea2.viewModels.ProductVM;

public class SecondFragment extends Fragment {

    private FragmentSecondBinding binding;
    private ProductVM productVM;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentSecondBinding.inflate(inflater, container, false);
        productVM = new ViewModelProvider(requireActivity()).get(ProductVM.class);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.saveBtn.setOnClickListener(this::saveProduct);
        binding.clearBtn.setOnClickListener(this::clearForm);

    }

    public void saveProduct(View view){

        Product product = new Product(binding.inputName.getText().toString(),
                binding.inputDesc.getText().toString(), Double.parseDouble(binding.inputPrice.getText().toString()));
        productVM.insert(product);
        //MainActivity.products.add(product);
        NavHostFragment.findNavController(SecondFragment.this)
                .navigate(R.id.action_SecondFragment_to_FirstFragment);
    }

    public void clearForm(View view){
        binding.inputDesc.setText("");
        binding.inputName.setText("");
        binding.inputPrice.setText("");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}