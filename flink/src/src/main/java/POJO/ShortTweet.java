package POJO;

import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.Table;

import java.io.Serializable;

@Table(keyspace= "Twitter", name = "tweets")
public class ShortTweet implements Serializable{

    @Column(name = "created_at")
    private String created_at;

    @Column(name = "coordinates_Tweet")
    private String coordinates_Tweet;

    @Column(name = "hashtags")
    private String hashtags; // 1 a N separado

    @Column(name = "media_url")
    private String media_url; // 1 a N separado

    @Column(name = "favorite_count")
    private Long favorite_count;

    @Column(name = "id_str")
    private String id_str;

    @Column(name = "in_reply_to_status_id_str")
    private String in_reply_to_status_id_str;

    @Column(name = "in_reply_to_user_id_str")
    private String in_reply_to_user_id_str;

    @Column(name = "lang")
    private String lang;

    @Column(name = "possibly_sensitive")
    private Boolean possibly_sensitive;

    @Column(name = "reTweet_count")
    private Long reTweet_count;

    @Column(name = "reTweeted")
    private Boolean reTweeted;

    @Column(name = "source")
    private String source;

    @Column(name = "text")
    private String text;

    @Column(name = "truncated")
    private Boolean truncated;

    @Column(name = "user_created")
    private String user_created;

    @Column(name = "user_description")
    private String user_description;

    @Column(name = "user_display_url")
    private String user_display_url;

    @Column(name = "user_favourites_count")
    private Long user_favourites_count;

    @Column(name = "user_followers_count")
    private Long user_followers_count;

    @Column(name = "user_friends_count")
    private Long user_friends_count;

    @Column(name = "user_geo_enabled")
    private Boolean user_geo_enabled;

    @Column(name = "user_lang")
    private String user_lang;

    @Column(name = "user_listed_count")
    private Long user_listed_count;

    @Column(name = "user_location")
    private String user_location;

    @Column(name = "user_name")
    private String user_name;

    @Column(name = "user_profile_background_image_url")
    private String user_profile_background_image_url;

    @Column(name = "user_profile_image_url")
    private String user_profile_image_url;

    @Column(name = "user_screen_name")
    private String user_screen_name;

    @Column(name = "user_statuses_count")
    private Long user_statuses_count;

    @Column(name = "user_time_zone")
    private String user_time_zone;


    @Column(name = "user_url")
    private String user_url;



    public ShortTweet() {
    }

    public ShortTweet(String id_str) {
            this.id_str = id_str;
    }

    private static final long serialVersionUID = 1038054554690916991L;


    public ShortTweet(String created_at, String coordinates_Tweet,
                      String hashtags, String media_url, Long favorite_count, String id_str, String in_reply_to_status_id_str,
                      String in_reply_to_user_id_str, String lang, Boolean possibly_sensitive, Long reTweet_count,
                      Boolean reTweetsed, String source, String text, Boolean truncated, String user_created, String user_description,
                      String user_display_url, Long user_favourites_count, Long user_followers_count, Long user_friends_count,
                      Boolean user_geo_enabled, String user_lang, Long user_listed_count, String user_location, String user_name,
                      String user_profile_background_image_url, String user_profile_image_url, String user_screen_name,
                      Long user_statuses_count, String user_time_zone, String user_url) {
        this.created_at  =created_at;
        this.coordinates_Tweet=coordinates_Tweet;
        this.hashtags =hashtags;
        this.media_url=media_url;
        this.favorite_count  =favorite_count;
        this.id_str  =id_str;
        this.in_reply_to_status_id_str=in_reply_to_status_id_str;
        this.in_reply_to_user_id_str =in_reply_to_user_id_str;
        this.lang =lang;
        this.possibly_sensitive  =possibly_sensitive;
        this.reTweet_count=reTweet_count;
        this.reTweeted=reTweeted;
        this.source  =source;
        this.text =text;
        this.truncated=truncated;
        this.user_created =user_created;
        this.user_description =user_description;
        this.user_display_url =user_display_url;
        this.user_favourites_count=user_favourites_count;
        this.user_followers_count =user_followers_count;
        this.user_friends_count  =user_friends_count;
        this.user_geo_enabled =user_geo_enabled;
        this.user_lang=user_lang;
        this.user_listed_count=user_listed_count;
        this.user_location=user_location;
        this.user_name=user_name;
        this.user_profile_background_image_url=user_profile_background_image_url;
        this.user_profile_image_url  =user_profile_image_url;
        this.user_screen_name =user_screen_name;
        this.user_statuses_count =user_statuses_count;
        this.user_time_zone  =user_time_zone;
        this.user_url =user_url;
    }


    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getCoordinates_Tweet() {
        return coordinates_Tweet;
    }

    public void setCoordinates_Tweet(String coordinates_Tweet) {
        this.coordinates_Tweet = coordinates_Tweet;
    }

    public String getHashtags() {
        return hashtags;
    }

    public void setHashtags(String hashtags) {
        this.hashtags = hashtags;
    }

    public String getMedia_url() {
        return media_url;
    }

    public void setMedia_url(String media_url) {
        this.media_url = media_url;
    }

    public Long getFavorite_count() {
        return favorite_count;
    }

    public void setFavorite_count(Long favorite_count) {
        this.favorite_count = favorite_count;
    }

    public String getId_str() {
        return id_str;
    }

    public void setId_str(String id_str) {
        this.id_str = id_str;
    }

    public String getIn_reply_to_status_id_str() {
        return in_reply_to_status_id_str;
    }

    public void setIn_reply_to_status_id_str(String in_reply_to_status_id_str) {
        this.in_reply_to_status_id_str = in_reply_to_status_id_str;
    }

    public String getIn_reply_to_user_id_str() {
        return in_reply_to_user_id_str;
    }

    public void setIn_reply_to_user_id_str(String in_reply_to_user_id_str) {
        this.in_reply_to_user_id_str = in_reply_to_user_id_str;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public Boolean getPossibly_sensitive() {
        return possibly_sensitive;
    }

    public void setPossibly_sensitive(Boolean possibly_sensitive) {
        this.possibly_sensitive = possibly_sensitive;
    }

    public Long getReTweet_count() {
        return reTweet_count;
    }

    public void setReTweet_count(Long reTweets_count) {
        this.reTweet_count = reTweets_count;
    }

    public Boolean getReTweeted() {
        return reTweeted;
    }

    public void setReTweeted(Boolean reTweetsed) {
        this.reTweeted = reTweetsed;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Boolean getTruncated() {
        return truncated;
    }

    public void setTruncated(Boolean truncated) {
        this.truncated = truncated;
    }

    public String getUser_created() {
        return user_created;
    }

    public void setUser_created(String user_created) {
        this.user_created = user_created;
    }

    public String getUser_description() {
        return user_description;
    }

    public void setUser_description(String user_description) {
        this.user_description = user_description;
    }

    public String getUser_display_url() {
        return user_display_url;
    }

    public void setUser_display_url(String user_display_url) {
        this.user_display_url = user_display_url;
    }

    public Long getUser_favourites_count() {
        return user_favourites_count;
    }

    public void setUser_favourites_count(Long user_favourites_count) {
        this.user_favourites_count = user_favourites_count;
    }

    public Long getUser_followers_count() {
        return user_followers_count;
    }

    public void setUser_followers_count(Long user_followers_count) {
        this.user_followers_count = user_followers_count;
    }

    public Long getUser_friends_count() {
        return user_friends_count;
    }

    public void setUser_friends_count(Long user_friends_count) {
        this.user_friends_count = user_friends_count;
    }

    public Boolean getUser_geo_enabled() {
        return user_geo_enabled;
    }

    public void setUser_geo_enabled(Boolean user_geo_enabled) {
        this.user_geo_enabled = user_geo_enabled;
    }

    public String getUser_lang() {
        return user_lang;
    }

    public void setUser_lang(String user_lang) {
        this.user_lang = user_lang;
    }

    public Long getUser_listed_count() {
        return user_listed_count;
    }

    public void setUser_listed_count(Long user_listed_count) {
        this.user_listed_count = user_listed_count;
    }

    public String getUser_location() {
        return user_location;
    }

    public void setUser_location(String user_location) {
        this.user_location = user_location;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_profile_background_image_url() {
        return user_profile_background_image_url;
    }

    public void setUser_profile_background_image_url(String user_profile_background_image_url) {
        this.user_profile_background_image_url = user_profile_background_image_url;
    }

    public String getUser_profile_image_url() {
        return user_profile_image_url;
    }

    public void setUser_profile_image_url(String user_profile_image_url) {
        this.user_profile_image_url = user_profile_image_url;
    }

    public String getUser_screen_name() {
        return user_screen_name;
    }

    public void setUser_screen_name(String user_screen_name) {
        this.user_screen_name = user_screen_name;
    }

    public Long getUser_statuses_count() {
        return user_statuses_count;
    }

    public void setUser_statuses_count(Long user_statuses_count) {
        this.user_statuses_count = user_statuses_count;
    }

    public String getUser_time_zone() {
        return user_time_zone;
    }

    public void setUser_time_zone(String user_time_zone) {
        this.user_time_zone = user_time_zone;
    }


    public String getUser_url() {
        return user_url;
    }

    public void setUser_url(String user_url) {
        this.user_url = user_url;
    }

    @Override
    public String toString(){
        return "ID: " + this.id_str ;
    }
}