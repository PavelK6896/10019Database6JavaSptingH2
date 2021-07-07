package app.web.pavelk.database6.schema;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"id", "value"})
@Table(schema = "test_1", name = "property_two")
public class PropertyTwo {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private Long value;

    @Column(name = "id_action", insertable = false, updatable = false)
    private Long idAction;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_action")
    @ToString.Exclude
    @JsonBackReference
    private ActionTwo actionTwo;

}
