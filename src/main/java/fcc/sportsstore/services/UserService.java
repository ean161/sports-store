package fcc.sportsstore.services;

import fcc.sportsstore.repositories.UserRepository;
import fcc.sportsstore.utils.Random;
import fcc.sportsstore.utils.Time;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;

@Service
public class UserService {

    final private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String generateId() {
        Time time = new Time();
        Random rand = new Random();
        ZonedDateTime date = time.getNow();

        String id = String.format("%d-%d-%d-%s",
                date.getYear(),
                date.getMonthValue(),
                date.getDayOfMonth(),
                rand.randString(10));
        return id;
    }
}
