package ma.ensa;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;


public class Banque {
    private final int id;
    private final String nom;
    private final String pays;


    @JsonCreator
    public Banque(
            @JsonProperty("id") int id,
            @JsonProperty("nom") String nom,
            @JsonProperty("pays") String pays) {
        this.id = id;
        this.nom = nom;
        this.pays = pays;
    }


    // Getters
    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getPays() {
        return pays;
    }

}


