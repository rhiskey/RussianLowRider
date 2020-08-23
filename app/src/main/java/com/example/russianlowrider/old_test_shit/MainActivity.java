package com.example.russianlowrider;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener {
    public static boolean isLeftPressed = false; // нажата левая кнопка
    public static boolean isRightPressed = false; // нажата правая кнопка
    View.OnClickListener viewClickLeftButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //Поднимаем зад пневму
        }
    };
    View.OnClickListener viewClickRightButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //Поднимаем перед пневму

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set fullscreen
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Set No Title
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        // Наше игровое пространство
        //this.setContentView(new GameSurface(this));
        //this.addContentView(new GameSurface(this),new LinearLayout.LayoutParams( LinearLayout.LayoutParams.MATCH_PARENT , LinearLayout.LayoutParams.MATCH_PARENT ) );

//        this.setContentView(R.layout.activity_main);
        //this.addContentView(R.layout.activity_main, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT , LinearLayout.LayoutParams.MATCH_PARENT ));

//        Button buttonLeft = findViewById(R.id.leftButton);
//        Button buttonRight = findViewById(R.id.rightButton);
//
//
//        buttonLeft.setOnClickListener(viewClickLeftButtonListener);
//        buttonRight.setOnClickListener(viewClickRightButtonListener);


        setContentView(R.layout.activity_main);

        GameSurface gameView = new GameSurface(this); // создаём gameView

        LinearLayout gameLayout = (LinearLayout) findViewById(R.id.gameLayout); // находим gameLayout
        gameLayout.addView(gameView); // и добавляем в него gameView

        Button leftButton = (Button) findViewById(R.id.leftButton); // находим кнопки
        Button rightButton = (Button) findViewById(R.id.rightButton);

        leftButton.setOnTouchListener(this); // и добавляем этот класс как слушателя (при нажатии сработает onTouch)
        rightButton.setOnTouchListener(this);

    }

    // This callback is called only when there is a saved instance that is previously saved by using
// onSaveInstanceState(). We restore some state in onCreate(), while we can optionally restore
// other state here, possibly usable after onStart() has completed.
// The savedInstanceState Bundle is same as the one used in onCreate().
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        //textView.setText(savedInstanceState.getString(TEXT_VIEW_KEY));
    }

    // invoked when the activity may be temporarily destroyed, save the instance state here
    @Override
    public void onSaveInstanceState(Bundle outState) {
/*        outState.putString(GAME_STATE_KEY, gameState);
        outState.putString(TEXT_VIEW_KEY, textView.getText());*/

        // call superclass to save any view hierarchy
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onStop() {
        // call the superclass method first
        super.onStop();

/*        // save the note's current draft, because the activity is stopping
        // and we want to be sure the current note progress isn't lost.
        ContentValues values = new ContentValues();
        values.put(NotePad.Notes.COLUMN_NAME_NOTE, getCurrentNoteText());
        values.put(NotePad.Notes.COLUMN_NAME_TITLE, getCurrentNoteTitle());

        // do this update in background on an AsyncQueryHandler or equivalent
        asyncQueryHandler.startUpdate (
                mToken,  // int token to correlate calls
                null,    // cookie, not used here
                uri,    // The URI for the note to update.
                values,  // The map of column names and new values to apply to them.
                null,    // No SELECT criteria are used.
                null     // No WHERE columns are used.
        );*/
    }

    @Override
    public void onBackPressed() {
        // Открыть всплывающее меню


        //this.addContentView(R.layout.activity_main, null);

        //this.setContentView(R.layout.activity_main);
//        Button backBtn = findViewById(R.id.leftButton);
//        backBtn.setText("Зад");
//        Button frontBtn = findViewById(R.id.rightButton);
//        frontBtn.setText("Перед");

        //this.setContentView(new GameSurface(this));
        return;
    }

    private void showPopupMenu(View v) {
        PopupMenu popupMenu = new PopupMenu(this, v);
        popupMenu.inflate(R.menu.popupmenu);

        popupMenu
                .setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.menu1:
                                Toast.makeText(getApplicationContext(),
                                        "Вы выбрали PopupMenu 1",
                                        Toast.LENGTH_SHORT).show();
                                return true;
                            case R.id.menu2:
                                Toast.makeText(getApplicationContext(),
                                        "Вы выбрали PopupMenu 2",
                                        Toast.LENGTH_SHORT).show();
                                return true;
                            case R.id.menu3:
                                Toast.makeText(getApplicationContext(),
                                        "Вы выбрали PopupMenu 3",
                                        Toast.LENGTH_SHORT).show();
                                return true;
                            default:
                                return false;
                        }
                    }
                });

        popupMenu.setOnDismissListener(new PopupMenu.OnDismissListener() {
            @Override
            public void onDismiss(PopupMenu menu) {
                Toast.makeText(getApplicationContext(), "onDismiss",
                        Toast.LENGTH_SHORT).show();
            }
        });
        popupMenu.show();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (v.getId()) { // определяем какая кнопка
            case R.id.leftButton:
                switch (event.getAction()) { // определяем нажата или отпущена
                    case MotionEvent.ACTION_DOWN:
                        isLeftPressed = true;
                        break;
                    case MotionEvent.ACTION_UP:
                        isLeftPressed = false;
                        break;
                }
                break;
            case R.id.rightButton:
                switch (event.getAction()) { // определяем нажата или отпущена
                    case MotionEvent.ACTION_DOWN:
                        isRightPressed = true;
                        break;
                    case MotionEvent.ACTION_UP:
                        isRightPressed = false;
                        break;
                }
                break;
        }
        return true;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}