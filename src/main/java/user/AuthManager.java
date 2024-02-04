package user;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class AuthManager {
    private static AuthManager instance = null;
    private User currentUser;
    private ArrayList<User> users = new ArrayList<>();
    private ObjectMapper objectMapper = new ObjectMapper();
    private String USUARIOS_JSON = "usuarios.json";

    private AuthManager() {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    }

    public static AuthManager getInstance() {
        if (instance == null) {
            instance = new AuthManager();
        }
        return instance;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void addMatchToCurrentUser(Match match) {
        currentUser.addToHistory(match);
        try {
            saveUsers();
        } catch (IOException e) {
            System.out.println("Erro ao salvar usuários.");
        }
    }

    public boolean login(String username, String password) {
        try {
            loadUsers();
            for (User user : users) {
                if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                    currentUser = user;
                    return true;
                } else {
                    return false;
                }
            }
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }

    public boolean register(String username, String password) {
        try {
            users.add(new User(username, password, new ArrayList<Match>()));
            saveUsers();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public void saveUsers() throws IOException {
        objectMapper.writeValue(new File(USUARIOS_JSON), users);
    }

    private void loadUsers() throws IOException {
        File file = new File(USUARIOS_JSON);
        if (file.exists()) {
            users.addAll(objectMapper.readValue(file, new TypeReference<List<User>>() {
            }));
        }
    }
}