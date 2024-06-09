package com.example.soft;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.net.Uri;

import java.util.List;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewHolder> {
    private List<FoodData> foodList;

    public FoodAdapter(List<FoodData> foodList) {
        this.foodList = foodList;
    }

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_food, parent, false);
        return new FoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {
        FoodData food = foodList.get(position);
        holder.foodName.setText(food.getName());
        holder.foodDescription.setText(food.getDescription());

        // food.getImageResId()가 "bf0"와 같은 String 형태로 반환된다고 가정합니다.
        String imageResName = food.getImageResId();
        // context는 holder가 속한 Activity나 Fragment의 context를 사용합니다.
        int resId = holder.itemView.getContext().getResources().getIdentifier(
                imageResName, "drawable", holder.itemView.getContext().getPackageName());
        // 얻은 리소스 ID를 사용하여 이미지를 설정합니다.
        holder.foodImage.setImageResource(resId);

        holder.foodUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(food.getUrl()));
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }

    public static class FoodViewHolder extends RecyclerView.ViewHolder {
        ImageView foodImage;
        TextView foodName;
        TextView foodDescription;
        TextView foodUrl;

        public FoodViewHolder(@NonNull View itemView) {
            super(itemView);
            foodImage = itemView.findViewById(R.id.food_image);
            foodName = itemView.findViewById(R.id.food_name);
            foodDescription = itemView.findViewById(R.id.food_description);
            foodUrl = itemView.findViewById(R.id.food_url);
        }
    }
}