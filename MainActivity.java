package com.example.installer;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Bundle;
import android.provider.Settings;
import android.wideget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;


public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_INSTALL_UNKNOWN_APPS = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Check if the app has permission to install unknown apps
        if (Settings.canDrawOverlays(this)) {
            installApk();
        } else {
            // Request permission to install unknown apps
            Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES)
                    .setData(Uri.parse(String.format("package:%s", getPackageName())));
            startActivityForResult(intent, REQUEST_CODE_INSTALL_UNKNOWN_APPS);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_INSTALL_UNKNOWN_APPS) {
            if (Settings.canDrawOverlays(this)) {
                installApk();
            } else {
                Toast.makeText(this, "Permission denied to install unknown apps", Toast.LENGTH_SHORT).show();
            }
        }
        return;
    }
    if (requestCode == REQUEST_CODE_INSTALL_UNKNOWN_APPS) {
            Uri selectedApkUri = data.getData();
            if (selectedApkUri != null) {
                installApkFromUri(selectedApkUri);
            } else {
                Toast.makeText(this, "No APK selected", Toast.LENGTH_SHORT).show();
            }
        }
    private void installApkFromUri(Uri apkUri) {
        File tempApk= new File(getCacheDir(), "temp.apk");
        if (tempApk.exists()) tempApk.delete();
            tempApk.delete();
        }
        try {
            InputStream inputStream = getContentResolver().openInputStream(apkUri);
            OutputStream outputStream = new FileOutputStream(tempApk);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }
            outputStream.close();
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        installApkFromFile(tempApk);
    }catch (Exception e) {
            Toast.makeText(this, "Failed to copy APK: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    public static void installApkFromFile(Context context, File apkFile) {
        if(!apkFile.exists()) {
            Toast.makeText(context, "APK file does not exist", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        Uri apkUri;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            apkUri = FileProvider.getUriForFile(context, context.getPackageName() + ".provider", apkFile);
        } else {
            apkUri = Uri.fromFile(apkFile);
        }
        intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
        context.startActivity(intent);
    }
    private void installApk() {
        // Replace with the actual path to your APK file
        File apkFile = new File(getExternalFilesDir(null), "your_app.apk");
        if (apkFile.exists()) {
            installApkFromFile(this, apkFile);
        } else {
            Toast.makeText(this, "APK file not found", Toast.LENGTH_SHORT).show();
        }
    }
}