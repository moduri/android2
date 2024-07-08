package com.dhprac.myapp2;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dhprac.myapp2.databinding.FragmentTest2Binding;
import com.dhprac.myapp2.databinding.FragmentTestBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TestFragment2#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TestFragment2 extends Fragment {

    FragmentTest2Binding fragmentTest2Binding;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TestFragment2() {

    }

    public static TestFragment2 newInstance() {
        TestFragment2 fragment = new TestFragment2();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentTest2Binding = FragmentTest2Binding.inflate(inflater);
        return fragmentTest2Binding.getRoot();
    }
}