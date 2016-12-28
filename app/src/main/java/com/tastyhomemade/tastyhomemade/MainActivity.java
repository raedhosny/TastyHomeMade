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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.tastyhomemade.tastyhomemade.Adapter.CategoriesAdapter;
import com.tastyhomemade.tastyhomemade.Adapter.MainMenuAdapter;
import com.tastyhomemade.tastyhomemade.Business.Categories;
import com.tastyhomemade.tastyhomemade.Business.CategoriesDB;
import com.tastyhomemade.tastyhomemade.Business.MainMenuItem;
import com.tastyhomemade.tastyhomemade.Fragment.ProfileFragment;
import com.tastyhomemade.tastyhomemade.Others.Settings;
import com.tastyhomemade.tastyhomemade.Others.Utils;
import com.tastyhomemade.tastyhomemade.Others.ViewMode;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    DrawerLayout Drawer_Layout;
    Button btnMainForm;
    EditText txtSearch;
    Button btnSearch;
    ListView lvMainMenu, lvCategories;
    List<MainMenuItem> FilteredSideMenuItemsList;
    List<MainMenuItem> AllSideMenuItemsList;
    Button btnMainRegister;
    TextView lblMainUserName;
    Button btnMainLogin;
    Button btnMainLogout;
    LinearLayout Linear_SideMenu;
    Settings ObjSettings;
    List<Categories> ObjCategoriesList;
    TextView lblHeader;
    TextView lblDate;
    View CategoriesHeader;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new Utils().SetCurrentLanguage(this, new Settings(this).getCurrentLanguageId());
        setContentView(R.layout.activity_main);

        Drawer_Layout = (DrawerLayout) this.findViewById(R.id.Drawer_Layout);
        btnMainForm = (Button) this.findViewById(R.id.btnMainForm);
        txtSearch = (EditText) this.findViewById(R.id.txtSearch);
        btnSearch = (Button) this.findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(this);
        btnMainRegister = (Button) this.findViewById(R.id.btnMainRegister);
        btnMainForm.setOnClickListener(this);
        btnMainRegister.setOnClickListener(this);
        lvMainMenu = (ListView) this.findViewById(R.id.lvMainMenu);
        lvCategories = (ListView) this.findViewById(R.id.lvCategories);
        lblMainUserName = (TextView) this.findViewById(R.id.lblMainUserName);
        btnMainLogin = (Button) this.findViewById(R.id.btnMainLogin);
        btnMainLogin.setOnClickListener(this);
        btnMainLogout = (Button) this.findViewById(R.id.btnMainLogout);
        btnMainLogout.setOnClickListener(this);
        Linear_SideMenu = (LinearLayout) findViewById(R.id.Linear_SideMenu);
        lblHeader = (TextView) findViewById(R.id.lblHeader);
        lblDate = (TextView) findViewById (R.id.lblDate);
        ObjSettings = new Settings(this);
        CategoriesHeader = getLayoutInflater().inflate(R.layout.categories_menu_header, null);

        LoadMainInfo();

        Drawer_Layout.closeDrawer(Linear_SideMenu);


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
        if (view == btnMainForm) {
            if (Drawer_Layout.isDrawerOpen(Linear_SideMenu)) {
                Drawer_Layout.closeDrawer(Linear_SideMenu);

            } else {
                Drawer_Layout.openDrawer(Linear_SideMenu);
            }
        } else if (view == btnMainRegister) {
            new Utils().ShowActivity(MainActivity.this, null, "Register");
        } else if (view == btnMainLogin) {

            new Utils().ShowActivity(MainActivity.this, null, "Login");
            //LoadMainInfo ();
            // Go to main activity

        } else if (view == btnMainLogout) {
            Settings ObjSettings = new Settings(this);
            ObjSettings.Clear();

            LoadMainInfo();


        }
        else if (view == btnSearch) {

            if (ObjSettings.getUserType().equals(Settings.enumUserType.FoodMaker.toString()))
                new Utils().ShowActivity(this, null, "MainForFoodMaker",ViewMode.SearchMode.name(),txtSearch.getText().toString().trim());
            else
                new Utils().ShowActivity(this, null, "Main", ViewMode.SearchMode.name(), "-1",txtSearch.getText().toString().trim());


        }


    }

    private void FillSideMenu() {
//        // Fill Main Menu List
        List<String> ItemsListTemp = new ArrayList<String>(Arrays.asList(Utils.GetResourceArrayName(this, R.array.MainMenuStrings, new Settings(this).getCurrentLanguageId())));
        List<String> ItemsListFiltered = new ArrayList<String>();

        FilteredSideMenuItemsList = new ArrayList<MainMenuItem>();
        AllSideMenuItemsList = new ArrayList<MainMenuItem>();

        for (String ItemTemp : ItemsListTemp) {
            int iId = Integer.valueOf(ItemTemp.split(",")[0]);
            String sName = ItemTemp.split(",")[1];
            String[] UserTypesList = ItemTemp.split(",")[2].split(";");

            AllSideMenuItemsList.add(new MainMenuItem(iId, sName));

            for (String sUserType : UserTypesList) {
                if (sUserType.equals(new Settings(this).getUserType()) || sUserType.equals("All")) {
                    ItemsListFiltered.add(iId + "," + sName);
                }
            }
        }


        for (String ItemTemp : ItemsListFiltered) {
            int iId = Integer.valueOf(ItemTemp.split(",")[0]);
            String sName = ItemTemp.split(",")[1];
            MainMenuItem ItemList = new MainMenuItem(iId, sName);
            FilteredSideMenuItemsList.add(ItemList);
        }

        View Header = getLayoutInflater().inflate(R.layout.main_menu_header, null);
        if (lvMainMenu.getHeaderViewsCount() == 0)
            lvMainMenu.addHeaderView(Header);
        lvMainMenu.setAdapter(new MainMenuAdapter(this, FilteredSideMenuItemsList, AllSideMenuItemsList));

        if (!ObjSettings.getUserType().equals(String.valueOf(Settings.enumUserType.FoodMaker) )) {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    ObjCategoriesList = new CategoriesDB().SelectAll(ObjSettings.getCurrentLanguageId());
                    final CategoriesAdapter ObjCategoriesAdapter = new CategoriesAdapter(MainActivity.this, ObjCategoriesList);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (lvCategories.getHeaderViewsCount() == 0) {

                                lvCategories.addHeaderView(CategoriesHeader);
                            }

                            lvCategories.setAdapter(ObjCategoriesAdapter);
                        }
                    });


                }
            });
            t.start();
        }
        else {

            lvCategories.setAdapter(null);
            lvCategories.removeHeaderView(CategoriesHeader);

        }


        Drawer_Layout.closeDrawer(Linear_SideMenu);

    }

    public void LoadMainInfo() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy");
        Calendar ObjCalendar = null;
        if (ObjSettings.getCurrentLanguageId() == 1) // Arabic
            ObjCalendar = Calendar.getInstance(new Locale("ar"));
        else
            ObjCalendar = Calendar.getInstance(new Locale("en"));
        lblDate.setText(sdf.format(ObjCalendar.getTime()));
        FillSideMenu();
        Settings ObjSettings = new Settings(this);
        if (ObjSettings.getUserId() != -1) {
            btnMainLogin.setVisibility(View.GONE);
            btnMainRegister.setVisibility(View.GONE);
            btnMainLogout.setVisibility(View.VISIBLE);
            lblMainUserName.setText(ObjSettings.getUserName());
        } else {
            btnMainLogin.setVisibility(View.VISIBLE);
            btnMainRegister.setVisibility(View.VISIBLE);
            btnMainLogout.setVisibility(View.GONE);

            lblMainUserName.setText(new Utils().GetResourceName(this, R.string.Visitor, ObjSettings.getCurrentLanguageId()));

        }

        if (ObjSettings.getUserType().equals(Settings.enumUserType.FoodMaker.toString()))
            new Utils().ShowActivity(this, null, "MainForFoodMaker",ViewMode.NormalMode.name());
        else
            new Utils().ShowActivity(this, null, "Main", ViewMode.NormalMode.name(),"-1");


    }


}
