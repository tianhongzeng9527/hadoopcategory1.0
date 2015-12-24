package tian.usrhandle;

import org.json.JSONException;
import org.json.JSONObject;
import tian.htmlhandle.TitleKeyWords;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by tian on 15-12-17.
 */
/*
1请求日期Datetime发起请求的时间,格式：20151217101232
2响应时间Datetime返回响应的时间,格式：20151217101232
3源IPString发起请求的客户端IP
4源端口String发起请求的客户端端口
5目的IPString服务器端IP
6目的端口String服务器端端口
7响应IPString返回响应到客户端的IP
8响应端口String返回响应到客户端端口
9客户端IPString接受响应的客户端IP
10客户端端口String接受响应的客户端端口
11请求方法String请求的方法类型，如GET
12URLString请求的URL
13User-AgentString用户的访问设备类型
14ReferURLString发起请求的上级URL
15CookieString发送请求时携带的Cookie集合，以Key=value形式
16域名String返回响应的
17URIString响应的URI18ContentString页面的HTML内容
 */
public class UserDataInformation {
    private String reqTime;
    private String respTime;
    private String reqIP;
    private String reqPort;
    private String destinationIP;
    private String destinationPost;
    private String respIP;
    private String respPort;
    private String acceptRespIP;
    private String acceptRespPost;
    private String reqMethod;
    private String reqURL;
    private String userEquipment;
    private String reqSupURL;
    private String reqCookie;
    private String domainName;
    private String htmlContent;
    private String schoolMessage;
    private List<String> keyWords;
    public boolean isNormalMessage;
    private final static int messageLength = 18;
    private final static int keyNum = 5;
    private Type type;
    private final static int sensitiveWordNum = -1;
    private TitleKeyWords titleKeyWords;
    private List<String> containsSensitiveWordsList;
    private Map<String, Integer> keyWordsType;
    private final static double similarScoreLine = -0.5;
    private String category;
    private final static int layer = 1;
    private final static String NOISE = "noise";

    public UserDataInformation(String userDataInformation) throws IOException {
        String[] splits = userDataInformation.split(" ");
        category = NOISE;
        if (splits.length < messageLength) {
            isNormalMessage = false;
        } else {
            isNormalMessage = true;
            initUserInformation(splits);
            initKeyWords();
            containsSensitiveWordsList = titleKeyWords.getContainsSensitiveWordsList();
        }
    }

    private void initUserInformation(String[] splits) {
        reqTime = splits[0];
        respTime = splits[1];
        reqIP = splits[2];
        reqPort = splits[3];
        destinationIP = splits[4];
        destinationPost = splits[5];
        respIP = splits[6];
        respPort = splits[7];
        acceptRespIP = splits[8];
        acceptRespPost = splits[9];
        reqMethod = splits[10];
        reqURL = splits[11];
        userEquipment = splits[12];
        reqSupURL = splits[13];
        reqCookie = splits[14];
        domainName = splits[15];
        htmlContent = splits[16];
        schoolMessage = splits[17];
    }

    private void initKeyWords() throws IOException {
        titleKeyWords = new TitleKeyWords(reqURL, keyNum);
        titleKeyWords.init();
        keyWords = titleKeyWords.getTopNumKey();
    }

    public void sensitiveClassify() {
        if (containsSensitiveWordsList.size() > sensitiveWordNum) {
            this.type = Type.sensitive;
        }
    }

    public void commonClassify() throws IOException, JSONException, InterruptedException {
        keyWordsType = new HashMap<String, Integer>();
        JSONObject categoryJsonObject = Category.category;
        for (int i = 0; i < layer; ) {
            for (String keyWord : keyWords) {
                double maxScore = 0;
                Iterator iterable = categoryJsonObject.keys();
                while (iterable.hasNext()) {
                    String tempCategory = (String) iterable.next();
                    double score = 0;
                    score = similarScore(keyWord, tempCategory);
                    if (score >= maxScore && score > similarScoreLine) {
                        category = tempCategory;
                        maxScore = score;
                    }
                }
                if (keyWordsType.containsKey(category))
                    keyWordsType.put(category, keyWordsType.get(category) + 1);
                else
                    keyWordsType.put(category, 1);
                int max = 0;
                for (Map.Entry<String, Integer> entry : keyWordsType.entrySet()) {
                    if (entry.getValue() > max) {
                        category = entry.getKey();
                        max = entry.getValue();
                    }
                }
                if (++i < layer)
                    try {
                        categoryJsonObject = categoryJsonObject.getJSONObject(category);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
            }
        }
    }

    private double similarScore(String word, String type) throws IOException, InterruptedException {
        double score = 0;
        URL url = new URL("http://192.168.20.9:8080/wordsDistance?word1=" + word + "&word2=" + type);
        URLConnection urlcon = url.openConnection();
        InputStream is = urlcon.getInputStream();
        BufferedReader buffer = new BufferedReader(new InputStreamReader(is));
        String s = buffer.readLine();
        score = Double.valueOf(s);
        buffer.close();
        return score;
    }

    public String toString() {
        if (category != NOISE)
            return schoolMessage + " 1 " + category;
        else if (this.type != Type.sensitive)
            return schoolMessage + " 2 " + NOISE;
        else {
            StringBuilder stringBuilder = new StringBuilder();
            for (String s : containsSensitiveWordsList) {
                stringBuilder.append(s);
                stringBuilder.append(" ");
            }
            return NOISE + reqIP + " " + stringBuilder + reqTime + category;
        }
    }

    public static void main(String[] args) throws IOException, JSONException, InterruptedException {
        String s = "0 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 数学家 tianhongzeng";
        UserDataInformation userDataInformation = new UserDataInformation(s);
        userDataInformation.sensitiveClassify();
        System.out.println(userDataInformation.similarScore("数学", "一部分"));
        userDataInformation.commonClassify();
        System.out.print(userDataInformation.toString());
    }
}
