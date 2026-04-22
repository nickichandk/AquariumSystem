package AquariumSystem.simpleaquarium;
import AquariumSystem.interfaces.Aquarium;
import AquariumSystem.model.WaterQuality;
import java.time.LocalDateTime;

public class SimpleAquarium {
    private LocalDateTime lastWaterChange;

    @Override
    public void feedFish() {
        System.out.println("Fiskene er fodret");
    }

    @Override
    public void registerWaterChange(String note, WaterQuality quality) {
        this.lastWaterChange = LocalDateTime.now();
        System.out.println("Vandet er skiftet" + note);
    }
    @Override
    public LocalDateTime getLastWaterChange() {
        return lastWaterChange;
    }
}
