
package POJO;

import com.fasterxml.jackson.annotation.*;
import org.apache.commons.lang.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "caracter",
    "said"
})
public class CaracterPOJO implements Serializable
{

    @JsonProperty("caracter")
    private String caracter;
    @JsonProperty("said")
    private String said;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    private final static long serialVersionUID = 2596054684680115586L;

    /**
     * No args constructor for use in serialization
     * 
     */
    public CaracterPOJO() {
    }

    /**
     * 
     * @param said
     * @param caracter
     */
    public CaracterPOJO(String caracter, String said) {
        super();
        this.caracter = caracter;
        this.said = said;
    }

    @JsonProperty("caracter")
    public String getCaracter() {
        return caracter;
    }

    @JsonProperty("caracter")
    public void setCaracter(String caracter) {
        this.caracter = caracter;
    }

    @JsonProperty("said")
    public String getSaid() {
        return said;
    }

    @JsonProperty("said")
    public void setSaid(String said) {
        this.said = said;
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
