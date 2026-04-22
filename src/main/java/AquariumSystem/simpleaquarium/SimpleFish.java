package AquariumSystem.simpleaquarium;

import AquariumSystem.interfaces.Fish;
import java.util.ArrayList;
import java.util.List;

public class SimpleFish {
    private String name;
    private List<String> HealthNotes = new ArrayList<>();

    public SimpleFish(String name) {
        this.name = name;
    }
    @Override
    public String getName() {
        return name;
    }
    @Override
    public void registerHealthCheck(String note) {
        healthNotes.add(note);
    }
}
