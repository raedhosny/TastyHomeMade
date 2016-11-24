package com.tastyhomemade.tastyhomemade;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.tastyhomemade.tastyhomemade.Adapter.MainMenuAdapter;
import com.tastyhomemade.tastyhomemade.Business.MainMenuItem;
import com.tastyhomemade.tastyhomemade.Fragment.ProfileFragment;
import com.tastyhomemade.tastyhomemade.Others.Settings;
import com.tastyhomemade.tastyhomemade.Others.Utils;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    DrawerLayout Drawer_Layout;
    Button btnMainForm;
    EditText txtSearch;
    Button btnSearch;
    ListView lvMainMenu;
    List<MainMenuItem> SideMenuItemsList;
    Button btnMainRegister;
    TextView lblMainUserName;
    Button btnMainLogin;
    Button btnMainLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Drawer_Layout = (DrawerLayout)this. findViewById(R.id.Drawer_Layout);
        btnMainForm = (Button) this. findViewById(R.id.btnMainForm);
        txtSearch  = (EditText) this. findViewById(R.id.txtSearch);
        btnSearch = (Button) this. findViewById(R.id.btnSearch);
        btnMainRegister = (Button) this. findViewById(R.id.btnMainRegister);
        btnMainForm.setOnClickListener(this);
        btnMainRegister.setOnClickListener(this);
        lvMainMenu = (ListView)this. findViewById(R.id.lvMainMenu);
        lblMainUserName = (TextView) this.findViewById(R.id.lblMainUserName);
        btnMainLogin = (Button)this.findViewById(R.id.btnMainLogin);
        btnMainLogin.setOnClickListener(this);
        btnMainLogout = (Button)this.findViewById(R.id.btnMainLogout);
        btnMainLogout.setOnClickListener(this);

        //  Slide Menu Initiation
        FillSideMenu();

        LoadMainInfo();

        Drawer_Layout.closeDrawer(lvMainMenu);
    }

    @Override
    public void onClick(View view) {
        if (view == btnMainForm)
        {
            if (Drawer_Layout.isDrawerOpen(lvMainMenu)  ) {
                Drawer_Layout.closeDrawer(lvMainMenu);

            }
            else {
                Drawer_Layout.openDrawer(lvMainMenu);
            }
        }
        else if (view  == btnMainRegister)
        {
            new Utils().ShowActivity(MainActivity.this,null,"Register");
        }
        else if (view  == btnMainLogin)
        {
            Settings ObjSettings = new Settings(this);
            new Utils().ShowActivity(MainActivity.this,null,"Login");
            btnMainLogin.setVisibility(View.GONE);
            btnMainRegister.setVisibility(View.GONE);
            btnMainLogout.setVisibility(View.VISIBLE);
            lblMainUserName.setText(ObjSettings.getUserName());
        }
        else if (view == btnMainLogout)
        {
            Settings ObjSettings = new Settings(this);
            ObjSettings.Clear();

            if (ObjSettings .getUserId() != -1)
            {
                btnMainLogin.setVisibility(View.GONE);
                btnMainRegister.setVisibility(View.GONE);

                lblMainUserName.setText(ObjSettings.getUserName());

            }

        }

    }

    private void FillSideMenu ()
    {
        // Fill Main Menu List
        List<String> ItemsListTemp= new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.MainMenuStrings)));
        SideMenuItemsList =  new ArrayList<MainMenuItem>();
        for (String ItemTemp : ItemsListTemp) {
            int iId = Integer.valueOf(ItemTemp.split(",")[0]);
            String sName = ItemTemp.split(",")[1];
            MainMenuItem ItemList = new MainMenuItem(iId,sName);
            SideMenuItemsList.add(ItemList );
        }

        View Header = getLayoutInflater().inflate(R.layout.main_menu_header,null);
        lvMainMenu.addHeaderView(Header);
        lvMainMenu.setAdapter(new MainMenuAdapter(this,SideMenuItemsList));


    }

    private void LoadMainInfo ()
    {
        Settings ObjSettings = new Settings(this);
        if (ObjSettings .getUserId() != -1)
        {
            btnMainLogin.setVisibility(View.VISIBLE);
            btnMainRegister.setVisibility(View.VISIBLE);
            lblMainUserName.setText(ObjSettings.getUserName());
            btnMainLogout.setVisibility(View.GONE);
        }
    }

}
