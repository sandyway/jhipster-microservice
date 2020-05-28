package online.kehan.connect.service.dto;

import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import javax.persistence.Lob;

/**
 * A DTO for the {@link online.kehan.connect.domain.ConnectConfig} entity.
 */
public class ConnectConfigDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String userId;

    @Lob
    private String facebook;

    @Lob
    private String viber;

    private ZonedDateTime createdDate;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public String getViber() {
        return viber;
    }

    public void setViber(String viber) {
        this.viber = viber;
    }

    public ZonedDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ConnectConfigDTO)) {
            return false;
        }

        return id != null && id.equals(((ConnectConfigDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ConnectConfigDTO{" +
            "id=" + getId() +
            ", userId='" + getUserId() + "'" +
            ", facebook='" + getFacebook() + "'" +
            ", viber='" + getViber() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            "}";
    }
}
