package com.example.bookservice.service;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Price;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
public class OrderService {
    @Value("${APIKEY}")
    private String apiKey;

    public RedirectView createOrder(String price) throws StripeException {

        price = price.replaceAll("[^\\d.]", "");

        //Parsing the price string into a double
        double priceDouble = Double.parseDouble(price);

        //Converting the double price to an integer (if needed)
        int priceInt = (int) priceDouble;

        Stripe.apiKey = apiKey;

        Map<String, Object> priceParams = new HashMap<>();
        priceParams.put("unit_amount", priceInt*100);
        priceParams.put("currency", "usd");
        priceParams.put("product_data", Collections.singletonMap("name", "Tome Treasures"));

        // Create the price
        Price priceObject = Price.create(priceParams);

        SessionCreateParams.LineItem item = SessionCreateParams.LineItem.builder()
                .setQuantity(1L)
                .setPrice(priceObject.getId())
                .build();

        SessionCreateParams params = SessionCreateParams.builder()
                .setInvoiceCreation(
                        SessionCreateParams.InvoiceCreation.builder().setEnabled(true).build()

                )
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("https://tome-treasures.onrender.com/success")
                .setCancelUrl("https://tome-treasures.onrender.com/cancel")
                .addLineItem(item)
                .putExtraParam("billing_address_collection", "required")
                .build();

        com.stripe.model.checkout.Session session = com.stripe.model.checkout.Session.create(params);

        return new RedirectView(session.getUrl(), true);
    }




}
