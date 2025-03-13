/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package admin.aptech.mb;

import aptech.entity.Product;
import aptech.sb.ProductFacadeLocal;
import jakarta.ejb.EJB;
import jakarta.inject.Named;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.FacesContext;
import jakarta.servlet.http.Part;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author quang
 */
@Named(value = "product")
@SessionScoped
public class ProductBean implements Serializable {

    @EJB
    private ProductFacadeLocal productFacade;
    
    private Product curProduct = null;
    private Part imagePart;
    private final String IMG_FOLDER="images";
    
    /**
     * Creates a new instance of ProductBean
     */
    public ProductBean() {
        
    }
    
    public List<Product> getProducts() {
        return productFacade.findAll();
    }
    
    public String create(){
        curProduct = new Product();
        return "create";
    }
    
    public void processUpload() {
        if (imagePart != null) {
            try {
                String rootPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("");
                String imgPath = rootPath + File.separator + "resources" + File.separator + IMG_FOLDER;
                File imgFolder = new File(imgPath);
                if (!imgFolder.exists()) {
                    imgFolder.mkdir();
                }
                File imgFile = new File(imgFolder + File.separator + imagePart.getSubmittedFileName());
                try (FileOutputStream imgFileOut = new FileOutputStream(imgFile)) {
                    byte[] buffer = new byte[1024];
                    int bytesRead;
                    InputStream is = imagePart.getInputStream();
                    while ((bytesRead = is.read(buffer)) != -1) {
                        imgFileOut.write(buffer, 0, bytesRead);
                    }
//                String imgFilePath = imgFolder + File.separator + imagePart.getSubmittedFileName();
                    //imagePart.write(imgFilePath);
                }
                curProduct.setImage(imagePart.getSubmittedFileName());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    public String processCreate() {
//        if (imagePart != null) {
//            curProduct.setImage(imagePart.getSubmittedFileName());
//        }
        productFacade.create(curProduct);
        return "index";
    }
    
    public String update(int id){
        return "update";
    }

    /**
     * @return the curProduct
     */
    public Product getCurProduct() {
        return curProduct;
    }

    /**
     * @param curProduct the curProduct to set
     */
    public void setCurProduct(Product curProduct) {
        this.curProduct = curProduct;
    }

    /**
     * @return the imagePart
     */
    public Part getImagePart() {
        return imagePart;
    }

    /**
     * @param imagePart the imagePart to set
     */
    public void setImagePart(Part imagePart) {
        this.imagePart = imagePart;
    }
}
