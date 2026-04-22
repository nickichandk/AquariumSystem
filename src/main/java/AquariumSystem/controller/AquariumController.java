package AquariumSystem.controller;

import AquariumSystem.file.FileLogger;
import AquariumSystem.interfaces.Aquarium;
import AquariumSystem.interfaces.Fish;
import AquariumSystem.validation.FishValidator;
import AquariumSystem.validation.WaterChangeValidator;
import AquariumSystem.model.WaterQuality;
import java.util.ArrayList;
import java.util.List;

import java.time.LocalDateTime;

public class AquariumController {

    private Aquarium aquarium;
    private List<Fish> fishList = new ArrayList<>();

    public AquariumController(Aquarium aquarium) {
        this.aquarium = aquarium;
    }

    public void addFish(Fish fish) {
        fishList.add(fish);
        FileLogger.log("Fisk tilføjet: " + fish.getName());
    }

    public void removeFish(int index) {
        if (index < 0 || index >= fishList.size()) {
            throw new IllegalArgumentException("Ugyldigt valg");
        }
        Fish removed = fishList.remove(index);
        FileLogger.log("Fisk slettet: " + removed.getName());
    }

    public List<Fish> getFishList() {
        return fishList;
    }

    public void feedFish() {
        aquarium.feedFish();
        FileLogger.log("Fisk fodret");
    }

    public void changeWater(String note, WaterQuality quality) {
        WaterChangeValidator.validate(note);
        aquarium.registerWaterChange(note, quality);
        FileLogger.log("Vand skiftet: " + note + " (" + quality + ")");
    }

    public LocalDateTime getLastWaterChange() {
        return aquarium.getLastWaterChange();
    }

    public void registerFishHealth(Fish fish, String note) {
        FishValidator.validateHealthNote(note);
        fish.registerHealthCheck(note);
        FileLogger.log("Health check: " + fish.getName() + " -> " + note);
    }
}