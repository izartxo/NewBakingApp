package newbaking.code.develop.bizartxo.newbakingapp.model;

/**
 * Created by izartxo on 9/12/17.
 */

public class Ingredient {

    public String getRid() {
        return rid;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }

    private String rid;
    private String quantity;
    private String ingredient;

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }


}

