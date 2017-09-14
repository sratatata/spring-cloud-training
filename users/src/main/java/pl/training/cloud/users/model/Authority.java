package pl.training.cloud.users.model;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(exclude = "users")
@RequiredArgsConstructor
@NoArgsConstructor
@Table(name = "authorities")
@Entity
@Data
public class Authority implements GrantedAuthority {

    @GeneratedValue
    @Id
    private Long id;
    @NonNull
    private String name;
    @ManyToMany(mappedBy = "authorities")
    private Set<User> users = new HashSet<>();

    @Override
    public String getAuthority() {
        return name;
    }

}
