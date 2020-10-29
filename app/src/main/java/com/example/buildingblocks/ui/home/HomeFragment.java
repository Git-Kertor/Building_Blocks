package com.example.buildingblocks.ui.home;

import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.AnimatorRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.vectordrawable.graphics.drawable.AnimationUtilsCompat;

import com.example.buildingblocks.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    TextView well;
    CheckBox chek;
    TextView teksto;
    View Movement;
    int[] completedTasks = {0,0,0,0};
    int TaskIndex = 0;
    int TaskLimit;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        well = root.findViewById(R.id.WellDone);
        chek = root.findViewById(R.id.Current_Task);
        teksto = root.findViewById(R.id.text_box);

        Typeface typeface = ResourcesCompat.getFont(this.getContext(), R.font.montser);
        teksto.setTypeface(typeface);

        chek.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                TaskCompleted();
            }
        });

        GetTask();
        return root;
    }
    public void SetTask(String task, int completionLevel)
    {
        boolean x = completionLevel < 4;
        if(x)
        {
            well.setVisibility(View.INVISIBLE);
            chek.setVisibility(View.VISIBLE);
            teksto.setVisibility(View.VISIBLE);
        }
        else
        {
            chek.setVisibility(View.INVISIBLE);
            teksto.setVisibility(View.INVISIBLE);
            well.setVisibility(View.VISIBLE); return;

        }
        teksto.setText(task);
    }

    public Void SetAnimation(boolean leave)
    {
        if(completedTasks[TaskIndex] > 3) {return null;}
        Animation animation = AnimationUtils.loadAnimation(chek.getContext(),
                (leave)?android.R.anim.slide_out_right: android.R.anim.slide_in_left);
        chek.setClickable(false);
        animation.setStartOffset(300);

        if(leave) {
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    completedTasks[TaskIndex] += 1;
                    GetTask();
                    SetAnimation(false);
                    chek.setChecked(false);
                    chek.setClickable(true);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
        }
        teksto.startAnimation(animation);
        return null;
    }

    public void TaskCompleted()
    {
        SetAnimation(true);
    }

    public void GetTask()
    {
        int currentTime = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        String[] morningTime = {
                "A clean shirt would look nice on you!",
                "A beard makes you look old, but you can shave it!",
                "A shower freshens you up. Always!",
                "Clean dishes are inviting!"
        };
        String[] noonTime = {
                "I wonder how your friend is doing.",
                "The store next door has a large ice cream collection.",
                "Working strengthens the heart!",
                "You can't do anything with an empty stomach."
        };
        String[] afternoonTime = {
                "Doing the dishes is a fun way to play with water",
                "How about a few jumping jacks outside?",
                "You can't go on with an empty stomach.",
                "How about a stroll around the park."
        };
        String[] eveningTime = {
                "How about a warm cup of tea and a sandwich! Yummy!",
                "Peace is something to enjoy!",
                "Clean teeth keep the dentist away!",
                "Early to bed, early to rise!"
        };

        boolean morning = currentTime < 12;
        boolean noon = currentTime < 16;
        boolean afternoon = currentTime < 20;
        String task;

        if(completedTasks[TaskIndex] > 3) {SetTask("", 10); return;}

        if(morning)
        {
            TaskIndex = 0;
            completedTasks[1] = 0;
            completedTasks[2] = 0;
            completedTasks[3] = 0;
            task = morningTime[completedTasks[TaskIndex]];
        }
        else if(noon)
        {
            TaskIndex = 1;
            completedTasks[0] = 0;
            completedTasks[2] = 0;
            completedTasks[3] = 0;
            task = afternoonTime[completedTasks[TaskIndex]];
        }
        else if(afternoon)
        {
            TaskIndex = 2;
            completedTasks[0] = 0;
            completedTasks[1] = 0;
            completedTasks[3] = 0;
            task = noonTime[completedTasks[TaskIndex]];
        }
        else
        {
            TaskIndex = 3;
            completedTasks[0] = 0;
            completedTasks[1] = 0;
            completedTasks[2] = 0;
            task = eveningTime[completedTasks[TaskIndex]];
        }
        SetTask(task, completedTasks[TaskIndex]);
    }
}