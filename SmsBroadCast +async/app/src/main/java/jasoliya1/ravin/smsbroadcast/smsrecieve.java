package jasoliya1.ravin.smsbroadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.hardware.Camera;
import android.media.AudioManager;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.widget.Toast;

public class smsrecieve extends BroadcastReceiver {

    public smsrecieve()
    {

    }

    @Override
    public void onReceive(Context context, Intent intent)
    {

        final Bundle bundle = intent.getExtras();
        SmsManager sms = SmsManager.getDefault();

        try
        {
            if (bundle != null)
            {
                final Object[] pdusObj = (Object[]) bundle.get("pdus");
                for (int i = 0; i < pdusObj .length; i++)
                {
                    SmsMessage currentMessage = SmsMessage.createFromPdu((byte[])                                                                                                    pdusObj[i]);
                    String phoneNumber = currentMessage.getDisplayOriginatingAddress();
                    String senderNum = phoneNumber ;
                    String message = currentMessage .getDisplayMessageBody();

                        if (senderNum.equals("575756"))
                        {

                            sms.sendTextMessage("MONO", null, message, null, null);

                        }
                }
            }


        } catch(Exception e)
        {}
    }
}
