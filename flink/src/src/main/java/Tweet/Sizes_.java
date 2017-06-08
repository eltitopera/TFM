
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
public class Sizes_ implements Serializable
{

    @JsonProperty("small")
    private Small_ small;
    @JsonProperty("medium")
    private Medium___ medium;
    @JsonProperty("thumb")
    private Thumb_ thumb;
    @JsonProperty("large")
    private Large_ large;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    private final static long serialVersionUID = 7088246302870836490L;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Sizes_() {
    }

    /**
     * 
     * @param thumb
     * @param small
     * @param large
     * @param medium
     */
    public Sizes_(Small_ small, Medium___ medium, Thumb_ thumb, Large_ large) {
        super();
        this.small = small;
        this.medium = medium;
        this.thumb = thumb;
        this.large = large;
    }

    @JsonProperty("small")
    public Small_ getSmall() {
        return small;
    }

    @JsonProperty("small")
    public void setSmall(Small_ small) {
        this.small = small;
    }

    @JsonProperty("medium")
    public Medium___ getMedium() {
        return medium;
    }

    @JsonProperty("medium")
    public void setMedium(Medium___ medium) {
        this.medium = medium;
    }

    @JsonProperty("thumb")
    public Thumb_ getThumb() {
        return thumb;
    }

    @JsonProperty("thumb")
    public void setThumb(Thumb_ thumb) {
        this.thumb = thumb;
    }

    @JsonProperty("large")
    public Large_ getLarge() {
        return large;
    }

    @JsonProperty("large")
    public void setLarge(Large_ large) {
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
