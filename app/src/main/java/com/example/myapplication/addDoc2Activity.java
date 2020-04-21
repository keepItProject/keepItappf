package com.example.myapplication;


        import androidx.annotation.NonNull;
        import androidx.annotation.RequiresApi;
        import androidx.appcompat.app.AppCompatActivity;

        import android.content.Intent;
        import android.graphics.Point;
        import android.graphics.Rect;
        import android.net.Uri;
        import android.os.Build;
        import android.os.Bundle;
        import android.provider.MediaStore;
        import android.util.Log;
        import android.view.View;
        import android.widget.CheckBox;
        import android.widget.ImageView;
        import android.widget.Spinner;
        import android.widget.TextView;

        import com.example.myapplication.Adaptors.CategoryAdapter;
        import com.example.myapplication.Data.Category;
        import com.google.android.gms.tasks.Task;
        import com.google.firebase.database.DataSnapshot;
        import com.google.firebase.database.DatabaseError;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;
        import com.google.firebase.database.ValueEventListener;
        import com.google.firebase.ml.vision.FirebaseVision;
        import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcode;
        import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcodeDetector;
        import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcodeDetectorOptions;
        import com.google.firebase.ml.vision.common.FirebaseVisionImage;
        import com.google.firebase.ml.vision.text.FirebaseVisionCloudTextRecognizerOptions;
        import com.google.firebase.ml.vision.text.FirebaseVisionText;
        import com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer;
        import com.theartofdev.edmodo.cropper.CropImage;
        import com.theartofdev.edmodo.cropper.CropImageView;


        import java.io.IOException;
        import java.time.LocalDate;
        import java.time.format.DateTimeFormatter;
        import java.util.ArrayList;
        import java.util.Arrays;
        import java.util.Date;
        import java.util.List;
        import java.util.Locale;
        import java.util.Objects;
        import java.util.concurrent.TimeUnit;
        import java.util.regex.Pattern;

public class addDoc2Activity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private ImageView receipt;
    private ArrayList<Float> prices;
    private TextView name, date, phone, email, invoiceNumber;
    private ArrayList<Category> categories = new ArrayList<>();
    private CheckBox mCheckBox;
    private Spinner mSpinner;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Init Views
        receipt = findViewById(R.id.receipt);
        name = findViewById(R.id.name);
        date = findViewById(R.id.date);
        phone = findViewById(R.id.phone);
        email = findViewById(R.id.email);
        invoiceNumber = findViewById(R.id.number);
        mCheckBox = (CheckBox) findViewById(R.id.checkBox);
        mSpinner = (Spinner) findViewById(R.id.spinner4);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("category");
        loadAllCategories(myRef);



        // hold data from another activity
        Uri myUri = Uri.parse(Objects.requireNonNull(getIntent().getExtras()).getString("imageUri"));
        extractTextFromImage(myUri);
    }
    private void handleAlarm(int id, Date date){
        if(mCheckBox.isChecked()){
            String periodChoiceTxt = (String)mSpinner.getSelectedItem();
            long offset = 0;
            if(getString(R.string.before_week).equalsIgnoreCase(periodChoiceTxt)){
                offset = TimeUnit.DAYS.toMillis(7);
            }else if (getString(R.string.before_month).equalsIgnoreCase(periodChoiceTxt)){
                offset = TimeUnit.DAYS.toMillis(30);
            }


            date.setTime(date.getTime() - offset);
            setAlarmAt(id, date);

        }
    }

    private void setAlarmAt(int id, Date date){
        Alarm.setAlarm(this, id, date.getTime());
    }

    private void loadAllCategories(DatabaseReference myRef) {
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Category category = ds.getValue(Category.class);
                    categories.add(category);
                }
                //get the spinner from the xml.
                Spinner dropdown = findViewById(R.id.spinner);
                CategoryAdapter adapter = new CategoryAdapter(addDoc2Activity.this,
                        categories);
                dropdown.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }


   /* // call from XML file
    public void takePhoto(View view) {
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .start(this);
    }
*/

    // @Override
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void extractTextFromImage(Uri resultUri) {
        //super.onActivityResult(requestCode, resultCode, data);
        if (resultUri != null) {
            // return Image from crop
            //CropImage.ActivityResult result = CropImage.getActivityResult(data);
            // if (resultCode == RESULT_OK) {
            // Uri resultUri = result.getUri();
            FirebaseVisionImage image = null;
            try {
                image = FirebaseVisionImage.fromBitmap(MediaStore.Images.Media.getBitmap(
                        this.getContentResolver(),
                        resultUri
                ));
                receipt.setImageBitmap(MediaStore.Images.Media.getBitmap(
                        this.getContentResolver(),
                        resultUri));

            } catch (IOException e) {
                e.printStackTrace();
            }
            //Firebase Init
            InitFirebase initFirebase = new InitFirebase().invoke();
            FirebaseVisionBarcodeDetector detector = initFirebase.getDetector();
            FirebaseVisionTextRecognizer textRecognizer = initFirebase.getTextRecognizer();
            assert image != null;
            //Barcode Number
            Task<List<FirebaseVisionBarcode>> barCodeNumber = detector.detectInImage(image)
                    .addOnSuccessListener(barcodes -> {
                        Log.d("BarCode Number", barcodes.toString());
                        for (FirebaseVisionBarcode barcode : barcodes) {
                            Rect bounds = barcode.getBoundingBox();
                            Point[] corners = barcode.getCornerPoints();

                            String rawValue = barcode.getRawValue();
                            invoiceNumber.setText(rawValue);
                            Log.w("rawValue", rawValue + "");

                            int valueType = barcode.getValueType();
                            Log.w("valueType", valueType + "");


                        }

                    })
                    .addOnFailureListener(e -> Log.d("BarCode Number", e.getMessage()));
            //OCR text
            textRecognizer.processImage(image)
                    .addOnSuccessListener(result1 -> {
                        boolean getName = false;
                        prices = new ArrayList<Float>();
                        String resultText = result1.getText();
                        // all Text in receipt
                        for (FirebaseVisionText.TextBlock block : result1.getTextBlocks()) {
                            String blockText = block.getText();
                            // check for date
                            if (date.getText().length() == 0)
                                date.setText(dateChecker(blockText) ? changeDateFormat(blockText) : "");
                            // check for phone number
                            if (phone.getText().length() == 0)
                                phone.setText(mobileNumber(blockText));
                            // loop in all lines in Text
                            for (FirebaseVisionText.Line line : block.getLines()) {
                                String lineText = line.getText();
                                //Log.d("lineText ", lineText);
                                if (!getName) {
                                    name.setText(lineText);
                                    getName = true;
                                }
                                if (phone.getText().length() == 0)
                                    phone.setText(mobileNumber(lineText));
                                if (date.getText().length() == 0)
                                    date.setText(dateChecker(lineText) ? lineText : "");
                                if (email.getText().length() == 0)
                                    email.setText(companyWebsite(lineText));
                                /// loop in all words in one line
                                for (FirebaseVisionText.Element element : line.getElements()) {
                                    String elementText = element.getText();
                                    if (phone.getText().length() == 0)
                                        phone.setText(mobileNumber(elementText));
                                    if (date.getText().length() == 0)
                                        date.setText(dateChecker(elementText) ? changeDateFormat(elementText) : "");
                                }
                            }
                        }

                    })
                    .addOnFailureListener(
                            e -> Log.w(TAG, Objects.requireNonNull(e.getMessage())));
        }/* else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }*/
    }


    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }


    private static Integer returnInvoiceNumber(ArrayList<Integer> invoiceNumbers) {
        for (Integer number :
                invoiceNumbers) {
            if (mobileNumber(number.toString()).length() != 0) {
                return number;
            }

        }
        return 0;
    }

    // saudi arabia number
    private static String mobileNumber(String text) {
        final String regex = "^(009665|9665|\\+9665|05|5)(5|0|3|6|4|9|1|8|7)([0-9]{7})$";
        final String regex1 = "^((?:[+?0?0?966]+)(?:\\s?\\d{2})(?:\\s?\\d{7}))$";
        if (Pattern.compile(regex + "|" + regex1).matcher(text.replaceAll(" ", "")).matches()) {
            return text;
        }
        return "";
    }

    // website & email
    private static String companyWebsite(String text) {
        final String regex1 = "^((?!\\.)[\\w-_.]*[^.])(@\\w+)(\\.\\w+(\\.\\w+)?[^.\\W])$";
        final String regex = "^(http\\:\\/\\/|https\\:\\/\\/)?([a-z0-9][a-z0-9\\-]*\\.)+[a-z0-9][a-z0-9\\-]*$";
        if (Pattern.compile(regex + "|" + regex1).matcher(text.replaceAll(" ", "")).matches()) {
            return text;
        }
        return "";
    }

    // extract date from text
    @RequiresApi(api = Build.VERSION_CODES.O)
    private boolean dateChecker(String text) {
        DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("[yyyy-MM-dd][MM/dd/yy][MMMM dd, yyyy][yyyy/MM/dd][dd-MMM-yyyy]", Locale.ENGLISH);
        try {
            LocalDate.parse(text, DATE_FORMATTER);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // extract date from text
    @RequiresApi(api = Build.VERSION_CODES.O)
    private String changeDateFormat(String text) {
        //Create formatter
        DateTimeFormatter FOMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        //Local date instance
        DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("[yyyy-MM-dd][MM/dd/yy][MMMM dd, yyyy][yyyy/MM/dd][dd-MMM-yyyy]", Locale.ENGLISH);
        //Get formatted String
        String dateString = FOMATTER.format(LocalDate.parse(text, DATE_FORMATTER));
        System.out.println(dateString);        //07/15/2018
        return dateString;
    }


    private static class InitFirebase {
        private FirebaseVisionBarcodeDetector detector;
        private FirebaseVisionTextRecognizer textRecognizer;

        public FirebaseVisionBarcodeDetector getDetector() {
            return detector;
        }

        public FirebaseVisionTextRecognizer getTextRecognizer() {
            return textRecognizer;
        }

        public InitFirebase invoke() {
            FirebaseVisionBarcodeDetectorOptions barcodeDetectorOptions =
                    new FirebaseVisionBarcodeDetectorOptions.Builder()
                            .build();
            detector = FirebaseVision.getInstance()
                    .getVisionBarcodeDetector();
            FirebaseVisionCloudTextRecognizerOptions options =
                    new FirebaseVisionCloudTextRecognizerOptions.Builder()
                            .setLanguageHints(Arrays.asList("en", "ar"))
                            .build();
            textRecognizer = FirebaseVision.getInstance()
                    .getCloudTextRecognizer(options);
            return this;
        }
    }
}
