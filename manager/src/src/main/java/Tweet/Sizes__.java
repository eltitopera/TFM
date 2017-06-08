
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
public class Sizes__ implements Serializable
{

    @JsonProperty("small")
    private Small__ small;
    @JsonProperty("medium")
    private Medium_____ medium;
    @JsonProperty("thumb")
    private Thumb__ thumb;
    @JsonProperty("large")
    private Large__ large;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    private final static long serialVersionUID = -670293115887595508L;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Sizes__() {
    }

    /**
     * 
     * @param thumb
     * @param small
     * @param large
     * @param medium
     */
    public Sizes__(Small__ small, Medium_____ medium, Thumb__ thumb, Large__ large) {
        super();
        this.small = small;
        this.medium = medium;
        this.thumb = thumb;
        this.large = large;
    }

    @JsonProperty("small")
    public Small__ getSmall() {
        return small;
    }

    @JsonProperty("small")
    public void setSmall(Small__ small) {
        this.small = small;
    }

    @JsonProperty("medium")
    public Medium_____ getMedium() {
        return medium;
    }

    @JsonProperty("medium")
    public void setMedium(Medium_____ medium) {
        this.medium = medium;
    }

    @JsonProperty("thumb")
    public Thumb__ getThumb() {
        return thumb;
    }

    @JsonProperty("thumb")
    public void setThumb(Thumb__ thumb) {
        this.thumb = thumb;
    }

    @JsonProperty("large")
    public Large__ getLarge() {
        return large;
    }

    @JsonProperty("large")
    public void setLarge(Large__ large) {
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
