package hello.itemservice.domain.item;

import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository //component 스캔의 대상이됨
public class ItemRepository {
    private static final Map<Long, Item> store = new HashMap<>(); // 동시에 여러 쓰레드가 접근 하게 될 경우 hastmap 사용하면 안됨 !!!
    private static long sequence = 0L; //static

    //item을 저장하는기능
    public Item save(Item item){
        item.setId(++sequence);
        store.put(item.getId(), item);
        return item;
    }
    //조회
    public Item findById(Long id){
        return store.get(id);
    }
    public List<Item> findAll(){
        return new ArrayList<>(store.values());
    }
    //update
    public void update (Long itemId, Item updateParam){
       Item findItem = findById(itemId);
       findItem.setItemName(updateParam.getItemName());
       findItem.setPrice(updateParam.getPrice());
       findItem.setQuantity(updateParam.getQuantity());

    }
    public void clearStore(){
        store.clear(); //hashmap 데이터 다날라감
    }
}
