package nais.sales.service.sales_service.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "lifecycle_phases", uniqueConstraints = @UniqueConstraint(name = "uk_lifecycle_phase_position", columnNames = "position"))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString()
@DynamicInsert
@DynamicUpdate
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class LifecyclePhase {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "lifecycle_phases_seq")
    @SequenceGenerator(name = "lifecycle_phases_seq", sequenceName = "lifecycle_phases_seq_id", initialValue = 1, allocationSize = 1)
    private long id;

    @Column(name = "title")
    private String title;

    @Column(name = "position")
    private int position;

    @Column(name = "description")
    private String description;

    @ManyToMany
    @JoinTable(
            name = "lifecycle_phase_next",
            joinColumns = @JoinColumn(name = "phase_id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "next_phase_id", nullable = false)
    )
    @com.fasterxml.jackson.annotation.JsonIdentityReference(alwaysAsId = true)
    private Set<LifecyclePhase> nextPhases = new HashSet<>();
}
