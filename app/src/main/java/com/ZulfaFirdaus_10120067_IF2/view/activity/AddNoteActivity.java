package com.ZulfaFirdaus_10120067_IF2.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ZulfaFirdaus_10120067_IF2.R;
import com.ZulfaFirdaus_10120067_IF2.databinding.ActivityAddNoteBinding;
import com.ZulfaFirdaus_10120067_IF2.model.Note;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * NAMA    : Zulfa Firdaus
 * NIM     : 10120067
 * Kelas   : IF-2
 * MataKuliah  : Aplikasi Komputasi Bergerak
 * Tugas Pengganti UTS Aplikasi Komputasi Bergerak
 */
public class AddNoteActivity extends AppCompatActivity {

    private ActivityAddNoteBinding binding;
    private FirebaseAuth auth;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddNoteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("Note");

        setupUser();

        binding.buttonAdd.setOnClickListener(v -> {
            addDailyNoteToDatabase();
        });

    }

    private void addDailyNoteToDatabase() {
        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser != null) {
            String title = binding.title.getText().toString().trim();
            String category = binding.category.getText().toString().trim();
            String content = binding.txtDesc.getText().toString().trim();

            if (TextUtils.isEmpty(title)) {
                Toast.makeText(this, "Judul Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
            } else if (TextUtils.isEmpty(category)) {
                Toast.makeText(this, "Kategori Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
            } else if (TextUtils.isEmpty(content)) {
                Toast.makeText(this, "Isi Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
            } else {
                String userId = currentUser.getUid(); // Get the user's unique identifier (UID)
                DatabaseReference userNotesRef = databaseReference.child(userId); // Reference to the user's notes

                Note notes = new Note(getCurrentDate(), title, category, content);

                userNotesRef.push().setValue(notes, (error, ref) -> {
                    if (error != null) {
                        Toast.makeText(this, "Gagal upload" + error.getMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Berhasil upload", Toast.LENGTH_SHORT).show();

                        finish(); // Finish the AddActivity to prevent it from appearing in the back stack
                    }
                });
            }
        } else {
            // Handle the case when currentUser is null (user is not logged in)
        }
    }

    private void setupUser() {
        auth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = auth.getCurrentUser();

        if (currentUser == null) {
            startActivity(new Intent(this, LoginActivity.class));
            this.finish();
        }
    }

    private String getCurrentDate() {
        // Create a Calendar instance and get the current date
        Calendar calendar = Calendar.getInstance();
        Date currentDate = calendar.getTime();

        // Format the date using SimpleDateFormat
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String formattedDate = dateFormat.format(currentDate);

        return formattedDate;
    }
}

/**
 * NAMA    : Zulfa Firdaus
 * NIM     : 10120067
 * Kelas   : IF-2
 * MataKuliah : Aplikasi Komputasi Bergerak
 * Tugas Pengganti UTS Aplikasi Komputasi Bergerak
 */
