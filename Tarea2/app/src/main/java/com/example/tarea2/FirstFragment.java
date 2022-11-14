package com.example.tarea2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.tarea2.databinding.FragmentFirstBinding;
import com.example.tarea2.models.Product;
import com.example.tarea2.viewModels.ProductVM;

import java.util.List;

public class FirstFragment extends Fragment implements RecyclerViewInterface{

    private FragmentFirstBinding binding;
    List<Product> products;
    ListAdapter adapter;
    private ProductVM productVM;
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        productVM = new ViewModelProvider(requireActivity()).get(ProductVM.class);
        binding = FragmentFirstBinding.inflate(inflater, container, false);
        adapter = new ListAdapter(null, this);
        productVM.getAllProducts().observe(getViewLifecycleOwner(), products -> adapter.setProducts(products));
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        binding.recyclerView.setAdapter(adapter);

        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.fab.setOnClickListener(view1 ->
                NavHostFragment.findNavController(FirstFragment.this)
                .navigate(R.id.action_FirstFragment_to_SecondFragment));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onEditClick(int position) {
        if(products == null){
            products = adapter.getProducts();
            Product product = products.get(position);
            System.out.println(product.getBrand());
            Bundle bundle = new Bundle();
            bundle.putInt("id", product.getId());
            bundle.putString("name", product.getName());
            bundle.putString("brand", product.getBrand());
            bundle.putDouble("price", product.getPrice());
            NavHostFragment.findNavController(FirstFragment.this)
                    .navigate(R.id.action_FirstFragment_to_SecondFragment, bundle);
        }
    }
}