package davideabbadessa.U2_W4_BUILD_WEEK_5_Azienda_Energetica.enums;

import davideabbadessa.U2_W4_BUILD_WEEK_5_Azienda_Energetica.exceptions.BadRequestException;

public enum StatusFattura {
    EMESSA, PAGATA, DA_SALDARE, SCADUTA;

    public static StatusFattura getStatoFattura(String statusFattura) {
        if (statusFattura.equalsIgnoreCase("emessa")) {
            return StatusFattura.EMESSA;
        } else if (statusFattura.equalsIgnoreCase("pagata")) {
            return StatusFattura.PAGATA;
        } else if (statusFattura.equalsIgnoreCase("da_saldare")) {
            return StatusFattura.DA_SALDARE;
        } else if (statusFattura.equalsIgnoreCase("scaduta")) {
            return StatusFattura.SCADUTA;
        } else {
            throw new BadRequestException("I Tipi di stato possibili per le fatture sono: EMESSA,PAGATA,DA_SALDARE,SCADUTA");
        }
    }
}
