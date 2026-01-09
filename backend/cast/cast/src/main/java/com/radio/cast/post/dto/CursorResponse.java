package com.radio.cast.post.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CursorResponse<PostListResponse> {
  private List<PostListResponse> postList;
  private Long nextCursor;
  private Boolean hasNext;
  private Integer size;
}
