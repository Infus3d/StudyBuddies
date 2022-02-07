package com.example.hello_world;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.hello_world.databinding.FragmentFirstBinding;

import java.util.Random;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;
    private TextView showCountTextView;
    private TextView redTextView, greenTextView, blueTextView;
    private int redInt, blueInt, greenInt;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) /**{
        // Inflate the layout for this fragment
        View fragmentFirstLayout = inflater.inflate(R.layout.fragment_first, container, false);
        // Get the count text view
        showCountTextView = fragmentFirstLayout.findViewById(R.id.textview_first);

        return fragmentFirstLayout;
    } */

    {

        binding = FragmentFirstBinding.inflate(inflater, container, false);
        showCountTextView = binding.getRoot().findViewById(R.id.textview_first);
        redTextView = binding.getRoot().findViewById(R.id.red_hex);
        greenTextView = binding.getRoot().findViewById(R.id.green_hex);
        blueTextView = binding.getRoot().findViewById(R.id.blue_hex);
        showCountTextView.setText("#000000");

        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String hex_color = redTextView.getText().toString() + greenTextView.getText().toString() + blueTextView.getText().toString();
                //int currentCount = Integer.parseInt(showCountTextView.getText().toString());
                FirstFragmentDirections.ActionFirstFragmentToSecondFragment action = FirstFragmentDirections.actionFirstFragmentToSecondFragment(hex_color);
                action.setMyArg(hex_color);
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(action /** R.id.action_FirstFragment_to_SecondFragment */);
            }
        });

        view.findViewById(R.id.blue_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                blueInt = getRandom();
                blueTextView.setText(getHex(blueInt));
                showCountTextView.setText(showCountTextView.getText().toString().substring(0, 5) + getHex(blueInt));
            }
        });

        view.findViewById(R.id.red_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redInt = getRandom();
                redTextView.setText(getHex(redInt));
                showCountTextView.setText(showCountTextView.getText().toString().substring(0, 1) + getHex(redInt) + showCountTextView.getText().toString().substring(3));

                /**
                Toast myToast = Toast.makeText(getActivity(), "Hello toast!", Toast.LENGTH_SHORT);
                myToast.show(); */
            }
        });

        view.findViewById(R.id.green_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                greenInt = getRandom();
                greenTextView.setText(getHex(greenInt));
                showCountTextView.setText(showCountTextView.getText().toString().substring(0, 3) + getHex(greenInt) + showCountTextView.getText().toString().substring(5));
                //countMe(view);
            }
        });
    }
    /**
    private void countMe(View view){
        String countString = showCountTextView.getText().toString();
        Integer count = Integer.parseInt(countString) + 1;
        showCountTextView.setText(count.toString());
    } */
    private int getRandom(){
        Random random = new java.util.Random();
        return random.nextInt(256);
    }

    private String getHex(int x){
        return "" + hexxit(((x/16)%16)) + hexxit((x%16));
    }

    private String hexxit(int x){
        if(x == 15) return "F";
        if(x == 14) return "E";
        if(x == 13) return "D";
        if(x == 12) return "C";
        if(x == 11) return "B";
        if(x == 10) return "A";
        return "" + x;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}