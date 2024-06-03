package com.example.soft;
import java.util.*;

public class FoodData {
    private BadFoods bad_foods;
    private GoodFoods good_foods;

    public FoodData() {
    }

    public FoodData(BadFoods bad_foods, GoodFoods good_foods) {
        this.bad_foods = bad_foods;
        this.good_foods = good_foods;
    }

    public BadFoods getBadFoods() {
        return bad_foods;
    }

    public void setBadFoods(BadFoods bad_foods) {
        this.bad_foods = bad_foods;
    }

    public GoodFoods getGoodFoods() {
        return good_foods;
    }

    public void setGoodFoods(GoodFoods good_foods) {
        this.good_foods = good_foods;
    }

    public static class BadFoods {
        private List<Food> bad_foods;

        public BadFoods() {
        }

        public BadFoods(List<Food> bad_foods) {
            this.bad_foods = bad_foods;
        }

        public List<Food> getBadFoods() {
            return bad_foods;
        }

        public void setBadFoods(List<Food> bad_foods) {
            this.bad_foods = bad_foods;
        }
    }

    public static class GoodFoods {
        private List<Food> good_foods;

        public GoodFoods() {
        }

        public GoodFoods(List<Food> good_foods) {
            this.good_foods = good_foods;
        }

        public List<Food> getGoodFoods() {
            return good_foods;
        }

        public void setGoodFoods(List<Food> good_foods) {
            this.good_foods = good_foods;
        }
    }

    public static class Food {
            private String description;
            private String name;
            private String url;
            private int imageResId;

            public Food() {
        }

        public Food(String description, String name, String url, int imageResId) {
            this.description = description;
            this.name = name;
            this.url = url;
            this.imageResId = imageResId;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public void setImageResId(int imageResId) { this.imageResId = imageResId; }
        public int getImageResId() { return imageResId; }
    }
}
