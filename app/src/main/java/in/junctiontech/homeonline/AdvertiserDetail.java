package in.junctiontech.homeonline;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class AdvertiserDetail extends AppCompatActivity {
private TextView tv_advertiser;
    private RadioButton advertiser_owner,advertiser_broker,advertiser_lives_in_same_building_yes,advertiser_lives_in_same_building_no,
            developer_private,developer_govt;
    private EditText advertise_et_owner_name_edit,advertise_et_owner_number_edit,advertise_et_owner_alternate_number_edit,
            advertise_et_owner_email_edit,advertise_et_developer_name_edit;
    private Spinner advertiser_spinner_owner_type;
    String owner_type="Freehold",owner_type_array[];


    private DBHandler db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advertiser_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        db = new DBHandler(this, "DB", null, 1);
        Resources r=this.getResources();
        owner_type_array= r.getStringArray(R.array.owner_type);
        tv_advertiser=(TextView)findViewById(R.id.tv_advertiser);
        tv_advertiser.setPaintFlags(tv_advertiser.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
        Bundle b = db.getIdName();
        tv_advertiser.setText(b.getString("name"));

        advertiser_owner=(RadioButton)findViewById(R.id.advertiser_owner);
        advertiser_broker=(RadioButton)findViewById(R.id.advertiser_broker);
        advertiser_lives_in_same_building_yes=(RadioButton)findViewById(R.id.advertiser_lives_in_same_building_yes);
        advertiser_lives_in_same_building_no=(RadioButton)findViewById(R.id.advertiser_lives_in_same_building_no);
        developer_private=(RadioButton)findViewById(R.id.developer_private);
        developer_govt=(RadioButton)findViewById(R.id.developer_govt);

        advertise_et_owner_name_edit=(EditText)findViewById(R.id.advertise_et_owner_name_edit);
        advertise_et_owner_number_edit=(EditText)findViewById(R.id.advertise_et_owner_number_edit);
        advertise_et_owner_alternate_number_edit=(EditText)findViewById(R.id.advertise_et_owner_alternate_number_edit);
        advertise_et_owner_email_edit=(EditText)findViewById(R.id.advertise_et_owner_email_edit);
        advertise_et_developer_name_edit=(EditText)findViewById(R.id.advertise_et_developer_name_edit);

        advertiser_spinner_owner_type=(Spinner)findViewById(R.id.advertiser_spinner_owner_type);
        advertiser_spinner_owner_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                owner_type = owner_type_array[position];
                //            Toast.makeText(BedRoom.this, bedroom_id, Toast.LENGTH_SHORT).show();


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



    }

    @Override
    public boolean onSupportNavigateUp() {
        startActivity(new Intent(this, STEP2.class));
        finish();
        return true;
    }

    public void onResume() {
        super.onResume();
        getAdvertiser();

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


        if(id==R.id.action_my_next){
            setAdvertiserDetail();
            Toast.makeText(this, "NEXT", Toast.LENGTH_LONG).show();
            startActivity(new Intent(this,LivingRoom.class));
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
    public void myClick(View v){
        setAdvertiserDetail();
        startActivity(new Intent(this,LivingRoom.class));
        finish();
    }

    private void setAdvertiserDetail(){

        String owner_name=advertise_et_owner_name_edit.getText().toString();
        String owner_number=advertise_et_owner_number_edit.getText().toString();
        String owner_alternate_number=advertise_et_owner_alternate_number_edit.getText().toString();
        String owner_email=advertise_et_owner_email_edit.getText().toString();
        String developer_name=advertise_et_developer_name_edit.getText().toString();
        String  owner_broker = (advertiser_owner.isChecked() ? "Owner" : "Broker");
        String  owner_lives_in_same_building = (advertiser_lives_in_same_building_yes.isChecked() ? "Yes" : "No");
        String  developer_type = (developer_private.isChecked() ? "Private" : "Govt");


        db.setAdvertiserDetail(owner_name,owner_number,owner_alternate_number,owner_email,developer_name,owner_broker,
                owner_lives_in_same_building,developer_type,owner_type,"true");
    }

    public void getAdvertiser() {
        Bundle b = db.getAdvertiserDetail();

        advertise_et_owner_name_edit.setText(b.getString("owner_name"));
        advertise_et_owner_number_edit.setText(b.getString("owner_number"));
        advertise_et_owner_alternate_number_edit.setText(b.getString("owner_alternate_number"));
        advertise_et_owner_email_edit.setText(b.getString("owner_email"));
        advertise_et_developer_name_edit.setText(b.getString("developer_name"));

        if (b.getString("owner_broker") == null);
          else if ((b.getString("owner_broker")).equalsIgnoreCase("Owner"))
            advertiser_owner.setChecked(true);
        else if ((b.getString("owner_broker")).equalsIgnoreCase("Broker"))
            advertiser_broker.setChecked(true);


        if (b.getString("owner_lives_in_same_building") == null);
        else if ((b.getString("owner_lives_in_same_building")).equalsIgnoreCase("Yes"))
            advertiser_lives_in_same_building_yes.setChecked(true);
        else if ((b.getString("owner_lives_in_same_building")).equalsIgnoreCase("No"))
            advertiser_lives_in_same_building_no.setChecked(true);

        if (b.getString("developer_type") == null);
                  else if ((b.getString("developer_type")).equalsIgnoreCase("Private"))
            developer_private.setChecked(true);
        else if ((b.getString("developer_type")).equalsIgnoreCase("Govt"))
            developer_private.setChecked(true);


        String s = b.getString("owner_type");

        if (s == null)
            advertiser_spinner_owner_type.setSelection(0);
        else {
            int i = 0;
            for (; i < owner_type_array.length; i++) {
                if (owner_type_array[i].equalsIgnoreCase(s))
                    break;
            }
            advertiser_spinner_owner_type.setSelection(i);
        }

    }
}