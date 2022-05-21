package com.example.capstondesign.model;

import android.os.AsyncTask;
import android.util.Log;

import com.example.capstondesign.controller.add_GroupBuying;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class UploadFileAsyncGroupBuying extends AsyncTask<String, Void, String> {

    GroupBuyingTimejsonTask groupBuyingTimejsonTask;
    @Override
    protected String doInBackground(String... params) {

        try {
            String time = add_GroupBuying.time;
            Log.d("timeUpload", time);
            //                      앱 저장 주소  / 앱 PackegeName            / 폴더  / 파일 이름
            String sourceFileUri = "/data/data/com.example.capstondesign/files/" + add_GroupBuying.titlestr.hashCode() + time + ".jpg"; // Main에 있는  copy.jpg와 같이 바꾸셔야 합니다



            HttpURLConnection conn = null;
            DataOutputStream dos = null;
            String lineEnd = "\r\n";
            String twoHyphens = "--";
            String boundary = "*****"; // 구분자
            int bytesRead, bytesAvailable, bufferSize;
            byte[] buffer;
            int maxBufferSize = 1 * 1024 * 1024;
            File sourceFile = new File(sourceFileUri);
            Log.d("sourceFile", sourceFile.toString());
            File sourceFile2 = new File(sourceFileUri);
            if (sourceFile2.exists()) { //sourceFile.isFile()
                Log.d("sourceFile", "sourceFIle여기까지2");
                try {
                    //                        JSP URL
                    String upLoadServerUri = "http://13.124.75.92:8080/multi.jsp";
                    Log.d("sourceFile", "sourceFIle여기까지3");

                    // open a URL connection to the Servlet
                    FileInputStream fileInputStream = new FileInputStream(
                            sourceFile);
                    URL url = new URL(upLoadServerUri);

                    // Open a HTTP connection to the URL
                    conn = (HttpURLConnection) url.openConnection();
                    conn.setDoInput(true); // Allow Inputs
                    conn.setDoOutput(true); // Allow Outputs
                    conn.setUseCaches(false); // Don't use a Cached Copy
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Connection", "Keep-Alive");
                    conn.setRequestProperty("ENCTYPE",
                            "multipart/form-data");
                    conn.setRequestProperty("Content-Type",
                            "multipart/form-data;boundary=" + boundary);
                    conn.setRequestProperty("bill", sourceFileUri);

                    dos = new DataOutputStream(conn.getOutputStream());
                    Log.d("dos", "됬다");

                    // 튜플처럼 사용 (class 를 하나만들어서 String 배열 두개를 만들고 ArrayList 로 저장후 for문 하나로 사용하기)
                    // 일반적인 데이터 들어가기작
                    // 일반적인 데이터  넣기
                    dos.writeBytes(twoHyphens + boundary + lineEnd); // --*****
                    dos.writeBytes("Content-Disposition: form-data; name=\"name\"" + lineEnd);
                    dos.writeBytes(lineEnd);
                    dos.writeBytes("13.124.75.92:8080/upload/"); // 여기가 MySQL에 저장됩니다.
                    Log.e("wwwww", dos.toString());
                    dos.writeBytes(lineEnd); // \r\n
                    // 데이터 넣기 끝
                    Log.d("dosWRITE", "됬다");

                    // 파일 데이터 시
                    dos.writeBytes(twoHyphens + boundary + lineEnd); // --*****
                    dos.writeBytes("Content-Disposition: form-data; name=\"file\";filename=\"" + sourceFileUri + "\"" + lineEnd); // Content-Disposition: form-data; name=file; filename=/data/data/com.example.multifileupload/files/copy.jpg\r\n

                    dos.writeBytes(lineEnd); // \r\n

                    // create a buffer of maximum size
                    bytesAvailable = fileInputStream.available();

                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    buffer = new byte[bufferSize];

                    // read file and write it into form...
                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                    while (bytesRead > 0) {

                        dos.write(buffer, 0, bufferSize);
                        bytesAvailable = fileInputStream.available();
                        bufferSize = Math
                                .min(bytesAvailable, maxBufferSize);
                        bytesRead = fileInputStream.read(buffer, 0,
                                bufferSize);

                    }
                    Log.d("dosREAD", "됬다");

                    // send multipart form data necesssary after file
                    // data...
                    dos.writeBytes(lineEnd);
                    dos.writeBytes(twoHyphens + boundary + twoHyphens
                            + lineEnd);

                    // Responses from the server (code and message)
                    int serverResponseCode = conn.getResponseCode();
                    String serverResponseMessage = conn
                            .getResponseMessage();

                    if (serverResponseCode == 200) {

                        // messageText.setText(msg);
                        //Toast.makeText(ctx, "File Upload Complete.",
                        //      Toast.LENGTH_SHORT).show();

                        // recursiveDelete(mDirectory1);

                    }

                    // close the streams //
                    fileInputStream.close();
                    dos.flush();
                    dos.close();
                    Log.d("??", "설마");



                } catch (Exception e) {

                    // dialog.dismiss();
                    e.printStackTrace();
                    Log.d("Exception", e.getMessage());

                }
                // dialog.dismiss();

            } // End else block


        } catch (Exception ex) {
            // dialog.dismiss();

            ex.printStackTrace();
            Log.d("Exception", ex.getMessage());
        }
        return "Executed";
    }

    @Override
    protected void onPostExecute(String result) {

    }

    @Override
    protected void onPreExecute() {
    }

    @Override
    protected void onProgressUpdate(Void... values) {
    }
}
