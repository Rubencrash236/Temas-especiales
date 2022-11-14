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
    private Product aux;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentSecondBinding.inflate(inflater, container, false);

        if(getArguments() != null){
            aux = new Product();
            aux.setId(getArguments().getInt("id"));
            aux.setName(getArguments().getString("name"));
            aux.setBrand(getArguments().getString("brand"));
            aux.setPrice(getArguments().getDouble("price"));
            binding.inputName.setText(aux.getName());
            binding.inputDesc.setText(aux.getBrand());
            binding.inputPrice.setText(aux.getPrice().toString());
        }
        productVM = new ViewModelProvider(requireActivity()).get(ProductVM.class);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.saveBtn.setOnClickListener(this::saveProduct);
        binding.clearBtn.setOnClickListener(this::clearForm);
        binding.deleteBtn.setOnClickListener(this::deleteProduct);

    }

    public void saveProduct(View view){

        Product product = new Product(binding.inputName.getText().toString(),
                binding.inputDesc.getText().toString(), Double.parseDouble(binding.inputPrice.getText().toString()));
        if(aux != null){
            product.setId(aux.getId());
            productVM.update(product);
        }else {
            productVM.insert(product);
        }

        //MainActivity.products.add(product);
        NavHostFragment.findNavController(SecondFragment.this)
                .navigate(R.id.action_SecondFragment_to_FirstFragment);
    }

    public void clearForm(View view){
        binding.inputDesc.setText("");
        binding.inputName.setText("");
        binding.inputPrice.setText("");
    }

    public void deleteProduct(View view) {
        productVM.delete(aux);
        NavHostFragment.findNavController(SecondFragment.this)
                .navigate(R.id.action_SecondFragment_to_FirstFragment);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}