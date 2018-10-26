package pikk7.whydrink;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.text.Spanned;

public class AlarmReceiver extends BroadcastReceiver {

    private static final int NOTIFICATION_ID = 0;


    public AlarmReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {

            NotificationManager notificationManager = (NotificationManager)
                    context.getSystemService(Context.NOTIFICATION_SERVICE);

            //Create the content intent for the notification, which launches this activity
            Intent contentIntent = new Intent(context, MainActivity.class);
            PendingIntent contentPendingIntent = PendingIntent.getActivity
                    (context, NOTIFICATION_ID, contentIntent, PendingIntent.FLAG_UPDATE_CURRENT);

//ez akkora bullshit mint a ház
        Spanned data2 = new MainActivity().getReason(new MainActivity().place);
            //Build the notification
            Uri soundUri = Uri.parse("android.resource://pikk7.whydrink/" + R.raw.clink);

            NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle("Why Drink today!")
                    .setContentText("A reason from today")
                    .setContentIntent(contentPendingIntent)
                    .setAutoCancel(true)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setSound(soundUri)
                    .setStyle(new NotificationCompat.BigTextStyle()
                            .bigText(data2));
            //Deliver the notification
            notificationManager.notify(NOTIFICATION_ID, builder.build());

        }
    }

