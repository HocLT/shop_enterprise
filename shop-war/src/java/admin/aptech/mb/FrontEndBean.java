/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package admin.aptech.mb;

import aptech.dto.CartItemDto;
import aptech.entity.Product;
import aptech.sb.CartFacadeLocal;
import aptech.sb.ProductFacadeLocal;
import jakarta.ejb.EJB;
import jakarta.inject.Named;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.transaction.SystemException;
import jakarta.transaction.UserTransaction;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author quang
 */
@Named(value = "frontEnd")
@SessionScoped
public class FrontEndBean implements Serializable {

    @EJB
    private ProductFacadeLocal productFacade;
    
    @EJB
    private CartFacadeLocal cartFacade;
    
    @Inject
    private UserTransaction ut;
    /**
     * Creates a new instance of FrontEndBean
     */
    public FrontEndBean() {
    }
    
    public List<Product> getProducts() {
        return productFacade.findAll();
    }
    
    public String addCart(int pid){
        cartFacade.addCart(pid, 1);
        return "products";  // quay lại trang products.xhtml
    }
    
    public List<CartItemDto> getCartItems(){
        return cartFacade.getCarts();
    }
    
    public String saveCart() {
        try {
            ut.begin();
            cartFacade.saveCart();
            ut.commit();
        } catch (Exception ex){
            try { 
                ut.rollback();
                ex.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        return "products";
    }
}
