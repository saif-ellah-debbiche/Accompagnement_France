package org.example.accompagnement_france_backend.retention.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.accompagnement_france_backend.retention.enums.RetentionAction;

@Entity
@Getter
@Setter
public class RetentionRule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // The configurable parameter: 30, 90, 180, etc.
    private int daysToRetainAfterClosure;

    // The configurable action: DELETE or ANONYMIZE
    @Enumerated(EnumType.STRING)
    private RetentionAction actionType;

    // Flag to ensure only one policy is currently enforced by the scheduler
    private boolean isActive = true;

    @Override
    public String toString() {
        return "RetentionRule{" +
                "daysToRetainAfterClosure=" + daysToRetainAfterClosure +
                ", actionType=" + actionType +
                ", isActive=" + isActive +
                '}';
    }
}
