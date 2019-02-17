package com.example.ekreasi.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.andreabaccega.formedittextvalidator.EmailValidator;
import com.andreabaccega.formedittextvalidator.OrValidator;
import com.andreabaccega.widget.FormEditText;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.ekreasi.R;
import com.example.ekreasi.databinding.ActivityAddArticleBinding;
import com.example.ekreasi.utils.Utility;
import com.example.ekreasi.view.AddArticleView;
import com.example.ekreasi.viewmodel.AddArticleViewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddArticleActivity extends AppCompatActivity {

    @BindView(R.id.input1)
    TextView input1;

    @BindView(R.id.edit_title)
    EditText editTitle;

    @BindView(R.id.input3)
    TextView input3;
    @BindView(R.id.edit_content)
    EditText editContent;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.gambar)
    ImageView gambar;
    @BindView(R.id.phone)
    EditText phone;

    @BindView(R.id.spin_category_id)
    Spinner spinCategoryId;


    private int REQUEST_CAMERA = 0, SELECT_FILE = 1, patokan;
    private String userChoosenTask;
    Bitmap bitmap;



    ImageView img;
    EditText tanggal;
    Spinner sp, sc;


    String cat_id;

    final Calendar myCalendar = Calendar.getInstance();


    private AddArticleViewModel addArticleViewModel;
    private ActivityAddArticleBinding addArticleBinding;

    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);


        addArticleBinding = DataBindingUtil.setContentView(this, R.layout.activity_add_article);
        addArticleViewModel = new AddArticleViewModel(this);
        addArticleBinding.setAddarticleview(addArticleViewModel);

        cat_id = "";



        FormEditText editName = (FormEditText) findViewById(R.id.edit_name);
        editName.addValidator(
                new OrValidator(
                        "This Email is Invalid",
                        new EmailValidator(null) // same here for null
                )
        );


        img = (ImageView) findViewById(R.id.gambar);
        tanggal = (EditText) findViewById(R.id.tanggal);
        sc = (Spinner) findViewById(R.id.spin_category);
        sp = (Spinner) findViewById(R.id.spin_category_id);

        tanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(AddArticleActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                patokan = 1;
                selectImage();
                Toast.makeText(AddArticleActivity.this, String.valueOf(patokan), Toast.LENGTH_LONG).show();
            }
        });

        CallSpinner();

        addArticleBinding.setPresenter(new AddArticleView() {

            @Override
            public void SaveData() {
                FormEditText editName = (FormEditText) findViewById(R.id.edit_name);
                if (editName.testValidity()) {

                }

                if (patokan == 1) {
                    String images = getStringImage(bitmap);
                    final String username = addArticleBinding.editTitle.getText().toString();
                    final String title = addArticleBinding.editName.getText().toString();
                    final String category = cat_id;
                    final String content = addArticleBinding.editContent.getText().toString();
                    final String phone = addArticleBinding.phone.getText().toString();
                    final String image = images;
                    final String tanggal = addArticleBinding.tanggal.getText().toString();

                    if (patokan == 0 && addArticleBinding.editName.getText().toString().equals("") || addArticleBinding.editTitle.getText().toString().equals("")
                            || addArticleBinding.editContent.getText().toString().equals("") || addArticleBinding.tanggal.getText().toString().equals("") || addArticleBinding.phone.getText().toString().equals("")) {

                        Toast.makeText(AddArticleActivity.this, "Isi Data yang Lengkap", Toast.LENGTH_SHORT).show();
                    } else if (addArticleBinding.phone.getText().toString().length() < 12) {
                        Toast.makeText(AddArticleActivity.this, "Phone Number is wrong", Toast.LENGTH_SHORT).show();
                    } else {
                        addArticleViewModel.sendSaveRequest(username, title, category, content, image, tanggal, phone);
                        finish();
                    }

                } else if (patokan == 0) {
                    Toast.makeText(AddArticleActivity.this, "Isi Data nya Lengkap", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void DisplayImage() {
                selectImage();
            }

        });
    }

    public void CallSpinner() {
        AndroidNetworking.post("http://fazilmuammar007.com/blogapp/list_category.php")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response

                        try {

                            ArrayList<String> category_id = new ArrayList<String>();
                            ArrayList<String> name = new ArrayList<String>();
                            category_id.add("");
                            name.add("Pilih Kategori");

                            JSONArray jsonArray = response.optJSONArray("result");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);

                                category_id.add(jsonObject.optString("category_id"));
                                name.add(jsonObject.optString("name"));
                            }


                            ArrayAdapter arrayAdapter;
                            arrayAdapter = new ArrayAdapter<String>(AddArticleActivity.this, R.layout.list_spinner, category_id);
                            sp.setAdapter(arrayAdapter);
                            arrayAdapter = new ArrayAdapter<String>(AddArticleActivity.this, R.layout.list_spinner, name);
                            sc.setAdapter(arrayAdapter);

                            // mengambil id kategori ketika mengklik spinner
                            sc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    String idd = sp.getItemAtPosition(position).toString();
                                    cat_id = sp.getItemAtPosition(position).toString();
                                    Log.d("category_id", idd);
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });


                        } catch (JSONException e) {

                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
                    }
                });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case Utility.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (userChoosenTask.equals("Take Photo"))
                        cameraIntent();
                    else if (userChoosenTask.equals("Choose from Library"))
                        galleryIntent();
                } else {

                }
                break;
        }
    }

    private void selectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Library",
                "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(AddArticleActivity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result = Utility.checkPermission(AddArticleActivity.this);

                if (items[item].equals("Take Photo")) {
                    patokan = 1;
                    userChoosenTask = "Take Photo";
                    if (result)
                        cameraIntent();

                } else if (items[item].equals("Choose from Library")) {
                    patokan = 1;
                    userChoosenTask = "Choose from Library";
                    if (result)
                        galleryIntent();

                } else if (items[item].equals("Cancel")) {
                    patokan = 0;
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void galleryIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);
    }

    private void cameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            Uri filePath = data.getData();
            try {
                //Getting the Bitmap from Gallery
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                //Setting the Bitmap to ImageView
                img.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);

            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }


    }

    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 15, baos);

        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }


    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");

        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        img.setImageBitmap(thumbnail);


    }

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {

        Bitmap bm = null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        img.setImageBitmap(bm);
    }


    private void updateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        tanggal.setText(sdf.format(myCalendar.getTime()));
    }


}
