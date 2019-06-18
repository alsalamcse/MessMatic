package rosette.draz.com;

import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ListMessages extends AppCompatActivity {

    public static ListView lv_messages;
    FirebaseAuth auth;
    FirebaseUser user;
    private DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_messages);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        lv_messages = findViewById(R.id.lv_messages);





        }
    public void listView ()
    {
        DatabaseReference users = databaseReference.child("Users:");
        users.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<String> list = new ArrayList<>();
                for (DataSnapshot it : dataSnapshot.getChildren()) {
                    list.add(dataSnapshot.child(it.getKey()).child("Message").getValue(String.class)) ;
                }
                String[] Messages = new String[list.size()];
                for (int i = 0; i < list.size(); i++) {
                    Messages[i] = list.get(i);
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(ListMessages.this, R.layout.activity_list_messages, Messages);
                AlertDialog.Builder list_view;
                lv_messages.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        lv_messages.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String value = (String) lv_messages.getItemAtPosition(position);
                Toast.makeText(ListMessages.this, "position :" + position, Toast.LENGTH_LONG).show();
            }
        });
    }
}
