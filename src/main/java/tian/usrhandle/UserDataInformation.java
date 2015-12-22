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
        String s = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">\n" +
                "     \n" +
                "    <html xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
                "    \n" +
                "<head>   \n" +
                "    \n" +
                "     \n" +
                "    <script type=\"text/javascript\" src=\"http://c.csdnimg.cn/pubfooter/js/tracking.js\" charset=\"utf-8\"></script>  \n" +
                "\n" +
                "    <script type=\"text/javascript\">\n" +
                "        var protocol = window.location.protocol;\n" +
                "        document.write('<script type=\"text/javascript\" src=\"' + protocol + '//csdnimg.cn/pubfooter/js/repoAddr2.js?v=' + Math.random() + '\"></' + 'script>');\n" +
                "    </script>\n" +
                "\n" +
                "     <script id=\"allmobilize\" charset=\"utf-8\" src=\"http://a.yunshipei.com/46aae4d1e2371e4aa769798941cef698/allmobilize.min.js\"></script>\n" +
                " <meta http-equiv=\"Cache-Control\" content=\"no-siteapp\" /><link rel=\"alternate\" media=\"handheld\" href=\"#\" />\n" +
                "\n" +
                "    <title>JAVA方法中通过调用URL来获取其返回的内容 - linwei_1029的专栏\n" +
                "        - 博客频道 - CSDN.NET</title>\n" +
                "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />\n" +
                "    <meta name=\"description\" content=\"Masonry与FDTemplateLayoutCell实践,UITableViewCell 自动布局\" />\n" +
                "    <script src=\"http://static.blog.csdn.net/scripts/jquery.js\" type=\"text/javascript\"></script>\n" +
                "    <script type=\"text/javascript\" src=\"http://static.blog.csdn.net/scripts/ad.js?v=1.1\"></script>\n" +
                "        <!--new top-->\n" +
                "       \n" +
                "        <link rel=\"stylesheet\" href=\"http://c.csdnimg.cn/public/common/toolbar/css/index.css\">\n" +
                "        <!--new top-->\n" +
                "\n" +
                "    <link rel=\"Stylesheet\" type=\"text/css\" href=\"http://static.blog.csdn.net/skin/default/css/style.css?v=1.1\" />\n" +
                "    <link id=\"RSSLink\" title=\"RSS\" type=\"application/rss+xml\" rel=\"alternate\" href=\"/linwei_1029/rss/list\" />\n" +
                "    <link rel=\"shortcut icon\" href=\"http://c.csdnimg.cn/public/favicon.ico\" />\n" +
                "    <link type=\"text/css\" rel=\"stylesheet\" href=\"http://static.blog.csdn.net/scripts/SyntaxHighlighter/styles/default.css\" />\n" +
                " \n" +
                "\n" +
                "\n" +
                "</head>\n" +
                "<body>\n" +
                "    \n" +
                "   \n" +
                "      <!--new top-->\n" +
                "    <script id=\"toolbar-tpl-scriptId\" fixed=\"true\" prod=\"blog\" skin=\"black\" src=\"http://c.csdnimg.cn/public/common/toolbar/js/html.js\" type=\"text/javascript\"></script>\n" +
                "     <!--new top-->\n" +
                "    <div id=\"container\">\n" +
                "        <div id=\"header\">\n" +
                "    <div class=\"header\">\n" +
                "        <div id=\"blog_title\">\n" +
                "            <h2>\n" +
                "                <a href=\"http://blog.csdn.net/linwei_1029\">linwei_1029的专栏</a></h2>\n" +
                "            <h3></h3>\n" +
                "            <div class=\"clear\">\n" +
                "            </div>\n" +
                "        </div>\n" +
                "        <div class=\"clear\">\n" +
                "        </div>\n" +
                "    </div>\n" +
                "</div>\n" +
                "<div id=\"navigator\">\n" +
                "    <div class=\"navigator_bg\">\n" +
                "    </div>\n" +
                "    <div class=\"navigator\">\n" +
                "        <ul>\n" +
                "            \n" +
                "                <li id=\"btnContents\"><a href=\"http://blog.csdn.net/linwei_1029?viewmode=contents\"><span onclick=\"_gaq.push(['_trackEvent','function', 'onclick', 'blog_articles_mulu'])\">\n" +
                "                    <img src=\"http://static.blog.csdn.net/images/ico_list.gif\">目录视图</span></a></li>\n" +
                "                <li id=\"btnView\"><a href=\"http://blog.csdn.net/linwei_1029?viewmode=list\"><span onclick=\"_gaq.push(['_trackEvent','function', 'onclick', 'blog_articles_zhaiyao'])\">\n" +
                "                    <img src=\"http://static.blog.csdn.net/images/ico_summary.gif\">摘要视图</span></a></li>\n" +
                "                <li id=\"btnRss\"><a href=\"http://blog.csdn.net/linwei_1029/rss/list\"><span onclick=\"_gaq.push(['_trackEvent','function', 'onclick', 'blog_articles_RSS'])\">\n" +
                "                    <img src=\"http://static.blog.csdn.net/images/ico_rss.gif\">订阅</span></a></li>                \n" +
                "\n" +
                "            </ul>\n" +
                "    </div>\n" +
                "</div>\n" +
                "<script type=\"text/javascript\">\n" +
                "    var username = \"linwei_1029\";\n" +
                "    var _blogger = username;\n" +
                "    var blog_address = \"http://blog.csdn.net/linwei_1029\";\n" +
                "    var static_host = \"http://static.blog.csdn.net\";\n" +
                "    var currentUserName = \"\";  \n" +
                "</script>\n" +
                "\n" +
                "        <div id=\"body\">\n" +
                "            <div id=\"main\">\n" +
                "                <div class=\"main\">\n" +
                "                        <div class=\"ad_class\">\n" +
                "<div class=\"notice tracking-ad\" data-mod='popu_3' > \n" +
                "\n" +
                "<a href=\"http://edu.csdn.net/app\">\n" +
                "<font color=red>学院APP首次下载，可得50C币！\n" +
                "</font></a>\n" +
                "&nbsp;&nbsp;&nbsp;\n" +
                "\n" +
                "<a href=\"http://bbs.csdn.net/topics/391876796\">\n" +
                "<font color=blue>欢迎来帮助开源“进步”\n" +
                "</font></a>\n" +
                "&nbsp;&nbsp;&nbsp;\n" +
                "<a href=\"http://bss.csdn.net/m/topic/cuanke_vote\">\n" +
                "<font color=red>当讲师？爱学习？投票攒课吧\n" +
                "</font></a>\n" +
                "&nbsp;&nbsp;&nbsp;\n" +
                "<a href=\"http://blog.csdn.net/atlassian2013/article/details/50291561\">\n" +
                "<font color=blue>认识Atlassian Datacenter产品\n" +
                "</font></a>\n" +
                "&nbsp;&nbsp;&nbsp;\n" +
                "<a href=\"http://blog.csdn.net/blogdevteam/article/details/49758609\">\n" +
                "<font color=red>【获奖公告】有奖试读—增长黑客\n" +
                "</font></a>\n" +
                "</div>                        </div>\n" +
                "                        \n" +
                "\n" +
                "<!--AdForward Begin:-->\n" +
                "<script type=\"text/javascript\" ads-src=\"http://csdnim.qtmojo.com/main/s?user=csdn|blog|fumeiti&db=csdnim&border=0&local=yes&js=ie\" src=\"http://creatim.qtmojo.cn/td/AllyesDeliver.min.js\" charset=\"gbk\"></script>\n" +
                "<!--AdForward End-->\n" +
                "\n" +
                "  \n" +
                "<link href=\"http://static.blog.csdn.net/css/comment1.css\" type=\"text/css\" rel=\"stylesheet\" />\n" +
                "<link href=\"http://static.blog.csdn.net/css/style1.css\" type=\"text/css\" rel=\"stylesheet\" />\n" +
                "<script language='JavaScript' type='text/javascript' src='http://download.csdn.net/js/jquery.cookie.js'></script>\n" +
                "<script type=\"text/javascript\" src=\"http://c.csdnimg.cn/rabbit/search-service/main.js\"></script>\n" +
                "<link rel=\"stylesheet\" href=\"http://static.blog.csdn.net/public/res-min/markdown_views.css?v=1.0\" />\n" +
                "<link rel=\"stylesheet\" href=\"http://static.blog.csdn.net/css/category.css?v=1.0\" />\n" +
                "<script type=\"text/javascript\" src=\"http://static.blog.csdn.net/public/res/bower-libs/MathJax/MathJax.js?config=TeX-AMS_HTML\"></script>\n" +
                "\n" +
                "  <script type=\"text/ecmascript\">\n" +
                "      window.quickReplyflag = true;\n" +
                "           \n" +
                "            var isBole = false;\n" +
                "            \n" +
                "          \n" +
                "    </script>\n" +
                "<div id=\"article_details\" class=\"details\">\n" +
                "    <div class=\"article_title\">   \n" +
                "         <span class=\"ico ico_type_Original\"></span>\n" +
                "\n" +
                "\n" +
                "    <h1>\n" +
                "        <span class=\"link_title\"><a href=\"/linwei_1029/article/details/6903290\">\n" +
                "        JAVA方法中通过调用URL来获取其返回的内容            \n" +
                "        </a></span>\n" +
                "    </h1>\n" +
                "</div>\n" +
                "\n" +
                "   \n" +
                "\n" +
                "        <div class=\"article_manage clearfix\">\n" +
                "        <div class=\"article_l\">\n" +
                "            <span class=\"link_categories\">\n" +
                "            标签：\n" +
                "              <a href='http://www.csdn.net/tag/url' target=_blank onclick=\"_gaq.push(['_trackEvent','function', 'onclick', 'blog_articles_tag']);\">url</a><a href='http://www.csdn.net/tag/java' target=_blank onclick=\"_gaq.push(['_trackEvent','function', 'onclick', 'blog_articles_tag']);\">java</a><a href='http://www.csdn.net/tag/string' target=_blank onclick=\"_gaq.push(['_trackEvent','function', 'onclick', 'blog_articles_tag']);\">string</a><a href='http://www.csdn.net/tag/exception' target=_blank onclick=\"_gaq.push(['_trackEvent','function', 'onclick', 'blog_articles_tag']);\">exception</a><a href='http://www.csdn.net/tag/action' target=_blank onclick=\"_gaq.push(['_trackEvent','function', 'onclick', 'blog_articles_tag']);\">action</a><a href='http://www.csdn.net/tag/null' target=_blank onclick=\"_gaq.push(['_trackEvent','function', 'onclick', 'blog_articles_tag']);\">null</a>\n" +
                "            </span>\n" +
                "        </div>\n" +
                "        <div class=\"article_r\">\n" +
                "            <span class=\"link_postdate\">2011-10-25 11:32</span>\n" +
                "            <span class=\"link_view\" title=\"阅读次数\">8968人阅读</span>\n" +
                "            <span class=\"link_comments\" title=\"评论次数\"> <a href=\"#comments\" onclick=\"_gaq.push(['_trackEvent','function', 'onclick', 'blog_articles_pinglun'])\">评论</a>(0)</span>\n" +
                "            <span class=\"link_collect\"> <a href=\"javascript:void(0);\" onclick=\"javascript:collectArticle('JAVA方法中通过调用URL来获取其返回的内容','6903290');return false;\" title=\"收藏\">收藏</a></span>\n" +
                "             <span class=\"link_report\"> <a href=\"#report\" onclick=\"javascript:report(6903290,2);return false;\" title=\"举报\">举报</a></span>\n" +
                "\n" +
                "        </div>\n" +
                "    </div>\n" +
                "      <div class=\"category clearfix\">\n" +
                "        <div class=\"category_l\">\n" +
                "           <img src=\"http://static.blog.csdn.net/images/category_icon.jpg\">\n" +
                "            <span>分类：</span>\n" +
                "        </div>\n" +
                "        <div class=\"category_r\">\n" +
                "                    <label  onclick=\"GetCategoryArticles('654355','linwei_1029','top','6903290');\">\n" +
                "                        <span onclick=\"_gaq.push(['_trackEvent','function', 'onclick', 'blog_articles_fenlei']);\">JAVA技术<em>（65）</em></span>\n" +
                "                      <img class=\"arrow-down\" src=\"http://static.blog.csdn.net/images/arrow_triangle _down.jpg\" style=\"display:inline;\">\n" +
                "                      <img class=\"arrow-up\" src=\"http://static.blog.csdn.net/images/arrow_triangle_up.jpg\" style=\"display:none;\">\n" +
                "                        <div class=\"subItem\">\n" +
                "                            <div class=\"subItem_t\"><a  href=\"http://blog.csdn.net/linwei_1029/article/category/654355\">作者同类文章</a><i class=\"J_close\">X</i></div>\n" +
                "                            <ul class=\"subItem_l\" id=\"top_654355\">                            \n" +
                "                            </ul>\n" +
                "                        </div>\n" +
                "                    </label>                    \n" +
                "        </div>\n" +
                "    </div>\n" +
                "    <script   type=\"text/javascript\" src=\"http://static.blog.csdn.net/scripts/category.js\"></script>  \n" +
                "        <div   class=\"bog_copyright\">         \n" +
                "            <p  class=\"copyright_p\" >版权声明：本文为博主原创文章，未经博主允许不得转载。</p>\n" +
                "        </div>\n" +
                "\n" +
                "  \n" +
                "\n" +
                "  \n" +
                "  \n" +
                "     \n" +
                "\n" +
                "<div id=\"article_content\" class=\"article_content\">\n" +
                "\n" +
                "<p>1. &nbsp;先通过以下的方法获取URL连接返回的流信息：</p>\n" +
                "<p></p>\n" +
                "<pre name=\"code\" class=\"java\">\tpublic String getReturnData(String urlString) throws UnsupportedEncodingException {\n" +
                "\t\tString res = &quot;&quot;; \n" +
                "\t\ttry { \n" +
                "\t\t\tURL url = new URL(urlString);\n" +
                "\t\t\tjava.net.HttpURLConnection conn = (java.net.HttpURLConnection)url.openConnection();\n" +
                "\t\t\tconn.setDoOutput(true);\n" +
                "\t\t\tconn.setRequestMethod(&quot;POST&quot;);\n" +
                "\t\t\tjava.io.BufferedReader in = new java.io.BufferedReader(new java.io.InputStreamReader(conn.getInputStream(),&quot;UTF-8&quot;));\n" +
                "\t\t\tString line;\n" +
                "\t\t\twhile ((line = in.readLine()) != null) {\n" +
                "\t\t\t\tres += line;\n" +
                "\t\t\t}\n" +
                "\t\t\tin.close();\n" +
                "\t\t} catch (Exception e) {\n" +
                "\t\t\tlogger.error(&quot;error in wapaction,and e is &quot; + e.getMessage());\n" +
                "\t\t}\n" +
                "//\t\tSystem.out.println(res);\n" +
                "\t\treturn res;\n" +
                "\t}</pre><br>\n" +
                "2. &nbsp;通过以上方法获取流，可以在转化为对应的字符串，在页面显示，或者通过PrintWriter，在ACTION中直接返回内容：\n" +
                "<p></p>\n" +
                "<p>&nbsp;&nbsp; &nbsp; (如果PrintWriter在页面中显示为乱码，则需要设置编码类型：response.setCharacterEncoding(&quot;utf-8&quot;);)</p>\n" +
                "<p></p>\n" +
                "<pre name=\"code\" class=\"java\">\t\t/*设置消息返回格式*/\n" +
                "\t\tresponse.setCharacterEncoding(&quot;utf-8&quot;);\n" +
                "\t\tresponse.setHeader(&quot;Pragma&quot;,&quot;No-cache&quot;);   \n" +
                "        response.setHeader(&quot;Cache-Control&quot;,&quot;no-cache&quot;);   \n" +
                "        response.setDateHeader(&quot;Expires&quot;,0);\n" +
                "\t\tPrintWriter out = response.getWriter();\n" +
                "\t\tout.write(content);\n" +
                "\t\tout.flush();\n" +
                "\t\tout.close();</pre><br>\n" +
                "<br>\n" +
                "<p></p>\n" +
                "<p><br>\n" +
                "</p>\n" +
                "<p><br>\n" +
                "</p>\n" +
                "   \n" +
                "</div>\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "<!-- Baidu Button BEGIN -->\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "<div class=\"bdsharebuttonbox\" style=\"float: right;\">\n" +
                "<a href=\"#\" class=\"bds_more\" data-cmd=\"more\" style=\"background-position:0 0 !important; background-image: url(http://bdimg.share.baidu.com/static/api/img/share/icons_0_16.png?v=d754dcc0.png) !important\"></a>\n" +
                "<a href=\"#\" class=\"bds_qzone\" data-cmd=\"qzone\" title=\"分享到QQ空间\"  style=\"background-position:0 -52px !important\"></a>\n" +
                "<a href=\"#\" class=\"bds_tsina\" data-cmd=\"tsina\" title=\"分享到新浪微博\"style=\"background-position:0 -104px !important\"></a>\n" +
                "<a href=\"#\" class=\"bds_tqq\" data-cmd=\"tqq\" title=\"分享到腾讯微博\"style=\"background-position:0 -260px !important\"></a>\n" +
                "<a href=\"#\" class=\"bds_renren\" data-cmd=\"renren\" title=\"分享到人人网\"style=\"background-position:0 -208px !important\"></a>\n" +
                "<a href=\"#\" class=\"bds_weixin\" data-cmd=\"weixin\" title=\"分享到微信\"style=\"background-position:0 -1612px !important\" ></a>\n" +
                "</div>\n" +
                "<script>window._bd_share_config = { \"common\": { \"bdSnsKey\": {}, \"bdText\": \"\", \"bdMini\": \"1\", \"bdMiniList\": false, \"bdPic\": \"\", \"bdStyle\": \"0\", \"bdSize\": \"16\" }, \"share\": {} }; with (document) 0[(getElementsByTagName('head')[0] || body).appendChild(createElement('script')).src = 'http://bdimg.share.baidu.com/static/api/js/share.js?v=89860593.js?cdnversion=' + ~(-new Date() / 36e5)];</script>\n" +
                "<!-- Baidu Button END -->\n" +
                "\n" +
                "   <link rel=\"stylesheet\" href=\"http://static.blog.csdn.net/css/blog_detail.css\" />\n" +
                "\n" +
                "    \n" +
                "<!--172.16.140.13-->\n" +
                "<ul class=\"article_next_prev\">\n" +
                "            <li class=\"prev_article\"><span  onclick=\"_gaq.push(['_trackEvent','function', 'onclick', 'blog_articles_shangyipian']);location.href='/linwei_1029/article/details/6903245';\">上一篇</span><a href=\"/linwei_1029/article/details/6903245\" onclick=\"_gaq.push(['_trackEvent','function', 'onclick', 'blog_articles_shangyipian'])\">JS校验表单(包括电话，邮编，手机号等等)</a></li>\n" +
                "            <li class=\"next_article\"><span onclick=\"_gaq.push(['_trackEvent','function', 'onclick', 'blog_articles_xiayipian']);location.href='/linwei_1029/article/details/6920438';\">下一篇</span><a href=\"/linwei_1029/article/details/6920438\" onclick=\"_gaq.push(['_trackEvent','function', 'onclick', 'blog_articles_xiayipian'])\">ACTION中直接上传图片或者文档</a></li>\n" +
                "</ul>\n" +
                "\n" +
                "<!-- Baidu Button BEGIN -->\n" +
                "<script type=\"text/javascript\" id=\"bdshare_js\" data=\"type=tools&amp;uid=1536434\" ></script>\n" +
                "<script type=\"text/javascript\" id=\"bdshell_js\"></script>\n" +
                "<script type=\"text/javascript\">\n" +
                "    document.getElementById(\"bdshell_js\").src = \"http://bdimg.share.baidu.com/static/js/shell_v2.js?cdnversion=\" + Math.ceil(new Date()/3600000)\n" +
                "</script>\n" +
                "<!-- Baidu Button END -->\n" +
                "\n" +
                " \n" +
                "\n" +
                "        <div id=\"digg\" ArticleId=\"6903290\">\n" +
                "            <dl id=\"btnDigg\" class=\"digg digg_disable\">\n" +
                "               \n" +
                "                 <dt>顶</dt>\n" +
                "                <dd>1</dd>\n" +
                "            </dl>\n" +
                "            <dl id=\"btnBury\" class=\"digg digg_disable\">\n" +
                "              \n" +
                "                  <dt>踩</dt>\n" +
                "                <dd>0</dd>\n" +
                "            </dl>\n" +
                "        </div>\n" +
                "\n" +
                "\n" +
                "        <div class=\"similar_article\"  style=\"display:none\">\n" +
                "                <h4>我的同类文章</h4>\n" +
                "                <div class=\"similar_c\"style=\"margin:20px 0px 0px 0px\">\n" +
                "                    <div class=\"similar_c_t\">\n" +
                "                                <label class=\"similar_cur\">\n" +
                "                                    <span  style=\"cursor:pointer\"  onclick=\"GetCategoryArticles('654355','linwei_1029','foot','6903290');\">JAVA技术<em>（65）</em></span>\n" +
                "                                </label>\n" +
                "                    </div>\n" +
                "                   \n" +
                "                    <div class=\"similar_wrap tracking-ad\" data-mod=\"popu_141\">\n" +
                "                        <a href=\"http://blog.csdn.net\" style=\"display:none\">http://blog.csdn.net</a>\n" +
                "                        <ul class=\"similar_list fl\">                          \n" +
                "                        </ul>\n" +
                "\n" +
                "                        <ul class=\"similar_list fr\">                           \n" +
                "                        </ul>\n" +
                "                    </div>\n" +
                "                </div>\n" +
                "            </div>    \n" +
                "    <script  type=\"text/javascript\">\n" +
                "        $(function () {\n" +
                "            GetCategoryArticles('654355', 'linwei_1029','foot','6903290');\n" +
                "        });\n" +
                "    </script>\n" +
                "      \n" +
                "</div>\n" +
                "<div id=\"suggest\"></div>\n" +
                "         <script  language=\"javascript\" type='text/javascript'>     \n" +
                "             $(function(){\n" +
                "                 $.get(\"/linwei_1029/svc/GetSuggestContent/6903290\",function(data){\n" +
                "                     $(\"#suggest\").html(data);\n" +
                "                 });     \n" +
                "             });             \n" +
                "         </script>  \n" +
                "\n" +
                "\n" +
                "<style>\n" +
                ".blog-ass-articl dd {\n" +
                "color: #369;\n" +
                "width: 99%; /*修改行*/\n" +
                "float: left;\n" +
                "overflow: hidden;\n" +
                "font: normal normal 12px/23px \"SimSun\";\n" +
                "height: 23px;\n" +
                "margin: 0;\n" +
                "padding: 0 0 0 10px;\n" +
                "margin-right: 30px;\n" +
                "background: url(http://static.blog.csdn.net/skin/default/images/blog-dot-red3.gif) no-repeat 0 10px;\n" +
                "}\n" +
                "</style>\n" +
                "\n" +
                "<dl class=\"blog-ass-articl\" id=\"res-relatived\" > \n" +
                "     <dt><span>猜你在找</span></dt>    \n" +
                "\n" +
                "\n" +
                "   \n" +
                "\n" +
                "\n" +
                "    <div id=\"adCollege\" style=\"width: 42%;float: left;\"> \n" +
                "        <script src=\"http://csdnimg.cn/jobreco/job_reco.js\" type=\"text/javascript\"></script> \n" +
                "        <script type=\"text/javascript\">\n" +
                "            csdn.position.showEdu({\n" +
                "                sourceType: \"blog\",\n" +
                "                searchType: \"detail\",\n" +
                "                searchKey: \"6903290\",\n" +
                "                username: \"\",\n" +
                "                recordcount: \"5\",\n" +
                "                containerId: \"adCollege\" //容器DIV的id。 \n" +
                "            });\n" +
                "        </script> \n" +
                "    </div>  \n" +
                "\n" +
                "    \n" +
                "     <div id=\"res\"  data-mod=\"popu_36\"  class=\"tracking-ad\" style=\"width: 42%;float: left;margin-right: 30px;\"></div>\n" +
                "   \n" +
                "</dl>\n" +
                "\n" +
                "\n" +
                "\n" +
                "<script type=\"text/javascript\">\n" +
                "    $(function () {\n" +
                "        setTimeout(function () {\n" +
                "            var searchtitletags = 'JAVA方法中通过调用URL来获取其返回的内容' + ',' + $(\"#tags\").html();\n" +
                "            searchService({\n" +
                "                index: 'blog',\n" +
                "                query: searchtitletags,\n" +
                "                from: 5,\n" +
                "                size: 5,\n" +
                "                appendTo: '#res',\n" +
                "                url: 'recommend',\n" +
                "                his: 2,\n" +
                "                client: \"blog_cf_enhance\",\n" +
                "                tmpl: '<dd style=\"background:url(http://static.blog.csdn.net/skin/default/images/blog-dot-red3.gif) no-repeat 0 10px;\"><a href=\"#{ url }\" title=\"#{ title }\" strategy=\"#{ strategy }\">#{ title }</a></dd>'\n" +
                "            });\n" +
                "        }, 500);\n" +
                "    });    \n" +
                "\n" +
                " </script>   \n" +
                "\n" +
                "    <div id=\"ad_cen\">        \n" +
                "<!--AdForward Begin:-->\n" +
                "<script type=\"text/javascript\" src=\"http://csdnim.qtmojo.com/main/s?user=csdn|blog|banner2&db=csdnim&border=0&local=yes&js=ie\" charset=\"gbk\"></script>\n" +
                "<!--AdForward End-->    </div>  \n" +
                "\n" +
                "<div class=\"comment_class\">\n" +
                "    <div id=\"comment_title\" class=\"panel_head\">\n" +
                "        <span class=\"see_comment\">查看评论</span><a name=\"comments\"></a></div>\n" +
                "    <div id=\"comment_list\">\n" +
                "    </div>\n" +
                "    <div id=\"comment_bar\">\n" +
                "    </div>\n" +
                "    <div id=\"comment_form\">\n" +
                "    </div>\n" +
                "\n" +
                "    <div class=\"announce\">\n" +
                "        * 以上用户言论只代表其个人观点，不代表CSDN网站的观点或立场<a name=\"reply\"></a><a name=\"quote\"></a></div>\n" +
                "</div>\n" +
                "\n" +
                "<script type=\"text/javascript\">\n" +
                "    var fileName = '6903290';\n" +
                "    var commentscount = 0;\n" +
                "    var islock = false\n" +
                "</script>\n" +
                "<script type=\"text/javascript\" src=\"http://static.blog.csdn.net/scripts/comment.js\"></script>\n" +
                "    <div id=\"ad_bot\">\n" +
                "    </div>\n" +
                "<div id=\"report_dialog\">\n" +
                "</div>\n" +
                "\n" +
                "<div id=\"d-top\"  style=\"bottom:60px;\">\n" +
                "        <a id=\"quick-reply\" class=\"btn btn-top q-reply\" title=\"快速回复\" style=\"display:none;\">\n" +
                "            <img src=\"http://static.blog.csdn.net/images/blog-icon-reply.png\" alt=\"快速回复\">\n" +
                "        </a>    \n" +
                "\n" +
                "    <a id=\"d-top-a\" class=\"btn btn-top backtop\"  style=\"display: none;\" title=\"返回顶部\" onclick=\"_gaq.push(['_trackEvent','function', 'onclick', 'blog_articles_huidaodingbu'])\" style=\"\">         \n" +
                "         <img src=\"http://static.blog.csdn.net/images/top.png\" alt=\"TOP\">\n" +
                "    </a>\n" +
                "</div>\n" +
                "<script type=\"text/javascript\">\n" +
                "    $(function ()\n" +
                "    {\n" +
                "        $(\"#ad_frm_0\").height(\"90px\");\n" +
                "        \n" +
                "        setTimeout(function(){\n" +
                "            $(\"#ad_frm_2\").height(\"200px\");\n" +
                "        },1000);    \n" +
                "    });\n" +
                "  \n" +
                "</script>\n" +
                "<style type=\"text/css\">\n" +
                "    .tag_list\n" +
                "    {\n" +
                "        background: none repeat scroll 0 0 #FFFFFF;\n" +
                "        border: 1px solid #D7CBC1;\n" +
                "        color: #000000;\n" +
                "        font-size: 12px;\n" +
                "        line-height: 20px;\n" +
                "        list-style: none outside none;\n" +
                "        margin: 10px 2% 0 1%;\n" +
                "        padding: 1px;\n" +
                "    }\n" +
                "    .tag_list h5\n" +
                "    {\n" +
                "        background: none repeat scroll 0 0 #E0DBD3;\n" +
                "        color: #47381C;\n" +
                "        font-size: 12px;\n" +
                "        height: 24px;\n" +
                "        line-height: 24px;\n" +
                "        padding: 0 5px;\n" +
                "        margin: 0;\n" +
                "    }\n" +
                "    .tag_list h5 a\n" +
                "    {\n" +
                "        color: #47381C;\n" +
                "    }\n" +
                "    .classify\n" +
                "    {\n" +
                "        margin: 10px 0;\n" +
                "        padding: 4px 12px 8px;\n" +
                "    }\n" +
                "    .classify a\n" +
                "    {\n" +
                "        margin-right: 20px;\n" +
                "        white-space: nowrap;\n" +
                "    }\n" +
                "</style>\n" +
                "\n" +
                "\n" +
                "<div class=\"tag_list\" style=\"display:none\"></div>\n" +
                "  <script  language=\"javascript\" type='text/javascript'>     \n" +
                "      $(function(){\n" +
                "              setTimeout(function(){\n" +
                "                  $.get(\"/linwei_1029/svc/GetTagContent\",function(data){\n" +
                "                      $(\".tag_list\").html(data).show();\n" +
                "                  });     \n" +
                "              });\n" +
                "          },500);                       \n" +
                " </script> \n" +
                "\n" +
                "\n" +
                "<div id=\"pop_win\" style=\"display:none ;position: absolute; z-index: 10000; border: 1px solid rgb(220, 220, 220); top: 222.5px; left: 630px; opacity: 1; background: none 0px 0px repeat scroll rgb(255, 255, 255);\">\n" +
                "    \n" +
                "</div>\n" +
                "<div id=\"popup_mask\"></div>\n" +
                "<style>\n" +
                "    #popup_mask\n" +
                "    {\n" +
                "        position: absolute;\n" +
                "        width: 100%;\n" +
                "        height: 100%;\n" +
                "        background: #000;\n" +
                "        z-index: 9999;\n" +
                "        left: 0px;\n" +
                "        top: 0px;\n" +
                "        opacity: 0.3;\n" +
                "        filter: alpha(opacity=30);\n" +
                "        display: none;\n" +
                "    }\n" +
                "\n" +
                "</style>\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "<script type=\"text/javascript\">\n" +
                "    $(function(){\n" +
                "        setTimeout(function(){\n" +
                "            $(\".comment_body:contains('回复')\").each(function(index,item){\n" +
                "                var u=$(this).text().split('：')[0].toString().replace(\"回复\",\"\")\n" +
                "                var thisComment=$(this);\n" +
                "                if(u)\n" +
                "                {\n" +
                "                    $.getJSON(\"https://passport.csdn.net/get/nick?callback=?\", {users: u}, function(a) {\n" +
                "                        if(a!=null&&a.data!=null&&a.data.length>0)\n" +
                "                        {\n" +
                "                            nick=a.data[0].n; \n" +
                "                            if(u!=nick)\n" +
                "                            {\n" +
                "                                thisComment.text(thisComment.text().replace(u,nick));  \n" +
                "                            }\n" +
                "                        }       \n" +
                "                    });  \n" +
                "                }\n" +
                "            });\n" +
                "        },200);  \n" +
                "        \n" +
                "        setTimeout(function(){\n" +
                "            $(\"a img[src='http://js.tongji.linezing.com/stats.gif']\").parent().css({\"position\":\"absolute\",\"left\":\"50%\"});\n" +
                "        },300);\n" +
                "    });\n" +
                "\n" +
                "    function loginbox(){\n" +
                "        var $logpop=$(\"#pop_win\");\n" +
                "        $logpop.html('<iframe src=\"https://passport.csdn.net/account/loginbox?service=http://static.blog.csdn.net/callback.htm\" frameborder=\"0\" height=\"600\" width=\"400\" scrolling=\"no\"></iframe>');\n" +
                "\n" +
                "        $('#popup_mask').css({\n" +
                "            opacity: 0.5,\n" +
                "            width: $( document ).width() + 'px',\n" +
                "            height:  $( document ).height() + 'px'\n" +
                "        });\n" +
                "        $('#popup_mask').css(\"display\",\"block\");\n" +
                " \n" +
                "        $logpop.css( {\n" +
                "            top: ($( window ).height() - $logpop.height())/ 2  + $( window \n" +
                "       ).scrollTop() + 'px',\n" +
                "            left:($( window ).width() - $logpop.width())/ 2\n" +
                "        } );\n" +
                " \n" +
                "        setTimeout( function () {\n" +
                "            $logpop.show();\n" +
                "            $logpop.css( {\n" +
                "                opacity: 1\n" +
                "            } );\n" +
                "        }, 200 );\n" +
                " \n" +
                "        $('#popup_mask').unbind(\"click\");\n" +
                "        $('#popup_mask').bind(\"click\", function(){\n" +
                "            $('#popup_mask').hide();\n" +
                "            var $clopop = $(\"#pop_win\");\n" +
                "            $(\"#common_ask_div_sc\").css(\"display\",\"none\");\n" +
                "            $clopop.css( {\n" +
                "                opacity: 0\n" +
                "            } );\n" +
                "            setTimeout( function () {\n" +
                "                $clopop.hide();\n" +
                "            }, 350 );\n" +
                "            return false;\n" +
                "        });\n" +
                "    }    \n" +
                "\n" +
                "</script>\n" +
                "                        <div class=\"clear\">\n" +
                "                        </div>\n" +
                "                    </div>                   \n" +
                "                \n" +
                "            </div>\n" +
                "                   \n" +
                "           <div id=\"side\">\n" +
                "    <div class=\"side\">\n" +
                "<div id=\"panel_Profile\" class=\"panel\">\n" +
                "<ul class=\"panel_head\"><span>个人资料</span></ul>\n" +
                "<ul class=\"panel_body profile\">\n" +
                "<div id=\"blog_userface\">\n" +
                "    <a href=\"http://my.csdn.net/linwei_1029\" target=\"_blank\">\n" +
                "    <img src=\"http://avatar.csdn.net/3/3/9/1_linwei_1029.jpg\" title=\"访问我的空间\" style=\"max-width:90%\"/>\n" +
                "    </a>\n" +
                "    <br />\n" +
                "    <span><a href=\"http://my.csdn.net/linwei_1029\" class=\"user_name\" target=\"_blank\">linwei_1029</a></span>\n" +
                "</div>\n" +
                "<div class=\"interact\">\n" +
                "\n" +
                "    <a href=\"javascript:void(0);\" class=\"attent\" id=\"span_add_follow\" title=\"[加关注]\"></a>\n" +
                "\n" +
                " <a href=\"javascript:void(0);\" class=\"letter\"  title=\"[发私信]\" onclick=\"window.open('http://msg.csdn.net/letters/model?receiver=linwei_1029','_blank','height=350,width=700');_gaq.push(['_trackEvent','function', 'onclick', 'blog_articles_sixin'])\"></a>  \n" +
                "</div>\n" +
                "<div id=\"blog_medal\">\n" +
                "                <div id=\"bms_box\">\n" +
                "                                            <a  target=\"_blank\">\n" +
                "                                                    <img src=\"http://c.csdnimg.cn/jifen/images/xunzhang/xunzhang/chizhiyiheng.png\" onmouseover=\"m_over_m(this,4)\" onmouseout=\"m_out_m()\" alt=\"1\" >\n" +
                "                                            </a>\n" +
                "               </div>\n" +
                "</div>\n" +
                "<ul id=\"blog_rank\">\n" +
                "    <li>访问：<span>472255次</span></li>\n" +
                "    <li>积分：<span>6474</span> </li>    \n" +
                "    <li >等级： <span style=\"position:relative;display:inline-block;z-index:1\" >\n" +
                "            <img src=\"http://c.csdnimg.cn/jifen/images/xunzhang/jianzhang/blog6.png\" alt=\"\" style=\"vertical-align: middle;\" id=\"leveImg\">\n" +
                "            <div id=\"smallTittle\" style=\" position: absolute;  left: -24px;  top: 25px;  text-align: center;  width: 101px;  height: 32px;  background-color: #fff;  line-height: 32px;  border: 2px #DDDDDD solid;  box-shadow: 0px 2px 2px rgba (0,0,0,0.1);  display: none;   z-index: 999;\">\n" +
                "            <div style=\"left: 42%;  top: -8px;  position: absolute;  width: 0;  height: 0;  border-left: 10px solid transparent;  border-right: 10px solid transparent;  border-bottom: 8px solid #EAEAEA;\"></div>\n" +
                "            积分：6474 </div>\n" +
                "        </span>  </li>\n" +
                "    <li>排名：<span>第1865名</span></li>\n" +
                "</ul>\n" +
                "<ul id=\"blog_statistics\">\n" +
                "    <li>原创：<span>188篇</span></li>\n" +
                "    <li>转载：<span>36篇</span></li>\n" +
                "    <li>译文：<span>0篇</span></li>\n" +
                "    <li>评论：<span>67条</span></li>\n" +
                "</ul>\n" +
                "</ul>\n" +
                "</div>\n" +
                "\n" +
                "\n" +
                "<div class=\"panel\" id=\"panel_Search\">\n" +
                "    <ul class=\"panel_head\"><span>文章搜索</span></ul>\n" +
                "    <ul class=\"panel_body\">\n" +
                "        <form id=\"frmSearch\" action=\"http://so.csdn.net/search\" class=\"form_search\" target=\"_blank\">\n" +
                "        <span><input id=\"inputSearch\" type=\"text\" class=\"blogsearch\" title=\"请输入关键字\" /></span>\n" +
                "        <input id=\"btnSubmit\" type=\"button\" value=\"搜索\" title=\"search in blog\" />\n" +
                "        <input type=\"hidden\" name=\"q\" id=\"inputQ\" />\n" +
                "        <input type=\"hidden\" name=\"t\" value=\"blog\" />\n" +
                "        <a id=\"btnSearchBlog\" target=\"_blank\"></a>\n" +
                "        </form>\n" +
                "    </ul>\n" +
                "</div>\n" +
                "\n" +
                "<script type=\"text/javascript\">\n" +
                "    $(function () {\n" +
                "        $(\"#btnSubmit\").click(function () {           \n" +
                "            search();\n" +
                "        });\n" +
                "\n" +
                "        $(\"#frmSearch\").submit(function () {\n" +
                "            search();\n" +
                "            return false;\n" +
                "        });\n" +
                "\n" +
                "        function search()\n" +
                "        {\n" +
                "            var url = \"http://so.csdn.net/so/search/s.do?q=\" + encodeURIComponent($(\"#inputSearch\").val()) + \"&u=\" + username + \"&t=blog\";\n" +
                "            window.location.href = url;\n" +
                "        }   \n" +
                "    });\n" +
                "</script><div id=\"panel_Category\" class=\"panel\">\n" +
                "<ul class=\"panel_head\"><span>文章分类</span></ul>\n" +
                "<ul class=\"panel_body\">    \n" +
                "                 <li>\n" +
                "                    <a href=\"/linwei_1029/article/category/654841\" onclick=\"_gaq.push(['_trackEvent','function', 'onclick', 'blog_articles_wenzhangfenlei']); \">设计模式</a><span>(9)</span>\n" +
                "                </li>\n" +
                "                 <li>\n" +
                "                    <a href=\"/linwei_1029/article/category/791297\" onclick=\"_gaq.push(['_trackEvent','function', 'onclick', 'blog_articles_wenzhangfenlei']); \">AJAX知识</a><span>(3)</span>\n" +
                "                </li>\n" +
                "                 <li>\n" +
                "                    <a href=\"/linwei_1029/article/category/659270\" onclick=\"_gaq.push(['_trackEvent','function', 'onclick', 'blog_articles_wenzhangfenlei']); \">Hibernate知识</a><span>(7)</span>\n" +
                "                </li>\n" +
                "                 <li>\n" +
                "                    <a href=\"/linwei_1029/article/category/655768\" onclick=\"_gaq.push(['_trackEvent','function', 'onclick', 'blog_articles_wenzhangfenlei']); \">IDE开发技巧收集</a><span>(5)</span>\n" +
                "                </li>\n" +
                "                 <li>\n" +
                "                    <a href=\"/linwei_1029/article/category/704151\" onclick=\"_gaq.push(['_trackEvent','function', 'onclick', 'blog_articles_wenzhangfenlei']); \">JavaScript知识</a><span>(10)</span>\n" +
                "                </li>\n" +
                "                 <li>\n" +
                "                    <a href=\"/linwei_1029/article/category/654355\" onclick=\"_gaq.push(['_trackEvent','function', 'onclick', 'blog_articles_wenzhangfenlei']); \">JAVA技术</a><span>(66)</span>\n" +
                "                </li>\n" +
                "                 <li>\n" +
                "                    <a href=\"/linwei_1029/article/category/655681\" onclick=\"_gaq.push(['_trackEvent','function', 'onclick', 'blog_articles_wenzhangfenlei']); \">JQuery|ZTree|JBox</a><span>(19)</span>\n" +
                "                </li>\n" +
                "                 <li>\n" +
                "                    <a href=\"/linwei_1029/article/category/662774\" onclick=\"_gaq.push(['_trackEvent','function', 'onclick', 'blog_articles_wenzhangfenlei']); \">JSF知识</a><span>(1)</span>\n" +
                "                </li>\n" +
                "                 <li>\n" +
                "                    <a href=\"/linwei_1029/article/category/726482\" onclick=\"_gaq.push(['_trackEvent','function', 'onclick', 'blog_articles_wenzhangfenlei']); \">JSP</a><span>(4)</span>\n" +
                "                </li>\n" +
                "                 <li>\n" +
                "                    <a href=\"/linwei_1029/article/category/956330\" onclick=\"_gaq.push(['_trackEvent','function', 'onclick', 'blog_articles_wenzhangfenlei']); \">Axis2|CXF|Webservice</a><span>(10)</span>\n" +
                "                </li>\n" +
                "                 <li>\n" +
                "                    <a href=\"/linwei_1029/article/category/655442\" onclick=\"_gaq.push(['_trackEvent','function', 'onclick', 'blog_articles_wenzhangfenlei']); \">Oracle|Mysql|MongoDB</a><span>(32)</span>\n" +
                "                </li>\n" +
                "                 <li>\n" +
                "                    <a href=\"/linwei_1029/article/category/662781\" onclick=\"_gaq.push(['_trackEvent','function', 'onclick', 'blog_articles_wenzhangfenlei']); \">Spring知识</a><span>(14)</span>\n" +
                "                </li>\n" +
                "                 <li>\n" +
                "                    <a href=\"/linwei_1029/article/category/689546\" onclick=\"_gaq.push(['_trackEvent','function', 'onclick', 'blog_articles_wenzhangfenlei']); \">Struts</a><span>(6)</span>\n" +
                "                </li>\n" +
                "                 <li>\n" +
                "                    <a href=\"/linwei_1029/article/category/680124\" onclick=\"_gaq.push(['_trackEvent','function', 'onclick', 'blog_articles_wenzhangfenlei']); \">weblogic服务器知识</a><span>(3)</span>\n" +
                "                </li>\n" +
                "                 <li>\n" +
                "                    <a href=\"/linwei_1029/article/category/654359\" onclick=\"_gaq.push(['_trackEvent','function', 'onclick', 'blog_articles_wenzhangfenlei']); \">英文学习</a><span>(0)</span>\n" +
                "                </li>\n" +
                "                 <li>\n" +
                "                    <a href=\"/linwei_1029/article/category/1567031\" onclick=\"_gaq.push(['_trackEvent','function', 'onclick', 'blog_articles_wenzhangfenlei']); \">Linux|ActiveMQ|Nginx|MemCache</a><span>(18)</span>\n" +
                "                </li>\n" +
                "                 <li>\n" +
                "                    <a href=\"/linwei_1029/article/category/5749681\" onclick=\"_gaq.push(['_trackEvent','function', 'onclick', 'blog_articles_wenzhangfenlei']); \">Redis</a><span>(3)</span>\n" +
                "                </li>\n" +
                "                 <li>\n" +
                "                    <a href=\"/linwei_1029/article/category/5755281\" onclick=\"_gaq.push(['_trackEvent','function', 'onclick', 'blog_articles_wenzhangfenlei']); \">WebSocket</a><span>(5)</span>\n" +
                "                </li>\n" +
                "                 <li>\n" +
                "                    <a href=\"/linwei_1029/article/category/5904565\" onclick=\"_gaq.push(['_trackEvent','function', 'onclick', 'blog_articles_wenzhangfenlei']); \">Netty</a><span>(1)</span>\n" +
                "                </li>\n" +
                "</ul>\n" +
                "</div><div id=\"panel_Archive\" class=\"panel\">\n" +
                "<ul class=\"panel_head\"><span>文章存档</span></ul>\n" +
                "<ul class=\"panel_body\">\n" +
                "<div id=\"archive_list\">\n" +
                "<!--归档统计-->\n" +
                "<li><a href=\"/linwei_1029/article/month/2015/12\">2015年12月</a><span>(4)</span></li><li><a href=\"/linwei_1029/article/month/2015/10\">2015年10月</a><span>(2)</span></li><li><a href=\"/linwei_1029/article/month/2015/08\">2015年08月</a><span>(10)</span></li><li><a href=\"/linwei_1029/article/month/2015/06\">2015年06月</a><span>(1)</span></li><li><a href=\"/linwei_1029/article/month/2014/12\">2014年12月</a><span>(8)</span></li><li><a href=\"/linwei_1029/article/month/2014/11\">2014年11月</a><span>(1)</span></li><li><a href=\"/linwei_1029/article/month/2014/08\">2014年08月</a><span>(2)</span></li><li><a href=\"/linwei_1029/article/month/2014/07\">2014年07月</a><span>(1)</span></li><li><a href=\"/linwei_1029/article/month/2014/06\">2014年06月</a><span>(2)</span></li><li><a href=\"/linwei_1029/article/month/2014/05\">2014年05月</a><span>(6)</span></li><li><a href=\"/linwei_1029/article/month/2014/04\">2014年04月</a><span>(4)</span></li><li><a href=\"/linwei_1029/article/month/2014/02\">2014年02月</a><span>(1)</span></li><li><a href=\"/linwei_1029/article/month/2014/01\">2014年01月</a><span>(2)</span></li><li><a href=\"/linwei_1029/article/month/2013/11\">2013年11月</a><span>(5)</span></li><li><a href=\"/linwei_1029/article/month/2013/10\">2013年10月</a><span>(1)</span></li><li><a href=\"/linwei_1029/article/month/2013/09\">2013年09月</a><span>(1)</span></li><li><a href=\"/linwei_1029/article/month/2013/08\">2013年08月</a><span>(2)</span></li><li><a href=\"/linwei_1029/article/month/2013/04\">2013年04月</a><span>(5)</span></li><li><a href=\"/linwei_1029/article/month/2013/03\">2013年03月</a><span>(5)</span></li><li><a href=\"/linwei_1029/article/month/2013/02\">2013年02月</a><span>(2)</span></li><li><a href=\"/linwei_1029/article/month/2013/01\">2013年01月</a><span>(1)</span></li><li><a href=\"/linwei_1029/article/month/2012/05\">2012年05月</a><span>(1)</span></li><li><a href=\"/linwei_1029/article/month/2012/04\">2012年04月</a><span>(1)</span></li><li><a href=\"/linwei_1029/article/month/2011/12\">2011年12月</a><span>(10)</span></li><li><a href=\"/linwei_1029/article/month/2011/11\">2011年11月</a><span>(7)</span></li><li><a href=\"/linwei_1029/article/month/2011/10\">2011年10月</a><span>(8)</span></li><li><a href=\"/linwei_1029/article/month/2011/08\">2011年08月</a><span>(1)</span></li><li><a href=\"/linwei_1029/article/month/2011/07\">2011年07月</a><span>(2)</span></li><li><a href=\"/linwei_1029/article/month/2011/05\">2011年05月</a><span>(1)</span></li><li><a href=\"/linwei_1029/article/month/2011/03\">2011年03月</a><span>(5)</span></li><li><a href=\"/linwei_1029/article/month/2010/11\">2010年11月</a><span>(3)</span></li><li><a href=\"/linwei_1029/article/month/2010/09\">2010年09月</a><span>(6)</span></li><li><a href=\"/linwei_1029/article/month/2010/08\">2010年08月</a><span>(10)</span></li><li><a href=\"/linwei_1029/article/month/2010/07\">2010年07月</a><span>(13)</span></li><li><a href=\"/linwei_1029/article/month/2010/06\">2010年06月</a><span>(9)</span></li><li><a href=\"/linwei_1029/article/month/2010/05\">2010年05月</a><span>(25)</span></li><li><a href=\"/linwei_1029/article/month/2010/04\">2010年04月</a><span>(24)</span></li><li><a href=\"/linwei_1029/article/month/2010/03\">2010年03月</a><span>(10)</span></li><li><a href=\"/linwei_1029/article/month/2010/02\">2010年02月</a><span>(21)</span></li>\n" +
                "</div>\n" +
                "</ul>\n" +
                "</div>\n" +
                "<div id=\"hotarticls\" class=\"panel\">\n" +
                "<ul class=\"panel_head\">\n" +
                "    <span>       \n" +
                "阅读排行    </span>\n" +
                "</ul>\n" +
                "\n" +
                "<ul class=\"panel_body itemlist\">\n" +
                "<li>\n" +
                "<a href=\"/linwei_1029/article/details/5798310\" title=\"如何在Oracle中导入dmp文件\">如何在Oracle中导入dmp文件</a><span>(21222)</span>\n" +
                "</li>\n" +
                "<li>\n" +
                "<a href=\"/linwei_1029/article/details/5510140\" title=\"Spring的ApplicationContext.getBean()的具体实现\">Spring的ApplicationContext.getBean()的具体实现</a><span>(20079)</span>\n" +
                "</li>\n" +
                "<li>\n" +
                "<a href=\"/linwei_1029/article/details/6990971\" title=\"httpclient通过POST来上传文件，而不是通过流的形式，并在服务端进行解析(通过httpmime.jar来操作)\">httpclient通过POST来上传文件，而不是通过流的形式，并在服务端进行解析(通过httpmime.jar来操作)</a><span>(17509)</span>\n" +
                "</li>\n" +
                "<li>\n" +
                "<a href=\"/linwei_1029/article/details/5813256\" title=\"tomcat启动的时候报 validateJarFile jar not loaded\">tomcat启动的时候报 validateJarFile jar not loaded</a><span>(14167)</span>\n" +
                "</li>\n" +
                "<li>\n" +
                "<a href=\"/linwei_1029/article/details/6873764\" title=\"Spring中报&quot;Could not resolve placeholder&quot;的解决方案\">Spring中报&quot;Could not resolve placeholder&quot;的解决方案</a><span>(11385)</span>\n" +
                "</li>\n" +
                "<li>\n" +
                "<a href=\"/linwei_1029/article/details/16964943\" title=\"ActiveMQ使用线程池实现消息的生产与消费\">ActiveMQ使用线程池实现消息的生产与消费</a><span>(10715)</span>\n" +
                "</li>\n" +
                "<li>\n" +
                "<a href=\"/linwei_1029/article/details/7033566\" title=\"servlet,RMI,webservice之间的区别\">servlet,RMI,webservice之间的区别</a><span>(10155)</span>\n" +
                "</li>\n" +
                "<li>\n" +
                "<a href=\"/linwei_1029/article/details/8720625\" title=\"JBox的简单使用例子\">JBox的简单使用例子</a><span>(10127)</span>\n" +
                "</li>\n" +
                "<li>\n" +
                "<a href=\"/linwei_1029/article/details/5467211\" title=\"org.dom4j.DocumentException:   null   Nested   exception:   null \">org.dom4j.DocumentException:   null   Nested   exception:   null </a><span>(10117)</span>\n" +
                "</li>\n" +
                "<li>\n" +
                "<a href=\"/linwei_1029/article/details/5868082\" title=\"ORA-00937: 不是单组分组函数\">ORA-00937: 不是单组分组函数</a><span>(9283)</span>\n" +
                "</li>\n" +
                "</ul>\n" +
                "</div>\n" +
                "<div id=\"hotarticls2\" class=\"panel\">\n" +
                "<ul class=\"panel_head\"><span>评论排行</span></ul>\n" +
                "<ul class=\"panel_body itemlist\">\n" +
                "<li>\n" +
                "<a href=\"/linwei_1029/article/details/6990971\" title=\"httpclient通过POST来上传文件，而不是通过流的形式，并在服务端进行解析(通过httpmime.jar来操作)\">httpclient通过POST来上传文件，而不是通过流的形式，并在服务端进行解析(通过httpmime.jar来操作)</a><span>(8)</span>\n" +
                "</li>\n" +
                "<li>\n" +
                "<a href=\"/linwei_1029/article/details/5467211\" title=\"org.dom4j.DocumentException:   null   Nested   exception:   null \">org.dom4j.DocumentException:   null   Nested   exception:   null </a><span>(5)</span>\n" +
                "</li>\n" +
                "<li>\n" +
                "<a href=\"/linwei_1029/article/details/16964943\" title=\"ActiveMQ使用线程池实现消息的生产与消费\">ActiveMQ使用线程池实现消息的生产与消费</a><span>(4)</span>\n" +
                "</li>\n" +
                "<li>\n" +
                "<a href=\"/linwei_1029/article/details/8720754\" title=\"JQuery与springmvc实现单个文件上传操作\">JQuery与springmvc实现单个文件上传操作</a><span>(3)</span>\n" +
                "</li>\n" +
                "<li>\n" +
                "<a href=\"/linwei_1029/article/details/5485890\" title=\"hibernateDaoSupport和JdbcDaoSupport的使用\">hibernateDaoSupport和JdbcDaoSupport的使用</a><span>(3)</span>\n" +
                "</li>\n" +
                "<li>\n" +
                "<a href=\"/linwei_1029/article/details/5868082\" title=\"ORA-00937: 不是单组分组函数\">ORA-00937: 不是单组分组函数</a><span>(3)</span>\n" +
                "</li>\n" +
                "<li>\n" +
                "<a href=\"/linwei_1029/article/details/6958775\" title=\"servlet和action中获取URL中的汉字(解决URL中汉字为乱码的问题)\">servlet和action中获取URL中的汉字(解决URL中汉字为乱码的问题)</a><span>(3)</span>\n" +
                "</li>\n" +
                "<li>\n" +
                "<a href=\"/linwei_1029/article/details/8863559\" title=\"使用CXF实现Webservice的服务接口提供以及相关的客户端实现\">使用CXF实现Webservice的服务接口提供以及相关的客户端实现</a><span>(3)</span>\n" +
                "</li>\n" +
                "<li>\n" +
                "<a href=\"/linwei_1029/article/details/5353085\" title=\"object references an unsaved transient instance - save the transient instance before flushing异常\">object references an unsaved transient instance - save the transient instance before flushing异常</a><span>(3)</span>\n" +
                "</li>\n" +
                "<li>\n" +
                "<a href=\"/linwei_1029/article/details/6873764\" title=\"Spring中报&quot;Could not resolve placeholder&quot;的解决方案\">Spring中报&quot;Could not resolve placeholder&quot;的解决方案</a><span>(2)</span>\n" +
                "</li>\n" +
                "</ul>\n" +
                "</div>\n" +
                "<div id=\"homepageArticles\" class=\"panel tracking-ad\" data-mod=\"popu_4\">\n" +
                "<ul class=\"panel_head\"><span>推荐文章</span></ul>\n" +
                "<ul class=\"panel_body\" id=\"ad_commend\">\n" +
                "<ul>\n" +
                "<li><a href=\"http://blog.csdn.net/iispring/article/details/50366021\" \n" +
                "\n" +
                "target=\"_blank\">*源码解析Android中View的layout布局过程</a></li>\n" +
                "<li><a href=\"http://blog.csdn.net/yinwenjie/article/details/50277289\" \n" +
                "\n" +
                "target=\"_blank\">*架构设计：系统间通信（17）——服务治理与Dubbo 中篇（分析）</a></li>\n" +
                "<li><a href=\"http://blog.csdn.net/crazy__chen/article/details/50338895\" \n" +
                "\n" +
                "target=\"_blank\">*一款炫酷Loading动画--加载失败</a></li>\n" +
                "<li><a \n" +
                "\n" +
                "href=\"http://blog.csdn.net/nomasp/article/details/50349172\"target=\"_blank\">*图解堆算法、链表、栈与队列（多图预警）</a></li>\n" +
                "<li><a href=\"http://blog.csdn.net/u014723529/article/details/50352404\" \n" +
                "\n" +
                "target=\"_blank\">*Eclipse快捷键 10个最有用的快捷键</a></li>\n" +
                "<li><a href=\"http://blog.csdn.net/baidu_mtc/article/details/50351342\"  \n" +
                "\n" +
                "target=\"_blank\">* 基于fiddler插件开发的移动测试网络监控与分析</a></li>\n" +
                "\n" +
                "</ul></ul>\n" +
                "</div>\n" +
                "\n" +
                "\n" +
                "<div id=\"newcomments\" class=\"panel\">\n" +
                "<ul class=\"panel_head\"><span>最新评论</span></ul>\n" +
                "<ul class=\"panel_body itemlist\">\n" +
                "    <li>\n" +
                "   \n" +
                "         <a href=\"/linwei_1029/article/details/8863559#comments\">使用CXF实现Webservice的服务接口提供以及相关的客户端实现</a>\n" +
                "    <p style=\"margin:0px;\"><a href=\"/u014459038\" class=\"user_name\">u014459038</a>:\n" +
                "@duanlexianer:像你说的这种是意义不大，但它真正的用途是两个不相关的系统功能调用。这下意...\n" +
                "    </p>\n" +
                "    </li>\n" +
                "    <li>\n" +
                "   \n" +
                "         <a href=\"/linwei_1029/article/details/5813256#comments\">tomcat启动的时候报 validateJarFile jar not loaded</a>\n" +
                "    <p style=\"margin:0px;\"><a href=\"/u013945165\" class=\"user_name\">u013945165</a>:\n" +
                "大腿 我也遇到这个问题 能帮我解决下吗？ Q：513765532\n" +
                "    </p>\n" +
                "    </li>\n" +
                "    <li>\n" +
                "   \n" +
                "         <a href=\"/linwei_1029/article/details/8844939#comments\">slf4j相关的配置信息</a>\n" +
                "    <p style=\"margin:0px;\"><a href=\"/Yitiansui\" class=\"user_name\">Yitiansui</a>:\n" +
                "怎么不要打印在控制台上？\n" +
                "    </p>\n" +
                "    </li>\n" +
                "    <li>\n" +
                "   \n" +
                "         <a href=\"/linwei_1029/article/details/8844939#comments\">使用CXF实现Webservice的服务接口提供以及相关的客户端实现</a>\n" +
                "    <p style=\"margin:0px;\"><a href=\"/han_yankun2009\" class=\"user_name\">han_yankun2009</a>:\n" +
                "初级入门\n" +
                "    </p>\n" +
                "    </li>\n" +
                "    <li>\n" +
                "   \n" +
                "         <a href=\"/linwei_1029/article/details/16964943#comments\">ActiveMQ使用线程池实现消息的生产与消费</a>\n" +
                "    <p style=\"margin:0px;\"><a href=\"/zhangzhq811\" class=\"user_name\">zhangzhq811</a>:\n" +
                "这样好像连接不能关闭的\n" +
                "    </p>\n" +
                "    </li>\n" +
                "    <li>\n" +
                "   \n" +
                "         <a href=\"/linwei_1029/article/details/7010539#comments\">如何获取新插入Oracle数据库Sequence值的5种方法</a>\n" +
                "    <p style=\"margin:0px;\"><a href=\"/u011360395\" class=\"user_name\">u011360395</a>:\n" +
                "@u011360395:就是我在写C#，有区别，唉\n" +
                "    </p>\n" +
                "    </li>\n" +
                "    <li>\n" +
                "   \n" +
                "         <a href=\"/linwei_1029/article/details/7010539#comments\">如何获取新插入Oracle数据库Sequence值的5种方法</a>\n" +
                "    <p style=\"margin:0px;\"><a href=\"/u011360395\" class=\"user_name\">u011360395</a>:\n" +
                "赞一个\n" +
                "    </p>\n" +
                "    </li>\n" +
                "    <li>\n" +
                "   \n" +
                "         <a href=\"/linwei_1029/article/details/6434898#comments\">通过template来把参数添加至对应的字符串中</a>\n" +
                "    <p style=\"margin:0px;\"><a href=\"/tuspark2015\" class=\"user_name\">tuspark2015</a>:\n" +
                "看到博客里面使用了StringTemplateLoader，它的用法很简单的：http://swif...\n" +
                "    </p>\n" +
                "    </li>\n" +
                "    <li>\n" +
                "   \n" +
                "         <a href=\"/linwei_1029/article/details/6434898#comments\">slf4j相关的配置信息</a>\n" +
                "    <p style=\"margin:0px;\"><a href=\"/u012892431\" class=\"user_name\">u012892431</a>:\n" +
                "感谢。如能再给出配置文件详细说明，更好、\n" +
                "    </p>\n" +
                "    </li>\n" +
                "    <li>\n" +
                "   \n" +
                "         <a href=\"/linwei_1029/article/details/5868082#comments\">ORA-00937: 不是单组分组函数</a>\n" +
                "    <p style=\"margin:0px;\"><a href=\"/snowwhite_can_do_it\" class=\"user_name\">snowwhite_can_do_it</a>:\n" +
                "讲得好模糊呀，还是不太清楚\n" +
                "    </p>\n" +
                "    </li>\n" +
                "</ul>\n" +
                "</div>\n" +
                "    </div>\n" +
                "    <div class=\"clear\">\n" +
                "    </div>\n" +
                " </div>   \n" +
                "            <div class=\"clear\">\n" +
                "            </div>\n" +
                "        </div>\n" +
                "        \n" +
                "\n" +
                "<script type=\"text/javascript\" src=\"http://c.csdnimg.cn/rabbit/cnick/cnick.js\"></script>\n" +
                "<script type=\"text/javascript\" src=\"http://static.blog.csdn.net/scripts/newblog.min.js\"></script>\n" +
                "\n" +
                "\n" +
                "<script type=\"text/javascript\" src=\"http://medal.blog.csdn.net/showblogmedal.ashx?blogid=789060\"></script>\n" +
                "<script type=\"text/javascript\" src=\"http://static.blog.csdn.net/scripts/JavaScript1.js\"></script>\n" +
                "\n" +
                "    <script type=\"text/javascript\" src=\"http://passport.csdn.net/content/loginbox/login.js\"></script>\n" +
                "<script type=\"text/javascript\">document.write(\"<img src=http://counter.csdn.net/pv.aspx?id=24 border=0 width=0 height=0>\");</script>\n" +
                "<script type=\"text/javascript\" src=\"http://www.csdn.net/ui/scripts/Csdn/counter.js\"></script>\n" +
                "<script type=\"text/javascript\" src=\"http://ad.csdn.net/scripts/ad-blog.js\"></script>\n" +
                "<script type=\"text/javascript\">\n" +
                "    $(function () {\n" +
                "        function __get_code_toolbar(snippet_id) {\n" +
                "            return $(\"<a href='https://code.csdn.net/snippets/\"\n" +
                "                    + snippet_id\n" +
                "                    + \"' target='_blank' title='在CODE上查看代码片' style='text-indent:0;'><img src='https://code.csdn.net/assets/CODE_ico.png' width=12 height=12 alt='在CODE上查看代码片' style='position:relative;top:1px;left:2px;'/></a>\"\n" +
                "                    + \"<a href='https://code.csdn.net/snippets/\"\n" +
                "                    + snippet_id\n" +
                "                    + \"/fork' target='_blank' title='派生到我的代码片'  style='text-indent:0;'><img src='https://code.csdn.net/assets/ico_fork.svg' width=12 height=12 alt='派生到我的代码片' style='position:relative;top:2px;left:2px;'/></a>\");\n" +
                "        }\n" +
                "        \n" +
                "        $(\"[code_snippet_id]\").each(function () {\n" +
                "            __s_id = $(this).attr(\"code_snippet_id\");\n" +
                "            if (__s_id != null && __s_id != \"\" && __s_id != 0 && parseInt(__s_id) > 70020) {\n" +
                "                __code_tool = __get_code_toolbar(__s_id);\n" +
                "                $(this).prev().find(\".tools\").append(__code_tool);\n" +
                "            }\n" +
                "        });\n" +
                "\n" +
                "        $(\".bar\").show();\n" +
                "    });\n" +
                "</script>\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "    </div>\n" +
                "      <!--new top-->\n" +
                "    \n" +
                "    <script id=\"csdn-toolbar-id\" btnId=\"header_notice_num\" wrapId=\"note1\" count=\"5\" subCount=\"5\" type=\"text/javascript\" src=\"http://c.csdnimg.cn/public/common/toolbar/js/toolbar.js\"></script>\n" +
                "     <!--new top-->\n" +
                "   \n" +
                "    <link href=\"http://c.csdnimg.cn/comm_ask/css/ask_float_block.css\" type=\"text/css\" rel=\"stylesheet\" />\n" +
                "    <script language='JavaScript' type='text/javascript' src='http://c.csdnimg.cn/comm_ask/js/libs/wmd.js'></script>\n" +
                "    <script language='JavaScript' type='text/javascript' src='http://c.csdnimg.cn/comm_ask/js/libs/showdown.js'></script>\n" +
                "    <script language='JavaScript' type='text/javascript' src='http://c.csdnimg.cn/comm_ask/js/libs/prettify.js'></script>\n" +
                "    <script language='JavaScript' type='text/javascript' src='http://c.csdnimg.cn/comm_ask/js/apps/ask_float_block.js'></script>\n" +
                "   \n" +
                "</body>\n" +
                "</html>   \n" +
                " ";
        titleKeyWords = new TitleKeyWords(s, keyNum);
//            titleKeyWords = new TitleKeyWords(htmlContent, keyNum);
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
