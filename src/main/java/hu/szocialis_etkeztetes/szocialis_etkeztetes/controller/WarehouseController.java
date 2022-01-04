package hu.szocialis_etkeztetes.szocialis_etkeztetes.controller;

import hu.szocialis_etkeztetes.szocialis_etkeztetes.model.Product;
import hu.szocialis_etkeztetes.szocialis_etkeztetes.model.Warehouse;
import hu.szocialis_etkeztetes.szocialis_etkeztetes.service.ProductService;
import hu.szocialis_etkeztetes.szocialis_etkeztetes.service.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Objects;

@Controller
public class WarehouseController {

    @Autowired
    WarehouseService warehouseService;

    @Autowired
    ProductService productService;

    @RequestMapping(path = "warehouseHandling", method = RequestMethod.GET)
    private String getWareHouseHandling(Model model, HttpSession session){
        String activeUser = String.valueOf(session.getAttribute("active"));
        if (!activeUser.equals("true")) {
            return "login";
        } else {
            model.addAttribute("warehouses", warehouseService.findAll());
            model.addAttribute("products", productService.findAll());
            return "warehouseHandling";
        }
    }

    // Új Raktár hozzáadása
   @RequestMapping(path = "/warehouseNew", method = RequestMethod.GET)
   private String getWarehouseNew(HttpSession session){
       String activeUser = String.valueOf(session.getAttribute("active"));
       String adminRoleType = String.valueOf(session.getAttribute("adminRoleType"));
       String warehouseRoleType = String.valueOf(session.getAttribute("warehouseRoleType"));
       if (!activeUser.equals("true")) {
           return "login";
       } else if(!adminRoleType.equals("true") && !warehouseRoleType.equals("true")){
           return "redirect:/warehouseHandling";
       }else{
           return "warehouseNew";
       }
   }

    @RequestMapping(path = "warehouseNew", method = RequestMethod.POST)
    private String postNewWarehouse(HttpSession session, @RequestParam String name, @RequestParam String address, @RequestParam int capacity){
        String activeUser = String.valueOf(session.getAttribute("active"));
        String adminRoleType = String.valueOf(session.getAttribute("adminRoleType"));
        String warehouseRoleType = String.valueOf(session.getAttribute("warehouseRoleType"));
        if (!activeUser.equals("true")) {
            return "login";
        } else if(!adminRoleType.equals("true") && !warehouseRoleType.equals("true")){
            return "redirect:/warehouseHandling";
        }
        Warehouse warehouseExistsAlready = warehouseService.findByName(name);
        if(warehouseExistsAlready != null){
            return "redirect:/warehouseNew";
        }
        warehouseService.saveWarehouse(new Warehouse(name, address, capacity, 0));

        return "redirect:/warehouseHandling";
    }

    //Raktár értékeinek frissítése
    @RequestMapping(path = "warehouseHandling/warehouseUpdate", method = RequestMethod.GET)
    public String getWarehouseUpdate(@RequestParam long id, HttpSession session, Model model) {
        String activeUser = String.valueOf(session.getAttribute("active"));
        String adminRoleType = String.valueOf(session.getAttribute("adminRoleType"));
        String warehouseRoleType = String.valueOf(session.getAttribute("warehouseRoleType"));
        if (!activeUser.equals("true")) {
            return "login";
        } else if(!adminRoleType.equals("true") && !warehouseRoleType.equals("true")){
            return "redirect:/warehouseHandling";
        }

        Warehouse warehouse = warehouseService.findById(id);;
        if(warehouseService.findById(id) == null){
            return "redirect:/warehouseHandling";
        }

        model.addAttribute("warehouse", warehouse);
        return "warehouseUpdate";
    }

    @RequestMapping(path = "warehouseHandling/warehouseUpdate", method = RequestMethod.POST)
    public String postWarehouseUpdate(HttpSession session, Model model, @RequestParam long id, @RequestParam String name, @RequestParam String address, @RequestParam int capacity){
        String activeUser = String.valueOf(session.getAttribute("active"));
        String adminRoleType = String.valueOf(session.getAttribute("adminRoleType"));
        String warehouseRoleType = String.valueOf(session.getAttribute("warehouseRoleType"));
        if (!activeUser.equals("true")) {
            return "login";
        } else if(!adminRoleType.equals("true") && !warehouseRoleType.equals("true")){
            return "redirect:/warehouseHandling";
        }
        Warehouse warehouseExistsAlready = warehouseService.findByName(name);
        Warehouse warehouseToUpdate = warehouseService.findById(id);
        if(warehouseExistsAlready != null && !Objects.equals(warehouseToUpdate.getId(), warehouseExistsAlready.getId())){
            model.addAttribute("warehouse", warehouseToUpdate);
            return "redirect:/warehouseHandling/warehouseUpdate?id=" + warehouseToUpdate.getId();
        }

        if(capacity <= warehouseToUpdate.getCapacity()){
            model.addAttribute("warehouse", warehouseToUpdate);
            return "redirect:/warehouseHandling/warehouseUpdate?id=" + warehouseToUpdate.getId();
        }

        warehouseService.updateWarehouse(id, name, address, capacity);
        return "redirect:/warehouseHandling";
    }

    //Raktár törlése
    @RequestMapping(path = "warehouseHandling/warehouseDelete", method = RequestMethod.GET)
    public String getWarehouseDelete(@RequestParam long id, HttpSession session) {
        String activeUser = String.valueOf(session.getAttribute("active"));
        String adminRoleType = String.valueOf(session.getAttribute("adminRoleType"));
        String warehouseRoleType = String.valueOf(session.getAttribute("warehouseRoleType"));
        if (!activeUser.equals("true")) {
            return "login";
        } else if(!adminRoleType.equals("true") && !warehouseRoleType.equals("true")){
            return "redirect:/warehouseHandling";
        }
        warehouseService.deleteWarehouse(id);
        return "redirect:/warehouseHandling";
    }

   //Raktárkészlet
   @RequestMapping(path = "warehouseHandling/productsInWarehouse", method = RequestMethod.GET)
    public String getWarehouseProducts(@RequestParam long id, HttpSession session, Model model) {
       String activeUser = String.valueOf(session.getAttribute("active"));
       String adminRoleType = String.valueOf(session.getAttribute("adminRoleType"));
       String warehouseRoleType = String.valueOf(session.getAttribute("warehouseRoleType"));
       if (!activeUser.equals("true")) {
           return "login";
       }
       Warehouse warehouse = warehouseService.findById(id);

       if (!warehouse.getName().isEmpty()) {
           List<Product> warehouseProducts = productService.findAllProductsByWarehouse(warehouse.getName());
           if(!warehouseProducts.isEmpty()){
               model.addAttribute("searchedProducts", warehouseProducts);
               return "productSearch";
           }else{
               return "redirect:/warehouseHandling";
           }
       } else {
           return "redirect:/warehouseHandling";
       }
    }
}
