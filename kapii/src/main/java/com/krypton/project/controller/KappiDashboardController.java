package com.krypton.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Controller
public class KappiDashboardController {

    private static final String FILE_PATH = "connections.txt";

    private List<String> savedConnections;

    public KappiDashboardController(){
        ensureFileExists();
        savedConnections= loadConnectionsFromFile();
    }
    @GetMapping("/")
    public String dashboard(Model model) {

        model.addAttribute("savedConnections", savedConnections);
        return "dashboard";
    }
    @PostMapping("/saveConnection")
    public String saveConnection(@RequestParam String bootstrapServer) {
        if(!savedConnections.contains(bootstrapServer)){
            savedConnections.add(bootstrapServer);
            saveConnectionsToFile();
        }
        return "redirect:/";
    }

    private List<String> loadConnectionsFromFile() {
        List<String> connections = new ArrayList<>();
        try {
            if (Files.exists(Paths.get(FILE_PATH))) {
                connections = Files.readAllLines(Paths.get(FILE_PATH)); // Read file line by line
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return connections;
    }

    private void saveConnectionsToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (String connection : savedConnections) {
                writer.write(connection);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void ensureFileExists() {
        File file = new File(FILE_PATH);
        try {
            if (!file.exists()) {
                file.createNewFile(); // Creates file if it doesn't exist
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
