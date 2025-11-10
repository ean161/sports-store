package fcc.sportsstore.services;

import fcc.sportsstore.entities.*;
import fcc.sportsstore.repositories.ItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {

    private final ItemRepository itemRepository;

    private final ProductService productService;

    private final ProductPropertyFieldService productPropertyFieldService;

    private final ProductPropertyDataService productPropertyDataService;

    public ItemService(ItemRepository itemRepository,
                       ProductService productService,
                       ProductPropertyFieldService productPropertyFieldService,
                       ProductPropertyDataService productPropertyDataService) {
        this.itemRepository = itemRepository;
        this.productService = productService;
        this.productPropertyFieldService = productPropertyFieldService;
        this.productPropertyDataService = productPropertyDataService;
    }

    public void save(Item item) {
        itemRepository.save(item);
    }

    public Item getById(String id) {
        return itemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item not found."));
    }

    public List<Item> getByUserAndType(User user, String type){
        return itemRepository.findByUserAndTypeOrderByCreatedAtDesc(user, type);
    }

    public Product getLiveProduct(Item item) {
        try {
            return productService.getById(item.getProductSnapshot().getProductId());
        } catch (Exception e) {
            return null;
        }
    }

    public ProductPropertyField getLiveField(ProductPropertySnapshot productPropertySnapshot) {
        try {
            return productPropertyFieldService.getById(productPropertySnapshot.getProductPropertyFieldId());
        } catch (Exception e) {
            return null;
        }
    }

    public void deleteById(String id) {
        itemRepository.deleteById(id);
    }
}
