
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
    "hashtags",
    "urls",
    "user_mentions",
    "symbols",
    "media"
})
public class Entities_ implements Serializable
{

    @JsonProperty("hashtags")
    private List<Hashtag_> hashtags = null;
    @JsonProperty("urls")
    private List<Object> urls = null;
    @JsonProperty("user_mentions")
    private List<UserMention> userMentions = null;
    @JsonProperty("symbols")
    private List<Object> symbols = null;
    @JsonProperty("media")
    private List<Medium____> media = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    private final static long serialVersionUID = -1191943628900336050L;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Entities_() {
    }

    /**
     * 
     * @param symbols
     * @param urls
     * @param hashtags
     * @param media
     * @param userMentions
     */
    public Entities_(List<Hashtag_> hashtags, List<Object> urls, List<UserMention> userMentions, List<Object> symbols, List<Medium____> media) {
        super();
        this.hashtags = hashtags;
        this.urls = urls;
        this.userMentions = userMentions;
        this.symbols = symbols;
        this.media = media;
    }

    @JsonProperty("hashtags")
    public List<Hashtag_> getHashtags() {
        return hashtags;
    }

    public String getAllHashtags() {
        String all_Hashtags = "";
        if(hashtags != null) {
            Iterator<Hashtag_> proc_hashtag = hashtags.iterator();
            while (proc_hashtag.hasNext()) {
                Hashtag_ aux = proc_hashtag.next();
                all_Hashtags = aux.getText() + ";";
            }
        }
        return all_Hashtags;
    }

    @JsonProperty("hashtags")
    public void setHashtags(List<Hashtag_> hashtags) {
        this.hashtags = hashtags;
    }

    @JsonProperty("urls")
    public List<Object> getUrls() {
        return urls;
    }

    @JsonProperty("urls")
    public void setUrls(List<Object> urls) {
        this.urls = urls;
    }

    @JsonProperty("user_mentions")
    public List<UserMention> getUserMentions() {
        return userMentions;
    }

    @JsonProperty("user_mentions")
    public void setUserMentions(List<UserMention> userMentions) {
        this.userMentions = userMentions;
    }

    @JsonProperty("symbols")
    public List<Object> getSymbols() {
        return symbols;
    }

    @JsonProperty("symbols")
    public void setSymbols(List<Object> symbols) {
        this.symbols = symbols;
    }

    @JsonProperty("media")
    public List<Medium____> getMedia() {
        return media;
    }

    public String getAllMediaURL(){
        String all_mediaURL="";
        if(media != null) {
            Iterator<Medium____> proc_Medium = media.iterator();
            while (proc_Medium.hasNext()) {
                Medium____ aux = proc_Medium.next();
                all_mediaURL = aux.getMediaUrl() + ";";
            }
        }
        return all_mediaURL;
    }

    public String getAllDisplayURL(){
        String all_displayURL="";
        if(media != null) {
            Iterator<Medium____> proc_Medium = media.iterator();
            while (proc_Medium.hasNext()) {
                Medium____ aux = proc_Medium.next();
                all_displayURL = aux.getDisplayUrl() + ";";
            }
        }
        return all_displayURL;
    }

    @JsonProperty("media")
    public void setMedia(List<Medium____> media) {
        this.media = media;
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
