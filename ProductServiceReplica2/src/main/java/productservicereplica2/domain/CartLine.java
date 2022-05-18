package productservicereplica2.domain;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CartLine {
    private int quantity;
    private String productNumber;
    private double price;
}
