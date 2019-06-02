package com.example.loginapp.Activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.features.ReturnMode;
import com.example.loginapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.jackandphantom.circularimageview.RoundedImage;
import com.yalantis.ucrop.UCrop;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.loginapp.Activities.BaseApp.storageRef;

public class AddLessonActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    private ProgressDialog progressDialog;

    @BindView(R.id.edtChinese_conversation)
    EditText edtChinese_conversation;
    @BindView(R.id.edtChinese_conv_pinyin)
    EditText edtChinese_conv_pinyin;
    @BindView(R.id.edtlesson)
    EditText edtLessonName;
    @BindView(R.id.edtChinesewords)
    EditText edtChinesewords;
    @BindView(R.id.edtenglishwords)
    EditText edtenglishwords;
    @BindView(R.id.edtenglishwords1)
    EditText edtenglishwords1;
    @BindView(R.id.edtquestion)
    EditText edtquestion;
    @BindView(R.id.edtCorrect_answers)
    EditText edtCorrect_answers;
    @BindView(R.id.edtWrong_answers1)
    EditText edtWrong_answers1;
    @BindView(R.id.edtWrong_answers2)
    EditText edtWrong_answers2;
    @BindView(R.id.edtWrong_answers3)
    EditText edtWrong_answers3;

    @BindView(R.id.button)
    Button button;
    @BindView(R.id.img_lesson)
    RoundedImage img_lesson;
    @BindView(R.id.img_words)
    RoundedImage img_words;
    @BindView(R.id.img_voice_conversation)
    RoundedImage img_voice_conversation;
    @BindView(R.id.img_voice_word)
    RoundedImage img_voice_word;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_lesson);
        ButterKnife.bind(this);
        progressDialog = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        setUI();
    }

    private void setUI() {
        button.setOnClickListener(this);
        img_lesson.setOnClickListener(this);
        img_words.setOnClickListener(this);
        img_voice_conversation.setOnClickListener(this);
        img_voice_word.setOnClickListener(this);
    }

    private void Addlesson(String lesson, String Chinesewords, String englishwords, String Chinese_conversation,
                           String Chinese_conv_pinyin, String englishwords1, String question, String Correct_answers,
                           String wrong1, String wrong2, String wrong3) {
        if (TextUtils.isEmpty(lesson)) {
            Toast.makeText(getApplicationContext(), "Please write something", Toast.LENGTH_SHORT).show();
        } else {

            Map<String, Object> conversation = new HashMap<>();
            conversation.put("chinese_conv_pinyin", Chinese_conv_pinyin);
            conversation.put("chinese_conversation", Chinese_conversation);
            conversation.put("english_conversation", englishwords1);
            conversation.put("conversation_voice", confVoiceUrl);

            Map<String, Object> words = new HashMap<>();
            words.put("chinese_words", Chinesewords);
            words.put("english_words", englishwords);
            words.put("words_image", img_uploaded_url2);
            words.put("words_voice", wordVoiceUrl);

            Map<String, Object> questions = new HashMap<>();
            questions.put("questions", question);
            questions.put("answers", Correct_answers);
            questions.put("wrong1", wrong1);
            questions.put("wrong2", wrong2);
            questions.put("wrong3", wrong3);

            Map<String, Object> mapLesson = new HashMap<>();
            mapLesson.put("lesson_id", "");
            mapLesson.put("lesson_name", lesson);
            mapLesson.put("lesson_image", img_uploaded_url);
            mapLesson.put("conversation", conversation);
            mapLesson.put("words", words);
            mapLesson.put("questions", questions);

            BaseApp.db
                    .collection("lesson")
                    .add(mapLesson)
                    .addOnSuccessListener(documentReference -> BaseApp.db
                            .collection("lesson")
                            .document(documentReference.getId())
                            .update("lesson_id", documentReference.getId())
                            .addOnSuccessListener(aVoid -> {
                                progressDialog.dismiss();
                                finish();
                            }));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                String lesson = edtLessonName.getText().toString();
                String Chinesewords = edtChinesewords.getText().toString();
                String englishwords = edtenglishwords.getText().toString();
                String Chinese_conv_pinyin = edtChinese_conv_pinyin.getText().toString();
                String Chinese_conversation = edtChinese_conversation.getText().toString();
                String englishwords1 = edtenglishwords1.getText().toString();
                String question = edtquestion.getText().toString();
                String Correct_answers = edtCorrect_answers.getText().toString();
                String wrong1 = edtWrong_answers1.getText().toString();
                String wrong2 = edtWrong_answers2.getText().toString();
                String wrong3 = edtWrong_answers3.getText().toString();

                progressDialog.setMessage("Create Lesson...");
                progressDialog.show();
                progressDialog.setCanceledOnTouchOutside(false);
                uploadFoto(lesson, Chinesewords, englishwords, Chinese_conversation, Chinese_conv_pinyin, englishwords1, question, Correct_answers, wrong1, wrong2, wrong3);

                break;
            case R.id.img_lesson:
                ImagePicker.create(AddLessonActivity.this)
                        .returnMode(ReturnMode.ALL) // set whether pick and / or camera action should return immediate result or not.
                        .toolbarFolderTitle("Select Picture") // folder selection title
                        .toolbarImageTitle("Tap to select") // image selection title
                        .toolbarArrowColor(Color.WHITE) // Toolbar 'up' arrow color
                        .single() // single mode
                        .showCamera(true) // show camera or not (true by default)
                        .imageDirectory("Camera") // directory name for captured image  ("Camera" folder by default)
                        .start();
                isImageChangeLesson = true;
                break;
            case R.id.img_words:
                ImagePicker.create(AddLessonActivity.this)
                        .returnMode(ReturnMode.ALL) // set whether pick and / or camera action should return immediate result or not.
                        .toolbarFolderTitle("Select Picture") // folder selection title
                        .toolbarImageTitle("Tap to select") // image selection title
                        .toolbarArrowColor(Color.WHITE) // Toolbar 'up' arrow color
                        .single() // single mode
                        .showCamera(true) // show camera or not (true by default)
                        .imageDirectory("Camera") // directory name for captured image  ("Camera" folder by default)
                        .start();
                isImageChangeWords = true;
                break;
            case R.id.img_voice_word:
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("audio/mpeg");
                startActivityForResult(intent, 1);
                wordVoiceUploaded = true;
                break;
            case R.id.img_voice_conversation:
                Intent intent2 = new Intent(Intent.ACTION_GET_CONTENT);
                intent2.setType("audio/mpeg");
                startActivityForResult(intent2, 2);
                confVoiceUploaded = true;
                break;
        }
    }

    private boolean isImageChangeLesson = false;
    private boolean isImageChangeWords = false;
    private String img_uploaded_url = "";
    private String img_uploaded_url2 = "";
    private boolean wordVoiceUploaded = false;
    private String wordVoiceUrl = "";
    private boolean confVoiceUploaded = false;
    private String confVoiceUrl = "";


    private void uploadVoiceWords(byte[] data, String filename) {
        StorageMetadata metadata = new StorageMetadata.Builder()
                .setContentType("audio/mpeg")
                .build();

        StorageReference imagesRef = storageRef.child("lesson/words/"+filename+".mp4");
        UploadTask uploadTask = imagesRef.putBytes(data, metadata);
        // Observe state change events such as progress, pause, and resume
        uploadTask.addOnProgressListener(taskSnapshot -> {
            double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
            Toast.makeText(this, "Upload is " + progress + "% done", Toast.LENGTH_SHORT).show();
        }).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                UploadTask.TaskSnapshot downloadUri = task.getResult();
                if(downloadUri == null){
                    return;
                }
                wordVoiceUrl = downloadUri.toString();
            } else {
                Toast.makeText(this, "Gagal bro", Toast.LENGTH_SHORT).show();
            }
        });
        /*uploadTask.addOnProgressListener(taskSnapshot -> {
            //int progress = (100 * (int)taskSnapshot.getBytesTransferred()) / (int)taskSnapshot.getTotalByteCount();
        }).continueWithTask(task -> {
            if (!task.isSuccessful()) {
                throw task.getException();
            }

            return imagesRef.getDownloadUrl();
        }).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Uri downloadUri = task.getResult();
                if (downloadUri == null)
                    return;

                wordVoiceUrl = downloadUri.toString();
                wordVoiceUploaded = false;
            }
        });*/
    }

    private void uploadConversationVoice(byte[] data, String filename) {
        StorageMetadata metadata = new StorageMetadata.Builder()
                .setContentType("audio/·mp3")
                .build();

        StorageReference imagesRef = storageRef.child("lesson/conversation/"+filename+".mp3");
        UploadTask uploadTask = imagesRef.putBytes(data, metadata);
        uploadTask.addOnProgressListener(taskSnapshot -> {
            double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
            Toast.makeText(this, "Upload is " + progress + "% done", Toast.LENGTH_SHORT).show();
        }).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                UploadTask.TaskSnapshot downloadUri = task.getResult();
                if(downloadUri == null){
                    return;
                }
                confVoiceUrl = downloadUri.toString();
            } else {
                Toast.makeText(this, "Gagal bro", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP) {
            final Uri resultUri = UCrop.getOutput(data);
            if (isImageChangeLesson) {
                img_lesson.setImageURI(resultUri);
                isImageChangeLesson = false;
            }
            if(isImageChangeWords) {
                img_words.setImageURI(resultUri);
                isImageChangeWords = false;
            }
        } else if (resultCode == UCrop.RESULT_ERROR) {
            final Throwable cropError = UCrop.getError(data);
        }

        if (ImagePicker.shouldHandle(requestCode, resultCode, data)) {
            com.esafirm.imagepicker.model.Image image = ImagePicker.getFirstImageOrNull(data);
            File imgFile = new File(image.getPath());
            if (imgFile.exists()) {
                UCrop.of(Uri.fromFile(imgFile), Uri.fromFile(imgFile))
                        .start(this);
            }
        }

        if(requestCode == 1){
            if(resultCode == RESULT_OK){
                Uri uriSound = data.getData();
                String path = uriSound.getPath();
                byte[] fileByteArray;
                File file = new File(path);
                fileByteArray = new byte[(int)file.length()];

                if(wordVoiceUploaded) {
                    uploadVoiceWords(fileByteArray, String.valueOf(System.currentTimeMillis()));
                }
            }
        }

        if(requestCode == 2){
            if(resultCode == RESULT_OK){
                Uri uriSound = data.getData();
                String path = uriSound.getPath();
                byte[] fileByteArray;
                File file = new File(path);
                fileByteArray = new byte[(int)file.length()];
                if(confVoiceUploaded){
                    uploadConversationVoice(fileByteArray, String.valueOf(System.currentTimeMillis()));
                }
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void uploadFoto(String lesson, String Chinesewords, String englishwords, String Chinese_conversation,
                            String Chinese_conv_pinyin, String englishwords1, String question, String Correct_answers, String wrong1,
                            String wrong2, String wrong3) {
        Long tsLong = System.currentTimeMillis() / 1000;
        StorageReference storyImage = storageRef.child("lesson/" + tsLong + ".jpg");

        Bitmap bitmap = Bitmap.createBitmap(img_lesson.getDrawable().getIntrinsicWidth(), img_lesson.getDrawable().getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        img_lesson.getDrawable().setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        img_lesson.getDrawable().draw(canvas);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 10, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = storyImage.putBytes(data);

        uploadTask.addOnProgressListener(taskSnapshot -> {
            int progress = (100 * (int) taskSnapshot.getBytesTransferred()) / (int) taskSnapshot.getTotalByteCount();
        }).continueWithTask(task -> {
            if (!task.isSuccessful()) {
                throw task.getException();
            }

            return storyImage.getDownloadUrl();
        }).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Uri downloadUri = task.getResult();
                if (downloadUri == null)
                    return;

                img_uploaded_url = downloadUri.toString();
                uploadImageWord(lesson, Chinesewords, englishwords, Chinese_conversation, Chinese_conv_pinyin, englishwords1, question, Correct_answers, wrong1, wrong2, wrong3);
            }
        });


    }

    private void uploadImageWord(String lesson, String Chinesewords, String englishwords, String Chinese_conversation,
                                 String Chinese_conv_pinyin, String englishwords1, String question, String Correct_answers,
                                 String wrong1, String wrong2, String wrong3) {
        Long tsLong = System.currentTimeMillis() / 1000;
        StorageReference wordsImage = storageRef.child("lesson/words/" + tsLong + ".jpg");

        Bitmap bitmap2 = Bitmap.createBitmap(img_words.getDrawable().getIntrinsicWidth(), img_words.getDrawable().getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas2 = new Canvas(bitmap2);
        img_words.getDrawable().setBounds(0, 0, canvas2.getWidth(), canvas2.getHeight());
        img_words.getDrawable().draw(canvas2);
        ByteArrayOutputStream baos2 = new ByteArrayOutputStream();
        bitmap2.compress(Bitmap.CompressFormat.JPEG, 10, baos2);
        byte[] data2 = baos2.toByteArray();

        UploadTask uploadTask2 = wordsImage.putBytes(data2);

        uploadTask2.addOnProgressListener(taskSnapshot -> {
            int progress = (100 * (int) taskSnapshot.getBytesTransferred()) / (int) taskSnapshot.getTotalByteCount();
        }).continueWithTask(task -> {
            if (!task.isSuccessful()) {
                throw task.getException();
            }

            return wordsImage.getDownloadUrl();
        }).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Uri downloadUri = task.getResult();
                if (downloadUri == null)
                    return;

                img_uploaded_url2 = downloadUri.toString();
                Addlesson(lesson, Chinesewords, englishwords, Chinese_conversation, Chinese_conv_pinyin, englishwords1, question, Correct_answers, wrong1, wrong2, wrong3);
            }
        });
    }
}
