package com.example.tarea2;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import com.example.tarea2.databinding.FragmentSecondBinding;
import com.example.tarea2.models.Product;
import com.example.tarea2.repositories.ProductRepository;
import com.example.tarea2.viewModels.ProductVM;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import static android.app.Activity.RESULT_OK;

public class SecondFragment extends Fragment {

    private FragmentSecondBinding binding;
    private ProductVM productVM;
    private Product aux;
    private Uri filePath;
    FirebaseStorage storage;
    StorageReference storageReference;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSecondBinding.inflate(inflater, container, false);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        productVM = new ViewModelProvider(requireActivity()).get(ProductVM.class);

        if(getArguments() != null){
            final long ONE_MB = 3024*3024;
            try {
                aux = productVM.findById(getArguments().getInt("id"));
            } catch (ExecutionException | InterruptedException e) {
                throw new RuntimeException(e);
            }
            StorageReference path = storageReference.child("images/"+aux.getUuid());
            path.getBytes(ONE_MB).addOnSuccessListener(bytes -> {
                Bitmap bm = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                binding.inputImg.setImageBitmap(bm);
            });

            binding.inputName.setText(aux.getName());
            binding.inputDesc.setText(aux.getBrand());
            binding.inputPrice.setText(aux.getPrice().toString());
        }
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.saveBtn.setOnClickListener(this::saveProduct);
        binding.clearBtn.setOnClickListener(this::clearForm);
        binding.deleteBtn.setOnClickListener(this::deleteProduct);

        binding.inputImg.setOnClickListener(view1 -> {
            //open an intent to pick or make a picture here
            selectImage();
        });

    }

    private void selectImage(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select image from here..."),22);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 22 && resultCode == RESULT_OK && data != null && data.getData() != null){
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(requireContext().getContentResolver(),filePath);
                binding.inputImg.setImageBitmap(bitmap);
            }catch (IOException e){
                e.printStackTrace();
            }
        }

    }
    private void uploadImg(Product product){
        if(filePath != null){
            ProgressDialog progressDialog = new ProgressDialog(getContext());
            progressDialog.setTitle("Uploading...");
            progressDialog.show();
            if(product.getId() == -1){
                product.setUuid(UUID.randomUUID().toString());
            }

            StorageReference ref = storageReference.child("images/"+ product.getUuid());
            ref.putFile(filePath).addOnSuccessListener(taskSnapshot -> {
                //product.setUri(ref.getDownloadUrl().getResult());
                progressDialog.dismiss();
                Toast.makeText(requireContext(), "Image Uploaded", Toast.LENGTH_SHORT).show();
            }). addOnFailureListener(e -> {
                progressDialog.dismiss();
                Toast.makeText(requireContext(), "Failed to upload", Toast.LENGTH_SHORT).show();
            }).addOnProgressListener(snapshot -> {
                double progress = (100.0 * snapshot.getBytesTransferred()/ snapshot.getTotalByteCount());
                progressDialog.setMessage("Uploaded"+(int)progress+"%");
            });
        }
    }


    public void saveProduct(View view){

        Product product = new Product(binding.inputName.getText().toString(),
                binding.inputDesc.getText().toString(), Double.parseDouble(binding.inputPrice.getText().toString()));
        if(aux != null){
            product.setId(aux.getId());
            uploadImg(product);
            productVM.update(product);
        }else {
            product.setId(-1);
            uploadImg(product);
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