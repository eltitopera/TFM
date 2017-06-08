
package Tweet;

import java.io.Serializable;
import java.util.HashMap;
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
    "source_user_id_str",
    "source_user_id",
    "source_status_id_str",
    "source_status_id",
    "sizes",
    "type",
    "expanded_url",
    "display_url",
    "url",
    "media_url_https",
    "media_url",
    "indices",
    "id_str",
    "id"
})
public class Medium______ implements Serializable
{

    @JsonProperty("source_user_id_str")
    private String sourceUserIdStr;
    @JsonProperty("source_user_id")
    private Long sourceUserId;
    @JsonProperty("source_status_id_str")
    private String sourceStatusIdStr;
    @JsonProperty("source_status_id")
    private Long sourceStatusId;
    @JsonProperty("sizes")
    private Sizes___ sizes;
    @JsonProperty("type")
    private String type;
    @JsonProperty("expanded_url")
    private String expandedUrl;
    @JsonProperty("display_url")
    private String displayUrl;
    @JsonProperty("url")
    private String url;
    @JsonProperty("media_url_https")
    private String mediaUrlHttps;
    @JsonProperty("media_url")
    private String mediaUrl;
    @JsonProperty("indices")
    private List<Long> indices = null;
    @JsonProperty("id_str")
    private String idStr;
    @JsonProperty("id")
    private Long id;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    private final static long serialVersionUID = -4789559470967731735L;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Medium______() {
    }

    /**
     * 
     * @param sizes
     * @param mediaUrl
     * @param sourceStatusIdStr
     * @param sourceUserId
     * @param type
     * @param idStr
     * @param url
     * @param mediaUrlHttps
     * @param id
     * @param displayUrl
     * @param sourceStatusId
     * @param indices
     * @param sourceUserIdStr
     * @param expandedUrl
     */
    public Medium______(String sourceUserIdStr, Long sourceUserId, String sourceStatusIdStr, Long sourceStatusId, Sizes___ sizes, String type, String expandedUrl, String displayUrl, String url, String mediaUrlHttps, String mediaUrl, List<Long> indices, String idStr, Long id) {
        super();
        this.sourceUserIdStr = sourceUserIdStr;
        this.sourceUserId = sourceUserId;
        this.sourceStatusIdStr = sourceStatusIdStr;
        this.sourceStatusId = sourceStatusId;
        this.sizes = sizes;
        this.type = type;
        this.expandedUrl = expandedUrl;
        this.displayUrl = displayUrl;
        this.url = url;
        this.mediaUrlHttps = mediaUrlHttps;
        this.mediaUrl = mediaUrl;
        this.indices = indices;
        this.idStr = idStr;
        this.id = id;
    }

    @JsonProperty("source_user_id_str")
    public String getSourceUserIdStr() {
        return sourceUserIdStr;
    }

    @JsonProperty("source_user_id_str")
    public void setSourceUserIdStr(String sourceUserIdStr) {
        this.sourceUserIdStr = sourceUserIdStr;
    }

    @JsonProperty("source_user_id")
    public Long getSourceUserId() {
        return sourceUserId;
    }

    @JsonProperty("source_user_id")
    public void setSourceUserId(Long sourceUserId) {
        this.sourceUserId = sourceUserId;
    }

    @JsonProperty("source_status_id_str")
    public String getSourceStatusIdStr() {
        return sourceStatusIdStr;
    }

    @JsonProperty("source_status_id_str")
    public void setSourceStatusIdStr(String sourceStatusIdStr) {
        this.sourceStatusIdStr = sourceStatusIdStr;
    }

    @JsonProperty("source_status_id")
    public Long getSourceStatusId() {
        return sourceStatusId;
    }

    @JsonProperty("source_status_id")
    public void setSourceStatusId(Long sourceStatusId) {
        this.sourceStatusId = sourceStatusId;
    }

    @JsonProperty("sizes")
    public Sizes___ getSizes() {
        return sizes;
    }

    @JsonProperty("sizes")
    public void setSizes(Sizes___ sizes) {
        this.sizes = sizes;
    }

    @JsonProperty("type")
    public String getType() {
        return type;
    }

    @JsonProperty("type")
    public void setType(String type) {
        this.type = type;
    }

    @JsonProperty("expanded_url")
    public String getExpandedUrl() {
        return expandedUrl;
    }

    @JsonProperty("expanded_url")
    public void setExpandedUrl(String expandedUrl) {
        this.expandedUrl = expandedUrl;
    }

    @JsonProperty("display_url")
    public String getDisplayUrl() {
        return displayUrl;
    }

    @JsonProperty("display_url")
    public void setDisplayUrl(String displayUrl) {
        this.displayUrl = displayUrl;
    }

    @JsonProperty("url")
    public String getUrl() {
        return url;
    }

    @JsonProperty("url")
    public void setUrl(String url) {
        this.url = url;
    }

    @JsonProperty("media_url_https")
    public String getMediaUrlHttps() {
        return mediaUrlHttps;
    }

    @JsonProperty("media_url_https")
    public void setMediaUrlHttps(String mediaUrlHttps) {
        this.mediaUrlHttps = mediaUrlHttps;
    }

    @JsonProperty("media_url")
    public String getMediaUrl() {
        return mediaUrl;
    }

    @JsonProperty("media_url")
    public void setMediaUrl(String mediaUrl) {
        this.mediaUrl = mediaUrl;
    }

    @JsonProperty("indices")
    public List<Long> getIndices() {
        return indices;
    }

    @JsonProperty("indices")
    public void setIndices(List<Long> indices) {
        this.indices = indices;
    }

    @JsonProperty("id_str")
    public String getIdStr() {
        return idStr;
    }

    @JsonProperty("id_str")
    public void setIdStr(String idStr) {
        this.idStr = idStr;
    }

    @JsonProperty("id")
    public Long getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(Long id) {
        this.id = id;
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
