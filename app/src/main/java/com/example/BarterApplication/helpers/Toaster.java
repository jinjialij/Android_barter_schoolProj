package com.example.BarterApplication.helpers;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.view.Gravity;
import android.widget.Toast;

import com.example.BarterApplication.MainActivity;
import com.example.BarterApplication.RegisterActivity;

public class Toaster {

    public static void generateToast(Context context, String msg){
        Toast toast = Toast.makeText(context, msg,
                Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL, 0, 0);
        toast.show();
    }

    // TODO: This doesn't work yet
    public static void generateToastAndRedirect(final Context originalContext, final Context desiredContext, String msg){
        generateToast(originalContext, msg);
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(originalContext, desiredContext.getClass());
                originalContext.startActivity(intent);
            }
        }, 3000);
    }


}
