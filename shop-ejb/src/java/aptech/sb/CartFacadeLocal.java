/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB40/SessionLocal.java to edit this template
 */
package aptech.sb;

import aptech.dto.CartItemDto;
import jakarta.ejb.Local;
import java.util.List;

/**
 *
 * @author quang
 */
@Local
public interface CartFacadeLocal {

    List<CartItemDto> getCarts();
    
    void addCart(int pid, int quantity);

    void saveCart();
    
}
