package com.loopslab.wordbywordquran.various;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.loopslab.wordbywordquran.R;

/**
 * Created by Sadmansamee on 7/26/15.
 */
public class AboutActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView tv_see_apps = (TextView) findViewById(R.id.tv_see_apps);
        ImageButton imageButton_loops_lab = (ImageButton) findViewById(R.id.iv_loopslab_logo);
        TextView mail_loopslab = (TextView) findViewById(R.id.mail_loopslab);
        Button rateUsButton = (Button) findViewById(R.id.rateUsButton);
        Button shareCopyButton = (Button) findViewById(R.id.shareCopyButton);


        rateUsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName()));
                startActivity(intent);
            }
        });

        shareCopyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AboutActivity.this);
                builder.setTitle(getResources().getString(R.string.shareCopy));
                builder.setItems(getResources().getStringArray(R.array.aya_operations), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if (which == 0) { //copy
                            ClipboardManager clipboard = (android.content.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                            ClipData clip = ClipData.newPlainText("Intent", getResources().getString(R.string.loopslab));
                            clipboard.setPrimaryClip(clip);
                            Toast.makeText(AboutActivity.this, getResources().getString(R.string.copyToastText), Toast.LENGTH_SHORT).show();

                        } else if (which == 1) {
                            Intent shareIntent = new Intent();
                            shareIntent.setAction(Intent.ACTION_SEND);
                            shareIntent.putExtra(Intent.EXTRA_STREAM, getResources().getString(R.string.loopslab));
                            shareIntent.setType("text/plain");
                            startActivity(shareIntent);
                        }
                    }
                });
                builder.show();
            }
        });

        tv_see_apps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse(getString(R.string.loopslab)));

                startActivity(intent);
            }
        });

        imageButton_loops_lab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse(getString(R.string.loopslabFb)));

                startActivity(intent);
            }
        });

        mail_loopslab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto", getString(R.string.mail_loopslab), null));
                startActivity(Intent.createChooser(intent, "Choose an Email client :"));
            }
        });
    }
}
