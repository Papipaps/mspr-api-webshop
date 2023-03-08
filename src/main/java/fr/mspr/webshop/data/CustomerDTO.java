package fr.mspr.webshop.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CustomerDTO extends BaseDTO {

    private Long id;
    private LocalDateTime createdAt;
    private String name;
    private String email;
    private String username;
    private String firstName;
    private String lastName;
    private Adress address;
    private Company company;
    private List<OrderDTO> orders;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerDTO that = (CustomerDTO) o;
        return Objects.equals(username, that.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }
}
