package flab.project.data.dto.model;

import static flab.project.data.enums.PostType.BASIC;

import flab.project.data.enums.PostType;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.Length;

@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter(AccessLevel.PROTECTED)
public class AddBasicPostRequest extends AddPostRequest {
    private static final PostType postType = BASIC;
}
