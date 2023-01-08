package oop;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class RentalCompany {
    private List<RentableTool> tools = new ArrayList<>();

    public void addTool(RentableTool tool) {
        tools.add(tool);
    }

    public RentableTool findToolById(String id){
        for (RentableTool tool:tools){
            if (tool.getId().equals(id)){
                return tool;
            }
        }
        return null;
    }

    public List<RentableTool> listFreeTools(){
        List<RentableTool> result = new ArrayList<>();
        for (RentableTool tool:tools){
            if (tool.getRentFrom() == null){
                result.add(tool);
            }
        }
        return result;
    }

    public int calculateTotalIncome(){
        int result = tools.stream()
                .map((s) -> s.getTotalIncome())
                .mapToInt(Integer::intValue)
                .sum();
        return result;
    }

    public void listExpiredToolsToFileAsCsv(Path path) throws IOException {
        Files.writeString(path, "id;name;rentTo");
        for (RentableTool tool: tools){
            if (tool.getRentTo() != null && LocalDateTime.now().isAfter(tool.getRentTo())){
                Files.writeString(path, "\n" + tool.getId() + ";" +
                        tool.getName() + ";" +
                        tool.getRentTo(), StandardOpenOption.APPEND);
            }
        }
    }
}
