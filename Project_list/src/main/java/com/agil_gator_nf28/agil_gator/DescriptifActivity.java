package com.agil_gator_nf28.agil_gator;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class DescriptifActivity extends ActionBarActivity {

    private TextView title_project, text_description, text_priority, text_advanced;
    private RelativeLayout layout = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //UTILISER burndown chart pour stats !!!
        super.onCreate(savedInstanceState);

        /** permet de deserializer xml -> java **/
        layout = (RelativeLayout) RelativeLayout.inflate(this, R.layout.activity_descritpif, null);

        title_project = (TextView)layout.findViewById(R.id.title_project);
        text_description = (TextView)layout.findViewById(R.id.descriptionContent);
        text_priority = (TextView)layout.findViewById(R.id.priorityContent);
        text_advanced = (TextView)layout.findViewById(R.id.avancedContent);

        setContentView(layout);

    }



}
