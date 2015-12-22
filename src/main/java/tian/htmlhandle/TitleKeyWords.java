package tian.htmlhandle;

import com.chenlb.mmseg4j.analysis.ComplexAnalyzer;
import com.chenlb.mmseg4j.analysis.TokenUtils;
import org.apache.log4j.Logger;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.PackedTokenAttributeImpl;
import org.jsoup.Jsoup;
import readability.Readability;
import tian.usrhandle.SensitiveWordDictionary;

import java.io.*;
import java.net.URL;
import java.util.*;


/**
 * Created by Administrator on 2015/12/2 0002.
 */
public class TitleKeyWords {
    private URL url;
    private int topNum;
    private Map<String, Integer> contentParticiple;
    private List<String> topNumKey;
    private String claenHtml;
    private int timeout;
    private String title;
    private String content;
    private Set<String> titleParticipleFrequency;
    private String html;
    private static Set<String> stopWordsDictionary;
    private static Set<String> drugWordsDictionary;
    private static Set<String> illegalWordsDictionary;
    private static Set<String> otherSensitiveWordsDictionary;
    private static Set<String> politicalWordsDictionary;
    private static Set<String> sexyWordsDictionary;
    private static Set<String> supersitionWordsDictionary;
    private List<String> containsDrugWordsList;
    private List<String> containsIllegalWordsList;
    private List<String> containsOtherWordsList;
    private List<String> containsPoliticalWordsList;
    private List<String> containsSexyWordsList;
    private List<String> containsSupersitionWordsList;
    private List<String> containsSensitiveWordsList;
    private static boolean isInitDictionary = true;

    private static Logger logger = Logger.getLogger(TitleKeyWords.class);

    public TitleKeyWords(URL url, int topNum) {
        this.url = url;
        this.topNum = topNum;
    }

    public TitleKeyWords(String html, int topNum) {
        this.html = html;
        this.topNum = topNum;
    }

    private void setTitle() {
        title = Jsoup.parse(claenHtml).title();
    }

    private void setClaenHtml() {
        Readability readability;
        try {
            if ((this.html != null) && (!"".equals(html)))
                readability = new Readability(html);
            else
                readability = new Readability(url, timeout);
            readability.init();
        } catch (IOException e) {
            logger.info(e.toString());
            return;
        }
        claenHtml = readability.outerHtml();
    }

    private void setContent() {
        content = Jsoup.parse(claenHtml).body().text();
    }

    private void setContentParticiple() {
        List<String> contentWords = toWords(content, new ComplexAnalyzer());
        for (String word : contentWords)
            if (!stopWordsDictionary.contains(word)) {
                if (contentParticiple.containsKey(word)) {
                    contentParticiple.put(word, contentParticiple.get(word) + 1);
                } else {
                    contentParticiple.put(word, 1);
                }
                setContainsSensitiveWordsList(word);
            }
    }

    private void setContainsSensitiveWordsList(String word) {
        if(SensitiveWordDictionary.sensitiveWordsDictionary.contains(word)){
            containsSensitiveWordsList.add(word);
        }
    }

    private List<String> toWords(String txt, Analyzer analyzer) {
        List<String> words = new ArrayList<String>();
        TokenStream ts = null;
        try {
            ts = analyzer.tokenStream("text", new StringReader(txt));
            ts.reset();
            for (PackedTokenAttributeImpl t = new PackedTokenAttributeImpl(); (t = TokenUtils.nextToken(ts, t)) != null; ) {
                words.add(t.toString());
            }
        } catch (IOException e) {
            logger.info(e.toString());
        } finally {
            if (ts != null) {
                try {
                    ts.close();
                } catch (IOException e) {
                    logger.info(e.toString());
                }
            }
        }
        return words;
    }

    private void setTitleParticipleFrequency() {
        List<String> contentWords = toWords(title, new ComplexAnalyzer());
        for (String word : contentWords)
            if (!stopWordsDictionary.contains(word))
                titleParticipleFrequency.add(word);
        sortMapByValue(contentParticiple);
    }

    private void setTopNumKey() {
        if (topNum > titleParticipleFrequency.size()) {
            topNum = titleParticipleFrequency.size();
        }
        int i = 1;
        for (String key : titleParticipleFrequency) {
            topNumKey.add(key);
            i++;
            if (topNum < i) {
                break;
            }
        }
    }

    private void sortMapByValue(Map<String, Integer> map) {
        List<Map.Entry<String, Integer>> list = new ArrayList<Map.Entry<String, Integer>>(map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
            //升序排序
            public int compare(Map.Entry<String, Integer> o1,
                               Map.Entry<String, Integer> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }

        });
        for (Map.Entry<String, Integer> mapping : list) {
            if (titleParticipleFrequency.contains(mapping.getKey())) {
                titleParticipleFrequency.add(mapping.getKey());
            }
        }
    }

    public Map<String, Integer> getContentParticiple() {
        return contentParticiple;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public List<String> getTopNumKey() {
        return topNumKey;
    }

    public String getClaenHtml() {
        return claenHtml;
    }

    public void init() throws IOException {
        claenHtml = "";
        contentParticiple = new TreeMap<String, Integer>();
        topNumKey = new ArrayList<String>();
        timeout = 20000;
        titleParticipleFrequency = new LinkedHashSet<String>();
        initStopWords();
        initSensitiveWordsDictionary();
//        initContainList();
        if (isInitDictionary) {
            isInitDictionary = false;
            initDictionary();
        }
        setClaenHtml();
        setContent();
        setTitle();
        setContentParticiple();
        setTitleParticipleFrequency();
        setTopNumKey();
        initContainsSensitiveWordsList();
    }

    public List<String> getContainsSensitiveWordsList() {
        return this.containsSensitiveWordsList;
    }

    private void initDictionary() throws IOException {
//        initStopWords();
//        initDrugWordsDictionary();
//        initIllegalWordsDictionary();
//        initOtherSensitiveWordsDictionary();
//        initPoliticalWordsDictionary();
//        initSexyWordsDictionary();
//        initSupersitionWordsDictionary();
    }

    private void initContainsSensitiveWordsList() {
//        containsSensitiveWordsList.add(containsDrugWordsList);
//        containsSensitiveWordsList.add(containsIllegalWordsList);
//        containsSensitiveWordsList.add(containsOtherWordsList);
//        containsSensitiveWordsList.add(containsPoliticalWordsList);
//        containsSensitiveWordsList.add(containsSexyWordsList);
//        containsSensitiveWordsList.add(containsSupersitionWordsList);
        containsSensitiveWordsList = new ArrayList<String>();
    }

    private void initStopWords() throws IOException {
//        stopWordsDictionary = getWords("/stopWordsDictionary.txt");
        stopWordsDictionary = new HashSet<String>();
    }

    private Set<String> getWords(String path) throws IOException {
        Set<String> words = new HashSet<String>();
        File file = new File(path);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
        String s = "";
        while ((s = bufferedReader.readLine()) != null) {
            s = s.trim();
            String[] splits = s.split(" ");
            for (int i = 0; i < splits.length; i++) {
                words.add(splits[i]);
            }
        }
        return words;
    }

    private void initDrugWordsDictionary() throws IOException {
        drugWordsDictionary = getWords("/drug.txt");
    }

    private void initIllegalWordsDictionary() throws IOException {
        illegalWordsDictionary = getWords("/illegal.txt");
    }

    private void initOtherSensitiveWordsDictionary() throws IOException {
        otherSensitiveWordsDictionary = getWords("/other.txt");
    }

    private void initPoliticalWordsDictionary() throws IOException {
        politicalWordsDictionary = getWords("/political.txt");
    }

    private void initSexyWordsDictionary() throws IOException {
        sexyWordsDictionary = getWords("/sexy.txt");
    }

    private void initSupersitionWordsDictionary() throws IOException {
        supersitionWordsDictionary = getWords("/supersition.txt");
    }

    private void initSensitiveWordsDictionary() {
        if(SensitiveWordDictionary.sensitiveWordsDictionary == null || SensitiveWordDictionary.isNeedChange == true){
            SensitiveWordDictionary sensitiveWordDictionary = new SensitiveWordDictionary();
            sensitiveWordDictionary.init();;
        }
    }

    public List<String> getContainsDrugWordsList() {
        return this.containsDrugWordsList;
    }

    public List<String> getContainsIllegalWordsList() {
        return this.containsIllegalWordsList;
    }

    public List<String> getContainsOtherWordsList() {
        return this.containsOtherWordsList;
    }

    public List<String> getContainsPoliticalWordsList() {
        return this.containsPoliticalWordsList;
    }

    public List<String> getContainsSexyWordsList() {
        return this.containsSexyWordsList;
    }

    public List<String> getContainsSupersitionWordsList() {
        return this.containsSupersitionWordsList;
    }
}