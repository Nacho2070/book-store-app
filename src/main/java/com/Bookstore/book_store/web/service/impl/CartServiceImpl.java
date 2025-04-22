package com.Bookstore.book_store.web.service.impl;

import com.Bookstore.book_store.model.Book;
import com.Bookstore.book_store.model.Cart;
import com.Bookstore.book_store.model.ItemCart;
import com.Bookstore.book_store.web.payload.BookDto;
import com.Bookstore.book_store.web.payload.CartDTO;
import com.Bookstore.book_store.web.payload.ItemCartDTO;
import com.Bookstore.book_store.web.repository.BookRepository;
import com.Bookstore.book_store.web.repository.CartRepository;
import com.Bookstore.book_store.web.repository.ItemCartRepository;
import com.Bookstore.book_store.web.service.CartService;
import jakarta.transaction.Transactional;
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
    private ModelMapper modelMapper;

    @Override
    public String createOrUpdateCart(List<ItemCartDTO> cartItems) {
        // Autorizado
        String userMailAuth = "pepe";

        // Check if the user have a cart
        Cart cart = cartRepository.findByuserMail(userMailAuth);
        if (cart == null) {
            cart = new Cart();
            // cart.setUser();
            cart.setTotalPrice(0.0);
        }else {
            cartRepository.deleteById(cart.getCartId());
        }/*
        List<ItemCart> cartsitems =  cartItems.stream()
                .map(()->{

                })
                .toList();*/
        Double totalPrice = Double.MIN_VALUE;

        for (ItemCartDTO itemCartDTO : cartItems) {
            ItemCart itemCart = new ItemCart();
            Book book = bookRepository.findById(itemCartDTO.getBookId()).orElseThrow(RuntimeException::new);

            totalPrice += book.getPrice();

            itemCart.setBook(book);
            itemCart.setQuantity((int) itemCartDTO.getQuantity());
            itemCart.setCart(cart);
            itemCartRepository.save(itemCart);
        }
        cart.setTotalPrice(totalPrice);
        cartRepository.save(cart);
        return "Cart created or updated successfully!";
    }

    @Override
    public List<CartDTO> getCarts() {
        List<Cart> cartList = cartRepository.findAll();

        return cartList.stream().map( cart -> {
           CartDTO cartDTO = modelMapper.map(cart, CartDTO.class);

           List<BookDto> bookDto = cart.getItemCart().stream().map(itemCart -> {
                Book book = itemCart.getBook();
                       return BookDto.builder()
                               .bookId(book.getBookId())
                               .title(book.getTitle())
                               .author(book.getAuthor())
                               .description(book.getDescription())
                               .image(book.getImage())
                               .price(book.getPrice())
                               .quantity(itemCart.getQuantity())

                               .build();

        }).toList();
           cartDTO.setBookDto(bookDto);
           return cartDTO;
        }).collect(Collectors.toList());

    }

    @Override
    public CartDTO addBookToCart(Long bookId, Integer quantity) {


        Cart cart = findCartByUserMail("pepe");

        Book book = bookRepository.findById(bookId).orElseThrow(RuntimeException::new);

        ItemCart itemCart = itemCartRepository.findItemCartBycartIdAndBookId(cart.getCartId(),book.getBookId());

        if (itemCart != null) {
            throw new RuntimeException("Product already exist in the cart!");
        }

        if (book.getQuantity() == 0) {
            throw new RuntimeException("Book have not stock");
        }

        if (book.getQuantity() < quantity) {
            throw new RuntimeException("Product " + book.getTitle() + " have not enough stock, please make a order with less than " + quantity);
        }


        ItemCart newItemCart = new ItemCart();
        newItemCart.setBook(book);
        newItemCart.setQuantity(quantity);
        newItemCart.setCart(cart);

        itemCartRepository.save(newItemCart);

        cart.setTotalPrice(cart.getTotalPrice() + book.getPrice());

        cartRepository.save(cart);

        return mapToCartDto(cart);
    }

    private Cart findCartByUserMail(String userMail) {
        Cart cart = cartRepository.findByuserMail(userMail);
        if (cart == null) {
            cart = new Cart();
            cart.setTotalPrice(0.0);
            return cart;
        }
        return cart;
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
                    .price(itemBook.getPrice())
                    .quantity(itemBook.getQuantity())
                    .build();
        }).toList();
        cartDTO.setBookDto(bookDto);
        return cartDTO;
    }
}
