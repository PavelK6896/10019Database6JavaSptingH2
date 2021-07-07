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
@Table(schema = "test_1", name = "action_two")
public class ActionTwo {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(name = "number_action")
    private Long numberAction;

    @OneToMany(mappedBy = "actionTwo")
    @ToString.Exclude
    @JsonBackReference
    private Set<PropertyTwo> propertyTwo;

}
