package com.example.hello_world;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.hello_world.databinding.FragmentSecondBinding;

import java.util.Locale;
import java.util.Random;

public class SecondFragment extends Fragment {

    private FragmentSecondBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentSecondBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //Integer count = SecondFragmentArgs.fromBundle(getArguments()).getMyArg();
        String hex_color = SecondFragmentArgs.fromBundle(getArguments()).getMyArg();
        String countText = getString(R.string.random_heading, hex_color);
        TextView headerView = view.getRootView().findViewById(R.id.textview_header);
        View myRootView = view.getRootView().findViewById(R.id.rootView_fragment2);
        myRootView.setBackgroundColor(Color.parseColor(hex_color));
        headerView.setText(countText);
//        Random random = new java.util.Random();
//        Integer randomNumber = 0;
//        if (count > 0) {
//            randomNumber = random.nextInt(count + 1);
//        }
//        TextView randomView = view.getRootView().findViewById(R.id.textView_random);
//        randomView.setText(randomNumber.toString());

        binding.buttonSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(SecondFragment.this)
                        .navigate(R.id.action_SecondFragment_to_FirstFragment);
            }
        });
    }
    @ColorInt
    private int get_Int(String hex_color){
        int ret = Integer.parseInt(hex_color, 16);
        return ret;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}