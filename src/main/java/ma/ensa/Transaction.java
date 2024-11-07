package ma.ensa;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.List;


public class Transaction {
    public enum Type {
        VIRIN,    // Internal Transfer
        VIREST,   // External Transfer
        VIRCHAC,  // Charge Transfer
        VIRMULTA  // Multi-Account Transfer
    }

    private final Type transactionType;
    private final Date timestamp;
    private final int reference;

    @JsonIgnoreProperties("transactions") // Avoid circular reference in `Compte`
    private final Compte sourceCompte;

    private final List<Compte> destinationComptes;

    // Constructor for both single and multiple destination accounts
    @JsonCreator
    public Transaction(
            @JsonProperty("timestamp") Date timestamp,
            @JsonProperty("reference") int reference,
            @JsonProperty("sourceCompte") Compte sourceCompte,
            @JsonProperty("destinationComptes") List<Compte> destinationComptes) {
        this.timestamp = new Date(timestamp.getTime()); // Defensive copy
        this.reference = reference;
        this.sourceCompte = sourceCompte;
        this.destinationComptes = destinationComptes; // Directly set the list of destination accounts
        // Determine transaction type based on destination accounts
        if (destinationComptes.size() > 1) {
            this.transactionType = Type.VIRMULTA; // Multi-Account Transfer
        } else {
            this.transactionType = determineTransactionType(sourceCompte, destinationComptes.get(0));
        }
    }

    // Getters
    public Type getTransactionType() {
        return transactionType;
    }

    public Date getTimestamp() {
        return new Date(timestamp.getTime()); // Defensive copy
    }

    public int getReference() {
        return reference;
    }

    public Compte getSourceCompte() {
        return sourceCompte;
    }

    public List<Compte> getDestinationComptes() {
        return destinationComptes;
    }

    private Type determineTransactionType(Compte source, Compte destination) {
        if (source.getBanque().getId() == destination.getBanque().getId() &&
                source.getBanque().getPays().equals(destination.getBanque().getPays())) {
            return Type.VIRIN; // Same bank, same country
        } else if (!source.getBanque().getPays().equals(destination.getBanque().getPays()) &&
                source.getBanque().getId() != destination.getBanque().getId()) {
            return Type.VIRCHAC; // Different bank, different country
        } else {
            return Type.VIREST; // Different bank or different country
        }
    }
}
