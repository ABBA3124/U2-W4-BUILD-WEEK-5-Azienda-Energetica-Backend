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
import java.util.*;

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

        Provincia roma = provinciaService.findByName("Roma").get();
        roma.setSigla("RM");
        provinciaService.save(roma);

        Provincia forliCesena = provinciaService.findByName("Forli-Cesena").get();
        forliCesena.setNome("Forlì-Cesena");
        provinciaService.save(forliCesena);

        provinciaService.save(forliCesena);

        provinciaService.save(new Provincia("Sud Sardegna", "SU", "Sardegna"));
        provinciaService.save(new Provincia("Verbano-Cusio-Ossola", "VCO", "Piemonte"));

        provinciaService.delete(provinciaService.findByName("Olbia Tempio").get());
        provinciaService.delete(provinciaService.findByName("Carbonia Iglesias").get());
        provinciaService.delete(provinciaService.findByName("Ogliastra").get());
        provinciaService.delete(provinciaService.findByName("Verbania").get());
        provinciaService.delete(provinciaService.findByName("Medio Campidano").get());
    }


    public void importComuniDaCsv(String filePath) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
        String line;
        boolean firstLine = true;

        Set<String> provinceSbagliate = new HashSet<>();

        Map<String, String> provinceMap = new HashMap<>();
        provinceMap.put("Monza e della Brianza", "Monza-Brianza");
        provinceMap.put("Vibo Valentia", "Vibo-Valentia");
        provinceMap.put("La Spezia", "La-Spezia");
        provinceMap.put("Valle d'Aosta/Vallée d'Aoste", "Aosta");
        provinceMap.put("Ascoli Piceno", "Ascoli-Piceno");
        provinceMap.put("Bolzano/Bozen", "Bolzano");
        provinceMap.put("Pesaro e Urbino", "Pesaro-Urbino");
        provinceMap.put("Reggio Calabria", "Reggio-Calabria");
        provinceMap.put("Reggio nell'Emilia", "Reggio-Emilia");

        while ((line = bufferedReader.readLine()) != null) {
            if (firstLine) {
                firstLine = false;
                continue;
            }

            String[] values = line.split(";");


            if (values.length > 3) {

                if (provinceMap.containsKey(values[3].trim())) {
                    values[3] = provinceMap.get(values[3].trim());
                }

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
