package io.mapwize.mapwizeuicomponents.Models;

public class Products {
    String id, p_name, p_price, p_desc, p_shop, p_photo, shopOwner, key, discount, DisPrice;


    public Products() {
    }

    public Products(String p_name, String p_price, String p_desc) {
        this.p_name = p_name;
        this.p_price = p_price;
        this.p_desc = p_desc;
    }

    public String getP_shop() {
        return p_shop;
    }
    public void setP_shop(String p_shop) {
        this.p_shop = p_shop;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getP_name() {
        return p_name;
    }

    public void setP_name(String p_name) {
        this.p_name = p_name;
    }

    public String getP_price() {
        return p_price;
    }

    public void setP_price(String p_price) {
        this.p_price = p_price;
    }

    public String getP_desc() {
        return p_desc;
    }

    public void setP_desc(String p_desc) {
        this.p_desc = p_desc;
    }

    public String getP_photo() {
        return p_photo;
    }

    public void setP_photo(String p_photo) {
        this.p_photo = p_photo;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getShopOwner() {
        return shopOwner;
    }

    public void setShopOwner(String shopOwner) {
        this.shopOwner = shopOwner;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getDisPrice() {
        return DisPrice;
    }

    public void setDisPrice(String disPrice) {
        DisPrice = disPrice;
    }
}
