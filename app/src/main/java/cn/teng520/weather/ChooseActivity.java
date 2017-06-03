package cn.teng520.weather;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ChooseActivity extends Activity {

    private ExpandableListView expandableListView;
    private List<String> groupData;
    private List<List<String>> childrenData;
    private void loadData(){
        groupData = new ArrayList<String>();
        childrenData = new ArrayList<List<String>>();
        try{
            InputStream is = getAssets().open("cities.json");
            String jsonText = new TextReader().readText(is, "UTF-8");
            JSONArray arr = new JSONArray(jsonText);
            List<String> city;
            for(int i=0;i<arr.length();i++){
                JSONObject o = arr.getJSONObject(i);
                groupData.add(o.getString("name"));
                JSONArray a = o.getJSONArray("cities");
                city = new ArrayList<String>();
                for(int j=0;j<a.length();j++){
                    JSONObject n = a.getJSONObject(j);
                    city.add(n.getString("n"));
                }
                childrenData.add(city);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_choose);

        loadData();

        expandableListView = (ExpandableListView) findViewById(R.id.expendlist);
        expandableListView.setAdapter(new ExpandableAdapter(ChooseActivity.this));
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
                Toast.makeText(getApplicationContext(),childrenData.get(i).get(i1),Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ChooseActivity.this,MainActivity.class);
                intent.putExtra("cityName",childrenData.get(i).get(i1));
                startActivity(intent);
                return false;
            }
        });
    }

    private class ExpandableAdapter extends BaseExpandableListAdapter{
        Activity activity;
        public ExpandableAdapter(Activity a){
            activity=a;
        }

        @Override
        public Object getChild(int i, int i1) {
            return childrenData.get(i).get(i1);
        }

        @Override
        public long getChildId(int i, int i1) {
            return i1;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            String string = childrenData.get(groupPosition).get(childPosition);
            return createView(string);
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            String string = groupData.get(groupPosition);
            return createView(string);
        }

        @Override
        public int getChildrenCount(int i) {
            return childrenData.get(i).size();
        }

        @Override
        public Object getGroup(int i) {
            return groupData.get(i);
        }

        @Override
        public int getGroupCount() {
            return groupData.size();
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public long getGroupId(int i) {
            return i;
        }

        @Override
        public boolean isChildSelectable(int i, int i1) {
            return true;
        }

        private TextView createView(String content) {
            AbsListView.LayoutParams layoutParams = new AbsListView.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            TextView text = new TextView(activity);
            text.setLayoutParams(layoutParams);
            text.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
            text.setPadding(80, 0, 0, 0);
            text.setText(content);
            return text;
        }
    }
}
