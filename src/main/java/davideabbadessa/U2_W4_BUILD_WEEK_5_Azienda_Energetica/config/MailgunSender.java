package davideabbadessa.U2_W4_BUILD_WEEK_5_Azienda_Energetica.config;


import davideabbadessa.U2_W4_BUILD_WEEK_5_Azienda_Energetica.entities.Cliente;
import davideabbadessa.U2_W4_BUILD_WEEK_5_Azienda_Energetica.entities.Utente;
import kong.unirest.core.HttpResponse;
import kong.unirest.core.JsonNode;
import kong.unirest.core.Unirest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MailgunSender {

    private String mailgunApiKey;
    private String mailgunDomainName;
    private String mailgunName;
    private String mailgunEmail;

    public MailgunSender(@Value("${mailgun.apikey}") String mailgunApiKey, @Value("${mailgun.domainname}") String mailgunDomainName, @Value("${mailgun.personalname}") String mailgunName, @Value("${mailgun.personalemail}") String mailgunEmail) {
        this.mailgunApiKey = mailgunApiKey;
        this.mailgunDomainName = mailgunDomainName;
        this.mailgunName = mailgunName;
        this.mailgunEmail = mailgunEmail;
    }

    public void sendRegistrationEmail(Utente recipient) {
        HttpResponse<JsonNode> response = Unirest.post("https://api.mailgun.net/v3/" + this.mailgunDomainName + "/messages")
                .basicAuth("api", this.mailgunApiKey)
                .queryString("from", this.mailgunName + " <" + this.mailgunEmail + ">")
                .queryString("to", recipient.getEmail())
                .queryString("subject", "Registrazione effettuata")
                .queryString("text", "Congratulazioni " + recipient.getNome() + " " + recipient.getCognome() + "! La tua registrazione e' andata a buon fine.")
                .asJson();
    }

    public void sendAdminMail(Utente utente, Cliente cliente, String text, String subject) {
        HttpResponse<JsonNode> response = Unirest.post("https://api.mailgun.net/v3/" + this.mailgunDomainName + "/messages")
                .basicAuth("api", this.mailgunApiKey)
                .queryString("from", utente.getNome() + " <" + utente.getEmail() + ">")
                .queryString("to", cliente.getEmailContatto())
                .queryString("subject", subject)
                .queryString("text", text)
                .asJson();
    }
}

