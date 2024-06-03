package com.example.soft;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import java.util.*;

public class FoodActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_good_food);

        ViewPager2 viewPager2 = findViewById(R.id.viewPager);

        List<FoodData.Food> goodFoodsList = new ArrayList<>();
        // 예제 데이터 생성

        goodFoodsList.add(new FoodData.Food("높은 항산화 성분과 전분 소화 효소가 함유되어 있어 당뇨병 환자에게 도움이 됩니다.", "시금치", "https://various.foodsafetykorea.go.kr/nutrient/general/food/detail.do?dbGrpCm=A&searchTextPre=&searchTextListStr=&sortOrder=DESC&sortFieldCnt=1&searchProcCode=LOG_IDX&searchLogType=main&searchSubOrderby=&searchDetailMode=all&searchGroup=&searchCrtMth=&searchMaker=&searchSource=&searchClass=&searchQc=&searchHcln=&searchReduct=&searchText=%EC%8B%9C%EA%B8%88%EC%B9%98&searchOper=AND&searchGroupText=&searchMakerText=&searchOrderby=CAL&searchPageCnt=10&pagenum=1&totalListCnt=172&pageblock=10&pagesize=10&searchFoodCd=D113-586000000-0001&searchRegionCd=ZZ&searchMonthCd=AVG", R.drawable.gf0 ));
        goodFoodsList.add(new FoodData.Food("식물성 단백질의 원천으로 탄수화물의 섭취를 줄이도록 돕습니다. 또한, 혈압과 콜레스테롤 조절에 도움을 주어 혈당 수치를 관리하는 데 도움이 됩니다.", "콩-두유", "https://various.foodsafetykorea.go.kr/nutrient/general/food/detail.do?dbGrpCm=A&searchTextPre=&searchTextListStr=&sortOrder=DESC&sortFieldCnt=1&searchProcCode=LOG_IDX&searchLogType=main&searchSubOrderby=&searchDetailMode=all&searchGroup=&searchCrtMth=&searchMaker=&searchSource=&searchClass=&searchQc=&searchHcln=&searchReduct=&searchText=%EC%BD%A9&searchOper=AND&searchGroupText=&searchMakerText=&searchOrderby=CAL&searchPageCnt=10&pagenum=3&totalListCnt=1549&pageblock=10&pagesize=10&searchFoodCd=P109-006050200-F004-001&searchRegionCd=ZZ&searchMonthCd=AVG", R.drawable.gf2 ));
        goodFoodsList.add(new FoodData.Food("불포화지방산이 풍부한 견과류는 혈중 콜레스테롤 수치를 낮추고 동맥경화, 고혈압 등을 예방하는 효과가 있습니다.","견과류-호두","https://various.foodsafetykorea.go.kr/nutrient/general/food/detail.do?dbGrpCm=A&searchTextPre=&searchTextListStr=&sortOrder=DESC&sortFieldCnt=1&searchProcCode=LOG_IDX&searchLogType=main&searchSubOrderby=&searchDetailMode=all&searchGroup=&searchCrtMth=&searchMaker=&searchSource=&searchClass=&searchQc=&searchHcln=&searchReduct=&searchText=%EA%B2%AC%EA%B3%BC%EB%A5%98&searchOper=AND&searchGroupText=&searchMakerText=&searchOrderby=CAL&searchPageCnt=10&pagenum=1&totalListCnt=362&pageblock=10&pagesize=10&searchFoodCd=P116-302030200-0329&searchRegionCd=ZZ&searchMonthCd=AVG",R.drawable.gf3));

        FoodData foodData = new FoodData(
                new FoodData.BadFoods(),
                new FoodData.GoodFoods(goodFoodsList)
        );

        // 어댑터에 데이터 설정
        FoodAdapter adapter = new FoodAdapter(foodData.getGoodFoods().getGoodFoods());
        viewPager2.setAdapter(adapter);
    }
}
