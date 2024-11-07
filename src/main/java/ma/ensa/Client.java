package ma.ensa;

import com.fasterxml.jackson.annotation.*;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.ArrayList;
import java.util.List;
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "numClient")
public class Client {
    private int numClient;
    private String nom;
    private String prenom;
    private String adresse;
    private String phone; // Changed to String for consistency
    private String email;

    @JsonIgnoreProperties("client") // To avoid circular references in `Compte`
    private List<Compte> comptes;

    // Constructor with @JsonCreator and @JsonProperty for deserialization
    @JsonCreator
    public Client(@JsonProperty("numClient") int numClient,
                  @JsonProperty("nom") String nom,
                  @JsonProperty("prenom") String prenom,
                  @JsonProperty("adresse") String adresse,
                  @JsonProperty("phone") String phone,
                  @JsonProperty("email") String email) {
        this.numClient = numClient;
        this.nom = nom;
        this.prenom = prenom;
        this.adresse = adresse;
        this.phone = phone;
        this.email = email;
        this.comptes = new ArrayList<>();
    }

    public void addCompte(Compte compte) {
        this.comptes.add(compte);
    }

    public void removeCompte(Compte compte) {
        this.comptes.remove(compte);
    }

    public List<Compte> getComptes() {
        return new ArrayList<>(comptes); // Defensive copy
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public int getNumClient() {
        return numClient; // Returning as integer
    }

    public String getAdresse() {
        return adresse;
    }

    public String getPhone() {
        return phone; // Return phone as String
    }

    public String getEmail() {
        return email;
    }
}
