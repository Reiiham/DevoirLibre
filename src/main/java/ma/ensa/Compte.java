package ma.ensa;

import com.fasterxml.jackson.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import com.fasterxml.jackson.annotation.JsonCreator;

import com.fasterxml.jackson.annotation.JsonProperty;


@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "numCompte")
@JsonIgnoreProperties({"client", "transactions"}) // Prevent circular reference in serialization
public class Compte {
    private int numCompte;
    private Date dateCreation;
    private Date dateUpdate;
    private String devise;

    @JsonIgnoreProperties("comptes") // Avoid circular reference in `Client`
    private Client client;

    private Banque banque;
    private List<Transaction> transactions;

    // Added clientId for deserialization
    private int clientId;

    // Constructor without adding to client
    @JsonCreator
    public Compte(@JsonProperty("numCompte") int numCompte,
                  @JsonProperty("dateCreation") Date dateCreation,
                  @JsonProperty("dateUpdate") Date dateUpdate,
                  @JsonProperty("devise") String devise,
                  @JsonProperty("banque") Banque banque,
                  @JsonProperty("clientId") int clientId) {
        this.numCompte = numCompte;
        this.dateCreation = new Date(dateCreation.getTime()); // Defensive copy
        this.dateUpdate = new Date(dateUpdate.getTime()); // Defensive copy
        this.devise = devise;
        this.banque = banque;
        this.clientId = clientId;  // Use clientId for linking
        transactions = new ArrayList<>();
    }

    // Getters and Setters
    public int getNumCompte() {
        return numCompte;
    }

    public void setNumCompte(int numCompte) {
        this.numCompte = numCompte;
    }

    public Date getDateCreation() {
        return new Date(dateCreation.getTime()); // Defensive copy
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = new Date(dateCreation.getTime()); // Defensive copy
    }

    public Date getDateUpdate() {
        return new Date(dateUpdate.getTime()); // Defensive copy
    }

    public void setDateUpdate(Date dateUpdate) {
        this.dateUpdate = new Date(dateUpdate.getTime()); // Defensive copy
    }

    public String getDevise() {
        return devise;
    }

    public void setDevise(String devise) {
        this.devise = devise;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Banque getBanque() {
        return banque;
    }

    public void setBanque(Banque banque) {
        this.banque = banque;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }
}
