package it.mainella.phone_state.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.TelephonyManager
import it.mainella.phone_state.utils.PhoneStateStatus

open class PhoneStateReceiver : BroadcastReceiver() {
    var status: PhoneStateStatus = PhoneStateStatus.NOTHING

    fun instance(context: Context) {
        val telephonyManager = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager

        status = when (telephonyManager.callState) {
            TelephonyManager.CALL_STATE_RINGING -> PhoneStateStatus.CALL_INCOMING
            TelephonyManager.CALL_STATE_OFFHOOK -> PhoneStateStatus.CALL_STARTED
            TelephonyManager.CALL_STATE_IDLE -> PhoneStateStatus.CALL_ENDED
            else -> PhoneStateStatus.NOTHING
        }
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        try {
            status = when (intent?.getStringExtra(TelephonyManager.EXTRA_STATE)) {
                TelephonyManager.EXTRA_STATE_RINGING -> PhoneStateStatus.CALL_INCOMING
                TelephonyManager.EXTRA_STATE_OFFHOOK -> PhoneStateStatus.CALL_STARTED
                TelephonyManager.EXTRA_STATE_IDLE -> PhoneStateStatus.CALL_ENDED
                else -> PhoneStateStatus.NOTHING
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
