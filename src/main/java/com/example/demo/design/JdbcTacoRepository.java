package com.example.demo.design;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.sql.Types;
import java.util.Date;
import java.util.List;

@Repository
public class JdbcTacoRepository implements TacoRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(JdbcTacoRepository.class);
    private final JdbcTemplate jdbc;

    @Autowired
    public JdbcTacoRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public Taco save(Taco taco) {
        final long tacoId = saveTacoInfo(taco);
        taco.setId(tacoId);
        saveIngredients(taco, tacoId);
        return taco;
    }

    private long saveTacoInfo(Taco taco) {
        taco.setCreatedAt(new Date());
        // You must call setReturnGeneratedKeys because of this regression:
        // Regression (1.4.197) - Generated keys is empty on insert #1052
        // https://github.com/h2database/h2database/issues/1052
        final PreparedStatementCreatorFactory pscf = new PreparedStatementCreatorFactory(
                "insert into Taco (name, createdAt) values (?, ?)",
                Types.VARCHAR, Types.TIMESTAMP
        );
        pscf.setReturnGeneratedKeys(true);
        final PreparedStatementCreator psc = pscf.newPreparedStatementCreator(List.of(
                taco.getName(),
                new Timestamp(taco.getCreatedAt().getTime())
        ));
        final GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        jdbc.update(psc, generatedKeyHolder);
        return generatedKeyHolder.getKey().longValue();
    }

    private void saveIngredients(Taco taco, long tacoId) {
        for (Ingredient ingredient : taco.getIngredients()) {
            jdbc.update("insert into Taco_Ingredients (taco, ingredient) values(?,?)",
                    tacoId, ingredient.getId());
        }
    }

}
