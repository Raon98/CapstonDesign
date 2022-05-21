package com.example.capstondesign.controller;

import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.capstondesign.R;
import com.example.capstondesign.model.Board;
import com.example.capstondesign.model.BoardTask;
import com.example.capstondesign.model.Profile;
import com.example.capstondesign.model.ProfileTask;
import com.example.capstondesign.model.UploadFileAsync;
import com.example.capstondesign.model.UploadFileAsyncBoard;
import com.example.capstondesign.model.addBoardTask;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutionException;

public class add_Board extends AppCompatActivity {
    Profile profile = LoginAcitivity.profile;
    Uri image;
    static Uri fileBoard[];
    ImageView imgView;
    public static String title, time;
    String nick, nickname, text;
    Button back;
    long now;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addboard);

        now = System.currentTimeMillis();

        final EditText EDTITLE = findViewById(R.id.addboard_title);
        final EditText EDTEXT = findViewById(R.id.addboard_text);

        back = (Button)findViewById(R.id.board_exit);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        imgView = findViewById(R.id.addImageView);

        imgView.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
                // 갤러리를 열기위해 타입을 지정
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                } else {
                    intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
                    intent.addCategory(Intent.CATEGORY_OPENABLE);
                }

                // Always show the chooser (if there are multiple options available)
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);

            }
        });

        Button upload = findViewById(R.id.upload);
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getNick();
                time = String.valueOf(now);
                Log.d("TIMEBOARD", time);
                nick = nickname;
                title = EDTITLE.getText().toString();
                text = EDTEXT.getText().toString();
                if(title.trim().length() >0 || text.trim().length() >0) {
                    if(fileBoard != null) {

                        for (int i = 0; i < fileBoard.length; i++) {
                            try {
                                InputStream ins = getContentResolver().openInputStream(fileBoard[i]);
                                // "/data/data/패키지 이름/files/copy.jpg" 저장
                                Log.d("에러 찾기", "여기서?3");
                                FileOutputStream fos = getApplicationContext().openFileOutput(title.hashCode() + time + ".jpg", 0);


                                Log.d("에러 찾기", "여기서?4");

                                byte[] buffer = new byte[1024 * 100];

                                while (true) {
                                    int data = ins.read(buffer);
                                    if (data == -1) {
                                        break;
                                    }

                                    fos.write(buffer, 0, data);
                                }

                                ins.close();
                                fos.close();

                                new UploadFileAsyncBoard().execute().get();

                                Log.d("UploadFile", "됬다");
                            } catch (IOException e) {
                                e.printStackTrace();
                                Log.d("IOException", e.getMessage());
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                                Log.d("InterrException", e.getMessage());
                            } catch (ExecutionException e) {
                                e.printStackTrace();
                                Log.d("ExecutionException", e.getMessage());
                            }
                        }
                        fileBoard = null;
                    }

                    addBoardTask();
                } else {
                    Toast.makeText(getApplicationContext(), "제목이나 내용을 모두 작성해주세요.", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    void getNick() {
        ProfileTask profileTask = new ProfileTask();
        try {
            String result = profileTask.execute(profile.getName(), profile.getEmail()).get();
            nickname = profileTask.substringBetween(result, "nickname:", "/");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    void addBoardTask() {

        Board board = new Board(nick, title, text, time);

        addBoardTask addBoardTask = new addBoardTask();
        addBoardTask.execute(nick, title, text, time);

        BoardTask.boardlist.add(board);
        Fragment_board.boardAdapter.notifyDataSetChanged();
        Intent intent = new Intent(getApplicationContext(), Fragment_main.class);
        intent.putExtra("boardNum", 1);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                try {
                    Log.e("Data" , data.toString());
                    //Log.e("Data" , data.getData().toString());
                    Log.d("possible", "여기서?");
                    //file = data.getData();
                    ClipData clipData = data.getClipData();


                    if(clipData.getItemCount() > 1 && clipData.getItemCount() < 9) {

                        Log.d("count", Integer.toString(clipData.getItemCount()));
                        fileBoard= new Uri[clipData.getItemCount()];
                        for(int i = 0; i < clipData.getItemCount(); i++) {
                            fileBoard[i] = clipData.getItemAt(i).getUri();
                            // 선택한 이미지에서 비트맵 생성
                            InputStream in = getContentResolver().openInputStream(clipData.getItemAt(i).getUri());
                            Bitmap img = BitmapFactory.decodeStream(in);
                            in.close();

                            imgView.setImageBitmap(img);

                        }
                    } else {
                        fileBoard = new Uri[1];
                        fileBoard[0] = data.getData();

                        InputStream in = getContentResolver().openInputStream(data.getData());
                        Bitmap img = BitmapFactory.decodeStream(in);
                        in.close();

                        imgView.setImageBitmap(img);

                    }
                } catch (Exception e) {
                    e.printStackTrace();

                    fileBoard = new Uri[1];
                    fileBoard[0] = data.getData();

                    InputStream in = null;
                    try {
                        in = getContentResolver().openInputStream(data.getData());
                    } catch (FileNotFoundException fileNotFoundException) {
                        fileNotFoundException.printStackTrace();
                    }
                    Bitmap img = BitmapFactory.decodeStream(in);
                    try {
                        in.close();
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }

                    imgView.setImageBitmap(img);
                }
            }
        }


    }
}