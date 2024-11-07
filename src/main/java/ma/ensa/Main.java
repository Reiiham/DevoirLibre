package ma.ensa;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            // Key Part 1: Create Clients and Banks
            Client client1 = new Client(1, "John", "Doe", "123 Main St", "1234567890", "john.doe@example.com");
            Client client2 = new Client(2, "Jane", "Smith", "456 Another St", "987654320", "jane.smith@example.com");
            Client client3 = new Client(3, "Jne", "Smith", "456 Another St", "987654320", "jane.smith@example.com");
            Client client4 = new Client(4, "Jae", "Smith", "456 Another St", "987654320", "jane.smith@example.com");

            Banque banque1 = new Banque(234, "BMCE", "Maroc");
            Banque banque2 = new Banque(567, "Société Générale", "France");
            Banque banque3 = new Banque(567, "Société Générale", "Maroc");

            // Key Part 2: Create Accounts and associate each with a client by clientId
            Compte compte1 = new Compte(101, new Date(), new Date(), "USD", banque1, 1);
            Compte compte2 = new Compte(102, new Date(), new Date(), "EUR", banque1, 2);
            Compte compte3 = new Compte(103, new Date(), new Date(), "GBP", banque3, 3);
            Compte compte4 = new Compte(104, new Date(), new Date(), "MAD", banque2, 4);

            // Associate accounts with clients using AccountService
            List<Client> clients = List.of(client1, client2, client3, client4);
            List<Compte> comptes = List.of(compte1, compte2, compte3, compte4);
            AccountService accountService = new AccountService();
            accountService.associateClientWithComptes(comptes, clients);

            // Key Part 3: Create Transactions (Various Types)
            List<Compte> compteList1 = new ArrayList<>();
            compteList1.add(compte2); // Internal transfer (VIRIN)
            Transaction transaction1 = new Transaction(new Date(), 1, compte1, compteList1);

            List<Compte> compteList2 = new ArrayList<>();
            compteList2.add(compte3); // External transfer (VIREST)
            Transaction transaction2 = new Transaction(new Date(), 2, compte4, compteList2);

            List<Compte> compteList3 = new ArrayList<>();
            compteList3.add(compte4); // Charge transfer (VIRCHAC)
            Transaction transaction3 = new Transaction(new Date(), 3, compte1, compteList3);

            List<Compte> destinationComptes = new ArrayList<>();
            destinationComptes.add(compte2);
            destinationComptes.add(compte3); // Multi-account transfer (VIRMULTA)
            Transaction transaction4 = new Transaction(new Date(), 4, compte1, destinationComptes);

            // Key Part 4: Serialization - Convert transactions to JSON
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.findAndRegisterModules(); // Register modules for date and time serialization
            objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

            String transaction1Json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(transaction1);
            System.out.println("Serialized Transaction 1 (VIRIN): " + transaction1Json);

            String transaction2Json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(transaction2);
            System.out.println("Serialized Transaction 2 (VIREST): " + transaction2Json);

            String transaction3Json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(transaction3);
            System.out.println("Serialized Transaction 3 (VIRCHAC): " + transaction3Json);

            String transaction4Json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(transaction4);
            System.out.println("Serialized Transaction 4 (VIRMULTA): " + transaction4Json);

            // Key Part 5: Deserialization - Convert JSON back to Transaction objects
            Transaction deserializedTransaction1 = objectMapper.readValue(transaction1Json, Transaction.class);
            System.out.println("Deserialized Transaction 1 Type: " + deserializedTransaction1.getTransactionType());

            Transaction deserializedTransaction2 = objectMapper.readValue(transaction2Json, Transaction.class);
            System.out.println("Deserialized Transaction 2 Type: " + deserializedTransaction2.getTransactionType());

            Transaction deserializedTransaction3 = objectMapper.readValue(transaction3Json, Transaction.class);
            System.out.println("Deserialized Transaction 3 Type: " + deserializedTransaction3.getTransactionType());

            Transaction deserializedTransaction4 = objectMapper.readValue(transaction4Json, Transaction.class);
            System.out.println("Deserialized Transaction 4 Type: " + deserializedTransaction4.getTransactionType());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
