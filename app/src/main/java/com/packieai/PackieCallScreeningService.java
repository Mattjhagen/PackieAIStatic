package com.packieai;

import android.telecom.Call;
import android.telecom.CallScreeningService;
import android.telecom.Connection;
import android.content.Intent;
import android.net.Uri;
import android.telephony.TelephonyManager;

public class PackieCallScreeningService extends CallScreeningService {

    @Override
    public void onScreenCall(Call.Details callDetails) {
        CallResponse.Builder responseBuilder = new CallResponse.Builder();

        String phoneNumber = callDetails.getHandle().getSchemeSpecificPart();

        if (isLikelyScam(phoneNumber)) {
            // Reject the call
            responseBuilder.setRejectCall(true);
            responseBuilder.setSkipCallLog(false);
            responseBuilder.setSkipNotification(true);

            // Immediately call the transfer number
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:9472254324"));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } else {
            // Allow the call
            responseBuilder.setDisallowCall(false);
            responseBuilder.setRejectCall(false);
        }

        respondToCall(callDetails, responseBuilder.build());
    }

    private boolean isLikelyScam(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.isEmpty()) {
            return true; // Unknown number
        }

        // Remove any non-digit characters
        String digits = phoneNumber.replaceAll("\\D", "");

        // Check for unknown/private numbers
        if (digits.length() < 10) {
            return true;
        }

        // Check for area codes known for scams (example: 800, 888, etc.)
        String areaCode = digits.substring(0, 3);
        String[] scamAreaCodes = {"800", "888", "877", "866", "855", "844", "833", "822", "900", "976"};

        for (String scamCode : scamAreaCodes) {
            if (areaCode.equals(scamCode)) {
                return true;
            }
        }

        // Additional checks can be added here, e.g., specific numbers, patterns

        return false;
    }
}