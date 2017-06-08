
package Tweet;

import java.io.Serializable;
import java.util.HashMap;
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
    "small",
    "medium",
    "thumb",
    "large"
})
public class Sizes___ implements Serializable
{

    @JsonProperty("small")
    private Small___ small;
    @JsonProperty("medium")
    private Medium_______ medium;
    @JsonProperty("thumb")
    private Thumb___ thumb;
    @JsonProperty("large")
    private Large___ large;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    private final static long serialVersionUID = 7397978459057426333L;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Sizes___() {
    }

    /**
     * 
     * @param thumb
     * @param small
     * @param large
     * @param medium
     */
    public Sizes___(Small___ small, Medium_______ medium, Thumb___ thumb, Large___ large) {
        super();
        this.small = small;
        this.medium = medium;
        this.thumb = thumb;
        this.large = large;
    }

    @JsonProperty("small")
    public Small___ getSmall() {
        return small;
    }

    @JsonProperty("small")
    public void setSmall(Small___ small) {
        this.small = small;
    }

    @JsonProperty("medium")
    public Medium_______ getMedium() {
        return medium;
    }

    @JsonProperty("medium")
    public void setMedium(Medium_______ medium) {
        this.medium = medium;
    }

    @JsonProperty("thumb")
    public Thumb___ getThumb() {
        return thumb;
    }

    @JsonProperty("thumb")
    public void setThumb(Thumb___ thumb) {
        this.thumb = thumb;
    }

    @JsonProperty("large")
    public Large___ getLarge() {
        return large;
    }

    @JsonProperty("large")
    public void setLarge(Large___ large) {
        this.large = large;
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
