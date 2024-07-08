package davideabbadessa.U2_W4_BUILD_WEEK_5_Azienda_Energetica.runners;

import davideabbadessa.U2_W4_BUILD_WEEK_5_Azienda_Energetica.services.CsvImportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Runner implements CommandLineRunner {

    @Autowired
    private CsvImportService csvImportService;


    @Override
    public void run(String... args) throws Exception {
        csvImportService.importProvinciaDaCsv("src/main/resources/files/province-italiane.csv");
        csvImportService.importComuniDaCsv("src/main/resources/files/comuni-italiani.csv");
    }
}
