package info.jemsit.product_service.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class DatabaseSeeder implements ApplicationRunner {

    private final ObjectMapper objectMapper;
    private final JdbcTemplate jdbc;
    @Override
    public void run(ApplicationArguments args) throws Exception {

        jdbc.execute("CREATE TABLE IF NOT EXISTS regions (" +
                "id BIGINT PRIMARY KEY, " +
                "name_uz VARCHAR(255), " +
                "name_oz VARCHAR(255), " +
                "name_ru VARCHAR(255)" +
                ")");
            jdbc.execute("CREATE TABLE IF NOT EXISTS districts (" +
                "id BIGINT PRIMARY KEY, " +
                "region_id BIGINT, " +
                "name_uz VARCHAR(255), " +
                "name_oz VARCHAR(255), " +
                "name_ru VARCHAR(255), " +
                "FOREIGN KEY (region_id) REFERENCES regions(id)" +
                ")");
            jdbc.execute("CREATE TABLE IF NOT EXISTS villages (" +
                "id BIGINT PRIMARY KEY, " +
                "district_id BIGINT, " +
                "name_uz VARCHAR(255), " +
                "name_oz VARCHAR(255), " +
                "name_ru VARCHAR(255), " +
                "FOREIGN KEY (district_id) REFERENCES uz_district(id)" +
                ")");

        Integer regionsCount = jdbc.queryForObject("SELECT COUNT(*) FROM regions", Integer.class);
        Integer districtsCount = jdbc.queryForObject("SELECT COUNT(*) FROM districts", Integer.class);
        Integer villages = jdbc.queryForObject("SELECT COUNT(*) FROM villages", Integer.class);


        if (regionsCount == null ||  regionsCount == 0) {
            seedRegions();
        }
        if (districtsCount == null || districtsCount == 0) {
            seedDistricts();
        }
        if (villages == null || villages == 0) {
            seedVillages();
        }
        System.out.println("Seeding done!");
    }

    private void seedRegions() {
        // Implement logic to seed regions into the database
        try {
            ClassPathResource resource = new ClassPathResource("data/regions.json");
            List<Map> rows = objectMapper.readValue(resource.getInputStream(), objectMapper.getTypeFactory().constructCollectionType(List.class, Map.class));

            for (Map row : rows) {
                jdbc.update(
                        "INSERT INTO regions (id, name_uz, name_oz, name_ru) VALUES (?, ?, ?, ?)",
                        row.get("id"), row.get("name_uz"), row.get("name_oz"), row.get("name_ru")
                );
            }
        } catch (Exception e) {
            System.out.println("Error seeding regions: " + e.getMessage());
        }
    }
    private void seedDistricts() throws Exception {
        try {
            ClassPathResource resource = new ClassPathResource("data/districts.json");
            List<Map> rows = objectMapper.readValue(resource.getInputStream(),
                    objectMapper.getTypeFactory().constructCollectionType(List.class, Map.class));

            for (Map row : rows) {
                jdbc.update(
                        "INSERT INTO districts (id, region_id, name_uz, name_oz, name_ru) VALUES (?, ?, ?, ?, ?)",
                        row.get("id"), row.get("region_id"), row.get("name_uz"), row.get("name_oz"), row.get("name_ru")
                );
            }
        }catch (Exception e) {
            System.out.println("Error seeding districts: " + e.getMessage());
        }
    }

    private void seedVillages() throws Exception {

        try {
            ClassPathResource resource = new ClassPathResource("data/villages.json");
            List<Map> rows = objectMapper.readValue(resource.getInputStream(),
                    objectMapper.getTypeFactory().constructCollectionType(List.class, Map.class));

            for (Map row : rows) {
                jdbc.update(
                        "INSERT INTO villages (id, district_id, name_uz, name_oz, name_ru) VALUES (?, ?, ?, ?, ?)",
                        row.get("id"), row.get("district_id"), row.get("name_uz"), row.get("name_oz"), row.get("name_ru")
                );
            }
        }catch (Exception e) {
            System.out.println("Error seeding villages: " + e.getMessage());
        }

    }


}
