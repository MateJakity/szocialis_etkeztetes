package hu.szocialis_etkeztetes.szocialis_etkeztetes.controller;

import hu.szocialis_etkeztetes.szocialis_etkeztetes.model.Product;
import hu.szocialis_etkeztetes.szocialis_etkeztetes.model.User;
import hu.szocialis_etkeztetes.szocialis_etkeztetes.model.Warehouse;
import hu.szocialis_etkeztetes.szocialis_etkeztetes.service.ProductService;
import hu.szocialis_etkeztetes.szocialis_etkeztetes.service.UserService;
import hu.szocialis_etkeztetes.szocialis_etkeztetes.service.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class ProductController {

    @Autowired
    ProductService productService;

    @Autowired
    WarehouseService warehouseService;

    @Autowired
    UserService userService;
    //Új termék felvétele raktározáshoz
    @RequestMapping(path = "productNew", method = RequestMethod.GET)
    private String getProductNew(Model model, HttpSession session) {
        String activeUser = String.valueOf(session.getAttribute("active"));
        if (!activeUser.equals("true")) {
            return "login";
        } else {
            model.addAttribute("warehouses", warehouseService.findAll());
            model.addAttribute("products", productService.findAll());
            return "productNew";
        }
    }

    @RequestMapping(path = "productNew", method = RequestMethod.POST)
    public String postProductNew(HttpSession session, Model model, @RequestParam String productName, @RequestParam double amount, @RequestParam String amountType,
                              @RequestParam String type, @RequestParam int price, @RequestParam String orderStatus, @RequestParam String warehouseStatus) {
        String activeUser = String.valueOf(session.getAttribute("active"));
        if (!activeUser.equals("true")) {
            return "login";
        } else {
            warehouseStatus = productService.warehouseStatusCheck(orderStatus, warehouseStatus);
            orderStatus = productService.orderStatusCheck(orderStatus, warehouseStatus);
            return productService.productNewRegister(model, productName, amount, amountType, type, price, orderStatus, warehouseStatus);
        }
    }

    //Termék törlése
    @RequestMapping(path = "product/delete", method = RequestMethod.GET)
    public String getProductDelete(@RequestParam long id, HttpSession session) {
        String activeUser = String.valueOf(session.getAttribute("active"));
        String adminRoleType = String.valueOf(session.getAttribute("adminRoleType"));
        String warehouseRoleType = String.valueOf(session.getAttribute("warehouseRoleType"));
        if (!activeUser.equals("true")) {
            return "login";
        } else if(!warehouseRoleType.equals("true") || !(adminRoleType.equals("true"))){
            return "redirect:/warehouseHandling";
        }

        User user = userService.findById((Long) session.getAttribute("uid"));
        Product product = productService.findById(id);
        if (user != null && product != null) {
            String productWarehouseStatus = product.getWarehouseStatus();
            Warehouse warehouse = warehouseService.findByName(productWarehouseStatus);
            if(warehouse != null) {
                warehouse.setCurrentQuantity(warehouse.getCurrentQuantity() - (int)product.getAmount());
                warehouseService.saveWarehouse(warehouse);
                productService.deleteByProductId(id);
                return "redirect:/warehouseHandling/productsInWarehouse?id=" + warehouse.getId();
            }
            productService.deleteByProductId(id);
            return "redirect:/productsAll";
        }
        return "redirect:/productsAll";
    }


    //Termék frissítése
    @RequestMapping(path = "product/productUpdate", method = RequestMethod.GET)
    public String getProductUpdate(@RequestParam long id, HttpSession session, Model model){
        String activeUser = String.valueOf(session.getAttribute("active"));
        Product product = productService.findById(id);
        if(activeUser.equals("true") && product != null){
            product.setAmount((int)product.getAmount());
            model.addAttribute("product", product);
            model.addAttribute("warehouses", warehouseService.findAll());
            return "productUpdate";
        }else{
            return "login";
        }
    }

    @RequestMapping(path = "product/productUpdate", method = RequestMethod.POST)
    public String postProductUpdate(HttpSession session,Model model, @RequestParam long id, @RequestParam String productName, @RequestParam double amount, @RequestParam String amountType, @RequestParam String type, @RequestParam int price, @RequestParam String orderStatus, @RequestParam String warehouseStatus){
        String activeUser = String.valueOf(session.getAttribute("active"));
        if(activeUser.equals("true")) {
            return productService.productUpdate(model, id, productName, amount, amountType, type, price, orderStatus, warehouseStatus);
        }else{
            return "login";
        }
    }

    //SidePanel
    //Tervezett rendelések
    @RequestMapping(path = "orderPlanned", method = RequestMethod.GET)
    private String getOrderPlanned(Model model, HttpSession session) {
        String activeUser = String.valueOf(session.getAttribute("active"));
        if (activeUser.equals("true")) {
            model.addAttribute("warehouses", warehouseService.findAll());
            model.addAttribute("orderAsked", productService.findByWarehouseStatusNone());
            return "orderPlanned";
        }else{
            return "login";
        }
    }

    //Összes felvett termék listája
    @RequestMapping(path = "productsAll", method = RequestMethod.GET)
    private String getAllProducts(HttpSession session, Model model){
        String activeUser = String.valueOf(session.getAttribute("active"));
        if (activeUser.equals("true")) {
            model.addAttribute("products", productService.findAll());
            return "productsAll";
        }else{
            return "login";
        }
    }

    //Keresett termékek('Search')
    @RequestMapping(path = "productSearch", method = RequestMethod.POST)
    private String postSearchedProduct(HttpSession session, Model model, @RequestParam String searchProduct){
            String activeUser = String.valueOf(session.getAttribute("active"));
            if (activeUser.equals("true")) {
                List<Product> foundProducts = productService.findAllProductsByName(productService.findAll(), searchProduct);
                if(!foundProducts.isEmpty()) {
                    model.addAttribute("searchedProducts", foundProducts);
                    return "productSearch";
                }else{
                    return "redirect:/warehouseHandling";
                }
            }else{
                return "login";
            }
    }

}
