package com.hui.huiheight;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.hui.huiheight.first.photo.CutPhonePictureActivity;

import walke.base.activity.BaseActivity;

public class MainsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mains);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //toolbar.setBackgroundColor(Color.TRANSPARENT);
        toolbar.setBackgroundResource(R.color.transparent);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.at_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                startActivity(new Intent(MainsActivity.this, CutPhonePictureActivity.class));
            }
        });
        TextView textView = (TextView) findViewById(R.id.mains_text);
        registerForContextMenu(textView);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        menu.setHeaderTitle("button的上下文菜单");
        menu.setHeaderIcon(R.mipmap.ic_launcher_round);
//        // 代码添加的菜单
//        menu.add(Menu.NONE, 11, Menu.NONE, "代码添加的菜单项");
//        // 添加二级菜单
//        SubMenu subMenu = menu.addSubMenu("代码添加的二级菜单");
//        subMenu.add(Menu.NONE, 12, Menu.NONE, "代码添加的二级菜单项");
    }
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.menu_settings:
                Toast.makeText(this, "123", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
        return super.onContextItemSelected(item);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.menu_settings) {
            toast(item.getTitle()+"");
            return true;
        }
        switch (item.getItemId()){
            case R.id.menu_test1:
                startActivity(new Intent(this, LaunchActivity.class));
                break;
            case R.id.menu_test2:
                startActivity(new Intent(this, HomeActivity.class));
                break;
            case R.id.menu_test3:
                startActivity(new Intent(this, com.jcodecraeer.xrecyclerview.example.MainActivity.class));
                break;
            case R.id.menu_test4:

                break;
            case R.id.menu_test5:

                break;
            case R.id.menu_test6:

                break;
        }
        toast(item.getTitle().toString());

        return super.onOptionsItemSelected(item);
    }
}
