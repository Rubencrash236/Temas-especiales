package com.example.examen1_ruben_osmani;

import android.view.View;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import org.jetbrains.annotations.NotNull;

public class ProductViewHolder extends RecyclerView.ViewHolder {
    private TextView pName;
    private TextView pDescription;
    private TextView pPrice;

    public ProductViewHolder(@NotNull View pView){
        super(pView);
        pName = pView.findViewById(R.id.pTitle);
        pDescription = pView.findViewById(R.id.pDescription);
        pPrice = pView.findViewById(R.id.pPrice);
    }

    public TextView getpName() {
        return pName;
    }

    public void setpName(TextView pName) {
        this.pName = pName;
    }

    public TextView getpDescription() {
        return pDescription;
    }

    public void setpDescription(TextView pDescription) {
        this.pDescription = pDescription;
    }

    public TextView getpPrice() {
        return pPrice;
    }

    public void setpPrice(TextView pPrice) {
        this.pPrice = pPrice;
    }
}
