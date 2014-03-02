package ru.droogcompanii.application.ui.helpers;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.telephony.PhoneNumberUtils;

/**
 * Created by ls on 31.01.14.
 */
public class Caller {
    
    public static void call(Context context, String phone) {
        String formattedPhone = PhoneNumberUtils.formatNumber(phone);
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + formattedPhone));
        context.startActivity(intent);
    }
}
