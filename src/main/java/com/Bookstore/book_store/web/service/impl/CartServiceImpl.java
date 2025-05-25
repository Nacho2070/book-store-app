package com.Bookstore.book_store.web.service.impl;

import com.Bookstore.book_store.exceptions.APIException;
import com.Bookstore.book_store.model.Book;
import com.Bookstore.book_store.model.Cart;
import com.Bookstore.book_store.model.ItemCart;
import com.Bookstore.book_store.utils.AuthUtil;
import com.Bookstore.book_store.web.payload.BookDto;
import com.Bookstore.book_store.web.payload.CartDTO;
import com.Bookstore.book_store.web.payload.ItemCartDTO;
import com.Bookstore.book_store.web.repository.BookRepository;
import com.Bookstore.book_store.web.repository.CartRepository;
import com.Bookstore.book_store.web.repository.ItemCartRepository;
import com.Bookstore.book_store.web.service.CartService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartServiceImpl  implements CartService {

    private final CartRepository cartRepository;
    private final BookRepository bookRepository;
    private final ItemCartRepository itemCartRepository;
    private final ModelMapper modelMapper;
    private final AuthUtil authUtil;

    @Override
    public String createOrUpdateCart(List<ItemCartDTO> cartItems) {

        String userMailAuth = authUtil.loggedInEmail();

        // Check if the user have a cart
        Cart existingCart = cartRepository.findCartByMail(userMailAuth);
        if (existingCart == null) {
            existingCart = new Cart();
            existingCart.setUser(authUtil.loggedInUser());
            existingCart.setTotalPrice(0.0);
            existingCart = cartRepository.save(existingCart);
        }else {
            cartRepository.deleteById(existingCart.getCartId());
        }
        double totalPrice = 0.00;

        for (ItemCartDTO itemCartDTO : cartItems) {
            Book book = bookRepository.findById(itemCartDTO.getBookId()).orElseThrow(RuntimeException::new);

            totalPrice += book.getPrice() * itemCartDTO.getQuantity();

            ItemCart itemCart = new ItemCart();
            itemCart.setBook(book);
            itemCart.setQuantity((int) itemCartDTO.getQuantity());
            itemCart.setCart(existingCart);
            itemCart.setBookPrice(book.getPrice());
            itemCartRepository.save(itemCart);
        }
        existingCart.setTotalPrice(totalPrice);
        cartRepository.save(existingCart);
        return "Cart created or updated successfully!";
    }

    @Override
    public List<CartDTO> getCarts() {
        List<Cart> cartList = cartRepository.findAll();
        if(cartList.isEmpty()) {
            throw new APIException("There are no carts in the database!");
        }
        return cartList.stream().map(this::mapToCartDto).collect(Collectors.toList());
    }

    @Override
    public CartDTO addBookToCart(Long bookId, Integer quantity) {

        Cart cart  = createCart();
        Book book = bookRepository.findById(bookId).orElseThrow(RuntimeException::new);

        ItemCart itemCart = itemCartRepository.findItemCartBycartIdAndBookId(cart.getCartId(),book.getBookId());

        if (itemCart != null) {
            throw new APIException("Product already exist in the cart!");
        }

        if (book.getQuantity() == 0) {
            throw new APIException("Book have not stock");
        }

        if (book.getQuantity() < quantity) {
            throw new APIException("Product " + book.getTitle() + " have not enough stock, please make a order with less than " + quantity);
        }


        ItemCart newItemCart = new ItemCart();
        newItemCart.setBook(book);
        newItemCart.setQuantity(quantity);

        newItemCart.setCart(cart);

        itemCartRepository.save(newItemCart);

        cart.setTotalPrice(cart.getTotalPrice() + (book.getPrice() * quantity));

        cartRepository.save(cart);

        return mapToCartDto(cart);
    }

    private Cart createCart() {

        String userMailAuth = authUtil.loggedInEmail();

        Cart cart = cartRepository.findCartByMail(userMailAuth);
        if (cart == null) {
            cart = new Cart();
            cart.setTotalPrice(0.00);
            cart.setUser(authUtil.loggedInUser());
            return cart;
        }
        return cart;
    }

    @Override
    public String deleteBookFromCart(Long cartId, Long bookId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(()-> new APIException("cart not found"));
        ItemCart itemCart = itemCartRepository.findItemCartBycartIdAndBookId(cartId,bookId);
        if (itemCart == null) {
            throw new APIException("Item "+bookId+" not found");
        }
        cart.setTotalPrice(cart.getTotalPrice() - (itemCart.getBookPrice() - itemCart.getQuantity()) );
        itemCartRepository.deleteCartItemByBookIdAndCartId(bookId,cartId);
        return "Product "+itemCart.getBook().getTitle()+" removed successfully!";
    }

    private CartDTO mapToCartDto(Cart cart) {
        CartDTO cartDTO = modelMapper.map(cart, CartDTO.class);

        List<BookDto> bookDto = cart.getItemCart().stream().map(item -> {
            Book itemBook = item.getBook();
            return BookDto.builder()
                    .bookId(itemBook.getBookId())
                    .title(itemBook.getTitle())
                    .author(itemBook.getAuthor())
                    .description(itemBook.getDescription())
                    .image(itemBook.getImage())
                    .genre(itemBook.getGenre().getGenreName())
                    .price(itemBook.getPrice())
                    .quantity(itemBook.getQuantity())
                    .build();
        }).toList();
        cartDTO.setBookDto(bookDto);
        return cartDTO;
    }
}
