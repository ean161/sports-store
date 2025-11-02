package fcc.sportsstore.services;

import fcc.sportsstore.entities.Item;
import fcc.sportsstore.entities.User;
import fcc.sportsstore.repositories.ItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {

    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public void save(Item item) {
        itemRepository.save(item);
    }

    public List<Item> getByUserAndType(User user, String type){
        return itemRepository.findByUserAndType(user, type);
    }
}
