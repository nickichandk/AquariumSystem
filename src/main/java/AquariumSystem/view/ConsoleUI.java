package AquariumSystem.view;

import AquariumSystem.controller.AquariumController;
import AquariumSystem.interfaces.Fish;
import AquariumSystem.model.WaterQuality;
import AquariumSystem.simpleaquarium.SimpleFish;

import java.util.Scanner;
import java.util.List;


public class ConsoleUI {

    private AquariumController controller;
    private Scanner scanner = new Scanner(System.in);

    public ConsoleUI(AquariumController controller) {
        this.controller = controller;
    }

    public void start() {

        while (true) {
            System.out.println("\n=== DIANAS AKVARIUM ===");
            System.out.println("1. Fodr fisk");
            System.out.println("2. Skift vand");
            System.out.println("3. Health check fisk");
            System.out.println("4. Vis fisk");
            System.out.println("5. Tilføj fisk");
            System.out.println("6. Slet fisk");
            System.out.println("7. Vis sidste vandskift");
            System.out.println("8. Afslut");

            int choice = readInt();

            try {
                switch (choice) {
                    case 1 -> controller.feedFish();
                    case 2 -> changeWater();
                    case 3 -> registerHealth();
                    case 4 -> showFish();
                    case 5 -> addFish();
                    case 6 -> removeFish();
                    case 7 -> showLastWaterChange();
                    case 8 -> { return; }
                    default -> printError("Ugyldigt valg");
                }
            } catch (Exception e) {
                printError(e.getMessage());
            }
        }
    }

    private void addFish() {
        System.out.print("Indtast navn på fisk: ");
        String name = scanner.nextLine();

        Fish fish = new SimpleFish(name);
        controller.addFish(fish);
    }

    private void removeFish() {
        showFish();
        System.out.print("Vælg nummer på fisk der skal slettes: ");
        int index = readInt() - 1;

        controller.removeFish(index);
    }

    private void showLastWaterChange() {
        var date = controller.getLastWaterChange();

        if (date == null) {
            System.out.println("Ingen vandskift registreret endnu.");
        } else {
            System.out.println("Sidste vandskift: " + date);
        }
    }

    private void registerHealth() {
        Fish fish = selectFish();
        if (fish == null) return;

        System.out.print("Sundhedsnote: ");
        String note = scanner.nextLine();

        controller.registerFishHealth(fish, note);
    }

    private void changeWater() {
        System.out.print("Note: ");
        String note = scanner.nextLine();

        controller.changeWater(note, WaterQuality.GOOD);
    }

    private Fish selectFish() {
        List<Fish> fishList = controller.getFishList();

        if (fishList.isEmpty()) {
            printError("Ingen fisk fundet");
            return null;
        }

        for (int i = 0; i < fishList.size(); i++) {
            System.out.println((i + 1) + ". " + fishList.get(i).getName());
        }

        int choice = readInt();

        if (choice < 1 || choice > fishList.size()) {
            printError("Ugyldigt valg");
            return null;
        }

        return fishList.get(choice - 1);
    }

    private void showFish() {
        List<Fish> fishList = controller.getFishList();

        if (fishList.isEmpty()) {
            System.out.println("Ingen fisk.");
            return;
        }

        for (int i = 0; i < fishList.size(); i++) {
            System.out.println((i + 1) + ". " + fishList.get(i).getName());
        }
    }

    // pæn fejlhåndtering
    private void printError(String message) {
        System.out.println(" Fejl: " + message);
    }

    private int readInt() {
        while (!scanner.hasNextInt()) {
            printError("Indtast et tal!");
            scanner.next();
        }
        int number = scanner.nextInt();
        scanner.nextLine();
        return number;
    }
}