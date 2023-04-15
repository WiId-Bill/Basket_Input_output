import org.junit.jupiter.api.*;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class BasketTest {
    @Test
public void testAddToBasket() {
        String[] products = {"Хлеб", "Яблоки", "Молоко", "Мороженое"};
        int[] prices = {100, 200, 300, 450};
       Basket basket = new Basket(products, prices);
       basket.addToCart(0,2);
        basket.addToCart(1,3);
        int[] actual = basket.getProductPrice();
        int [] expected = {2,3,0,0};
        assertArrayEquals(expected,actual);
    }

    @Test
    public void testLoadFromTxtFile(){
        Basket basket =  Basket.loadFromTxtFile(new File("src/test/resources/test_basket.txt"));
        String[] actualProducts = basket.getProducts();
        String[] expectedProducts = {"Хлеб", "Яблоки", "Молоко", "Мороженое"};
        int[] actualPrices =basket.getPrices();
        int[] expectedPrices = {100, 200, 300, 450};
        assertArrayEquals(expectedProducts,actualProducts);
        assertArrayEquals(expectedPrices,actualPrices);
        int[] actualProductPrice = basket.getProductPrice();
        int [] expectedProductPrice = {1,2,0,4};
        assertArrayEquals(expectedProductPrice,actualProductPrice);

    }
    @Test
    public void testLoadFromTxtFileNotExist(){
        Assertions.assertThrows(RuntimeException.class,
        ()-> Basket.loadFromTxtFile(new File("srs/test/resources/test_basket.txt")));
    }
}