package app.web.pavelk.database6.schema;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import static javax.persistence.GenerationType.IDENTITY;


@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"id", "name"})
@Table(schema = "test_1", name = "type_object")
public class TypeObject {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String name;
}
