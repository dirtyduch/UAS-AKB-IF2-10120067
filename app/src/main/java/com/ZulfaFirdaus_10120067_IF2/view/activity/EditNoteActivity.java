package com.ZulfaFirdaus_10120067_IF2.view.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.ZulfaFirdaus_10120067_IF2.R;
import com.ZulfaFirdaus_10120067_IF2.databinding.ActivityEditNoteBinding;
import com.ZulfaFirdaus_10120067_IF2.model.Note;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class EditNoteActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private DatabaseReference databaseReference;

    private ActivityEditNoteBinding binding;
    private String noteKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditNoteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Initialize FirebaseAuth instance
        auth = FirebaseAuth.getInstance();

        // Retrieve the DailyNote object and its key from the Intent extras
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("daily_note")) {
            Note dailyNote = intent.getParcelableExtra("daily_note");
            noteKey = intent.getStringExtra("note_key");

            // Now you can use the dailyNote object to populate the detail activity
            binding.title.setText(dailyNote.getTitle());
            binding.category.setText(dailyNote.getCategory());
            binding.txtDesc.setText(dailyNote.getDesc());
        }

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("Note");

        setupUser();

        binding.buttonAdd.setOnClickListener(v -> updateData());

        binding.buttonDelete.setOnClickListener(v -> deleteData());
    }

    private void updateData() {
        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser != null && noteKey != null) {
            String userId = currentUser.getUid();
            DatabaseReference userNotesRef = databaseReference.child(userId);

            String newTitle = binding.title.getText().toString();
            String newCategory = binding.category.getText().toString();
            String newContent = binding.txtDesc.getText().toString();

            Note notes = new Note(getCurrentDate(),newTitle, newCategory, newContent);

            // Update the specific item using the noteKey
            userNotesRef.child(noteKey).setValue(notes, (error, ref) -> {
                if (error != null) {
                    Toast.makeText(this, "Gagal update" + error.getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "berhasil update", Toast.LENGTH_SHORT).show();

                    finish(); // Finish the AddActivity to prevent it from appearing in the back stack
                }
            });
        } else {
            // Handle the case when currentUser is null or noteKey is null
        }
    }

    private void deleteData() {
        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser != null && noteKey != null) {
            String userId = currentUser.getUid();
            DatabaseReference userNotesRef = databaseReference.child(userId);

            // Delete the specific item using the noteKey
            userNotesRef.child(noteKey).removeValue()
                    .addOnSuccessListener(aVoid -> {
                        // Data successfully deleted
                        Toast.makeText(EditNoteActivity.this, "Data deleted successfully", Toast.LENGTH_SHORT).show();
                        finish(); // Close the DetailActivity after deleting the item
                    })
                    .addOnFailureListener(e -> {
                        // Error occurred while deleting data
                        Toast.makeText(EditNoteActivity.this, "Failed to delete data: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        } else {
            // Handle the case when currentUser is null or noteKey is null
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