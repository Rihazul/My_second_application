package com.example.studentprofile.Views;

import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.studentprofile.Database;
import com.example.studentprofile.Models.DialogFrame;
import com.example.studentprofile.R;
import com.example.studentprofile.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements DialogFrame.ExampleDialogListner {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;
    private Database db;
    public String Surname;
    public String Name;
    public String Student_id;
    public String GPA;

    public FragmentRefreshListener getFragmentRefreshListener() {
        return fragmentRefreshListener;
    }

    public void setFragmentRefreshListener(FragmentRefreshListener fragmentRefreshListener) {
        this.fragmentRefreshListener = fragmentRefreshListener;
    }

    private FragmentRefreshListener fragmentRefreshListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
     setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        NavController navController = Navigation.findNavController(this, R.id.main_container);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              openDialog();
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            db =new Database(this);
        }


    }
    public void hidebutton(){
        binding.fab.setVisibility(View.INVISIBLE);
    }
    public void showbutton(){
        binding.fab.setVisibility(View.VISIBLE);
    }
    public void openDialog(){
        DialogFrame dialogFrame = new DialogFrame();
        dialogFrame.show(getSupportFragmentManager(),"dialog frame");
    }
    @Override
    public void addtodb(){
        if(Surname != "" && Name !="" && Student_id != "" && GPA != ""){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                if(db.addtoprofile(Surname,Name,Student_id,GPA)){
                    Toast.makeText(this,"Success",Toast.LENGTH_LONG).show();
                    if(getFragmentRefreshListener()!=null){
                        getFragmentRefreshListener().onRefresh();
                    }

                }else{
                    Toast.makeText(this,"Failure",Toast.LENGTH_LONG).show();
                }
            }
        }else{
            Toast.makeText(this,"Enter Values",Toast.LENGTH_LONG).show();

        }
    }



    public interface FragmentRefreshListener{
        void onRefresh();
    }


    @Override
    public void applytext(String Surname, String Name, String Studentid, String GPA) {
            this.Surname= Surname;
            this.Name=Name;
            this.Student_id=Studentid;
            this.GPA=GPA;
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.main_container);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}