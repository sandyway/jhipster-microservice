package online.kehan.connect.service.dto;

import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import javax.persistence.Lob;

/**
 * A DTO for the {@link online.kehan.connect.domain.ConnectEvent} entity.
 */
public class ConnectEventDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String analyticId;

    @Lob
    private String connectEvent;

    @Lob
    private String reference;

    private ZonedDateTime createdDate;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAnalyticId() {
        return analyticId;
    }

    public void setAnalyticId(String analyticId) {
        this.analyticId = analyticId;
    }

    public String getConnectEvent() {
        return connectEvent;
    }

    public void setConnectEvent(String connectEvent) {
        this.connectEvent = connectEvent;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
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
        if (!(o instanceof ConnectEventDTO)) {
            return false;
        }

        return id != null && id.equals(((ConnectEventDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ConnectEventDTO{" +
            "id=" + getId() +
            ", analyticId='" + getAnalyticId() + "'" +
            ", connectEvent='" + getConnectEvent() + "'" +
            ", reference='" + getReference() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            "}";
    }
}
