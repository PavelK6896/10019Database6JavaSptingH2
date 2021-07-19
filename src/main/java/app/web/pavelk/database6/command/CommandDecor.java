package app.web.pavelk.database6.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class CommandDecor {
    Long id;
    String key;
    String info;
    String description;
    Execute execute;
}
