package com.dhprac.myapp2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class FragmentTestActivity extends AppCompatActivity {

    TestFragment testFragment;
    TestFragment2 testFragment2;
    Button fragment1Btn;
    Button fragment2Btn;
    Button fragmentDeleteBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_test);

        fragment1Btn = findViewById(R.id.btn_fragment1);
        fragment2Btn = findViewById(R.id.btn_fragment2);
        fragmentDeleteBtn = findViewById(R.id.btn_fragment_delete);

        fragment1Btn.setOnClickListener(new Fragment1ClickListener());
        fragment2Btn.setOnClickListener(new Fragment2ClickListener());
        fragmentDeleteBtn.setOnClickListener(new FragmentDeleteClickListener());
    }

    class Fragment1ClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            testFragment = new TestFragment();

            // 관리자를 가져옴
            FragmentManager fragmentManager = getSupportFragmentManager();
            // 프래그먼트를 설정
            FragmentTransaction transaction = fragmentManager.beginTransaction();

            transaction.replace(R.id.fcv_fragment, testFragment);
            transaction.addToBackStack(null);

            transaction.commit();

        }
    }

    class Fragment2ClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            testFragment2 = TestFragment2.newInstance();

            // 관리자를 가져옴
            FragmentManager fragmentManager = getSupportFragmentManager();
            // 프래그먼트를 설정
            FragmentTransaction transaction = fragmentManager.beginTransaction();

            transaction.replace(R.id.fcv_fragment, testFragment2);
            transaction.addToBackStack(null);

            transaction.commit();

        }
    }

    class FragmentDeleteClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            // 현재 BackStack에 담겨있는 Fragment의 수를 가져옴
            int count = fragmentManager.getBackStackEntryCount();

            // BackStack에 하나라도 있으면 pop 아니면 finish
            if (count > 0) {
                fragmentManager.popBackStack();
            } else {
                finish();
            }
        }
    }
}