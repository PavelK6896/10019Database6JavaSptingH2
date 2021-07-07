package app.web.pavelk.database6.schema;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

import static javax.persistence.GenerationType.IDENTITY;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"id", "numberAction"})
@Table(schema = "test_1", name = "action_one")
public class ActionOne {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(name = "number_action")
    private Long numberAction;

    @Column(name = "id_area", insertable = false, updatable = false)
    private Long idArea;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_area")
    @ToString.Exclude
    @JsonBackReference
    private Area area;

    @Column(name = "id_type", insertable = false, updatable = false)
    private Long idType;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_type")
    @ToString.Exclude
    @JsonBackReference
    private TypeObject typeObject;

    @OneToMany(mappedBy = "actionOne")
    @ToString.Exclude
    @JsonBackReference
    private Set<PropertyOne> propertyOne;

}
