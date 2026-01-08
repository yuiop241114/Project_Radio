package com.radio.cast.post.dto;

import java.util.List;

public class CursorResponse<PostListResponse> {
  private List<PostListResponse> postDate;
  private Long nextCursor;
  private Boolean hasNext;
  private Integer size;
}
