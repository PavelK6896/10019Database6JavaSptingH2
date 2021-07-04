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
@EqualsAndHashCode(of = {"id", "code"})
@Table(schema = "test_1", name = "area")
public class Area {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(name = "code")
    private Long code;

    @Column(name = "id_car")
    private Long idCar;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_car",  insertable = false, updatable = false)
    @ToString.Exclude
    @JsonBackReference
    private Car car;


}
