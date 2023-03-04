package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Button;
import android.widget.SearchView;


import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    List<Button> labels = new ArrayList<Button>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setQueryListeners();
        createCards();
    }

    void createCards(){
        LinearLayout main = findViewById(R.id.rlMain);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );

        String[] tags = {
                "AndroidFP", "Deitel", "Google", "iPhoneFP", "JavaFP",
                "JavaHTP", "Twitter", "Facebook", "Phone", "android",
                "java", "wiki", "dei", "ios", "face", "book"};
        int amount_of_cards = tags.length;

        for (int i = 0; i < amount_of_cards; i++) {
            main.addView(createCard(params,  tags[i]));
        }
    }

    public void setQueryListeners() {
        SearchView search = (SearchView)findViewById(R.id.searchView);

        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            public boolean onQueryTextChange(String newText) {
                String query_string = newText.toLowerCase();

                for (Button element : labels) {
                    if (element.getText().toString().toLowerCase().contains(query_string)) {
                        LinearLayout parent = (LinearLayout)element.getParent();
                        parent.setVisibility(View.VISIBLE);
                    } else {
                        LinearLayout parent = (LinearLayout)element.getParent();
                        parent.setVisibility(View.GONE);
                    }
                }
                return false;
            }
        });
    }

    LinearLayout createCard(LinearLayout.LayoutParams params, String str) {
        LinearLayout card = new LinearLayout(this);
        card.setLayoutParams(params);
        card.setOrientation(LinearLayout.HORIZONTAL);
        card.setPadding(0, 0, 0, 10);

        final float scale = this.getResources().getDisplayMetrics().density;
        int label_width = (int) (220 * scale + 0.5f);
        int label_height = (int) (50 * scale + 0.5f);

        Button label = new Button(this);
        label.setLayoutParams(new LinearLayout.LayoutParams(label_width, label_height));
        label.setClickable(false);
        label.setAllCaps(false);
        label.setText(str);
        card.addView(label);
        labels.add(label);

        int edit_width = (int) (90 * scale + 0.5f);
        int edit_height = (int) (50 * scale + 0.5f);

        LinearLayout.LayoutParams edit_layout = new LinearLayout.LayoutParams(edit_width, edit_height);
        edit_layout.leftMargin = 40;

        Button edit = new Button(this);
        edit.setLayoutParams(edit_layout);
        edit.setClickable(true);
        edit.setText("Edit");
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(label);
            }
        });
        card.addView(edit);
        return card;
    }

    private void showDialog(Button label) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter the new name of the tag");
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_view, null);

        final EditText edit_dialog = (EditText) view.findViewById(R.id.edit_dialog);
        edit_dialog.setText(label.getText());

        builder.setView(view);
        builder.setNegativeButton("cancel",null);
        builder.setPositiveButton("confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                label.setText(edit_dialog.getText().toString());
            }
        });
        builder.show();
    }

    public void onSaveButtonClicked(View view) {
        TextInputEditText text = (TextInputEditText)findViewById(R.id.new_tag);
        String txt = text.getText().toString();
        if (txt.length() == 0) return;

        LinearLayout main = findViewById(R.id.rlMain);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );

        main.addView(createCard(params, txt), 0);
        text.setText("");
    }

    public void onClearTagsClicked(View view) {
        LinearLayout main = findViewById(R.id.rlMain);

        for (Button element : labels) {
            View parent = (View)element.getParent();
            main.removeView(parent);
        }
    }
}