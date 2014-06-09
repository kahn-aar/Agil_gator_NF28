package com.agil_gator_nf28.agil_gator;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.agil_gator_nf28.BddInterne.SousTacheBDD;
import com.agil_gator_nf28.BddInterne.TacheBDD;
import com.agil_gator_nf28.SousTaches.SousTache;
import com.agil_gator_nf28.Taches.Tache;
import com.agil_gator_nf28.constantes.AndroidConstantes;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.SeriesSelection;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Mathieu on 05/06/14.
 */
public class Statistic extends ActionBarActivity {

    // ordonnées somme des difficultés restantes, temps
    //camenbert pourcentage des sous taches
    private int tacheId;
    private int projectId;
    private static int[] COLORS = new int[] { Color.GREEN, Color.BLUE,Color.YELLOW, Color.RED };
    private static String[] NAME_LIST = new String[] { "A Faire", "En Cours", "A Relire", "Done" };
    private CategorySeries mSeries = new CategorySeries("");
    private DefaultRenderer mRenderer = new DefaultRenderer();
    private GraphicalView mChartView;
    private Tache tache;
    private int afaire, encours, arelire, done;
    private static HashMap<Integer,String> mapTitle = new HashMap<Integer, String>() {{put(1,"A Faire");}{put(2,"En Cours");}{put(3,"A Relire");}{put(4,"Done");}};

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_statistic);

        Intent intent = getIntent();
        tacheId = intent.getIntExtra(AndroidConstantes.TACHE_ID,-1);
        projectId = intent.getIntExtra(AndroidConstantes.PROJECT_ID,-1);


        if(tacheId != -1){
            TacheBDD tacheBDD = new TacheBDD(this);

            tacheBDD.open();

            tache = tacheBDD.getTacheWithId(tacheId);

            tacheBDD.close();

            SousTacheBDD subBDD = new SousTacheBDD(this);
            subBDD.open();
            List<SousTache> sousTaches = subBDD.getSousTaches(tache);
            subBDD.close();

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

                    if (seriesSelection != null) {
                        Toast.makeText(Statistic.this, mapTitle.get(seriesSelection.getPointIndex()+1) + " : nombre de sous tâches ="+ seriesSelection.getValue(), Toast.LENGTH_SHORT).show();
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
        this.finish();
        return null;
    }

}
