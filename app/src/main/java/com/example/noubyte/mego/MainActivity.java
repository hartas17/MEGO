package com.example.noubyte.mego;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;


import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.gc.materialdesign.views.ButtonFlat;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.Utils;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Vector;

import io.palaima.smoothbluetooth.Device;
import io.palaima.smoothbluetooth.SmoothBluetooth;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,OnChartValueSelectedListener {

    private Button Scann;
    private Button Connect;
    private Button Disconnect;

    private ButtonFlat connect;
    private ButtonFlat disconnect;
    private ListView Serial;
    private SmoothBluetooth mSmoothBluetooth;
    private LineChart mChart,mChart2,mChart3;
    private String val;
    boolean flag=false;
    private char medicion='g';
    private TextView opcion;
    private float valorx,valory,valorz;
    FloatingActionButton fab;
    private static final String TAG="MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        mSmoothBluetooth = new SmoothBluetooth(this);
        mSmoothBluetooth.setListener(mListener);
        valorx=0;
        valory=0;
        valorz=0;
        setContentView(R.layout.activity_main);
        opcion=(TextView)findViewById(R.id.textView);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setData(45, 100);
        val="";

        init();

        //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        connect=(ButtonFlat) findViewById(R.id.button_Conectar);
        connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                val="";
                mSmoothBluetooth.tryConnection();

            }
        });

        disconnect=(ButtonFlat) findViewById(R.id.button_Desconectar);
        disconnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mChart.clear();
                mChart2.clear();
                mChart3.clear();
                val="";
                init();
                mSmoothBluetooth.disconnect();
            }
        });
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                mSmoothBluetooth.doDiscovery();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void init(){
        mChart = (LineChart) findViewById(R.id.chart);
        mChart.setOnChartValueSelectedListener(this);
        mChart.setDrawGridBackground(false);
        mChart.getDescription().setEnabled(false);
        // add an empty data object

        mChart.setData(new LineData());
//        mChart.getXAxis().setDrawLabels(false);
//        mChart.getXAxis().setDrawGridLines(false);

        mChart.invalidate();
        //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<mchart2>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        mChart2 = (LineChart) findViewById(R.id.chart2);
        mChart2.setOnChartValueSelectedListener(this);
        mChart2.setDrawGridBackground(false);
        mChart2.getDescription().setEnabled(false);


        mChart2.setData(new LineData());


        mChart2.invalidate();
        //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<mchart3>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        mChart3 = (LineChart) findViewById(R.id.chart3);
        mChart3.setOnChartValueSelectedListener(this);
        mChart3.setDrawGridBackground(false);
        mChart3.getDescription().setEnabled(false);


        mChart3.setData(new LineData());


        mChart3.invalidate();
    }

    private void addEntry(float y,float rango) {
        LineData data = mChart.getData();

        LineDataSet set = (LineDataSet) data.getDataSetByIndex(0);
        // set.addEntry(...); // can be called as well

        if (set == null) {
            set = createSet();
            set.setDrawValues(false);
            set.setDrawCircles(false);
            data.addDataSet(set);
        }

        // choose a random dataSet
        int randomDataSetIndex = (int) (Math.random() * data.getDataSetCount());
        float yValue = (float)(Math.random() * 10) + 50f;

        data.addEntry(new Entry(data.getDataSetByIndex(randomDataSetIndex).getEntryCount(),y), randomDataSetIndex);
        data.notifyDataChanged();

        // let the chart know it's data has changed



        mChart.notifyDataSetChanged();
        mChart.getAxisLeft().setAxisMaximum(rango*2);
        mChart.getAxisLeft().setAxisMinimum(rango*-2);
        mChart.setVisibleYRangeMaximum(rango*5, YAxis.AxisDependency.LEFT);

        mChart.moveViewTo(data.getDataSetCount() - 7, 50f, YAxis.AxisDependency.LEFT);
    }

    private void addEntry2(float y, float rango) {
        LineData data = mChart2.getData();

        LineDataSet set = (LineDataSet) data.getDataSetByIndex(0);
        // set.addEntry(...); // can be called as well

        if (set == null) {
            set = createSet();
            set.setDrawValues(false);
            set.setDrawCircles(false);
            data.addDataSet(set);
        }

        // choose a random dataSet
        int randomDataSetIndex = (int) (Math.random() * data.getDataSetCount());


        data.addEntry(new Entry(data.getDataSetByIndex(randomDataSetIndex).getEntryCount(),y), randomDataSetIndex);
        data.notifyDataChanged();

        // let the chart know it's data has changed
        mChart2.notifyDataSetChanged();

        mChart2.getAxisLeft().setAxisMaximum(rango*2);
        mChart2.getAxisLeft().setAxisMinimum(rango*-2);
        mChart2.setVisibleYRangeMaximum(rango*5, YAxis.AxisDependency.LEFT);
//
//
        mChart2.moveViewTo(data.getDataSetCount() - 7, 50f, YAxis.AxisDependency.LEFT);
    }

    private void addEntry3(float y,float rango) {
        LineData data = mChart3.getData();

        LineDataSet set = (LineDataSet) data.getDataSetByIndex(0);
        // set.addEntry(...); // can be called as well

        if (set == null) {
            set = createSet();
            set.setDrawValues(false);
            set.setDrawCircles(false);
            data.addDataSet(set);
        }

        // choose a random dataSet
        int randomDataSetIndex = (int) (Math.random() * data.getDataSetCount());

        data.addEntry(new Entry(data.getDataSetByIndex(randomDataSetIndex).getEntryCount(),y), randomDataSetIndex);
        data.notifyDataChanged();

        // let the chart know it's data has changed
        mChart3.notifyDataSetChanged();

        //mChart3.setVisibleXRangeMaximum(6);
        mChart3.getAxisLeft().setAxisMaximum(rango*2);
        mChart3.getAxisLeft().setAxisMinimum(rango*-2);
        mChart3.setVisibleYRangeMaximum(rango*5, YAxis.AxisDependency.LEFT);
//
//            // this automatically refreshes the chart (calls invalidate())
        mChart3.moveViewTo(data.getDataSetCount() - 7, 50f, YAxis.AxisDependency.LEFT);
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    private void setData(int count, float range) {

        ArrayList<Entry> values = new ArrayList<Entry>();

        for (int i = 0; i < count; i++) {

            float val = (float) (Math.random() * range) + 3;
            values.add(new Entry(i, val));
        }

        LineDataSet set1;


            // create a dataset and give it a type
            set1 = new LineDataSet(values, "DataSet 1");

            // set the line to be drawn like this "- - - - - -"
            set1.enableDashedLine(360f, 5f, 0f);
            set1.enableDashedHighlightLine(10f, 5f, 0f);
            set1.setColor(Color.BLACK);
            set1.setCircleColor(Color.BLACK);
            set1.setLineWidth(1f);
            set1.setCircleRadius(3f);
            set1.setDrawCircleHole(false);
            set1.setValueTextSize(9f);
            set1.setDrawFilled(true);
            set1.setFormLineWidth(1f);
            set1.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
            set1.setFormSize(15.f);

            if (Utils.getSDKInt() >= 18) {
                // fill drawable only supported on api level 18 and above
                Drawable drawable = ContextCompat.getDrawable(this, R.drawable.fade_red);
                set1.setFillDrawable(drawable);
            }
            else {
                set1.setFillColor(Color.BLACK);
            }

            ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
            dataSets.add(set1); // add the datasets

            // create a data object with the datasets
            LineData data = new LineData(dataSets);

            // set data


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch(id){
            case R.id.action_acel:
                medicion='a';
                opcion.setText("Acelerometro");

                break;
            case R.id.action_giro:
                medicion='g';
                opcion.setText("Giroscopio");
                break;
            case R.id.action_mag:
                medicion='m';
                opcion.setText("Magnetometro");
                break;
            case R.id.action_pitch:
                medicion='p';
                opcion.setText("Pitch/Roll/Heading");
                break;
        }
      return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {
        } else if (id == R.id.nav_slideshow) {
        } else if (id == R.id.nav_manage) {
        } else if (id == R.id.nav_share) {
        } else if (id == R.id.nav_send) {
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSmoothBluetooth.stop();
    }

    private SmoothBluetooth.Listener mListener = new SmoothBluetooth.Listener() {
        @Override
        public void onBluetoothNotSupported() {
            //device does not support bluetooth
        }

        @Override
        public void onBluetoothNotEnabled() {
            //bluetooth is disabled, probably call Intent request to enable bluetooth
        }

        @Override
        public void onConnecting(Device device) {
            //called when connecting to particular device
        }

        @Override
        public void onConnected(Device device) {
            //called when connected to particular device
        }

        @Override
        public void onDisconnected() {
            //called when disconnected from device
        }

        @Override
        public void onConnectionFailed(Device device) {
            //called when connection failed to particular device
        }

        @Override
        public void onDiscoveryStarted() {
            //called when discovery is started
        }

        @Override
        public void onDiscoveryFinished() {
            //called when discovery is finished
        }

        @Override
        public void onNoDevicesFound() {
            //called when no devices found
        }

        @Override
        public void onDevicesFound(final List<Device> deviceList,
                                   final SmoothBluetooth.ConnectionCallback connectionCallback) {
            //receives discovered devices list and connection callback
            //you can filter devices list and connect to specific one
            //connectionCallback.connectTo(deviceList.get(position));
            Vector<String> temp = new Vector<String>();

            for(int i=0;i<deviceList.size();i++){
                temp.add(deviceList.get(i).getName());
            }

            new MaterialDialog.Builder(MainActivity.this)
                    .title("Devices")
                    .items(temp)
                    .itemsCallbackSingleChoice(-1, new MaterialDialog.ListCallbackSingleChoice() {
                        @Override
                        public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                            /**
                             * If you use alwaysCallSingleChoiceCallback(), which is discussed below,
                             * returning false here won't allow the newly selected radio button to actually be selected.
                             **/
                            try{
                            connectionCallback.connectTo(deviceList.get(which));
                            dialog.dismiss();
                            }catch(Exception e){
                                e.printStackTrace();
                            }
                            return true;
                        }
                    })
                    .positiveText("Conectando")
                    .show();
            }

        @Override
        public void onDataReceived(int data) {
            val+=(char)data;
            if(data==10){
                String [] spl=val.split("\t");
                if(spl.length>=5){
                    float y1,y2,y3;
                    switch(medicion){
                        case 'a':
                            y1=Float.parseFloat(spl[1].split(" ")[1]);
                            y2=Float.parseFloat(spl[1].split(" ")[2]);
                            y3=Float.parseFloat(spl[1].split(" ")[3]);
                            addEntry(y1,2f);
                            addEntry2(y2,2f);
                            addEntry3(y3,2f);
                            if (Math.abs(valorx-Math.abs(y1))>0.05){
                                System.out.println("movimiento en x");
                            }
                            if (Math.abs(valory-Math.abs(y2))>0.05){
                                System.out.println("movimiento en y");
                            }
                            if (Math.abs(valorz-Math.abs(y3))>0.05) {
                                System.out.println("movimiento en z");
                            }
                            valorx=Math.abs(y1);
                            valory=Math.abs(y2);
                            valorz=Math.abs(y3);
                            break;
                        case 'm':
                            //System.out.println(spl[2]);
                            //System.out.println(spl[2].split(" ").length);
                            y1=Float.parseFloat(spl[2].split(" ")[1]);
                            y2=Float.parseFloat(spl[2].split(" ")[2]);
                            y3=Float.parseFloat(spl[2].split(" ")[3]);
                            addEntry(y1,1f);
                            addEntry2(y2,1f);
                            addEntry3(y3,1f);
                            break;
                        case 'g':
                            //System.out.println(spl[0]);
                            //System.out.println(spl[0].split(" ").length);
                            y1=Float.parseFloat(spl[0].split(" ")[1]);
                            y2=Float.parseFloat(spl[0].split(" ")[2]);
                            y3=Float.parseFloat(spl[0].split(" ")[3]);
                            addEntry(y1,245f);
                            addEntry2(y2,245f);
                            addEntry3(y3,245f);
                            break;
                        case 'p':
                            /* System.out.println(spl[3]);
                            System.out.println(spl[3].split(" ").length);
                            System.out.println(spl[4]);
                            System.out.println(spl[4].split(" ").length);*/
                           y1=Float.parseFloat(spl[3].split(" ")[2]);
                            y2=Float.parseFloat(spl[3].split(" ")[3]);
                            y3=Float.parseFloat(spl[4].split(" ")[1]);
                            addEntry(y1,100);
                            addEntry2(y2,200);
                            addEntry3(y3,100);
                            break;
                    }
                    val="";

                }
                else
                {
                    val="";
                }
            }


        }
    };

    @Override
    public void onValueSelected(Entry e, Highlight h) {
        Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected() {

    }
    private LineDataSet createSet() {

        LineDataSet set = new LineDataSet(null, "DataSet 1");
        set.setLineWidth(2.5f);
        set.setCircleRadius(4.5f);
        set.setColor(Color.rgb(240, 99, 99));
        set.setCircleColor(Color.rgb(240, 99, 99));
        set.setHighLightColor(Color.rgb(190, 190, 190));
        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        set.setValueTextSize(10f);

        return set;
    }
}
