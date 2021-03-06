package com.dcrandroid.service

import android.app.*
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.drawable.Icon
import android.os.Build
import android.os.IBinder
import android.support.v4.app.NotificationCompat
import com.dcrandroid.R
import com.dcrandroid.util.Utils
import dcrwallet.Dcrwallet

class DcrdService : Service() {

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        super.onTaskRemoved(rootIntent)
    }

    var serverStatus = "Chain server is loading"
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val channel = NotificationChannel("chain server", "Dcrandroid Chain Server", NotificationManager.IMPORTANCE_DEFAULT)
            channel.enableLights(false)
            channel.setSound(null, null)
            channel.enableVibration(false)
            channel.lockscreenVisibility = Notification.VISIBILITY_PUBLIC
            channel.importance = NotificationManager.IMPORTANCE_LOW
            manager.createNotificationChannel(channel)
        }
        showNotification()

        val filter = IntentFilter("SHUTDOWN")
        registerReceiver(receiver,filter)
        return super.onStartCommand(intent, flags, startId)
    }

    fun showNotification(){
        val notification : Notification
        val intent = Intent("SHUTDOWN")
        val pi = PendingIntent.getBroadcast(this, 2, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val icon = Icon.createWithResource(this, R.drawable.ic_menu_share)
            val action = Notification.Action.Builder(icon,"SHUTDOWN", pi).build()
            notification = Notification.Builder(this, "chain server")
                    .setContentTitle("Decred Chain Server")
                    .setSubText("Chain Server")
                    .setContentText(serverStatus)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setOngoing(true)
                    .setAutoCancel(true)
                    .setSound(null)
                    .addAction(action)
                    .build()
        }else{
            val action = NotificationCompat.Action.Builder(R.drawable.ic_menu_share,"SHUTDOWN", pi).build()
            notification = NotificationCompat.Builder(this)
                    .setContentTitle("Decred Chain Server")
                    .setSubText("Chain Server")
                    .setContentText(serverStatus)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setOngoing(true)
                    .setAutoCancel(true)
                    .addAction(action)
                    .setSound(null)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .build()
        }
        startForeground(1, notification)
    }

    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            println("BROADCAST RECEIVED")
            stopForeground(true)
            stopSelf()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(receiver)
        val i = Intent("kill")
        sendBroadcast(i)
        Dcrwallet.shutdown()
    }

    override fun onCreate() {
        super.onCreate()
        Dcrwallet.runDcrd()
        object : Thread() {
            override fun run() {
                while (true) {
                    try {
                        val result = Dcrwallet.runDcrCommands(getString(R.string.getbestblock))
                        val bestBlock = Utils.parseBestBlock(result)

                        //println("BestBock: ${bestBlock.height} Hash: ${bestBlock.hash}")
                        var percentageSynced = Math.round((bestBlock.height/Utils.estimatedBlocks()) * 100)
                        percentageSynced = if (percentageSynced > 100) 100 else percentageSynced
                        serverStatus = "Block Height: ${bestBlock.height}, % Synced: $percentageSynced%"
                        showNotification()
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                    Thread.sleep(3000)
                }
            }
        }.start()
    }
}