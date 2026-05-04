package com.ecommerce.project.config;

import com.ecommerce.project.model.Address;
import com.ecommerce.project.model.AppRole;
import com.ecommerce.project.model.Cart;
import com.ecommerce.project.model.CartItem;
import com.ecommerce.project.model.Category;
import com.ecommerce.project.model.Product;
import com.ecommerce.project.model.Role;
import com.ecommerce.project.model.User;
import com.ecommerce.project.repository.CartItemRepository;
import com.ecommerce.project.repository.CartRepository;
import com.ecommerce.project.repository.CategoryRepository;
import com.ecommerce.project.repository.ProductRepository;
import com.ecommerce.project.repository.RoleRepository;
import com.ecommerce.project.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@Transactional
public class DemoDataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final PasswordEncoder passwordEncoder;

    public DemoDataInitializer(
            RoleRepository roleRepository,
            UserRepository userRepository,
            CategoryRepository categoryRepository,
            ProductRepository productRepository,
            CartRepository cartRepository,
            CartItemRepository cartItemRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        Role userRole = ensureRole(AppRole.ROLE_USER);
        Role sellerRole = ensureRole(AppRole.ROLE_SELLER);
        Role adminRole = ensureRole(AppRole.ROLE_ADMIN);

        User demoUser = ensureUser("demo", "demo@gearhub.local", "password123!", Set.of(userRole));
        User sellerUser = ensureUser("seller1", "seller1@gearhub.local", "seller123!", Set.of(userRole, sellerRole));
        ensureUser("admin", "admin@gearhub.local", "admin1234!", Set.of(userRole, sellerRole, adminRole));

        Category audioCategory = ensureCategory("오디오");
        Category computingCategory = ensureCategory("컴퓨팅");
        Category gamingCategory = ensureCategory("게이밍");
        Category mobileAccessoryCategory = ensureCategory("모바일 액세서리");

        Product monitor = ensureProduct(
                computingCategory,
                sellerUser,
                "27인치 QHD 모니터",
                "사무 작업과 콘텐츠 소비를 모두 커버하는 27인치 QHD 모니터입니다. 100Hz 주사율과 USB-C 입력을 지원합니다.",
                "https://images.unsplash.com/photo-1527443195645-1133f7f28990?auto=format&fit=crop&w=1200&q=80",
                18,
                329000,
                12
        );
        Product mechanicalKeyboard = ensureProduct(
                computingCategory,
                sellerUser,
                "저소음 기계식 키보드",
                "업무와 코딩에 모두 어울리는 저소음 기계식 키보드입니다. 무선 3대 멀티페어링을 지원합니다.",
                "https://images.unsplash.com/photo-1511467687858-23d96c32e4ae?auto=format&fit=crop&w=1200&q=80",
                26,
                159000,
                15
        );
        Product bookshelfSpeaker = ensureProduct(
                audioCategory,
                sellerUser,
                "북셀프 스피커",
                "책상과 거실 어디에나 어울리는 2채널 북셀프 스피커입니다. 선명한 중고역과 단단한 저역이 강점입니다.",
                "https://images.unsplash.com/photo-1545454675-3531b543be5d?auto=format&fit=crop&w=1200&q=80",
                14,
                219000,
                10
        );
        Product noiseCancellingHeadphone = ensureProduct(
                audioCategory,
                sellerUser,
                "노이즈 캔슬링 헤드폰",
                "출퇴근과 재택근무를 모두 커버하는 프리미엄 무선 헤드폰입니다. 빠른 충전과 장시간 배터리를 지원합니다.",
                "https://images.unsplash.com/photo-1505740420928-5e560c06d30e?auto=format&fit=crop&w=1200&q=80",
                20,
                289000,
                18
        );
        Product gamingMouse = ensureProduct(
                gamingCategory,
                sellerUser,
                "초경량 게이밍 마우스",
                "59g 경량 설계와 고성능 센서를 갖춘 FPS 중심 게이밍 마우스입니다.",
                "https://images.unsplash.com/photo-1613141411244-0e4ac259d217?auto=format&fit=crop&w=1200&q=80",
                32,
                89000,
                8
        );
        Product gamepad = ensureProduct(
                gamingCategory,
                sellerUser,
                "무선 게임패드",
                "PC와 태블릿, 스마트폰에서 모두 사용할 수 있는 무선 게임패드입니다.",
                "https://images.unsplash.com/photo-1606144042614-b2417e99c4e3?auto=format&fit=crop&w=1200&q=80",
                16,
                79000,
                5
        );
        Product magsafeStand = ensureProduct(
                mobileAccessoryCategory,
                sellerUser,
                "맥세이프 3 in 1 충전 스탠드",
                "스마트폰, 이어버드, 스마트워치를 한 번에 충전하는 데스크형 충전 스탠드입니다.",
                "https://images.unsplash.com/photo-1511707171634-5f897ff02aa9?auto=format&fit=crop&w=1200&q=80",
                22,
                99000,
                14
        );
        Product usbHub = ensureProduct(
                mobileAccessoryCategory,
                sellerUser,
                "USB-C 8포트 멀티 허브",
                "HDMI, USB-A, SD 카드 슬롯을 지원하는 휴대형 멀티 허브입니다. 노트북과 태블릿 연결성을 확장합니다.",
                "https://images.unsplash.com/photo-1545239351-1141bd82e8a6?auto=format&fit=crop&w=1200&q=80",
                28,
                69000,
                7
        );

        ensureAddress(
                demoUser,
                "집",
                "김포트폴리오",
                "010-1234-5678",
                "서울특별시 성동구 왕십리로 83-21",
                "서울특별시 성동구 왕십리로 83-21",
                "서울 성동구 행당동 168-151",
                "04750",
                "서울리버뷰",
                "서울",
                "성동구",
                "행당동"
        );
        ensureAddress(
                demoUser,
                "사무실",
                "김포트폴리오",
                "010-9876-5432",
                "서울특별시 강남구 테헤란로 427",
                "서울특별시 강남구 테헤란로 427",
                "서울 강남구 삼성동 159",
                "06159",
                "테헤란오피스",
                "서울",
                "강남구",
                "삼성동"
        );

        seedDemoCart(demoUser, List.of(
                new SeedCartItem(noiseCancellingHeadphone, 1),
                new SeedCartItem(mechanicalKeyboard, 1),
                new SeedCartItem(magsafeStand, 1),
                new SeedCartItem(bookshelfSpeaker, 1),
                new SeedCartItem(monitor, 1),
                new SeedCartItem(gamingMouse, 1),
                new SeedCartItem(gamepad, 1),
                new SeedCartItem(usbHub, 2)
        ));
    }

    private Role ensureRole(AppRole appRole) {
        return roleRepository.findByRoleName(appRole)
                .orElseGet(() -> roleRepository.save(new Role(appRole)));
    }

    private User ensureUser(String username, String email, String password, Set<Role> roles) {
        User user = userRepository.findByUsername(username)
                .orElseGet(() -> new User(username, email, passwordEncoder.encode(password)));
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setRoles(new HashSet<>(roles));
        return userRepository.save(user);
    }

    private Category ensureCategory(String categoryName) {
        Category category = categoryRepository.findByCategoryName(categoryName);
        if (category == null) {
            category = new Category();
            category.setCategoryName(categoryName);
            category.setProducts(new ArrayList<>());
        }
        return categoryRepository.save(category);
    }

    private Product ensureProduct(
            Category category,
            User seller,
            String productName,
            String description,
            String image,
            int quantity,
            double price,
            double discount
    ) {
        Product product = productRepository.findByProductNameIgnoreCase(productName).orElseGet(Product::new);
        product.setProductName(productName);
        product.setDescription(description);
        product.setImage(image);
        product.setQuantity(quantity);
        product.setPrice(price);
        product.setDiscount(discount);
        product.setSpecialPrice(Math.round((price - (price * (discount / 100.0))) * 100) / 100.0);
        product.setCategory(category);
        product.setUser(seller);
        return productRepository.save(product);
    }

    private void ensureAddress(
            User user,
            String title,
            String recipient,
            String recipientNumber,
            String addressName,
            String roadNameAddress,
            String jibunAddress,
            String postalCode,
            String buildingName,
            String region1DepthName,
            String region2DepthName,
            String region3DepthName
    ) {
        boolean alreadyExists = user.getAddresses().stream()
                .anyMatch(address -> title.equalsIgnoreCase(address.getTitle()) && addressName.equalsIgnoreCase(address.getAddressName()));

        if (alreadyExists) {
            return;
        }

        Address address = new Address();
        address.setTitle(title);
        address.setRecipient(recipient);
        address.setRecipientNumber(recipientNumber);
        address.setAddressName(addressName);
        address.setRoadNameAddress(roadNameAddress);
        address.setJibunAddress(jibunAddress);
        address.setPostalCode(postalCode);
        address.setBuildingName(buildingName);
        address.setRegion1DepthName(region1DepthName);
        address.setRegion2DepthName(region2DepthName);
        address.setRegion3DepthName(region3DepthName);
        address.setUser(user);

        user.getAddresses().add(address);
        userRepository.save(user);
    }

    private void seedDemoCart(User user, List<SeedCartItem> items) {
        Cart cart = cartRepository.findCartByEmail(user.getEmail());
        if (cart == null) {
            cart = new Cart();
            cart.setUser(user);
            cart.setTotalPrice(0.0);
            cart = cartRepository.save(cart);
        }

        cartItemRepository.deleteAllByCartId(cart.getCartId());

        double totalPrice = 0.0;
        for (SeedCartItem item : items) {
            CartItem cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setProduct(item.product());
            cartItem.setQuantity(item.quantity());
            cartItem.setDiscount(item.product().getDiscount());
            cartItem.setProductPrice(item.product().getSpecialPrice());
            cartItemRepository.save(cartItem);

            totalPrice += item.product().getSpecialPrice() * item.quantity();
        }

        cart.setTotalPrice(totalPrice);
        cartRepository.save(cart);
    }

    private record SeedCartItem(Product product, int quantity) {
    }
}
