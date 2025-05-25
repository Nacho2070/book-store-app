package com.Bookstore.book_store.web.controller;

import com.Bookstore.book_store.web.payload.CartDTO;
import com.Bookstore.book_store.web.payload.ItemCartDTO;
import com.Bookstore.book_store.web.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/public")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping("/cart/create")
    public ResponseEntity<String> createOrUpdateCart(@RequestBody List<ItemCartDTO> cartItems) {
        String response = cartService.createOrUpdateCart(cartItems);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    @PostMapping("/cart/book/{bookId}/quantity/{quantity}")
    public ResponseEntity<CartDTO> addBookToCart(
            @PathVariable Long bookId,
            @PathVariable Integer quantity)
    {
        CartDTO cartDTO = cartService.addBookToCart(bookId,quantity);
        return new ResponseEntity<>(cartDTO,HttpStatus.CREATED);
    }

    @GetMapping("/cart")
    public ResponseEntity<List<CartDTO> > getCarts()
    {
        List<CartDTO> cartDTO = cartService.getCarts();
        return ResponseEntity.ok(cartDTO);
    }
    @DeleteMapping("/cart/{cartId}/bookId/{bookId}")
    public ResponseEntity<String> deleteProductFromCart(@PathVariable Long cartId, @PathVariable Long bookId)
    {
        String status = cartService.deleteBookFromCart(cartId,bookId);
        return new ResponseEntity<>(status,HttpStatus.OK);
    }
}
