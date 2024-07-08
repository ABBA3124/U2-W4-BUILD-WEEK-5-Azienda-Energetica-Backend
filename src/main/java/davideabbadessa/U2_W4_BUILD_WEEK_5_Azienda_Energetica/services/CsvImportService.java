package davideabbadessa.U2_W4_BUILD_WEEK_5_Azienda_Energetica.services;


import davideabbadessa.U2_W4_BUILD_WEEK_5_Azienda_Energetica.entities.Comune;
import davideabbadessa.U2_W4_BUILD_WEEK_5_Azienda_Energetica.entities.Provincia;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class CsvImportService {

    private static final Logger log = LoggerFactory.getLogger(CsvImportService.class);
    @Autowired
    private ProvinciaService provinciaService;

    @Autowired
    private ComuneService comuneService;

    public void importProvinciaDaCsv(String filePath) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
        String line;
        boolean firstLine = true;

        while ((line = bufferedReader.readLine()) != null) {
            if (firstLine) {
                firstLine = false;
                continue;
            }

            String[] values = line.split(";");
            if (values.length >= 2) {
                Provincia nuovaProvincia = new Provincia(values[1].trim(), values[0].trim(), values[2].trim());
                provinciaService.save(nuovaProvincia);
            }
        }
        bufferedReader.close();

    }


    public void importComuniDaCsv(String filePath) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
        String line;
        boolean firstLine = true;

        Set<String> provinceSbagliate = new HashSet<>();

        while ((line = bufferedReader.readLine()) != null) {
            if (firstLine) {
                firstLine = false;
                continue;
            }

            String[] values = line.split(";");

            if (values.length > 3) {
                Optional<Provincia> optionalProvincia = provinciaService.findByName(values[3].trim());

                if (!optionalProvincia.isPresent()) {
                    provinceSbagliate.add(values[3].trim());
                } else {
                    Provincia provinciaDelComune = optionalProvincia.get();
                    Comune nuovoComune = new Comune(values[2].trim(), provinciaDelComune);
                    comuneService.save(nuovoComune);
                }


            }
        }
        bufferedReader.close();
        System.out.println("Lista delle province non inserite:");

        provinceSbagliate.forEach(System.out::println);
    }
}
