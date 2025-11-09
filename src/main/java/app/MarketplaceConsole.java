package app;

import model.Product;
import service.ProductService;
import service.UserService;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class MarketplaceConsole {
    private final ProductService productService;
    private final UserService userService;
    private final Scanner scanner = new Scanner(System.in);

    private String currentUser;

    public MarketplaceConsole(ProductService productService, UserService userService) {
        this.productService = productService;
        this.userService = userService;
    }

    public void start() {
        System.out.println("Добро пожаловать в каталог маркетплейса!");
        boolean exit = false;
        while (!exit) {
            if (currentUser == null) {
                exit = !handleAuthentication();
            } else {
                exit = !handleMainMenu();
            }
        }
        System.out.println("До свидания!");
    }

    private boolean handleAuthentication() {
        System.out.println("1. Войти");
        System.out.println("2. Зарегистрироваться");
        System.out.println("0. Выход");
        System.out.print("Выберите действие: ");
        String choice = scanner.nextLine();
        switch (choice) {
            case "1" -> login();
            case "2" -> register();
            case "0" -> {
                return false;
            }
            default -> System.out.println("Неизвестная команда");
        }
        return true;
    }

    private boolean handleMainMenu() {
        System.out.println();
        System.out.println("Текущий пользователь: " + currentUser);
        System.out.println("Всего товаров в каталоге: " + productService.getProductCount());
        System.out.println("1. Показать все товары");
        System.out.println("2. Добавить товар");
        System.out.println("3. Редактировать товар");
        System.out.println("4. Удалить товар");
        System.out.println("5. Поиск и фильтрация");
        System.out.println("6. Сменить пароль");
        System.out.println("7. Выйти из аккаунта");
        System.out.println("0. Завершить работу");
        System.out.print("Выберите действие: ");
        String choice = scanner.nextLine();
        switch (choice) {
            case "1" -> displayProducts(productService.getAllProducts());
            case "2" -> addProduct();
            case "3" -> editProduct();
            case "4" -> deleteProduct();
            case "5" -> searchProducts();
            case "6" -> changePassword();
            case "7" -> logout();
            case "0" -> {
                logout();
                return false;
            }
            default -> System.out.println("Неизвестная команда");
        }
        return true;
    }

    private void login() {
        System.out.print("Логин: ");
        String username = scanner.nextLine();
        System.out.print("Пароль: ");
        String password = scanner.nextLine();
        userService.login(username, password)
                .ifPresentOrElse(user -> {
                    currentUser = user.getUsername();
                    System.out.println("Успешный вход");
                }, () -> System.out.println("Неверное имя пользователя или пароль"));
    }

    private void register() {
        System.out.print("Введите имя пользователя: ");
        String username = scanner.nextLine();
        System.out.print("Введите пароль: ");
        String password = scanner.nextLine();
        try {
            userService.register(username, password);
            System.out.println("Пользователь успешно зарегистрирован");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private void logout() {
        if (currentUser != null) {
        }
        currentUser = null;
    }

    private void addProduct() {
        Product product = readProductData(null);
        if (product == null) {
            return;
        }
        try {
            productService.addProduct(product);
            System.out.println("Товар успешно добавлен");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private void editProduct() {
        System.out.print("Введите название товара для редактирования: ");
        String name = scanner.nextLine();
        productService.findByName(name)
                .ifPresentOrElse(existing -> {
                    Product updated = readProductData(existing);
                    if (updated == null) {
                        return;
                    }
                    try {
                        productService.updateProduct(updated);
                        System.out.println("Товар обновлен");
                    } catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                    }
                }, () -> System.out.println("Товар не найден"));
    }

    private void deleteProduct() {
        System.out.print("Введите название товара для удаления: ");
        String name = scanner.nextLine();
        if (productService.findByName(name).isPresent()) {
            productService.removeProduct(name);
            System.out.println("Товар удален");
        } else {
            System.out.println("Товар не найден");
        }
    }

    private void searchProducts() {
        System.out.print("Введите часть названия (или оставьте пустым): ");
        String name = emptyToNull(scanner.nextLine());
        System.out.print("Категория (или пусто): ");
        String category = emptyToNull(scanner.nextLine());
        System.out.print("Бренд (или пусто): ");
        String brand = emptyToNull(scanner.nextLine());
        BigDecimal minPrice = readPrice("Минимальная цена (или пусто): ");
        BigDecimal maxPrice = readPrice("Максимальная цена (или пусто): ");
        Map<String, String> params = readParameters();

        ProductService.SearchResult result = productService.searchProducts(name, category, brand, minPrice, maxPrice, params);
        displayProducts(result.products());
    }

    private void changePassword() {
        System.out.print("Введите новый пароль: ");
        String newPassword = scanner.nextLine();
        if (newPassword.isBlank()) {
            System.out.println("Пароль не может быть пустым");
            return;
        }
        try {
            userService.changePassword(currentUser, newPassword);
            System.out.println("Пароль обновлен");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private Product readProductData(Product existing) {
        Product product = new Product();
        if (existing != null) {
            product.setName(existing.getName());
            product.setPrice(existing.getPrice());
            product.setCategory(existing.getCategory());
            product.setBrand(existing.getBrand());
            product.setParameters(new HashMap<>(existing.getParameters()));
        }

        if (existing == null) {
            System.out.print("Название товара: ");
            String name = scanner.nextLine();
            if (name.isBlank()) {
                System.out.println("Название товара не может быть пустым");
                return null;
            }
            product.setName(name);
        } else {
            System.out.println("Название товара изменить нельзя");
        }

        System.out.printf("Категория [%s]: ", nullToDash(product.getCategory()));
        String category = scanner.nextLine();
        if (!category.isBlank()) {
            product.setCategory(category);
        }

        System.out.printf("Бренд [%s]: ", nullToDash(product.getBrand()));
        String brand = scanner.nextLine();
        if (!brand.isBlank()) {
            product.setBrand(brand);
        }

        System.out.printf("Цена [%s]: ", product.getPrice() == null ? "" : product.getPrice());
        String priceInput = scanner.nextLine();
        if (!priceInput.isBlank()) {
            try {
                product.setPrice(new BigDecimal(priceInput));
            } catch (NumberFormatException e) {
                System.out.println("Некорректная цена");
                return null;
            }
        }

        Map<String, String> parameters = existing == null ? new HashMap<>() : new HashMap<>(existing.getParameters());
        System.out.println("Добавьте или обновите параметры (пустой ключ завершит ввод)");
        while (true) {
            System.out.print("Параметр: ");
            String key = scanner.nextLine();
            if (key.isBlank()) {
                break;
            }
            System.out.print("Значение: ");
            String value = scanner.nextLine();
            parameters.put(key, value);
        }
        product.setParameters(parameters);
        return product;
    }

    private Map<String, String> readParameters() {
        Map<String, String> params = new HashMap<>();
        System.out.println("Параметры фильтрации (пустой ключ завершит ввод)");
        while (true) {
            System.out.print("Параметр: ");
            String key = scanner.nextLine();
            if (key.isBlank()) {
                break;
            }
            System.out.print("Значение: ");
            String value = scanner.nextLine();
            params.put(key, value);
        }
        return params;
    }

    private BigDecimal readPrice(String message) {
        System.out.print(message);
        String input = scanner.nextLine();
        if (input.isBlank()) {
            return null;
        }
        try {
            return new BigDecimal(input);
        } catch (NumberFormatException e) {
            System.out.println("Некорректная цена, фильтр по цене не будет применен");
            return null;
        }
    }

    private void displayProducts(List<Product> products) {
        if (products.isEmpty()) {
            System.out.println("Товары не найдены");
            return;
        }
        for (Product product : products) {
            String price = product.getPrice() == null ? null : product.getPrice().toPlainString();
            System.out.printf("- %s | Категория: %s | Бренд: %s | Цена: %s%n", product.getName(),
                    nullToDash(product.getCategory()), nullToDash(product.getBrand()), nullToDash(price));
            if (!product.getParameters().isEmpty()) {
                product.getParameters().forEach((key, value) ->
                        System.out.printf("    %s: %s%n", key, value));
            }
        }
    }

    private String emptyToNull(String value) {
        return value == null || value.isBlank() ? null : value;
    }

    private String nullToDash(String value) {
        return value == null || value.isBlank() ? "-" : value;
    }
}
