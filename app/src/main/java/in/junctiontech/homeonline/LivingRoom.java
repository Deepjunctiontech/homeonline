package in.junctiontech.homeonline;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class LivingRoom extends AppCompatActivity {
private CheckBox sofa,diningtable,ac,tv,shoerack,falseceiling;
    private RadioGroup flooringtype;
    private RadioButton marble,wood,ceramic,stone,latimate;
    private Spinner livingroom_spiner_flooringtype;
    private DBHandler db;
    private String livingroom_flooringtype="Marble";
    private Spinner property_spinner_total_living;
    private String livingroom_id="1";
    private Button detail_btn_living;
    static int i=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_living_room);
        sofa= (CheckBox) findViewById(R.id.livingroom_ck_sofa);
        diningtable= (CheckBox) findViewById(R.id.livingroom_ck_diningtable);
        ac= (CheckBox) findViewById(R.id.livingroom_ck_ac);
        tv= (CheckBox) findViewById(R.id.livingroom_ck_tv);
        shoerack= (CheckBox) findViewById(R.id.livingroom_ck_shoerack);
        falseceiling= (CheckBox) findViewById(R.id.livingroom_ck_falseceiling);
        livingroom_spiner_flooringtype = (Spinner) findViewById(R.id.livingroom_spiner_flooringtype);
        property_spinner_total_living=(Spinner) findViewById(R.id.property_spinner_total_living);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        db = new DBHandler(this, "DB", null, 1);

        TextView name = (TextView) findViewById(R.id.tv_living);
        name.setPaintFlags(name.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
        Bundle b = db.getIdName();
        name.setText(b.getString("name"));

        String check=db.getNoOfRoom("no_of_livingroom");
        detail_btn_living= (Button) findViewById(R.id.detail_btn_living);
       if(check==null)
           check="1";
        int c= Integer.parseInt(check);
        String []total=new String[c];

        for(int i=0;i<c;i++)
            total[i]=i+1+"";


        ArrayAdapter<String> obj=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,total);
        property_spinner_total_living.setAdapter(obj);

        livingroom_spiner_flooringtype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                livingroom_flooringtype = ((TextView) view).getText().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        property_spinner_total_living.setAdapter(obj);

        property_spinner_total_living.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position!=0)
                setLiving();
               livingroom_id=((TextView)view).getText().toString();
                getLiving(livingroom_id);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    @Override
    public boolean onSupportNavigateUp() {
        startActivity(new Intent(this,STEP2.class));
        finish();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.actionbar_save, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_my_next) {
            setLiving();
            Toast.makeText(this,"NEXT",Toast.LENGTH_LONG).show();
            startActivity(new Intent(this, BedRoom.class));
            finish();
         //   setLiving();
        }

        return super.onOptionsItemSelected(item);
    }


    private void setLiving() {

        String sofa1 = (sofa.isChecked() ? "Y" : "N");
        String diningtable1 = (diningtable.isChecked() ? "Y" : "N");
        String ac1a = (ac.isChecked() ? "Y" : "N");
        String tv1 = (tv.isChecked() ? "Y" : "N");
        String shoerack1 = (shoerack.isChecked() ? "Y" : "N");
        String false_ceiling1 = (falseceiling.isChecked() ? "Y" : "N");


        db.setLivingRoom(livingroom_id,sofa1,diningtable1,ac1a,tv1,shoerack1,livingroom_flooringtype,false_ceiling1,"true");


    }
    public void onResume(){
        super.onResume();
        getLiving(livingroom_id);

}

    public void getLiving(String livingroom_id) {
        Bundle b=db.getLivingRoom(livingroom_id);
        Resources r= this.getResources();
        String flooringtype[]=r.getStringArray(R.array.flooring);

        String s=b.getString("flooring_type");

        if(s==null)
        livingroom_spiner_flooringtype.setSelection(0);
        else {
            int i=0;
            for(;i<flooringtype.length;i++){
                if(flooringtype[i].equalsIgnoreCase(s))
                    break;
            }
            livingroom_spiner_flooringtype.setSelection(i);
        }




        if(b.getString("sofa")==null)
        sofa.setChecked(false);
        else if((b.getString("sofa")).equalsIgnoreCase("Y"))
            sofa.setChecked(true);
        else if((b.getString("sofa")).equalsIgnoreCase("N"))
        sofa.setChecked(false);

        if(b.getString("ac")==null)
            ac.setChecked(false);
        else if((b.getString("ac")).equalsIgnoreCase("Y"))
            ac.setChecked(true);
        else if((b.getString("ac")).equalsIgnoreCase("N"))
            ac.setChecked(false);

        if(b.getString("tv")==null)
        tv.setChecked(false);
        else if((b.getString("tv")).equalsIgnoreCase("Y"))
            tv.setChecked(true);
        else if((b.getString("tv")).equalsIgnoreCase("N"))
            tv.setChecked(false);

        if(b.getString("dining_table")==null)
        diningtable.setChecked(false);
        else if((b.getString("dining_table")).equalsIgnoreCase("Y"))
            diningtable.setChecked(true);
        else if((b.getString("dining_table")).equalsIgnoreCase("N"))
            diningtable.setChecked(false);

        if(b.getString("shoe_rack")==null)
        shoerack.setChecked(false);
        else if((b.getString("shoe_rack")).equalsIgnoreCase("Y"))
            shoerack.setChecked(true);
        else if((b.getString("shoe_rack")).equalsIgnoreCase("N"))
            shoerack.setChecked(false);

        if(b.getString("false_ceiling")==null)
        falseceiling.setChecked(false);
        else if((b.getString("false_ceiling")).equalsIgnoreCase("Y"))
            falseceiling.setChecked(true);
        else if((b.getString("false_ceiling")).equalsIgnoreCase("N"))
            falseceiling.setChecked(false);

    }

    public void myClick(View v){
        setLiving();
        startActivity(new Intent(this,BedRoom.class));
        finish();
    }

}