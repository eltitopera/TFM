
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
    "media"
})
public class ExtendedEntities implements Serializable
{

    @JsonProperty("media")
    private List<Medium__> media = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    private final static long serialVersionUID = 3104019600037314894L;

    /**
     * No args constructor for use in serialization
     * 
     */
    public ExtendedEntities() {
    }

    /**
     * 
     * @param media
     */
    public ExtendedEntities(List<Medium__> media) {
        super();
        this.media = media;
    }

    @JsonProperty("media")
    public List<Medium__> getMedia() {
        return media;
    }

    @JsonProperty("media")
    public void setMedia(List<Medium__> media) {
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
