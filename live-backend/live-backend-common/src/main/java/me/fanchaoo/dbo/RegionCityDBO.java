package me.fanchaoo.dbo;
import lombok.Data;

/**
 * 
 *
 * @author null
 * @date 2020-06-20
 */
@Data
public class RegionCityDBO {
    /**
     * 
     */
    private String id;

    /**
     * 
     */
    private String name;

    /**
     * 
     */
    private String provinceId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId == null ? null : provinceId.trim();
    }
}