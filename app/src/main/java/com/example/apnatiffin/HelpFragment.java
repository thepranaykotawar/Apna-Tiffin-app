package com.example.apnatiffin;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class HelpFragment extends Fragment {
    TextView Heading1TV,Heading3TV,Heading4TV,Heading5TV,text1,text2,text3,Heading2TV,text4,text5,text6,text7,text8,text9,text10,text11,text12,text13,text14,text15;
    ImageView btn1,btn2,btn3,btn4,btn5;
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_help,container,false);

        btn1 = root.findViewById(R.id.btn1);
        btn2 = root.findViewById(R.id.btn2);
        btn3 = root.findViewById(R.id.btn3);
        btn4 = root.findViewById(R.id.btn4);
        btn5 = root.findViewById(R.id.btn5);

        Heading1TV = root.findViewById(R.id.Heading1TV);
        Heading2TV = root.findViewById(R.id.Heading2TV);
        Heading3TV = root.findViewById(R.id.Heading3TV);
        Heading4TV = root.findViewById(R.id.Heading4TV);
        Heading5TV = root.findViewById(R.id.Heading5TV);

        text1 = root.findViewById(R.id.text1);
        text2 = root.findViewById(R.id.text2);
        text3 = root.findViewById(R.id.text3);
        text4 = root.findViewById(R.id.text4);
        text5 = root.findViewById(R.id.text5);
        text6 = root.findViewById(R.id.text6);
        text7 = root.findViewById(R.id.text7);
        text8 = root.findViewById(R.id.text8);
        text9 = root.findViewById(R.id.text9);
        text10 = root.findViewById(R.id.text10);
        text11 = root.findViewById(R.id.text11);
        text12 = root.findViewById(R.id.text12);
        text13 = root.findViewById(R.id.text13);
        text14 = root.findViewById(R.id.text14);
        text15 = root.findViewById(R.id.text15);


        btn1.setOnClickListener(v -> {
            if(text1.getVisibility() == View.GONE && text2.getVisibility() == View.GONE && text3.getVisibility() == View.GONE){
                text1.setVisibility(View.VISIBLE);
                text2.setVisibility(View.VISIBLE);
                text3.setVisibility(View.VISIBLE);
                btn1.setImageResource(R.drawable.ic_uparrow);
            }else {
                text1.setVisibility(View.GONE);
                text2.setVisibility(View.GONE);
                text3.setVisibility(View.GONE);
                btn1.setImageResource(R.drawable.ic_downarrow);
            }
        });
        btn2.setOnClickListener(v -> {
            if(text4.getVisibility() == View.GONE && text5.getVisibility() == View.GONE && text6.getVisibility() == View.GONE){
                text4.setVisibility(View.VISIBLE);
                text5.setVisibility(View.VISIBLE);
                text6.setVisibility(View.VISIBLE);
                btn2.setImageResource(R.drawable.ic_uparrow);
            }else {
                text4.setVisibility(View.GONE);
                text5.setVisibility(View.GONE);
                text6.setVisibility(View.GONE);
                btn2.setImageResource(R.drawable.ic_downarrow);
            }
        });
        btn3.setOnClickListener(v -> {
            if(text7.getVisibility() == View.GONE && text8.getVisibility() == View.GONE && text9.getVisibility() == View.GONE){
                text7.setVisibility(View.VISIBLE);
                text8.setVisibility(View.VISIBLE);
                text9.setVisibility(View.VISIBLE);
                btn3.setImageResource(R.drawable.ic_uparrow);
            }else {
                text7.setVisibility(View.GONE);
                text8.setVisibility(View.GONE);
                text9.setVisibility(View.GONE);
                btn3.setImageResource(R.drawable.ic_downarrow);
            }
        });
        btn4.setOnClickListener(v -> {
            if(text10.getVisibility() == View.GONE && text11.getVisibility() == View.GONE && text12.getVisibility() == View.GONE){
                text10.setVisibility(View.VISIBLE);
                text11.setVisibility(View.VISIBLE);
                text12.setVisibility(View.VISIBLE);
                btn4.setImageResource(R.drawable.ic_uparrow);
            }else {
                text10.setVisibility(View.GONE);
                text11.setVisibility(View.GONE);
                text12.setVisibility(View.GONE);
                btn4.setImageResource(R.drawable.ic_downarrow);
            }
        });
        btn5.setOnClickListener(v -> {
            if(text13.getVisibility() == View.GONE && text14.getVisibility() == View.GONE && text15.getVisibility() == View.GONE){
                text13.setVisibility(View.VISIBLE);
                text14.setVisibility(View.VISIBLE);
                text15.setVisibility(View.VISIBLE);
                btn5.setImageResource(R.drawable.ic_uparrow);
            }else {
                text13.setVisibility(View.GONE);
                text14.setVisibility(View.GONE);
                text15.setVisibility(View.GONE);
                btn5.setImageResource(R.drawable.ic_downarrow);
            }
        });
        return root;

    }
}