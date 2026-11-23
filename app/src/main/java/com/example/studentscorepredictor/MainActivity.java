package com.example.studentscorepredictor;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.tensorflow.lite.Interpreter;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class MainActivity extends Activity {
    private Interpreter tflite;
    private float[] means = new float[3];
    private float[] scales = new float[3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText studyTime = findViewById(R.id.studyTime);
        EditText absences = findViewById(R.id.absences);
        EditText previousGrade = findViewById(R.id.previousGrade);
        Button predictButton = findViewById(R.id.predictButton);
        TextView result = findViewById(R.id.result);

        try {
            tflite = new Interpreter(loadModelFile());
            loadScalerConfig();
        } catch (Exception e) {
            Toast.makeText(this, "Model files missing. Run the Colab training notebook first.", Toast.LENGTH_LONG).show();
            predictButton.setEnabled(false);
        }

        predictButton.setOnClickListener(v -> {
            try {
                float study = Float.parseFloat(studyTime.getText().toString().trim());
                float absent = Float.parseFloat(absences.getText().toString().trim());
                float g2 = Float.parseFloat(previousGrade.getText().toString().trim());

                if (study < 1 || study > 4 || absent < 0 || g2 < 0 || g2 > 20) {
                    result.setText("Use study time 1-4, absences 0 or more, and G2 from 0-20.");
                    return;
                }

                float[][] input = new float[1][3];
                float[] raw = {study, absent, g2};
                for (int i = 0; i < 3; i++) input[0][i] = (raw[i] - means[i]) / scales[i];

                float[][] output = new float[1][1];
                tflite.run(input, output);
                float predicted = Math.max(0f, Math.min(20f, output[0][0]));
                result.setText(String.format("Predicted final grade (G3): %.1f / 20", predicted));
            } catch (NumberFormatException e) {
                result.setText("Please enter valid numbers in all three fields.");
            } catch (Exception e) {
                result.setText("Prediction error: " + e.getMessage());
            }
        });
    }

    private MappedByteBuffer loadModelFile() throws Exception {
        try (InputStream is = getAssets().open("student_score_model.tflite")) {
            java.io.File temp = new java.io.File(getCacheDir(), "student_score_model.tflite");
            try (java.io.FileOutputStream fos = new java.io.FileOutputStream(temp)) {
                byte[] buffer = new byte[4096];
                int read;
                while ((read = is.read(buffer)) != -1) fos.write(buffer, 0, read);
            }
            try (FileInputStream input = new FileInputStream(temp);
                 FileChannel channel = input.getChannel()) {
                return channel.map(FileChannel.MapMode.READ_ONLY, 0, channel.size());
            }
        }
    }

    private void loadScalerConfig() throws Exception {
        StringBuilder json = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(getAssets().open("scaler_config.json")))) {
            String line;
            while ((line = reader.readLine()) != null) json.append(line);
        }
        JSONObject obj = new JSONObject(json.toString());
        for (int i = 0; i < 3; i++) {
            means[i] = (float) obj.getJSONArray("mean").getDouble(i);
            scales[i] = (float) obj.getJSONArray("scale").getDouble(i);
        }
    }

    @Override
    protected void onDestroy() {
        if (tflite != null) tflite.close();
        super.onDestroy();
    }
}
