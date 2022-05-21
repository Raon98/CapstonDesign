package com.example.capstondesign.controller;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.capstondesign.R;
import com.example.capstondesign.model.CountWriteTask;
import com.example.capstondesign.model.Profile;
import com.example.capstondesign.model.ProfileCountjsonTask;
import com.example.capstondesign.model.ProfileTask;
import com.facebook.login.LoginManager;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;
import com.nhn.android.naverlogin.OAuthLogin;
import com.squareup.picasso.Picasso;

import java.util.concurrent.ExecutionException;

public class Fragment_profile extends Fragment {
    String nickname;
    TextView nicknameTv, myInfoclass;
    Button logout, in_profilebtn, in_watchlist, setupbtn, noticebtn, tradebtn;
    ImageView classImage;
    int login = LoginAcitivity.login;
    public static String number;
    String strurl;
    CountWriteTask countWriteTask;

    String count;

    ImageView showUserProfile;

    Profile profile = LoginAcitivity.profile;
    ProfileCountjsonTask profileCountjsonTask;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Fragment_profile() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BlankFragment_first.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_profile newInstance(String param1, String param2) {
        Fragment_profile fragment = new Fragment_profile();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        StrictMode.ThreadPolicy policy =
                new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        View v = inflater.inflate(R.layout.fragment_profile, container, false);

        nicknameTv = v.findViewById(R.id.nickname);

        profileCountjsonTask = new ProfileCountjsonTask();
        showUserProfile = v.findViewById(R.id.Myinfoimage);

        classImage = v.findViewById(R.id.classimage);
        myInfoclass = v.findViewById(R.id.myinfoclass);

        try {
            //
            //String a = profileTask.substringBetween(result1, "number:", "/");

            Log.d("TEST", number);
            if (number.equals("-1")) {
                strurl = "http://13.124.75.92:8080/king.png";
                Log.d("NUM0", strurl);
            } else {
                strurl = "http://13.124.75.92:8080/upload/" + profile.getEmail() + number + ".jpg";
                Log.d("NUM", strurl);
            }
            profile.setPicture(strurl);
            Picasso.get().load(Uri.parse(strurl)).into(showUserProfile);
        } catch (Exception e) {
            e.printStackTrace();
            profile.setPicture("http://13.124.75.92:8080/king.png");
            Picasso.get().load(Uri.parse("http://13.124.75.92:8080/king.png")).into(showUserProfile);
        }



        //프로필을 불러오는 Task를 통해 프로필 값들을 입력함
        ProfileTask profileTask = new ProfileTask();
        try {
            Log.d("PROFILENAME", profile.getName());
            Log.d("PROFILENAME", profile.getEmail());
            String result = profileTask.execute(profile.getName(), profile.getEmail()).get();

            nickname = profileTask.substringBetween(result, "nickname:", "/");
            Log.d("NICKNAME", nickname);
            profile.setNickname(nickname);
            nicknameTv.setText(nickname);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        countWriteTask = new CountWriteTask();
        try {
            String result = countWriteTask.execute(nickname).get();
            int a = result.indexOf(":");
            count =  result.substring(a+1);
            int i = Integer.parseInt(count);
            Log.d("NICKNAME", count);

            // i가 쓴 게시글 갯수(게시글, 공동구매글 포함한 숫자임)

            if(i < 5) {
                classImage.setImageResource(R.drawable.mbs_b);
                myInfoclass.setText("자취준비생");
            } else if(i < 20) {
                classImage.setImageResource(R.drawable.mbs_c);
                myInfoclass.setText("자취초보");
            } else if(i < 40) {
                classImage.setImageResource(R.drawable.mbs_s);
                myInfoclass.setText("자취중수");
            } else if(i < 60) {
                classImage.setImageResource(R.drawable.mbs_g);
                myInfoclass.setText("자취고수");
            }else {
                classImage.setImageResource(R.drawable.mbs_k);
                myInfoclass.setText("자취왕");
            }

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        in_profilebtn = (Button) v.findViewById(R.id.viewProfile);
        in_profilebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), in_profile.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        in_watchlist = (Button) v.findViewById(R.id.inrebtn);
        in_watchlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), in_watchlist.class);
                startActivity(intent);
            }
        });

        noticebtn = v.findViewById(R.id.noticebtn);
        noticebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), in_notice.class);
                startActivity(intent);
            }
        });

        setupbtn = v.findViewById(R.id.setupbtn);

        //세팅 이벤트
        setupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), in_setting.class);
                startActivity(intent);
            }
        });

        tradebtn = v.findViewById(R.id.tradebtn);

        tradebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), in_board.class);
                startActivity(intent);
            }
        });


        logout = v.findViewById(R.id.logoutbtn);

        //로그아웃 이벤트
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });

        return v;
    }


    void logout () {
        Log.d("LOGIN", String.valueOf(LoginAcitivity.login));
        //네이버 로그인시 login 값은 2
        if (login == 1) {
            //카카오 로그인시 login 값은 1
            Toast.makeText(getContext(), "로그아웃 되었습니다.", Toast.LENGTH_SHORT).show();
            UserManagement.getInstance().requestLogout(new LogoutResponseCallback() {
                @Override
                public void onCompleteLogout() {
                    Intent intent = new Intent(getActivity(), Fragment_main.class);
                    startActivity(intent);
                    LoginAcitivity.Login = false;
                    LoginAcitivity.login = 0;
                    Log.d("LOGOUT", String.valueOf(login));
                }
            });
            getActivity().finish();
        } else if (login == 2) {

            OAuthLogin mOAuthLoginModule;
            mOAuthLoginModule = OAuthLogin.getInstance();
            mOAuthLoginModule.init(
                    getContext()
                    , getString(R.string.naver_client_id)
                    , getString(R.string.naver_client_secret)
                    , getString(R.string.naver_client_name)
            );
            mOAuthLoginModule.logout(getContext());
            Intent intent = new Intent(getActivity(), Fragment_main.class);
            startActivity(intent);
            LoginAcitivity.Login = false;
            Toast.makeText(getContext(), "로그아웃 되었습니다.", Toast.LENGTH_SHORT).show();
            LoginAcitivity.login = 0;
            getActivity().finish();
        } else if (login == 3) {
            //페이스북 로그인시 login 값은 3
            LoginManager.getInstance().logOut();
            Toast.makeText(getContext(), "로그아웃 되었습니다.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getActivity(), Fragment_main.class);
            startActivity(intent);
            LoginAcitivity.Login = false;
            LoginAcitivity.login = 0;
            getActivity().finish();
        } else if (login == 4) {
            //이메일 로그인시 login 값은 4
            Intent intent = new Intent(getActivity(), Fragment_main.class);
            Toast.makeText(getContext(), "로그아웃 되었습니다.", Toast.LENGTH_SHORT).show();
            startActivity(intent);
            LoginAcitivity.Login = false;
            LoginAcitivity.login = 0;
            getActivity().finish();
        }
    }

}


