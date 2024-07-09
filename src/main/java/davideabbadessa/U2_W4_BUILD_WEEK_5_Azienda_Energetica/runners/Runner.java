package davideabbadessa.U2_W4_BUILD_WEEK_5_Azienda_Energetica.runners;

import davideabbadessa.U2_W4_BUILD_WEEK_5_Azienda_Energetica.entities.Provincia;
import davideabbadessa.U2_W4_BUILD_WEEK_5_Azienda_Energetica.services.ComuneService;
import davideabbadessa.U2_W4_BUILD_WEEK_5_Azienda_Energetica.services.CsvImportService;
import davideabbadessa.U2_W4_BUILD_WEEK_5_Azienda_Energetica.services.ProvinciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class Runner implements CommandLineRunner {

    @Autowired
    private CsvImportService csvImportService;

    @Autowired
    private ProvinciaService provinciaService;

    @Autowired
    private ComuneService comuneService;


    @Override
    @Transactional
    public void run(String... args) throws Exception {

        if (!provinciaService.esistonoProvince()) {
            csvImportService.importProvinciaDaCsv("src/main/resources/files/province-italiane.csv");
        }

        if (!comuneService.esistonoComuni()) {
            csvImportService.importComuniDaCsv("src/main/resources/files/comuni-italiani.csv");
        }

        System.out.println("Lista province senza comuni: ");
        List<Provincia> provinceSenzaComuni = provinciaService.trovaProvinceSenzaComuni();
        provinceSenzaComuni.forEach(System.out::println);

    }
}
