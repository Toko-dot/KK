package com.ms.kk.module.setting;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.ms.kk.R;
import com.ms.kk.base.BaseActivity;
import com.ms.kk.base.Logger;
import com.ms.kk.constant.MKey;
import com.ms.kk.databinding.ActivitySettingBinding;
import com.ms.kk.module.about.AboutUsActivity;
import com.ms.kk.module.login.LoginActivity;
import com.ms.kk.utils.SystemUtils;
import com.tencent.mmkv.MMKV;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.MultipartBody;
import okhttp3.OkHttp;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

public class SettingActivity extends BaseActivity<SettingViewModel> {

    private ActivitySettingBinding binding;

    public final static int SETTING_CODE = 112;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_setting);
        initView();
        initViewModel();
    }

    @Override
    public void initView() {
        super.initView();
        binding.llHeader.tvTitle.setText("设置");
        binding.llHeader.igBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void initViewModel() {
        super.initViewModel();
        viewModel.version.set(SystemUtils.getVersionName(this));
        binding.setVm(viewModel);
    }

    public void onExit(View view) {
        MMKV.defaultMMKV().putString(MKey.KEY_USER_INFO, "").commit();
        MMKV.defaultMMKV().putString(MKey.KEY_TOKEN, "").commit();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivityForResult(intent, LoginActivity.CODE_LOGIN);
    }

    @Override
    public void finish() {
        setResult(RESULT_OK);
        super.finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        finish();
    }

    public void onAbout(View view) {
//        startActivity(new Intent(this, AboutUsActivity.class));
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {


                    Document document = Jsoup.connect("https://www.hmtv.me/zongyi").get();

                    Elements elementsByClass = document.getElementsByClass("u-movie");
                    HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                        @Override
                        public void log(String message) {
                            Logger.logD(URLDecoder.decode(message));
                        }
                    });
                    httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

                    for (Element element : elementsByClass) {

                        String brief = element.getElementsByTag("a").attr("href");

                        document = Jsoup.connect(brief).get();

                        String name = document.getElementsByClass("video_img").get(0).getElementsByTag("img").get(0).attr("alt").replace("的海报", "");

                        String url = "https://www.hmtv.me"+document.getElementsByClass("vlink").get(0).getElementsByTag("a").attr("href");

                        ArrayList<String> list = new ArrayList<>();

                        parseDir(list, url);

                        for (int i = 0; i < list.size(); i++) {
                            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                                    .addInterceptor(httpLoggingInterceptor)
                                    .build();
                            FormBody body = new FormBody.Builder()
                                    .add("pName", name)
                                    .add("name", (i + 1) > 10 ? "第" + (i + 1) + "集" : "第0" + (i + 1) + "集")
                                    .add("play", list.get(i))
                                    .add("thumb", "")
                                    .build();
                            Request request = new Request.Builder()
                                    .url("http://111.229.83.8/api/movie/add")
                                    .post(body)
                                    .build();
                            Response execute = okHttpClient.newCall(request).execute();
                            if (!execute.isSuccessful()) {
                                break;
                            }
                        }

                    }

                    Logger.logD("结束结束结束结束结束结束");

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }

    public void onChat(View view) {
        SystemUtils.joinQQ(this, "2479024161");
    }

    public void parseDir(List<String> list, String url) {
        try {
            Document document = Jsoup.connect(url).get();

            Elements elements = document.getElementsByClass("tab").get(0).getElementsByTag("a");

            for (Element element : elements) {
                parse(list, "https://www.hmtv.me" + element.attr("href"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void parse(List<String> list, String url) {
        try {
            Document document = Jsoup.connect(url).get();

            String text = document.getElementsByClass("content").get(0).getElementsByTag("script").get(0).html();

            String[] split = text.split(",");

            String play = split[1].split("=")[1];
            if (!TextUtils.isEmpty(play) && play.contains(".m3u8")) {
                play = play.substring(1, play.length() - 1);
                list.add(play);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static class MovieSource {
        private String pName;
        private String name;
        private String play;

        public MovieSource(String pName, String name, String play) {
            this.pName = pName;
            this.name = name;
            this.play = play;
        }

        public String getpName() {
            return pName;
        }

        public void setpName(String pName) {
            this.pName = pName;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPlay() {
            return play;
        }

        public void setPlay(String play) {
            this.play = play;
        }
    }

}