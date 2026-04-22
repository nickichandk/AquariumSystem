package AquariumSystem.interfaces;
import java.time.LocalDateTime;
import AquariumSystem.model.WaterQuality;

public interface Aquarium {
    void feedFish();
    void registerWaterChange(String note, WaterQuality quality);
    LocalDateTime getLastWaterChange();
}
