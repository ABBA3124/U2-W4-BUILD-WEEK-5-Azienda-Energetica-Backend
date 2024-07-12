package davideabbadessa.U2_W4_BUILD_WEEK_5_Azienda_Energetica.enums;

import davideabbadessa.U2_W4_BUILD_WEEK_5_Azienda_Energetica.exceptions.BadRequestException;

public enum TipoClienti {
    PA, SAS, SPA, SRL;

    public static TipoClienti getTipoClienti(String tipoClienti) {
        if (tipoClienti.equalsIgnoreCase("pa")) {
            return TipoClienti.PA;
        } else if (tipoClienti.equalsIgnoreCase("sas")) {
            return TipoClienti.SAS;
        } else if (tipoClienti.equalsIgnoreCase("spa")) {
            return TipoClienti.SPA;
        } else if (tipoClienti.equalsIgnoreCase("srl")) {
            return TipoClienti.SRL;
        } else {
            throw new BadRequestException("I Tipi di cliente possibili per l'aziende sono: PA,SAS,SPA,SRL");
        }
    }
}
