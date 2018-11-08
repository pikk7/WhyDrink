package pikk7.whydrink;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import static pikk7.whydrink.MainActivity.place;

public class AlarmReceiver extends BroadcastReceiver {




    public AlarmReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {

      NotificationManager notificationManager = (NotificationManager)
                    context.getSystemService(Context.NOTIFICATION_SERVICE);

            //Create the content intent for the notification, which launches this activity
            Intent contentIntent = new Intent(context, MainActivity.class);
            PendingIntent contentPendingIntent = PendingIntent.getActivity
                    (context, 3, contentIntent, PendingIntent.FLAG_UPDATE_CURRENT);


        String data2 = MainActivity.getInstance().getReason(place).toString();
            //Build the notification
            Uri soundUri = Uri.parse("android.resource://pikk7.whydrink/" + R.raw.clink);

            Notification.Builder builder = new Notification.Builder(context)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle("Why Drink today!")
                    .setContentText("A reason from today")
                    .setContentIntent(contentPendingIntent)
                    .setAutoCancel(true)
                    .setPriority(Notification.PRIORITY_HIGH)
                    .setSound(soundUri)
                    .setStyle(new Notification.BigTextStyle()
                            .bigText(data2));
            //Deliver the notification
            notificationManager.notify(3, builder.build());

        }
    }

