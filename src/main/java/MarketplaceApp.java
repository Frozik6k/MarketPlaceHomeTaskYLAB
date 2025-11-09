import app.MarketplaceConsole;
import repository.ProductRepository;
import repository.UserRepository;
import repository.impl.ProductRepositoryImpl;
import repository.impl.UserRepositoryImpl;
import service.ProductService;
import service.UserService;

public class MarketplaceApp {

    public static void main(String[] args) {
        ProductRepository productRepository = new ProductRepositoryImpl();
        UserRepository userRepository = new UserRepositoryImpl();
        ProductService productService = new ProductService(productRepository);
        UserService userService = new UserService(userRepository);

        new MarketplaceConsole(productService, userService).start();
    }
}
