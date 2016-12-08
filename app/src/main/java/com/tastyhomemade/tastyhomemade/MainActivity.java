package com.tastyhomemade.tastyhomemade;

import android.content.res.Configuration;
import android.content.res.Resources;
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
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    DrawerLayout Drawer_Layout;
    Button btnMainForm;
    EditText txtSearch;
    Button btnSearch;
    ListView lvMainMenu;
    List<MainMenuItem> FilteredSideMenuItemsList;
    List<MainMenuItem> AllSideMenuItemsList;
    Button btnMainRegister;
    TextView lblMainUserName;
    Button btnMainLogin;
    Button btnMainLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new Utils().SetCurrentLanguage(this,new Settings(this).getCurrentLanguageId());
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

        LoadMainInfo();

        Drawer_Layout.closeDrawer(lvMainMenu);


    }

    @Override
    protected void onStart() {
        super.onStart();
       // LoadMainInfo();
    }

    @Override
    protected void onResume() {
        super.onResume();
       // LoadMainInfo();
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

            new Utils().ShowActivity(MainActivity.this,null,"Login");
            //LoadMainInfo ();
            // Go to main activity

        }
        else if (view == btnMainLogout)
        {
            Settings ObjSettings = new Settings(this);
            ObjSettings.Clear();

            LoadMainInfo ();



        }

    }

    private void FillSideMenu ()
    {
//        // Fill Main Menu List
        List<String> ItemsListTemp= new ArrayList<String>(Arrays.asList(Utils.GetResourceArrayName(this, R.array.MainMenuStrings,new Settings(this).getCurrentLanguageId())));
        List<String> ItemsListFiltered = new ArrayList<String>();

        FilteredSideMenuItemsList =  new ArrayList<MainMenuItem>();
        AllSideMenuItemsList =  new ArrayList<MainMenuItem>();

        for (String ItemTemp : ItemsListTemp)
        {
            int iId = Integer.valueOf(ItemTemp.split(",")[0]);
            String sName = ItemTemp.split(",")[1];
            String[] UserTypesList = ItemTemp.split(",")[2].split(";");

            AllSideMenuItemsList.add(new MainMenuItem(iId,sName));

            for (String sUserType : UserTypesList)
            {
                if (sUserType.equals( new Settings(this).getUserType()))
                {
                    ItemsListFiltered.add(iId + "," + sName);
                }
            }
        }


        for (String ItemTemp : ItemsListFiltered) {
            int iId = Integer.valueOf(ItemTemp.split(",")[0]);
            String sName = ItemTemp.split(",")[1];
            MainMenuItem ItemList = new MainMenuItem(iId,sName);
            FilteredSideMenuItemsList.add(ItemList );
        }

        View Header = getLayoutInflater().inflate(R.layout.main_menu_header,null);
        if (lvMainMenu.getHeaderViewsCount() == 0 )
            lvMainMenu.addHeaderView(Header);
        lvMainMenu.setAdapter(new MainMenuAdapter(this,FilteredSideMenuItemsList,AllSideMenuItemsList));
        Drawer_Layout.closeDrawer(lvMainMenu);

    }

    public void LoadMainInfo ()
    {

        FillSideMenu();
        Settings ObjSettings = new Settings(this);
        if (ObjSettings .getUserId() != -1)
        {
            btnMainLogin.setVisibility(View.GONE);
            btnMainRegister.setVisibility(View.GONE);
            btnMainLogout.setVisibility(View.VISIBLE);
            lblMainUserName.setText(ObjSettings.getUserName());
        }
        else
        {
            btnMainLogin.setVisibility(View.VISIBLE);
            btnMainRegister.setVisibility(View.VISIBLE);
            btnMainLogout.setVisibility(View.GONE);

            lblMainUserName.setText(new Utils().GetResourceName(this,R.string.Visitor,ObjSettings.getCurrentLanguageId()));

        }

      new Utils().ShowActivity(this,null,"Main","-1");
    }


}
