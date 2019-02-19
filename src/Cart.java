import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.*;

public class Cart {
	
    private static Cart cart = null;
    private List<String> cd_id_cart;

	private Cart(){
		cd_id_cart = new ArrayList<String>();
	}
	
	public static Cart getCart() {
        if(cart == null) {
            cart = new Cart();
        }
        return cart;
    }
	
	public static void addToCart(String id){
		getCart().cd_id_cart.add(id);
	}

	public static List<String> getCds(){
		return getCart().cd_id_cart;
	}
	
	public static void clearCart(){
		getCart().cd_id_cart.clear();
	}
	
	public static boolean inCart(String element){
		for (int i=0; i < getCart().cd_id_cart.size(); i++){
			if (getCart().cd_id_cart.get(i).equals(element))
				return true;
		}
		return false;
	}
	
	public static boolean isEmpty(){
		return getCart().cd_id_cart.isEmpty();
	}
	
	public static int getSize(){
		return getCart().cd_id_cart.size();
	}
	
}