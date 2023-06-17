
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
public class MVCMain
{

    public static class Client 
    {
        private int id;
        private String nom;
        private String prenom;
        private List<Compte> comptes; // Liste des comptes du client

    public Client(int id, String nom, String prenom) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        comptes = new ArrayList<Compte>();
    }

    // Getters et Setters

    public void ajouterCompte(Compte compte) {
        comptes.add(compte);
    }

    

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getNom() {
            return nom;
        }

        public void setNom(String nom) {
            this.nom = nom;
        }

        public String getPrenom() {
            return prenom;
        }

        public void setPrenom(String prenom) {
            this.prenom = prenom;
        }
    
        
        public List<Compte> getComptes() {
        return this.comptes;
    }
    }

    public static class Compte {
        private int id;
        private String numero;
        private double solde;

        public Compte(int id, String numero, double solde) {
            this.id = id;
            this.numero = numero;
            this.solde = solde;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getNumero() {
            return numero;
        }

        public void setNumero(String numero) {
            this.numero = numero;
        }

        public double getSolde() {
            return solde;
        }

        public void setSolde(double solde) {
            this.solde = solde;
        }
    }

    public static class Cheque extends Compte {
        private double frais;

        public Cheque(int id, String numero, double solde, double frais) {
            super(id, numero, solde);
            this.frais = frais;
        }

        public double getFrais() {
            return frais;
        }

        public void setFrais(double frais) {
            this.frais = frais;
        }
    }



    public static class Epargne extends Compte {
        private LocalDate dateDebutBlocage;
        private LocalDate dateFinBlocage;
        private double taux;

        public Epargne(int id, String numero, double solde, LocalDate dateDebutBlocage, LocalDate dateFinBlocage, double taux) {
            super(id, numero, solde);
            this.dateDebutBlocage = dateDebutBlocage;
            this.dateFinBlocage = dateFinBlocage;
            this.taux = taux;
        }

        public LocalDate getDateDebutBlocage() {
            return dateDebutBlocage;
        }

        public void setDateDebutBlocage(LocalDate dateDebutBlocage) {
            this.dateDebutBlocage = dateDebutBlocage;
        }

        public LocalDate getDateFinBlocage() {
            return dateFinBlocage;
        }

        public void setDateFinBlocage(LocalDate dateFinBlocage) {
            this.dateFinBlocage = dateFinBlocage;
        }

        public double getTaux() {
            return taux;
        }

        public void setTaux(double taux) {
            this.taux = taux;
        }
    }

    public static  class View {
        private Controller controller;
        private Scanner scanner;

        /**
         * 
         */
        public View() {
            controller = new Controller();
            scanner = new Scanner(System.in);
        }



        /**
         * 
         */
        public void afficherMenu() {
            boolean continuer = true;

            while (continuer) {
                System.out.println("1. Ajouter un client");
                System.out.println("2. Lister les clients");
                System.out.println("3. Afficher les comptes d'un client");
                System.out.println("4. Quitter");
                System.out.print("Choix : ");
                int choix = scanner.nextInt();

                switch (choix) {
                    case 1:
                        ajouterClient();
                        break;
                    
                    
                    case 2:
                        controller.listerClients();
                        break;

                    case 3:
                        System.out.print("ID du client : ");
                        int clientId = scanner.nextInt();
                        Client client = controller.getClientById(clientId);
                        if (client != null) {
                            controller.listerComptes(client);
                        } else {
                            System.out.println("Client non trouvé.");
                        }
                        break;
                    case 4:
                    continuer = false;
                    break;
                    default:
                        System.out.println("Choix invalide !");
                        break;
                }
            }
        }

        private void ajouterClient() 
        {
            
            System.out.print("Nom du client : ");
            String nom = scanner.next();
            System.out.print("Prénom du client : ");
            String prenom = scanner.next();

            Client client = new Client(0, nom, prenom);
            Compte compte = ajouterCompte();
            controller.ajouterClient(client,compte );
            System.out.println("Client ajouté avec succès !");
        }

        private MVCMain.Compte ajouterCompte() 
        {
            
            System.out.print("Numéro du compte : ");
            String numero = scanner.next();
            System.out.print("Solde du compte : ");
            double solde = scanner.nextDouble();

            System.out.println("1. Compte Chèque");
            System.out.println("2. Compte Épargne");
            System.out.print("Choix du type de compte : ");
            int choix = scanner.nextInt();
            Compte compte;

        switch (choix) 
        {

            

            case 1:
                System.out.print("Frais du compte chèque : ");
                double frais = scanner.nextDouble();
                compte = new Cheque(0, numero, solde, frais);
                break;
            
            case 2:
                System.out.print("Date de début de blocage (AAAA-MM-JJ) : ");
                String dateDebutBlocageStr = scanner.next();
                LocalDate dateDebutBlocage = LocalDate.parse(dateDebutBlocageStr);
                System.out.print("Date de fin de blocage (AAAA-MM-JJ) : ");
                String dateFinBlocageStr = scanner.next();
                LocalDate dateFinBlocage = LocalDate.parse(dateFinBlocageStr);
                System.out.print("Taux d'intérêt du compte épargne : ");
                double taux = scanner.nextDouble();
                compte = new Epargne(0, numero, solde, dateDebutBlocage, dateFinBlocage, taux);
                break;
            default:
                System.out.println("Choix invalide !");
                return null;
                
        }
        return compte;
        }
    }


    public static class Controller {
        private List<Client> clients;
        private List<Compte> comptes;
        private int lastClientId; // Variable pour attribuer automatiquement les ID des clients
        private int lastCompteId; // Variable pour attribuer automatiquement les ID des comptes
    public Controller() {
        clients = new ArrayList<>();
        lastClientId = 0;
        lastCompteId= 0;
    }

    public void ajouterClient(Client client, Compte compte) {
        if (!clients.contains(client)) {
            // Attribuer un nouvel ID au client
            lastClientId++;
            client.setId(lastClientId);
            clients.add(client);
        }
        
        // Ajouter le compte au client
        lastCompteId++;
        compte.setId(lastClientId);
        client.ajouterCompte(compte);
        
    }

        

        public Client getClientById(int clientId) 
        {
            for (Client client : clients) 
            {
                if (client.getId() == clientId) {
                    return client;
                }
            }
    return null; // Si aucun client correspondant à l'ID n'est trouvé
}

        public void ajouterCompte(Compte compte) {
            comptes.add(compte);
        }

        public void listerClients() {
        System.out.println("-----------------------------");
        if (clients.isEmpty()) {
            System.out.println("Aucun client trouvé.");
        } else {
            System.out.println("Liste des clients :");
            for (Client client : clients) {
                System.out.println("ID : " + client.getId());
                System.out.println("Nom : " + client.getNom());
                System.out.println("Prénom : " + client.getPrenom());
                System.out.println("---------------------------");
            }
        System.out.println("-----------------------------");
        }
    }

        public void listerComptes(Client client) {
            System.out.println("Comptes du client : " + client.getNom() + " " + client.getPrenom());
            List<Compte> comptes = client.getComptes();
            if (comptes.isEmpty()) {
                System.out.println("Aucun compte trouvé.");
            } else {
                for (Compte compte : comptes) {
                    System.out.println("ID : " + compte.getId());
                    System.out.println("Numéro : " + compte.getNumero());
                    System.out.println("Solde : " + compte.getSolde());
                    if (compte instanceof Cheque) {
                        Cheque cheque = (Cheque) compte;
                        System.out.println("Type de compte : Chèque");
                        System.out.println("Frais : " + cheque.getFrais());
                    } else if (compte instanceof Epargne) {
                        Epargne epargne = (Epargne) compte;
                        System.out.println("Type de compte : Épargne");
                        System.out.println("Date de début de blocage : " + epargne.getDateDebutBlocage());
                        System.out.println("Date de fin de blocage : " + epargne.getDateFinBlocage());
                        System.out.println("Taux d'intérêt : " + epargne.getTaux());
                    }
                    System.out.println("---------------------------");
                }
            }
        }


}
        

        


    




    

    
    public static void main(String[] args) {
        
        final View view = new View();
        view.afficherMenu();
    }
    }








