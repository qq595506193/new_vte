package utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;

/**
 * Description:显示指定组件的对话框,并跳转至指定的Activity
 * @author  maoyun0903@163.com
 * @version  1.0
 */
public class DialogUtil
{
    // 定义一个显示消息的对话框
    public static void showDialog(final Context ctx
            , String msg , boolean goHome)
    {
        // 创建一个AlertDialog.Builder对象
        AlertDialog.Builder builder = new AlertDialog.Builder(ctx)
                .setMessage(msg).setCancelable(false);
        if(goHome)
        {
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {

                }
            });
        }
        else
        {
            builder.setPositiveButton("确定", null);
        }
        builder.create().show();
    }
    // 定义一个显示指定组件的对话框
    public static void showDialog(Context ctx , View view)
    {
        new AlertDialog.Builder(ctx)
                .setView(view).setCancelable(false)
                .setPositiveButton("确定", null)
                .create()
                .show();
    }
}