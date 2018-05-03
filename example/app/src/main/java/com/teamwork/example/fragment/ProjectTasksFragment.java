package com.teamwork.example.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.teamwork.example.R;
import com.teamwork.example.model.TWTask;
import com.teamwork.example.pojo.response.TasksResponsePojo;
import com.teamwork.example.util.EnumUtil;

import org.parceler.apache.commons.lang.ArrayUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ProjectTasksFragment extends Fragment{
    //HOST
    private ProjectTasksCallback mActivity;

    //VIEWS
    private PieChart pieChart;
    private SwipeRefreshLayout refreshLayout;

    //OTHERS
    private List<TWTask> tasks;

    public ProjectTasksFragment() {
        // Required empty public constructor
    }

    public static ProjectTasksFragment newInstance() {
        return new ProjectTasksFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_project_tasks, container, false);

        pieChart = view.findViewById(R.id.fragment_project_tasks_chart);
        refreshLayout = view.findViewById(R.id.fragment_project_tasks_refresh_layout);

        refreshLayout.setOnRefreshListener(() -> {
            mActivity.onRequestListMyTasks();
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ProjectTasksFragment.ProjectTasksCallback) {
            mActivity = (ProjectTasksFragment.ProjectTasksCallback) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement ProjectTasksFragment.ProjectTasksCallback");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mActivity = null;
    }

    //TODO : Needs to be refactored. I was running out of time already at this point but it's definitely something to have a proper look later
    private void updateChart(){
        List<PieEntry> entries = new ArrayList<>();
        int late = 0, today = 0, noDate = 0, completed = 0, invalidDate = 0, started = 0, others = 0;

        for (TWTask task : tasks){
            // ------ no date / started -----//
            if(task.getDateDue() == null || task.getDateDue().isEmpty()){
                if(task.getDateStart() == null || task.getDateStart().isEmpty()){
                    noDate++;
                }else{
                    started++;
                }
                continue;
            }
            // ------ invalid date -----//
            Date dueDate;
            //This is the format coming from API for this date field. Time is being ignored.
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd", Locale.ENGLISH);

            try {
                dueDate = dateFormat.parse(task.getDateDue());

            } catch (ParseException e) {
                dueDate = null;
            }

            if(dueDate == null){
                invalidDate++;
                continue;
            }
            // ------ late -----//
            if(new Date().compareTo(dueDate) > 0 && !task.isCompleted()){
                late++;
                continue;
            }
            // ------ today -----//
            if(new Date().compareTo(dueDate) == 0 && !task.isCompleted()){
                today++;
                continue;
            }
            // ------ completed -----//
            if(task.isCompleted()){
                completed++;
                continue;
            }
            // ------ other status (not started, on hold...) -----//
            others++;
        }

        List<Integer> colors = new ArrayList<>();

        if(late > 0){
            entries.add(new PieEntry(late, getString(R.string.project_task_status_late)));
            colors.add(R.color.example_task_status_late);
        }
        if(today > 0){
            entries.add(new PieEntry(today, getString(R.string.project_task_status_today)));
            colors.add(R.color.example_task_status_today);
        }
        if(noDate > 0){
            entries.add(new PieEntry(noDate, getString(R.string.project_task_status_no_date)));
            colors.add(R.color.example_task_status_no_date);
        }
        if(completed > 0){
            entries.add(new PieEntry(completed, getString(R.string.project_task_status_completed)));
            colors.add(R.color.example_task_status_completed);
        }
        if(invalidDate > 0){
            entries.add(new PieEntry(invalidDate, getString(R.string.project_task_status_invalid_date)));
            colors.add(R.color.example_task_status_invalid);
        }
        if(started > 0){
            entries.add(new PieEntry(started, getString(R.string.project_task_status_started)));
            colors.add(R.color.example_task_status_started);
        }
        if(others > 0){
            entries.add(new PieEntry(others, getString(R.string.project_task_status_other)));
            colors.add(R.color.example_task_status_other);
        }

        PieDataSet dataSet = new PieDataSet(entries, "");
        dataSet.setColors(ArrayUtils.toPrimitive(colors.toArray(new Integer[colors.size()])), getContext());

        dataSet.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        dataSet.setSliceSpace(1.5f);
        dataSet.setDrawValues(true);
        dataSet.setValueTextSize(16);
        dataSet.setValueFormatter((value, entry, dataSetIndex, viewPortHandler) -> String.valueOf((int)value));

        PieData data = new PieData(dataSet);
        pieChart.setData(data);
        pieChart.setDrawHoleEnabled(true);
        pieChart.getLegend().setEnabled(true);
        pieChart.getLegend().setTextSize(16);
        pieChart.getLegend().setOrientation(Legend.LegendOrientation.VERTICAL);
        pieChart.getLegend().setDrawInside(false);
        pieChart.getLegend().setFormToTextSpace(10);
        pieChart.getDescription().setEnabled(false);
        pieChart.setDrawEntryLabels(false);
        pieChart.setExtraBottomOffset(10);
        pieChart.invalidate();
    }


    //PUBLIC METHODS

    public void onProjectTasksRetrieved(TasksResponsePojo response){
        if(refreshLayout.isRefreshing()){
            refreshLayout.setRefreshing(false);
        }
        if(response.getCode().equals(EnumUtil.RESPONSE_CODE.SUCCESS) && response.getTasks() != null){
            tasks = new ArrayList<>();
            tasks.addAll(response.getTasks());
        }

        updateChart();
    }

    public void refreshIfNeeded(){
        if(tasks == null){
            refreshLayout.setRefreshing(true);
            mActivity.onRequestListMyTasks();
        }
    }




    //FRAGMENT INTERFACE CALLBACK

    public interface ProjectTasksCallback{
        void onRequestListMyTasks();
    }
}
