package app.web.pavelk.database6.repo;


import app.web.pavelk.database6.schema.Area;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface AreaRepo extends Repository<Area, Long> {
    List<Area> findAll();
    Area saveAndFlush(Area area);
}
