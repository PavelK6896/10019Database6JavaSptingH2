package app.web.pavelk.database6.schema;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;


@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"idOne", "idTwo"})
@Table(schema = "test_1", name = "action_one_two")
@IdClass(ActionOneTwo.ActionOneTwoId.class)
public class ActionOneTwo {

    @Id
    private Long idOne;

    @Id
    private Long idTwo;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_one")
    @ToString.Exclude
    @JsonBackReference
    private ActionOne actionOne;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_two")
    @ToString.Exclude
    @JsonBackReference
    private ActionTwo actionTwo;

    @NoArgsConstructor
    @AllArgsConstructor
    public static class ActionOneTwoId implements Serializable {

        @Column(name = "id_one", insertable = false, updatable = false)
        private Long idOne;

        @Column(name = "id_two", insertable = false, updatable = false)
        private Long idTwo;
    }


}
