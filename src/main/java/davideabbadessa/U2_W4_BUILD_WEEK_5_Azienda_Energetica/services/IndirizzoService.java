package davideabbadessa.U2_W4_BUILD_WEEK_5_Azienda_Energetica.services;

import davideabbadessa.U2_W4_BUILD_WEEK_5_Azienda_Energetica.repositories.IndirizzoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IndirizzoService {
    @Autowired
    private IndirizzoRepository indirizzoRepository;
}
