package go.pickapp.service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import go.pickapp.Activity.MainActivity;
import go.pickapp.Activity.Order_detailsActivity;
import go.pickapp.Controller.Config;
import go.pickapp.R;

//Created by Dharvik on 6/25/2016.

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";
    public static String Noty_type;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        remoteMessage.getCollapseKey();
        sendNotification(remoteMessage.getData());
    }

    //{status=1, imageUrl=, type=Order, bedge=3, title=FOS, message=Order 00000010 is preparing.}

    private void sendNotification(Map<String, String> messageBody) {
        Log.i("Message Body", "" + messageBody);
        String message = "", imageUrl = "", orderid = "";
        String type, statusStr = "";
        try {

            statusStr = messageBody.get("status");

            switch (statusStr) {
                case "1":
                    message = messageBody.get("message");
                    orderid = messageBody.get("orderid");
                    Log.e("Normal--->", message);
                    break;


                case "2":

                    message = messageBody.get("message");
                    imageUrl = messageBody.get("imageUrl");
                    Log.e("Image ----> ", imageUrl);
                    break;
            }

        } catch (Exception e) {
            e.printStackTrace();
            message = "";
        }
        Log.e("hellloo", "msg aayoo ");
        Log.i("Message Body", "" + messageBody);


        Intent noti_Intent = null;
        switch (statusStr) {
            case "1":
                noti_Intent = new Intent(this, Order_detailsActivity.class);
                noti_Intent.putExtra("Order_id", orderid);
                noti_Intent.putExtra("val", "1");
                noti_Intent.putExtra("back", "2");
                // noti_Intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                // startActivity(noti_Intent);  Open Screen automatically
                break;
            case "2":
                noti_Intent = new Intent(this, MainActivity.class);
                noti_Intent.putExtra("fragmentcode", Config.Fragment_ID.home);
                noti_Intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                // startActivity(noti_Intent);
                break;

        }

        noti_Intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent intent = PendingIntent.getActivity(this, 0, noti_Intent, PendingIntent.FLAG_ONE_SHOT);

        if (statusStr.equals("1")) {
            noti_one(message, intent);
        } else {
            notification(message, intent, imageUrl);
        }

    }

    public void noti_one(String message, PendingIntent intent) {
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                .setContentTitle(getResources().getString(R.string.GoPickApp))
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(intent)
                .setWhen(System.currentTimeMillis());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            int color = getResources().getColor(R.color.Red);
            builder.setSmallIcon(R.drawable.favicon);
            builder.setColor(color);
        } else {
            builder.setSmallIcon(R.drawable.favicon);
        }
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, builder.build());
    }


    public void notification(String message, PendingIntent intent, String noti_url) {
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder nb = new NotificationCompat.Builder(this);
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            int color = getResources().getColor(R.color.Red);
            nb.setSmallIcon(R.drawable.favicon);
            nb.setColor(color);
        } else {
            nb.setSmallIcon(R.drawable.favicon);
        }
        nb.setContentTitle(getResources().getString(R.string.GoPickApp));
        nb.setContentText(message);
        nb.setContentIntent(intent);
        nb.setSound(defaultSoundUri);
        nb.setAutoCancel(true);
        Bitmap bitmap_image = getBitmapFromURL(noti_url);

        bitmap_image = Bitmap.createScaledBitmap(bitmap_image, 500, 500, false);
        NotificationCompat.BigPictureStyle s = new NotificationCompat.BigPictureStyle().bigPicture(bitmap_image);
        s.setSummaryText(message);
        nb.setStyle(s);
        NotificationManager mNotificationManager =
                (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        // mId allows you to update the notification later on.
        mNotificationManager.notify(11221, nb.build());
    }

    public Bitmap getBitmapFromURL(String strURL) {
        try {
            URL url = new URL(strURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
