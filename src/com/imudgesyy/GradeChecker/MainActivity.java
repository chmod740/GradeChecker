package com.imudgesyy.GradeChecker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import com.imudgesyy.java.jwxttool.JwxtTool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends Activity {
    private EditText et_username;
    private EditText et_password;
    private Button bt_ok;
    //private TextView tv_grades;
    private MyAdapter listAdapter;
    private ListView listView;
    private ArrayList<HashMap<String,Object>> listItem;
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        setContentView(R.layout.main);
        //初始化
        init();
        et_username = (EditText) findViewById(R.id.username);
        et_password = (EditText) findViewById(R.id.password);
        bt_ok = (Button) findViewById(R.id.button);
        StudentModel studentModel = DataUtil.getStudentInfo(MainActivity.this);
        et_username.setText(studentModel.getUsername());
        et_password.setText(studentModel.getPassword());
        //tv_grades = (TextView) findViewById(R.id.grades);

        bt_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //bt_ok.setEnabled(false);
                StudentModel studentModel = new StudentModel();
                studentModel.setUsername(et_username.getText().toString());
                studentModel.setPassword(et_password.getText().toString());
                DataUtil.saveStudentInfo(studentModel,MainActivity.this);

                listItem.clear();
                if (et_username.getText().toString().equals("") || et_password.getText().toString().equals(""))
                    Toast.makeText(MainActivity.this,"请输入个人信息" , Toast.LENGTH_SHORT).show();

                JwxtTool jwxtTool = new JwxtTool(et_username.getText().toString(),et_password.getText().toString());
                String text = "";
                try {
                    Map<String,String>grades = jwxtTool.getGrades();
                    for (String key: grades.keySet()){
//                        text += "课程：" + key + " 成绩：" + grades.get(key) + "\n";
                        HashMap<String,Object> map = new HashMap<String, Object>();
                        map.put("course_name",key);
                        map.put("course_value",grades.get(key));
                        listItem.add(map);
                    }
                    listAdapter.notifyDataSetChanged();
                    int number = 0;
                    int number2 = 0;
                    for (String key: grades.keySet()){

                        if(Double.parseDouble(grades.get(key))<60){
                            number++;

                        }
                        number2++;
                    }
                    Toast.makeText(getApplication(),"不及格科目数量："+number,Toast.LENGTH_SHORT).show();
                }catch (Exception e){
                    //Toast.makeText(getApplication(),"ERROR",Toast.LENGTH_SHORT).show();
                }
                //bt_ok.setEnabled(true);
            }

        });
    }

    private void init(){
        listView = (ListView)findViewById(R.id.listview);
        listItem = new ArrayList<HashMap<String, Object>>();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Toast.makeText(getApplication(),"OnClick"+i+" 个",Toast.LENGTH_SHORT).show();
            }
        });
        listAdapter = new MyAdapter(getApplication(),listItem,
                R.layout.list_item,
                new String[]{
                        "course_name",
                        "course_value"
                },
                new int[]{
                        R.id.course_name,
                        R.id.course_value
                });
        listView.setAdapter(listAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(Menu.NONE,Menu.FIRST+1,1,"关于");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case Menu.FIRST + 1:
                Intent intent = new Intent(MainActivity.this,AboutActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
