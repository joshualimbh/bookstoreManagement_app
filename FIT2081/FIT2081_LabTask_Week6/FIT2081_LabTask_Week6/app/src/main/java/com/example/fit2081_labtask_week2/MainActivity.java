package com.example.fit2081_labtask_week2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.Random;
import java.util.StringTokenizer;

public class MainActivity extends AppCompatActivity {
    ArrayList<String> week5List = new ArrayList<>();
    ArrayList<Data> week6List = new ArrayList<>();
    //recycler view
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    recyclerViewAdapter myRecyclerAdapter;
    DrawerLayout drawerLayout;
    ArrayAdapter week5Adapter;
    String Title, ID, Author,Desc,newISBN;
    double Price;
    int ISBN;
    int count= 0;
    View myFrame;
    int x_down;
    int y_down;
    Double price_counter = 0.0 ;
    EditText TitleOfBook,IDofBook,AuthorOfBook,DescOfBook,PriceOfBook,ISBNOfBook;
    GestureDetector gestureDetector;
    RandomString randomString = new RandomString() {
        @Override
        public String generateNewRandomString(int length) {
            return super.generateNewRandomString(length);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_layout);
        //recycler view
        recyclerView = findViewById(R.id.myRecyclerView);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        myRecyclerAdapter = new recyclerViewAdapter(week6List);
        recyclerView.setAdapter(myRecyclerAdapter);
        //toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //List View
//        ListView listView = findViewById(R.id.listView);
//        week5Adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,week5List);
//        listView.setAdapter(week5Adapter);
        //drawer layout
        drawerLayout = findViewById(R.id.drawerLayout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        //Navigation View
        NavigationView navigationView =findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(new MyDrawerListener());
        //week 1-4
        TitleOfBook = findViewById(R.id.TitleOfBook);
        IDofBook = findViewById(R.id.IDofBook);
        AuthorOfBook = findViewById(R.id.AuthorOfBook);
        DescOfBook = findViewById(R.id.DescOfBook);
        PriceOfBook = findViewById(R.id.PriceOfBook);
        ISBNOfBook = findViewById(R.id.ISBNOfBook);
        /* Request permissions to access SMS */
        ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.SEND_SMS, android.Manifest.permission.RECEIVE_SMS, Manifest.permission.READ_SMS}, 0);
        /* Create and instantiate the local broadcast receiver
           This class listens to messages come from class SMSReceiver*/
        MyBroadCastReceiver myBroadCastReceiver = new MyBroadCastReceiver();
        /* * Register the broadcast handler with the intent filter that is declared in
         * class SMSReceiver @line 11* */
        registerReceiver(myBroadCastReceiver,new IntentFilter(SMSReceiver.SMS_FILTER));
        //Week10 Gestures
        myFrame = findViewById(R.id.touchLayout);
//        myFrame.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                int action = motionEvent.getActionMasked();
//                switch(action){
//                    case MotionEvent.ACTION_DOWN:
//                        x_down = (int)motionEvent.getX();
//                        y_down = (int)motionEvent.getY();
//                        return true;
//                    case MotionEvent.ACTION_MOVE:
//                        price_counter = 0.0;
//
//                        if(Math.abs(y_down-motionEvent.getY())<40){
//                            if(x_down-motionEvent.getX()<0){
//                                price_counter+=1.0;
//                                price_counter = Double.valueOf(String.valueOf(PriceOfBook.getText()))+price_counter;
//                                PriceOfBook.setText(price_counter.toString());
//                            }
//                        }
//                        return true;
//                    case MotionEvent.ACTION_UP:
//                        if(Math.abs(y_down-motionEvent.getY())<40){
//                            if(x_down-motionEvent.getX()>0){
//                                addBook();
//                            }
//                        }
//                        else if (Math.abs(x_down-motionEvent.getX())<40){
//                            if(y_down>motionEvent.getY()){
//                                clearFields();
//                            }
//                        }
//                }
//                return true;
//            }
//        });
        //week5FAB
        FloatingActionButton fab = findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addBook();
            }
        });
        //week11
        gestureDetector = new GestureDetector(this,new MGestureDetector());
        myFrame.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                gestureDetector.onTouchEvent(motionEvent);
                return true;
            }
        });

    }
    class MGestureDetector extends GestureDetector.SimpleOnGestureListener{
        @Override
        public boolean onDoubleTap(@NonNull MotionEvent e) {
            clearFields();
            return super.onDoubleTap(e);
        }

        @Override
        public boolean onSingleTapConfirmed(@NonNull MotionEvent e) {
            newISBN = randomString.generateNewRandomString(5);
            ISBNOfBook.setText(newISBN.toString());
            return super.onSingleTapConfirmed(e);
        }

        @Override // e1  = coordinates of the initial point; e2 = final coordinates ( constantly changes ); distance X = distance of current position - previous position; distance Y same but for Y
        public boolean onScroll(@NonNull MotionEvent e1, @NonNull MotionEvent e2, float distanceX, float distanceY) {
            price_counter = 0.0;
                        if(Math.abs(distanceY)<40){
                            if(distanceX<0){
                                price_counter+=1.0;
                                price_counter = Double.valueOf(String.valueOf(PriceOfBook.getText()))+price_counter;
                                PriceOfBook.setText(price_counter.toString());
                            }}
                        if(Math.abs(distanceY)<40){
                            if(distanceX>0){
                                price_counter-=1.0;
                                price_counter = Double.valueOf(String.valueOf(PriceOfBook.getText()))+price_counter;
                                PriceOfBook.setText(price_counter.toString());
                            }
                        }
                        if(Math.abs(distanceX)<40){
                            if(distanceY<0 || distanceY>0){TitleOfBook.setText("Untitled");}
                        }
            return super.onScroll(e1, e2, distanceX, distanceY);
        }

        @Override
        public void onLongPress(@NonNull MotionEvent e) {
            SharedPreferences mySP = getSharedPreferences("WEEK3SP",0);

            IDofBook.setText(mySP.getString("BOOK_ID",""));
            TitleOfBook.setText(mySP.getString("BOOK_Title",""));
            ISBNOfBook.setText(mySP.getString("BOOK_ISBN",""));
            DescOfBook.setText(mySP.getString("BOOK_Desc",""));
            AuthorOfBook.setText(mySP.getString("BOOK_Author",""));
            PriceOfBook.setText(String.valueOf(mySP.getFloat("BOOK_Price",0)));
            super.onLongPress(e);
        }

        @Override
        public boolean onFling(@NonNull MotionEvent e1, @NonNull MotionEvent e2, float velocityX, float velocityY) {
            if (velocityX >=1000 || velocityY >= 1000){
                moveTaskToBack(true);}
            return super.onFling(e1, e2, velocityX, velocityY);
        }

    }
    public abstract class RandomString {

        public  String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

        public  String lower = upper.toLowerCase();

        public  String digits = "0123456789";

        public  String alphaNummeric = upper + lower + digits;


        public String generateNewRandomString(int length) {
            char[] buf;
            Random random=new Random();
            if (length < 1) throw new IllegalArgumentException();
            buf = new char[length];
            for (int idx = 0; idx < buf.length; ++idx)
                buf[idx] = alphaNummeric.charAt(random.nextInt(alphaNummeric.length()));
            return new String(buf);
        }}
    class MyDrawerListener implements NavigationView.OnNavigationItemSelectedListener {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            // get the id of the selected item
            int id = item.getItemId();

            if (id == R.id.menuAddBook) {
                addBook();

            }
            else if (id == R.id.menuClearBooks) {
                week6List.clear();
                myRecyclerAdapter.notifyDataSetChanged();

            }
            else if (id == R.id.menuRemoveBook) {
                week6List.remove(week6List.size()-1);
                count--;
                myRecyclerAdapter.notifyDataSetChanged();

            }

            // close the drawer
            drawerLayout.closeDrawers();
            // tell the OS we want this function to be run to allow interception in case there are more important things
            //if interception is available the clicks will not work
            return true;
        }
    }
    //options menu to inflate / hook into toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menuClearAll) {
            clearFields();


        } else if (id == R.id.menuLoadBook) {
            loadBook();

        }
        // tell the OS
        return true;
    }

    public void addBook(){
        Title = TitleOfBook.getText().toString();
        Price = Double.valueOf(PriceOfBook.getText().toString());
        showToast("The price of "+ Title + " is $" + String.format("%.2f", Price) + ".");

        SharedPreferences mySP = getSharedPreferences("WEEK3SP",0);
        SharedPreferences.Editor myEditor = mySP.edit();

        myEditor.putString("BOOK_Title",TitleOfBook.getText().toString());
        myEditor.putString("BOOK_ID",IDofBook.getText().toString());
        myEditor.putString("BOOK_Author",AuthorOfBook.getText().toString());
        myEditor.putString("BOOK_Desc",DescOfBook.getText().toString());
        myEditor.putFloat("BOOK_Price", (float) Double.parseDouble(PriceOfBook.getText().toString()));
        myEditor.putString("BOOK_ISBN",ISBNOfBook.getText().toString());
        myEditor.commit();

        //week 5 show in list view

        Data data =new Data(TitleOfBook.getText().toString(),AuthorOfBook.getText().toString(),DescOfBook.getText().toString(),
                TitleOfBook.getText().toString(),ISBNOfBook.getText().toString(),(float) Double.parseDouble(PriceOfBook.getText().toString()),count);
        week6List.add(data);
        count+=1;
        myRecyclerAdapter.notifyDataSetChanged();

    }
    class MyBroadCastReceiver extends BroadcastReceiver {
        /*
         * This method 'onReceive' will get executed every time class SMSReceive sends a broadcast
         * */
        @Override
        public void onReceive(Context context, Intent intent) {
            /*
             * Retrieve the message from the intent
             * */
            String msg = intent.getStringExtra(SMSReceiver.SMS_MSG_KEY);
            /*
             * String Tokenizer is used to parse the incoming message
             * The protocol is to have the account holder name and account number separate by a semicolon
             * */
            //EditText TitleOfBook,IDofBook,AuthorOfBook,DescOfBook,PriceOfBook,ISBNOfBook;
            StringTokenizer sT = new StringTokenizer(msg, "|");
            String idofbook = sT.nextToken();
            String titleofbook = sT.nextToken();
            String isbnofbook = sT.nextToken();
            String authorofbook = sT.nextToken();
            String descofbook = sT.nextToken();
            Double priceofbook = Double.valueOf(sT.nextToken());


            /*
             * Now, its time to update the UI
             * */
            TitleOfBook.setText(titleofbook);
            IDofBook.setText(idofbook);
            AuthorOfBook.setText(authorofbook);
            DescOfBook.setText(descofbook);
            PriceOfBook.setText(priceofbook.toString());
            ISBNOfBook.setText(isbnofbook);



        }}

    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences mySP = getSharedPreferences("WEEK3SP",0);

        IDofBook.setText(mySP.getString("BOOK_ID",""));
        TitleOfBook.setText(mySP.getString("BOOK_Title",""));
        ISBNOfBook.setText(mySP.getString("BOOK_ISBN",""));
        DescOfBook.setText(mySP.getString("BOOK_Desc",""));
        AuthorOfBook.setText(mySP.getString("BOOK_Author",""));
        PriceOfBook.setText(String.valueOf(mySP.getFloat("BOOK_Price",0)));


    }
    public void loadBook(){
        super.onStart();
        SharedPreferences mySP = getSharedPreferences("WEEK3SP",0);

        IDofBook.setText(mySP.getString("BOOK_ID",""));
        TitleOfBook.setText(mySP.getString("BOOK_Title",""));
        ISBNOfBook.setText(mySP.getString("BOOK_ISBN",""));
        DescOfBook.setText(mySP.getString("BOOK_Desc",""));
        AuthorOfBook.setText(mySP.getString("BOOK_Author",""));
        PriceOfBook.setText(String.valueOf(mySP.getFloat("BOOK_Price",0)));

    }
    public void onISBNAssignClick(View view){ //extra week3
        SharedPreferences mySP = getSharedPreferences("WEEK3SP",0);
        SharedPreferences.Editor myEditor = mySP.edit();

        myEditor.putString("BOOK_ISBN","00112233");
        myEditor.commit();

    }

    private void showToast(String text){
        Toast.makeText(MainActivity.this,text,Toast.LENGTH_SHORT).show();

    }

    public void clearFields(){
        TitleOfBook.setText("");
        IDofBook.setText("");
        AuthorOfBook.setText("");
        DescOfBook.setText("");
        PriceOfBook.setText("0.0");
        ISBNOfBook.setText("");
    }

    //@Override
    //protected void onSaveInstanceState(@NonNull Bundle outState) {
        //super.onSaveInstanceState(outState);
        //outState.putString("TITLE", TitleOfBook.getText().toString());
        //outState.putString("ISBN", ISBNOfBook.getText().toString());
    //}

    //@Override
    //protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        //super.onRestoreInstanceState(savedInstanceState);
        //TitleOfBook.setText(savedInstanceState.getString("TITLE"));
        //ISBNOfBook.setText(savedInstanceState.getString("ISBN"));



}
// week 11

