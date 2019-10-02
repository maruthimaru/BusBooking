package com.jp.busbooking.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.jp.busbooking.R;
import com.jp.busbooking.activity.LoginActivity;
import com.jp.busbooking.helper.StaticData;

public class ChoosePlaceFragment extends Fragment {
View view;
StaticData staticData;
TextView search,login;
EditText from, to;
FloatingActionButton floatingActionButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_choose_place,container,false);
        search=view.findViewById(R.id.search);
        from=view.findViewById(R.id.from);
        login=view.findViewById(R.id.login);
        to=view.findViewById(R.id.to);
        staticData=new StaticData(getActivity());
        floatingActionButton=view.findViewById(R.id.floatingActionButton);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String fromPla=from.getText().toString();
                String toPal=to.getText().toString();
                if (fromPla.length()>0){
                    from.setError(null);
                    if (toPal.length()>0){
                        to.setError(null);
                        setFragment(new BusListFragment(),fromPla,toPal);
                    }else {
                        to.requestFocus();
                        to.setError("need to add");
                    }
                }  else {
                    from.requestFocus();
                    from.setError("need to add");
                }
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(new LoginActivity(),"","");
            }
        });
        staticData.handleBackPress(view);
        return view;
    }

    private void setFragment(Fragment fragment,String from,String to) {
        // create a FragmentManager
        Bundle bundle=new Bundle();
        bundle.putString("from",from);
        bundle.putString("to",to);
        FragmentManager fm = getActivity().getSupportFragmentManager();
        // create a FragmentTransaction to begin the transaction and replace the Fragment
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        // replace the FrameLayout with new Fragment
        fragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.framLayout, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit(); // save the changes
    }
}
