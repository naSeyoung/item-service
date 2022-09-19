package hello.itemservice.web.basic;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.PostConstruct;
import java.util.List;

@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor // this를만들어줌
public class BasicItemController {
    private final ItemRepository itemRepository;
 /*   @Autowired
    public BasicItemController(ItemRepository itemRepository){
        this.itemRepository = itemRepository;
    }*/
    @GetMapping
    public String items(Model model){
        List<Item> item = itemRepository.findAll();
        model.addAttribute("itemss",item);
        return "basic/items";
    }
    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model){
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item",item);
        return "basic/item";
    }
    @GetMapping("/add")
    public String addForm(){
        return "basic/addForm";
    }
    //@PostMapping("/add")
    public String addItemV1(@RequestParam String itemName,
                       @RequestParam int price,
                       @RequestParam Integer quantity,
                       Model model){
        Item item = new Item();
        item.setItemName(itemName);
        item.setPrice(price);
        item.setQuantity(quantity);

        itemRepository.save(item);
        model.addAttribute("item",item);

        return "basic/item";
    }
   // @PostMapping("/add")
    public String addItemV2(@ModelAttribute("item") Item item,
                            Model model){

        itemRepository.save(item);
       // model.addAttribute("item",item); // 자동추가 생략가능

        return "basic/item";
    }

  //   @PostMapping("/add")
    public String addItemV3(@ModelAttribute Item item, Model model){
        //@ModelAttribute ==> vo의 첫글자를 소문자로 자동으로 변환, 자동으로 addAttribute에 "item" 으로 저장되는거임
        itemRepository.save(item);
        // model.addAttribute("item",item); // 자동추가 생략가능

        return "basic/item";
    }
   // @PostMapping("/add")
    public String addItemV4(Item item){ // @ModelAttribute 까지도 생략 가능
        itemRepository.save(item);
         return "basic/item";
    }
  //  @PostMapping("/add")
    public String addItemV5(Item item){ // @ModelAttribute 까지도 생략 가능
        itemRepository.save(item);
        return "redirect:/basic/items/"+item.getId();
    }

    @PostMapping("/add")
    public String addItemV6(Item item, RedirectAttributes redirectAttributes){
        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId",savedItem.getId());
        redirectAttributes.addAttribute("status",true);

        return "redirect:/basic/items/{itemId}";
    }
    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model){
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item",item);
        return "basic/editForm";
    }
    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @ModelAttribute Item item) {
        itemRepository.update(itemId, item);
        return "redirect:/basic/items/{itemId}";
    }
    //테스트용 데이터 추가
    @PostConstruct
    public void init(){
        itemRepository.save(new Item("그래픽카드",10000,10));
        itemRepository.save(new Item("모니터",20000,20));
    }
}



