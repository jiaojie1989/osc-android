package net.oschina.app.api.remote;

import android.content.Context;
import android.support.v4.util.Pair;
import android.text.TextUtils;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import net.oschina.app.AppContext;
import net.oschina.app.AppException;
import net.oschina.app.api.ApiHttpClient;
import net.oschina.app.bean.EventApplyData;
import net.oschina.app.bean.NewsList;
import net.oschina.app.bean.Report;
import net.oschina.app.bean.Tweet;
import net.oschina.app.improve.account.AccountHelper;
import net.oschina.app.improve.bean.simple.About;
import net.oschina.app.team.bean.Team;
import net.oschina.app.util.StringUtils;

import org.kymjs.kjframe.utils.KJLoger;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

public class OSChinaApi {

    /**
     * 登陆
     *
     * @param username
     * @param password
     * @param handler
     */
    public static void login(String username, String password,
                             AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("username", username);
        params.put("pwd", password);
        params.put("keep_login", 1);
        String loginurl = "action/api/login_validate";
        ApiHttpClient.post(loginurl, params, handler);
    }

    public static void openIdLogin(String s) {

    }

    /**
     * 获取新闻列表
     *
     * @param catalog 类别 （1，2，3）
     * @param page    第几页
     * @param handler
     */
    public static void getNewsList(int catalog, int page,
                                   AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("catalog", catalog);
        params.put("pageIndex", page);
        params.put("pageSize", AppContext.PAGE_SIZE);
        if (catalog == NewsList.CATALOG_WEEK) {
            params.put("show", "week");
        } else if (catalog == NewsList.CATALOG_MONTH) {
            params.put("show", "month");
        }
        ApiHttpClient.get("action/api/news_list", params, handler);
    }

    public static void getBlogList(String type, int pageIndex,
                                   AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("type", type);
        params.put("pageIndex", pageIndex);
        params.put("pageSize", AppContext.PAGE_SIZE);
        ApiHttpClient.get("action/api/blog_list", params, handler);
    }

    public static void getPostList(int catalog, int page,
                                   AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("catalog", catalog);
        params.put("pageIndex", page);
        params.put("pageSize", AppContext.PAGE_SIZE);
        ApiHttpClient.get("action/api/post_list", params, handler);
    }

    public static void getPostListByTag(String tag, int page,
                                        AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("tag", tag);
        params.put("pageIndex", page);
        params.put("pageSize", AppContext.PAGE_SIZE);
        ApiHttpClient.get("action/api/post_list", params, handler);
    }

    public static void getTweetList(int uid, int page,
                                    AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("uid", uid);
        params.put("pageIndex", page);
        params.put("pageSize", AppContext.PAGE_SIZE);
        ApiHttpClient.get("action/api/tweet_list", params, handler);
    }

    public static void getTweetTopicList(int page, String topic,
                                         AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("pageIndex", page);
        params.put("title", topic);
        params.put("pageSize", AppContext.PAGE_SIZE);
        ApiHttpClient.get("action/api/tweet_topic_list", params, handler);
    }

    /**
     * get   TweetLikeList
     *
     * @param pageIndex begin 0
     * @param handler   asyncHttpResponseHandler
     */
    public static void getTweetLikeList(int pageIndex, AsyncHttpResponseHandler handler) {
        ApiHttpClient.get("action/api/my_tweet_like_list?pageIndex=" + pageIndex + "&pageSize=" + 20, handler);
    }

    public static void pubLikeTweet(int tweetId, int authorId,
                                    AsyncHttpResponseHandler handler, Context context) {

        RequestParams params = new RequestParams();
        params.put("tweetid", tweetId);
        params.put("uid", AccountHelper.getUserId());
        params.put("ownerOfTweet", authorId);
        ApiHttpClient.post("action/api/tweet_like", params, handler);
    }

    public static void pubUnLikeTweet(int tweetId, int authorId,
                                      AsyncHttpResponseHandler handler, Context context) {
        RequestParams params = new RequestParams();
        params.put("tweetid", tweetId);
        params.put("uid", AccountHelper.getUserId());
        params.put("ownerOfTweet", authorId);
        ApiHttpClient.post("action/api/tweet_unlike", params, handler);
    }

    public static void getTweetLikeList(int tweetId, int page,
                                        AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("tweetid", tweetId);
        params.put("pageIndex", page);
        params.put("pageSize", AppContext.PAGE_SIZE);
        ApiHttpClient.get("action/api/tweet_like_list", params, handler);

    }

    public static void getActiveList(int uid, int catalog, int page,
                                     AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("uid", uid);
        params.put("catalog", catalog);
        params.put("pageIndex", page);
        params.put("pageSize", AppContext.PAGE_SIZE);
        ApiHttpClient.get("action/api/active_list", params, handler);
    }

    public static void getFriendList(int uid, int relation, int page,
                                     AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("uid", uid);
        params.put("relation", relation);
        params.put("pageIndex", page);
        params.put("pageSize", AppContext.PAGE_SIZE);
        ApiHttpClient.get("action/api/friends_list", params, handler);
    }

    /**
     * 获取所有关注好友列表
     *
     * @param uid     指定用户UID
     * @param handler
     */
    public static void getAllFriendsList(int uid, int relation, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("uid", uid);
        params.put("relation", relation);
        params.put("all", 1);
        ApiHttpClient.get("action/api/friends_list", params, handler);
    }

    /**
     * 获取用户收藏
     *
     * @param uid     指定用户UID
     * @param type    收藏类型: 0:全部收藏　1:软件　2:话题　3:博客　4:新闻　5:代码
     * @param page
     * @param handler
     */
    public static void getFavoriteList(int uid, int type, int page,
                                       AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("uid", uid);
        params.put("type", type);
        params.put("pageIndex", page);
        params.put("pageSize", AppContext.PAGE_SIZE);
        ApiHttpClient.get("action/api/favorite_list", params, handler);
    }

    /**
     * 分类列表
     *
     * @param tag     第一级:0
     * @param handler
     */
    public static void getSoftwareCatalogList(int tag,
                                              AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams("tag", tag);
        ApiHttpClient.get("action/api/softwarecatalog_list", params, handler);
    }

    public static void getSoftwareTagList(int searchTag, int page,
                                          AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("searchTag", searchTag);
        params.put("pageIndex", page);
        params.put("pageSize", AppContext.PAGE_SIZE);
        ApiHttpClient.get("action/api/softwaretag_list", params, handler);
    }

    /**
     * @param searchTag 　　软件分类　　推荐:recommend 最新:time 热门:view 国产:list_cn
     * @param page
     * @param handler
     */
    public static void getSoftwareList(String searchTag, int page,
                                       AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("searchTag", searchTag);
        params.put("pageIndex", page);
        params.put("pageSize", AppContext.PAGE_SIZE);
        ApiHttpClient.get("action/api/software_list", params, handler);
    }

    /**
     * 获取评论列表
     *
     * @PARAM ID
     * @PARAM CATALOG
     * 1新闻 2帖子 3动弹 4动态
     * @PARAM PAGE
     * @PARAM HANDLER
     */
    public static void getCommentList(int id, int catalog, int page,
                                      AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("catalog", catalog);
        params.put("id", id);
        params.put("pageIndex", page);
        params.put("pageSize", AppContext.PAGE_SIZE);
        params.put("clientType", "android");
        ApiHttpClient.get("action/api/comment_list", params, handler);
    }

    public static void getBlogCommentList(int id, int page,
                                          AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("id", id);
        params.put("pageIndex", page);
        params.put("pageSize", AppContext.PAGE_SIZE);
        ApiHttpClient.get("action/api/blogcomment_list", params, handler);
    }

    public static void getChatMessageList(int friendId, int page, AsyncHttpResponseHandler
            handler) {
        RequestParams params = new RequestParams();
        params.put("id", friendId);
        params.put("pageIndex", page);
        params.put("pageSize", AppContext.PAGE_SIZE);
        ApiHttpClient.get("action/api/message_detail", params, handler);
    }

    public static void getUserInformation(int uid, int hisuid, String hisname,
                                          int pageIndex, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("uid", uid);
        params.put("hisuid", hisuid);
        params.put("hisname", hisname);
        params.put("pageIndex", pageIndex);
        params.put("pageSize", AppContext.PAGE_SIZE);
        ApiHttpClient.get("action/api/user_information", params, handler);
    }

    @SuppressWarnings("deprecation")
    public static void getUserBlogList(int authoruid, final String authorname,
                                       final int uid, final int pageIndex,
                                       AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("authoruid", authoruid);
        params.put("authorname", URLEncoder.encode(authorname));
        params.put("uid", uid);
        params.put("pageIndex", pageIndex);
        params.put("pageSize", AppContext.PAGE_SIZE);
        ApiHttpClient.get("action/api/userblog_list", params, handler);
    }

    public static void updateRelation(long uid, long hisuid, int newrelation,
                                      AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("uid", uid);
        params.put("hisuid", hisuid);
        params.put("newrelation", newrelation);
        ApiHttpClient.post("action/api/user_updaterelation", params, handler);
    }

    public static void getMyInformation(int uid,
                                        AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("uid", uid);
        ApiHttpClient.get("action/api/my_information", params, handler);
    }

    /**
     * 获取新闻明细
     *
     * @param id      新闻的id
     * @param handler
     */
    public static void getNewsDetail(int id, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams("id", id);
        ApiHttpClient.get("action/api/news_detail", params, handler);
    }

    public static void getBlogDetail(int id, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams("id", id);
        ApiHttpClient.get("action/api/blog_detail", params, handler);
    }

    /**
     * 获取软件详情
     *
     * @param ident
     * @param handler
     */
    public static void getSoftwareDetail(String ident,
                                         AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams("ident",
                ident);
        ApiHttpClient.get("action/api/software_detail", params, handler);
    }

    /***
     * 通过id获取软件详情
     *
     * @param id
     * @param handler
     */
    public static void getSoftwareDetail(int id, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams("id",
                id);
        ApiHttpClient.get("action/api/software_detail", params, handler);
    }

    public static void getPostDetail(int id, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams("id", id);
        ApiHttpClient.get("action/api/post_detail", params, handler);
    }

    public static void getTweetDetail(int id, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams("id", id);
        ApiHttpClient.get("action/api/tweet_detail", params, handler);
    }

    /**
     * 用户针对某个新闻，帖子，动弹，消息发表评论的接口，参数使用POST方式提交
     *
     * @param catalog        　　 1新闻　　2 帖子　　３　动弹　　４消息中心
     * @param id             被评论的某条新闻，帖子，动弹或者某条消息的id
     * @param uid            当天登陆用户的UID
     * @param content        发表的评论内容
     * @param isPostToMyZone 是否转发到我的空间，０不转发　　１转发到我的空间（注意该功能之对某条动弹进行评论是有效，其他情况下服务器借口可以忽略该参数）
     * @param handler
     */
    public static void publicComment(int catalog, long id, int uid,
                                     String content, int isPostToMyZone, AsyncHttpResponseHandler
                                             handler) {
        RequestParams params = new RequestParams();
        params.put("catalog", catalog);
        params.put("id", id);
        params.put("uid", uid);
        params.put("content", content);
        params.put("isPostToMyZone", isPostToMyZone);
        ApiHttpClient.post("action/api/comment_pub", params, handler);
    }

    public static void replyComment(int id, int catalog, int replyid,
                                    int authorid, int uid, String content,
                                    AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("catalog", catalog);
        params.put("id", id);
        params.put("uid", uid);
        params.put("content", content);
        params.put("replyid", replyid);
        params.put("authorid", authorid);
        ApiHttpClient.post("action/api/comment_reply", params, handler);
    }

    public static void publicBlogComment(long blog, int uid, String content,
                                         AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("blog", blog);
        params.put("uid", uid);
        params.put("content", content);
        ApiHttpClient.post("action/api/blogcomment_pub", params, handler);
    }

    public static void replyBlogComment(long blog, long uid, String content,
                                        long reply_id, long objuid, AsyncHttpResponseHandler
                                                handler) {
        RequestParams params = new RequestParams();
        params.put("blog", blog);
        params.put("uid", uid);
        params.put("content", content);
        params.put("reply_id", reply_id);
        params.put("objuid", objuid);
        ApiHttpClient.post("action/api/blogcomment_pub", params, handler);
    }

    public static void pubTweet(Tweet tweet, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("uid", tweet.getAuthorid());
        params.put("msg", tweet.getBody());

        // Map<String, File> files = new HashMap<String, File>();
        if (!TextUtils.isEmpty(tweet.getImageFilePath())) {
            try {
                params.put("img", new File(tweet.getImageFilePath()));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        if (!TextUtils.isEmpty(tweet.getAudioPath())) {
            try {
                params.put("amr", new File(tweet.getAudioPath()));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        ApiHttpClient.post("action/api/tweet_pub", params, handler);
    }


    public static void pubSoftWareTweet(Tweet tweet, int softid,
                                        AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("uid", tweet.getAuthorid());
        params.put("msg", tweet.getBody());
        params.put("project", softid);
        ApiHttpClient.post("action/api/software_tweet_pub", params, handler);
    }

    /**
     * pub software tweet
     *
     * @param content content
     * @param handler handler
     */
    public static void pubSoftwareTweet(String content, AsyncHttpResponseHandler handler) {
        if (!TextUtils.isEmpty(content)) {
            RequestParams params = new RequestParams();
            params.put("content", content);
            ApiHttpClient.post("/action/apiv2/tweet", params, handler);
        }
    }

    /**
     * del software tweet
     *
     * @param sourceId tweet's id
     * @param handler  handler
     */
    public static void delSoftwareTweet(long sourceId, AsyncHttpResponseHandler handler) {
        if (sourceId <= 0) return;
        RequestParams params = new RequestParams();
        params.put("sourceId", sourceId);
        ApiHttpClient.post("/action/apiv2/tweet_delete", params, handler);

    }

    public static void deleteTweet(int uid, int tweetid,
                                   AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("uid", uid);
        params.put("tweetid", tweetid);
        ApiHttpClient.post("action/api/tweet_delete", params, handler);
    }

    public static void deleteComment(int id, int catalog, int replyid,
                                     int authorid, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("id", id);
        params.put("catalog", catalog);
        params.put("replyid", replyid);
        params.put("authorid", authorid);
        ApiHttpClient.post("action/api/comment_delete", params, handler);
    }

    public static void deleteBlog(int uid, int authoruid, int id,
                                  AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("uid", uid);
        params.put("authoruid", authoruid);
        params.put("id", id);
        ApiHttpClient.post("action/api/userblog_delete", params, handler);
    }

    public static void deleteBlogComment(int uid, int blogid, int replyid,
                                         int authorid, int owneruid, AsyncHttpResponseHandler
                                                 handler) {
        RequestParams params = new RequestParams();
        params.put("uid", uid);
        params.put("blogid", blogid);
        params.put("replyid", replyid);
        params.put("authorid", authorid);
        params.put("owneruid", owneruid);
        ApiHttpClient.post("action/api/blogcomment_delete", params, handler);
    }

    /**
     * 用户添加收藏
     *
     * @param uid   用户UID
     * @param objid 比如是新闻ID 或者问答ID 或者动弹ID
     * @param type  1:软件 2:话题 3:博客 4:新闻 5:代码
     */
    public static void addFavorite(int uid, long objid, int type,
                                   AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("uid", uid);
        params.put("objid", objid);
        params.put("type", type);
        ApiHttpClient.post("action/api/favorite_add", params, handler);
    }

    public static void delFavorite(int uid, long objid, int type,
                                   AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("uid", uid);
        params.put("objid", objid);
        params.put("type", type);
        ApiHttpClient.post("action/api/favorite_delete", params, handler);
    }

    public static void getSearchList(String catalog, String content,
                                     int pageIndex, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("catalog", catalog);
        params.put("content", content);
        params.put("pageIndex", pageIndex);
        params.put("pageSize", AppContext.PAGE_SIZE);
        ApiHttpClient.get("action/api/search_list", params, handler);
    }

    public static void publicMessage(int uid, int receiver, String content,
                                     AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("uid", uid);
        params.put("receiver", receiver);
        params.put("content", content);

        ApiHttpClient.post("action/api/message_pub", params, handler);
    }

    public static void deleteMessage(int uid, int friendid,
                                     AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("uid", uid);
        params.put("friendid", friendid);
        ApiHttpClient.post("action/api/message_delete", params, handler);
    }

    public static void forwardMessage(int uid, String receiverName,
                                      String content, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("uid", uid);
        params.put("receiverName", receiverName);
        params.put("content", content);
        ApiHttpClient.post("action/api/message_pub", params, handler);
    }

    public static void getMessageList(int uid, int pageIndex,
                                      AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("uid", uid);
        params.put("pageIndex", pageIndex);
        params.put("pageSize", AppContext.PAGE_SIZE);
        ApiHttpClient.get("action/api/message_list", params, handler);
    }

    public static void updatePortrait(int uid, File portrait,
                                      AsyncHttpResponseHandler handler) throws
            FileNotFoundException {
        RequestParams params = new RequestParams();
        params.put("uid", uid);
        params.put("portrait", portrait);
        ApiHttpClient.post("action/api/portrait_update", params, handler);
    }

    public static void getNotices(AsyncHttpResponseHandler handler, Context context) {
        RequestParams params = new RequestParams();
        params.put("uid", AccountHelper.getUserId());
        ApiHttpClient.get("action/api/user_notice", params, handler);
    }

    /**
     * 清空通知消息
     *
     * @param uid
     * @param type 1:@我的信息 2:未读消息 3:评论个数 4:新粉丝个数
     * @return
     * @throws AppException
     */
    public static void clearNotice(int uid, int type,
                                   AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("uid", uid);
        params.put("type", type);
        ApiHttpClient.post("action/api/notice_clear", params, handler);
    }

    public static void singnIn(String url, AsyncHttpResponseHandler handler) {
        ApiHttpClient.getDirect(url, handler);
    }

    /**
     * 获取软件的动态列表
     *
     * @param softid
     * @param handler
     */
    public static void getSoftTweetList(int softid, int page,
                                        AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("project", softid);
        params.put("pageIndex", page);
        params.put("pageSize", AppContext.PAGE_SIZE);
        ApiHttpClient.get("action/api/software_tweet_list", params, handler);
    }

    /**
     * get software tweet list
     *
     * @param tag     software tag
     * @param handler handler
     */
    public static void getSoftwareTweetList(String tag, String pageToken, TextHttpResponseHandler handler) {
        if (TextUtils.isEmpty(tag)) return;
        RequestParams params = new RequestParams();
        params.put("tag", tag);
        if (!TextUtils.isEmpty(pageToken))
            params.put("pageToken", pageToken);
        ApiHttpClient.get("action/apiv2/tweets", params, handler);
    }

    /**
     * pub tweet like status
     *
     * @param sourceId source id
     * @param handler  handler
     */
    public static void pubSoftwareLike(long sourceId, TextHttpResponseHandler handler) {
        if (sourceId <= 0) return;
        RequestParams params = new RequestParams();
        params.put("sourceId", sourceId);
        ApiHttpClient.post("action/apiv2/tweet_like_reverse", params, handler);
    }

    public static void checkUpdate(AsyncHttpResponseHandler handler) {
        ApiHttpClient.get("MobileAppVersion.xml", handler);
    }

    /**
     * 查找用户
     *
     * @param username
     * @param handler
     */
    public static void findUser(String username,
                                AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("name", username);
        ApiHttpClient.get("action/api/find_user", params, handler);
    }

    /**
     * 获取活动列表
     *
     * @param pageIndex
     * @param uid       <= 0 近期活动 实际的用户ID 则获取用户参与的活动列表，需要已登陆的用户
     * @param handler
     */
    public static void getEventList(int pageIndex, int uid,
                                    AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("pageIndex", pageIndex);
        params.put("uid", uid);
        params.put("pageSize", AppContext.PAGE_SIZE);
        ApiHttpClient.get("action/api/event_list", params, handler);
    }

    /**
     * 获取某活动已出席的人员列表
     *
     * @param eventId
     * @param pageIndex
     * @param handler
     */
    public static void getEventApplies(int eventId, int pageIndex,
                                       AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("pageIndex", pageIndex);
        params.put("event_id", eventId);
        params.put("pageSize", AppContext.PAGE_SIZE);
        ApiHttpClient.get("action/api/event_attend_user", params, handler);
    }

    /**
     * 举报
     *
     * @param report
     * @param handler
     */
    public static void report(Report report, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("obj_id", report.getObjId());
        params.put("url", report.getUrl());
        params.put("obj_type", report.getObjType());
        params.put("reason", report.getReason());
        if (report.getOtherReason() != null
                && !StringUtils.isEmpty(report.getOtherReason())) {
            params.put("memo", report.getOtherReason());
        }
        ApiHttpClient.post("action/communityManage/report", params, handler);
    }

    /**
     * 摇一摇，随机数据
     *
     * @param handler
     */
    public static void shake(AsyncHttpResponseHandler handler) {
        shake(-1, handler);
    }

    /**
     * 摇一摇指定请求类型
     */
    public static void shake(int type, AsyncHttpResponseHandler handler) {
        String inter = "action/api/rock_rock";
        if (type > 0) {
            inter = (inter + "/?type=" + type);
        }
        ApiHttpClient.get(inter, handler);
    }

    /**
     * 活动报名
     *
     * @param data
     * @param handler
     */
    public static void eventApply(EventApplyData data,
                                  AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("event", data.getEvent());
        params.put("user", data.getUser());
        params.put("name", data.getName());
        params.put("gender", data.getGender());
        params.put("mobile", data.getPhone());
        params.put("company", data.getCompany());
        params.put("job", data.getJob());
        if (!StringUtils.isEmpty(data.getRemark())) {
            params.put("misc_info", data.getRemark());
        }
        ApiHttpClient.post("action/api/event_apply", params, handler);
    }

    /**
     * team动态
     *
     * @param team
     * @param page
     * @param handler
     */
    public static void teamDynamic(Team team, int page,
                                   AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        // int uid = AppContext.getInstance().getLoginUid();
        // params.put("uid", uid);
        params.put("teamid", team.getId());
        params.put("pageIndex", page);
        params.put("pageSize", 20);
        params.put("type", "all");
        ApiHttpClient.get("action/api/team_active_list", params, handler);
    }

    /**
     * 获取team列表
     *
     * @param handler
     */
    public static void teamList(AsyncHttpResponseHandler handler, Context context) {
        RequestParams params = new RequestParams();
        params.put("uid", AccountHelper.getUserId());
        ApiHttpClient.get("action/api/team_list", params, handler);
    }

    /**
     * 获取team成员列表
     *
     * @param handler
     */
    public static void getTeamMemberList(int teamid,
                                         AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("teamid", teamid);
        ApiHttpClient.get("action/api/team_member_list", params, handler);
    }

    /**
     * 获取team成员个人信息
     *
     * @param handler
     */
    public static void getTeamUserInfo(String teamid, String uid,
                                       int pageIndex, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("teamid", teamid);
        params.put("uid", uid);
        params.put("pageIndex", pageIndex);
        params.put("pageSize", 20);
        ApiHttpClient.get("action/api/team_user_information", params, handler);
    }

    /**
     * 获取我的任务中进行中、未完成、已完成等状态的数量
     */
    public static void getMyIssueState(String teamid, String uid,
                                       AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("teamid", teamid);
        params.put("uid", uid);
        ApiHttpClient.get("action/api/team_user_issue_information", params,
                handler);
    }

    /**
     * 获取指定用户的动态
     */
    public static void getUserDynamic(int teamid, String uid, int pageIndex,
                                      AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("teamid", teamid);
        params.put("pageIndex", pageIndex);
        params.put("pageSize", 20);
        params.put("type", "git");
        params.put("uid", uid);
        ApiHttpClient.get("action/api/team_active_list", params, handler);
    }

    /**
     * 动态详情
     *
     * @param activeid
     * @param teamid
     * @param uid
     * @param handler
     */
    public static void getDynamicDetail(int activeid, int teamid, int uid,
                                        AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("teamid", teamid);
        params.put("uid", uid);
        params.put("activeid", activeid);
        ApiHttpClient.get("action/api/team_active_detail", params, handler);
    }

    /**
     * 获取指定用户的任务
     */
    public static void getMyIssue(String teamid, String uid, int pageIndex,
                                  String type, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("teamid", teamid);
        params.put("uid", uid);
        params.put("pageIndex", pageIndex);
        params.put("pageSize", 20);
        params.put("state", type);
        params.put("projectid", "-1");
        ApiHttpClient.get("action/api/team_issue_list", params, handler);
    }

    /**
     * 获取指定周周报
     *
     * @param teamid
     * @param year
     * @param week
     * @param handler
     */
    public static void getDiaryFromWhichWeek(int teamid, int year, int week,
                                             AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("teamid", teamid);
        params.put("year", year);
        params.put("week", week);
        ApiHttpClient.get("action/api/team_diary_list", params, handler);
    }

    /**
     * 删除一个便签
     *
     * @param id  便签id
     * @param uid 用户id
     */
    public static void deleteNoteBook(int id, long uid,
                                      AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("uid", uid);
        params.put("id", id); // 便签id
        ApiHttpClient
                .get("action/api/team_stickynote_recycle", params, handler);
    }

    public static void getNoteBook(int uid, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("uid", uid);
        ApiHttpClient.get("action/api/team_sticky_list", params, handler);
    }

    /**
     * 获取指定周报的详细信息
     *
     * @param teamid
     * @param diaryid
     * @param handler
     */
    public static void getDiaryDetail(int teamid, int diaryid,
                                      AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("teamid", teamid);
        params.put("diaryid", diaryid);
        ApiHttpClient.get("action/api/team_diary_detail", params, handler);
    }

    /**
     * diary评论列表
     *
     * @param teamid
     * @param diaryid
     * @param handler
     */
    public static void getDiaryComment(int teamid, int diaryid,
                                       AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("teamid", teamid);
        params.put("id", diaryid);
        params.put("type", "diary");
        params.put("pageIndex", 0);
        params.put("pageSize", "20");
        KJLoger.debug(teamid + "==getDiaryComment接口=" + diaryid);
        ApiHttpClient
                .get("action/api/team_reply_list_by_type", params, handler);
    }

    /**
     * 周报评论（以后可改为全局评论）
     *
     * @param uid
     * @param teamid
     * @param diaryId
     * @param content
     * @param handler
     */
    public static void sendComment(int uid, int teamid, int diaryId,
                                   String content, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("uid", uid);
        params.put("teamid", teamid);
        params.put("type", "118");
        params.put("tweetid", diaryId);
        params.put("content", content);
        ApiHttpClient.post("action/api/team_tweet_reply", params, handler);
    }

    /***
     * 客户端扫描二维码登陆
     *
     * @param url
     * @param handler
     * @return void
     * @author 火蚁 2015-3-13 上午11:45:47
     */
    public static void scanQrCodeLogin(String url,
                                       AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        String uuid = url.substring(url.lastIndexOf("=") + 1);
        params.put("uuid", uuid);
        ApiHttpClient.getDirect(url, handler);
    }

    /***
     * 使用第三方登陆
     *
     * @param catalog    类别
     * @param openIdInfo 第三方的info
     * @param handler    handler
     */
    public static void open_login(String catalog, String openIdInfo, AsyncHttpResponseHandler
            handler) {
        RequestParams params = new RequestParams();
        params.put("catalog", catalog);
        params.put("openid_info", openIdInfo);
        ApiHttpClient.post("action/api/openid_login", params, handler);
    }

    /***
     * 第三方登陆账号绑定
     *
     * @param catalog    类别（QQ、wechat）
     * @param openIdInfo 第三方info
     * @param userName   用户名
     * @param pwd        密码
     * @param handler    handler
     */
    public static void bind_openid(String catalog, String openIdInfo, String userName, String
            pwd, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("catalog", catalog);
        params.put("openid_info", openIdInfo);
        params.put("username", userName);
        params.put("pwd", pwd);
        ApiHttpClient.post("action/api/openid_bind", params, handler);
    }

    /***
     * 使用第三方账号注册
     *
     * @param catalog    类别（qq、wechat）
     * @param openIdInfo 第三方info
     * @param handler    handler
     */
    public static void openid_reg(String catalog, String openIdInfo, AsyncHttpResponseHandler
            handler) {
        RequestParams params = new RequestParams();
        params.put("catalog", catalog);
        params.put("openid_info", openIdInfo);
        ApiHttpClient.post("action/api/openid_reg", params, handler);
    }

    /**
     * 意见反馈
     */
    public static void feedback(String content, File file, AsyncHttpResponseHandler handler, Context context) {
        RequestParams params = new RequestParams();
        if (file != null && file.exists())
            try {
                params.put("file", file);
            } catch (FileNotFoundException e) {
            }
        params.put("uid", AccountHelper.getUserId());
        params.put("receiver", "2609904");
        params.put("content", content);
        ApiHttpClient.post("action/api/message_pub", params, handler);
    }


    public static final int CATALOG_BANNER_NEWS = 1; // 资讯Banner
    public static final int CATALOG_BANNER_BLOG = 2; // 博客Banner
    public static final int CATALOG_BANNER_EVENT = 3; // 活动Banner

    /**
     * 请求Banner列表接口
     *
     * @param catalog Banner类别 {@link #CATALOG_BANNER_NEWS, #CATALOG_BANNER_BLOG, #CATALOG_BANNER_EVENT}
     * @param handler AsyncHttpResponseHandler
     */
    public static void getBannerList(int catalog, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("catalog", catalog);
        ApiHttpClient.get("action/apiv2/banner", params, handler);
    }


    /**
     * 请求资讯列表
     *
     * @param pageToken 请求上下页数据令牌
     * @param handler   AsyncHttpResponseHandler
     */
    public static void getNewsList(String pageToken, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        if (!TextUtils.isEmpty(pageToken)) {
            params.put("pageToken", pageToken);
        }

        ApiHttpClient.get("action/apiv2/news", params, handler);
    }


    public static final String CATALOG_NEWS_DETAIL = "news";
    public static final String CATALOG_TRANSLATE_DETAIL = "translation";
    public static final String CATALOG_SOFTWARE_DETAIL = "software";

    /**
     * 请求资讯详情
     *
     * @param id      请求该资讯详情页
     * @param handler AsyncHttpResponseHandler
     */
    public static void getNewsDetail(long id, String type, AsyncHttpResponseHandler handler) {
        if (id <= 0) return;
        RequestParams params = new RequestParams();
        params.put("id", id);
        ApiHttpClient.get("action/apiv2/" + type, params, handler);
    }

    /**
     * 请求资讯详情 [直接用软件名去请求]
     *
     * @param ident   请求该资讯详情页
     * @param handler AsyncHttpResponseHandler
     */
    public static void getSoftwareDetail(String ident, String type, AsyncHttpResponseHandler handler) {
        if (TextUtils.isEmpty(ident)) return;
        RequestParams params = new RequestParams();
        params.put("ident", ident);
        ApiHttpClient.get("action/apiv2/" + type, params, handler);
    }

    public static final int CATALOG_BLOG_NORMAL = 1; // 最新
    public static final int CATALOG_BLOG_HEAT = 2; // 最热
    public static final int CATALOG_BLOG_RECOMMEND = 3;//推荐

    /**
     * 请求博客列表
     *
     * @param catalog   博客类别 {@link #CATALOG_BLOG_NORMAL, #CATALOG_BLOG_HEAT}
     * @param pageToken 请求上下页数据令牌
     * @param handler   AsyncHttpResponseHandler
     */
    public static void getBlogList(int catalog, String pageToken, AsyncHttpResponseHandler handler) {
        if (catalog <= 0)
            catalog = 1;
        RequestParams params = new RequestParams();
        params.put("catalog", catalog);
        if (!TextUtils.isEmpty(pageToken)) {
            params.put("pageToken", pageToken);
        }

        ApiHttpClient.get("action/apiv2/blog", params, handler);
    }

    /**
     * 请求用户自己的博客列表
     *
     * @param pageToken 请求上下页数据令牌
     * @param handler   AsyncHttpResponseHandler
     */
    public static void getUserBlogList(String pageToken, long userId, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();

        if (!TextUtils.isEmpty(pageToken)) {
            params.put("pageToken", pageToken);
        }
        if (userId <= 0) return;
        params.put("authorId", userId);
        ApiHttpClient.get("action/apiv2/blog", params, handler);
    }

    /**
     * 请求用户自己的博客列表
     *
     * @param pageToken 请求上下页数据令牌
     * @param handler   AsyncHttpResponseHandler
     */
    public static void getUserQuestionList(String pageToken, long userId, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        if (!TextUtils.isEmpty(pageToken)) {
            params.put("pageToken", pageToken);
        }
        if (userId <= 0) return;
        params.put("authorId", userId);
        ApiHttpClient.get("action/apiv2/question", params, handler);
    }


    /**
     * 请求博客详情
     *
     * @param id      博客id
     * @param handler AsyncHttpResponseHandler
     */
    public static void getBlogDetail(long id, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("id", id);
        ApiHttpClient.get("action/apiv2/blog", params, handler);
    }


    /**
     * 请求活动列表
     *
     * @param pageToken 请求上下页数据令牌
     * @param handler   AsyncHttpResponseHandler
     */
    public static void getEventList(String pageToken, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        if (!TextUtils.isEmpty(pageToken)) {
            params.put("pageToken", pageToken);
        }

        ApiHttpClient.get("action/apiv2/event", params, handler);
    }

    /**
     * 请求活动详情
     *
     * @param id      id
     * @param handler handler
     */
    public static void getEventDetail(long id, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("id", id);
        ApiHttpClient.get("action/apiv2/event", params, handler);
    }

    /**
     * 更改收藏状态
     *
     * @param id      id
     * @param type    type
     * @param handler handler
     */
    public static void getFavReverse(long id, int type, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("id", id);
        params.put("type", type);
        ApiHttpClient.get("action/apiv2/favorite_reverse", params, handler);
    }

    /**
     * 更改关注状态（关注／取消关注）
     *
     * @param id      id
     * @param handler handler
     */
    public static void addUserRelationReverse(long id, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("id", id);
        ApiHttpClient.get("action/apiv2/user_relation_reverse", params, handler);
    }

    public static final int CATALOG_QUESTION_QUESTION = 1; // 提问
    public static final int CATALOG_QUESTION_SHARE = 2; // 最热
    public static final int CATALOG_QUESTION_MULTI = 3; // 综合
    public static final int CATALOG_QUESTION_OCC = 4; // 职业
    public static final int CATALOG_QUESTION_DEPOT = 5; // 站务

    /**
     * 请求问答列表
     *
     * @param catalog   问答类型  {@link #CATALOG_QUESTION_QUESTION, #CATALOG_QUESTION_SHARE, #CATALOG_QUESTION_MULTI},
     *                  {@link #CATALOG_QUESTION_OCC, #CATALOG_QUESTION_DEPOT}
     * @param pageToken 请求上下页数据令牌
     * @param handler   AsyncHttpResponseHandler
     */
    public static void getQuestionList(int catalog, String pageToken, AsyncHttpResponseHandler handler) {
        if (catalog <= 0)
            catalog = 1;
        RequestParams params = new RequestParams();
        params.put("catalog", catalog);
        if (!TextUtils.isEmpty(pageToken)) {
            params.put("pageToken", pageToken);
        }

        ApiHttpClient.get("action/apiv2/question", params, handler);
    }

    /**
     * 请求问答详情
     *
     * @param id      问答id
     * @param handler AsyncHttpResponseHandler
     */
    public static void getQuestionDetail(long id, AsyncHttpResponseHandler handler) {
        if (id <= 0) return;
        RequestParams params = new RequestParams();
        params.put("id", id);
        ApiHttpClient.get("action/apiv2/question", params, handler);
    }

    /**
     * 请求评论详情
     *
     * @param id      评论Id
     * @param handler AsyncHttpResponseHandler
     */
    public static void getComment(long id, long aid, int type, TextHttpResponseHandler handler) {
        if (id <= 0) return;
        RequestParams params = new RequestParams();
        params.put("id", id);
        params.put("authorId", aid);
        params.put("type", type);
        ApiHttpClient.get("action/apiv2/comment", params, handler);
    }

    public static final int COMMENT_SOFT = 1; // 软件推荐-不支持
    public static final int COMMENT_QUESTION = 2; // 讨论区帖子
    public static final int COMMENT_BLOG = 3; // 博客
    public static final int COMMENT_TRANSLATION = 4; // 翻译文章
    public static final int COMMENT_EVENT = 5; // 活动类型
    public static final int COMMENT_NEWS = 6; // 资讯类型

    /**
     * 请求评论列表
     *
     * @param sourceId  目标Id，该sourceId为资讯、博客、问答等文章的Id
     * @param type      问答类型  {@link #COMMENT_SOFT, #COMMENT_QUESTION, #COMMENT_BLOG},
     *                  {@link #COMMENT_TRANSLATION, #COMMENT_EVENT, #COMMENT_NEWS}
     * @param parts     请求的数据节点 parts="refer,reply"
     * @param pageToken 请求上下页数据令牌
     * @param handler   AsyncHttpResponseHandler
     */
    public static void getComments(long sourceId, int type, String parts, String pageToken, TextHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("sourceId", sourceId);
        params.put("type", type);
        if (!TextUtils.isEmpty(parts)) {
            params.put("parts", parts);
        }
        if (!TextUtils.isEmpty(pageToken)) {
            params.put("pageToken", pageToken);
        }
        ApiHttpClient.get("action/apiv2/comment", params, handler);
    }

    /**
     * 发表评论
     *
     * @param sid     文章id
     * @param referId 引用的评论的id
     * @param replyId 回复的评论的id
     * @param oid     引用、回复的发布者id
     * @param type    文章类型 1:软件推荐, 2:问答帖子, 3:博客, 4:翻译文章, 5:活动, 6:资讯
     * @param content 内容
     * @param handler 你懂得
     */
    public static void publishComment(long sid, long referId, long replyId, long oid,
                                      int type, String content, TextHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("sourceId", sid);
        params.put("type", type);
        params.put("content", content);
        if (referId > 0)
            params.put("referId", referId);
        if (replyId > 0)
            params.put("replyId", replyId);
        if (oid > 0)
            params.put("reAuthorId", oid);
        ApiHttpClient.post("action/apiv2/comment_pub", params, handler);
    }


    /**
     * 发表资讯评论
     *
     * @see {{@link #publicComment(int, long, int, String, int, AsyncHttpResponseHandler)}}
     */
    public static void pubNewsComment(long sid, long commentId, long commentAuthorId, String comment, TextHttpResponseHandler handler) {

        if (commentId == 0 || commentId == sid) {
            commentId = 0;
            commentAuthorId = 0;
        }
        publishComment(sid, 0, commentId, commentAuthorId, 6, comment, handler);
    }

    /**
     * 发表问答评论
     *
     * @see {{@link #publicComment(int, long, int, String, int, AsyncHttpResponseHandler)}}
     */
    public static void pubQuestionComment(long sid, long commentId, long commentAuthorId, String comment, TextHttpResponseHandler handler) {

        if (commentId == 0 || commentId == sid) {
            commentId = 0;
            commentAuthorId = 0;
        }
        publishComment(sid, 0, commentId, commentAuthorId, 2, comment, handler);
    }


    /**
     * 发表翻译评论
     *
     * @see {{@link #publicComment(int, long, int, String, int, AsyncHttpResponseHandler)}}
     */
    public static void pubTranslateComment(long sid, long commentId, long commentAuthorId, String comment, TextHttpResponseHandler handler) {

        if (commentId == 0 || commentId == sid) {
            commentId = 0;
            commentAuthorId = 0;
        }
        publishComment(sid, 0, commentId, commentAuthorId, 4, comment, handler);
    }

    /**
     * 发布博客评论
     *
     * @see {{@link #publicComment(int, long, int, String, int, AsyncHttpResponseHandler)}}
     */
    public static void pubBlogComment(long sid, long commentId, long commentAuthorId, String comment, TextHttpResponseHandler handler) {
        if (commentId == 0 || commentId == sid) {
            commentId = 0;
            commentAuthorId = 0;
        }

        publishComment(sid, 0, commentId, commentAuthorId, 3, comment, handler);
    }

    /**
     * 问答的回答, 顶\踩
     *
     * @param sid     source id 问答的id
     * @param cid     回答的id
     * @param opt     操作类型 0:取消, 1:顶, 2:踩
     * @param handler
     */
    public static void questionVote(long sid, long cid, int opt, TextHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("sourceId", sid);
        params.put("commentId", cid);
        params.put("voteOpt", opt);
        ApiHttpClient.post("action/apiv2/question_vote", params, handler);
    }

    /**
     * 上传图片接口
     * http://doc.oschina.net/app_v2?t=105508
     *
     * @param token     上传口令，单次口令最多上传9张图片。
     * @param imagePath 图片地址
     * @param handler   回调
     */
    public static void uploadImage(String token, String imagePath, AsyncHttpResponseHandler handler) {
        if (TextUtils.isEmpty(imagePath))
            throw new NullPointerException("imagePath is not null.");
        RequestParams params = new RequestParams();
        if (!TextUtils.isEmpty(token))
            params.put("token", token);
        try {
            params.put("resource", new File(imagePath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ApiHttpClient.post("action/apiv2/resource_image", params, handler);
    }

    /**
     * 发布动弹
     * 链接 http://doc.oschina.net/app_v2?t=105522
     *
     * @param content     内容
     * @param imagesToken 图片token
     * @param audioToken  语音token
     * @param about       相关节点，仅仅关注 {@link About#id}, {@link About#type}, {@link About#image}
     * @param handler     回调
     */
    public static void pubTweet(String content, String imagesToken, String audioToken, About about, AsyncHttpResponseHandler handler) {
        if (TextUtils.isEmpty(content))
            throw new NullPointerException("content is not null.");
        RequestParams params = new RequestParams();
        params.put("content", content);
        if (!TextUtils.isEmpty(imagesToken))
            params.put("images", imagesToken);
        if (!TextUtils.isEmpty(audioToken))
            params.put("audio", audioToken);
        if (about != null) {
            params.put("aboutId", about.getId());
            params.put("aboutType", about.getType());
            params.put("aboutImage", about.getImage());
        }
        ApiHttpClient.post("action/apiv2/tweet", params, handler);
    }

    /**
     * 请求用户动弹列表
     *
     * @param authorId  用户id
     * @param pageToken pageToken
     * @param handler   回调
     */
    public static void getUserTweetList(long authorId, String pageToken, TextHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("authorId", authorId);
        if (!TextUtils.isEmpty(pageToken))
            params.put("pageToken", pageToken);
        ApiHttpClient.get("action/apiv2/tweets", params, handler);
    }

    /**
     * 请求热门最新动弹列表
     *
     * @param type      type类型，1: 最新、2: 热门
     * @param pageToken pageToken
     * @param handler   回调
     */
    public static void getTweetList(int type, String pageToken, TextHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("type", type);
        if (!TextUtils.isEmpty(pageToken))
            params.put("pageToken", pageToken);
        ApiHttpClient.get("action/apiv2/tweets", params, handler);
    }

    /**
     * 请求话题动弹列表
     *
     * @param tag       话题动弹
     * @param pageToken pageToken
     * @param handler   回调
     */
    public static void getTagTweetList(String tag, String pageToken, TextHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("tag", tag);
        if (!TextUtils.isEmpty(pageToken))
            params.put("pageToken", pageToken);
        ApiHttpClient.get("action/apiv2/tweets", params, handler);
    }

    /**
     * 请求动弹详情
     *
     * @param id      动弹id
     * @param handler 回调
     */
    public static void getTweetDetail(long id, TextHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("id", id);
        ApiHttpClient.get("action/apiv2/tweet", params, handler);
    }

    /**
     * 请求动弹评论列表
     *
     * @param sourceId 动弹id
     * @param handler  回调
     */
    public static void getTweetCommentList(long sourceId, String pageToken, TextHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("sourceId", sourceId);
        if (!TextUtils.isEmpty(pageToken))
            params.put("pageToken", pageToken);
        ApiHttpClient.get("action/apiv2/tweet_comments", params, handler);
    }

    /**
     * 请求动弹点赞列表
     *
     * @param sourceId 动弹id
     * @param handler  回调
     */
    public static void getTweetLikeList(long sourceId, String pageToken, TextHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("sourceId", sourceId);
        if (!TextUtils.isEmpty(pageToken))
            params.put("pageToken", pageToken);
        ApiHttpClient.get("action/apiv2/tweet_likes", params, handler);
    }


    /**
     * 发表动弹评论列表
     *
     * @param sourceId 动弹id
     * @param content  内容
     * @param replyId  回复的用户id
     * @param handler  回调
     */
    public static void pubTweetComment(long sourceId, String content, long replyId, TextHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("sourceId", sourceId);
        params.put("content", content);
        if (replyId > 0) params.put("replyId", replyId);
        ApiHttpClient.post("action/apiv2/tweet_comment", params, handler);
    }

    /**
     * 更改动弹点赞状态
     *
     * @param sourceId 动弹id
     * @param handler  回调
     */
    public static void reverseTweetLike(long sourceId, TextHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("sourceId", sourceId);
        ApiHttpClient.get("action/apiv2/tweet_like_reverse", params, handler);
    }

    /**
     * 删除动弹评论
     *
     * @param sourceId  动弹id
     * @param commentId 评论id
     * @param handler   回调
     */
    public static void deleteTweetComment(long sourceId, long commentId, TextHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("sourceId", sourceId);
        params.put("commentId", commentId);
        ApiHttpClient.get("action/apiv2/tweet_comment_delete", params, handler);
    }

    /**
     * 删除动弹评论
     *
     * @param sourceId 动弹id
     * @param handler  回调
     */
    public static void deleteTweet(long sourceId, TextHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("sourceId", sourceId);
        ApiHttpClient.get("action/apiv2/tweet_delete", params, handler);
    }

    /**
     * 获取消息列表
     *
     * @param authorId  authorId 用户id，不加该参数时返回所有给我发送消息的列表
     * @param pageToken pageToken
     * @param handler   回调
     */
    public static void getMessageList(long authorId, String pageToken, TextHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("authorId", authorId);
        if (!TextUtils.isEmpty(pageToken))
            params.put("pageToken", pageToken);
        ApiHttpClient.get("action/apiv2/messages", params, handler);
    }

    /**
     * 获取用户私信列表
     *
     * @param pageToken pageToken
     * @param handler   回调
     */
    public static void getUserMessageList(String pageToken, TextHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        if (!TextUtils.isEmpty(pageToken))
            params.put("pageToken", pageToken);
        ApiHttpClient.get("action/apiv2/user_msg_letters", params, handler);
    }

    /**
     * 发送消息
     *
     * @param authorId 接收者
     * @param content  发送内容
     * @param handler  回调
     */
    public static void pubMessage(long authorId, String content, TextHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("authorId", authorId);
        params.put("content", content);
        ApiHttpClient.post("action/apiv2/messages_pub", params, handler);
    }

    public static void pubMessage(long authorId, File content, TextHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("authorId", authorId);
        try {
            params.put("file", content);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ApiHttpClient.post("action/apiv2/messages_pub", params, handler);
    }

    /**
     * 添加反馈，私信接口
     *
     * @param authorId
     * @param content
     * @param file
     * @param handler
     */
    public static void pubMessage(long authorId, String content, File file, TextHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("authorId", authorId);
        params.put("content", content);
        if (file != null && file.exists()) {
            try {
                params.put("file", file);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        ApiHttpClient.post("action/apiv2/messages_pub", params, handler);
    }

    /**
     * 获取AT我的列表。
     *
     * @param pageToken pageToken
     * @param handler   回调
     */
    public static void getMsgMentionList(String pageToken, TextHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        if (!TextUtils.isEmpty(pageToken))
            params.put("pageToken", pageToken);
        ApiHttpClient.get("action/apiv2/user_msg_mentions", params, handler);
    }

    /**
     * 评论我的列表
     *
     * @param pageToken pageToken
     * @param handler   回调
     */
    public static void getMsgCommentList(String pageToken, TextHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        if (!TextUtils.isEmpty(pageToken))
            params.put("pageToken", pageToken);
        ApiHttpClient.get("action/apiv2/user_msg_comments", params, handler);
    }

    /**
     * 获取某用户的信息
     *
     * @param uid  user id
     * @param nick unique personal suffix
     */
    public static void getUserInfo(long uid, String nick, String suffix, TextHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        if (uid > 0) params.put("id", uid);
        if (!TextUtils.isEmpty(nick)) params.put("nickname", nick);
        if (!TextUtils.isEmpty(suffix)) params.put("suffix", suffix);
        ApiHttpClient.get("action/apiv2/user_info", params, handler);
    }

    /**
     * 获取某用户的动态(讨论)列表
     *
     * @param uid       user id
     * @param pageToken page token
     * @param handler   async handler
     */
    public static void getUserActives(long uid, String pageToken, TextHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("id", uid);
        if (!TextUtils.isEmpty(pageToken))
            params.put("pageToken", pageToken);
        ApiHttpClient.get("action/apiv2/user_activity", params, handler);
    }

    /**
     * 获取当前的新消息数量
     *
     * @param handler TextHttpResponseHandler
     */
    public static void getNotice(TextHttpResponseHandler handler) {
        ApiHttpClient.get("action/apiv2/notice", handler);
    }

    /**
     * 获取个人信息
     */
    public static void getUserInfo(TextHttpResponseHandler handler) {
        // RequestParams params = new RequestParams();
        // params.put("id", uid);
        ApiHttpClient.get("action/apiv2/user_info", handler);

    }

    /**
     * update the user icon
     *
     * @param file    file
     * @param handler handler
     */
    public static void updateUserIcon(File file, TextHttpResponseHandler handler) {
        if (file == null) return;
        RequestParams params = new RequestParams();
        try {
            params.put("portrait", file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ApiHttpClient.post("action/apiv2/user_edit_portrait", params, handler);

    }

    public static final int TYPE_USER_FOLOWS = 1;
    public static final int TYPE_USER_FANS = 2;

    /**
     * @param type      {@link #TYPE_USER_FOLOWS ,#TYPE_USER_FANS}
     * @param userId    userId
     * @param pageToken pageToken
     * @param handler   handler
     */
    public static void getUserFansOrFlows(int type, long userId, String pageToken, TextHttpResponseHandler handler) {
        if (userId <= 0) return;
        RequestParams params = new RequestParams();
        params.put("id", userId);
        params.put("pageToken", pageToken);

        String uri = "user_follows";
        if (type == TYPE_USER_FANS) {
            uri = "user_fans";
        }
        ApiHttpClient.get("action/apiv2/" + uri, params, handler);
    }


    public static final int CATALOG_ALL = 0;
    public static final int CATALOG_SOFTWARE = 1;
    public static final int CATALOG_QUESTION = 2;
    public static final int CATALOG_BLOG = 3;
    public static final int CATALOG_TRANSALITON = 4;
    public static final int CATALOG_EVENT = 5;
    public static final int CATALOG_NEWS = 6;

    /**
     * get user favorites
     *
     * @param catalog   catalog  {@link #CATALOG_ALL,#CATALOG_SOFTWARE,#CATALOG_QUESTION,
     *                  {@link #CATALOG_BLOG,#CATALOG_TRANSALITON,#CATALOG_EVENT,#CATALOG_NEWS}
     * @param pagetoken pagetoken
     * @param handler   handler
     */
    public static void getUserFavorites(int catalog, String pagetoken, TextHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("catalog", catalog);
        if (pagetoken != null)
            params.put("pageToken", pagetoken);
        ApiHttpClient.get("action/apiv2/favorites", params, handler);
    }

    /**
     * 摇一摇(抽奖)
     *
     * @param timestamp 当前摇一摇的时间戳
     * @param appToken  App唯一校验
     * @param signature 加密后的字符串
     * @param handler
     */
    public static void getShakePresent(long timestamp, String appToken, String signature, TextHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("timestamp", timestamp);
        params.put("appToken", appToken);
        params.put("signature", signature);
        ApiHttpClient.post("action/apiv2/shake_present", params, handler);
    }

    /**
     * 摇一摇(综合)
     *
     * @param handler
     */
    public static void getShakeNews(TextHttpResponseHandler handler) {
        ApiHttpClient.get("action/apiv2/shake_news", handler);
    }

    /**
     * 打赏接口
     *
     * @param type      打赏类型, 博客: 16344358(暂时是固定值)
     * @param targetId  博客id
     * @param attach    支付类型 alipay、wepay
     * @param money     多少钱呐!
     * @param subject   博客标题
     * @param donatorId 捐助者id
     * @param authorId  博客作者id
     * @param message   留言
     * @param returnUrl 博客url
     * @param notifyUrl ???
     * @param sign      签名
     * @param handler   handler
     */
    public static void reward(
            long type,
            long targetId,
            String attach,
            long money,
            String subject,
            long donatorId,
            long authorId,
            String message,
            String returnUrl,
            String notifyUrl,
            String sign,
            TextHttpResponseHandler handler) {
        // pass
    }

    public static void reward(List<Pair<String, String>> pairs, TextHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        for (Pair<String, String> pair : pairs) {
            params.put(pair.first, pair.second);
        }
        ApiHttpClient.getHttpClient().addHeader("Host", "121.41.10.133");
        ApiHttpClient.getHttpClient().post("http://121.41.10.133/action/apiv2/blog_reward", params, handler);
    }

    public static void reward(Map<String, String> pairs, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams(pairs);
        Log.e("oschina", "params: " + params.toString());

        AsyncHttpClient client = new AsyncHttpClient();

        Log.e("oschina", "post request");
        client.post("http://121.41.10.133/action/apiv2/reward_order", params, handler);
    }

    public static void checkUpdate(TextHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("appId", 1);
        params.put("catalog", 1);
        params.put("all", false);
        ApiHttpClient.get("action/apiv2/product_version", params, handler);
    }

    public static void getCollectionList(String pageToken, TextHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("catalog", 0);
        if (!TextUtils.isEmpty(pageToken))
            params.put("pageToken", pageToken);
        ApiHttpClient.get("action/apiv2/favorites", params, handler);
    }


    public static final String LOGIN_WEIBO = "weibo";
    public static final String LOGIN_QQ = "qq";
    public static final String LOGIN_WECHAT = "wechat";

    /**
     * @param catalog  open catalog
     * @param openInfo openInfo
     * @param handler  handler
     */
    public static void openLogin(String catalog, String openInfo, TextHttpResponseHandler handler) {
        if (TextUtils.isEmpty(openInfo)) return;
        RequestParams params = new RequestParams();
        params.put("catalog", catalog);
        params.put("info", openInfo);

        ApiHttpClient.post("action/apiv2/account_open_login", params, handler);
    }

    /**
     * 搜索
     *
     * @param catalog   搜索类型
     * @param content   搜索内容
     * @param pageToken next page token
     * @param handler   handler
     */
    public static void search(int catalog, String content, String pageToken, TextHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("catalog", catalog);
        params.put("content", content);
        params.put("pageToken", pageToken);
        ApiHttpClient.get("action/apiv2/search", params, handler);
    }

    /**
     * login account
     *
     * @param username username
     * @param pwd      pwd
     * @param handler  handler
     */
    public static void login(String username, String pwd, TextHttpResponseHandler handler) {

        RequestParams params = new RequestParams();
        params.put("account", username);
        params.put("password", pwd);

        ApiHttpClient.post("action/apiv2/account_login", params, handler);

    }


    /**
     * send  sms code
     *
     * @param phone  phone number
     * @param smsCallType  smsCallType
     * @param handler  handler
     */
    public static final int REGISTER_INTENT = 1;
    public static final int RESET_PWD_INTENT = 2;

    public static void sendSmsCode(String phone, int intent, TextHttpResponseHandler handler) {

        RequestParams params = new RequestParams();

        params.put("phone", phone);
        params.put("intent", intent);

        ApiHttpClient.post("action/apiv2/phone_send_code", params, handler);

    }


    /**
     * validate and get phone token
     *
     * @param phoneNumber phoneNumber
     * @param smsCode     smsCode
     * @param handler     handler
     */
    public static void validateRegisterInfo(String phoneNumber, String smsCode, TextHttpResponseHandler handler) {

        RequestParams params = new RequestParams();
        params.put("phone", phoneNumber);
        params.put("code", smsCode);

        ApiHttpClient.post("action/apiv2/phone_validate", params, handler);
    }


    /**
     * register user info
     *
     * @param username   username
     * @param password   pwd
     * @param gender     gender
     * @param phoneToken token
     * @param handler    handler
     */
    public static void register(String username, String password, int gender, String phoneToken, TextHttpResponseHandler
            handler) {

        RequestParams params = new RequestParams();
        params.put("username", username);
        params.put("password", password);
        params.put("gender", gender);
        params.put("phoneToken", phoneToken);

        ApiHttpClient.post("action/apiv2/account_register", params, handler);

    }

    /**
     * reset pwd
     *
     * @param password   password
     * @param phoneToken token
     * @param handler    handler
     */
    public static void resetPwd(String password, String phoneToken, TextHttpResponseHandler handler) {

        RequestParams params = new RequestParams();
        params.put("password", password);
        params.put("phoneToken", phoneToken);

        ApiHttpClient.post("action/apiv2/account_password_forgot", params, handler);

    }

    /**
     * 获得首页数据
     *
     * @param api       动态api
     * @param pageToken pageToken
     * @param handler   handler
     */

    public static void getSubscription(String api, String pageToken, TextHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        if (!TextUtils.isEmpty(pageToken)) {
            params.put("pageToken", pageToken);
        }
        ApiHttpClient.getHttpClient().get(api, params, handler);
    }

    /**
     * 获得banner
     *
     * @param api     动态api
     * @param handler handler
     */
    public static void getBanner(String api, TextHttpResponseHandler handler) {
        ApiHttpClient.getHttpClient().get(api, handler);
    }

    /**
     * 动弹列表
     * @param aid author id, 请求某人的动弹
     * @param tag 相关话题
     * @param type 1: 广场（所有动弹）， 2：朋友圈（好友动弹）
     * @param order 1: 最新， 2：最热
     * @param handler handler
     */
    public static void getTweetList(
            Long aid,
            String tag,
            Integer type,
            Integer order,
            String pageToken,
            TextHttpResponseHandler handler
    ){
        RequestParams params = new RequestParams();
        if (aid != null){
            params.put("authorId", aid);
        }else if (!TextUtils.isEmpty(tag)){
            params.put("tag", tag);
        }else {
            params.put("type", type);
            params.put("order", order);
        }
        params.put("pageToken", pageToken);
        ApiHttpClient.get("action/apiv2/tweet_list", params, handler);
    }


}
