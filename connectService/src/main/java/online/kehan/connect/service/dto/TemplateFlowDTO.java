package online.kehan.connect.service.dto;

import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import javax.persistence.Lob;

/**
 * A DTO for the {@link online.kehan.connect.domain.TemplateFlow} entity.
 */
public class TemplateFlowDTO implements Serializable {
    
    private Long id;

    
    @Lob
    private String connectDetails;

    @NotNull
    private String userId;

    @NotNull
    private String createdBy;

    private ZonedDateTime createdDate;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getConnectDetails() {
        return connectDetails;
    }

    public void setConnectDetails(String connectDetails) {
        this.connectDetails = connectDetails;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
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
        if (!(o instanceof TemplateFlowDTO)) {
            return false;
        }

        return id != null && id.equals(((TemplateFlowDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TemplateFlowDTO{" +
            "id=" + getId() +
            ", connectDetails='" + getConnectDetails() + "'" +
            ", userId='" + getUserId() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            "}";
    }
}
