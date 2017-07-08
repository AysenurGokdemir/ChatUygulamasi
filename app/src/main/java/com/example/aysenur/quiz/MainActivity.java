package com.example.aysenur.quiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private FirebaseUser fUser;
    private FirebaseAuth fAuth;
    private FirebaseDatabase fDatabase;
    private DatabaseReference dRef;

    private int i = 0;
    private int point = 0;
    private ArrayList<Quiz> quiz;
    TextView tv_quiz_question;
    RadioButton rb_quiz_answer_1,rb_quiz_answer_2,rb_quiz_answer_3,rb_quiz_answer_4,rb_quiz_answer_5;
    Button btn_quiz_answer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fAuth = FirebaseAuth.getInstance();
        fUser = fAuth.getCurrentUser();
        fDatabase = FirebaseDatabase.getInstance();
        dRef = fDatabase.getReference("questions");

        quiz = new ArrayList<Quiz>();

        if (fUser == null)
        {
            Intent intent = new Intent(MainActivity.this,LoginActivity.class);
            startActivity(intent);
            finish();
        }

        tv_quiz_question= (TextView) findViewById(R.id.tv_quiz_question);
        btn_quiz_answer = (Button) findViewById(R.id.btn_quiz_answer);
        rb_quiz_answer_1 = (RadioButton) findViewById(R.id.rb_quiz_answer_1);
        rb_quiz_answer_2 = (RadioButton) findViewById(R.id.rb_quiz_answer_2);
        rb_quiz_answer_3 = (RadioButton) findViewById(R.id.rb_quiz_answer_3);
        rb_quiz_answer_4 = (RadioButton) findViewById(R.id.rb_quiz_answer_4);
        rb_quiz_answer_5 = (RadioButton) findViewById(R.id.rb_quiz_answer_5);


        dRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren())
                {
                    quiz.add(ds.getValue(Quiz.class));
                }
                soruGoster();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }


        });

        btn_quiz_answer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (quiz.get(i).getCorrectAnswer())
                {
                    case "a":
                        if  (rb_quiz_answer_1.isChecked()) {
                            point+=5;
                        }
                        i++;
                        soruGoster();
                        toastGoster();
                        break;
                    case "b":
                        if  (rb_quiz_answer_2.isChecked()) {
                            point+=5;
                        }
                        i++;
                        soruGoster();
                        toastGoster();
                        break;
                    case "c":
                        if  (rb_quiz_answer_3.isChecked()) {
                            point+=5;
                        }
                        i++;
                        soruGoster();
                        toastGoster();
                        break;
                    case "d":
                        if  (rb_quiz_answer_4.isChecked()) {
                            point+=5;
                        }
                        i++;
                        soruGoster();
                        toastGoster();
                        break;
                    case "e":
                        if  (rb_quiz_answer_5.isChecked()) {
                            point+=5;
                        }
                        i++;
                        soruGoster();
                        toastGoster();
                        break;
                    default:
                        break;
                }
            }
        });
    }

    private void soruGoster()
    {
        if (i >= quiz.size())
        {
            btn_quiz_answer.setEnabled(false);
            toastGoster();
            dRef = fDatabase.getReference("Puan").push();
            dRef.child("Kullanıcı").setValue(fUser.getEmail());
            dRef.child("Puan").setValue(point);
        }
        else
        {
            rb_quiz_answer_1.setChecked(false);
            rb_quiz_answer_2.setChecked(false);
            rb_quiz_answer_3.setChecked(false);
            rb_quiz_answer_4.setChecked(false);
            rb_quiz_answer_5.setChecked(false);
            tv_quiz_question.setText(quiz.get(i).getQuestionText());
            rb_quiz_answer_1.setText(quiz.get(i).getChoice_a());
            rb_quiz_answer_2.setText(quiz.get(i).getChoice_b());
            rb_quiz_answer_3.setText(quiz.get(i).getChoice_c());
            rb_quiz_answer_4.setText(quiz.get(i).getChoice_d());
            rb_quiz_answer_5.setText(quiz.get(i).getChoice_e());
        }


    }

    private void toastGoster()
    {
        Toast.makeText(getApplicationContext(),"Puanınız : " + point,Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.exit:
                fAuth.signOut();
                Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
                break;

        }
        return super.onOptionsItemSelected(item);
    }
}
