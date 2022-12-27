package uz.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link uz.domain.Text} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TextDTO implements Serializable {

    private Long id;

    @NotNull
    private String text;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TextDTO)) {
            return false;
        }

        TextDTO textDTO = (TextDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, textDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TextDTO{" +
            "id=" + getId() +
            ", text='" + getText() + "'" +
            "}";
    }
}
