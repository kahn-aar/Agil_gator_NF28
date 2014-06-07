package com.agil_gator_nf28.agil_gator;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.agil_gator_nf28.BddInterne.ProjetBDD;
import com.agil_gator_nf28.BddInterne.SousTacheBDD;
import com.agil_gator_nf28.BddInterne.SprintBDD;
import com.agil_gator_nf28.BddInterne.TacheBDD;
import com.agil_gator_nf28.Projet.Projet;
import com.agil_gator_nf28.SousTaches.SousTache;
import com.agil_gator_nf28.Sprint.Sprint;
import com.agil_gator_nf28.Taches.Tache;
import com.agil_gator_nf28.constantes.AndroidConstantes;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.SeriesSelection;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Statistic_sprint extends ActionBarActivity {

    // ordonnées somme des difficultés restantes, temps
    //camenbert pourcentage des sous taches
    private int ID;
    private static int[] COLORS = new int[] { Color.GREEN, Color.BLUE,Color.YELLOW, Color.RED };
    //private int[] values = new int[4];
    private static String[] NAME_LIST = new String[] { "A Faire", "En Cours", "A Relire", "Done" };
    private CategorySeries mSeries = new CategorySeries("");
    private DefaultRenderer mRenderer = new DefaultRenderer();
    private GraphicalView mChartView;
    private Projet projet;
    private Sprint sprint;
    private int afaire, encours, arelire, done;
    private static HashMap<Integer,String> mapTitle = new HashMap<Integer, String>() {{put(1,"A Faire");}{put(2,"En Cours");}{put(3,"A Relire");}{put(4,"Done");}};

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_statistic);

        Intent intent = getIntent();
        ID = Integer.valueOf(intent.getStringExtra(AndroidConstantes.PROJECT_ID));

        if(ID != -1){
            ProjetBDD projetBDD = new ProjetBDD(this);
            projetBDD.open();
            projet = projetBDD.getProjetById(ID);
            projetBDD.close();

            SprintBDD sprintBDD = new SprintBDD(this);
            sprintBDD.open();
            sprint = sprintBDD.getLastSprintOfProject(projet);
            ArrayList<SousTache> sousTaches = sprintBDD.getSousTachesFromSprintID(sprint.getId());
            sprintBDD.close();

            Tache tache = new Tache();
            tache.setSousTaches(sousTaches);

            afaire = tache.getSousTachesAFaire().size();
            encours = tache.getSousTachesEnCours().size();
            arelire = tache.getSousTachesARelire().size();
            done = tache.getSousTachesDone().size();

            int[] values = new int[] {afaire,encours,arelire,done};

            mRenderer.setApplyBackgroundColor(true);
            mRenderer.setBackgroundColor(Color.argb(100, 50, 50, 50));
            mRenderer.setChartTitleTextSize(20);
            mRenderer.setLabelsTextSize(15);
            mRenderer.setLegendTextSize(15);
            mRenderer.setMargins(new int[] { 20, 30, 15, 0 });
            mRenderer.setZoomButtonsVisible(true);
            mRenderer.setStartAngle(90);

            for (int i = 0; i < values.length; i++) {
                mSeries.add(NAME_LIST[i] + " " + values[i], values[i]);
                SimpleSeriesRenderer renderer = new SimpleSeriesRenderer();
                renderer.setColor(COLORS[(mSeries.getItemCount() - 1) % COLORS.length]);
                mRenderer.addSeriesRenderer(renderer);
            }

            if (mChartView != null) {
                mChartView.repaint();
            }
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        if (mChartView == null) {
            LinearLayout layout = (LinearLayout) findViewById(R.id.statistic_view);
            mChartView = ChartFactory.getPieChartView(this, mSeries, mRenderer);
            mRenderer.setClickEnabled(true);
            mRenderer.setSelectableBuffer(10);

            mChartView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SeriesSelection seriesSelection = mChartView.getCurrentSeriesAndPoint();

                    if (seriesSelection == null) {
                        Toast.makeText(Statistic_sprint.this, "Pas d'element du camembert a été selectionné" + afaire + " " + encours + " " + arelire + " " + done + " " + ID, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(Statistic_sprint.this, mapTitle.get(seriesSelection.getPointIndex()+1) + " : nombre de sous tâches ="+ seriesSelection.getValue(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

            layout.addView(mChartView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT));
        }
        else {
            mChartView.repaint();
        }
    }

    @Override
    public Intent getSupportParentActivityIntent() {
        Intent intent = new Intent(this, Page_projet.class);
        intent.putExtra(AndroidConstantes.PROJECT_ID, String.valueOf(projet.getId()));
        return intent;
    }

}
