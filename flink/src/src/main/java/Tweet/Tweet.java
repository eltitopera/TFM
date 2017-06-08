package Tweet;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "_id",
    "created_at",
    "id_str",
    "text",
    "source",
    "truncated",
    "in_reply_to_status_id",
    "in_reply_to_status_id_str",
    "in_reply_to_user_id",
    "in_reply_to_user_id_str",
    "in_reply_to_screen_name",
    "user",
    "geo",
    "coordinates",
    "place",
    "contributors",
    "retweeted_status",
    "is_quote_status",
    "retweet_count",
    "favorite_count",
    "entities",
    "extended_entities",
    "favorited",
    "retweeted",
    "possibly_sensitive",
    "filter_level",
    "lang",
    "timestamp_ms",
    "__v"
})
public class Tweet implements Serializable
{

    @JsonProperty("_id")
    private Id id;
    @JsonProperty("created_at")
    private String createdAt;
    @JsonProperty("id_str")
    private String idStr;
    @JsonProperty("text")
    private String text;
    @JsonProperty("source")
    private String source;
    @JsonProperty("truncated")
    private Boolean truncated;
    @JsonProperty("in_reply_to_status_id")
    private Long inReplyToStatusId;
    @JsonProperty("in_reply_to_status_id_str")
    private String inReplyToStatusIdStr;
    @JsonProperty("in_reply_to_user_id")
    private Long inReplyToUserId;
    @JsonProperty("in_reply_to_user_id_str")
    private String inReplyToUserIdStr;
    @JsonProperty("in_reply_to_screen_name")
    private String inReplyToScreenName;
    @JsonProperty("user")
    private User user;
    @JsonProperty("geo")
    private Object geo;
    @JsonProperty("coordinates")
    private Coordinates coordinates;
    @JsonProperty("place")
    private Place place;
    @JsonProperty("contributors")
    private Object contributors;
    @JsonProperty("retweeted_status")
    private RetweetedStatus retweetedStatus;
    @JsonProperty("is_quote_status")
    private Boolean isQuoteStatus;
    @JsonProperty("retweet_count")
    private Long retweetCount;
    @JsonProperty("favorite_count")
    private Long favoriteCount;
    @JsonProperty("entities")
    private Entities_ entities;
    @JsonProperty("extended_entities")
    private ExtendedEntities_ extendedEntities;
    @JsonProperty("favorited")
    private Boolean favorited;
    @JsonProperty("retweeted")
    private Boolean retweeted;
    @JsonProperty("possibly_sensitive")
    private Boolean possiblySensitive;
    @JsonProperty("filter_level")
    private String filterLevel;
    @JsonProperty("lang")
    private String lang;
    @JsonProperty("timestamp_ms")
    private String timestampMs;
    @JsonProperty("__v")
    private Long v;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    private final static long serialVersionUID = -8591302305214301598L;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Tweet() {
    }

    /**
     * 
     * @param inReplyToUserIdStr
     * @param retweeted
     * @param retweetCount
     * @param extendedEntities
     * @param truncated
     * @param lang
     * @param id
     * @param inReplyToStatusIdStr
     * @param createdAt
     * @param place
     * @param coordinates
     * @param text
     * @param contributors
     * @param filterLevel
     * @param geo
     * @param timestampMs
     * @param inReplyToScreenName
     * @param retweetedStatus
     * @param entities
     * @param possiblySensitive
     * @param idStr
     * @param inReplyToStatusId
     * @param v
     * @param source
     * @param favoriteCount
     * @param favorited
     * @param inReplyToUserId
     * @param user
     * @param isQuoteStatus
     */

    public Tweet(Tweet tweet){
        super();
        this.id = tweet.id;
        this.createdAt = tweet.createdAt;
        this.idStr = tweet.idStr;
        this.text = tweet.text;
        this.source = tweet.source;
        this.truncated = tweet.truncated;
        this.inReplyToStatusId = tweet.inReplyToStatusId;
        this.inReplyToStatusIdStr = tweet.inReplyToStatusIdStr;
        this.inReplyToUserId = tweet.inReplyToUserId;
        this.inReplyToUserIdStr = tweet.inReplyToUserIdStr;
        this.inReplyToScreenName = tweet.inReplyToScreenName;
        this.user = tweet.user;
        this.geo = tweet.geo;
        this.coordinates = tweet.coordinates;
        this.place = tweet.place;
        this.contributors = tweet.contributors;
        this.retweetedStatus = tweet.retweetedStatus;
        this.isQuoteStatus = tweet.isQuoteStatus;
        this.retweetCount = tweet.retweetCount;
        this.favoriteCount = tweet.favoriteCount;
        this.entities = tweet.entities;
        this.extendedEntities = tweet.extendedEntities;
        this.favorited = tweet.favorited;
        this.retweeted = tweet.retweeted;
        this.possiblySensitive = tweet.possiblySensitive;
        this.filterLevel = tweet.filterLevel;
        this.lang = tweet.lang;
        this.timestampMs = tweet.timestampMs;
        this.v = tweet.v;
    }

    public Tweet(Id id, String createdAt, String idStr, String text, String source, Boolean truncated, Long inReplyToStatusId, String inReplyToStatusIdStr, Long inReplyToUserId, String inReplyToUserIdStr, String inReplyToScreenName, User user, Object geo, Coordinates coordinates, Place place, Object contributors, RetweetedStatus retweetedStatus, Boolean isQuoteStatus, Long retweetCount, Long favoriteCount, Entities_ entities, ExtendedEntities_ extendedEntities, Boolean favorited, Boolean retweeted, Boolean possiblySensitive, String filterLevel, String lang, String timestampMs, Long v) {
        super();
        this.id = id;
        this.createdAt = createdAt;
        this.idStr = idStr;
        this.text = text;
        this.source = source;
        this.truncated = truncated;
        this.inReplyToStatusId = inReplyToStatusId;
        this.inReplyToStatusIdStr = inReplyToStatusIdStr;
        this.inReplyToUserId = inReplyToUserId;
        this.inReplyToUserIdStr = inReplyToUserIdStr;
        this.inReplyToScreenName = inReplyToScreenName;
        this.user = user;
        this.geo = geo;
        this.coordinates = coordinates;
        this.place = place;
        this.contributors = contributors;
        this.retweetedStatus = retweetedStatus;
        this.isQuoteStatus = isQuoteStatus;
        this.retweetCount = retweetCount;
        this.favoriteCount = favoriteCount;
        this.entities = entities;
        this.extendedEntities = extendedEntities;
        this.favorited = favorited;
        this.retweeted = retweeted;
        this.possiblySensitive = possiblySensitive;
        this.filterLevel = filterLevel;
        this.lang = lang;
        this.timestampMs = timestampMs;
        this.v = v;
    }

    @JsonProperty("_id")
    public Id getId() {
        return id;
    }

    @JsonProperty("_id")
    public void setId(Id id) {
        this.id = id;
    }

    @JsonProperty("created_at")
    public String getCreatedAt() {
        return createdAt;
    }

    @JsonProperty("created_at")
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    @JsonProperty("id_str")
    public String getIdStr() {
        return idStr;
    }

    @JsonProperty("id_str")
    public void setIdStr(String idStr) {
        this.idStr = idStr;
    }

    @JsonProperty("text")
    public String getText() {
        return text;
    }

    @JsonProperty("text")
    public void setText(String text) {
        this.text = text;
    }

    @JsonProperty("source")
    public String getSource() {
        return source;
    }

    @JsonProperty("source")
    public void setSource(String source) {
        this.source = source;
    }

    @JsonProperty("truncated")
    public Boolean getTruncated() {
        return truncated;
    }

    @JsonProperty("truncated")
    public void setTruncated(Boolean truncated) {
        this.truncated = truncated;
    }

    @JsonProperty("in_reply_to_status_id")
    public Long getInReplyToStatusId() {
        return inReplyToStatusId;
    }

    @JsonProperty("in_reply_to_status_id")
    public void setInReplyToStatusId(Long inReplyToStatusId) {
        this.inReplyToStatusId = inReplyToStatusId;
    }

    @JsonProperty("in_reply_to_status_id_str")
    public String getInReplyToStatusIdStr() {
        return inReplyToStatusIdStr;
    }

    @JsonProperty("in_reply_to_status_id_str")
    public void setInReplyToStatusIdStr(String inReplyToStatusIdStr) {
        this.inReplyToStatusIdStr = inReplyToStatusIdStr;
    }

    @JsonProperty("in_reply_to_user_id")
    public Long getInReplyToUserId() {
        return inReplyToUserId;
    }

    @JsonProperty("in_reply_to_user_id")
    public void setInReplyToUserId(Long inReplyToUserId) {
        this.inReplyToUserId = inReplyToUserId;
    }

    @JsonProperty("in_reply_to_user_id_str")
    public String getInReplyToUserIdStr() {
        return inReplyToUserIdStr;
    }

    @JsonProperty("in_reply_to_user_id_str")
    public void setInReplyToUserIdStr(String inReplyToUserIdStr) {
        this.inReplyToUserIdStr = inReplyToUserIdStr;
    }

    @JsonProperty("in_reply_to_screen_name")
    public String getInReplyToScreenName() {
        return inReplyToScreenName;
    }

    @JsonProperty("in_reply_to_screen_name")
    public void setInReplyToScreenName(String inReplyToScreenName) {
        this.inReplyToScreenName = inReplyToScreenName;
    }

    @JsonProperty("user")
    public User getUser() {
        return user;
    }

    @JsonProperty("user")
    public void setUser(User user) {
        this.user = user;
    }

    @JsonProperty("geo")
    public Object getGeo() {
        return geo;
    }

    @JsonProperty("geo")
    public void setGeo(Object geo) {
        this.geo = geo;
    }

    @JsonProperty("coordinates")
    public Coordinates getCoordinates() {
        return coordinates;
    }

    public String getAllCoordinates() {
        String All_coordinates = "";
        if (coordinates != null) {
            All_coordinates = coordinates.getType()+";";
            List<List<List<Double>>> listCoords = coordinates.getCoordinates();
            Iterator<Double> coords= listCoords.get(0).get(0).iterator();
            while (coords.hasNext()) {
                Double coord = coords.next();
                All_coordinates = coord.toString()+";";

            }
        }
        return All_coordinates;
    }

    @JsonProperty("coordinates")
    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    @JsonProperty("place")
    public Place getPlace() {
        return place;
    }

    @JsonProperty("place")
    public void setPlace(Place place) {
        this.place = place;
    }

    @JsonProperty("contributors")
    public Object getContributors() {
        return contributors;
    }

    @JsonProperty("contributors")
    public void setContributors(Object contributors) {
        this.contributors = contributors;
    }

    @JsonProperty("retweeted_status")
    public RetweetedStatus getRetweetedStatus() {
        return retweetedStatus;
    }

    @JsonProperty("retweeted_status")
    public void setRetweetedStatus(RetweetedStatus retweetedStatus) {
        this.retweetedStatus = retweetedStatus;
    }

    @JsonProperty("is_quote_status")
    public Boolean getIsQuoteStatus() {
        return isQuoteStatus;
    }

    @JsonProperty("is_quote_status")
    public void setIsQuoteStatus(Boolean isQuoteStatus) {
        this.isQuoteStatus = isQuoteStatus;
    }

    @JsonProperty("retweet_count")
    public Long getRetweetCount() {
        return retweetCount;
    }

    @JsonProperty("retweet_count")
    public void setRetweetCount(Long retweetCount) {
        this.retweetCount = retweetCount;
    }

    @JsonProperty("favorite_count")
    public Long getFavoriteCount() {
        return favoriteCount;
    }

    @JsonProperty("favorite_count")
    public void setFavoriteCount(Long favoriteCount) {
        this.favoriteCount = favoriteCount;
    }

    @JsonProperty("entities")
    public Entities_ getEntities() {
        return entities;
    }

    @JsonProperty("entities")
    public void setEntities(Entities_ entities) {
        this.entities = entities;
    }

    @JsonProperty("extended_entities")
    public ExtendedEntities_ getExtendedEntities() {
        return extendedEntities;
    }

    @JsonProperty("extended_entities")
    public void setExtendedEntities(ExtendedEntities_ extendedEntities) {
        this.extendedEntities = extendedEntities;
    }

    @JsonProperty("favorited")
    public Boolean getFavorited() {
        return favorited;
    }

    @JsonProperty("favorited")
    public void setFavorited(Boolean favorited) {
        this.favorited = favorited;
    }

    @JsonProperty("retweeted")
    public Boolean getRetweeted() {
        return retweeted;
    }

    @JsonProperty("retweeted")
    public void setRetweeted(Boolean retweeted) {
        this.retweeted = retweeted;
    }

    @JsonProperty("possibly_sensitive")
    public Boolean getPossiblySensitive() {
        return possiblySensitive;
    }

    @JsonProperty("possibly_sensitive")
    public void setPossiblySensitive(Boolean possiblySensitive) {
        this.possiblySensitive = possiblySensitive;
    }

    @JsonProperty("filter_level")
    public String getFilterLevel() {
        return filterLevel;
    }

    @JsonProperty("filter_level")
    public void setFilterLevel(String filterLevel) {
        this.filterLevel = filterLevel;
    }

    @JsonProperty("lang")
    public String getLang() {
        return lang;
    }

    @JsonProperty("lang")
    public void setLang(String lang) {
        this.lang = lang;
    }

    @JsonProperty("timestamp_ms")
    public String getTimestampMs() {
        return timestampMs;
    }

    @JsonProperty("timestamp_ms")
    public void setTimestampMs(String timestampMs) {
        this.timestampMs = timestampMs;
    }

    @JsonProperty("__v")
    public Long getV() {
        return v;
    }

    @JsonProperty("__v")
    public void setV(Long v) {
        this.v = v;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
