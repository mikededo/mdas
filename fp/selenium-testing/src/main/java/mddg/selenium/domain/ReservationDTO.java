package mddg.selenium.domain;

import lombok.*;

@ToString
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReservationDTO {
    private String origin;
    private String destination;
    private String outboundDate;
    private String returnDate;
    private Integer passengers;
}
