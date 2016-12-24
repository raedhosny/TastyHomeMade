package com.tastyhomemade.tastyhomemade.Others;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.tastyhomemade.tastyhomemade.R;

/**
 * Created by Raed on 12/15/2016.
 */

public class WaitDialog {

    Context context;
    ProgressDialog MyDialog;
    Settings ObjSettings;

    public WaitDialog(Context p_Context) {
        context = p_Context;
        ObjSettings = new Settings(p_Context);
        MyDialog = new ProgressDialog(p_Context);
        MyDialog.setMessage(Utils.GetResourceName(p_Context, R.string.PleaseWait, ObjSettings.getCurrentLanguageId()));
        MyDialog.setCancelable(false);
        //MyDialog.setIndeterminate(true);

    }

    public void ShowDialog() {
        MyDialog.show();
    }

    public void HideDialog() {
        MyDialog.hide();
    }


}
