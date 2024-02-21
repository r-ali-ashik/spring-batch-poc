package cloud.ap.poc.dto;

import lombok.Data;

@Data
public class SalesInfoDto {
    private String product;
    private String seller;
    private Integer sellerId;
    private Double price;
    private String city;
    private String category;
}
