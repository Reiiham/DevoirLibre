package ma.ensa;

import java.util.List;

public class AccountService {

    // Method to link the comptes with their client using clientId
    public void associateClientWithComptes(List<Compte> comptes, List<Client> clients) {
        for (Compte compte : comptes) {
            for (Client client : clients) {
                // Match based on clientId in the Compte and Client
                if (compte.getClientId() == client.getNumClient()) {
                    client.addCompte(compte);  // Add compte to the client
                    compte.setClient(client);  // Set client to the compte
                    break;  // Exit loop once the right client is found
                }
            }
        }
    }
}
